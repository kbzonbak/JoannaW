package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class DOTaxDocumentAddDataDTO implements Serializable{

	private Long donumber;
	private Long dotaxdocumentnumber;
		
	public Long getDonumber() {
		return donumber;
	}
	public void setDonumber(Long donumber) {
		this.donumber = donumber;
	}
	public Long getDotaxdocumentnumber() {
		return dotaxdocumentnumber;
	}
	public void setDotaxdocumentnumber(Long dotaxdocumentnumber) {
		this.dotaxdocumentnumber = dotaxdocumentnumber;
	}
		
}
