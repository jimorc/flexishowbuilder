package com.github.jimorc.flexishowbuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * XLSWorkbook generates an Apache POI workbook that can then be saved as an XLS file.
 */
public final class XLSWorkbook {
    private Workbook workbook;

    /**
     * Constructor builds a POI workbook from the input CSV object.
     * @param csv the OutputCSV object used to create the workbook content.
     * */
    public XLSWorkbook(final OutputCSV csv) {
        workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("CSVToXLS");
        CSVLine[] lines = csv.getLines();
        int rowIndex = 0;
        for (CSVLine line: lines) {
            Row row = sheet.createRow(rowIndex++);
            int colNum = 0;
            switch (line) {
                case TitleImageLine l:
                    Cell cell0 = row.createCell(colNum);
                    cell0.setCellValue(l.field(0));
                    break;
                case ImageAndPersonLine ipl:
                    Cell iCell0 = row.createCell(colNum++);
                    iCell0.setCellValue(ipl.getImageFileName());
                    Cell iCell1 = row.createCell(colNum++);
                    iCell1.setCellValue(ipl.getImageTitle());
                    Cell iCell2 = row.createCell(colNum++);
                    iCell2.setCellValue(ipl.getPersonFullName());
                    Cell iCell3 = row.createCell(colNum++);
                    iCell3.setCellValue(ipl.getPersonFirstName());
                    Cell iCell4 = row.createCell(colNum++);
                    iCell4.setCellValue(ipl.getPersonLastName());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Write the workbook to the specified XLS file.
     * @param fileName the file to write the XLS data to.
     * @throws IOException thrown when the file write fails.
     */
    public void writeToFile(String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        workbook.close();
        BuilderGUI.LOG.debug("output.xls written successfully");
    }
}
