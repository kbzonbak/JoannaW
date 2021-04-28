
package cl.chilexpress.ws.osb.ebo.headerresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for datosTransaccion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datosTransaccion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="internalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idTransaccionNegocio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaHoraInicioTrx" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="fechaHoraFinTrx" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datosTransaccion", propOrder = {
    "internalCode",
    "idTransaccionNegocio",
    "fechaHoraInicioTrx",
    "fechaHoraFinTrx",
    "estado"
})
public class DatosTransaccion {

    @XmlElement(required = true)
    protected String internalCode;
    @XmlElement(required = true)
    protected String idTransaccionNegocio;
    @XmlElement(required = true, nillable = true)
    protected XMLGregorianCalendar fechaHoraInicioTrx;
    @XmlElement(required = true, nillable = true)
    protected XMLGregorianCalendar fechaHoraFinTrx;
    @XmlElement(required = true, nillable = true)
    protected String estado;

    /**
     * Gets the value of the internalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternalCode() {
        return internalCode;
    }

    /**
     * Sets the value of the internalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternalCode(String value) {
        this.internalCode = value;
    }

    /**
     * Gets the value of the idTransaccionNegocio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTransaccionNegocio() {
        return idTransaccionNegocio;
    }

    /**
     * Sets the value of the idTransaccionNegocio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTransaccionNegocio(String value) {
        this.idTransaccionNegocio = value;
    }

    /**
     * Gets the value of the fechaHoraInicioTrx property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaHoraInicioTrx() {
        return fechaHoraInicioTrx;
    }

    /**
     * Sets the value of the fechaHoraInicioTrx property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaHoraInicioTrx(XMLGregorianCalendar value) {
        this.fechaHoraInicioTrx = value;
    }

    /**
     * Gets the value of the fechaHoraFinTrx property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaHoraFinTrx() {
        return fechaHoraFinTrx;
    }

    /**
     * Sets the value of the fechaHoraFinTrx property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaHoraFinTrx(XMLGregorianCalendar value) {
        this.fechaHoraFinTrx = value;
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

}
