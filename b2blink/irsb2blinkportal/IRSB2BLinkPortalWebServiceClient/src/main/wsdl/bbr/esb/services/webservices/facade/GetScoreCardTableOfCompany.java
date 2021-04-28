
package bbr.esb.services.webservices.facade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getScoreCardTableOfCompany complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getScoreCardTableOfCompany">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://facade.webservices.services.esb.bbr/}scoreCardTableOfCompanyInitParamDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getScoreCardTableOfCompany", propOrder = {
    "arg0"
})
public class GetScoreCardTableOfCompany {

    protected ScoreCardTableOfCompanyInitParamDTO arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link ScoreCardTableOfCompanyInitParamDTO }
     *     
     */
    public ScoreCardTableOfCompanyInitParamDTO getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScoreCardTableOfCompanyInitParamDTO }
     *     
     */
    public void setArg0(ScoreCardTableOfCompanyInitParamDTO value) {
        this.arg0 = value;
    }

}
