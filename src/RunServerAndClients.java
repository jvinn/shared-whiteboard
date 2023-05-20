import client.Client;
import server.Server;

public class RunServerAndClients {
    public static void main(String[] args) {
        new Server("localhost", 4444, "james-server");
        new Client("localhost", 4444, "james-client-1");
        new Client("localhost", 4444, "james-client-2");
//        new Client("localhost", 4444, "james-client-3");
    }
}
