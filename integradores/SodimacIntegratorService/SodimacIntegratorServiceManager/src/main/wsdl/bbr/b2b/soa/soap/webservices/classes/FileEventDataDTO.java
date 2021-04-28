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
 * <p>Clase Java para fileEventDataDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="fileEventDataDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="accesscode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="companykey" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="datecreated" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="datereceived" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="documentid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="ok" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="received" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
@XmlType(name = "fileEventDataDTO", propOrder = {
    "accesscode",
    "companykey",
    "datecreated",
    "datereceived",
    "documentid",
    "filename",
    "id",
    "ok",
    "received",
    "servicecode",
    "servicekey",
    "servicename",
    "sitecode",
    "sitekey",
    "sitename"
})
public class FileEventDataDTO {

    protected String accesscode;
    protected Long companykey;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datecreated;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datereceived;
    protected String documentid;
    protected String filename;
    protected Long id;
    protected boolean ok;
    protected boolean received;
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
