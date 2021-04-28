package bbr.b2b.regional.logistic.utils;

import java.io.File;
import java.util.Comparator;


public class FileComparator implements Comparator {

	// Comparator interface requires defining compare method.
	public int compare(Object obj1, Object obj2) {

		File file1 = (File) obj1;
		File file2 = (File) obj2;
		long comp = file1.getName().compareToIgnoreCase(file2.getName());

		if (comp > 0)
			//... If different lengths, we're done.				 
			return 1;
		else if (comp < 0)
			return -1;
		else {
			//... If equal lengths, sort per item_id.
			comp = file1.lastModified() - file2.lastModified();
			if (comp > 0)
				return 1;
			else if (comp < 0)
				return -1;
			else
				return 0;
		}
	}
}
