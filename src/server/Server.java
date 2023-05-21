package server;

import shared.IRemoteSketches;
import shared.RemoteSketches;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

/*
TODO:
The clients need to create their own RMI objects which are iterated through by the server to update their whiteboards
This RMI object should be the client canvases so the server can then call repaint on them

See RemotePaint in the example code
The UnicastRemoteObject (RemotePaint) should contain the canvas



 */

public class Server {
    private static final Logger log = Logger.getLogger(Server.class.getName());
    private String serverIP;
    private int serverPort;
    private String username;
    private ServerUI serverUI;
    private Registry registry;
    private IRemoteSketches remoteSketches;

    public Server(String serverIP, int serverPort, String username) {
        try {
            this.serverIP = serverIP;
            this.serverPort = serverPort;
            this.username = username;

            this.serverUI = new ServerUI(username + " [Server]");
            this.registry = LocateRegistry.createRegistry(1099);
            this.remoteSketches = new RemoteSketches(serverUI.getCanvas());

            registry.bind("RemoteSketches", remoteSketches);

            serverUI.getCanvas().setRemoteSketches(remoteSketches);

            log.info("New server started by \"" + username + "\" at " + serverIP + ":" + serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
