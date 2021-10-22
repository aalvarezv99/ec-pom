package CommonFuntions;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadExcel {
    /**
     * Clase para leer datos de un excel
     */
    public void readExcel(String filepath, String sheetname) throws IOException {

        File file = new File(filepath);

        FileInputStream inputStream = new FileInputStream(file);

        XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);

        XSSFSheet newSheet = newWorkbook.getSheet(sheetname);

        int rowCount = newSheet.getLastRowNum() - newSheet.getFirstRowNum();

        for (int i = 0; i <= rowCount; i++) {
            XSSFRow row = newSheet.getRow(i);
            for (int j = 0; j <= row.getLastCellNum(); j++) {
                System.out.println(row.getCell(j).getStringCellValue() + "||");
            }
        }
    }
}
