package modelos;

import Conexion.ConexionMySQL;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Actividad {
    private int id_hojareporte;
    private String placa;
    private String nombreCliente;
    private String apellidoCliente;
    private String actividad;
    private String descripcion;
    private String repuestoNombre;
    private String cantidad;
    private String monto;
    private int fkid_vehiculo;
    private int fkid_repuesto;

    public Actividad(int id_hojareporte, String placa, String nombreCliente, String apellidoCliente, String actividad, String descripcion, String repuestoNombre, String cantidad, String monto, int fkid_vehiculo, int fkid_repuesto) {
        this.id_hojareporte = id_hojareporte;
        this.placa = placa;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.actividad = actividad;
        this.descripcion = descripcion;
        this.repuestoNombre = repuestoNombre;
        this.cantidad = cantidad;
        this.monto = monto;
        this.fkid_vehiculo = fkid_vehiculo;
        this.fkid_repuesto = fkid_repuesto;
    }

    public int getId_hojareporte() {
        return id_hojareporte;
    }

    public void setId_hojareporte(int id_hojareporte) {
        this.id_hojareporte = id_hojareporte;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRepuestoNombre() {
        return repuestoNombre;
    }

    public void setRepuestoNombre(String repuestoNombre) {
        this.repuestoNombre = repuestoNombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public int getFkid_vehiculo() {
        return fkid_vehiculo;
    }

    public void setFkid_vehiculo(int fkid_vehiculo) {
        this.fkid_vehiculo = fkid_vehiculo;
    }

    public int getFkid_repuesto() {
        return fkid_repuesto;
    }

    public void setFkid_repuesto(int fkid_repuesto) {
        this.fkid_repuesto = fkid_repuesto;
    }

    public static void llenarTableView(ObservableList<Actividad> lista){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "SELECT hojareporte.id_hojareporte, vehiculo.placa, cliente.nombre, cliente.apellido, \n" +
                            "hojareporte.actividad, hojareporte.descripcion, hojareporte.cantidad, hojareporte.monto, \n" +
                            "vehiculo.id_vehiculo, repuesto.nombre, repuesto.id_repuesto FROM hojareporte\n" +
                            "INNER JOIN vehiculo ON vehiculo.id_vehiculo = hojareporte.id_vehiculo\n" +
                            "INNER JOIN repuesto ON repuesto.id_repuesto = hojareporte.id_repuesto\n" +
                            "INNER JOIN cliente ON cliente.id_cliente = vehiculo.id_cliente"
            );
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                lista.add(new Actividad(
                        resultado.getInt("hojareporte.id_hojareporte"),
                        resultado.getString("vehiculo.placa"),
                        resultado.getString("cliente.nombre"),
                        resultado.getString("cliente.apellido"),
                        resultado.getString("hojareporte.actividad"),
                        resultado.getString("hojareporte.descripcion"),
                        resultado.getString("repuesto.nombre"),
                        resultado.getString("hojareporte.cantidad"),
                        resultado.getString("hojareporte.monto"),
                        resultado.getInt("vehiculo.id_vehiculo"),
                        resultado.getInt("repuesto.id_repuesto")));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static ObservableList<Actividad> datosDeActividadBuscada(ObservableList<Actividad> listaDatosActividad, String placaBuscada){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "SELECT * FROM cliente WHERE nombre LIKE ?"
            );
            sentencia.setString(1,placaBuscada + "%");
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                listaDatosActividad.add(new Actividad(
                        resultado.getInt("hojareporte.id_hojareporte"),
                        resultado.getString("vehiculo.placa"),
                        resultado.getString("cliente.nombre"),
                        resultado.getString("cliente.apellido"),
                        resultado.getString("hojareporte.actividad"),
                        resultado.getString("hojareporte.descripcion"),
                        resultado.getString("repuesto.nombre"),
                        resultado.getString("hojareporte.cantidad"),
                        resultado.getString("hojareporte.monto"),
                        resultado.getInt("vehiculo.id_vehiculo"),
                        resultado.getInt("repuesto.id_repuesto")));
            }
            return listaDatosActividad;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static boolean eliminarRegistroSeleccionado(int idHojaReporte){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "DELETE FROM hojareporte WHERE id_hojareporte = ?"
            );
            sentencia.setInt(1, idHojaReporte);
            sentencia.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public String toString(){
        return this.repuestoNombre; //Para llenar el combobox
    }
}
