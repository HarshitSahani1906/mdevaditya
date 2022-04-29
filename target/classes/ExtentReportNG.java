package Resources;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG extends base{
	static ExtentReports extent;

	public static ExtentReports getReportObject() {
		String path = System.getProperty("user.dir") + "/reports/index.html";
		//String path = System.getProperty("user.dir")+"/reports/"+dtf.format(now).toString().replaceAll(" ", "_").replaceAll("/", "-").replaceAll(":", "-")+"/index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		String Username=System.getProperty("BUILD_USER");
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		//extent.setSystemInfo("Tester", "Harshit Sahani");
		extent.setSystemInfo("Tester", Username);
		return extent;

	}
}
