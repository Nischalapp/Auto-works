package com.w3w.generic.functions;

import static com.codeborne.selenide.Selenide.$;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.w3w.repository.Locator_Interface;
import com.w3w.repository.Page;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;



//Class description: 
//*****This class contains generic methods means commons methods for all features like Login,Create Account,Navigation etc*****\\


public class W3WGenericfunctions extends Page implements Locator_Interface{

	//WebDriver driver;
	static Collator coll = Collator.getInstance(Locale.US);
	static MobileDriver mdriver = (MobileDriver)driver;

	public W3WGenericfunctions(WebDriver driver) {
		super(driver);	

	}

	//Method to click on any button on screen
	/* Author: Nischal Sinha
	 *Purpose: Click on CTA button
	 *Parameters: NA
	 * Date: 
	 */	

	public static void clickOnButton(String locatorName){				
		try{

			WebDriverWait wait = new WebDriverWait(driver,30);
			WebElement clickableButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath(locatorName)));
			//WebElement clickableButton = driver.findElement(By.xpath(locatorName));
			if(clickableButton.isDisplayed()){
				Reporter.log("Button "+locatorName+" was clicked",true);
				clickableButton.click();
			}						
		}catch(Exception ex){
			Reporter.log("Button "+locatorName+" was not clicked ",true);
			Assert.fail("Button "+locatorName+" was not clicked");			
		}
	}


	/*
	 * isObject  displayed
	 * 
	 */
	public static boolean isObjectDisplayed(String xpathofObject)
	{
		try{
			WebElement clickableButton = driver.findElement(By.xpath(xpathofObject));
			if(clickableButton.isDisplayed()){
				Reporter.log(""+xpathofObject+" object is displayed",true);
				return true;
			}
			else
			{
				Reporter.log(""+xpathofObject+" object is not displayed",false);
				Assert.fail(""+xpathofObject+" object is not displayed");	
				return false;
			}
		}catch(Exception ex){

			return false;
		}
	}
	//Method to validate expected strings on UI
	/* Author: Nischal Sinha
	 *Purpose: validate expected strings on UI
	 *Parameters: NA
	 * Date: 
	 */	

	public boolean verifyUIString(String expectedString, String locatorName, String devicePlatform){
		String actualString =null;
		boolean matchFlag = false;
		try{			
			WebElement UIName= driver.findElement(By.xpath(locatorName));

			if(devicePlatform.equalsIgnoreCase("iOS")){
				actualString= UIName.getAttribute("label");
			}else{				
				actualString= UIName.getAttribute("text");
			}			
			if(W3WGenericfunctions.isdataVerifiedUnicode(actualString, expectedString)){
				Thread.sleep(1000);
				Reporter.log("Expected String = "+expectedString+" matches with actual string",true);
				matchFlag=true;
			}			
		}catch(Exception ex){			
			Reporter.log("Expected String = "+expectedString+" does not match with actual string",true);
			Assert.fail("Expected String = "+expectedString+" does not match with actual string");	
		}
		return matchFlag;
	}


	//Method to compare Expected and Actual strings 
	/* Author: Nischal Sinha
	 *Purpose: compare Expected and Actual strings
	 *Parameters: NA
	 * Date:
	 */		

	public static boolean isdataVerifiedUnicode(String Actual,String ExpectedStr)
	{
		coll.setStrength(Collator.PRIMARY);
		//String updatedjob = $(By.xpath(ObjName)).getText();	
		if(coll.compare(Actual,ExpectedStr)==0){
			return true ;
		}
		return false ;
	}


	//Method to input text into any text field:
	/* Author: Nischal Sinha
	 *Purpose: input text into any text field
	 *Parameters: NA
	 * Date: 
	 */		

	public static void inputFormData(String ElementName,String InputText , String devicePlatform){		
		try{			
			WebElement InputElement,InputElementsendText;

			if(devicePlatform.equalsIgnoreCase("Android")){

				System.out.println("//*[@text='"+ElementName+"' or @text='"+ElementName.toUpperCase()+"']/../*[2]");
				InputElement =  $(By.xpath("//*[@text='"+ElementName+"'or @text='"+ElementName.toUpperCase()+"']/../*[2]"));			
				InputElementsendText =  $(By.xpath("//*[@text='"+ElementName+"'or @text='"+ElementName.toUpperCase()+"']/../*[2]"));
			}
			else{
				InputElement =  $(By.xpath("//*[@label='"+ElementName+"' or @label='"+ElementName.toUpperCase()+"']/following-sibling::*[1]"));			
				InputElementsendText =  $(By.xpath("//*[@label='"+ElementName+"' or @label='"+ElementName.toUpperCase()+"' ]/following-sibling::*[1]"));
			}
			if(InputElement.isDisplayed()){
				Thread.sleep(2000);
				Reporter.log(""+ElementName+" input field is displayed on page",true);
				InputElement.click();
				System.out.println("Clicked on "+ElementName);
				Thread.sleep(1000);
				InputElementsendText.sendKeys(InputText);
				Thread.sleep(2000);
				if(devicePlatform.equalsIgnoreCase("iOS")){
					W3WGenericfunctions.clickOnButton(Done_Button);					
				}
				Thread.sleep(2000);
			}			
		}catch(Exception ex){
			Reporter.log(""+ElementName+" input field is not displayed on page",true);
			Assert.fail(""+ElementName+" input field is not displayed on page");			
		}
	}

	//Method to get the error message text and verify on screen:
	/* Author: Nischal Sinha
	 *Purpose: Get the error message text and verify on screen
	 *Parameters: NA
	 * Date: 
	 */	

	public static String validateErrorMessage(String locatorName, String devicePlatform){

		WebElement errorMessageLocator;
		String errorMessage=null;

		try{			
			errorMessageLocator = driver.findElement(By.xpath(locatorName));
			if(errorMessageLocator.isDisplayed()){
				Reporter.log("Error message is displayed on screen",true);
				if(devicePlatform.equalsIgnoreCase("iOS")){
					errorMessage = errorMessageLocator.getAttribute("label");
					Reporter.log("Following Error message is displayed on screen"+" "+errorMessage,true);
				}else{
					errorMessage = errorMessageLocator.getAttribute("text");
				}
			}
		}catch(Exception ex){
			Reporter.log("Error message is not displayed on screen"+" "+errorMessage,true);			
		}
		return errorMessage;
	}

	//Method to get the error message text and verify on screen:
	/* Author: Nischal Sinha
	 *Purpose: Get the error message text and verify on screen
	 *Parameters: NA
	 * Date: 
	 */	


	public static boolean verifylaunch(String GetStartedButton,String devicePlatform){

		boolean launchFlag=false;

		try{			

			//Click on "GET STARTED" button to navigate to Notification screen. 			
			clickOnButton(GetStartedButton);

			Thread.sleep(3000);

			//Verify Login Page
			WebElement LoginPageLocator = driver.findElement(By.xpath(""));

			if(LoginPageLocator.isDisplayed()){
				launchFlag=true;	
				Reporter.log("Login Page launched sucessfully",true);
			}			
		}catch(Exception ex){
			Reporter.log("Login Page not launched sucessfully",true);

		}		
		return launchFlag;		
	}


	//Method to Scroll to the end of the page and click on Agree button:
	/* Author:
	 *Purpose: Scroll to the end of the page and click on Agree button
	 *Parameters: NA
	 * Date: 
	 */	

	public static void ClickOnTermsAndConditions(String AgreeDisAgree, String devicePlatform) throws InterruptedException
	{
		if(devicePlatform.equalsIgnoreCase("android")){
			for(int i=0;i<5;i++){
				System.out.println("iteration="+i);
				TouchAction action = new TouchAction((PerformsTouchActions) driver);
				action.press(460, 1518).waitAction(3000).moveTo(90, 324).release().perform();   		
				Thread.sleep(3000);
			}
		}else{

			//Add iOS code later
		}

		//Click on AgreeButton
		clickOnButton(AgreeDisAgree);		
	}


	//Method to Refresh the Page:
	/* Author:
	 *Purpose: Refresh the Page
	 *Parameters: NA
	 * Date: 
	 */	
	public static void refreshPage(String devicePlatform) throws InterruptedException{				
		if(devicePlatform.equalsIgnoreCase("android")){
			for(int i=0;i<5;i++){
				System.out.println("iteration="+i);
				TouchAction action = new TouchAction((PerformsTouchActions) driver);
				action.press(45, 1202).waitAction(3000).moveTo(45, 1496).release().perform();   			
				Thread.sleep(2000);
			}
		}else{
			for(int i=0;i<5;i++){
				System.out.println("iteration="+i);
				TouchAction action = new TouchAction((PerformsTouchActions) driver);
				action.press(0, 278).waitAction(3000).moveTo(0, 375).release().perform();   		
				Thread.sleep(2000);
			}
		}	
	}

	//Method to Pull to Refresh the Rewards,Offers and Gift Cards in Wallet:
	/* Author: 
	 *Purpose: Pull to Refresh the Rewards,Offers and Gift Cards in Wallet
	 *Parameters: NA
	 * Date: 
	 */	

	public static void PulltoRefreshInsideWallet(String devicePlatform) throws InterruptedException
	{
		if(devicePlatform.equalsIgnoreCase("android")){
			for(int i=0;i<5;i++){
				System.out.println("iteration="+i);
				TouchAction action = new TouchAction((PerformsTouchActions) driver);
				action.press(45, 1202).waitAction(3000).moveTo(45, 1496).release().perform();   			
				Thread.sleep(2000);
			}
		}else{
			for(int i=0;i<5;i++){
				System.out.println("iteration="+i);
				TouchAction action = new TouchAction((PerformsTouchActions) driver);
				action.press(0, 278).waitAction(3000).moveTo(0, 375).release().perform();   		
				Thread.sleep(2000);
			}
		}	
	}

	//Method to scroll to the bottom of the screen:
	/* Author: 
	 *Purpose: Scroll to the bottom of the application's screen
	 *Parameters: device platform
	 * Date: 
	 */	
	public static void ScrollToBottomOfScreen(String devicePlatform) throws InterruptedException
	{
		if(devicePlatform.equalsIgnoreCase("android")){
			for(int i=0;i<5;i++){
				System.out.println("iteration="+i);
				TouchAction action = new TouchAction((MobileDriver) driver);
				action.press(460, 1518).waitAction(3000).moveTo(90, 324).release().perform();   		
				Thread.sleep(3000);
			}
		}else{

			//Add iOS code later
		}	
	}


	// Method to clear the text from any textFeild
	/* Author: Nischal Sinha
	 *Purpose: Method to clear the text from any textFeild
	 *Parameters:ElementlabelName
	 * Date Modified: 
	 */	
	public void clearTextFeild(String ElementlabelName,String devicePlatform){				

		WebElement ClearButton ;
		try{
			if(devicePlatform.equalsIgnoreCase("Android")){
				//				System.out.println("//*[@text='"+ElementlabelName+"' or @text='"+ElementlabelName.toUpperCase()+"']/../*[2]");
				//				InputElement =  $(By.xpath("//*[@text='"+ElementlabelName+"'or @text='"+ElementlabelName.toUpperCase()+"']/../*[2]"));			
				//				InputElementsendText =  $(By.xpath("//*[@text='"+ElementlabelName+"'or @text='"+ElementlabelName.toUpperCase()+"']/../*[2]"));				
				ClearButton = $(By.xpath("//*[contains(@text,'"+ElementlabelName+"') or contains(@text,'"+ElementlabelName.toUpperCase()+"')]"));				
			}
			else{
				//				InputElement =  $(By.xpath("//*[@label='"+ElementlabelName+"' or @label='"+ElementlabelName.toUpperCase()+"']/following-sibling::*[1]"));			
				//				InputElementsendText =  $(By.xpath("//*[@label='"+ElementlabelName+"' or @label='"+ElementlabelName.toUpperCase()+"' ]/following-sibling::*[1]"));

				ClearButton = $(By.xpath("//*[contains(@label,'"+ElementlabelName+"') or contains(@label,'"+ElementlabelName.toUpperCase()+"')]"));
			}
			if(ClearButton.isDisplayed()){
				Thread.sleep(2000);
				Reporter.log(""+ElementlabelName+" input field is displayed on page",true);
				ClearButton.click();
				System.out.println("Clicked on "+ElementlabelName);	
				if(devicePlatform.equalsIgnoreCase("iOS")){
					W3WGenericfunctions.clickOnButton(Done_Button);					
				}
				Thread.sleep(2000);
			}			
		}catch(Exception ex){
			Reporter.log("Clear button is not displayed for "+ElementlabelName+"",true);
			Assert.fail("Clear button is not displayed for "+ElementlabelName+"");
		}	
	}


	// Method to get the value of text field
	/* Author: Nischal Sinha
	 *Purpose: Method to get the value of text field
	 *Parameters:ElementlabelName
	 * Date Modified: 
	 */	

	public String getTextFieldValue(String ElementlabelName,String devicePlatform){			
		String setinputText = null;		
		WebElement InputElement,InputElementsendText ;
		try{

			if(devicePlatform.equalsIgnoreCase("android")){
				System.out.println("//*[@text='"+ElementlabelName+"' or @text='"+ElementlabelName.toUpperCase()+"']/../*[2]");
				InputElement =  $(By.xpath("//*[@text='"+ElementlabelName+"'or @text='"+ElementlabelName.toUpperCase()+"']/../*[2]"));			
				InputElementsendText =  $(By.xpath("//*[@text='"+ElementlabelName+"'or @text='"+ElementlabelName.toUpperCase()+"']/../*[2]"));
			}else{
				InputElement =  $(By.xpath("//*[@label='"+ElementlabelName+"' or @label='"+ElementlabelName.toUpperCase()+"']/following-sibling::*[1]"));			
				InputElementsendText =  $(By.xpath("//*[@label='"+ElementlabelName+"' or @label='"+ElementlabelName.toUpperCase()+"' ]/following-sibling::*[1]"));
			}			
			if(devicePlatform.equalsIgnoreCase("android")){
				setinputText = InputElementsendText.getText();	
			}else{
				setinputText = InputElementsendText.getAttribute("value");	
			}
			Reporter.log("setinputText is present ",true);
		}catch(Exception ex){
			Reporter.log("Error in getting set inputtext from textField",true);
			Assert.fail("Error in getting set inputtext from textField");			
		}
		return setinputText;					
	}
}