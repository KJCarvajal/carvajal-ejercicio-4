package modelos;

import Conexion.ConexionMySQL;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private int id_usuario;
    private String identidad;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String correo_electronico;
    private String nombre_usuario;
    private String contrasenia;
    private String tipo_usuario;

    public Usuario(int id_usuario, String identidad, String nombre, String apellido, String telefono, String direccion, String correo_electronico, String nombre_usuario, String contrasenia, String tipo_usuario) {
        this.id_usuario = id_usuario;
        this.identidad = identidad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo_electronico = correo_electronico;
        this.nombre_usuario = nombre_usuario;
        this.contrasenia = contrasenia;
        this.tipo_usuario = tipo_usuario;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
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

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public static void llenarTableView(ObservableList<Usuario> lista){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "SELECT * FROM usuario"
            );
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                lista.add(new Usuario(
                        resultado.getInt("id_usuario"),
                        resultado.getString("identidad"),
                        resultado.getString("nombre"),
                        resultado.getString("apellido"),
                        resultado.getString("telefono"),
                        resultado.getString("direccion"),
                        resultado.getString("correo_electronico"),
                        resultado.getString("nombre_usuario"),
                        resultado.getString("contrasenia"),
                        resultado.getString("tipo_usuario")));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static ObservableList<Usuario> datosDeUsuarioBuscado(ObservableList<Usuario> listaDatosUsuario, String usuarioBuscado){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "SELECT * FROM usuario WHERE nombre LIKE ?"
            );
            sentencia.setString(1, usuarioBuscado + "%");
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                listaDatosUsuario.add(new Usuario(
                        resultado.getInt("id_usuario"),
                        resultado.getString("identidad"),
                        resultado.getString("nombre"),
                        resultado.getString("apellido"),
                        resultado.getString("telefono"),
                        resultado.getString("direccion"),
                        resultado.getString("correo_electronico"),
                        resultado.getString("nombre_usuario"),
                        resultado.getString("contrasenia"),
                        resultado.getString("tipo_usuario")));
            }

            return listaDatosUsuario;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

//    @Override
//    public String toString(){
//        return "Usuario [identidad=" + identidad +
//                ", nombre =" + nombre +
//                ", apellido =" + apellido +
//                ", telefono =" + telefono +
//                ", direccion =" + direccion +
//                ", correo_electronico =" + correo_electronico +
//                ", nombre_usuario =" + nombre_usuario +
//                ", contrasenia =" + contrasenia +
//                ", tipo_usuario  =" + tipo_usuario + "]";
//    }
}
