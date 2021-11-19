package actions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AbstractTest {
	private WebDriver driver;
	private final String workingDir = System.getProperty("user.dir");
	protected final Log log;

	// Contructor
	protected AbstractTest() {
		log = LogFactory.getLog(getClass());
	}

	protected int randomData() {
		Random random = new Random();
		return random.nextInt(99999);
	}

	public WebDriver openMultiBrowser(String browserName) {
		if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("firefoxheadless")) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(true);
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("chrome")) {
			//WebDriverManager.chromedriver().version("").setup();
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("headlesschrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("headless");
			options.addArguments("window-size=" + Constants.HEADLESS_RESOLUTION);
			driver = new ChromeDriver(options);
		} else if (browserName.equalsIgnoreCase("ie")) {

			WebDriverManager.iedriver().arch32().setup();
			driver = new InternetExplorerDriver();
		} else {
			System.out.println("Please choose your browser name in TestNG xml file");
		}
		driver.get(Constants.PRODUCTION_URL);
		driver.manage().timeouts().implicitlyWait(Constants.LONG_TIME_OUT, TimeUnit.SECONDS);
		return driver;
	}

	private boolean checkTrue(boolean condition) {
    	boolean pass = true;
		try {
			if (condition == true) {
				log.info(" -------------------------- PASSED -------------------------- ");
			} else {
				log.info(" -------------------------- FAILED -------------------------- ");
			}
			Assert.assertTrue(condition);
		} catch (Throwable e) {
			pass = false;

			// Add lỗi vào ReportNG
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyTrue(boolean condition) {
		return checkTrue(condition);
	}

	private boolean checkFailed(boolean condition) {
		boolean pass = true;
		try {
			if (condition == false) {
				log.info(" -------------------------- PASSED -------------------------- ");
			} else {
				log.info(" -------------------------- FAILED -------------------------- ");
			}
			Assert.assertFalse(condition);
		} catch (Throwable e) {
			pass = false;
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyFalse(boolean condition) {
		return checkFailed(condition);
	}

	private boolean checkEquals(Object actual, Object expected) {
		boolean pass = true;
		try {
			Assert.assertEquals(actual, expected);
			log.info(" -------------------------- PASSED -------------------------- ");
		} catch (Throwable e) {
			pass = false;
			log.info(" -------------------------- FAILED -------------------------- ");
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyEquals(Object actual, Object expected) {
		return checkEquals(actual, expected);
	}
	protected void closeBrowserAndDriver(WebDriver driver) {
		try {
			// get ra tên của OS và convert qua chữ thường
			String osName = System.getProperty("os.name").toLowerCase();
			log.info("OS name = " + osName);

			// Khai báo 1 biến command line để thực thi
			String cmd = "";
			if (driver != null) {
				driver.quit();
			}

			if (driver.toString().toLowerCase().contains("chrome")) {
				if (osName.toLowerCase().contains("mac")) {
					cmd = "pkill chromedriver";
				} else if (osName.toLowerCase().contains("windows")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
				}
				Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			}

			if (driver.toString().toLowerCase().contains("internetexplorer")) {
				if (osName.toLowerCase().contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
					Process process = Runtime.getRuntime().exec(cmd);
					process.waitFor();
				}
			}

			if (driver.toString().toLowerCase().contains("gecko")) {
				if (osName.toLowerCase().contains("mac")) {
					cmd = "pkill geckodriver";
				} else if (osName.toLowerCase().contains("windows")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq geckodriver*\"";
				}
				Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			}

			log.info("---------- QUIT BROWSER SUCCESS ----------");
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

	@BeforeSuite
	public void deleteAllFilesInReportNGScreenshot() {
		System.out.println("-----------------START: Delete file in folder-----------------");
		deleteAllFileInFolder();
		System.out.println("-----------------FINISH: Delete file in folder-----------------");
	}

	private void deleteAllFileInFolder() {
		try {
			String pathFolderDownload = workingDir + "\\ReportNGScreenshots";
			File file = new File(pathFolderDownload);
			File[] listOfFile = file.listFiles();
			for (int i = 0; i < listOfFile.length; i++) {
				if (listOfFile[i].isFile()) {
					System.out.println(listOfFile[i].getName());
					new File(listOfFile[i].toString()).delete();
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	public String startDate() {
		Calendar calendarNowTime = Calendar.getInstance();

		Date time = calendarNowTime.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String timeNow = dateFormat.format(time);
		return timeNow;

	}
	

	public String endDate() {
		Calendar calendarFurtureTime = Calendar.getInstance();
		calendarFurtureTime.add(Calendar.DAY_OF_MONTH, 1);
		Date nextDay = calendarFurtureTime.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String timeNow = dateFormat.format(nextDay);
		return timeNow;
	}


	
}
