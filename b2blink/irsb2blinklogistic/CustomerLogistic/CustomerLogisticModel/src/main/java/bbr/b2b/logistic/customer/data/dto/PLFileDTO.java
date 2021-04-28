package bbr.b2b.logistic.customer.data.dto;

import java.util.List;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class PLFileDTO  extends BaseResultDTO  {
	
	List<String> files;
	List<BaseResultDTO> errores;
	String filename;

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public List<BaseResultDTO> getErrores() {
		return errores;
	}

	public void setErrores(List<BaseResultDTO> errores) {
		this.errores = errores;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
