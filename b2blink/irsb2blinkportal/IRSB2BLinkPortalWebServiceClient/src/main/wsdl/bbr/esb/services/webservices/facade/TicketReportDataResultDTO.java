
package bbr.esb.services.webservices.facade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ticketReportDataResultDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ticketReportDataResultDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="adjunto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adjunto2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaadjunto" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaproceso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechasolicitud" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="nrosolicitud" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="referencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tiposolicitud" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ticketReportDataResultDTO", propOrder = {
    "adjunto",
    "adjunto2",
    "cliente",
    "estado",
    "fechaadjunto",
    "fechaproceso",
    "fechasolicitud",
    "nrosolicitud",
    "referencia",
    "tiposolicitud"
})
public class TicketReportDataResultDTO {

    protected String adjunto;
    protected String adjunto2;
    protected String cliente;
    protected String estado;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaadjunto;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaproceso;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechasolicitud;
    protected Long nrosolicitud;
    protected String referencia;
    protected String tiposolicitud;

    /**
     * Gets the value of the adjunto property.
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
     * Sets the value of the adjunto property.
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
     * Gets the value of the adjunto2 property.
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
     * Sets the value of the adjunto2 property.
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
     * Gets the value of the cliente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * Sets the value of the cliente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCliente(String value) {
        this.cliente = value;
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
     * Gets the value of the fechaadjunto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaadjunto() {
        return fechaadjunto;
    }

    /**
     * Sets the value of the fechaadjunto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaadjunto(XMLGregorianCalendar value) {
        this.fechaadjunto = value;
    }

    /**
     * Gets the value of the fechaproceso property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaproceso() {
        return fechaproceso;
    }

    /**
     * Sets the value of the fechaproceso property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaproceso(XMLGregorianCalendar value) {
        this.fechaproceso = value;
    }

    /**
     * Gets the value of the fechasolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechasolicitud() {
        return fechasolicitud;
    }

    /**
     * Sets the value of the fechasolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechasolicitud(XMLGregorianCalendar value) {
        this.fechasolicitud = value;
    }

    /**
     * Gets the value of the nrosolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNrosolicitud() {
        return nrosolicitud;
    }

    /**
     * Sets the value of the nrosolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNrosolicitud(Long value) {
        this.nrosolicitud = value;
    }

    /**
     * Gets the value of the referencia property.
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
     * Sets the value of the referencia property.
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
     * Gets the value of the tiposolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTiposolicitud() {
        return tiposolicitud;
    }

    /**
     * Sets the value of the tiposolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTiposolicitud(String value) {
        this.tiposolicitud = value;
    }

}
