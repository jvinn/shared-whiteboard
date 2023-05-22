package shared;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class MyCanvas extends JPanel {
    public ShapeType currentShapeType = ShapeType.FREEHAND;
    public Point startPoint, endPoint;
    public final Sketches sketches = new Sketches();
    public IRemoteSketches remoteSketches;

    public MyCanvas() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endPoint = e.getPoint();
                sketches.addFreehand(null);

                try {
                    remoteSketches.addShape(currentShapeType, startPoint, endPoint);

                    switch(currentShapeType) {
                        case FREEHAND -> remoteSketches.addFreehand(sketches.getFreehandPoints());
                        case LINE -> remoteSketches.addLine(startPoint, endPoint);
                        case CIRCLE -> remoteSketches.addCircle(startPoint, endPoint);
                        case RECTANGLE -> remoteSketches.addRectangle(startPoint, endPoint);
                        case OVAL -> remoteSketches.addOval(startPoint, endPoint);
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

                sketches.addShape(currentShapeType, startPoint, endPoint);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Utils.drawShapes(g2d, sketches.getShapes());
        Utils.drawFreehand(g2d, sketches.getFreehandPoints());
    }

    public void setCurrentShapeType(ShapeType currentShapeType) {
        this.currentShapeType = currentShapeType;
    }

    public void setRemoteSketches(IRemoteSketches remoteSketches) {
        this.remoteSketches = remoteSketches;
    }

    public void updateSketches(ArrayList<Shape> shapes, ArrayList<Point> freehandPoints) {
        this.sketches.setShapes(shapes);
        this.sketches.setFreehandPoints(freehandPoints);
    }
}
