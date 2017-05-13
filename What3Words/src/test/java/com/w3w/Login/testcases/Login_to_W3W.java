package com.w3w.Login.testcases;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.codeborne.selenide.WebDriverRunner;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w3w.generic.functions.LoginGenericfunctions;
import com.w3w.generic.functions.AppLauncher;
import com.w3w.generic.functions.W3WGenericfunctions;
import com.w3w.generic.functions.TakeScreenshot;
import com.w3w.repository.Locator_Interface;

import io.appium.java_client.MobileDriver;



/* Author: Nischal Sinha

 */

public class Login_to_W3W implements Locator_Interface {

	WebDriver driver;
	SoftAssert s_assert = new SoftAssert();
	AppLauncher integratoObj = new AppLauncher();
	W3WGenericfunctions generic ;
	@SuppressWarnings("rawtypes")
	MobileDriver mdriver;	
	String Env;
	String devicePlatform = null;
	private static ExtentReports extent;
	ExtentTest test;
	String DeviceName=null;
	String PlatformVersion=null;
	String DevicePlatform=null;
	TakeScreenshot screen;
	String fileName = System.getProperty("user.dir") + "/Screenshot/";
	String TestcaseName=null;

	@DataProvider
	public Object[][] FormsData() throws Exception{

		return com.w3w.generic.functions.ExcelDataProvider.dataProvider(System.getProperty("user.dir") + "/src/test/resources/App/Login/BM1081&BM1082/LoginSucessDashboard_1081_1082.xlsx" , "LoginSuccessDashboard", "Y") ;

	}

	@SuppressWarnings("rawtypes")
	@Parameters({"appPackage","platformName", "platformVersion", "deviceName", "deviceOrientation", "TestName", "simulator","delay","browserName","HideKeyboard" })
	@BeforeMethod
	public void Setup(String appPackage,String platformName, String platformVersion, String deviceName, String deviceOrientation,
			String TestName, boolean simulator,int delay,String browserName,String HideKeyboard) throws MalformedURLException, InterruptedException {


		driver = integratoObj.mobileDriver(appPackage,platformName, platformVersion,deviceName, deviceOrientation, TestName,
				simulator, browserName,HideKeyboard);

		// Initializing the Environment 
		Env= platformName+" "+platformVersion+" "+ deviceName+" "+deviceOrientation+" "+TestName+" "+simulator;
		Thread.sleep(delay+1000);	

		mdriver = (MobileDriver)driver;
		generic  = new W3WGenericfunctions(driver);
		WebDriverRunner.setWebDriver(driver);
		devicePlatform = platformName;	
		PlatformVersion = platformVersion;
		screen = new TakeScreenshot(driver);

	}


	@Test(dataProvider="FormsData")
	public void SucessfulLogin(Hashtable<String,String> data) throws InterruptedException, IOException
	{ 

		String Testcase = data.get("TestCase"); //To use for Reporting purpose
		String Userstory =  data.get("UserStory")+"_";
		String TestCaseName=Userstory+Testcase;
		Reporter.log(TestCaseName);

		extent = new ExtentReports(System.getProperty("user.dir") + "/CustomReport/What3Words_TestReport.html", false);


		// creates a toggle for the given test, adds all log events under it    
		test = extent.startTest(TestCaseName);

		try{	
			boolean applaunchFlag = W3WGenericfunctions.verifylaunch("",devicePlatform);
			if(applaunchFlag==true){
				Reporter.log("App launched Sucessfully", true);
				
			}



			if(data.get("VerificationKey").equals("SucessfulLoginDashboard")){


				//Enter username and Password on Login Screen
				LoginGenericfunctions.login(data.get("Username"), data.get("Password"), devicePlatform);
				test.log(LogStatus.PASS, "Step 1: Enter username and Password on Login Screen");

				Thread.sleep(1000);

							
				if(W3WGenericfunctions.isObjectDisplayed("")){						
					Reporter.log("User is Navigated to Dashboard after sucessfull login",true);
					Reporter.log("TestCase Name:-"+TestCaseName+" is passed",true);
					screen.screenCapture(""+fileName+TestCaseName+""+".png");
					test.log(LogStatus.PASS, "Step 6: Verify BBW Logo Bar UI on Home screen");	
					test.log(LogStatus.INFO, "Screenshot below: " + test.addScreenCapture(""+fileName+TestCaseName+""+".png") );
				}else{
					Reporter.log("TestCase Name:-"+TestCaseName+" is failed",true);
					test.log(LogStatus.PASS, "Step 6: Verify BBW Logo Bar UI on Home screen");	
					screen.screenCapture(""+fileName+TestCaseName+""+".png");	
					test.log(LogStatus.INFO, "Screenshot below: " + test.addScreenCapture(""+fileName+TestCaseName+""+".png") );
					Assert.fail("TestCase Name:-"+TestCaseName+" is failed");					
				}						
			}
		}catch(Exception ex){				
			Reporter.log("TestCase Name:-"+TestCaseName+" is failed",true);
			test.log(LogStatus.FAIL, "TestCase Name:-"+TestCaseName+" is failed");
			screen.screenCapture(""+fileName+TestCaseName+""+".png");
			test.log(LogStatus.INFO, "Screenshot below: " + test.addScreenCapture(""+fileName+TestCaseName+""+".png") );
			Assert.fail("TestCase Name:-"+TestCaseName+" is failed");
		}			

	}

	@AfterMethod
	public void methodTeardown() throws InterruptedException{
		
		System.out.println("....Close W3W Mobile App....");
		Reporter.log("Close the app",true);
		driver.quit();
		//End the test Instance
		extent.endTest(test);
		//Calling flush writes everything to the log file
		extent.flush();

	}

}
