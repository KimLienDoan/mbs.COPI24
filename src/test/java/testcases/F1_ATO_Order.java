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
	public void TC03_ATO_HSX_CCQETF_Trade()  {
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
		log.info("click button Xác nhận mua");
		order.clicktoBuyConfirm();
		log.info("Nhập SMS OTP");
		order.clickToSMSOTP();
		order.senkeyOTPCode("123456");
		order.clickConfirmBtn();
		log.info("Expect: Thong bao: Dat lenh thanh cong");
		verifyTrue(orderLO.verifyBuySuccess());
	}

//	@Test(priority = 4)
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

//	@Test(priority = 5)
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
//	@Test(priority = 6)
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
		if(order.isDisplayedOTP()== true){
			log.info("Nhập SMS OTP");
			order.clickToSMSOTP();
			order.senkeyOTPCode("123456");
			order.clickConfirmBtn();
			log.info("Expect: Thong bao: Dat lenh thanh cong");
			verifyTrue(orderLO.verifyBuySuccess());
		}
		else
		{
			log.info("Expect: Thong bao: Dat lenh thanh cong");
			verifyTrue(orderLO.verifyBuySuccess());
		}
	}

	//LO-Nhập lệnh cổ phiếu sàn HNX khác bước giá 100
//	@Test(priority = 7)
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
//	@Test(priority = 8)
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

//	@Test(priority = 9)
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

	//Nhập lệnh cổ phiếu CCQ đóng HSX có bước giá lẻ hợp lệ - Giá nhập <10: Bước giá 10
//	@Test(priority = 10)
	public void TC10_LO_HSX_CCQ_Close_PriceLower10WithStep10() throws InterruptedException {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_Lower10);

		log.info("SendKey price ");
		Thread.sleep(500);
		order.clearToPriceInputWithKeys();
		order.sendKeyPriceInput(orderLO.getTextTCPrice());

		log.info("SendKey Mass");
		Thread.sleep(500);
		order.clearToMassInputWithKeys();
		order.sendKeyMassInput(mass);

		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		if(order.isDisplayedOTP()== true){
			log.info("Nhập SMS OTP");
			order.clickToSMSOTP();
			order.senkeyOTPCode("123456");
			order.clickConfirmBtn();
			log.info("Expect: Thong bao: Dat lenh thanh cong");
			verifyTrue(orderLO.verifyBuySuccess());
		}
		else{
			log.info("click button Xác nhận mua");
			order.clicktoBuyConfirm();
			log.info("Expect: Thong bao: Dat lenh thanh cong");
			verifyTrue(orderLO.verifyBuySuccess());
		}


	}

	//Nhập lệnh cổ phiếu /CCQ đóng HSX có bước giá lẻ hợp lệ 10 ≤ giá nhập< 50: Bước giá 50
//	@Test(priority = 11)
	public void TC11_LO_HSX_CCQ_Close_PriceBetween10And50WithStep50() throws InterruptedException {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_Between_10_50);

		log.info("SendKey price ");
		Thread.sleep(500);
		order.clearToPriceInputWithKeys();
		order.sendKeyPriceInput(orderLO.getTextInputPriceBetween10And50WithStep50());

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

	/*Nhập lệnh cổ phiếu /CCQ ETF HSX có bước giá lẻ hợp lệ: 10 ≤ giá nhập< 50: Bước giá 10 >Giá không hợp lệ */
	// BUG: stagging ko phan biet dc CCQ dong va CCQ ETF
//	@Test(priority = 12)
	public void TC12_LO_HSX_CCQ_Close_PriceBetween10And50WithStep10() {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX_Between_10_50);

		log.info("SendKey price ");
		order.clearToPriceInputWithKeys();
		order.sendKeyPriceInput(orderLO.getTextInputPriceBetween10And50WithStep10());

		log.info("SendKey Mass");
		order.clearToMassInputWithKeys();
		order.sendKeyMassInput(mass);

		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();

		log.info("Expect: Thong bao: Giá đặt không hợp lệ (Bước giá 50đ)");
		verifyTrue(orderLO.verifyBuyInvaildStepPrice50());
	}

	/*Nhập lệnh cổ phiếu /CCQ ETF HSX có bước giá lẻ hợp lệ: 10 ≤ giá nhập< 50: Bước giá 10 */
//	@Test(priority = 13)
	public void TC13_LO_HSX_CCQ_ETF_PriceWithStep10() throws InterruptedException {
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

	/*Nhập lệnh CCQ ETF sàn HSX có bước giá là 1 đồng*/
//	@Test(priority = 14)
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

	//HNX- MP - ko dat dc lenh
//	@Test(priority = 15)
	public void TC15_MP_HNX_Trade() throws InterruptedException {

		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);

		log.info("Click button price - chọn lệnh MP ");
		order.clickToPriceInput();

		log.info("Verify Thông báo ");
		verifyTrue(orderATO.verifyNoDisplayMP());
	}

	//HNX- MOK-ko đặt được lệnh
//	@Test(priority = 16)
	public void TC16_MOK_HNX_Trade() throws InterruptedException {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);

		log.info("Click button price - chọn lệnh MOK ");
		order.clickToPriceInput();

		log.info("Verify Thông báo ");
		verifyTrue(orderATO.verifyNoDisplayMOK());
	}

	//HNX- MAK-ko đặt được lệnh
//	@Test(priority = 17)
	public void TC17_MAK_HNX_Trade(){
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);

		log.info("Click button price - chọn lệnh MOK ");
		order.clickToPriceInput();

		log.info("Verify Thông báo ");
		verifyTrue(orderATO.verifyNoDisplayMAK());
	}

	//HSX- MP - ko dat dc lenh
//	@Test(priority = 18)
	public void TC18_MP_HSX_Trade() throws InterruptedException {

		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX);

		log.info("Click button price - chọn lệnh MP ");
		order.clickToPriceInput();

		log.info("Verify Thông báo ");
		verifyTrue(orderATO.verifyNoDisplayMP());
	}

	//HSX- MOK-ko đặt được lệnh
//	@Test(priority = 19)
	public void TC19_MOK_HSX_Trade() throws InterruptedException {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX);

		log.info("Click button price - chọn lệnh MOK ");
		order.clickToPriceInput();

		log.info("Verify Thông báo ");
		verifyTrue(orderATO.verifyNoDisplayMOK());
	}

	//HNX- MAK-ko đặt được lệnh
//	@Test(priority = 20)
	public void TC20_MAK_HSX_Trade(){
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX);

		log.info("Click button price - chọn lệnh MOK ");
		order.clickToPriceInput();

		log.info("Verify Thông báo ");
		verifyTrue(orderATO.verifyNoDisplayMAK());
	}

	//HNX- MTL ko đặt được lệnh
//	@Test(priority = 21)
	public void TC21_MTL_HSX_Trade(){
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX);

		log.info("Click button price - chọn lệnh MOK ");
		order.clickToPriceInput();

		log.info("Verify Thông báo ");
		verifyTrue(orderATO.verifyNoDisplayMTL());
	}

	//ATC-HNX-success
//	@Test(priority = 22)
	public void TC22_ATC_HNX_Trade() throws InterruptedException {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);

		log.info("Click button price - chọn lệnh ATC ");
		order.clearToPriceInputWithKeys();
		Thread.sleep(500);
		order.clickToAtcOrderPrice();

		log.info("SendKey ATC Mass");
		order.clearToMassInputWithKeys();
		Thread.sleep(500);
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

	//ATC-HSX-success
//	@Test(priority = 22)
	public void TC22_ATC_HSX_Trade() throws InterruptedException {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX);

		log.info("Click button price - chọn lệnh ATC ");
		order.clearToPriceInputWithKeys();
		Thread.sleep(500);
		order.clickToAtcOrderPrice();

		log.info("SendKey ATC Mass");
		order.clearToMassInputWithKeys();
		Thread.sleep(500);
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

	//PLO-HSX-success
//	@Test(priority = 23)
	public void TC23_PLO_HSX_Trade() throws InterruptedException {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHSX);

		log.info("Click button price - chọn lệnh PLO ");
		order.clearToPriceInputWithKeys();
		Thread.sleep(500);
		order.clickToPloOrderPrice();

		log.info("SendKey ATC Mass");
		order.clearToMassInputWithKeys();
		Thread.sleep(500);
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

	//PLO-HNX-success
//	@Test(priority = 24)
	public void TC24_PLO_HSX_Trade() throws InterruptedException {
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);

		log.info("Click button price - chọn lệnh PLO ");
		order.clearToPriceInputWithKeys();
		Thread.sleep(500);
		order.clickToPloOrderPrice();

		log.info("SendKey ATC Mass");
		order.clearToMassInputWithKeys();
		Thread.sleep(500);
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

	//PLO-HNX-success
//	@Test(priority = 23)
	public void TC23_PLO_HNX_Trade(){
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);

		log.info("Click button price - chọn lệnh MAK ");
		order.clearToPriceInputWithKeys();
		order.clickToPloOrderPrice();

		log.info("SendKey MAK Mass");
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

	//MTL-HNX
//	@Test(priority = 18)
	public void TC1_MTL_HNX_Trade(){
		log.info("Nhập mã CK");
		order.inputToCKCode(exchangesCodeHNX);

		log.info("Click button price - chọn lệnh MTL ");
		order.clearToPriceInputWithKeys();
		order.clickToMtlOrderPrice();

		log.info("SendKey MAK Mass");
		order.clearToMassInputWithKeys();
		order.sendKeyMassInput(mass);

		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();

		log.info("click button Xác nhận mua");
		order.clicktoBuyConfirm();

		log.info("Nhập SMS OTP");
	//	order.clickToSMSOTP();
	//	order.senkeyOTPCode("123456");
	//	order.clickConfirmBtn();

		log.info("Verify Thông báo ");
		verifyTrue(orderLO.verifyBuySuccess());
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
