package bbr.esb.users.entities;

import bbr.esb.users.data.interfaces.IMessageFolderType;

public class MessageFolderType implements IMessageFolderType {

	private static final long serialVersionUID = -3984447401883188198L;

	private Long id;
	private String code;
	private String description;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
