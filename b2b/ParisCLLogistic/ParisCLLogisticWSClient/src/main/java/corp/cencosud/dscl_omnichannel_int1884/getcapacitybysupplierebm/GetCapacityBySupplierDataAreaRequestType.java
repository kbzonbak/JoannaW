
package corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierinputmessage.CapacityRequest;


/**
 * <p>Java class for GetCapacityBySupplierDataAreaRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetCapacityBySupplierDataAreaRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierInputMessage}CapacityRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetCapacityBySupplierDataAreaRequestType", propOrder = {
    "capacityRequest"
})
public class GetCapacityBySupplierDataAreaRequestType {

    @XmlElement(name = "CapacityRequest", namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierInputMessage", required = true)
    protected CapacityRequest capacityRequest;

    /**
     * Gets the value of the capacityRequest property.
     * 
     * @return
     *     possible object is
     *     {@link CapacityRequest }
     *     
     */
    public CapacityRequest getCapacityRequest() {
        return capacityRequest;
    }

    /**
     * Sets the value of the capacityRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link CapacityRequest }
     *     
     */
    public void setCapacityRequest(CapacityRequest value) {
        this.capacityRequest = value;
    }

}
