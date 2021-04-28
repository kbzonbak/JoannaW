package bbr.esb.users.data.classes;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public class WSEndpointDataDTO implements IIdentifiable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7841552984469596276L;

	private String endpoint;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
}
