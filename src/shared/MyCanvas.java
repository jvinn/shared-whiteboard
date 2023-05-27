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
    private Sketches sketches = new Sketches();
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
                        sketches.addFreehand(null, currentColor);
                        remoteSketches.addFreehand(sketches.getFreehandPoints());
                    }
                    else if(currentShapeType == ShapeType.TEXT) {
                        String text = JOptionPane.showInputDialog("Enter your text:");
                        sketches.addText(text, endPoint, currentColor);
                        remoteSketches.addText(text, endPoint, currentColor);
                    }
                    else {
                        sketches.addShape(currentShapeType, startPoint, endPoint, currentColor);
                        remoteSketches.addShape(currentShapeType, startPoint, endPoint, currentColor);
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
                    sketches.addFreehand(e.getPoint(), currentColor);
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

    public void updateSketches(ArrayList<ColoredElement<Shape>> shapes, ArrayList<ColoredElement<Point>> points, HashMap<String, ColoredElement<Point>> text) {
        this.sketches.setShapes(shapes);
        this.sketches.setFreehandPoints(points);
        this.sketches.setText(text);
    }

    @Override
    public void updateCanvas(ArrayList<ColoredElement<Shape>> shapes, ArrayList<ColoredElement<Point>> freehand, HashMap<String, ColoredElement<Point>> text) throws RemoteException {
        this.updateSketches(shapes, freehand, text);
        repaint();
    }

    @Override
    public void closeCanvas() {
        System.exit(0);
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    public Sketches getSketches() {
        return sketches;
    }

    public void setSketches(Sketches sketches) {
        this.sketches = sketches;
    }

    public IRemoteSketches getRemoteSketches() {
        return remoteSketches;
    }
}
