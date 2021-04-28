package bbr.esb.events.data.interfaces;

import bbr.common.adtclasses.interfaces.IElement;

public interface IDocumentTraceType extends IElement {

	public boolean isClientVisible();

	public void setClientVisible(boolean clientVisible);

	public String getClienteDescription() ;

	public void setClienteDescription(String clienteDescription);
	
	public String getCode();

	public void setCode(String code);
}
