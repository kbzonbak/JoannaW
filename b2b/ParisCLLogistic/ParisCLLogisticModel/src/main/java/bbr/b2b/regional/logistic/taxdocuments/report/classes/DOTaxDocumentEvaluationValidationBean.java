package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class DOTaxDocumentEvaluationValidationBean implements Serializable{
	
	private Long dotaxdocumentnumber;
	private Long donumber;
	private String state;
	
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
