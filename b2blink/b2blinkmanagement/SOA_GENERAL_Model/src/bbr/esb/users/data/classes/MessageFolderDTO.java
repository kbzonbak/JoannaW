package bbr.esb.users.data.classes;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.users.data.interfaces.IMessageFolder;

public class MessageFolderDTO extends ElementDTO implements IMessageFolder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2548544274667024097L;

	private String path="";

	private Long companykey;
	
	private Long messagefoldertypekey;

	public Long getMessagefoldertypekey() {
		return messagefoldertypekey;
	}

	public void setMessagefoldertypekey(Long messagefoldertypekey) {
		this.messagefoldertypekey = messagefoldertypekey;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public String getPath() {
		return path;
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
