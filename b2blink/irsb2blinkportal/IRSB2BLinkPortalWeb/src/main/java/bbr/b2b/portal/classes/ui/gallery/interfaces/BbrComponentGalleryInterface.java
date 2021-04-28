package bbr.b2b.portal.classes.ui.gallery.interfaces;

import java.util.List;

import bbr.b2b.portal.classes.ui.gallery.BbrSelectionMode;

public interface BbrComponentGalleryInterface<T>
{
	public BbrSelectionMode getSelectionMode();
	public void setSelectionMode(BbrSelectionMode selectionMode);
	public List<BbrGalleryItem<T>> getGalleryItems();
	public void setGalleryItems(List<BbrGalleryItem<T>> galleryItems);
	public void setGalleryItems(BbrGalleryItem<T>... galleryItems);
	public List<BbrGalleryItem<T>> getSelectedItems();
	public List<T> getSelectedValues();
}
