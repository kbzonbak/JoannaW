package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class DocumentUpdateDTO implements Serializable {

	private Long documentid;
	private Long number;
	
	public Long getDocumentid() {
		return documentid;
	}
	public void setDocumentid(Long documentid) {
		this.documentid = documentid;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}	
}
