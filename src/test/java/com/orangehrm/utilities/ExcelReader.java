package com.orangehrm.utilities;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    Workbook workbook;
    Sheet sheet;

    public ExcelReader(String excelPath) throws IOException {

        FileInputStream file =
                new FileInputStream(excelPath);

        workbook = WorkbookFactory.create(file);
    }

    // Get Total Rows
    public int getRowCount(String sheetName) {

        sheet = workbook.getSheet(sheetName);

        return sheet.getLastRowNum() + 1;
    }

    // Get Total Columns
    public int getColumnCount(String sheetName) {

        sheet = workbook.getSheet(sheetName);

        Row row = sheet.getRow(0);

        return row.getLastCellNum();
    }

    // Get Cell Data
//    public String getCellData(String sheetName,
//                              int colNum,
//                              int rowNum) {
//
//        sheet = workbook.getSheet(sheetName);
//
//        Row row = sheet.getRow(rowNum - 1);
//
//        Cell cell = row.getCell(colNum);
//
//        DataFormatter formatter =
//                new DataFormatter();
//
//        return formatter.formatCellValue(cell);
//    }

    public String getCellData(String sheetName,
                              int colNum,
                              int rowNum) {

        sheet = workbook.getSheet(sheetName);

        if(sheet == null) {
            return "";
        }

        Row row = sheet.getRow(rowNum - 1);

        if(row == null) {
            return "";
        }

        Cell cell = row.getCell(colNum);

        if(cell == null) {
            return "";
        }

        DataFormatter formatter = new DataFormatter();

        return formatter.formatCellValue(cell).trim();
    }


    public String getCellData(String sheetName,
                              String colName,
                              int rowNum) {

        sheet = workbook.getSheet(sheetName);

        Row headerRow = sheet.getRow(0);

        int colNum = -1;

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {

            if (headerRow.getCell(i).getStringCellValue()
                    .trim()
                    .equalsIgnoreCase(colName)) {

                colNum = i;
                break;
            }
        }

        if (colNum == -1) {
            return "";
        }

        Row row = sheet.getRow(rowNum - 1);

        if (row == null) {
            return "";
        }

        Cell cell = row.getCell(colNum);

        if (cell == null) {
            return "";
        }

        DataFormatter formatter = new DataFormatter();

        return formatter.formatCellValue(cell);
    }
}