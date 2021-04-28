package bbr.b2b.portal.components.walldiary;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.PublishingReportResultDTO;
import bbr.b2b.users.report.classes.PublishingTypeDTO;
import bbr.b2b.users.report.classes.PublishingTypeResultDTO;
import cl.bbr.core.components.basics.BbrTabContent;
import cl.bbr.core.components.basics.BbrTabSheet;
import cl.bbr.core.components.basics.BbrUI;

public class TicketsPanel extends VerticalLayout
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long	serialVersionUID	= 422110660891507871L;
	private BbrUI			bbrUI			= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private boolean isProcedure = true;

	public TicketsPanel(BbrUI bbrUI)
	{
		this.bbrUI = bbrUI;
		initializeControl();
	}

	private void initializeControl()
	{
		Component toolBar = this.getToolBar();
		BbrTabSheet table = this.getTicketsTabs();
		if (table != null)
		{
			this.addComponents(toolBar, table);
			this.setExpandRatio(table, 1);
		}
		//
		//
		// this.addLayoutClickListener((LayoutClickListener &
		// Serializable)e -> newsLayout_clickHandler(e));

	}

	// private void newsLayout_clickHandler(LayoutClickEvent e)
	// {
	// NewsBanner banner = new NewsBanner();
	// banner.initializeView();
	// banner.open(null, true);
	// }

	private HorizontalLayout getToolBar()
	{
		HorizontalLayout result = new HorizontalLayout();
		result.addStyleName("table-others-news-toolbar");
		result.setWidth("100%");

		Label headerCaption = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, "tickets"));
		headerCaption.addStyleName(ValoTheme.LABEL_H4);
		headerCaption.addStyleName(ValoTheme.LABEL_COLORED);
		headerCaption.addStyleName(ValoTheme.LABEL_NO_MARGIN);

		MenuBar leftMenuBar = new MenuBar();
		leftMenuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		leftMenuBar.addItem("", VaadinIcons.CHECK, null);

		result.addComponents(leftMenuBar, headerCaption);
		result.setExpandRatio(headerCaption, 1);
		result.setComponentAlignment(leftMenuBar, Alignment.MIDDLE_LEFT);
		result.setComponentAlignment(headerCaption, Alignment.MIDDLE_CENTER);

		return result;
	}

	private BbrTabSheet getTicketsTabs()
	{
		BbrTabSheet result = null;
		
		PublishingTypeResultDTO procedureResult = this.getProcedureTypes(); //trae todos los procedimientos activos con datos
		if (procedureResult != null && procedureResult.getPublishingtypes() != null && procedureResult.getPublishingtypes().length > 0)
		{
			result = new BbrTabSheet();
			for (PublishingTypeDTO publishingType : procedureResult.getPublishingtypes())
			{
				BbrTabContent ticketTab = new TicketTabContent(publishingType.getCode(), this.bbrUI);
				ticketTab.addStyleName("bbr-overflow-auto");

				result.addBbrTab(ticketTab, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, publishingType.getCode()).equals("M/R")
				? publishingType.getName() : I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, publishingType.getCode()));

			}
			result.addStyleName("wall-diary-tab-sheet");
			result.setHeight("100%");
			result.setSizeFull();
		}
		else
		{
			result = null;
		}
		return result;
	}

	private PublishingTypeResultDTO getProcedureTypes()
	{
		PublishingTypeResultDTO procedureTypes = null; //todos los procedimientos
		//la idea es ingresar los proceduretypes en result
		PublishingTypeResultDTO result = new PublishingTypeResultDTO();	//resultado 
		try
		{
			procedureTypes = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.bbrUI).getProcedureTypes();
			if (procedureTypes != null)
			{
				PublishingTypeDTO[] procedureType = procedureTypes.getPublishingtypes();
				List<PublishingTypeDTO> publishingTypes = new ArrayList<>();
				for (int i = 0; i < procedureType.length; i++)
				{
					PublishingReportResultDTO procedureResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.bbrUI).getActivePublishingsByUserAndType(this.bbrUI.getUser().getId(), procedureType[i].getCode().toString(), this.isProcedure);
					if (procedureResult != null && procedureResult.getPublishingdata() != null && procedureResult.getPublishingdata().length > 0)
					{
						//debe añadir un proceduretype valido
						publishingTypes.add(procedureType[i]);
					}
				}
				
				PublishingTypeDTO[] array = publishingTypes.toArray(new PublishingTypeDTO[publishingTypes.size()]);
				result.setPublishingtypes(array);
			}
			return result;//resultado
			
		}
		catch (BbrUserException ex)
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), this.bbrUI);
		}
		catch (BbrSystemException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
}
