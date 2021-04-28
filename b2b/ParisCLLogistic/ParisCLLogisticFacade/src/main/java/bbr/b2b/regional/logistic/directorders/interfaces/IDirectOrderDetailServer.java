package bbr.b2b.regional.logistic.directorders.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.buyorders.report.classes.PDFVeVPDOrderDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderDetailW;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderDetail;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderDetailId;
public interface IDirectOrderDetailServer extends IGenericServer<DirectOrderDetail, DirectOrderDetailId, DirectOrderDetailW> {

	DirectOrderDetailW addDirectOrderDetail(DirectOrderDetailW directorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DirectOrderDetailW[] getDirectOrderDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DirectOrderDetailW updateDirectOrderDetail(DirectOrderDetailW directorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DirectOrderDetailW[] getDirectOrderDetailDataofDirectOrder(Long directorderid) throws ServiceException;
	VeVPDOrderDetailReportDataDTO[] getVeVPDOrdersDetailsByOrder(Long orderid, Long vendorid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException;
	VeVPDOrderDetailReportTotalDataDTO getCountVeVPDOrdersDetailsByOrder(Long orderid,  Long vendorid) throws ServiceException;
	FileDownloadInfoResultDTO getCSVDirectOrderVeVReport(Long[] salestoreid, Long vendorid, Long orderstatetypeid, Date since, Date until, Long userId, boolean courier) throws OperationFailedException, NotFoundException;
	int countCSVDirectOrderVeVReport(Long[] salestoreid, Long vendorid, Long orderstatetypeid, Date since, Date until, boolean courier);
	
	PDFVeVPDOrderDetailReportDataDTO[] getPDFVeVPDOrdersDetailsByOrder(Long orderid, Long vendorid) throws ServiceException;
}
