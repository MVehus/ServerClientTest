import net.tomp2p.p2p.Peer;

public class main {

    public static void main(String[] args) {
        int port = 5000;
        String IPAdress = "localhost";
        Thread t = new Thread((Runnable) new Server(port));

        Client client = new Client(IPAdress, port);
    }
}
