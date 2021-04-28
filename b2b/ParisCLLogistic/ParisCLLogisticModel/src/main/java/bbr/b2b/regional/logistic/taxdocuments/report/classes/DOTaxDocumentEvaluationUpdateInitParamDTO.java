package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class DOTaxDocumentEvaluationUpdateInitParamDTO implements Serializable {

	private DOTaxDocumentEvaluationUpdateDataDTO[] updatedata;
	private String username;
	private String usertype;
	
	public DOTaxDocumentEvaluationUpdateDataDTO[] getUpdatedata() {
		return updatedata;
	}
	public void setUpdatedata(DOTaxDocumentEvaluationUpdateDataDTO[] updatedata) {
		this.updatedata = updatedata;
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
