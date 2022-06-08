package com.qa.linkedin.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class LinkedinLogggedinPage extends BasePageWeb {
	
	public static final String lHmPg = null;

	private Logger log=Logger.getLogger(LinkedinLogggedinPage.class);
	
	public LinkedinLogggedinPage() {
		
		PageFactory.initElements(driver,this);
		
	}
	@FindBy(xpath="//input[@placeholder='Search']")
	private WebElement profileRailCard;
	
	@FindBy(xpath="//img[contains(@class,'global-nav__me-photo ghost-person ember-view')]")
	private WebElement profileImageIcon;
	
	@FindBy(xpath="//a[@class='global-nav__secondary-link mv1'][contains(.,'Sign Out')]")
	private WebElement signOutLink;
	
	@FindBy(xpath="//input[contains(@class,'search-global-typeahead__input')]")
	private WebElement searchEditBox;
	
	String linkedinLoggedinPgTitle="Feed | LinkedIn";
	
	public void verifyLinkedinLeggedinPgTitle() {
		
		log.debug("verify the linkedin loggegin  page title:  " + linkedinLoggedinPgTitle);
		wait.until(ExpectedConditions.titleContains(linkedinLoggedinPgTitle));
		Assert.assertTrue(driver.getTitle().contains(linkedinLoggedinPgTitle));
	}
	
	
	public void verifyProfileRailCard() {
		
		log.debug("wait and verify the Linkedin loggegin page profile railcard");
		wait.until(ExpectedConditions.visibilityOf(profileRailCard));
		Assert.assertTrue(profileRailCard.isDisplayed());
	}
	
	public void doSignOut() throws InterruptedException {
		
		log.debug("performing the doSignOut action....");
		wait.until(ExpectedConditions.visibilityOf(profileImageIcon));
		click(profileImageIcon);
		log.debug("click on signout link...");
		wait.until(ExpectedConditions.visibilityOf(signOutLink));
		click(signOutLink);
	}
	
	public SearchResultsPage doPeopleSearch(String keyword) throws InterruptedException {
		log.debug("performing the people search for  :"+keyword);
		clearText(searchEditBox);
		log.debug("type in the search bar  :"+keyword+"  in the search edit box");
		sendKey(searchEditBox,keyword);
		log.debug("Press the ENTER key");
		searchEditBox.sendKeys(Keys.ENTER); 
		Thread.sleep(2000);
		return new SearchResultsPage();
	}

}
