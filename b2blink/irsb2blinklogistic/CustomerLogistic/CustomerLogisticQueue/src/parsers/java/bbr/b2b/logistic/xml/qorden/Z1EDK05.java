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
 *         &lt;element ref="{}RFF1153" minOccurs="0"/&gt;
 *         &lt;element ref="{}RFF1154" minOccurs="0"/&gt;
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
    "rff1153",
    "rff1154"
})
@XmlRootElement(name = "Z1EDK05")
public class Z1EDK05 {

    @XmlElement(name = "RFF1153")
    protected RFF1153 rff1153;
    @XmlElement(name = "RFF1154")
    protected RFF1154 rff1154;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad rff1153.
     * 
     * @return
     *     possible object is
     *     {@link RFF1153 }
     *     
     */
    public RFF1153 getRFF1153() {
        return rff1153;
    }

    /**
     * Define el valor de la propiedad rff1153.
     * 
     * @param value
     *     allowed object is
     *     {@link RFF1153 }
     *     
     */
    public void setRFF1153(RFF1153 value) {
        this.rff1153 = value;
    }

    /**
     * Obtiene el valor de la propiedad rff1154.
     * 
     * @return
     *     possible object is
     *     {@link RFF1154 }
     *     
     */
    public RFF1154 getRFF1154() {
        return rff1154;
    }

    /**
     * Define el valor de la propiedad rff1154.
     * 
     * @param value
     *     allowed object is
     *     {@link RFF1154 }
     *     
     */
    public void setRFF1154(RFF1154 value) {
        this.rff1154 = value;
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
