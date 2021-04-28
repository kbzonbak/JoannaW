
package corp.cencosud.xmlns.core.ebm.common.ebm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for EBMHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EBMHeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EBMID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CreationDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Sender" type="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}SenderType"/>
 *         &lt;element name="Target" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EBMTracking" type="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}EBMTrackingType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Addressing" type="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}EBMAddressingType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EBMHeaderType", propOrder = {
    "ebmid",
    "creationDateTime",
    "sender",
    "target",
    "ebmTracking",
    "addressing"
})
public class EBMHeaderType {

    @XmlElement(name = "EBMID", required = true)
    protected String ebmid;
    @XmlElement(name = "CreationDateTime")
    protected XMLGregorianCalendar creationDateTime;
    @XmlElement(name = "Sender", required = true)
    protected SenderType sender;
    @XmlElement(name = "Target")
    protected String target;
    @XmlElement(name = "EBMTracking", required = true)
    protected List<EBMTrackingType> ebmTracking;
    @XmlElement(name = "Addressing", required = true)
    protected List<EBMAddressingType> addressing;

    /**
     * Gets the value of the ebmid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEBMID() {
        return ebmid;
    }

    /**
     * Sets the value of the ebmid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEBMID(String value) {
        this.ebmid = value;
    }

    /**
     * Gets the value of the creationDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreationDateTime() {
        return creationDateTime;
    }

    /**
     * Sets the value of the creationDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreationDateTime(XMLGregorianCalendar value) {
        this.creationDateTime = value;
    }

    /**
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link SenderType }
     *     
     */
    public SenderType getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link SenderType }
     *     
     */
    public void setSender(SenderType value) {
        this.sender = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarget(String value) {
        this.target = value;
    }

    /**
     * Gets the value of the ebmTracking property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ebmTracking property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEBMTracking().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EBMTrackingType }
     * 
     * 
     */
    public List<EBMTrackingType> getEBMTracking() {
        if (ebmTracking == null) {
            ebmTracking = new ArrayList<EBMTrackingType>();
        }
        return this.ebmTracking;
    }

    /**
     * Gets the value of the addressing property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addressing property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddressing().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EBMAddressingType }
     * 
     * 
     */
    public List<EBMAddressingType> getAddressing() {
        if (addressing == null) {
            addressing = new ArrayList<EBMAddressingType>();
        }
        return this.addressing;
    }

}
