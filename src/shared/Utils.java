package shared;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

    public static void initializeButtons(MyCanvas canvas, JPanel buttonPanel) {
        JButton freehandButton = new JButton("Freehand");
        JButton lineButton = new JButton("Line");
        JButton circleButton = new JButton("Circle");
        JButton ovalButton = new JButton("Oval");
        JButton rectangleButton = new JButton("Rectangle");

        freehandButton.addActionListener(e -> canvas.setCurrentShapeType(ShapeType.FREEHAND));
        lineButton.addActionListener(e -> canvas.setCurrentShapeType(ShapeType.LINE));
        circleButton.addActionListener(e -> canvas.setCurrentShapeType(ShapeType.CIRCLE));
        ovalButton.addActionListener(e -> canvas.setCurrentShapeType(ShapeType.OVAL));
        rectangleButton.addActionListener(e -> canvas.setCurrentShapeType(ShapeType.RECTANGLE));

        buttonPanel.add(freehandButton);
        buttonPanel.add(lineButton);
        buttonPanel.add(circleButton);
        buttonPanel.add(ovalButton);
        buttonPanel.add(rectangleButton);

        buttonPanel.setBackground(Color.PINK);
    }
}
