package globalStructures;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerInf extends Remote {

    void sendMessage(int idx, String u, String hash) throws RemoteException;
    String getMessage(int idx, String tag) throws RemoteException;
}
