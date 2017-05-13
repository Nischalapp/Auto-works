package com.w3w.generic.functions;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

public class TakeScreenshot {
	WebDriver driver;
	File scrFile;
	public TakeScreenshot(WebDriver driver){

		this.driver = driver;
	}

	public void screenCapture(String fileName) throws IOException{				
		try{
			scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);  

			FileUtils.copyFile(scrFile, new File(fileName));
			
			Thread.sleep(3000);

			//BufferedImage bufferedImage;

//			try {
//
//				//read image file
//				bufferedImage = ImageIO.read(new File(fileName));
//
//				// create a blank, RGB, same width and height, and a white background
//				BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
//						bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
//				newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
//
//				String[] converttoJPEG = fileName.split(".png");
//
//				System.out.println(converttoJPEG[0]);
//
//				String JPEG_filename =converttoJPEG[0]+".jpg";
//
//				System.out.println(JPEG_filename);
//
//				// write to jpeg file
//				ImageIO.write(newBufferedImage, "jpg", new File(JPEG_filename));
//
//				System.out.println("Converted to JPEG");
//
//			} catch (IOException e) {
//
//				e.printStackTrace();	
//
//			}	
			Reporter.log("Screenshot is captured and stored in your drive",true);			
		}catch(Exception ex){
			Reporter.log("Screenshot is not captured",true);	
			Assert.fail("Screenshot is not captured");
		}			
	}
}
