package bbr.b2b.logistic.ddcdeliveries.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryStateW;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDeliveryState;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDeliveryStateDataDTO;

public interface IDdcDeliveryStateServer extends IElementServer<DdcDeliveryState, DdcDeliveryStateW> {

	DdcDeliveryStateW addDdcDeliveryState(DdcDeliveryStateW ddcdeliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcDeliveryStateW[] getDdcDeliveryStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcDeliveryStateW updateDdcDeliveryState(DdcDeliveryStateW ddcdeliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcOrderDeliveryStateDataDTO[] getDdcOrderDeliveryStateByDdcOrder(Long ddcorderid, Long vendorid,  int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException;
}
