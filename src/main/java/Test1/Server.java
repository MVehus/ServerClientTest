package Test1;

import Utilities.IpChecker;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ArrayList<ServerSideConnection> clients;
    private Queue<String> messages;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Server IP: " + IpChecker.getIp() + "\nPort: " + port);
            System.out.println("Waiting for clients...");
            clients = new ArrayList<>();
            messages = new LinkedList<>();

            while (clients.size() < 2) {
                clientSocket = serverSocket.accept();
                ServerSideConnection ssc = new ServerSideConnection(clientSocket);
                clients.add(ssc);
                new Thread(ssc).start();
                System.out.println("Client connected");
            }

            while (true){
                if(!messages.isEmpty()){
                    sendToAllClients(messages.poll());
                }
            }

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void sendToAllClients(String str) {
        for (ServerSideConnection client : clients) {
            client.write(str);
        }
    }


    private class ServerSideConnection implements Runnable {

        private BufferedReader in;
        private PrintWriter out;
        Socket socket;

        public ServerSideConnection(Socket clientSocket) {
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
                        System.out.println("Message: " + inputLine + " from " + clientSocket.toString());
                        this.write(inputLine);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        public void write(String str) {
            try {
                out.println(str);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(32401);
    }
}
