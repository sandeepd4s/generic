package com.client.project.selenium;



import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.client.project.qa.at.selenium.utils.SharedDriver;
import com.google.common.base.Function;
//import com.thoughtworks.selenium.Wait;

public class Sync {
	
	WebDriver webDriver;
	SharedDriver sharedDriver;
	
	/**
	 * Constructor of Sync 
	 * @param webDriver (WebDriver) The WebDriver object to be used to wait and find the element
	 */
	public Sync(WebDriver webDriver){
		//super(webDriver);
//		this.webDriver=webDriver;
		sharedDriver = new SharedDriver();
		
		this.webDriver= sharedDriver.setDriver();
		
	}
	
	public int getWaitTime(int... optionalTimeout){
		int value;
		if(optionalTimeout.length<=0){
			value=0;
		}else value = optionalTimeout[0];
		return value;
	}
	
	/**
	 * To verify whether the element is loaded and displayed on the page
	 * @param locator Object of the element to be found
	 * @param waitTime the time in seconds to wait until returning a failure
	 * @return true if element is located within timeout period else false
	 */
	public boolean isElementPresent(By locator, int... waitTime){
		boolean flag = false;
		try{
			WebDriverWait wait = new WebDriverWait(webDriver, getWaitTime(waitTime));
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			if(webDriver.findElement(locator).isDisplayed()||webDriver.findElement(locator).isEnabled()){
				flag = true;
			}
		}catch(NoSuchElementException e){
			//e.printStackTrace();
		}
		catch(TimeoutException e){
			e.printStackTrace();
		}catch(Exception e){
			//e.printStackTrace();
		}
		return flag;
	}
	
	public boolean isElementPresentWithFluentWait(final By locator){
		boolean flag = false;
		
		try{
		FluentWait wait = new FluentWait<WebDriver>(webDriver)
							  .withTimeout(60, TimeUnit.SECONDS)
							  .pollingEvery(5, TimeUnit.SECONDS)
							  .ignoring(NoSuchElementException.class);
							  
		WebElement element = (WebElement) wait.until(new Function<WebDriver, WebElement>(){
			
			public WebElement apply(WebDriver webDriver) {
				// TODO Auto-generated method stub
				return webDriver.findElement(locator);
			}
		});
		if(element.isDisplayed()||webDriver.findElement(locator).isEnabled()){
			flag = true;
		}else flag = false;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	/***
	 * isAllFramesLoadedWithfluentWait(framesCount) method is to wait until frame count greater than or equals to given parameter 
	 * @param framesCount
	 * @return
	 */
	
	public boolean isAllFramesLoadedWithfluentWait(int framesCount){
		boolean flag = false;
		try{
			FluentWait wait = new FluentWait<WebDriver>(webDriver)
					  .withTimeout(1000, TimeUnit.SECONDS)
					  .pollingEvery(5, TimeUnit.SECONDS)
					  .ignoring(NoSuchElementException.class);
			
			List<WebElement> elements = (List<WebElement>) wait.until(new Function<WebDriver, List<WebElement>>() {
				
				@Override
				public List<WebElement> apply(WebDriver webDriver) {
					// TODO Auto-generated method stub
					return webDriver.findElements(By.tagName("iframe"));
				}
			});
			if(elements.size()>=framesCount){
				flag = true;
			}else flag = false;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean isElementEnbledWithFluentWait(final By locator){
		boolean flag = false;
		
		try{
		FluentWait wait = new FluentWait<WebDriver>(webDriver)
							  .withTimeout(120, TimeUnit.SECONDS)
							  .pollingEvery(5, TimeUnit.SECONDS)
							  .ignoring(NoSuchElementException.class);
							  
		WebElement element = (WebElement) wait.until(new Function<WebDriver, WebElement>(){
			
			public WebElement apply(WebDriver webDriver) {
				// TODO Auto-generated method stub
				return webDriver.findElement(locator);
			}
		});
		if(webDriver.findElement(locator).isEnabled()){
			flag = true;
		}else flag = false;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean isElementVisible(By locator, int... waitTime){
		boolean flag = false;
		try{
			WebDriverWait wait = new WebDriverWait(webDriver, getWaitTime(waitTime));
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			if(webDriver.findElement(locator).isDisplayed()||webDriver.findElement(locator).isEnabled()){
				flag = true;
			}
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
		catch(TimeoutException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public  boolean isElementEnabled(By locator, int... waitTime){
		boolean flag = false;
		try{
			WebDriverWait wait = new WebDriverWait(webDriver, getWaitTime(waitTime));
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			if(webDriver.findElement(locator).isEnabled()){
				flag = true;
			}
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
		catch(TimeoutException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean waitUntilClickable(By locator, String friendlyWebElementName, int... optionWaitTime){
		boolean flag = false;
		try{
			WebDriverWait wait = new WebDriverWait(webDriver, getWaitTime(optionWaitTime));
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			if(webDriver.findElement(locator).isDisplayed()){
				flag = true;
			}
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
		catch(TimeoutException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public boolean waitUntilInvisible(By locator, String friendlyWebElementName, int... optionWaitTime){
		boolean flag = false;
		try{
			WebDriverWait wait = new WebDriverWait(webDriver, getWaitTime(optionWaitTime));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			if(!webDriver.findElement(locator).isDisplayed()){
				flag = true;
			}
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
		catch(TimeoutException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
//	public boolean waitUntilOptionsEnable(By locator, String option, String friendlyOptionName, int...optionWaitTime){
//		boolean flag = false;
//		try{
//			WebDriverWait wait = new WebDriverWait(webDriver, getWaitTime(optionWaitTime));
//			if(webDriver.findElement(locator).i)
//			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//			wait.until(webDriver.findElement(locator).findElement(By.tagName("/option")).isEnabled()
//			 webDriver.findElement(locator).findElement(By.xpath("//option[text()='option']"));
//			wait.until(ExpectedConditions.pre
//			
//		return false;
//		
//	}
	
	public boolean waitUntilAlertVisible(int... optionalTimeout){
		boolean flag = false;
		try{
			WebDriverWait wait = new WebDriverWait(webDriver, getWaitTime(optionalTimeout));
			wait.until(ExpectedConditions.alertIsPresent());
			flag=true;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public void waitUntilFrameVisible(By locator, int... optionalTimeout){
		WebDriverWait wait = new WebDriverWait(webDriver, getWaitTime(optionalTimeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}
	
	public void ab(String name, int... optionalTimeout){
		//webDriver.findElements(By.xpath("//iframe[contains(text(),'name')]"));
		WebDriverWait wait = new WebDriverWait(webDriver, getWaitTime(optionalTimeout));
		
	}
	
	public void stop(long t) throws InterruptedException{
		Thread.sleep(t);
	}
	
	
	
}
