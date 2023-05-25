package rmi;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.List;

public class RemoteUserPanel extends JPanel implements IRemoteUserPanel {
    public RemoteUserPanel() {
        super();
    }

    @Override
    public void updateUserList(List<String> newUserList) throws RemoteException {
        SwingUtilities.invokeLater(() -> {
            remove(1);
            add(new JList<>(newUserList.toArray(new String[0])));
            revalidate();
            repaint();
        });
    }
}
