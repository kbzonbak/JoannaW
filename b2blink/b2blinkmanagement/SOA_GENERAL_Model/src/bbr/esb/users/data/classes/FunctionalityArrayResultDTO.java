package bbr.esb.users.data.classes;

import bbr.common.adtclasses.classes.BaseResultDTO;

public class FunctionalityArrayResultDTO extends BaseResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -99153860911912771L;

	private FunctionalityDTO[] functionalities;

	public FunctionalityDTO[] getFunctionalities() {
		return functionalities;
	}

	public void setFunctionalities(FunctionalityDTO[] functionalities) {
		this.functionalities = functionalities;
	}

}
