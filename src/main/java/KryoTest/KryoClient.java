package KryoTest;

import Test1.TestObject;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class KryoClient {

    public KryoClient(String IpAddress, int port) {
        Client client = new Client();
        client.start();
        try {
            client.connect(5000, IpAddress, port);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof String) {
                    String response = (String) object;
                    System.out.println(response);
                }
            }
        });
        String testString = "hei";
        client.sendTCP(testString);
    }

    public static void main(String[] args) {
        KryoClient client = new KryoClient("92.221.197.177", 32401);
    }
}
