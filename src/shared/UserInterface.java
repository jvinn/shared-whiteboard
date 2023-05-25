package shared;

import rmi.IRemoteChatList;
import rmi.RemoteChatPanel;
import rmi.RemoteUserPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static shared.Utils.*;

public class UserInterface {
    private JFrame jFrame;
    private JPanel buttonPanel;
    private JPanel colorPanel;
    private JTextField textField;
    private RemoteUserPanel userPanel;
    private RemoteChatPanel chatPanel;
    private IRemoteChatList remoteChatList;
    private JList<String> userList;
    private MyCanvas canvasPanel;
    private boolean isServer;
    private String username;

    public UserInterface(String title, boolean isServer, IRemoteChatList remoteChatList, String username) {
        this.isServer = isServer;
        this.remoteChatList = remoteChatList;
        this.username = username;

        jFrame = new JFrame();
        jFrame.setTitle(title);
        jFrame.setSize(800, 600);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        canvasPanel = new MyCanvas();
        canvasPanel.setBorder(BorderFactory.createMatteBorder(1,2,1,1, Color.BLACK));

        buttonPanel = new JPanel();
        initializeButtons(canvasPanel, buttonPanel);

        colorPanel = new JPanel();
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

        jFrame.getContentPane().add(northPanel, BorderLayout.NORTH);
        jFrame.getContentPane().add(canvasPanel, BorderLayout.CENTER);
        jFrame.getContentPane().add(userPanel, BorderLayout.EAST);
        jFrame.getContentPane().add(chatPanel, BorderLayout.SOUTH);
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
        userList = new JList<>(newUserList.toArray(new String[0])); // converting to array and assigning it to userList
        userPanel.add(userList);
        userPanel.revalidate();
        userPanel.repaint();
    }

    public void setRemoteChatList(IRemoteChatList remoteChatList) {
        this.remoteChatList = remoteChatList;
    }
}