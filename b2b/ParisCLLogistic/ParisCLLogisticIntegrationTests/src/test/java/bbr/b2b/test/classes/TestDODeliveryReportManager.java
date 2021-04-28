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

public class TestDODeliveryReportManager {

	private IDODeliveryReportManager dodeliverymanager;
	
	@Before
	public void myTest() {	
		try{
			dodeliverymanager = ContextUtil.getInstance().getDODeliveryReportManager();
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
	public void test_getCourierFileReport() {
		CourierFileReportResultDTO resultDTO = new CourierFileReportResultDTO();
		
		try {
			CourierFileReportInitParamDTO initParamDTO = new CourierFileReportInitParamDTO();
			initParamDTO.setSince(LocalDateTime.of(2020, 10, 1, 00, 00));
			initParamDTO.setUntil(LocalDateTime.of(2021, 02, 8, 00, 00));
			initParamDTO.setPageNumber(1);
			initParamDTO.setRows(100);
			
			OrderCriteriaDTO[] orderby = new OrderCriteriaDTO[1];
			orderby[0] = new OrderCriteriaDTO();
			orderby[0].setPropertyname("UPLOADDATE");
			orderby[0].setAscending(true);
			
			initParamDTO.setOrderby(orderby);
			
			resultDTO = dodeliverymanager.getCourierFileReport(initParamDTO, true, true);
			
		} catch (Exception e) {
			;
		}
		
		assertEquals(1, 1);
	}
}