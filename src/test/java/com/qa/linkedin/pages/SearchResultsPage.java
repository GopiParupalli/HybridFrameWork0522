package com.qa.linkedin.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class SearchResultsPage extends BasePageWeb {
	
	private Logger log=Logger.getLogger(SearchResultsPage.class);
	
	public SearchResultsPage() {
		
		
		PageFactory.initElements(driver,this);

	}

	String searchResultsPgTitle="Search | LinkedIn";
	
	////div[@class='t-16 t-black t-bold']
	////div[contains(@class.'search-nec__banner-card')]/a
	////div[@class='search-nec__banner-card']
	@FindBy(xpath="//div[@class='t-16 t-black t-bold']")
	private WebElement seeAllPeopleResultsLink;
	
	@FindBy(xpath="//div[@class='search-results-container']")
	private WebElement searchResultsText;
	
	@FindBy(xpath="//ul[contains(@class,'global-nav__primary-items')]/li/a")
	private WebElement homeTab;
	
	public void verifySearchResultsPgTitle() {
		log.debug("verify the linkedin search results  page title:  " + searchResultsPgTitle);
		wait.until(ExpectedConditions.titleContains(searchResultsPgTitle));
		Assert.assertTrue(driver.getTitle().contains(searchResultsPgTitle));
	}
	
	public void clickSeeAllPeopleResultsLink() throws InterruptedException {
		log.debug("wait and click on seeAllPeopleResultsLink");
		click(seeAllPeopleResultsLink);
		/*
		 * String parentWID = driver.getWindowHandle(); //switch back to parent window
		 * driver.switchTo().window(parentWID); driver.switchTo().defaultContent();
		 */
		
		
	}
	
	public long fetchResultsCount() {
		log.debug("fetch the results count text");
		String resultsTxt=searchResultsText.getText();
		log.debug("Search results text is:"+resultsTxt);
		
		 //String resultsTxt="About 2,410,000 results"
		 //split the above string using String class- split(delimiter) here delimiter is space
		
		String[] str=resultsTxt.split(" ");
		long count=0;
		if(str.length>=3) {
		//str[]=["About","2,410,000","results"]
		String s1=str[1].replace(",", "");
		//convert the String into long primitive format
		 count=Long.parseLong(s1);
		}else {
			//str[]=["300","results"]
			String s1=str[0];
			//convert the String into long primitive format
			count=Long.parseLong(s1);
		}
		return count;
		
	}

	
	public long getResultsCount() throws InterruptedException {
		verifySearchResultsPgTitle();
		if(isElementPresent(By.xpath("//a[@class='app-aware-link'][contains(.,'See all people results')]"))) {
			clickSeeAllPeopleResultsLink();
			return fetchResultsCount();
		}else {
			return fetchResultsCount();
		}
		
	}
	
	public void clickOnHomeTab() throws InterruptedException {
		log.debug("click on Home Tab");
		click(homeTab);
		
	}

}
