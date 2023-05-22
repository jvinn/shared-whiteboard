package client;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static shared.Utils.initializeButtons;

public class ClientUI {
    private JFrame jFrame;
    private JPanel buttonPanel;
    private JPanel userPanel;
    private ClientCanvas canvas;

    public ClientUI(String title) {
        jFrame = new JFrame();
        jFrame.setTitle(title);
        jFrame.setSize(800, 600);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        canvas = new ClientCanvas();
        canvas.setBorder(BorderFactory.createLineBorder(Color.GRAY, 15));

        buttonPanel = new JPanel();
        initializeButtons(canvas, buttonPanel);

        userPanel = new JPanel();
        userPanel.setBackground(Color.CYAN);
        userPanel.setSize(100, 100);

        jFrame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        jFrame.getContentPane().add(canvas, BorderLayout.CENTER);
        jFrame.getContentPane().add(userPanel, BorderLayout.EAST);
        jFrame.setVisible(true);
    }

    public ClientCanvas getCanvas() {
        return canvas;
    }
}