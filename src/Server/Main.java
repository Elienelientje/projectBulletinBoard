package Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main extends Application {

    ServerController sc;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ServerLayout.fxml"));
        sc = getController();
        primaryStage.setTitle("Bulletin Server");
        primaryStage.getIcons().add(new Image("https://www.pinclipart.com/picdir/middle/33-330180_clip-art-black-and-white-library-application-server.png"));
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
