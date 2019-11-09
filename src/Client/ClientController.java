package Client;

import Server.ServerImpl;
import globalStructures.ServerInf;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientController {

    boolean hasConnection = false;

    @FXML
    private TextArea feedback;

    @FXML
    private TextField serverIP;

    @FXML
    private Button openConnection;

    @FXML
    private void openConnection(){
        if(!hasConnection){
            if(serverIP.getText().equals("")){
                feedback.appendText("You need to fill in a hostname or an IP adrees of the server");
                return;
            }
            String ip = serverIP.getText();
            System.out.println("ip;" + ip);
            try {
                Registry ServerRegistry = LocateRegistry.getRegistry(ip, 1099);
                ServerInf sinf = (ServerInf) ServerRegistry.lookup("ServerImpl");
                hasConnection = true;
                feedback.appendText("Connected to server!\n");
            } catch (java.rmi.ConnectException e) {
                e.printStackTrace();
                feedback.appendText("Host refused connection!\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else feedback.appendText("Client is already connected\n");
    }

    @FXML
    private void printInfo() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("IP Address:- " + inetAddress.getHostAddress());
        System.out.println("Host Name:- " + inetAddress.getHostName());
    }
}
