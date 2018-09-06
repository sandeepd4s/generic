package com.client.project.qa.at.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File f = new File(System.getProperty("user.dir")+"\\testdata\\NonMBRS.xlsx");
		FileInputStream fi = new FileInputStream(f);
		XSSFWorkbook workbook = new XSSFWorkbook(fi);
		Sheet sheet = workbook.getSheet("sheet1");
		Iterator<Row> rowIterator = sheet.iterator();
		Boolean headerRowFlag = false;
		String data;
		
		System.out.println("Max Columns:"+SpreadsheetVersion.EXCEL2007.getMaxColumns());
		System.out.println("Max Rows:"+SpreadsheetVersion.EXCEL2007.getMaxRows());
		
		while(rowIterator.hasNext()){
			Iterator<Cell> cellIterator = null;
				Row currentRow = rowIterator.next();
				cellIterator = currentRow.iterator();
			
			while(cellIterator.hasNext()){
				Cell currentCell = cellIterator.next();
				System.out.println("Cell Type:"+currentCell.getCellType());
				if(currentCell.getCellType()==0){
					double a = currentCell.getNumericCellValue();
					System.out.println("Numeric value: "+a);
				}
				if(currentCell.getCellType()==1){
					String a = currentCell.getStringCellValue();
					System.out.println("String value : "+a);
				}
				System.out.println("Column:"+currentCell.getColumnIndex());
				System.out.println("Row:"+currentCell.getRowIndex());
				
//				data =currentCell.getStringCellValue();
//				System.out.println(data);
			}
		}
		
		
		
	}

}
