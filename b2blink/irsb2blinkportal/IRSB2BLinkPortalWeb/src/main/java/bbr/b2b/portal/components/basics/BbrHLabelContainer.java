package bbr.b2b.portal.components.basics;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class BbrHLabelContainer extends HorizontalLayout 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3593847197110399334L;

	public BbrHLabelContainer(Label label, Label value) 
	{
		// -> Por defecto el label tiene 200px de width
		this(label, value, "200px");
	}
	
	public BbrHLabelContainer(Label label, Label value, String labelWidth) 
	{
		// -> HorizontalLayout
		this.addStyleName("bbr-vfield-container");
		this.addComponents(label,value); 
		label.setCaptionAsHtml(true);
		
		// -> Label 
		label.setSizeFull();
		label.setStyleName("bbr-vfield-container-label left with-background");
		label.setWidth("200px");
		
		// -> Value
		value.setSizeFull();
		// No mostrar label "null"
		if(value.getValue() == null){
			value.setValue("");
		}
		value.addStyleName("bbr-vfield-container-value");
	}
}
