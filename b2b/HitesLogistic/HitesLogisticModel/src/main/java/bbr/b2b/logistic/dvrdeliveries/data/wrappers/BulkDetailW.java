package bbr.b2b.logistic.dvrdeliveries.data.wrappers;

import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IBulkDetail;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IBulkDetailPK;
import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public class BulkDetailW implements IBulkDetail, IBulkDetailPK {

	private Double units;
	private Long bulkid;
	private Long dvrorderid;
	private Long dvrdeliveryid;
	private Long itemid;
	private Long locationid;
	private Integer position;
	private String batchnumber;

	public int compareTo(IPrimaryKey arg0) {
		long result = 0;
		result = bulkid.longValue() - ((IBulkDetailPK) arg0).getBulkid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = dvrdeliveryid.longValue() - ((IBulkDetailPK) arg0).getDvrdeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = dvrorderid.longValue() - ((IBulkDetailPK) arg0).getDvrorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IBulkDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = locationid.longValue() - ((IBulkDetailPK) arg0).getLocationid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IBulkDetailPK) arg0).getPosition().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Double getUnits() {
		return this.units;
	}

	public Long getBulkid() {
		return this.bulkid;
	}

	public Long getDvrorderid() {
		return this.dvrorderid;
	}

	public Long getDvrdeliveryid() {
		return this.dvrdeliveryid;
	}

	public Long getItemid() {
		return this.itemid;
	}

	public Long getLocationid() {
		return this.locationid;
	}

	public Integer getPosition() {
		return this.position;
	}

	public void setUnits(Double units) {
		this.units = units;
	}

	public void setBulkid(Long bulkid) {
		this.bulkid = bulkid;
	}

	public void setDvrorderid(Long dvrorderid) {
		this.dvrorderid = dvrorderid;
	}

	public void setDvrdeliveryid(Long dvrdeliveryid) {
		this.dvrdeliveryid = dvrdeliveryid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public void setLocationid(Long locationid) {
		this.locationid = locationid;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getBatchnumber() {
		return batchnumber;
	}

	public void setBatchnumber(String batchnumber) {
		this.batchnumber = batchnumber;
	}

}
