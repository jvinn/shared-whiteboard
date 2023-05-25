package shared;

import rmi.IRemoteCanvas;
import rmi.IRemoteSketches;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class MyCanvas extends JPanel implements IRemoteCanvas {
    private ShapeType currentShapeType = ShapeType.FREEHAND;
    private Color currentColor = Color.BLACK;
    private Point startPoint, endPoint;
    private final Sketches sketches = new Sketches();
    private IRemoteSketches remoteSketches;

    public MyCanvas() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endPoint = e.getPoint();

                try {
                    if(currentShapeType == ShapeType.FREEHAND) {
                        sketches.addFreehand(null);
                        remoteSketches.addFreehand(sketches.getFreehandPoints());
                    }
                    else if(currentShapeType == ShapeType.TEXT) {
                        String text = JOptionPane.showInputDialog("Enter your text:");
                        sketches.addText(text, endPoint);
                        remoteSketches.addText(text, endPoint);
                    }
                    else {
                        sketches.addShape(currentShapeType, startPoint, endPoint);
                        remoteSketches.addShape(currentShapeType, startPoint, endPoint);
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

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
        Utils.drawText(g2d, sketches.getText());
    }

    public void setCurrentShapeType(ShapeType currentShapeType) {
        this.currentShapeType = currentShapeType;
    }

    public void setRemoteSketches(IRemoteSketches remoteSketches) {
        this.remoteSketches = remoteSketches;
    }

    public void updateSketches(ArrayList<Shape> shapes, ArrayList<Point> freehandPoints, HashMap<String, Point> text) {
        this.sketches.setShapes(shapes);
        this.sketches.setFreehandPoints(freehandPoints);
        this.sketches.setText(text);
    }

    @Override
    public void updateCanvas(ArrayList<Shape> shapes, ArrayList<Point> freehand, HashMap<String, Point> text) throws RemoteException {
        this.updateSketches(shapes, freehand, text);
        repaint();
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
        System.out.println("Current color: " + currentColor);
    }
}
