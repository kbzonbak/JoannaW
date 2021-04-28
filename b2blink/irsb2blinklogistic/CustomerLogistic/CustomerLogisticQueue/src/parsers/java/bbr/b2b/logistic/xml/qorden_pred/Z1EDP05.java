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
 *         &lt;element ref="{}IMD7077" minOccurs="0"/&gt;
 *         &lt;element ref="{}IMD7009" minOccurs="0"/&gt;
 *         &lt;element ref="{}IMD3055" minOccurs="0"/&gt;
 *         &lt;element ref="{}EAN11" minOccurs="0"/&gt;
 *         &lt;element ref="{}EANTP" minOccurs="0"/&gt;
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
    "imd7077",
    "imd7009",
    "imd3055",
    "ean11",
    "eantp"
})
@XmlRootElement(name = "Z1EDP05")
public class Z1EDP05 {

    @XmlElement(name = "IMD7077")
    protected IMD7077 imd7077;
    @XmlElement(name = "IMD7009")
    protected IMD7009 imd7009;
    @XmlElement(name = "IMD3055")
    protected IMD3055 imd3055;
    @XmlElement(name = "EAN11")
    protected EAN11 ean11;
    @XmlElement(name = "EANTP")
    protected EANTP eantp;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad imd7077.
     * 
     * @return
     *     possible object is
     *     {@link IMD7077 }
     *     
     */
    public IMD7077 getIMD7077() {
        return imd7077;
    }

    /**
     * Define el valor de la propiedad imd7077.
     * 
     * @param value
     *     allowed object is
     *     {@link IMD7077 }
     *     
     */
    public void setIMD7077(IMD7077 value) {
        this.imd7077 = value;
    }

    /**
     * Obtiene el valor de la propiedad imd7009.
     * 
     * @return
     *     possible object is
     *     {@link IMD7009 }
     *     
     */
    public IMD7009 getIMD7009() {
        return imd7009;
    }

    /**
     * Define el valor de la propiedad imd7009.
     * 
     * @param value
     *     allowed object is
     *     {@link IMD7009 }
     *     
     */
    public void setIMD7009(IMD7009 value) {
        this.imd7009 = value;
    }

    /**
     * Obtiene el valor de la propiedad imd3055.
     * 
     * @return
     *     possible object is
     *     {@link IMD3055 }
     *     
     */
    public IMD3055 getIMD3055() {
        return imd3055;
    }

    /**
     * Define el valor de la propiedad imd3055.
     * 
     * @param value
     *     allowed object is
     *     {@link IMD3055 }
     *     
     */
    public void setIMD3055(IMD3055 value) {
        this.imd3055 = value;
    }

    /**
     * Obtiene el valor de la propiedad ean11.
     * 
     * @return
     *     possible object is
     *     {@link EAN11 }
     *     
     */
    public EAN11 getEAN11() {
        return ean11;
    }

    /**
     * Define el valor de la propiedad ean11.
     * 
     * @param value
     *     allowed object is
     *     {@link EAN11 }
     *     
     */
    public void setEAN11(EAN11 value) {
        this.ean11 = value;
    }

    /**
     * Obtiene el valor de la propiedad eantp.
     * 
     * @return
     *     possible object is
     *     {@link EANTP }
     *     
     */
    public EANTP getEANTP() {
        return eantp;
    }

    /**
     * Define el valor de la propiedad eantp.
     * 
     * @param value
     *     allowed object is
     *     {@link EANTP }
     *     
     */
    public void setEANTP(EANTP value) {
        this.eantp = value;
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
