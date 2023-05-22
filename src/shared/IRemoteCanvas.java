package shared;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IRemoteCanvas extends Remote {
    void updateCanvas(ArrayList<Shape> shapes, ArrayList<Point> freehandPoints, HashMap<String, Point> text) throws RemoteException;
}
