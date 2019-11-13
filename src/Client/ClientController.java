package Client;

import globalStructures.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.util.Random;

import static java.time.zone.ZoneRulesProvider.refresh;

public class ClientController {

    boolean hasConnection = false;
    Registry ServerRegistry;
    ServerInf sinf;

    int rx_idx = 5;
    String rx_tag = "shhsd";
    String rx_secretKey = "test";

    int tx_idx = 5;
    String tx_tag = "shhsd";
    String tx_secretKey = "test";

    int range_idx = 50;
    int length_tag = 50;

    @FXML
    private TextArea feedback;

    @FXML
    private TextFlow chatText;

    @FXML
    private TextField serverIP;

    @FXML
    private TextField userInput;

    @FXML
    private Button openConnection;

    @FXML
    private void openConnection() throws UnknownHostException {
        if(!hasConnection){
            String ip = serverIP.getText();
            if(ip.equals("")){
                feedback.appendText("You need to fill in a hostname or an IP adrees of the server");
                //TIJDELIJK:
                InetAddress inetAddress = InetAddress.getLocalHost();
                ip =  inetAddress.getHostAddress();
                //return;
            }
            try {
                ServerRegistry = LocateRegistry.getRegistry(ip, 1099);
                sinf = (ServerInf) ServerRegistry.lookup("ServerImpl");
                hasConnection = true;
                feedback.appendText("Connected to server!\n");
                startListening();
                feedback.appendText("Listening for messages on server!\n");
            } catch (java.rmi.ConnectException e) {
                e.printStackTrace();
                feedback.appendText("Host refused connection!\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else feedback.appendText("Client is already connected\n");
    }

    private void startListening(){
        Thread t = new Thread(new Runnable() {
            public void run(){
                try {
                    while(true){
                        String encryptedMessage = null;

                        while(encryptedMessage == null){
                            encryptedMessage = sinf.getMessage(rx_idx, rx_tag);
                        }

                        String message = AES.decrypt(encryptedMessage, rx_secretKey) ;
                        SerializedObject object = (SerializedObject) Serializer.fromString(message);

                        rx_idx = object.getIdx();
                        rx_tag = object.getTag();
                        appendChatText(object.getMessage(), false);
                        rx_secretKey = Hasher.hash(rx_secretKey);
                    }

                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }});
        t.start();

    }



    private void appendChatText(String message, boolean me){
        Text t1 = new Text();
        if(me){
            t1.setStyle("-fx-fill: #4F8A10;-fx-font-weight:normal;");
            t1.setText("<le moi> " + message + "\n");

        } else {
            t1.setStyle("-fx-fill: #692387;-fx-font-weight:normal;");
            t1.setText("<le vous> " + message + "\n");
        }
        Platform.runLater(() -> {
            chatText.getChildren().add(t1);
            refresh();
        });

    }

    @FXML
    public void onEnter() throws IOException {
        sendMessage();
    }

    @FXML
    private void sendMessage() throws IOException {
        String message = userInput.getText();
        appendChatText(message, true);
        userInput.clear();

        Random rand = new Random();

        int new_tx_idx = rand.nextInt(range_idx);
        byte[] array = new byte[length_tag]; // length is bounded by 7
        new Random().nextBytes(array);
        String new_tx_tag = new String(array, Charset.forName("UTF-8"));

        SerializedObject u = new SerializedObject(message, new_tx_tag, new_tx_idx);
        String serializedU = Serializer.toString(u);

        String encryptedU = AES.encrypt(serializedU, tx_secretKey);

        String oldTagHashed = Hasher.hash(tx_tag);
        sinf.sendMessage(this.tx_idx, encryptedU, oldTagHashed);
        this.tx_idx = new_tx_idx;
        this.tx_tag = new_tx_tag;

        tx_secretKey = Hasher.hash(tx_secretKey);


    }

}
