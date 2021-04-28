package bbr.b2b.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;

public class BlockingTypeArrayResultDTO extends BaseResultsDTO{

	private BlockingTypeDTO[] blockingType;

	public BlockingTypeDTO[] getBlockingType() {
		return blockingType;
	}

	public void setBlockingTypes(BlockingTypeDTO[] blockingType) {
		this.blockingType = blockingType;
	}
}
