package bbr.b2b.portal.constants.customer;

import java.io.File;

import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;

public class CustomerServiceConstants
{

	private static CustomerServiceConstants _instance;

	public static synchronized CustomerServiceConstants getInstance()
	{
		if (_instance == null)
		{
			_instance = new CustomerServiceConstants();
		}

		return _instance;
	}

	private String				MASTERSLOAD_FILE_PATH					= B2BSystemPropertiesUtil.getProperty("mastersload_file_path");
	private String				PRODUCTFILE_MASTERSLOAD_FILE_PATH		= B2BSystemPropertiesUtil.getProperty("productfile_mastersload_file_path");
	private String				LOCALFILE_MASTERSLOAD_FILE_PATH			= B2BSystemPropertiesUtil.getProperty("localfile_mastersload_file_path");
	private String				HOMOLOGATORFILE_MASTERSLOAD_FILE_PATH	= B2BSystemPropertiesUtil.getProperty("homologatorfile_mastersload_file_path");
	private String				SCORECARD_WEBSERVICE_ENDPOINT_PATH		= new String(B2BSystemPropertiesUtil.getProperty("scorecard_webservice_endpoint_path"));

	private Boolean				mastersLoadFilePathChecked				= false;
	private Boolean				scoreCardWebServiceEndpointPathChecked	= false;

	// tipos de archivo carga maestros Customer Service
	public static final String	PRODUCTFILE								= "PROD";
	public static final String	LOCALFILE								= "LOC";
	public static final String	HOMOLOGATORFILE							= "HOM";

	public static final String	REPORT_OPTION							= "Listado Orden de Compra";
	public static final String	SITENAME_DETAIL							= "CENCO_SUPERCL_LOG";

	public String getFileMastersLoadPath()
	{
		if ((this.MASTERSLOAD_FILE_PATH != null) && (this.MASTERSLOAD_FILE_PATH.length() > 0) && !this.mastersLoadFilePathChecked)
		{
			this.mastersLoadFilePathChecked = true;

			File dir = new File(this.MASTERSLOAD_FILE_PATH);
			dir.mkdirs();
			return this.MASTERSLOAD_FILE_PATH;
		}
		else if (this.mastersLoadFilePathChecked)
		{
			return this.MASTERSLOAD_FILE_PATH;
		}
		else
		{
			System.out.println("WARNING - variable 'mastersload_file_path' no definida");
			return "";
		}
	}

	public String getScoreCardWebServiceEndpointPath()
	{
		if ((this.SCORECARD_WEBSERVICE_ENDPOINT_PATH != null) && (this.SCORECARD_WEBSERVICE_ENDPOINT_PATH.length() > 0) && !this.scoreCardWebServiceEndpointPathChecked)
		{
			this.scoreCardWebServiceEndpointPathChecked = true;

			File dir = new File(this.SCORECARD_WEBSERVICE_ENDPOINT_PATH);
			dir.mkdirs();
		}

		return this.SCORECARD_WEBSERVICE_ENDPOINT_PATH;
	}

	public String getFileMastersLoadPathByFileType(String fileType)
	{
		String result = "";
		switch (fileType)
		{
			case "PROD":
				if ((this.PRODUCTFILE_MASTERSLOAD_FILE_PATH != null) && (this.PRODUCTFILE_MASTERSLOAD_FILE_PATH.length() > 0))
				{
					File dir = new File(this.PRODUCTFILE_MASTERSLOAD_FILE_PATH);
					dir.mkdirs();
					result = this.PRODUCTFILE_MASTERSLOAD_FILE_PATH;
				}
				break;
			case "LOC":
				if ((this.LOCALFILE_MASTERSLOAD_FILE_PATH != null) && (this.LOCALFILE_MASTERSLOAD_FILE_PATH.length() > 0))
				{
					File dir = new File(this.LOCALFILE_MASTERSLOAD_FILE_PATH);
					dir.mkdirs();
					result = this.LOCALFILE_MASTERSLOAD_FILE_PATH;
				}
				break;
			case "HOM":
				if ((this.HOMOLOGATORFILE_MASTERSLOAD_FILE_PATH != null) && (this.HOMOLOGATORFILE_MASTERSLOAD_FILE_PATH.length() > 0))
				{
					File dir = new File(this.HOMOLOGATORFILE_MASTERSLOAD_FILE_PATH);
					dir.mkdirs();
					result = this.HOMOLOGATORFILE_MASTERSLOAD_FILE_PATH;
				}
				break;
			default:
				System.out.println("WARNING - variable no definida");
				break;
		}
		return getFileMastersLoadPath() + result;
	}
}
