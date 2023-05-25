package client;

import rmi.*;
import shared.ChatMessage;
import shared.MyCanvas;
import shared.UserInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Client {
    private static final Logger log = Logger.getLogger(Client.class.getName());
    private final String username;
    private final Registry registry;
    private final IRemoteSketches remoteSketches;
    private final IRemoteUserList remoteUsers;
    private final IRemoteChatList remoteChat;
    private final UserInterface clientUI;

    public Client(String serverIP, int serverPort, String username) {
        try {
            this.username = username;
            this.registry = LocateRegistry.getRegistry("localhost");
            this.remoteChat = (IRemoteChatList) registry.lookup("RemoteChat");
            this.remoteSketches = (IRemoteSketches) registry.lookup("RemoteSketches");
            this.remoteUsers = (IRemoteUserList) registry.lookup("RemoteUsers");
            this.clientUI = new UserInterface(username + " [Client]", false, remoteChat, username);

            RemoteUserPanel userPanel = clientUI.getUserPanel();
            UnicastRemoteObject.exportObject(userPanel, serverPort);
            remoteUsers.addClient(username, userPanel);

            RemoteChatPanel chatPanel = clientUI.getChatPanel();
            UnicastRemoteObject.exportObject(chatPanel, serverPort);
            remoteChat.addChatPanel(chatPanel);

            MyCanvas clientCanvas = clientUI.getCanvas();
            clientCanvas.setRemoteSketches(remoteSketches);
            UnicastRemoteObject.exportObject(clientCanvas, serverPort);
            remoteSketches.addClientCanvas(clientCanvas);

            log.info("New client \"" + username + "\" connected to " + serverIP + ":" + serverPort);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
