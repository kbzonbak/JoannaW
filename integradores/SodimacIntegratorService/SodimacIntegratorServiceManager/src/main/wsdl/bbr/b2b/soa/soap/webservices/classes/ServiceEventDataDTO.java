//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.08 a las 02:24:51 PM CLT 
//


package bbr.b2b.soa.soap.webservices.classes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para serviceEventDataDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="serviceEventDataDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="accesscode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="activation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="companykey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="custom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="formatkey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="formatname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="orders" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="resenddata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="servicecode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="servicekey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="servicename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sitecode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sitekey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="sitename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="statuscode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceEventDataDTO", propOrder = {
    "accesscode",
    "activation",
    "companykey",
    "custom",
    "formatkey",
    "formatname",
    "id",
    "orders",
    "resenddata",
    "servicecode",
    "servicekey",
    "servicename",
    "sitecode",
    "sitekey",
    "sitename",
    "statuscode"
})
public class ServiceEventDataDTO {

    protected String accesscode;
    protected String activation;
    protected Long companykey;
    protected String custom;
    protected Long formatkey;
    protected String formatname;
    protected Long id;
    @XmlElement(nillable = true)
    protected List<String> orders;
    protected String resenddata;
    protected String servicecode;
    protected Long servicekey;
    protected String servicename;
    protected String sitecode;
    protected Long sitekey;
    protected String sitename;
    protected String statuscode;

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
     * Obtiene el valor de la propiedad custom.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustom() {
        return custom;
    }

    /**
     * Define el valor de la propiedad custom.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustom(String value) {
        this.custom = value;
    }

    /**
     * Obtiene el valor de la propiedad formatkey.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFormatkey() {
        return formatkey;
    }

    /**
     * Define el valor de la propiedad formatkey.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFormatkey(Long value) {
        this.formatkey = value;
    }

    /**
     * Obtiene el valor de la propiedad formatname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormatname() {
        return formatname;
    }

    /**
     * Define el valor de la propiedad formatname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatname(String value) {
        this.formatname = value;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the orders property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orders property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrders().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOrders() {
        if (orders == null) {
            orders = new ArrayList<String>();
        }
        return this.orders;
    }

    /**
     * Obtiene el valor de la propiedad resenddata.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResenddata() {
        return resenddata;
    }

    /**
     * Define el valor de la propiedad resenddata.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResenddata(String value) {
        this.resenddata = value;
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

    /**
     * Obtiene el valor de la propiedad statuscode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatuscode() {
        return statuscode;
    }

    /**
     * Define el valor de la propiedad statuscode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatuscode(String value) {
        this.statuscode = value;
    }

}
