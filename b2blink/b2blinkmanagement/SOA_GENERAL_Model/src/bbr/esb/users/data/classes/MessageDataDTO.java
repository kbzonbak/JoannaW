package bbr.esb.users.data.classes;

import java.io.Serializable;

public class MessageDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8545068643798595257L;

	private MessageFolderDataDTO folder;

	private MessageFormatDataDTO format;

	private boolean encrypt;

	private String encryptpassword;

	private WSEndpointDataDTO endpoint;

	private String sitename;
	
	private String servicename;	
	
	private boolean compresseddocument;
	
	public boolean getCompresseddocument() {
		return compresseddocument;
	}

	public void setCompresseddocument(boolean compresseddocument) {
		this.compresseddocument = compresseddocument;
	}

	public boolean getEncrypt() {
		return encrypt;
	}

	public String getEncryptpassword() {
		return encryptpassword;
	}

	public MessageFolderDataDTO getFolder() {
		return folder;
	}

	public MessageFormatDataDTO getFormat() {
		return format;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}

	public void setEncryptpassword(String encryptpassword) {
		this.encryptpassword = encryptpassword;
	}

	public void setFolder(MessageFolderDataDTO folder) {
		this.folder = folder;
	}

	public void setFormat(MessageFormatDataDTO format) {
		this.format = format;
	}

	public WSEndpointDataDTO getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(WSEndpointDataDTO endpoint) {
		this.endpoint = endpoint;
	}

	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}	

}
