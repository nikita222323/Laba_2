package ru.mephi.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SheetReader {

    public Map<String, List<Double>> readFromExcel(String file, String name) throws IOException {
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet myExcelSheet = myExcelBook.getSheet(name);
        if (myExcelSheet == null) {
            throw new IllegalArgumentException("Лист с таким названием не найден в книге Excel.");
        }
        int lastRowNum = myExcelSheet.getLastRowNum();
        Map<String, List<Double>> result = new HashMap<>();
        Row labelsRow = myExcelSheet.getRow(0);
        int totalColumns = labelsRow.getLastCellNum();
        for (int columnIndex = 0; columnIndex < totalColumns; columnIndex++) {
            String label = labelsRow.getCell(columnIndex).getStringCellValue();
            List<Double> columnData = new ArrayList<>();
            for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
                Row row = myExcelSheet.getRow(rowIndex);
                if (row != null && row.getCell(columnIndex) != null) {
                    double cellValue = row.getCell(columnIndex).getNumericCellValue();
                    columnData.add(cellValue);
                }
            }
            result.put(label, columnData);
        }
        return result;
    }

    public static List<String> getSheetNames(String filePath) throws IOException {
        List<String> sheetNames = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook myExcelBook = new XSSFWorkbook(fis)) {

            int numberOfSheets = myExcelBook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                sheetNames.add(myExcelBook.getSheetName(i));
            }
        }

        return sheetNames;
    }
}