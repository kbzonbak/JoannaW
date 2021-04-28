
package cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq package. 
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

    private final static QName _ReqGenerarIntegracionAsistida_QNAME = new QName("http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaReq", "reqGenerarIntegracionAsistida");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RemitenteType }
     * 
     */
    public RemitenteType createRemitenteType() {
        return new RemitenteType();
    }

    /**
     * Create an instance of {@link DestinatarioType }
     * 
     */
    public DestinatarioType createDestinatarioType() {
        return new DestinatarioType();
    }

    /**
     * Create an instance of {@link GenerarIntegracionAsistidaRequestType }
     * 
     */
    public GenerarIntegracionAsistidaRequestType createGenerarIntegracionAsistidaRequestType() {
        return new GenerarIntegracionAsistidaRequestType();
    }

    /**
     * Create an instance of {@link DireccionType }
     * 
     */
    public DireccionType createDireccionType() {
        return new DireccionType();
    }

    /**
     * Create an instance of {@link PiezaType }
     * 
     */
    public PiezaType createPiezaType() {
        return new PiezaType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerarIntegracionAsistidaRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaReq", name = "reqGenerarIntegracionAsistida")
    public JAXBElement<GenerarIntegracionAsistidaRequestType> createReqGenerarIntegracionAsistida(GenerarIntegracionAsistidaRequestType value) {
        return new JAXBElement<GenerarIntegracionAsistidaRequestType>(_ReqGenerarIntegracionAsistida_QNAME, GenerarIntegracionAsistidaRequestType.class, null, value);
    }

}
