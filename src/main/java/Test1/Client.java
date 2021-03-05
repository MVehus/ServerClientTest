package Test1;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    //private PrintWriter out;
    //private BufferedReader in;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    //private BufferedReader stdIn;

    public Client(String IPAddress, int port) {
        // Tries to connect the client to the server.
        try {
            socket = new Socket(IPAddress, port);
            //out = new PrintWriter(socket.getOutputStream(), true);
            //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //stdIn = new BufferedReader(new InputStreamReader(System.in));
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server");

            //Incoming message handler
            new Thread(() -> {
                while (true) {
                    try {
                        //System.out.println(in.readLine());
                        System.out.println(in.readObject().toString());
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }).start();

            // Checks for keyboard input and sends to server
            TestObject testObject = new TestObject();
            /*
            while (true) {
                String userInput = stdIn.readLine();
                out.println(userInput);
            }
             */
            out.writeObject(testObject);
            while(true){

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("92.221.197.177", 32401);
    }
}
