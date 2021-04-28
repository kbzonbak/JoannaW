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
 *         &lt;element ref="{}MOA5025" minOccurs="0"/&gt;
 *         &lt;element ref="{}CNT6069" minOccurs="0"/&gt;
 *         &lt;element ref="{}CNT6066" minOccurs="0"/&gt;
 *         &lt;element ref="{}UNT0074" minOccurs="0"/&gt;
 *         &lt;element ref="{}UNT0062" minOccurs="0"/&gt;
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
    "moa5025",
    "cnt6069",
    "cnt6066",
    "unt0074",
    "unt0062"
})
@XmlRootElement(name = "Z1EDS01")
public class Z1EDS01 {

    @XmlElement(name = "MOA5025")
    protected MOA5025 moa5025;
    @XmlElement(name = "CNT6069")
    protected CNT6069 cnt6069;
    @XmlElement(name = "CNT6066")
    protected CNT6066 cnt6066;
    @XmlElement(name = "UNT0074")
    protected UNT0074 unt0074;
    @XmlElement(name = "UNT0062")
    protected UNT0062 unt0062;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad moa5025.
     * 
     * @return
     *     possible object is
     *     {@link MOA5025 }
     *     
     */
    public MOA5025 getMOA5025() {
        return moa5025;
    }

    /**
     * Define el valor de la propiedad moa5025.
     * 
     * @param value
     *     allowed object is
     *     {@link MOA5025 }
     *     
     */
    public void setMOA5025(MOA5025 value) {
        this.moa5025 = value;
    }

    /**
     * Obtiene el valor de la propiedad cnt6069.
     * 
     * @return
     *     possible object is
     *     {@link CNT6069 }
     *     
     */
    public CNT6069 getCNT6069() {
        return cnt6069;
    }

    /**
     * Define el valor de la propiedad cnt6069.
     * 
     * @param value
     *     allowed object is
     *     {@link CNT6069 }
     *     
     */
    public void setCNT6069(CNT6069 value) {
        this.cnt6069 = value;
    }

    /**
     * Obtiene el valor de la propiedad cnt6066.
     * 
     * @return
     *     possible object is
     *     {@link CNT6066 }
     *     
     */
    public CNT6066 getCNT6066() {
        return cnt6066;
    }

    /**
     * Define el valor de la propiedad cnt6066.
     * 
     * @param value
     *     allowed object is
     *     {@link CNT6066 }
     *     
     */
    public void setCNT6066(CNT6066 value) {
        this.cnt6066 = value;
    }

    /**
     * Obtiene el valor de la propiedad unt0074.
     * 
     * @return
     *     possible object is
     *     {@link UNT0074 }
     *     
     */
    public UNT0074 getUNT0074() {
        return unt0074;
    }

    /**
     * Define el valor de la propiedad unt0074.
     * 
     * @param value
     *     allowed object is
     *     {@link UNT0074 }
     *     
     */
    public void setUNT0074(UNT0074 value) {
        this.unt0074 = value;
    }

    /**
     * Obtiene el valor de la propiedad unt0062.
     * 
     * @return
     *     possible object is
     *     {@link UNT0062 }
     *     
     */
    public UNT0062 getUNT0062() {
        return unt0062;
    }

    /**
     * Define el valor de la propiedad unt0062.
     * 
     * @param value
     *     allowed object is
     *     {@link UNT0062 }
     *     
     */
    public void setUNT0062(UNT0062 value) {
        this.unt0062 = value;
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
