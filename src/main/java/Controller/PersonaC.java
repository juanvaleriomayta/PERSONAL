package Controller;

import Dao.ImplPersonaD;
import Model.PersonaM;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Named(value = "personaC")
@SessionScoped
@Data
public class PersonaC implements Serializable {

    private PersonaM persona = new PersonaM();
    private List<PersonaM> listPersona;

    @PostConstruct
    public void init() {
        try {
            listarPersona();
        } catch (Exception e) {
        }
    }

    public void limpiarPersona() throws Exception {
        persona = new PersonaM();
    }

    public void registrarPersona() throws Exception {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            dao.registrarPersona(persona);
            listarPersona();
            limpiarPersona();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Persona", "Registrado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error al registrar"));
            throw e;
        }
    }

    public void listarPersona() throws Exception {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            listPersona = dao.listarPersona();
        } catch (Exception e) {
            throw e;
        }
    }

    /*REPORTE EN EXCEL*/
    public static String path = "C:\\Users\\jvale\\Downloads\\Participantes.xlsx";

    public static void main(String[] args) throws SQLException, Exception {
        PersonaReport reportePersona = new PersonaReport();
        ImplPersonaD dao = new ImplPersonaD();

        List<PersonaM> lista = dao.listarPersona();

        XSSFWorkbook excel = new XSSFWorkbook();
        XSSFSheet hoja1 = excel.createSheet();
        XSSFRow titulos = hoja1.createRow(0);
        titulos.createCell(0).setCellValue("DNI");
        titulos.createCell(1).setCellValue("NOMBRES Y APELLIDOS");
        titulos.createCell(2).setCellValue("CORREO ELECTRONICO");
        titulos.createCell(3).setCellValue("FECHA DE NACIMIENTO");
        int numrow = 0; //Nos posicionamos en la primera fila
        for (PersonaM model : lista) {
            numrow++; //Pasar a la siguiente Fila
            XSSFRow row = hoja1.createRow(numrow); //Creamos la fila para poder escribir en ella
            row.createCell(0).setCellValue(model.getDNIPER());
            row.createCell(1).setCellValue(model.getNOMAPEPER());
            row.createCell(2).setCellValue(model.getEMAPER());
            row.createCell(3).setCellValue(model.getFECNACPER());
        }
        try {
            FileOutputStream elFichero = new FileOutputStream(path);
            excel.write(elFichero);
            elFichero.close();
        } catch (IOException e) {
            System.out.println(e.getMessage() + e.getLocalizedMessage());
        }
//        reportePersona.reporteAlternancias(path, path);

    }
    
    public void reporteAlternancias(String CIs, String CodAula) throws Exception {
//        this.Conexion();

        //Metodo que crea y devuelve Excel
//        XSSFWorkbook libro = generarReporteAlternancias(CIs, CodAula);

        //Creamos un respuesta HTTP
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        //Le agregamos unas definiciones: Arhivo Adjunto(Descargar) , Nombre del Archivo
        response.addHeader("Content-disposition", "attachment; filename=ReporteAlternancia.xlsx");
        //Obtenemos la referencia al contenido de la respuesta HTTP
        ServletOutputStream stream = response.getOutputStream();

        //Escribimos el excel en el contenido de la respuesta HTTP
//        libro.write(stream);

        //Guardamos
        stream.flush();
        //Cerramos
        stream.close();
        //Devolvemos la respuesta HTTP construida al navegador
        FacesContext.getCurrentInstance().responseComplete();
    }
}
