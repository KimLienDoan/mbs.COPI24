package pageObject;

import actions.AbstractPage;
import org.openqa.selenium.WebDriver;
import pageUis.F1_AtoOrderPageUi;
import pageUis.OrderPageUi;


public class OrderPageObject extends AbstractPage{
	WebDriver driver;

	public OrderPageObject(WebDriver mappingDriver) {

		driver = mappingDriver;
	}
	public void clickToF3() {
		waitForElementVisible(driver, OrderPageUi.F3_BTN);
		clickToElement(driver, OrderPageUi.F3_BTN);
	}

	public void inputToHNXCode(String value) {
		waitForElementVisible(driver, OrderPageUi.EXCHANGES_CODE_INPUT);
		sendkeyToElement(driver, OrderPageUi.EXCHANGES_CODE_INPUT, value);
		waitForElementVisible(driver, OrderPageUi.EXCHANGES_CODE_BTN);
		clickToElement(driver, OrderPageUi.EXCHANGES_CODE_BTN);
	}

	public void clickToPriceInput() {
		waitForElementVisible(driver, OrderPageUi.PRICE_INPUT);
		clickToElement(driver, OrderPageUi.PRICE_INPUT);
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
	public void clearPriceInput() {
		waitForElementVisible(driver,OrderPageUi.PRICE_INPUT);
		clearElementInTextbox(driver,OrderPageUi.PRICE_INPUT);
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

}
