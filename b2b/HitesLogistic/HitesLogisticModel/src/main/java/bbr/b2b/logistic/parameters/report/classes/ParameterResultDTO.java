package bbr.b2b.logistic.parameters.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ParameterResultDTO extends BaseResultDTO {

	private ParameterDTO[] parameter;

	public ParameterDTO[] getParameter() {
		return parameter;
	}

	public void setParameter(ParameterDTO[] parameter) {
		this.parameter = parameter;
	}

}
