package Client;

import globalStructures.ServerInf;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    private void start() {
        try {
            Registry ServerRegistry = LocateRegistry.getRegistry("192.168.0.115", 1099);
            ServerInf sinf = (ServerInf) ServerRegistry.lookup("ServerImpl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

}
