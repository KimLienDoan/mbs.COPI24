package testcases;

import actions.AbstractTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.F0_LoginPageObject;
import pageObject.F1_AtoOrderPageObject;

import java.util.concurrent.TimeUnit;

public class F1_AtoOrder extends AbstractTest {
	 WebDriver driver;
	String exchangesCode, mass;
	F1_AtoOrderPageObject orderATO;

	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
	//	driver = openMultiBrowser(browserName);
	}

	@BeforeClass
	public void beforeClass() {
	orderATO = new F1_AtoOrderPageObject(driver);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
