package com.w3w.generic.functions;

import static com.codeborne.selenide.Selenide.$;

import java.text.Collator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.codeborne.selenide.SelenideElement;
import com.w3w.repository.Locator_Interface;
import com.w3w.repository.Page;

import io.appium.java_client.MobileDriver;


//Class description: 
//*****This class contains Login generic methods means commons methods for Login*****\\


public class LoginGenericfunctions extends Page implements Locator_Interface{

	//WebDriver driver;
	static Collator coll = Collator.getInstance(Locale.US);
	MobileDriver mdriver = (MobileDriver)driver;

	public LoginGenericfunctions(WebDriver driver) {
		super(driver);	

	}

	
	//Method to login 
	/* Author: Nischal Sinha
	 *Purpose: login into the BBW application
	 *Parameters: Username, Password , devicePlatform
	 * Date: 
	 */	
		
	public static void login(String userName, String passWord,String devicePlatform){		
		try{			
			if(!userName.equalsIgnoreCase("null")){				
				W3WGenericfunctions.inputFormData(Email, userName, devicePlatform);		
				Reporter.log("Username "+ userName+" is entered into textfield", true);				
			}			
			//For iOS
			if(devicePlatform.equalsIgnoreCase("iOS")){
				SelenideElement clickOnDone =$(By.xpath("//*[@label='Done']"));
				if(clickOnDone.exists()){
					Reporter.log("Done link is  present", true);
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					clickOnDone.click();
					Reporter.log("Clicked on  Done Link", true);
				}
			}
			if(!passWord.equalsIgnoreCase("null")){				
				W3WGenericfunctions.inputFormData(Password, passWord, devicePlatform);	
				Reporter.log("Password "+passWord+" is entered into textfield", true);				
			}					
		}catch(Exception ex){
			Reporter.log("Error in entering Username/Password into textfield", true);
			Assert.fail("Error in entering Username/Password into textfield");
		}		
	}
	
	
	
	
	
}