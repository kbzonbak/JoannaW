package bbr.b2b.regional.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class AssignedDatingResultDTO extends BaseResultDTO {

	private AssignedDatingDataDTO[] assignedDatingDataWs;
	private AssignedResourceBlockingDataDTO[] assignedBlockadeDataWs;
	private AssignedDatingTotalizerByDockDTO[] assignedDatingTotalizerDTOs;
	private DockTypeDTO[] dockTypeDTOs;
	
	public AssignedDatingDataDTO[] getAssignedDatingDataWs() {
		return assignedDatingDataWs;
	}

	public void setAssignedDatingDataWs(AssignedDatingDataDTO[] assignedDatingDataWs) {
		this.assignedDatingDataWs = assignedDatingDataWs;
	}

	public AssignedResourceBlockingDataDTO[] getAssignedBlockadeDataWs() {
		return assignedBlockadeDataWs;
	}

	public void setAssignedBlockadeDataWs(AssignedResourceBlockingDataDTO[] assignedBlockadeDataWs) {
		this.assignedBlockadeDataWs = assignedBlockadeDataWs;
	}

	public AssignedDatingTotalizerByDockDTO[] getAssignedDatingTotalizerDTOs() {
		return assignedDatingTotalizerDTOs;
	}

	public void setAssignedDatingTotalizerDTOs(AssignedDatingTotalizerByDockDTO[] assignedDatingTotalizerDTOs) {
		this.assignedDatingTotalizerDTOs = assignedDatingTotalizerDTOs;
	}

	public DockTypeDTO[] getDockTypeDTOs() {
		return dockTypeDTOs;
	}

	public void setDockTypeDTOs(DockTypeDTO[] dockTypeDTOs) {
		this.dockTypeDTOs = dockTypeDTOs;
	}
}
