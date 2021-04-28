
package corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplieroutputmessage.Capacities;


/**
 * <p>Java class for GetCapacityBySupplierDataAreaResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetCapacityBySupplierDataAreaResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierOutputMessage}capacities"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetCapacityBySupplierDataAreaResponseType", propOrder = {
    "capacities"
})
public class GetCapacityBySupplierDataAreaResponseType {

    @XmlElement(namespace = "http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierOutputMessage", required = true)
    protected Capacities capacities;

    /**
     * Gets the value of the capacities property.
     * 
     * @return
     *     possible object is
     *     {@link Capacities }
     *     
     */
    public Capacities getCapacities() {
        return capacities;
    }

    /**
     * Sets the value of the capacities property.
     * 
     * @param value
     *     allowed object is
     *     {@link Capacities }
     *     
     */
    public void setCapacities(Capacities value) {
        this.capacities = value;
    }

}
