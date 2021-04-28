package bbr.b2b.regional.logistic.taxdocuments.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.taxdocuments.data.interfaces.IDOTaxDocumentState;

public class DOTaxDocumentState implements IDOTaxDocumentState {

	private Long id;
	private Date when;
	private String who;
	private String usertype;
	private String comment;
	private DOTaxDocument dotaxdocument;
	private DOTaxDocumentStateType dotaxdocumentstatetype;
	private Long ticketnumber;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public DOTaxDocument getDotaxdocument() {
		return dotaxdocument;
	}
	public void setDotaxdocument(DOTaxDocument dotaxdocument) {
		this.dotaxdocument = dotaxdocument;
	}
	public DOTaxDocumentStateType getDotaxdocumentstatetype() {
		return dotaxdocumentstatetype;
	}
	public void setDotaxdocumentstatetype(DOTaxDocumentStateType dotaxdocumentstatetype) {
		this.dotaxdocumentstatetype = dotaxdocumentstatetype;
	}
	public Long getTicketnumber() {
		return ticketnumber;
	}
	public void setTicketnumber(Long ticketnumber) {
		this.ticketnumber = ticketnumber;
	}
		
}