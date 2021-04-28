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
 *         &lt;element ref="{}PRI5125" minOccurs="0"/&gt;
 *         &lt;element ref="{}PRI5118" minOccurs="0"/&gt;
 *         &lt;element ref="{}PRI5387" minOccurs="0"/&gt;
 *         &lt;element ref="{}PAC7224" minOccurs="0"/&gt;
 *         &lt;element ref="{}PACXXXX1" minOccurs="0"/&gt;
 *         &lt;element ref="{}PACXXXX2" minOccurs="0"/&gt;
 *         &lt;element ref="{}MOA5025" minOccurs="0"/&gt;
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
    "pri5125",
    "pri5118",
    "pri5387",
    "pac7224",
    "pacxxxx1",
    "pacxxxx2",
    "moa5025"
})
@XmlRootElement(name = "Z1EDP03")
public class Z1EDP03 {

    @XmlElement(name = "PRI5125")
    protected PRI5125 pri5125;
    @XmlElement(name = "PRI5118")
    protected PRI5118 pri5118;
    @XmlElement(name = "PRI5387")
    protected PRI5387 pri5387;
    @XmlElement(name = "PAC7224")
    protected PAC7224 pac7224;
    @XmlElement(name = "PACXXXX1")
    protected PACXXXX1 pacxxxx1;
    @XmlElement(name = "PACXXXX2")
    protected PACXXXX2 pacxxxx2;
    @XmlElement(name = "MOA5025")
    protected MOA5025 moa5025;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad pri5125.
     * 
     * @return
     *     possible object is
     *     {@link PRI5125 }
     *     
     */
    public PRI5125 getPRI5125() {
        return pri5125;
    }

    /**
     * Define el valor de la propiedad pri5125.
     * 
     * @param value
     *     allowed object is
     *     {@link PRI5125 }
     *     
     */
    public void setPRI5125(PRI5125 value) {
        this.pri5125 = value;
    }

    /**
     * Obtiene el valor de la propiedad pri5118.
     * 
     * @return
     *     possible object is
     *     {@link PRI5118 }
     *     
     */
    public PRI5118 getPRI5118() {
        return pri5118;
    }

    /**
     * Define el valor de la propiedad pri5118.
     * 
     * @param value
     *     allowed object is
     *     {@link PRI5118 }
     *     
     */
    public void setPRI5118(PRI5118 value) {
        this.pri5118 = value;
    }

    /**
     * Obtiene el valor de la propiedad pri5387.
     * 
     * @return
     *     possible object is
     *     {@link PRI5387 }
     *     
     */
    public PRI5387 getPRI5387() {
        return pri5387;
    }

    /**
     * Define el valor de la propiedad pri5387.
     * 
     * @param value
     *     allowed object is
     *     {@link PRI5387 }
     *     
     */
    public void setPRI5387(PRI5387 value) {
        this.pri5387 = value;
    }

    /**
     * Obtiene el valor de la propiedad pac7224.
     * 
     * @return
     *     possible object is
     *     {@link PAC7224 }
     *     
     */
    public PAC7224 getPAC7224() {
        return pac7224;
    }

    /**
     * Define el valor de la propiedad pac7224.
     * 
     * @param value
     *     allowed object is
     *     {@link PAC7224 }
     *     
     */
    public void setPAC7224(PAC7224 value) {
        this.pac7224 = value;
    }

    /**
     * Obtiene el valor de la propiedad pacxxxx1.
     * 
     * @return
     *     possible object is
     *     {@link PACXXXX1 }
     *     
     */
    public PACXXXX1 getPACXXXX1() {
        return pacxxxx1;
    }

    /**
     * Define el valor de la propiedad pacxxxx1.
     * 
     * @param value
     *     allowed object is
     *     {@link PACXXXX1 }
     *     
     */
    public void setPACXXXX1(PACXXXX1 value) {
        this.pacxxxx1 = value;
    }

    /**
     * Obtiene el valor de la propiedad pacxxxx2.
     * 
     * @return
     *     possible object is
     *     {@link PACXXXX2 }
     *     
     */
    public PACXXXX2 getPACXXXX2() {
        return pacxxxx2;
    }

    /**
     * Define el valor de la propiedad pacxxxx2.
     * 
     * @param value
     *     allowed object is
     *     {@link PACXXXX2 }
     *     
     */
    public void setPACXXXX2(PACXXXX2 value) {
        this.pacxxxx2 = value;
    }

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
