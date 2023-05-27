package rmi;

import shared.ColoredElement;
import shared.MyCanvas;
import shared.ShapeType;
import shared.Utils;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class RemoteSketches extends UnicastRemoteObject implements IRemoteSketches {
    private ArrayList<ColoredElement<Shape>> shapes = new ArrayList<>();
    private ArrayList<ColoredElement<Point>> freehand = new ArrayList<>();
    private HashMap<String, ColoredElement<Point>> text = new HashMap<>();
    private final ArrayList<IRemoteCanvas> clientCanvases = new ArrayList<>();
    private MyCanvas serverCanvas;

    public RemoteSketches() throws RemoteException {
        super();
    }

    @Override
    public void addFreehand(ArrayList<ColoredElement<Point>> points) throws RemoteException {
        freehand.addAll(points);
        updateWhiteboards();
    }

    @Override
    public void addShape(ShapeType shapeType, Point p1, Point p2, Color color) throws RemoteException {
        Shape shape = Utils.shapeFromPoints(shapeType, p1, p2);
        shapes.add(new ColoredElement<>(shape, color));
        updateWhiteboards();
    }

    @Override
    public void addText(String string, Point point, Color color) throws RemoteException {
        this.text.put(string, new ColoredElement<>(point, color));
        updateWhiteboards();
    }

    @Override
    public void updateWhiteboards() {
        serverCanvas.updateSketches(shapes, freehand, text);
        serverCanvas.repaint();

        try {
            for(IRemoteCanvas clientCanvas : clientCanvases) {
                clientCanvas.updateCanvas(shapes, freehand, text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeClients() {
        try {
            for(IRemoteCanvas clientCanvas : clientCanvases) {
                clientCanvas.closeCanvas();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addClientCanvas(IRemoteCanvas clientCanvas) {
        clientCanvases.add(clientCanvas);
        updateWhiteboards();
    }

    @Override
    public void setServerCanvas(MyCanvas serverCanvas) {
        this.serverCanvas = serverCanvas;
    }

    @Override
    public void setSketches(ArrayList<ColoredElement<Shape>> shapes, ArrayList<ColoredElement<Point>> freehand, HashMap<String, ColoredElement<Point>> text) {
        this.shapes = shapes;
        this.freehand = freehand;
        this.text = text;
        updateWhiteboards();
    }
}
