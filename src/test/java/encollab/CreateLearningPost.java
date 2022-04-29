package encollab;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import Resources.base;
import pageObjects.CreatePostPage;
import pageObjects.HubPage;
import pageObjects.LandingPage;
import pageObjects.LearnigGridPage;
import pageObjects.MyProfilePage;
import pageObjects.MySpacePage;

public class CreateLearningPost extends base  {
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
		lp.getEmail().sendKeys(prop.getProperty("username"));
		log.info("Entered Username");
		lp.getPassword().sendKeys(prop.getProperty("password"));
		log.info("Entered Password");
		lp.getLoginButton().click();
		log.info("Clicked on Login Button");
		w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));

	}
	

	@Test(priority = 0)
	public void CreateLearningDraft() throws IOException, InterruptedException {

		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Initiating Method :' " + MethodName + " '");
		w.until(ExpectedConditions.elementToBeClickable(hp.getLearningGridButton()));
		hp.getLearningGridButton().click();
		String PostHeadLineText = null;
		String PostDescription = null;
		try {
			if (w.until(ExpectedConditions.urlMatches(prop.getProperty("learningGrid")))) {
				w.until(ExpectedConditions.elementToBeClickable(lg.getCreatePostButton()));
				lg.getCreatePostButton().click();
				LocalDateTime now;
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

				try {
					if (w.until(ExpectedConditions.urlMatches(prop.getProperty("learningCreatePost")))) {
						w.until(ExpectedConditions.elementToBeClickable(cpp.getPostHeadline()));
						String PostHedlinePlaceHolder = cpp.getPostHeadline().getAttribute("placeholder").toString();

						if (PostHedlinePlaceHolder.equals("Post Headline")) {

							now = LocalDateTime.now();
							PostHeadLineText = dtf.format(now)
									+ " This is Content saved after creating the draft which is present is my spaces section of dev build..";
							cpp.getPostHeadline().sendKeys(PostHeadLineText);
							log.info("Post Headline Inserted");

						} else {
							log.error("Place holder for PostHealine is not correct ");
							Assert.assertTrue(PostHedlinePlaceHolder.equals("Post Headline"),
									"Place holder for PostHealine is not correct");
						}
						String SubHeadlinePlaceholder = cpp.getSubHealine().getAttribute("placeholder").toString();
						if (SubHeadlinePlaceholder.equals("Write a sub-heading..")) {
							now = LocalDateTime.now();
							cpp.getSubHealine()
									.sendKeys("This is a sub headline post created from Automated Script on: "
											+ dtf.format(now).toString());
							log.info("Sub  Headline Inserted");
						} else {
							log.error("Place holder for Sub Healine is not correct ");
							Assert.assertTrue(SubHeadlinePlaceholder.equals("Write a sub-heading.."),
									"Place holder for Sub Healine is not correct");
						}
						Thread.sleep(1000);

						List<String> windowhandles = new ArrayList<String>();
						Set<String> windows = driver.getWindowHandles();
						Iterator<String> it1 = windows.iterator();
						while (it1.hasNext()) {
							windowhandles.add(it1.next());
						}

						String parentId = windowhandles.get(0);
						try {
							now = LocalDateTime.now();
							cpp.getPostDescription();
							PostDescription = dtf.format(now).toString()
									+ "content changed for draft : this is a post description body, data entered here is through automated script on :"
									+ " to create a post ";
							cpp.getDescriptionBoy().sendKeys(PostDescription);
							log.info(" Post description is inserted ");
							driver.switchTo().window(parentId);
						} catch (Exception e) {
							log.error(e);
						}
						cpp.getTags().sendKeys("automation");
						log.info("tag is inserted ");
						int randomchoice = randomNumbergenration(1, 21);
						cpp.getPostCategory(randomchoice).click();
						Thread.sleep(2000);
						try {
							if (cpp.getSaveDraftButton().isEnabled()) {
								cpp.getSaveDraftButton().click();
								Thread.sleep(1000);
								log.info("Save Draft button clicked");
								try {
									w.until(ExpectedConditions.elementToBeClickable(cpp.getToastMessage()));
									if (cpp.getToastMessage().getText().equals(prop.getProperty("DraftToastMessage"))) {
										log.info("Save message verified");

									}

								} catch (Exception e) {
									log.error("Confirmation message didn't showed", e);
									Assert.assertTrue(false, "confirmation modal didn't showed Up :" + e);

								}
								try {
									if (w.until(ExpectedConditions.urlMatches(prop.getProperty("myspace")))) {

										w.until(ExpectedConditions.elementToBeClickable(msp.getPostEditButton()));
										String Update = getDate();
										try {
											Assert.assertEquals(msp.getPostDescription().getText().toString(),
													PostDescription.substring(0, 100));
											log.info("Post Description matched");
											Assert.assertEquals(msp.getPostUpdatedOn().getText().toString(), Update);
											log.info("Post udate On data matched");
											Assert.assertEquals(msp.getPostHeadline().getText().toString(),
													PostHeadLineText.substring(0, 120));
											log.info("Post HeadLine data matched");
										} catch (Exception e) {
											log.error("Error occured while fetching content of draft", e);

										}
									}

								} catch (Exception e) {
									log.error("MySpace Url  is not  loaded in the given time ", e);
									Assert.assertTrue(false, "MySpace Url  is not  loaded in the given time : " + e);

								}

							}
						} catch (Exception e) {
							log.error("Save Draft button is not active ", e);
						}

					}
				} catch (Exception e) {
					log.error("Url for Create Post  is not matching  :", e);
					Assert.assertTrue(false,
							"URl displayed is not of Create post   URL displayed is " + driver.getCurrentUrl());
				}
			}

		} catch (Exception e) {
			log.error("Url for learning grid is not matching  :", e);
			Assert.assertTrue(false,
					"URl displayed is not of learning grid /n URL displayed is " + driver.getCurrentUrl());
		}

	}

	@Test(priority = 1)
	public void CreatePostwithYoutubeLink() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Initiating Method :' " + MethodName + " '");
		w.until(ExpectedConditions.elementToBeClickable(hp.getLearningGridButton()));
		hp.getLearningGridButton().click();
		String PostHeadLineText = null;
		String PostDescription = null;
		String PostCategoryname = null;
		try {
			if (w.until(ExpectedConditions.urlMatches(prop.getProperty("learningGrid")))) {
				w.until(ExpectedConditions.elementToBeClickable(lg.getCreatePostButton()));
				lg.getCreatePostButton().click();
				LocalDateTime now;
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

				try {
					if (w.until(ExpectedConditions.urlMatches(prop.getProperty("learningCreatePost")))) {
						w.until(ExpectedConditions.elementToBeClickable(cpp.getPostHeadline()));
						String PostHedlinePlaceHolder = cpp.getPostHeadline().getAttribute("placeholder").toString();

						if (PostHedlinePlaceHolder.equals("Post Headline")) {

							now = LocalDateTime.now();
							PostHeadLineText = dtf.format(now)
									+ " This is Content saved after creating the draft which is present is my spaces section of dev build..";
							cpp.getPostHeadline().sendKeys(PostHeadLineText);
							log.info("Post Headline Inserted");
						} else {
							log.error("Place holder for PostHealine is not correct ");
							Assert.assertTrue(PostHedlinePlaceHolder.equals("Post Headline"),
									"Place holder for PostHealine is not correct");
						}
						String SubHeadlinePlaceholder = cpp.getSubHealine().getAttribute("placeholder").toString();
						if (SubHeadlinePlaceholder.equals("Write a sub-heading..")) {
							now = LocalDateTime.now();
							cpp.getSubHealine()
									.sendKeys("This is a sub headline post created from Automated Script on: "
											+ dtf.format(now).toString());
							log.info("Sub  Headline Inserted");
						} else {
							log.error("Place holder for Sub Healine is not correct ");
							Assert.assertTrue(SubHeadlinePlaceholder.equals("Write a sub-heading.."),
									"Place holder for Sub Healine is not correct");
						}
						Thread.sleep(1000);

						try {
							cpp.getYoutubeLinkButton().click();
							w.until(ExpectedConditions.elementToBeClickable(cpp.getYoutubeLinkTextField()));
							linksChoose();
							cpp.getYoutubeLinkTextField().sendKeys(links.getProperty("YoutubeLink"));
							if (cpp.getYoutubeLinkSubmitButton().isEnabled()) {
								cpp.getYoutubeLinkSubmitButton().click();
								log.info("Youtube Submit button clicked");
								Thread.sleep(2000);
							} else {
								log.error("Youtube Link Submit button is not enabled ");
								Assert.assertTrue(false, "Youtube Link Submit button is not enabled ");
							}

						} catch (Exception e) {
							log.error("Unable to upload youtube link", e);
							Assert.assertTrue(false, "Unable to upload youtube link" + e);

						}

						List<String> windowhandles = new ArrayList<String>();
						Set<String> windows = driver.getWindowHandles();
						Iterator<String> it1 = windows.iterator();
						while (it1.hasNext()) {
							windowhandles.add(it1.next());
						}

						String parentId = windowhandles.get(0);
						try {
							now = LocalDateTime.now();
							cpp.getPostDescription();
							PostDescription = dtf.format(now).toString()
									+ " Content changed for draft : this is a post description body, data entered here is through automated script on :"
									+ " to create a post ";
							cpp.getDescriptionBoy().sendKeys(PostDescription);
							log.info(" Post description is inserted ");
							driver.switchTo().window(parentId);
						} catch (Exception e) {
							log.error(e);

						}
						cpp.getTags().sendKeys("automation");
						log.info("tag is inserted ");
						int randomchoice = randomNumbergenration(1, 21);
						PostCategoryname = cpp.getPostCategory(randomchoice).getText().toString();
						System.out.println("Post Category name is : " + PostCategoryname);
						cpp.getPostCategory(randomchoice).click();
						Thread.sleep(2000);

						try {
							if (cpp.getSubmitButton().isEnabled()) {
								cpp.getSubmitButton().click();
								log.info("Submit Button clicked ");
								try {
									w.until(ExpectedConditions.elementToBeClickable(cpp.getConfirmationMessage()));
									if (cpp.getConfirmationMessage().getText()
											.equals(prop.getProperty("createPostConfirmationMessage"))) {
										cpp.getcloseButtonDialogBox().click();
										log.info("close dialog button clicked ");
										w.until(ExpectedConditions.urlMatches(prop.getProperty("hubPage")));
										w.until(ExpectedConditions.elementToBeClickable(hp.getMySpaceButton()));
										hp.getMySpaceButton().click();
										w.until(ExpectedConditions.elementToBeClickable(msp.getMyPendingPostButton()));
										msp.getMyPendingPostButton().click();
										w.until(ExpectedConditions.visibilityOf(msp.getPendingPostHeadline()));

										try {
											Assert.assertEquals(msp.getPendingPostHeadline().getText().toString(),
													PostHeadLineText.substring(0, 80) + " ...");
											log.info("Post Headline Matched");
											Assert.assertEquals(msp.getPendingPostDescription().getText().toString(),
													PostDescription.substring(0, 90));
											log.info("Post description Matched");
											Assert.assertEquals(msp.getPendingPostCategory().getText().toString(),
													PostCategoryname);
											log.info("Post Category Matched");
											Assert.assertEquals(msp.getPendingPostUpdatedOn().getText().toString(),
													getDate());
											log.info("Post Updated On Date is matched ");

										} catch (Exception e) {

										}

									}

								} catch (Exception e) {
									log.error("confirmation modal didn't showed Up", e);
									Assert.assertTrue(false, "confirmation modal didn't showed Up :" + e);

								}

							} else {
								log.info("Testcase Failed as submit buttonis not ative ");
								Assert.assertTrue(cpp.getSubmitButton().isEnabled(), "Submit button is disabled");

							}
						} catch (Exception e) {
							log.error("Submit button is not active ", e);
						}

					}
				} catch (Exception e) {
					log.error("Url for Create Post  is not matching  :", e);
					Assert.assertTrue(false,
							"URl displayed is not of Create post  /n URL displayed is " + driver.getCurrentUrl());
				}
			}

		} catch (Exception e) {
			log.error("Url for learning grid is not matching  :", e);
			Assert.assertTrue(false,
					"URl displayed is not of learning grid /n URL displayed is " + driver.getCurrentUrl());
		}

	}

	@Test(priority=3,alwaysRun=true)
	public void DeleteDraft() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Initiating Method :' " + MethodName + " '");
		try {
			if(driver.getCurrentUrl().equals(prop.getProperty("myspace"))) {
			log.info("User is on My space Page ");
			msp.getMyDraftButton().click();
			w.until(ExpectedConditions.elementToBeClickable(msp.getPostDeleteButton()));
			}
			else {
				w.until(ExpectedConditions.elementToBeClickable(hp.getMySpaceButton()));
				hp.getMySpaceButton().click();
				w.until(ExpectedConditions.urlMatches(prop.getProperty("myspace")));
				w.until(ExpectedConditions.elementToBeClickable(msp.getMyDraftButton()));
				msp.getMyDraftButton().click();
			}
			
		}catch(Exception e) {
			log.error("User is unable to navigate to my space page ", e);
			Assert.assertTrue(false, "User is unable to navigate to my space page "+e);
			
		}
		w.until(ExpectedConditions.elementToBeClickable(msp.getPostDeleteButton()));
		msp.getPostDeleteButton().click();
		w.until(ExpectedConditions.elementToBeClickable(msp.getDeleteConfirmationButton()));
		msp.getDeleteConfirmationButton().click();

		try {
			w.until(ExpectedConditions.visibilityOf(msp.getDraftDeleteToastMessage()));
			if (msp.getDraftDeleteToastMessage().getText().equals(prop.getProperty("DraftDeleteToastMessage"))) {
				log.info("Draft Delete  message verified");
			}

		} catch (Exception e) {
			log.error("Delete Draft Confirmation modal didn't showed", e);
			Assert.assertTrue(false, "confirmation modal didn't showed Up :" + e);
		}

	}

	 @Test(priority=2,alwaysRun=true)
	public void EditDraft() throws IOException, InterruptedException {
		MethodName = new Throwable().getStackTrace()[0].getMethodName();
		log.info("Initiating Method :' " + MethodName + " '");
		try {
			if(driver.getCurrentUrl().equals(prop.getProperty("myspace"))) {
			log.info("User is on My space Page ");
			msp.getMyDraftButton().click();
			w.until(ExpectedConditions.elementToBeClickable(msp.getPostEditButton()));
			
			}
			else {
				w.until(ExpectedConditions.elementToBeClickable(hp.getMySpaceButton()));
				hp.getMySpaceButton().click();
				w.until(ExpectedConditions.urlMatches(prop.getProperty("myspace")));
				w.until(ExpectedConditions.elementToBeClickable(msp.getMyDraftButton()));
				msp.getMyDraftButton().click();
				
			}
			
		}catch(Exception e) {
			log.error("User is unable to navigate to my space page ", e);
			Assert.assertTrue(false, "User is unable to navigate to my space page "+e);
			
		}
		w.until(ExpectedConditions.elementToBeClickable(msp.getPostEditButton()));
		log.info("First Post headline is : " + msp.getPostHeadline().getText().toString());
		log.info("First Post Description is : " + msp.getPostDescription().getText().toString());
		log.info("First Post Updated on : " + msp.getPostUpdatedOn().getText().toString());
		msp.getPostEditButton().click();
		String PostHeadLineContent = null;
		String PostSubHeadLineContent = null;
		String PostDescriptionContent = null;
		LocalDateTime now;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		try {
			if (w.until(ExpectedConditions.urlContains(prop.getProperty("editDraftPage")))) {
				try {
					cpp.getPostHeadline().click();
					Thread.sleep(500);
					cpp.getPostHeadline().clear();
					Thread.sleep(500);
					cpp.getSubHealine().clear();
					cpp.getTags().clear();
					String PostHedlinePlaceHolder = cpp.getPostHeadline().getAttribute("placeholder").toString();
					if (PostHedlinePlaceHolder.equals("Post Headline")) {
						now = LocalDateTime.now();
						PostHeadLineContent = dtf.format(now)
								+ " This is Content saved after editing the draft which is present is my spaces section of dev build...";
						System.out.println(PostHeadLineContent.length());
						cpp.getPostHeadline().sendKeys(PostHeadLineContent);
						log.info("Post Headline Inserted");
					} else {
						log.error("Place holder for PostHealine is not correct ");
						Assert.assertTrue(PostHedlinePlaceHolder.equals("Post Headline"),
								"Place holder for PostHealine is not correct");
					}
					String SubHeadlinePlaceholder = cpp.getSubHealine().getAttribute("placeholder").toString();
					if (SubHeadlinePlaceholder.equals("Write a sub-heading..")) {
						now = LocalDateTime.now();
						PostSubHeadLineContent = "This is a sub headline post for draft saved :"
								+ dtf.format(now).toString();
						cpp.getSubHealine().sendKeys(PostSubHeadLineContent);
						log.info("Sub  Headline Inserted");
					} else {
						log.error("Place holder for Sub Healine is not correct ");
						Assert.assertTrue(SubHeadlinePlaceholder.equals("Write a sub-heading.."),
								"Place holder for Sub Healine is not correct");
					}
					Thread.sleep(1000);
					List<String> windowhandles = new ArrayList<String>();
					Set<String> windows = driver.getWindowHandles();
					Iterator<String> it1 = windows.iterator();
					while (it1.hasNext()) {
						windowhandles.add(it1.next());
					}
					String parentId = windowhandles.get(0);
					try {
						now = LocalDateTime.now();
						cpp.getPostDescription();
						cpp.getDescriptionBoy().clear();
						Thread.sleep(500);
						PostDescriptionContent = dtf.format(now).toString()
								+ "content changed for draft : this is a post description body, data entered here is through automated script on :"
								+ " to create a post ";
						System.out.println(PostDescriptionContent.length());
						cpp.getDescriptionBoy().sendKeys(PostDescriptionContent);
						log.info(" Post description is inserted ");
						driver.switchTo().window(parentId);
					} catch (Exception e) {
						log.error(e);

					}
					cpp.getTags().sendKeys("AfterEdit");
					cpp.getTags().sendKeys(Keys.ENTER);
					log.info("tag is inserted ");
					int randomchoice = randomNumbergenration(1, 21);
					cpp.getPostCategory(randomchoice).click();
					Thread.sleep(2000);
					try {
						if (cpp.getSaveDraftButton().isEnabled()) {
							cpp.getSaveDraftButton().click();
							log.info("Save Draft button clicked");
							try {
								w.until(ExpectedConditions.elementToBeClickable(cpp.getToastMessage()));
								if (cpp.getToastMessage().getText().equals(prop.getProperty("DraftToastMessage"))) {
									log.info("Save message verified");
								}

							} catch (Exception e) {
								log.error("Confirmation message didn't showed", e);
								Assert.assertTrue(false, "confirmation modal didn't showed Up :" + e);
							}
							try {
								if (w.until(ExpectedConditions.urlMatches(prop.getProperty("myspace")))) {

									w.until(ExpectedConditions.elementToBeClickable(msp.getPostEditButton()));
									String Update = getDate();
									try {
										Assert.assertEquals(msp.getPostUpdatedOn().getText().toString(), Update);
										log.info("Post creation date matched ");
										Assert.assertEquals(msp.getPostDescription().getText().toString(),
												PostDescriptionContent.substring(0, 100));
										log.info("Post description matched ");
										Assert.assertEquals(msp.getPostHeadline().getText().toString(),
												PostHeadLineContent.substring(0, 120));
										log.info("Post HeadLine matched ");

									} catch (Exception e) {
										log.error("Error occured while fetching content of draft", e);

									}
								}

							} catch (Exception e) {
								log.error("MySpace Url  is not  loaded in the given time ", e);
								Assert.assertTrue(false, "MySpace Url  is not  loaded in the given time : " + e);

							}

						} else {
							log.error("Save Draft  button is not active ");
							Assert.assertTrue(false, "Save Draft  button is not active ");
						}
					} catch (Exception e) {
						log.error("issues Occured with Save Draft Button ", System.getProperty("line.separator"), e);
						Assert.assertTrue(false,
								"issues Occured with Save Draft Button " + System.getProperty("line.separator") + e);
					}

				} catch (Exception e) {
					Assert.assertTrue(false, "Unable to lter data in text fields " + e);
				}

			}

		} catch (Exception e) {
			log.error("Url doesn't matches with Edit Draft Page", e);
			Assert.assertTrue(false, "Url doesn't matches with Edit Draft Page" + e);
		}

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
	

	public String getDate() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
		String UpdateOn = formatter.format(date);
		//System.out.println("Date Format with dd MMMM yyyy : " + UpdateOn);
		return UpdateOn;
	}




}
