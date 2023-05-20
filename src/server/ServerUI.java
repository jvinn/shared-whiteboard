package server;

import javax.swing.*;
import java.awt.*;

import static shared.Utils.initializeButtons;

public class ServerUI extends JFrame {
    private JButton freehandButton, lineButton, circleButton, ovalButton, rectangleButton;
    private JPanel buttonPanel;
    private ServerCanvas canvas;

    public ServerUI(String title) {
        setTitle(title);
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        canvas = new ServerCanvas();
        buttonPanel = new JPanel();

        initializeButtons(canvas, buttonPanel, freehandButton, lineButton, circleButton, ovalButton, rectangleButton);
        canvas.setBorder(BorderFactory.createLineBorder(Color.GRAY, 15));
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);
    }

    public ServerCanvas getCanvas() {
        return canvas;
    }
}