//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.07.31 at 10:58:15 AM CLT 
//


package bbr.b2b.regional.logistic.xml.qevaluation;

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
 *         &lt;element ref="{}SequenceNumber"/>
 *         &lt;element ref="{}TransNbr"/>
 *         &lt;element ref="{}nasn"/>
 *         &lt;element ref="{}DateCreated"/>
 *         &lt;element ref="{}Ponbr"/>
 *         &lt;element ref="{}As400UserID"/>
 *         &lt;element ref="{}Reference3"/>
 *         &lt;element ref="{}ReferenceCode3" minOccurs="0"/>
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
    "sequenceNumber",
    "transNbr",
    "nasn",
    "dateCreated",
    "ponbr",
    "as400UserID",
    "reference3",
    "referenceCode3"
})
@XmlRootElement(name = "QEVALUACION")
public class QEVALUACION {

    @XmlElement(name = "SequenceNumber")
    protected long sequenceNumber;
    @XmlElement(name = "TransNbr")
    protected long transNbr;
    @XmlElement(required = true)
    protected String nasn;
    @XmlElement(name = "DateCreated", required = true)
    protected String dateCreated;
    @XmlElement(name = "Ponbr", required = true)
    protected String ponbr;
    @XmlElement(name = "As400UserID", required = true)
    protected String as400UserID;
    @XmlElement(name = "Reference3", required = true)
    protected String reference3;
    @XmlElement(name = "ReferenceCode3")
    protected String referenceCode3;

    /**
     * Gets the value of the sequenceNumber property.
     * 
     */
    public long getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     */
    public void setSequenceNumber(long value) {
        this.sequenceNumber = value;
    }

    /**
     * Gets the value of the transNbr property.
     * 
     */
    public long getTransNbr() {
        return transNbr;
    }

    /**
     * Sets the value of the transNbr property.
     * 
     */
    public void setTransNbr(long value) {
        this.transNbr = value;
    }

    /**
     * Gets the value of the nasn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNasn() {
        return nasn;
    }

    /**
     * Sets the value of the nasn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNasn(String value) {
        this.nasn = value;
    }

    /**
     * Gets the value of the dateCreated property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateCreated(String value) {
        this.dateCreated = value;
    }

    /**
     * Gets the value of the ponbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPonbr() {
        return ponbr;
    }

    /**
     * Sets the value of the ponbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPonbr(String value) {
        this.ponbr = value;
    }

    /**
     * Gets the value of the as400UserID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAs400UserID() {
        return as400UserID;
    }

    /**
     * Sets the value of the as400UserID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAs400UserID(String value) {
        this.as400UserID = value;
    }

    /**
     * Gets the value of the reference3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference3() {
        return reference3;
    }

    /**
     * Sets the value of the reference3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference3(String value) {
        this.reference3 = value;
    }

    /**
     * Gets the value of the referenceCode3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceCode3() {
        return referenceCode3;
    }

    /**
     * Sets the value of the referenceCode3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceCode3(String value) {
        this.referenceCode3 = value;
    }

}