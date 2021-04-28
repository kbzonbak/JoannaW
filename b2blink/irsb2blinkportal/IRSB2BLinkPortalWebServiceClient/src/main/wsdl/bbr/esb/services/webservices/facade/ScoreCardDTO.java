
package bbr.esb.services.webservices.facade;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for scoreCardDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="scoreCardDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}baseResultDTO">
 *       &lt;sequence>
 *         &lt;element name="siteprogressarray" type="{http://facade.webservices.services.esb.bbr/}siteProgress" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="totalerror" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="totalinprogress" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="totalsucess" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scoreCardDTO", propOrder = {
    "siteprogressarray",
    "totalerror",
    "totalinprogress",
    "totalsucess"
})
public class ScoreCardDTO
    extends BaseResultDTO
{

    @XmlElement(nillable = true)
    protected List<SiteProgress> siteprogressarray;
    protected Integer totalerror;
    protected Integer totalinprogress;
    protected Integer totalsucess;

    /**
     * Gets the value of the siteprogressarray property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the siteprogressarray property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSiteprogressarray().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SiteProgress }
     * 
     * 
     */
    public List<SiteProgress> getSiteprogressarray() {
        if (siteprogressarray == null) {
            siteprogressarray = new ArrayList<SiteProgress>();
        }
        return this.siteprogressarray;
    }

    /**
     * Gets the value of the totalerror property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalerror() {
        return totalerror;
    }

    /**
     * Sets the value of the totalerror property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalerror(Integer value) {
        this.totalerror = value;
    }

    /**
     * Gets the value of the totalinprogress property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalinprogress() {
        return totalinprogress;
    }

    /**
     * Sets the value of the totalinprogress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalinprogress(Integer value) {
        this.totalinprogress = value;
    }

    /**
     * Gets the value of the totalsucess property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalsucess() {
        return totalsucess;
    }

    /**
     * Sets the value of the totalsucess property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalsucess(Integer value) {
        this.totalsucess = value;
    }

}
