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
import pageUis.F2_LoOrderPageUi;

import java.util.concurrent.TimeUnit;

public class F1_ATO_Order extends AbstractTest {
	WebDriver driver;
	F0_LoginPageObject login;
	OrderPageObject order;
	F2_LoOrderPageObject orderLO;
	String exchangesCodeHNX="AAV";
	String exchangesCodeUPCOM="ABB";
	String exchangesCodeHSX_CCQ="E1VFVN30";
	String exchangesCodeHSX= "AAM";
	String mass="100";
	F1_AtoOrderPageObject orderATO;
	String username = "090721";
	String password = "mbs123456";
	String exchangesCodeHSX_Lower10 = "SD8"; // Giá nhập <10: Bước giá 10
	String exchangesCodeHSX_Between_10_50 = "FUCVREIT";//10 ≤ giá nhập< 50: Bước giá 50
	String exchangesCodeHSX_Higher_50 = "VIC"; //giá nhập ≥ 50: Bước giá 100
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = openMultiBrowser(browserName);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		login = new F0_LoginPageObject(driver);
		orderATO = new F1_AtoOrderPageObject(driver);
		order = new OrderPageObject(driver);
		orderLO = new F2_LoOrderPageObject(driver);
	}

	@Test (priority = 0)
	public void Login() {
		log.info("Click button Đăng Nhập");
		login.clickLogin1Btn();
		log.info("Nhập STK/Mật Khẩu");
		login.inputLoginTxt(username, password);
		log.info("Click button Đăng Nhập");
		login.clickLogin2Btn();
		verifyTrue(login.verifyLogin());
	}

	@Test (priority = 1)
	public void aTOExchangesHNX() throws Exception {
		log.info("Click to F3");
		order.clickToF3();
		order.clickToAccountDropDown(order.getTextAccountWithTK1());
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);
		log.info("Click button price ");
		order.clickToPriceInput();
		log.info("Verify ko hiển thị giá ATO ");
		verifyTrue(orderATO.verifyNoDisplayATO());
	}

	@Test(priority = 2)
	public void aTOExchangesUPCOM() {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeUPCOM);
		log.info("Click button price ");
		order.clickToPriceInput();
		log.info("Verify ko hiển thị giá ATO ");
		verifyTrue(orderATO.verifyNoDisplayATO());
	}

	@Test(priority = 3)
	public void aTOExchangesHSX()  {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_CCQ);
		log.info("Click button price ");
		order.clickToPriceInput();
		log.info("Click to ATO price");
		orderATO.clickToATOPriceInput();
		log.info("SendKey ATO Mass");
		order.clearMassInput();
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
	}

	@Test(priority = 4)
	public void loHsxWithHigherCeilingPrice() {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_CCQ);
		log.info("SendKey price ");
		order.clearPriceInput();
		order.sendKeyPriceInput(orderLO.getTextHigherCeilingPrice());
		log.info("SendKey Mass");
		order.clearMassInput();
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("expect : Giá phải nằm trong khoảng từ Sàn đến Trần");
		verifyTrue(orderLO.verifyLoHsxWithFloorCeilingPrice());
	}

	@Test(priority = 5)
	public void loHsxWithLowFloorPrice() {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_CCQ);
		log.info("SendKey price ");
		order.clearPriceInput();
		order.sendKeyPriceInput(orderLO.getTextLowFloorPrice());
		log.info("SendKey Mass");
		order.clearMassInput();
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("expect : Giá phải nằm trong khoảng từ Sàn đến Trần");
		verifyTrue(orderLO.verifyLoHsxWithFloorCeilingPrice());
	}
	//LO-Nhập lệnh cổ phiếu sàn HNX/UPCOM với bước giá 100
	@Test(priority = 6)
	public void loHnxPriceWithStep100() throws InterruptedException {
		Thread.sleep(1000);
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);
		log.info("SendKey price ");
		order.clickToPriceInput();
		order.clearPriceInput();
		order.sendKeyPriceInput(orderLO.getTextTCPrice());
		log.info("SendKey Mass");
		order.clearMassInput();
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("click button Xác nhận mua");
		order.clicktoBuyConfirm();
		log.info("Nhập SMS OTP");
		order.clickToSMSOTP();
		order.senkeyOTPCode("123456");
		order.clickConfirmBtn();
		log.info("Expect: Thong bao: Dat lenh thanh cong");
		verifyTrue(orderLO.verifyBuySuccess());
	}
	//LO-Nhập lệnh cổ phiếu sàn HNX khác bước giá 100
	@Test(priority = 7)
	public void loHnxPriceWithOtherStep100() throws InterruptedException {
		Thread.sleep(5000);
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);
		log.info("SendKey price ");
		Thread.sleep(1000);
		order.clearPriceInput();
		order.sendKeyPriceInput(orderLO.getTextTCPriceWithOther100());
		log.info("SendKey Mass");
		order.clearMassInput();
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("Expect: Thong bao: Giá đặt không hợp lệ");
		verifyTrue(orderLO.verifyBuylWithInvalidPrice100());
	}

	//LO-Nhập lệnh cổ phiếu sàn UPCOM với bước giá 100
	@Test(priority = 8)
	public void loUpcomPriceWithStep100() throws InterruptedException {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeUPCOM);
		log.info("SendKey price ");
		order.clickToPriceInput();
		order.clearPriceInput();
		order.sendKeyPriceInput(orderLO.getTextTCPrice());
		log.info("SendKey Mass");
		//order.clickToMassInput();
		order.clearMassInput();
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("click button Xác nhận mua");
		order.clicktoBuyConfirm();
		//log.info("Nhập SMS OTP");
		//order.clickToSMSOTP();
		//order.senkeyOTPCode("123456");
		//order.clickConfirmBtn();
		log.info("Expect: Thong bao: Dat lenh thanh cong");
		verifyTrue(orderLO.verifyBuySuccess());
	}

	@Test(priority = 9)
	public void loUpcomPriceWithOtherStep100() {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeUPCOM);
		log.info("SendKey price ");
		order.clearPriceInput();
		order.sendKeyPriceInput(orderLO.getTextTCPriceWithOther100());
		log.info("SendKey Mass");
		order.clearMassInput();
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("Expect: Thong bao: Giá đặt không hợp lệ");
		verifyTrue(orderLO.verifyBuylWithInvalidPrice100());
	}
	/*Nhập lệnh cổ phiếu /CCQ đóng HSX có bước giá lẻ hợp lệ:
	- Giá nhập <10: Bước giá 10 */

	@Test(priority = 10)
	public void loHSXCloseInputPriceLower10WithStep10() {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_Lower10);
		log.info("SendKey price ");
		order.clearPriceInput();
		order.sendKeyPriceInput(orderLO.getTextTCPrice());
		log.info("SendKey Mass");
		order.clearMassInput();
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("click button Xác nhận mua");
		order.clicktoBuyConfirm();
		log.info("Nhập SMS OTP");
		order.senkeyOTPCode("123456");
		log.info("Expect: Thong bao: Dat lenh thanh cong");
		verifyTrue(orderLO.verifyBuySuccess());
	}
	/*Nhập lệnh cổ phiếu /CCQ đóng HSX có bước giá lẻ hợp lệ:
	- 10 ≤ giá nhập< 50: Bước giá 50 */

	@Test(priority = 11)
	public void loHSXCloseInputPriceBetween10And50WithStep50() {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_Between_10_50);
		log.info("SendKey price ");
		order.clearPriceInput();
		order.sendKeyPriceInput(orderLO.getTextInputPriceBetween10And50WithStep50());
		log.info("SendKey Mass");
		order.clearMassInput();
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("click button Xác nhận mua");
		order.clicktoBuyConfirm();
		log.info("Nhập SMS OTP");
		order.senkeyOTPCode("123456");
		log.info("Expect: Thong bao: Dat lenh thanh cong");
		verifyTrue(orderLO.verifyBuySuccess());
	}
	/*Nhập lệnh cổ phiếu /CCQ ETF HSX có bước giá lẻ hợp lệ: 10 ≤ giá nhập< 50: Bước giá 10 >Giá không hợp lệ */
	@Test(priority = 12)
	public void loHSXCloseInputPriceBetween10And50WithStep10() {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_Between_10_50);
		log.info("SendKey price ");
		order.clearPriceInput();
		order.sendKeyPriceInput(orderLO.getTextInputPriceBetween10And50WithStep10());
		log.info("SendKey Mass");
		order.clearMassInput();
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("Expect: Thong bao: Giá đặt không hợp lệ (Bước giá 50đ)");
		verifyTrue(orderLO.verifyBuyInvaildStepPrice50());
	}


	/*Nhập lệnh cổ phiếu /CCQ ETF HSX có bước giá lẻ hợp lệ: 10 ≤ giá nhập< 50: Bước giá 10 */
	@Test(priority = 12)
	public void loHSX_ETFInputPriceWithStep10() {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_CCQ);
		log.info("SendKey price ");
		order.clearPriceInput();
		order.sendKeyPriceInput(orderLO.getTextInputPriceBetween10And50WithStep10());
		log.info("SendKey Mass");
		order.clearMassInput();
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		log.info("Expect: Thong bao: Thành công");
		verifyTrue(orderLO.verifyBuySuccess());
	}

	/*Nhập lệnh cổ phiếu /CCQ ETF HSX có bước giá lẻ hợp lệ: 10 ≤ giá nhập< 50: Bước giá 10 */
	@Test(priority = 12)
	public void loHSX_ETFInputPriceWithStep1() {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_CCQ);
		log.info("SendKey price ");
		order.clearPriceInput();
		order.sendKeyPriceInput(orderLO.getTextInputPriceBetween10And50WithStep1());
		log.info("SendKey Mass");
		order.clearMassInput();
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		log.info("Expect: Thong bao: Giá đặt không hợp lệ (Bước giá 10đ)");
		verifyTrue(orderLO.verifyBuyInvaildStepPrice10());
	}


	//MP -HNX
//	@Test
	public void mpExchangesHNX(){
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);
		log.info("Click button price - chọn lệnh MP ");
		order.clickToPriceInput();
		order.clickToMpOrderPrice();
		log.info("SendKey MP Mass");
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("Verify Thông báo ");
		verifyTrue(orderATO.verifyNoDisplayMP());
	}

    //MOK-HSX ( 9h25-11h30)
//	@Test
	public void mokExchangesHNX(){
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);
		log.info("Click button price - chọn lệnh MP ");
		order.clickToPriceInput();
		order.clickToMokOrderPrice();
		log.info("SendKey MOK Mass");
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("Verify Thông báo ");
		verifyTrue(orderATO.verifyNoDisplayMOK());
	}

	//MAK-HNX
//	@Test
	public void makExchangesHNX(){
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);
		log.info("Click button price - chọn lệnh MP ");
		order.clickToPriceInput();
		order.clickToMakOrderPrice();
		log.info("SendKey MAK Mass");
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("Verify Thông báo ");
		verifyTrue(orderATO.verifyNoDisplayMAK());
	}

	//MTL-HNX
//	@Test
	public void mtlExchangesHNX(){
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);
		log.info("Click button price - chọn lệnh MP ");
		order.clickToPriceInput();
		order.clickToMtlOrderPrice();
		log.info("SendKey MAK Mass");
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("Verify Thông báo ");
		verifyTrue(orderATO.verifyNoDisplayMTL());
	}


	//MP -HSX
//	@Test
	public void mpExchangesHSX(){
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_CCQ);
		log.info("Click button price - chọn lệnh MP ");
		order.clickToPriceInput();
		order.clickToMpOrderPrice();
		log.info("SendKey MP Mass");
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("Verify Thông báo ");
		verifyTrue(orderATO.verifyNoDisplayMP());
	}


	@AfterClass
	public void afterClass() {

	//	driver.quit();
	}

}
