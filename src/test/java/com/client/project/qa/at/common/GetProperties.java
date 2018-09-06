package com.client.project.qa.at.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.sun.jna.platform.win32.OaIdl.SAFEARRAYBOUND;

public class GetProperties {
	private static FileInputStream file;
	private static FileOutputStream fo;
	private static DataFactory dataFactory = new DataFactory();
	private static Properties prop = new Properties();
	
	
	/**Method Name Specifies the file name, from which we are getting the property 
	 * argument : property, which should be exist in the config properties file
	 * @author SandeepReddyD
	 * Date : 25/05/2018
	 */
	public static String getConfigPropety(String property) {
		String val="";
		prop = new Properties();
		try{
			file = new FileInputStream(System.getProperty("user.dir")+"\\config.properties");
			prop.load(file);
			val =prop.getProperty(property);
			file.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return val;
	}
	
	
	/***
	 * Method Name Specifies the file name, from which we are setting the specified property
	 * argument : property, which should be exist in the config properties file
	 * @author SandeepReddyD
	 * Date : 13/06/2018
	 */
	public static void setConfigProperty(String property, String value){
		try{
			file = new FileInputStream(System.getProperty("user.dir")+"\\config.properties");
			prop.load(file);
			file.close();
			
			fo = new FileOutputStream(System.getProperty("user.dir")+"\\config.properties");
			prop.setProperty(property, value);
			prop.store(fo, null);
			fo.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/***
	 * Method Name Specifies the file name, from which we are update the specified property
	 * argument : property, which should be exist in the config properties file
	 * @author SandeepReddyD
	 * Date : 13/06/2018
	 */
	public static void updateConfigProperty(String property, String value){
		String propValue="";
		try{
			file = new FileInputStream(System.getProperty("user.dir")+"\\config.properties");
			prop.load(file);
			propValue = prop.getProperty(property);
			file.close();
			propValue = propValue+" "+value;
			fo = new FileOutputStream(System.getProperty("user.dir")+"\\config.properties");
			prop.setProperty(property, propValue);
			prop.store(fo, null);
			fo.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws IOException *
	 * 
	 */
	public static Map<String, String> getNonMbrSheetRowUpdateNBusRowNum() throws IOException{
		  int rowNum = Integer.valueOf(GetProperties.getConfigPropety("nonMemberSheetRow"));
		  System.out.println("********NonMember Row*****"+rowNum);
		  Map<String, String> nonMemberData = dataFactory.getNonMemberData(rowNum);
		  System.out.println(nonMemberData);
		  GetProperties.setConfigProperty("nonMemberSheetRow", String.valueOf(++rowNum));
		  //GetProperties.updateConfigProperty("nonMemberNewBusinessSheetRow", String.valueOf(rowNum));
		return nonMemberData;
	}
	
	
	///****************************************
	//Method Name Specifies the file name, from which we are getting the property 
	//This method is to read properties from product property file
	// argument : property, which should be exist in the config properties file
	// Written  By : Sandeep Reddy Daida
	// Date : 25/05/2018
	///******************************************
	
	public static String getProductPropety(String property) {
		try{
			file = new FileInputStream(System.getProperty("user.dir")+"\\product.properties");
			prop.load(file);
		}catch(Exception e){
			e.printStackTrace();
		}
		return prop.getProperty(property);
	}
	
	///****************************************
	//Method Name Specifies the file name, from which we are getting the property 
	//This method is to read properties from product property file
	// argument : property, which should be exist in the online properties file
	// Written  By : Sandeep Reddy Daida
	// Date : 25/05/2018
	///******************************************
	
	public static String getOnlinePropety(String property) {
		try{
			file = new FileInputStream(System.getProperty("user.dir")+"\\online.properties");
			prop.load(file);
		}catch(Exception e){
			e.printStackTrace();
		}
		return prop.getProperty(property);
	}
}
