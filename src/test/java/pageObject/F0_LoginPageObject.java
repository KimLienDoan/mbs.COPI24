package pageObject;

import org.openqa.selenium.WebDriver;

import actions.AbstractPage;
import pageUis.F0_LoginPageUi;


public class F0_LoginPageObject extends AbstractPage{
	WebDriver driver;

	public F0_LoginPageObject(WebDriver mappingDriver) {

		driver = mappingDriver;
	}
	public void clickLogin1Btn() {
		// TODO Auto-generated method stub
		waitForElementVisible(driver, F0_LoginPageUi.LOGIN1_BTN);
		clickToElement(driver, F0_LoginPageUi.LOGIN1_BTN);
	}

	public void inputLoginTxt(String username, String password) {
		// TODO Auto-generated method stub
		waitForElementVisible(driver, F0_LoginPageUi.USERNAME_TXT);
		sendkeyToElement(driver, F0_LoginPageUi.USERNAME_TXT, username);
		waitForElementVisible(driver, F0_LoginPageUi.USERNAME_TXT);
		sendkeyToElement(driver, F0_LoginPageUi.PASSWORD_TXT, password);
	}

	public void clickLogin2Btn() {
		// TODO Auto-generated method stub
		waitForElementVisible(driver, F0_LoginPageUi.LOGIN2_BTN);
		clickToElement(driver, F0_LoginPageUi.LOGIN2_BTN);
	}

	public boolean verifyLogin() {
		return isControlDisplayed(driver,F0_LoginPageUi.VERIFY_LOGIN);
	}
}
