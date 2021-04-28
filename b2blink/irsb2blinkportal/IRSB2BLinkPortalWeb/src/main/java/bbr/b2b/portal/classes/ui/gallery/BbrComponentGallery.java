package bbr.b2b.portal.classes.ui.gallery;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.ui.gallery.interfaces.BbrComponentGalleryInterface;
import bbr.b2b.portal.classes.ui.gallery.interfaces.BbrGalleryItem;

public class BbrComponentGallery<T> extends CssLayout implements BbrComponentGalleryInterface<T>
{
	private static final long						serialVersionUID	= 349382104940304257L;

	private Map<BbrGalleryItem<T>, Component>		mapItemComponents	= null;
	private Map<BbrGalleryItem<T>, VerticalLayout>	mapItemSourceLayout	= null;
	private BbrSelectionMode						selectionMode		= BbrSelectionMode.SINGLE;

	public BbrSelectionMode getSelectionMode()
	{
		return selectionMode;
	}

	public void setSelectionMode(BbrSelectionMode selectionMode)
	{
		this.selectionMode = selectionMode;
		this.initializeView();
	}

	private List<BbrGalleryItem<T>> galleryItems = null;

	public List<BbrGalleryItem<T>> getGalleryItems()
	{
		return galleryItems;
	}

	public void setGalleryItems(List<BbrGalleryItem<T>> galleryItems)
	{
		this.galleryItems = galleryItems;
		this.initializeView();
	}

	@SuppressWarnings("unchecked")
	public void setGalleryItems(BbrGalleryItem<T>... galleryItems)
	{
		this.galleryItems = (galleryItems != null && galleryItems.length > 0) ? Arrays.asList(galleryItems) : null;
		this.initializeView();
	}

	public List<BbrGalleryItem<T>> getSelectedItems()
	{
		List<BbrGalleryItem<T>> result = null;
		if (this.galleryItems != null && !this.galleryItems.isEmpty())
		{
			result = this.galleryItems.stream().filter(item -> item.isSelected() == true).collect(Collectors.toList());
		}
		return result;
	}

	public List<T> getSelectedValues()
	{
		List<T> result = null;
		if (this.galleryItems != null && !this.galleryItems.isEmpty())
		{
			result = this.galleryItems.stream().filter(item -> item.isSelected() == true).map(BbrGalleryItem::getValue).collect(Collectors.toList());
		}
		return result;
	}

	public BbrComponentGallery()
	{
		super();
	}

	public BbrComponentGallery(List<BbrGalleryItem<T>> galleryItems)
	{
		super();
		this.setGalleryItems(galleryItems);
	}

	@SuppressWarnings("unchecked")
	public BbrComponentGallery(BbrGalleryItem<T>... galleryItems)
	{
		super();
		this.setGalleryItems(galleryItems);
	}

	private void initializeView()
	{
		this.removeAllComponents();
		this.mapItemComponents = new HashMap<>();
		this.mapItemSourceLayout = new HashMap<>();
		
		// this.setStyleName("bbr-component-gallery");
		if (this.galleryItems != null && !this.galleryItems.isEmpty())
		{
			for (BbrGalleryItem<T> item : this.galleryItems)
			{
				if (item.getComponent() != null)
				{
					this.addComponent(wrapComponent(item));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private VerticalLayout wrapComponent(BbrGalleryItem<T> item)
	{
		VerticalLayout result = null;
		if (item != null && item.getComponent() != null)
		{
			Component component = item.getComponent();
			component.setSizeFull();

			result = new VerticalLayout();
			result.setHeight("100px");
			result.setWidth("100px");
			result.setSpacing(false);
			result.setMargin(false);
			result.addComponent(component);
			result.setStyleName("bbr-gallery-item");
			result.addLayoutClickListener(new LayoutClickListener()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void layoutClick(LayoutClickEvent event)
				{
					VerticalLayout sourcePanel = (VerticalLayout) event.getComponent();

					if (sourcePanel != null && sourcePanel.getData() != null)
					{
						if (sourcePanel.getData() instanceof RadioButtonGroup<?>)
						{
							RadioButtonGroup<BbrGalleryItem<T>> item = (RadioButtonGroup<BbrGalleryItem<T>>) sourcePanel.getData();
							BbrGalleryItem<T> bbrGalleyItem = (BbrGalleryItem<T>) item.getData();

							deselectAllButSource(bbrGalleyItem);
							item.setSelectedItem(bbrGalleyItem);
						}
					}
				}
			});

			if (selectionMode != BbrSelectionMode.NONE)
			{
				Component selectComponent = null;
				if (selectionMode == BbrSelectionMode.SINGLE)
				{
					RadioButtonGroup<BbrGalleryItem<T>> rdbItem = new RadioButtonGroup<>();
					rdbItem.setItems(item);
					rdbItem.setData(item);
					rdbItem.setItemCaptionGenerator(galleryItem -> null);
					rdbItem.setHeight("25px");
					rdbItem.setWidth("25px");

					selectComponent = rdbItem;
				}
				else 
					if (selectionMode == BbrSelectionMode.MULTI)
				{
					CheckBox chkItem = new CheckBox();
					chkItem.addValueChangeListener(new ValueChangeListener<Boolean>()
					{
						private static final long serialVersionUID = 1269005851983034795L;

						@Override
						public void valueChange(ValueChangeEvent<Boolean> event)
						{
							if (event.getValue() == true)
							{
								((GalleryItem<?>) ((CheckBox) event.getComponent()).getData()).addStyleName("bbr-gallery-item-selected");
							}
							else
							{
								((GalleryItem<?>) ((CheckBox) event.getComponent()).getData()).removeStyleName("bbr-gallery-item-selected");
							}

						}
					});
					chkItem.setData(item);
					selectComponent = chkItem;

					// component.setStyleName("bbr-gallery-item-multi");
					// component.addStyleName("bbr-gallery-item");
					// selectComponent.setStyleName("bbr-gallery-item-selector-multi");
				}

				mapItemComponents.put(item, selectComponent);
				mapItemSourceLayout.put(item, result);
				
				result.setData(selectComponent);

				VerticalLayout pnlSelector = new VerticalLayout();
				pnlSelector.setMargin(false);
				pnlSelector.setSizeFull();
				pnlSelector.addComponent(selectComponent);

				result.addComponent(selectComponent);
				result.setComponentAlignment(selectComponent, Alignment.BOTTOM_CENTER);

				result.setExpandRatio(component, 1F);

			}

		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected void deselectAllButSource(BbrGalleryItem<T> bbrGalleryItem)
	{
		if (this.galleryItems != null && !this.galleryItems.isEmpty())
		{
			this.galleryItems.forEach(item ->
			{
				VerticalLayout sourcePanel = mapItemSourceLayout.get(item);
				if (item != bbrGalleryItem)
				{
					sourcePanel.removeStyleName("bbr-gallery-item-selected");
					
					Component component = mapItemComponents.get(item);
					if (component instanceof RadioButtonGroup<?>)
					{
						((RadioButtonGroup<BbrGalleryItem<T>>) component).setSelectedItem(null);
						item.setSelected(false);
					}
				}
				else
				{
					sourcePanel.addStyleName("bbr-gallery-item-selected");
					item.setSelected(true);
				}
			});
		}
	}

}
