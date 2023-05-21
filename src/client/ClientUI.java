package client;

import client.ClientCanvas;
import server.ServerCanvas;
import shared.IRemoteSketches;
import shared.ShapeType;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static shared.Utils.initializeButtons;

public class ClientUI {
    private JFrame jFrame;
    private JPanel buttonPanel;
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

        jFrame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        jFrame.getContentPane().add(canvas, BorderLayout.CENTER);
        jFrame.setVisible(true);
    }

    public ClientCanvas getCanvas() {
        return canvas;
    }
}