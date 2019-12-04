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
        Parent rootLoginUsuario = FXMLLoader.load(getClass().getResource("frmLoginUsuario.fxml"));

        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("MecaniK");
        Scene sceneLoginUsuario = new Scene(rootLoginUsuario);
        //scene.setFill(Color.RED);
        primaryStage.setScene(sceneLoginUsuario);
        primaryStage.show();
        //#0dbf3d <-- Color verde primario
    }

    public static void main(String[] args) {
        launch(args);
    }

}
