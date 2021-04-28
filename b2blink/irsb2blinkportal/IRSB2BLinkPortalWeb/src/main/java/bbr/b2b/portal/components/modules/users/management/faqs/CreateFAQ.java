package bbr.b2b.portal.components.modules.users.management.faqs;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.TextEditorUtil;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.AddFaqInitParamDTO;
import bbr.b2b.users.report.classes.FaqDTO;
import bbr.b2b.users.report.classes.ProfileArrayResultDTO;
import bbr.b2b.users.report.classes.ProfileDTO;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;

public class CreateFAQ extends BbrWindow
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long			serialVersionUID	= 3247626779164695209L;

	private TextField					txt_Question;
	private CKEditorTextField			txt_Answer;

	private BbrAdvancedGrid<ProfileDTO>	dgd_Profiles;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public CreateFAQ(BbrUI parent)
	{
		super(parent);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		// Question Text
		txt_Question = new TextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "question"));
		txt_Question.setWidth(100, Unit.PERCENTAGE);
		// Answer Text Editor
		txt_Answer = new CKEditorTextField(TextEditorUtil.getInstance().getCKEditorConfig());
		txt_Answer.setLocale(this.getUser().getLocale());
		txt_Answer.setWidth(100, Unit.PERCENTAGE);
		txt_Answer.setHeight(225, Unit.PIXELS);
		txt_Answer.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "answer"));

		// Profile Table
		dgd_Profiles = new BbrAdvancedGrid<>(this.getLocale());
		dgd_Profiles.setSortable(false);
		this.initializeGridColumns();
		dgd_Profiles.addStyleName("report-grid");
		dgd_Profiles.addStyleName("bbr-multi-grid");
		dgd_Profiles.setSizeFull();
		dgd_Profiles.setSelectionMode(SelectionMode.MULTI);
		dgd_Profiles.setId("dgd_Profiles");
		this.updateProfiles();

		// Buttons Layout
		Button btn_Create = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"));
		btn_Create.addClickListener((ClickListener & Serializable) e -> btnCreate_clickHandler(e));
		btn_Create.setStyleName("primary");
		btn_Create.addStyleName("btn-generic");
		btn_Create.setWidth("100%");

		Button btn_Reset = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "reset"));
		btn_Reset.addClickListener((ClickListener & Serializable) e -> btnReset_clickHandler(e));
		btn_Reset.setStyleName("primary");
		btn_Reset.addStyleName("btn-generic");
		btn_Reset.setWidth("100%");

		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-generic");
		btn_Cancel.setWidth("100%");

		HorizontalLayout buttonsLayout = new HorizontalLayout(btn_Create, btn_Reset, btn_Cancel);
		buttonsLayout.setWidth("500px");
		buttonsLayout.setComponentAlignment(btn_Create, Alignment.MIDDLE_LEFT);
		buttonsLayout.setComponentAlignment(btn_Reset, Alignment.MIDDLE_CENTER);
		buttonsLayout.setComponentAlignment(btn_Cancel, Alignment.MIDDLE_RIGHT);
		buttonsLayout.addStyleName("buttons-layout");
		buttonsLayout.setSpacing(true);

		// Vertical Layout for Components
		VerticalLayout mainLayout = new VerticalLayout(txt_Question, txt_Answer, dgd_Profiles, buttonsLayout);
		mainLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(dgd_Profiles, 1F);
		mainLayout.addStyleName("bbr-margin-windows");

		// Main Windows
		this.setWidth(700, Unit.PIXELS);
		this.setHeight(600, Unit.PIXELS);
		this.setResizable(false);
		this.addStyleName("win-details");
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "create_faqs"));
		this.setContent(mainLayout);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void btnCreate_clickHandler(ClickEvent event)
	{
		this.addFaqData();
	}

	private void btnReset_clickHandler(ClickEvent event)
	{
		this.resetFormData();
	}

	private void btnClose_clickHandler(ClickEvent event)
	{
		this.close();
	}

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private void initializeGridColumns()
	{
		dgd_Profiles.addColumn(ProfileDTO::getName)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "profiles"));
	}

	private void updateProfiles()
	{
		if (UI.getCurrent() instanceof BbrUI)
		{
			SessionBean sessionBean = (SessionBean) ((BbrUI) UI.getCurrent()).getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
			if (sessionBean != null)
			{
				try
				{
					ProfileArrayResultDTO profilesResult = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().getProfiles();
					if (profilesResult != null && profilesResult.getProfiles() != null && profilesResult.getProfiles().length > 0)
					{
						ListDataProvider<ProfileDTO> dataprovider = new ListDataProvider<>(Arrays.asList(profilesResult.getProfiles()));
						dgd_Profiles.setDataProvider(dataprovider);
					}
				}
				catch (BbrUserException e)
				{
					AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
				}
				catch (BbrSystemException e)
				{
					e.printStackTrace();
				}

			}
		}
	}

	private void resetFormData()
	{
		this.txt_Question.setValue("");
		this.txt_Answer.setValue("");
		this.dgd_Profiles.deselectAll();
	}

	private void addFaqData()
	{
		if (UI.getCurrent() instanceof BbrUI)
		{
			SessionBean sessionBean = (SessionBean) ((BbrUI) UI.getCurrent()).getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
			if (sessionBean != null)
			{
				if (isValidData())
				{
					FaqDTO faq = new FaqDTO();
					faq.setQuestion(txt_Question.getValue().trim());
					faq.setAnswer(txt_Answer.getValue().trim());
					faq.setLastchange(LocalDateTime.now());
					faq.setCount(0);

					faq.setUsdata(sessionBean.getUserFullName());

					ArrayList<Long> ids = new ArrayList<>();
					dgd_Profiles.getSelectedItems().forEach((Consumer<ProfileDTO> & Serializable) profile -> ids.add(profile.getId()));

					Long[] profilesIds = ids.toArray(new Long[ids.size()]);

					AddFaqInitParamDTO initParam = new AddFaqInitParamDTO();
					initParam.setFaq(faq);
					initParam.setPrKeysAdd(profilesIds);

					try
					{
						BaseResultDTO addResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().doAddOrUpdateFaq(initParam, true);
						if (addResult.getStatuscode().equals("0"))
						{
							// Se agreg� el Faq sin problemas.
							BbrEvent bbrEvent = new BbrEvent(BbrEvent.ITEM_CREATED);
							bbrEvent.setResultObject(faq);

							this.dispatchBbrEvent(bbrEvent);
							this.resetFormData();

							BbrMessageBox
									.createInfo(CreateFAQ.this.getBbrUIParent())
									.withOkButton()
									.withCaption(I18NManager.getI18NString(CreateFAQ.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"))
									.withMessage(I18NManager.getI18NString(CreateFAQ.this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "faq_added"))
									.open();
						}
						else
						{
							BbrMessageBox
									.createError(CreateFAQ.this.getBbrUIParent())
									.withCloseButton()
									.withCaption(I18NManager.getI18NString(CreateFAQ.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"))
									.withMessage(addResult.getStatusmessage())
									.open();
						}
					}
					catch (BbrUserException e)
					{
						AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
					}
					catch (BbrSystemException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					// Faltan campos
					BbrMessageBox
							.createWarning(CreateFAQ.this.getBbrUIParent())
							.withCloseButton()
							.withCaption(I18NManager.getI18NString(CreateFAQ.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_warning"))
							.withMessage(I18NManager.getI18NString(CreateFAQ.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "msg_missing_fields"))
							.open();
				}
			}
		}

	}

	private Boolean isValidData()
	{
		txt_Question.setComponentError(null);
		txt_Answer.setComponentError(null);
		dgd_Profiles.setComponentError(null);

		Boolean result = true;

		if (txt_Question.getValue() == null || txt_Question.getValue().length() <= 0)
		{
			txt_Question.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "question_required")));
			result = false;
		}
		if (txt_Answer.getValue() == null || txt_Answer.getValue().length() <= 0)
		{
			txt_Answer.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "answer_required")));
			result = false;
		}

		if (dgd_Profiles.getSelectedItems() == null || dgd_Profiles.getSelectedItems().size() == 0)
		{
			dgd_Profiles.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "profile_required")));
			result = false;
		}

		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
