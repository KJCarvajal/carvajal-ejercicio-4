package sample.CONTROLLERS;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerEmpleados implements Initializable {

    //region Variables de traidas de Scene Builder
    //Para llenar el tableView
    @FXML
    private TableView<Usuario> tableViewEmpleado;
    @FXML
    private TableColumn<Usuario, String> colIdentidad;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, String> colApellido;
    @FXML
    private TableColumn<Usuario, String> colTelefono;
    @FXML
    private TableColumn<Usuario, String> colDireccion;
    @FXML
    private TableColumn<Usuario, String> colCorreo;
    @FXML
    private TableColumn<Usuario, String> colNombreUsuario;
    @FXML
    private TableColumn<Usuario, String> colContrasenia;
    @FXML
    private TableColumn<Usuario, String> colTipoUsuario;

    //Para el registro de empleados
    @FXML
    private Button btnNuevoEmpleado;
    @FXML
    private Label lblNuevoEmpleado;
    @FXML
    private VBox VBoxFormularioNuevoEmpleado;
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
    private TextField txtCorreo;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtContrasenia;
    @FXML
    private TextField txtTipoUsuario;
    @FXML
    private Label lblNotificacion;
    @FXML
    private TextField txtCampoBusqueda;


    //endregion

    //region Variable Locales
    private ObservableList<Usuario> listaEmpleados;

    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        //tableViewEmpleado.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        actualizarTableViewEmpleados();
    }

    private void actualizarTableViewEmpleados(){
        listaEmpleados = FXCollections.observableArrayList();
        Usuario.llenarTableView(listaEmpleados);
        tableViewEmpleado.setItems(listaEmpleados);
        columnasTableViewUsuarios();
    }

    public void onRegistarButtonClicked(MouseEvent event) {
        actualizarTableViewEmpleados();
        VBoxFormularioNuevoEmpleado.setVisible(true);
        lblNuevoEmpleado.setText("Nuevo empleado");
        lblNotificacion.setText("");
        vaciarCampos();
    }

    public void onCancelarRegistroClicked(MouseEvent event) {
        VBoxFormularioNuevoEmpleado.setVisible(false);

    }

    public void onGuardarButtonClicked(MouseEvent event) {
        Usuario usuario = tableViewEmpleado.getSelectionModel().getSelectedItem();

        System.out.println(usuario);
        PreparedStatement pst = null;

        try {
            if(usuario != null && usuario.getId_usuario() > 0){
                if (verificarCamposVacios()) {
                    pst = ConexionMySQL.abrirConexion().prepareStatement(
                            "UPDATE usuario SET identidad =?, nombre =?, apellido =?, telefono =?, direccion =?, correo_electronico =?, nombre_usuario =?, contrasenia =?, tipo_usuario =? WHERE id_usuario = " + usuario.getId_usuario()
                    );
                    pst.setString(1, txtIdentidad.getText().trim());
                    pst.setString(2, txtNombre.getText().trim());
                    pst.setString(3, txtApellido.getText().trim());
                    pst.setString(4, txtTelefono.getText().trim());
                    pst.setString(5, txtDireccion.getText().trim());
                    pst.setString(6, txtCorreo.getText().trim());
                    pst.setString(7, txtNombreUsuario.getText().trim());
                    pst.setString(8, txtContrasenia.getText().trim());
                    pst.setString(9, txtTipoUsuario.getText().trim());
                    pst.executeUpdate();

                    lblNotificacion.setText("Modificacion exitosa");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));

                } else {
                    lblNotificacion.setText("Existen campos vacios");
                    lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
                    VBoxFormularioNuevoEmpleado.setVisible(true);
                }

            } else {
                if (verificarCamposVacios()) {
                    pst = ConexionMySQL.abrirConexion().prepareStatement(
                            "INSERT INTO usuario VALUES(?,?,?,?,?,?,?,?,?,?)"
                    );
                    pst.setString(1, "0");
                    pst.setString(2, txtIdentidad.getText().trim());
                    pst.setString(3, txtNombre.getText().trim());
                    pst.setString(4, txtApellido.getText().trim());
                    pst.setString(5, txtTelefono.getText().trim());
                    pst.setString(6, txtDireccion.getText().trim());
                    pst.setString(7, txtCorreo.getText().trim());
                    pst.setString(8, txtNombreUsuario.getText().trim());
                    pst.setString(9, txtContrasenia.getText().trim());
                    pst.setString(10, txtTipoUsuario.getText().trim());
                    pst.executeUpdate();

                    lblNotificacion.setText("Regitro exitoso");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));

                } else {
                    lblNotificacion.setText("Existen campos vacios");
                    lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
                    VBoxFormularioNuevoEmpleado.setVisible(true);
                }
            }

            vaciarCampos();
            actualizarTableViewEmpleados(); //Para actualizar los cambios
            VBoxFormularioNuevoEmpleado.setVisible(false);

        } catch (Exception e) {
            lblNotificacion.setText("Error en los cambios");
            lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
        }
    }

    public void onModificarButtonClicked(MouseEvent event) {
        Usuario usuario = tableViewEmpleado.getSelectionModel().getSelectedItem();
        lblNotificacion.setText("");

        if(usuario != null && usuario.getId_usuario() > 0){
            VBoxFormularioNuevoEmpleado.setVisible(true);
            lblNuevoEmpleado.setText("Editar usuario");
            //-----------------------------------------
            txtIdentidad.setText(usuario.getIdentidad());
            txtNombre.setText(usuario.getNombre());
            txtApellido.setText(usuario.getApellido());
            txtTelefono.setText(usuario.getTelefono());
            txtDireccion.setText(usuario.getDireccion());
            txtCorreo.setText(usuario.getCorreo_electronico());
            txtNombreUsuario.setText(usuario.getNombre_usuario());
            txtContrasenia.setText(usuario.getContrasenia());
            txtTipoUsuario.setText(usuario.getTipo_usuario());
        } else {
            lblNotificacion.setText("Seleccione una fila para editar");
        }
    }

    private boolean verificarCamposVacios() {
        if (!txtIdentidad.getText().isBlank() &&
                !txtNombre.getText().isBlank() &&
                !txtApellido.getText().isBlank() &&
                !txtTelefono.getText().isBlank() &&
                !txtDireccion.getText().isBlank() &&
                !txtCorreo.getText().isBlank() &&
                !txtNombreUsuario.getText().isBlank() &&
                !txtContrasenia.getText().isBlank() &&
                !txtTipoUsuario.getText().isBlank()) {
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
        txtCorreo.setText("");
        txtNombreUsuario.setText("");
        txtContrasenia.setText("");
        txtTipoUsuario.setText("");
    }

    /**Busqueda parcial del usuario ingresado */
    public void onBuscarCampoUsuarioKeyTyped(KeyEvent event) {
        listaEmpleados = FXCollections.observableArrayList();
        Usuario.datosDeUsuarioBuscado(listaEmpleados, txtCampoBusqueda.getText().trim());
        tableViewEmpleado.setItems(listaEmpleados);

        columnasTableViewUsuarios();
    }

    private void columnasTableViewUsuarios() {
        colIdentidad.setCellValueFactory(new PropertyValueFactory<>("identidad"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo_electronico"));
        colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre_usuario"));
        colContrasenia.setCellValueFactory(new PropertyValueFactory<>("contrasenia"));
        colTipoUsuario.setCellValueFactory(new PropertyValueFactory<>("tipo_usuario"));
    }
}
