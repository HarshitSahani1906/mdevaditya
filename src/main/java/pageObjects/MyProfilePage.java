package pageObjects;


import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Resources.base;

public class MyProfilePage{
	public static Logger log = LogManager.getLogger(base.class.getName());

	public WebDriver driver = null;

	public MyProfilePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		
		this.driver = driver;

	}

	private By Firstname = By.cssSelector("input[name ='firstName']");
	private By Lastname = By.cssSelector("input[name ='lastName']");
	private By FirstNameInvalidErrorMessage = By.xpath("//p[@aria-label='firstname-required']");
	private By FirstNameTooLongErrorMessage = By.xpath("//p[@aria-label='firstname-too-long']");
	private By LastNameTooLongErrorMessage = By.xpath("//p[@aria-label='lastname-too-long']");
	private By LastNameErrorInvalidMessage = By.xpath("//p[@aria-label='lastname-required']");
	
	private By FirstNameInvalidMessage=By.xpath("//p[@aria-label='firstname-invalid']");
	
	private By LastNameInvalidMessage=By.xpath("//p[@aria-label='lastname-invalid']");
	
	
	private By EmailId=By.xpath("//form[@id='form-project']/div[2]/div[2]/div/input");
	private By SaveButton = By.cssSelector("button.btn.btn-action");
	private By toastMessage=By.xpath("//div[@ng-bind-html='message']");
	private By MaleRadioButton = By.xpath("//label[@class='m-r-30 m-t-7']");
	private By FemaleRadioButton = By.xpath("//label[@class='m-t-7']");
	private By MyHeadline = By.xpath("//input[@name='headline']");
	private By OrganizationName = By.xpath("//div[@name='organisation']");
	private By JobFunction = By.xpath("//div[@name='department']");
	private By Country = By.xpath("//div[@name='country']");
	private By State = By.xpath("//div[@name='state']");
	private By City = By.xpath("//div[@name='city']");
	private By CountryCode = By.xpath("//div[@name='countryCode']");
	private By MobileNumber = By.xpath("//input[@name='mobile']");
	private By ChangePassword = By.xpath("//button[@id='btnToggleSlideUpSize']");
	private By CalenderDropDown=By.xpath("//button[@aria-label='Open calendar']");
	// ############################# Integration of Facebook Profile ###################################
	private By FacebookProfileButton = By.xpath("//div[@id='socialPlatformList']/div[2]/div/div/span/span");
	private By FacebookEmailTextBox = By.cssSelector("#email");
	private By FacebookPasswordTextBox = By.cssSelector("#pass");
	private By FacebookLoginButton = By.xpath("//div[@id='buttons']/label[2]/input");
	private By FacebookContinueButton = By.xpath("//div[@class='_6-v1']/div[2]/div/div/div/div/span");
	private By FacebookNextButton = By.xpath("//div[@aria-label='Next']");
	private By FacebookDoneButton = By.xpath("//div[@class='_6o75']/div[2]/div/div/div/div/span");
	private By FacebookOKButton = By.xpath("//div[@class='_6-v1']/div/div/div/div/span");
	private By FacebookRemoveAccountButton= By.xpath("//button[@aria-label='facebook-remove']");
	private By FacebookUpdateAccountButton= By.xpath("//button[@aria-label='facebook-update']");
	// ############################# Integration of Facebook Profile ###################################

	// ############################# Integration of LinkedIn Profile###################################
	private By LinkedInProfileButton = By.xpath("//div[@id='socialPlatformList']/div[3]/div/div/span/span");
	private By LinkedInEmailTextBox = By.xpath("//*[@id='username']");
	private By LinkedInPasswordTextBox = By.xpath("//*[@id='password']");
	private By LinkedInSignInButton = By.xpath("//button[@aria-label='Sign in']");
	private By LinkedInRemoveAccountButton= By.xpath("//button[@aria-label='linkedin-remove']");
	private By LinkedInUpdateAccountButton= By.xpath("//button[@aria-label='linkedin-update']");
	private By LinkedInAllowButton= By.xpath("//button[@id='oauth__auth-form__submit-btn']");
	// ############################# Integration of LinkedIn Profile ###################################

	//############################# Integration of Instagram Profile ###################################
	private By InstagramProfileButton = By.xpath("//div[@id='socialPlatformList']/div[4]/div/div/span/span");
	private By InstagramEmailTextBox = By.xpath("//form[@class='HmktE']/div/div[1]/div/label/input");
	private By InstagramPasswordTextBox = By.xpath("//input[@aria-label='Password']");
	private By InstagramSignInButton = By.xpath("//form[@class='HmktE']/div/div[3]/button/div");
	private By InstagramSaveInfoButton = By.xpath("//div[@class='JErX0']/button");
	private By InstagramAllowButton = By.xpath("//div[@class='yijoQ']/div/div[2]/button");
	private By InstagramRemoveAccountButton= By.xpath("//button[@aria-label='instagram-remove']");
	private By InstagramUpdateAccountButton= By.xpath("//button[@aria-label='instagram-update']");

	//############################# Integration of Instagram Profile ###################################

	public int randomNumberGenerator(int Lowerbound, int Upperbound) {

		int randomSelection = base.randomNumbergenration(Lowerbound, Upperbound);
		return randomSelection;

	}

	public long createRandomInteger(int aStart, long aEnd, Random aRandom) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		long range = aEnd - (long) aStart + 1;
		// System.out.println("range>>>>>>>>>>>"+range);

		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		// System.out.println("fraction>>>>>>>>>>>>>>>>>>>>"+fraction);

		long randomNumber = fraction + (long) aStart;

		//System.out.println("Generated : " + randomNumber);
		return randomNumber;

	}

	public WebElement getEmailId() {
		// System.out.println("inside get mail method");
		return driver.findElement(EmailId);

	}
	
	public WebElement getFirstName() {
		// System.out.println("inside get mail method");
		return driver.findElement(Firstname);

	}
	
	public WebElement getCalenderDropDown() {
		// System.out.println("inside get mail method");
		return driver.findElement(CalenderDropDown);

	}
	public WebElement getCalenderYYYYMM() {
		// System.out.println("inside get mail method");
		By YYYYMM=By.xpath("//td[@id='md-4-year-2003-2-1']");
		return driver.findElement(YYYYMM);

	}
	
	public WebElement getCalenderYYYYMMdd() {
		// System.out.println("inside get mail method");
		By YYYYMMDD=By.xpath("//td[@id='md-4-month-2003-2-26']");
		return driver.findElement(YYYYMMDD);

	}
	public  void  getDate() throws InterruptedException {
		getCalenderDropDown().click();
		Thread.sleep(1000);
		Boolean bool=true;
		WebElement element=driver.findElement(By.xpath("//td[@aria-label='May 2003']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		while(bool) {
			Boolean display =driver.findElement(By.xpath("//td[@class='md-calendar-month-label']")).isDisplayed();
			if(display.equals(true)) {
				
				//System.out.println("inside harry1");
				Actions actions = new Actions(driver);
				js.executeScript("arguments[0].scrollIntoView(true);",element);
				
				actions.moveToElement(element);
				
			
				actions.click().perform();
				log.info("For Calender Year and Month are choosen");
				WebDriverWait w = new WebDriverWait(driver, 3);
				
				WebElement date=driver.findElement(By.xpath("//td[@aria-label='Saturday May 3 2003']"));
				w.until(ExpectedConditions.elementToBeClickable(date));
				actions.moveToElement(date).click().perform();
				log.info("For Calender date is choosen");
				
				
				bool= false ;
				
			}
			else {
				//System.out.println("inside harry");
				bool=false;
			}
		}
		
		
	}
	
	public WebElement getFirstNameInvalidErrorMessage() {
		// System.out.println("inside get mail method");
		return driver.findElement(FirstNameInvalidErrorMessage);

	}
	
	public WebElement getFirstNameInvalidMessage() {
		// System.out.println("inside get mail method");
		return driver.findElement(FirstNameInvalidMessage);

	}
	
	public WebElement getLastNameInvalidMessage() {
		// System.out.println("inside get mail method");
		return driver.findElement(LastNameInvalidMessage);

	}
	
	public WebElement getFirstNameTooLongErrorMessage() {
		// System.out.println("inside get mail method");
		return driver.findElement(FirstNameTooLongErrorMessage);

	}
	
	public WebElement getLastNameInvalidErrorMessage() {
		// System.out.println("inside get mail method");
		return driver.findElement(LastNameErrorInvalidMessage);

	}
	
	public WebElement getLastNameTooLongErrorMessage() {
		// System.out.println("inside get mail method");
		return driver.findElement(LastNameTooLongErrorMessage);

	}

	public WebElement getLastname() {
		// System.out.println("inside get password method");
		return driver.findElement(Lastname);

	}

	public WebElement getSaveButton() {
		// System.out.println("inside get mail method");

		return driver.findElement(SaveButton);

	}
	
	public WebElement  getToastMessage() {
		//System.out.println(driver.findElement(toastMessage).getText());
		//System.out.println("Harry");
	
		return driver.findElement(toastMessage);
	}

	

	public WebElement getMaleRadioButton() {
		// System.out.println("inside get mail method");

		return driver.findElement(MaleRadioButton);

	}

	public WebElement getFemaleRadioButton() {
		// System.out.println("inside get mail method");

		return driver.findElement(FemaleRadioButton);

	}

	public WebElement getMyHeadline() {
		// System.out.println("inside get mail method");

		return driver.findElement(MyHeadline);

	}

	public WebElement getOrganizationName() {

		return driver.findElement(OrganizationName);

	}

	public WebElement getorganizationAttribute(int randomNumber) {
		// System.out.println("inside get mail method");

		String orgName = "#ui-select-choices-row-0-";
		String s = Integer.toString(randomNumber);
		String append = orgName + s;
		// System.out.println(append);
		By organizationAttribute1 = By.cssSelector(append);

		return driver.findElement(organizationAttribute1);

	}

	public WebElement getJobFunction() {

		return driver.findElement(JobFunction);

	}

	public WebElement getJobFunctionAttribute(int randomNumber) {
		// System.out.println("inside get mail method");

		String JobFunction = "#ui-select-choices-row-1-";
		String s = Integer.toString(randomNumber);
		String append = JobFunction + s;
		// System.out.println(append);
		By JobFunctionAttribute = By.cssSelector(append);
		

		return driver.findElement(JobFunctionAttribute);

	}

	public WebElement getCountry() {

		//System.out.println(driver.findElement(Country));
		return driver.findElement(Country);

	}

	public WebElement getCountryAttribute(int randomNumber) {
		// System.out.println("inside get mail method");

		//String country = "#ui-select-choices-row-2-";
		String SubString1 = "//div[@id='ui-select-choices-row-2-";
		String SubString2="']";
		String s = Integer.toString(randomNumber);
		String append = SubString1 + s+SubString2;
		By CountryAttribute = By.xpath(append);
		System.out.println(driver.findElement(CountryAttribute));
		return driver.findElement(CountryAttribute);

	}
	
	

	public WebElement getState() {

		//System.out.println(driver.findElement(State));
		return driver.findElement(State);

	}

	public WebElement getStateAttribute(int randomNumber) {
		//System.out.println("inside  getStateAttribute method");

		String State = "#ui-select-choices-row-3-";
		String s = Integer.toString(randomNumber);
		String append = State + s;
		//System.out.println(append);
		By StateAttribute = By.cssSelector(append);
		//System.out.println(driver.findElement(StateAttribute));
		return driver.findElement(StateAttribute);

	}

	public WebElement getCity() {

		return driver.findElement(City);

	}

	public WebElement getCityAttribute(int randomNumber) {
		// System.out.println("inside get mail method");

		String City = "#ui-select-choices-row-4-";
		String s = Integer.toString(randomNumber);
		String append = City + s;
		//System.out.println(append);
		By CityAttribute = By.cssSelector(append);
		//System.out.println(driver.findElement(CityAttribute));
		return driver.findElement(CityAttribute);

	}

	public WebElement getCountryCode() {

		return driver.findElement(CountryCode);

	}

	public WebElement getCountryCodeAttribute(int randomNumber) {
		// System.out.println("inside get mail method");

		String Code = "#ui-select-choices-row-5-";
		String s = Integer.toString(randomNumber);
		String append = Code + s;
		// System.out.println(append);
		By CountryCodeAttribute = By.cssSelector(append);
		//System.out.println(driver.findElement(CountryCodeAttribute));
		return driver.findElement(CountryCodeAttribute);
	}

	public WebElement getMobileNumber() {

		return driver.findElement(MobileNumber);

	}

	public WebElement getChangePassword() {

		return driver.findElement(ChangePassword);

	}
	
	//#############################Facebook methods #########################

	public WebElement getFacebookProfileButton() {
		// System.out.println("inside get mail method");

		return driver.findElement(FacebookProfileButton);

	}

	public WebElement getFacebookEmailTextBox() {

		return driver.findElement(FacebookEmailTextBox);

	}

	public WebElement getFacebookPasswordTextBox() {

		return driver.findElement(FacebookPasswordTextBox);

	}

	public WebElement getFacebookLoginButton() {

		return driver.findElement(FacebookLoginButton);

	}

	public WebElement getFacebookContinueButton() {

		return driver.findElement(FacebookContinueButton);

	}

	public WebElement getFacebookNextButton() {

		return driver.findElement(FacebookNextButton);

	}

	public WebElement getFacebookDoneButton() {

		return driver.findElement(FacebookDoneButton);

	}

	public WebElement getFacebookOKButton() {

		return driver.findElement(FacebookOKButton);

	}
	
	public WebElement getFacebookRemoveAccountButton() {

		return driver.findElement(FacebookRemoveAccountButton);

	}
	
	public WebElement getFacebookUpdateAccountButton() {

		return driver.findElement(FacebookUpdateAccountButton);

	}
	
	//#############################LinkedIn methods #########################

	public WebElement getLinkedInProfileButton() {

		return driver.findElement(LinkedInProfileButton);

	}

	public WebElement getLinkedInEmailTextBox() {

		return driver.findElement(LinkedInEmailTextBox);

	}

	public WebElement getLinkedInPasswordTextBox() {

		return driver.findElement(LinkedInPasswordTextBox);

	}

	public WebElement getLinkedInSignInButton() {

		return driver.findElement(LinkedInSignInButton);

	}
	
	public WebElement getLinkedInRemoveAccountButton() {

		return driver.findElement(LinkedInRemoveAccountButton);

	}
	
	public WebElement getLinkedInUpdateAccountButton() {

		return driver.findElement(LinkedInUpdateAccountButton);

	}
	public WebElement getLinkedInAllowButton() {

		return driver.findElement(LinkedInAllowButton);

	}
	
	
	//#############################Instagram methods #########################

	public WebElement getInstagramProfileButton() {

		return driver.findElement(InstagramProfileButton);

	}

	public WebElement getInstagramEmailTextBox() {

		return driver.findElement(InstagramEmailTextBox);

	}

	public WebElement getInstagramPasswordTextBox() {

		return driver.findElement(InstagramPasswordTextBox);

	}

	public WebElement getInstagramSignInButton() {

		return driver.findElement(InstagramSignInButton);

	}

	public WebElement getInstagramRemoveAccountButton() {

		return driver.findElement(InstagramRemoveAccountButton);

	}
	
	public WebElement getInstagramSaveInfoButton() {

		return driver.findElement(InstagramSaveInfoButton);

	}
	
	public WebElement getInstagramUpdateAccountButton() {

		return driver.findElement(InstagramUpdateAccountButton);

	}
	
	public WebElement getInstagramAllowButton() {

		return driver.findElement(InstagramAllowButton);

	}

}
