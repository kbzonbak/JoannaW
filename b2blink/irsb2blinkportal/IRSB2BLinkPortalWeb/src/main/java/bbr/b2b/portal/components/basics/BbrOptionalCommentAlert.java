package bbr.b2b.portal.components.basics;

import java.io.Serializable;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.basics.BbrOptionalCommentAlertDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class BbrOptionalCommentAlert extends BbrWindow
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long				serialVersionUID				= 7854654986887408058L;
	private static final String				STYLE_LABEL_SURE				= "v-caption";
	private TextArea						txtArea_Comment					= null;
	private Label							lbl_AvailableCharacters			= null;
	private BbrOptionalCommentAlertBuilder	bbrOptionalCommentAlertBuilder	= null;
	private Button							primaryButton					= null;
	public static final String				BTN_ACCEPT						= "btnAccept";
	public static final String				BTN_CANCEL						= "btnCancel";

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================
	private BbrOptionalCommentAlert(BbrOptionalCommentAlertBuilder bbrOptionalCommentAlertBuilder)
	{
		super(bbrOptionalCommentAlertBuilder.bbrUIParent);
		this.bbrOptionalCommentAlertBuilder = bbrOptionalCommentAlertBuilder;
		this.initializeView();
	}

	@Override
	public void initializeView()
	{
		HorizontalLayout cmp_Message = this.getMessageSection();

		VerticalLayout cmp_Comment = this.getCommentSection();

		BbrButtonGroup bbrGroupButton = new BbrButtonGroup.BbrGroupButtonBuilder(this.getBbrUIParent())
				.withPrimaryButtonLabel(this.bbrOptionalCommentAlertBuilder.primaryButtonCaption)
				.withPrimaryButtonListener((ClickListener & Serializable) (e) -> this.btn_Accept_clickHandler(this.bbrOptionalCommentAlertBuilder.isValidateAccept, this.bbrOptionalCommentAlertBuilder.isAcceptAndClose))
				.withCancelButtonLabel(this.bbrOptionalCommentAlertBuilder.cancelButtonCaption)
				.withCancelButtonListener((ClickListener & Serializable) (e) -> this.btn_Cancel_clickHandler(this.bbrOptionalCommentAlertBuilder.isValidateCancel)).build();
		primaryButton = bbrGroupButton.getPrimaryButton();
		primaryButton.setEnabled(false);

		VerticalLayout mainLayout = new VerticalLayout();
		if (this.bbrOptionalCommentAlertBuilder.isShowWishContinue)
		{
			Label lbl_Sure = new Label(this.bbrOptionalCommentAlertBuilder.wishContinueText);
			lbl_Sure.setStyleName(STYLE_LABEL_SURE);
			mainLayout.addComponents(cmp_Message, cmp_Comment, lbl_Sure, bbrGroupButton);
			mainLayout.setExpandRatio(lbl_Sure, 0.2F);
			mainLayout.setExpandRatio(cmp_Message, 0.6F);
			mainLayout.setExpandRatio(bbrGroupButton, 0.3F);
			mainLayout.setExpandRatio(cmp_Comment, 0.9F);
			mainLayout.setComponentAlignment(lbl_Sure, Alignment.BOTTOM_CENTER);
		}
		else
		{
			mainLayout.addComponents(cmp_Message, cmp_Comment, bbrGroupButton);
			mainLayout.setExpandRatio(cmp_Message, 0.8F);
			mainLayout.setExpandRatio(cmp_Comment, 1F);
			mainLayout.setExpandRatio(bbrGroupButton, 0.3F);
		}
		mainLayout.setComponentAlignment(bbrGroupButton, Alignment.MIDDLE_CENTER);
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.addStyleName(this.bbrOptionalCommentAlertBuilder.styleMainLayout);
		this.setWidth(this.bbrOptionalCommentAlertBuilder.widthWindow);
		this.setHeight(this.bbrOptionalCommentAlertBuilder.heightWindow);
		this.setResizable(this.bbrOptionalCommentAlertBuilder.isResizable);
		this.setCaption(this.bbrOptionalCommentAlertBuilder.title);
		this.setContent(mainLayout);

	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================
	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void btn_Accept_clickHandler(boolean hasValidateAccept, boolean isAcceptAndClose)
	{
		if (hasValidateAccept)
		{
			this.getEvent(BTN_ACCEPT);
		}
		else if (isAcceptAndClose)
		{
			this.getEvent(BTN_ACCEPT);
			this.close();
		}
		else
		{
			this.close();
		}
	}

	private void btn_Cancel_clickHandler(boolean hasValidateCancel)
	{
		if (hasValidateCancel)
		{
			this.getEvent(BTN_CANCEL);
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
		BbrOptionalCommentAlertDTO initParam = this.getInitParam();
		alertResponseEvent.setResultObject(initParam);
		this.dispatchBbrEvent(alertResponseEvent);
	}

	private BbrOptionalCommentAlertDTO getInitParam()
	{
		BbrOptionalCommentAlertDTO optionalCommentAlert = new BbrOptionalCommentAlertDTO();
		// por defecto en caso de no existir data se asigna el id -1L
		optionalCommentAlert.setData((this.bbrOptionalCommentAlertBuilder.data != null) ? this.bbrOptionalCommentAlertBuilder.data : -1L);
		optionalCommentAlert.setComment((this.txtArea_Comment.getValue() != null) ? this.txtArea_Comment.getValue().trim() : "");

		return optionalCommentAlert;
	}

	private HorizontalLayout getMessageSection()
	{
		Label lbl_Message = new Label(this.bbrOptionalCommentAlertBuilder.message);
		lbl_Message.setWidth("98%");
		lbl_Message.setContentMode(ContentMode.HTML);

		HorizontalLayout result = new HorizontalLayout(lbl_Message);
		result.setExpandRatio(lbl_Message, 1);
		result.setSizeFull();
		result.setMargin(false);
		result.setSpacing(false);
		return result;
	}

	private void txt_ValueChangeEvenHandler(ValueChangeEvent<String> e)
	{
		if (this.txtArea_Comment.getValue().trim().equals(""))
		{
			this.primaryButton.setEnabled(false);
		}
		else
		{
			this.primaryButton.setEnabled(true);
		}
		this.lbl_AvailableCharacters.setValue(
				this.bbrOptionalCommentAlertBuilder.availableCharacters + (this.bbrOptionalCommentAlertBuilder.maxLengthTextArea - this.txtArea_Comment.getValue().length()));
	}

	private VerticalLayout getCommentSection()
	{
		this.txtArea_Comment = new TextArea();
		this.txtArea_Comment.setMaxLength(this.bbrOptionalCommentAlertBuilder.maxLengthTextArea);
		this.txtArea_Comment.setPlaceholder(this.bbrOptionalCommentAlertBuilder.commentPlaceholder);
		this.txtArea_Comment.setHeight(this.bbrOptionalCommentAlertBuilder.commentHeight);
		this.txtArea_Comment.setWidth(this.bbrOptionalCommentAlertBuilder.commentWidth);
		this.txtArea_Comment.addValueChangeListener((ValueChangeListener<String>) e -> this.txt_ValueChangeEvenHandler(e));
		this.txtArea_Comment.focus();

		this.lbl_AvailableCharacters = new Label(this.bbrOptionalCommentAlertBuilder.availableCharacters + (this.bbrOptionalCommentAlertBuilder.maxLengthTextArea));

		VerticalLayout pnlComments = new VerticalLayout(this.txtArea_Comment, this.lbl_AvailableCharacters);
		pnlComments.setComponentAlignment(this.lbl_AvailableCharacters, Alignment.TOP_RIGHT);
		pnlComments.setWidth(this.bbrOptionalCommentAlertBuilder.commentWidth);
		pnlComments.setMargin(false);
		pnlComments.setSpacing(false);
		return pnlComments;
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

	public static class BbrOptionalCommentAlertBuilder
	{
		private BbrUI	bbrUIParent				= null;
		private String	message					= null;
		private Object	data					= null;
		private String	title					= null;
		private String	commentHeight			= "100px";
		private String	commentWidth			= "100%";
		private String	heightWindow			= "370px";
		private String	widthWindow				= "500px";
		private String	styleMainLayout			= "bbr-margin-windows";
		private int		maxLengthTextArea		= 255;
		private boolean	isValidateCancel		= false;
		private boolean	isValidateAccept		= false;
		private boolean	isAcceptAndClose		= false;
		private boolean	isResizable				= false;
		private boolean	isShowWishContinue		= true;
		private String	primaryButtonCaption	= null;
		private String	cancelButtonCaption		= null;
		private String	commentPlaceholder		= null;
		private String	availableCharacters		= null;
		private String	wishContinueText		= null;

		public BbrOptionalCommentAlertBuilder withCommentPlaceholder(String commentPlaceholder)
		{
			this.commentPlaceholder = commentPlaceholder;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withStyleMainLayout(String styleMainLayout)
		{
			this.styleMainLayout = styleMainLayout;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withCancelButtonCaption(String cancelButtonCaption)
		{
			this.cancelButtonCaption = cancelButtonCaption;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withPrimaryButtonCaption(String primaryButtonCaption)
		{
			this.primaryButtonCaption = primaryButtonCaption;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withMessage(String message)
		{
			this.message = message;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withData(Object data)
		{
			this.data = data;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withTitle(String title)
		{
			this.title = title;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withIsAcceptAndClose(boolean isAcceptAndClose)
		{
			this.isAcceptAndClose = isAcceptAndClose;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withIsValidateAccept(boolean isValidateAccept)
		{
			this.isValidateAccept = isValidateAccept;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withIsValidateCancel(boolean isValidateCancel)
		{
			this.isValidateCancel = isValidateCancel;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withMaxLengthTextArea(int maxLengthTextArea)
		{
			this.maxLengthTextArea = maxLengthTextArea;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withCommentHeight(String commentHeight)
		{
			this.commentHeight = commentHeight;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withCommentWidth(String commentWidth)
		{
			this.commentWidth = commentWidth;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withWidthWindow(String widthWindow)
		{
			this.widthWindow = widthWindow;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withHeightWindow(String heightWindow)
		{
			this.heightWindow = heightWindow;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withIsResizable(boolean isResizable)
		{
			this.isResizable = isResizable;
			return this;
		}

		public BbrOptionalCommentAlertBuilder withIsShowWishContinue(boolean isShowWishContinue)
		{
			this.isShowWishContinue = isShowWishContinue;
			return this;
		}

		public BbrOptionalCommentAlertBuilder(BbrUI bbrUIParent)
		{
			this.bbrUIParent = bbrUIParent;

			primaryButtonCaption = I18NManager.getI18NString(this.bbrUIParent, BbrUtilsResources.RES_GENERIC, "accept");
			cancelButtonCaption = I18NManager.getI18NString(this.bbrUIParent, BbrUtilsResources.RES_GENERIC, "cancel");
			commentPlaceholder = I18NManager.getI18NString(this.bbrUIParent, BbrUtilsResources.RES_GENERIC, "add_comment_optional");
			availableCharacters = I18NManager.getI18NString(this.bbrUIParent, BbrUtilsResources.RES_GENERIC, "available_characters");
			wishContinueText = I18NManager.getI18NString(this.bbrUIParent, BbrUtilsResources.RES_GENERIC, "wish_continue");
		}

		public BbrOptionalCommentAlert build()
		{
			return new BbrOptionalCommentAlert(this);
		}

	}

}
