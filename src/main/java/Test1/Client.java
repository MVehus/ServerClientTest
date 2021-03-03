package Test1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;

    public Client(String IPAddress, int port)
    {
        try
        {
            socket = new Socket(IPAddress, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to server");
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            while (true)
            {
                String userInput;
                if ((userInput = stdIn.readLine()) != null)
                {
                    out.println(userInput);
                    System.out.println("echo: " + in.readLine());
                }
                if(in.readLine() != null){
                    System.out.println("Message from antoher user: " + in.readLine());
                }
            }

        } catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("IP address: ");
        String IPAddress = scanner.nextLine();
        System.out.println("Port: ");
        int port = scanner.nextInt();
        Client client = new Client(IPAddress, port);
    }
}
