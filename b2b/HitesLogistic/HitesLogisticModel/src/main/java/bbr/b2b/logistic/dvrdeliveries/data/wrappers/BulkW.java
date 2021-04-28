package bbr.b2b.logistic.dvrdeliveries.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IBulk;

public class BulkW extends ElementDTO implements IBulk {

	private String lpncode;
	private Double totalunits;
	private Boolean active;
	private Long dvrdeliveryid;
	private Long documentid;

	public String getLpncode() {
		return lpncode;
	}

	public Double getTotalunits() {
		return totalunits;
	}

	public Boolean getActive() {
		return active;
	}

	public Long getDvrdeliveryid() {
		return dvrdeliveryid;
	}

	public Long getDocumentid() {
		return documentid;
	}

	public void setLpncode(String lpncode) {
		this.lpncode = lpncode;
	}

	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setDvrdeliveryid(Long dvrdeliveryid) {
		this.dvrdeliveryid = dvrdeliveryid;
	}

	public void setDocumentid(Long documentid) {
		this.documentid = documentid;
	}

}
