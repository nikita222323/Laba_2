package ru.mephi.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SheetWriter {
    public static void write(Map<String, List<?>> allResults, List<String> labels, String filename) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Статистика без ковариации.");
            int rowNum = 1;
            writeListToSheet(sheet, "", 0, labels);

            for (Map.Entry<String, List<?>> entry : allResults.entrySet()) {
                String header = entry.getKey();
                List<?> values = entry.getValue();
                if (header != "Ковариация"){
                    writeListToSheet(sheet, header, rowNum++, values);}
            }

            for (int i = 0; i < allResults.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            Sheet covarianceSheet = workbook.createSheet("Ковариация");
            writeCovarianceMatrix(covarianceSheet, (List<List<Double>>) allResults.get("Ковариация"), labels);

            String pathToDesktop = System.getProperty("user.home") + "/Desktop";
            File desktopDir = new File(pathToDesktop);

            try (FileOutputStream fileOut = new FileOutputStream(new File(pathToDesktop, filename + ".xlsx"))) {
                workbook.write(fileOut);
            } catch (IOException e) {
                throw new IOException("Ошибка при сохранении", e);
            }
        } catch (IOException e) {
            throw new IOException("Ошибка при экспорте", e);
        }
    }


    private static void writeListToSheet(Sheet sheet, String header, int rowNum, List<?> values) {
        Row row = sheet.createRow(rowNum);
        CellStyle style = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        style.setFont(font);
        row.createCell(0).setCellValue(header);
        row.getCell(0).setCellStyle(style);
        int cellNum = 1;
        for (Object value : values) {
            Cell cell = row.createCell(cellNum++);
            if (value instanceof Double) {
                cell.setCellValue((Double) value);
            } else if (value instanceof String) {
                cell.setCellValue((String) value);
            } else if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            }
        }

        CellStyle centerAlignStyle = sheet.getWorkbook().createCellStyle();
        centerAlignStyle.setAlignment(HorizontalAlignment.CENTER);
        for (int i = 1; i <= values.size(); i++) {
            row.getCell(i).setCellStyle(centerAlignStyle);
        }
    }

    private static void writeCovarianceMatrix(Sheet sheet, List<List<Double>> covarianceMatrix, List<String> labels) {
        int rowNum = 0;
        int startColumn = 1;
        Row labelsRow = sheet.createRow(rowNum++);
        for (int i = 1; i < labels.size() + 1; i++) {
            labelsRow.createCell(i).setCellValue(labels.get(i - 1));
        }
        for (int i = 0; i < labels.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(labels.get(i));
        }
        rowNum = 1;
        for (List<Double> row : covarianceMatrix) {
            Row currentRow = sheet.getRow(rowNum);
            if (currentRow == null) {
                currentRow = sheet.createRow(rowNum);
            }
            int cellNum = startColumn;
            for (Double value : row) {
                Cell cell = currentRow.createCell(cellNum++);
                cell.setCellValue(value);
            }
            rowNum++;
        }
    }
}