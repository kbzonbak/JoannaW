package bbr.esb.events.data.classes;

import java.util.ArrayList;

public class DocumentsToResendInitParamDTO {

	ArrayList<DocumentToResendInitParamDTO> documents;
	
	public DocumentsToResendInitParamDTO() {		
		documents = new ArrayList<DocumentToResendInitParamDTO>();
	}

	public ArrayList<DocumentToResendInitParamDTO> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<DocumentToResendInitParamDTO> documents) {
		this.documents = documents;
	}

	
	
}
