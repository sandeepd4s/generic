package com.client.project.qa.at.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataFactory {
	
	private String dataResourcePath = System.getProperty("user.dir")+GetProperties.getConfigPropety( "dataResourcePath" );
	public List<Object> header = new ArrayList<Object>();
	
	
	/***
	 * This method is to read the test data from mentioned file name and given path
	 * @author SandeepReddyD
	 * @param fileName - Name of the file name, from which you want to read the data
	 * @param method 
	 * @return
	 */
	public List<Object> getTestDataSet(String fileName, String method) {
		String filePath = this.dataResourcePath+"//"+fileName;
		return this.xlsxDataReader(filePath, method.trim());
	}
	
	/***
	 * This method is to get the sheet object for the given xslx file and sheet
	 * @author SandeepReddyD
	 * @param filePath 
	 * @param workSheetName
	 * @return Sheet object for the given xslx and sheet
	 * @throws IOException
	 */
	public Sheet xlsxGetSheet(String filePath, String workSheetName) throws IOException{
		FileInputStream file = new FileInputStream(new File(filePath));

		//Create Workbook instance holding reference to .xlsx file
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		//Get first/desired sheet from the workbook
		return  workbook.getSheet(workSheetName);
	}
	
	/***
	 * This method is to get the test data for given flename and sheet - If user do not provide row number
	 * This method can also fetch you specified row data - If user gives specific roen number
	 * @param filePath
	 * @param workSheetName
	 * @param rowNum
	 * @return List<Object>
	 */
	public List<Object> xlsxDataReader(String filePath, String workSheetName, int... rowNum){
		List<Object> result = new ArrayList<Object>();
		try {
			Sheet sheet = xlsxGetSheet(filePath, workSheetName);
			
			//Iterate through each rows one by one
			 Row row =sheet.getRow(rowNum[0]);
			 Iterator<Cell> cellIterator = row.cellIterator();
			 while(cellIterator.hasNext()){
				 Cell cell = cellIterator.next();
				 int cellType = cell.getCellType();	                
	                //Check the cell type and format accordingly
	                switch (cellType) 
	                {
	                	case Cell.CELL_TYPE_FORMULA:
	                		result.add(handleString(cell));
	                        break;
	                	case Cell.CELL_TYPE_BOOLEAN:
	                		result.add(cell.getBooleanCellValue());
	                        break;
	                    case Cell.CELL_TYPE_NUMERIC:
	                    	Double d = cell.getNumericCellValue();
	                    	BigDecimal bd = new BigDecimal(d.toString());
	                    	Long l = bd.longValue();
	                    	result.add(l);
		                    break;
	                    case Cell.CELL_TYPE_BLANK:
	                    	result.add("");
	                    	break;
	                    case Cell.CELL_TYPE_STRING:
	                    	result.add(handleString(cell));
	                        break;
	                }
	            }
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	private List<Object[]> cleanDataSetOfEmptyObjects(List<Object[]> resultSet) {
		for (int i=resultSet.size();i>0;i--) {
			if (resultSet.get(i-1).length == 0) {
				resultSet.remove(i-1);
			}
		}
		return resultSet;
	}
	
	/***
	 * This method is to get String value for a specific cell
	 * @param cell
	 * @return
	 */
	public String handleString(Cell cell) {
		String handledString = "";
		if (!cell.getStringCellValue().equals("")) {
			handledString = cell.getStringCellValue();
		}
		return handledString;
	}
	
	/***
	 * 
	 * @param rowNum
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> getNonMemberData(int rowNum) throws IOException{
		String nonMember[] = ConfigReader.getConfigProps().get("nonMemberData").split("\\-");
		String dataFile_NonMember = nonMember[0].trim();
		String sheetName_NonMember = nonMember[1].trim();
		
		if(header.size()==0){
				header=xlsxDataReader(dataResourcePath+dataFile_NonMember, sheetName_NonMember, 0);
		}
		Sheet sheet = xlsxGetSheet(dataResourcePath+dataFile_NonMember, sheetName_NonMember);
		List<Object> data = null ;
		Integer columns = 0;
		System.out.println("No.of rows:"+sheet.getPhysicalNumberOfRows());
		while(sheet.getPhysicalNumberOfRows()>rowNum){
			data = xlsxDataReader(dataResourcePath+dataFile_NonMember, sheetName_NonMember, rowNum);

			String classifier = (String) data.get(data.size()-1);;
			System.out.println("Clasifier:::"+classifier);
			
			System.out.println("Row Number "+rowNum+" Classified as :"+classifier);
			if(classifier.equalsIgnoreCase("Not_Found")) {
				GetProperties.updateConfigProperty("nonMemberNewBusinessSheetRow", String.valueOf(rowNum));
				break;
			}
			++rowNum;
		}							
		
		Map<String, String> nonMemberData = new LinkedHashMap<String, String>();
		System.out.println("Header size::"+header.size());
		System.out.println("data size::"+data.size());
		
		if(header.size()==data.size()){
			for(int i=0;i<header.size();++i){
				nonMemberData.put(header.get(i).toString(), data.get(i).toString());
			}
		}
		return nonMemberData;
	}
	
	public Map<String, String> getMemberData(int rowNum){
		String nonMember[] = ConfigReader.getConfigProps().get("memberData").split("\\-");
		String dataFile_NonMember = nonMember[0].trim();
		String sheetName_NonMember = nonMember[1].trim();
		
		List<Object> header = xlsxDataReader(dataResourcePath+dataFile_NonMember, sheetName_NonMember, 0);
		List<Object> data = xlsxDataReader(dataResourcePath+dataFile_NonMember, sheetName_NonMember, Integer.valueOf(GetProperties.getConfigPropety("memberSheetRow")));
		Map<String, String> memberData = new LinkedHashMap<String, String>();
		
		if(header.size()==data.size()){
			for(int i=0;i<header.size();++i){
				memberData.put(header.get(i).toString(), data.get(i).toString());
			}
		}
		return memberData;
	}
		
//		getTestDataSet(dataFile_NonMember, sheetName_NonMember);
//		Map<String, Object> a = new HashMap<String, Object>();
//		for(HashMap<String, Object> m : finalData){
//			//System.out.println("^^^Keys^^^^"+m.keySet());
//			//System.out.println("^^^Values^^^^"+m.values());
//			a =	m.entrySet().stream()
//				.filter(map -> map.getKey() == "STATUS" && map.getValue()=="Not Found")
//				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue())); //x->"Not Found".equals(x.getClass().getName()))	;	}
//		}
//		finalData1.add((HashMap<String, Object>) a);
		
		
	
	
}
