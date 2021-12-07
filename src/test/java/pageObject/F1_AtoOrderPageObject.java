package pageObject;

import actions.AbstractPage;
import actions.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageUis.F1_AtoOrderPageUi;
import pageUis.OrderPageUi;

import java.util.List;


public class F1_AtoOrderPageObject extends AbstractPage{
	WebDriver driver;

	public F1_AtoOrderPageObject(WebDriver mappingDriver) {

		driver = mappingDriver;
	}
	public void clickToATOPriceInput() {
		waitForElementVisible(driver, F1_AtoOrderPageUi.ATO_PRICE_BTN);
		clickToElement(driver, F1_AtoOrderPageUi.ATO_PRICE_BTN);
	}

	public boolean verifyNoDisplayATO() {
		return isControlUndisplayed(driver,F1_AtoOrderPageUi.ATO_PRICE_BTN);
	}


	public boolean verifyNoDisplayMP() {
		return isControlUndisplayed(driver,F1_AtoOrderPageUi.MP_PRICE_BTN);
	}

	public boolean verifyNoDisplayMOK() {
		return isControlUndisplayed(driver,F1_AtoOrderPageUi.MP_PRICE_BTN);
	}

	public boolean verifyNoDisplayMAK() {
		return isControlUndisplayed(driver,F1_AtoOrderPageUi.MAK_PRICE_BTN);
	}

	public boolean verifyNoDisplayMTL() {
		return isControlUndisplayed(driver,F1_AtoOrderPageUi.MTL_PRICE_BTN);
	}


}
