package actions;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AbstractPage {
	
	/* Web Browser */

	public void openUrl(WebDriver driver, String urlValue) {
		driver.get(urlValue);
	}

	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getPageCurrentUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}

//	public String getTextID(String Id) {
//		return driver(;
//	}
//	
	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}

	public void refreshToPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	public void acceptAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}

	public void cancelAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}

	public String getTextAlert(WebDriver driver) {
		return driver.switchTo().alert().getText();
	}

	public void sendkeyAlert(WebDriver driver, String value) {
		driver.switchTo().alert().sendKeys(value);
	}

	/* Web Element */

	public void clickToElement(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		element.click();
	}

	public void clickToElement(WebDriver driver, String locator, String... values) {
		locator = String.format(locator, (Object[]) values);
		element = driver.findElement(By.xpath(locator));
		element.click();
	}

	public void clearElementInTextbox(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		element.clear();
	}

	public void sendkeyToElement(WebDriver driver, String locator, String value) {
		element = driver.findElement(By.xpath(locator));
		element.sendKeys(value);
	}

	public void sendkeyToElement(WebDriver driver, String locator, String sendkeyvalue, String... values) {
		locator = String.format(locator, (Object[]) values);
		element = driver.findElement(By.xpath(locator));
		element.sendKeys(sendkeyvalue);
	}

	public void selectItemInDropdown(WebDriver driver, String locator, String itemText) {
		element = driver.findElement(By.xpath(locator));
		select = new Select(element);
		select.selectByVisibleText(itemText);
	}

	public String getSelectedItemInDropdown(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}

	public void selectItemInCustomDropdown(WebDriver driver, String parentdropdownlocator, String allItemDropdown,
			String ExpectedText) throws Exception {

		element = driver.findElement(By.xpath(parentdropdownlocator));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		Thread.sleep(500);

		WebDriverWait driverwait = new WebDriverWait(driver, 500);
		driverwait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemDropdown)));

		elements = driver.findElements(By.xpath(allItemDropdown));

		for (int i = 0; i < elements.size(); i++) {
			String ItemText = elements.get(i).getText();
			if (ItemText.equals(ExpectedText)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elements.get(i));
				Thread.sleep(500);
				if (elements.get(i).isDisplayed()) {
					elements.get(i).click();
				} else {
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", elements.get(i));
				}
				break;
			}
		}
	}
	
	WebDriver driver;

	public String getAttributeValue(WebDriver driver, String locator, String attributeName) {
		element = driver.findElement(By.xpath(locator));
		return element.getAttribute(attributeName);
	}

	public String getTextElement(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public String getTextElements(WebDriver driver, String locator, String... values) {
		locator = String.format(locator, (Object[]) values);
		element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public int countElementNumber(WebDriver driver, String locator) {
		elements = driver.findElements(By.xpath(locator));
		return elements.size();
	}

	public void checkTheCheckbox(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void unCheckTheCheckbox(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		if (element.isSelected()) {
			element.click();
		}
	}

	public boolean isControlDisplayed(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		return element.isDisplayed();
	}

	public boolean isControlUndisplayed(WebDriver driver, String locator) {
		overrideGlobalTimeout(driver, Constants.SHORT_TIME_OUT);
		List<WebElement> elements = driver.findElements(By.xpath(locator));

		if (elements.size() == 0) {
			overrideGlobalTimeout(driver, Constants.LONG_TIME_OUT);
			return true;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			overrideGlobalTimeout(driver, Constants.LONG_TIME_OUT);
			return true;
		} else {
			overrideGlobalTimeout(driver, Constants.LONG_TIME_OUT);
			return false;
		}
	}

	public void overrideGlobalTimeout(WebDriver driver, int Timeout) {
		driver.manage().timeouts().implicitlyWait(Timeout, TimeUnit.SECONDS);
	}

	public boolean isControlDisplayed(WebDriver driver, String locator, String... values) {
		locator = String.format(locator, (Object[]) values);
		element = driver.findElement(By.xpath(locator));
		return element.isDisplayed();
	}

	public boolean isSelected(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		return element.isSelected();
	}

	public boolean isControlEnabled(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		return element.isEnabled();
	}

	public void switchToChilWindowByID(WebDriver driver, String parent) {
		Set<String> allwindowns = driver.getWindowHandles();
		for (String runWindown : allwindowns) {
			if (!runWindown.equals(parent)) {
				driver.switchTo().window(runWindown);
				break;
			}
		}
	}

	public void switchToWindownByTitle(WebDriver driver, String title) {
		Set<String> allWindown = driver.getWindowHandles();
		for (String runWindown : allWindown) {
			driver.switchTo().window(runWindown);
			String curentWin = driver.getTitle();
			if (curentWin.equals(title)) {
				break;
			}
		}
	}

	public boolean closeAllWindownWithoutParent(WebDriver driver, String ParentID) {
		Set<String> allWindown = driver.getWindowHandles();
		for (String runWindown : allWindown) {
			if (!runWindown.equals(ParentID)) {
				driver.switchTo().window(runWindown);
				driver.close();
			}
		}
		driver.switchTo().window(ParentID);
		if (driver.getWindowHandles().size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	public void switchToIframe(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		driver.switchTo().frame(element);
	}
	
	public void hoverMouseToElement(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		action = new Actions(driver);
		action.moveToElement(element).perform();
	}

	public void doubleClickToElement(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		action = new Actions(driver);
		action.doubleClick(element).perform();
	}

	public void rightClick(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		action = new Actions(driver);
		action.contextClick(element).perform();
	}

	public void sendKeyboardToElement(WebDriver driver, String locator, Keys key) {
		element = driver.findElement(By.xpath(locator));
		action = new Actions(driver);
		action.sendKeys(element, key).perform();
	}

	public void sendKeyboardToElement(WebDriver driver, String locator, Keys key, String... values) {
		locator = String.format(locator, (Object[]) values);
		element = driver.findElement(By.xpath(locator));
		action = new Actions(driver);
		action.sendKeys(element, key).perform();
	}

	public Object navigateToUrlByJs(WebDriver driver, String url) {
		javascriptExecutor = (JavascriptExecutor) driver;
		return javascriptExecutor.executeScript("window.location = '" + url + "'");
	}

	public Object executorForBrowser(WebDriver driver, String javascript) {
		javascriptExecutor = (JavascriptExecutor) driver;
		return javascriptExecutor.executeScript(javascript);
	}

	public boolean verifyTextInInnerText(WebDriver driver, String textExpected) {
		javascriptExecutor = (JavascriptExecutor) driver;
		String textActual = (String) javascriptExecutor
				.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}

	public boolean checkAnyImageLoaded(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		javascriptExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) javascriptExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				element);
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public void clickToElementByJs(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		javascriptExecutor = (JavascriptExecutor) driver;
		javascriptExecutor.executeScript("arguments[0].click()", element);
	}

	public void scrollToBottomPage(WebDriver driver) {
		javascriptExecutor = (JavascriptExecutor) driver;
		javascriptExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void scrollToElement(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		javascriptExecutor = (JavascriptExecutor) driver;
		javascriptExecutor.executeScript("arguments[0].scrollIntoView(true)", element);
	}

	public void sendkeyToElementByJs(WebDriver driver, String locator, String value) {
		element = driver.findElement(By.xpath(locator));
		javascriptExecutor = (JavascriptExecutor) driver;
		javascriptExecutor.executeScript("arguments[0].setAttribute('value','" + value + "')", element);
	}

	public void highlightElement(WebDriver driver, String locator) {
		element = driver.findElement(By.xpath(locator));
		javascriptExecutor = (JavascriptExecutor) driver;
		String originalStyle = element.getAttribute("style");
		javascriptExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 5px solid yellow; border-style: dashed;");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		javascriptExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				originalStyle);
	}

	public void removeAttributeInDOM(WebDriver driver, String locator, String attribute) {
		element = driver.findElement(By.xpath(locator));
		javascriptExecutor = (JavascriptExecutor) driver;
		javascriptExecutor.executeScript("arguments[0].removeAttribute('" + attribute + "')", element);
	}

	public void waitForElementPresence(WebDriver driver, String locator) {
		waitExplicit = new WebDriverWait(driver, longTimeout);
		byLocator = By.xpath(locator);
		waitExplicit.until(ExpectedConditions.presenceOfElementLocated(byLocator));
	}

	public void waitForElementVisible(WebDriver driver, String locator) {
		waitExplicit = new WebDriverWait(driver, longTimeout);
		byLocator = By.xpath(locator);
		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
	}

	public void waitForElementVisible(WebDriver driver, String locator, String... values) {
		locator = String.format(locator, (Object[]) values);
		waitExplicit = new WebDriverWait(driver, longTimeout);
		byLocator = By.xpath(locator);
		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
	}

	public void waitForElementClickable(WebDriver driver, String locator) {
		waitExplicit = new WebDriverWait(driver, longTimeout);
		byLocator = By.xpath(locator);
		waitExplicit.until(ExpectedConditions.elementToBeClickable(byLocator));
	}

	public void waitForElementInVisible(WebDriver driver, String locator) {
		waitExplicit = new WebDriverWait(driver, longTimeout);
		byLocator = By.xpath(locator);

		overrideGlobalTimeout(driver, Constants.SHORT_TIME_OUT);
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(byLocator));
		overrideGlobalTimeout(driver, Constants.LONG_TIME_OUT);
	}

	public void sleepInSecond(WebDriver driver, long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean compareListContainText(WebDriver driver, String locator, String key) {
		int dem = 0;
		elements = driver.findElements(By.xpath(locator));
		key = key.toLowerCase();
		for (int i = 0; i < elements.size(); i++) {
			String ItemText = elements.get(i).getText().toLowerCase();
			if (ItemText.contains(key)) {
				dem++;
			}
		}
		if (dem == elements.size()) {
			return true;
		} else {
			return false;
		}
	}
	/*
	 * public void openMultiplePages(WebDriver driver, String pageName) {
	 * waitForElementVisible(driver, AbstractPageUI.DYNAMIC_MENU_LINK, pageName);
	 * clickToElement(driver, AbstractPageUI.DYNAMIC_MENU_LINK, pageName); }
	 */

	private WebElement element;
	private Select select;
	private List<WebElement> elements;
	private Actions action;
	private JavascriptExecutor javascriptExecutor;
	private WebDriverWait waitExplicit;
	private By byLocator;
	private int longTimeout = Constants.LONG_TIME_OUT;
}