
package corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabricationebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.dscl_omnichannel_int1855.leadtimefabricationinputmessage.ItemProcessingTime;


/**
 * <p>Java class for ConsultLeadTimeFabricationEBMDataAreaRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsultLeadTimeFabricationEBMDataAreaRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cencosud.corp/DSCL_OmniChannel_INT1855/LeadTimeFabricationInputMessage}itemProcessingTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultLeadTimeFabricationEBMDataAreaRequestType", propOrder = {
    "itemProcessingTime"
})
public class ConsultLeadTimeFabricationEBMDataAreaRequestType {

    @XmlElement(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1855/LeadTimeFabricationInputMessage", required = true)
    protected ItemProcessingTime itemProcessingTime;

    /**
     * Gets the value of the itemProcessingTime property.
     * 
     * @return
     *     possible object is
     *     {@link ItemProcessingTime }
     *     
     */
    public ItemProcessingTime getItemProcessingTime() {
        return itemProcessingTime;
    }

    /**
     * Sets the value of the itemProcessingTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemProcessingTime }
     *     
     */
    public void setItemProcessingTime(ItemProcessingTime value) {
        this.itemProcessingTime = value;
    }

}
