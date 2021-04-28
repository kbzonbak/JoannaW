package bbr.b2b.logistic.ddcorders.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderDetailW;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderDetail;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderDetailId;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailDataDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailExcelReportResultDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailReportTotalDataDTO;

public interface IDdcOrderDetailServer extends IGenericServer<DdcOrderDetail, DdcOrderDetailId, DdcOrderDetailW> {

	DdcOrderDetailW addDdcOrderDetail(DdcOrderDetailW ddcorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcOrderDetailW[] getDdcOrderDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcOrderDetailW updateDdcOrderDetail(DdcOrderDetailW ddcorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	void doCalculateTotalDdcOrderDetails(Long[] ddcorderids);
	DdcOrderDetailDataDTO[] getDdcOrderDetailByDdcOrder(Long ddcorderid, Long vendorid,  int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException;
	DdcOrderDetailReportTotalDataDTO countDdcOrderDetailByDdcOrder(Long ddcorderid, Long vendorid) throws AccessDeniedException;
	DdcOrderDetailW[] getDirectOrderDetailDataofDirectOrder(Long directorderid) throws ServiceException;
	DdcOrderDetailExcelReportResultDTO getDdcOrderDetailExcelReportByOrder(Long ddcorderid) throws OperationFailedException;
	int countDdcOrderDetailExcelReportByOrder(Long ddcorderid);
}
