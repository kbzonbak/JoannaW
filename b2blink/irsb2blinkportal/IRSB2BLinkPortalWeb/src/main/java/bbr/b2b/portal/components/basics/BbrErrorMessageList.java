package bbr.b2b.portal.components.basics;

import java.io.Serializable;
import java.util.Arrays;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;

public class BbrErrorMessageList extends BbrWindow
{

	private static final long	serialVersionUID	= 2181291225471782983L;
	private static final String	LINE			= "statuscode";
	private static final String	OBSERVATIONS			= "statusmessage";
	String				firstMessage		= null;
	BaseResultDTO[]		provider		= null;
	Button				btn_Send		= null;
	Runnable			buttonAction		= null;

	public BbrErrorMessageList(BbrUI parent, BaseResultDTO[] provider, String firstMessage)
	{
		super(parent);
		this.firstMessage = firstMessage;

		this.provider = provider;
		//this.buttonAction = buttonAction;

	}

	@Override
	public void initializeView()
	{
		HorizontalLayout cmp_FirstCaption = this.getCaptionSection(this.firstMessage, "60px");

		ListDataProvider<BaseResultDTO> dataprovider;
		if (this.provider != null && this.provider.length > 0)
		{
			dataprovider = new ListDataProvider<>(Arrays.asList(this.provider));
		}
		else
		{
			dataprovider = new ListDataProvider<>(Arrays.asList(new BaseResultDTO[0]));
		}
		
		
		
		BbrAdvancedGrid<BaseResultDTO> dgd_positions = new BbrAdvancedGrid<>(this.getLocale());
		dgd_positions.setDataProvider(dataprovider);
		dgd_positions.setWidth("550px");
		dgd_positions.addStyleName("report-grid");
		dgd_positions.setSelectionMode(SelectionMode.NONE);
		dgd_positions.setHeaderRowHeight(38D);
		dgd_positions.addCustomColumn(BaseResultDTO::getStatuscode, I18NManager.getI18NString(this.getBbrUIParent(),BbrUtilsResources.RES_MODULES_COMMERCIAL, "line"), true)
		.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setSortable(false).setId(LINE);
		dgd_positions.addCustomColumn(BaseResultDTO::getStatusmessage, I18NManager.getI18NString(this.getBbrUIParent(),BbrUtilsResources.RES_MODULES_COMMERCIAL, "observations"), true)
		.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setSortable(false).setId(OBSERVATIONS);

		this.btn_Send = new Button(I18NManager.getI18NString(this.getBbrUIParent(),BbrUtilsResources.RES_GENERIC, "accept"));
		this.btn_Send.addStyleName("btn-generic");
		this.btn_Send.setWidth("120px");
//		this.btn_Send.addStyleName("bbr-buttons-panel");
		this.btn_Send.addClickListener((ClickListener & Serializable) (e) -> this.runAction(this.buttonAction));

		// Vertical Layout for Components
		VerticalLayout mainLayout = new VerticalLayout(cmp_FirstCaption, dgd_positions, this.btn_Send);
		mainLayout.setComponentAlignment(cmp_FirstCaption, Alignment.MIDDLE_LEFT);
		mainLayout.setComponentAlignment(dgd_positions, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(this.btn_Send, Alignment.BOTTOM_CENTER);
		mainLayout.setExpandRatio(dgd_positions, 1F);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(false);
		mainLayout.setSizeFull();
		mainLayout.addStyleName("bbr-win-container");

		// Main Windows
		this.setWidth("610px");
		this.setHeight("390px");
		this.setResizable(false);
		this.setClosable(true);
		this.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"));
		this.setContent(mainLayout);
	}

	private HorizontalLayout getCaptionSection(String text, String heigth)
	{
		HorizontalLayout result = this.getCaptionSection(text, heigth, null);
		return result;
	}

	private HorizontalLayout getCaptionSection(String text, String heigth, Resource res)
	{
		HorizontalLayout result = new HorizontalLayout();
		if (res != null)
		{
			result.addComponent(new BbrHSeparator("10px"));
			Image img = new Image(null, res);
			img.setWidth("24px");
			img.setHeight("24px");
			result.addComponent(img);
		}

		Label lbl_Caption = new Label(text);
		lbl_Caption.setContentMode(ContentMode.HTML);
		lbl_Caption.setStyleName("v-caption");
		result.addComponent(lbl_Caption);
		result.setWidth("100%");
		result.setHeight(heigth);
		result.setMargin(false);
		result.setSpacing(true);
		result.setExpandRatio(lbl_Caption, 1F);
		return result;
	}

	public void runAction(Runnable buttonAction)
	{
		if (buttonAction != null)
		{
			buttonAction.run();
		}
		this.close();
	}
}
