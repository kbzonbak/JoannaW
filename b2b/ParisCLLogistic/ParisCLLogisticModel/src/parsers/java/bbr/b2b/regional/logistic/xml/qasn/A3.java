//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.30 at 09:49:02 AM CLT 
//


package bbr.b2b.regional.logistic.xml.qasn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}C0"/>
 *         &lt;element ref="{}C1"/>
 *         &lt;element ref="{}C2"/>
 *         &lt;element ref="{}C3"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "c0",
    "c1",
    "c2",
    "c3"
})
@XmlRootElement(name = "A3")
public class A3 {

    @XmlElement(name = "C0", required = true)
    protected String c0;
    @XmlElement(name = "C1", required = true)
    protected String c1;
    @XmlElement(name = "C2", required = true)
    protected String c2;
    @XmlElement(name = "C3", required = true)
    protected C3 c3;

    /**
     * Gets the value of the c0 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getC0() {
        return c0;
    }

    /**
     * Sets the value of the c0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setC0(String value) {
        this.c0 = value;
    }

    /**
     * Gets the value of the c1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getC1() {
        return c1;
    }

    /**
     * Sets the value of the c1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setC1(String value) {
        this.c1 = value;
    }

    /**
     * Gets the value of the c2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getC2() {
        return c2;
    }

    /**
     * Sets the value of the c2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setC2(String value) {
        this.c2 = value;
    }

    /**
     * Gets the value of the c3 property.
     * 
     * @return
     *     possible object is
     *     {@link C3 }
     *     
     */
    public C3 getC3() {
        return c3;
    }

    /**
     * Sets the value of the c3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link C3 }
     *     
     */
    public void setC3(C3 value) {
        this.c3 = value;
    }

}
