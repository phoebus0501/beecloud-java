package cn.beecloud;



import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.beecloud.BCEumeration.PAY_CHANNEL;

public class BCPayTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		BeeCloud.registerApp(TestConstant.KTestAppID, TestConstant.kTestAppSecret);
	}

	@Test
	public void testPay() {
		Map map = new HashMap();
		map.put("opchannel", "1002");
		BCPayResult result = BCPay.startBCPay(PAY_CHANNEL.WX_NATIVE, 1, "201507240100010000006235", "气费", map, "http://118.186.253.55:8889/V04/99bill.html", null, null, null);
		System.out.println("test");
	}
	
	@Test
	public void testRefund() {
		
		
		BCPayResult result = BCPay.startBCRefund(PAY_CHANNEL.WX, "201507170000", "327c4023fc3b46fd9caceaf3cd3bb3d6", 2, null);
		System.out.println("test1");
	
		result = BCPay.startBCRefund(PAY_CHANNEL.UN,  "201507142677696345", null, 1, null);
		System.out.println("test2");
	
		result = BCPay.startBCRefund(PAY_CHANNEL.ALI, "201507101112", null, 1,  null);
		System.out.println("test3");
		
	}
	
	@Test
	public void testQueryBill() {
		BCQueryResult bcQueryResult = BCPay.startQueryBill(PAY_CHANNEL.ALI_WEB, null, null, null, null, null);
		System.out.println("test1");
	}
	
	@Test
	public void testQueryRefund() {
		BCQueryResult bcQueryResult = BCPay.startQueryRefund(PAY_CHANNEL.ALI_WEB, null, null, null, null, null, null);
	}
	
	@Test
	public void testQueryWeChatRefundStatus() {
		BCQueryStatusResult result = BCPay.startWeChatRefundStatusQuery("201507149424f344");
		System.out.println("test1");
	}
}
