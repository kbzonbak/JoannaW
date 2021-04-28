//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.08 a las 02:24:51 PM CLT 
//


package bbr.b2b.soa.soap.webservices.classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para serviceEventDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="serviceEventDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}elementDTO"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="companykey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="custom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="datecreated" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="dateprocessed" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="processed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="resenddata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="servicekey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="sitekey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceEventDTO", propOrder = {
    "companykey",
    "custom",
    "datecreated",
    "dateprocessed",
    "processed",
    "resenddata",
    "servicekey",
    "sitekey"
})
public class ServiceEventDTO
    extends ElementDTO
{

    protected Long companykey;
    protected String custom;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datecreated;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateprocessed;
    protected boolean processed;
    protected String resenddata;
    protected Long servicekey;
    protected Long sitekey;

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
     * Obtiene el valor de la propiedad datecreated.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatecreated() {
        return datecreated;
    }

    /**
     * Define el valor de la propiedad datecreated.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatecreated(XMLGregorianCalendar value) {
        this.datecreated = value;
    }

    /**
     * Obtiene el valor de la propiedad dateprocessed.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateprocessed() {
        return dateprocessed;
    }

    /**
     * Define el valor de la propiedad dateprocessed.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateprocessed(XMLGregorianCalendar value) {
        this.dateprocessed = value;
    }

    /**
     * Obtiene el valor de la propiedad processed.
     * 
     */
    public boolean isProcessed() {
        return processed;
    }

    /**
     * Define el valor de la propiedad processed.
     * 
     */
    public void setProcessed(boolean value) {
        this.processed = value;
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

}
