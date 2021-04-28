//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:48:44 PM CLT 
//


package bbr.b2b.logistic.xml.qorden_pred;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{}VEGR1_BEZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}VEGR2_BEZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}VEGR3_BEZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}VEGR4_BEZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}VEGR5_BEZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}VHART_BEZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}MAGRV_BEZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}VEBEZ" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="SEGMENT" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "vegr1BEZ",
    "vegr2BEZ",
    "vegr3BEZ",
    "vegr4BEZ",
    "vegr5BEZ",
    "vhartbez",
    "magrvbez",
    "vebez"
})
@XmlRootElement(name = "E1EDL38")
public class E1EDL38 {

    @XmlElement(name = "VEGR1_BEZ")
    protected VEGR1BEZ vegr1BEZ;
    @XmlElement(name = "VEGR2_BEZ")
    protected VEGR2BEZ vegr2BEZ;
    @XmlElement(name = "VEGR3_BEZ")
    protected VEGR3BEZ vegr3BEZ;
    @XmlElement(name = "VEGR4_BEZ")
    protected VEGR4BEZ vegr4BEZ;
    @XmlElement(name = "VEGR5_BEZ")
    protected VEGR5BEZ vegr5BEZ;
    @XmlElement(name = "VHART_BEZ")
    protected VHARTBEZ vhartbez;
    @XmlElement(name = "MAGRV_BEZ")
    protected MAGRVBEZ magrvbez;
    @XmlElement(name = "VEBEZ")
    protected VEBEZ vebez;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad vegr1BEZ.
     * 
     * @return
     *     possible object is
     *     {@link VEGR1BEZ }
     *     
     */
    public VEGR1BEZ getVEGR1BEZ() {
        return vegr1BEZ;
    }

    /**
     * Define el valor de la propiedad vegr1BEZ.
     * 
     * @param value
     *     allowed object is
     *     {@link VEGR1BEZ }
     *     
     */
    public void setVEGR1BEZ(VEGR1BEZ value) {
        this.vegr1BEZ = value;
    }

    /**
     * Obtiene el valor de la propiedad vegr2BEZ.
     * 
     * @return
     *     possible object is
     *     {@link VEGR2BEZ }
     *     
     */
    public VEGR2BEZ getVEGR2BEZ() {
        return vegr2BEZ;
    }

    /**
     * Define el valor de la propiedad vegr2BEZ.
     * 
     * @param value
     *     allowed object is
     *     {@link VEGR2BEZ }
     *     
     */
    public void setVEGR2BEZ(VEGR2BEZ value) {
        this.vegr2BEZ = value;
    }

    /**
     * Obtiene el valor de la propiedad vegr3BEZ.
     * 
     * @return
     *     possible object is
     *     {@link VEGR3BEZ }
     *     
     */
    public VEGR3BEZ getVEGR3BEZ() {
        return vegr3BEZ;
    }

    /**
     * Define el valor de la propiedad vegr3BEZ.
     * 
     * @param value
     *     allowed object is
     *     {@link VEGR3BEZ }
     *     
     */
    public void setVEGR3BEZ(VEGR3BEZ value) {
        this.vegr3BEZ = value;
    }

    /**
     * Obtiene el valor de la propiedad vegr4BEZ.
     * 
     * @return
     *     possible object is
     *     {@link VEGR4BEZ }
     *     
     */
    public VEGR4BEZ getVEGR4BEZ() {
        return vegr4BEZ;
    }

    /**
     * Define el valor de la propiedad vegr4BEZ.
     * 
     * @param value
     *     allowed object is
     *     {@link VEGR4BEZ }
     *     
     */
    public void setVEGR4BEZ(VEGR4BEZ value) {
        this.vegr4BEZ = value;
    }

    /**
     * Obtiene el valor de la propiedad vegr5BEZ.
     * 
     * @return
     *     possible object is
     *     {@link VEGR5BEZ }
     *     
     */
    public VEGR5BEZ getVEGR5BEZ() {
        return vegr5BEZ;
    }

    /**
     * Define el valor de la propiedad vegr5BEZ.
     * 
     * @param value
     *     allowed object is
     *     {@link VEGR5BEZ }
     *     
     */
    public void setVEGR5BEZ(VEGR5BEZ value) {
        this.vegr5BEZ = value;
    }

    /**
     * Obtiene el valor de la propiedad vhartbez.
     * 
     * @return
     *     possible object is
     *     {@link VHARTBEZ }
     *     
     */
    public VHARTBEZ getVHARTBEZ() {
        return vhartbez;
    }

    /**
     * Define el valor de la propiedad vhartbez.
     * 
     * @param value
     *     allowed object is
     *     {@link VHARTBEZ }
     *     
     */
    public void setVHARTBEZ(VHARTBEZ value) {
        this.vhartbez = value;
    }

    /**
     * Obtiene el valor de la propiedad magrvbez.
     * 
     * @return
     *     possible object is
     *     {@link MAGRVBEZ }
     *     
     */
    public MAGRVBEZ getMAGRVBEZ() {
        return magrvbez;
    }

    /**
     * Define el valor de la propiedad magrvbez.
     * 
     * @param value
     *     allowed object is
     *     {@link MAGRVBEZ }
     *     
     */
    public void setMAGRVBEZ(MAGRVBEZ value) {
        this.magrvbez = value;
    }

    /**
     * Obtiene el valor de la propiedad vebez.
     * 
     * @return
     *     possible object is
     *     {@link VEBEZ }
     *     
     */
    public VEBEZ getVEBEZ() {
        return vebez;
    }

    /**
     * Define el valor de la propiedad vebez.
     * 
     * @param value
     *     allowed object is
     *     {@link VEBEZ }
     *     
     */
    public void setVEBEZ(VEBEZ value) {
        this.vebez = value;
    }

    /**
     * Obtiene el valor de la propiedad segment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT() {
        return segment;
    }

    /**
     * Define el valor de la propiedad segment.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT(String value) {
        this.segment = value;
    }

}
