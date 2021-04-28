
package bbr.esb.services.webservices.facade;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for companiesServiceStatusReportDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="companiesServiceStatusReportDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="custommaxdelay" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="datas" type="{http://facade.webservices.services.esb.bbr/}companiesServiceStatusDataDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="monitoringannotation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="servicekey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="servicename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sitekey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="sitename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "companiesServiceStatusReportDTO", propOrder = {
    "custommaxdelay",
    "datas",
    "monitoringannotation",
    "servicekey",
    "servicename",
    "sitekey",
    "sitename"
})
public class CompaniesServiceStatusReportDTO {

    protected Integer custommaxdelay;
    @XmlElement(nillable = true)
    protected List<CompaniesServiceStatusDataDTO> datas;
    protected String monitoringannotation;
    protected Long servicekey;
    protected String servicename;
    protected Long sitekey;
    protected String sitename;

    /**
     * Gets the value of the custommaxdelay property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCustommaxdelay() {
        return custommaxdelay;
    }

    /**
     * Sets the value of the custommaxdelay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCustommaxdelay(Integer value) {
        this.custommaxdelay = value;
    }

    /**
     * Gets the value of the datas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CompaniesServiceStatusDataDTO }
     * 
     * 
     */
    public List<CompaniesServiceStatusDataDTO> getDatas() {
        if (datas == null) {
            datas = new ArrayList<CompaniesServiceStatusDataDTO>();
        }
        return this.datas;
    }

    /**
     * Gets the value of the monitoringannotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonitoringannotation() {
        return monitoringannotation;
    }

    /**
     * Sets the value of the monitoringannotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonitoringannotation(String value) {
        this.monitoringannotation = value;
    }

    /**
     * Gets the value of the servicekey property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getServicekey() {
        return servicekey;
    }

    /**
     * Sets the value of the servicekey property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setServicekey(Long value) {
        this.servicekey = value;
    }

    /**
     * Gets the value of the servicename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServicename() {
        return servicename;
    }

    /**
     * Sets the value of the servicename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServicename(String value) {
        this.servicename = value;
    }

    /**
     * Gets the value of the sitekey property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSitekey() {
        return sitekey;
    }

    /**
     * Sets the value of the sitekey property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSitekey(Long value) {
        this.sitekey = value;
    }

    /**
     * Gets the value of the sitename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitename() {
        return sitename;
    }

    /**
     * Sets the value of the sitename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitename(String value) {
        this.sitename = value;
    }

}
