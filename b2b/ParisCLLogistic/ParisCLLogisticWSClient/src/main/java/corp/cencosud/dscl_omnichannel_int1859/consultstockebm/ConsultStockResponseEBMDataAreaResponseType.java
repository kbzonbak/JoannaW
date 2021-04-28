
package corp.cencosud.dscl_omnichannel_int1859.consultstockebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.dscl_omnichannel_int1859.availabilitysyncoutputmessage.Availability;


/**
 * <p>Java class for ConsultStockResponseEBMDataAreaResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsultStockResponseEBMDataAreaResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cencosud.corp/DSCL_OmniChannel_INT1859/AvailabilitySyncOutputMessage}availability"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultStockResponseEBMDataAreaResponseType", propOrder = {
    "availability"
})
public class ConsultStockResponseEBMDataAreaResponseType {

    @XmlElement(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1859/AvailabilitySyncOutputMessage", required = true)
    protected Availability availability;

    /**
     * Gets the value of the availability property.
     * 
     * @return
     *     possible object is
     *     {@link Availability }
     *     
     */
    public Availability getAvailability() {
        return availability;
    }

    /**
     * Sets the value of the availability property.
     * 
     * @param value
     *     allowed object is
     *     {@link Availability }
     *     
     */
    public void setAvailability(Availability value) {
        this.availability = value;
    }

}
