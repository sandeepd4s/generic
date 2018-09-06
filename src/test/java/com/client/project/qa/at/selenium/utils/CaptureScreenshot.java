package com.client.project.qa.at.selenium.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.client.project.qa.at.common.util.customizedreport.ManageReport;

public class CaptureScreenshot {

	public static String get(String screenshotName, WebDriver driver, String dateStr) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File screenshot = ts.getScreenshotAs(OutputType.FILE);
			String screeshotFile = System.getProperty("user.dir") + "\\reports\\reports_"+ManageReport.getTimeStamp()+"\\screenshots\\" + screenshotName
					+ dateStr + ".png";
			File screenshotFile = new File(screeshotFile);
			FileUtils.copyFile(screenshot, screenshotFile);
			System.out.println("Screenshot captured: " + screenshotName);
			return screeshotFile;
		} catch (Exception e) {
			System.out.println("CaptureScreenshot error" + e.getMessage());
			return e.getMessage();
		}
	}
}