
package corp.cencosud.dscl_omnichannel_int1859.consultstock;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import corp.cencosud.dscl_omnichannel_int1859.consultstockebm.ConsultStockRequestEBMType;
import corp.cencosud.dscl_omnichannel_int1859.consultstockebm.ConsultStockResponseEBMType;

@WebService(name = "ConsultStockPortType", targetNamespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStock")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ConsultStockPortType {

	@WebMethod(operationName = "ConsultStock", action = "http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStock")
	@WebResult(name = "ConsultStockResponseEBM", targetNamespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStockEBM")
	public ConsultStockResponseEBMType consultStock(@WebParam(name = "ConsultStockRequestEBM", targetNamespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStockEBM")
	ConsultStockRequestEBMType ConsultStockRequestEBM);

}
