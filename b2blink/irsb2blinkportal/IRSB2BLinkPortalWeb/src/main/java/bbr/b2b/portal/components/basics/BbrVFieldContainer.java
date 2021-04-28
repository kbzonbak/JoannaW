package bbr.b2b.portal.components.basics;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class BbrVFieldContainer extends VerticalLayout 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 334085764530180522L;

	public BbrVFieldContainer(Component label, Component value) 
	{
		this(label, value, "100%");
	}
	
	public BbrVFieldContainer(Component label, Component value, String height) 
	{
		this.addStyleName("bbr-vfield-container");
		
		label.setSizeFull();
		label.addStyleName("bbr-vfield-container-label");
		value.setSizeFull();
		value.addStyleName("bbr-vfield-container-value");
		this.addComponents(label,value); 
		this.setWidth(height);
		this.setMargin(false);
		this.setSpacing(false);
	}
}
