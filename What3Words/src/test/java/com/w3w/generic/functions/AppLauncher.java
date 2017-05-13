package com.w3w.generic.functions;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;


public class AppLauncher { 
	DesiredCapabilities caps ;

	// Sauce username 
	public static String username = ""; 

	public static  ThreadLocal<AppiumDriver<WebElement>> driver =new ThreadLocal<AppiumDriver<WebElement>>();

	// public static ThreadLocal<AppiumDriver<IOSElement>> driver = new ThreadLocal<AppiumDriver<IOSElement>>();



	public  AppiumDriver<WebElement> getWebDriver() { 
		return driver.get(); 
	} 



	public String getSessionId() { 
		return sessionId.get(); 
	} 

	// Sauce access key 
	public static String accesskey = ""; 

	/** 
	 * ThreadLocal variable which contains the Sauce Job Id. 
	 */ 	
	public static ThreadLocal<String> sessionId = new ThreadLocal<String>(); 




	//Mobile driver
	public  WebDriver mobileDriver(String appPackage, String platformName, String platformVersion, String deviceName, String deviceOrientation,String TestName,boolean simulator, 
			String browserName, String HideKeyboard) throws MalformedURLException {

		if(simulator)
		{
			if(platformName.equalsIgnoreCase("Android"))
			{
				caps = DesiredCapabilities.android();
				caps.setCapability("deviceName",deviceName);
				caps.setCapability("deviceOrientation",deviceOrientation);
				caps.setCapability("platformVersion",platformVersion);				
				caps.setCapability("platformName",platformName);
				caps.setCapability("appPackage", "");
				caps.setCapability("browserName", ""); 				
				caps.setCapability("app","sauce-storage:");
			}
			else if(platformName.equalsIgnoreCase("iOS"))
			{	

				System.out.println("Test" +platformName);    		
				if(deviceName.contains("iPad"))

				{
					caps = DesiredCapabilities.ipad();
				}
				else{
					caps = DesiredCapabilities.iphone();
				}
				caps.setCapability("deviceName",deviceName);
				caps.setCapability("deviceOrientation", deviceOrientation);
				caps.setCapability("platformName", platformName);
				caps.setCapability("platformVersion", platformVersion);	
				caps.setCapability("browserName", "");
				caps.setCapability("app","sauce-storage:");
			}

			caps.setCapability("appiumVersion", "1.5.3");

			if(platformName.equalsIgnoreCase("Android"))
			{
				driver.set(new AndroidDriver<WebElement>(new URL("http://" +username + ":" + accesskey + "@ondemand.saucelabs.com:80/wd/hub"),  caps));
			}
			else
			{

				driver.set(new IOSDriver<WebElement>(new URL("http://" +username + ":" + accesskey + "@ondemand.saucelabs.com:80/wd/hub"),  caps));
			}
		}
		else
		{

			caps = new DesiredCapabilities();
			caps.setCapability("appium-version", "1.5.3");
			caps.setCapability("platformName", platformName);
			caps.setCapability("platformVersion", platformVersion);
			caps.setCapability("deviceOrientation", deviceOrientation);			
			caps.setCapability("deviceName", deviceName);	
			caps.setCapability("browserName", browserName);
			if(platformName.equalsIgnoreCase("android")){
				caps.setCapability("unicodeKeyboard", HideKeyboard);
			}

			if(platformName.equalsIgnoreCase("Android") && browserName.equalsIgnoreCase(""))
			{	
				caps.setCapability("app", "");
				driver.set(new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),  caps));
			}
			else if(platformName.equalsIgnoreCase("iOS") && browserName.equalsIgnoreCase(""))
			{				
				caps.setCapability("app", "");
				driver.set(new IOSDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),  caps));
			}	
			else if (platformName.equalsIgnoreCase("Android") && browserName.equalsIgnoreCase("browser")) {
				caps.setCapability(CapabilityType.BROWSER_NAME, browserName);
				driver.set(new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),  caps));

			}
			else if (platformName.equalsIgnoreCase("iOS") && browserName.equalsIgnoreCase("safari")){
				caps.setCapability(CapabilityType.BROWSER_NAME, browserName);
				driver.set(new IOSDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),  caps));
			}
		}
		// set current sessionId         
		String id = ((RemoteWebDriver) getWebDriver()).getSessionId().toString(); 
		sessionId.set(id); 
		// print out sessionId and jobname for consumption by Sauce Jenkins plugin 
		System.out.println(String.format("SauceOnDemandSessionID=%1$s job-name=%2$s", id, TestName)); 
		// returns the desired driver object  
		return driver.get(); 
	} 

} 
