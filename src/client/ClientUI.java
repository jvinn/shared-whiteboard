package client;

import client.ClientCanvas;
import server.ServerCanvas;
import shared.IRemoteSketches;
import shared.ShapeType;

import javax.swing.*;
import java.awt.*;

import static shared.Utils.initializeButtons;

public class ClientUI extends JFrame {
    private JButton freehandButton, lineButton, circleButton, ovalButton, rectangleButton;
    private JPanel buttonPanel;
    private ClientCanvas canvas;

    public ClientUI(String title) {
        setTitle(title);
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        canvas = new ClientCanvas();
        buttonPanel = new JPanel();

        initializeButtons(canvas, buttonPanel, freehandButton, lineButton, circleButton, ovalButton, rectangleButton);
        canvas.setBorder(BorderFactory.createLineBorder(Color.GRAY, 15));
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);
    }

    public ClientCanvas getCanvas() {
        return canvas;
    }
}