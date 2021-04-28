
package corp.cencosud.dscl_omnichannel_int1873.consulttransitlineebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.xmlns.core.ebm.common.ebm.EBMType;


/**
 * <p>Java class for ConsultTransitLineRequestEBMType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsultTransitLineRequestEBMType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}EBMType">
 *       &lt;sequence>
 *         &lt;element name="DataArea" type="{http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLineEBM}ConsultTransitRequestEBMDataAreaRequestType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultTransitLineRequestEBMType", propOrder = {
    "dataArea"
})
public class ConsultTransitLineRequestEBMType
    extends EBMType
{

    @XmlElement(name = "DataArea", required = true)
    protected ConsultTransitRequestEBMDataAreaRequestType dataArea;

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link ConsultTransitRequestEBMDataAreaRequestType }
     *     
     */
    public ConsultTransitRequestEBMDataAreaRequestType getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsultTransitRequestEBMDataAreaRequestType }
     *     
     */
    public void setDataArea(ConsultTransitRequestEBMDataAreaRequestType value) {
        this.dataArea = value;
    }

}
