package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {

    private static Connection conn;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "Nemurenai8";
    private static final String url = "jdbc:mysql://localhost:3306/base-mecanica-v6?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private static int contador = 0;

    protected ConexionMySQL(){}

    public static Connection abrirConexion(){
        conn = null;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);

            if(conn != null){
                contador++;
                if(contador == 1)
                    System.out.println("Conexion establecida " + contador + " vez");
                else
                    System.out.println("Conexion establecida ya " + contador + " veces");
            }

        } catch (ClassNotFoundException | SQLException e){
            System.out.println("Error al conectar " + e);
        }
        return conn;
    }

    public Connection getConnection(){
        return conn;
    }
}

