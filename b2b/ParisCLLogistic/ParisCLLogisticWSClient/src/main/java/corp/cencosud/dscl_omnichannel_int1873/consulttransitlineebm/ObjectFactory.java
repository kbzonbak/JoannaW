
package corp.cencosud.dscl_omnichannel_int1873.consulttransitlineebm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the corp.cencosud.dscl_omnichannel_int1873.consulttransitlineebm package. 
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

    private final static QName _ConsultTransitLineRequestEBM_QNAME = new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLineEBM", "ConsultTransitLineRequestEBM");
    private final static QName _ConsultTransitLineResponseEBM_QNAME = new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLineEBM", "ConsultTransitLineResponseEBM");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: corp.cencosud.dscl_omnichannel_int1873.consulttransitlineebm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultTransitLineRequestEBMType }
     * 
     */
    public ConsultTransitLineRequestEBMType createConsultTransitLineRequestEBMType() {
        return new ConsultTransitLineRequestEBMType();
    }

    /**
     * Create an instance of {@link ConsultTransitTimeResponseEBMDataAreaResponseType }
     * 
     */
    public ConsultTransitTimeResponseEBMDataAreaResponseType createConsultTransitTimeResponseEBMDataAreaResponseType() {
        return new ConsultTransitTimeResponseEBMDataAreaResponseType();
    }

    /**
     * Create an instance of {@link ConsultTransitLineResponseEBMType }
     * 
     */
    public ConsultTransitLineResponseEBMType createConsultTransitLineResponseEBMType() {
        return new ConsultTransitLineResponseEBMType();
    }

    /**
     * Create an instance of {@link ConsultTransitRequestEBMDataAreaRequestType }
     * 
     */
    public ConsultTransitRequestEBMDataAreaRequestType createConsultTransitRequestEBMDataAreaRequestType() {
        return new ConsultTransitRequestEBMDataAreaRequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultTransitLineRequestEBMType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLineEBM", name = "ConsultTransitLineRequestEBM")
    public JAXBElement<ConsultTransitLineRequestEBMType> createConsultTransitLineRequestEBM(ConsultTransitLineRequestEBMType value) {
        return new JAXBElement<ConsultTransitLineRequestEBMType>(_ConsultTransitLineRequestEBM_QNAME, ConsultTransitLineRequestEBMType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultTransitLineResponseEBMType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLineEBM", name = "ConsultTransitLineResponseEBM")
    public JAXBElement<ConsultTransitLineResponseEBMType> createConsultTransitLineResponseEBM(ConsultTransitLineResponseEBMType value) {
        return new JAXBElement<ConsultTransitLineResponseEBMType>(_ConsultTransitLineResponseEBM_QNAME, ConsultTransitLineResponseEBMType.class, null, value);
    }

}
