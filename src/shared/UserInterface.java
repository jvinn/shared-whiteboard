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
        colorPanel.setPreferredSize(new Dimension(800, 30));
        colorPanel.setBorder(BorderFactory.createMatteBorder(1,2,1,2, Color.BLACK));

        Color[] colors = {
                new Color(255, 0, 0),
                new Color(255, 165, 0),
                new Color(255, 255, 0),
                new Color(0, 128, 0),
                new Color(0, 0, 255),
                new Color(75, 0, 130),
                new Color(143, 0, 255),
                new Color(255, 192, 203),
                new Color(220, 20, 60),
                new Color(255, 105, 180),
                new Color(219, 112, 147),
                new Color(176, 224, 230),
                new Color(64, 224, 208),
                new Color(72, 209, 204),
                new Color(0, 206, 209),
                new Color(135, 206, 250),
                new Color(30, 144, 255),
                new Color(0, 191, 255),
                new Color(127, 255, 212),
                new Color(102, 205, 170),
                new Color(60, 179, 113),
                new Color(46, 139, 87),
                new Color(154, 205, 50),
                new Color(85, 107, 47),
                new Color(107, 142, 35),
                new Color(189, 183, 107),
                new Color(238, 232, 170),
                new Color(240, 230, 140),
                new Color(255, 250, 205),
                new Color(255, 248, 220)
        };
//        Color[] colors = {
//                new Color(255, 0, 0),     // red
//                new Color(255, 127, 0),   // orange
//                new Color(255, 255, 0),   // yellow
//                new Color(127, 255, 0),   // chartreuse
//                new Color(0, 255, 0),     // green
//                new Color(0, 255, 127),   // spring green
//                new Color(0, 255, 255),   // cyan
//                new Color(0, 127, 255),   // azure
//                new Color(0, 0, 255),     // blue
//                new Color(127, 0, 255),   // violet
//                new Color(255, 0, 255),   // magenta
//                new Color(255, 0, 127),   // rose
//                new Color(0, 0, 0),       // black
//                new Color(127, 127, 127), // gray
//                new Color(85, 85, 85),    // dark gray
//                new Color(170, 170, 170)  // light gray
//        };

        for (Color color : colors) {
            JPanel colorSquare = new JPanel();
            colorSquare.setBackground(color);
            colorSquare.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // optional: add a border
            colorSquare.setPreferredSize(new Dimension(20, 20)); // adjust this value to control the size of the squares
            colorSquare.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    canvasPanel.setCurrentColor(color); // Update the current color in canvasPanel when a colorSquare is clicked
                }
            });
            colorPanel.add(colorSquare);
        }

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