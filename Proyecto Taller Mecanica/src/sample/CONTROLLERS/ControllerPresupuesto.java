package sample.CONTROLLERS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelos.Actividad;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPresupuesto {
    //region Variables de traidas de Scene Builder
    @FXML
    private TextField txtCodigoActividad;
    @FXML
    private TextField txtIdentidad;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtDirecion;
    @FXML
    private TextField txtTotal;

    //Para llenar el tableView
    @FXML
    private TableView<Actividad> tableViewActividad;
    @FXML
    private TableColumn<Actividad, String> colPlaca;
    @FXML
    private TableColumn<Actividad, String> colActividad;
    @FXML
    private TableColumn<Actividad, String> colDescripcion;
    @FXML
    private TableColumn<Actividad, String> colValor;

    //endregion

    //region Variable Locales
    private ObservableList<Actividad> listaActividades;

    //endregion

    //@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void actualizarTableViewActividad(){
        listaActividades = FXCollections.observableArrayList();
        Actividad.llenarTableView(listaActividades);
        tableViewActividad.setItems(listaActividades);
        columnasTableViewActividad();
    }

    private void columnasTableViewActividad() {
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colActividad.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

    }

    public void onGenerarButtonClicked(javafx.scene.input.MouseEvent event) {
        if (Actividad.buscarActividad(txtCodigoActividad.getText().trim())){
            actualizarTableViewActividad();
        }
    }
}
