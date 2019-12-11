package modelos;

import Conexion.ConexionMySQL;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cliente {
    private int id_cliente;
    private String identidad;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;

    public Cliente(int id_cliente, String identidad, String nombre, String apellido, String telefono, String direccion) {
        this.id_cliente = id_cliente;
        this.identidad = identidad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getId_cliente() {
        return id_cliente;
    }
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getIdentidad() {
        return identidad;
    }
    public void setIdentidad(String identidad) {
        this.identidad = identidad;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public static void llenarTableView(ObservableList<Cliente> lista){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "SELECT * FROM cliente"
            );
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                lista.add(new Cliente(
                        resultado.getInt("id_cliente"),
                        resultado.getString("identidad"),
                        resultado.getString("nombre"),
                        resultado.getString("apellido"),
                        resultado.getString("telefono"),
                        resultado.getString("direccion")));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static ObservableList<Cliente> datosDeClienteBuscado(ObservableList<Cliente> listaDatosCliente, String clienteBuscado){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "SELECT * FROM cliente WHERE nombre LIKE ?"
            );
            sentencia.setString(1,clienteBuscado + "%");
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                listaDatosCliente.add(new Cliente(
                        resultado.getInt("id_cliente"),
                        resultado.getString("identidad"),
                        resultado.getString("nombre"),
                        resultado.getString("apellido"),
                        resultado.getString("telefono"),
                        resultado.getString("direccion")));
            }
            return listaDatosCliente;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static boolean eliminarRegistroSeleccionado(int idCliente){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "DELETE FROM cliente WHERE id_cliente = ?"
            );
            sentencia.setInt(1, idCliente);
            sentencia.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
