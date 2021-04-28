package bbr.b2b.regional.logistic.deliveries.interfaces;

import java.util.Date;
import java.util.List;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryW;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliverySourceDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryWSRequestData;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryIdsByPagesW;
import bbr.b2b.regional.logistic.deliveries.report.classes.ExcelDODeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PDFDODeliveryDetailReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PDFDODeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ShippingCertificationDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryMobileDataDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryReportDataMobileDTO;

public interface IDODeliveryServer extends IElementServer<DODelivery, DODeliveryW> {

	DODeliveryW addDODelivery(DODeliveryW dodelivery) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DODeliveryW[] getDODeliverys() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DODeliveryW updateDODelivery(DODeliveryW dodelivery) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DODeliveryReportDataDTO[] getDODeliveryReport(Long number, String requestnumber, String text, Long vendorid, Date since, Date until, String dotype, int filtertype, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated, String sendnumber, Long withdrawalnumber) throws OperationFailedException, AccessDeniedException;
	Integer getDODeliveryCountReport(Long number, String text, Long vendorid, Date since, Date until, String dotype,int filtertype, String sendnumber, Long withdrawalnumber) throws OperationFailedException, AccessDeniedException;
	int getDODeliveryReportCountByDeliveries(Long[] deliveryIds);
	ExcelDODeliveryReportDataDTO[] getExcelDODeliveryReportByOrders(Long[] deliveryids) throws ServiceException;
	DeliveryIdsByPagesW getDODeliveryIdsByPages(Long vendorid, Long ocnumber, String requestnumber, String clientrut, Date since, Date until, String dotype, int rows, Integer filterType, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges, String sendnumber, Long withdrawalnumber) throws ServiceException;
	DODeliveryReportDataMobileDTO[] getDODeliveryDataByTruckDispatcher(String truckdispatcheremail, Long[] validstatetypeids);
	DODeliveryMobileDataDTO getDODeliveryDataById(Long dodeliveryid);
	PDFDODeliveryReportDataDTO[] getPDFDODeliveryReportByIDs(Long vendorid, Long[] deliveryid) throws ServiceException;
	PDFDODeliveryReportDataDTO getPDFDODeliveryReport(Long vendorid, Long deliveryid) throws ServiceException;
	PDFDODeliveryDetailReportDataDTO[] getPDFDeliveryDetailReport(Long vendorid, Long dodeliveryid) throws ServiceException;
	DODeliveryWSRequestData[] getWSRequestDataByDODeliveryId(Long dodeliveryid) throws ServiceException;
	Integer countDODeliveryCouriers(Long dodeliveryid);
	ShippingCertificationDTO getShippingCertificationReport(Long dodeliveryid) throws ServiceException;
	Long[] getDODeliveryIdsByNumbers(List<Long> numbers);
	DODeliverySourceDataDTO[] getDODeliveryDataSource(Long[] deliveryids) throws ServiceException;
}
