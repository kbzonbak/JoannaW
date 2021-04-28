package bbr.b2b.logistic.managers.interfaces;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckOrderInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckOrderResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckReceptionInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckReceptionResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.HistoryInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderDetailReportInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderDetailReportResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderHistoryDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderStateTypeResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.ResendInitParamDTO;

public interface IBuyOrderReportManager {

	public OrderStateTypeResultDTO getOrderStateTypes();
	public OrderReportResultDTO getOrderReportByVendorAndFilter(OrderReportInitParamDTO initParamDTO);
	public OrderDetailReportResultDTO getOrderDetailByOrder(OrderDetailReportInitParamDTO initParamDTO);
	public BaseResultDTO doResendOC(ResendInitParamDTO ocnumbers);
	public OrderHistoryDTO getOrderHistory(HistoryInitParamDTO initparam);
	public CheckOrderResultDTO doCheckOrderByRetail(CheckOrderInitParamDTO initparam);
	public CheckReceptionResultDTO doCheckReceptionByRetail(CheckReceptionInitParamDTO initparam);
}
