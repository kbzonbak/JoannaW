package bbr.b2b.portal.classes.ui.gallery;

import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.ui.gallery.interfaces.BbrGalleryItem;

public class GalleryItemFiles<T> extends VerticalLayout implements BbrGalleryItem<T>
{
	private static final long	serialVersionUID				= 1911278996160507512L;
	private T					item							= null;
	private boolean				isSelected						= false;

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

	private Resource	resource	= null;
	private String		fileName	= "";

	public GalleryItemFiles(Resource resource)
	{
		super();
		this.resource = resource;
		initializeView();
	}

	public GalleryItemFiles(T item)
	{
		super();
		this.item = item;
	}

	public GalleryItemFiles(T item, Resource resource, String fileName)
	{
		super();
		this.fileName = fileName;
		this.resource = resource;
		this.item = item;

		initializeView();
	}

	private void initializeView()
	{
		Image imgPerson = new Image(null, this.resource);
		imgPerson.setWidth("20px");
		imgPerson.setHeight("20px");
		imgPerson.setDescription(fileName);
		imgPerson.addStyleName("margin-left-14");
		
		Label lblFileName = new Label(this.fileName);
		lblFileName.setWidth("100%");
		lblFileName.setHeight("20px");
		lblFileName.addStyleName("margin-left-5");
		
		HorizontalLayout pnlCard = new HorizontalLayout(imgPerson, lblFileName);
		pnlCard.setWidth("100%");
		pnlCard.setHeight("20px");
		pnlCard.setMargin(false);
		pnlCard.setSpacing(false);
		pnlCard.setExpandRatio(lblFileName, 1F);
		
		
		this.setMargin(false);
		this.addComponent(pnlCard);
	}

}
