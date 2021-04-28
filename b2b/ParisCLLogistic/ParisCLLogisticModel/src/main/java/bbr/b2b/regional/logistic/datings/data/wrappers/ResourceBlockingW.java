package bbr.b2b.regional.logistic.datings.data.wrappers;

import bbr.b2b.regional.logistic.datings.data.interfaces.IResourceBlocking;

public class ResourceBlockingW extends ReserveW implements IResourceBlocking {

	private String comment;
	private Long blockinggroupid;

	public String getComment(){ 
		return this.comment;
	}
	public Long getBlockinggroupid(){ 
		return this.blockinggroupid;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setBlockinggroupid(Long blockinggroupid){ 
		this.blockinggroupid = blockinggroupid;
	}
}
