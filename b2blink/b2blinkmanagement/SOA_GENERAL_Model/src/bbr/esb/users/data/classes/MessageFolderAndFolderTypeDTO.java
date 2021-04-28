package bbr.esb.users.data.classes;


public class MessageFolderAndFolderTypeDTO  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2548544274667024097L;

	FTPMessageFolderDTO ftp = new FTPMessageFolderDTO();
	MessageFolderDTO mf = new MessageFolderDTO();
	MessageFolderTypeDTO mft = new MessageFolderTypeDTO();
	
	public FTPMessageFolderDTO getFtp() {
		return ftp;
	}
	public void setFtp(FTPMessageFolderDTO ftp) {
		this.ftp = ftp;
	}
	public MessageFolderDTO getMf() {
		return mf;
	}
	public void setMf(MessageFolderDTO mf) {
		this.mf = mf;
	}
	public MessageFolderTypeDTO getMft() {
		return mft;
	}
	public void setMft(MessageFolderTypeDTO mft) {
		this.mft = mft;
	}	
}
