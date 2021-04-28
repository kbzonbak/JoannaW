
package corp.cencosud.xmlns.core.ebm.common.ebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EBMType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EBMType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}EBMHeader"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EBMType", propOrder = {
    "ebmHeader"
})
public class EBMType {

    @XmlElement(name = "EBMHeader", required = true)
    protected EBMHeaderType ebmHeader;

    /**
     * Gets the value of the ebmHeader property.
     * 
     * @return
     *     possible object is
     *     {@link EBMHeaderType }
     *     
     */
    public EBMHeaderType getEBMHeader() {
        return ebmHeader;
    }

    /**
     * Sets the value of the ebmHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link EBMHeaderType }
     *     
     */
    public void setEBMHeader(EBMHeaderType value) {
        this.ebmHeader = value;
    }

}
