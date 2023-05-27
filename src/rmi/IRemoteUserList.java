package rmi;

import shared.UserInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRemoteUserList extends Remote {
    void addClient(String username, IRemoteUserPanel remoteUserPanel) throws RemoteException;
    void setServerUI(UserInterface serverUI) throws RemoteException;
    List<String> getUserList() throws RemoteException;
}
