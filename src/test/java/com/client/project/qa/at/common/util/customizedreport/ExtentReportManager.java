package com.client.project.qa.at.common.util.customizedreport;

import java.util.HashMap;

import com.client.project.qa.at.common.ConfigReader;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentReportManager {
	private static ExtentReports eReport;
	private static ConfigReader readPropsFile;
	private static HashMap<String, String> map;

	public synchronized static ExtentReports getReporter(String filePath) {
		readPropsFile = new ConfigReader();
		map = readPropsFile.getConfigProps();
		if (eReport == null) {
			eReport = new ExtentReports(filePath, false);
			eReport.addSystemInfo("UserName : ", System.getProperty("user.name"))
//					.addSystemInfo("Environment : ", map.get("Envionment"))
					.addSystemInfo("Device MCD : ", map.get("Browser").toString());
		}
		return eReport;
	}
}
