package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //region Variables de traidas de Scene Builder
    @FXML private VBox VBoxMenuLateralLeft;
    @FXML private ImageView btnImgMecanikMenuInicio;
    @FXML private Button btnMenuInicio;

    //endregion

    //region Variable Locales
    private boolean menuOcultado = false;

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

    public void onExitButtonClicked(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

}

