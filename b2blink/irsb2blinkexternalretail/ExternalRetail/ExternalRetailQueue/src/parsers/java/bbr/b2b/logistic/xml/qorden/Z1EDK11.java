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
 *         &lt;element ref="{}EXPVZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}ZOLLA" minOccurs="0"/&gt;
 *         &lt;element ref="{}INCO1" minOccurs="0"/&gt;
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
    "expvz",
    "zolla",
    "inco1"
})
@XmlRootElement(name = "Z1EDK11")
public class Z1EDK11 {

    @XmlElement(name = "EXPVZ")
    protected EXPVZ expvz;
    @XmlElement(name = "ZOLLA")
    protected ZOLLA zolla;
    @XmlElement(name = "INCO1")
    protected INCO1 inco1;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad expvz.
     * 
     * @return
     *     possible object is
     *     {@link EXPVZ }
     *     
     */
    public EXPVZ getEXPVZ() {
        return expvz;
    }

    /**
     * Define el valor de la propiedad expvz.
     * 
     * @param value
     *     allowed object is
     *     {@link EXPVZ }
     *     
     */
    public void setEXPVZ(EXPVZ value) {
        this.expvz = value;
    }

    /**
     * Obtiene el valor de la propiedad zolla.
     * 
     * @return
     *     possible object is
     *     {@link ZOLLA }
     *     
     */
    public ZOLLA getZOLLA() {
        return zolla;
    }

    /**
     * Define el valor de la propiedad zolla.
     * 
     * @param value
     *     allowed object is
     *     {@link ZOLLA }
     *     
     */
    public void setZOLLA(ZOLLA value) {
        this.zolla = value;
    }

    /**
     * Obtiene el valor de la propiedad inco1.
     * 
     * @return
     *     possible object is
     *     {@link INCO1 }
     *     
     */
    public INCO1 getINCO1() {
        return inco1;
    }

    /**
     * Define el valor de la propiedad inco1.
     * 
     * @param value
     *     allowed object is
     *     {@link INCO1 }
     *     
     */
    public void setINCO1(INCO1 value) {
        this.inco1 = value;
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
