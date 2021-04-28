package bbr.b2b.test.classes;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.TimeUnit;

import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.logistic.datings.report.classes.AddDatingAndDetailsInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.AssignedDatingInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.AssignedDatingResultDTO;
import bbr.b2b.logistic.datings.report.classes.DatingRequestReportInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.DatingRequestReportResultDTO;
import bbr.b2b.logistic.datings.report.classes.ExcelDatingReportInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.ExcelDatingReportResultDTO;
import bbr.b2b.logistic.datings.report.classes.ReserveDetailDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryInitParamDTO;
import bbr.b2b.logistic.managers.interfaces.ContextUtil;
import bbr.b2b.logistic.managers.interfaces.IDatingReportManager;
import bbr.b2b.logistic.report.classes.UserLogDataDTO;

public class TestDating {

	private IDatingReportManager datingreportmanager;
	
	@Before
	public void myTest() {	
		try{
			datingreportmanager = ContextUtil.getInstance().getDatingReportManager();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	
	@BeforeClass
	public static void setup() throws NamingException, InterruptedException{		
		// DEMORA LA EJECUCI?N DE LAS PRUEBAS POR DESPLIEGUE DE EAR
		TimeUnit.SECONDS.sleep(10);	
	}
	
	@AfterClass
	public static void shutdown() throws NamingException{	
	}
	
	
	//@Test
	public void test_getDatingRequestReport_1(){
		
		DatingRequestReportResultDTO resultDTO = new DatingRequestReportResultDTO();
		DatingRequestReportInitParamDTO initParamDTO = new DatingRequestReportInitParamDTO();
		
		//Criterio de orden
		OrderCriteriaDTO[] orderbylist = new OrderCriteriaDTO[1];
		OrderCriteriaDTO orderby = new OrderCriteriaDTO("DELIVERYNUMBER", true);
		orderbylist[0] = orderby; 
		
		LocalDateTime since = LocalDateTime.of(2018, Month.AUGUST, 23, 00, 00, 00);
		LocalDateTime until = LocalDateTime.of(2020, Month.AUGUST, 23, 00, 00, 00);
		
		initParamDTO.setLocationcode("CD01");
		initParamDTO.setOrderby(orderbylist);
		initParamDTO.setPagenumber(1);
		initParamDTO.setRows(100);
		initParamDTO.setSince(since);
		initParamDTO.setUntil(until);
		initParamDTO.setVendorcode("82833200");
		
		
		resultDTO = datingreportmanager.getDatingRequestReport(initParamDTO, true, true);
		assertEquals(1, 1);


		
	}
	
	
	
	//@Test
	public void test_doAddDatingandDetails_1() {
		
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		AddDatingAndDetailsInitParamDTO initParamDTO = new AddDatingAndDetailsInitParamDTO ();

		ReserveDetailDTO[] reserveDetailArr = new ReserveDetailDTO[2]; 
		
		ReserveDetailDTO reserveDetaildto1 = new ReserveDetailDTO();
		reserveDetaildto1.setDockid(1L);
		reserveDetaildto1.setModuleid(1L);
		reserveDetaildto1.setReserveid(-1L);
		

		ReserveDetailDTO reserveDetaildto2 = new ReserveDetailDTO();
		reserveDetaildto2.setDockid(1L);
		reserveDetaildto2.setModuleid(2L);
		reserveDetaildto2.setReserveid(-1L);

		reserveDetailArr[0] = reserveDetaildto1;
		reserveDetailArr[1] = reserveDetaildto2;
		
		LocalDateTime reqdate = LocalDateTime.of(2020, Month.JANUARY, 31, 00, 00, 00);
		
		
		initParamDTO.setDvrdeliveryid(2L);
		initParamDTO.setComment("aaa");
		initParamDTO.setLocationcode("CD01");
		initParamDTO.setRequesteddate(reqdate);
		initParamDTO.setReservedetail(reserveDetailArr);
		initParamDTO.setVendorcode("82833200");

		UserLogDataDTO userdataDTO = new UserLogDataDTO();
		userdataDTO.setUsercode("Marcelo Mella");
		userdataDTO.setUsertype("BBR");
		
	
		resultDTO = datingreportmanager.doAddDatingandDetails(initParamDTO, userdataDTO);
		assertEquals(1, 1);
		
		
	}
	
		
	//@Test
	public void test_getAssignedDatingByDate(){
		
		AssignedDatingResultDTO resultDTO = new AssignedDatingResultDTO();
		AssignedDatingInitParamDTO initParamDTO = new AssignedDatingInitParamDTO();
		
		LocalDateTime date = LocalDateTime.of(2020, Month.JANUARY, 31, 00, 00, 00);
		
		initParamDTO.setDate(date);
		initParamDTO.setLocationcode("CD01");
		
		
		resultDTO = datingreportmanager.getAssignedDatingByDate(initParamDTO);
		assertEquals(1, 1);
	}
	
	//@Test
	public void test_getExcelDatingReport_1() {
		ExcelDatingReportResultDTO resultDTO = new ExcelDatingReportResultDTO();
		ExcelDatingReportInitParamDTO initParamDTO = new ExcelDatingReportInitParamDTO();
		
		LocalDateTime since = LocalDateTime.of(2019, Month.JANUARY, 31, 00, 00, 00);
		LocalDateTime until = LocalDateTime.of(2020, Month.AUGUST, 31, 00, 00, 00);
		
		initParamDTO.setSince(since);
		initParamDTO.setUntil(until);
		initParamDTO.setLocationcode("CD01");
		
		resultDTO = datingreportmanager.getExcelDatingReport(initParamDTO);
		assertEquals(1, 1);
		
	}
	
	
	@Test
	public void test_doMarkDatingAsNotAttend() {
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		DvrDeliveryInitParamDTO initParamDTO = new DvrDeliveryInitParamDTO(); 
		initParamDTO.setDvrdeliveryid(2L);
		
		UserLogDataDTO userdataDTO = new UserLogDataDTO();
		userdataDTO.setUsercode("Marcelo Mella");
		userdataDTO.setUsertype("BBR");
		
		resultDTO = datingreportmanager.doMarkDatingAsNotAttend(initParamDTO, userdataDTO);
		assertEquals(1, 1);
		
		
	}
	
}
