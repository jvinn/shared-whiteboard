package shared;

import rmi.IRemoteChatList;
import rmi.RemoteChatPanel;
import rmi.RemoteUserPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static void drawShapes(Graphics2D g2d, ArrayList<Shape> shapes) {
        for (Shape shape : shapes) {
            // TODO: Set color before drawing
            g2d.draw(shape);
        }
    }

    public static void drawFreehand(Graphics2D g2d, ArrayList<Point> freehandPoints) {
        if (freehandPoints.size() < 2) {
            return;
        }

        for (int i = 0; i < freehandPoints.size() - 1; i++) {
            Point p1 = freehandPoints.get(i);
            Point p2 = freehandPoints.get(i + 1);

            if(p1 != null && p2 != null) {
                // TODO: Set color before drawing
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    public static void drawText(Graphics2D g2d, HashMap<String, Point> texts) {
        // TODO: Set color before drawing
        for (Map.Entry<String, Point> text : texts.entrySet()) {
            g2d.drawString(text.getKey(), text.getValue().x, text.getValue().y);
        }
    }

    public static void initializeButtons(MyCanvas canvas, JPanel buttonPanel) {
        JButton freehandButton = new JButton("Freehand");
        JButton lineButton = new JButton("Line");
        JButton circleButton = new JButton("Circle");
        JButton ovalButton = new JButton("Oval");
        JButton rectangleButton = new JButton("Rectangle");
        JButton textButton = new JButton("Text");

        freehandButton.addActionListener(e -> canvas.setCurrentShapeType(ShapeType.FREEHAND));
        lineButton.addActionListener(e -> canvas.setCurrentShapeType(ShapeType.LINE));
        circleButton.addActionListener(e -> canvas.setCurrentShapeType(ShapeType.CIRCLE));
        ovalButton.addActionListener(e -> canvas.setCurrentShapeType(ShapeType.OVAL));
        rectangleButton.addActionListener(e -> canvas.setCurrentShapeType(ShapeType.RECTANGLE));
        textButton.addActionListener(e -> canvas.setCurrentShapeType(ShapeType.TEXT));

        buttonPanel.add(freehandButton);
        buttonPanel.add(lineButton);
        buttonPanel.add(circleButton);
        buttonPanel.add(ovalButton);
        buttonPanel.add(rectangleButton);
        buttonPanel.add(textButton);

        buttonPanel.setBackground(new Color(121, 252, 210));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(2,2,1,2, Color.BLACK));
    }

    public static void initializeColors(JPanel colorPanel, MyCanvas canvasPanel) {
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

        for (Color color : colors) {
            JPanel colorSquare = new JPanel();
            colorSquare.setBackground(color);
            colorSquare.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            colorSquare.setPreferredSize(new Dimension(20, 20));
            colorSquare.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    canvasPanel.setCurrentColor(color);
                }
            });
            colorPanel.add(colorSquare);
        }
    }

    public static void initializeUserList(RemoteUserPanel userPanel, JList<String> userList) {
        userPanel.setBackground(new Color(201, 255, 238));
        userPanel.setPreferredSize(new Dimension(100, 100));
        userPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,2, Color.BLACK));

        JLabel userLabel = new JLabel("Users");

        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.add(userLabel);
        userPanel.add(userList);
    }

    public static void initializeChat(IRemoteChatList remoteChatList, RemoteChatPanel chatPanel, String username) {
        chatPanel.setBackground(new Color(207, 253, 255));
        chatPanel.setPreferredSize(new Dimension(800, 120));
        chatPanel.setBorder(BorderFactory.createMatteBorder(1,2,2,2, Color.BLACK));

        JLabel chatLabel = new JLabel("Chat");
        JTextField textField = new JTextField(20);
        textField.setMaximumSize(new Dimension(500, 20));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        textField.addActionListener(e -> {
            try {
                String text = textField.getText();
                remoteChatList.addMessage(new ChatMessage(username, text));
                textField.setText("");
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(Color.WHITE);
        chatLabel.setBackground(Color.WHITE);
        textField.setBackground(Color.WHITE);

        centerPanel.add(chatLabel);
        centerPanel.add(textField);

        chatPanel.add(centerPanel);
    }

    public static Shape shapeFromPoints(ShapeType shapeType, Point p1, Point p2) {
        Shape shape;

        switch(shapeType) {
            case LINE -> {
                shape = new Line2D.Float(p1, p2);
            }
            case CIRCLE -> {
                int radius = (int) Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
                shape = new Ellipse2D.Float(p1.x - radius, p1.y - radius, radius * 2, radius * 2);
            }
            case OVAL -> {
                shape = new Ellipse2D.Float(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);
            }
            case RECTANGLE -> {
                shape = new Rectangle2D.Float(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y));
            }
            default -> shape = null;
        }
        return shape;
    }
}
