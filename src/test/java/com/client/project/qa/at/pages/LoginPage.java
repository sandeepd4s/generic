package com.client.project.qa.at.pages;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.client.project.qa.at.common.GetProperties;
import com.client.project.selenium.SafeActions;

public class LoginPage extends SafeActions {
	private static WebDriver driver;
	public static LoginPage loginPage;
	private WebDriver webDriver;
	public static String mainWindow;
	public static String timestamp = getTimeStamp();
	public static Calendar now = Calendar.getInstance();
	public static String timeStampString = toAlphabetic(Long.valueOf(getTimeStamp()));
	
	public LoginPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		this.driver = webDriver;
	}
	
	public static LoginPage getLoginPage(WebDriver webDriver){
		if(loginPage==null)
			return new LoginPage(webDriver);
		else
			return loginPage;
	}
	
	public static String toAlphabetic(Long i) {
	    if( i<0 ) {
	        return "-"+toAlphabetic(-i-1);
	    }

	    long quot = i/26;
	    long rem = i%26;
	    char letter = (char)((int)'A' + rem);
	    if( quot == 0 ) {
	        return ""+letter;
	    } else {
	        return toAlphabetic(quot-1) + letter;
	    }
	    
	}
	
	public static String getAlphaString(){
		String act = "";
		if(timeStampString.length()<6){
			for(int i=0;i<5;i++){
				act=timeStampString.charAt(i)+"";
			}
		}
		return act;
	}


	private static By loginUserNam = By.id("username");
	private static By loginPswrd = By.id("password");
	private static By loginBtn = By.id("Login");

	// **********************
	// launchBrowser() is to launch the browser
	// ***********************
	public WebDriver launchBrowser() {
		System.out.println("*************" + System.getProperty("user.dir"));

		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
		webDriver = new ChromeDriver();
		webDriver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);

		new WebDriverWait(webDriver, 10);
		return webDriver;
	}
	
	public static String getTimeStamp(){
		//sdf.format(new Timestamp(System.currentTimeMillis()))
		return new SimpleDateFormat("yyyymmddhhmmss").format(new Timestamp(System.currentTimeMillis()));
	}
	
	public void redirect(){
	    	driver.navigate().to(GetProperties.getConfigPropety("url"));
	}
	
	/***
	 * To switch main Window
	 * @author SandeepReddyD
	 */
	public void switchToMainWindow(){
		driver.switchTo().window(new ArrayList<String>(driver.getWindowHandles()).get(0));
	}
	
	/***
	 * To refresh the page
	 */
	public void pageRefresh(){
		driver.navigate().refresh();
	}
	
	public void closeTitledWindow(String title){
		switchToWindowWithTitleContainsAndCloseWindow(title);
	}
	

}
