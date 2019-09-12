/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.EstadisticoM;
import Model.PersonaM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jvale
 */
public class ImplRangoD extends Conexion{
    
    
       /*Este metodo me permitira listar la fecha de rango de un Reporte 
     Estadistico desde el inicio hasta el final de un programa*/
    public ArrayList<EstadisticoM> listarFech_Ini_Fin() throws Exception {
        try {
            ArrayList<EstadisticoM> reporteS = new ArrayList<>();
            ResultSet rs;
            this.Conectar();
            String Sql = "SELECT * FROM PERSONA";
//            String Sql = "SELECT CODPER,DNIPER,NOMAPEPER,EMAPER,FECNACPER,FECINIPER,FECFINPER FROM PERSONA";
            PreparedStatement ps = this.getCn().prepareCall(Sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                EstadisticoM prog = new EstadisticoM();
                prog.setCODPER(rs.getString("CODPER"));
//                prog.setDNIPER(rs.getString("DNIPER"));
//                prog.setNOMAPEPER(rs.getString("NOMAPEPER"));
//                prog.setEMAPER(rs.getString("EMAPER"));
//                prog.setFECNACPER(rs.getString("FECNACPER"));
                prog.setFECHINI(rs.getString("FECINIPER"));
                prog.setFECHFIN(rs.getString("FECFINPER"));
                reporteS.add(prog);
            }
            return reporteS;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}
