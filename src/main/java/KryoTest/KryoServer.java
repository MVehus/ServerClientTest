package KryoTest;

import Test1.TestObject;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;

public class KryoServer {

    Server server;
    ArrayList<Connection> clients;

    public KryoServer(int port) {
        server = new Server();
        server.start();
        try {
            server.bind(port);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        clients = new ArrayList<>();
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (!clients.contains(connection))
                    clients.add(connection);

                if (object instanceof String) {
                    String request = (String) object;
                    System.out.println(request);

                    String response = "Hallo ja";
                    connection.sendTCP(response);
                }
            }
        });
    }

    public static void main(String[] args) {
        KryoServer server = new KryoServer(32401);
    }
}
