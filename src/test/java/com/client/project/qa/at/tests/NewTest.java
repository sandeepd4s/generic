package com.client.project.qa.at.tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.functors.SwitchTransformer;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.client.project.qa.at.common.ConfigReader;
import com.client.project.qa.at.common.DataFactory;
import com.client.project.qa.at.common.Dates;
import com.client.project.qa.at.common.GetProperties;
import com.client.project.qa.at.common.ScenariosFactory;
import com.client.project.qa.at.pages.LoginPage;
import com.client.project.qa.at.selenium.utils.SharedDriver;
import com.relevantcodes.extentreports.ExtentReports;


public class NewTest {

	DataFactory dataFactory = new DataFactory();
	WebDriver driver;
	public SharedDriver sd = new SharedDriver();
	ExtentReports eReport;

	LoginPage loginPage;

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws IOException {
		System.out.println("Before Method");

		driver = sd.setDriver();
		eReport = sd.setExtentReport();
		loginPage = LoginPage.getLoginPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) throws InterruptedException {
		System.out.println("After Method in New Test");
		sd.endExtentReport(result, driver);
		loginPage.redirect();
	}

	@BeforeClass(alwaysRun = true)
	public void nameBefore() throws Exception {

	}

	@BeforeSuite
	public void beforeSuite() throws IOException {
		System.out.println("Before Suite");
		sd.setExtentReport();
	}

	@AfterSuite
	public void afterSuite() throws IOException, InterruptedException {
		System.out.println("After Suite");
		sd.driverQuit();
		eReport.close();
	}

	//@Test(dataProvider = "testSource")
	@Test
	public void scenarios() throws InterruptedException, IOException, Exception {

		// fetching data and assigning to HashMap
		Map<String, String> data = new LinkedHashMap<>();
		Map<String, String> memberData = new LinkedHashMap<>();

		loginPage.redirect();
//		loginPage.login(channel);
		sd.startReport("Scenario : Channel-" );
//		
		sd.printOnReport("Scenario : Channel -");
		
		sd.printOnReport("*******************");
		
		
}
		    

	@Test
	public void test(){
		sd.startReport("Scenario : 2");
//		
		sd.printOnReport("Scenario : Test");
		
		sd.printOnReport("*******************");
	}

//	@DataProvider(name = "testSource")
//	public Iterator<Object[]> getTestDataSet(Method method) {
//		ScenariosFactory scenariosFactory = new ScenariosFactory();
//		return scenariosFactory.getTestDataSet(ConfigReader.getConfigProps().get("scenariosData").split("\\-")[0],
//				method.getName());
//	}
}
