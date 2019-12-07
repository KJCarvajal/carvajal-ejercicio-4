package modelos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Usuarios {
    private IntegerProperty id_usuario;
    private String identidad;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String correo_electronico;
    private String nombre_usuario;
    private String contrasenia;
    private String tipo_usuario;

    public Usuarios(int id_usuario, String identidad, String nombre, String apellido, String telefono, String direccion, String correo_electronico, String nombre_usuario, String contrasenia, String tipo_usuario) {
        this.id_usuario = new SimpleIntegerProperty(id_usuario);
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
        return id_usuario.get();
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = new SimpleIntegerProperty(id_usuario);
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
}
