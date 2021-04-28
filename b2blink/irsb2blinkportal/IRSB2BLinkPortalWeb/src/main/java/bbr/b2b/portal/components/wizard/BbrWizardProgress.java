package bbr.b2b.portal.components.wizard;

import java.io.Serializable;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.components.wizard.events.BbrStepEvent;
import bbr.b2b.portal.components.wizard.events.BbrStepEventListener;

public class BbrWizardProgress extends VerticalLayout 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2551513738367785559L;
	private BbrWizard parentWizard = null;
	public BbrWizard getParentWizard() 
	{
		return parentWizard;
	}
	
	public BbrWizardProgress(BbrWizard parentWizard) 
	{
		this.setWidth("100%");
		this.parentWizard = parentWizard;
		if(this.parentWizard != null)
		{
			BbrWizardStep firstStep = parentWizard.getFirstStep();

			this.parentWizard.addBbrStepListener(BbrStepEvent.STEP_CHANGED, (BbrStepEventListener & Serializable) e -> parantWizard_stepHandler(e));
			this.updateStepsList(firstStep);	
		}
	}

	private void parantWizard_stepHandler(BbrStepEvent e) 
	{
		if(e != null && e.getEventType().equals(BbrStepEvent.STEP_CHANGED) && e.getResultStep() != null)
		{
			this.updateStepsList(e.getResultStep());
		}
	}

	private void updateStepsList(BbrWizardStep current) 
	{
		this.removeAllComponents();
		this.addStyleName("bbr-wizard-progress");
		if(this.parentWizard != null && this.parentWizard.getListSteps() != null  && this.parentWizard.getListSteps().size() > 0)
		{
			int i = 1;
			for (BbrWizardStep step : this.parentWizard.getListSteps()) 
			{
				String caption = i + " - " + step.getStepCaption();
		
				Label lblStep = new Label(caption); 

				if(current.getStepId().equals(step.getStepId()))
				{
					lblStep.addStyleName("bbr-wizard-progress-step-selected");	
				}
				else
				{
					lblStep.addStyleName("bbr-wizard-progress-step-normal");
				}
				
				this.addComponent(lblStep);
				
				i++;
			}
		}
	}

	
	
	
}
