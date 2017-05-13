package com.w3w.repository;

public interface Locator_Interface {

	//UI Verification : Showcase Page, Notification, GeoLocation, Login	
	String Not_to_Love_Text_Scroll ="";
	String Only_a_few_Scroll ="";
	String You_Will_Love_Scroll="//*[contains(@label,'love this')]";
	String SignUp_Today="//*[contains(@label,'Sign up today to earn')]"; 
	String Stay_In_The_loop_Text_Scroll ="";
	String AlwaysAllow="//*[@label='Always Allow' or @text='Always Allow']";
	String GeoLocation_Headline="//*[contains(@label,'nearby!') or contains(@text,'nearby!')]";
	String Notification_Headline="//*[@label='Stay-in-the-loop' or @resource-id='com.bathandbody.bbw.dev:id/headline_text_view']";
	String Done_Button="//*[@label='Done']";
	String Home_Navigation_Tab="//*[contains(@label,'HOME')] | (//*[contains(@resource-id,'indHolder')])[1]";
	String Email="";
	String Password="";
	




}
