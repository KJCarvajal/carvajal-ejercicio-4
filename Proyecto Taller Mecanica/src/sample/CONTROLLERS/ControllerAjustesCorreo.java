package sample.CONTROLLERS;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ControllerAjustesCorreo {
    //region Variables de traidas de Scene Builder
    @FXML CheckBox checkBoxPermisoDeInforme;
    @FXML TextField txtCorreoEmpresa;
    @FXML TextField txtContraseniaCorreoEmpresa;
    @FXML TextField txtConfirmarContrasenia;
    @FXML TextField txtCorreoPersonal;
    @FXML VBox VBoxCamposCorreos;
    //endregion

    public void onGuardarCambiosDeEmpresa(MouseEvent event) {

    }

    public void onCheckBoxPermisoClicked(MouseEvent event) {
        if(checkBoxPermisoDeInforme.isSelected()){
            VBoxCamposCorreos.setDisable(false);
        } else {
            VBoxCamposCorreos.setDisable(true);
        }
    }
}
