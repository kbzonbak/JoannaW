
package corp.cencosud.dscl_omnichannel_int1859.consultstockebm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the corp.cencosud.dscl_omnichannel_int1859.consultstockebm package. 
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

    private final static QName _ConsultStockResponseEBM_QNAME = new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStockEBM", "ConsultStockResponseEBM");
    private final static QName _ConsultStockRequestEBM_QNAME = new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStockEBM", "ConsultStockRequestEBM");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: corp.cencosud.dscl_omnichannel_int1859.consultstockebm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultStockRequestEBMType }
     * 
     */
    public ConsultStockRequestEBMType createConsultStockRequestEBMType() {
        return new ConsultStockRequestEBMType();
    }

    /**
     * Create an instance of {@link ConsultStockRequestEBMDataAreaRequestType }
     * 
     */
    public ConsultStockRequestEBMDataAreaRequestType createConsultStockRequestEBMDataAreaRequestType() {
        return new ConsultStockRequestEBMDataAreaRequestType();
    }

    /**
     * Create an instance of {@link ConsultStockResponseEBMType }
     * 
     */
    public ConsultStockResponseEBMType createConsultStockResponseEBMType() {
        return new ConsultStockResponseEBMType();
    }

    /**
     * Create an instance of {@link ConsultStockResponseEBMDataAreaResponseType }
     * 
     */
    public ConsultStockResponseEBMDataAreaResponseType createConsultStockResponseEBMDataAreaResponseType() {
        return new ConsultStockResponseEBMDataAreaResponseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultStockResponseEBMType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStockEBM", name = "ConsultStockResponseEBM")
    public JAXBElement<ConsultStockResponseEBMType> createConsultStockResponseEBM(ConsultStockResponseEBMType value) {
        return new JAXBElement<ConsultStockResponseEBMType>(_ConsultStockResponseEBM_QNAME, ConsultStockResponseEBMType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultStockRequestEBMType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStockEBM", name = "ConsultStockRequestEBM")
    public JAXBElement<ConsultStockRequestEBMType> createConsultStockRequestEBM(ConsultStockRequestEBMType value) {
        return new JAXBElement<ConsultStockRequestEBMType>(_ConsultStockRequestEBM_QNAME, ConsultStockRequestEBMType.class, null, value);
    }

}
