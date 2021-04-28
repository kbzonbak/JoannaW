package bbr.b2b.portal.components.walldiary;

import com.vaadin.ui.VerticalLayout;

public class UserManualPanel extends VerticalLayout 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = 7606096933342509963L;
	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	

	public UserManualPanel()
	{
		initializeControl();
	}
	
	private void initializeControl()
	{
		//Table table 				= this.getTable();
		//this.addComponent(table);
	}

//	private Table getTable()
//	{
//		Table result = new Table();
//		
//		result.addStyleName("table-last-load-date");
//		result.addStyleName(ValoTheme.TABLE_COMPACT);
//		result.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
//		result.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
//		result.addStyleName(ValoTheme.TABLE_SMALL);
//		result.setSortEnabled(false);
//		result.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
//		result.setSizeFull();
////		result.addGeneratedColumn("colLink", new Table.ColumnGenerator() 
////		{
////			private static final long serialVersionUID = 3051296881155979749L;
////			@Override
////			public Object generateCell(Table source, Object itemId, Object columnId) 
////			{
////				LastLoadProcessDateDTO currentDate = (LastLoadProcessDateDTO) itemId;
////				Button linkButton = new Button(currentDate.getProcess());
////				linkButton.addStyleName("link-button");
////				linkButton.setData(itemId);
////				linkButton.addClickListener(new Button.ClickListener() 
////				{
////					private static final long serialVersionUID = 7381858822185629654L;
////					@Override
////					public void buttonClick(ClickEvent event) 
////					{
////						if(event.getButton().getData() != null)
////						{
////							LastLoadProcessDateDTO selectedDate = (LastLoadProcessDateDTO) event.getButton().getData();
////							
////							Notification.show(selectedDate.getProcess());
////						}
////					}
////				});
////						
////				return linkButton;
////			}
////		});
////		
////		List<LastLoadProcessDateDTO> lastLoadDateDataSource = null ;
////		
////		LastLoadProcessDateDTO loadDate = new LastLoadProcessDateDTO();
////		loadDate.setDate("");
////		loadDate.setProcess("VENTAS - 12-12-2015");
////		
////		lastLoadDateDataSource = Arrays.asList(loadDate);
////		/*if (UI.getCurrent() instanceof BbrUI) 
////		{
////			SessionBean sessionBean = (SessionBean) ((BbrUI)UI.getCurrent()).getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
////			if(sessionBean != null)
////			{
////				FaqsResultDTO faqsResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getFaqQuestionsForUserProfiles(sessionBean.getUserId());
////				if(faqsResult != null && faqsResult.getFaqDTOs() != null && faqsResult.getFaqDTOs().length > 0)
////				{
////					lastLoadDateDataSource = Arrays.asList(faqsResult.getFaqDTOs());
////				}
////			}
////		}*/
////		
////		result.setContainerDataSource(new BeanItemContainer<LastLoadProcessDateDTO>(LastLoadProcessDateDTO.class, lastLoadDateDataSource));
////
////		result.setVisibleColumns("colLink");
////		
////		
//		return result;
//	}
}
