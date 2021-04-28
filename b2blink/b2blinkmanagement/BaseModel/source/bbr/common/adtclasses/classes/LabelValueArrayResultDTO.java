package bbr.common.adtclasses.classes;

public class LabelValueArrayResultDTO extends BaseResultDTO {

	private LabelValueDTO[] labelValueWs = null;

	public LabelValueArrayResultDTO() {
		super();
	}

	public LabelValueDTO[] getLabelValueWs() {
		return labelValueWs;
	}

	public void setLabelValueWs(LabelValueDTO[] labelValueWs) {
		this.labelValueWs = labelValueWs;
	}

}
