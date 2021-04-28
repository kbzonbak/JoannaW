package bbr.b2b.portal.constants;

import java.io.File;

import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;

public class PortalConstants
{

	private static PortalConstants _instance;

	public static synchronized PortalConstants getInstance()
	{
		if (_instance == null)
		{
			_instance = new PortalConstants();
		}

		return _instance;
	}

	private String					FILE_TRANSFER_PATH							= new String(B2BSystemPropertiesUtil.getProperty("download_file_path"));
	private String					FILE_UPLOAD_PATH							= new String(B2BSystemPropertiesUtil.getProperty("upload_file_path"));
	private String					FILE_CK_UPLOAD_PATH							= new String(B2BSystemPropertiesUtil.getProperty("ck_upload_file_path"));
	private String					REPORTS_DOCUMENTS_BASE_PATH					= new String(B2BSystemPropertiesUtil.getProperty("reports_documents_base_path"));
	private String					IS_EXTERNAL_RETAIL							= B2BSystemPropertiesUtil.getProperty("is_external_retail");
	private Boolean					fileTransferPathChecked						= false;

	private Boolean					fileUploadPathChecked						= false;
	private Boolean					fileCkUploadPathChecked						= false;

	private String					CHAT_SHARED_FILES_TRANSFER_PATH				= "";
	private Boolean					chatSharedFilesPathChecked					= false;

	public static final Long		ID_RETAIL									= 1L;
	public static final Long		ID_BBR										= 2L;

	public static final String		DEFAULT_POSITION							= "Sin Cargo";

	public static final String[]	SUBGRUPO_PR_RETAILER						= { "Cat. N1", "Cat. N2", "SKU" };																								// subgrp_pr
																																																				// subgrp_pr
	public static final String[]	SUBGRUPO_PR_PROVEEDOR						= { "Nivel 1", "Nivel 2", "SKU" };																								// subgrp_pv
																																																				// subgrp_pv
	public static final String[]	SUBGRUPO_LO_RETAILER						= { "Departamento", "Ciudad", "Local" };																						// subgrp_lo

	public static final String[]	MONTHS										= { "january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december" };

	public static final int			CAT_PROD_NIVEL								= SUBGRUPO_PR_RETAILER.length - 1;
	public static final int			CAT_LOC_NIVEL								= SUBGRUPO_LO_RETAILER.length - 1;

	public static final String		CHARSET_CSVLE								= "UTF-16LE";
	public static final String		CHARSET_CSV									= "UTF-16LE";

	public static final String		URL_ICF										= "http://icfweb.proveedores.cencosud.cl/ch_icf_proveedores/loginExternalServlet";

	public static final String		REGEXP_MAIL									= "^[a-zA-Z0-9]+([-+._&][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([-.][a-zA-Z0-9]+)*[.][a-zA-Z]{2,6}$";

	// Archivos properties manejados por el sistema.
	/***********************************************************************************/
	public static final String		MAIL_PROPERTIES_FILE						= "bbr.b2b.portal.properties.Mail";
	public static final String		SYSINFO_PROPERTIES_FILE						= "bbr.b2b.portal.properties.SysInfo";
	public static final String		CONTACT_PROPERTIES_FILE						= "bbr.b2b.portal.properties.Contact";
	public static final String		COMMENTCONTACT_PROPERTIES_FILE				= "bbr.b2b.portal.properties.CommentContact";
	public static final String		MIMETYPE_PROPERTIES_FILE					= "bbr.b2b.portal.properties.MimeType";
	public static final String		PAGES_FUNCS_PROPERTIES_FILE					= "bbr.b2b.portal.properties.PagesFuncMapping";
	public static final String		STATUS_CODE_UTILS_PROPERTIES_FILE			= "bbr.b2b.superar.portal.properties.PortalStatusCodeUtils";

	private String					WEBPROJECT_PATH								= "";

	// PATH DE INTERFACES MANAGER
	public static final String		MANAGER_CLASSPATH							= "java:global/IRSB2BLinkPortalEAR/IRSB2BLinkPortalManager/managers";
	public static final String		WEB_CLASSPATH								= "java:global/IRSB2BLinkPortalEAR/IRSB2BLinkPortalWeb/managers";
	public static final String		WEB_CLASSPATH_DAO							= "java:global/IRSB2BLinkPortalEAR/IRSB2BLinkPortalWeb/dao";

	// private static final String OS = "linux";
	private static final String		OS											= System.getProperty("os.name");

	// Para usar en la subida de imagenes en modificacion de atributos
	public static final String		INTERNAL_FILES								= "INTERNAL-FILES";

	public static final int			DEFAULT_PAGE_NUMBER					= 1;
	
	public static String getOS()
	{
		return OS;
	}

	/***
	 * Se evalua "is_external_retail" del standalone para diferenciar si los
	 * usuarios son externos (Keycloak) o internos.
	 * 
	 * @return
	 * 		Por defecto isExternalRetail = true -> son externos (Keycloak)
	 */
	public boolean isExternalRetail()
	{
		// Se evalua "is_external_retail" del standalone para diferenciar si los
		// usuarios son externos (Keycloak) o internos.
		if ((this.IS_EXTERNAL_RETAIL != null))
		{
			return new Boolean(this.IS_EXTERNAL_RETAIL);
		}
		else
		{
			// Por defecto isExternalRetail = true -> son externos (Keycloak)
			return true;
		}
	}

	public String getReportsDocumentsBasePath()
	{
		if ((this.REPORTS_DOCUMENTS_BASE_PATH != null) && (this.REPORTS_DOCUMENTS_BASE_PATH.length() > 0))
		{
			File dir = new File(this.REPORTS_DOCUMENTS_BASE_PATH);
			if (!dir.exists())
			{
				dir.mkdirs();
			}
		}

		return this.REPORTS_DOCUMENTS_BASE_PATH;
	}

	public String getReportsDocumentsPathByType(String type)
	{
		String result = this.getReportsDocumentsBasePath();

		result += type.toUpperCase() + File.separator;

		if ((result != null) && (result.length() > 0))
		{
			File dir = new File(result);
			if (!dir.exists())
			{
				dir.mkdirs();
			}
		}

		return result;
	}

	public String getFileTransferPath()
	{
		if ((this.FILE_TRANSFER_PATH != null) && (this.FILE_TRANSFER_PATH.length() > 0) && !this.fileTransferPathChecked)
		{
			this.fileTransferPathChecked = true;

			File dir = new File(this.FILE_TRANSFER_PATH);
			dir.mkdirs();
		}

		return this.FILE_TRANSFER_PATH;
	}

	public String getFileUploadPath()
	{
		if ((this.FILE_UPLOAD_PATH != null) && (this.FILE_UPLOAD_PATH.length() > 0) && !this.fileUploadPathChecked)
		{
			this.fileUploadPathChecked = true;

			File dir = new File(this.FILE_UPLOAD_PATH);
			dir.mkdirs();
		}

		return this.FILE_UPLOAD_PATH;
	}

	public String getFileCkUploadPath()
	{
		if ((this.FILE_CK_UPLOAD_PATH != null) && (this.FILE_CK_UPLOAD_PATH.length() > 0) && !this.fileCkUploadPathChecked)
		{
			this.fileCkUploadPathChecked = true;

			File dir = new File(this.FILE_CK_UPLOAD_PATH);
			dir.mkdirs();
		}

		return this.FILE_CK_UPLOAD_PATH;
	}

	public void setFileTransferPath(String s)
	{
		this.FILE_TRANSFER_PATH = (s != null) ? s.trim() : "";

		if ((this.FILE_TRANSFER_PATH != null) && (this.FILE_TRANSFER_PATH.length() > 0) && !this.fileTransferPathChecked)
		{
			this.fileTransferPathChecked = true;

			File dir = new File(this.FILE_TRANSFER_PATH);
			dir.mkdirs();
		}
	}

	

	

	public String getChatSharedFilesTransferPath()
	{
		if ((this.CHAT_SHARED_FILES_TRANSFER_PATH != null) && (this.CHAT_SHARED_FILES_TRANSFER_PATH.length() > 0) && !this.chatSharedFilesPathChecked)
		{
			this.chatSharedFilesPathChecked = true;

			File dir = new File(this.CHAT_SHARED_FILES_TRANSFER_PATH);
			dir.mkdirs();
		}

		return this.CHAT_SHARED_FILES_TRANSFER_PATH;
	}

	public void setChatSharedFilesTransferPath(String s)
	{
		this.CHAT_SHARED_FILES_TRANSFER_PATH = (s != null) ? s.trim() : "";

		if ((this.CHAT_SHARED_FILES_TRANSFER_PATH != null) && (this.CHAT_SHARED_FILES_TRANSFER_PATH.length() > 0) && !this.chatSharedFilesPathChecked)
		{
			this.chatSharedFilesPathChecked = true;

			File dir = new File(this.CHAT_SHARED_FILES_TRANSFER_PATH);
			dir.mkdirs();
		}
	}

	private String baseDownloadPath = null;

	public String getBaseDownloadPath()
	{
		return this.baseDownloadPath;
	}

	public void setBaseDownloadPath(String path)
	{
		this.baseDownloadPath = (path != null) ? path.trim() : "";
	}

	public void setWebProjectPath(String s)
	{
		this.WEBPROJECT_PATH = s;
	}

	public String getWebProjectPath()
	{
		return this.WEBPROJECT_PATH;
	}
}
