package Test1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;

    public Client(String IPAddress, int port) {
        try {
            socket = new Socket(IPAddress, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to server");
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            //Incoming message handler
            new Thread(() -> {
                while (true) {
                    try {
                        System.out.println(in.readLine());
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }).start();

            // Checks for keyboard input and sends to server
            while (true) {
                String userInput = stdIn.readLine();
                out.println(userInput);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        /*
        Scanner scanner = new Scanner(System.in);
        System.out.println("IP address: ");
        String IPAddress = scanner.nextLine();
        System.out.println("Port: ");
        int port = scanner.nextInt();

         */
        Client client = new Client("92.221.197.177", 32401);
    }
}
