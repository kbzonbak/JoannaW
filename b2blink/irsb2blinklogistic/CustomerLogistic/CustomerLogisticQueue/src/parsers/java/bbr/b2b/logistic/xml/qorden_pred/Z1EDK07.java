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
 *         &lt;element ref="{}PAT4279" minOccurs="0"/&gt;
 *         &lt;element ref="{}PAT2475" minOccurs="0"/&gt;
 *         &lt;element ref="{}PAT2151" minOccurs="0"/&gt;
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
    "pat4279",
    "pat2475",
    "pat2151"
})
@XmlRootElement(name = "Z1EDK07")
public class Z1EDK07 {

    @XmlElement(name = "PAT4279")
    protected PAT4279 pat4279;
    @XmlElement(name = "PAT2475")
    protected PAT2475 pat2475;
    @XmlElement(name = "PAT2151")
    protected PAT2151 pat2151;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad pat4279.
     * 
     * @return
     *     possible object is
     *     {@link PAT4279 }
     *     
     */
    public PAT4279 getPAT4279() {
        return pat4279;
    }

    /**
     * Define el valor de la propiedad pat4279.
     * 
     * @param value
     *     allowed object is
     *     {@link PAT4279 }
     *     
     */
    public void setPAT4279(PAT4279 value) {
        this.pat4279 = value;
    }

    /**
     * Obtiene el valor de la propiedad pat2475.
     * 
     * @return
     *     possible object is
     *     {@link PAT2475 }
     *     
     */
    public PAT2475 getPAT2475() {
        return pat2475;
    }

    /**
     * Define el valor de la propiedad pat2475.
     * 
     * @param value
     *     allowed object is
     *     {@link PAT2475 }
     *     
     */
    public void setPAT2475(PAT2475 value) {
        this.pat2475 = value;
    }

    /**
     * Obtiene el valor de la propiedad pat2151.
     * 
     * @return
     *     possible object is
     *     {@link PAT2151 }
     *     
     */
    public PAT2151 getPAT2151() {
        return pat2151;
    }

    /**
     * Define el valor de la propiedad pat2151.
     * 
     * @param value
     *     allowed object is
     *     {@link PAT2151 }
     *     
     */
    public void setPAT2151(PAT2151 value) {
        this.pat2151 = value;
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
