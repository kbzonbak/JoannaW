
package bbr.esb.services.webservices.facade;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for scoreCardTableOfCompanyReturnDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="scoreCardTableOfCompanyReturnDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}baseResultDTO">
 *       &lt;sequence>
 *         &lt;element name="pageInfoDTO" type="{http://facade.webservices.services.esb.bbr/}pageInfoDTO" minOccurs="0"/>
 *         &lt;element name="scorecards" type="{http://facade.webservices.services.esb.bbr/}scoreCardTableDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scoreCardTableOfCompanyReturnDTO", propOrder = {
    "pageInfoDTO",
    "scorecards"
})
public class ScoreCardTableOfCompanyReturnDTO
    extends BaseResultDTO
{

    protected PageInfoDTO pageInfoDTO;
    @XmlElement(nillable = true)
    protected List<ScoreCardTableDTO> scorecards;

    /**
     * Gets the value of the pageInfoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link PageInfoDTO }
     *     
     */
    public PageInfoDTO getPageInfoDTO() {
        return pageInfoDTO;
    }

    /**
     * Sets the value of the pageInfoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageInfoDTO }
     *     
     */
    public void setPageInfoDTO(PageInfoDTO value) {
        this.pageInfoDTO = value;
    }

    /**
     * Gets the value of the scorecards property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scorecards property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScorecards().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScoreCardTableDTO }
     * 
     * 
     */
    public List<ScoreCardTableDTO> getScorecards() {
        if (scorecards == null) {
            scorecards = new ArrayList<ScoreCardTableDTO>();
        }
        return this.scorecards;
    }

}
