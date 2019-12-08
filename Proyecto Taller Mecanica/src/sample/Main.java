package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent rootLoginUsuario = FXMLLoader.load(getClass().getResource("FXML/frmLoginUsuario.fxml"));

        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("MecaniK");
        Scene sceneLoginUsuario = new Scene(rootLoginUsuario);
        //scene.setFill(Color.RED);
        primaryStage.setScene(sceneLoginUsuario);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
