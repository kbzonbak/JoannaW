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
 *         &lt;element ref="{}SE"/>
 *         &lt;element ref="{}IM"/>
 *         &lt;element ref="{}OC"/>
 *         &lt;element ref="{}GD"/>
 *         &lt;element ref="{}NC"/>
 *         &lt;element ref="{}AC"/>
 *         &lt;element ref="{}SC"/>
 *         &lt;element ref="{}PL"/>
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
    "se",
    "im",
    "oc",
    "gd",
    "nc",
    "ac",
    "sc",
    "pl"
})
@XmlRootElement(name = "QPLIMPORTADOREQ")
public class QPLIMPORTADOREQ {

    @XmlElement(name = "SE")
    protected long se;
    @XmlElement(name = "IM", required = true)
    protected String im;
    @XmlElement(name = "OC")
    protected long oc;
    @XmlElement(name = "GD")
    protected long gd;
    @XmlElement(name = "NC", required = true)
    protected String nc;
    @XmlElement(name = "AC", required = true)
    protected String ac;
    @XmlElement(name = "SC", required = true)
    protected SC sc;
    @XmlElement(name = "PL", required = true)
    protected PL pl;

    /**
     * Gets the value of the se property.
     * 
     */
    public long getSE() {
        return se;
    }

    /**
     * Sets the value of the se property.
     * 
     */
    public void setSE(long value) {
        this.se = value;
    }

    /**
     * Gets the value of the im property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIM() {
        return im;
    }

    /**
     * Sets the value of the im property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIM(String value) {
        this.im = value;
    }

    /**
     * Gets the value of the oc property.
     * 
     */
    public long getOC() {
        return oc;
    }

    /**
     * Sets the value of the oc property.
     * 
     */
    public void setOC(long value) {
        this.oc = value;
    }

    /**
     * Gets the value of the gd property.
     * 
     */
    public long getGD() {
        return gd;
    }

    /**
     * Sets the value of the gd property.
     * 
     */
    public void setGD(long value) {
        this.gd = value;
    }

    /**
     * Gets the value of the nc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNC() {
        return nc;
    }

    /**
     * Sets the value of the nc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNC(String value) {
        this.nc = value;
    }

    /**
     * Gets the value of the ac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAC() {
        return ac;
    }

    /**
     * Sets the value of the ac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAC(String value) {
        this.ac = value;
    }

    /**
     * Gets the value of the sc property.
     * 
     * @return
     *     possible object is
     *     {@link SC }
     *     
     */
    public SC getSC() {
        return sc;
    }

    /**
     * Sets the value of the sc property.
     * 
     * @param value
     *     allowed object is
     *     {@link SC }
     *     
     */
    public void setSC(SC value) {
        this.sc = value;
    }

    /**
     * Gets the value of the pl property.
     * 
     * @return
     *     possible object is
     *     {@link PL }
     *     
     */
    public PL getPL() {
        return pl;
    }

    /**
     * Sets the value of the pl property.
     * 
     * @param value
     *     allowed object is
     *     {@link PL }
     *     
     */
    public void setPL(PL value) {
        this.pl = value;
    }

}