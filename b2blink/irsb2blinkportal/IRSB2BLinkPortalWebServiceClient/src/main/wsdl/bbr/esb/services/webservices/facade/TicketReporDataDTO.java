
package bbr.esb.services.webservices.facade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ticketReporDataDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ticketReporDataDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}pageInitParamDTO">
 *       &lt;sequence>
 *         &lt;element name="companyrut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filterType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numdoc" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="queryToCount" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="serviceid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="since" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statusid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="until" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ticketReporDataDTO", propOrder = {
    "companyrut",
    "filterType",
    "numdoc",
    "queryToCount",
    "serviceid",
    "since",
    "statusid",
    "until"
})
public class TicketReporDataDTO
    extends PageInitParamDTO
{

    protected String companyrut;
    protected Integer filterType;
    protected Long numdoc;
    protected Boolean queryToCount;
    protected String serviceid;
    protected String since;
    protected String statusid;
    protected String until;

    /**
     * Gets the value of the companyrut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyrut() {
        return companyrut;
    }

    /**
     * Sets the value of the companyrut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyrut(String value) {
        this.companyrut = value;
    }

    /**
     * Gets the value of the filterType property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFilterType() {
        return filterType;
    }

    /**
     * Sets the value of the filterType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFilterType(Integer value) {
        this.filterType = value;
    }

    /**
     * Gets the value of the numdoc property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumdoc() {
        return numdoc;
    }

    /**
     * Sets the value of the numdoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumdoc(Long value) {
        this.numdoc = value;
    }

    /**
     * Gets the value of the queryToCount property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isQueryToCount() {
        return queryToCount;
    }

    /**
     * Sets the value of the queryToCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setQueryToCount(Boolean value) {
        this.queryToCount = value;
    }

    /**
     * Gets the value of the serviceid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceid() {
        return serviceid;
    }

    /**
     * Sets the value of the serviceid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceid(String value) {
        this.serviceid = value;
    }

    /**
     * Gets the value of the since property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSince() {
        return since;
    }

    /**
     * Sets the value of the since property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSince(String value) {
        this.since = value;
    }

    /**
     * Gets the value of the statusid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusid() {
        return statusid;
    }

    /**
     * Sets the value of the statusid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusid(String value) {
        this.statusid = value;
    }

    /**
     * Gets the value of the until property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUntil() {
        return until;
    }

    /**
     * Sets the value of the until property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUntil(String value) {
        this.until = value;
    }

}
