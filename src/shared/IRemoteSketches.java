package shared;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IRemoteSketches extends Remote {
    void addFreehand(ArrayList<Point> points) throws RemoteException;
    void addShape(ShapeType shapeType, Point p1, Point p2) throws RemoteException;
    void addText(String text, Point point) throws RemoteException;
    void addClientCanvas(IRemoteCanvas clientCanvas) throws RemoteException;
    void updateWhiteboards() throws RemoteException;
}
