package bbr.b2b.logistic.ddcorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.logistic.report.classes.WSResultDTO;

public class RescheduleDateDDCOrdersResultDTO extends BaseResultDTO {

	private BaseResultDTO[] validationerrors;
	private RescheduleDateDDCOrdersDataDTO[] rejectedOrders;
	private WSResultDTO wsresult;

	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}

	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}

	public RescheduleDateDDCOrdersDataDTO[] getRejectedOrders() {
		return rejectedOrders;
	}

	public void setRejectedOrders(RescheduleDateDDCOrdersDataDTO[] rejectedOrders) {
		this.rejectedOrders = rejectedOrders;
	}

	public WSResultDTO getWsresult() {
		return wsresult;
	}

	public void setWsresult(WSResultDTO wsresult) {
		this.wsresult = wsresult;
	}

}
