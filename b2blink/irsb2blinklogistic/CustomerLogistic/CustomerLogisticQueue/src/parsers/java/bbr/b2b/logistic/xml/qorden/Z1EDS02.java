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
 *         &lt;element ref="{}UNH0062" minOccurs="0"/&gt;
 *         &lt;element ref="{}UNH0052" minOccurs="0"/&gt;
 *         &lt;element ref="{}UNH0051" minOccurs="0"/&gt;
 *         &lt;element ref="{}UNH0057" minOccurs="0"/&gt;
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
    "unh0062",
    "unh0052",
    "unh0051",
    "unh0057"
})
@XmlRootElement(name = "Z1EDS02")
public class Z1EDS02 {

    @XmlElement(name = "UNH0062")
    protected UNH0062 unh0062;
    @XmlElement(name = "UNH0052")
    protected UNH0052 unh0052;
    @XmlElement(name = "UNH0051")
    protected UNH0051 unh0051;
    @XmlElement(name = "UNH0057")
    protected UNH0057 unh0057;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad unh0062.
     * 
     * @return
     *     possible object is
     *     {@link UNH0062 }
     *     
     */
    public UNH0062 getUNH0062() {
        return unh0062;
    }

    /**
     * Define el valor de la propiedad unh0062.
     * 
     * @param value
     *     allowed object is
     *     {@link UNH0062 }
     *     
     */
    public void setUNH0062(UNH0062 value) {
        this.unh0062 = value;
    }

    /**
     * Obtiene el valor de la propiedad unh0052.
     * 
     * @return
     *     possible object is
     *     {@link UNH0052 }
     *     
     */
    public UNH0052 getUNH0052() {
        return unh0052;
    }

    /**
     * Define el valor de la propiedad unh0052.
     * 
     * @param value
     *     allowed object is
     *     {@link UNH0052 }
     *     
     */
    public void setUNH0052(UNH0052 value) {
        this.unh0052 = value;
    }

    /**
     * Obtiene el valor de la propiedad unh0051.
     * 
     * @return
     *     possible object is
     *     {@link UNH0051 }
     *     
     */
    public UNH0051 getUNH0051() {
        return unh0051;
    }

    /**
     * Define el valor de la propiedad unh0051.
     * 
     * @param value
     *     allowed object is
     *     {@link UNH0051 }
     *     
     */
    public void setUNH0051(UNH0051 value) {
        this.unh0051 = value;
    }

    /**
     * Obtiene el valor de la propiedad unh0057.
     * 
     * @return
     *     possible object is
     *     {@link UNH0057 }
     *     
     */
    public UNH0057 getUNH0057() {
        return unh0057;
    }

    /**
     * Define el valor de la propiedad unh0057.
     * 
     * @param value
     *     allowed object is
     *     {@link UNH0057 }
     *     
     */
    public void setUNH0057(UNH0057 value) {
        this.unh0057 = value;
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
