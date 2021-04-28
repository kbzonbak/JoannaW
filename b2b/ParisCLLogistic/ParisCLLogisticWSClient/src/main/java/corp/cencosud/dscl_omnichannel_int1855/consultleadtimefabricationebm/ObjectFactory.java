
package corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabricationebm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabricationebm package. 
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

    private final static QName _ConsultLeadTimeFabricationResponseEBM_QNAME = new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1855/ConsultLeadTimeFabricationEBM", "ConsultLeadTimeFabricationResponseEBM");
    private final static QName _ConsultLeadTimeFabricationRequestEBM_QNAME = new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1855/ConsultLeadTimeFabricationEBM", "ConsultLeadTimeFabricationRequestEBM");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabricationebm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultLeadTimeFabricationResponseEBMType }
     * 
     */
    public ConsultLeadTimeFabricationResponseEBMType createConsultLeadTimeFabricationResponseEBMType() {
        return new ConsultLeadTimeFabricationResponseEBMType();
    }

    /**
     * Create an instance of {@link ConsultLeadTimeFabricationResponseEBMDataAreaResponseType }
     * 
     */
    public ConsultLeadTimeFabricationResponseEBMDataAreaResponseType createConsultLeadTimeFabricationResponseEBMDataAreaResponseType() {
        return new ConsultLeadTimeFabricationResponseEBMDataAreaResponseType();
    }

    /**
     * Create an instance of {@link ConsultLeadTimeFabricationEBMType }
     * 
     */
    public ConsultLeadTimeFabricationEBMType createConsultLeadTimeFabricationEBMType() {
        return new ConsultLeadTimeFabricationEBMType();
    }

    /**
     * Create an instance of {@link ConsultLeadTimeFabricationEBMDataAreaRequestType }
     * 
     */
    public ConsultLeadTimeFabricationEBMDataAreaRequestType createConsultLeadTimeFabricationEBMDataAreaRequestType() {
        return new ConsultLeadTimeFabricationEBMDataAreaRequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultLeadTimeFabricationResponseEBMType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1855/ConsultLeadTimeFabricationEBM", name = "ConsultLeadTimeFabricationResponseEBM")
    public JAXBElement<ConsultLeadTimeFabricationResponseEBMType> createConsultLeadTimeFabricationResponseEBM(ConsultLeadTimeFabricationResponseEBMType value) {
        return new JAXBElement<ConsultLeadTimeFabricationResponseEBMType>(_ConsultLeadTimeFabricationResponseEBM_QNAME, ConsultLeadTimeFabricationResponseEBMType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultLeadTimeFabricationEBMType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1855/ConsultLeadTimeFabricationEBM", name = "ConsultLeadTimeFabricationRequestEBM")
    public JAXBElement<ConsultLeadTimeFabricationEBMType> createConsultLeadTimeFabricationRequestEBM(ConsultLeadTimeFabricationEBMType value) {
        return new JAXBElement<ConsultLeadTimeFabricationEBMType>(_ConsultLeadTimeFabricationRequestEBM_QNAME, ConsultLeadTimeFabricationEBMType.class, null, value);
    }

}
