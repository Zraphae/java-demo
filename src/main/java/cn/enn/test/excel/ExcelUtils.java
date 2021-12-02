package cn.enn.test.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@Slf4j
public class ExcelUtils {

    private XSSFSheet sheet;

    ExcelUtils(String filePath, String sheetName) {
        try {
            XSSFWorkbook sheets = new XSSFWorkbook(filePath);
            //获取sheet
            this.sheet = sheets.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Cell getExcelDateByIndex(int rowNum, int columnNum) {
        XSSFRow row = this.sheet.getRow(rowNum);
        Cell column = row.getCell(columnNum);
        return column;
    }

    public void readExcelData(boolean skipHeader) {

        //获取行数
        int rows = this.sheet.getPhysicalNumberOfRows();

        for (int i = 0; i < rows; i++) {
            //获取列数
            XSSFRow row = this.sheet.getRow(i);
            int columns = row.getPhysicalNumberOfCells();
            for (int j = 0; j < columns; j++) {
                String cell = row.getCell(j).toString();
                System.out.println(cell);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        ExcelUtils excelUtils = new ExcelUtils("/Users/zhaopeng/Documents/numbers/test.xlsx", "data");

        Iterator<Row> iterator = excelUtils.sheet.iterator();
        while(iterator.hasNext()){
            Row row = iterator.next();
            Cell database = row.getCell(3);
//            log.info("----->database: {}", database);
            Cell type = row.getCell(7);
//            log.info("----->type: {}", type);
            Cell tableName = row.getCell(4);
//            log.info("----->tableName: {}", tableName);
            Cell colName = row.getCell(5);
//            log.info("----->colName: {}", colName);
            Cell colType = row.getCell(6);
//            log.info("----->colType: {}", colType);

            File file = new File("/Users/zhaopeng/Desktop/test.sql");
            if(StringUtils.equals(type.getStringCellValue(),"表")){
                String sql = "ALTER TABLE " +  database + "." + tableName + " SET TBLPROPERTIES ('comment' = '" + tableName + "');" + "\n";
                log.info("sql: {}", sql);
                FileUtils.writeStringToFile(file, sql, true);
            }
            if(StringUtils.equals(type.getStringCellValue(),"字段")){
                String sql = "alter table " + database + "." + tableName + " change column " + colName + " " + colName + " " + colType + " COMMENT '" + colName + "');" + "\n";
                log.info("sql: {}", sql);
                FileUtils.writeStringToFile(file, sql, true);
            }

        }

    }
}
