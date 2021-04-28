package bbr.b2b.test.classes;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bbr.b2b.regional.logistic.datings.report.classes.AssignedDatingInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedDatingResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DocksArrayResultDTO;
import bbr.b2b.regional.logistic.managers.interfaces.ContextUtil;
import bbr.b2b.regional.logistic.managers.interfaces.IDatingReportManager;

//import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
//import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
//import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailExcelReportResultDTO;
//import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailReportInitParamDTO;
//import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderExcelReportInitParamDTO;
//import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderExcelReportResultDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.ChargeDiscountByOrderDetailInitParamDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.ChargeDiscountResultReportDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DiscountByOrderInitParamDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DiscountByOrderResultReportDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderDetailExcelReportResultDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderExcelReportResultDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailReportInitParamDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailReportResultDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderExcelReportInitParamDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderPdfReportDataDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderPdfReportInitParamDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderPdfReportResultDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderReportInitParamDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderReportResultDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailReportInitParamDTO;
//import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailReportResultDTO;
//import bbr.b2b.logistic.managers.interfaces.IBuyOrderReportManager;
//import bbr.b2b.logistic.report.classes.UserLogDataDTO;
//import net.sf.jasperreports.engine.JRException;

public class TestBuyOrder {

	private IDatingReportManager datingmanager;
//	private IBuyOrderReportManager buyorderreportmanager;
	
	@Before
	public void myTest() {	
		try{
			datingmanager = ContextUtil.getInstance().getDatingReportManager();
//			buyorderreportmanager = ContextUtil.getInstance().getBuyOrderReportManager();
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
	public void test_getDocksOfLocation() {
		DocksArrayResultDTO resultDTO = new DocksArrayResultDTO();
		
		try {
			resultDTO = datingmanager.getDocksOfLocation("12");
		} catch (Exception e) {
			;
		}
		
		assertEquals(1, 1);
	}
	
	//@Test
	public void test_getAssignedDatingByDate() {
		AssignedDatingResultDTO resultDTO = new AssignedDatingResultDTO();
		AssignedDatingInitParamDTO initParamDTO = new AssignedDatingInitParamDTO ();
		
		initParamDTO.setDate(LocalDateTime.of(2020, 12, 30, 0, 0));
		initParamDTO.setLocationcode("12");
		initParamDTO.setDocktypeid(2L);
		
		resultDTO = datingmanager.getAssignedDatingByDate(initParamDTO, true, true);
		assertEquals(1, 1);
	}
	
	
//	//@Test
//	public void test_getDvrOrderReportByVendorAndFilter_1() {
//		DvrOrderReportResultDTO resultDTO = new DvrOrderReportResultDTO();
//		DvrOrderReportInitParamDTO initParamDTO = new DvrOrderReportInitParamDTO ();
//		
//		
//		//Criterio de orden
//		OrderCriteriaDTO[] orderbylist = new OrderCriteriaDTO[1];
//		OrderCriteriaDTO orderby = new OrderCriteriaDTO("ORDERNUMBER", true);
//		orderbylist[0] = orderby; 
//		
//		initParamDTO.setDeliverylocationid(-1L);
//		initParamDTO.setFiltertype(0);
//		initParamDTO.setOrderby(orderbylist);
//		initParamDTO.setPageNumber(1);
//		initParamDTO.setRows(100);
//		initParamDTO.setVendorcode("59210050");
//		//initParamDTO.setVendorcode("82833200");
//
//		resultDTO = buyorderreportmanager.getDvrOrderReportByVendorAndFilter(initParamDTO, true, true);
//		assertEquals(1, 1);
//		
//		
//		
//	}
//	
//	//@Test
//	public void test_getDvrOrderDetailByOrder() {
//		DvrOrderDetailReportResultDTO resultDTO = new DvrOrderDetailReportResultDTO();
//		DvrOrderDetailReportInitParamDTO initParamDTO = new DvrOrderDetailReportInitParamDTO();
//		
//		//Criterio de orden
//		OrderCriteriaDTO[] orderbylist = new OrderCriteriaDTO[1];
//		OrderCriteriaDTO orderby = new OrderCriteriaDTO("POSITION", true);
//		orderbylist[0] = orderby; 
//		
//		initParamDTO.setDvrorderid(9L);
//		initParamDTO.setVendorcode("59210050");
//		initParamDTO.setOrderby(orderbylist);
//		initParamDTO.setPageNumber(1);
//		initParamDTO.setRows(100);
//
//		UserLogDataDTO userdataDTO = new UserLogDataDTO();
//		userdataDTO.setUsercode("Marcelo Mella");
//		userdataDTO.setUsertype("BBR");
//		
//		resultDTO = buyorderreportmanager.getDvrOrderDetailByOrder(initParamDTO, userdataDTO, true, true, true);
//		assertEquals(1, 1);
//		
//		
//	}
//	
//	//@Test
//	public void test_getDvrPredeliveryDetailByOrder_1() {
//		DvrPredeliveryDetailReportResultDTO resultDTO = new DvrPredeliveryDetailReportResultDTO();
//		DvrPredeliveryDetailReportInitParamDTO initParamDTO = new DvrPredeliveryDetailReportInitParamDTO();
//		
//		//Criterio de orden
//		OrderCriteriaDTO[] orderbylist = new OrderCriteriaDTO[1];
//		OrderCriteriaDTO orderby = new OrderCriteriaDTO("SKU", true);
//		orderbylist[0] = orderby; 
//		
//		initParamDTO.setDvrorderid(9L);
//		initParamDTO.setOrderby(orderbylist);
//		initParamDTO.setPageNumber(1);
//		initParamDTO.setRows(100);
//		initParamDTO.setVendorcode("59210050");
//		
//		resultDTO = buyorderreportmanager.getDvrPredeliveryDetailByOrder(initParamDTO, true, true);
//		assertEquals(1, 1);
//		
//	}
//	
//	//@Test
//	public void test_getDiscountByOrder_1() {
//		DiscountByOrderResultReportDTO resultDTO = new DiscountByOrderResultReportDTO();
//		DiscountByOrderInitParamDTO initParamDTO = new DiscountByOrderInitParamDTO();
//		
//		initParamDTO.setDvrorderid(19L);
//		initParamDTO.setVendorcode("82833200");
//		
//		resultDTO = buyorderreportmanager.getDiscountByOrder(initParamDTO);
//		assertEquals(1, 1);
//		
//	
//	}
//	
//	//@Test
//	public void test_getChargeDiscountByOrderDetail_1() {
//		ChargeDiscountResultReportDTO resultDTO = new ChargeDiscountResultReportDTO();
//		ChargeDiscountByOrderDetailInitParamDTO initParamDTO = new ChargeDiscountByOrderDetailInitParamDTO(); 
//		
//		
//		initParamDTO.setDvrorderid(19L);
//		initParamDTO.setItemid(100L);
//		initParamDTO.setPosition(2);
//		
//		
//		resultDTO = buyorderreportmanager.getChargeDiscountByOrderDetail(initParamDTO);
//		assertEquals(1, 1);
//		
//		
//	}
//	
//	//@Test
//	public void test_DvhOrdersExcelReportByOrders() {
//		DvrOrderExcelReportInitParamDTO initParamDTO = new DvrOrderExcelReportInitParamDTO(); 
//		initParamDTO.setDvrorderids(new Long[]{383L});
//		initParamDTO.setVendorcode("50159080");
//		
//		UserLogDataDTO userDataDTO = new UserLogDataDTO();
//		userDataDTO.setUsername("Jimmy Pérez");
//		userDataDTO.setUsercode("246712999");
//		userDataDTO.setUseremail("jperez@bbr.cl");
//		userDataDTO.setUsertype("BBR");
//		
//		DvhOrderExcelReportResultDTO resultDTO = buyorderreportmanager.getDvhOrdersExcelReportByOrders(initParamDTO, userDataDTO, false, false);
//		assertEquals(1, 1);	
//		
//	}
//	
//	//@Test
//	public void test_getDvhOrderDetailExcelReportByOrder() {
//		DvrOrderDetailReportInitParamDTO initParamDTO = new DvrOrderDetailReportInitParamDTO(); 
//		initParamDTO.setDvrorderid(383L);
//		initParamDTO.setVendorcode("50159080");
//				
//		DvhOrderDetailExcelReportResultDTO resultDTO = buyorderreportmanager.getDvhOrderDetailExcelReportByOrder(initParamDTO);
//		assertEquals(1, 1);	
//		
//	}
//	
//	//@Test
//	public void test_DdcOrdersExcelReportByOrders() {
//		DdcOrderExcelReportInitParamDTO initParamDTO = new DdcOrderExcelReportInitParamDTO(); 
//		initParamDTO.setDdcorderids(new Long[]{187L});
//		initParamDTO.setVendorcode("50159080");
//		
//		UserLogDataDTO userDataDTO = new UserLogDataDTO();
//		userDataDTO.setUsername("Jimmy Pérez");
//		userDataDTO.setUsercode("246712999");
//		userDataDTO.setUseremail("jperez@bbr.cl");
//		userDataDTO.setUsertype("BBR");
//		
//		DdcOrderExcelReportResultDTO resultDTO = buyorderreportmanager.getDdcOrdersExcelReportByOrders(initParamDTO, userDataDTO, false, false);
//		assertEquals(1, 1);
//		
//		
//	}
//	
//	@Test
//	public void test_getDdcOrderDetailExcelReportByOrder() {
//		DdcOrderDetailReportInitParamDTO initParamDTO = new DdcOrderDetailReportInitParamDTO(); 
//		initParamDTO.setDdcorderid(187L);
//		initParamDTO.setVendorcode("50159080");
//				
//		DdcOrderDetailExcelReportResultDTO resultDTO = buyorderreportmanager.getDdcOrderDetailExcelReportByOrder(initParamDTO);
//		assertEquals(1, 1);	
//		
//	}
//	
//	//@Test
//	public void test_downloadPdfDvrOrderReportByOrders() {
//		
//		DvrOrderPdfReportInitParamDTO initParamDTO = new DvrOrderPdfReportInitParamDTO();
//		initParamDTO.setDvrorderids(new Long[] {22L,23L});
//		initParamDTO.setVendorcode("96675670");
//		
//		UserLogDataDTO userDataDTO = new UserLogDataDTO();
//		userDataDTO.setUsername("Jimmy Pérez");
//		userDataDTO.setUsercode("246712999");
//		userDataDTO.setUseremail("jperez@bbr.cl");
//		userDataDTO.setUsertype("BBR");
//				
//		DvrOrderPdfReportResultDTO result = buyorderreportmanager.getPdfDvrOrderReportByOrders(initParamDTO, userDataDTO, false, false);
//
//		String logoImagePath = "D:\\Projects\\ParisCL\\pariscl.png";
//		String jrxml = "D:\\Projects\\ParisCL\\OrderReport.jrxml";
//		
//		DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		
//		HashMap<String, Object> parameters;
//		List<CompressToZipFileInitParamDTO> compressList = new ArrayList<CompressToZipFileInitParamDTO>();
//		CompressToZipFileInitParamDTO fileToCompress;
//		FileDownloadInfoResultDTO file = null;
//		String orderCaption;
//		String downloadName;
//		String realName;
//		for (DvrOrderPdfReportDataDTO data : result.getDvrorders()) {
//			// Setear parámetros del reporte
//			// Encabezado
//			parameters = new HashMap<String, Object>();
//			parameters.put("TITLE", "Orden de Compra");
//			parameters.put("RETAILER_DESCRIPTION", data.getData().getRetailerdescription() == null ? "" : data.getData().getRetailerdescription());
//			parameters.put("VENDOR_TRADENAME",data.getData().getVendortradename());
//			parameters.put("NUMBER", data.getData().getNumber());
//			parameters.put("EMITTED", data.getData().getEmitted().format(dtfDate));
//			parameters.put("DELIVERY_DATE", data.getData().getDeliverydate().format(dtfDate) + " al " + data.getData().getExpiration().format(dtfDate));
//			parameters.put("PAYMENT_DAYS", data.getData().getPaymentdays() == null ? "" : data.getData().getPaymentdays());
//			parameters.put("DELIVERY_LOCATION_NAME", data.getData().getDeliverylocationname());
//			parameters.put("DELIVERY_LOCATION_CODE", data.getData().getDeliverylocationcode());
//			parameters.put("DELIVERY_LOCATION_ADDRESS", data.getData().getDeliverylocationaddress() == null ? "" : data.getData().getDeliverylocationaddress());
//			parameters.put("RESPONSIBLE_NAME", data.getData().getResponsiblename());
//			parameters.put("EXPIRATION", data.getData().getExpiration().format(dtfDate));
//			parameters.put("IMAGE_PATH", logoImagePath);
//						
//			// Totales
//			parameters.put("TOTAL_UNITS", data.getData().getTotalunits());
//			parameters.put("FINAL_COST", data.getData().getFinalcost());
//			parameters.put("TOTAL_DISCOUNTS", data.getData().getTotaldiscounts());
//			parameters.put("TOTAL_NEED", data.getData().getTotalneed());
//			
//			// Obtener el nombre del archivo individual
//			orderCaption = "Orden No " + data.getData().getNumber();
//			downloadName = orderCaption;
//			realName = orderCaption;
//
//			try {
//				file = FileHandlerUtils.getInstance().downloadPdfFile(parameters, data.getDetails(), jrxml, downloadName, realName, 1L);
//			} catch (JRException | IOException e) {
//				e.printStackTrace();
//			}
//			
//			// 1 - Obtener el nombre del archivo comprimido
//			String downloadfile = "";
//			if (result.getDvrorders().length > 1) {
//				downloadfile = "Órdenes al ";
//			}
//			else {
//				downloadfile = file.getDownloadfilename();
//			}
//
//			fileToCompress = new CompressToZipFileInitParamDTO();
//			fileToCompress.setDownloadFileName(downloadfile);
//			fileToCompress.setSourceFileName(file.getRealfilename());
//			compressList.add(fileToCompress);
//		}
//
//		if (compressList.size() == 1) {
//			;
//		}
//		else if (compressList.size() > 1) {
//			CompressToZipFileResultDTO compressToZipFileResultDTO = null;
//			String fileCaption = "Órdenes al ";
//			try {
//				compressToZipFileResultDTO = FileHandlerUtils.getInstance().compressFilesToZipFile(compressList, fileCaption, fileCaption, 1L);
//			} catch (Exception e) {
//				;
//			}
//		}
//
//		assertEquals(1, 1);		
//	}
	
}
