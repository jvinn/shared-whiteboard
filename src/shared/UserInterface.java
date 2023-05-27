package shared;

import rmi.IRemoteChatList;
import rmi.RemoteChatPanel;
import rmi.RemoteUserPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.List;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static shared.Utils.*;

public class UserInterface {
    private final RemoteUserPanel userPanel;
    private final RemoteChatPanel chatPanel;
    private JList<String> userList;
    private final MyCanvas canvasPanel;

    public UserInterface(String title, IRemoteChatList remoteChatList, String username, boolean isServer) {

        JFrame jFrame = new JFrame();
        jFrame.setTitle(title);
        jFrame.setSize(800, 600);
        jFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    canvasPanel.getRemoteSketches().closeClients();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });
        canvasPanel = new MyCanvas();
        canvasPanel.setBorder(BorderFactory.createMatteBorder(1,2,1,1, Color.BLACK));

        JMenuBar menuBar = new JMenuBar();
        if(isServer) initializeMenuBar(menuBar, canvasPanel, jFrame);

        JPanel buttonPanel = new JPanel();
        initializeButtons(canvasPanel, buttonPanel);

        JPanel colorPanel = new JPanel();
        initializeColors(colorPanel, canvasPanel);

        userPanel = new RemoteUserPanel();
        userList = new JList<>();
        initializeUserList(userPanel, userList);

        chatPanel = new RemoteChatPanel();
        initializeChat(remoteChatList, chatPanel, username);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(buttonPanel);
        northPanel.add(colorPanel);

        jFrame.getContentPane().add(chatPanel, BorderLayout.SOUTH);
        jFrame.getContentPane().add(northPanel, BorderLayout.NORTH);
        jFrame.getContentPane().add(canvasPanel, BorderLayout.CENTER);
        jFrame.getContentPane().add(userPanel, BorderLayout.EAST);
        jFrame.setVisible(true);
    }

    public MyCanvas getCanvas() {
        return canvasPanel;
    }

    public RemoteUserPanel getUserPanel() {
        return userPanel;
    }

    public RemoteChatPanel getChatPanel() {
        return chatPanel;
    }

    public void updateUserList(List<String> newUserList) {
        userPanel.remove(userList);
        userList = new JList<>(newUserList.toArray(new String[0]));
        userPanel.add(userList);
        userPanel.revalidate();
        userPanel.repaint();
    }
}