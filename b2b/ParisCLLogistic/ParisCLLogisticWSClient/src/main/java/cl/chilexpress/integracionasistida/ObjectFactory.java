
package cl.chilexpress.integracionasistida;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import cl.chilexpress.ws.osb.ebo.headerrequest.DatosHeaderRequest;
import cl.chilexpress.ws.osb.ebo.headerresponse.DatosHeaderResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cl.chilexpress.integracionasistida package. 
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

    private final static QName _HeaderRequest_QNAME = new QName("http://www.chilexpress.cl/IntegracionAsistida/", "headerRequest");
    private final static QName _HeaderResponse_QNAME = new QName("http://www.chilexpress.cl/IntegracionAsistida/", "headerResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cl.chilexpress.integracionasistida
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IntegracionAsistidaResponse }
     * 
     */
    public IntegracionAsistidaResponse createIntegracionAsistidaResponse() {
        return new IntegracionAsistidaResponse();
    }

    /**
     * Create an instance of {@link IntegracionAsistidaRequest }
     * 
     */
    public IntegracionAsistidaRequest createIntegracionAsistidaRequest() {
        return new IntegracionAsistidaRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatosHeaderRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.chilexpress.cl/IntegracionAsistida/", name = "headerRequest")
    public JAXBElement<DatosHeaderRequest> createHeaderRequest(DatosHeaderRequest value) {
        return new JAXBElement<DatosHeaderRequest>(_HeaderRequest_QNAME, DatosHeaderRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatosHeaderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.chilexpress.cl/IntegracionAsistida/", name = "headerResponse")
    public JAXBElement<DatosHeaderResponse> createHeaderResponse(DatosHeaderResponse value) {
        return new JAXBElement<DatosHeaderResponse>(_HeaderResponse_QNAME, DatosHeaderResponse.class, null, value);
    }

}
