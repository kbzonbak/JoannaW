package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

/**
 * @author Jose Vilchez
 *
 */
public class DOTaxDocumentEvaluationInitParam implements Serializable {

	private String vendorcode;
	private Long[] documentsId;
	private boolean evaluation;
	private String username;
	private String usertype;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long[] getDocumentsId() {
		return documentsId;
	}
	public void setDocumentsId(Long[] documentsId) {
		this.documentsId = documentsId;
	}
	public boolean getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(boolean evaluation) {
		this.evaluation = evaluation;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
		
}
