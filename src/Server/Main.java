package Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main extends Application {

    ServerController sc;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ServerLayout.fxml"));
        sc = getController();
        primaryStage.setTitle("Bulletin Server");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    private ServerController getController(){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("ServerLayout.fxml"));
        return loader.getController();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
