
package cl.chilexpress.ws.osb.interno.com.generarintegracionasistidaresp;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cl.chilexpress.ws.osb.interno.com.generarintegracionasistidaresp package. 
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

    private final static QName _RespGenerarIntegracionAsistida_QNAME = new QName("http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaResp", "respGenerarIntegracionAsistida");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cl.chilexpress.ws.osb.interno.com.generarintegracionasistidaresp
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DatosOTType }
     * 
     */
    public DatosOTType createDatosOTType() {
        return new DatosOTType();
    }

    /**
     * Create an instance of {@link GenerarIntegracionAsistidaResponseType }
     * 
     */
    public GenerarIntegracionAsistidaResponseType createGenerarIntegracionAsistidaResponseType() {
        return new GenerarIntegracionAsistidaResponseType();
    }

    /**
     * Create an instance of {@link DatosEtiquetaType }
     * 
     */
    public DatosEtiquetaType createDatosEtiquetaType() {
        return new DatosEtiquetaType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerarIntegracionAsistidaResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaResp", name = "respGenerarIntegracionAsistida")
    public JAXBElement<GenerarIntegracionAsistidaResponseType> createRespGenerarIntegracionAsistida(GenerarIntegracionAsistidaResponseType value) {
        return new JAXBElement<GenerarIntegracionAsistidaResponseType>(_RespGenerarIntegracionAsistida_QNAME, GenerarIntegracionAsistidaResponseType.class, null, value);
    }

}
