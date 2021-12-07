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
		waitForElementVisible(driver,F1_AtoOrderPageUi.VERIFY_MP_PRICE_BTN);
		return isControlDisplayed(driver,F1_AtoOrderPageUi.VERIFY_MP_PRICE_BTN);
	}

	public boolean verifyNoDisplayMOK() {
		waitForElementVisible(driver,F1_AtoOrderPageUi.VERIFY_MP_PRICE_BTN);
		return isControlDisplayed(driver,F1_AtoOrderPageUi.VERIFY_MP_PRICE_BTN);
	}

	public boolean verifyNoDisplayMAK() {
		waitForElementVisible(driver,F1_AtoOrderPageUi.VERIFY_MAK_PRICE_BTN);
		return isControlDisplayed(driver,F1_AtoOrderPageUi.VERIFY_MAK_PRICE_BTN);
	}

	public boolean verifyNoDisplayMTL() {
		waitForElementVisible(driver,F1_AtoOrderPageUi.VERIFY_MTL_PRICE_BTN);
		return isControlDisplayed(driver,F1_AtoOrderPageUi.VERIFY_MTL_PRICE_BTN);
	}


}
