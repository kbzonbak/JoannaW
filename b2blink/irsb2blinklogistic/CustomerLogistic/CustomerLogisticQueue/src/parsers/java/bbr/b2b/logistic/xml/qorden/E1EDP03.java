//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:48:19 PM CLT 
//


package bbr.b2b.logistic.xml.qorden;

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
 *         &lt;element ref="{}IDDAT" minOccurs="0"/&gt;
 *         &lt;element ref="{}DATUM" minOccurs="0"/&gt;
 *         &lt;element ref="{}UZEIT" minOccurs="0"/&gt;
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
    "iddat",
    "datum",
    "uzeit"
})
@XmlRootElement(name = "E1EDP03")
public class E1EDP03 {

    @XmlElement(name = "IDDAT")
    protected IDDAT iddat;
    @XmlElement(name = "DATUM")
    protected DATUM datum;
    @XmlElement(name = "UZEIT")
    protected UZEIT uzeit;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad iddat.
     * 
     * @return
     *     possible object is
     *     {@link IDDAT }
     *     
     */
    public IDDAT getIDDAT() {
        return iddat;
    }

    /**
     * Define el valor de la propiedad iddat.
     * 
     * @param value
     *     allowed object is
     *     {@link IDDAT }
     *     
     */
    public void setIDDAT(IDDAT value) {
        this.iddat = value;
    }

    /**
     * Obtiene el valor de la propiedad datum.
     * 
     * @return
     *     possible object is
     *     {@link DATUM }
     *     
     */
    public DATUM getDATUM() {
        return datum;
    }

    /**
     * Define el valor de la propiedad datum.
     * 
     * @param value
     *     allowed object is
     *     {@link DATUM }
     *     
     */
    public void setDATUM(DATUM value) {
        this.datum = value;
    }

    /**
     * Obtiene el valor de la propiedad uzeit.
     * 
     * @return
     *     possible object is
     *     {@link UZEIT }
     *     
     */
    public UZEIT getUZEIT() {
        return uzeit;
    }

    /**
     * Define el valor de la propiedad uzeit.
     * 
     * @param value
     *     allowed object is
     *     {@link UZEIT }
     *     
     */
    public void setUZEIT(UZEIT value) {
        this.uzeit = value;
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
