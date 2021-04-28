package bbr.b2b.logistic.parameters.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IParameter extends IElement {

	public String getCode();
	public String getDescription();
	public String getValue();
	public String getUnit();
	public Boolean getVisible();
	public Boolean getActive();
	public LocalDateTime getCreationdate();
	public LocalDateTime getModificationdate();
	public void setCode(String code);
	public void setDescription(String description);
	public void setValue(String value);
	public void setUnit(String unit);
	public void setVisible(Boolean visible);
	public void setActive(Boolean active);
	public void setCreationdate(LocalDateTime creationdate);
	public void setModificationdate(LocalDateTime modificationdate);
}
