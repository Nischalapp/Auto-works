package com.w3w.repository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class BBWCommonPage extends Page {

	public BBWCommonPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}

	// Objects on launch page

	// <<Variables>>
	// WelcomeText
	@FindBy(xpath = "//UIATableCell[1]/UIAStaticText[@name='module_headline']")
	public WebElement WelcomeText;

	// WelcomeWindow
	@FindBy(xpath = "//*[@label='Sign In' or @text='SIGN IN' or @label='sign in']")
	public WebElement WelcomeWindow;

	// Header
	@FindBy(xpath = "//*[@name='HOME']/..")
	public WebElement Header;

	//SIGN IN Page header
	@FindBy(xpath="//*[@label='Registered Customers' or @text='Registered Customers' or @label='registered customers']")
	public WebElement SignInHeader;

	//MORE Page header
	@FindBy(xpath="//*[@label='my account' or @text='My Account']")
	public WebElement MOREHeader;

	//CreateAccountHeader
	@FindBy(xpath="//*[@label='CREATE AN ACCOUNT']")
	public WebElement CreateAccountHeader;

	// HomeOutline
	@FindBy(xpath = "//*[@name='HOME']")
	public WebElement homeOutline;

	// ShopOutline
	@FindBy(xpath = "//*[@name='SHOP']")
	public WebElement shopOutline;

	// OffersOutline
	@FindBy(xpath = "//*[@name='OFFERS']")
	public WebElement offersOutline;

	// WalletOutline
	@FindBy(xpath = "//*[@name='WALLET']")
	public WebElement walletOutline;

	// MoreOutline
	@FindBy(xpath = "//*[@name='MORE']")
	public WebElement moreOutline;

	// UIALink
	// MenuLink
	@FindBy(xpath = "//*[@name='Menu']")
	public WebElement shopContentMenuLink;

	// offersContent
	@FindBy(xpath = "//UIAStaticText[contains(@value,'Coming Soon')]")
	public WebElement offersContent;

	// walletContent
	@FindBy(xpath = "//UIAStaticText[contains(@value,'Coming Soon')]")
	public WebElement walletContent;

	// More
	@FindBy(xpath = "//UIAStaticText[contains(@value,'Coming Soon')]")
	public WebElement moreContent;

	//My Account header
	@FindBy(xpath="//*[@label='my account' or @text='My Account']")
	public WebElement MyAccount;

	//Angel Card header
	@FindBy(xpath="//*[@label='angel card' or @text='Angel Card']")
	public WebElement AngelCard;

	//Sign In Button on More Menu Page
	@FindBy(xpath="//*[@label='sign in' or @text='sign in']")
	public WebElement SignInOnMore_Menu;

	//Sign Out Button on More Menu Page
	@FindBy(xpath="//*[@label='SIGN OUT' or @text='sign out']")
	public WebElement SignOutOnMore_Menu;

	//More Menu Button
	@FindBy(xpath="//*[@label='MORE' or @text='MORE']")
	public WebElement More_Menu_Button;

	//More Menu Button
	@FindBy(xpath="//*[@label='SIGN OUT' or @content-desc='SIGN OUT']")
	public WebElement MYACCOUNT_SIGNOUT_Button;

	//More Menu Angel Card
	@FindBy(xpath="//*[@label='To manage your Angel credit card account, we're connecting you with Comenity Bank.' or @text='To manage your Angel credit card account, we're connecting you with Comenity Bank.']")
	public WebElement UIAlert_AngelCard;

	//MoreMenu Angel Continue Button
	@FindBy(xpath="//*[@name='Continue' or @content-desc='Continue']")
	public WebElement UIAlertAngel_Continue_Button;

	//Angel Card header
	@FindBy(xpath="//*[@label='love list' or @text='Love List']")
	public WebElement LoveList;

	//App Settings header
	@FindBy(xpath="//*[@label='app settings' or @text='App Settings']")
	public WebElement AppSettings;

	//Angel Card header
	@FindBy(xpath="//*[@label='privacy & notices' or @text='Privacy & Notices']")
	public WebElement PrivacyandNotices;


}
