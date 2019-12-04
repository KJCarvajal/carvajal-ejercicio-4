package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {

    //region Variables de traidas de Scene Builder
    @FXML private Button btnIngresar;

    //endregion

    //region Variables Locales
    //Stage stage = null;

    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    public void onIngresarbuttonClicked(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent rootPrincipal = null;

        if(event.getSource() == btnIngresar){
            stage = (Stage) btnIngresar.getScene().getWindow();

            rootPrincipal = FXMLLoader.load(getClass().getResource("frmPrincipal.fxml"));
            Scene scenePrincipal = new Scene(rootPrincipal);
            stage.setScene(scenePrincipal);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }
    }
}
