/*
 * Created on 15-01-2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package bbr.b2b.portal.wrapper;

/**
 * @author Marcelo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CompressToZipFileResultDTO {
	
	private String realFileName		= null;
	private String downloadFileName	= null;

	/**
	 * 
	 */
	public CompressToZipFileResultDTO() {
		super();
	}


	/**
	 * @return
	 */
	public String getDownloadFileName() {
		return downloadFileName;
	}

	/**
	 * @return
	 */
	public String getRealFileName() {
		return realFileName;
	}

	/**
	 * @param string
	 */
	public void setDownloadFileName(String string) {
		downloadFileName = string;
	}

	/**
	 * @param string
	 */
	public void setRealFileName(String string) {
		realFileName = string;
	}

}