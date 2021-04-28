package bbr.b2b.portal.components.wizard.events;

import java.io.Serializable;

public interface BbrStepEventListener extends Serializable
{
	public void bbrEvent_handler(BbrStepEvent event); 
}
