//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.08 a las 02:24:51 PM CLT 
//


package bbr.b2b.soa.soap.webservices.classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para contractedServiceMonitorDataDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="contractedServiceMonitorDataDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="accesscode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="detail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pendingmessages" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="servicename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "contractedServiceMonitorDataDTO", propOrder = {
    "accesscode",
    "detail",
    "pendingmessages",
    "servicename",
    "sitename"
})
public class ContractedServiceMonitorDataDTO {

    protected String accesscode;
    protected String detail;
    protected Integer pendingmessages;
    protected String servicename;
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
     * Obtiene el valor de la propiedad detail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Define el valor de la propiedad detail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetail(String value) {
        this.detail = value;
    }

    /**
     * Obtiene el valor de la propiedad pendingmessages.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPendingmessages() {
        return pendingmessages;
    }

    /**
     * Define el valor de la propiedad pendingmessages.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPendingmessages(Integer value) {
        this.pendingmessages = value;
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
