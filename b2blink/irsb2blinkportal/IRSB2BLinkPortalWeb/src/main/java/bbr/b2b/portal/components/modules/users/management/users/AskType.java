package bbr.b2b.portal.components.modules.users.management.users;

import java.io.Serializable;

import com.vaadin.ui.Button.ClickListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.constants.EnumUserType;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.components.basics.BbrButtonGroup;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class AskType extends BbrWindow
{
	private static final long serialVersionUID = -6147210166734303324L;

	public AskType(BbrUI parent)
	{
		super(parent);
	}

	@Override
	public void initializeView()
	{
		BbrButtonGroup buttonGroup = new BbrButtonGroup.BbrGroupButtonBuilder(this.getBbrUIParent())
				.withPrimaryButtonLabel(EnumUserType.PROVIDER.getValue())
				.withPrimaryButtonListener((ClickListener & Serializable) (e) -> this.btn_ClickHandler(EnumUserType.PROVIDER.getValue()))
				.withCancelButtonLabel(EnumUserType.RETAILER.getDescription())
				.withCancelButtonListener((ClickListener & Serializable) (e) -> this.btn_ClickHandler(EnumUserType.RETAILER.getValue()))
				.build();

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addComponent(buttonGroup);
		mainLayout.setSpacing(false);
		mainLayout.setMargin(new MarginInfo(true, false, false, false));

		this.setContent(mainLayout);
		this.setHeight("120px");
		this.setWidth("400px");
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_user_type"));

	}

	private void btn_ClickHandler(String type)
	{
		this.dispatchBbrEvent(new BbrEvent(type));
		this.close();
	}
}
