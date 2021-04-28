
package corp.cencosud.xmlns.core.ebm.common.ebm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EBMTrackingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EBMTrackingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FileName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ParentEBMID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IntegrationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReferenceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EBMTrackingType", propOrder = {
    "fileName",
    "parentEBMID",
    "integrationCode",
    "referenceID"
})
public class EBMTrackingType {

    @XmlElement(name = "FileName")
    protected String fileName;
    @XmlElementRef(name = "ParentEBMID", namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", type = JAXBElement.class)
    protected JAXBElement<String> parentEBMID;
    @XmlElementRef(name = "IntegrationCode", namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", type = JAXBElement.class)
    protected JAXBElement<String> integrationCode;
    @XmlElementRef(name = "ReferenceID", namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", type = JAXBElement.class)
    protected JAXBElement<String> referenceID;

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the parentEBMID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getParentEBMID() {
        return parentEBMID;
    }

    /**
     * Sets the value of the parentEBMID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setParentEBMID(JAXBElement<String> value) {
        this.parentEBMID = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the integrationCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIntegrationCode() {
        return integrationCode;
    }

    /**
     * Sets the value of the integrationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIntegrationCode(JAXBElement<String> value) {
        this.integrationCode = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the referenceID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getReferenceID() {
        return referenceID;
    }

    /**
     * Sets the value of the referenceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setReferenceID(JAXBElement<String> value) {
        this.referenceID = ((JAXBElement<String> ) value);
    }

}
