//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.07.24 at 11:23:28 AM CLT 
//


package bbr.b2b.regional.logistic.xml.qplimport;

import java.math.BigInteger;
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
 *         &lt;element ref="{}NO"/>
 *         &lt;element ref="{}EM"/>
 *         &lt;element ref="{}FE"/>
 *         &lt;element ref="{}HH"/>
 *         &lt;element ref="{}CO"/>
 *         &lt;element ref="{}UN"/>
 *         &lt;element ref="{}NP"/>
 *         &lt;element ref="{}CE"/>
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
    "no",
    "em",
    "fe",
    "hh",
    "co",
    "un",
    "np",
    "ce"
})
@XmlRootElement(name = "SC")
public class SC {

    @XmlElement(name = "NO", required = true)
    protected String no;
    @XmlElement(name = "EM", required = true)
    protected String em;
    @XmlElement(name = "FE", required = true)
    protected String fe;
    @XmlElement(name = "HH", required = true)
    protected String hh;
    @XmlElement(name = "CO", required = true)
    protected String co;
    @XmlElement(name = "UN", required = true)
    protected BigInteger un;
    @XmlElement(name = "NP", required = true)
    protected BigInteger np;
    @XmlElement(name = "CE", required = true)
    protected String ce;

    /**
     * Gets the value of the no property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNO() {
        return no;
    }

    /**
     * Sets the value of the no property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNO(String value) {
        this.no = value;
    }

    /**
     * Gets the value of the em property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEM() {
        return em;
    }

    /**
     * Sets the value of the em property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEM(String value) {
        this.em = value;
    }

    /**
     * Gets the value of the fe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFE() {
        return fe;
    }

    /**
     * Sets the value of the fe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFE(String value) {
        this.fe = value;
    }

    /**
     * Gets the value of the hh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHH() {
        return hh;
    }

    /**
     * Sets the value of the hh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHH(String value) {
        this.hh = value;
    }

    /**
     * Gets the value of the co property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCO() {
        return co;
    }

    /**
     * Sets the value of the co property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCO(String value) {
        this.co = value;
    }

    /**
     * Gets the value of the un property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUN() {
        return un;
    }

    /**
     * Sets the value of the un property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUN(BigInteger value) {
        this.un = value;
    }

    /**
     * Gets the value of the np property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNP() {
        return np;
    }

    /**
     * Sets the value of the np property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNP(BigInteger value) {
        this.np = value;
    }

    /**
     * Gets the value of the ce property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCE() {
        return ce;
    }

    /**
     * Sets the value of the ce property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCE(String value) {
        this.ce = value;
    }

}