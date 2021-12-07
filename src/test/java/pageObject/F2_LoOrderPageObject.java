package pageObject;

import actions.AbstractPage;
import org.openqa.selenium.WebDriver;
import pageUis.F1_AtoOrderPageUi;
import pageUis.F2_LoOrderPageUi;
import pageUis.OrderPageUi;


public class F2_LoOrderPageObject extends AbstractPage{
	WebDriver driver;

	public F2_LoOrderPageObject(WebDriver mappingDriver) {

		driver = mappingDriver;
	}
	//LO
	public String getTextHigherCeilingPrice(){
		waitForElementVisible(driver, OrderPageUi.GETTEXT_CEILING_PRICE);
		String ceilingPrice = getTextElement(driver, OrderPageUi.GETTEXT_CEILING_PRICE);
		float highCeilingPrice1 = Float.parseFloat(ceilingPrice) + 20;
		return Float.toString(highCeilingPrice1);
	}
	public String getTextLowFloorPrice(){
		waitForElementVisible(driver, OrderPageUi.GETTEXT_FLOOR_PRICE);
		String floorPrice = getTextElement(driver, OrderPageUi.GETTEXT_FLOOR_PRICE);
		float lowCeilingPrice1 = Float.parseFloat(floorPrice) - 1;
		String lowCeilingPrice = String.valueOf(lowCeilingPrice1);
		return lowCeilingPrice;
	}
	public boolean verifyLoHsxWithFloorCeilingPrice() {
		waitForElementVisible(driver,F2_LoOrderPageUi.VERIFY_LO_HSX_FLOOR_CEILING_PRICE);
		return isControlDisplayed(driver, F2_LoOrderPageUi.VERIFY_LO_HSX_FLOOR_CEILING_PRICE);
	}

	public String getTextTCPrice() {
		waitForElementVisible(driver,OrderPageUi.GETTEXT_TC_PRICE);
		return getTextElement(driver,OrderPageUi.GETTEXT_TC_PRICE);
	}
	public String getTextTCPriceWithOther100() {
		waitForElementVisible(driver,OrderPageUi.GETTEXT_TC_PRICE);
		String TCPrice = getTextElement(driver,OrderPageUi.GETTEXT_TC_PRICE);
		float TCPrice1 = (float) (Float.parseFloat(TCPrice)+ 0.2534);
		return Float.toString(TCPrice1);
	}

	public String getTextInputPriceBetween10And50WithStep50() {
		waitForElementVisible(driver,OrderPageUi.GETTEXT_TC_PRICE);
		String TCPrice = getTextElement(driver,OrderPageUi.GETTEXT_TC_PRICE);
		float TCPrice1 = (float) (Float.parseFloat(TCPrice)+ 0.05);
		return Float.toString(TCPrice1);
	}

	public String getTextInputPriceBetween10And50WithStep10() {
		waitForElementVisible(driver,OrderPageUi.GETTEXT_TC_PRICE);
		String TCPrice = getTextElement(driver,OrderPageUi.GETTEXT_TC_PRICE);
		float TCPrice1 = (float) (Float.parseFloat(TCPrice)+ 0.11);
		return Float.toString(TCPrice1);
	}

	public String getTextInputPriceBetween10And50WithStep1() {
		waitForElementVisible(driver,OrderPageUi.GETTEXT_TC_PRICE);
		String TCPrice = getTextElement(driver,OrderPageUi.GETTEXT_TC_PRICE);
		float TCPrice1 = (float) (Float.parseFloat(TCPrice)+ 0.002);
		return Float.toString(TCPrice1);
	}

	public boolean verifyBuySuccess() {
		waitForElementVisible(driver,OrderPageUi.VERIFY_BUY_SUCCESS);
		return isControlDisplayed(driver,OrderPageUi.VERIFY_BUY_SUCCESS);
	}

	public boolean verifyBuylWithInvalidPrice100() {
		return isControlDisplayed(driver,F2_LoOrderPageUi.VERIFY_BUY_WITH_INVALID_PRICE_100);
	}

	public boolean verifyBuyInvaildStepPrice50() {
		return isControlDisplayed(driver,F2_LoOrderPageUi.VERIFY_BUY_WITH_INVALID_STEP_PRICE_50);
	}
	public boolean verifyBuyInvaildStepPrice10() {
		return isControlDisplayed(driver,F2_LoOrderPageUi.VERIFY_BUY_WITH_INVALID_STEP_PRICE_10);
	}
}
