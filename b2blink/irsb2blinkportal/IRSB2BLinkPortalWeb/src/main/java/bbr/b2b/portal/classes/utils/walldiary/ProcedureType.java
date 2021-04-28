package bbr.b2b.portal.classes.utils.walldiary;

import bbr.b2b.portal.classes.constants.BbrPublishingConstants;

public enum ProcedureType 
{
	COMMERCIAL(BbrPublishingConstants.COMMERCIAL_PROC_PUBLISHING_CODE),FINANCE(BbrPublishingConstants.FINANCES_PROC_PUBLISHING_CODE),LOGISTIC(BbrPublishingConstants.LOGISTIC_PROC_PUBLISHING_CODE) ;
	
	private String value ;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	private ProcedureType(String value) {
		this.value = value;
	}
	
}
