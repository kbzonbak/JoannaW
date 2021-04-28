package bbr.b2b.portal.classes.constants;

import com.vaadin.server.Resource;
import com.vaadin.ui.UI;

import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.components.basics.BbrUI;

public enum EnumToolbarButton
{
	ACTION_RIGHT_PRIMARY(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_ActionRightPrimary.png") ,
	ACTION_RIGHT_ALTERNATIVE(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_ActionRightAlternative.png") ,
	ACTIVATE_ALTERNATIVE(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_ActivateAlternative.png") ,
	ACTIVATE_PRIMARY(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_ActivatePrimary.png") ,
	ADD_PRIMARY(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_AddPrimary.png"),
	ADD_ALTERNATIVE(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_AddAlternative.png"),
	DEACTIVATE_ALTERNATIVE(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DeactivateAlternative.png"),
	DEACTIVATE_PRIMARY(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DeactivatePrimary.png"),
	DELETE(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Delete.png"),
	DOWNLOAD_ALTERNATIVE(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DownloadAlternative.png"),
	DOWNLOAD_PRIMARY(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DownloadPrimary.png"),
	EDIT_ALTERNATIVE(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_EditAlternative.png"),
	EDIT_PRIMARY(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_EditPrimary.png"),
	HELP(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Help.png"),
	LIST(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_List.png"),
	PLAY_ALTERNATIVE(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_PlayAlternative.png"),
	PLAY_PRIMARY(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_PlayPrimary.png"),
	REFRESH(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Refresh.png"),
	SEARCH_ALTERNATIVE(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_SearchAlternative.png"),
	SEARCH_PRIMARY(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_SearchPrimary.png"),
	UPLOAD_ALTERNATIVE(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_UploadAlternative.png"),
	UPLOAD_PRIMARY(CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_UploadPrimary.png"),
	;

	private String		path;
	private Resource	resource;
	
	private EnumToolbarButton(String path)
	{
		this.path = path;
		this.resource = BbrThemeResourcesUtils.getInstance().getResource((BbrUI) UI.getCurrent(), path);
	}

	public String getPath()
	{
		return this.path;
	}

	public Resource getResource()
	{
		return this.resource;
	}
	
}
