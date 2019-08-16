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
            String sql = "INSERT INTO PERSONA (DNIPER,NOMAPEPER,EMAPER,FECNACPER) VALUES (?,?,?,STR_TO_DATE(?,'%d/%m/%Y'))";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, per.getDNIPER());
            ps.setString(2, per.getNOMAPEPER());
            ps.setString(3, per.getEMAPER());
            ps.setString(4, per.getFECNACPER());
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
            String sql = "SELECT CODPER,DNIPER,NOMAPEPER,EMAPER,date_FORMAT(FECNACPER,'%d/%m/%Y') AS FECNACPER FROM PERSONA WHERE EMAPER REGEXP ('^[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,6}$')";
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
                lista.add(listPer);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            Cerrar();
        }
        return lista;
    }

}
