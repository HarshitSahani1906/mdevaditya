package encollab;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Resources.base;
import pageObjects.AdminControlPage;
import pageObjects.CreatePostPage;
import pageObjects.HubPage;
import pageObjects.LandingPage;
import pageObjects.LearnigGridPage;
import pageObjects.MyProfilePage;
import pageObjects.MySpacePage;

public class AdminApprovePost extends base {
	public WebDriver driver;
	public static Logger log = LogManager.getLogger(base.class.getName());
	String MethodName = null;
	WebDriverWait w;
	MyProfilePage mp;
	LandingPage lp;
	HubPage hp;
	LearnigGridPage lg;
	CreatePostPage cpp;
	MySpacePage msp;
	AdminControlPage acp;

	public String getDate() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
		String UpdateOn = formatter.format(date);
		System.out.println("Date Format with dd MMMM yyyy : " + UpdateOn);
		return UpdateOn;
	}

	@BeforeTest
	public void openBrowser() throws IOException, InterruptedException {
		driver = initializeDriver();
		w = new WebDriverWait(driver, 3);
		mp = new MyProfilePage(driver);
		lp = new LandingPage(driver);
		hp = new HubPage(driver);
		lg = new LearnigGridPage(driver);
		cpp = new CreatePostPage(driver);
		msp = new MySpacePage(driver);
		acp = new AdminControlPage(driver);
		lp.getEmail().sendKeys(prop.getProperty("username"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("password"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");
		w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));

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

	@Test
	public void PostApprove() {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Initiating Method :' " + MethodName + " '");
		try {
			w.until(ExpectedConditions.elementToBeClickable(hp.getMyProfileLogo()));
			hp.getMyProfileLogo().click();
			w.until(ExpectedConditions.elementToBeClickable(hp.getMyProfileButton()));
			if (hp.getAdminControlButtonPresence()) {

				w.until(ExpectedConditions.elementToBeClickable(hp.getLogoutButton()));
				hp.getLogoutButton().click();
				w.until(ExpectedConditions.urlMatches(prop.getProperty("loginUrl")));
				w.until(ExpectedConditions.elementToBeClickable(lp.getEmail()));
				lp.getEmail().sendKeys(prop.getProperty("adminusername"));
				log.info("Entered Username");
				lp.getPassword().sendKeys(prop.getProperty("adminpassword"));
				log.info("Entered Password");
				lp.getLoginButton().click();
				log.info("Clicked on Login Button");
				w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
			}

		} catch (Exception e) {
			log.error("User is unable to login with admin credentials ", e);
			Assert.assertTrue(false, "User is unable to login with admin credentials " + e);

		}
		w.until(ExpectedConditions.elementToBeClickable(hp.getMyProfileLogo()));
		hp.getMyProfileLogo().click();
		w.until(ExpectedConditions.elementToBeClickable(hp.getAdminControlButton()));
		hp.getAdminControlButton().click();
		w.until(ExpectedConditions.urlMatches(prop.getProperty("adminControlPage")));
		log.info("Admin control button clicked ");
		w.until(ExpectedConditions.elementToBeClickable(acp.getPostSection()));
		acp.getPostSection().click();
		w.until(ExpectedConditions.elementToBeClickable(acp.getPostPendingTab()));
		acp.getPostPendingTab().click();
		w.until(ExpectedConditions.elementToBeClickable(acp.getPendingPostApproveButton()));
		String PendingPostUser = acp.getPendingPostUser().getText().toString();
		String PendingPostTittle = acp.getPendingPostTittle().getText().toString();
		String PendingPostCategory = acp.getPendingPostCategory().getText().toString();
		String PendingPostCreatedOn = acp.getPendingPostCreatedOn().getText().toString();
		String PendingPostLandingPage = acp.getPendingPostLandingPage().getText().toString();
		log.info(PendingPostUser);
		acp.getPendingPostApproveButton().click();
		try {
			w.until(ExpectedConditions.visibilityOf(acp.getPostApproveToastMessage()));
			if (acp.getPostApproveToastMessage().getText().equals(prop.getProperty("PostApprovedToastMessage"))) {
				log.info("Post approve  message verified");

			}

		} catch (Exception e) {
			log.error(" Post approval Confirmation message didn't showed", e);
			Assert.assertTrue(false, " Post approval Confirmation message didn't showed" + e);

		}
		try {
			acp.getPostApprovedTab().click();
			log.info("clicked on approved button ");

			try {
				w.until(ExpectedConditions.visibilityOf(acp.getApprovedPostUser()));
				Assert.assertEquals(acp.getApprovedPostUser().getText().toString(), PendingPostUser);
				Assert.assertEquals(acp.getApprovedPostTittle().getText().toString(), PendingPostTittle);
				Assert.assertEquals(acp.getApprovedPostCategory().getText().toString(), PendingPostCategory);
				Assert.assertEquals(acp.getApprovedPostCreatedOn().getText().toString(), PendingPostCreatedOn);
				Assert.assertEquals(acp.getapprovedPostLandingPage().getText().toString(), PendingPostLandingPage);
				log.info("data matched after approving the post");

			} catch (Exception e) {
				log.error("Data didn't matched of pending post when approved ", e);
				Assert.assertTrue(false, "Data didn't matched of pending post when approved " + e);

			}

		} catch (Exception e) {
			log.error("Unable to click on approve  button", e);
			Assert.assertTrue(false, "Data didn't matched of pending post when approved " + e);

		}
	}

	@Test(dependsOnMethods = { "PostApprove" })
	public void PostDecline() {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Initiating Method :' " + MethodName + " '");
		LocalDateTime now;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MMMM/YYYY HH:mm:ss");
		try {
			w.until(ExpectedConditions.elementToBeClickable(hp.getMyProfileLogo()));
			hp.getMyProfileLogo().click();
			w.until(ExpectedConditions.elementToBeClickable(hp.getMyProfileButton()));
			if (hp.getAdminControlButtonPresence()) {

				w.until(ExpectedConditions.elementToBeClickable(hp.getLogoutButton()));
				hp.getLogoutButton().click();
				w.until(ExpectedConditions.urlMatches(prop.getProperty("loginUrl")));
				w.until(ExpectedConditions.elementToBeClickable(lp.getEmail()));
				lp.getEmail().sendKeys(prop.getProperty("adminusername"));
				log.info("Entered Username");
				lp.getPassword().sendKeys(prop.getProperty("adminpassword"));
				log.info("Entered Password");
				lp.getLoginButton().click();
				log.info("Clicked on Login Button");
				w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
			}

		} catch (Exception e) {
			log.error("User is unable to login with admin credentials ", e);
			Assert.assertTrue(false, "User is unable to login with admin credentials " + e);

		}
		w.until(ExpectedConditions.elementToBeClickable(hp.getMyProfileLogo()));
		hp.getMyProfileLogo().click();
		w.until(ExpectedConditions.elementToBeClickable(hp.getAdminControlButton()));
		hp.getAdminControlButton().click();
		w.until(ExpectedConditions.urlMatches(prop.getProperty("adminControlPage")));
		log.info("Admin control button clicked ");
		w.until(ExpectedConditions.elementToBeClickable(acp.getPostSection()));
		acp.getPostSection().click();
		w.until(ExpectedConditions.elementToBeClickable(acp.getPostPendingTab()));
		acp.getPostPendingTab().click();
		w.until(ExpectedConditions.elementToBeClickable(acp.getPendingPostApproveButton()));
		String PendingPostUser = acp.getPendingPostUser().getText().toString();
		String PendingPostTittle = acp.getPendingPostTittle().getText().toString();
		String PendingPostCategory = acp.getPendingPostCategory().getText().toString();
		String PendingPostCreatedOn = acp.getPendingPostCreatedOn().getText().toString();
		String PendingPostLandingPage = acp.getPendingPostLandingPage().getText().toString();
		log.info(PendingPostUser);
		acp.getPendingPostDeclineButton().click();

		w.until(ExpectedConditions.elementToBeClickable(acp.getDeclineResponseTextArea()));
		now = LocalDateTime.now();
		String SubmitResponseCheck = "this is a response";
		acp.getDeclineResponseTextArea().sendKeys(SubmitResponseCheck);
		Boolean ValidateSubmitButton = acp.getDeclineSUbmitResponseButton().isEnabled();
		try {
			if (ValidateSubmitButton == false) {
				acp.getDeclineResponseTextArea().clear();
				Thread.sleep(500);
				String PostDeclineMessage = dtf.format(now) + " This is a response to Decline Post through automation";
				acp.getDeclineResponseTextArea().sendKeys(PostDeclineMessage);
				ValidateSubmitButton = acp.getDeclineSUbmitResponseButton().isEnabled();
				if (ValidateSubmitButton == true) {
					acp.getDeclineSUbmitResponseButton().click();
					w.until(ExpectedConditions.visibilityOf(acp.getPostApproveToastMessage()));
					if (acp.getPostApproveToastMessage().getText()
							.equals(prop.getProperty("PostDeclineToastMessage"))) {
						log.info("Post approve  message verified");
						hp.getMyProfileLogo().click();
						w.until(ExpectedConditions.elementToBeClickable(hp.getLogoutButton()));
						hp.getLogoutButton().click();
						w.until(ExpectedConditions.urlMatches(prop.getProperty("loginUrl")));
						w.until(ExpectedConditions.elementToBeClickable(lp.getEmail()));
						lp.getEmail().sendKeys(prop.getProperty("username"));
						log.info("Entered Username");
						lp.getPassword().sendKeys(prop.getProperty("password"));
						log.info("Entered Password");
						lp.getLoginButton().click();
						log.info("Clicked on Login Button");
						w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
						w.until(ExpectedConditions.elementToBeClickable(hp.getMySpaceButton()));
						hp.getMySpaceButton().click();
						w.until(ExpectedConditions.elementToBeClickable(msp.getMyPendingPostButton()));
						msp.getMyPendingPostButton().click();
						w.until(ExpectedConditions.visibilityOf(msp.getDecineMessageButton()));
						try {
							if(PendingPostTittle.length()>80) {
								log.info(msp.getPendingPostHeadline().getText().toString());
								log.info(PendingPostTittle.length()+" : "+PendingPostTittle.substring(0, 80) + " ...");
	
								Assert.assertEquals(msp.getPendingPostHeadline().getText().toString(),
										PendingPostTittle.substring(0, 80) + " ...");
								log.info("Post Headline Matched");
							}else {
								log.info(msp.getPendingPostHeadline().getText().toString());
								log.info(PendingPostTittle.length()+" : "+ PendingPostTittle);
								Assert.assertEquals(msp.getPendingPostHeadline().getText().toString(),
										PendingPostTittle );
								log.info("Post Headline Matched");
								
							}
							
							
							Assert.assertEquals(msp.getPendingPostCategory().getText().toString(),
									PendingPostCategory);
							log.info("Post Category Matched");
							Assert.assertEquals(msp.getPendingPostUpdatedOn().getText().toString(),
									getDate());
							log.info("Post Updated On Date is matched ");

						} catch (Exception e) {
							log.error("Data mismatched under My Pending Post Section", e);
							Assert.assertTrue(false, "Data mismatched under My Pending Post Section"+e);

						}
						
						msp.getDecineMessageButton().click();
						w.until(ExpectedConditions.elementToBeClickable(msp.getAdminDeclineMessageTextArea()));
						Assert.assertEquals(msp.getAdminDeclineMessageTextArea().getText(), PostDeclineMessage);
						log.info("Decline Message verified");
						msp.getCloseDialogBox().click();
					} else {
						log.error("Toast Message for declining a Post didn't matched ");
						Assert.assertTrue(false, "Toast Message for declining a Post didn't matched ");

					}
				} else {
					log.error("Validation over Decline Post Submit button failed. It should be enabled at this point ");
					Assert.assertTrue(false,
							"Validation over Decline Post Submit button failed. It should be enabled at this point ");

				}

			} else {
				log.error("Validation over Decline Post Submit button failed. It should be disabled at this point ");
				Assert.assertTrue(false,
						"Validation over Decline Post Submit button failed. It should be disabled at this point ");

			}
		} catch (Exception e) {
			log.error("Error Occurred while Submitting a respone for Declining a post ");
			Assert.assertTrue(false, "Error Occurred while Submitting a respone for Declining a post ");

		}

		
	}



}
