package rmi;

import shared.ChatMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRemoteChatPanel extends Remote {
    void updateChatPanel(List<ChatMessage> chatMessages) throws RemoteException;
}
