package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import Resources.base;

public class MySpacePage extends base {
	
	public static Logger log = LogManager.getLogger(base.class.getName());
	public WebDriver driver ;
	WebDriverWait w;

	public MySpacePage(WebDriver driver) {
		// TODO Constructor
		this.driver = driver;
		new WebDriverWait(driver, 3);

	}
	
	private By MyPublishedPostButton=By.xpath("//md-tab-item[@data-tab-id='my-published-posts']");
	private By MyPendingPostButton=By.xpath("//md-tab-item[@data-tab-id='my-pending-posts']");
	private By MyDraftButton=By.xpath("//md-tab-item[@data-tab-id='my-drafts']");
	private By MyScheduledShareButton=By.xpath("//md-tab-item[@data-tab-id='my-scheduled-shares']");
	private By MyActivitesButton=By.xpath("//md-tab-item[@data-tab-id='my-activities']");
	private By DeleteConfirmationButton=By.xpath("//button[@oco-id='mySpaceDiscardDraft']");
	private By DraftDeleteToastMessage=By.xpath("//div[@ng-bind-html='message']");
	private By AdminDeclineMessageTextArea=By.xpath("//div[@id='admin-message']");
	private By CloseDialogBox=By.xpath("//button[@id='admin-message-close']");
	
	
	
	public WebElement getMyPublishedPostButton() {
		return driver.findElement(MyPublishedPostButton);

	}
	public WebElement getCloseDialogBox() {
		return driver.findElement(CloseDialogBox);

	}
	public WebElement getAdminDeclineMessageTextArea() {
		return driver.findElement(AdminDeclineMessageTextArea);

	}
	public WebElement getDraftDeleteToastMessage() {
		return driver.findElement(DraftDeleteToastMessage);

	}
	public WebElement getDeleteConfirmationButton() {
		return driver.findElement(DeleteConfirmationButton);

	}
	public WebElement getMyPendingPostButton() {
	
		return driver.findElement(MyPendingPostButton);

	}
	public WebElement getMyDraftButton() {
	
		return driver.findElement(MyDraftButton);

	}
	public WebElement getMyScheduledShareButton() {
	
		return driver.findElement(MyScheduledShareButton);

	}
	
	public WebElement getMyActivitesButton() {
	
		return driver.findElement(MyActivitesButton);

	}
	
	public WebElement getPostHeadline() {
		
		return driver.findElement(By.xpath("//div[@id='draft_row0']/div[1]/h6"));	
	}
	public WebElement getPostDescription() {
		
		return driver.findElement(By.xpath("//div[@id='draft_row0']/div[2]/h6"));	
	}
	public WebElement getPostUpdatedOn() {
		
		return driver.findElement(By.xpath("//div[@id='draft_row0']/div[3]/h6"));	
	}
	public WebElement getPostEditButton() {
		
		return driver.findElement(By.xpath("//div[@id='draft_row0']/div[4]/span[1]"));	
	}
	public WebElement getPostDeleteButton() {
		
		return driver.findElement(By.xpath("//div[@id='draft_row0']/div[4]/span[3]"));	
	}
	public WebElement getPendingPostHeadline() {
		
		return driver.findElement(By.xpath("//div[@id='pending_row0']/div[2]/h6"));	
	}
	public WebElement getPendingPostDescription() {
		
		return driver.findElement(By.xpath("//div[@id='pending_row0']/div[3]/h6"));	
	}
	public WebElement getPendingPostCategory() {
		
		return driver.findElement(By.xpath("//div[@id='pending_row0']/div[4]/h6"));	
	}
	public WebElement getPendingPostUpdatedOn() {
		
		return driver.findElement(By.xpath("//div[@id='pending_row0']/div[5]/h6"));	
	}
public WebElement getDecineMessageButton() {
		
	return driver.findElement(By.xpath("//div[@id='pending_row0']/div[5]/h6/i"));	
	}
	

}
