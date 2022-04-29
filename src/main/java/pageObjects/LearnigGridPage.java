package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import Resources.base;

public class LearnigGridPage {

	public static Logger log = LogManager.getLogger(base.class.getName());
	public WebDriver driver ;
	

	public LearnigGridPage(WebDriver driver) {
		// TODO Constructor
		this.driver = driver;
		
	}
	private By createPostButton=By.xpath("//div[@id='gridHeaderTopBtns']/a[1]/span");
	public WebElement getCreatePostButton() {
		// System.out.println("inside get mail method");
		return driver.findElement(createPostButton);

	}
	
	
	
	

}
