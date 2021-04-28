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
 * <p>Clase Java para fileEventDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="fileEventDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}elementDTO"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="companykey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="datecreated" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="datereceived" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="documentid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="informed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ok" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="received" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
@XmlType(name = "fileEventDTO", propOrder = {
    "companykey",
    "datecreated",
    "datereceived",
    "documentid",
    "filename",
    "informed",
    "ok",
    "received",
    "servicekey",
    "sitekey"
})
public class FileEventDTO
    extends ElementDTO
{

    protected Long companykey;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datecreated;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datereceived;
    protected String documentid;
    protected String filename;
    protected boolean informed;
    protected boolean ok;
    protected boolean received;
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
     * Obtiene el valor de la propiedad datereceived.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatereceived() {
        return datereceived;
    }

    /**
     * Define el valor de la propiedad datereceived.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatereceived(XMLGregorianCalendar value) {
        this.datereceived = value;
    }

    /**
     * Obtiene el valor de la propiedad documentid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentid() {
        return documentid;
    }

    /**
     * Define el valor de la propiedad documentid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentid(String value) {
        this.documentid = value;
    }

    /**
     * Obtiene el valor de la propiedad filename.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Define el valor de la propiedad filename.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilename(String value) {
        this.filename = value;
    }

    /**
     * Obtiene el valor de la propiedad informed.
     * 
     */
    public boolean isInformed() {
        return informed;
    }

    /**
     * Define el valor de la propiedad informed.
     * 
     */
    public void setInformed(boolean value) {
        this.informed = value;
    }

    /**
     * Obtiene el valor de la propiedad ok.
     * 
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * Define el valor de la propiedad ok.
     * 
     */
    public void setOk(boolean value) {
        this.ok = value;
    }

    /**
     * Obtiene el valor de la propiedad received.
     * 
     */
    public boolean isReceived() {
        return received;
    }

    /**
     * Define el valor de la propiedad received.
     * 
     */
    public void setReceived(boolean value) {
        this.received = value;
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
