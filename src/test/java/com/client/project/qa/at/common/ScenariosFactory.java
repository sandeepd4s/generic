package com.client.project.qa.at.common;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ScenariosFactory {
	
	private String dataResourcePath = System.getProperty("user.dir")+GetProperties.getConfigPropety( "dataResourcePath" );
	public static ArrayList<HashMap<String, Object>> finalData = new ArrayList<HashMap<String, Object>>();
	
	public Iterator<Object[]> getTestDataSet(String fileName, String method) {
		String filePath = this.dataResourcePath+"//"+fileName;
		return this.xlsxFileReader(filePath, method.trim()).iterator();
	}
	
	public List<Object[]> xlsxFileReader(String filePath, String workSheetName) {
		List<Object[]> result = new ArrayList<Object[]>();
		boolean firstRowFlag = true;
		HashMap<String, Object> rec = new HashMap();
		
		try {
			FileInputStream file = new FileInputStream(new File(filePath));
			//Create Workbook instance holding reference to .xlsx file
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheet(workSheetName);
			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			
			int rowc = 0;
			ArrayList<String> head = new ArrayList<String>();
			
			while (rowIterator.hasNext())
	        {
//				if (firstRowFlag) {
//					firstRowFlag = false;
//					rowIterator.next();
//				}
				
	            Row row = rowIterator.next();
	            rowc++;
	            //System.out.println("*****"+row.getPhysicalNumberOfCells());
	            //For each row, iterate through all the columns
	            Iterator<Cell> cellIterator = row.cellIterator();
	            List<Object> rowItems = new ArrayList<Object>();
	            int i = 0,j=0;
	            while (cellIterator.hasNext()) 
	            {
	                Cell cell = cellIterator.next();
	                int cellType = cell.getCellType();	                
	                //Check the cell type and format accordingly
	                switch (cellType) 
	                {
	                	case Cell.CELL_TYPE_FORMULA:
	                		rec.put(head.get(j), handleString(cell));
	                		rowItems.add(handleString(cell));
	                		j++;
	                        break;
	                	case Cell.CELL_TYPE_BOOLEAN:
	                		rec.put(head.get(j), cell.getBooleanCellValue());
	                		rowItems.add(cell.getBooleanCellValue());
	                		j++;
	                        break;
	                    case Cell.CELL_TYPE_NUMERIC:
	                    	Double d = cell.getNumericCellValue();
	                    	BigDecimal bd = new BigDecimal(d.toString());
	                    	Long l = bd.longValue();
	                    	rec.put(head.get(j), l);
	                    	rowItems.add(l);
			                //rowItems.add(cell.getNumericCellValue());
			                j++;
		                    break;
	                    case Cell.CELL_TYPE_BLANK:
	                    	rec.put(head.get(j), handleString(cell));
	                    	rowItems.add("");
	                    	j++;
	                    	break;
	                    case Cell.CELL_TYPE_STRING:
	                    	if(firstRowFlag){
	                    		//ArrayList<String> head = new ArrayList<String>();
                    			head.add(handleString(cell));
                    			i++;
	                    	}else{
	                    		rec.put(head.get(j), handleString(cell));
	                    		//System.out.println("*********Rec in string"+rec..);//+rec.get(head.get(j)));
	                    		rowItems.add(handleString(cell));
	                    		j++;
	                    	}	                    	
	                        break;
	                }
	            }
	            //System.out.println("header"+head);
	            firstRowFlag=false;
	            Map m = new HashMap();
	            Set s = rec.entrySet();
	            Iterator itr = s.iterator();
	            while(itr.hasNext()){
	            	Map.Entry entry = (Map.Entry) itr.next();
	            	//System.out.println("Key :"+entry.getKey() +"*****"+entry.getValue());
	            }
	            
	            if(rec.size()!=0){
	            	finalData.add(rec);
	            }
	            if(rowItems.size()!=0)
	            	result.add(rowItems.toArray());
	        }
	        file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("*********Rec"+rec);
		//System.out.println("Final Data:"+finalData);
		firstRowFlag = true;
		return cleanDataSetOfEmptyObjects(result);		
	}
	
	private List<Object[]> cleanDataSetOfEmptyObjects(List<Object[]> resultSet) {
		for (int i=resultSet.size();i>0;i--) {
			if (resultSet.get(i-1).length == 0) {
				resultSet.remove(i-1);
			}
		}
		return resultSet;
	}
	
	public String handleString(Cell cell) {
		String handledString = "";
		if (!cell.getStringCellValue().equals("")) {
			handledString = cell.getStringCellValue();
		}
		return handledString;
	}
	
	public ArrayList<HashMap<String, Object>> getNonMemberData(){
		finalData = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> finalData1 = new ArrayList<HashMap<String, Object>>();
		String nonMember[] = ConfigReader.getConfigProps().get("nonMemberData").split("\\-");
		String dataFile_NonMember = nonMember[0].trim();
		String sheetName_NonMember = nonMember[1].trim();
		
		getTestDataSet(dataFile_NonMember, sheetName_NonMember);
		Map<String, Object> a = new HashMap<String, Object>();
		
		
		for(HashMap<String, Object> m : finalData){
			//System.out.println("^^^Keys^^^^"+m.keySet());
			//System.out.println("^^^Values^^^^"+m.values());
			a =	m.entrySet().stream()
				.filter(map -> map.getKey() == "STATUS" && map.getValue()=="Not Found")
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue())); //x->"Not Found".equals(x.getClass().getName()))	;	}
		}
		finalData1.add((HashMap<String, Object>) a);
		return finalData1;
		
	}
	
	public ArrayList<HashMap<String, Object>> getMemberData(){
		finalData = new ArrayList<HashMap<String, Object>>();
		String member[] = ConfigReader.getConfigProps().get("memberData").split("\\-");
		String dataFile_Member = member[0].trim();
		String sheetName_Member = member[1].trim();
		
		getTestDataSet(dataFile_Member, sheetName_Member);
		return finalData;
		
	}
}
