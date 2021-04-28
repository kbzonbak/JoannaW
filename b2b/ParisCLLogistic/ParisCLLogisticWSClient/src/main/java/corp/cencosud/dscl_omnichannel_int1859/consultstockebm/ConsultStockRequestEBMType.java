
package corp.cencosud.dscl_omnichannel_int1859.consultstockebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.xmlns.core.ebm.common.ebm.EBMType;


/**
 * <p>Java class for ConsultStockRequestEBMType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsultStockRequestEBMType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}EBMType">
 *       &lt;sequence>
 *         &lt;element name="DataArea" type="{http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStockEBM}ConsultStockRequestEBMDataAreaRequestType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultStockRequestEBMType", propOrder = {
    "dataArea"
})
public class ConsultStockRequestEBMType
    extends EBMType
{

    @XmlElement(name = "DataArea", required = true)
    protected ConsultStockRequestEBMDataAreaRequestType dataArea;

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link ConsultStockRequestEBMDataAreaRequestType }
     *     
     */
    public ConsultStockRequestEBMDataAreaRequestType getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsultStockRequestEBMDataAreaRequestType }
     *     
     */
    public void setDataArea(ConsultStockRequestEBMDataAreaRequestType value) {
        this.dataArea = value;
    }

}
