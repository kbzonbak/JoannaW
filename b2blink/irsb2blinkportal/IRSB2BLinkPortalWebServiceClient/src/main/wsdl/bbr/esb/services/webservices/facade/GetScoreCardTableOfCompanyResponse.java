
package bbr.esb.services.webservices.facade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getScoreCardTableOfCompanyResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getScoreCardTableOfCompanyResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://facade.webservices.services.esb.bbr/}scoreCardTableOfCompanyReturnDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getScoreCardTableOfCompanyResponse", propOrder = {
    "_return"
})
public class GetScoreCardTableOfCompanyResponse {

    @XmlElement(name = "return")
    protected ScoreCardTableOfCompanyReturnDTO _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link ScoreCardTableOfCompanyReturnDTO }
     *     
     */
    public ScoreCardTableOfCompanyReturnDTO getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScoreCardTableOfCompanyReturnDTO }
     *     
     */
    public void setReturn(ScoreCardTableOfCompanyReturnDTO value) {
        this._return = value;
    }

}
