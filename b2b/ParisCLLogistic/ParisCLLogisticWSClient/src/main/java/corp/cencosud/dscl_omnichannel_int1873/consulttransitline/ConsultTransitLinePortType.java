
package corp.cencosud.dscl_omnichannel_int1873.consulttransitline;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import corp.cencosud.dscl_omnichannel_int1873.consulttransitlineebm.ConsultTransitLineRequestEBMType;
import corp.cencosud.dscl_omnichannel_int1873.consulttransitlineebm.ConsultTransitLineResponseEBMType;

@WebService(name = "ConsultTransitLinePortType", targetNamespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLine")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ConsultTransitLinePortType {

	@WebMethod(operationName = "ConsultTransitLine", action = "http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultTransitLine")
	@WebResult(name = "ConsultTransitLineResponseEBM", targetNamespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLineEBM")
	public ConsultTransitLineResponseEBMType consultTransitLine(@WebParam(name = "ConsultTransitLineRequestEBM", targetNamespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLineEBM")
	ConsultTransitLineRequestEBMType ConsultTransitLineRequestEBM);

}
