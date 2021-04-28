
package corp.cencosud.dscl_omnichannel_int1873.consulttransitlineebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.dscl_omnichannel_int1873.transittimeinputmessage.TransitTimeProcessing;


/**
 * <p>Java class for ConsultTransitRequestEBMDataAreaRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsultTransitRequestEBMDataAreaRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cencosud.corp/DSCL_OmniChannel_INT1873/TransitTimeInputMessage}transitTimeProcessing"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultTransitRequestEBMDataAreaRequestType", propOrder = {
    "transitTimeProcessing"
})
public class ConsultTransitRequestEBMDataAreaRequestType {

    @XmlElement(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1873/TransitTimeInputMessage", required = true)
    protected TransitTimeProcessing transitTimeProcessing;

    /**
     * Gets the value of the transitTimeProcessing property.
     * 
     * @return
     *     possible object is
     *     {@link TransitTimeProcessing }
     *     
     */
    public TransitTimeProcessing getTransitTimeProcessing() {
        return transitTimeProcessing;
    }

    /**
     * Sets the value of the transitTimeProcessing property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransitTimeProcessing }
     *     
     */
    public void setTransitTimeProcessing(TransitTimeProcessing value) {
        this.transitTimeProcessing = value;
    }

}
