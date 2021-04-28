
package corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import corp.cencosud.xmlns.core.ebm.common.ebm.ResponseEBMType;


/**
 * <p>Java class for GetCapacityBySupplierResponseEBMType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetCapacityBySupplierResponseEBMType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}ResponseEBMType">
 *       &lt;sequence>
 *         &lt;element name="DataArea" type="{http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierEBM}GetCapacityBySupplierDataAreaResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetCapacityBySupplierResponseEBMType", propOrder = {
    "dataArea"
})
public class GetCapacityBySupplierResponseEBMType
    extends ResponseEBMType
{

    @XmlElement(name = "DataArea")
    protected GetCapacityBySupplierDataAreaResponseType dataArea;

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link GetCapacityBySupplierDataAreaResponseType }
     *     
     */
    public GetCapacityBySupplierDataAreaResponseType getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCapacityBySupplierDataAreaResponseType }
     *     
     */
    public void setDataArea(GetCapacityBySupplierDataAreaResponseType value) {
        this.dataArea = value;
    }

}
