package sample.CONTROLLERS;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import modelos.Correo;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ControllerAjustesCorreo {
    //region Variables de traidas de Scene Builder
    @FXML CheckBox checkBoxPermisoDeInforme;
    @FXML TextField txtCorreoEmpresa;
    @FXML TextField txtContraseniaCorreoEmpresa;
    @FXML TextField txtConfirmarContrasenia;
    @FXML TextField txtCorreoPersonal;
    @FXML VBox VBoxCamposCorreos;
    @FXML Button btnGuardarCambios;


    String asunto= "Su correo se ha actualizado";
    String  cuerpo= "Se ha realizado un cambio de correo del taller de Mecanik";
    String remitente= "mecanik2019@gmail.com";
    String clave = "KevinBrian2019";


    //endregion

    public void onGuardarCambiosDeEmpresa(MouseEvent event) {

if (txtContraseniaCorreoEmpresa.getText().isEmpty()|| txtContraseniaCorreoEmpresa.getText().isEmpty() || txtConfirmarContrasenia.getText().isEmpty()|| txtCorreoPersonal.getText().isEmpty()){
        showAlert(Alert.AlertType.ERROR, "Error!", "Debe llenar los campos!");
}
else {
    if (txtConfirmarContrasenia.getText().trim().equals(txtContraseniaCorreoEmpresa.getText().trim())) {

        String destino = txtCorreoEmpresa.getText().trim();
        String correoPersonal = txtCorreoPersonal.getText().trim();
        //Correo.enviarCorreoTexto(remitente, "Cambio de correo", "se ha detectado un cambio de correo de el taller Mecanik", remitente, clave);
       // Correo.enviarCorreoTexto(destino, "Nuevo correo registrado", "Este correo se ha registrado para ser el nuevo correo de el taller Mecanik", remitente, clave);
       Correo.enviarCorreoConArchivoAdjunto(correoPersonal,"prueba 1 tiempo dinamico",remitente,clave);
        Correo.enviarCorreoConArchivoAdjunto("elnegrojose3@gmail.com","prueba 12",remitente,clave);

    }
    else {
        showAlert(Alert.AlertType.ERROR, "Error!", "Las contraseñas no son similares");

    }
    txtCorreoPersonal.setText("");
    txtConfirmarContrasenia.setText("");
    txtContraseniaCorreoEmpresa.setText("");
    txtCorreoEmpresa.setText("");
}

    }

    public void onCheckBoxPermisoClicked(MouseEvent event) {
        if(checkBoxPermisoDeInforme.isSelected()){
            VBoxCamposCorreos.setDisable(false);
        } else {
            VBoxCamposCorreos.setDisable(true);
        }
    }

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
