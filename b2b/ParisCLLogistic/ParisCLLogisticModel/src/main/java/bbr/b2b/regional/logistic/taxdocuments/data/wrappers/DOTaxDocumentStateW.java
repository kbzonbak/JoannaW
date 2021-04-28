package bbr.b2b.regional.logistic.taxdocuments.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.taxdocuments.data.interfaces.IDOTaxDocumentState;

public class DOTaxDocumentStateW extends ElementDTO implements IDOTaxDocumentState {
	
	private Date when;
	private String who;
	private String usertype;
	private String comment;
	private Long dotaxdocumentid;
	private Long dotaxdocumentstatetypeid;
	private Long ticketnumber;
	
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
	public Long getDotaxdocumentid() {
		return dotaxdocumentid;
	}
	public void setDotaxdocumentid(Long dotaxdocumentid) {
		this.dotaxdocumentid = dotaxdocumentid;
	}
	public Long getDotaxdocumentstatetypeid() {
		return dotaxdocumentstatetypeid;
	}
	public void setDotaxdocumentstatetypeid(Long dotaxdocumentstatetypeid) {
		this.dotaxdocumentstatetypeid = dotaxdocumentstatetypeid;
	}
	public Long getTicketnumber() {
		return ticketnumber;
	}
	public void setTicketnumber(Long ticketnumber) {
		this.ticketnumber = ticketnumber;
	}
	
}
