package Server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerController {

    boolean serverStarted = false;

    @FXML
    private TextArea feedback;

    @FXML
    private Label ip;

    @FXML
    private Label hostname;

    @FXML
    private Label port;

    public void setFBText(String text){
        feedback.appendText(text + "\n");
    }

    @FXML
    private void startServer() throws UnknownHostException {
        if(!serverStarted){
            try {
                Registry myRegistry = LocateRegistry.createRegistry(1099);
                myRegistry.rebind("ServerImpl", new ServerImpl(this));
            } catch (Exception e) {
                e.printStackTrace();
                feedback.appendText("Something failed while setting up server\n");
            }
            InetAddress inetAddress = InetAddress.getLocalHost();
            hostname.setText(inetAddress.getHostName());
            ip.setText(inetAddress.getHostAddress());
            port.setText("1099");
            feedback.appendText("Server up and running!\n");
            serverStarted = true;
        } else feedback.appendText("Server already running\n");

    }

}
