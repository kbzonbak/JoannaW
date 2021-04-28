package bbr.b2b.portal.classes.ui;

import com.vaadin.addon.charts.shared.MouseEventDetails.MouseButton;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import bbr.b2b.users.report.classes.CompanyDataResultDTO;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.components.basics.BbrUI;

@Theme("irsb2blinkportalweb")
@Title("| MercadoLibre |")
public class MeliUI extends BbrUI
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long	serialVersionUID		= 5882724157372379105L;

	private String				verificationCode;
	private String				melicode				= null;
	private SessionBean			sessionBean				= null;
	private LayoutClickListener	redirect_clickHandler	= null;
	private String				message					= null;
	private CompanyDataDTO		lastProvider			= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

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
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	protected void init(VaadinRequest request)
	{
		if (request != null)
		{

			verificationCode = request.getParameter("code");
			if (verificationCode != null && verificationCode.length() > 0)
			{
				this.melicode = verificationCode;

				this.sessionBean = (SessionBean) this.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
				this.setSessionUserBean(sessionBean, BbrUtilsConstants.SESSION_BEAN_NAME);

				BaseResultDTO result = doProcess(this);

				// Header Layout
				final HorizontalLayout pnlHeader = new HorizontalLayout();
				pnlHeader.setWidth("100%");
				pnlHeader.setHeight("56px");
				pnlHeader.setStyleName("pnlHeader");
				pnlHeader.setId("pnlHeader");
				String res_logo = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "logo_header");
				Resource res = new ThemeResource(res_logo);
				Image image = new Image(null, res);
				image.setStyleName("logoHeader");
				pnlHeader.addComponents(image);				
				
				// Logo Layout
				final HorizontalLayout pnlLogo = new HorizontalLayout();
				pnlLogo.setWidth(100, Unit.PERCENTAGE);
				pnlLogo.setHeight(100, Unit.PIXELS);

				Resource resMeli = BbrThemeResourcesUtils.getInstance().getResource((BbrUI) UI.getCurrent(), "assets/images/modules/logo_mercadoLibre.png");
				Image imageMeli = new Image(null, resMeli);

				pnlLogo.addComponents(imageMeli);
				pnlLogo.setComponentAlignment(imageMeli, Alignment.MIDDLE_CENTER);
				pnlLogo.setId("pnlLogo");
				pnlLogo.setStyleName("mp-panelLogo");

				// Footer Layout
				final HorizontalLayout pnlFooter = new HorizontalLayout();
				pnlFooter.setId("pnlFooter");
				pnlFooter.setSizeFull();
				pnlFooter.setWidth(100, Unit.PERCENTAGE);
				pnlFooter.setHeight(25, Unit.PIXELS);
				pnlFooter.setStyleName("pnlFooter");
				Label lblFooter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "app_footer"));

				lblFooter.setWidthUndefined();
				pnlFooter.addComponent(lblFooter);
				pnlFooter.setComponentAlignment(lblFooter, Alignment.MIDDLE_CENTER);

				final HorizontalLayout pnlChangePassword = new HorizontalLayout();
				pnlChangePassword.setSizeFull();
				pnlChangePassword.setHeight(100, Unit.PIXELS);
				pnlChangePassword.setId("pnlChangePassword");				

				final HorizontalLayout redirect = new HorizontalLayout();
				redirect.setSizeFull();
				redirect.setHeight(100, Unit.PIXELS);
				redirect.setId("pnlRedirect");

				if (result != null)
				{

					// respuesta ws
					Label errorMsg = new Label(this.message);
					errorMsg.setContentMode(ContentMode.HTML);
					errorMsg.setSizeFull();
					VerticalLayout errorLayout = new VerticalLayout();
					errorLayout.setWidthUndefined();
					errorLayout.addComponents(errorMsg);
					errorLayout.setComponentAlignment(errorMsg, Alignment.MIDDLE_CENTER);
					errorLayout.setStyleName("mp-message-text");

					// redireccion2
					HorizontalLayout redirectLayout = new HorizontalLayout();
					redirectLayout.addComponent(new Label("Volver a página principal"));
					redirectLayout.addLayoutClickListener(e -> this.redirect_clickHandler(e));
					redirectLayout.addStyleName(BbrStyles.ACTION_BUTTON);
					redirectLayout.addStyleName("mp-btn-redirect");// TODO cambiar css por uno general

					pnlChangePassword.addComponent(errorLayout);
					pnlChangePassword.setComponentAlignment(errorLayout, Alignment.MIDDLE_CENTER);

					redirect.addComponent(redirectLayout);
					redirect.setComponentAlignment(redirectLayout, Alignment.MIDDLE_CENTER);

				}

				// Main Layout
				final VerticalLayout pnlMain = new VerticalLayout();
				pnlMain.setMargin(new MarginInfo(false, false, false, false));
				pnlMain.setSizeFull();
				pnlMain.setSpacing(false);
				pnlMain.addComponents(pnlHeader, pnlLogo, pnlChangePassword, redirect);
				pnlMain.setExpandRatio(pnlHeader, 1F);
				pnlMain.setExpandRatio(pnlLogo, 1F);
				pnlMain.setExpandRatio(pnlChangePassword, 1F);
				pnlMain.setExpandRatio(redirect, 1F);
				pnlMain.addComponent(pnlFooter);
				pnlMain.setId("pnlMain");
				pnlMain.setStyleName("mp-panel-main");

				// This BbrUI Content
				this.setContent(pnlMain);
			}
			else
			{

				String mainUrl = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "main");
				this.goToURL(mainUrl);
			}
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
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

	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private void redirect_clickHandler(LayoutClickEvent event)
	{
		if (event.getButton().name().equals(MouseButton.LEFT.name()))
		{
			String mainUrl = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "main");
			this.goToURL(mainUrl);
		}
	}

	private BaseResultDTO doProcess(BbrUI bbrUI)
	{
		BaseResultDTO result = null;
		{
			try
			{
				result = EJBFactory.getMarketPlaceEJBFactory().getMarketPlaceManagerLocal(bbrUI).doAuthenticateML(getLastProvider(), this.melicode, this.sessionBean.getUserId());
				if (result.getStatuscode().equals("200"))
				{
					this.message = this.lastProvider.getName() + "[" + this.lastProvider.getRut() + "] se ha autenticado exitosamente en MercadoLibre ";
				}
				else
				{
					this.message = this.lastProvider.getName() + "[" + this.lastProvider.getRut() + "] ha ocurrido un error en la autenticación \n " + result.getStatusmessage();
				}
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut(e.getMessage(), bbrUI);
			}
			catch (BbrSystemException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	private String getLastProvider()
	{
		String result = null;
		try
		{
			result = "";

			CompanyDataResultDTO providersResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(this).getMostUsedProvidersByUserId(this.sessionBean.getUser().getId());
			BbrError error = ErrorsMngr.getInstance().getError(providersResult, this, !this.hasAlertWindowOpen());

			if (!error.hasError() && providersResult != null)
			{
				CompanyDataDTO lastUsedCompany = providersResult.getLastUsedCompanyDataDTOs() != null ? providersResult.getLastUsedCompanyDataDTOs() : null;
				this.lastProvider = lastUsedCompany;
				result = lastUsedCompany.getRut();
			}
		}

		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this);
		}

		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}

		return result;

	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
}
