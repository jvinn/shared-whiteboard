package server;

import rmi.*;
import shared.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

public class Server {
    private static final Logger log = Logger.getLogger(Server.class.getName());
    private String username;
    private UserInterface serverUI;
    private Registry registry;
    private IRemoteSketches remoteSketches;
    private IRemoteUserList remoteUsers;
    private IRemoteChatList remoteChat;

    public Server(String serverIP, int serverPort, String username) {
        try {
            this.username = username;
            this.registry = LocateRegistry.createRegistry(1099); // TODO: The serverIP isn't used - CONCERNING!
            this.remoteSketches = new RemoteSketches();
            this.remoteUsers = new RemoteUserList();
            this.remoteChat = new RemoteChatList();
            this.serverUI = new UserInterface(username + " [Server]", true, remoteChat, username);

            remoteSketches.setServerCanvas(serverUI.getCanvas());
            remoteUsers.setServerUI(serverUI);
            remoteChat.setServerChatPanel(serverUI.getChatPanel());

            registry.bind("RemoteSketches", remoteSketches);
            registry.bind("RemoteUsers", remoteUsers);
            registry.bind("RemoteChat", remoteChat);

            serverUI.getCanvas().setRemoteSketches(remoteSketches);
            serverUI.setRemoteChatList(remoteChat);
            remoteChat.setServerChatPanel(serverUI.getChatPanel());

            log.info("New server started by \"" + username + "\" at " + serverIP + ":" + serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
