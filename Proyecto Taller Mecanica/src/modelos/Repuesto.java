package modelos;

import Conexion.ConexionMySQL;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Repuesto {
    private int id_repuesto;
    private String nombre;
    private String costo;
    private String cantidad;

    public Repuesto(int id_repuesto, String nombre, String costo, String cantidad) {
        this.id_repuesto = id_repuesto;
        this.nombre = nombre;
        this.costo = costo;
        this.cantidad = cantidad;
    }

    public int getId_repuesto() {
        return id_repuesto;
    }

    public void setId_repuesto(int id_repuesto) {
        this.id_repuesto = id_repuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public static void llenarTableView(ObservableList<Repuesto> lista){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "SELECT * FROM repuesto"
            );
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                lista.add(new Repuesto(
                        resultado.getInt("id_repuesto"),
                        resultado.getString("nombre"),
                        resultado.getString("costo"),
                        resultado.getString("cantidad")));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static ObservableList<Repuesto> datosDeRepuestoBuscado(ObservableList<Repuesto> listaDatosRepuesto, String repuestoBuscado){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "SELECT * FROM repuesto WHERE nombre LIKE ?"
            );
            sentencia.setString(1,repuestoBuscado + "%");
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                listaDatosRepuesto.add(new Repuesto(
                        resultado.getInt("id_repuesto"),
                        resultado.getString("nombre"),
                        resultado.getString("costo"),
                        resultado.getString("cantidad")));
            }
            return listaDatosRepuesto;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static boolean eliminarRegistroSeleccionado(int idRepuesto){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "DELETE FROM repuesto WHERE id_repuesto = ?"
            );
            sentencia.setInt(1, idRepuesto);
            sentencia.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
