package pageUis;

public class OrderPageUi {
	//lệnh cơ sở
	public static final String F3_BTN = "//div[text()='F3']";
	public static final String EXCHANGES_CODE_INPUT = "//input[@name='input-ticker-search']";
	public static final String EXCHANGES_CODE_BTN = "//li[@class='styles__TextItem-sc-1qs6d2y-6 gkrZpP ticker-item']";
	public static final String PRICE_INPUT= "//input[@name='input-price-place-order']";
	public static final String MASS_INPUT = "//input[@name='input-volume-place-order']";
	public static final String BUY_ORDER_BTN = "//div[text()='ĐẶT LỆNH MUA']";
	public static final String ATO_PRICE_INPUT = "//input[@name='input-price-place-order']";
	public static final String GETTEXT_CEILING_PRICE = "//div[text()='Trần']/following-sibling::div";
	public static final String GETTEXT_FLOOR_PRICE = "//div[text()='Sàn']/following-sibling::div";

	//MP/MOK/MAK/MTL
	public static final String MP_PRICE_BTN = "//div[@class='styles__SuggestionContainer-sc-ooog9b-1 jpVvgX']/div[text()='MP']";
	public static final String MOK_PRICE_BTN = "//div[@class='styles__SuggestionContainer-sc-ooog9b-1 jpVvgX']/div[text()='MOK']";
	public static final String MAK_PRICE_BTN = "//div[@class='styles__SuggestionContainer-sc-ooog9b-1 jpVvgX']/div[text()='MAK']";
	public static final String MTL_PRICE_BTN = "//div[@class='styles__SuggestionContainer-sc-ooog9b-1 jpVvgX']/div[text()='MTL']";
}
