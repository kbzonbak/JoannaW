package bbr.b2b.portal.classes.utils.fep;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;

import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.wrappers.fep.HelpFileValues;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.components.basics.BbrUI;

public class FEPOthersFilesFactory
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private BbrUI			bbrUIParent	= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public FEPOthersFilesFactory(BbrUI bbrUIParent)
	{
		this.bbrUIParent = bbrUIParent;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************
	public HelpFileValues getOtherLayout(File newfile)
	{
		HelpFileValues result = new HelpFileValues();
		Resource resFile = (newfile != null) ? new FileResource(newfile) : null;
		String ext = FilenameUtils.getExtension(newfile.getPath()).toUpperCase();

		if (resFile != null)
		{

			long bytes = newfile.length();
			double kb = Math.rint(bytes / 1024F * 100) / 100;
			result.setFileName(newfile.getName());
			result.setSize(kb);
			result.setResFile(this.getResourceCase(ext));
			result.setImage(false);

		}
		return result;
	}

	private Resource getResourceCase(String ext)
	{
		Resource result = null;

		if (ext.toLowerCase().trim().equals("xls") || ext.toLowerCase().trim().equals("xlsx") || ext.toLowerCase().trim().equals("csv"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.bbrUIParent, BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_excel.png");
		}
		else if (ext.toLowerCase().trim().equals("pdf"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.bbrUIParent, BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_pdf.png");
		}
		else if (ext.toLowerCase().trim().equals("rar"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.bbrUIParent, BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_rar.png");
		}
		else if (ext.toLowerCase().trim().equals("doc") || ext.toLowerCase().trim().equals("docx"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.bbrUIParent, BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_word.png");
		}
		else if (ext.toLowerCase().trim().equals("ppt") || ext.toLowerCase().trim().equals("pptx"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.bbrUIParent, BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_ppt.png");
		}
		else if (ext.toLowerCase().trim().equals("txt"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.bbrUIParent, BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_txt.png");
		}
		else if (ext.toLowerCase().trim().equals("zip"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.bbrUIParent, BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_zip.png");
		}
		else
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.bbrUIParent, BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_generic.png");
		}

		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
