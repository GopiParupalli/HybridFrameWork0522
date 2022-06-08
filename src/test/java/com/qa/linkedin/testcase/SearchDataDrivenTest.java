package com.qa.linkedin.testcase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.linkedin.base.TestBase;
import com.qa.linkedin.pages.LinkedinHomePage;
import com.qa.linkedin.pages.LinkedinLogggedinPage;
import com.qa.linkedin.pages.SearchResultsPage;
import com.qa.linkedin.util.ExcelUtils;

public class SearchDataDrivenTest extends TestBase {

	private Logger log=Logger.getLogger(SearchDataDrivenTest.class);
	private LinkedinHomePage lHmPg;
	private LinkedinLogggedinPage llPg;
	private SearchResultsPage srRPg;
	private String excelPath=System.getProperty("user.dir")+"/src/test/java/com/qa/linkedin/data/Book.xlsx";
	
	@Test
	public void doLoginTest() throws InterruptedException, IOException {
		log.debug("Started executing the doLoginTest()..." );
		lHmPg.verifyLinkedinHomePageTitle();
		lHmPg.verifyLinkedinLogo();
		lHmPg.clickSignInLink();
		lHmPg.verifyLinkedinSignInPageTitle();
		lHmPg.verifyLinkedinsigninHeaderText();
		llPg=lHmPg.doLogin(readPropertyValue("uname"),readPropertyValue("pwd"));
		//llPg.lHmPg.doLogin()
		llPg.verifyLinkedinLeggedinPgTitle();
		
		
	}
	
	@Test(dataProvider="dp",dependsOnMethods= {"doLoginTest"})
	public void doSearchTest(String s) throws InterruptedException {
		log.debug("started executing the searchTest for:  "+s);
		llPg.verifyProfileRailCard();
		srRPg=llPg.doPeopleSearch(s);
		Thread.sleep(1000);
		//long count=srRPg.getResultsCount();
		//log.debug("search results count for  :"+s+" is: "+count);
		//driver.close();
		srRPg.clickOnHomeTab();
		
	}
	@DataProvider
	public Object[][]dp() throws InvalidFormatException, IOException{
		Object[][] data=new ExcelUtils().getTestData(excelPath,"sheet1");
		return data;
	}
	

	@BeforeClass
	public void initializeObjects() {
		log.debug("initialize all the page classes");
		lHmPg=new LinkedinHomePage();
		llPg=new LinkedinLogggedinPage();
		srRPg=new SearchResultsPage();
		
	}
	@AfterClass
	public void afterClass() throws InterruptedException {
		log.debug("Started Executing the signOut Action");
		llPg.doSignOut();
	}
	
}
