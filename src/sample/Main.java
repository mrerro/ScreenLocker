package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Check mazafaking database");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        //primaryStage.setAlwaysOnTop(true);
        //primaryStage.setMaximized(true);
        KeyHook.blockWindowsKey();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
