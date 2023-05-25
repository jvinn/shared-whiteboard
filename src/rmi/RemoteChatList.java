package rmi;

import shared.ChatMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RemoteChatList extends UnicastRemoteObject implements IRemoteChatList {
    RemoteChatPanel serverChatPanel;
    List<IRemoteChatPanel> remoteChatPanels = new ArrayList<>();
    List<ChatMessage> messages = new ArrayList<>();

    public RemoteChatList() throws RemoteException {
        super();
    }

    @Override
    public void addChatPanel(IRemoteChatPanel remoteChatPanel) throws RemoteException {
        remoteChatPanels.add(remoteChatPanel);
    }

    @Override
    public void addMessage(ChatMessage message) throws RemoteException {
        messages.add(message);

        serverChatPanel.updateChatPanel(messages);
        serverChatPanel.repaint();

        for(IRemoteChatPanel remoteChatPanel : remoteChatPanels) {
            remoteChatPanel.updateChatPanel(messages);
        }
    }

    @Override
    public void setServerChatPanel(RemoteChatPanel serverChatPanel) throws RemoteException {
        this.serverChatPanel = serverChatPanel;
    }
}
