package Dao;

import Interface.PersonaI;
import Model.PersonaM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplPersonaD extends Conexion implements PersonaI {

    @Override
    public void registrarPersona(PersonaM per) throws Exception {
        try {
            Conectar();
            String sql = "INSERT INTO PERSONA (DNIPER,NOMAPEPER,EMAPER,FECNACPER,FECINIPER,FECFINPER) VALUES (?,?,?,TO_DATE(?,'dd/MM/yyyy'),TO_DATE(?,'dd/MM/yyyy'),TO_DATE(?,'dd/MM/yyyy'))";
//            String sql = "INSERT INTO PERSONA (DNIPER,NOMAPEPER,EMAPER,FECNACPER) VALUES (?,?,?,STR_TO_DATE(?,'%d/%m/%Y'))";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, per.getDNIPER());
            ps.setString(2, per.getNOMAPEPER());
            ps.setString(3, per.getEMAPER());
            ps.setString(4, per.getFECNACPER());
            ps.setString(5, per.getFECINIPER());
            ps.setString(6, per.getFECFINPER());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            Cerrar();
        }
    }

    @Override
    public List<PersonaM> listarPersona() throws Exception {
        List<PersonaM> lista;
        ResultSet rs;
        try {
            Conectar();
            String sql = "SELECT CODPER,DNIPER,NOMAPEPER,EMAPER,TO_CHAR(FECNACPER,'dd/MM/yyyy') AS FECNACPER,TO_CHAR(FECINIPER,'dd/MM/yyyy') AS FECINIPER,TO_CHAR(FECFINPER,'dd/MM/yyyy') AS FECFINPER FROM PERSONA where regexp_like (EMAPER,'^[a-z0-9._-]+@[a-z0-9.-]+.[a-z]{2,3}$','i')";
//            String sql = "SELECT CODPER,DNIPER,NOMAPEPER,EMAPER,date_FORMAT(FECNACPER,'%d/%m/%Y') AS FECNACPER "
//                    + "FROM PERSONA WHERE EMAPER REGEXP ('^[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,6}$')";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            PersonaM listPer;
            while (rs.next()) {
                listPer = new PersonaM();
                listPer.setCODPER(rs.getString("CODPER"));
                listPer.setDNIPER(rs.getString("DNIPER"));
                listPer.setNOMAPEPER(rs.getString("NOMAPEPER"));
                listPer.setEMAPER(rs.getString("EMAPER"));
                listPer.setFECNACPER(rs.getString("FECNACPER"));
                listPer.setFECINIPER(rs.getString("FECINIPER"));
                listPer.setFECFINPER(rs.getString("FECFINPER"));
                lista.add(listPer);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            Cerrar();
        }
        return lista;
    }

    /*Este metodo me permitira listar la fecha de rango de un Reporte 
     Estadistico desde el inicio hasta el final de un programa*/
    public ArrayList<PersonaM> listarFech_Ini_Fin() throws Exception {
        try {
            ArrayList<PersonaM> reporteS = new ArrayList<>();
            ResultSet rs;
            this.Conectar();
            String Sql = "SELECT CODPER, TO_CHAR(FECINIPER, 'dd/MM/yyyy') AS FECINIPER, TO_CHAR(FECFINPER, 'dd/MM/yyyy') AS FECFINPER FROM PERSONA";
//            String Sql = "SELECT CODPER,DNIPER,NOMAPEPER,EMAPER,FECNACPER,FECINIPER,FECFINPER FROM PERSONA";
            PreparedStatement ps = this.getCn().prepareCall(Sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PersonaM prog = new PersonaM();
                prog.setCODPER(rs.getString("CODPER"));
//                prog.setDNIPER(rs.getString("DNIPER"));
//                prog.setNOMAPEPER(rs.getString("NOMAPEPER"));
//                prog.setEMAPER(rs.getString("EMAPER"));
//                prog.setFECNACPER(rs.getString("FECNACPER"));
                prog.setFECINIPER(rs.getString("FECINIPER"));
                prog.setFECFINPER(rs.getString("FECFINPER"));
                reporteS.add(prog);
            }
            return reporteS;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public List<PersonaM> listarFech_Ini_Fin_Rango(String rango) throws Exception {

        List<PersonaM> listaRang;
        ResultSet rs;
        try {
            this.Conectar();
            String Sql = "SELECT DNIPER,NOMAPEPER,EMAPER,TO_CHAR(FECNACPER,'DD/MM/YYYY') AS FECNACPER, "
                    + "TO_CHAR(FECINIPER,'DD/MM/YYYY') AS FECINIPER,TO_CHAR(FECFINPER,'DD/MM/YYYY') AS FECFINPER,CODPER "
                    + "FROM PERSONA WHERE FECINIPER BETWEEN ? AND ?";
//            String Sql = "SELECT * FROM PERSONA WHERE TO_date(FECINIPER,'dd/mm/yy HH:MI') >= '01/01/2014' AND TO_date(FECFINPER,'dd/mm/yy HH:MI') <= '30/11/2018'";
            PreparedStatement ps = this.getCn().prepareStatement(Sql);
            ps.setString(1, rango);
            ps.setString(2, rango);
            rs = ps.executeQuery();
            listaRang = new ArrayList<>();
            PersonaM rang;
            while (rs.next()) {
                rang = new PersonaM();
                rang.setDNIPER(rs.getString("DNIPER"));
                rang.setNOMAPEPER(rs.getString("NOMAPEPER"));
                rang.setEMAPER(rs.getString("EMAPER"));
                rang.setFECNACPER(rs.getString("FECNACPER"));
                rang.setFECINIPER(rs.getString("FECINIPER"));
                rang.setFECFINPER(rs.getString("FECFINPER"));
                rang.setCODPER(rs.getString("CODPER"));
                listaRang.add(rang);
            }
            return listaRang;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }
//    public List<PersonaM> listarFech_Ini_Fin() throws Exception {
//
//        List<PersonaM> listaRang;
//        ResultSet rs;
//        try {
//            this.Conectar();
////            String Sql = "SELECT * FROM PERSONA WHERE TO_date(fechini,'dd/MM/yy') >= '10/10/2015' AND TO_date(fechfin,'dd/MM/yy') <= '10/10/2020' ";
//            String Sql = "SELECT CODPER,FECINIPER,FECFINPER FROM PERSONA";
//            PreparedStatement ps = this.getCn().prepareStatement(Sql);
//            rs = ps.executeQuery();
//            listaRang = new ArrayList<>();
//            PersonaM rang;
//            while (rs.next()) {
//                rang = new PersonaM();
//                rang.setCODPER(rs.getString("CODPER"));
//                rang.setFECINIPER(rs.getString("FECINIPER"));
//                rang.setFECFINPER(rs.getString("FECFINPER"));
////                rang.setTIPO_PROGRAMA(rs.getString("TIPO_PROGRAMA"));
////                rang.setCERTIFICADOS(rs.getString("CERTIFICADOS"));
////                rang.setPARTICIPANTES_INSCRITOS(rs.getString("PARTICIPANTES_INSCRITOS"));
////                rang.setTOTAL_PROGRAMA(rs.getString("TOTAL_PROGRAMA"));
//                listaRang.add(rang);
//            }
//            return listaRang;
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            this.Cerrar();
//        }
//    }
}
