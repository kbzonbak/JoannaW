
package corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.xmlns.core.ebm.common.ebm.EBMType;


/**
 * <p>Java class for GetCapacityBySupplierRequestEBMType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetCapacityBySupplierRequestEBMType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}EBMType">
 *       &lt;sequence>
 *         &lt;element name="DataArea" type="{http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierEBM}GetCapacityBySupplierDataAreaRequestType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetCapacityBySupplierRequestEBMType", propOrder = {
    "dataArea"
})
public class GetCapacityBySupplierRequestEBMType
    extends EBMType
{

    @XmlElement(name = "DataArea", required = true)
    protected GetCapacityBySupplierDataAreaRequestType dataArea;

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link GetCapacityBySupplierDataAreaRequestType }
     *     
     */
    public GetCapacityBySupplierDataAreaRequestType getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCapacityBySupplierDataAreaRequestType }
     *     
     */
    public void setDataArea(GetCapacityBySupplierDataAreaRequestType value) {
        this.dataArea = value;
    }

}
