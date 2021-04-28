package bbr.esb.services.data.classes;

import bbr.common.adtclasses.classes.BaseResultDTO;

public class RequestFilterForTicketDTO extends BaseResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String values;
	Long ids;
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}
	public Long getIds() {
		return ids;
	}
	public void setIds(Long ids) {
		this.ids = ids;
	}
	
}
