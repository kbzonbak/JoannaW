package bbr.b2b.portal.classes.utils.app;

import java.io.Serializable;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.files_transfer.BbrFileUploader;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class UploadMessageBox extends BbrWindow
{
	private static final long	serialVersionUID	= -2612186575702442971L;
	public final String			BTN_ACCEPT			= "btnaccept";
	public final String			BTN_CANCEL			= "btncancel";
	private String				btnUploadStyleName	= "v-button btn-generic";
	private String				btnCloseStyleName	= "btn-generic";
	private String				mainLayoutStyleName	= "bbr-margin-windows-10";
	private String				messageBoxHeight	= "200px";
	private String				messageBoxWidth		= "350px";
	private HorizontalLayout	contentLayout		= null;
	private HorizontalLayout	buttonLayout		= null;
	private String				title				= null;
	private String				caption				= null;
	private Upload				btn_Upload			= null;
	private BbrFileUploader		uploaderReceiver	= null;
	private Runnable			runBeforeAccept		= null;
	private boolean				hasValidateCancel	= false;
	private String				uploadButtonCaption	= null;
	private String				closeButtonCaption	= null;
	private String				path				= null;

	public void setRunBeforeAccept(Runnable runBeforeAccept)
	{
		this.runBeforeAccept = runBeforeAccept;
	}

	public void setMessageBoxHeight(String messageBoxHeight)
	{
		this.messageBoxHeight = messageBoxHeight;
	}

	public void setMessageBoxWidth(String messageBoxWidth)
	{
		this.messageBoxWidth = messageBoxWidth;
	}

	public void setUploadButtonCaption(String uploadButtonCaption)
	{
		this.uploadButtonCaption = uploadButtonCaption;
	}

	public void setCloseButtonCaption(String closeButtonCaption)
	{
		this.closeButtonCaption = closeButtonCaption;
	}

	public void setBtnUploadStyleName(String btnUploadStyleName)
	{
		this.btnUploadStyleName = btnUploadStyleName;
	}

	public void setHasValidateCancel(boolean hasValidateCancel)
	{
		this.hasValidateCancel = hasValidateCancel;
	}

	/***
	 * UploadMessageBox may set the path or use default by user
	 * @param path
	 */
	public void setPathToUpload(String path)
	{
		this.path = path;
	}

	public UploadMessageBox(BbrUI parentUI, String title, String caption)
	{
		super(parentUI);
		this.title = title;
		this.caption = caption;
	}

	@Override
	public void initializeView()
	{
		// Content message
		Label contentMessage = new Label(caption);
		contentMessage.setContentMode(ContentMode.HTML);
		contentMessage.setSizeFull();

		// Content Layout
		this.contentLayout = new HorizontalLayout();
		this.contentLayout.setMargin(false);
		this.contentLayout.setSpacing(true);
		this.contentLayout.setSizeFull();
		this.contentLayout.addComponent(contentMessage);
		this.contentLayout.setExpandRatio(contentMessage, 1F);
		this.contentLayout.setComponentAlignment(contentMessage, Alignment.MIDDLE_CENTER);

		// Upload Button
		this.uploaderReceiver = new BbrFileUploader(this.path != null ? this.path : BbrAppConstants.getUploadPathOfUser(this.getUser().getId()));
		this.btn_Upload = new Upload(null, this.uploaderReceiver);
		this.btn_Upload.addFinishedListener((FinishedListener & Serializable) (e) -> this.runAction(e, this.runBeforeAccept));
		this.btn_Upload.setWidth("100%");
		this.btn_Upload.setButtonStyleName(this.btnUploadStyleName);
		this.btn_Upload.setButtonCaption(this.uploadButtonCaption != null ? this.uploadButtonCaption : I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "ok"));

		// Close Button
		Button button = new Button(this.closeButtonCaption != null ? this.closeButtonCaption : I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "close"));
		button.addClickListener((ClickListener & Serializable) e -> this.btn_Cancel_clickHandler(this.hasValidateCancel));
		button.addStyleName(this.btnCloseStyleName);

		// Buttons Layout
		this.buttonLayout = new HorizontalLayout();
		this.buttonLayout.addComponent(this.btn_Upload);
		this.buttonLayout.addComponent(button);
		this.buttonLayout.setComponentAlignment(this.btn_Upload, Alignment.MIDDLE_RIGHT);
		this.buttonLayout.setComponentAlignment(button, Alignment.MIDDLE_LEFT);
		this.buttonLayout.setMargin(false);
		this.buttonLayout.setSpacing(true);
		this.buttonLayout.setHeight("30px");

		// Main layout
		VerticalLayout mainLayout = new VerticalLayout(this.contentLayout, this.buttonLayout);
		mainLayout.setExpandRatio(this.contentLayout, 1F);
		mainLayout.setComponentAlignment(this.contentLayout, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(this.buttonLayout, Alignment.BOTTOM_CENTER);
		mainLayout.setSizeFull();
		mainLayout.setMargin(true);
		mainLayout.addStyleName(this.mainLayoutStyleName);

		// this layout
		this.setContent(mainLayout);
		this.setCaption(this.title);
		this.setHeight(this.messageBoxHeight);
		this.setWidth(this.messageBoxWidth);
		this.setClosable(false);
		this.setModal(true);
		this.setResizable(false);
	}

	public void runAction(FinishedEvent e, Runnable buttonAction)
	{
		if (buttonAction != null)
		{
			buttonAction.run();
		}
		this.close();
		this.getEvent(e, BTN_ACCEPT);
	}

	private void btn_Cancel_clickHandler(boolean hasValidateCancel)
	{
		if (hasValidateCancel)
		{
			this.getEvent(null, BTN_CANCEL);
		}
		else
		{
			this.close();
		}
	}

	private void getEvent(FinishedEvent e, String eventType)
	{
		BbrEvent alertResponseEvent = new BbrEvent(eventType);
		if (e != null && eventType.equals(BTN_ACCEPT))
		{
			alertResponseEvent.setResultObject(e);
		}
		this.dispatchBbrEvent(alertResponseEvent);
	}
}
