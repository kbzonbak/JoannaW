
package corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplier;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierebm.GetCapacityBySupplierRequestEBMType;
import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierebm.GetCapacityBySupplierResponseEBMType;

@WebService(name = "GetCapacityBySupplier", targetNamespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplier")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface GetCapacityBySupplier {

	@WebMethod(operationName = "GetCapacityBySupplier", action = "http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplier")
	@WebResult(name = "GetCapacityBySupplierResponseEBM", targetNamespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierEBM")
	public GetCapacityBySupplierResponseEBMType getCapacityBySupplier(@WebParam(name = "GetCapacityBySupplierRequestEBM", targetNamespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierEBM")
	GetCapacityBySupplierRequestEBMType GetCapacityBySupplierRequestEBM);

}
