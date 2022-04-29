package pageObjects;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import Resources.base;

public class LandingPage extends base{
	public WebDriver driver;
	public static Logger log = LogManager.getLogger(base.class.getName());
	private By username = By.cssSelector("input[id ='username']");
	private By password = By.cssSelector("input[id ='password']");
	private By loginButton = By.cssSelector("input[name ='SignIn']");
	private By toastMessage=By.xpath("//div[@ng-bind-html='message']");
	private By errorMessage=By.xpath("//p[@id='errorMessage']");
	
	
	public LandingPage(WebDriver driver) {
		// TODO Auto-generated constructor stub

		this.driver = driver;
		
			
	}
	
	

	public WebElement getEmail() {
		//System.out.println("inside get mail method");
		return driver.findElement(username);

	}

	public WebElement getPassword() {
		//System.out.println("inside get password method");
		return driver.findElement(password);

	}

	public WebElement getLoginButton() {
		//System.out.println("inside getLoginButton method");
		return driver.findElement(loginButton);

	}
	public String  loginToastMessage() {
		//System.out.println("inside loginToastMessage method");
		//System.out.println(driver.findElement(toastMessage).getText());
		return driver.findElement(toastMessage).getText();
	}
	public String  errorMessage() {
		//System.out.println(driver.findElement(errorMessage).getText());
		//System.out.println("Harry");
	
		return driver.findElement(errorMessage).getText();
	}
	public WebElement  getToastMessage() {
		//System.out.println(driver.findElement(toastMessage).getText());
		//System.out.println("Harry");
	
		return driver.findElement(toastMessage);
	}
	
	


}
