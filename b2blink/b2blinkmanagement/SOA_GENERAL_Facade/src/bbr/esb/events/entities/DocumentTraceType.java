package bbr.esb.events.entities;

import bbr.esb.events.data.interfaces.IDocumentTraceType;

public class DocumentTraceType implements IDocumentTraceType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3567025164789718840L;

	private Long id;
	
	private boolean clientVisible;	

	private String clienteDescription;
	
	private String code;
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	
	
}
