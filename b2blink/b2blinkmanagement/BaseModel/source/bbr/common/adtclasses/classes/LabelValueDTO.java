package bbr.common.adtclasses.classes;

import java.io.Serializable;

public class LabelValueDTO implements Serializable {

	private long id;
	private String code;
	private String label;

	public LabelValueDTO() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
