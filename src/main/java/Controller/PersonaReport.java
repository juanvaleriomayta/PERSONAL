/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.PersonaM;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author jvale
 */
@Named(value = "personaReport")
public class PersonaReport implements Serializable {

//    public String path = "C:\\Users\\jvale\\Downloads\\Participantes.xlsx";

    public void generarReportePersona(List<PersonaM> lista) {
        XSSFWorkbook libro = new XSSFWorkbook();
        XSSFSheet hoja = libro.createSheet();
        int filas = 2;
        XSSFRow fila = hoja.createRow(filas);
        fila.createCell(1).setCellValue("DNI");
        fila.createCell(2).setCellValue("NOMBRES Y APELLIDOS");
        fila.createCell(3).setCellValue("CORREO ELECTRONICO");
        fila.createCell(4).setCellValue("FECHA DE NACIMIENTO");

        for (PersonaM model : lista) {
            filas++;
            fila = hoja.createRow(filas);
            fila.createCell(1).setCellValue(model.getDNIPER());
            fila.createCell(2).setCellValue(model.getNOMAPEPER());
            fila.createCell(3).setCellValue(model.getEMAPER());
            fila.createCell(4).setCellValue(model.getFECNACPER());
//            fila.createCell(5).setCellValue(model.getCampo5());
        }
        for (int i = 1; i < 10; i++) {
            hoja.autoSizeColumn(i);
        }
//        guardarReporte(libro);
    }

//    public void guardarReporte(XSSFWorkbook libro) {
//        try {
//            FileOutputStream elFichero = new FileOutputStream(path);
//            libro.write(elFichero);
//            elFichero.close();
//        } catch (IOException e) {
//            System.out.println(e.getMessage() + e.getLocalizedMessage());
//        }
//    }
    
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
