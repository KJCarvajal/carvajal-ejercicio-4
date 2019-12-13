package modelos;

import Conexion.ConexionMySQL;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehiculo {
    private int id_vehiculo;
    private String identidad;
    private String nombre;
    private String apellido;
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private int fkid_cliente;

    public Vehiculo(int id_vehiculo, String identidad, String nombre, String apellido, String placa, String marca, String modelo, String color, int fkid_cliente) {
        this.id_vehiculo = id_vehiculo;
        this.identidad = identidad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.fkid_cliente = fkid_cliente;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getFkid_cliente() {
        return fkid_cliente;
    }

    public void setFkid_cliente(int fkid_cliente) {
        this.fkid_cliente = fkid_cliente;
    }

    public static ObservableList<Vehiculo> llenarTableView(ObservableList<Vehiculo> lista){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "SELECT vehiculo.id_vehiculo, cliente.identidad, cliente.nombre, cliente.apellido,\n" +
                            "vehiculo.placa, vehiculo.marca, vehiculo.modelo, vehiculo.color, vehiculo.id_cliente FROM vehiculo\n" +
                            "INNER JOIN cliente ON cliente.id_cliente = vehiculo.id_cliente"
            );
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                lista.add( new Vehiculo(
                        resultado.getInt("vehiculo.id_vehiculo"),
                        resultado.getString("cliente.identidad"),
                        resultado.getString("cliente.nombre"),
                        resultado.getString("cliente.apellido"),
                        resultado.getString("vehiculo.placa"),
                        resultado.getString("vehiculo.marca"),
                        resultado.getString("vehiculo.modelo"),
                        resultado.getString("vehiculo.color"),
                        resultado.getInt("vehiculo.id_cliente")));
            }
            return lista;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static ObservableList<Vehiculo> datosDeVehiculoBuscado(ObservableList<Vehiculo> listaDatosVehiculo, String placaBuscada){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "SELECT vehiculo.id_vehiculo, cliente.identidad, cliente.nombre, cliente.apellido,\n" +
                            "vehiculo.placa, vehiculo.marca, vehiculo.modelo, vehiculo.color, vehiculo.id_cliente FROM vehiculo\n" +
                            "INNER JOIN cliente ON cliente.id_cliente = vehiculo.id_cliente WHERE vehiculo.placa LIKE ?"
            );
            sentencia.setString(1,placaBuscada + "%");
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                listaDatosVehiculo.add(new Vehiculo(
                        resultado.getInt("vehiculo.id_vehiculo"),
                        resultado.getString("cliente.identidad"),
                        resultado.getString("cliente.nombre"),
                        resultado.getString("cliente.apellido"),
                        resultado.getString("vehiculo.placa"),
                        resultado.getString("vehiculo.marca"),
                        resultado.getString("vehiculo.modelo"),
                        resultado.getString("vehiculo.color"),
                        resultado.getInt("vehiculo.id_cliente")));
            }
            return listaDatosVehiculo;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static boolean eliminarRegistroSeleccionado(int idVehiculo){
        PreparedStatement sentencia = null;
        try {
            sentencia = ConexionMySQL.abrirConexion().prepareStatement(
                    "DELETE FROM vehiculo WHERE id_vehiculo = ?"
            );
            sentencia.setInt(1, idVehiculo);
            sentencia.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

}
