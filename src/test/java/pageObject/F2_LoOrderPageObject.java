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
		String ceilingPrice = getTextElement(driver, OrderPageUi.GETTEXT_CEILING_PRICE);
		float highCeilingPrice1 = Float.parseFloat(ceilingPrice) + 10;
		return Float.toString(highCeilingPrice1);
	}
	public String getTextLowFloorPrice(){
		waitForElementVisible(driver, OrderPageUi.GETTEXT_FLOOR_PRICE);
		String floorPrice = getTextElement(driver, OrderPageUi.GETTEXT_FLOOR_PRICE);
		int lowCeilingPrice1 = Integer.parseInt(floorPrice) - 1;
		String lowCeilingPrice = String.valueOf(lowCeilingPrice1);
		return lowCeilingPrice;
	}
	public boolean verifyLoHsxWithFloorCeilingPrice() {
		return isControlDisplayed(driver, F2_LoOrderPageUi.VERIFY_LO_HSX_FLOOR_CEILING_PRICE);
	}

}
