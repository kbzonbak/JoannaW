//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2019.06.24 a las 09:55:44 AM CLT 
//


package bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrdenConsolidado;

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
 *         &lt;element name="nombreportal" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codproveedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codcomprador" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroconsolidado" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="archivo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
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
    "nombreportal",
    "codproveedor",
    "codcomprador",
    "numeroconsolidado",
    "archivo",
    "estado",
    "fecha"
})
@XmlRootElement(name = "NotificacionReciboOrdenConsolidado")
public class NotificacionReciboOrdenConsolidado {

    @XmlElement(required = true)
    protected String nombreportal;
    @XmlElement(required = true)
    protected String codproveedor;
    @XmlElement(required = true)
    protected String codcomprador;
    @XmlElement(required = true)
    protected String numeroconsolidado;
    @XmlElement(required = true)
    protected String archivo;
    @XmlElement(required = true)
    protected String estado;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;

    /**
     * Obtiene el valor de la propiedad nombreportal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreportal() {
        return nombreportal;
    }

    /**
     * Define el valor de la propiedad nombreportal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreportal(String value) {
        this.nombreportal = value;
    }

    /**
     * Obtiene el valor de la propiedad codproveedor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodproveedor() {
        return codproveedor;
    }

    /**
     * Define el valor de la propiedad codproveedor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodproveedor(String value) {
        this.codproveedor = value;
    }

    /**
     * Obtiene el valor de la propiedad codcomprador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodcomprador() {
        return codcomprador;
    }

    /**
     * Define el valor de la propiedad codcomprador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodcomprador(String value) {
        this.codcomprador = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroconsolidado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroconsolidado() {
        return numeroconsolidado;
    }

    /**
     * Define el valor de la propiedad numeroconsolidado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroconsolidado(String value) {
        this.numeroconsolidado = value;
    }

    /**
     * Obtiene el valor de la propiedad archivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArchivo() {
        return archivo;
    }

    /**
     * Define el valor de la propiedad archivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArchivo(String value) {
        this.archivo = value;
    }

    /**
     * Obtiene el valor de la propiedad estado.
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
     * Define el valor de la propiedad estado.
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
     * Obtiene el valor de la propiedad fecha.
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
     * Define el valor de la propiedad fecha.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

}
