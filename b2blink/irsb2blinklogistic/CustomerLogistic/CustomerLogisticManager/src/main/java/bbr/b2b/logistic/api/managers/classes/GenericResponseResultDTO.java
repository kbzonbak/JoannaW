package bbr.b2b.logistic.api.managers.classes;

import java.io.Reader;

public class GenericResponseResultDTO extends ResponseResultDTO {

	private Reader genericReader;
	private String jsonResponse;
	
	public Reader getGenericReader() {		
		return genericReader;
	}

	public void setGenericReader(Reader genericReader) {
		this.genericReader = genericReader;
	}

	public String getJsonResponse() {
		return jsonResponse;
	}

	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}	
}
