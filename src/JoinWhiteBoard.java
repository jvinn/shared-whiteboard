import client.Client;
import javax.swing.JOptionPane;

public class JoinWhiteBoard {
    private static final int REQUIRED_ARGS = 3;

    public static void main(String[] args) {
        if(args.length != REQUIRED_ARGS) {
            JOptionPane.showMessageDialog(null, "Incorrect number of arguments. Usage: java JoinWhiteBoard <serverIPAddress> <serverPort> <username>", "Error", JOptionPane.ERROR_MESSAGE);
        }

        String serverIP = args[0];
        int serverPort = Integer.parseInt(args[1]);
        String username = args[2];

        new Client(serverIP, serverPort, username);
    }
}
