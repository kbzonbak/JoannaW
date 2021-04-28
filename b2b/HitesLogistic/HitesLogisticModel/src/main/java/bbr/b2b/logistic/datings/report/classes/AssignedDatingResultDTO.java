package bbr.b2b.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class AssignedDatingResultDTO extends BaseResultDTO {

	private AssignedDatingDataDTO[] assignedDatingData;
	private AssignedResourceBlockingDataDTO[] assignedBlockadeData;
	private AssignedPreDatingResourceGroupDataDTO[] assignedPreDatingResourceGroupData;
	private AssignedDatingTotalizerByDockDTO[] assignedDatingTotalizerByDock;

	public AssignedDatingDataDTO[] getAssignedDatingData() {
		return assignedDatingData;
	}

	public AssignedResourceBlockingDataDTO[] getAssignedBlockadeData() {
		return assignedBlockadeData;
	}

	public AssignedPreDatingResourceGroupDataDTO[] getAssignedPreDatingResourceGroupData() {
		return assignedPreDatingResourceGroupData;
	}

	public AssignedDatingTotalizerByDockDTO[] getAssignedDatingTotalizerByDock() {
		return assignedDatingTotalizerByDock;
	}

	public void setAssignedDatingData(AssignedDatingDataDTO[] assignedDatingData) {
		this.assignedDatingData = assignedDatingData;
	}

	public void setAssignedBlockadeData(AssignedResourceBlockingDataDTO[] assignedBlockadeData) {
		this.assignedBlockadeData = assignedBlockadeData;
	}

	public void setAssignedPreDatingResourceGroupData(
			AssignedPreDatingResourceGroupDataDTO[] assignedPreDatingResourceGroupData) {
		this.assignedPreDatingResourceGroupData = assignedPreDatingResourceGroupData;
	}

	public void setAssignedDatingTotalizerByDock(AssignedDatingTotalizerByDockDTO[] assignedDatingTotalizerByDock) {
		this.assignedDatingTotalizerByDock = assignedDatingTotalizerByDock;
	}

}
