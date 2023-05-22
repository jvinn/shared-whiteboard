package shared;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static void drawShapes(Graphics2D g2d, ArrayList<Shape> shapes) {
        for (Shape shape : shapes) {
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
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    public static void drawText(Graphics2D g2d, HashMap<String, Point> texts) {
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

        buttonPanel.setBackground(Color.PINK);
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
