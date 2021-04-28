package bbr.b2b.logistic.dvrdeliveries.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDocumentType;

public class DocumentTypeW extends ElementDTO implements IDocumentType {

	private String code;
	private String name;
	private String type;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
