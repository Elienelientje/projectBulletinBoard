package Client;

import Server.ServerController;
import globalStructures.ServerInf;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.lang.ref.Cleaner;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main extends Application {

    ClientController cc;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ClientLayout.fxml"));
        cc = getController();
        primaryStage.setTitle("Client service");
        primaryStage.getIcons().add(new Image("https://img.icons8.com/pastel-glyph/2x/person-male.png"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    private ClientController getController(){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Server.Main.class.getResource("ClientLayout.fxml"));
        return loader.getController();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void start() {
        try {
            Registry ServerRegistry = LocateRegistry.getRegistry("192.168.0.115", 1099);
            ServerInf sinf = (ServerInf) ServerRegistry.lookup("ServerImpl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
