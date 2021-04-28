package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class PackingListReportDataDTO implements Serializable {

	private Long deliverynumber;
	private String destinationlocationcode;
	private String destinationlocation;
	private Long ordernumber;
	private String lpncode;
	private String sku;
	private String itemdescription;
	private String documentnumber;
	private String documentype;
	private Double units;

	public Long getDeliverynumber() {
		return deliverynumber;
	}

	public void setDeliverynumber(Long deliverynumber) {
		this.deliverynumber = deliverynumber;
	}

	public String getDestinationlocationcode() {
		return destinationlocationcode;
	}

	public void setDestinationlocationcode(String destinationlocationcode) {
		this.destinationlocationcode = destinationlocationcode;
	}

	public String getDestinationlocation() {
		return destinationlocation;
	}

	public void setDestinationlocation(String destinationlocation) {
		this.destinationlocation = destinationlocation;
	}

	public Long getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}

	public String getLpncode() {
		return lpncode;
	}

	public void setLpncode(String lpncode) {
		this.lpncode = lpncode;
	}

	public String getSku() {
		return sku;
	}

	public String getItemdescription() {
		return itemdescription;
	}

	public void setItemdescription(String itemdescription) {
		this.itemdescription = itemdescription;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDocumentnumber() {
		return documentnumber;
	}

	public void setDocumentnumber(String documentnumber) {
		this.documentnumber = documentnumber;
	}

	public String getDocumentype() {
		return documentype;
	}

	public void setDocumentype(String documentype) {
		this.documentype = documentype;
	}

	public Double getUnits() {
		return units;
	}

	public void setUnits(Double units) {
		this.units = units;
	}

}
