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
 * <p>Clase Java para companiesServiceStatusReportDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="companiesServiceStatusReportDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="custommaxdelay" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="datas" type="{http://facade.webservices.services.esb.bbr/}companiesServiceStatusDataDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="monitoringannotation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="servicekey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="servicename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
     * Obtiene el valor de la propiedad monitoringannotation.
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
     * Define el valor de la propiedad monitoringannotation.
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
