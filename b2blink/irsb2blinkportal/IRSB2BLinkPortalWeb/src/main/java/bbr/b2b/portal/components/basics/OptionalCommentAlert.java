package bbr.b2b.portal.components.basics;

import java.io.Serializable;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.basics.OptionalCommentAlertDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class OptionalCommentAlert extends BbrWindow
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long	serialVersionUID		= 7854654986887408058L;
	private String				caption					= null;
	private TextArea			txtArea_Comment			= null;
	private Label				lbl_AvailableCharacters	= null;
	private Long				id						= null;
	private String				title					= null;
	private boolean				hasValidateCancel		= false;
	private int					maxLengthTextArea		= 255;
	private String				mainLayoutStyleName		= "bbr-margin-windows";
	private String				commentHeight			= "100px";
	private String				commentWidth			= "100%";
	private String				heightWindow			= null;
	private boolean				resizable				= false;
	public void setMaxLengthTextArea(int maxLengthTextArea)
	{
		this.maxLengthTextArea = maxLengthTextArea;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public OptionalCommentAlert(BbrUI parent, String title, String caption, Long id, boolean hasValidateCancel)
	{
		super(parent);
		this.caption = caption;
		this.id = id;
		this.title = title;
		this.hasValidateCancel = hasValidateCancel;
	}

	public OptionalCommentAlert(BbrUI parent, String title, String caption, Long id, boolean hasValidateCancel, boolean resizable)
	{
		super(parent);
		this.caption = caption;
		this.id = id;
		this.title = title;
		this.hasValidateCancel = hasValidateCancel;
		this.resizable = resizable;
	}

	public OptionalCommentAlert(BbrUI parent, String title, String caption, Long id, boolean hasValidateCancel, String heightContentLabel, String heightWindow)
	{
		super(parent);
		this.caption = caption;
		this.id = id;
		this.title = title;
		this.hasValidateCancel = hasValidateCancel;
		this.heightWindow = heightWindow;
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
		HorizontalLayout cmp_Caption = this.getCaptionSection();

		VerticalLayout cmp_Comment = this.getCommentSection();

		Label lbl_Sure = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "are_you_sure"));
		lbl_Sure.setStyleName("v-caption");

		BbrButtonGroup bbrGroupButton = new BbrButtonGroup.BbrGroupButtonBuilder(this.getBbrUIParent())
				.withPrimaryButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"))
				.withPrimaryButtonListener((ClickListener & Serializable) (e) -> this.btn_Accept_clickHandler())
				.withCancelButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"))
				.withCancelButtonListener((ClickListener & Serializable) (e) -> this.btn_Cancel_clickHandler(this.hasValidateCancel))
				.build();

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addComponents(cmp_Caption, cmp_Comment, lbl_Sure, bbrGroupButton);
		mainLayout.setExpandRatio(cmp_Caption, 0.6F);
		mainLayout.setExpandRatio(cmp_Comment, 0.9F);
		mainLayout.setExpandRatio(lbl_Sure, 0.2F);
		mainLayout.setExpandRatio(bbrGroupButton, 0.3F);
		mainLayout.setComponentAlignment(lbl_Sure, Alignment.BOTTOM_CENTER);
		mainLayout.setComponentAlignment(bbrGroupButton, Alignment.MIDDLE_CENTER);
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.addStyleName(mainLayoutStyleName);
		this.setWidth("500px");
		this.setHeight("370px");
		if (this.heightWindow != null && !this.heightWindow.equals(""))
		{
			this.setHeight(this.heightWindow);
		}
		this.setResizable(this.resizable);
		this.setCaption(this.title);
		this.setContent(mainLayout);

	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================
	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void btn_Accept_clickHandler()
	{
		this.getEvent(OptionalCommentAlertDTO.BTN_ACCEPT);
		this.close();
	}

	private void btn_Cancel_clickHandler(boolean hasValidateCancel)
	{
		if (hasValidateCancel)
		{
			this.getEvent(OptionalCommentAlertDTO.BTN_CANCEL);
		}
		else
		{
			this.close();
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void getEvent(String eventType)
	{
		BbrEvent alertResponseEvent = new BbrEvent(eventType);
		OptionalCommentAlertDTO initParam = this.getInitParam();
		alertResponseEvent.setResultObject(initParam);
		this.dispatchBbrEvent(alertResponseEvent);
	}

	private OptionalCommentAlertDTO getInitParam()
	{
		OptionalCommentAlertDTO optionalCommentAlert = new OptionalCommentAlertDTO();

		optionalCommentAlert.setId((this.id != null) ? this.id : -1L);

		optionalCommentAlert.setComment((this.txtArea_Comment.getValue() != null) ? this.txtArea_Comment.getValue().trim() : "");

		return optionalCommentAlert;
	}

	private HorizontalLayout getCaptionSection()
	{
		Label lbl_Caption = new Label(this.caption);
		lbl_Caption.setContentMode(ContentMode.HTML);
		lbl_Caption.setStyleName("v-caption");

		HorizontalLayout result = new HorizontalLayout(lbl_Caption);
		result.setSizeFull();
		result.setMargin(false);
		result.setSpacing(false);
		return result;
	}

	private void txt_ValueChangeEvenHandler(ValueChangeEvent<String> e)
	{
		this.lbl_AvailableCharacters.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "available_characters")
				+ (maxLengthTextArea - this.txtArea_Comment.getValue().length()));
	}

	private VerticalLayout getCommentSection()
	{
		this.txtArea_Comment = new TextArea();
		this.txtArea_Comment.setMaxLength(maxLengthTextArea);
		this.txtArea_Comment.setPlaceholder(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "add_comment_optional"));
		this.txtArea_Comment.setHeight(commentHeight);
		this.txtArea_Comment.setWidth(commentWidth);
		this.txtArea_Comment.addValueChangeListener((ValueChangeListener<String>) e -> this.txt_ValueChangeEvenHandler(e));

		this.lbl_AvailableCharacters = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "available_characters") + (maxLengthTextArea));

		VerticalLayout pnlComments = new VerticalLayout(this.txtArea_Comment, this.lbl_AvailableCharacters);
		pnlComments.setComponentAlignment(this.lbl_AvailableCharacters, Alignment.TOP_RIGHT);
		pnlComments.setWidth(commentWidth);
		pnlComments.setMargin(false);
		pnlComments.setSpacing(false);
		return pnlComments;
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
