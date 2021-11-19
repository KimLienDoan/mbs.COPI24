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
	String exchangesCodeUPCOM="";
	String exchangesCodeHSX_CCQ="E1VFVN30";
	String exchangesCodeHSX= "AAM";
	String mass="100";
	F1_AtoOrderPageObject orderATO;
	String username = "090721";
	String password = "mbs123456";

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

	@Test(priority = 1)
	public void Login() {
		log.info("Click button Đăng Nhập");
		login.clickLogin1Btn();
		log.info("Nhập STK/Mật Khẩu");
		login.inputLoginTxt(username, password);
		log.info("Click button Đăng Nhập");
		login.clickLogin2Btn();
		verifyTrue(login.verifyLogin());
	}

	@Test(priority = 2)
	public void aTOExchangesHNX(){
		log.info("Click to F3");
		order.clickToF3();
		log.info("Nhập mã CK");
		order.inputToHNXCode(exchangesCodeHNX);
		log.info("Click button price ");
		order.clickToPriceInput();
		log.info("Verify ko hiển thị giá ATO ");
		verifyTrue(orderATO.verifyNoDisplayATO());
	}

	@Test(priority = 3)
	public void aTOExchangesUPCOM() {
		log.info("Nhập mã CK");
		order.inputToHNXCode(exchangesCodeUPCOM);
		log.info("Click button price ");
		order.clickToPriceInput();
		log.info("Verify ko hiển thị giá ATO ");
		verifyTrue(orderATO.verifyNoDisplayATO());
	}

	@Test(priority = 4)
	public void aTOExchangesHSX()  {
		log.info("Nhập mã CK");
		order.inputToHNXCode(exchangesCodeHSX_CCQ);
		log.info("Click button price ");
		order.clickToPriceInput();
		log.info("Click to ATO price");
		orderATO.clickToATOPriceInput();
		log.info("SendKey ATO Mass");
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
	}

	@Test(priority = 5)
	public void loHsxWithHigherCeilingPrice() throws InterruptedException {
		mass = "100";
		log.info("Nhập mã CK");
		order.inputToHNXCode(exchangesCodeHSX_CCQ);
		log.info("SendKey price ");
		order.clearPriceInput();
		order.sendKeyPriceInput(orderLO.getTextHigherCeilingPrice());
		log.info("SendKey Mass");
		order.sendKeyMassInput(mass);
		Thread.sleep(4000);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("expect : Giá phải nằm trong khoảng từ Sàn đến Trần");
		verifyTrue(orderLO.verifyLoHsxWithFloorCeilingPrice());
	}

	@Test(priority = 6)
	public void loHsxWithLowFloorPrice() throws InterruptedException {
		mass = "1000";
		log.info("Nhập mã CK");
		order.inputToHNXCode(exchangesCodeHSX_CCQ);
		log.info("SendKey price ");
		Thread.sleep(5000);
		order.clearPriceInput();
		Thread.sleep(5000);
		order.sendKeyPriceInput(orderLO.getTextLowFloorPrice());
		log.info("SendKey Mass");
		order.sendKeyMassInput(mass);
		log.info("Click button 'Đặt lệnh mua'");
		order.clickToBuyOrder();
		log.info("expect : Giá phải nằm trong khoảng từ Sàn đến Trần");
		verifyTrue(orderLO.verifyLoHsxWithFloorCeilingPrice());
	}





	//MP -HNX
	@Test
	public void mpExchangesHNX(){
		log.info("Nhập mã CK");
		order.inputToHNXCode(exchangesCodeHNX);
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

    //MOK-HNX
	@Test
	public void mokExchangesHNX(){
		log.info("Nhập mã CK");
		order.inputToHNXCode(exchangesCodeHNX);
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
	@Test
	public void makExchangesHNX(){
		log.info("Nhập mã CK");
		order.inputToHNXCode(exchangesCodeHNX);
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
	@Test
	public void mtlExchangesHNX(){
		log.info("Nhập mã CK");
		order.inputToHNXCode(exchangesCodeHNX);
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
	@Test
	public void mpExchangesHSX(){
		log.info("Nhập mã CK");
		order.inputToHNXCode(exchangesCodeHSX_CCQ);
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

		driver.quit();
	}

}
