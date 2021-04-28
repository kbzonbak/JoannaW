package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.logistic.report.classes.WSResultDTO;

public class RescheduleDateDVHOrdersResultDTO extends BaseResultDTO {

	private BaseResultDTO[] validationerrors;
	private RescheduleDateDVHOrdersDataDTO[] rejectedOrders;
	private WSResultDTO wsresult;

	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}

	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}

	public RescheduleDateDVHOrdersDataDTO[] getRejectedOrders() {
		return rejectedOrders;
	}

	public void setRejectedOrders(RescheduleDateDVHOrdersDataDTO[] rejectedOrders) {
		this.rejectedOrders = rejectedOrders;
	}

	public WSResultDTO getWsresult() {
		return wsresult;
	}

	public void setWsresult(WSResultDTO wsresult) {
		this.wsresult = wsresult;
	}

}
