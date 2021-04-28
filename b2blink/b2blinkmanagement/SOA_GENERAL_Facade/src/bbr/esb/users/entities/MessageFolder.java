package bbr.esb.users.entities;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.users.data.interfaces.IMessageFolder;

public class MessageFolder extends ElementDTO implements IMessageFolder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6588205626449675115L;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String path;

	private Company company;
	
	private MessageFolderType messagefoldertype;

	public MessageFolderType getMessagefoldertype() {
		return messagefoldertype;
	}

	public void setMessagefoldertype(MessageFolderType messagefoldertype) {
		this.messagefoldertype = messagefoldertype;
	}

	public Company getCompany() {
		return company;
	}

	public String getPath() {
		return path;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
