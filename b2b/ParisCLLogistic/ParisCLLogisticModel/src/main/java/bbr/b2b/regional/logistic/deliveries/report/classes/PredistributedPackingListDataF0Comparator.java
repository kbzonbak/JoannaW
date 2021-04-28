package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.util.Comparator;

public class PredistributedPackingListDataF0Comparator implements Comparator{
	
	public PredistributedPackingListDataF0Comparator() {
				
	}

	public int compare(Object obj1, Object obj2) {

		PredistributedPackingListDataDTO bd1 = (PredistributedPackingListDataDTO) obj1;
		PredistributedPackingListDataDTO bd2 = (PredistributedPackingListDataDTO) obj2;

		// Compares by iteminternalcode
		if (bd1.getIteminternalcode().compareTo(bd2.getIteminternalcode()) > 0) {
			//... If bigger than ..., we're done.
			return 1;
		}
		else if (bd1.getIteminternalcode().compareTo(bd2.getIteminternalcode()) < 0) {
			//... If smaller than ..., we're done.
			return -1;
		}
		else {
			//... If equals, compares by taxdocumentnumber
			if (bd1.getTaxdocumentnumber().compareTo(bd2.getTaxdocumentnumber()) > 0) {
				//... If bigger than ..., we're done.
				return 1;
			}
			else if (bd1.getTaxdocumentnumber().compareTo(bd2.getTaxdocumentnumber()) < 0) {
				//... If smaller than ..., we're done.
				return -1;
			}
			else {
				//... If equals, compares by locationcode
				if (bd1.getLocationcode().compareTo(bd2.getLocationcode()) > 0) {
					//... If bigger than ..., we're done.
					return 1;
				}
				else if (bd1.getLocationcode().compareTo(bd2.getLocationcode()) < 0) {
					//... If smaller than ..., we're done.
					return -1;
				}
				else {
					//... If equals ..., we're done.
					return 0;
				}
			}
		}
	}
}
