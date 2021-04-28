package bbr.b2b.regional.logistic.utils;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter {
	private String extension = "";
	private String prefijo	= ""; 
	
	public FileFilter() {
		this.extension = "*";
	}

	public FileFilter(String extension) {
		this.extension = extension;
	}

	public FileFilter(String prefijo, String extension){
		this.prefijo = prefijo;
		this.extension = extension;
	}
	
	public boolean accept(File dir, String name) {
		String start = "";
		String end = "";
		if (dir.exists()) {
			
			if (prefijo.equalsIgnoreCase("") || name.startsWith(prefijo)){
				if (extension.equalsIgnoreCase("gz")) {
					if (name.endsWith(extension)) {
						return true;
					} else {
						return false;
					}

				} else if (extension.equalsIgnoreCase("trg")) {
					if (name.toLowerCase().endsWith(extension)) {
						return true;
					} else {
						return false;
					}

				} else if (extension.equalsIgnoreCase("xml")) {
					if (name.toLowerCase().endsWith(extension)) {
						return true;
					} else {
						return false;
					}
				} else if (extension.equalsIgnoreCase("csv")) {
					if (name.toLowerCase().endsWith(extension)) {
						return true;
					} else {
						return false;
					}
				}else {
					start = extension.substring(0, 3);
					end = extension.substring(3, extension.length());
					if ((name.startsWith(start)) && (name.endsWith(end))) {
						return true;
					} else {
						return false;
					}
				}
			}
			else{
				return false;
			}			
		}
		return false;
	}
};
