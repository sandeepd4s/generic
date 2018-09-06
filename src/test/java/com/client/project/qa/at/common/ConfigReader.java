package com.client.project.qa.at.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

//import com.firsttechfed.omnichannel.qa.at.selenium.utils.ExecVariable;

public class ConfigReader {
	
	/**
	 * @author SandeepReddyD
	 * @categor get the all properties from config.properties in HashMap
	 */
	
	
	public static HashMap<String, String> getConfigProps() {
		Properties prop = new Properties();
		InputStream input = null;
		HashMap<String, String> confMap = new HashMap();

		try {

			input = new FileInputStream("config.properties");
			if (input == null) {
				System.out.println("Sorry, unable to find Config File");
			}

			prop.load(input);

			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				// System.out.println("Key : " + key + ", Value : " + value);

				// populate hash map
				confMap.put(key, value);
				// System.out.println("hashmap details is : " + hmap);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return confMap;
		}
	}
	
	
	/**
	 * @author SandeepReddyD
	 * @category Execution List of HashMaps (Execution properties from config.properties)
	 */
	public static ArrayList<HashMap<String, String>> execList = new ArrayList();
	
	public static HashMap<String, String> envMap = null;
	
	/**
	 * This method is to return List of HashMaps, Each HashMap contains each property for a specific product execution.
	 * @author SandeepReddyD
	 * @return ArrayList<HashMap>
	 */
//	public static ArrayList<HashMap<String, String>> getExecutionReader(){
//		//execution is a key in config.properties, which contains all the execution properties for each execution.
//		//String execution = GetProperties.getConfigPropety("execution");
//		HashMap<String, String> confMap = getConfigProps();
//		String executionString = confMap.get("execution");
//		
//		//, is a delemitor for form submission.
//		
//		String e[] = executionString.split(ExecVariable.scenDelem);
//		
//		for(int i=0;i<e.length;i++){
//			  String exe[] =e[0].split(ExecVariable.appDelem);
//			  //String env = exe[0];
//			  //System.out.println(env);
//			  String login = exe[0];
//			  String memType = exe[1];
//			  String method = exe[2];
//			  String appType = exe[3];
//			  String productGroup = exe[4];
//			  String product = exe[5];
//			  String applicants = exe[6];
//			  
//			  envMap = new HashMap<String, String>();
//			  
//			  //envMap.put(ExecVariable.env, env);
//			  envMap.put(ExecVariable.login, login);
//			  envMap.put(ExecVariable.memType, memType);
//			  envMap.put(ExecVariable.method, method);
//			  envMap.put(ExecVariable.appType, appType);
//			  envMap.put(ExecVariable.productGroup, productGroup);
//			  envMap.put(ExecVariable.product, product);
//			  envMap.put(ExecVariable.applicants, applicants);
//			  
//			  execList.add(envMap);
//			  envMap=null;
//		}
//		return execList;
//	}
	
}
