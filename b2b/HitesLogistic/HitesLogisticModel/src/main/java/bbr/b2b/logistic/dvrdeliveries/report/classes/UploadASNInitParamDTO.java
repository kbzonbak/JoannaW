package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class UploadASNInitParamDTO implements Serializable {

	private Long dvrdeliveryid;
	private Long[] documentids;

	public Long getDvrdeliveryid() {
		return dvrdeliveryid;
	}

	public void setDvrdeliveryid(Long dvrdeliveryid) {
		this.dvrdeliveryid = dvrdeliveryid;
	}

	public Long[] getDocumentids() {
		return documentids;
	}

	public void setDocumentids(Long[] documentids) {
		this.documentids = documentids;
	}
}
