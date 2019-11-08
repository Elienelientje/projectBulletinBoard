package Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    private void startServer() {
        try {
            Registry myRegistry = LocateRegistry.createRegistry(1099);
            myRegistry.rebind("ServerImpl", new ServerImpl());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server up and running!");
    }
    public static void main(String[] args) {
        Main main = new Main();
        main.startServer();
    }
}
