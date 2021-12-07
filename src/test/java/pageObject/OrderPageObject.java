package pageObject;

import actions.AbstractPage;
import actions.Constants;
import org.openqa.selenium.*;
import pageUis.F1_AtoOrderPageUi;
import pageUis.OrderPageUi;

import java.util.List;


public class OrderPageObject extends AbstractPage{
	WebDriver driver;
	WebElement element;

	public OrderPageObject(WebDriver mappingDriver) {

		driver = mappingDriver;
	}
	public void clickToF3() {
		waitForElementVisible(driver, OrderPageUi.F3_BTN);
		clickToElement(driver, OrderPageUi.F3_BTN);
	}
	public void clickToAccountDropDown(String itemText) throws Exception {
		waitForElementVisible(driver,OrderPageUi.ACCOUNT_DROPDOWN);
		clickToElement(driver, OrderPageUi.ACCOUNT_DROPDOWN);
		selectItemInCustomDropdown(driver,OrderPageUi.ACCOUNT_DROPDOWN,OrderPageUi.ACCOUNT_LIST_DROPDOWN,itemText);
	}
	public String getTextAccountWithTK1(){
		waitForElementVisible(driver,OrderPageUi.ACCOUNT_NAME);
		String accountName = getTextElement(driver,OrderPageUi.ACCOUNT_NAME);
		String accountTK1 = accountName + "1";
		return accountTK1;
	}
	public void inputToCKCode(String value) {
		waitForElementVisible(driver, OrderPageUi.EXCHANGES_CODE_INPUT);
		sendkeyToElement(driver, OrderPageUi.EXCHANGES_CODE_INPUT, value);
		waitForElementVisible(driver, OrderPageUi.EXCHANGES_CODE_BTN);
		clickToElement(driver, OrderPageUi.EXCHANGES_CODE_BTN);
	}

	public void clickToPriceInput() {
		waitForElementVisible(driver, OrderPageUi.PRICE_INPUT);
		clickToElement(driver, OrderPageUi.PRICE_INPUT);
	}

	public void clearToPriceInputWithKeys() {
		waitForElementVisible(driver, OrderPageUi.PRICE_INPUT);
		clearElementInTextboxbyKeys(driver, OrderPageUi.PRICE_INPUT);
	}

	public void clickToMassInput() {
		waitForElementVisible(driver, OrderPageUi.MASS_INPUT);
		clickToElement(driver, OrderPageUi.MASS_INPUT);
	}

	public void clearToMassInputWithKeys() {
		waitForElementVisible(driver, OrderPageUi.MASS_INPUT);
		clearElementInTextboxbyKeys(driver, OrderPageUi.MASS_INPUT);
	}

	public void sendKeyMassInput(String value) {
		waitForElementVisible(driver, OrderPageUi.MASS_INPUT);
		sendkeyToElement(driver,OrderPageUi.MASS_INPUT,value);
	}

	public void clickToBuyOrder() {
		waitForElementVisible(driver, OrderPageUi.BUY_ORDER_BTN);
		clickToElement(driver,OrderPageUi.BUY_ORDER_BTN);
	}
	public void sendKeyPriceInput(String value) {
		waitForElementVisible(driver, OrderPageUi.ATO_PRICE_INPUT);
		sendkeyToElement(driver,OrderPageUi.ATO_PRICE_INPUT,value);
	}

	public void clickPriceInputByJS() {
		waitForElementVisible(driver, OrderPageUi.PRICE_INPUT);
		clickToElementbyJS(driver,OrderPageUi.PRICE_INPUT);
	}

	public void clearPriceInput() {
		waitForElementVisible(driver,OrderPageUi.PRICE_INPUT);
		clearElementInTextbox(driver,OrderPageUi.PRICE_INPUT);
	}


	public void clearPriceInputByJS() {
		waitForElementVisible(driver,OrderPageUi.PRICE_INPUT);
		 element =  driver.findElement(By.xpath(OrderPageUi.PRICE_INPUT));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].value = '';",element );
	}


	public void clicktoBuyConfirm() {
		waitForElementVisible(driver, OrderPageUi.BUY_VERIFY_BTN);
		clickToElement(driver, OrderPageUi.BUY_VERIFY_BTN);
	}

	public void senkeyOTPCode(String Value) {
		waitForElementVisible(driver, OrderPageUi.SENDKEY_OTP_CODE);
		sendkeyToElement(driver, OrderPageUi.SENDKEY_OTP_CODE,Value);
	}

	public void clickToMpOrderPrice() {
		waitForElementVisible(driver, OrderPageUi.MP_PRICE_BTN);
		clickToElement(driver,OrderPageUi.MP_PRICE_BTN);
	}

	public void clickToMokOrderPrice() {
		waitForElementVisible(driver, OrderPageUi.MOK_PRICE_BTN);
		clickToElement(driver,OrderPageUi.MOK_PRICE_BTN);
	}
	public void clickToMakOrderPrice() {
		waitForElementVisible(driver, OrderPageUi.MAK_PRICE_BTN);
		clickToElement(driver,OrderPageUi.MAK_PRICE_BTN);
	}

	public void clickToMtlOrderPrice() {
		waitForElementVisible(driver, OrderPageUi.MTL_PRICE_BTN);
		clickToElement(driver,OrderPageUi.MTL_PRICE_BTN);
	}

	public void clearMassInput() {
		waitForElementVisible(driver, OrderPageUi.MASS_INPUT);
		clearElementInTextbox(driver,OrderPageUi.MASS_INPUT);
	}

	public void clickToSMSOTP() {
		waitForElementVisible(driver, OrderPageUi.SMS_OTP_BTN);
		clickToElement(driver,OrderPageUi.SMS_OTP_BTN);
	}

	public void clickConfirmBtn() {
		waitForElementVisible(driver, OrderPageUi.SMS_CONFIRM_BTN);
		clickToElement(driver,OrderPageUi.SMS_CONFIRM_BTN);
	}

	public void clickToTCPriceTxt() {
		waitForElementVisible(driver, OrderPageUi.GETTEXT_TC_PRICE);
		clickToElement(driver,OrderPageUi.GETTEXT_TC_PRICE);
	}

	public boolean isDisplayedOTP() {
		overrideGlobalTimeout(driver, Constants.SHORT_TIME_OUT);
		List<WebElement> elements = driver.findElements(By.xpath(OrderPageUi.DISPLAY_OTP));
		if (elements.size() == 0)
		{
			return false;
		}
		else
		{
			return true;
		}

	}

}
