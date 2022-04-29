package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Resources.base;

public class CreatePostPage {
	public static Logger log = LogManager.getLogger(base.class.getName());
	public WebDriver driver;

	public CreatePostPage(WebDriver driver) {
		// TODO Constructor
		this.driver = driver;

	}

	private By PostHeadline = By.xpath("//textarea[@ng-model='postData.heading']");

	private By SubHealine = By.xpath("//textarea[@ng-model='postData.subheading']");
	private By Tags = By.xpath("//input[@ng-model='postData.tag']");
	private By PostDescriptionFrame = By.xpath("//div[@id='mceu_32']/iframe");
	private By DescriptionBoy = By.xpath("//body[@id='tinymce']");
	private By SubmitButton = By.xpath("//button[@id='learning_btn']");
	private By ConfirmationMessage = By.xpath("//div[@class='modal-body']/p");
	private By ToastMessage=By.xpath("//div[@ng-bind-html='message']");
	private By closeButtonDialogBox = By.xpath("//a[@class='close']");
	private By SaveDraftButton = By.xpath("//button[@id='draft_btn']/span");
	private By UploadImageButton=By.xpath("//button[@name='bannerImageFile']");
	private By YoutubeLinkButton=By.xpath("//button[@id='yt_btn']");
	private By YoutubeLinkTextField=By.xpath("//input[@name='videoUrl']");
	private By YoutubeLinkSubmitButton=By.xpath("//form[@name='uploadVideoForm']/div[2]/button");
	private By PostCategory;

	public WebElement getPostHeadline() {

		return driver.findElement(PostHeadline);

	}

	public WebElement getSubHealine() {

		return driver.findElement(SubHealine);

	}

	public WebElement getTags() {

		return driver.findElement(Tags);

	}

	public void getPostDescription() {
		
		driver.switchTo().frame(0);
		//driver.switchTo().frame("ui-tinymce-1_ifr");
		
	}

	public WebElement getDescriptionBoy() {

		return driver.findElement(DescriptionBoy);
	}

	public WebElement getPostCategory(int number) {
		String xpath = "//div[@id='postCategoryBlock']/div/div[" + number + "]/label";
		PostCategory = By.xpath(xpath);

		return driver.findElement(PostCategory);
	}

	public WebElement getSubmitButton() {

		return driver.findElement(SubmitButton);

	}

	public WebElement getConfirmationMessage() {

		return driver.findElement(ConfirmationMessage);

	}
	public WebElement getToastMessage() {

		return driver.findElement(ToastMessage);

	}

	public WebElement getcloseButtonDialogBox() {

		return driver.findElement(closeButtonDialogBox);

	}

	public WebElement getSaveDraftButton() {

		return driver.findElement(SaveDraftButton);

	}
	
	public WebElement getUploadImageButton() {

		return driver.findElement(UploadImageButton);

	}
	public WebElement getYoutubeLinkButton() {

		return driver.findElement(YoutubeLinkButton);

	}
	public WebElement getYoutubeLinkTextField() {

		return driver.findElement(YoutubeLinkTextField);

	}
	public WebElement getYoutubeLinkSubmitButton() {

		return driver.findElement(YoutubeLinkSubmitButton);

	}
}
