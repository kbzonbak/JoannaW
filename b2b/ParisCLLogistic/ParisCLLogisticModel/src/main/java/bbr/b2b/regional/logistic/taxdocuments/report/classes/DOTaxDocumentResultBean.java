package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class DOTaxDocumentResultBean implements Serializable{
	
	private Long dotaxdocumentnumber;
	private Long directordernumber;
	private String comment;
	
	public DOTaxDocumentResultBean() {
	}
	
	public DOTaxDocumentResultBean(Long dotaxdocumentnumber, Long directordernumber, String comment) {
		super();
		this.dotaxdocumentnumber = dotaxdocumentnumber;
		this.directordernumber = directordernumber;
		this.comment = comment;
	}
	
	public Long getDotaxdocumentnumber() {
		return dotaxdocumentnumber;
	}
	public void setDotaxdocumentnumber(Long dotaxdocumentnumber) {
		this.dotaxdocumentnumber = dotaxdocumentnumber;
	}
	public Long getDirectordernumber() {
		return directordernumber;
	}
	public void setDirectordernumber(Long directordernumber) {
		this.directordernumber = directordernumber;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
