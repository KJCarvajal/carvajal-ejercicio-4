package sample.CONTROLLERS;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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

public class ControllerClientes implements Initializable {

    //region Variables de traidas de Scene Builder
    //Para llenar el tableView
    @FXML
    private TableView<Cliente> tableViewCliente;
    @FXML
    private TableColumn<Cliente, String> colIdentidad;
    @FXML
    private TableColumn<Cliente, String> colNombre;
    @FXML
    private TableColumn<Cliente, String> colApellido;
    @FXML
    private TableColumn<Cliente, String> colTelefono;
    @FXML
    private TableColumn<Cliente, String> colDireccion;

    //Para el registro de clientes
    @FXML
    private Label lblNuevoCliente;
    @FXML
    private VBox VBoxFormularioNuevoCliente;
    @FXML
    private TextField txtIdentidad;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtDireccion;
    @FXML
    private Label lblNotificacion;
    @FXML
    private TextField txtCampoBusqueda;
    //endregion

    //region Variable Locales
    private ObservableList<Cliente> listaClientes;

    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        //tableViewCliente.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        txtIdentidad.addEventFilter(KeyEvent.ANY, filtrarSoloNumeros);
        txtNombre.addEventFilter(KeyEvent.ANY, filtrarSoloLetras);
        txtApellido.addEventFilter(KeyEvent.ANY, filtrarSoloLetras);
        txtTelefono.addEventFilter(KeyEvent.ANY, filtrarSoloNumeros);
        actualizarTableViewClientes();
    }

    private void actualizarTableViewClientes(){
        listaClientes = FXCollections.observableArrayList();
        Cliente.llenarTableView(listaClientes);
        tableViewCliente.setItems(listaClientes);
        columnasTableViewClientes();
    }

    public void onRegistarButtonClicked(MouseEvent event) {
        actualizarTableViewClientes();
        VBoxFormularioNuevoCliente.setVisible(true);
        lblNuevoCliente.setText("Nuevo cliente");
        lblNotificacion.setText("");
        vaciarCampos();
    }

    public void onCancelarRegistroClicked(MouseEvent event) {
        VBoxFormularioNuevoCliente.setVisible(false);

    }

    public void onGuardarButtonClicked(MouseEvent event) {
        Cliente cliente = tableViewCliente.getSelectionModel().getSelectedItem();
        int usuarioEnLinea = Usuario.idUsuarioForaneo; //Traido desde el login controller

        PreparedStatement pst = null;
        try {
            if(cliente != null && cliente.getId_cliente() > 0){
                if (verificarCamposVacios()) {
                    pst = ConexionMySQL.abrirConexion().prepareStatement(
                            "UPDATE cliente SET identidad =?, nombre =?, apellido =?, telefono =?, direccion =? WHERE id_cliente = " + cliente.getId_cliente()
                    );
                    pst.setString(1, txtIdentidad.getText().trim());
                    pst.setString(2, txtNombre.getText().trim());
                    pst.setString(3, txtApellido.getText().trim());
                    pst.setString(4, txtTelefono.getText().trim());
                    pst.setString(5, txtDireccion.getText().trim());
                    pst.executeUpdate();

                    lblNotificacion.setText("Modificacion exitosa");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));
                    vaciarCampos();
                    actualizarTableViewClientes(); //Para actualizar los cambios
                    VBoxFormularioNuevoCliente.setVisible(false);

                } else {
                    lblNotificacion.setText("Existen campos vacios");
                    lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
                    VBoxFormularioNuevoCliente.setVisible(true);
                }

            } else {
                if (verificarCamposVacios()) {
                    pst = ConexionMySQL.abrirConexion().prepareStatement(
                            "INSERT INTO cliente VALUES(?,?,?,?,?,?,?)"
                    );
                    pst.setString(1, "0");
                    pst.setString(2, txtIdentidad.getText().trim());
                    pst.setString(3, txtNombre.getText().trim());
                    pst.setString(4, txtApellido.getText().trim());
                    pst.setString(5, txtTelefono.getText().trim());
                    pst.setString(6, txtDireccion.getText().trim());
                    pst.setInt(7, usuarioEnLinea);
                    pst.executeUpdate();

                    lblNotificacion.setText("Regitro exitoso");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));
                    vaciarCampos();
                    actualizarTableViewClientes(); //Para actualizar los cambios
                    VBoxFormularioNuevoCliente.setVisible(false);

                } else {
                    lblNotificacion.setText("Existen campos vacios");
                    lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
                    VBoxFormularioNuevoCliente.setVisible(true);
                }
            }

        } catch (Exception e) {
            lblNotificacion.setText("Error en los cambios");
            lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
        }
    }

    public void onModificarButtonClicked(MouseEvent event) {
        Cliente cliente = tableViewCliente.getSelectionModel().getSelectedItem();
        lblNotificacion.setText("");

        if(cliente != null && cliente.getId_cliente() > 0){
            VBoxFormularioNuevoCliente.setVisible(true);
            lblNuevoCliente.setText("Editar cliente");
            //-----------------------------------------

            txtIdentidad.setText(cliente.getIdentidad());
            txtNombre.setText(cliente.getNombre());
            txtApellido.setText(cliente.getApellido());
            txtTelefono.setText(cliente.getTelefono());
            txtDireccion.setText(cliente.getDireccion());

        } else {
            lblNotificacion.setText("Seleccione una fila para editar");
        }
    }

    public void onEliminarButtonClicked(MouseEvent event) {
        Cliente cliente = tableViewCliente.getSelectionModel().getSelectedItem();
        lblNotificacion.setText("");

        if(cliente != null && cliente.getId_cliente() > 0){
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Eliminar");
            alerta.setContentText("Esta seguro que desea eliminar esta fila");

            alerta.showAndWait();

            if(alerta.getResult().getText().equals("Aceptar")){
                if(Cliente.eliminarRegistroSeleccionado(cliente.getId_cliente())){
                    lblNotificacion.setText("Cliente eliminado");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));
                    actualizarTableViewClientes();
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
        if (!txtIdentidad.getText().isBlank() &&
                !txtNombre.getText().isBlank() &&
                !txtApellido.getText().isBlank() &&
                !txtTelefono.getText().isBlank() &&
                !txtDireccion.getText().isBlank()) {
            return true;
        }
        return false;
    }

    private void vaciarCampos() {
        txtIdentidad.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
    }

    /**Busqueda parcial del cliente ingresado */
    public void onBuscarCampoUsuarioKeyTyped(KeyEvent event) {
        listaClientes = FXCollections.observableArrayList();
        Cliente.datosDeClienteBuscado(listaClientes, txtCampoBusqueda.getText().trim());
        tableViewCliente.setItems(listaClientes);

        columnasTableViewClientes();
    }

    private void columnasTableViewClientes() {
        colIdentidad.setCellValueFactory(new PropertyValueFactory<>("identidad"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
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
