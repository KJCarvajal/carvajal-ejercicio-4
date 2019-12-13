package sample.CONTROLLERS;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //region Variables de traidas de Scene Builder
    @FXML private VBox VBoxMenuLateralLeft;
    @FXML private ImageView btnImgMecanikMenuInicio;
    @FXML private Button btnMenuInicio;
    @FXML private BorderPane borderPanePrincipal;
    @FXML private Button btnEmpleados;
    @FXML private AnchorPane panelMenuInicial;

    //endregion

    //region Variable Locales
    private Stage stage = null; //Para abrir formularios

    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    public void onOcultarMenuClicked(MouseEvent event){

        if(btnMenuInicio.getText().equals("")){
            VBoxMenuLateralLeft.setPrefWidth(180);
            btnMenuInicio.setText("MecaniK");
            btnImgMecanikMenuInicio.setFitWidth(140);
            btnImgMecanikMenuInicio.setFitHeight(135);

        } else {
            VBoxMenuLateralLeft.setPrefWidth(45);
            btnMenuInicio.setText("");
            btnImgMecanikMenuInicio.setFitWidth(40);
            btnImgMecanikMenuInicio.setFitHeight(40);
        }
    }

    public void onButtonInicioMenuPrincipal(MouseEvent event){
        borderPanePrincipal.setCenter(panelMenuInicial);
        Scene scene = new Scene(borderPanePrincipal);
        stage.setScene(scene);
        stage.show();
    }

    public void onActividadesButtonClicked(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/frmActividades.fxml"));
        AnchorPane rootActividades = loader.load();

        borderPanePrincipal.setCenter(rootActividades);
        Scene scene = new Scene(borderPanePrincipal);
        stage.setScene(scene);
        stage.show();
    }

    public void onClientesButtonClicked(MouseEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/frmClientes.fxml"));
        AnchorPane rootClientes = loader.load();

        borderPanePrincipal.setCenter(rootClientes);
        Scene scene = new Scene(borderPanePrincipal);
        stage.setScene(scene);
        stage.show();
    }

    public void onVehiculosButtonClicked(MouseEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/frmVehiculos.fxml"));
        AnchorPane rootVehiculo = loader.load();

        borderPanePrincipal.setCenter(rootVehiculo);
        Scene scene = new Scene(borderPanePrincipal);
        stage.setScene(scene);
        stage.show();
    }

    public void onRepuestoButtonClicked(MouseEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/frmRepuestos.fxml"));
        AnchorPane rootRepuesto = loader.load();

        borderPanePrincipal.setCenter(rootRepuesto);
        Scene scene = new Scene(borderPanePrincipal);
        stage.setScene(scene);
        stage.show();
    }

    public void onEmpleadosButtonClicked(MouseEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/frmEmpleados.fxml"));
        AnchorPane rootEmpleados = loader.load();

        borderPanePrincipal.setCenter(rootEmpleados);
        Scene scene = new Scene(borderPanePrincipal);
        stage.setScene(scene);
        stage.show();
    }

    public void onAjustesCorreoButtonClicked(MouseEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/frmAjustesCorreo.fxml"));
        AnchorPane rootAjustes = loader.load();

        borderPanePrincipal.setCenter(rootAjustes); //Para que el nuevo formulario quede en el centro
        Scene scene = new Scene(borderPanePrincipal);
        stage.setScene(scene);
        stage.show();
    }

    public void onExitButtonClicked(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

}

