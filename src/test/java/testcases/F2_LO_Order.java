package testcases;

import actions.AbstractTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.F0_LoginPageObject;
import pageObject.F1_AtoOrderPageObject;
import pageObject.F2_LoOrderPageObject;
import pageObject.OrderPageObject;

import java.util.concurrent.TimeUnit;

public class F2_LO_Order extends AbstractTest {
	WebDriver driver;
	String exchangesCode;
	F0_LoginPageObject login;
	F1_AtoOrderPageObject orderATO;
	F2_LoOrderPageObject orderLO;
	String username = "090721";
	String password = "mbs123456";
	OrderPageObject order;
	String exchangesCodeHNX = "AAV";
	String exchangesCodeUPCOM = "ABB";
	String exchangesCodeHSX_CCQ = "E1VFVN30";
	String exchangesCodeHSX = "AAM";
	String mass = "100";

	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = openMultiBrowser(browserName);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		login = new F0_LoginPageObject(driver);
		order = new OrderPageObject(driver);
		orderATO = new F1_AtoOrderPageObject(driver);
		orderLO = new F2_LoOrderPageObject(driver);
	}

	@Test(priority = 0)
	public void Login() {
		log.info("Click button Đăng Nhập");
		login.clickLogin1Btn();
		log.info("Nhập STK/Mật Khẩu");
		login.inputLoginTxt(username, password);
		log.info("Click button Đăng Nhập");
		login.clickLogin2Btn();
		verifyTrue(login.verifyLogin());
	}

	@Test(priority = 1)
	public void TC01_ATO_HNX_Trade() throws Exception {
		log.info("Click to F3");
		order.clickToF3();
		order.clickToAccountDropDown(order.getTextAccountWithTK1());
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);
		Thread.sleep(1000);
		log.info("Click button price ");
		order.clickToPriceInput();
		log.info("Verify ko hiển thị giá ATO ");
		verifyTrue(orderATO.verifyNoDisplayATO());
	}

	@Test(priority = 2)
	public void TC02_ATO_UPCOM_Trade() {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeUPCOM);
		log.info("Click button price ");
		order.clickToPriceInput();
		log.info("Verify ko hiển thị giá ATO ");
		verifyTrue(orderATO.verifyNoDisplayATO());
	}

	@Test(priority = 3)
	public void TC03_ATO_HSX_CCQETF_Trade() {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_CCQ);
		log.info("Click button price ");
		order.clickToPriceInput();
		log.info("Verify ko hiển thị giá ATO ");
		verifyTrue(orderATO.verifyNoDisplayATO());
	}

	@Test(priority = 4)
	public void TC04_LO_HSX_PriceHigherCeiling() throws InterruptedException {
		Thread.sleep(3000);
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_CCQ);

		log.info("SendKey price ");
		Thread.sleep(500);
		order.clearToPriceInputWithKeys();
		order.clearPriceInput();
		order.sendKeyPriceInput(orderLO.getTextHigherCeilingPrice());

		log.info("SendKey Mass");
		order.clickToMassInput();
		order.clearMassInput();
		order.sendKeyMassInput(mass);

		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("expect : Giá phải nằm trong khoảng từ Sàn đến Trần");
		verifyTrue(orderLO.verifyLoHsxWithFloorCeilingPrice());
	}

	@Test(priority = 5)
	public void TC05_LO_HSX_PriceLowerFloor() throws InterruptedException {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_CCQ);
		log.info("SendKey price ");
		Thread.sleep(500);
		order.clickToPriceInput();
		order.clearToPriceInputWithKeys();
		order.sendKeyPriceInput(orderLO.getTextLowFloorPrice());

		log.info("SendKey Mass");
		Thread.sleep(500);
		order.clearToMassInputWithKeys();
		order.clickToMassInput();
		order.sendKeyMassInput(mass);

		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();

		log.info("expect : Giá phải nằm trong khoảng từ Sàn đến Trần");
		verifyTrue(orderLO.verifyLoHsxWithFloorCeilingPrice());
	}

	//LO-Nhập lệnh cổ phiếu sàn HNX/UPCOM với bước giá 100
	@Test(priority = 6)
	public void TC06_LO_HNX_PriceStep100() throws InterruptedException {
		Thread.sleep(500);
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);

		log.info("SendKey price ");
		Thread.sleep(1000);
		order.clickToPriceInput();
		order.clearToPriceInputWithKeys();
		order.sendKeyPriceInput(orderLO.getTextTCPrice());

		log.info("SendKey Mass");
		Thread.sleep(500);
		order.clearToMassInputWithKeys();
		order.sendKeyMassInput(mass);

		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();

		log.info("click button Xác nhận mua");
		order.clicktoBuyConfirm();
		if (order.isDisplayedOTP() == true) {
			log.info("Nhập SMS OTP");
			order.clickToSMSOTP();
			order.senkeyOTPCode("123456");
			order.clickConfirmBtn();
			log.info("Expect: Thong bao: Dat lenh thanh cong");
			verifyTrue(orderLO.verifyBuySuccess());
		} else {
			log.info("Expect: Thong bao: Dat lenh thanh cong");
			verifyTrue(orderLO.verifyBuySuccess());
		}
	}

	//LO-Nhập lệnh cổ phiếu sàn HNX khác bước giá 100
	@Test(priority = 7)
	public void TC07_LO_HNX_PriceOtherStep100() throws InterruptedException {
		Thread.sleep(5000);
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);

		log.info("SendKey price ");
		Thread.sleep(500);
		//	order.clearPriceInputByJS();
		order.clearToPriceInputWithKeys();
		order.sendKeyPriceInput(orderLO.getTextTCPriceWithOther100());

		log.info("SendKey Mass");
		Thread.sleep(500);
		//order.clickToMassInput();
		order.clearToMassInputWithKeys();
		order.sendKeyMassInput(mass);

		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();

		log.info("Expect: Thong bao: Giá đặt không hợp lệ");
		verifyTrue(orderLO.verifyBuylWithInvalidPrice100());
	}

	//LO-Nhập lệnh cổ phiếu sàn UPCOM với bước giá 100
	@Test(priority = 8)
	public void TC08_LO_UPCOM_PriceStep100() throws InterruptedException {
		Thread.sleep(1000);
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeUPCOM);

		log.info("SendKey price ");
		Thread.sleep(500);
		order.clearToPriceInputWithKeys();
		order.sendKeyPriceInput(orderLO.getTextTCPrice());

		log.info("SendKey Mass");
		order.clearToMassInputWithKeys();
		order.sendKeyMassInput(mass);

		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();

		log.info("click button Xác nhận mua");
		order.clicktoBuyConfirm();
		if (order.isDisplayedOTP() == true) {

			log.info("Nhập SMS OTP");
			order.clickToSMSOTP();
			order.senkeyOTPCode("123456");
			order.clickConfirmBtn();
			log.info("Expect: Thong bao: Dat lenh thanh cong");
			verifyTrue(orderLO.verifyBuySuccess());
		} else {
			log.info("Expect: Thong bao: Dat lenh thanh cong");
			verifyTrue(orderLO.verifyBuySuccess());
		}
	}

	//Nhập lệnh cổ phiếu sàn UPCOM với  bước giá  #100
	@Test(priority = 9)
	public void TC09_LO_UPCOM_PriceOtherStep100() {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeUPCOM);

		log.info("SendKey price ");
		order.clearToPriceInputWithKeys();
		order.sendKeyPriceInput(orderLO.getTextTCPriceWithOther100());

		log.info("SendKey Mass");
		order.clearToMassInputWithKeys();
		order.sendKeyMassInput(mass);

		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();

		log.info("Expect: Thong bao: Giá đặt không hợp lệ");
		verifyTrue(orderLO.verifyBuylWithInvalidPrice100());
	}

	/*Nhập lệnh cổ phiếu /CCQ ETF HSX có bước giá lẻ hợp lệ: 10 ≤ giá nhập< 50: Bước giá 10 */
	@Test(priority = 10)
	public void TC10_LO_HSX_CCQ_ETF_PriceWithStep10() throws InterruptedException {
		Thread.sleep(5000);
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_CCQ);

		log.info("SendKey price ");
		order.clearToPriceInputWithKeys();
		order.sendKeyPriceInput(orderLO.getTextInputPriceBetween10And50WithStep10());

		log.info("SendKey Mass");
		order.clearToMassInputWithKeys();
		order.sendKeyMassInput(mass);

		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();

		log.info("click button Xác nhận mua");
		order.clicktoBuyConfirm();

		if (order.isDisplayedOTP() == true) {

			log.info("Nhập SMS OTP");
			order.clickToSMSOTP();
			order.senkeyOTPCode("123456");
			order.clickConfirmBtn();
			log.info("Expect: Thong bao: Dat lenh thanh cong");
			verifyTrue(orderLO.verifyBuySuccess());

		} else {

			log.info("Expect: Thong bao: Dat lenh thanh cong");
			verifyTrue(orderLO.verifyBuySuccess());
		}
	}

	/*Nhập lệnh CCQ ETF sàn HSX có bước giá là 1 đồng */
	public void TC14_LO_HSX_CCQ_ETF_PriceWithStep1() throws InterruptedException {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_CCQ);

		log.info("SendKey price ");
		order.clearToPriceInputWithKeys();
		Thread.sleep(500);
		order.sendKeyPriceInput(orderLO.getTextInputPriceBetween10And50WithStep1());

		log.info("SendKey Mass");
		order.clearToMassInputWithKeys();
		Thread.sleep(500);
		order.sendKeyMassInput(mass);

		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();

		log.info("Expect: Thong bao: Giá đặt không hợp lệ (Bước giá 10đ)");
		verifyTrue(orderLO.verifyBuyInvaildStepPrice10());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
