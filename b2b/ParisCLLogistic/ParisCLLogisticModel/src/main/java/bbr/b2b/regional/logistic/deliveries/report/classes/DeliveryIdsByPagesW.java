package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;


public class DeliveryIdsByPagesW extends BaseResultDTO{

	private Long[] deliveryIds;
	private int totalRows;
		
	public Long[] getDeliveryIds() {
		return deliveryIds;
	}
	public void setDeliveryIds(Long[] deliveryIds) {
		this.deliveryIds = deliveryIds;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}	
}
