
package corp.cencosud.xmlns.core.ebm.common.ebm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EBMAddressingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EBMAddressingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReplyToAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CorrelID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EBMAddressingType", propOrder = {
    "replyToAddress",
    "correlID"
})
public class EBMAddressingType {

    @XmlElementRef(name = "ReplyToAddress", namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", type = JAXBElement.class)
    protected JAXBElement<String> replyToAddress;
    @XmlElementRef(name = "CorrelID", namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", type = JAXBElement.class)
    protected JAXBElement<String> correlID;

    /**
     * Gets the value of the replyToAddress property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getReplyToAddress() {
        return replyToAddress;
    }

    /**
     * Sets the value of the replyToAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setReplyToAddress(JAXBElement<String> value) {
        this.replyToAddress = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the correlID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCorrelID() {
        return correlID;
    }

    /**
     * Sets the value of the correlID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCorrelID(JAXBElement<String> value) {
        this.correlID = ((JAXBElement<String> ) value);
    }

}
