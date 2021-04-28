package bbr.b2b.portal.classes.utils.app;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;

public class BbrActionPanel extends HorizontalLayout
{
	private static final long	serialVersionUID	= 6934544576666396894L;

	private Image				image;
	private float				width;
	private float				height;

	public BbrActionPanel(Image image)
	{
		this(image, 300, 150);
	}

	public BbrActionPanel(Image image, float width, float height)
	{
		super();
		this.image = image;
		this.width = width;
		this.height = height;
	}

	public void initializeView()
	{
		// TODO Agregar Imagen al control, estilo y esas cosas
		this.addStyleName("bbr-action-panel");
		this.removeAllComponents();
		this.addComponent(image);
		this.image.setStyleName("bbr-action-panel-enabled-image");
		this.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		this.setWidth(width, Unit.PIXELS);
		this.setHeight(height, Unit.PIXELS);
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		String imageStyle = (enabled)?"bbr-action-panel-enabled-image":"bbr-action-panel-disabled-image";
		this.image.setStyleName(imageStyle);
	}
	
	
}
