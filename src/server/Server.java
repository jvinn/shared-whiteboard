package server;

import rmi.*;
import shared.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

public class Server {
    private static final Logger log = Logger.getLogger(Server.class.getName());

    public Server(String serverIP, int serverPort, String username) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099); // TODO: The serverIP isn't used - CONCERNING!
            IRemoteSketches remoteSketches = new RemoteSketches();
            IRemoteUserList remoteUsers = new RemoteUserList();
            IRemoteChatList remoteChat = new RemoteChatList();
            UserInterface serverUI = new UserInterface(username + " [Server]", remoteChat, username);

            remoteSketches.setServerCanvas(serverUI.getCanvas());
            remoteUsers.setServerUI(serverUI);
            remoteChat.setServerChatPanel(serverUI.getChatPanel());

            registry.bind("RemoteSketches", remoteSketches);
            registry.bind("RemoteUsers", remoteUsers);
            registry.bind("RemoteChat", remoteChat);

            serverUI.getCanvas().setRemoteSketches(remoteSketches);
            remoteChat.setServerChatPanel(serverUI.getChatPanel());

            log.info("New server started by \"" + username + "\" at " + serverIP + ":" + serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
