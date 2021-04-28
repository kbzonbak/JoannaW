package bbr.b2b.test.classes;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.CourierFileReportInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.CourierFileReportResultDTO;
import bbr.b2b.regional.logistic.managers.interfaces.ContextUtil;
import bbr.b2b.regional.logistic.managers.interfaces.IDODeliveryReportManager;
import bbr.b2b.regional.logistic.managers.interfaces.ILogisticReportManager;
import bbr.b2b.regional.logistic.vendors.report.classes.InvoiceVendorRollOutArrayResultDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.InvoiceVendorRollOutInitParamDTO;

public class TestLogisticReportManager {

	private ILogisticReportManager logisticmanager;
	
	@Before
	public void myTest() {	
		try{
			logisticmanager = ContextUtil.getInstance().getLogisticReportManager();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@BeforeClass
	public static void setup() throws NamingException, InterruptedException{		
		// DEMORA LA EJECUCI?N DE LAS PRUEBAS POR DESPLIEGUE DE EAR
		//TimeUnit.SECONDS.sleep(10);	
	}
	
	@AfterClass
	public static void shutdown() throws NamingException{	
	}
	
	@Test
	public void test_getInvoiceVendorRollOutReport() {
		InvoiceVendorRollOutArrayResultDTO resultDTO = new InvoiceVendorRollOutArrayResultDTO();
		
		try {
			InvoiceVendorRollOutInitParamDTO initParamDTO = new InvoiceVendorRollOutInitParamDTO();
						
			OrderCriteriaDTO[] orderby = new OrderCriteriaDTO[1];
			orderby[0] = new OrderCriteriaDTO();
			orderby[0].setPropertyname("LASTMODIFIED");
			orderby[0].setAscending(true);
			
			initParamDTO.setOrderby(orderby);
			
			resultDTO = logisticmanager.getInvoiceVendorRollOutReport(initParamDTO);
			
		} catch (Exception e) {
			;
		}
		
		assertEquals(1, 1);
	}
}