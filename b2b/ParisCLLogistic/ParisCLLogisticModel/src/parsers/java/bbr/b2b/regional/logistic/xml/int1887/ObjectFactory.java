//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.25 at 10:23:52 AM CLT 
//


package bbr.b2b.regional.logistic.xml.int1887;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bbr.b2b.regional.logistic.xml.int1887 package. 
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

    private final static QName _TipoNota_QNAME = new QName("", "tipo_nota");
    private final static QName _NumOrdencompra_QNAME = new QName("", "num_ordencompra");
    private final static QName _Tipo_QNAME = new QName("", "tipo");
    private final static QName _NuevaFecha_QNAME = new QName("", "nueva_fecha");
    private final static QName _IdCompania_QNAME = new QName("", "id_compania");
    private final static QName _TipoEntidad_QNAME = new QName("", "tipo_entidad");
    private final static QName _Accion_QNAME = new QName("", "accion");
    private final static QName _Origen_QNAME = new QName("", "origen");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bbr.b2b.regional.logistic.xml.int1887
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tipo_nota")
    public JAXBElement<String> createTipoNota(String value) {
        return new JAXBElement<String>(_TipoNota_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "num_ordencompra")
    public JAXBElement<String> createNumOrdencompra(String value) {
        return new JAXBElement<String>(_NumOrdencompra_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tipo")
    public JAXBElement<String> createTipo(String value) {
        return new JAXBElement<String>(_Tipo_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nueva_fecha")
    public JAXBElement<String> createNuevaFecha(String value) {
        return new JAXBElement<String>(_NuevaFecha_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "id_compania")
    public JAXBElement<String> createIdCompania(String value) {
        return new JAXBElement<String>(_IdCompania_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tipo_entidad")
    public JAXBElement<String> createTipoEntidad(String value) {
        return new JAXBElement<String>(_TipoEntidad_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "accion")
    public JAXBElement<String> createAccion(String value) {
        return new JAXBElement<String>(_Accion_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "origen")
    public JAXBElement<String> createOrigen(String value) {
        return new JAXBElement<String>(_Origen_QNAME, String.class, null, value);
    }

}
