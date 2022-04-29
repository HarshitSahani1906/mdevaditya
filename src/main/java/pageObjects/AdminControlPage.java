package pageObjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminControlPage {

	WebDriver driver = null;

	public AdminControlPage(WebDriver driver) {

		this.driver = driver;
	}

	private By FeedSection = By.xpath("//div[@class='sb_feed']/span[1]/span[1]");
	private By PostSection = By.xpath("//div[@class='sb_feed']/span[2]/span[1]");
	private By CampaignSection = By.xpath("//div[@class='sb_feed']/span[3]/span[1]");
	private By RewardSection = By.xpath("//div[@class='sb_feed']/span[4]/span[1]");
	private By UserSection = By.xpath("//div[@class='sb_feed']/span[5]/span[1]");
	private By BroadcastSection = By.xpath("//div[@class='sb_feed']/span[6]/span[1]");
	private By FeedInternalContent = By.xpath("//md-tab-item[@data-tab-id='internal-content']/span[1]");
	private By FeedExternalContent = By.xpath("//md-tab-item[@data-tab-id='external-content']/span[1]");
	private By FeedSource = By.xpath("//md-tab-item[@data-tab-id='sources']/span[1]");
	private By FeedSentimentalAlert = By.xpath("//md-tab-item[@data-tab-id='sentiment-alert']/span[1]");
	private By PostPendingTab = By.xpath("//md-tab-item[@data-tab-id='pending']/span[1]");
	private By PostApprovedTab = By.xpath("//md-tab-item[@data-tab-id='approved']/span[1]");
	private By PostArchivedTab = By.xpath("//md-tab-item[@data-tab-id='archived']/span[1]");
	private By PostSentimentAlertTab = By.xpath("//md-tab-item[@data-tab-id='sentiment-alert']/span[1]");
	private By PostApproveToastMessage=By.xpath("//div[@ng-bind-html='message']");
	private By PostDeclineToastMessage=By.xpath("//div[@ng-bind-html='message']");
	private By DeclineSUbmitResponseButton=By.xpath("//form[@name='declinePostForm']/div[2]/div[1]/button");
	private By DeclineResponseTextArea=By.xpath("//form[@name='declinePostForm']/div[1]/div[1]/textarea");

	public WebElement getFeedSection() {

		return driver.findElement(FeedSection);

	}
	public WebElement getDeclineSUbmitResponseButton() {

		return driver.findElement(DeclineSUbmitResponseButton);

	}
	public WebElement getPostDeclineToastMessage() {

		return driver.findElement(PostDeclineToastMessage);

	}
	public WebElement getDeclineResponseTextArea() {

		return driver.findElement(DeclineResponseTextArea);

	}
	public WebElement getPostApproveToastMessage() {

		return driver.findElement(PostApproveToastMessage);

	}

	public WebElement getPostSection() {

		return driver.findElement(PostSection);

	}

	public WebElement getCampaignSection() {

		return driver.findElement(CampaignSection);

	}

	public WebElement getRewardSection() {

		return driver.findElement(RewardSection);

	}

	public WebElement getUserSection() {

		return driver.findElement(UserSection);

	}

	public WebElement getBroadcastSection() {

		return driver.findElement(BroadcastSection);

	}

	public WebElement getFeedInternalContent() {

		return driver.findElement(FeedInternalContent);

	}

	public WebElement getFeedExternalContent() {

		return driver.findElement(FeedExternalContent);

	}

	public WebElement getFeedSource() {

		return driver.findElement(FeedSource);

	}

	public WebElement getFeedSentimentalAlert() {

		return driver.findElement(FeedSentimentalAlert);

	}

	public WebElement getPostPendingTab() {

		return driver.findElement(PostPendingTab);

	}

	public WebElement getPostApprovedTab() {

		return driver.findElement(PostApprovedTab);

	}

	public WebElement getPostArchivedTab() {

		return driver.findElement(PostArchivedTab);

	}

	public WebElement getPostSentimentAlertTab() {

		return driver.findElement(PostSentimentAlertTab);

	}

	public WebElement getPendingPostApproveButton() {

		return driver.findElement(By.xpath("//div[@id='pending_post_row0']/span[6]/p/a[1]"));
	}
	public WebElement getPendingPostDeclineButton() {

		return driver.findElement(By.xpath("//div[@id='pending_post_row0']/span[6]/p/a[2]"));
	}
	
	public WebElement getPendingPostUser() {

		return driver.findElement(By.xpath("//div[@id='pending_post_row0']/span[1]/h6"));
	}
	public WebElement getPendingPostTittle() {

		return driver.findElement(By.xpath("//div[@id='pending_post_row0']/span[2]/h6/a[1]"));
	}
	public WebElement getPendingPostCategory() {

		return driver.findElement(By.xpath("//div[@id='pending_post_row0']/span[3]/h6"));
	}
	public WebElement getPendingPostCreatedOn() {

		return driver.findElement(By.xpath("//div[@id='pending_post_row0']/span[4]/h6"));
	}
	public WebElement getPendingPostLandingPage() {

		return driver.findElement(By.xpath("//div[@id='pending_post_row0']/span[5]/h6"));
	}

	public WebElement getApprovedPostUser() {

		return driver.findElement(By.xpath("//div[@id='approved_post_row0']/span[1]/h6"));
	}
	public WebElement getApprovedPostTittle() {

		return driver.findElement(By.xpath("//div[@id='approved_post_row0']/span[2]/h6/a[1]"));
	}
	public WebElement getApprovedPostCategory() {

		return driver.findElement(By.xpath("//div[@id='approved_post_row0']/span[3]/h6"));
	}
	public WebElement getApprovedPostCreatedOn() {

		return driver.findElement(By.xpath("//div[@id='approved_post_row0']/span[4]/h6"));
	}
	public WebElement getapprovedPostLandingPage() {

		return driver.findElement(By.xpath("//div[@id='approved_post_row0']/span[5]/h6"));
	}

}
