
package corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierebm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierebm package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetCapacityBySupplierRequestEBM_QNAME = new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierEBM", "GetCapacityBySupplierRequestEBM");
    private final static QName _GetCapacityBySupplierResponseEBM_QNAME = new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierEBM", "GetCapacityBySupplierResponseEBM");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierebm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCapacityBySupplierDataAreaResponseType }
     * 
     */
    public GetCapacityBySupplierDataAreaResponseType createGetCapacityBySupplierDataAreaResponseType() {
        return new GetCapacityBySupplierDataAreaResponseType();
    }

    /**
     * Create an instance of {@link GetCapacityBySupplierResponseEBMType }
     * 
     */
    public GetCapacityBySupplierResponseEBMType createGetCapacityBySupplierResponseEBMType() {
        return new GetCapacityBySupplierResponseEBMType();
    }

    /**
     * Create an instance of {@link GetCapacityBySupplierDataAreaRequestType }
     * 
     */
    public GetCapacityBySupplierDataAreaRequestType createGetCapacityBySupplierDataAreaRequestType() {
        return new GetCapacityBySupplierDataAreaRequestType();
    }

    /**
     * Create an instance of {@link GetCapacityBySupplierRequestEBMType }
     * 
     */
    public GetCapacityBySupplierRequestEBMType createGetCapacityBySupplierRequestEBMType() {
        return new GetCapacityBySupplierRequestEBMType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCapacityBySupplierRequestEBMType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierEBM", name = "GetCapacityBySupplierRequestEBM")
    public JAXBElement<GetCapacityBySupplierRequestEBMType> createGetCapacityBySupplierRequestEBM(GetCapacityBySupplierRequestEBMType value) {
        return new JAXBElement<GetCapacityBySupplierRequestEBMType>(_GetCapacityBySupplierRequestEBM_QNAME, GetCapacityBySupplierRequestEBMType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCapacityBySupplierResponseEBMType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierEBM", name = "GetCapacityBySupplierResponseEBM")
    public JAXBElement<GetCapacityBySupplierResponseEBMType> createGetCapacityBySupplierResponseEBM(GetCapacityBySupplierResponseEBMType value) {
        return new JAXBElement<GetCapacityBySupplierResponseEBMType>(_GetCapacityBySupplierResponseEBM_QNAME, GetCapacityBySupplierResponseEBMType.class, null, value);
    }

}
