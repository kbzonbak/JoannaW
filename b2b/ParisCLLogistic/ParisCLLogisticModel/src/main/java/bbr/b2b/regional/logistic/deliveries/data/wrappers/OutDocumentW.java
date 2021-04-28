package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOutDocument;

public class OutDocumentW extends ElementDTO implements IOutDocument {

	private String refdocument;
	private Long outreceptionid;

	public String getRefdocument(){ 
		return this.refdocument;
	}
	public Long getOutreceptionid(){ 
		return this.outreceptionid;
	}
	public void setRefdocument(String refdocument){ 
		this.refdocument = refdocument;
	}
	public void setOutreceptionid(Long outreceptionid){ 
		this.outreceptionid = outreceptionid;
	}
}
