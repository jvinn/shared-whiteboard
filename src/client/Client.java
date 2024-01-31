package client;

import rmi.*;
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

    public Client(String serverIP, int serverPort, String username) {
        try {
            Registry registry = LocateRegistry.getRegistry(serverIP, serverPort);
            IRemoteChatList remoteChat = (IRemoteChatList) registry.lookup("RemoteChat");
            IRemoteSketches remoteSketches = (IRemoteSketches) registry.lookup("RemoteSketches");
            IRemoteUserList remoteUsers = (IRemoteUserList) registry.lookup("RemoteUsers");
            UserInterface clientUI = new UserInterface(username + " [Client]", remoteChat, username, false);

            RemoteUserPanel userPanel = clientUI.getUserPanel();
            UnicastRemoteObject.exportObject(userPanel, 0);
            remoteUsers.addClient(username, userPanel);

            RemoteChatPanel chatPanel = clientUI.getChatPanel();
            UnicastRemoteObject.exportObject(chatPanel, 0);
            remoteChat.addChatPanel(chatPanel);

            MyCanvas clientCanvas = clientUI.getCanvas();
            clientCanvas.setRemoteSketches(remoteSketches);
            UnicastRemoteObject.exportObject(clientCanvas, 0);
            remoteSketches.addClientCanvas(clientCanvas);

            log.info("New client \"" + username + "\" connected to " + serverIP + ":" + serverPort);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
