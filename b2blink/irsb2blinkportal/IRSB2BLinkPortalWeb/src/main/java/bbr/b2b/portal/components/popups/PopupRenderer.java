package bbr.b2b.portal.components.popups;

import java.io.File;
import java.io.Serializable;

import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.SelectedPopupUtils;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.PopUpReportDataDTO;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.components.basics.BbrUI;

public class PopupRenderer extends HorizontalLayout
{
	private static final long	serialVersionUID			= -3532410796186595625L;
	private static final String	IMAGE_CONTENT_DEFAULT_STYLE	= "bbr-overflow-auto override center-content";
	private static final String	BG_COLOR_DEFAULT_STYLE		= "bg-color-gray";

	private SelectedPopupUtils	selectedPopupUtils			= new SelectedPopupUtils();
	private BbrUI				bbrUI						= null;
	private Runnable			executeAccept				= null;

	public void setExecuteAccept(Runnable executeAccept)
	{
		this.executeAccept = executeAccept;
	}

	public PopupRenderer(BbrUI bbrUI, PopUpReportDataDTO popup)
	{
		this.buildLayout(bbrUI, popup);
	}

	public PopupRenderer(BbrUI bbrUI, PopUpReportDataDTO popup, Runnable executeAccept)
	{
		this.setExecuteAccept(executeAccept);
		this.buildLayout(bbrUI, popup);
	}

	private void buildLayout(BbrUI bbrUI, PopUpReportDataDTO popup)
	{
		this.bbrUI = bbrUI;
		this.selectedPopupUtils.setNotification(popup);
		CKEditorConfig config = new CKEditorConfig();
		config.useCompactTags();
		config.disableElementsPath();
		config.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
		config.disableSpellChecker();
		config.setWidth("100%");
		config.setReadOnly(true);

		Label lbl_Date = new Label(BbrDateUtils.getInstance().toShortDate(popup.getPublishingdate()));
		lbl_Date.setWidth("100%");
		Label lbl_Title = new Label(popup.getTitle());
		lbl_Title.setWidth("100%");

		HorizontalLayout pnl_NewsTitle = new HorizontalLayout(lbl_Title);
		pnl_NewsTitle.setWidth("100%");
		pnl_NewsTitle.setExpandRatio(lbl_Title, 1F);

		HorizontalLayout pnl_NewsDate = new HorizontalLayout(lbl_Date);
		pnl_NewsDate.setWidth("100%");
		pnl_NewsDate.setExpandRatio(lbl_Date, 1F);

		Label lbl_Content = new Label(popup.getHtmlcontent());
		lbl_Content.setContentMode(ContentMode.HTML);

		final CKEditorTextField ckEditorTextField = new CKEditorTextField(config);
		ckEditorTextField.setViewWithoutEditor(true);
		ckEditorTextField.setValue(popup.getHtmlcontent());
		ckEditorTextField.setSizeUndefined();

		VerticalLayout mainLayout = new VerticalLayout();
		if (this.selectedPopupUtils.isPopupTemplate1())
		{
			mainLayout.addComponents(pnl_NewsTitle, pnl_NewsDate);
			mainLayout.addComponent(ckEditorTextField);
			mainLayout.setExpandRatio(ckEditorTextField, 1F);
		}
		else if (this.selectedPopupUtils.isPopupTemplateImage())
		{
			Image image = this.getImageFromHtmlcontent(popup.getHtmlcontent());
			if (image != null && this.selectedPopupUtils.isValidHtmlPathForImage())
			{
				if (this.selectedPopupUtils.isLinkAvailable())
				{
					BrowserWindowOpener opener = new BrowserWindowOpener(new ExternalResource(popup.getLink()));
					opener.setWindowName("_blank");
					opener.extend(image);
				}
				HorizontalLayout imageContentContainer = new HorizontalLayout();
				imageContentContainer.setStyleName("center-content");
				imageContentContainer.addComponent(image);
				imageContentContainer.setSizeFull();

				mainLayout.addComponent(imageContentContainer);
				mainLayout.setExpandRatio(imageContentContainer, 0.9F);
				if (this.selectedPopupUtils.isPopupTypeAudit())
				{
					Label accept = new Label(I18NManager.getI18NString(this.bbrUI, BbrUtilsResources.RES_GENERIC, "i_agree"));

					Button btn_accept = new Button(I18NManager.getI18NString(this.bbrUI, BbrUtilsResources.RES_GENERIC, "accept"));
					btn_accept.addStyleName("btn-generic");
					btn_accept.setHeight("24px");
					btn_accept.setEnabled(false);

					Runnable execute = this.executeAccept;
					if (execute != null)
					{
						btn_accept.addClickListener((ClickListener & Serializable) e -> execute.run());
					}

					CheckBox check = new CheckBox();
					check.addValueChangeListener((ValueChangeListener<Boolean> & Serializable) e ->
					{
						btn_accept.setEnabled(e.getValue());
					});

					HorizontalLayout checkAcceptContainer = new HorizontalLayout(check, accept, btn_accept);
					checkAcceptContainer.setComponentAlignment(check, Alignment.MIDDLE_LEFT);
					checkAcceptContainer.setComponentAlignment(accept, Alignment.MIDDLE_LEFT);
					checkAcceptContainer.setComponentAlignment(btn_accept, Alignment.MIDDLE_LEFT);
					checkAcceptContainer.addStyleName("bbr-margin-windows-zero-top-bottom");
					checkAcceptContainer.setHeight("35px");

					mainLayout.addComponents(checkAcceptContainer);
					mainLayout.setComponentAlignment(checkAcceptContainer, Alignment.BOTTOM_LEFT);
					mainLayout.setExpandRatio(checkAcceptContainer, 0.07F);
				}
			}
			else
			{
				HorizontalLayout empty = setPreviewDefaultImage();
				mainLayout.addComponents(empty);
				mainLayout.setExpandRatio(empty, 1F);
			}
		}

		lbl_Date.addStyleName("walldiary-news-date");
		lbl_Title.addStyleName("walldiary-news-title");
		lbl_Content.addStyleName("walldiary-news-content");
		mainLayout.addStyleName("walldiary-news");
		mainLayout.setSpacing(false);
		mainLayout.setMargin(false);
		mainLayout.setWidth("100%");
		this.setSizeFull();
		this.addComponents(mainLayout);
		this.setExpandRatio(mainLayout, 1F);
		this.setMargin(false);
	}

	private HorizontalLayout setPreviewDefaultImage()
	{
		HorizontalLayout result = new HorizontalLayout();
		Resource res = BbrThemeResourcesUtils.getInstance().getResource(this.bbrUI, BbrUtilsResources.PATH_BASE_IMAGES_BASIC + "default_insert_image.png");
		Image image = new Image(null, res);
		result.addComponent(image);
		result.setStyleName(IMAGE_CONTENT_DEFAULT_STYLE);
		result.addStyleName(BG_COLOR_DEFAULT_STYLE);
		return result;
	}

	private Image getImageFromHtmlcontent(String htmlcontent)
	{
		Image image = null;
		if (htmlcontent != null && htmlcontent.length() > 0)
		{
			File res = new File(htmlcontent);
			Resource source = new FileResource(res);
			image = new Image(null, source);
		}
		return image;
	}
}
