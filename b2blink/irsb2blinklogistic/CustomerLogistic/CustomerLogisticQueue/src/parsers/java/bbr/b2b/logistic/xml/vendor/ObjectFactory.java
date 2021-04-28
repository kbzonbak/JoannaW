//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:49:33 PM CLT 
//


package bbr.b2b.logistic.xml.vendor;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bbr.b2b.logistic.xml.vendor package. 
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

    private final static QName _Field1_QNAME = new QName("", "Field1");
    private final static QName _Field2_QNAME = new QName("", "Field2");
    private final static QName _Field3_QNAME = new QName("", "Field3");
    private final static QName _Field4_QNAME = new QName("", "Field4");
    private final static QName _Field5_QNAME = new QName("", "Field5");
    private final static QName _Field6_QNAME = new QName("", "Field6");
    private final static QName _Field7_QNAME = new QName("", "Field7");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bbr.b2b.logistic.xml.vendor
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link VENDORDWH }
     * 
     */
    public VENDORDWH createVENDORDWH() {
        return new VENDORDWH();
    }

    /**
     * Create an instance of {@link VS }
     * 
     */
    public VS createVS() {
        return new VS();
    }

    /**
     * Create an instance of {@link V }
     * 
     */
    public V createV() {
        return new V();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Field1")
    public JAXBElement<String> createField1(String value) {
        return new JAXBElement<String>(_Field1_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Field2")
    public JAXBElement<String> createField2(String value) {
        return new JAXBElement<String>(_Field2_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Field3")
    public JAXBElement<String> createField3(String value) {
        return new JAXBElement<String>(_Field3_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Field4")
    public JAXBElement<String> createField4(String value) {
        return new JAXBElement<String>(_Field4_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Field5")
    public JAXBElement<String> createField5(String value) {
        return new JAXBElement<String>(_Field5_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Field6")
    public JAXBElement<String> createField6(String value) {
        return new JAXBElement<String>(_Field6_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Field7")
    public JAXBElement<String> createField7(String value) {
        return new JAXBElement<String>(_Field7_QNAME, String.class, null, value);
    }

}
