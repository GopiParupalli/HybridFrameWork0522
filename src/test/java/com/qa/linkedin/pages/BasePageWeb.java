package com.qa.linkedin.pages;

import java.nio.charset.Charset;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.qa.linkedin.base.TestBase;

public class BasePageWeb extends TestBase {

	private Logger log = Logger.getLogger(BasePageWeb.class);
	Actions act;

	// Constructor
	public BasePageWeb() {

		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		act = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	public void click(WebElement element) throws InterruptedException {
		log.debug("wait for the element to be clickable or not");
		wait.until(ExpectedConditions.elementToBeClickable(element));
		log.debug("click on the given element");
		element.click();

	}

	public void clickByActions(WebElement element) {
		log.debug("wait for the element to be clickable or not");
		wait.until(ExpectedConditions.elementToBeClickable(element));
		log.debug("click an element by Actions class click()");
		act.click(element).build().perform();
	}

	public void clickUsingJsExecutor(WebElement element) throws InterruptedException {
		log.debug("wait for the element to be clickable or not");
		wait.until(ExpectedConditions.elementToBeClickable(element));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		log.debug("click an element using javascript click");
		executor.executeScript("arguments[0].click();", element);
	}

	public boolean isClickable(WebElement element) throws InterruptedException {
		log.debug("wait for the element to be clickable or not");
		wait.until(ExpectedConditions.elementToBeClickable(element));
		return element.isDisplayed();
	}

	public String getText(WebElement element) throws InterruptedException {
		log.debug("wait for the visibility of an element");
		wait.until(ExpectedConditions.visibilityOf(element));
		log.debug("fetch the text for an element");
		return element.getText();
	}

	public void clearText(WebElement element) throws InterruptedException {
		log.debug("wait for the element to be clickable or not");
		wait.until(ExpectedConditions.elementToBeClickable(element));
		log.debug("clear the content in given editbox element");
		element.clear();

	}

	public void clearTextWithBackSapce(WebElement element) throws InterruptedException {
		log.debug("wait for the element to be clickable or not");
		wait.until(ExpectedConditions.elementToBeClickable(element));
		while (!element.getAttribute("value").toString().contentEquals("")) {
			element.sendKeys(Keys.BACK_SPACE);
		}
	}

	public boolean isCkeckedElement(WebElement element, String attribute) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		try {
			return element.getAttribute(attribute).toString().contentEquals("true");
		} catch (NullPointerException e) {
			return false;
		}
	}

	boolean isEnableElement(WebElement element) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.isEnabled();
	}

	public void isVisible(WebElement element) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	boolean isDisplayedElement(WebElement element) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.isDisplayed();
	}

	boolean isPresentElement(WebElement element) {

		try {
			return element.isDisplayed();
			// return true;
		} catch (Exception e) {
			return false;
		}
	}

	boolean selectOptionCarousel(List<WebElement> options, String option, WebElement nextBtn)
			throws InterruptedException {

		for (WebElement webElement : options) {
			if (webElement.getText().equalsIgnoreCase("")) {
				while (!webElement.isDisplayed()) {
					click(nextBtn);
				}
				if (webElement.getText().equalsIgnoreCase(option)) {
					// click(webElement);
					return true;
				}
			}

		}
		return false;
	}

	boolean isNotDisplayedElement(WebElement element) throws InterruptedException {
		try {
			return !element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	boolean selectOptionList(List<WebElement> options, String option) throws InterruptedException {
		for (WebElement webElement : options) {
			log.debug("header name is:" + webElement.getText());
			if (webElement.getText().equalsIgnoreCase(option)) {
				click(webElement);
				return true;

			}
		}

		return false;
	}

	boolean selectOption(List<WebElement> options, String option) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOfAllElements(options));
		for (WebElement webElement : options) {
			if (webElement.getAttribute("value").equalsIgnoreCase(option)) {
				click(webElement);
				return true;
			}
		}
		return false;
	}

	void selectOption(Select selector, String value) {
		selector.selectByValue(value);
	}

	boolean isDisableElement(WebElement element) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		return !element.isEnabled();
	}

	public void addNewTab() throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.open('');");
		Thread.sleep(5000);
	}

	void assertText(WebElement element, String text) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		Assert.assertEquals(element.getText().toLowerCase(), text.toLowerCase(),
				"The text " + text + " is not equals to " + element.getText());

	}

	void assertContainsText(WebElement element, String text) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		Assert.assertTrue(element.getText().toString().contains(text),
				"The text " + text + " is not equals to " + element.getText().toString());
	}

	void assertTextContainsElementText(WebElement element, String text) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		Assert.assertTrue(text.contains(element.getText().toString()),
				"The text " + text + " is not equals to " + element.getText().toString());
	}

	void assertTextIsNotEquals(WebElement element, String text) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		Assert.assertFalse(element.getText().equals(text), "The text " + text + " is equals to " + element.getText());
	}

	void assertSubstring(WebElement element, String text) throws InterruptedException {

		Assert.assertTrue(element.getText().contains(text),
				"The text " + element.getText() + "doesn't contain the string " + text);
	}

	void sendKey(WebElement element, String text) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.clear();
		element.sendKeys(text);

	}

	boolean selectByText(List<WebElement> elements, String text) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
		for (WebElement webElement : elements) {
			if (webElement.getText().equalsIgnoreCase(text)) {
				click(webElement);
				return true;
			}
		}
		return false;
	}

	void assertNumbersNotEquals(int actual, int expected) {

		Assert.assertNotEquals(actual, expected, "The values [" + actual + "] and [" + expected + "] are equals.");
	}

	int getSize(List<WebElement> elements) throws InterruptedException {
		if (!elements.isEmpty()) {
			wait.until(ExpectedConditions.visibilityOf(elements.get(0)));
			return elements.size();
		}
		return 0;
	}

	void assertTextIgnoreCase(WebElement element, String text) throws InterruptedException {
		Assert.assertTrue(element.getText().equalsIgnoreCase(text),
				"The values [" + element.getText() + "] and [" + text + "] are not equals.");
	}

	boolean assertTextNotPresentInList(List<WebElement> options, String text) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(options.get(0)));
		for (WebElement webElement : options) {
			if (webElement.getText().equalsIgnoreCase(text)) {
				return false;
			}
		}
		return true;
	}

	boolean selectOptionListByAttribute(List<WebElement> options, String option, String attrib)
			throws InterruptedException {

		for (WebElement webElement : options) {
			if (webElement.getAttribute(attrib).equalsIgnoreCase(option)) {
				click(webElement);
				return true;
			}
		}
		return false;
	}

	boolean selectOptionCarouselByAttribute(List<WebElement> element, String option, WebElement nextBtn, String attrib)
			throws InterruptedException {

		for (WebElement webElement : element) {
			if (webElement.getAttribute(attrib).equalsIgnoreCase(option)) {
				while (!webElement.isDisplayed()) {
					click(nextBtn);
					Thread.sleep(1000);
				}
				if (webElement.getAttribute(attrib).equalsIgnoreCase(option)) {
					click(webElement);
					return true;
				}
			}
		}
		return false;
	}

	boolean isAttributePresent(WebElement element, String attribute) {

		Boolean result = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}

	public void selectOptionByLabelText(WebElement element, String text) {
		Select sel = new Select(element);
		wait.until(ExpectedConditions.visibilityOf(element));
		sel.selectByVisibleText(text);
	}

	public void selectOptionByAttribute(WebElement element, String attriVal) {
		Select sel = new Select(element);
		sel.selectByValue(attriVal);
	}

	public void selectOptionByIndex(WebElement element, int index) {
		Select sel = new Select(element);
		sel.selectByIndex(index);
	}

	public boolean isVisibleElement(WebElement element) throws InterruptedException {

		return element.isDisplayed();
		// return (element.getAttribute("visible").toString().equalsIgnoreCase("true"))
		// ? true : false;
	}

	public boolean isChecked(WebElement element) {
		return element.isSelected();
	}

	public boolean isUnChecked(WebElement element) {
		return !(element.isSelected());
	}

	public void flash(WebElement element) {

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 8; i++) {
			changeColor("rgb(0,200,0)", element);// 1
			changeColor(bgcolor, element);// 2
		}
	}

	public void scrollForElement(WebElement element) {
		JavascriptExecutor jsx = (JavascriptExecutor) driver;
		jsx.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	private void changeColor(String color, WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}

	public boolean isElementPresent(By by) {

		try {

			driver.findElement(by);
			return true;

		} catch (NoSuchElementException e) {

			return false;

		}

	}

	public static String getAlphaNumericString(int n) {
		// length is bounded by 256 Character
		byte[] array = new byte[256];
		new Random().nextBytes(array);

		String randomString = new String(array, Charset.forName("UTF-8"));

		// Create a StringBuffer to store the result
		StringBuffer r = new StringBuffer();

		// Append first 20 alphanumeric characters
		// from the generated random String into the result
		for (int k = 0; k < randomString.length(); k++) {

			char ch = randomString.charAt(k);

			if (((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {

				r.append(ch);
				n--;
			}
		}

		// return the resultant string
		return r.toString();
	}

	public WebElement waitUntilClickable(WebDriver driver, By by) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
		return element;
	}

	public void click(WebDriver driver, WebElement element) {
		wait.until(ExpectedConditions.visibilityOfAllElements(Arrays.asList(element)));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		/*
		 * log.debug("press RETURN key"); element.sendKeys(Keys.RETURN);
		 */
		log.debug("press ENTER key");
		element.sendKeys(Keys.ENTER);
	}

	public void clickByActions(WebDriver driver, WebElement element) {
		wait.until(ExpectedConditions.visibilityOfAllElements(Arrays.asList(element)));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		Actions act = new Actions(driver);
		act.moveToElement(element).click(element).build().perform();
	}

	public void clickByPosition(WebDriver driver, WebElement element) {

		Point p = element.getLocation();

		Actions act = new Actions(driver);

		act.moveToElement(element).moveByOffset(p.x, p.y).click().perform();
	}

	public WebElement waitForVisibility(WebDriver driver, By by) {
		return waitForVisibility(driver, by, 45);
	}

	public ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) {
		return new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement toReturn = driver.findElement(locator);
				if (toReturn.isDisplayed()) {
					return toReturn;
				}
				return null;
			}
		};
	}

	public WebElement waitForVisibility(WebDriver driver, By by, int waitTime) {
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		WebElement divElement = wait.until(visibilityOfElementLocated(by));
		return divElement;
	}

	public static String dateFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		sdf.setTimeZone(TimeZone.getTimeZone("IST"));
		String dateist = sdf.format(new Date(0, 0, 0));
		// dateist=dateist.replace(" ", "-").replace(":", "-");
		return dateist;
	}

	public static String getCurrentDay() {
		// Create a Calendar Object
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		// Get Current Day as a number
		int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("Today Int: " + todayInt + "\n");

		// Integer to String Conversion
		String todayStr = Integer.toString(todayInt);
		System.out.println("Today Str: " + todayStr + "\n");
		return todayStr;
	}

	public boolean isTextPresent(String str) {
		WebElement bodyElement = driver.findElement(By.tagName("body"));
		return bodyElement.getText().contains(str);
	}

	public void switchWindow(int windowCount) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(windowCount));

	}

}