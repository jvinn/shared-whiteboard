package rmi;

import shared.UserInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RemoteUserList extends UnicastRemoteObject implements IRemoteUserList {
    private final List<IRemoteUserPanel> clientUserPanels = new ArrayList<>();
    private final List<String> userList = new ArrayList<>();
    private UserInterface serverUI;

    public RemoteUserList(String serverUsername) throws RemoteException {
        super();
        userList.add(serverUsername);
    }

    @Override
    public void addClient(String username, IRemoteUserPanel remoteUserPanel) throws RemoteException {
        userList.add(username);
        clientUserPanels.add(remoteUserPanel);

        serverUI.updateUserList(userList);

        for(IRemoteUserPanel clientUserPanel : clientUserPanels) {
            clientUserPanel.updateUserList(userList);
        }
    }

    @Override
    public void setServerUI(UserInterface serverUI) {
        this.serverUI = serverUI;
    }

    @Override
    public List<String> getUserList() {
        return userList;
    }
}
