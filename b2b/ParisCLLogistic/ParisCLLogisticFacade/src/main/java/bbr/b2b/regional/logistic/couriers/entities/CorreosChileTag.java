package bbr.b2b.regional.logistic.couriers.entities;

import bbr.b2b.regional.logistic.couriers.data.interfaces.ICorreosChileTag;

public class CorreosChileTag extends CourierTag implements ICorreosChileTag {

	private Long id;

	private String abbreviationcenter;

	private String delegationcode;

	private String delegationname;

	private String destinationaddress;

	private String routingcode;

	private String recordsend;

	private String destinationcommune;

	private String abbreviationservice;

	private String admissioncode;

	private String cuartel;

	private String sector;

	private String sdp;

	private String movil;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbbreviationcenter() {
		return abbreviationcenter;
	}

	public void setAbbreviationcenter(String abbreviationcenter) {
		this.abbreviationcenter = abbreviationcenter;
	}

	public String getDelegationcode() {
		return delegationcode;
	}

	public void setDelegationcode(String delegationcode) {
		this.delegationcode = delegationcode;
	}

	public String getDelegationname() {
		return delegationname;
	}

	public void setDelegationname(String delegationname) {
		this.delegationname = delegationname;
	}

	public String getDestinationaddress() {
		return destinationaddress;
	}

	public void setDestinationaddress(String destinationaddress) {
		this.destinationaddress = destinationaddress;
	}

	public String getRoutingcode() {
		return routingcode;
	}

	public void setRoutingcode(String routingcode) {
		this.routingcode = routingcode;
	}

	public String getRecordsend() {
		return recordsend;
	}

	public void setRecordsend(String recordsend) {
		this.recordsend = recordsend;
	}

	public String getDestinationcommune() {
		return destinationcommune;
	}

	public void setDestinationcommune(String destinationcommune) {
		this.destinationcommune = destinationcommune;
	}

	public String getAbbreviationservice() {
		return abbreviationservice;
	}

	public void setAbbreviationservice(String abbreviationservice) {
		this.abbreviationservice = abbreviationservice;
	}

	public String getAdmissioncode() {
		return admissioncode;
	}

	public void setAdmissioncode(String admissioncode) {
		this.admissioncode = admissioncode;
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

	public String getSdp() {
		return sdp;
	}

	public void setSdp(String sdp) {
		this.sdp = sdp;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

}
