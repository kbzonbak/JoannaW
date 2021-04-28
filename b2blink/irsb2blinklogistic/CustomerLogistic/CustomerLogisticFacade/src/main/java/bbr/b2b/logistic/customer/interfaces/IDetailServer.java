package bbr.b2b.logistic.customer.interfaces;

import java.util.List;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.buyorders.data.dto.HeaderOrderDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderPreDeliveryDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderProductDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.TotalPredeliveryDTO;
import bbr.b2b.logistic.buyorders.data.dto.TotalProductsDTO;
import bbr.b2b.logistic.customer.data.wrappers.DetailW;
import bbr.b2b.logistic.customer.entities.Detail;
import bbr.b2b.logistic.customer.entities.DetailId;

public interface IDetailServer extends IGenericServer<Detail, DetailId, DetailW> {

	DetailW addDetail(DetailW detail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DetailW[] getDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DetailW updateDetail(DetailW detail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public HeaderOrderDetailDTO[] getHeaderOrderDetail(String ocnumber, Long vendorid, Long clientid);
	public TotalProductsDTO getCountProductDetailsByOrder(Long orderid, Long vendorid, Long clientid) throws AccessDeniedException;
	public TotalPredeliveryDTO getCountPredeliveryDetailsByOrder(Long ocnumber, Long vendorid, Long clientid) throws AccessDeniedException;
	public OrderProductDetailDTO[] getProductOrdersDetails(Long orderid, Long vendorid, Long clientid,  int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException;
	public OrderPreDeliveryDetailDTO[] getPredeliveryOrderDetails(Long ocnumber, Long vendorid, Long clientid, int pagenumber, int rows,OrderCriteriaDTO[] orderby, boolean ispaginated, List<String> productos) throws AccessDeniedException;
}
