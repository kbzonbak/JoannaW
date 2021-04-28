package bbr.b2b.portal.components.walldiary;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.components.basics.BbrClickLabel;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.users.report.classes.FaqDTO;
import bbr.b2b.users.report.classes.FaqsResultDTO;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.components.basics.BbrUI;

public class FaqsPanel extends VerticalLayout
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long	serialVersionUID	= 4475805477573182647L;

	private BbrUI				parentUI			= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	public FaqsPanel(BbrUI parentUI)
	{
		this.parentUI = parentUI;
		initializeControl();
	}

	private void initializeControl()
	{
		this.addComponent(getFaqs());
	}

	private VerticalLayout getFaqs()
	{
		VerticalLayout result = new VerticalLayout();
		result.setHeight(null);
		result.setMargin(false);
		if (UI.getCurrent() instanceof BbrUI)
		{
			SessionBean sessionBean = (SessionBean) ((BbrUI) UI.getCurrent()).getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
			if (sessionBean != null)
			{
				try
				{
					FaqsResultDTO faqsResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getFaqQuestionsForUserProfiles(sessionBean.getUserId());
					if (faqsResult != null && faqsResult.getFaqDTOs() != null && faqsResult.getFaqDTOs().length > 0)
					{
						for (FaqDTO currentFaq : faqsResult.getFaqDTOs())
						{
							BbrClickLabel linkLabel = new BbrClickLabel(currentFaq.getQuestion(), VaadinIcons.COMMENT_O);
							linkLabel.addStyleName("link-button");
							linkLabel.setData(currentFaq);

							linkLabel.addBbrEventListener(new BbrEventListener()
							{
								/**
								 * 
								 */
								private static final long serialVersionUID = 1L;

								@Override
								public void bbrEvent_handler(BbrEvent event)
								{
									if (event.getResultObject() != null)
									{
										try
										{
											FaqsResultDTO faqsResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getFaqAnswerByQuestion(((FaqDTO) event.getResultObject()).getId());

											FaqDTO selectedFaq = ((faqsResult.getFaqDTOs() != null) && (faqsResult.getFaqDTOs().length > 0)) ? (faqsResult.getFaqDTOs()[0]) : null;

											if (selectedFaq != null)
											{
												FaqDetails details = new FaqDetails(FaqsPanel.this.parentUI, selectedFaq);
												details.initializeView();
												details.open(true);
											}
										}
										catch (BbrUserException | BbrSystemException e)
										{
											e.printStackTrace();
										}
									}
								}
							});

							result.addComponent(linkLabel);
						}
					}
				}
				catch (BbrUserException e)
				{
					e.printStackTrace();
				}
				catch (BbrSystemException e)
				{
					e.printStackTrace();
				}
			}
		}

		return result;
	}
}
