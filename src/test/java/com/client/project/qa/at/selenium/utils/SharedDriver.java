package com.client.project.qa.at.selenium.utils;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.client.project.qa.at.common.GetProperties;
import com.client.project.qa.at.common.util.customizedreport.ExtentReportManager;
import com.client.project.qa.at.common.util.customizedreport.ManageReport;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class SharedDriver extends ManageReport{

	private static String binaryResourcePath;
	private static WebDriver driver;
	public static String dateStr;
	public static ExtentReports eReport;
	public static ExtentTest eTest;
	
	public SharedDriver() {
		binaryResourcePath = GetProperties.getConfigPropety("binaryResourcePath");
	}
	
	
	public WebDriver setDriver() {
		if ( null == driver ) {
//            String browser = GetProperties.getConfigPropety( "browser" );
            driver = getDriverInstance(Boolean.parseBoolean(GetProperties.getConfigPropety( "incognitoMode" )));
            driver.manage().timeouts().implicitlyWait(Integer.valueOf(GetProperties.getConfigPropety("Implicitwait")), TimeUnit.MILLISECONDS);
            driver.manage().timeouts().pageLoadTimeout(Integer.valueOf(GetProperties.getConfigPropety("PageloadTimeout")), TimeUnit.MILLISECONDS);
        }
		return driver;
	}
	
	public static WebDriver getDriver() {
		
        return driver;
    }    
	    
	private static WebDriver getDriverInstance( boolean incognitoMode) {
        DesiredCapabilities capability;
        WebDriver newDriver = null;
        ChromeOptions options = new ChromeOptions();
	    if(GetProperties.getConfigPropety("Browser").equals("FIREFOX")){
            System.setProperty("webdriver.gecko.driver", System.getProperty( "user.dir" ) + binaryResourcePath + "/geckodriver/geckodriver.exe");
            newDriver = new FirefoxDriver();
	    }else if(GetProperties.getConfigPropety("Browser").equals("Chrome")){
        	capability = getChromeCapabilities(incognitoMode, true);
        	System.setProperty( "webdriver.chrome.driver", System.getProperty( "user.dir" ) + binaryResourcePath + "/chromedriver/chromedriver.exe" );
            newDriver = new ChromeDriver( capability );
	    }if(GetProperties.getConfigPropety("Browser").equals("IE")) { 
        	capability = getIECapabilities();
            System.setProperty( "webdriver.ie.driver", System.getProperty( "user.dir" ) + binaryResourcePath + "/ieDriver/IEDriverServer.exe" );
            newDriver = new InternetExplorerDriver( capability );
        }
	    
        return newDriver;
    }
	
	public void navigateTo(){
		if(GetProperties.getConfigPropety("Envionment").equals("FTQuality"))
			driver.navigate().to(GetProperties.getConfigPropety("FTQuality"));
	    if(GetProperties.getConfigPropety("Envionment").equals("FirstSand"))
	    	driver.navigate().to(GetProperties.getConfigPropety("FirstSand"));
	}
	
	
	public void enableIncognitoChromeAutomationExtension() {
		try {
			getDriver().get("chrome://extensions-frame");
			getDriver().findElement(By.xpath("//*[@focus-type='incognito']")).click();
			//info("Enabling the automation extension for Chrome via incognito.");
		} catch (Exception e) {
//    		error(e);
    	}
	}

	
	private static ChromeOptions getChromeOptions(boolean incognito) {
		ChromeOptions options = new ChromeOptions();
		if (incognito) 
			{ options.addArguments( "--incognito" ); }
		options.addArguments( "test-type" );
        options.addArguments( "--start-maximized" );
        options.addArguments("--no-sandbox");
        return options;
	}
	
	private static DesiredCapabilities getChromeCapabilities(boolean incognito, boolean remote) {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability( "nativeEvents", false );
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability( ChromeOptions.CAPABILITY, getChromeOptions(incognito) );
        if (remote) 
        	{ capabilities.setPlatform( Platform.WINDOWS ); }
        return capabilities;
	}
	
	private static DesiredCapabilities getIECapabilities() {
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setBrowserName("internet explorer");
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
        capabilities.setCapability( "nativeEvents", false );
        capabilities.setCapability("ie.ensureCleanSession", true);
        capabilities.setPlatform( Platform.WINDOWS );
        return capabilities;
	}
	
	
	public ExtentReports setExtentReport() throws IOException {
		System.out.println("In Before Suite");
		//cleanReports();
		createReportDirectories();
		SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		dateStr = df.format(new java.util.Date());
		final String filePath = System.getProperty("user.dir") + "\\reports\\reports_"+ManageReport.getTimeStamp()+"\\TestReport.html";
		eReport = ExtentReportManager.getReporter(filePath);
		System.out.println("Exiting Before Suite");
		return eReport;
	}
	
	public void endExtentReport(ITestResult result, WebDriver driver) {
		eReport.endTest(eTest);
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = CaptureScreenshot.get(result.getName(), driver, dateStr);
			System.out.println("screenshotPath.................." + screenshotPath);
			eTest.log(LogStatus.FAIL, "Snapshot below: " + eTest.addScreenCapture(screenshotPath));
			eTest.log(LogStatus.FAIL, result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			eTest.log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
		} else {
			eTest.log(LogStatus.PASS, "Test passed");
		}
		eReport.endTest(eTest);
		eReport.flush();
		// _driver.quit();
	}
	
	public void startReport(String scenarioName) {
		eTest = eReport.startTest(scenarioName);
	}
	
	public void printOnReport(String messageToPrint) {
		System.out.println(messageToPrint);
		eTest.log(LogStatus.PASS, messageToPrint);
		Reporter.log(messageToPrint);
	}
	
	public void driverQuit() throws IOException, InterruptedException{
		driver.quit();
		Process p =Runtime.
		   getRuntime().
		   exec(System.getProperty("user.dir")+"\\processkill\\chromeexe.bat");
		p.waitFor();
		p=Runtime.
		   getRuntime().
		   exec(System.getProperty("user.dir")+"\\processkill\\chromedriver.bat");
		p.waitFor();
		p =Runtime.
				   getRuntime().
				   exec(System.getProperty("user.dir")+"\\processkill\\chromeexe.bat");
				p.waitFor();
	}
	
	
	
}

	
        
	