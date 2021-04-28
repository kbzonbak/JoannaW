package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.logistic.report.classes.WSResultDTO;

public class RejectDVHOrdersResultDTO extends BaseResultDTO {

	private BaseResultDTO[] validationerrors;
	private RejectDVHOrdersDataDTO[] rejectedOrders;
	private WSResultDTO wsresult;

	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}

	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}

	public RejectDVHOrdersDataDTO[] getRejectedOrders() {
		return rejectedOrders;
	}

	public void setRejectedOrders(RejectDVHOrdersDataDTO[] rejectedOrders) {
		this.rejectedOrders = rejectedOrders;
	}

	public WSResultDTO getWsresult() {
		return wsresult;
	}

	public void setWsresult(WSResultDTO wsresult) {
		this.wsresult = wsresult;
	}

}
