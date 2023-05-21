package server;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static shared.Utils.initializeButtons;

public class ServerUI {
    private JFrame jFrame;
    private JPanel buttonPanel;
    private ServerCanvas canvas;

    public ServerUI(String title) {
        jFrame = new JFrame();
        jFrame.setTitle(title);
        jFrame.setSize(800, 600);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        canvas = new ServerCanvas();
        canvas.setBorder(BorderFactory.createLineBorder(Color.GRAY, 15));

        buttonPanel = new JPanel();
        initializeButtons(canvas, buttonPanel);

        jFrame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        jFrame.getContentPane().add(canvas, BorderLayout.CENTER);
        jFrame.setVisible(true);
    }

    public ServerCanvas getCanvas() {
        return canvas;
    }
}