package bbr.b2b.portal.classes.utils.app;

import org.vaadin.openesignforms.ckeditor.CKEditorConfig;

import bbr.b2b.portal.classes.constants.BbrPublishingConstants;

public class TextEditorUtil 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static TextEditorUtil instance = new TextEditorUtil();
	public static TextEditorUtil getInstance()
	{
		return instance;
	}
	

	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	private TextEditorUtil() 
	{
		
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************
	
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PUBLIC METHODS
	// ****************************************************************************************
	public CKEditorConfig getCKEditorConfig()
	{
		CKEditorConfig result = new CKEditorConfig();
        result.useCompactTags();
        result.disableElementsPath();
        result.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
        result.disableSpellChecker();
        result.setWidth("100%");
        result.setFilebrowserUploadUrl(BbrPublishingConstants.FILE_UPLOAD_URL);
        result.setFilebrowserImageUploadUrl(BbrPublishingConstants.IMAGE_UPLOAD_URL);
        result.setSkin("bootstrapck");
        
		return result;
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			PUBLIC METHODS
	// ****************************************************************************************
	
	

}
