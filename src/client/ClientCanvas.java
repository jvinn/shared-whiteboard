package client;

import shared.IRemoteCanvas;
import shared.MyCanvas;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientCanvas extends MyCanvas implements IRemoteCanvas {
    public ClientCanvas() {
        super();
    }

    @Override
    public void updateCanvas(ArrayList<Shape> shapes, ArrayList<Point> freehandPoints) throws RemoteException {
        this.updateSketches(shapes, freehandPoints);
        repaint();
    }
}
