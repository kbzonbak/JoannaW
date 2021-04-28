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
public class CompressToZipFileInitParamDTO {
	
	private String sourceFileName	= null;
	private String downloadFileName	= null;

	/**
	 * 
	 */
	public CompressToZipFileInitParamDTO() {
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
	public String getSourceFileName() {
		return sourceFileName;
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
	public void setSourceFileName(String string) {
		sourceFileName = string;
	}

}