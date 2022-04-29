package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Resources.base;

public class HubPage extends base {

	public static Logger log = LogManager.getLogger(base.class.getName());
	public WebDriver driver ;
	WebDriverWait w;

	public HubPage(WebDriver driver) {
		// TODO Constructor
		this.driver = driver;
		new WebDriverWait(driver, 3);

	}

	private By MyProfileLogo = By.cssSelector("img[class ='img-cover']");
	private By MyProfileButton = By.linkText("My Profile");
	private By AdminControlButton = By.linkText("Admin Control");

	private By TourExitButon = By.xpath("//a[text()='Exit']");
	private By LogoutButton = By.xpath("//span[text()='Logout']");
	private By LearningGridButton=By.xpath("//span[text()='The Learning Grid']");
	private By MySpaceButton=By.xpath("//span[text()='My Space']");
	private By LeaderboardButton=By.xpath("//span[text()='Leaderboard']");
	private By MyCampaignsButton=By.xpath("//span[text()='My Campaigns']");
	private By HubButton=By.xpath("//span[text()='Hub']");
	private By ConnectHubButton=By.xpath("//span[@id='connectHubSpan']");
	private By CreatePostButton=By.xpath("//span[@id='create_post_btn']");

	public WebElement getMyProfileLogo() {
		// System.out.println("inside get mail method");
		return driver.findElement(MyProfileLogo);

	}
	public WebElement getAdminControlButton() {
		// System.out.println("inside get mail method");
		return driver.findElement(AdminControlButton);

	}
	public boolean getAdminControlButtonPresence() {
		// System.out.println("inside get mail method");
		return driver.findElements(AdminControlButton).isEmpty();

	}

	public WebElement getLogoutButton() {
		// System.out.println("inside get mail method");
		return driver.findElement(LogoutButton);

	}
	public WebElement getHubButton() {
		// System.out.println("inside get mail method");
		return driver.findElement(HubButton);

	}
	

	public WebElement getTourExitButon() {
		// System.out.println("inside get mail method");
		return driver.findElement(TourExitButon);

	}

	public WebElement getMyProfileButton() {
		// System.out.println("inside get mail method");
		return driver.findElement(MyProfileButton);

	}
	public WebElement getLearningGridButton() {
		// System.out.println("inside get mail method");
		return driver.findElement(LearningGridButton);

	}
	public WebElement getMySpaceButton() {
		// System.out.println("inside get mail method");
		return driver.findElement(MySpaceButton);

	}
	public WebElement getLeaderboardButton() {
		// System.out.println("inside get mail method");
		return driver.findElement(LeaderboardButton);

	}
	public WebElement getMyCampaignsButton() {
		// System.out.println("inside get mail method");
		return driver.findElement(MyCampaignsButton);

	}
	
	public WebElement getConnectHubButton() {
		// System.out.println("inside get mail method");
		return driver.findElement(ConnectHubButton);

	}
	public WebElement getCreatePostButton() {
		// System.out.println("inside get mail method");
		return driver.findElement(CreatePostButton);

	}
	
	

}
