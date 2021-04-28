package bbr.b2b.regional.logistic.deliveries.entities;

import bbr.b2b.regional.logistic.deliveries.entities.OutReception;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOutDocument;

public class OutDocument implements IOutDocument {

	private Long id;
	private String refdocument;
	private OutReception outreception;

	public Long getId(){ 
		return this.id;
	}
	public String getRefdocument(){ 
		return this.refdocument;
	}
	public OutReception getOutreception(){ 
		return this.outreception;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setRefdocument(String refdocument){ 
		this.refdocument = refdocument;
	}
	public void setOutreception(OutReception outreception){ 
		this.outreception = outreception;
	}
}
