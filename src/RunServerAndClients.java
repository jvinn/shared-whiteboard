import client.Client;
import server.Server;

public class RunServerAndClients {
    public static void main(String[] args) {
        new Server("localhost", 4444, "james");
        new Client("localhost", 4444, "will");

//        for(int i = 0; i < 15; i++) {
//            new Client("localhost", 4444, "User " + i);
//        }
    }
}
