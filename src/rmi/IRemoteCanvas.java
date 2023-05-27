package rmi;

import shared.ColoredElement;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IRemoteCanvas extends Remote {
    void updateCanvas(ArrayList<ColoredElement<Shape>> shapes, ArrayList<ColoredElement<Point>> freehand, HashMap<String, ColoredElement<Point>> text) throws RemoteException;
    void closeCanvas() throws RemoteException;
}
