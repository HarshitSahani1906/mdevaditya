package encollab;

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
import org.testng.Assert;
import org.testng.annotations.Test;

import Resources.base;
import pageObjects.AgreementPage;
import pageObjects.HubPage;
import pageObjects.LandingPage;
import pageObjects.MyProfilePage;

public class SocialProfileCode extends base  {
	public WebDriver driver;
	public static Logger log = LogManager.getLogger(base.class.getName());
	String MethodName= null;
	@Test(dependsOnMethods = { "choosingSocialProfileComplete" },alwaysRun = true)
	public void validateNewUser() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Initiating Method :' " + MethodName + " '");

		driver = initializeDriver();
		MyProfilePage mp = new MyProfilePage(driver);
		LandingPage lp = new LandingPage(driver);
		AgreementPage ap = new AgreementPage(driver);
		HubPage hp = new HubPage(driver);
		WebDriverWait w = new WebDriverWait(driver, 3);
		// code to Login into Application
		/* ############################################################### */
		w.until(ExpectedConditions.elementToBeClickable(lp.getEmail()));
		lp.getEmail().sendKeys(prop.getProperty("newUsername"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("newPassword"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");
		/* ############################################################### */
		Thread.sleep(2000);

		if (driver.getCurrentUrl().equals(prop.getProperty("agreementpage"))) {

			w.until(ExpectedConditions.urlMatches(prop.getProperty("agreementpage")));
			Boolean bool = ap.getAgreeButton().isEnabled();
			if (!bool) {
				w.until(ExpectedConditions.elementToBeClickable(ap.getAcceptCheckBox()));
				ap.getAcceptCheckBox().click();
				log.info("clicked on checkbox");
				bool = ap.getAgreeButton().isEnabled();
				if (bool) {
					ap.getAgreeButton().click();
					log.info("clicked on Agree button ");
					log.info("Entered complete Profile Page");

					// Adding facebook profile

					WebDriverWait wait = new WebDriverWait(driver, 4);
					mp.getFacebookProfileButton().click();
					Set<String> facebookWindows = driver.getWindowHandles();
					Iterator<String> it = facebookWindows.iterator();
					String fbParentId = it.next();
					String fbChildId = it.next();
					// System.out.println(fbChildId);
					driver.switchTo().window(fbChildId);

					wait.until(ExpectedConditions.urlContains("www.facebook.com/login.php?"));
					mp.getFacebookEmailTextBox().sendKeys(prop1.getProperty("facebookUserName"));
					mp.getFacebookPasswordTextBox().sendKeys(prop1.getProperty("facebookPassword"));
					mp.getFacebookLoginButton().click();
					Thread.sleep(2000);
					wait.until(ExpectedConditions.urlContains("www.facebook.com/v2.8/dialog/oauth?"));
					mp.getFacebookContinueButton().click();
					Thread.sleep(2000);

					wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookNextButton()));
					mp.getFacebookNextButton().click();
					// log.info("Next Button Clicked ");

					wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookDoneButton()));
					Thread.sleep(2000);
					mp.getFacebookDoneButton().click();
					// log.info("Done Button Clicked ");
					wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookOKButton()));
					Thread.sleep(2000);
					mp.getFacebookOKButton().click();
					// log.info("OK Button Clicked ");
					driver.switchTo().window(fbParentId);
					log.info("Facebook profile is added to account Successfully");
					Thread.sleep(2000);

					// Adding LinkedIn Profile

					mp.getLinkedInProfileButton().click();
					List<String> windowhandles = new ArrayList<String>();
					Set<String> windows = driver.getWindowHandles();
					Iterator<String> it1 = windows.iterator();
					while (it1.hasNext()) {
						windowhandles.add(it.next());
					}
					
					String parentId = windowhandles.get(0);
					String childId =windowhandles.get(1);
					List<String> windowhandles1 = new ArrayList<String>();
					driver.switchTo().window(windowhandles.get(1));
					Thread.sleep(2000);
					wait.until(ExpectedConditions.elementToBeClickable(mp.getLinkedInEmailTextBox()));
					log.info(prop1.getProperty("linkedInUserName") + "  /  " + prop1.getProperty("linkedInPassword"));
					try {
						mp.getLinkedInEmailTextBox().sendKeys(prop1.getProperty("linkedInUserName"));
						mp.getLinkedInPasswordTextBox().sendKeys(prop1.getProperty("linkedInPassword"));
					} catch (Exception e) {
						log.error(e);
						log.error("Unable to send  Social credentials ");
						Assert.assertTrue(false);
					}
					
					mp.getLinkedInSignInButton().click();
					
					Thread.sleep(5000);
					driver.switchTo().window(parentId);
					driver.navigate().refresh();
					w.until(ExpectedConditions.visibilityOf(mp.getLinkedInProfileButton()));
					Set<String> windows1 = driver.getWindowHandles();
					Iterator<String> it2 = windows1.iterator();
					while (it2.hasNext()){
						windowhandles1.add(it2.next());
					}
					System.out.println(windowhandles1.size()+" :"+ windowhandles1.get(0));
					if (windowhandles1.size() == 1) {
						driver.switchTo().window(windowhandles1.get(0));
						
						driver.navigate().refresh();
						try {
							
							w.until(ExpectedConditions.visibilityOf(mp.getLinkedInProfileButton()));
							if (mp.getLinkedInUpdateAccountButton().isDisplayed()) {
								log.info("LinkedIn account has been added  for soicaloop successfully");
								
							} 
						} catch (Exception e) {
							log.error("LinkedIn Account not added", e);
							

						}
					}else {
						driver.switchTo().window(windowhandles1.get(1));
						String CurrentUrl = null;
						int choice = 0;
						try {
							CurrentUrl = driver.getCurrentUrl();
							Thread.sleep(2000);
							System.out.println(CurrentUrl);
							boolean check = CurrentUrl.contains("https://www.linkedin.com/checkpoint/challenge");
							System.out.println(check);
							if (CurrentUrl.contains("https://www.linkedin.com/oauth/v2/login-success?")) {
								choice = 1;
							} else if (CurrentUrl.contains("https://www.linkedin.com/checkpoint/challenge")) {
								choice = 2;
							}
							System.out.println(choice);
							switch (choice) {
							case 1: {
								w.until(ExpectedConditions.elementToBeClickable(mp.getLinkedInAllowButton()));
								mp.getLinkedInAllowButton().click();
								break;
							}
							case 2: {
								try {
									log.info("check 1");
									driver.switchTo().window(childId).close();
									driver.switchTo().window(parentId);
									mp.getLinkedInProfileButton().click();
									accountChoose();
									List<String> windowhandles2 = new ArrayList<String>();
									Set<String> windows2 = driver.getWindowHandles();
									Iterator<String> it3 = windows2.iterator();
									while(it3.hasNext()) {
										windowhandles2.add(it3.next());
									}
									String parentId1 = windowhandles2.get(0);
									String childId1 = windowhandles2.get(1);
									driver.switchTo().window(childId1);
									Thread.sleep(2000);
									wait.until(ExpectedConditions.elementToBeClickable(mp.getLinkedInEmailTextBox()));
									log.info(prop1.getProperty("linkedInUserName") + "  /  "
											+ prop1.getProperty("linkedInPassword"));
									try {
										mp.getLinkedInEmailTextBox().sendKeys(prop1.getProperty("linkedInUserName"));
										mp.getLinkedInPasswordTextBox().sendKeys(prop1.getProperty("linkedInPassword"));
									} catch (Exception e) {
										log.error(e);
										log.error("Unable to send  Social credentials ");
										Assert.assertTrue(false, "Unable to insert User credentials on Linked Page");
									}
									
									mp.getLinkedInSignInButton().click();
									if (w.until(ExpectedConditions.urlContains("https://www.linkedin.com/checkpoint/challenge"))) {
										log.error("Cannot add linkedIn Account");
										
										driver.switchTo().window(childId1).close();
										
										

									} else {

										driver.switchTo().window(parentId1);
										
									}

								} catch (Exception e) {
									log.error(e);

								}
								break;

							}
							default: {
								log.info("Invalid choide ");

							}

							}

						} catch (Exception e) {
							log.error(e);
							Assert.assertTrue(false, "" + e);

						}

						Thread.sleep(1000);
						
						driver.switchTo().window(parentId);
						
						Thread.sleep(1000);
						driver.navigate().refresh();
						w.until(ExpectedConditions.visibilityOf(mp.getLinkedInProfileButton()));
						try {
							if(mp.getLinkedInUpdateAccountButton().isDisplayed()) {
								log.info("LinkedIn account has been added  for soicaloop successfully");
							}
						}catch(Exception e) {
							log.error(e);
							log.info("Cannot add linkedIn Proifile");
							
							Assert.assertTrue(false,""+ e);
						}
						
						

					}

					// Adding Instagram profile

					mp.getInstagramProfileButton().click();

					wait.until(ExpectedConditions.urlContains("www.instagram.com/accounts/login/?"));
					wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramEmailTextBox()));
					mp.getInstagramEmailTextBox().sendKeys(prop1.getProperty("instagramUserName"));
					mp.getInstagramPasswordTextBox().sendKeys(prop1.getProperty("instagramPassword"));
					mp.getInstagramSignInButton().click();

					wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramSaveInfoButton()));
					mp.getInstagramSaveInfoButton().click();
					wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramAllowButton()));
					mp.getInstagramAllowButton().click();
					// driver.switchTo().window(instaParentId);
					Thread.sleep(2000);

					// filling FirstName and LastName
					w.until(ExpectedConditions.elementToBeClickable(mp.getFirstName()));
					mp.getFirstName().clear();
					mp.getFirstName().sendKeys(prop.getProperty("newFirstName"));
					log.info("First name has been entered");

					mp.getLastname().clear();
					mp.getLastname().sendKeys(prop.getProperty("newLastName"));
					log.info("Last name has been entered");

					// filling gender
					mp.getFemaleRadioButton().click();
					log.info("Clicked on female radio button");
					Thread.sleep(1000);
					mp.getMaleRadioButton().click();
					log.info("Clicked on female radio button");

					// validating email field is disabled
					String EmailField = mp.getEmailId().getAttribute("disabled");
					boolean bool1 = Boolean.parseBoolean(EmailField);
					Assert.assertTrue(bool1);
					if (bool == false) {
						log.info("Email field is editable");
						Assert.assertTrue(false);

					}

					else {
						log.info("Email field is non-editable");

					}

					// ****************************inserting calendar ***************

					mp.getDate();

					// inserting organisation name
					int randomNumber = mp.randomNumberGenerator(0, 16);
					log.info("Selecting organisation at option number :" + randomNumber);
					mp.getOrganizationName().click();
					mp.getorganizationAttribute(randomNumber).click();

					log.info("Organisation name clicked");

					// inserting Job Function
					randomNumber = mp.randomNumberGenerator(0, 14);
					log.info("Selecting Job Function at option number :" + randomNumber);
					mp.getJobFunction().click();
					mp.getJobFunctionAttribute(randomNumber).click();

					// inserting User's Location

					// int randomCountry = mp.randomNumberGenerator(0, 123); Generalizing Country
					int randomCountry = 98; // 98 is a code for India
					// log.info("Selecting country at option number :" + randomCountry);
					mp.getCountry().click();
					mp.getCountryAttribute(randomCountry).click();

					log.info(" Country as : 'India ' is selected ");
					Thread.sleep(1000);

					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("scroll(0, 250)");

					// int randomState = mp.randomNumberGenerator(0, 10);
					int randomState = 36;// 36 is the code for uttar pradesh
					// log.info("Selecting State at option number :" + randomState);
					mp.getState().click();
					Thread.sleep(2000);
					mp.getStateAttribute(randomState).click();

					log.info(" 'Uttar Pradesh' as a state is selected");
					Thread.sleep(1000);

					jse.executeScript("scroll(0, 250)");

					int randomCity = mp.randomNumberGenerator(0, 687);
					log.info("Selecting city at option number :" + randomCity);
					mp.getCity().click();
					Thread.sleep(1000);
					// System.out.println(mp.getCityAttribute(randomCity).getText());
					String cityName = mp.getCityAttribute(randomCity).getText();
					mp.getCityAttribute(randomCity).click();

					log.info(" City as : ' " + cityName + " ' is selected");

					// updating contact information
					int randomCountryCode = 98;
					log.info("Selecting country code at option number :" + randomCountryCode);
					mp.getCountryCode().click();
					// System.out.println(randomCountryCode);
					mp.getCountryCodeAttribute(randomCountryCode).click();

					log.info(" Country code for India Selected ");
					Thread.sleep(1000);

					int START = 1000000000;
					long END = 5999999999L;

					Random random = new Random();
					long MobileNumber = mp.createRandomInteger(START, END, random);
					String number = Long.toString(MobileNumber);

					mp.getMobileNumber().clear();
					Thread.sleep(500);
					mp.getMobileNumber().sendKeys(number);
					log.info("Mobile Number is Updated as: ' " + number + "  '");

					// updating Welcome message
					mp.getMyHeadline().clear();
					Thread.sleep(500);
					mp.getMyHeadline().sendKeys("Welcome Aboard");
					log.info("My Healine message entered");
					Thread.sleep(1000);
					saveButton();
					w.until(ExpectedConditions.elementToBeClickable(hp.getTourExitButon()));
					hp.getTourExitButon().click();
					log.info("Tour Exited");
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
					driver.close();

				} else {
					log.info(" Agree button is not active after checking ");
					Assert.assertTrue(false);

				}

			} else {
				log.info(" Agree button is already active");
				Assert.assertTrue(false);
			}

		}

		else if (driver.getCurrentUrl().equals(prop.getProperty("completeProfilePage"))) {
			log.info("Entered complete Profile Page"); // filling FirstName and LastName

			WebDriverWait wait = new WebDriverWait(driver, 4);
			mp.getFacebookProfileButton().click();
			Set<String> facebookWindows = driver.getWindowHandles();
			Iterator<String> it = facebookWindows.iterator();
			String fbParentId = it.next();
			String fbChildId = it.next();
			// System.out.println(fbChildId);
			driver.switchTo().window(fbChildId);
			wait.until(ExpectedConditions.urlContains("www.facebook.com/login.php?"));
			mp.getFacebookEmailTextBox().sendKeys(prop1.getProperty("facebookUserName"));
			mp.getFacebookPasswordTextBox().sendKeys(prop1.getProperty("facebookPassword"));
			mp.getFacebookLoginButton().click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.urlContains("www.facebook.com/v2.8/dialog/oauth?"));
			mp.getFacebookContinueButton().click();
			Thread.sleep(2000);

			wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookNextButton()));
			mp.getFacebookNextButton().click();
			// log.info("Next Button Clicked ");

			wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookDoneButton()));
			Thread.sleep(2000);
			mp.getFacebookDoneButton().click();
			// log.info("Done Button Clicked ");
			wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookOKButton()));
			Thread.sleep(2000);
			mp.getFacebookOKButton().click();
			// log.info("OK Button Clicked ");
			driver.switchTo().window(fbParentId);
			log.info("Facebook profile is added to account Successfully");
			Thread.sleep(2000);

			// Adding LinkedIn Profile

			mp.getLinkedInProfileButton().click();
			List<String> windowhandles = new ArrayList<String>();
			Set<String> windows = driver.getWindowHandles();
			Iterator<String> it1 = windows.iterator();
			while (it1.hasNext()) {
				windowhandles.add(it.next());
			}
			
			String parentId = windowhandles.get(0);
			String childId =windowhandles.get(1);
			List<String> windowhandles1 = new ArrayList<String>();
			driver.switchTo().window(windowhandles.get(1));
			Thread.sleep(2000);
			wait.until(ExpectedConditions.elementToBeClickable(mp.getLinkedInEmailTextBox()));
			log.info(prop1.getProperty("linkedInUserName") + "  /  " + prop1.getProperty("linkedInPassword"));
			try {
				mp.getLinkedInEmailTextBox().sendKeys(prop1.getProperty("linkedInUserName"));
				mp.getLinkedInPasswordTextBox().sendKeys(prop1.getProperty("linkedInPassword"));
			} catch (Exception e) {
				log.error(e);
				log.error("Unable to send  Social credentials ");
				Assert.assertTrue(false);
			}
			
			mp.getLinkedInSignInButton().click();
			
			Thread.sleep(5000);
			driver.switchTo().window(parentId);
			driver.navigate().refresh();
			w.until(ExpectedConditions.visibilityOf(mp.getLinkedInProfileButton()));
			Set<String> windows1 = driver.getWindowHandles();
			Iterator<String> it2 = windows1.iterator();
			while (it2.hasNext()){
				windowhandles1.add(it2.next());
			}
			System.out.println(windowhandles1.size()+" :"+ windowhandles1.get(0));
			if (windowhandles1.size() == 1) {
				driver.switchTo().window(windowhandles1.get(0));
				
				driver.navigate().refresh();
				try {
					
					w.until(ExpectedConditions.visibilityOf(mp.getLinkedInProfileButton()));
					if (mp.getLinkedInUpdateAccountButton().isDisplayed()) {
						log.info("LinkedIn account has been added  for soicaloop successfully");
						
					} 
				} catch (Exception e) {
					log.error("LinkedIn Account not added", e);
					

				}
			}else {
				driver.switchTo().window(windowhandles1.get(1));
				String CurrentUrl = null;
				int choice = 0;
				try {
					CurrentUrl = driver.getCurrentUrl();
					Thread.sleep(2000);
					System.out.println(CurrentUrl);
					boolean check = CurrentUrl.contains("https://www.linkedin.com/checkpoint/challenge");
					System.out.println(check);
					if (CurrentUrl.contains("https://www.linkedin.com/oauth/v2/login-success?")) {
						choice = 1;
					} else if (CurrentUrl.contains("https://www.linkedin.com/checkpoint/challenge")) {
						choice = 2;
					}
					System.out.println(choice);
					switch (choice) {
					case 1: {
						w.until(ExpectedConditions.elementToBeClickable(mp.getLinkedInAllowButton()));
						mp.getLinkedInAllowButton().click();
						break;
					}
					case 2: {
						try {
							log.info("check 1");
							driver.switchTo().window(childId).close();
							driver.switchTo().window(parentId);
							mp.getLinkedInProfileButton().click();
							accountChoose();
							List<String> windowhandles2 = new ArrayList<String>();
							Set<String> windows2 = driver.getWindowHandles();
							Iterator<String> it3 = windows2.iterator();
							while(it3.hasNext()) {
								windowhandles2.add(it3.next());
							}
							String parentId1 = windowhandles2.get(0);
							String childId1 = windowhandles2.get(1);
							driver.switchTo().window(childId1);
							Thread.sleep(2000);
							wait.until(ExpectedConditions.elementToBeClickable(mp.getLinkedInEmailTextBox()));
							log.info(prop1.getProperty("linkedInUserName") + "  /  "
									+ prop1.getProperty("linkedInPassword"));
							try {
								mp.getLinkedInEmailTextBox().sendKeys(prop1.getProperty("linkedInUserName"));
								mp.getLinkedInPasswordTextBox().sendKeys(prop1.getProperty("linkedInPassword"));
							} catch (Exception e) {
								log.error(e);
								log.error("Unable to send  Social credentials ");
								Assert.assertTrue(false, "Unable to insert User credentials on Linked Page");
							}
							
							mp.getLinkedInSignInButton().click();
							if (w.until(ExpectedConditions.urlContains("https://www.linkedin.com/checkpoint/challenge"))) {
								log.error("Cannot add linkedIn Account");
								
								driver.switchTo().window(childId1).close();
								
								

							} else {

								driver.switchTo().window(parentId1);
								
							}

						} catch (Exception e) {
							log.error(e);

						}
						break;

					}
					default: {
						log.info("Invalid choide ");

					}

					}

				} catch (Exception e) {
					log.error(e);
					Assert.assertTrue(false, "" + e);

				}

				Thread.sleep(1000);
				
				driver.switchTo().window(parentId);
				
				Thread.sleep(1000);
				driver.navigate().refresh();
				w.until(ExpectedConditions.visibilityOf(mp.getLinkedInProfileButton()));
				try {
					if(mp.getLinkedInUpdateAccountButton().isDisplayed()) {
						log.info("LinkedIn account has been added  for soicaloop successfully");
					}
				}catch(Exception e) {
					log.error(e);
					log.info("Cannot add linkedIn Proifile");
					
					Assert.assertTrue(false,""+ e);
				}
				
				

			}

			// Adding Instagram profile

			mp.getInstagramProfileButton().click();

			wait.until(ExpectedConditions.urlContains("www.instagram.com/accounts/login/?"));
			wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramEmailTextBox()));
			mp.getInstagramEmailTextBox().sendKeys(prop1.getProperty("instagramUserName"));
			mp.getInstagramPasswordTextBox().sendKeys(prop1.getProperty("instagramPassword"));
			mp.getInstagramSignInButton().click();

			wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramSaveInfoButton()));
			mp.getInstagramSaveInfoButton().click();
			wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramAllowButton()));
			mp.getInstagramAllowButton().click();
			// driver.switchTo().window(instaParentId);
			Thread.sleep(2000);

			// filling FirstName and LastName
			w.until(ExpectedConditions.elementToBeClickable(mp.getFirstName()));
			mp.getFirstName().clear();
			mp.getFirstName().sendKeys(prop.getProperty("newFirstName"));
			log.info("First name has been entered");

			mp.getLastname().clear();
			mp.getLastname().sendKeys(prop.getProperty("newLastName"));
			log.info("Last name has been entered");

			// filling gender
			mp.getFemaleRadioButton().click();
			log.info("Clicked on female radio button");
			Thread.sleep(2000);
			mp.getMaleRadioButton().click();
			log.info("Clicked on female radio button");
			// **************** calender*********************

			// ************************************************
			// validating email field is disabled
			String EmailField = mp.getEmailId().getAttribute("disabled");
			boolean bool1 = Boolean.parseBoolean(EmailField);
			Assert.assertTrue(bool1);
			if (bool1 == false) {
				log.info("Email field is editable");
				Assert.assertTrue(false);
			}else {
				log.info("Email field is non-editable");

			}
			// ****************************inserting calendar ***************

			mp.getDate();

			// inserting organisation name
			int randomNumber = mp.randomNumberGenerator(0, 16);
			log.info("Selecting organisation at option number :" + randomNumber);
			mp.getOrganizationName().click();
			mp.getorganizationAttribute(randomNumber).click();

			log.info("Organisation name clicked");

			// inserting Job Function
			randomNumber = mp.randomNumberGenerator(0, 14);
			log.info("Selecting Job Function at option number :" + randomNumber);
			mp.getJobFunction().click();
			mp.getJobFunctionAttribute(randomNumber).click();

			// inserting User's Location

			// int randomCountry = mp.randomNumberGenerator(0, 123); Generalizing Country
			int randomCountry = 98; // 98 is a code for India
			// log.info("Selecting country at option number :" + randomCountry);
			mp.getCountry().click();
			mp.getCountryAttribute(randomCountry).click();

			log.info(" Country as : 'India ' is selected ");
			Thread.sleep(1000);

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("scroll(0, 250)");

			// int randomState = mp.randomNumberGenerator(0, 10);
			int randomState = 36;// 36 is the code for uttar pradesh
			// log.info("Selecting State at option number :" + randomState);
			mp.getState().click();
			Thread.sleep(2000);
			mp.getStateAttribute(randomState).click();

			log.info(" 'Uttar Pradesh' as a state is selected");
			Thread.sleep(1000);
			jse.executeScript("scroll(0, 250)");
			int randomCity = mp.randomNumberGenerator(0, 687);
			log.info("Selecting city at option number :" + randomCity);
			mp.getCity().click();
			Thread.sleep(2000);
			
			String cityName = mp.getCityAttribute(randomCity).getText();
			mp.getCityAttribute(randomCity).click();

			log.info(" City as : ' " + cityName + " ' is selected");

			// updating contact information
			int randomCountryCode = 98;
			log.info("Selecting country code at option number :" + randomCountryCode);
			mp.getCountryCode().click();
			
			mp.getCountryCodeAttribute(randomCountryCode).click();

			log.info(" Country code for India Selected ");
			Thread.sleep(1000);

			int START = 1000000000;
			long END = 5999999999L;

			Random random = new Random();
			long MobileNumber = mp.createRandomInteger(START, END, random);
			String number = Long.toString(MobileNumber);

			mp.getMobileNumber().clear();
			Thread.sleep(500);
			mp.getMobileNumber().sendKeys(number);
			log.info("Mobile Number is Updated as: ' " + number + "  '");

			// updating Welcome message
			mp.getMyHeadline().clear();
			Thread.sleep(500);
			mp.getMyHeadline().sendKeys("Welcome Aboard");
			log.info("My Healine message entered");
			Thread.sleep(500);

			saveButton();
			w.until(ExpectedConditions.elementToBeClickable(hp.getTourExitButon()));
			hp.getTourExitButon().click();
			log.info("Tour Exited");
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
			driver.close();

		}

	}
	
	
	@Test
	public void choosingSocialProfileComplete() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		driver = initializeDriver();
		accountChoose();
		log.info("New Social Credentials Added to File ");
		driver.close();
	}


	public void saveButton() {
		MyProfilePage mp = new MyProfilePage(driver);
		mp.getSaveButton().click();
		WebDriverWait w = new WebDriverWait(driver, 3);
		w.until(ExpectedConditions.visibilityOf(mp.getToastMessage()));

		if (mp.getToastMessage().getText().equals(prop.getProperty("saveToastMessage"))) {
			log.info("toast Message Verified");
			// System.out.println("Hello harry");
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);

		}

	}



	@Test(dependsOnMethods = { "addInstagramProfile" }, alwaysRun = true)
	public void updateInstagram() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		driver = initializeDriver();
		log.info("Driver For MyProfile Page is initilized");
		MyProfilePage mp = new MyProfilePage(driver);
		LandingPage lp = new LandingPage(driver);
		HubPage hp = new HubPage(driver);

		// code to Login into Application
		/* ############################################################### */
		lp.getEmail().sendKeys(prop.getProperty("username"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("username"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");
		/* ############################################################### */

		WebDriverWait w = new WebDriverWait(driver, 3);
		w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
		hp.getMyProfileLogo().click();
		log.info("clicked on MyProfile Logo");
		hp.getMyProfileButton().click();
		log.info("Clicked on MyProfile button");
		w.until(ExpectedConditions.urlMatches(prop.getProperty("myProfilePage")));
		log.info("Fetching MyProfilePage");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramUpdateAccountButton()));
		mp.getInstagramUpdateAccountButton().click();
		wait.until(ExpectedConditions.urlContains("www.instagram.com/accounts/login/?"));
		wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramEmailTextBox()));
		mp.getInstagramEmailTextBox().sendKeys(prop1.getProperty("instagramUserName"));
		mp.getInstagramPasswordTextBox().sendKeys(prop1.getProperty("instagramPassword"));
		mp.getInstagramSignInButton().click();
		wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramSaveInfoButton()));
		mp.getInstagramSaveInfoButton().click();
		wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramAllowButton()));
		mp.getInstagramAllowButton().click();
		Thread.sleep(3000);
		log.info("Instagram Profile is updated for account");
		//driver.navigate().refresh();
		w.until(ExpectedConditions.urlMatches(prop.getProperty("myProfilePage")));
		w.until(ExpectedConditions.elementToBeClickable(mp.getInstagramUpdateAccountButton()));
		driver.close();

	}

	@Test(dependsOnMethods = { "addFacebookProfile" }, alwaysRun = true)
	public void updateFacebook() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");

		driver = initializeDriver();
		log.info("Driver For MyProfile Page is initilized");
		MyProfilePage mp = new MyProfilePage(driver);
		LandingPage lp = new LandingPage(driver);
		HubPage hp = new HubPage(driver);

		// code to Login into Application
		/* ############################################################### */
		lp.getEmail().sendKeys(prop.getProperty("username"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("username"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");
		/* ############################################################### */

		WebDriverWait w = new WebDriverWait(driver, 3);
		w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
		hp.getMyProfileLogo().click();
		log.info("clicked on MyProfile Logo");
		hp.getMyProfileButton().click();
		log.info("Clicked on MyProfile button");
		w.until(ExpectedConditions.urlMatches(prop.getProperty("myProfilePage")));
		log.info("Fetching MyProfilePage");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookUpdateAccountButton()));
		mp.getFacebookUpdateAccountButton().click();
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		String parentId = it.next();
		String childId = it.next();
		System.out.println(childId);
		driver.switchTo().window(childId);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.urlContains("www.facebook.com/login.php?"));
		mp.getFacebookEmailTextBox().sendKeys(prop1.getProperty("facebookUserName"));
		mp.getFacebookPasswordTextBox().sendKeys(prop1.getProperty("facebookPassword"));
		mp.getFacebookLoginButton().click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.urlContains("www.facebook.com/v2.8/dialog/oauth?"));
		mp.getFacebookContinueButton().click();
		Thread.sleep(1000);
		// wait.until(ExpectedConditions.urlContains(""));
		wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookNextButton()));
		mp.getFacebookNextButton().click();
		// log.info("Next Button Clicked ");

		wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookDoneButton()));
		Thread.sleep(1000);
		mp.getFacebookDoneButton().click();
		// log.info("Done Button Clicked ");
		wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookOKButton()));
		Thread.sleep(1000);
		mp.getFacebookOKButton().click();
		// log.info("OK Button Clicked ");
		driver.switchTo().window(parentId);

		Thread.sleep(200);
		log.info("Facebook account has been  sucessfully updated for the account ");
		driver.close();

	}
	
	@Test(dependsOnMethods = { "addLinkedInProfile" }, alwaysRun = true)
	public void updateLinkedIn() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		driver = initializeDriver();
		log.info("Driver For MyProfile Page is initilized");
		MyProfilePage mp = new MyProfilePage(driver);
		LandingPage lp = new LandingPage(driver);
		HubPage hp = new HubPage(driver);

		// code to Login into Application
		/* ############################################################### */
		lp.getEmail().sendKeys(prop.getProperty("username"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("username"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");
		/* ############################################################### */

		WebDriverWait w = new WebDriverWait(driver, 3);
		w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
		hp.getMyProfileLogo().click();
		log.info("clicked on MyProfile Logo");
		hp.getMyProfileButton().click();
		log.info("Clicked on MyProfile button");
		w.until(ExpectedConditions.urlMatches(prop.getProperty("myProfilePage")));
		log.info("Fetching MyProfilePage");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(mp.getLinkedInUpdateAccountButton()));
		mp.getLinkedInUpdateAccountButton().click();
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		String parentId = it.next();
		String childId = it.next();
		System.out.println(childId);
		driver.switchTo().window(childId);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.urlContains("www.linkedin.com/uas/login?"));
		mp.getLinkedInEmailTextBox().sendKeys(prop1.getProperty("linkedInUserName"));
		mp.getLinkedInPasswordTextBox().sendKeys(prop1.getProperty("linkedInPassword"));
		mp.getLinkedInSignInButton().click();

		Thread.sleep(1000);
		driver.switchTo().window(parentId);
		Thread.sleep(1000);
		log.info("LinkedIn account has been successfully updated for socialoop");
		driver.close();

	}

	
	
	@Test(dependsOnMethods = { "choosingSocialProfile" },alwaysRun = true)
	public void addFacebookProfile() throws IOException, InterruptedException {

		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		driver = initializeDriver();
		
		log.info("New Social Credentials Added to File ");
		
		MyProfilePage mp = new MyProfilePage(driver);
		LandingPage lp = new LandingPage(driver);
		HubPage hp = new HubPage(driver);
		
		// code to Login into Application
		/* ############################################################### */
		lp.getEmail().sendKeys(prop.getProperty("username"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("username"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");
		/* ############################################################### */

		WebDriverWait w = new WebDriverWait(driver, 3);
		w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
		hp.getMyProfileLogo().click();
		log.info("clicked on MyProfile Logo");
		hp.getMyProfileButton().click();
		log.info("Clicked on MyProfile button");
		w.until(ExpectedConditions.urlMatches(prop.getProperty("myProfilePage")));
		log.info("Fetching MyProfilePage");
		
		//log.info("check 1");
		Thread.sleep(2000);
		try {if (mp.getFacebookRemoveAccountButton().isDisplayed()) {
			//log.info("check 2");
			mp.getFacebookRemoveAccountButton().click();
			log.info("Removed already added Facebook account");
			Thread.sleep(2000);
		}
			
		}catch(Exception e) {
			log.error(e);
		}
		//log.info("check 3");
		WebDriverWait wait = new WebDriverWait(driver, 4);
		w.until(ExpectedConditions.elementToBeClickable(mp.getFacebookProfileButton()));
		mp.getFacebookProfileButton().click();
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		String parentId = it.next();
		String childId = it.next();
		System.out.println(childId);
		driver.switchTo().window(childId);
		Thread.sleep(2000);
		wait.until(ExpectedConditions.urlContains("www.facebook.com/login.php?"));
		try {
			mp.getFacebookEmailTextBox().sendKeys(prop1.getProperty("facebookUserName"));
			mp.getFacebookPasswordTextBox().sendKeys(prop1.getProperty("facebookPassword"));
			
		}catch(Exception e) {
			log.error(e);
			log.error("Unable to send  Social credentials ");
			Assert.assertTrue(false);
		}
		
		mp.getFacebookLoginButton().click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.urlContains("www.facebook.com/v2.8/dialog/oauth?"));
		mp.getFacebookContinueButton().click();
		Thread.sleep(2000);
		// wait.until(ExpectedConditions.urlContains(""));
		wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookNextButton()));
		mp.getFacebookNextButton().click();
		// log.info("Next Button Clicked ");

		wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookDoneButton()));
		Thread.sleep(2000);
		mp.getFacebookDoneButton().click();
		// log.info("Done Button Clicked ");
		wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookOKButton()));
		Thread.sleep(2000);
		mp.getFacebookOKButton().click();
		// log.info("OK Button Clicked ");
		driver.switchTo().window(parentId);
		log.info("Facebook profile is added to account Successfully");
		Thread.sleep(2000);
		w.until(ExpectedConditions.visibilityOf(mp.getFacebookUpdateAccountButton()));
		driver.close();
	}
	
	@Test(dependsOnMethods = { "choosingSocialProfile" }, alwaysRun = true)
	public void addLinkedInProfile() throws IOException, InterruptedException {

		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		driver = initializeDriver();

		log.info("New Social Credentials Added to File ");

		MyProfilePage mp = new MyProfilePage(driver);
		LandingPage lp = new LandingPage(driver);
		HubPage hp = new HubPage(driver);

		// code to Login into Application
		/* ############################################################### */
		lp.getEmail().sendKeys(prop.getProperty("username"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("username"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");
		/* ############################################################### */

		WebDriverWait w = new WebDriverWait(driver, 3);
		w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
		hp.getMyProfileLogo().click();
		log.info("clicked on MyProfile Logo");
		hp.getMyProfileButton().click();
		log.info("Clicked on MyProfile button");
		w.until(ExpectedConditions.urlMatches(prop.getProperty("myProfilePage")));
		log.info("Fetching MyProfilePage");

		WebDriverWait wait = new WebDriverWait(driver, 4);
		w.until(ExpectedConditions.elementToBeClickable(mp.getLinkedInProfileButton()));
		Thread.sleep(3000);
		try {
			if (mp.getLinkedInRemoveAccountButton().isDisplayed()) {

				mp.getLinkedInRemoveAccountButton().click();
				log.info("Removed already added LinkedIn  account account");
				Thread.sleep(2000);
			}

		} catch (Exception e) {
			log.error(e);
		}
		mp.getLinkedInProfileButton().click();
		List<String> windowhandles = new ArrayList<String>();
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		while (it.hasNext()) {
			windowhandles.add(it.next());
		}
		
		String parentId = windowhandles.get(0);
		String childId =windowhandles.get(1);
		List<String> windowhandles1 = new ArrayList<String>();
		driver.switchTo().window(windowhandles.get(1));
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(mp.getLinkedInEmailTextBox()));
		log.info(prop1.getProperty("linkedInUserName") + "  /  " + prop1.getProperty("linkedInPassword"));
		try {
			mp.getLinkedInEmailTextBox().sendKeys(prop1.getProperty("linkedInUserName"));
			mp.getLinkedInPasswordTextBox().sendKeys(prop1.getProperty("linkedInPassword"));
		} catch (Exception e) {
			log.error(e);
			log.error("Unable to send  Social credentials ");
			Assert.assertTrue(false);
		}
		
		mp.getLinkedInSignInButton().click();
		
		Thread.sleep(5000);
		driver.switchTo().window(parentId);
		driver.navigate().refresh();
		w.until(ExpectedConditions.visibilityOf(mp.getLinkedInProfileButton()));
		Set<String> windows1 = driver.getWindowHandles();
		Iterator<String> it1 = windows1.iterator();
		while (it1.hasNext()){
			windowhandles1.add(it1.next());
		}
		System.out.println(windowhandles1.size()+" :"+ windowhandles1.get(0));
		if (windowhandles1.size() == 1) {
			driver.switchTo().window(windowhandles1.get(0));
			
			driver.navigate().refresh();
			try {
				
				w.until(ExpectedConditions.visibilityOf(mp.getLinkedInProfileButton()));
				if (mp.getLinkedInUpdateAccountButton().isDisplayed()) {
					log.info("LinkedIn account has been added  for soicaloop successfully");
					
				} 
			} catch (Exception e) {
				log.error("LinkedIn Account not added", e);
				

			}
		}else {
			driver.switchTo().window(windowhandles1.get(1));
			String CurrentUrl = null;
			int choice = 0;
			try {
				CurrentUrl = driver.getCurrentUrl();
				Thread.sleep(2000);
				System.out.println(CurrentUrl);
				boolean check = CurrentUrl.contains("https://www.linkedin.com/checkpoint/challenge");
				System.out.println(check);
				if (CurrentUrl.contains("https://www.linkedin.com/oauth/v2/login-success?")) {
					choice = 1;
				} else if (CurrentUrl.contains("https://www.linkedin.com/checkpoint/challenge")) {
					choice = 2;
				}
				System.out.println(choice);
				switch (choice) {
				case 1: {
					w.until(ExpectedConditions.elementToBeClickable(mp.getLinkedInAllowButton()));
					mp.getLinkedInAllowButton().click();
					break;
				}
				case 2: {
					try {
						log.info("check 1");
						driver.switchTo().window(childId).close();
						driver.switchTo().window(parentId);
						mp.getLinkedInProfileButton().click();
						accountChoose();
						List<String> windowhandles2 = new ArrayList<String>();
						Set<String> windows2 = driver.getWindowHandles();
						Iterator<String> it2 = windows2.iterator();
						while(it2.hasNext()) {
							windowhandles2.add(it2.next());
						}
						String parentId1 = windowhandles2.get(0);
						String childId1 = windowhandles2.get(1);
						driver.switchTo().window(childId1);
						Thread.sleep(2000);
						wait.until(ExpectedConditions.elementToBeClickable(mp.getLinkedInEmailTextBox()));
						log.info(prop1.getProperty("linkedInUserName") + "  /  "
								+ prop1.getProperty("linkedInPassword"));
						try {
							mp.getLinkedInEmailTextBox().sendKeys(prop1.getProperty("linkedInUserName"));
							mp.getLinkedInPasswordTextBox().sendKeys(prop1.getProperty("linkedInPassword"));
						} catch (Exception e) {
							log.error(e);
							log.error("Unable to send  Social credentials ");
							Assert.assertTrue(false, "Unable to insert User credentials on Linked Page");
						}
						
						mp.getLinkedInSignInButton().click();
						if (w.until(ExpectedConditions.urlContains("https://www.linkedin.com/checkpoint/challenge"))) {
							log.error("Cannot add linkedIn Account");
							
							driver.switchTo().window(childId1).close();
							
							

						} else {

							driver.switchTo().window(parentId1);
							
						}

					} catch (Exception e) {
						log.error(e);

					}
					break;

				}
				default: {
					log.info("Invalid choide ");

				}

				}

			} catch (Exception e) {
				log.error(e);
				Assert.assertTrue(false, "" + e);

			}

			Thread.sleep(1000);
			
			driver.switchTo().window(parentId);
			
			Thread.sleep(1000);
			driver.navigate().refresh();
			w.until(ExpectedConditions.visibilityOf(mp.getLinkedInProfileButton()));
			try {
				if(mp.getLinkedInUpdateAccountButton().isDisplayed()) {
					log.info("LinkedIn account has been added  for soicaloop successfully");
				}
			}catch(Exception e) {
				log.error(e);
				log.info("Cannot add linkedIn Proifile");
				
				Assert.assertTrue(false,""+ e);
			}
			
			

		}
		driver.close();

	}	
	
	@Test(dependsOnMethods = { "choosingSocialProfile" },alwaysRun = true)
	public void addInstagramProfile() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		driver = initializeDriver();
		
		log.info("New Social Credentials Added to File ");
		
		MyProfilePage mp = new MyProfilePage(driver);
		LandingPage lp = new LandingPage(driver);
		HubPage hp = new HubPage(driver);

		// code to Login into Application
		/* ############################################################### */
		lp.getEmail().sendKeys(prop.getProperty("username"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("username"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");
		/* ############################################################### */

		WebDriverWait w = new WebDriverWait(driver, 3);
		w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
		hp.getMyProfileLogo().click();
		log.info("clicked on MyProfile Logo");
		hp.getMyProfileButton().click();
		log.info("Clicked on MyProfile button");
		w.until(ExpectedConditions.urlMatches(prop.getProperty("myProfilePage")));
		log.info("Fetching MyProfilePage");
		WebDriverWait wait = new WebDriverWait(driver, 4);
		w.until(ExpectedConditions.elementToBeClickable(mp.getInstagramProfileButton()));
		Thread.sleep(3000);
		try {
			if (mp.getInstagramRemoveAccountButton().isDisplayed()) {
				mp.getInstagramRemoveAccountButton().click();
				log.info("Removed already added instagram account account");
				Thread.sleep(2000);
			}
			
		}catch(Exception e) {
			log.error(e);
		}
		
		mp.getInstagramProfileButton().click();

		wait.until(ExpectedConditions.urlContains("www.instagram.com/accounts/login/?"));
		wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramEmailTextBox()));
		log.info(prop1.getProperty("socialAccount")+"  /  "+prop1.getProperty("password"));
		mp.getInstagramEmailTextBox().sendKeys(prop1.getProperty("instagramUserName"));
		mp.getInstagramPasswordTextBox().sendKeys(prop1.getProperty("instagramPassword"));
		mp.getInstagramSignInButton().click();

		wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramSaveInfoButton()));
		mp.getInstagramSaveInfoButton().click();
		wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramAllowButton()));
		mp.getInstagramAllowButton().click();

		Thread.sleep(1000);
		log.info("Instagram Profile is added to account");
		Thread.sleep(3000);
		w.until(ExpectedConditions.visibilityOf(mp.getInstagramUpdateAccountButton()));
		driver.close();

	}
	
	@Test
	public void choosingSocialProfile() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		driver = initializeDriver();
		accountChoose();
		log.info("New Social Credentials Added to File ");
		driver.close();
	}

	@Test(dependsOnMethods = { "addInstagramProfile", "updateInstagram" }, alwaysRun = true)
	public void removeInstagram() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		
		driver = initializeDriver();
		log.info("Driver For MyProfile Page is initilized");
		MyProfilePage mp = new MyProfilePage(driver);
		LandingPage lp = new LandingPage(driver);
		HubPage hp = new HubPage(driver);

		// code to Login into Application
		/* ############################################################### */
		lp.getEmail().sendKeys(prop.getProperty("username"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("username"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");
		/* ############################################################### */

		WebDriverWait w = new WebDriverWait(driver, 3);
		w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
		hp.getMyProfileLogo().click();
		log.info("clicked on MyProfile Logo");
		hp.getMyProfileButton().click();
		log.info("Clicked on MyProfile button");
		w.until(ExpectedConditions.urlMatches(prop.getProperty("myProfilePage")));
		log.info("Fetching MyProfilePage");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramRemoveAccountButton()));
		mp.getInstagramRemoveAccountButton().click();
		log.info("Instagram profile has been removed Succesffully from the account");
		Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(mp.getInstagramProfileButton()));
		driver.close();

	}

	@Test(dependsOnMethods = { "addFacebookProfile", "updateFacebook" }, alwaysRun = true)
	public void removeFacebook() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");

		driver = initializeDriver();
		log.info("Driver For MyProfile Page is initilized");
		MyProfilePage mp = new MyProfilePage(driver);
		LandingPage lp = new LandingPage(driver);
		HubPage hp = new HubPage(driver);

		// code to Login into Application
		/* ############################################################### */
		lp.getEmail().sendKeys(prop.getProperty("username"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("username"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");
		/* ############################################################### */

		WebDriverWait w = new WebDriverWait(driver, 3);
		w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
		hp.getMyProfileLogo().click();
		log.info("clicked on MyProfile Logo");
		hp.getMyProfileButton().click();
		log.info("Clicked on MyProfile button");
		w.until(ExpectedConditions.urlMatches(prop.getProperty("myProfilePage")));
		log.info("Fetching MyProfilePage");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(mp.getFacebookRemoveAccountButton()));
		mp.getFacebookRemoveAccountButton().click();
		Thread.sleep(1000);
		log.info("Facebook account has been removed sucessfully from the account ");
		driver.close();

	}
	
	@Test(dependsOnMethods = { "addLinkedInProfile", "updateLinkedIn" }, alwaysRun = true)
	public void removeLinkedIn() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Intiating Method :' " + MethodName + " '");
		driver = initializeDriver();
		log.info("Driver For MyProfile Page is initilized");
		MyProfilePage mp = new MyProfilePage(driver);
		LandingPage lp = new LandingPage(driver);
		HubPage hp = new HubPage(driver);

		// code to Login into Application
		/* ############################################################### */
		lp.getEmail().sendKeys(prop.getProperty("username"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("username"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");
		/* ############################################################### */

		WebDriverWait w = new WebDriverWait(driver, 3);
		w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
		hp.getMyProfileLogo().click();
		log.info("clicked on MyProfile Logo");
		hp.getMyProfileButton().click();
		log.info("Clicked on MyProfile button");
		w.until(ExpectedConditions.urlMatches(prop.getProperty("myProfilePage")));
		log.info("Fetching MyProfilePage");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(mp.getLinkedInRemoveAccountButton()));
		mp.getLinkedInRemoveAccountButton().click();
		log.info("LinkedIn account has been removed from soicaloop successfully");
		Thread.sleep(200);
		driver.close();

	}


}
