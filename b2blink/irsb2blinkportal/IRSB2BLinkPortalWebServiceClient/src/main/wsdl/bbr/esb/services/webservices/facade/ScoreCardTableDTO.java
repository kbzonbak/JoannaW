
package bbr.esb.services.webservices.facade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for scoreCardTableDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="scoreCardTableDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}elementDTO">
 *       &lt;sequence>
 *         &lt;element name="companyid" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="numdoc" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="retail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serviceid" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="siteid" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="sitio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scoreCardTableDTO", propOrder = {
    "companyid",
    "estado",
    "fecha",
    "numdoc",
    "retail",
    "serviceid",
    "siteid",
    "sitio"
})
public class ScoreCardTableDTO
    extends ElementDTO
{

    protected Long companyid;
    protected String estado;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    protected Long numdoc;
    protected String retail;
    protected Long serviceid;
    protected Long siteid;
    protected String sitio;

    /**
     * Gets the value of the companyid property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCompanyid() {
        return companyid;
    }

    /**
     * Sets the value of the companyid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCompanyid(Long value) {
        this.companyid = value;
    }

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Gets the value of the fecha property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the numdoc property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumdoc() {
        return numdoc;
    }

    /**
     * Sets the value of the numdoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumdoc(Long value) {
        this.numdoc = value;
    }

    /**
     * Gets the value of the retail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetail() {
        return retail;
    }

    /**
     * Sets the value of the retail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetail(String value) {
        this.retail = value;
    }

    /**
     * Gets the value of the serviceid property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getServiceid() {
        return serviceid;
    }

    /**
     * Sets the value of the serviceid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setServiceid(Long value) {
        this.serviceid = value;
    }

    /**
     * Gets the value of the siteid property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSiteid() {
        return siteid;
    }

    /**
     * Sets the value of the siteid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSiteid(Long value) {
        this.siteid = value;
    }

    /**
     * Gets the value of the sitio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitio() {
        return sitio;
    }

    /**
     * Sets the value of the sitio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitio(String value) {
        this.sitio = value;
    }

}
