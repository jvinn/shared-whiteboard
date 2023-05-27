package shared;

import rmi.IRemoteChatList;
import rmi.RemoteChatPanel;
import rmi.RemoteUserPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static void drawShapes(Graphics2D g2d, ArrayList<ColoredElement<Shape>> shapes) {
        for (ColoredElement<Shape> shape : shapes) {
            g2d.setColor(shape.color());
            g2d.draw(shape.element());
        }
    }

    public static void drawFreehand(Graphics2D g2d, ArrayList<ColoredElement<Point>> freehandPoints) {
        if (freehandPoints.size() < 2) {
            return;
        }

        for (int i = 0; i < freehandPoints.size() - 1; i++) {
            Point p1 = freehandPoints.get(i).element();
            Point p2 = freehandPoints.get(i + 1).element();

            if(p1 != null && p2 != null) {
                g2d.setColor(freehandPoints.get(i).color());
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    public static void drawText(Graphics2D g2d, HashMap<String, ColoredElement<Point>> texts) {
        for (Map.Entry<String, ColoredElement<Point>> text : texts.entrySet()) {
            g2d.setColor(text.getValue().color());
            g2d.drawString(text.getKey(), text.getValue().element().x, text.getValue().element().y);
        }
    }

    public static void initializeMenuBar(JMenuBar menuBar, MyCanvas canvasPanel, JFrame jFrame) {
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem closeItem = new JMenuItem("Close");

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(closeItem);

        newItem.addActionListener(e -> {
            canvasPanel.getSketches().getShapes().clear();
            canvasPanel.getSketches().getFreehandPoints().clear();
            canvasPanel.getSketches().getText().clear();
            canvasPanel.repaint();
            try {
                canvasPanel.getRemoteSketches().setSketches(new ArrayList<>(), new ArrayList<>(), new HashMap<>());
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
        openItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    FileInputStream fis = new FileInputStream(fileChooser.getSelectedFile());
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    Sketches sketches = (Sketches) ois.readObject();
                    canvasPanel.setSketches(sketches);
                    ois.close();
                    fis.close();
                    canvasPanel.repaint();
                    canvasPanel.getRemoteSketches().setSketches(sketches.getShapes(), sketches.getFreehandPoints(), sketches.getText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        saveItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile());
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(canvasPanel.getSketches());
                    oos.close();
                    fos.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        closeItem.addActionListener(e -> {
            jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
        });
        jFrame.setJMenuBar(menuBar);
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

        buttonPanel.setBackground(new Color(255, 105, 180));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(2,2,1,2, Color.BLACK));
    }

    public static void initializeColors(JPanel colorPanel, MyCanvas canvasPanel) {
        colorPanel.setPreferredSize(new Dimension(800, 30));
        colorPanel.setBorder(BorderFactory.createMatteBorder(1,2,1,2, Color.BLACK));

        Color[] colors = {
                new Color(255, 255, 255),
                new Color(127, 127, 127),
                new Color(0, 0, 0),
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
                new Color(238, 232, 170)
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
        userPanel.setBackground(new Color(255, 192, 203));
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
