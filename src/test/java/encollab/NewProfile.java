package encollab;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
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
import Resources.base;
import pageObjects.AgreementPage;
import pageObjects.HubPage;
import pageObjects.LandingPage;
import pageObjects.MyProfilePage;

public class NewProfile extends base {
	public WebDriver driver;

	public static Logger log = LogManager.getLogger(base.class.getName());
	String MethodName = null;
	WebDriverWait w;
	MyProfilePage mp;
	LandingPage lp;
	HubPage hp;
	AgreementPage ap;

	@BeforeTest
	public void openBrowser() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver For MyProfile Page is initialized");
		w = new WebDriverWait(driver, 5);
		mp = new MyProfilePage(driver);
		lp = new LandingPage(driver);
		hp = new HubPage(driver);
		ap = new AgreementPage(driver);
		lp.getEmail().sendKeys(prop.getProperty("newUsername"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("newPassword"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");

	}

	@AfterTest
	public void closeBrowser() {
		driver.close();
		log.info("closing browser");
	}
	public void saveButton() {
		MyProfilePage mp = new MyProfilePage(driver);
		mp.getSaveButton().click();
		WebDriverWait w = new WebDriverWait(driver, 3);
		w.until(ExpectedConditions.visibilityOf(mp.getToastMessage()));

		if (mp.getToastMessage().getText().equals(prop.getProperty("saveToastMessage"))) {
			log.info("toast Message Verified");
			
			
		} else {
			Assert.assertTrue(false,"Toast message is not as expected");

		}

	}

	@Test
	public void validateNewUser() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Initiating Method :' " + MethodName + " '");
		Boolean NavigateAgreementPage=false;
		Boolean NavigateCompleteProfilePage=false;
		Boolean NavigateHubPage=false;

		Thread.sleep(2000);
		int choice = 0;
		int numberOfRun = 0;
		String CurrentUrl = driver.getCurrentUrl();
		if (CurrentUrl.equalsIgnoreCase(prop.getProperty("agreementpage"))) {
			choice = 1;
		} else if (CurrentUrl.equalsIgnoreCase(prop.getProperty("completeProfilePage"))) {
			choice = 2;
		} else if (CurrentUrl.equalsIgnoreCase(prop.getProperty("hubPage"))) {
			choice = 3;
		}

		while (choice != 0) {
			numberOfRun++;
			if (numberOfRun > 5) {
				break;
			}

			switch (choice) {
			case 1: {
				try {
					w.until(ExpectedConditions.urlMatches(prop.getProperty("agreementpage")));
					w.until(ExpectedConditions.visibilityOf(ap.getAgreeButton()));
					Boolean bool = ap.getAgreeButton().isEnabled();
					if (!bool) {
						w.until(ExpectedConditions.elementToBeClickable(ap.getAcceptCheckBox()));
						ap.getAcceptCheckBox().click();
						log.info("clicked on checkbox");
						bool = ap.getAgreeButton().isEnabled();
						if (bool) {
							ap.getAgreeButton().click();
							w.until(ExpectedConditions.urlMatches(prop.getProperty("completeProfilePage")));
							NavigateAgreementPage=true;
							choice=2;
							
						} else {
							log.info(" Agree button is not active after Accepting All Terms");
							Assert.assertTrue(false," Agree button is not active after Accepting All Terms");

						}

					} else {
						log.info("Agree button is active before accepting all terms");
						Assert.assertTrue(false,"Agree button is active before accepting all terms");
					}
					
				}catch(Exception e) {
					log.error("Error occured during Agreement Page ", e);
					Assert.assertTrue(false, "Error occured during Agreement Page "+e);
				}
				

				break;
			}
			case 2: {
				try {
					w.until(ExpectedConditions.urlMatches(prop.getProperty("completeProfilePage")));
					log.info("Entered complete Profile Page");
					int randomNumber;
					// filling FirstName and LastName
					try {
						w.until(ExpectedConditions.elementToBeClickable(mp.getFirstName()));
						mp.getFirstName().clear();
						mp.getFirstName().sendKeys(prop.getProperty("newFirstName"));
						log.info("First name has been entered");
						mp.getLastname().clear();
						mp.getLastname().sendKeys(prop.getProperty("newLastName"));
						log.info("Last name has been entered");
						
					}catch(Exception e) {
						log.error("Error occured while  inserting FirstName and LastName", e);
						Assert.assertTrue(false, "Error occured while  inserting FirstName and LastName"+e);				
					}
					
					
					try {
						Thread.sleep(500);
						mp.getFemaleRadioButton().click();
						Thread.sleep(500);
						mp.getMaleRadioButton().click();
						log.info("Clicked on female radio button");											
					}catch(Exception e) {
						log.error("Error occured while selecting gender", e);
						Assert.assertTrue(false, "Error occured while selecting gender"+e);				
					}
					
					try {
						// validating email field is disabled
						String EmailField = mp.getEmailId().getAttribute("disabled");
						boolean bool1 = Boolean.parseBoolean(EmailField);
						Assert.assertTrue(bool1);
						if (bool1 == false) {
							log.info("Email field is editable");
							Assert.assertTrue(false);
						} else {
							log.info("Email field is non-editable");

						}
																
					}catch(Exception e) {
						log.error("Error occured while validating email field", e);
						Assert.assertTrue(false, "Error occured while validating email field"+e);				
					}
					
					try {
						//inserting DateOfBirth
						mp.getDate();															
					}catch(Exception e) {
						log.error("Error occured while inserting DateOfBirth", e);
						Assert.assertTrue(false, "Error occured while inserting DateOfBirth"+e);				
					}
					try {
						// inserting organisation name
						randomNumber = mp.randomNumberGenerator(0, 16);
						log.info("Selecting organisation at option number :" + randomNumber);
						mp.getOrganizationName().click();
						w.until(ExpectedConditions.visibilityOf(mp.getorganizationAttribute(randomNumber)));
						mp.getorganizationAttribute(randomNumber).click();

						log.info("Organisation name clicked");
															
					}catch(Exception e) {
						log.error("Error occured while inserting organisation name", e);
						Assert.assertTrue(false, "Error occured while inserting organisation name"+e);				
					}
					try {
						// inserting Job Function
						randomNumber = mp.randomNumberGenerator(0, 14);
						log.info("Selecting Job Function at option number :" + randomNumber);
						mp.getJobFunction().click();
						w.until(ExpectedConditions.visibilityOf(mp.getJobFunctionAttribute(randomNumber)));
						mp.getJobFunctionAttribute(randomNumber).click();
						
					}catch(Exception e) {
						log.error("Error occured while inserting Job Function", e);
						Assert.assertTrue(false, "Error occured while inserting Job Function"+e);				
					}
					try {
						// inserting User's Location

						// int randomCountry = mp.randomNumberGenerator(0, 123); Generalizing Country
						int randomCountry = 98; // 98 is a code for India
						// log.info("Selecting country at option number :" + randomCountry);
						mp.getCountry().click();
						Thread.sleep(2000);
						//w.until(ExpectedConditions.elementToBeClickable(mp.getCountryAttribute(randomCountry + 2)));
						//w.until(ExpectedConditions.elementToBeClickable(mp.getCountryAttribute(randomCountry)));
						mp.getCountryAttribute(randomCountry).click();

						log.info(" Country as : 'India ' is selected ");
						Thread.sleep(1000);

						JavascriptExecutor jse = (JavascriptExecutor) driver;
						jse.executeScript("scroll(0, 250)");

						// int randomState = mp.randomNumberGenerator(0, 10);
						int randomState = 36;// 36 is the code for uttar pradesh
						// log.info("Selecting State at option number :" + randomState);
						mp.getState().click();
						w.until(ExpectedConditions.visibilityOf(mp.getStateAttribute(randomState)));
						mp.getStateAttribute(randomState).click();

						log.info(" 'Uttar Pradesh' as a state is selected");
						Thread.sleep(1000);
						jse.executeScript("scroll(0, 250)");
						int randomCity = mp.randomNumberGenerator(0, 687);
						log.info("Selecting city at option number :" + randomCity);
						mp.getCity().click();
						Thread.sleep(2000);

						String cityName = mp.getCityAttribute(randomCity).getText();
						w.until(ExpectedConditions.visibilityOf(mp.getCityAttribute(randomCity)));
						mp.getCityAttribute(randomCity).click();

						log.info(" City as : ' " + cityName + " ' is selected");
					}catch(Exception e) {
						log.error("Error occured while inserting User's Location", e);
						Assert.assertTrue(false, "Error occured while inserting User's Location"+e);				
					}
					try {
						// updating contact information
						int randomCountryCode = 98;
						log.info("Selecting country code at option number :" + randomCountryCode);
						mp.getCountryCode().click();
						w.until(ExpectedConditions.visibilityOf(mp.getCountryCodeAttribute(randomCountryCode)));
						mp.getCountryCodeAttribute(randomCountryCode).click();
						log.info(" Country code for India Selected ");
						int START = 1000000000;
						long END = 5999999999L;

						Random random = new Random();
						long MobileNumber = mp.createRandomInteger(START, END, random);
						String number = Long.toString(MobileNumber);

						mp.getMobileNumber().clear();
						Thread.sleep(500);
						mp.getMobileNumber().sendKeys(number);
						log.info("Mobile Number is Updated as: ' " + number + "  '");
						
					}catch(Exception e) {
						log.error("Error occured while inserting contact information", e);
						Assert.assertTrue(false, "Error occured while inserting contact information"+e);				
					}
					try {
						// updating Welcome message
						mp.getMyHeadline().clear();
						Thread.sleep(500);
						mp.getMyHeadline().sendKeys("Welcome Aboard");
						log.info("My Healine message entered");
						Thread.sleep(500);
					}catch(Exception e) {
						log.error("Error occured while inserting Welcome message", e);
						Assert.assertTrue(false, "Error occured while inserting Welcome message"+e);				
					}
					saveButton();
					Thread.sleep(1000);
					w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
					w.until(ExpectedConditions.elementToBeClickable(hp.getTourExitButon()));
					hp.getTourExitButon().click();
					log.info("Tour Exited");
					NavigateCompleteProfilePage=true;
					choice=3;
					
				}catch(Exception e) {
					log.error("Error occured while CompleteProfile Page is loaded ", e);
					Assert.assertTrue(false, "Error occured while CompleteProfile Page is loaded "+e);				
				}
				break;
			}
			case 3: {
				try {
					w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
					Thread.sleep(1000);
					w.until(ExpectedConditions.elementToBeClickable(hp.getMyProfileLogo()));
					hp.getMyProfileLogo().click();
					log.info("clicked on MyProfile Logo");
					w.until(ExpectedConditions.elementToBeClickable(hp.getLogoutButton()));
					hp.getLogoutButton().click();
					log.info("clicked on Logout Button");
					w.until(ExpectedConditions.urlMatches(prop.getProperty("loginUrl")));
					log.info("Login Url matches after Logout ");
					w.until(ExpectedConditions.elementToBeClickable(lp.getEmail()));
					NavigateHubPage=true;
					if(NavigateHubPage==true&&NavigateCompleteProfilePage==true&& NavigateAgreementPage==true) {
						choice =0;		
					}else {
						lp.getEmail().sendKeys(prop.getProperty("newUsername"));
						log.info("Entered Username");
						lp.getPassword().sendKeys(prop.getProperty("newPassword"));
						log.info("Entered Password");
						lp.getLoginButton().click();
						log.info("Clicked on Login Button");
						Thread.sleep(1000);
						CurrentUrl = driver.getCurrentUrl();
						if (CurrentUrl.equalsIgnoreCase(prop.getProperty("agreementpage"))) {
							choice = 1;
						} else if (CurrentUrl.equalsIgnoreCase(prop.getProperty("completeProfilePage"))) {
							choice = 2;
						} else if (CurrentUrl.equalsIgnoreCase(prop.getProperty("hubPage"))) {
							choice = 3;
						}
						
					}


				} catch (Exception e) {
					log.info("Complete Profile page is not displayed ", e);

				}

				break;
			}

			default: {
				log.info("Page displayed  does not matches with code ");
			}

			}

		}
		if (choice == 0 && numberOfRun > 0 && numberOfRun <= 5) {
			log.info("Test Case is successfully executed");
		} else {
			log.error("Testcase failed due to exceed attempts");
			Assert.assertTrue(false, "Testcase failed due to exceed attempts");
		}

	}

}
