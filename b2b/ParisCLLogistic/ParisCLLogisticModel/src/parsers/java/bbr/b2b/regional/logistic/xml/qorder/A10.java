//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.09.10 at 06:25:04 PM CLST 
//


package bbr.b2b.regional.logistic.xml.qorder;

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
 *         &lt;element ref="{}D0"/>
 *         &lt;element ref="{}D1" minOccurs="0"/>
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
    "d0",
    "d1"
})
@XmlRootElement(name = "A10")
public class A10 {

    @XmlElement(name = "D0", required = true)
    protected String d0;
    @XmlElement(name = "D1")
    protected String d1;

    /**
     * Gets the value of the d0 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getD0() {
        return d0;
    }

    /**
     * Sets the value of the d0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setD0(String value) {
        this.d0 = value;
    }

    /**
     * Gets the value of the d1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getD1() {
        return d1;
    }

    /**
     * Sets the value of the d1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setD1(String value) {
        this.d1 = value;
    }

}
