package shared;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IRemoteCanvas extends Remote {
    void updateCanvas(ArrayList<Shape> shapes, ArrayList<Point> freehandPoints) throws RemoteException;
}
