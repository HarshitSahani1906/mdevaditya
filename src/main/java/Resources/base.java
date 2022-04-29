package Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

public class base {
	public WebDriver driver;
	public Properties prop;
	public Properties prop1;
	public Properties links;
	public static Logger log = LogManager.getLogger(base.class.getName());
	private static String OS = System.getProperty("os.name").toLowerCase();
	public String pathForSocialFile = null;
	public String pathForLinksFile=null;


	public WebDriver initializeDriver() throws IOException {

		prop = new Properties();
		String userDirectory = null;
		String currentDir = System.getProperty("user.dir");
		String pathForDataPropertyFile = currentDir + "/src/main/java/Resources/data.properties";
		FileInputStream fis = new FileInputStream(pathForDataPropertyFile);
		prop.load(fis);

		prop1 = new Properties();
		pathForSocialFile = currentDir + "/src/main/java/Resources/social.properties";
		FileInputStream fis1 = new FileInputStream(pathForSocialFile);
		prop1.load(fis1);
		
		links=new Properties();
		pathForLinksFile = currentDir + "/src/main/java/Resources/links.properties";
		FileInputStream fis2 = new FileInputStream(pathForLinksFile);
		links.load(fis2);
		


		String browser = prop.getProperty("browser");
		//String browser = System.getProperty("BROWSER");
		

		if (browser.equals("chrome")) {

			if (isWindows()) {
				userDirectory = currentDir + "/drivers/chromedriver.exe";
				// System.out.println("This is Windows");
			} else if (isMac()) {
				userDirectory = currentDir + "/drivers/chromedriver";
				// System.out.println("This is Mac");
			} else if (isUnix()) {
				userDirectory = currentDir + "/drivers/chromedriver";
				// System.out.println("This is Unix or Linux");
			} else {
				log.error("Invalid Operating System");
			}
			System.setProperty("webdriver.chrome.driver", userDirectory);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability("applicationCacheEnabled", false);
			ChromeOptions option = new ChromeOptions();
			option.addArguments("headless");
			// driver = new ChromeDriver(option);
			driver = new ChromeDriver();
			log.info("Driver is initialized");
			driver.manage().window().maximize();
			log.info("Window is maximised");
			driver.get(prop.getProperty("loginUrl"));
			log.info("Navigated to Url ");
		} else if (browser.equals("firefox")) {
			if (isWindows()) {
				userDirectory = currentDir + "/drivers/geckodriver.exe";
				// System.out.println("This is Windows");
			} else if (isMac()) {
				userDirectory = currentDir + "/drivers/geckodriver";
				// System.out.println("This is Mac");
			} else if (isUnix()) {
				userDirectory = currentDir + "/drivers/geckodriver";
				// System.out.println("This is Unix or Linux");
			} else {
				log.error("Invalid Operating System");
			}
			// executing code in Firefox driver
			System.setProperty("webdriver.gecko.driver", userDirectory);
			driver = new FirefoxDriver();
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setCapability("applicationCacheEnabled", false);
			log.info("Driver is initialised");
			driver.manage().window().maximize();
			log.info("Window is maximized");
			driver.get(prop.getProperty("loginUrl"));
			log.info("Navigated to Url ");

		}

		else if (browser.equals("safari")) {

			driver = new SafariDriver();
			DesiredCapabilities cap = DesiredCapabilities.safari();
			cap.setCapability("applicationCacheEnabled", false);

			log.info("Driver is initialised");
			driver.manage().window().maximize();
			log.info("Window is maximized");
			driver.get(prop.getProperty("loginUrl"));
			// System.out.println(driver);
			log.info("Navigated to Url ");
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return driver;
	}

	public String getTakeScreenShotPath(String TestCaseName, WebDriver driver) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts1 = new Timestamp(time);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String destinationFile = System.getProperty("user.dir") + "/reports/" + TestCaseName + ts1 + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;

	}

	public static int randomNumbergenration(int Lowerbound, int Upperbound) {
		int max = Upperbound;
		int min = Lowerbound;
		int int_random = (int) Math.floor(Math.random() * (max - min + 1) + min);
		return int_random;

	}

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

	public void accountChoose() throws IOException {

		int choice = randomNumbergenration(1, 4);
		// log.info("choice for social account is :" + choice);

		switch (choice) {
		case 1: {

			prop1.setProperty("facebookUserName", "Automation.emcollab@gmail.com");
			prop1.setProperty("facebookPassword", "Harry1906");

			prop1.store(new FileWriter(pathForSocialFile), "Changing Credential for Social Accounts");

			break;
		}
		case 2: {

			prop1.setProperty("facebookUserName", "Automation1.emcollab@gmail.com");
			prop1.setProperty("facebookPassword", "Harry1906");

			prop1.store(new FileWriter(pathForSocialFile), "Changing Credential for Social Accounts");

			break;

		}
		case 3: {

			prop1.setProperty("facebookUserName", "ayushi.thk@gmail.com");
			prop1.setProperty("facebookPassword", "Harry1906");

			prop1.store(new FileWriter(pathForSocialFile), "Changing Credential for Social Accounts");

			break;

		}
		case 4: {

			prop1.setProperty("facebookUserName", "ayushi.elv@gmail.com");
			prop1.setProperty("facebookPassword", "Harry1906");

			prop1.store(new FileWriter(pathForSocialFile), "Changing Credential for Social Accounts");

			break;

		}
		default: {
			log.info("choice  selected is beyond boundary limits ");

		}

		}

		choice = randomNumbergenration(1, 2);
		// log.info("choice for social account is :" + choice);

		switch (choice) {

		case 1: {

			prop1.setProperty("linkedInUserName", "rishabh.elv@gmail.com");
			prop1.setProperty("linkedInPassword", "Harry1906");

			prop1.store(new FileWriter(pathForSocialFile), "Changing Credential for Social Accounts");

			break;

		}
		case 2: {

			prop1.setProperty("linkedInUserName", "rishabh.elv.1906@gmail.com");
			prop1.setProperty("linkedInPassword", "Harry1906");

			prop1.store(new FileWriter(pathForSocialFile), "Changing Credential for Social Accounts");

			break;

		}
		default: {
			log.info("choice  selected is beyond boundary limits ");

		}

		}

		choice = randomNumbergenration(1, 6);
		// log.info("choice for social account is :" + choice);

		switch (choice) {
		case 1: {

			prop1.setProperty("instagramUserName", "Automation.emcollab@gmail.com");
			prop1.setProperty("instagramPassword", "Harry1906");

			prop1.store(new FileWriter(pathForSocialFile), "Changing Credential for Social Accounts");

			break;
		}
		case 2: {

			prop1.setProperty("instagramUserName", "Automation1.emcollab@gmail.com");
			prop1.setProperty("instagramPassword", "Harry1906");

			prop1.store(new FileWriter(pathForSocialFile), "Changing Credential for Social Accounts");

			break;

		}
		case 3: {

			prop1.setProperty("instagramUserName", "ayushi.thk@gmail.com");
			prop1.setProperty("instagramPassword", "Harry1906");

			prop1.store(new FileWriter(pathForSocialFile), "Changing Credential for Social Accounts");

			break;

		}
		case 4: {

			prop1.setProperty("instagramUserName", "ayushi.elv@gmail.com");
			prop1.setProperty("instagramPassword", "Harry1906");

			prop1.store(new FileWriter(pathForSocialFile), "Changing Credential for Social Accounts");

			break;

		}
		case 5: {

			prop1.setProperty("instagramUserName", "rishabh.elv@gmail.com");
			prop1.setProperty("instagramPassword", "Harry1906");

			prop1.store(new FileWriter(pathForSocialFile), "Changing Credential for Social Accounts");

			break;

		}
		case 6: {

			prop1.setProperty("instagramUserName", "rishabh.elv.1906@gmail.com");
			prop1.setProperty("instagramPassword", "Harry1906");

			prop1.store(new FileWriter(pathForSocialFile), "Changing Credential for Social Accounts");

			break;

		}
		default: {
			log.info("choice  selected is beyond boundary limits ");

		}

		}

	}



	public void linksChoose() throws IOException {

		int choice = randomNumbergenration(1, 15);
		log.info("choice for link  is :" + choice);

		switch (choice) {
		case 1: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=Hbt56gFj998");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		case 2: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=FRn5J31eAMw");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		case 3: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=hBh_CC5y8-s");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		case 4: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=TlB_eWDSMt4");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		case 5: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=0LhBvp8qpro");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		case 6: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=hKB-YGF14SY");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		case 7: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=W7kSd1nSrJI");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		case 8: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=vHzMJuc9Zuk&list=PLFGoYjJG_fqoBFPevCDZDCufDX5h-o3yO");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		case 9: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=FX322RVNGj4");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		case 10: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=uM_m6EzMg3k");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		case 11: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=rIaz-l1Kf8w");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		case 12: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=-ETQ97mXXF0");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		case 13: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=uT_GcOGEFsk");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		case 14: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=5cPIukqXe5w");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		
		case 15: {

			links.setProperty("YoutubeLink", "https://www.youtube.com/watch?v=dqlzQXo1wqo");			
			links.store(new FileWriter(pathForLinksFile), "Changing Youtube link for creating Post");
			break;
		}
		
		default: {
			log.info("choice  selected is beyond boundary limits ");

		}

		}


	}

}
