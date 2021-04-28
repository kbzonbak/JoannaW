
package corp.cencosud.dscl_omnichannel_int1859.consultstockebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.dscl_omnichannel_int1859.availabilitysyncinputmessage.AvailabilityRequest;


/**
 * <p>Java class for ConsultStockRequestEBMDataAreaRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsultStockRequestEBMDataAreaRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cencosud.corp/DSCL_OmniChannel_INT1859/AvailabilitySyncInputMessage}availabilityRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultStockRequestEBMDataAreaRequestType", propOrder = {
    "availabilityRequest"
})
public class ConsultStockRequestEBMDataAreaRequestType {

    @XmlElement(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1859/AvailabilitySyncInputMessage", required = true)
    protected AvailabilityRequest availabilityRequest;

    /**
     * Gets the value of the availabilityRequest property.
     * 
     * @return
     *     possible object is
     *     {@link AvailabilityRequest }
     *     
     */
    public AvailabilityRequest getAvailabilityRequest() {
        return availabilityRequest;
    }

    /**
     * Sets the value of the availabilityRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AvailabilityRequest }
     *     
     */
    public void setAvailabilityRequest(AvailabilityRequest value) {
        this.availabilityRequest = value;
    }

}
