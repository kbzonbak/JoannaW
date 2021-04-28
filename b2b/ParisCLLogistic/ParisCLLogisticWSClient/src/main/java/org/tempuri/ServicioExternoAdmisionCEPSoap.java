
package org.tempuri;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "ServicioExternoAdmisionCEPSoap", targetNamespace = "http://tempuri.org/")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ServicioExternoAdmisionCEPSoap {

	@WebMethod(operationName = "admitirEnvio", action = "http://tempuri.org/admitirEnvio")
	@WebResult(name = "admitirEnvioResult", targetNamespace = "http://tempuri.org/")
	public RespuestaAdmisionTO admitirEnvio(@WebParam(name = "usuario", targetNamespace = "http://tempuri.org/")
	String usuario, @WebParam(name = "contrasena", targetNamespace = "http://tempuri.org/")
	String contrasena, @WebParam(name = "admisionTo", targetNamespace = "http://tempuri.org/")
	AdmisionTO admisionTo);

}
