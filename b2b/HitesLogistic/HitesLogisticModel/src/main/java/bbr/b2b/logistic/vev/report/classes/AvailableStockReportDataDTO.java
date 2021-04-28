package bbr.b2b.logistic.vev.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class AvailableStockReportDataDTO extends BaseResultDTO {

	private AvailableStockDataDTO[] availablestock;
	private String lastupdateuser;
	private String lastupdatetime;
	
	public AvailableStockDataDTO[] getAvailablestock() {
		return availablestock;
	}
	public void setAvailablestock(AvailableStockDataDTO[] availablestock) {
		this.availablestock = availablestock;
	}
	public String getLastupdateuser() {
		return lastupdateuser;
	}
	public void setLastupdateuser(String lastupdateuser) {
		this.lastupdateuser = lastupdateuser;
	}
	public String getLastupdatetime() {
		return lastupdatetime;
	}
	public void setLastupdatetime(String lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}	
}