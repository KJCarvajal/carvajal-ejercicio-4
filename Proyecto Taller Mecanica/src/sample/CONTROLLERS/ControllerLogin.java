package sample.CONTROLLERS;


import Conexion.ConexionMySQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.Usuario;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {

    //region Variables de traidas de Scene Builder

    @FXML private Button btnIngresar;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasenia;

    //endregion

    //region Variables Locales
    //Stage stage = null;

    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    public void onIngresarbuttonClicked(ActionEvent event) {

        Stage stage = null;
        Parent rootPrincipal = null;

        try {
            if(event.getSource() == btnIngresar){
                stage = (Stage) btnIngresar.getScene().getWindow();
                if (txtUsuario.getText().isEmpty()){

                    showAlert(Alert.AlertType.ERROR, "Error!",

                            "Ingrese su nombre de usuario");
                    return;
                }

                if (txtContrasenia.getText().isEmpty()){

                    showAlert(Alert.AlertType.ERROR,"Error!",

                            "Ingrese su contraseña");
                    return;
                }

                String usuario = txtUsuario.getText();
                String contrasenia = txtContrasenia.getText();

                boolean mensaje = comprobarLogin(usuario, contrasenia);

                if (mensaje){
                    //infoBox("Inicio de sesion exitosa", "Info", "Success");
                    rootPrincipal = FXMLLoader.load(getClass().getResource("../FXML/frmPrincipal.fxml"));
                    Scene scenePrincipal = new Scene(rootPrincipal);
                    stage.setScene(scenePrincipal);
                    stage.setMaximized(true);
                    stage.show();

                } else {
                    showAlert(Alert.AlertType.ERROR,"Usuario no encontrado", "Usuario o contraseña incorrecta");
                }
            }

        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private boolean comprobarLogin(String usuario, String contrasenia) {
        try {
            PreparedStatement sentenciaParaLogin = ConexionMySQL.abrirConexion().prepareStatement(
                    "SELECT * FROM usuario WHERE nombre_usuario = ? and contrasenia = ?"
            );
            sentenciaParaLogin.setString(1, usuario);
            sentenciaParaLogin.setString(2, contrasenia);

            ResultSet resultSet = sentenciaParaLogin.executeQuery();
            if (resultSet.next()){
                Usuario.idUsuarioForaneo = resultSet.getInt("id_usuario");
                return true;
            }

        }catch (SQLException e){
            System.err.println(e.getMessage());

        }
        return false;

    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
