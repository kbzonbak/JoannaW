package bbr.b2b.portal.components.walldiary;

import com.vaadin.ui.VerticalLayout;

import cl.bbr.core.classes.basics.BbrUser;

public class OthersNewsPanel extends VerticalLayout 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = 422110660891507871L;
	//private BbrUser bbrUser = null;

	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	

	public OthersNewsPanel(BbrUser bbrUser)
	{
		//this.bbrUser = bbrUser;
		initializeControl();
	}
	
	private void initializeControl()
	{
//		Component toolBar			= this.getToolBar();
//		Table table 				= this.getTable();
//
//		this.addComponents(toolBar, table);
//		this.setExpandRatio(table, 1);

	}
	
//	private HorizontalLayout getToolBar()
//	{
//		HorizontalLayout result = new HorizontalLayout();
//		result.addStyleName("table-others-news-toolbar");
//		result.setWidth("100%");
//
//		Label headerCaption = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, "others_news"));
//		headerCaption.addStyleName(ValoTheme.LABEL_H4);
//		headerCaption.addStyleName(ValoTheme.LABEL_COLORED);
//		headerCaption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
//	
//		MenuBar leftMenuBar = new MenuBar();
//		leftMenuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
//		
//		leftMenuBar.addItem("", FontAwesome.NEWSPAPER_O, null);
//
//		result.addComponents(leftMenuBar, headerCaption);
//		result.setExpandRatio(headerCaption, 1);
//		result.setComponentAlignment(leftMenuBar, Alignment.MIDDLE_LEFT);
//		result.setComponentAlignment(headerCaption, Alignment.MIDDLE_CENTER);
//		
//		return result;
//	}
	
//	private Table getTable()
//	{
//		Table result = new Table();
//		
//		result.addStyleName("table-more_news");
//		result.addStyleName(ValoTheme.TABLE_COMPACT);
//		result.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
//		result.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
//		result.addStyleName(ValoTheme.TABLE_SMALL);
//		result.setSortEnabled(false);
//		result.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
//		result.setSizeFull();
//		result.addGeneratedColumn("colLink", new Table.ColumnGenerator() 
//		{
//			private static final long serialVersionUID = 3051296881155979749L;
//			@Override
//			public Object generateCell(Table source, Object itemId, Object columnId) 
//			{
//				PublishingReportDataDTO currentNews = (PublishingReportDataDTO) itemId;
//				Button linkButton = new Button(currentNews.getTitle());
//				linkButton.addStyleName("link-button");
//				linkButton.setIcon(FontAwesome.CHEVRON_RIGHT);
//				linkButton.setData(itemId);
//				linkButton.addClickListener(new Button.ClickListener() 
//				{
//					private static final long serialVersionUID = 7381858822185629654L;
//					@Override
//					public void buttonClick(ClickEvent event) 
//					{
//						if(event.getButton().getData() != null)
//						{
//							PublishingReportDataDTO selectedFaq = (PublishingReportDataDTO) event.getButton().getData();
//							
//							Notification.show(selectedFaq.getTitle());
//						}
//					}
//				});
//						
//				return linkButton;
//			}
//		});
//		
//		List<PublishingReportDataDTO> activePublishingDataSource = null ;
//		
//		try 
//		{
//			PublishingReportResultDTO activePublishingResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal().getActivePublishingsByUserAndType(this.bbrUser.getId(),BbrPublishingConstants.NOVELTIES_PUBLISHING_CODE);
//			if(activePublishingResult != null && activePublishingResult.getPublishingdata() != null && activePublishingResult.getPublishingdata().length > 0)
//			{
//				activePublishingDataSource = Arrays.asList(activePublishingResult.getPublishingdata());
//			}
//			
//			result.setContainerDataSource(new BeanItemContainer<PublishingReportDataDTO>(PublishingReportDataDTO.class, activePublishingDataSource));
//
//			result.setVisibleColumns("colLink");
//		} 
//		catch (BbrUserException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (BbrSystemException e) 
//		{
//			e.printStackTrace();
//		}
//
//		return result;
//	}
}
