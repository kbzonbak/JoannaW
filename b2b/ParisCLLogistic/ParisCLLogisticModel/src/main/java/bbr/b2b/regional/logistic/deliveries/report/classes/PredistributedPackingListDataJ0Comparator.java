package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.util.Comparator;

public class PredistributedPackingListDataJ0Comparator implements Comparator{
	
	public PredistributedPackingListDataJ0Comparator() {
				
	}

	public int compare(Object obj1, Object obj2) {

		PredistributedPackingListDataDTO bd1 = (PredistributedPackingListDataDTO) obj1;
		PredistributedPackingListDataDTO bd2 = (PredistributedPackingListDataDTO) obj2;

		// Compares by lpncode
		if (bd1.getLpncode().compareTo(bd2.getLpncode()) > 0) {
			//... If bigger than ..., we're done.
			return 1;
		}
		else if (bd1.getLpncode().compareTo(bd2.getLpncode()) < 0) {
			//... If smaller than ..., we're done.
			return -1;
		}
		else {
			//... If equals ..., we're done.
			return 0;	
		}
	}
}