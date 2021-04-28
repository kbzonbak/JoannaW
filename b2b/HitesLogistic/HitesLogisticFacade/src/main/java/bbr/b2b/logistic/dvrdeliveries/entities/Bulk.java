package bbr.b2b.logistic.dvrdeliveries.entities;

import bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IBulk;

public class Bulk implements IBulk {

	private Long id;
	private String lpncode;
	private Double totalunits;
	private Boolean active;
	private DvrDelivery dvrdelivery;
	private Document document;

	public Long getId() {
		return id;
	}

	public String getLpncode() {
		return lpncode;
	}

	public Double getTotalunits() {
		return totalunits;
	}

	public Boolean getActive() {
		return active;
	}

	public DvrDelivery getDvrdelivery() {
		return dvrdelivery;
	}

	public Document getDocument() {
		return document;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setDvrdelivery(DvrDelivery dvrdelivery) {
		this.dvrdelivery = dvrdelivery;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
