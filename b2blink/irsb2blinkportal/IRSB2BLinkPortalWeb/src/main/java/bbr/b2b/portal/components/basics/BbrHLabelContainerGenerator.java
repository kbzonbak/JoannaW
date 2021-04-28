package bbr.b2b.portal.components.basics;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class BbrHLabelContainerGenerator extends HorizontalLayout
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8845184501920666180L;
	private Label	lblValue;
	private Label	lblcmp;

	public BbrHLabelContainerGenerator(String label, String value)
	{
		// -> Por defecto el label tiene 200px de width
		this(label, value, "200px", true);
	}

	public BbrHLabelContainerGenerator(Object label, Object value, boolean isWithBackground)
	{
		// -> Por defecto el label tiene 200px de width
		this(label, value, "200px", isWithBackground);
	}

	public BbrHLabelContainerGenerator(Object labelCaption, Object value, String labelWidth)
	{
		this(labelCaption, value, labelWidth, true);
	}

	public BbrHLabelContainerGenerator(Object labelCaption, Object value, String labelWidth, boolean isWithBackground)
	{
		// -> HorizontalLayout
		lblcmp = new Label(String.valueOf(labelCaption));
		lblValue = new Label(String.valueOf(value));

		lblcmp.setContentMode(ContentMode.HTML);
		lblValue.setContentMode(ContentMode.HTML);

		lblcmp.setCaptionAsHtml(true);
		lblValue.setCaptionAsHtml(true);

		this.addStyleName("bbr-vfield-container");
		this.addComponents(lblcmp, lblValue);

		// -> Label
		lblcmp.setSizeFull();
		String style = "bbr-vfield-container-label left";
		if (isWithBackground)
		{
			style += " with-background";
		}

		lblcmp.setStyleName(style);
		lblcmp.setWidth(labelWidth);

		// -> Value
		lblValue.setSizeFull();
		lblValue.addStyleName("bbr-vfield-container-value");
	}
	
	public Label getValueLabel()
	{
		return this.lblValue;
	}

	public Label getLabel()
	{
		return this.lblcmp;
	}
}
