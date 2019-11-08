package Server;

import globalStructures.ServerInf;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements ServerInf {
    public ServerImpl() throws RemoteException {


    }
}
