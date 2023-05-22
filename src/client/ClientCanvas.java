package client;

import shared.IRemoteCanvas;
import shared.MyCanvas;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientCanvas extends MyCanvas implements IRemoteCanvas {
    public ClientCanvas() {
        super();
    }

    @Override
    public void updateCanvas(ArrayList<Shape> shapes, ArrayList<Point> freehand, HashMap<String, Point> text) throws RemoteException {
        this.updateSketches(shapes, freehand, text);
        repaint();
    }
}
