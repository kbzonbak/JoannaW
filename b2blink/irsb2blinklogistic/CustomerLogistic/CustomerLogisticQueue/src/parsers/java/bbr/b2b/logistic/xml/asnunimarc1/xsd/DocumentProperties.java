//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.08.28 a las 06:38:56 PM CLT 
//


package bbr.b2b.logistic.xml.asnunimarc1.xsd;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Author" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LastAuthor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Created" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastSaved" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Company" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "author",
    "lastAuthor",
    "created",
    "lastSaved",
    "company",
    "version"
})
@XmlRootElement(name = "DocumentProperties", namespace = "urn:schemas-microsoft-com:office:office")
public class DocumentProperties {

    @XmlElement(name = "Author", namespace = "urn:schemas-microsoft-com:office:office", required = true)
    protected String author;
    @XmlElement(name = "LastAuthor", namespace = "urn:schemas-microsoft-com:office:office", required = true)
    protected String lastAuthor;
    @XmlElement(name = "Created", namespace = "urn:schemas-microsoft-com:office:office", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar created;
    @XmlElement(name = "LastSaved", namespace = "urn:schemas-microsoft-com:office:office", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastSaved;
    @XmlElement(name = "Company", namespace = "urn:schemas-microsoft-com:office:office", required = true)
    protected String company;
    @XmlElement(name = "Version", namespace = "urn:schemas-microsoft-com:office:office", required = true)
    protected BigDecimal version;

    /**
     * Obtiene el valor de la propiedad author.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Define el valor de la propiedad author.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthor(String value) {
        this.author = value;
    }

    /**
     * Obtiene el valor de la propiedad lastAuthor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastAuthor() {
        return lastAuthor;
    }

    /**
     * Define el valor de la propiedad lastAuthor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastAuthor(String value) {
        this.lastAuthor = value;
    }

    /**
     * Obtiene el valor de la propiedad created.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreated() {
        return created;
    }

    /**
     * Define el valor de la propiedad created.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreated(XMLGregorianCalendar value) {
        this.created = value;
    }

    /**
     * Obtiene el valor de la propiedad lastSaved.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastSaved() {
        return lastSaved;
    }

    /**
     * Define el valor de la propiedad lastSaved.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastSaved(XMLGregorianCalendar value) {
        this.lastSaved = value;
    }

    /**
     * Obtiene el valor de la propiedad company.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompany() {
        return company;
    }

    /**
     * Define el valor de la propiedad company.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompany(String value) {
        this.company = value;
    }

    /**
     * Obtiene el valor de la propiedad version.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVersion() {
        return version;
    }

    /**
     * Define el valor de la propiedad version.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVersion(BigDecimal value) {
        this.version = value;
    }

}
