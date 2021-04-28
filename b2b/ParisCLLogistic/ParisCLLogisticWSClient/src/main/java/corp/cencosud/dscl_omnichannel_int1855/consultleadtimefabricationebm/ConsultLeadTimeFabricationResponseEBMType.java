
package corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabricationebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.xmlns.core.ebm.common.ebm.ResponseEBMType;


/**
 * <p>Java class for ConsultLeadTimeFabricationResponseEBMType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsultLeadTimeFabricationResponseEBMType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}ResponseEBMType">
 *       &lt;sequence>
 *         &lt;element name="DataArea" type="{http://www.cencosud.corp/DSCL_OmniChannel_INT1855/ConsultLeadTimeFabricationEBM}ConsultLeadTimeFabricationResponseEBMDataAreaResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultLeadTimeFabricationResponseEBMType", propOrder = {
    "dataArea"
})
public class ConsultLeadTimeFabricationResponseEBMType
    extends ResponseEBMType
{

    @XmlElement(name = "DataArea")
    protected ConsultLeadTimeFabricationResponseEBMDataAreaResponseType dataArea;

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link ConsultLeadTimeFabricationResponseEBMDataAreaResponseType }
     *     
     */
    public ConsultLeadTimeFabricationResponseEBMDataAreaResponseType getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsultLeadTimeFabricationResponseEBMDataAreaResponseType }
     *     
     */
    public void setDataArea(ConsultLeadTimeFabricationResponseEBMDataAreaResponseType value) {
        this.dataArea = value;
    }

}
