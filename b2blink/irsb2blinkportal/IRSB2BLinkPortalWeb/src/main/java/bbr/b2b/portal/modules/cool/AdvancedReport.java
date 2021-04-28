package bbr.b2b.portal.modules.cool;

import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.logi.utils.LogiUtils;
import bbr.b2b.portal.classes.logi.wrappers.SecurityData;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;

public class AdvancedReport extends BbrModule
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long serialVersionUID = 4982315246309578782L;
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public AdvancedReport(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================
	@Override
	public void initializeView()
	{
		BbrUser user = this.getUser();
		String url = this.getSecuredFrameUrl();

		if (user != null && url != null)
		{
			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.addStyleName("report-layout-no-filter");
			mainLayout.setSizeFull();
			mainLayout.setMargin(false);
			BrowserFrame iframe = BbrUtils.getInstance().getExternalIFrame(url);
			mainLayout.addComponents(iframe);
			mainLayout.setExpandRatio(iframe, 1F);

			this.addComponents(mainLayout);
		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0016"));
		}
	}

	private String getSecuredFrameUrl()
	{
		String url = AppUtils.getInstance().getFullServerContextPath(this.getBbrUIParent(), BbrAppConstants.LOGI_PATH);
		System.out.println("LTO- FullServerContextPath:" + url);

		SecurityData securityData = new SecurityData();
		securityData.setBaseURL(url);
		securityData.setUsername(this.getBbrUIParent().getUser().getFullName());

		String securityKey = LogiUtils.getInstance().getSecurityKey(securityData, this.getBbrUIParent());
		securityData.setSecurityKey(securityKey);
		System.out.println("LTO- SecurityKey:" + securityKey);

		String reportName = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_EXTERNAL_CODES, this.getModuleCode());

		return LogiUtils.getInstance().getReportURL(securityData, reportName);
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
