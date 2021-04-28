package bbr.b2b.portal.components.utils.generic;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Date;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import cl.bbr.captcha.classes.CaptchaData;
import cl.bbr.captcha.manager.BbrCaptchaManager;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;

public class BbrCaptcha extends CustomComponent 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3479240713013303692L;
	private BbrTextField txtAnswer = null;
	private Image imgCaptcha = null;
	private Label lblQuestion = null;
	private String sessionId = VaadinSession.getCurrent().getSession().getId();

	public BbrCaptcha() 
	{
		this.initializeComponent();
	}
	
	private void initializeComponent()
	{
		//Refresh Button
		Button btnRefreshCaptcha = new Button(VaadinIcons.REFRESH);
		btnRefreshCaptcha.setWidth("45px");
		btnRefreshCaptcha.addStyleName("btn-captcha");
		btnRefreshCaptcha.addClickListener((ClickListener & Serializable)e -> btnRefresh_handler(e));

		//Captcha Question Panel
		imgCaptcha = new Image();
		
		HorizontalLayout pnlCaptcha = new HorizontalLayout();
		pnlCaptcha.addComponents(imgCaptcha, btnRefreshCaptcha);
		pnlCaptcha.setSpacing(true);
		pnlCaptcha.setComponentAlignment(imgCaptcha, Alignment.MIDDLE_LEFT);
		pnlCaptcha.setComponentAlignment(btnRefreshCaptcha, Alignment.MIDDLE_RIGHT);
		
		//Question Label
		lblQuestion = new  Label();
		lblQuestion.setWidthUndefined();
		lblQuestion.setId("label_question");
		
		VerticalLayout pnlQuestion = new VerticalLayout(lblQuestion);
		pnlQuestion.setHeight("25px");
		pnlQuestion.setWidth("350px");
		pnlQuestion.setStyleName("captcha-label-question");
		//pnlQuestion.setComponentAlignment(lblQuestion, Alignment.MIDDLE_CENTER);
		
		//Answer Text Field
		txtAnswer = new BbrTextField();
		txtAnswer.setWidth("100%");
		txtAnswer.setRestrict(RestrictRange.NUMBERS);
		txtAnswer.addStyleName("captcha-text-field");

		//Main Panel
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addComponents(pnlCaptcha,pnlQuestion,txtAnswer);
		mainLayout.setWidth("100%");
		mainLayout.setSpacing(false);
		mainLayout.setComponentAlignment(pnlCaptcha, Alignment.TOP_CENTER);
		mainLayout.setComponentAlignment(pnlQuestion, Alignment.MIDDLE_CENTER);
		
		this.refreshCaptcha();
		this.setCompositionRoot(mainLayout);
	}
	
	private void btnRefresh_handler(ClickEvent e) 
	{
		this.refreshCaptcha();
	}

	public Boolean validateAnswer()
	{
		return BbrCaptchaManager.getInstance().validateResponse(sessionId, txtAnswer.getValue());
	}
	
	public void refreshCaptcha()
	{
		CaptchaData captchaData = BbrCaptchaManager.getInstance().getCaptchaChallengeData(sessionId);
		BufferedImage challengeImage = captchaData.getImage();
		ImageStreamResource imagesource = new ImageStreamResource(challengeImage);
		StreamResource resource =   new StreamResource(imagesource, "img_"+new Date().getTime()+".png");
		
		imgCaptcha.setSource(resource);
		lblQuestion.setCaption(captchaData.getQuestion());
		txtAnswer.setValue("");
	}
}
