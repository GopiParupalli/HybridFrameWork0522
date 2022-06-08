package com.qa.linkedin.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class LinkedinHomePage extends BasePageWeb {

	private Logger log = Logger.getLogger(LinkedinHomePage.class);

	// create a constructor
	public LinkedinHomePage() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "a.nav__logo-link")
	private WebElement linkedinlogo;

	@FindBy(css = "a.nav__button-secondary")
	private WebElement signInLink;

	@FindBy(css = "h1.header__content__heading ")
	private WebElement signinHeaderText;

	@FindBy(id = "username")
	private WebElement emailEditBox;

	@FindBy(name = "session_password")
	private WebElement pwdEditBox;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement signInBtn;

	String linkedinHomePageTitle = "LinkedIn: Log In or Sign Up";
	String linkedinSignInPageTitle = "LinkedIn Login, Sign in | LinkedIn";

	public void verifyLinkedinHomePageTitle() {
		log.debug("verify the linkedin home page title:  " + linkedinHomePageTitle);
		wait.until(ExpectedConditions.titleContains(linkedinHomePageTitle));
		Assert.assertTrue(driver.getTitle().contains(linkedinHomePageTitle));
	}

	public void verifyLinkedinSignInPageTitle() {
		log.debug("verify the linkedin SignIn page title:  " + linkedinSignInPageTitle);
		wait.until(ExpectedConditions.titleIs(linkedinSignInPageTitle));
		Assert.assertEquals(driver.getTitle(), linkedinSignInPageTitle);
	}

	public void verifyLinkedinLogo() {
		log.debug("wait and verify the Linkedin Logo");
		wait.until(ExpectedConditions.visibilityOf(linkedinlogo));
		Assert.assertTrue(linkedinlogo.isDisplayed());
	}

	public void verifyLinkedinsigninHeaderText() {
		log.debug("wait and verify the Linkedin SignIn Header Text");
		wait.until(ExpectedConditions.visibilityOf(signinHeaderText));
		Assert.assertTrue(signinHeaderText.isDisplayed());
	}

	public void clickSignInLink() throws InterruptedException {
		log.debug("clicking on signin link");
		click(signInLink);
	}

	public void clickSignInBtn() throws InterruptedException {
		log.debug("clicking on signin Button");
		click(signInBtn);
	}
	
	public LinkedinLogggedinPage doLogin(String uname,String pwd) throws InterruptedException {
		
		log.debug("performng the login operation");
		log.debug("clear the content in username editbox  :"+emailEditBox);
		clearText(emailEditBox);
		log.debug("type the  "+uname+"in email edit box");
		sendKey(emailEditBox,uname);
		log.debug("clear the content in password editbox  :"+pwdEditBox);
		clearText(pwdEditBox);
		log.debug("type the  "+pwd+"in password edit box");
		sendKey(pwdEditBox,pwd);
		clickSignInBtn();
		return new LinkedinLogggedinPage();
		
	}

}
