package bbr.b2b.logistic.ddcdeliveries.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.ddcdeliveries.data.interfaces.IDdcDeliveryStateType;

public class DdcDeliveryStateTypeW extends ElementDTO implements IDdcDeliveryStateType {

	private String code;
	private String name;
	private Boolean closed;
	private Boolean showable;
	private Boolean selectable;
	private String codews;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public Boolean getShowable() {
		return showable;
	}

	public void setShowable(Boolean showable) {
		this.showable = showable;
	}

	public Boolean getSelectable() {
		return selectable;
	}

	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}

	public String getCodews() {
		return codews;
	}

	public void setCodews(String codews) {
		this.codews = codews;
	}

}
