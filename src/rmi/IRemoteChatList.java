package rmi;

import shared.ChatMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteChatList extends Remote {
    void addChatPanel(IRemoteChatPanel remoteChatPanel) throws RemoteException;
    void addMessage(ChatMessage message) throws RemoteException;
    void setServerChatPanel(RemoteChatPanel serverChatPanel) throws RemoteException;
}
