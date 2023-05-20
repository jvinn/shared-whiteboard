package local;

import shared.ShapeType;
import shared.Sketches;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

// JPanel extends Container -> repaint() updates components
// JPanel implements serializable -> can be sent using RMI
public class CanvasPanel extends JPanel {
    private ShapeType currentShapeType = ShapeType.FREEHAND;
    private Point startPoint, endPoint;
    private final Sketches sketches = new Sketches();

    public CanvasPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endPoint = e.getPoint();
                sketches.addFreehand(null);
//                freehandPoints.add(null);
                addShape();
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(currentShapeType == ShapeType.FREEHAND) {
                    sketches.addFreehand(e.getPoint());
                }
            }
        });
    }

    private void addShape() {
        System.out.println(currentShapeType);

        sketches.addShape(currentShapeType, startPoint, endPoint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        ArrayList<Shape> shapes = sketches.getShapes();
        ArrayList<Point> freehandPoints = sketches.getFreehandPoints();

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Shape shape : shapes) {
            g2d.draw(shape);
        }

        if (freehandPoints.size() < 2) {
            return;
        }
        for (int i = 0; i < freehandPoints.size() - 1; i++) {
            Point p1 = freehandPoints.get(i);
            Point p2 = freehandPoints.get(i + 1);

            if(p1 != null && p2 != null) {
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    public void setCurrentShapeType(ShapeType currentShapeType) {
        System.out.println("currentShapeType: " + currentShapeType);
        this.currentShapeType = currentShapeType;
    }
}
