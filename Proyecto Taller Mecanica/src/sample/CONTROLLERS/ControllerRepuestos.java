package sample.CONTROLLERS;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import modelos.Cliente;
import modelos.Repuesto;
import modelos.Usuario;
import Conexion.ConexionMySQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.DataTruncation;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class ControllerRepuestos implements Initializable {

    //region Variables de traidas de Scene Builder
    //Para llenar el tableView
    @FXML
    private TableView<Repuesto> tableViewRepuesto;
    @FXML
    private TableColumn<Repuesto, String> colNombre;
    @FXML
    private TableColumn<Repuesto, String> colCosto;
    @FXML
    private TableColumn<Repuesto, String> colCantidad;

    //Para el registro de clientes
    @FXML
    private Label lblNuevoCliente;
    @FXML
    private VBox VBoxFormulario;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCosto;
    @FXML
    private TextField txtCantidad;
    @FXML
    private Label lblNotificacion;
    @FXML
    private TextField txtCampoBusqueda;
    //endregion

    //region Variable Locales
    private ObservableList<Repuesto> listaRepuestos;

    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        //tableViewRepuesto.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        txtNombre.addEventFilter(KeyEvent.ANY, filtrarSoloLetras);
//        txtCosto.addEventFilter(KeyEvent.ANY, filtrarSoloNumeros);
//        txtCantidad.addEventFilter(KeyEvent.ANY, filtrarSoloNumeros);
        actualizarTableViewRepuesto();
    }

    private void actualizarTableViewRepuesto(){
        listaRepuestos = FXCollections.observableArrayList();
        Repuesto.llenarTableView(listaRepuestos);
        tableViewRepuesto.setItems(listaRepuestos);
        columnasTableViewRepuesto();
    }

    public void onRegistarButtonClicked(MouseEvent event) {
        actualizarTableViewRepuesto();
        VBoxFormulario.setVisible(true);
        lblNuevoCliente.setText("Nuevo repuesto");
        lblNotificacion.setText("");
        vaciarCampos();
    }

    public void onCancelarRegistroClicked(MouseEvent event) {
        VBoxFormulario.setVisible(false);

    }

    public void onGuardarButtonClicked(MouseEvent event) {
        Repuesto repuesto = tableViewRepuesto.getSelectionModel().getSelectedItem();
        int usuarioEnLinea = Usuario.idUsuarioForaneo; //Traido desde el login controller

        PreparedStatement pst = null;
        try {
            if(repuesto != null && repuesto.getId_repuesto() > 0){
                if (verificarCamposVacios()) {
                    pst = ConexionMySQL.abrirConexion().prepareStatement(
                            "UPDATE repuesto SET nombre =?, costo =?, cantidad =? WHERE id_repuesto = " + repuesto.getId_repuesto()
                    );
                    pst.setString(1, txtNombre.getText().trim());
                    pst.setString(2, txtCosto.getText().trim());
                    pst.setString(3, txtCantidad.getText().trim());
                    pst.executeUpdate();

                    lblNotificacion.setText("Modificacion exitosa");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));
                    vaciarCampos();
                    actualizarTableViewRepuesto(); //Para actualizar los cambios
                    VBoxFormulario.setVisible(false);

                } else {
                    lblNotificacion.setText("Existen campos vacios");
                    lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
                    VBoxFormulario.setVisible(true);
                }

            } else {
                if (verificarCamposVacios()) {
                    pst = ConexionMySQL.abrirConexion().prepareStatement(
                            "INSERT INTO repuesto VALUES(?,?,?,?,?)"
                    );
                    pst.setString(1, "0");
                    pst.setString(2, txtNombre.getText().trim());
                    pst.setString(3, txtCosto.getText().trim());
                    pst.setString(4, txtCantidad.getText().trim());
                    pst.setInt(5, usuarioEnLinea);
                    pst.executeUpdate();

                    lblNotificacion.setText("Regitro exitoso");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));
                    vaciarCampos();
                    actualizarTableViewRepuesto(); //Para actualizar los cambios
                    VBoxFormulario.setVisible(false);

                } else {
                    lblNotificacion.setText("Existen campos vacios");
                    lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
                    VBoxFormulario.setVisible(true);
                }
            }

        } catch (Exception e) {
            lblNotificacion.setText("Error en los cambios");
            lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
            System.err.println(e.getMessage());
        }
    }

    public void onModificarButtonClicked(MouseEvent event) {
        Repuesto repuesto = tableViewRepuesto.getSelectionModel().getSelectedItem();
        lblNotificacion.setText("");

        if(repuesto != null && repuesto.getId_repuesto() > 0){
            VBoxFormulario.setVisible(true);
            lblNuevoCliente.setText("Editar repuesto");
            //-----------------------------------------

            txtNombre.setText(repuesto.getNombre());
            txtCosto.setText(repuesto.getCosto());
            txtCantidad.setText(repuesto.getCantidad());

        } else {
            lblNotificacion.setText("Seleccione una fila para editar");
        }
    }

    public void onEliminarButtonClicked(MouseEvent event) {
        Repuesto repuesto = tableViewRepuesto.getSelectionModel().getSelectedItem();
        lblNotificacion.setText("");

        if(repuesto != null && repuesto.getId_repuesto() > 0){
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Eliminar");
            alerta.setContentText("Esta seguro que desea eliminar esta fila");

            alerta.showAndWait();

            if(alerta.getResult().getText().equals("Aceptar")){
                if(Repuesto.eliminarRegistroSeleccionado(repuesto.getId_repuesto())){
                    lblNotificacion.setText("Repuesto eliminado");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));
                    actualizarTableViewRepuesto();
                } else {
                    lblNotificacion.setText("No se pudo eliminar el repuesto");
                    lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
                }
            }
        } else {
            lblNotificacion.setText("Seleccione una fila para eliminar");
        }
    }

    private boolean verificarCamposVacios() {
        if (!txtNombre.getText().isBlank() &&
                !txtCosto.getText().isBlank() &&
                !txtCantidad.getText().isBlank()) {
            return true;
        }
        return false;
    }

    private void vaciarCampos() {
        txtNombre.setText("");
        txtCosto.setText("");
        txtCantidad.setText("");
    }

    /**Busqueda parcial del cliente ingresado */
    public void onBuscarCampoUsuarioKeyTyped(KeyEvent event) {
        listaRepuestos = FXCollections.observableArrayList();
        Repuesto.datosDeRepuestoBuscado(listaRepuestos, txtCampoBusqueda.getText().trim());
        tableViewRepuesto.setItems(listaRepuestos);

        columnasTableViewRepuesto();
    }

    private void columnasTableViewRepuesto() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
    }

    private EventHandler<KeyEvent> filtrarSoloLetras = new EventHandler<KeyEvent>() {
        boolean negarContenido = false;

        @Override
        public void handle(KeyEvent event) {
            if(negarContenido){
                event.consume();
            }
            String temp = event.getCode().toString();
            if(!temp.matches("[a-zA-z\\s]") //La \s permite los espacios
                    && event.getCode() != KeyCode.SHIFT){
                if(event.getEventType() == KeyEvent.KEY_PRESSED){
                    negarContenido = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                    negarContenido = false;
                }
            }
        }
    };

    private EventHandler<KeyEvent> filtrarSoloNumeros = new EventHandler<KeyEvent>() {
        boolean negarContenido = false;
        static final int LONGITUD_MAX_ID = 13;

        @Override
        public void handle(KeyEvent event) {
            TextField txtCampo = (TextField) event.getSource();

            if(negarContenido){
                event.consume();
            }

            if(!event.getText().matches("[0-9]")
                    && event.getCode() != KeyCode.BACK_SPACE){
                if(event.getEventType() == KeyEvent.KEY_PRESSED){
                    negarContenido = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED){
                    negarContenido = false;
                }
            }
            if(txtCampo.getText().length() > LONGITUD_MAX_ID - 1
                    && event.getCode() != KeyCode.BACK_SPACE){
                if(event.getEventType() == KeyEvent.KEY_PRESSED){
                    negarContenido = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED){
                    negarContenido = false;
                }
            }
        }
    };

}
