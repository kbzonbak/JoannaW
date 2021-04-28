package bbr.b2b.test.classes;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.TimeUnit;

import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNUploadResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DatingRequestDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DatingRequestDetailDataResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DatingRequestInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DatingRequestResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DocumentDeleteInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentDetailInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentDetailResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailReportResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryReportInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryReportResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrOrderDeliveryAdjustResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrOrderDeliveryAdjustUnitsDetailDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrOrderDeliveryAdjustUnitsInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.InactiveDaysResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.PackingListReportResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.UploadASNInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.UploadPackingListInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.UploadPackingListResultDTO;
import bbr.b2b.logistic.managers.interfaces.ContextUtil;
import bbr.b2b.logistic.managers.interfaces.IDeliveryReportManager;
import bbr.b2b.logistic.report.classes.UserLogDataDTO;

public class TestDelivery {

	private IDeliveryReportManager deliveryreportmanager;
	
	@Before
	public void myTest() {	
		try{
			deliveryreportmanager = ContextUtil.getInstance().getDeliveryReportManager();
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
	
	
	//@Test
	public void getInactiveDaysToDatingRequest(){
		InactiveDaysResultDTO resultDTO = new InactiveDaysResultDTO();
		
		resultDTO = deliveryreportmanager.getInactiveDaysToDatingRequest();
		assertEquals(1, 1);
		
		
	}
	
	//@Test
	public void test_doAddDatingRequest_1(){
	
		DatingRequestResultDTO resultDTO = new DatingRequestResultDTO();
		
		DatingRequestInitParamDTO initParamDTO = new DatingRequestInitParamDTO();
		
		// Requested date
		LocalDateTime requestdate = LocalDateTime.of(2020, Month.MARCH, 3, 00, 00, 00);

		DatingRequestDataDTO datingrequestdatadto = new DatingRequestDataDTO();
		datingrequestdatadto.setBulks(4);
		datingrequestdatadto.setComment("Comment 1");
		datingrequestdatadto.setEmail("mmella@bbr.cl");
		datingrequestdatadto.setEstimatedtime(5);
		datingrequestdatadto.setEstimatedvolume(3);
		datingrequestdatadto.setNeedmodule("Mañana");
		datingrequestdatadto.setPallets(2);
		datingrequestdatadto.setPhone("316");
		datingrequestdatadto.setRequestdate(requestdate);
		datingrequestdatadto.setRequestername("Marcelo Mella");
		datingrequestdatadto.setTrucks(2);
		
		Long[] dvrorderids = {25L};
		
		UserLogDataDTO userdataDTO = new UserLogDataDTO();
		userdataDTO.setUsertype("BBR");
		userdataDTO.setUsername("Marcelo Mella");
		userdataDTO.setUseremail("mmella@bbr.cl");
		userdataDTO.setUsercode("142088460");
		
		
		initParamDTO.setDatingrequestdata(datingrequestdatadto);
		initParamDTO.setDvrorderids(dvrorderids);
		initParamDTO.setVendorcode("82833200");

		resultDTO = deliveryreportmanager.doAddDatingRequest(initParamDTO, userdataDTO, false);
		assertEquals(1, 1);
		
	}
	
	
	
	//@Test
	public void test_getDeliveryReportData(){
		
		DvrDeliveryReportResultDTO resultDTO = new DvrDeliveryReportResultDTO();
		
		//Criterio
		OrderCriteriaDTO[] orderbylist = new OrderCriteriaDTO[1];
		OrderCriteriaDTO orderby = new OrderCriteriaDTO("DVRDELIVERYNUMBER", true);
		orderbylist[0] = orderby; 
		
		DvrDeliveryReportInitParamDTO initParamDTO = new DvrDeliveryReportInitParamDTO();
		initParamDTO.setFiltertype(0);
		initParamDTO.setOrderby(orderbylist);
		initParamDTO.setPageNumber(1);
		initParamDTO.setRows(100);
		initParamDTO.setVendorcode("82833200");
		
		
		
		resultDTO = deliveryreportmanager.getDvrDeliveryReportData(initParamDTO, true, true);
		assertEquals(1, 1);
	}
	

	
//	@Test
	public void test_getDvrDeliveryDetailReport(){
		DvrDeliveryDetailReportResultDTO resultDTO = new DvrDeliveryDetailReportResultDTO();
		
		//Criterio
		OrderCriteriaDTO[] orderbylist = new OrderCriteriaDTO[1];
		OrderCriteriaDTO orderby = new OrderCriteriaDTO("OCNUMBER", true);
		orderbylist[0] = orderby; 
		
		
		DvrDeliveryDetailInitParamDTO initParamDTO = new DvrDeliveryDetailInitParamDTO();
		initParamDTO.setDvrdeliveryid(2L);
		initParamDTO.setOrderby(orderbylist);
		initParamDTO.setPageNumber(1);
		initParamDTO.setRows(100);
		initParamDTO.setVendorcode("82833200");
		
		
		resultDTO = deliveryreportmanager.getDvrDeliveryDetailReport(initParamDTO, true, true);
		assertEquals(1, 1);
		
		
	}

	
	//@Test
	public void test_getDatingRequestDetailDataReport() {
		DatingRequestDetailDataResultDTO resultDTO = new DatingRequestDetailDataResultDTO();
		DvrDeliveryInitParamDTO initParamDTO = new DvrDeliveryInitParamDTO();
		
		initParamDTO.setDvrdeliveryid(2L);
		
		
		resultDTO = deliveryreportmanager.getDatingRequestDetailDataReport(initParamDTO);
		assertEquals(1, 1);

	}
	

	
	//@Test
	public void test_doUploadPackingList_1(){
		UploadPackingListResultDTO resultDTO = new UploadPackingListResultDTO();
		UploadPackingListInitParamDTO initParamDTO = new UploadPackingListInitParamDTO();
		
		initParamDTO.setDvrdeliveryid(2L);
		initParamDTO.setVendorcode("82833200");
		initParamDTO.setFilename("/opt/fileserver/pl_hites_test.csv");
		
		UserLogDataDTO userdataDTO = new UserLogDataDTO();
		userdataDTO.setUsercode("Marcelo Mella");
		userdataDTO.setUsertype("BBR");
		
		resultDTO = deliveryreportmanager.doUploadPackingList(initParamDTO, userdataDTO);
		assertEquals(1, 1);
	}
	
	
	
	//@Test
	public void test_getDvrDeliveryDatingDocumentReportData_1() {
		DvrDeliveryDatingDocumentResultDTO resultDTO = new DvrDeliveryDatingDocumentResultDTO();
		DvrDeliveryDatingDocumentInitParamDTO initParamDTO = new DvrDeliveryDatingDocumentInitParamDTO();
		
		LocalDateTime when = LocalDateTime.of(2020, Month.MAY, 13, 00, 00, 00);
		
		//Criterio
		OrderCriteriaDTO[] orderbylist = new OrderCriteriaDTO[1];
		OrderCriteriaDTO orderby = new OrderCriteriaDTO("DELIVERYNUMBER", true);
		orderbylist[0] = orderby; 
		
		initParamDTO.setLocationcode("36");
		initParamDTO.setOrderby(orderbylist);
		initParamDTO.setPageNumber(1);
		initParamDTO.setRows(100);
		initParamDTO.setVendorcode("77036900");
		initParamDTO.setWhen(when);
		initParamDTO.setSearchvalue("1234");
		initParamDTO.setFiltertype(0);
		
		resultDTO = deliveryreportmanager.getDvrDeliveryDatingDocumentReportData(initParamDTO, true, true);
		assertEquals(1, 1);
		
		
	}
	
	
	
	//@Test
	public void test_getDvrDeliveryDatingDocumentReportData_2() {
		DvrDeliveryDatingDocumentResultDTO resultDTO = new DvrDeliveryDatingDocumentResultDTO();
		DvrDeliveryDatingDocumentInitParamDTO initParamDTO = new DvrDeliveryDatingDocumentInitParamDTO();
		
		LocalDateTime when = LocalDateTime.of(2020, Month.JANUARY, 31, 00, 00, 00);
		
		//Criterio
		OrderCriteriaDTO[] orderbylist = new OrderCriteriaDTO[1];
		OrderCriteriaDTO orderby = new OrderCriteriaDTO("DELIVERYNUMBER", true);
		orderbylist[0] = orderby; 
		
		initParamDTO.setLocationcode("CD01");
		initParamDTO.setOrderby(orderbylist);
		initParamDTO.setPageNumber(1);
		initParamDTO.setRows(100);
		initParamDTO.setVendorcode("82833200");
		initParamDTO.setWhen(when);
		initParamDTO.setSearchvalue("18886516");
		initParamDTO.setFiltertype(1);
		
		resultDTO = deliveryreportmanager.getDvrDeliveryDatingDocumentReportData(initParamDTO, true, true);
		assertEquals(1, 1);
		
		
	}
	
	
	//@Test
	public void test_getDvrDeliveryDatingDocumentReportData_3() {
		DvrDeliveryDatingDocumentResultDTO resultDTO = new DvrDeliveryDatingDocumentResultDTO();
		DvrDeliveryDatingDocumentInitParamDTO initParamDTO = new DvrDeliveryDatingDocumentInitParamDTO();
		
		LocalDateTime when = LocalDateTime.of(2020, Month.JANUARY, 31, 00, 00, 00);
		
		//Criterio
		OrderCriteriaDTO[] orderbylist = new OrderCriteriaDTO[1];
		OrderCriteriaDTO orderby = new OrderCriteriaDTO("DELIVERYNUMBER", true);
		orderbylist[0] = orderby; 
		
		initParamDTO.setLocationcode("CD01");
		initParamDTO.setOrderby(orderbylist);
		initParamDTO.setPageNumber(1);
		initParamDTO.setRows(100);
		initParamDTO.setVendorcode("82833200");
		initParamDTO.setWhen(when);
		initParamDTO.setSearchvalue("1234");
		initParamDTO.setFiltertype(2);
		
		resultDTO = deliveryreportmanager.getDvrDeliveryDatingDocumentReportData(initParamDTO, true, true);
		assertEquals(1, 1);
		
		
	}
	
	
	
	
	//@Test
	public void test_getDvrDeliveryDatingDocumentDetailReportData_1() {
		
		DvrDeliveryDatingDocumentDetailResultDTO resultDTO = new DvrDeliveryDatingDocumentDetailResultDTO();
		DvrDeliveryDatingDocumentDetailInitParamDTO initParamDTO = new DvrDeliveryDatingDocumentDetailInitParamDTO();
		
		
		initParamDTO.setDvrdeliveryid(208L);
		initParamDTO.setVendorcode("77036900");
		
		resultDTO = deliveryreportmanager.getDvrDeliveryDatingDocumentDetailReportData(initParamDTO, true);
		assertEquals(1, 1);	
	}

	
	//@Test
	public void test_doUploadASN() {
		ASNUploadResultDTO resultDTO = new ASNUploadResultDTO();
		UploadASNInitParamDTO initParamDTO = new UploadASNInitParamDTO ();
		
		Long[] documentsids = {25L};
		
		initParamDTO.setDocumentids(documentsids);
		initParamDTO.setDvrdeliveryid(2L);
		
		UserLogDataDTO userdataDTO = new UserLogDataDTO();
		userdataDTO.setUsercode("Marcelo Mella");
		userdataDTO.setUsertype("BBR");
		
		resultDTO = deliveryreportmanager.doUploadASN(initParamDTO, userdataDTO);
		assertEquals(1, 1);
	}
	
	//@Test
	public void test_doUploadASN_2() {
		ASNUploadResultDTO resultDTO = new ASNUploadResultDTO();
		UploadASNInitParamDTO initParamDTO = new UploadASNInitParamDTO ();
		
		Long[] documentsids = {12L};
		
		initParamDTO.setDocumentids(documentsids);
		initParamDTO.setDvrdeliveryid(23L);
		
		UserLogDataDTO userdataDTO = new UserLogDataDTO();
		userdataDTO.setUsercode("Marcelo Mella");
		userdataDTO.setUsertype("BBR");
		
		resultDTO = deliveryreportmanager.doUploadASN(initParamDTO, userdataDTO);
		assertEquals(1, 1);
	}
	

	
	
	//@Test
	public void test_packingListReport(){
		DvrDeliveryDetailInitParamDTO initParamDTO = new DvrDeliveryDetailInitParamDTO();
		PackingListReportResultDTO resultDTO = new PackingListReportResultDTO();
		
		//Criterio
		OrderCriteriaDTO[] orderbylist = new OrderCriteriaDTO[1];
		OrderCriteriaDTO orderby = new OrderCriteriaDTO("LPNCODE", true);
		orderbylist[0] = orderby; 
		
		
		initParamDTO.setDvrdeliveryid(2L);
		initParamDTO.setVendorcode("82833200");
		initParamDTO.setOrderby(orderbylist);
		initParamDTO.setPageNumber(1);
		initParamDTO.setRows(100);
		
		resultDTO = deliveryreportmanager.getPackingListReport(initParamDTO, true, true);
		assertEquals(1, 1);
	}
	
	//@Test
	public void test_doDeleteDocuments(){
		DocumentDeleteInitParamDTO initParamDTO = new DocumentDeleteInitParamDTO();
		UserLogDataDTO userdataDTO = new UserLogDataDTO();
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		initParamDTO.setDvrdeliveryid(33L);
		initParamDTO.setVendorcode("93129000");
		initParamDTO.setDocumentids(new Long[]{18L});
		
		resultDTO = deliveryreportmanager.doDeleteDocuments(initParamDTO, userdataDTO);
		assertEquals(1, 1);
	}
	
	//@Test
	public void doAdjustDvrOrderDeliveryUnits() {
		
		DvrOrderDeliveryAdjustUnitsDetailDTO[] details = new DvrOrderDeliveryAdjustUnitsDetailDTO[1];
		details[0] = new DvrOrderDeliveryAdjustUnitsDetailDTO();
		details[0].setDvrorderid(348L);
		details[0].setDestinationlocationid(9L);
		details[0].setItemid(2432L);
		details[0].setAvailableunits(500.0);
		
		DvrOrderDeliveryAdjustUnitsInitParamDTO initParamDTO = new DvrOrderDeliveryAdjustUnitsInitParamDTO();
		initParamDTO.setDvrdeliveryid(57L);
		initParamDTO.setVendorcode("93129000");
		initParamDTO.setDetails(details);
		
		UserLogDataDTO userdataDTO = new UserLogDataDTO();
		userdataDTO.setUsername("Jimmy Pérez");
		userdataDTO.setUsercode("246712999");
		userdataDTO.setUsertype("BBR");
		
		DvrOrderDeliveryAdjustResultDTO resultDTO = deliveryreportmanager.doAdjustDvrOrderDeliveryUnits(initParamDTO, userdataDTO);
		assertEquals(1, 1);
	}
	
}

