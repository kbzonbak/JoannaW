package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class PDFVeVPDOrderReportDataDTO implements Serializable {

	private Long id;

	private Long docnumber;

	private String requestnumber;

	private String originaldeliverydate;

	private String clientrut;

	private String clientname;

	private String clientaddress;

	private String sellerstore;

	private String regiondelivery;

	private String communedelivery;

	private String citydelivery;

	private String clientphone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDocnumber() {
		return docnumber;
	}

	public void setDocnumber(Long docnumber) {
		this.docnumber = docnumber;
	}

	public String getRequestnumber() {
		return requestnumber;
	}

	public void setRequestnumber(String requestnumber) {
		this.requestnumber = requestnumber;
	}

	public String getOriginaldeliverydate() {
		return originaldeliverydate;
	}

	public void setOriginaldeliverydate(String originaldeliverydate) {
		this.originaldeliverydate = originaldeliverydate;
	}

	public String getClientrut() {
		return clientrut;
	}

	public void setClientrut(String clientrut) {
		this.clientrut = clientrut;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getClientaddress() {
		return clientaddress;
	}

	public void setClientaddress(String clientaddress) {
		this.clientaddress = clientaddress;
	}

	public String getSellerstore() {
		return sellerstore;
	}

	public void setSellerstore(String sellerstore) {
		this.sellerstore = sellerstore;
	}

	public String getRegiondelivery() {
		return regiondelivery;
	}

	public void setRegiondelivery(String regiondelivery) {
		this.regiondelivery = regiondelivery;
	}

	public String getCommunedelivery() {
		return communedelivery;
	}

	public void setCommunedelivery(String communedelivery) {
		this.communedelivery = communedelivery;
	}

	public String getCitydelivery() {
		return citydelivery;
	}

	public void setCitydelivery(String citydelivery) {
		this.citydelivery = citydelivery;
	}

	public String getClientphone() {
		return clientphone;
	}

	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
	}

}
