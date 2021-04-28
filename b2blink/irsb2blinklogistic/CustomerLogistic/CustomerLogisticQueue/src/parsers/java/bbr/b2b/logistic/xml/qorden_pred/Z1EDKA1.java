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
 *         &lt;element ref="{}NAD3039" minOccurs="0"/&gt;
 *         &lt;element ref="{}NAD3055" minOccurs="0"/&gt;
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
    "nad3039",
    "nad3055"
})
@XmlRootElement(name = "Z1EDKA1")
public class Z1EDKA1 {

    @XmlElement(name = "NAD3039")
    protected NAD3039 nad3039;
    @XmlElement(name = "NAD3055")
    protected NAD3055 nad3055;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad nad3039.
     * 
     * @return
     *     possible object is
     *     {@link NAD3039 }
     *     
     */
    public NAD3039 getNAD3039() {
        return nad3039;
    }

    /**
     * Define el valor de la propiedad nad3039.
     * 
     * @param value
     *     allowed object is
     *     {@link NAD3039 }
     *     
     */
    public void setNAD3039(NAD3039 value) {
        this.nad3039 = value;
    }

    /**
     * Obtiene el valor de la propiedad nad3055.
     * 
     * @return
     *     possible object is
     *     {@link NAD3055 }
     *     
     */
    public NAD3055 getNAD3055() {
        return nad3055;
    }

    /**
     * Define el valor de la propiedad nad3055.
     * 
     * @param value
     *     allowed object is
     *     {@link NAD3055 }
     *     
     */
    public void setNAD3055(NAD3055 value) {
        this.nad3055 = value;
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
