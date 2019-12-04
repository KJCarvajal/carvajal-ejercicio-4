package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("frmPrincipal.fxml"));

        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("MecaniK");
        Scene scene = new Scene(root);
        //scene.setFill(Color.RED);
        primaryStage.setScene(scene);
        primaryStage.show();
        //#0dbf3d <-- Color verde primario

    }

    public static void main(String[] args) {
        launch(args);
    }

}
