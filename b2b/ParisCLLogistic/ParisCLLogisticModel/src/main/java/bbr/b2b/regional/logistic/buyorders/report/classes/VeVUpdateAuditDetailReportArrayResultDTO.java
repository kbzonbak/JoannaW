package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VeVUpdateAuditDetailReportArrayResultDTO extends BaseResultDTO{

	private VeVAuditDetailDTO[] vevauditdto;
	private int totaldata;
	
	public int getTotaldata() {
		return totaldata;
	}
	public void setTotaldata(int totaldata) {
		this.totaldata = totaldata;
	}
	public VeVAuditDetailDTO[] getVevauditdto() {
		return vevauditdto;
	}
	public void setVevauditdto(VeVAuditDetailDTO[] vevauditdto) {
		this.vevauditdto = vevauditdto;
	}

}

