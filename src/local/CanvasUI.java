package local;

import shared.ShapeType;

import javax.swing.*;
import java.awt.*;

public class CanvasUI extends JFrame {
    private JButton freehandButton, lineButton, circleButton, ovalButton, rectangleButton;
    private JPanel buttonPanel;
    private CanvasPanel whiteboardPanel;

    public CanvasUI() {
        setTitle("Local Canvas");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        buttonPanel = new JPanel();
        whiteboardPanel = new CanvasPanel();

        freehandButton = new JButton("Freehand");
        lineButton = new JButton("Line");
        circleButton = new JButton("Circle");
        ovalButton = new JButton("Oval");
        rectangleButton = new JButton("Rectangle");

        freehandButton.addActionListener(e -> whiteboardPanel.setCurrentShapeType(ShapeType.FREEHAND));
        lineButton.addActionListener(e -> whiteboardPanel.setCurrentShapeType(ShapeType.LINE));
        circleButton.addActionListener(e -> whiteboardPanel.setCurrentShapeType(ShapeType.CIRCLE));
        ovalButton.addActionListener(e -> whiteboardPanel.setCurrentShapeType(ShapeType.OVAL));
        rectangleButton.addActionListener(e -> whiteboardPanel.setCurrentShapeType(ShapeType.RECTANGLE));

        buttonPanel.add(freehandButton);
        buttonPanel.add(lineButton);
        buttonPanel.add(circleButton);
        buttonPanel.add(ovalButton);
        buttonPanel.add(rectangleButton);

        buttonPanel.setBackground(Color.PINK);
        whiteboardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 15));

        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(whiteboardPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new CanvasUI();
    }
}