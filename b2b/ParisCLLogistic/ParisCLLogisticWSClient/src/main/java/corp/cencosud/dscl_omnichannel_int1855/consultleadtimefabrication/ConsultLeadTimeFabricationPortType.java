
package corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabrication;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabricationebm.ConsultLeadTimeFabricationEBMType;
import corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabricationebm.ConsultLeadTimeFabricationResponseEBMType;

@WebService(name = "ConsultLeadTimeFabricationPortType", targetNamespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1855/ConsultLeadTimeFabrication")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ConsultLeadTimeFabricationPortType {

	@WebMethod(operationName = "ConsultLeadTimeFabrication", action = "http://www.cencosud.corp/DSCL_OmniChannel_INT1855/ConsultLeadTimeFabrication")
	@WebResult(name = "ConsultLeadTimeFabricationResponseEBM", targetNamespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1855/ConsultLeadTimeFabricationEBM")
	public ConsultLeadTimeFabricationResponseEBMType consultLeadTimeFabrication(@WebParam(name = "ConsultLeadTimeFabricationRequestEBM", targetNamespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1855/ConsultLeadTimeFabricationEBM")
	ConsultLeadTimeFabricationEBMType ConsultLeadTimeFabricationRequestEBM);

}
