package sample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBase {

    private static Connection conexion;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "Nemurenai8";
    private static final String url = "jdbc:mysql://localhost:3306/base-mecanica-v5?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private static int contador = 0;

    protected ConexionBase(){}

    public static Connection abrirConexion(){
        conexion = null;
        try{
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, password);

            if(conexion != null){
                contador++;
                if(contador == 1)
                    System.out.println("Conexion establecida " + contador + " vez");
                else
                    System.out.println("Conexion establecida " + contador + " veces");
            }

        } catch (ClassNotFoundException | SQLException e){
            System.out.println("Error al conectar " + e);
        }
        return conexion;
    }

    public Connection getConnection(){
        return conexion;
    }
}
