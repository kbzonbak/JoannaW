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

import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.PositionDTO;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;

public class BbrInfoMessageList extends BbrWindow
{

	private static final long	serialVersionUID	= 2181291225471782983L;
	private static final String	NAME				= "name";
	String						firstMessage		= null;
	String						secondMessage		= null;
	String						thirdMessage		= null;
	String						fourMessage			= null;
	PositionDTO[]				provider			= null;
	Button						btn_Send			= null;
	Runnable					buttonAction		= null;

	public BbrInfoMessageList(BbrUI parent, PositionDTO[] provider, String firstMessage, String secondMessage, String thirdMessage, String fourMessage, Runnable buttonAction)
	{
		super(parent);
		this.firstMessage = firstMessage;
		this.secondMessage = secondMessage;
		this.thirdMessage = thirdMessage;
		this.fourMessage = fourMessage;
		this.provider = provider;
		this.buttonAction = buttonAction;

	}

	@Override
	public void initializeView()
	{
		HorizontalLayout cmp_FirstCaption = this.getCaptionSection(this.firstMessage, "60px");
		HorizontalLayout cmp_SecondCaption = this.getCaptionSection(this.secondMessage, "30px");
		HorizontalLayout cmp_ThirdCaption = this.getCaptionSection(this.thirdMessage, "30px", EnumToolbarButton.ADD_ALTERNATIVE.getResource());
		HorizontalLayout cmp_FourCaption = this.getCaptionSection(this.fourMessage, "30px", EnumToolbarButton.EDIT_ALTERNATIVE.getResource());

		BbrAdvancedGrid<PositionDTO> dgd_positions = new BbrAdvancedGrid<>(this.getLocale());
		dgd_positions.setWidth("400px");
		dgd_positions.addStyleName("report-grid");
		dgd_positions.setSelectionMode(SelectionMode.NONE);
		dgd_positions.setHeaderRowHeight(38D);
		dgd_positions.addCustomColumn(PositionDTO::getName, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_pending_position"), true)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setSortable(false)
				.setId(NAME);

		ListDataProvider<PositionDTO> dataprovider;
		if (this.provider != null && this.provider.length > 0)
		{
			dataprovider = new ListDataProvider<>(Arrays.asList(this.provider));
		}
		else
		{
			dataprovider = new ListDataProvider<>(Arrays.asList(new PositionDTO[0]));
		}
		dgd_positions.setDataProvider(dataprovider);

		this.btn_Send = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"));
		this.btn_Send.addStyleName("btn-generic");
		this.btn_Send.setWidth("120px");

		this.btn_Send.addClickListener((ClickListener & Serializable) (e) -> this.runAction(this.buttonAction));

		// Vertical Layout for Components
		VerticalLayout mainLayout = new VerticalLayout(cmp_FirstCaption,
				dgd_positions,
				cmp_SecondCaption,
				cmp_ThirdCaption,
				cmp_FourCaption,
				this.btn_Send);
		mainLayout.setComponentAlignment(cmp_FirstCaption, Alignment.MIDDLE_LEFT);
		mainLayout.setComponentAlignment(cmp_SecondCaption, Alignment.BOTTOM_LEFT);
		mainLayout.setComponentAlignment(cmp_ThirdCaption, Alignment.BOTTOM_LEFT);
		mainLayout.setComponentAlignment(cmp_FourCaption, Alignment.BOTTOM_LEFT);
		mainLayout.setComponentAlignment(dgd_positions, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(this.btn_Send, Alignment.MIDDLE_CENTER);
		mainLayout.setExpandRatio(dgd_positions, 1F);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(false);
		mainLayout.setSizeFull();
		mainLayout.addStyleName("bbr-win-container");

		// Main Windows
		this.setWidth("590px");
		this.setHeight("385px");
		this.setResizable(false);
		this.setClosable(false);
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
