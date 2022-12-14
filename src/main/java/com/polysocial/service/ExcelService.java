package com.polysocial.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.polysocial.entity.Book;

public class ExcelService {
	public static final int COLUMN_INDEX_STUDENTCODE = 1;
	public static final int COLUMN_INDEX_FULLNAME = 0;
	public static final int COLUMN_INDEX_EMAIL = 2;

	public List<Book> readExcel(String excelFilePath) throws IOException {
		List<Book> listBooks = new ArrayList<>();

		// Get file
		InputStream inputStream = new FileInputStream(new File(excelFilePath));

		// Get workbook
		Workbook workbook = getWorkbook(inputStream, excelFilePath);
	
		// Get sheet
		
		Sheet sheet = workbook.getSheetAt(0);
		// Get all rows
		Iterator<Row> iterator = sheet.iterator();
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			if (nextRow.getRowNum() == 0) {
				// Ignore header
				continue;
			}

			// Get all cells
			Iterator<Cell> cellIterator = nextRow.cellIterator();

			// Read cells and set value for book object
			Book book = new Book();
			while (cellIterator.hasNext()) {
				// Read cell
				Cell cell = cellIterator.next();
				Object cellValue = getCellValue(cell);
				if (cellValue == null || cellValue.toString().isEmpty()) {
					continue;
				}
				// Set value for book object
				int columnIndex = cell.getColumnIndex();
				switch (columnIndex) {
				case COLUMN_INDEX_STUDENTCODE:
					book.setMaSV((String) getCellValue(cell));
					break;
				case COLUMN_INDEX_FULLNAME:
					book.setHoTen((String) getCellValue(cell));
					break;
				case COLUMN_INDEX_EMAIL:
					book.setEmail((String) getCellValue(cell));
					break;
				default:
					break;
				}

			}
			listBooks.add(book);
		}

		workbook.close();
		inputStream.close();

		return listBooks;
	}

	// Get Workbook
	private Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
		Workbook workbook = null;
		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}

		return workbook;
	}

	// Get cell value
	private Object getCellValue(Cell cell) {
		CellType cellType = cell.getCellTypeEnum();
		Object cellValue = null;
		switch (cellType) {
		case STRING:
			cellValue = cell.getStringCellValue();
			break;
		case _NONE:
		case BLANK:
		case ERROR:
			break;
		default:
			break;
		}

		return cellValue;
	}
}