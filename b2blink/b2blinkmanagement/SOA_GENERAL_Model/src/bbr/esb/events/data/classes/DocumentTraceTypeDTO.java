package bbr.esb.events.data.classes;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.events.data.interfaces.IDocumentTraceType;

public class DocumentTraceTypeDTO extends ElementDTO implements IDocumentTraceType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3567025164789718840L;

		
	private boolean clientVisible;	
	
	private String clienteDescription;
	
	private String code;

	

	public boolean isClientVisible() {
		return clientVisible;
	}

	public void setClientVisible(boolean clientVisible) {
		this.clientVisible = clientVisible;
	}	

	public String getClienteDescription() {
		return clienteDescription;
	}

	public void setClienteDescription(String clienteDescription) {
		this.clienteDescription = clienteDescription;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
