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
 * <p>Clase Java para ticketEventDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ticketEventDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}elementDTO"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="adjunto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="adjunto2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="attachmentdate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="autoattachmentdate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="companykey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="currentstatetypedate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="currentstatetypekey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="datecreated" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="messageid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="referencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="servicekey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="sitekey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="ticketnumber" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="userid" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ticketEventDTO", propOrder = {
    "adjunto",
    "adjunto2",
    "attachmentdate",
    "autoattachmentdate",
    "companykey",
    "currentstatetypedate",
    "currentstatetypekey",
    "datecreated",
    "messageid",
    "referencia",
    "servicekey",
    "sitekey",
    "ticketnumber",
    "userid"
})
public class TicketEventDTO
    extends ElementDTO
{

    protected String adjunto;
    protected String adjunto2;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar attachmentdate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar autoattachmentdate;
    protected Long companykey;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar currentstatetypedate;
    protected Long currentstatetypekey;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datecreated;
    protected String messageid;
    protected String referencia;
    protected Long servicekey;
    protected Long sitekey;
    protected Long ticketnumber;
    protected Long userid;

    /**
     * Obtiene el valor de la propiedad adjunto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdjunto() {
        return adjunto;
    }

    /**
     * Define el valor de la propiedad adjunto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdjunto(String value) {
        this.adjunto = value;
    }

    /**
     * Obtiene el valor de la propiedad adjunto2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdjunto2() {
        return adjunto2;
    }

    /**
     * Define el valor de la propiedad adjunto2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdjunto2(String value) {
        this.adjunto2 = value;
    }

    /**
     * Obtiene el valor de la propiedad attachmentdate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAttachmentdate() {
        return attachmentdate;
    }

    /**
     * Define el valor de la propiedad attachmentdate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAttachmentdate(XMLGregorianCalendar value) {
        this.attachmentdate = value;
    }

    /**
     * Obtiene el valor de la propiedad autoattachmentdate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAutoattachmentdate() {
        return autoattachmentdate;
    }

    /**
     * Define el valor de la propiedad autoattachmentdate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAutoattachmentdate(XMLGregorianCalendar value) {
        this.autoattachmentdate = value;
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
     * Obtiene el valor de la propiedad currentstatetypedate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCurrentstatetypedate() {
        return currentstatetypedate;
    }

    /**
     * Define el valor de la propiedad currentstatetypedate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCurrentstatetypedate(XMLGregorianCalendar value) {
        this.currentstatetypedate = value;
    }

    /**
     * Obtiene el valor de la propiedad currentstatetypekey.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCurrentstatetypekey() {
        return currentstatetypekey;
    }

    /**
     * Define el valor de la propiedad currentstatetypekey.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCurrentstatetypekey(Long value) {
        this.currentstatetypekey = value;
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
     * Obtiene el valor de la propiedad messageid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageid() {
        return messageid;
    }

    /**
     * Define el valor de la propiedad messageid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageid(String value) {
        this.messageid = value;
    }

    /**
     * Obtiene el valor de la propiedad referencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Define el valor de la propiedad referencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencia(String value) {
        this.referencia = value;
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

    /**
     * Obtiene el valor de la propiedad ticketnumber.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTicketnumber() {
        return ticketnumber;
    }

    /**
     * Define el valor de la propiedad ticketnumber.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTicketnumber(Long value) {
        this.ticketnumber = value;
    }

    /**
     * Obtiene el valor de la propiedad userid.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * Define el valor de la propiedad userid.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setUserid(Long value) {
        this.userid = value;
    }

}
