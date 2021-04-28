package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;
import java.util.Date;

public class DOTaxDocumentStateCommentDTO implements Serializable {

	private Long directordernumber;
	private Long dotaxdocumentnumber;
	private String dotaxdocumentstatetypecode;
	private String dotaxdocumentstatetype;
	private String comment;
	private Date when;
	
	public Long getDirectordernumber() {
		return directordernumber;
	}
	public void setDirectordernumber(Long directordernumber) {
		this.directordernumber = directordernumber;
	}
	public Long getDotaxdocumentnumber() {
		return dotaxdocumentnumber;
	}
	public void setDotaxdocumentnumber(Long dotaxdocumentnumber) {
		this.dotaxdocumentnumber = dotaxdocumentnumber;
	}
	public String getDotaxdocumentstatetypecode() {
		return dotaxdocumentstatetypecode;
	}
	public void setDotaxdocumentstatetypecode(String dotaxdocumentstatetypecode) {
		this.dotaxdocumentstatetypecode = dotaxdocumentstatetypecode;
	}
	public String getDotaxdocumentstatetype() {
		return dotaxdocumentstatetype;
	}
	public void setDotaxdocumentstatetype(String dotaxdocumentstatetype) {
		this.dotaxdocumentstatetype = dotaxdocumentstatetype;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}

}
