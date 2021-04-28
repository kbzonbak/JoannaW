
package corp.cencosud.dscl_omnichannel_int1873.consulttransitlineebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.xmlns.core.ebm.common.ebm.ResponseEBMType;


/**
 * <p>Java class for ConsultTransitLineResponseEBMType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsultTransitLineResponseEBMType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}ResponseEBMType">
 *       &lt;sequence>
 *         &lt;element name="DataArea" type="{http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLineEBM}ConsultTransitTimeResponseEBMDataAreaResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultTransitLineResponseEBMType", propOrder = {
    "dataArea"
})
public class ConsultTransitLineResponseEBMType
    extends ResponseEBMType
{

    @XmlElement(name = "DataArea")
    protected ConsultTransitTimeResponseEBMDataAreaResponseType dataArea;

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link ConsultTransitTimeResponseEBMDataAreaResponseType }
     *     
     */
    public ConsultTransitTimeResponseEBMDataAreaResponseType getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsultTransitTimeResponseEBMDataAreaResponseType }
     *     
     */
    public void setDataArea(ConsultTransitTimeResponseEBMDataAreaResponseType value) {
        this.dataArea = value;
    }

}
