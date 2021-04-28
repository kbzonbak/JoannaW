package bbr.b2b.portal.classes.ui.gallery.interfaces;

import com.vaadin.ui.Component;

public interface BbrGalleryItem<T>
{
	public boolean isSelected();
	public void setSelected(boolean isSelected);
	public T getValue();
	public Component getComponent();
}
