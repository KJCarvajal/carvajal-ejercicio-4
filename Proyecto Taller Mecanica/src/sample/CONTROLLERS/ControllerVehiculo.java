package sample.CONTROLLERS;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import Conexion.ConexionMySQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import modelos.Cliente;
import modelos.Usuario;
import modelos.Vehiculo;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerVehiculo implements Initializable {

    //region Variables de traidas de Scene Builder
    //Para llenar el tableView
    @FXML
    private TableView<Vehiculo> tableViewVehiculo;
    @FXML
    private TableColumn<Vehiculo, String> colIdentidad;
    @FXML
    private TableColumn<Vehiculo, String> colNombre;
    @FXML
    private TableColumn<Vehiculo, String> colApellido;
    @FXML
    private TableColumn<Vehiculo, String> colPlaca;
    @FXML
    private TableColumn<Vehiculo, String> colMarca;
    @FXML
    private TableColumn<Vehiculo, String> colModelo;
    @FXML
    private TableColumn<Vehiculo, String> colColor;

    //Para el registro de clientes
    @FXML
    private Label lblNuevoCliente;
    @FXML
    private VBox VBoxFormularioVehiculo;
    @FXML
    private TextField txtIdentidad;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtPlaca;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtColor;
    @FXML
    private Label lblNotificacion;
    @FXML
    private TextField txtCampoBusqueda;
    //endregion

    //region Variable Locales
    private ObservableList<Vehiculo> listaVehiculos;
    private int idClienteDeLaIdentidadIngresada;

    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        actualizarTableViewVehiculo();
    }

    private void actualizarTableViewVehiculo(){
        listaVehiculos = FXCollections.observableArrayList();
        Vehiculo.llenarTableView(listaVehiculos);
        tableViewVehiculo.setItems(listaVehiculos);

        columnasTableViewVehiculo();
    }

    public void onRegistarButtonClicked(MouseEvent event) {
        actualizarTableViewVehiculo();
        VBoxFormularioVehiculo.setVisible(true);
        lblNuevoCliente.setText("Nuevo cliente");
        lblNotificacion.setText("");
        vaciarCampos();
    }

    public void onCancelarRegistroClicked(MouseEvent event) {
        VBoxFormularioVehiculo.setVisible(false);

    }

    public void onGuardarButtonClicked(MouseEvent event) {
        Vehiculo vehiculo = tableViewVehiculo.getSelectionModel().getSelectedItem();
        int usuarioEnLinea = Usuario.idUsuarioForaneo; //Traido desde el login controller

        PreparedStatement pst = null;
        try {
            if(vehiculo != null && vehiculo.getId_vehiculo() > 0){
                if (verificarCamposVacios()) {
                    pst = ConexionMySQL.abrirConexion().prepareStatement(
                            "UPDATE vehiculo SET placa =?, marca =?, modelo =?, color =?, id_cliente =? WHERE id_vehiculo = " + vehiculo.getId_vehiculo()
                    );
                    pst.setString(1, txtPlaca.getText().trim());
                    pst.setString(2, txtMarca.getText().trim());
                    pst.setString(3, txtModelo.getText().trim());
                    pst.setString(4, txtColor.getText().trim());
                    pst.setInt(5, vehiculo.getFkid_cliente()); //CAMBIARLA
                    pst.executeUpdate();

                    lblNotificacion.setText("Modificacion exitosa");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));
                    vaciarCampos();
                    actualizarTableViewVehiculo(); //Para actualizar los cambios
                    VBoxFormularioVehiculo.setVisible(false);

                } else {
                    lblNotificacion.setText("Existen campos vacios");
                    lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
                    VBoxFormularioVehiculo.setVisible(true);
                }

            } else {
                if (verificarCamposVacios()) {
                    pst = ConexionMySQL.abrirConexion().prepareStatement(
                            "INSERT INTO vehiculo VALUES(?,?,?,?,?,?,?)"
                    );
                    pst.setString(1, "0");
                    pst.setString(2, txtPlaca.getText().trim());
                    pst.setString(3, txtMarca.getText().trim());
                    pst.setString(4, txtModelo.getText().trim());
                    pst.setString(5, txtColor.getText().trim());
                    pst.setInt(6, idClienteDeLaIdentidadIngresada);
                    pst.setInt(7, usuarioEnLinea);
                    pst.executeUpdate();

                    lblNotificacion.setText("Regitro exitoso");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));
                    vaciarCampos();
                    actualizarTableViewVehiculo(); //Para actualizar los cambios
                    VBoxFormularioVehiculo.setVisible(false);

                } else {
                    lblNotificacion.setText("Existen campos vacios");
                    lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
                    VBoxFormularioVehiculo.setVisible(true);
                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            lblNotificacion.setText("Error en los cambios");
            lblNotificacion.setTextFill(Color.rgb(237, 29, 24));


            System.out.println("El id del cliente: " + idClienteDeLaIdentidadIngresada);
            System.out.println("El usuario que lo antendio: " + usuarioEnLinea);
        }
    }

    public void onModificarButtonClicked(MouseEvent event) {
        Vehiculo vehiculo = tableViewVehiculo.getSelectionModel().getSelectedItem();

        lblNotificacion.setText("");

        if(vehiculo != null && vehiculo.getId_vehiculo() > 0){
            VBoxFormularioVehiculo.setVisible(true);
            lblNuevoCliente.setText("Editar vehiculo");
            //-----------------------------------------

            txtIdentidad.setText(vehiculo.getIdentidad());
            txtNombre.setText(vehiculo.getNombre());
            txtApellido.setText(vehiculo.getApellido());
            txtPlaca.setText(vehiculo.getPlaca());
            txtMarca.setText(vehiculo.getMarca());
            txtModelo.setText(vehiculo.getModelo());
            txtColor.setText(vehiculo.getColor());

        } else {
            lblNotificacion.setText("Seleccione una fila para editar");
        }
    }

    public void onEliminarButtonClicked(MouseEvent event) {
        Vehiculo vehiculo = tableViewVehiculo.getSelectionModel().getSelectedItem();
        lblNotificacion.setText("");

        if(vehiculo != null && vehiculo.getId_vehiculo() > 0){
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Eliminar");
            alerta.setContentText("Esta seguro que desea eliminar esta fila");

            alerta.showAndWait();

            if(alerta.getResult().getText().equals("Aceptar")){
                if(Vehiculo.eliminarRegistroSeleccionado(vehiculo.getId_vehiculo())){
                    lblNotificacion.setText("Vehiculo eliminado");
                    lblNotificacion.setTextFill(Color.rgb(13, 191, 61));
                    actualizarTableViewVehiculo();
                } else {
                    lblNotificacion.setText("No se pudo eliminar el vehiculo");
                    lblNotificacion.setTextFill(Color.rgb(237, 29, 24));
                }
            }
        } else {
            lblNotificacion.setText("Seleccione una fila para eliminar");
        }
    }

    private boolean verificarCamposVacios() {
        if (!txtPlaca.getText().isBlank() &&
                !txtMarca.getText().isBlank() &&
                !txtIdentidad.getText().isBlank() &&
                !txtModelo.getText().isBlank() &&
                !txtColor.getText().isBlank()) {
            return true;
        }
        return false;
    }

    private void vaciarCampos() {
        txtIdentidad.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtPlaca.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtColor.setText("");
    }

    /**Busqueda parcial del cliente ingresado */
    public void onBuscarCampoUsuarioKeyTyped(KeyEvent event) {
        listaVehiculos = FXCollections.observableArrayList();
        Vehiculo.datosDeVehiculoBuscado(listaVehiculos, txtCampoBusqueda.getText().trim());
        tableViewVehiculo.setItems(listaVehiculos);

        columnasTableViewVehiculo();
    }

    /**Busqueda de identidad en el textField */
    public void onIdentificarClienteKeyTyped(KeyEvent event){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "SELECT id_cliente, nombre, apellido FROM cliente WHERE identidad LIKE ?"
            );
            sentencia.setString(1, txtIdentidad.getText().trim());
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                txtNombre.setText(resultado.getString("nombre"));
                txtApellido.setText(resultado.getString("apellido"));
                idClienteDeLaIdentidadIngresada = resultado.getInt("id_cliente");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void columnasTableViewVehiculo() {
        colIdentidad.setCellValueFactory(new PropertyValueFactory<>("identidad"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
    }

}

