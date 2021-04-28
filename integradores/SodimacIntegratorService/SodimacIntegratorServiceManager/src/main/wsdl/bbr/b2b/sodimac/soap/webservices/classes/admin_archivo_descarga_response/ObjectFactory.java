//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.08 a las 02:24:52 PM CLT 
//


package bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_response;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_response package. 
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

    private final static QName _AdminArchivoDescargaResponse_QNAME = new QName("http://b2b.falabella.com/schemas/ol/admin-archivo-descarga-response", "admin-archivo-descarga-response");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_response
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AdminArchivoDescargaResponse }
     * 
     */
    public AdminArchivoDescargaResponse createAdminArchivoDescargaResponse() {
        return new AdminArchivoDescargaResponse();
    }

    /**
     * Create an instance of {@link Messages }
     * 
     */
    public Messages createMessages() {
        return new Messages();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdminArchivoDescargaResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AdminArchivoDescargaResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://b2b.falabella.com/schemas/ol/admin-archivo-descarga-response", name = "admin-archivo-descarga-response")
    public JAXBElement<AdminArchivoDescargaResponse> createAdminArchivoDescargaResponse(AdminArchivoDescargaResponse value) {
        return new JAXBElement<AdminArchivoDescargaResponse>(_AdminArchivoDescargaResponse_QNAME, AdminArchivoDescargaResponse.class, null, value);
    }

}
