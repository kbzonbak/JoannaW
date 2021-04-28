package bbr.b2b.portal.components.walldiary;

import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.TextEditorUtil;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.FaqDTO;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class FaqDetails extends BbrWindow
{
	private static final long	serialVersionUID	= -3532410796186595625L;

	private FaqDTO				selectedFaq			= null;

	public FaqDetails(BbrUI parent, FaqDTO selectedFaq)
	{
		super(parent);
		this.selectedFaq = selectedFaq;
	}

	@Override
	public void initializeView()
	{
		Label lbl_Date = new Label(BbrDateUtils.getInstance().toShortDate(selectedFaq.getLastchange()));
		lbl_Date.setWidth("100%");

		Label lbl_Title = new Label(selectedFaq.getQuestion());
		lbl_Title.setWidth("100%");

		HorizontalLayout pnl_NewsTitle = new HorizontalLayout(lbl_Title);
		pnl_NewsTitle.setWidth("100%");
		pnl_NewsTitle.setExpandRatio(lbl_Title, 1F);

		HorizontalLayout pnl_NewsDate = new HorizontalLayout(lbl_Date);
		pnl_NewsDate.setWidth("100%");
		pnl_NewsDate.setExpandRatio(lbl_Date, 1F);

		final CKEditorTextField ckEditorTextField = new CKEditorTextField(TextEditorUtil.getInstance().getCKEditorConfig());
		ckEditorTextField.setViewWithoutEditor(true);
		ckEditorTextField.setValue(selectedFaq.getAnswer());
		ckEditorTextField.setHeight(null);

		lbl_Date.addStyleName("walldiary-news-date");
		lbl_Title.addStyleName("walldiary-news-title");

		VerticalLayout textLayout = new VerticalLayout(ckEditorTextField);
		textLayout.setSizeFull();
		textLayout.addStyleName("bbr-overflow-auto");
		textLayout.setMargin(false);

		VerticalLayout mainLayout = new VerticalLayout(pnl_NewsTitle, pnl_NewsDate, textLayout);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(textLayout, 1F);
		mainLayout.addStyleName("bbr-margin-windows");
		mainLayout.setSpacing(false);

		// Ventana
		this.setWidth(80, Unit.PERCENTAGE);
		this.setHeight(550, Unit.PIXELS);
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "answer"));
		this.setContent(mainLayout);
	}
}
