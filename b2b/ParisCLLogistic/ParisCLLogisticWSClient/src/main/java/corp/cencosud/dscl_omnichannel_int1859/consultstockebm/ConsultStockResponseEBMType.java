
package corp.cencosud.dscl_omnichannel_int1859.consultstockebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.xmlns.core.ebm.common.ebm.ResponseEBMType;


/**
 * <p>Java class for ConsultStockResponseEBMType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsultStockResponseEBMType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}ResponseEBMType">
 *       &lt;sequence>
 *         &lt;element name="DataArea" type="{http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStockEBM}ConsultStockResponseEBMDataAreaResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultStockResponseEBMType", propOrder = {
    "dataArea"
})
public class ConsultStockResponseEBMType
    extends ResponseEBMType
{

    @XmlElement(name = "DataArea")
    protected ConsultStockResponseEBMDataAreaResponseType dataArea;

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link ConsultStockResponseEBMDataAreaResponseType }
     *     
     */
    public ConsultStockResponseEBMDataAreaResponseType getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsultStockResponseEBMDataAreaResponseType }
     *     
     */
    public void setDataArea(ConsultStockResponseEBMDataAreaResponseType value) {
        this.dataArea = value;
    }

}
