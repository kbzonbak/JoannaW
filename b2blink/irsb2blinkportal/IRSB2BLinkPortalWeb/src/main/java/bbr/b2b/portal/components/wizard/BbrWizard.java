package bbr.b2b.portal.components.wizard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.components.wizard.events.BbrStepEvent;
import bbr.b2b.portal.components.wizard.events.BbrStepEventListener;
import cl.bbr.core.components.basics.BbrHSeparator;

public class BbrWizard extends CustomComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6171010167719891081L;

	private HashMap<String,ArrayList<BbrStepEventListener>> bbrStepEventsListenerList = null;

	private VerticalLayout pnlSelectedStep 	= new VerticalLayout();
	private VerticalLayout pnlButtons 		= new VerticalLayout();
	
	private AbstractLayout  infoLayout = null;
	private BbrWizardProgress stepProgressLayout = null;
	
	private ArrayList<BbrWizardStep> listSteps = null;
	public ArrayList<BbrWizardStep> getListSteps() {
		return listSteps;
	}
	
	private BbrWizardStep selectedBbrStep = null;
	public BbrWizardStep getSelectedBbrStep() 
	{
		return selectedBbrStep;
	}

	
	
	private HorizontalLayout mainLayout = null;

	private Button btnCancel = null;
	private Button btnBack = null;
	private Button btnNext = null;
	private Button btnEnd = null;
	
	public AbstractLayout getInfoLayout() {
		return infoLayout;
	}

	public void setInfoLayout(AbstractLayout infoLayout) {
		this.infoLayout = infoLayout;
	}
	
	
	public BbrWizard(ArrayList<BbrWizardStep> stepsLayout, AbstractLayout infoLayout) 
	{
		this.listSteps = stepsLayout;
		this.infoLayout = infoLayout ;
		
		this.initializeComponents();
	}

	private void initializeComponents() 
	{
		this.initializeAllButtons();
		
		this.stepProgressLayout = new BbrWizardProgress(this);
		
		mainLayout = new HorizontalLayout();
		mainLayout.setSizeFull();
		this.setCompositionRoot(mainLayout);
		
		//Info Panel (Info + Progress Panel)
		VerticalLayout infoPanel = this.getInfoPanel();
		VerticalLayout mainPanel = this.getMainPanel(); 
		
		
		mainLayout.addComponents(infoPanel, mainPanel);
		mainLayout.setExpandRatio(mainPanel, 1F);
	}


	
	public synchronized void addBbrStepListener(String type, BbrStepEventListener listener)
	{
		if (bbrStepEventsListenerList == null) 
		{
			bbrStepEventsListenerList = new HashMap<>();
		}
		
		ArrayList<BbrStepEventListener> stepListenerList = bbrStepEventsListenerList.get(type);
		
		if(stepListenerList == null)
		{
			stepListenerList = new ArrayList<>();
		}
			
		if (!stepListenerList.contains(listener)) 
		{
			stepListenerList.add(listener);
		}
		
		bbrStepEventsListenerList.put(type, stepListenerList);
	}
	

	public synchronized void removeBbrStepListener(String type, BbrStepEventListener listener) 
	{
		if (bbrStepEventsListenerList != null) 
		{
			ArrayList<BbrStepEventListener> stepListenerList = bbrStepEventsListenerList.get(type);
			if(stepListenerList != null)
			{
				stepListenerList.remove(listener);	
				bbrStepEventsListenerList.put(type, stepListenerList);
			}
		}
	}

	private synchronized void dispatchBbrStepEvent(BbrStepEvent event)
	{
		if (bbrStepEventsListenerList != null && event != null) 
		{
			String messageTopic = event.getEventType();
			
			ArrayList<BbrStepEventListener> stepListenerList = bbrStepEventsListenerList.get(messageTopic);
			if(stepListenerList != null)
			{
				for (BbrStepEventListener listener : stepListenerList) 
				{
					listener.bbrEvent_handler(event);
				}
			}
		}
	}
	
	public BbrWizardStep getFirstStep()
	{
		BbrWizardStep result = null;
		
		if(listSteps != null && listSteps.size() > 0)
		{
			result = listSteps.get(0);
		}
		
		return result;
	}

	public BbrWizardStep getLastStep()
	{
		BbrWizardStep result = null;
		
		if(listSteps != null && listSteps.size() > 0)
		{
			result = listSteps.get(listSteps.size()-1);
		}
		
		return result;
	}
	
	private VerticalLayout getInfoPanel()
	{
		VerticalLayout result = new VerticalLayout();
		
		if(this.infoLayout != null && this.stepProgressLayout != null)
		{
			result.setWidth(infoLayout.getWidth(), infoLayout.getWidthUnits());
			result.setHeight("100%");
			result.addStyleName("bbr-wizard-info-panel");
			result.addComponents(stepProgressLayout, infoLayout);
			result.setExpandRatio(stepProgressLayout, 1F);
		}
		
		return result;
	}
	
	private VerticalLayout getMainPanel() //Step + Buttons
	{
		VerticalLayout result = new VerticalLayout();
		result.addStyleName("bbr-wizard-step-panel");
		result.setSizeFull();
		
		this.selectedBbrStep = this.getFirstStep() ;
		
		pnlSelectedStep.removeAllComponents();
		pnlSelectedStep.addComponent(this.selectedBbrStep.getStepContent());
		
		pnlButtons.removeAllComponents();
		pnlButtons.addStyleName("bbr-wizard-buttons-panel");
		
		HorizontalLayout bpanel = this.getButtonPanel(this.selectedBbrStep);
		pnlButtons.addComponent(bpanel);
		pnlButtons.setComponentAlignment(bpanel, Alignment.MIDDLE_CENTER);
		
		selectedBbrStep.onSelecteStep(null);
		
		result.addComponent(pnlSelectedStep);
		result.addComponent(pnlButtons);
		result.setExpandRatio(pnlSelectedStep, 1F);
		
		return result;
	}
	
	private void updateMainPanel(BbrWizardStep currentStep)
	{
		pnlSelectedStep.removeAllComponents();
		pnlSelectedStep.addComponent(currentStep.getStepContent());
		
		HorizontalLayout bpanel = this.getButtonPanel(currentStep);
		
		pnlButtons.removeAllComponents();
		pnlButtons.addComponent(bpanel);
		
		pnlButtons.addComponent(bpanel);
		pnlButtons.setComponentAlignment(bpanel, Alignment.MIDDLE_CENTER);
		
	}
	
	private HorizontalLayout getButtonPanel(BbrWizardStep currentStep)
	{
		HorizontalLayout result = new HorizontalLayout();
		result.addStyleName("bbr-wizard-buttons-layout");
		result.setSpacing(true);
		result.setResponsive(true);
		BbrHSeparator separator1 = new BbrHSeparator("50px");
		BbrHSeparator separator2 = new BbrHSeparator("50px");
		
		result.addComponent(btnCancel);
		result.addComponent(separator1);
		result.addComponent(btnBack);
		btnBack.setEnabled(false);
		result.addComponent(btnNext);
		btnNext.setEnabled(false);
		result.addComponent(separator2);
		result.addComponent(btnEnd);
		btnEnd.setEnabled(false);
		
		if(listSteps != null && listSteps.size() > 0)
		{
			if(listSteps.size()== 1)
			{
				btnEnd.setEnabled(true);
			}
			else
			{
				if(!isFirstStep(currentStep))
				{
					btnBack.setEnabled(true);
				}
				
				if(!isLastStep(currentStep))
				{
					btnNext.setEnabled(true);
				}
				else
				{
					btnEnd.setEnabled(true);
				}
			}
		}
		
		return result;
	}
	
	private void initializeAllButtons()
	{
		btnCancel  = new Button("Cancelar");
		btnCancel.addStyleName("bbr-wizard-buttons");
		btnCancel.addStyleName("bbr-wizard-buttons-cancel");
		btnCancel.addClickListener((ClickListener & Serializable) e -> cancelButton_handler(e));

		btnBack = new Button("Anterior");
		btnBack.addStyleName("bbr-wizard-buttons");
		btnBack.addStyleName("bbr-wizard-buttons-back");
		btnBack.addClickListener((ClickListener & Serializable) e -> prevButton_handler(e));

		btnNext = new Button("Siguiente");
		btnNext.addStyleName("bbr-wizard-buttons");
		btnNext.addStyleName("bbr-wizard-buttons-next");
		btnNext.addClickListener((ClickListener & Serializable) e -> nextButton_handler(e));
		
		btnEnd = new Button("Finalizar");
		btnEnd.addStyleName("bbr-wizard-buttons");
		btnEnd.addStyleName("bbr-wizard-buttons-end");
		btnEnd.addClickListener((ClickListener & Serializable) e -> endButton_handler(e));
		
	}
	
	private Boolean isAllowedToNextStep()
	{
		Boolean result = false ;
		
		if(selectedBbrStep != null)
		{
			result = selectedBbrStep.isAllowedToNextStep();
		}
		
		return result;
	}

	private Boolean isAllowedToFinished()
	{
		Boolean result = false ;
		
		if(this.getLastStep() != null)
		{
			result = this.getLastStep().isAllowedToNextStep();
		}
		
		return result;
	}

	private Object getTransferObject()
	{
		Object result = null ;
		
		if(selectedBbrStep != null)
		{
			result = selectedBbrStep.getTransferObject();
		}
		
		return result;
	}
	private void nextButton_handler(ClickEvent e) 
	{
		if(isAllowedToNextStep())
		{
			Object transferObj = this.getTransferObject();
			
			BbrWizardStep nextStep = this.getNextBbrStep();
			
			this.updateMainPanel(nextStep);
		
			if(this.selectedBbrStep != null)
			{
				this.selectedBbrStep.onLostSelected();
			}
			
			this.selectedBbrStep = nextStep;
			this.selectedBbrStep.onSelecteStep(transferObj);
			
			BbrStepEvent stepEvent = new BbrStepEvent(BbrStepEvent.STEP_CHANGED);
			stepEvent.setResultStep(nextStep);
			
			this.dispatchBbrStepEvent(stepEvent);
		}
		else
		{
			BbrStepEvent bbrStepEvent = new BbrStepEvent(BbrStepEvent.STEP_NOT_ALLOWED_NEXT);
			dispatchBbrStepEvent(bbrStepEvent);
		}
	}

	private void prevButton_handler(ClickEvent e) 
	{
		BbrWizardStep prevStep = this.getPrevBbrStep();
		
		Object transferObj = this.getTransferObject();

		this.updateMainPanel(prevStep);
		
		if(this.selectedBbrStep != null)
		{
			this.selectedBbrStep.onLostSelected();
		}
		
		this.selectedBbrStep = prevStep;
		this.selectedBbrStep.onSelecteStep(transferObj);

		BbrStepEvent stepEvent = new BbrStepEvent(BbrStepEvent.STEP_CHANGED);
		stepEvent.setResultStep(prevStep);
		
		this.dispatchBbrStepEvent(stepEvent);
	}

	private void cancelButton_handler(ClickEvent e) 
	{
		BbrStepEvent stepEvent = new BbrStepEvent(BbrStepEvent.STEP_CANCEL);
		
		this.dispatchBbrStepEvent(stepEvent);
	}
	
	private void endButton_handler(ClickEvent e) 
	{
		if(isAllowedToFinished())
		{
			BbrStepEvent stepEvent = new BbrStepEvent(BbrStepEvent.STEP_FINISHED);
			
			this.dispatchBbrStepEvent(stepEvent);	
		}
	}

	private Boolean isFirstStep(BbrWizardStep currentStep)
	{
		Boolean result = false ;
		
		BbrWizardStep firstStep = this.getFirstStep();
		
		if(firstStep != null)
		{
			if(firstStep.getStepId().equals(currentStep.getStepId()))
			{
				result = true ;
			}
		}
		
		return result;
	}

	private Boolean isLastStep(BbrWizardStep currentStep)
	{
		Boolean result = false ;
		
		BbrWizardStep lastStep = this.getLastStep();
		
		if(lastStep != null)
		{
			if(lastStep.getStepId().equals(currentStep.getStepId()))
			{
				result = true ;
			}
		}
		
		return result;
	}
	
	private int getSelectedStepIndex()
	{
		int result = -1;
		if(listSteps != null && listSteps.size() > 0)
		{
			for (int i = 0; i < listSteps.size(); i++) 
			{
				if(selectedBbrStep.getStepId().equals(listSteps.get(i).getStepId()))
				{
					result = i;
					break;
				}
			}	
		}
		
		return result;
	}


	private BbrWizardStep getBbrStepByIndex(int index)
	{
		BbrWizardStep result = null;
		
		if(listSteps != null && listSteps.size() > index && index >= 0)
		{
			result = listSteps.get(index);	
		}
		
		return result;
	}
	
	private BbrWizardStep getNextBbrStep()
	{
		BbrWizardStep result = null;
		
		int nextIndex = this.getSelectedStepIndex()+1;
		
		result = this.getBbrStepByIndex(nextIndex);
		
		return result;
	}

	private BbrWizardStep getPrevBbrStep()
	{
		BbrWizardStep result = null;
		
		int prevIndex = this.getSelectedStepIndex()-1;
		
		result = this.getBbrStepByIndex(prevIndex);
		
		return result;
	}
 

}
