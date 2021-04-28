package bbr.b2b.portal.components.walldiary;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.users.report.classes.PublishingReportDataDTO;
import bbr.b2b.users.report.classes.PublishingReportResultDTO;
import cl.bbr.core.components.basics.BbrTabContent;
import cl.bbr.core.components.basics.BbrUI;

public class TicketTabContent extends BbrTabContent 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = -696185086938854625L;
	
//	private BbrUser bbrUser = null;
//
//	private BbrAdvancedGrid<PublishingReportDataDTO> dgd_Procedures ;

	private boolean		isProcedure		= true;
	private String	procedureType;
	private BbrUI			bbrUI			= null;
	public String getProcedureType() 
	{
		return procedureType;
	}

	public void setProcedureType(String procedureType) 
	{
		this.procedureType = procedureType;
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	public TicketTabContent(String procedureType, BbrUI bbrUI) 
	{
		this(procedureType, bbrUI,false);
	}

	public TicketTabContent(String procedureType, BbrUI bbrUI, Boolean closeable) 
	{
		super(closeable);
		this.bbrUI = bbrUI;
		this.procedureType = procedureType ;
		this.buildTabContent();
	}

	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************

	
	
	
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************
	
	// ****************************************************************************************
	// ENDING SECTION 		---->			INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************

	
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
	// ****************************************************************************************
	
	// ****************************************************************************************
	// ENDING SECTION 		---->			OVERRIDDEN METHODS
	// ****************************************************************************************

	
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION 		---->			EVENTS HANDLERS
	// ****************************************************************************************
	
	 
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION 		---->			PUBLIC METHODS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PRIVATE METHODS
	// ****************************************************************************************
	private void buildTabContent()
	{
		if(this.procedureType != null)
		{
			this.setHeight("100%");
			this.addComponent(this.getTable());	
		}
		
	}
	
	private Component  getTable()
	{
		VerticalLayout result = new VerticalLayout();
		result.setId("tabContent");
		result.setHeight(null);
		result.setMargin(false);
		try
		{
			PublishingReportResultDTO procedureResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.bbrUI).getActivePublishingsByUserAndType(this.bbrUI.getUser().getId(), this.procedureType,this.isProcedure);
			if (procedureResult != null && procedureResult.getPublishingdata() != null && procedureResult.getPublishingdata().length > 0)
			{
				for (PublishingReportDataDTO item : procedureResult.getPublishingdata())
				{
					Component procedure = new ProcedureRenderer(item);
					result.addComponent(procedure);
				}

			}
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
	// ****************************************************************************************
	// ENDING SECTION 		---->			PRIVATE METHODS
	// ****************************************************************************************

}
