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
 *         &lt;element ref="{}B0"/>
 *         &lt;element ref="{}B1"/>
 *         &lt;element ref="{}B2"/>
 *         &lt;element ref="{}B3"/>
 *         &lt;element ref="{}B4"/>
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
    "b0",
    "b1",
    "b2",
    "b3",
    "b4"
})
@XmlRootElement(name = "A2")
public class A2 {

    @XmlElement(name = "B0", required = true)
    protected String b0;
    @XmlElement(name = "B1", required = true)
    protected String b1;
    @XmlElement(name = "B2", required = true)
    protected B2 b2;
    @XmlElement(name = "B3", required = true)
    protected B3 b3;
    @XmlElement(name = "B4", required = true)
    protected String b4;

    /**
     * Gets the value of the b0 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getB0() {
        return b0;
    }

    /**
     * Sets the value of the b0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setB0(String value) {
        this.b0 = value;
    }

    /**
     * Gets the value of the b1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getB1() {
        return b1;
    }

    /**
     * Sets the value of the b1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setB1(String value) {
        this.b1 = value;
    }

    /**
     * Gets the value of the b2 property.
     * 
     * @return
     *     possible object is
     *     {@link B2 }
     *     
     */
    public B2 getB2() {
        return b2;
    }

    /**
     * Sets the value of the b2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link B2 }
     *     
     */
    public void setB2(B2 value) {
        this.b2 = value;
    }

    /**
     * Gets the value of the b3 property.
     * 
     * @return
     *     possible object is
     *     {@link B3 }
     *     
     */
    public B3 getB3() {
        return b3;
    }

    /**
     * Sets the value of the b3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link B3 }
     *     
     */
    public void setB3(B3 value) {
        this.b3 = value;
    }

    /**
     * Gets the value of the b4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getB4() {
        return b4;
    }

    /**
     * Sets the value of the b4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setB4(String value) {
        this.b4 = value;
    }

}
