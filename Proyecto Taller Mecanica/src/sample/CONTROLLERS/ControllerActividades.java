package sample.CONTROLLERS;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import modelos.Actividad;
import modelos.Cliente;
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
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class ControllerActividades implements Initializable {

    //region Variables de traidas de Scene Builder
    //Para llenar el tableView
    @FXML
    private TableView<Actividad> tableViewActividades;
    @FXML
    private TableColumn<Actividad, String> colPlaca;
    @FXML
    private TableColumn<Actividad, String> colNombre;
    @FXML
    private TableColumn<Actividad, String> colApellido;
    @FXML
    private TableColumn<Actividad, String> colActividad;
    @FXML
    private TableColumn<Actividad, String> colDescripcion;
    @FXML
    private TableColumn<Actividad, String> colCantidad;
    @FXML
    private TableColumn<Actividad, String> colRepuesto;
    @FXML
    private TableColumn<Actividad, String> colMonto;

    //Para el registro de clientes
    @FXML
    private Label lblNuevoCliente;
    @FXML
    private VBox VBoxFormularioActividad;
    @FXML
    private TextField txtPlaca;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private ComboBox<Actividad> cbBoxRepuesto;
    @FXML
    private TextField txtActividad;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtCantidad;
    @FXML
    private Label lblNotificacion;
    @FXML
    private TextField txtCampoBusqueda;
    //endregion

    //region Variable Locales
    private ObservableList<Actividad> listaActividades;
    private int idVehiculoDePlacaIngresada;
    private int idRepuestoSeleccionado;

    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        //tableViewActividades.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        txtCantidad.addEventFilter(KeyEvent.ANY, filtrarSoloNumeros);
//        txtActividad.addEventFilter(KeyEvent.ANY, filtrarSoloLetras);
        actualizarTableViewActividades();
    }

    private void actualizarTableViewActividades(){
        listaActividades = FXCollections.observableArrayList();
        Actividad.llenarTableView(listaActividades);
        tableViewActividades.setItems(listaActividades);
        columnasTableViewClientes();
    }

    public void onRegistarButtonClicked(MouseEvent event) {
        actualizarTableViewActividades();
        VBoxFormularioActividad.setVisible(true);
        lblNuevoCliente.setText("Nueva actividad");
        lblNotificacion.setText("");
        vaciarCampos();
    }

    public void onCancelarRegistroClicked(MouseEvent event) {
        VBoxFormularioActividad.setVisible(false);

    }

    public void onGuardarButtonClicked(MouseEvent event) {
        Actividad actividad = tableViewActividades.getSelectionModel().getSelectedItem();
        int usuarioEnLinea = Usuario.idUsuarioForaneo; //Traido desde el login controller

        PreparedStatement pst = null;
        try {
            if(actividad != null && actividad.getId_hojareporte() > 0){
                if (verificarCamposVacios()) {
                    pst = ConexionMySQL.abrirConexion().prepareStatement(/////////////////////////AQUI
                            "UPDATE hojareporte SET actividad =?, descripcion =?, cantidad =?, id_repuesto = ? WHERE id_hojareporte = " + actividad.getId_hojareporte()
                    );
                    pst.setString(1, txtActividad.getText().trim());
                    pst.setString(2, txtDescripcion.getText().trim());
                    pst.setString(3, txtCantidad.getText().trim());
//                    pst.setString(4, idRepuestoSeleccionado;
//                    pst.setString(5, actividad.getFkid_vehiculo();
                    pst.executeUpdate();

                    lblNotificacion.setText("Modificacion exitosa");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));
                    vaciarCampos();
                    actualizarTableViewActividades(); //Para actualizar los cambios
                    VBoxFormularioActividad.setVisible(false);

                } else {
                    lblNotificacion.setText("Existen campos vacios");
                    lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
                    VBoxFormularioActividad.setVisible(true);
                }

            } else {
                if (verificarCamposVacios()) {
                    pst = ConexionMySQL.abrirConexion().prepareStatement(
                            "INSERT INTO hojareporte VALUES(?,?,?,?,?,?,?)"
                    );
                    pst.setString(1, "0");
                    pst.setString(2, txtActividad.getText().trim());
                    pst.setString(3, txtDescripcion.getText().trim());
                    pst.setString(4, txtCantidad.getText().trim());
//                    pst.setString(5, idRepuestoSeleccionado;
//                    pst.setString(6, idVehiculoDePlacaIngresada;
                    pst.setInt(7, usuarioEnLinea);
                    pst.executeUpdate();

                    lblNotificacion.setText("Regitro exitoso");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));
                    vaciarCampos();
                    actualizarTableViewActividades(); //Para actualizar los cambios
                    VBoxFormularioActividad.setVisible(false);

                } else {
                    lblNotificacion.setText("Existen campos vacios");
                    lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
                    VBoxFormularioActividad.setVisible(true);
                }
            }

        } catch (Exception e) {
            lblNotificacion.setText("Error en los cambios");
            lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
        }
    }

    public void onModificarButtonClicked(MouseEvent event) {
        Actividad actividad = tableViewActividades.getSelectionModel().getSelectedItem();
        lblNotificacion.setText("");

        if(actividad != null && actividad.getId_hojareporte() > 0){
            VBoxFormularioActividad.setVisible(true);
            lblNuevoCliente.setText("Editar cliente");
            //-----------------------------------------

            txtPlaca.setText(actividad.getPlaca());
            txtNombre.setText(actividad.getNombreCliente());
            txtApellido.setText(actividad.getApellidoCliente());
            txtActividad.setText(actividad.getActividad());
            txtDescripcion.setText(actividad.getDescripcion());
            txtCantidad.setText(actividad.getCantidad());

        } else {
            lblNotificacion.setText("Seleccione una fila para editar");
        }
    }

    public void onEliminarButtonClicked(MouseEvent event) {
        Actividad actividad = tableViewActividades.getSelectionModel().getSelectedItem();
        lblNotificacion.setText("");

        if(actividad != null && actividad.getId_hojareporte() > 0){
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Eliminar");
            alerta.setContentText("Esta seguro que desea eliminar esta fila");

            alerta.showAndWait();

            if(alerta.getResult().getText().equals("Aceptar")){
                if(Actividad.eliminarRegistroSeleccionado(actividad.getId_hojareporte())){
                    lblNotificacion.setText("Cliente eliminado");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));
                    actualizarTableViewActividades();
                } else {
                    lblNotificacion.setText("No se pudo eliminar el cliente");
                    lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
                }
            }
        } else {
            lblNotificacion.setText("Seleccione una fila para eliminar");
        }
    }

    private boolean verificarCamposVacios() {
        if (!txtActividad.getText().isBlank() &&
                !txtNombre.getText().isBlank() &&
                !txtApellido.getText().isBlank() &&
                !txtCantidad.getText().isBlank() &&
                !txtDescripcion.getText().isBlank()) {
            return true;
        }
        return false;
    }

    private void vaciarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtActividad.setText("");
        txtDescripcion.setText("");
    }

    /**Busqueda parcial del cliente ingresado */
    public void onBuscarCampoUsuarioKeyTyped(KeyEvent event) {
        listaActividades = FXCollections.observableArrayList();
        Actividad.datosDeActividadBuscada(listaActividades, txtCampoBusqueda.getText().trim());
        tableViewActividades.setItems(listaActividades);

        columnasTableViewClientes();
    }

    private void columnasTableViewClientes() {
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("identidad"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colActividad.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
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

