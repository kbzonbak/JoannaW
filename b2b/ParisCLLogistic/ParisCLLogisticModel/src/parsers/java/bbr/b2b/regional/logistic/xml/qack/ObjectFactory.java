//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.22 at 09:59:32 AM CLT 
//


package bbr.b2b.regional.logistic.xml.qack;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bbr.b2b.regional.logistic.xml.qack package. 
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

    private final static QName _A2_QNAME = new QName("", "A2");
    private final static QName _A1_QNAME = new QName("", "A1");
    private final static QName _A4_QNAME = new QName("", "A4");
    private final static QName _A3_QNAME = new QName("", "A3");
    private final static QName _A6_QNAME = new QName("", "A6");
    private final static QName _A5_QNAME = new QName("", "A5");
    private final static QName _A0_QNAME = new QName("", "A0");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bbr.b2b.regional.logistic.xml.qack
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ACK }
     * 
     */
    public ACK createACK() {
        return new ACK();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "A2")
    public JAXBElement<String> createA2(String value) {
        return new JAXBElement<String>(_A2_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "A1")
    public JAXBElement<String> createA1(String value) {
        return new JAXBElement<String>(_A1_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "A4")
    public JAXBElement<String> createA4(String value) {
        return new JAXBElement<String>(_A4_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "A3")
    public JAXBElement<String> createA3(String value) {
        return new JAXBElement<String>(_A3_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "A6")
    public JAXBElement<String> createA6(String value) {
        return new JAXBElement<String>(_A6_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "A5")
    public JAXBElement<String> createA5(String value) {
        return new JAXBElement<String>(_A5_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "A0")
    public JAXBElement<String> createA0(String value) {
        return new JAXBElement<String>(_A0_QNAME, String.class, null, value);
    }

}
