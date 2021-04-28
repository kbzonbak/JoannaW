//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.08 a las 02:24:51 PM CLT 
//


package bbr.b2b.soa.soap.webservices.classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para companiesServiceStatusDataDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="companiesServiceStatusDataDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="accesscode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="activation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="as2id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="companykey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="custommaxdelay" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="monitor" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="servicecode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="servicekey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="servicename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sitecode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sitekey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="sitename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "companiesServiceStatusDataDTO", propOrder = {
    "accesscode",
    "activation",
    "as2Id",
    "companykey",
    "custommaxdelay",
    "monitor",
    "servicecode",
    "servicekey",
    "servicename",
    "sitecode",
    "sitekey",
    "sitename"
})
public class CompaniesServiceStatusDataDTO {

    protected String accesscode;
    protected String activation;
    @XmlElement(name = "as2id")
    protected String as2Id;
    protected Long companykey;
    protected Integer custommaxdelay;
    protected Boolean monitor;
    protected String servicecode;
    protected Long servicekey;
    protected String servicename;
    protected String sitecode;
    protected Long sitekey;
    protected String sitename;

    /**
     * Obtiene el valor de la propiedad accesscode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccesscode() {
        return accesscode;
    }

    /**
     * Define el valor de la propiedad accesscode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccesscode(String value) {
        this.accesscode = value;
    }

    /**
     * Obtiene el valor de la propiedad activation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivation() {
        return activation;
    }

    /**
     * Define el valor de la propiedad activation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivation(String value) {
        this.activation = value;
    }

    /**
     * Obtiene el valor de la propiedad as2Id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAs2Id() {
        return as2Id;
    }

    /**
     * Define el valor de la propiedad as2Id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAs2Id(String value) {
        this.as2Id = value;
    }

    /**
     * Obtiene el valor de la propiedad companykey.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCompanykey() {
        return companykey;
    }

    /**
     * Define el valor de la propiedad companykey.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCompanykey(Long value) {
        this.companykey = value;
    }

    /**
     * Obtiene el valor de la propiedad custommaxdelay.
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
     * Define el valor de la propiedad custommaxdelay.
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
     * Obtiene el valor de la propiedad monitor.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMonitor() {
        return monitor;
    }

    /**
     * Define el valor de la propiedad monitor.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMonitor(Boolean value) {
        this.monitor = value;
    }

    /**
     * Obtiene el valor de la propiedad servicecode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServicecode() {
        return servicecode;
    }

    /**
     * Define el valor de la propiedad servicecode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServicecode(String value) {
        this.servicecode = value;
    }

    /**
     * Obtiene el valor de la propiedad servicekey.
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
     * Define el valor de la propiedad servicekey.
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
     * Obtiene el valor de la propiedad servicename.
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
     * Define el valor de la propiedad servicename.
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
     * Obtiene el valor de la propiedad sitecode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitecode() {
        return sitecode;
    }

    /**
     * Define el valor de la propiedad sitecode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitecode(String value) {
        this.sitecode = value;
    }

    /**
     * Obtiene el valor de la propiedad sitekey.
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
     * Define el valor de la propiedad sitekey.
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
     * Obtiene el valor de la propiedad sitename.
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
     * Define el valor de la propiedad sitename.
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
