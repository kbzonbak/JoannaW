package bbr.common.adtclasses.classes;

import java.io.Serializable;

import bbr.common.adtclasses.interfaces.IElement;

public class ElementDTO implements IElement, Serializable {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
