package com.DBToExcel.DBToExcel.Service;

import com.DBToExcel.DBToExcel.Model.Details;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class ExcelGeneratorClass {

    private List< Details > studentList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGeneratorClass(List<Details> listOfStudents) {
        this.studentList = listOfStudents;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("Student");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Student Name", style);
        createCell(row, 2, "Email", style);
        createCell(row, 3, "Mobile No.", style);
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Details record: studentList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getStudentName(), style);
            createCell(row, columnCount++, record.getEmail(), style);
            createCell(row, columnCount++, record.getMobileNo(), style);
        }
        addTotalRow(rowCount);

    }

    private void addTotalRow(int rowCount) {
        // Create a new row for totals
        Row totalRow = sheet.createRow(rowCount);

        // Create cells for ID, Student Name, Email, and Mobile No.
        for (int i = 0; i < 4; i++) {
            createCell(totalRow, i, "", totalRow.getSheet().getRow(0).getCell(i).getCellStyle());
        }

        // Calculate and write the total for the Mobile No. column
        calculateAndWriteTotal(totalRow);
    }

    private void calculateAndWriteTotal(Row totalRow) {
        int totalColumnIndex = 3; // Index of the Mobile No. column
        Cell totalCell = totalRow.getCell(totalColumnIndex);

        for (int i = 1; i < totalRow.getRowNum(); i++) {
            Row row = sheet.getRow(i);
            // Calculate the sum of mobile numbers for each student
            String mobileNo = ( row.getCell(totalColumnIndex).getStringCellValue());
            totalCell.setCellValue(totalCell.getStringCellValue() + mobileNo);
        }

        // Set the label for the total row
        createCell(totalRow, totalColumnIndex - 1, "Total", totalRow.getCell(totalColumnIndex).getCellStyle());
    }
    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
