package bbr.b2b.portal.components.walldiary;

import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.users.report.classes.PublishingReportDataDTO;
import cl.bbr.core.classes.utilities.BbrDateUtils;

public class NewsRenderer extends HorizontalLayout 
{
	private static final long serialVersionUID = -3532410796186595625L;

	public NewsRenderer(PublishingReportDataDTO news) 
	{
		this.buildLayout(news);
	}

	private void buildLayout(PublishingReportDataDTO news) 
	{
		CKEditorConfig config = new CKEditorConfig();
		config.useCompactTags();
		config.disableElementsPath();
		config.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
		config.disableSpellChecker();
		config.setWidth("100%");
		config.setReadOnly(true);
        
		Label lbl_Date 	= new Label(BbrDateUtils.getInstance().toShortDate(news.getInitdate()));
		lbl_Date.setWidth("100%");
		Label lbl_Title = new Label(news.getTitle());
		lbl_Title.setWidth("100%");
		
		HorizontalLayout pnl_NewsTitle = new HorizontalLayout(lbl_Title);
		pnl_NewsTitle.setWidth("100%");
		pnl_NewsTitle.setExpandRatio(lbl_Title, 1F);
		
		HorizontalLayout pnl_NewsDate = new HorizontalLayout(lbl_Date);
		pnl_NewsDate.setWidth("100%");
		pnl_NewsDate.setExpandRatio(lbl_Date, 1F);
		
		Label lbl_Content = new Label(news.getHtmlcontent());
		lbl_Content.setContentMode(ContentMode.HTML);
		
		final CKEditorTextField ckEditorTextField = new CKEditorTextField(config);
		ckEditorTextField.setViewWithoutEditor(true);
		ckEditorTextField.setValue(news.getHtmlcontent());
		ckEditorTextField.setSizeUndefined();
		ckEditorTextField.addStyleName("walldiary-news-preferences");
		
		VerticalLayout mainLayout = new VerticalLayout(pnl_NewsTitle,pnl_NewsDate,ckEditorTextField);
		mainLayout.setExpandRatio(ckEditorTextField, 1F);
		
		lbl_Date.addStyleName("walldiary-news-date");
		lbl_Title.addStyleName("walldiary-news-title");
		
		mainLayout.addStyleName("walldiary-news");
		mainLayout.setSpacing(false);
		mainLayout.setMargin(false);
		this.setSizeFull();
		this.addComponents(mainLayout);
		this.setExpandRatio(mainLayout, 1F);
	}
}
