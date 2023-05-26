package shared;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Sketches implements Serializable {
    private ArrayList<Shape> shapes = new ArrayList<>(); // TODO: Update these to contain a color
    private ArrayList<Point> freehand = new ArrayList<>();
    private HashMap<String, Point> text = new HashMap<>();

    public Sketches() {
        super();
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public ArrayList<Point> getFreehandPoints() {
        return freehand;
    }

    public void setFreehandPoints(ArrayList<Point> freehandPoints) {
        this.freehand = freehandPoints;
    }

    public HashMap<String, Point> getText() {
        return text;
    }

    public void setText(HashMap<String, Point> text) {
        this.text = text;
    }

    public void addShape(ShapeType shapeType, Point startPoint, Point endPoint) {
        switch (shapeType) {
            case LINE -> shapes.add(new Line2D.Float(startPoint, endPoint));
            case CIRCLE -> {
                int radius = (int) Math.sqrt(Math.pow(endPoint.x - startPoint.x, 2) + Math.pow(endPoint.y - startPoint.y, 2));
                shapes.add(new Ellipse2D.Float(startPoint.x - radius, startPoint.y - radius, radius * 2, radius * 2));
            }
            case OVAL ->
                    shapes.add(new Ellipse2D.Float(startPoint.x, startPoint.y, endPoint.x - startPoint.x, endPoint.y - startPoint.y));
            case RECTANGLE ->
                    shapes.add(new Rectangle2D.Float(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y), Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y)));
        }
    }

    public void addFreehand(Point point) {
        freehand.add(point);
    }

    public void addText(String string, Point point) {
        text.put(string, point);
    }
}

