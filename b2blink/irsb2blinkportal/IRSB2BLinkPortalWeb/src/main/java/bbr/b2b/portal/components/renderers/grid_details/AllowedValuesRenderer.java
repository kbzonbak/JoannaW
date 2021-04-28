package bbr.b2b.portal.components.renderers.grid_details;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.fep.common.data.classes.AttributeValueDTO;
import bbr.b2b.portal.components.utils.fep.LanguagesAttributesValue;

public class AllowedValuesRenderer extends HorizontalLayout
{

	private static final long serialVersionUID = 738621064677634867L;

	public AllowedValuesRenderer(LanguagesAttributesValue languagesAttributesValue, Long languageId)
	{
		AttributeValueDTO attrib = languagesAttributesValue.getMapLanguages().get(languageId);
		String value = attrib != null ? attrib.getValue() : " ";

		if (value != null)
		{
			Label caption = new Label();
			caption.setCaption(value);
			this.addStyleName("margin-top-5");
			this.setMargin(false);
			this.addComponent(caption);
			this.setExpandRatio(caption, 1F);
			this.setWidth("100%");
			this.markAsDirty();
		}
	}

}
