package modelos;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Excel {

    public static void crearExcel() {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Presupuesto");
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Hola Mundo");

        try {
            FileOutputStream fileout = new FileOutputStream("presupuesto.xlsx");
            book.write(fileout);
            fileout.close();

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
