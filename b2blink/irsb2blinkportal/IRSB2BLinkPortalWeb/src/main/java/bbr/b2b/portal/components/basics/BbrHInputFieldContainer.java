package bbr.b2b.portal.components.basics;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class BbrHInputFieldContainer extends HorizontalLayout
{
	// Constants
	private static final long	serialVersionUID		= -8821275180088675109L;
	private static String		DEFAULT_WIDTH			= "80px";
	// Components
	public Component			component				= null;
	public Label				label					= null;
	// Variables
	private String				labelStyle				= null;

	public void setLabelStyle(String labelStyle)
	{
		this.labelStyle = labelStyle;
	}

	public Label getLabel()
	{
		return label;
	}

	/**
	 * Returns a panel that works to group a {@link caption} and
	 * a {@link component} in a HorizontalLayout
	 * <p>
	 * By default label witdh is 80px (DEFAULT_WIDTH)
	 * 
	 * @param caption
	 *            sets Label width {@link caption} to the left
	 * @param component
	 *            sets {@link component} to the right
	 */
	public BbrHInputFieldContainer(String caption, Component component)
	{
		// -> Por defecto el label tiene 80px de width
		this(caption, DEFAULT_WIDTH, component);
	}

	/**
	 * Returns a panel that works to group a {@link caption} and
	 * a {@link component} in a HorizontalLayout
	 * 
	 * @param caption
	 *            sets Label width {@link caption} to the left
	 * @param labelWidth
	 *            sets custom label width
	 * @param component
	 *            sets {@link component} to the right
	 */
	public BbrHInputFieldContainer(String caption, String labelWidth, Component component)
	{
		// -> Label
		this.label = new Label(caption);
		this.label.setWidth(labelWidth);
		this.label.addStyleName(this.labelStyle);
		// -> Component
		this.component = component;
		// -> HorizontalLayout
		this.addComponents(this.label, this.component);
		this.setExpandRatio(this.component, 1F);
		// ----------------
		this.setSizeFull();
	}

	/**
	 * Returns a panel that works to group a {@link caption} and
	 * a {@link component} in a HorizontalLayout
	 * 
	 */
	public BbrHInputFieldContainer(BbrHInputFieldContainerBuilder bbrHInputFieldContainerBuilder)
	{
		// -> Label
		if (bbrHInputFieldContainerBuilder.caption != null)
		{
			this.label = new Label(bbrHInputFieldContainerBuilder.caption);
			this.label.setWidth(bbrHInputFieldContainerBuilder.labelWidth);
			this.label.addStyleName(bbrHInputFieldContainerBuilder.labelStyle);
		}
		// -> Component
		if (bbrHInputFieldContainerBuilder.component != null)
		{
			this.component = bbrHInputFieldContainerBuilder.component;
			if (this.label != null)
			{
				this.addComponents(this.label, bbrHInputFieldContainerBuilder.component);
			}
			else
			{
				this.addComponent(bbrHInputFieldContainerBuilder.component);
			}
			this.setExpandRatio(bbrHInputFieldContainerBuilder.component, 1F);
		}
		else
		{
			if (this.label != null)
			{
				this.addComponents(this.label);
				this.setExpandRatio(this.label, 1F);
			}
		}
		// ----------------
		if (bbrHInputFieldContainerBuilder.width == null && bbrHInputFieldContainerBuilder.height == null)
		{
			this.setSizeFull();
		}
		else
		{
			this.setWidth(bbrHInputFieldContainerBuilder.width);
			this.setHeight(bbrHInputFieldContainerBuilder.height);
		}
		this.setVisible(bbrHInputFieldContainerBuilder.visible);
	}

	/**
	 * This is the builder pattern class that returns a panel that works to
	 * group a {@link caption} and
	 * a {@link component} in a HorizontalLayout
	 */
	public static class BbrHInputFieldContainerBuilder
	{
		// Variables
		private String		caption		= null;
		private String		labelStyle	= null;
		private String		labelWidth	= DEFAULT_WIDTH;
		private String		width		= null;
		private String		height		= null;
		private Component	component	= null;
		private boolean		visible		= true;

		public BbrHInputFieldContainerBuilder withComponent(Component component)
		{
			this.component = component;
			return this;
		}

		public BbrHInputFieldContainerBuilder withLabelStyle(String labelStyle)
		{
			this.labelStyle = labelStyle;
			return this;
		}

		public BbrHInputFieldContainerBuilder withLabelWidth(String labelWidth)
		{
			this.labelWidth = labelWidth;
			return this;
		}
		
		/**
		 * Set {@link width} , height will be null until you set it too
		*/
		public BbrHInputFieldContainerBuilder withWidth(String width)
		{
			this.width = width;
			return this;
		}
		/**
		 * Set {@link heigth} , width will be null until you set it too
		*/
		public BbrHInputFieldContainerBuilder withHeigth(String heigth)
		{
			this.height = heigth;
			return this;
		}

		public BbrHInputFieldContainerBuilder withCaption(String caption)
		{
			this.caption = caption;
			return this;
		}

		public BbrHInputFieldContainerBuilder withVisibility(boolean visible)
		{
			this.visible = visible;
			return this;
		}

		public BbrHInputFieldContainer build()
		{
			return new BbrHInputFieldContainer(this);
		}

	}

}
