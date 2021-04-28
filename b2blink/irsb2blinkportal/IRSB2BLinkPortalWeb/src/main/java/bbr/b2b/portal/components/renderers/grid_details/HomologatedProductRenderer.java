package bbr.b2b.portal.components.renderers.grid_details;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;

public class HomologatedProductRenderer extends HorizontalLayout
{
	private static final long serialVersionUID = 1226678720939602529L;

	public HomologatedProductRenderer(boolean value)
	{
		CheckBox chkValue = new CheckBox();

		chkValue.setValue(value);
		chkValue.setReadOnly(true);

		this.addComponent(chkValue);
		this.setMargin(false);
		this.markAsDirtyRecursive();
	}
}
