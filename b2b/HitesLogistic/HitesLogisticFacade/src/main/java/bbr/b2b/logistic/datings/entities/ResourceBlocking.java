package bbr.b2b.logistic.datings.entities;

import bbr.b2b.logistic.datings.entities.Reserve;
import bbr.b2b.logistic.datings.entities.ResourceBlockingGroup;
import bbr.b2b.logistic.datings.data.interfaces.IResourceBlocking;

public class ResourceBlocking extends Reserve implements IResourceBlocking {

	private ResourceBlockingGroup blockinggroup;
	private String comment;

	public ResourceBlockingGroup getBlockinggroup() {
		return this.blockinggroup;
	}

	public void setBlockinggroup(ResourceBlockingGroup blockinggroup) {
		this.blockinggroup = blockinggroup;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
