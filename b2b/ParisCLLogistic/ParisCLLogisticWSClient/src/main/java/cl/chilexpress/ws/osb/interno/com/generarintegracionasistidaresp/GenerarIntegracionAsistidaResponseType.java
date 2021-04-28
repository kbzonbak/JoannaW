
package cl.chilexpress.ws.osb.interno.com.generarintegracionasistidaresp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import cl.chilexpress.ws.osb.ebo.estadooperacion.EstadoOperacionType;


/**
 * <p>Java class for GenerarIntegracionAsistidaResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GenerarIntegracionAsistidaResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EstadoOperacion" type="{http://ws.chilexpress.cl/OSB/EBO/EstadoOperacion}EstadoOperacionType"/>
 *         &lt;element name="DatosOT" type="{http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaResp}DatosOTType"/>
 *         &lt;element name="DatosEtiqueta" type="{http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaResp}DatosEtiquetaType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenerarIntegracionAsistidaResponseType", propOrder = {
    "estadoOperacion",
    "datosOT",
    "datosEtiqueta"
})
public class GenerarIntegracionAsistidaResponseType {

    @XmlElement(name = "EstadoOperacion", required = true)
    protected EstadoOperacionType estadoOperacion;
    @XmlElement(name = "DatosOT", required = true)
    protected DatosOTType datosOT;
    @XmlElement(name = "DatosEtiqueta")
    protected DatosEtiquetaType datosEtiqueta;

    /**
     * Gets the value of the estadoOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link EstadoOperacionType }
     *     
     */
    public EstadoOperacionType getEstadoOperacion() {
        return estadoOperacion;
    }

    /**
     * Sets the value of the estadoOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoOperacionType }
     *     
     */
    public void setEstadoOperacion(EstadoOperacionType value) {
        this.estadoOperacion = value;
    }

    /**
     * Gets the value of the datosOT property.
     * 
     * @return
     *     possible object is
     *     {@link DatosOTType }
     *     
     */
    public DatosOTType getDatosOT() {
        return datosOT;
    }

    /**
     * Sets the value of the datosOT property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosOTType }
     *     
     */
    public void setDatosOT(DatosOTType value) {
        this.datosOT = value;
    }

    /**
     * Gets the value of the datosEtiqueta property.
     * 
     * @return
     *     possible object is
     *     {@link DatosEtiquetaType }
     *     
     */
    public DatosEtiquetaType getDatosEtiqueta() {
        return datosEtiqueta;
    }

    /**
     * Sets the value of the datosEtiqueta property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosEtiquetaType }
     *     
     */
    public void setDatosEtiqueta(DatosEtiquetaType value) {
        this.datosEtiqueta = value;
    }

}
