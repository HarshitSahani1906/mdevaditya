package encollab;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import Resources.base;
import pageObjects.LandingPage;

import org.testng.AssertJUnit;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;

public class LoginPage extends base {

	public WebDriver driver;
	public static Logger log = LogManager.getLogger(base.class.getName());
	String MethodName = null;

	@Test(dataProvider = "getData")
	public void loginPageTestCredentials(String Username, String Password, String choice)
			throws IOException, InterruptedException {

		if (choice.equals("1")) {
			MethodName = new Throwable().getStackTrace()[0].getMethodName();
			log.info("Intiating Method :' " + MethodName + " : with correct user credentials" + " '");

		} else if (choice.equals("2")) {
			MethodName = new Throwable().getStackTrace()[0].getMethodName();
			log.info("Intiating Method :' " + MethodName + ": with correct UserName & wrong Password" + " '");

		} else if (choice.equals("3")) {
			MethodName = new Throwable().getStackTrace()[0].getMethodName();
			log.info("Intiating Method :' " + MethodName + ": with wrong UserName & correct Password" + " '");

		} else if (choice.equals("4")) {
			MethodName = new Throwable().getStackTrace()[0].getMethodName();
			log.info("Intiating Method :' " + MethodName + ": with wrong UserName & wrong Password" + " '");

		}

		driver = initializeDriver();
		log.info("Driver For Login Page is initialized");
		LandingPage lp = new LandingPage(driver);
		// fetching Email & password textbox from login page and click
		int casesForLogin = Integer.parseInt(choice);
		log.info("Dataset choice has been fetched");
		

		switch (casesForLogin) {
		case 1: {
			// correct Username and Correct password is passed
			try {
				lp.getEmail().sendKeys(Username);
				log.info("Username entered as  " + Username);

				lp.getPassword().sendKeys(Password);
				log.info("password  entered as  " + Password);
				lp.getLoginButton().click();
				log.info("Clicked on login");

				// code to apply Assert over verifying correct url is loaded or not in case when
				// correct UserName and password is passed
				WebDriverWait w = new WebDriverWait(driver, 10);
				Assert.assertTrue(w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage"))));
				log.info("Navigated to correct url ie. " + prop.getProperty("hubPage"));
			} catch (Exception e) {
				log.error(e);
			} finally {
				Thread.sleep(2000);
				driver.quit();

			}
			break;

		}
		case 2: {

			// correct username and wrong password is passed
			try {
				WebDriverWait w = new WebDriverWait(driver, 3);
				lp.getEmail().sendKeys(Username);
				log.info("Username entered as  " + Username);
				lp.getPassword().sendKeys(Password);
				log.info("password  entered as  " + Password);
				lp.getLoginButton().click();
				log.info("Clicked on login");
				w.until(ExpectedConditions.visibilityOf(lp.getToastMessage()));
				lp.loginToastMessage();
				lp.errorMessage();

				if (lp.errorMessage().equals(prop.getProperty("loginErrorMessage"))
						&& lp.loginToastMessage().equals(prop.getProperty("loginToastMessage"))) {
					// System.out.println(lp.errorMessage());
					log.info("Navigated to correct url ie. " + prop.getProperty("loginUrl"));
					log.info("Error Message matched ");
					log.info("Toast message Verified");
					Assert.assertTrue(w.until(ExpectedConditions.urlMatches(prop.getProperty("loginUrl"))));

				}

			} catch (Exception e) {
				log.error(e);
			} finally {
				Thread.sleep(2000);
				driver.quit();

			}
			break;

		}

		case 3: {

			// wrong username and correct password is passed
			try {
				WebDriverWait w = new WebDriverWait(driver, 3);
				lp.getEmail().sendKeys(Username);
				log.info("Username entered as  " + Username);
				lp.getPassword().sendKeys(Password);
				log.info("password  entered as  " + Password);
				lp.getLoginButton().click();
				log.info("Clicked on login");
				w.until(ExpectedConditions.visibilityOf(lp.getToastMessage()));
				lp.loginToastMessage();
				lp.errorMessage();

				if (lp.errorMessage().equals(prop.getProperty("loginErrorMessage"))
						&& lp.loginToastMessage().equals(prop.getProperty("loginToastMessage"))) {
					// System.out.println(lp.errorMessage());
					log.info("Navigated to correct url ie. " + prop.getProperty("loginUrl"));
					log.info("Error Message matched ");
					log.info("Toast message Verified");
					Assert.assertTrue(w.until(ExpectedConditions.urlMatches(prop.getProperty("loginUrl"))));

				}

			} catch (Exception e) {
				log.error(e);
			} finally {
				Thread.sleep(2000);
				driver.quit();

			}
			break;

		}
		case 4: {

			// wrong username and wrong password is passed
			try {
				WebDriverWait w = new WebDriverWait(driver, 3);
				lp.getEmail().sendKeys(Username);
				log.info("Username entered as  " + Username);
				lp.getPassword().sendKeys(Password);
				log.info("password  entered as  " + Password);
				lp.getLoginButton().click();
				log.info("Clicked on login");
				w.until(ExpectedConditions.visibilityOf(lp.getToastMessage()));
				lp.loginToastMessage();
				lp.errorMessage();

				if (lp.errorMessage().equals(prop.getProperty("loginErrorMessage"))
						&& lp.loginToastMessage().equals(prop.getProperty("loginToastMessage"))) {
					// System.out.println(lp.errorMessage());
					log.info("Navigated to correct url ie. " + prop.getProperty("loginUrl"));
					log.info("Error Message matched ");
					log.info("Toast message Verified");
					Assert.assertTrue(w.until(ExpectedConditions.urlMatches(prop.getProperty("loginUrl"))));

				}

			} catch (Exception e) {
				log.error(e);
			} finally {
				Thread.sleep(2000);
				driver.quit();

			}
			break;
		}
		default: {
			AssertJUnit.assertFalse(false);
			driver.close();
		}

		}
	}

	@DataProvider
	public Object[][] getData() {
		// Rows stand for how many values per each Test
		// Column stand for how many input to be given
		Object[][] data = new Object[4][3];
		// DataSets
		// 1: correct UserName & Password
		
		 data[0][0] = "automation"; 
		 data[0][1] = "automation"; 
		 data[0][2] = "1";
		 

		// 2:correct UserName & wrong Password
		data[1][0] = "automation";
		data[1][1] = "1234567";
		data[1][2] = "2";

		
		 // 3:wrong UserName & correct Password 
		data[2][0] = "hs001"; 
		data[2][1] ="automation"; 
		data[2][2] = "3";
		 
		 // 4:wrong UserName & wrong Password 
		data[3][0] = "hs001"; 
		data[3][1] = "1234567"; 
		data[3][2] = "4";
		 

		return data;

	}
	
	
}
