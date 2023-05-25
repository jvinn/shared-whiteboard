package rmi;

import shared.MyCanvas;
import shared.ShapeType;
import shared.Utils;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class RemoteSketches extends UnicastRemoteObject implements IRemoteSketches {
    private static final Logger log = Logger.getLogger(RemoteSketches.class.getName());
    private final ArrayList<Shape> shapes = new ArrayList<>();
    private final ArrayList<Point> freehandPoints = new ArrayList<>();
    private final HashMap<String, Point> text = new HashMap<>();
    private final ArrayList<IRemoteCanvas> clientCanvases = new ArrayList<>();
    private MyCanvas serverCanvas;

    public RemoteSketches() throws RemoteException {
        super();
    }

    @Override
    public void addFreehand(ArrayList<Point> points) throws RemoteException {
        freehandPoints.add(null);
        freehandPoints.addAll(points);
        updateWhiteboards();
    }

    @Override
    public void addShape(ShapeType shapeType, Point p1, Point p2) throws RemoteException {
        shapes.add(Utils.shapeFromPoints(shapeType, p1, p2));
        updateWhiteboards();
    }

    @Override
    public void addText(String text, Point point) throws RemoteException {
        this.text.put(text, point);
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
