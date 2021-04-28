package bbr.b2b.portal.components.modules.management;

import java.io.Serializable;
import java.util.Set;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.UserTreeFunctionalityDTO;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class SelectFunctionality extends BbrWindow 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = 3247626779164695209L;

	private Tree<UserTreeFunctionalityDTO> dgdFunctionalities = null;
	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	public SelectFunctionality(BbrUI parent) 
	{
		super(parent);
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView() 
	{
		Panel pnlTree = new Panel(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "title_select_module"));
		pnlTree.setSizeFull();
		
		UserTreeFunctionalityDTO root = FunctionalityMngr.getInstance().getRootFunctionality();
		
		TreeData<UserTreeFunctionalityDTO> treeData = FunctionalityMngr.getInstance().getTreeData(root, null, null);
		TreeDataProvider<UserTreeFunctionalityDTO> dsFunctionalities = new TreeDataProvider<>(treeData);
		
		dgdFunctionalities = new Tree<>();
		dgdFunctionalities.setDataProvider(dsFunctionalities);
		dgdFunctionalities.setSizeFull();
		dgdFunctionalities.setItemCaptionGenerator(e -> e.getDescription());
		
		dgdFunctionalities.expand(root);

		//Buttons Layout
		Button btn_Select = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "select"));
		btn_Select.addClickListener((ClickListener & Serializable)e -> select_clickHandler(e));

		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.addClickListener(e->close());

		HorizontalLayout buttonsLayout = new HorizontalLayout(btn_Select, btn_Cancel);
		buttonsLayout.setWidth(100,Unit.PERCENTAGE);
		buttonsLayout.setComponentAlignment(btn_Select, Alignment.MIDDLE_LEFT);
		buttonsLayout.setComponentAlignment(btn_Cancel, Alignment.MIDDLE_RIGHT);
		buttonsLayout.addStyleName("buttons-layout");

		VerticalLayout layoutTree = new VerticalLayout(dgdFunctionalities);
		layoutTree.addStyleName("bbr-auto-scroll-layout");
		layoutTree.setHeight("100%");
		layoutTree.setWidth("100%");
		
		pnlTree.setContent(layoutTree);
		
		//Vertical Layout for Components
		VerticalLayout mainLayout = new VerticalLayout(pnlTree,buttonsLayout);
		mainLayout.setSpacing(true);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(pnlTree, 1F);
		mainLayout.addStyleName("form-vertical-container");
		mainLayout.addStyleName("bbr-win-container");

		//Main Windows
		this.setWidth(600,Unit.PIXELS);
		this.setHeight(450,Unit.PIXELS);

		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "title_select_module"));
		this.setContent(mainLayout);
	}


	// ****************************************************************************************
	// ENDING SECTION 		---->			OVERRIDDEN METHODS
	// ****************************************************************************************


	

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			EVENTS HANDLERS
	// ****************************************************************************************
	private void select_clickHandler(ClickEvent e) 
	{
		Set<UserTreeFunctionalityDTO> functionality =  dgdFunctionalities.getSelectedItems();
		if(functionality != null && functionality.size()>0)
		{
			BbrEvent event = new BbrEvent(BbrEvent.ITEM_SELECTED);
			event.setResultObject(functionality.iterator().next());
			dispatchBbrEvent(event);
			this.close();
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "module_required"));
		}	
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			EVENTS HANDLERS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PRIVATE METHODS
	// ****************************************************************************************
	
	// ****************************************************************************************
	// ENDING SECTION 		---->			PRIVATE METHODS
	// ****************************************************************************************



}
