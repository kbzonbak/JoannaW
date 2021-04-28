package bbr.b2b.portal.components.wizard;

import com.vaadin.ui.AbstractLayout;

public interface BbrWizardStep {

	public String getStepId();
    public String getStepCaption();
    public AbstractLayout getStepContent();
    public boolean isAllowedAdvance();
    public boolean isAllowedBack();
    public void onSelecteStep(Object transferObj);
    public void onLostSelected();
	public Boolean isAllowedToNextStep();
	public Object getTransferObject();

}
