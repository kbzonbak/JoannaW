package bbr.esb.users.data.classes;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public class MessageFormatDataDTO implements IIdentifiable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7841552984469596276L;

	private String format;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
