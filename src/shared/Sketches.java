package shared;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Sketches implements Serializable {
    private ArrayList<ColoredElement<Shape>> shapes = new ArrayList<>();
    private ArrayList<ColoredElement<Point>> freehand = new ArrayList<>();
    private HashMap<String, ColoredElement<Point>> text = new HashMap<>();

    public Sketches() {
        super();
    }

    public ArrayList<ColoredElement<Shape>> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<ColoredElement<Shape>> shapes) {
        this.shapes = shapes;
    }

    public ArrayList<ColoredElement<Point>> getFreehandPoints() {
        return freehand;
    }

    public void setFreehandPoints(ArrayList<ColoredElement<Point>> freehandPoints) {
        this.freehand = freehandPoints;
    }

    public HashMap<String, ColoredElement<Point>> getText() {
        return text;
    }

    public void setText(HashMap<String, ColoredElement<Point>> text) {
        this.text = text;
    }

    public void addShape(ShapeType shapeType, Point startPoint, Point endPoint, Color color) {
        Shape shape = null;
        switch (shapeType) {
            case LINE -> shape = new Line2D.Float(startPoint, endPoint);
            case CIRCLE -> {
                int radius = (int) Math.sqrt(Math.pow(endPoint.x - startPoint.x, 2) + Math.pow(endPoint.y - startPoint.y, 2));
                shape = new Ellipse2D.Float(startPoint.x - radius, startPoint.y - radius, radius * 2, radius * 2);
            }
            case OVAL ->
                    shape = new Ellipse2D.Float(startPoint.x, startPoint.y, endPoint.x - startPoint.x, endPoint.y - startPoint.y);
            case RECTANGLE ->
                    shape = new Rectangle2D.Float(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y), Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
        }
        shapes.add(new ColoredElement<>(shape, color));
    }

    public void addFreehand(Point point, Color color) {
        freehand.add(new ColoredElement<>(point, color));
    }

    public void addText(String string, Point point, Color color) {
        text.put(string, new ColoredElement<>(point, color));
    }
}

