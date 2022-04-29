package encollab;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.Test;
import Resources.base;
import jdk.internal.org.jline.utils.Log;
import pageObjects.HubPage;
import pageObjects.LandingPage;
import pageObjects.MyProfilePage;

import org.testng.Assert;
import org.testng.Assert;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

public class MyProfile extends base {
	public WebDriver driver;
	public static Logger log = LogManager.getLogger(base.class.getName());
	String MethodName = null;
	WebDriverWait w;
	MyProfilePage mp;
	LandingPage lp;
	HubPage hp;

	@BeforeTest
	public void openBrowser() throws IOException, InterruptedException  {
		driver = initializeDriver();
		log.info("Driver For MyProfile Page is initialized");
		w = new WebDriverWait(driver, 3);
		mp = new MyProfilePage(driver);
		lp = new LandingPage(driver);
		hp = new HubPage(driver);
		lp.getEmail().sendKeys(prop.getProperty("username"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("password"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");
		w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
		w.until(ExpectedConditions.elementToBeClickable(hp.getMyProfileLogo()));
		hp.getMyProfileLogo().click();
		log.info("clicked on MyProfile Logo");
		hp.getMyProfileButton().click();
		log.info("Clicked on MyProfile button");
		w.until(ExpectedConditions.urlMatches(prop.getProperty("myProfilePage")));
		log.info("Fetching MyProfilePage");
		
	}
	
	@AfterTest
	public void closeBrowser() {
		hp.getMyProfileLogo().click();
		w.until(ExpectedConditions.elementToBeClickable(hp.getLogoutButton()));
		hp.getLogoutButton().click();
		w.until(ExpectedConditions.urlMatches(prop.getProperty("loginUrl")));
		driver.close();
		log.info("closing browser");
	}
	
	@Test(dataProvider = "getAccountHolderName",priority=0, groups={"MyProfile.updateUsername"})
	public void updateUsername(String firstName, String LastName, String choice)
			throws IOException, InterruptedException {
		if (choice.equals("1")) {
			MethodName = new Throwable().getStackTrace()[0].getMethodName();
			log.info("Intiating Method :' " + MethodName + " with user name of 90 charecters" + " '");

		} else if (choice.equals("2")) {
			MethodName = new Throwable().getStackTrace()[0].getMethodName();
			log.info("Intiating Method :' " + MethodName + " with user name of >90 charecters" + " '");

		} else if (choice.equals("3")) {
			MethodName = new Throwable().getStackTrace()[0].getMethodName();
			log.info("Intiating Method :' " + MethodName + " with null charecters" + " '");

		} else if (choice.equals("4")) {
			MethodName = new Throwable().getStackTrace()[0].getMethodName();
			log.info("Intiating Method :' " + MethodName + " with special case charecters charecters" + " '");

		}

		int casesForAccountHolderName = Integer.parseInt(choice);
		log.info("Dataset choice has been fetched");
		

		switch (casesForAccountHolderName) {
		case 1: {

			try {
				Thread.sleep(1000);
				mp.getFirstName().clear();
				mp.getFirstName().sendKeys(firstName);
				log.info("First name has been entered");

				mp.getLastname().clear();
				mp.getLastname().sendKeys(LastName);
				log.info("Last name has been entered");
				saveButton();
				log.info("Save Button has been clicked ");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}

			break;

		}
		case 2: {

			try {
				Thread.sleep(1000);
				mp.getFirstName().clear();
				mp.getFirstName().sendKeys(firstName);
				log.info("First name has been entered as:" + firstName);

				mp.getLastname().clear();
				mp.getLastname().sendKeys(LastName);
				log.info("Last name has been entered as:" + LastName);
				String attribute = mp.getSaveButton().getAttribute("disabled");
				String FirstNameErrorMessage = mp.getFirstNameTooLongErrorMessage().getText();
				String LastNameErrorMessage = mp.getLastNameTooLongErrorMessage().getText();
				Assert.assertTrue(
						attribute.equalsIgnoreCase("true") && FirstNameErrorMessage.equals("Name is too long.")
								&& LastNameErrorMessage.equals("Name is too long."));
				if (attribute.equalsIgnoreCase("true") && FirstNameErrorMessage.equals("Name is too long.")
						&& LastNameErrorMessage.equals("Name is too long.")) {
					log.info("Save Button is disabled ");
					mp.getFirstName().clear();
					mp.getFirstName().sendKeys("automation");
					mp.getLastname().clear();
					mp.getLastname().sendKeys("user");
					saveButton();
					log.info("Save Button has been clicked ");

				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}

			break;

		}
		case 3: {

			try {
				Thread.sleep(1000);
				mp.getFirstName().clear();
				mp.getFirstName().sendKeys(firstName);
				log.info("First name has been entered as:" + firstName);
				mp.getLastname().clear();
				mp.getLastname().sendKeys(LastName);
				log.info("Last name has been entered as:" + LastName);
				String FirstNameErrorMessage = mp.getFirstNameInvalidErrorMessage().getText();
				String LastNameErrorMessage = mp.getLastNameInvalidErrorMessage().getText();
				String attribute = mp.getSaveButton().getAttribute("disabled");
				Assert.assertTrue(attribute.equalsIgnoreCase("true")
						&& FirstNameErrorMessage.equals("First name is required/invalid.")
						&& LastNameErrorMessage.equals("Last name is required/invalid."));
				if (attribute.equalsIgnoreCase("true")
						&& FirstNameErrorMessage.equals("First name is required/invalid.")
						&& LastNameErrorMessage.equals("Last name is required/invalid.")) {
					log.info("Save Button is disabled ");
					mp.getFirstName().clear();
					mp.getFirstName().sendKeys("automation");
					mp.getLastname().clear();
					mp.getLastname().sendKeys("user");
					saveButton();
					log.info("Save Button has been clicked ");

				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
			break;

		}
		case 4: {

			try {
				Thread.sleep(1000);
				mp.getFirstName().clear();
				mp.getFirstName().sendKeys(firstName);
				log.info("First name has been entered as:" + firstName);

				mp.getLastname().clear();
				mp.getLastname().sendKeys(LastName);
				log.info("Last name has been entered as:" + LastName);
				String FirstNameErrorMessage = mp.getFirstNameInvalidMessage().getText();
				String LastNameErrorMessage = mp.getLastNameInvalidMessage().getText();
				String attribute = mp.getSaveButton().getAttribute("disabled");
				Assert.assertTrue(
						attribute.equalsIgnoreCase("true") && FirstNameErrorMessage.equals("Firstname is not valid.")
								&& LastNameErrorMessage.equals("Lastname is not valid."));
				if (attribute.equalsIgnoreCase("true") && FirstNameErrorMessage.equals("Firstname is not valid.")
						&& LastNameErrorMessage.equals("Lastname is not valid.")) {
					log.info("Save Button is disabled ");
					log.info("FirstName and Last name do not support special charecters in respective fields");
					mp.getFirstName().clear();
					mp.getFirstName().sendKeys("automation");
					mp.getLastname().clear();
					mp.getLastname().sendKeys("user");
					saveButton();

					log.info("Save Button has been clicked ");

				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}

			break;

		}

		default: {
			Assert.assertFalse(false);
			
		}

		}

	}

	@DataProvider
	public Object[][] getAccountHolderName() {
		// Rows stand for how many values per each Test
		// Column stand for how many input to be given
		Object[][] data = new Object[4][3];
		// DataSets
		// 1: correct UserName & Password
		data[0][0] = "HarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarry";
		data[0][1] = "SahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahani";
		data[0][2] = "1";

		data[1][0] = "HarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarryHarry";
		data[1][1] = "SahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahaniSahani";
		data[1][2] = "2";

		data[2][0] = "";
		data[2][1] = "";
		data[2][2] = "3";

		data[3][0] = "@#$%^";
		data[3][1] = "@#$%^";
		data[3][2] = "4";

		return data;

	}

	@Test(priority=1)
	public void validateEmailField() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		w.until(ExpectedConditions.visibilityOf(mp.getEmailId()));
		//System.out.println(mp.getEmailId().getAttribute("disabled"));
		String EmailField = mp.getEmailId().getAttribute("disabled");
		boolean bool = Boolean.parseBoolean(EmailField);
		Assert.assertTrue(bool);
		if (bool == false)
			log.info("Email field is editable");
		else
			log.info("Email field is non-editable");


	}
	
	@Test(priority=1)
	public void validateCalender() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		w.until(ExpectedConditions.elementToBeClickable(mp.getCalenderDropDown()));
		mp.getDate();
		saveButton();
		

	}

	@Test(priority=1)
	public void updateGender() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");


		mp.getFemaleRadioButton().click();
		log.info("Clicked on female radio button");
		saveButton();
		Thread.sleep(500);
		mp.getMaleRadioButton().click();
		log.info("Clicked on Male radio button");
		saveButton();
		
		Thread.sleep(500);
		

	}

	@Test(priority=4)
	public void updateWelcomeMessage() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");

		w.until(ExpectedConditions.visibilityOf(mp.getMyHeadline()));
		Thread.sleep(2000);
		mp.getMyHeadline().clear();
		mp.getMyHeadline().sendKeys("Welcome Aboard");
		saveButton();
		log.info("My Healine message entered");
		mp.getMyHeadline().clear();
		Thread.sleep(500);
		mp.getMyHeadline().sendKeys("This is automation user");
		saveButton();
		
		

	}
	
	@Test(priority=2)
	public void updateOrganization() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");

		int randomNumber = mp.randomNumberGenerator(0, 16);
		log.info("Selecting organnisation at option number :" + randomNumber);
		mp.getOrganizationName().click();
		w.until(ExpectedConditions.elementToBeClickable(mp.getorganizationAttribute(randomNumber)));
		mp.getorganizationAttribute(randomNumber).click();
		saveButton();
		log.info("Orgnisation name clicked");
		Thread.sleep(500);

	}

	@Test(priority=2)
	public void updateJobFunction() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		
		int randomNumber = mp.randomNumberGenerator(0, 14);
		log.info("Selecting Job Function at option number :" + randomNumber);
		mp.getJobFunction().click();
		w.until(ExpectedConditions.elementToBeClickable(mp.getJobFunctionAttribute(randomNumber)));
		mp.getJobFunctionAttribute(randomNumber).click();
		// System.out.println();
		saveButton();
		log.info(" Job Function name clicked");
		Thread.sleep(500);
		
	}

	@Test(priority=3)
	public void updateLocation() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		
		// int randomCountry = mp.randomNumberGenerator(0, 123); Generalizing Country
		int randomCountry = 98; // 98 is a code for India
		// log.info("Selecting country at option number :" + randomCountry);
		mp.getCountry().click();
		w.until(ExpectedConditions.elementToBeClickable(mp.getCountryAttribute(randomCountry)));
		mp.getCountryAttribute(randomCountry).click();

		log.info(" Country as : 'India ' is seleceted ");
		Thread.sleep(1000);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, 250)");

		// int randomState = mp.randomNumberGenerator(0, 10);
		int randomState = 36;// 36 is the code for uttar pradesh
		// log.info("Selecting State at option number :" + randomState);
		mp.getState().click();
		w.until(ExpectedConditions.elementToBeClickable(mp.getStateAttribute(randomState)));;
		mp.getStateAttribute(randomState).click();

		log.info(" 'Uttar Pradesh' as a state is selected");
		Thread.sleep(1000);

		jse.executeScript("scroll(0, 250)");

		int randomCity = mp.randomNumberGenerator(0, 687);
		log.info("Selecting city at option number :" + randomCity);
		mp.getCity().click();
		
		String cityName = mp.getCityAttribute(randomCity).getText();
		w.until(ExpectedConditions.elementToBeClickable(mp.getCityAttribute(randomCity)));
		mp.getCityAttribute(randomCity).click();

		log.info(" City as : ' " + cityName + " ' is selected");

		saveButton();
		Thread.sleep(500);
		

	}
	
	@Test(priority=3)
	public void updateContactInfo() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, 400)");

		// int randomCountryCode = mp.randomNumberGenerator(0, 123); Generalizing
		// Country Code
		int randomCountryCode = 98;
		log.info("Selecting country code at option number :" + randomCountryCode);
		
		mp.getCountryCode().click();
		w.until(ExpectedConditions.elementToBeClickable(mp.getCountryCodeAttribute(randomCountryCode)));
		mp.getCountryCodeAttribute(randomCountryCode).click();

		log.info(" Country code for India Selected ");
		Thread.sleep(1000);

		int START = 1000000000;
		// int END = Integer.parseInt("9999999999");
		// long END = Integer.parseInt("9999999999");
		long END = 9999999999L;

		Random random = new Random();
		long MobileNumber = mp.createRandomInteger(START, END, random);
		String number = Long.toString(MobileNumber);

		mp.getMobileNumber().clear();
		Thread.sleep(2000);
		mp.getMobileNumber().sendKeys(number);
		log.info("Mobile Number is Updated as: ' " + number + "  '");
		Thread.sleep(1000);
		saveButton();

	}
	
	public void saveButton() {
		MyProfilePage mp = new MyProfilePage(driver);
		mp.getSaveButton().click();
		WebDriverWait w = new WebDriverWait(driver, 3);
		w.until(ExpectedConditions.visibilityOf(mp.getToastMessage()));

		if (mp.getToastMessage().getText().equals(prop.getProperty("saveToastMessage"))) {
			log.info("toast Message Verified");
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);

		}

	}

	
}
