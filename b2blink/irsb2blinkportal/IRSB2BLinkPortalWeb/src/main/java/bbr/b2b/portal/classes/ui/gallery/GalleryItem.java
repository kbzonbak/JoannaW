package bbr.b2b.portal.classes.ui.gallery;

import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.ui.gallery.interfaces.BbrGalleryItem;

public class GalleryItem<T> extends VerticalLayout implements BbrGalleryItem<T>
{
	private static final long	serialVersionUID	= 1911278996160507512L;
	private T					item				= null;
	private boolean				isSelected			= false;

	@Override
	public boolean isSelected()
	{
		return this.isSelected;
	}

	@Override
	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}

	@Override
	public T getValue()
	{
		return item;
	}

	@Override
	public Component getComponent()
	{
		return this;
	}

	private Resource resource = null;
	private String description = "";

	public GalleryItem(Resource resource)
	{
		super();
		this.resource = resource;
		initializeView();
	}

	public GalleryItem(T item)
	{
		super();
		this.item = item;
	}

	public GalleryItem(T item, Resource resource, String description)
	{
		super();
		this.description  = description;
		this.resource = resource;
		this.item = item;

		initializeView();
	}
	
	private void initializeView()
	{
		Image imgPerson = new Image(null, this.resource);
		imgPerson.setSizeFull();
		imgPerson.setDescription(description);
		this.setMargin(false);
		this.addComponent(imgPerson);
	}
}
