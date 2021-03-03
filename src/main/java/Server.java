import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;



    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Server IP: " + InetAddress.getLocalHost().getHostAddress() + "\nPort: " + port);
            System.out.println("Waiting for clients...");


            while(true) {
                clientSocket = serverSocket.accept();
                ServerSideConnection ssc = new ServerSideConnection(clientSocket);
                Thread t = new Thread(ssc);
                t.start();
                System.out.println("Client connected");
            }

        } catch (IOException e){
            System.out.println(e.toString());
        }
    }

    private class ServerSideConnection implements Runnable {

        private BufferedReader in;
        private PrintWriter out;
        Socket socket;

        public ServerSideConnection(Socket clientSocket){
            socket = clientSocket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while (true) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Message from " + inputLine + " from " + clientSocket.toString());
                         out.println(inputLine);
                    }
                }
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(5000);
    }


}
