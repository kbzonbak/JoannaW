package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailDataDTO;

public class AddImportedDeliveryOfOrdersInitParamDTO implements Serializable {

	private String vendorcode;
	private Long nGuia;
	private String nCont;	
	private String nImp;
	private String requestDate;
	private String userName;
	private String email;
	private PreDeliveryDetailDataDTO[] preddetails;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getNGuia() {
		return nGuia;
	}
	public void setNGuia(Long guia) {
		nGuia = guia;
	}
	public String getNCont() {
		return nCont;
	}
	public void setNCont(String cont) {
		nCont = cont;
	}
	public String getNImp() {
		return nImp;
	}
	public void setNImp(String imp) {
		nImp = imp;
	}	
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public PreDeliveryDetailDataDTO[] getPreddetails() {
		return preddetails;
	}
	public void setPreddetails(PreDeliveryDetailDataDTO[] preddetails) {
		this.preddetails = preddetails;
	}
}
