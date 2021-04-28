package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class ASNDetailDataToMessageDTO implements Serializable {

	private Long ocnumber;
	private String action;
	private String itemsku;
	private String destinationlocationcode;
	private String lpncode;
	private Double units;
	private String referencenumber;
	private Long documentid;
	
	public Long getOcnumber() {
		return ocnumber;
	}

	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getItemsku() {
		return itemsku;
	}

	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	
	public String getDestinationlocationcode() {
		return destinationlocationcode;
	}

	public void setDestinationlocationcode(String destinationlocationcode) {
		this.destinationlocationcode = destinationlocationcode;
	}

	public String getLpncode() {
		return lpncode;
	}

	public void setLpncode(String lpncode) {
		this.lpncode = lpncode;
	}

	public Double getUnits() {
		return units;
	}

	public void setUnits(Double units) {
		this.units = units;
	}

	public String getReferencenumber() {
		return referencenumber;
	}

	public void setReferencenumber(String referencenumber) {
		this.referencenumber = referencenumber;
	}

	public Long getDocumentid() {
		return documentid;
	}

	public void setDocumentid(Long documentid) {
		this.documentid = documentid;
	}

}
