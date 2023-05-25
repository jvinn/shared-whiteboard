package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRemoteUserPanel extends Remote {
    void updateUserList(List<String> newUserList) throws RemoteException;
}
