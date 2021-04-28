package bbr.b2b.logistic.ddcdeliveries.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDdcFile extends IElement {

	public LocalDateTime getWhen();
	public void setWhen(LocalDateTime when);
	public String getFilename();
	public void setFilename(String filename);
	public String getFiletype();
	public void setFiletype(String filetype);
}
