/**
 * @author dvillanueva
 * Esta clase filtra por fecha de creación  
 */
package bbr.b2b.test.classes;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

public class LastModifiedDateFilter implements FileFilter {

	private Date date = null;

	public LastModifiedDateFilter() {
		this.date = new Date();
	}

	public LastModifiedDateFilter(Date date) {
		this.date = date;
	}

	public boolean accept(File file) {
		boolean result = file != null && file.exists() && file.lastModified() < date.getTime();
		return result;
	}
}
