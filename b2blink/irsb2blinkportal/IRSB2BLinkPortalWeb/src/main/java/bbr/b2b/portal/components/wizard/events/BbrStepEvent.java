package bbr.b2b.portal.components.wizard.events;

import java.io.Serializable;

import bbr.b2b.portal.components.wizard.BbrWizardStep;

public class BbrStepEvent implements Serializable
{
	private static final long serialVersionUID = -4900331894847001370L;
	
	public static final String STEP_CHANGED 			= "stepChanged";
	public static final String STEP_CANCEL 				= "stepCancel";
	public static final String STEP_FINISHED			= "stepFinished";
	public static final String STEP_NOT_ALLOWED_NEXT 	= "stepNotAllowedNext";
	
	private String eventType;
	public String getEventType() 
	{
		return eventType;
	}
	public void setEventType(String eventType) 
	{
		this.eventType = eventType;
	}
	
	private BbrWizardStep resultStep ;
	public BbrWizardStep getResultStep() 
	{
		return resultStep;
	}
	public void setResultStep(BbrWizardStep resultObject) 
	{
		this.resultStep = resultObject;
	}
	
	
	public BbrStepEvent(String eventType) 
	{
		this.setEventType(eventType);
	}
}
