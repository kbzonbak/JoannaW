package bbr.b2b.portal.components.walldiary;

import com.vaadin.ui.VerticalLayout;

public class ActiveUsersPanel extends VerticalLayout 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = 4475805477573182647L;

	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	public ActiveUsersPanel()
	{
		initializeControl();
	}
	
	private void initializeControl()
	{
//		HorizontalLayout toolBar 	= this.getToolBar();
//		Table table 				= this.getTable();
//		
//		this.addComponent(toolBar);
//		this.addComponent(table);
//		
//		this.setExpandRatio(table, 1);
	}

//	private Table getTable()
//	{
//		Table result = new Table();
//		
//		result.addStyleName("table-active-user");
//		result.addStyleName(ValoTheme.TABLE_COMPACT);
//		result.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
//		result.addStyleName(ValoTheme.TABLE_SMALL);
//		result.setSortEnabled(false);
//		result.setColumnAlignment("name", Align.LEFT);
//		result.setColumnAlignment("position", Align.LEFT);
//		result.setColumnAlignment("lastlogin", Align.CENTER);
//		result.setColumnHeaderMode(ColumnHeaderMode.EXPLICIT);
//		result.setSizeFull();
//		List<UserForNewsDTO> activeUsersDataSource = null ;
//		if (UI.getCurrent() instanceof BbrUI) 
//		{
//			SessionBean sessionBean = (SessionBean) ((BbrUI)UI.getCurrent()).getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
//			if(sessionBean != null)
//			{
//				try 
//				{
//					UserForNewsResultDTO activeUsersResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getUsersForNews(sessionBean.getUserId());
//					if(activeUsersResult != null && activeUsersResult.getUserForNewsWs() != null && activeUsersResult.getUserForNewsWs().length > 0)
//					{
//						activeUsersDataSource = Arrays.asList(activeUsersResult.getUserForNewsWs());
//					}
//				} 
//				catch (BbrUserException e) 
//				{
//					e.printStackTrace();
//				} 
//				catch (BbrSystemException e) 
//				{
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		result.setContainerDataSource(new BeanItemContainer<UserForNewsDTO>(UserForNewsDTO.class, activeUsersDataSource));
//		result.setConverter("lastlogin", new BbrShortDateConverter());
//
//		result.setVisibleColumns("name", "position", "lastlogin");
//		result.setColumnHeaders(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, "name"), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, "position"),I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, "last_login"));
//
//		
//		return result;
//	}
	
//	private HorizontalLayout getToolBar()
//	{
//		HorizontalLayout result = new HorizontalLayout();
//		result.addStyleName("table-others-news-toolbar");
//		result.setWidth("100%");
//
//		Label headerCaption = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, "active_users"));
//		headerCaption.addStyleName(ValoTheme.LABEL_H4);
//		headerCaption.addStyleName(ValoTheme.LABEL_COLORED);
//		headerCaption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
//	
//		MenuBar leftMenuBar = new MenuBar();
//		leftMenuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
//		
//		leftMenuBar.addItem("", VaadinIcons.USERS, null);
//
//		result.addComponents(leftMenuBar, headerCaption);
//		result.setExpandRatio(headerCaption, 1);
//		result.setComponentAlignment(leftMenuBar, Alignment.MIDDLE_LEFT);
//		result.setComponentAlignment(headerCaption, Alignment.MIDDLE_CENTER);
//		
//		return result;
//	}
	
}
