package bbr.b2b.regional.logistic.datings.entities;

import bbr.b2b.regional.logistic.datings.entities.Reserve;
import bbr.b2b.regional.logistic.datings.entities.ResourceBlockingGroup;
import bbr.b2b.regional.logistic.datings.data.interfaces.IResourceBlocking;

public class ResourceBlocking extends Reserve implements IResourceBlocking {

	private String comment;
	private ResourceBlockingGroup blockinggroup;

	public String getComment(){ 
		return this.comment;
	}
	public ResourceBlockingGroup getBlockinggroup(){ 
		return this.blockinggroup;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setBlockinggroup(ResourceBlockingGroup blockinggroup){ 
		this.blockinggroup = blockinggroup;
	}
}
