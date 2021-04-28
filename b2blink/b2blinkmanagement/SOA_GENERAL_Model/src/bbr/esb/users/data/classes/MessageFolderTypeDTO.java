package bbr.esb.users.data.classes;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.users.data.interfaces.IMessageFolderType;

public class MessageFolderTypeDTO extends ElementDTO implements IMessageFolderType {

	private static final long serialVersionUID = -2585778320386113878L;
	private String code="";
	private String description="";
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
