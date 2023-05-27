package rmi;

import shared.ColoredElement;
import shared.MyCanvas;
import shared.ShapeType;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IRemoteSketches extends Remote {
    void addFreehand(ArrayList<ColoredElement<Point>> points) throws RemoteException;
    void addShape(ShapeType shapeType, Point p1, Point p2, Color color) throws RemoteException;
    void addText(String text, Point point, Color color) throws RemoteException;
    void addClientCanvas(IRemoteCanvas clientCanvas) throws RemoteException;
    void updateWhiteboards() throws RemoteException;
    void closeClients() throws RemoteException;
    void setServerCanvas(MyCanvas serverCanvas) throws RemoteException;
    void setSketches(ArrayList<ColoredElement<Shape>> shapes, ArrayList<ColoredElement<Point>> freehand, HashMap<String, ColoredElement<Point>> text) throws RemoteException;
}
