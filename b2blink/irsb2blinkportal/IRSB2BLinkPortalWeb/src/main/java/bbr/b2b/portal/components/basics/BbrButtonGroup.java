package bbr.b2b.portal.components.basics;

import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.components.basics.BbrUI;

/**
 * BbrGroupButton
 * 
 * @author Richard Lozada
 *
 */
public class BbrButtonGroup extends HorizontalLayout
{
	private static final long	serialVersionUID	= -7569324053950519997L;
	Button						primaryButton		= null;
	Button						secundaryButton		= null;
	Button						cancelButton		= null;
	BbrGroupButtonBuilder		builder				= null;
	BbrUI						bbrUI				= null;

	public BbrButtonGroup(BbrGroupButtonBuilder builder)
	{
		initializateButtons(builder);
		this.setWidth("100%");
		this.setMargin(false);
	}

	public void showOnlyPrimaryButton()
	{
		this.showOnlyButton(this.primaryButton);
	}

	public void showOnlyCancelButton()
	{
		this.showOnlyButton(this.cancelButton);
	}

	public void showOnlySecundaryButton()
	{
		this.showOnlyButton(this.secundaryButton);
	}

	public BbrButtonGroup getDefaultButtonGroup()
	{
		return new BbrButtonGroup(builder);
	}

	private void showOnlyButton(Button button)
	{
		this.removeAllComponents();
		this.addComponent(button);
		this.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
	}

	private void initializateButtons(BbrGroupButtonBuilder builder)
	{
		if (builder.primaryButton)
		{
			this.primaryButton = new Button(
					builder.primaryButtonLabel == null ? I18NManager.getI18NString(builder.bbrUI, BbrUtilsResources.RES_GENERIC, "accept") : builder.primaryButtonLabel);
			this.primaryButton.setStyleName(builder.primaryButtonStyle);

			if (builder.primaryButtonClickShortcut != -1)
			{
				this.primaryButton.setClickShortcut(builder.primaryButtonClickShortcut);
			}
			if (builder.primaryButtonWidth != null)
			{
				this.primaryButton.setWidth(builder.primaryButtonWidth);
			}
			if (builder.primaryButtonListener != null)
			{
				this.primaryButton.addClickListener(builder.primaryButtonListener);
			}
			if (builder.primaryButtonHeight != null)
			{
				this.primaryButton.setHeight(builder.primaryButtonHeight);
			}
			if (builder.primaryButtonIcon != null)
			{
				this.secundaryButton.setIcon(builder.primaryButtonIcon);
			}
			this.addComponent(this.primaryButton);
			this.setComponentAlignment(this.primaryButton, Alignment.MIDDLE_RIGHT);
		}

		if (builder.secundaryButton)
		{
			this.secundaryButton = new Button(builder.secundaryButtonLabel);
			this.secundaryButton.setStyleName(builder.secundaryButtonStyle);

			if (builder.secundaryButtonClickShortcut != -1)
			{
				this.secundaryButton.setClickShortcut(builder.secundaryButtonClickShortcut);
			}
			if (builder.secundaryButtonWidth != null)
			{
				this.secundaryButton.setWidth(builder.secundaryButtonWidth);
			}
			if (builder.secundaryButtonListener != null)
			{
				this.secundaryButton.addClickListener(builder.secundaryButtonListener);
			}
			if (builder.secundaryButtonHeight != null)
			{
				this.secundaryButton.setHeight(builder.secundaryButtonHeight);
			}
			if (builder.secundaryButtonIcon != null)
			{
				this.secundaryButton.setIcon(builder.secundaryButtonIcon);
			}
			this.addComponent(this.secundaryButton);
			this.setComponentAlignment(this.secundaryButton, Alignment.MIDDLE_CENTER);
		}

		if (builder.cancelButton)
		{
			this.cancelButton = new Button(
					builder.cancelButtonLabel == null ? I18NManager.getI18NString(builder.bbrUI, BbrUtilsResources.RES_GENERIC, "cancel") : builder.cancelButtonLabel);
			this.cancelButton.setStyleName(builder.cancelButtonStyle);

			if (builder.cancelButtonClickShortcut != -1)
			{
				this.cancelButton.setClickShortcut(builder.cancelButtonClickShortcut);
			}
			if (builder.cancelButtonWidth != null)
			{
				this.cancelButton.setWidth(builder.cancelButtonWidth);
			}
			if (builder.cancelButtonListener != null)
			{
				this.cancelButton.addClickListener(builder.cancelButtonListener);
			}
			if (builder.cancelButtonHeight != null)
			{
				this.cancelButton.setHeight(builder.cancelButtonHeight);
			}
			if (builder.cancelButtonIcon != null)
			{
				this.cancelButton.setIcon(builder.cancelButtonIcon);
			}
			this.addComponent(this.cancelButton);
			this.setComponentAlignment(this.cancelButton, Alignment.MIDDLE_LEFT);
		}
		this.bbrUI = builder.bbrUI;
		this.builder = builder;
	}

	public Button getPrimaryButton()
	{
		return this.primaryButton;
	}

	public Button getSecundaryButton()
	{
		return this.secundaryButton;
	}

	public Button getCancelButton()
	{
		return this.cancelButton;
	}

	public static class BbrGroupButtonBuilder
	{
		private String			primaryButtonStyle				= "btn-generic";
		private String			secundaryButtonStyle			= "btn-generic";
		private String			cancelButtonStyle				= "btn-generic";

		private String			primaryButtonLabel				= null;
		private String			secundaryButtonLabel			= null;
		private String			cancelButtonLabel				= null;

		private String			primaryButtonWidth				= "140px";
		private String			secundaryButtonWidth			= "140px";
		private String			cancelButtonWidth				= "140px";

		private String			primaryButtonHeight				= null;
		private String			secundaryButtonHeight			= null;
		private String			cancelButtonHeight				= null;

		private ClickListener	primaryButtonListener			= null;
		private ClickListener	secundaryButtonListener			= null;
		private ClickListener	cancelButtonListener			= null;

		private int				primaryButtonClickShortcut		= -1;
		private int				secundaryButtonClickShortcut	= -1;
		private int				cancelButtonClickShortcut		= -1;

		private boolean			primaryButton					= true;
		private boolean			secundaryButton					= false;
		private boolean			cancelButton					= true;

		private Resource		primaryButtonIcon				= null;
		private Resource		secundaryButtonIcon				= null;
		private Resource		cancelButtonIcon				= null;

		private BbrUI			bbrUI							= null;

		public BbrGroupButtonBuilder(BbrUI bbrUI)
		{
			this.bbrUI = bbrUI;
		}

		public BbrGroupButtonBuilder withPrimaryButtonStyle(String primaryButtonStyle)
		{
			this.primaryButtonStyle = primaryButtonStyle;
			return this;
		}

		public BbrGroupButtonBuilder withSecundaryButtonStyle(String secundaryButtonStyle)
		{
			this.secundaryButtonStyle = secundaryButtonStyle;
			return this;
		}

		public BbrGroupButtonBuilder withCancelButtonStyle(String cancelButtonStyle)
		{
			this.cancelButtonStyle = cancelButtonStyle;
			return this;
		}

		public BbrGroupButtonBuilder withPrimaryButtonWidth(String primaryButtonWidth)
		{
			this.primaryButtonWidth = primaryButtonWidth;
			return this;
		}

		public BbrGroupButtonBuilder withSecundaryButtonWidth(String secundaryButtonWidth)
		{
			this.secundaryButtonWidth = secundaryButtonWidth;
			return this;
		}

		public BbrGroupButtonBuilder withCancelButtonWidth(String cancelButtonWidth)
		{
			this.cancelButtonWidth = cancelButtonWidth;
			return this;
		}

		public BbrGroupButtonBuilder withPrimaryButtonHeight(String primaryButtonHeight)
		{
			this.primaryButtonHeight = primaryButtonHeight;
			return this;
		}

		public BbrGroupButtonBuilder withSecundaryButtonHeight(String secundaryButtonHeight)
		{
			this.secundaryButtonHeight = secundaryButtonHeight;
			return this;
		}

		public BbrGroupButtonBuilder withCancelButtonHeight(String cancelButtonHeight)
		{
			this.cancelButtonHeight = cancelButtonHeight;
			return this;
		}

		public BbrGroupButtonBuilder withPrimaryButtonListener(ClickListener primaryButtonListener)
		{
			this.primaryButtonListener = primaryButtonListener;
			return this;
		}

		public BbrGroupButtonBuilder withSecundaryButtonListener(ClickListener secundaryButtonListener)
		{
			this.secundaryButtonListener = secundaryButtonListener;
			return this;
		}

		public BbrGroupButtonBuilder withCancelButtonListener(ClickListener cancelButtonListener)
		{
			this.cancelButtonListener = cancelButtonListener;
			return this;
		}

		public BbrGroupButtonBuilder withPrimaryButtonClickShortcut(int primaryButtonClickShortcut)
		{
			this.primaryButtonClickShortcut = primaryButtonClickShortcut;
			return this;
		}

		public BbrGroupButtonBuilder withSecundaryButtonClickShortcut(int secundaryButtonClickShortcut)
		{
			this.secundaryButtonClickShortcut = secundaryButtonClickShortcut;
			return this;
		}

		public BbrGroupButtonBuilder withCancelButtonClickShortcut(int cancelButtonClickShortcut)
		{
			this.cancelButtonClickShortcut = cancelButtonClickShortcut;
			return this;
		}

		public BbrGroupButtonBuilder withPrimaryButton(boolean primaryButton)
		{
			this.primaryButton = primaryButton;
			return this;
		}

		public BbrGroupButtonBuilder withSecundaryButton(boolean secundaryButton)
		{
			this.secundaryButton = secundaryButton;
			return this;
		}

		public BbrGroupButtonBuilder withCancelButton(boolean cancelButton)
		{
			this.cancelButton = cancelButton;
			return this;
		}

		public BbrGroupButtonBuilder withPrimaryButtonLabel(String primaryButtonLabel)
		{
			this.primaryButtonLabel = primaryButtonLabel;
			return this;
		}

		public BbrGroupButtonBuilder withSecundaryButtonLabel(String secundaryButtonLabel)
		{
			this.secundaryButtonLabel = secundaryButtonLabel;
			return this;
		}

		public BbrGroupButtonBuilder withCancelButtonLabel(String cancelButtonLabel)
		{
			this.cancelButtonLabel = cancelButtonLabel;
			return this;
		}

		public BbrGroupButtonBuilder withPrimaryButtonIcon(Resource primaryButtonIcon)
		{
			this.primaryButtonIcon = primaryButtonIcon;
			return this;
		}

		public BbrGroupButtonBuilder withSecundaryButtonIcon(Resource secundaryButtonIcon)
		{
			this.secundaryButtonIcon = secundaryButtonIcon;
			return this;
		}

		public BbrGroupButtonBuilder withCancelButtonIcon(Resource cancelButtonIcon)
		{
			this.cancelButtonIcon = cancelButtonIcon;
			return this;
		}

		public BbrButtonGroup build()
		{
			return new BbrButtonGroup(this);
		}
	}
}
