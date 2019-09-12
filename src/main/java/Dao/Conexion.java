package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.Data;

@Data
public class Conexion {

    private Connection cn = null;

    public void Conectar() throws Exception {
        try {
            if (cn == null) {
                Class.forName("oracle.jdbc.OracleDriver");
//                Class.forName("com.mysql.jdbc.Driver");
                cn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "FILTERDATE", "vallegrande2018");
//                cn = DriverManager.getConnection("jdbc:mysql://35.211.37.203:3306/PEOPLE", "root", "Amartepor1000A");
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    //Metodo de cerrar la conexión
    public void Cerrar() throws Exception {
        if (cn != null) {
            if (cn.isClosed() == false) {
                cn.close();
                cn = null;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Conexion dao = new Conexion();
        dao.Conectar();
        if (dao.getCn() != null) {
            System.out.println("Conectado con éxito");
        } else {
            System.err.println("Error en la Conexión");
        }
    }

}
