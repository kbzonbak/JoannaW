package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ASNDataToMessageDTO implements Serializable {

	private Long dvrorderid;
	private Long ordernumber;
	private String ordertypecode;
	private Long dvrdeliverynumber;
	private String action;
	private String vendorcode;
	private String deliverylocationcode;
	private Long documentid;
	private String documentnumber;
	private String asnnumber;
	private String documentcode;
	private String documenttype;
	private LocalDateTime pluploaddate;
	private Double totalamount;
	private Double netamount;
	private Double taxamount;
	private LocalDateTime expirationdate;
	private List<ASNDetailDataToMessageDTO> asndetaildatatomessage;

	public Long getDvrorderid() {
		return dvrorderid;
	}

	public void setDvrorderid(Long dvrorderid) {
		this.dvrorderid = dvrorderid;
	}

	public Long getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}

	public String getOrdertypecode() {
		return ordertypecode;
	}

	public void setOrdertypecode(String ordertypecode) {
		this.ordertypecode = ordertypecode;
	}

	public Long getDvrdeliverynumber() {
		return dvrdeliverynumber;
	}

	public void setDvrdeliverynumber(Long dvrdeliverynumber) {
		this.dvrdeliverynumber = dvrdeliverynumber;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public String getDeliverylocationcode() {
		return deliverylocationcode;
	}

	public void setDeliverylocationcode(String deliverylocationcode) {
		this.deliverylocationcode = deliverylocationcode;
	}
	
	public Long getDocumentid() {
		return documentid;
	}

	public void setDocumentid(Long documentid) {
		this.documentid = documentid;
	}

	public String getDocumentnumber() {
		return documentnumber;
	}

	public void setDocumentnumber(String documentnumber) {
		this.documentnumber = documentnumber;
	}

	public String getAsnnumber() {
		return asnnumber;
	}

	public void setAsnnumber(String asnnumber) {
		this.asnnumber = asnnumber;
	}

	public String getDocumentcode() {
		return documentcode;
	}

	public void setDocumentcode(String documentcode) {
		this.documentcode = documentcode;
	}

	public String getDocumenttype() {
		return documenttype;
	}

	public void setDocumenttype(String documenttype) {
		this.documenttype = documenttype;
	}

	public LocalDateTime getPluploaddate() {
		return pluploaddate;
	}

	public void setPluploaddate(LocalDateTime pluploaddate) {
		this.pluploaddate = pluploaddate;
	}

	public Double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}

	public Double getNetamount() {
		return netamount;
	}

	public void setNetamount(Double netamount) {
		this.netamount = netamount;
	}

	public Double getTaxamount() {
		return taxamount;
	}

	public void setTaxamount(Double taxamount) {
		this.taxamount = taxamount;
	}

	public LocalDateTime getExpirationdate() {
		return expirationdate;
	}

	public void setExpirationdate(LocalDateTime expirationdate) {
		this.expirationdate = expirationdate;
	}

	public List<ASNDetailDataToMessageDTO> getAsndetaildatatomessage() {
		return asndetaildatatomessage;
	}

	public void setAsndetaildatatomessage(List<ASNDetailDataToMessageDTO> asndetaildatatomessage) {
		this.asndetaildatatomessage = asndetaildatatomessage;
	}
}
