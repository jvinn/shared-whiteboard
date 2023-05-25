package rmi;

import shared.UserInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteUserList extends Remote {
    void addClient(String username, IRemoteUserPanel remoteUserPanel) throws RemoteException;
    void setServerUI(UserInterface serverUI) throws RemoteException;
}
