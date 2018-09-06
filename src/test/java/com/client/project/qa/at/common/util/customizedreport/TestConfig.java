package com.client.project.qa.at.common.util.customizedreport;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.client.project.qa.at.common.ConfigReader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public abstract class TestConfig extends ManageReport {
	protected static ExtentReports eReport;
	protected ExtentTest eTest;
	protected WebDriver driver;
	public static String dateStr;
	ConfigReader configReader;

	@AfterMethod
	protected void afterMethod(ITestResult result) {
		System.out.println("After method in TestConfig");
		if (result.getStatus() == ITestResult.FAILURE) {
			File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(src, new File(System.getProperty("user.dir")+"\\reports\\screenshots"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//			String screenshotPath = CaptureScreenshot.get(result.getName(), driver, dateStr);
			//			System.out.println("screenshotPath.................." + screenshotPath);
			// String image = eTest.addScreenCapture(screenshotPath);
			// eTest.log(LogStatus.FAIL, image);
			eTest.log(LogStatus.INFO, "Snapshot below: " + eTest.addScreenCapture(System.getProperty("user.dir")+"\\reports\\screenshots"));
			eTest.log(LogStatus.FAIL, result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			eTest.log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
		} else {
			eTest.log(LogStatus.PASS, "Test passed");
		}

		eReport.endTest(eTest);
		eReport.flush();

	}

	@BeforeSuite
	public void beforeSuite() throws IOException {
		System.out.println("In Before Suite");
		
		//cleanReports();
		createReportDirectories();
		
		SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		dateStr = df.format(new java.util.Date());
		final String filePath = System.getProperty("user.dir") + "\\reports\\TestReport.html";
		eReport = ExtentReportManager.getReporter(filePath);
		System.out.println("Exiting Before Suite");
	}

	@AfterSuite
	protected void afterSuite() throws IOException {
		Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");
		eReport.close();
		driver.quit();

	}
}
