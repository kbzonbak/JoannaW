
package cl.chilexpress.integracionasistida;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;

import cl.chilexpress.ws.osb.ebo.headerrequest.DatosHeaderRequest;
import cl.chilexpress.ws.osb.ebo.headerresponse.DatosHeaderResponse;

@WebService(name = "IntegracionAsistidaPT", targetNamespace = "http://www.chilexpress.cl/IntegracionAsistida/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface IntegracionAsistidaPT {

	@WebMethod(operationName = "IntegracionAsistidaOp", action = "")
	@WebResult(name = "IntegracionAsistidaResponse", targetNamespace = "http://www.chilexpress.cl/IntegracionAsistida/")
	public IntegracionAsistidaResponse integracionAsistidaOp(@WebParam(name = "IntegracionAsistidaRequest", targetNamespace = "http://www.chilexpress.cl/IntegracionAsistida/")
	cl.chilexpress.integracionasistida.IntegracionAsistidaRequest IntegracionAsistidaRequest, @WebParam(name = "headerRequest", targetNamespace = "http://www.chilexpress.cl/IntegracionAsistida/", header = true)
	DatosHeaderRequest headerRequest, @WebParam(name = "headerResponse", targetNamespace = "http://www.chilexpress.cl/IntegracionAsistida/", header = true, mode = WebParam.Mode.OUT)
	Holder<DatosHeaderResponse> headerResponse);

}
