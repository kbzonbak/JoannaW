package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class DOTaxDocumentEvaluationUpdateDataDTO implements Serializable {
	
	private Integer row;
	private Long dotaxdocumentnumber;
	private Long donumber;
	private Boolean approve;
	
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Long getDotaxdocumentnumber() {
		return dotaxdocumentnumber;
	}
	public void setDotaxdocumentnumber(Long dotaxdocumentnumber) {
		this.dotaxdocumentnumber = dotaxdocumentnumber;
	}
	public Long getDonumber() {
		return donumber;
	}
	public void setDonumber(Long donumber) {
		this.donumber = donumber;
	}
	public Boolean getApprove() {
		return approve;
	}
	public void setApprove(Boolean approve) {
		this.approve = approve;
	}
		
}
