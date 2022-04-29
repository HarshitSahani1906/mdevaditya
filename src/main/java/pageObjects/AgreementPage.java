package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AgreementPage {
	
	WebDriver driver=null;

	public AgreementPage(WebDriver driver) {
		
		this.driver = driver;
	}
	private By AcceptCheckBox= By.xpath("//input[@id='termsOfUseCheckBox']");
	private By AgreeButton=By.xpath("//button[@id='tnc-agree']");
	
	 public WebElement getAcceptCheckBox() {
			// System.out.println("inside get mail method");
			return driver.findElement(AcceptCheckBox);

		}
	 public WebElement getAgreeButton() {
			// System.out.println("inside get mail method");
			return driver.findElement(AgreeButton);

		}

}
