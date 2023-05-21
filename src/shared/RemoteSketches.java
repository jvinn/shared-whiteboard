package shared;

import client.ClientCanvas;
import server.ServerCanvas;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Logger;

public class RemoteSketches extends UnicastRemoteObject implements IRemoteSketches {
    private static final Logger log = Logger.getLogger(RemoteSketches.class.getName());
    private final ArrayList<Shape> shapes = new ArrayList<>();
    private final ArrayList<Point> freehandPoints = new ArrayList<>();
    private final ArrayList<ClientCanvas> clientCanvases = new ArrayList<>();
    private final ServerCanvas serverCanvas;

    public RemoteSketches(ServerCanvas serverCanvas) throws RemoteException {
        super();
        this.serverCanvas = serverCanvas;
    }

    @Override
    public void addFreehand(ArrayList<Point> points) throws RemoteException {
        log.info("addFreehand");
        freehandPoints.add(null);
        freehandPoints.addAll(points);
        updateWhiteboards();
    }

    @Override
    public void addLine(Point p1, Point p2) throws RemoteException {
        log.info("addLine");
        shapes.add(new Line2D.Float(p1, p2));
        updateWhiteboards();
    }

    @Override
    public void addCircle(Point p1, Point p2) throws RemoteException {
        log.info("addCircle");
        int radius = (int) Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
        shapes.add(new Ellipse2D.Float(p1.x - radius, p1.y - radius, radius * 2, radius * 2));
        updateWhiteboards();
    }

    @Override
    public void addOval(Point p1, Point p2) throws RemoteException {
        log.info("addOval");
        shapes.add(new Ellipse2D.Float(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y));
        updateWhiteboards();
    }

    @Override
    public void addRectangle(Point p1, Point p2) throws RemoteException {
        log.info("addRectangle");
        shapes.add(new Rectangle2D.Float(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y)));
        updateWhiteboards();
    }

    @Override
    public void updateWhiteboards() {
        log.info("New shape added, updating all canvases");

        System.out.println("num clients to be updated: " + clientCanvases.size());
        System.out.println(clientCanvases);

        serverCanvas.updateSketches(shapes, freehandPoints);
        serverCanvas.repaint();

        for(ClientCanvas clientCanvas : clientCanvases) {
            clientCanvas.updateSketches(shapes, freehandPoints);
            clientCanvas.repaint();
        }
    }

    @Override
    public void addClientCanvas(ClientCanvas clientCanvas) {
        clientCanvases.add(clientCanvas);
    }
}
