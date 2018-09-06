package com.client.project.selenium;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class SafeActions extends Sync {

	protected WebDriver webDriver;
	// private By close = By.xpath("//a[@id='ext-gen10']");

	public SafeActions(WebDriver webDriver) {
		// TODO Auto-generated constructor stub
		super(webDriver);
		this.webDriver = webDriver;
	}

	public void safeClickWithFluentWait(By locator, String friendlyWebElementName, int... optionalTimeout) {
		// if (isElementVisible(close))
		// webDriver.findElement(close).click();
		try {
			if (isElementPresentWithFluentWait(locator)) {
				webDriver.findElement(locator).click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(friendlyWebElementName + " is not attached to the page document");
		}
	}

	/**
	 * Method - SafeMethod for User click, waits until the element is loaded and
	 * then performs a click action
	 * 
	 * @param locator
	 * @param friendlyWebElementName
	 * @param optionalTimeout
	 */
	public void safeClick(By locator, String friendlyWebElementName, int... optionalTimeout) {
		try {
			// if (isElementVisible(close))
			// webDriver.findElement(close).click();
			if (waitUntilClickable(locator, friendlyWebElementName, getWaitTime(optionalTimeout))) {
				// System.out.println("**************** Locator****" + locator +
				// " ******** is clickable now");
				WebElement element = webDriver.findElement(locator);
				element.click();
			}
		} catch (StaleElementReferenceException e) {
			e.printStackTrace();
			Assert.fail(
					friendlyWebElementName + " is not attached to the page document - StaleElementREferenceException");
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail(friendlyWebElementName + " is not found in DOM in the page document in time "
					+ getWaitTime(optionalTimeout) + " seconds - " + " NoSuchElementException");
		}
		// } catch (Exception e) {
		// e.printStackTrace();
		// Assert.fail(friendlyWebElementName + " was not found on the page");
		// }
	}
	
	/**
	 * Method - SafeMethod for User click, waits until the element is loaded and
	 * then performs a click action
	 * 
	 * @param locator
	 * @param friendlyWebElementName
	 * @param optionalTimeout
	 */
	public void safeClicks(By locator, String friendlyWebElementName, int... optionalTimeout) {
		try {
			// if (isElementVisible(close))
			// webDriver.findElement(close).click();
			if (waitUntilClickable(locator, friendlyWebElementName, getWaitTime(optionalTimeout))) {
				// System.out.println("**************** Locator****" + locator +
				// " ******** is clickable now");
				List<WebElement> element = webDriver.findElements(locator);
				for(WebElement ele : element){
					ele.click();
				}
			}
		} catch (StaleElementReferenceException e) {
			e.printStackTrace();
			Assert.fail(
					friendlyWebElementName + " is not attached to the page document - StaleElementREferenceException");
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail(friendlyWebElementName + " is not found in DOM in the page document in time "
					+ getWaitTime(optionalTimeout) + " seconds - " + " NoSuchElementException");
		}
		// } catch (Exception e) {
		// e.printStackTrace();
		// Assert.fail(friendlyWebElementName + " was not found on the page");
		// }
	}
	
	/**
	 * Method - SafeMethod for User click, waits until the element is loaded and
	 * then performs a click action
	 * 
	 * @param locator
	 * @param friendlyWebElementName
	 * @param optionalTimeout
	 */
	public void safeJsClicks(By locator, String friendlyWebElementName, int... optionalTimeout) {
		try {
			// if (isElementVisible(close))
			// webDriver.findElement(close).click();
			//if (waitUntilClickable(locator, friendlyWebElementName, getWaitTime(optionalTimeout))) {
				// System.out.println("**************** Locator****" + locator +
				// " ******** is clickable now");
				List<WebElement> element = webDriver.findElements(locator);
				System.out.println(element.size());
				for(WebElement ele : element){
					//ele.click();
					//jsClick(ele);
					actionsClick(ele);
				}
			//}
		} catch (StaleElementReferenceException e) {
			e.printStackTrace();
			Assert.fail(
					friendlyWebElementName + " is not attached to the page document - StaleElementREferenceException");
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail(friendlyWebElementName + " is not found in DOM in the page document in time "
					+ getWaitTime(optionalTimeout) + " seconds - " + " NoSuchElementException");
		}
		// } catch (Exception e) {
		// e.printStackTrace();
		// Assert.fail(friendlyWebElementName + " was not found on the page");
		// }
	}

	public void safeClick(WebElement element, String friendlyName) {
		element.click();
	}

	public void jsClick(By locator, int... optionWaitTime) {
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("arguments[0].click();", webDriver.findElement(locator));
	}
	
	public void jsClick(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("arguments[0].click();", ele);
	}

	// public void jsScriptClick(By locator, int...optionWaitTime){
	// JavascriptExecutor js = (JavascriptExecutor) webDriver;
	// js.executeScript("window.getComputedStyle(document.querySelector('em.x-btn-split'),
	// '::after').click();");
	// }

	public void safeType(By locator, String text, String friendlyWebElementName, int... optionWaitTime) {
		int waitTime = 0;

		try {
			waitTime = getWaitTime(optionWaitTime);
			if (isElementPresent(locator, waitTime)) {

				WebElement element = webDriver.findElement(locator);
				element.click();
				Thread.sleep(500);
				element.sendKeys(text);
			} else {
				Assert.fail("Unable to enter " + text + " in" + friendlyWebElementName + " in time" + waitTime
						+ " Seconds");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Unable to enter " + text + " text in - " + friendlyWebElementName);
		}
	}

	public void safeType(By locator, Keys key, String friendlyWebElementName, int... optionWaitTime) {
		int waitTime = 0;
		try {
			waitTime = getWaitTime(optionWaitTime);
			if (isElementPresent(locator, waitTime)) {

				WebElement element = webDriver.findElement(locator);
				element.click();
				// element.clear();
				element.sendKeys(Keys.ENTER);
			} else {
				Assert.fail(
						"Unable to press " + key + " in" + friendlyWebElementName + " in time" + waitTime + " Seconds");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Unable to press " + key + " text in - " + friendlyWebElementName);
		}
	}

	public void safeTypeWithFluentWait(By locator, String text, String friendlyName) {
		if (isElementPresentWithFluentWait(locator)) {
			WebElement element = webDriver.findElement(locator);
			element.click();
			element.sendKeys(text);
		}
	}

	public void safeTypeKeys(By locator, Keys key, String friendlyName) {
		if (isElementPresentWithFluentWait(locator)) {
			WebElement element = webDriver.findElement(locator);
			element.click();
			element.sendKeys(key);
		}
	}

	public String getText(By locator, String friendlyName, int... optionalwaitTime) {
		String text = null;
		if (isElementPresentWithFluentWait(locator)) {
			WebElement element = webDriver.findElement(locator);
			text = element.getText();
			System.out.println("*****");
		}
		System.out.println(text);
		return text;
	}
	
	public String getText(WebElement element, String friendlyName, int... optionalwaitTime) {
		String text = null;
		text = element.getText();
		System.out.println("*****");
		System.out.println(text);
		return text;
	}

	public ArrayList<String> getTexts(By locator, String friendlyname, int... optionalwaitTime) {
		ArrayList<String> list = new ArrayList<String>();
		String text = null;
		if (isElementPresentWithFluentWait(locator)) {
			List<WebElement> element = webDriver.findElements(locator);
			for (WebElement ele : element) {
				text = ele.getText();
				list.add(text);
			}
			
		}
		return list;
	}

	public String getTexByVisibility(By locator, String friendlyName, int... optionalwaitTime) {
		String text = null;
		if (isElementVisible(locator, getWaitTime(optionalwaitTime))) {
			WebElement element = webDriver.findElement(locator);
			text = element.getText();
		}
		return text;
	}

	public String getValue(By locator, String friendlyname, int... optionalwaitTime) {
		String text = null;
		if (isElementPresentWithFluentWait(locator)) {
			WebElement element = webDriver.findElement(locator);
			text = element.getAttribute("value");
		}
		return text;
	}

	public List getValues(By locator, String friendlyname, int... optionalwaitTime) {
		ArrayList list = new ArrayList();
		String text = null;
		if (isElementPresentWithFluentWait(locator)) {
			List<WebElement> element = webDriver.findElements(locator);
			for (WebElement ele : element) {
				text = ele.getAttribute("value");
			}
			list.add(text);
		}
		return list;
	}

	public String getAttributeValue(By locator, String attribute, String friendlyname, int... optionalwaitTime) {
		String text = null;
		if (isElementPresent(locator, getWaitTime(optionalwaitTime))) {
			WebElement element = webDriver.findElement(locator);
			text = element.getAttribute(attribute);
		}
		return text;
	}

	public void safeClickOnTab(String tabName) {
		if (isElementPresent(By.linkText(tabName))) {
			WebElement link = webDriver.findElement(By.linkText(tabName));
			link.click();
		}
	}

	public boolean isTextPresent(String text) {
		boolean flag = false;
		if (webDriver.findElement(By.tagName("//body")).getText().contains(text))
			flag = true;
		else
			flag = false;
		return flag;
	}

	/***
	 * this method is to select option in drop down
	 * 
	 * @param locator
	 *            - Locator of the drop down
	 * @param optionToSelect
	 *            - option to select for drop down
	 * @param friendlyName
	 *            - object in the page
	 * @param optionalWaitTime
	 *            - max time to wait for the element
	 */

	public void safeSelectByValue(By locator, String optionToSelect, String friendlyName, int... optionalWaitTime) {
		int waitTime = 0;

		try {
			waitTime = getWaitTime(optionalWaitTime);
			if (isElementPresent(locator, waitTime)) {

				WebElement element = webDriver.findElement(locator);
				Select select = new Select(element);
				select.selectByValue(optionToSelect);
			} else {
				Assert.fail("Unable to find the element " + friendlyName + " in the time " + waitTime + " seconds");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Unable to find the element " + friendlyName + " or option " + optionToSelect);
		}

	}

	public List<String> getOptionsFromDropdown(By locator, String friendlyName, int... optionalWaitTime) {
		int waitTime = 0;
		List<String> optValues = null;

		try {
			waitTime = getWaitTime(optionalWaitTime);
			if (isElementPresent(locator, waitTime)) {

				WebElement element = webDriver.findElement(locator);
				Select select = new Select(element);
				List<WebElement> opt = select.getAllSelectedOptions();
				optValues = opt.stream().map(val -> val.getText()).collect(Collectors.toList());
			} else {
				Assert.fail("Unable to find the element " + friendlyName + " in the time " + waitTime + " seconds");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Unable to find the element " + friendlyName + " or options ");
		}
		return optValues;
	}

	/***
	 * 
	 * @param ele
	 * @param optionToSelect
	 * @param friendlyName
	 * @param optionalWaitTime
	 */

	public void safeSelectByValue(WebElement ele, String optionToSelect, String friendlyName, int... optionalWaitTime) {
		Select select = new Select(ele);
		select.selectByValue(optionToSelect);
	}

	public void safeSelectByVisibleText(By locator, String optionToSelect, String friendlyName,
			int... optionalWaitTime) {
		int waitTime = 0;
		try {
			waitTime = getWaitTime(optionalWaitTime);
			if (isElementPresent(locator, waitTime)) {
				WebElement element = webDriver.findElement(locator);
				Select select = new Select(element);
				select.selectByVisibleText(optionToSelect);
			} else {
				Assert.fail("Unable to find the element " + friendlyName + " in the time " + waitTime + " seconds");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Unable to find the element " + friendlyName + " or option " + optionToSelect);
		}
	}

	public boolean getStatus(By locator, String friendlyName, int... optionalWaitTime) {
		int waitTime = 0;
		boolean flag = false;
		try {
			waitTime = getWaitTime(optionalWaitTime);
			if (isElementPresent(locator, waitTime)) {
				WebElement element = webDriver.findElement(locator);
				if (!element.isEnabled())
					flag = true;
				if (element.isEnabled())
					flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Unable to find the element " + friendlyName);
		}
		return flag;
	}

	public List<WebElement> getAllElements(By locator, String friendlyName, int... optionalWaitTime) {
		int waitTime = 0;
		List<WebElement> elements = null;
		try {
			waitTime = getWaitTime(optionalWaitTime);
			if (isElementPresent(locator, waitTime)) {
				elements = webDriver.findElements(locator);
			} else
				Assert.fail("Unable to find the element " + friendlyName);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Unable to find the element " + friendlyName);
		}
		return elements;
	}

	// /***
	// * This method is to fetch all WebElements in the given section
	// * @param section - locator of section
	// * @param locator - locator of objects insie the section
	// * @param friendlyElementName - String
	// * @param optionalWaitTime - wait time
	// * @return List<WebElement> of given locators
	// */
	//
	// public List<WebElement> getAllElements(By section, String
	// friendlyElementName, int... optionalWaitTime){
	// int waitTime=0;
	// List<WebElement> element=null;
	// if(isElementPresent(section, 1)){
	// element=webDriver.findElements(section);
	// }else Assert.fail("Unable to find the element "+section+"
	// "+friendlyElementName);
	// return element;
	// }

	public void checkAllElements(List<WebElement> element, int... optionalWaitTime) {
		int waitTime = 0;
		if (element.size() >= 1) {
			for (WebElement ele : element) {
				safeClick(ele);
			}
		} else
			Assert.fail("The size of List is " + element.size());
	}

	public void safeClick(WebElement element) {
		element.click();
	}

	public void safeClickOnLink(String linkText) {
		webDriver.findElement(By.linkText(linkText)).click();
	}

	public void selectOptions(By locator, String option, String friendlyName, int... optionalWaitTime) {
		List<WebElement> element = webDriver.findElements(locator);
		for (WebElement ele : element) {
			safeSelectByValue(ele, option, friendlyName, optionalWaitTime);
		}
	}

	public void alertAccept() {
		if (waitUntilAlertVisible(100)) {
			Alert alert = webDriver.switchTo().alert();
			alert.accept();
		}
	}

	public String getTextAlertAccept(String option) {
		String alertText = null;
		if (option != "Now") {
			if (waitUntilAlertVisible(100)) {
				Alert alert = webDriver.switchTo().alert();
				alertText = alert.getText();
				alert.accept();
			}
		}
		return alertText;
	}

	public void clearData(By locator, String friendlyName, int... optionalWaitTime) {
		webDriver.findElement(locator).clear();
	}

	public boolean isTextFieldClear(By locator, String friendlyName, int... optionalWaitTime) {
		boolean flag = false;
		if (webDriver.findElement(locator).getText().equals(""))
			flag = true;
		else
			flag = false;
		return flag;
	}

	public void safeValueUpdate(By locator, String text, String friendlyName, int... optionalWaitTime) {
		clearData(locator, friendlyName, optionalWaitTime);
		if (isTextFieldClear(locator, friendlyName, optionalWaitTime))
			safeType(locator, text, friendlyName, optionalWaitTime);
	}

	public void safeSetPathToAttach(By locator, String path, int... optionalWaitTime) throws InterruptedException {
		webDriver.findElement(locator).sendKeys(path);
		Thread.sleep(10000);
	}

	public void selectValueInSubWindow(By elem) {
		String windowId = null;
		Set<String> windows = webDriver.getWindowHandles();
		Iterator itr = windows.iterator();
		while (itr.hasNext()) {
			windowId = itr.next().toString();
			System.out.println("************" + windowId);
			webDriver.switchTo().window(windowId);
			if (isElementVisible(elem, 1)) {
				webDriver.findElement(elem).click();
				break;
			}
		}
	}

	public List<WebElement> getAllOptions(By locator) {
		List<WebElement> allOptions;
		WebElement element = webDriver.findElement(locator);
		Select select = new Select(element);
		allOptions = select.getOptions();
		return allOptions;
	}

	public String getSelectedOption(By locator) {
		WebElement element = webDriver.findElement(locator);
		Select select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}

	public void safeSwitchToFrame(By locator, int... optionalWaitTime) {
		// int waitTime;
		// boolean flag;
		// try {
		// waitTime = getWaitTime(optionalWaitTime);
		// if (isElementPresent(locator, waitTime)) {
		// WebElement element = webDriver.findElement(locator);
		// if (!element.isEnabled())
		// flag = true;
		// if (element.isEnabled())
		// flag = false;
		// }
		// webDriver.switchTo().frame(webDriver.findElement(locator));

		List<WebElement> framesList = webDriver.findElements(By.tagName("iframe"));
		System.out.println("framesList.size() : " + framesList.size());
		int i = 0;
		boolean flag = false;
		for (WebElement frame : framesList) {
			System.out.println("Frame Name:"+frame.getAttribute("name"));
			try {
				webDriver.switchTo().frame(frame);
				if (isElementPresent(locator)) {
					System.out.println("Frame number : " + i);
					//System.out.println("********" + getText(locator, "******", 1));
					break;
				} else
					webDriver.switchTo().defaultContent();
				++i;
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}
	
	
	public void safeSwitchToFrame_Id(String frameID){
		webDriver.switchTo().frame(frameID);
	}
	
	public void safeSwitchToMainFrame(){
		webDriver.switchTo().defaultContent();
	}

	
	public void switchToOneChildWindow(String expWindowTitle) {
		String curWindow = webDriver.getTitle();
		Set<String> windows = webDriver.getWindowHandles();
		Iterator<String> i = windows.iterator();
		while (i.hasNext()) {
			String childWindow = i.next();
			webDriver.switchTo().window(childWindow);
			System.out.println(webDriver.getTitle());
			System.out.println(expWindowTitle);
			if (expWindowTitle.endsWith(webDriver.getTitle())) {
				// webDriver.switchTo().window(childWindow);
				break;
			}
		}
	}

	public void closeCurrentWindow(String windowTitle) {

		String curWindow = webDriver.getTitle();
		Set<String> windows = webDriver.getWindowHandles();
		Iterator<String> i = windows.iterator();
		while (i.hasNext()) {
			String childWindow = i.next();
			webDriver.switchTo().window(childWindow);
			System.out.println(webDriver.getTitle());
			System.out.println(windowTitle);
			if (windowTitle.endsWith(webDriver.getTitle())) {
				webDriver.close();
				break;
			}
		}
	}

//	public String expectedWindow(String epectedWindowTitle) {
//		String requriedWindowID = null;
//		currentWindow = webDriver.getWindowHandle();
//		Set<String> set = webDriver.getWindowHandles();
//		Iterator<String> it = set.iterator();
//		it.next();
//		while (it.hasNext()) {
//			String actWindow = it.next();
//			webDriver.switchTo().window(actWindow);
//			if (epectedWindowTitle.equals(webDriver.getTitle())) {
//				requriedWindowID = actWindow;
//				System.out.println("catch =" + requriedWindowID);
//
//			} else {
//
//				webDriver.close();
//
//			}
//
//		}
//		return requriedWindowID;
//	}

	public void quitDriver() {
		webDriver.quit();
	}

	public void switchToWindowWithTitleAndCloseWindow(String windowTitle) {
		Set<String> windows = webDriver.getWindowHandles();
		Iterator<String> i = windows.iterator();
		while (i.hasNext()) {
			String window = i.next();
			String cwindow = webDriver.switchTo().window(window).getTitle();
			if (windowTitle.equals(cwindow)) {
				// webDriver.close();
				break;
			}

		}
	}

	public String getTextFieldValue(By locator, String friendlyName) {
		return webDriver.findElement(locator).getAttribute("value");
	}

	public void switchToMainWindow(String mainWindow) {
		webDriver.switchTo().window(mainWindow);
	}

	public void actionsSliderMove(By locator) {
		WebElement element = webDriver.findElement(locator);
		Actions move = new Actions(webDriver);
		move.dragAndDropBy(element, 30, 0).build().perform();
	}

	public void jsSlider(By locator, String value) {
		WebElement element = webDriver.findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("arguments[0].aria-valuenow=" + value, element);
	}

	public void actionsClick(By locator) {
		WebElement element = webDriver.findElement(locator);
		Actions move = new Actions(webDriver);
		move.moveToElement(webDriver.findElement(locator)).click().build().perform();
	}
	
	public void actionsClick(WebElement element) {
		Actions move = new Actions(webDriver);
		move.moveToElement(element).click().build().perform();
	}

	public void sliderKeys(By locator, String value) {
		WebElement slider = webDriver.findElement(locator);
		for (int i = 1; i <= Integer.valueOf(value); i++) {
			slider.sendKeys(Keys.ARROW_RIGHT);
		}
	}

	public void jsSetValue(By locator, String text) {
		WebElement element = webDriver.findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("arguments[0].value=" + text, element);
	}
	
	public String jsReturnValue(By locator){
		WebElement element = webDriver.findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		System.out.println("JS REturn"+js.executeScript("return "+"argumetns[0].innerText;", element).toString());
		return js.executeScript("return "+"argumetns[0].innerText;", element).toString();
	}

	public void eSignAction(By locator) {
		WebElement element = webDriver.findElement(locator);
		element.click();
		// Actions builder = new Actions(webDriver);
		// // builder.moveToElement(element, 721, 327).click()
		// builder.moveByOffset(670, 230).clickAndHold().build().perform();
		// builder.moveByOffset(700, 330).click().build().perform();
		// builder.release().build().perform();
		// // AndHold()
		// // .moveByOffset(670, 230)
		// // .moveByOffset(650, 280)
		// // .release().
		// // .build().perform();
		// System.out.println("not clcikd");
	}

	public void eSignAction2(By locator) {
		try {
			WebElement element = webDriver
					.findElement(By.xpath("//div[text()='Please sign below']/following::div[1]/canvas")); // where

			// your
			webDriver.findElement(By.xpath("//div[text()='Please sign below']/following::div[1]/canvas")).click();
			Actions builder = new Actions(webDriver);
			builder.moveToElement(element, 800, 200) // start point
					.click().moveByOffset(800, 800) // second point
					.doubleClick().build().perform();
			System.out.println("Clicked succesfully");
			// drawAction.perform();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void switchToWindowWithTitleContainsAndCloseWindow(String windowTitle) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String> windows = webDriver.getWindowHandles();
		Iterator<String> i = windows.iterator();
		while (i.hasNext()) {
			String window = i.next();
			String cwindow = webDriver.switchTo().window(window).getTitle();
			System.out.println("Window Title----" + cwindow);
			if (cwindow.contains(windowTitle)) {
				webDriver.close();
				break;
			}

		}
	}

	public void switchToOneChildWindowTitleContains(String mainWindow) {
		Set<String> windows = webDriver.getWindowHandles();
		Iterator<String> i = windows.iterator();
		while (i.hasNext()) {
			String childWindow = i.next();
			if (!childWindow.contains(mainWindow)) {
				webDriver.switchTo().window(childWindow);
			}
		}
	}

	public void swithToMainWindow() {
		webDriver.switchTo().window(new ArrayList<String>(webDriver.getWindowHandles()).get(0));
	}

	public void safeSendKeys(By locator, Keys keys) {
		webDriver.findElement(locator).sendKeys(keys);
	}

	public void safeSendKeysText(By locator, String keys) {
		webDriver.findElement(locator).sendKeys(keys);
	}

	/***
	 * switch to new tab and close it and again swith to main tab
	 * 
	 * @author VivekD
	 */
	public void switchToNewTabAndCloseTab() {

		ArrayList<String> tabs2 = new ArrayList<String>(webDriver.getWindowHandles());
		webDriver.switchTo().window(tabs2.get(1));
		webDriver.close();
		webDriver.switchTo().window(tabs2.get(0));
	}
	
	/***
	 * switch to new tab and close it and again swith to main tab
	 * 
	 * @author SandeepReddyD
	 */
	public void switchToNewTabAndCloseTab(String channel, String product) {
		if(channel.equalsIgnoreCase("Contact")||channel.equalsIgnoreCase("Branch")){
			ArrayList<String> tabs2 = new ArrayList<String>(webDriver.getWindowHandles());
			webDriver.switchTo().window(tabs2.get(2));
			webDriver.close();
			webDriver.switchTo().window(tabs2.get(1));
		}
	}
	
	/***
	 * switch to new tab and close it and again swith to main tab
	 * 
	 * @author VivekD
	 */
	public void switchToNewTabAndCloseTab(String prodtWindowName) {

		ArrayList<String> tabs2 = new ArrayList<String>(webDriver.getWindowHandles());
		webDriver.switchTo().window(tabs2.get(1));
		if(webDriver.getTitle().equals(prodtWindowName)){
			webDriver.close();
		}
		webDriver.switchTo().window(tabs2.get(0));
	}
	
	/***
	 * mainPageRefresh() is to refresh the main window
	 */
	public void mainPageRefresh(){
		ArrayList<String> tabs2 = new ArrayList<String>(webDriver.getWindowHandles());
		webDriver.switchTo().window(tabs2.get(0));
		webDriver.navigate().refresh();
	}
	/***
	 *getDerivedWebElement is to fetch the WebElement 
	 * @param source - > locator1, Which is Parent
	 * @param target -> locator2, Which is child of parent
	 * @return
	 */
	public WebElement getDeriverWebElement(By source, By target){
		WebElement ele = webDriver.findElement(source);
		return ele.findElement(target);
	}
	
	/***
	 * Method is to get the count of the specified elements
	 * @param locator
	 * @return
	 */
	public int getcount(By locator){
		return webDriver.findElements(locator).size();
	}

}
