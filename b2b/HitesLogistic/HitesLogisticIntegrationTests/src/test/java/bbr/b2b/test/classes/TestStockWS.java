package bbr.b2b.test.classes;

import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bbr.b2b.logistic.managers.interfaces.ContextUtil;
import bbr.b2b.logistic.managers.interfaces.IVeVReportManager;
import bbr.b2b.logistic.vev.report.classes.AvailableAndDailyStockUpdateInitParamDTO;
import bbr.b2b.logistic.vev.report.classes.AvailableStockReportDataDTO;
import bbr.b2b.logistic.vev.report.classes.AvailableStockSendDataWSDTO;
import bbr.b2b.logistic.vev.report.classes.StockReportInitParamDTO;
import bbr.b2b.logistic.vev.report.classes.WSReportUploadDataDTO;

public class TestStockWS {

	private static IVeVReportManager vevreportmanager;
	
	@Before
	public void myTest() {	
		try{
			vevreportmanager = ContextUtil.getInstance().getVeVReportManager();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	
	@BeforeClass
	public static void setup() throws NamingException, InterruptedException{		

	}
	
	@AfterClass
	public static void shutdown() throws NamingException{	
	}
	
	
	@Test
	public void test_getAvailableStockReport(){
	
		
		AvailableStockReportDataDTO ResultDTO = new AvailableStockReportDataDTO();
		StockReportInitParamDTO initParamDTO = new StockReportInitParamDTO();
		initParamDTO.setVendorrut("93830000");
		
		ResultDTO = vevreportmanager.getAvailableStockReport(initParamDTO/*, client*/);
		
		System.out.println("Status: "+ ResultDTO.getStatuscode());
		
	}
	
	
	//@Test
	public void test_updatelistStock(){
		
		AvailableAndDailyStockUpdateInitParamDTO initparam = new AvailableAndDailyStockUpdateInitParamDTO();
		
		WSReportUploadDataDTO ResultDTO = new WSReportUploadDataDTO();
		
		AvailableStockSendDataWSDTO[] stockdto = new AvailableStockSendDataWSDTO[1];
		AvailableStockSendDataWSDTO stock = new AvailableStockSendDataWSDTO();
		
		stock.setDailyrep(1);
		stock.setStockdisp(0);
		stock.setItemcode("800479001");
		stock.setWarehouse(null);
		
		stockdto[0] = stock;
		
		initparam.setStock(stockdto);
		initparam.setVendordni("931290002");
		
		ResultDTO = vevreportmanager.updateAvailableAndDailyRepStock(initparam, false);
		
		System.out.println("Status: "+ ResultDTO.getStatuscode());
		
	}
	
}
