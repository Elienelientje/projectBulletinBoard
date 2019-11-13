package Server;

import globalStructures.Hasher;
import globalStructures.ServerInf;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;

public class ServerImpl extends UnicastRemoteObject implements ServerInf {

    ServerController sc;
    int n = 20;
    HashMap<Integer, Cell> bulletinBoard = new HashMap<Integer, Cell>(n);


    public ServerImpl(ServerController sc) throws RemoteException {
        this.sc = sc;
        for(int i = 0; i < n; i++){
            bulletinBoard.put(i, new Cell());
        }
    }

    @Override
    public void sendMessage(int idx, String u, String hash) throws RemoteException {
        int real_idx = idx % this.n;
        sc.setFBText("Someone sending a message");
        sc.setFBText("Message put in " + real_idx);
        Message m = new Message(u, hash);
        bulletinBoard.get(real_idx).add(m);
    }

    @Override
    public String getMessage(int idx, String tag) throws RemoteException {

        idx %= n;
        HashSet<Message> messages =  bulletinBoard.get(idx).getMessages();
        if(messages.isEmpty())return null;

        sc.setFBText("getMessage(" + idx + ", tag)");

        Message temp = null;
        boolean founded = false;


        for(Message m: messages){
            if(m.getTag().equals(Hasher.hash(tag))){
                founded = true;
                temp = m;
            }
        }

        if(founded){
            messages.remove(temp);
            bulletinBoard.get(idx).setMessages(messages);
            return temp.getValue();
        }

        return null;
    }
}
