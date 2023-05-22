package client;

import shared.IRemoteSketches;
import shared.MyCanvas;
import shared.UserInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class Client {
    private static final Logger log = Logger.getLogger(Client.class.getName());
    private final String serverIP;
    private final int serverPort;
    private final String username;
    private final Registry registry;
    private final IRemoteSketches remoteSketches;
    private final UserInterface clientUI;

    public Client(String serverIP, int serverPort, String username) {
        try {
            this.serverIP = serverIP;
            this.serverPort = serverPort;
            this.username = username;
            this.registry = LocateRegistry.getRegistry("localhost");
            this.remoteSketches = (IRemoteSketches) registry.lookup("RemoteSketches");
            this.clientUI = new UserInterface(username + " [Client]");

            MyCanvas clientCanvas = clientUI.getCanvas();
            clientCanvas.setRemoteSketches(remoteSketches);
            UnicastRemoteObject.exportObject(clientCanvas, 4444);
            remoteSketches.addClientCanvas(clientCanvas);

            log.info("New client \"" + username + "\" connected to " + serverIP + ":" + serverPort);

        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
