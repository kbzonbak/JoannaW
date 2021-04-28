package bbr.b2b.regional.logistic.couriers.report.classes;

import java.io.Serializable;

public class CorreosChileCSVDTO implements Serializable {
	
	private String vendorname;
	private String vendorrut;
	private String clientcode;
	private String vendorphone1;
	private String abbreviationservice;
	private Long number;
	private String routingcode;
	private String sendnumber;
	private String destinationcommune;
	private String destinationaddress;
	private String clientname;
	private String clientphone;
	private String clientcomment;
	private String sdp;
	private String delegationname;
	private String cuartel;
	private String sector;
	private Long withdrawalnumber;

	public String getVendorname() {
		return vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public String getVendorrut() {
		return vendorrut;
	}

	public void setVendorrut(String vendorrut) {
		this.vendorrut = vendorrut;
	}

	public String getClientcode() {
		return clientcode;
	}

	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}

	public String getVendorphone1() {
		return vendorphone1;
	}

	public void setVendorphone1(String vendorphone1) {
		this.vendorphone1 = vendorphone1;
	}

	public String getAbbreviationservice() {
		return abbreviationservice;
	}

	public void setAbbreviationservice(String abbreviationservice) {
		this.abbreviationservice = abbreviationservice;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getRoutingcode() {
		return routingcode;
	}

	public void setRoutingcode(String routingcode) {
		this.routingcode = routingcode;
	}

	public String getSendnumber() {
		return sendnumber;
	}

	public void setSendnumber(String sendnumber) {
		this.sendnumber = sendnumber;
	}

	public String getDestinationcommune() {
		return destinationcommune;
	}

	public void setDestinationcommune(String destinationcommune) {
		this.destinationcommune = destinationcommune;
	}

	public String getDestinationaddress() {
		return destinationaddress;
	}

	public void setDestinationaddress(String destinationaddress) {
		this.destinationaddress = destinationaddress;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getClientphone() {
		return clientphone;
	}

	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
	}

	public String getClientcomment() {
		return clientcomment;
	}

	public void setClientcomment(String clientcomment) {
		this.clientcomment = clientcomment;
	}

	public String getSdp() {
		return sdp;
	}

	public void setSdp(String sdp) {
		this.sdp = sdp;
	}

	public String getDelegationname() {
		return delegationname;
	}

	public void setDelegationname(String delegationname) {
		this.delegationname = delegationname;
	}

	public String getCuartel() {
		return cuartel;
	}

	public void setCuartel(String cuartel) {
		this.cuartel = cuartel;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public Long getWithdrawalnumber() {
		return withdrawalnumber;
	}

	public void setWithdrawalnumber(Long withdrawalnumber) {
		this.withdrawalnumber = withdrawalnumber;
	}
	
}
