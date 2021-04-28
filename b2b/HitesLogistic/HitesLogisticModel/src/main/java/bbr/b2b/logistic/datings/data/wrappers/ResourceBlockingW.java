package bbr.b2b.logistic.datings.data.wrappers;

import bbr.b2b.logistic.datings.data.interfaces.IResourceBlocking;

public class ResourceBlockingW extends ReserveW implements IResourceBlocking {

	private Long blockinggroupid;
	private String comment;

	public Long getBlockinggroupid() {
		return this.blockinggroupid;
	}

	public void setBlockinggroupid(Long blockinggroupid) {
		this.blockinggroupid = blockinggroupid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
