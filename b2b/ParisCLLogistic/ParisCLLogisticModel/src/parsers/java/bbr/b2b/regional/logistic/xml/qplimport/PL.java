//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.07.24 at 11:23:28 AM CLT 
//


package bbr.b2b.regional.logistic.xml.qplimport;

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
 *         &lt;element ref="{}BS"/>
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
    "bs"
})
@XmlRootElement(name = "PL")
public class PL {

    @XmlElement(name = "BS", required = true)
    protected BS bs;

    /**
     * Gets the value of the bs property.
     * 
     * @return
     *     possible object is
     *     {@link BS }
     *     
     */
    public BS getBS() {
        return bs;
    }

    /**
     * Sets the value of the bs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BS }
     *     
     */
    public void setBS(BS value) {
        this.bs = value;
    }

}