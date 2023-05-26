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
        shapes.add(new ColoredElement<>(Utils.shapeFromPoints(shapeType, startPoint, endPoint), color));
    }

    public void addFreehand(Point point, Color color) {
        freehand.add(new ColoredElement<>(point, color));
    }

    public void addText(String string, Point point, Color color) {
        text.put(string, new ColoredElement<>(point, color));
    }
}

