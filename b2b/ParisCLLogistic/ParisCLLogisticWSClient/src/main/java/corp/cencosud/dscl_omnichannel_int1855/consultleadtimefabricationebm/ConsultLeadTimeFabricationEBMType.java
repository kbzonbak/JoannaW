
package corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabricationebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.xmlns.core.ebm.common.ebm.EBMType;


/**
 * <p>Java class for ConsultLeadTimeFabricationEBMType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsultLeadTimeFabricationEBMType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}EBMType">
 *       &lt;sequence>
 *         &lt;element name="DataArea" type="{http://www.cencosud.corp/DSCL_OmniChannel_INT1855/ConsultLeadTimeFabricationEBM}ConsultLeadTimeFabricationEBMDataAreaRequestType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultLeadTimeFabricationEBMType", propOrder = {
    "dataArea"
})
public class ConsultLeadTimeFabricationEBMType
    extends EBMType
{

    @XmlElement(name = "DataArea", required = true)
    protected ConsultLeadTimeFabricationEBMDataAreaRequestType dataArea;

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link ConsultLeadTimeFabricationEBMDataAreaRequestType }
     *     
     */
    public ConsultLeadTimeFabricationEBMDataAreaRequestType getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsultLeadTimeFabricationEBMDataAreaRequestType }
     *     
     */
    public void setDataArea(ConsultLeadTimeFabricationEBMDataAreaRequestType value) {
        this.dataArea = value;
    }

}
