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
    private final ArrayList<ColoredElement<Shape>> shapes = new ArrayList<>();
    private final ArrayList<ColoredElement<Point>> freehandPoints = new ArrayList<>();
    private final HashMap<String, ColoredElement<Point>> text = new HashMap<>();
    private final ArrayList<IRemoteCanvas> clientCanvases = new ArrayList<>();
    private MyCanvas serverCanvas;

    public RemoteSketches() throws RemoteException {
        super();
    }

    @Override
    public void addFreehand(ArrayList<ColoredElement<Point>> points) throws RemoteException {
        freehandPoints.addAll(points);
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
        serverCanvas.updateSketches(shapes, freehandPoints, text);
        serverCanvas.repaint();

        try {
            for(IRemoteCanvas clientCanvas : clientCanvases) {
                clientCanvas.updateCanvas(shapes, freehandPoints, text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addClientCanvas(IRemoteCanvas clientCanvas) {
        clientCanvases.add(clientCanvas);
    }

    @Override
    public void setServerCanvas(MyCanvas serverCanvas) {
        this.serverCanvas = serverCanvas;
    }
}
