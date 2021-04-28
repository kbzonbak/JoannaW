
package corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabricationebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.dscl_omnichannel_int1855.leadtimefabricationoutputmessage.Skus;


/**
 * <p>Java class for ConsultLeadTimeFabricationResponseEBMDataAreaResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsultLeadTimeFabricationResponseEBMDataAreaResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cencosud.corp/DSCL_OmniChannel_INT1855/LeadTimeFabricationOutputMessage}skus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultLeadTimeFabricationResponseEBMDataAreaResponseType", propOrder = {
    "skus"
})
public class ConsultLeadTimeFabricationResponseEBMDataAreaResponseType {

    @XmlElement(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1855/LeadTimeFabricationOutputMessage", required = true)
    protected Skus skus;

    /**
     * Gets the value of the skus property.
     * 
     * @return
     *     possible object is
     *     {@link Skus }
     *     
     */
    public Skus getSkus() {
        return skus;
    }

    /**
     * Sets the value of the skus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Skus }
     *     
     */
    public void setSkus(Skus value) {
        this.skus = value;
    }

}
