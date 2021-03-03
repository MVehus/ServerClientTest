package Test1;

import Utilities.IpChecker;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Server implements Observer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ArrayList<ServerSideConnection> clients;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Server IP: " + IpChecker.getIp() + "\nPort: " + port);
            System.out.println("Waiting for clients...");
            clients = new ArrayList<>();

            while (true) {
                clientSocket = serverSocket.accept();
                ServerSideConnection ssc = new ServerSideConnection(clientSocket);
                clients.add(ssc);
                new Thread(ssc).start();
                System.out.println("Client connected");
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

    @Override
    public void update(Observable o, Object arg) {
        sendToAllClients(arg.toString());
    }

    private class ServerSideConnection extends Observable implements Runnable {

        private BufferedReader in;
        private PrintWriter out;
        Socket socket;
        //String inputLine;

        public ServerSideConnection(Socket clientSocket) {
            socket = clientSocket;
            //addObserver(Server.this);
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
                        //this.write(inputLine);
                        Server.this.sendToAllClients(inputLine);
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
