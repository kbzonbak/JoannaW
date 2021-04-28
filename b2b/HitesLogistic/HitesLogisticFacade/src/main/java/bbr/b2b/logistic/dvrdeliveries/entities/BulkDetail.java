package bbr.b2b.logistic.dvrdeliveries.entities;

import bbr.b2b.logistic.dvrdeliveries.entities.Bulk;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetail;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IBulkDetail;

public class BulkDetail implements IBulkDetail {

	private BulkDetailId id;
	private Double units;
	private String batchnumber;
	private Bulk bulk;
	private DvrOrderDeliveryDetail dvrorderdeliverydetail;

	public BulkDetailId getId() {
		return id;
	}

	public void setId(BulkDetailId id) {
		this.id = id;
	}

	public Double getUnits() {
		return units;
	}

	public void setUnits(Double units) {
		this.units = units;
	}

	public String getBatchnumber() {
		return batchnumber;
	}

	public void setBatchnumber(String batchnumber) {
		this.batchnumber = batchnumber;
	}

	public Bulk getBulk() {
		return bulk;
	}

	public void setBulk(Bulk bulk) {
		this.bulk = bulk;
	}

	public DvrOrderDeliveryDetail getDvrorderdeliverydetail() {
		return dvrorderdeliverydetail;
	}

	public void setDvrorderdeliverydetail(DvrOrderDeliveryDetail dvrorderdeliverydetail) {
		this.dvrorderdeliverydetail = dvrorderdeliverydetail;
	}

}
