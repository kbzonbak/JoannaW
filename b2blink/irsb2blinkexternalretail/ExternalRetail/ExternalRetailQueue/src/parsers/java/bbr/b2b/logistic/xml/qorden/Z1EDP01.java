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
 *         &lt;element ref="{}QTY6063" minOccurs="0"/&gt;
 *         &lt;element ref="{}QTY6060" minOccurs="0"/&gt;
 *         &lt;element ref="{}QTY6411" minOccurs="0"/&gt;
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
    "qty6063",
    "qty6060",
    "qty6411"
})
@XmlRootElement(name = "Z1EDP01")
public class Z1EDP01 {

    @XmlElement(name = "QTY6063")
    protected QTY6063 qty6063;
    @XmlElement(name = "QTY6060")
    protected QTY6060 qty6060;
    @XmlElement(name = "QTY6411")
    protected QTY6411 qty6411;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad qty6063.
     * 
     * @return
     *     possible object is
     *     {@link QTY6063 }
     *     
     */
    public QTY6063 getQTY6063() {
        return qty6063;
    }

    /**
     * Define el valor de la propiedad qty6063.
     * 
     * @param value
     *     allowed object is
     *     {@link QTY6063 }
     *     
     */
    public void setQTY6063(QTY6063 value) {
        this.qty6063 = value;
    }

    /**
     * Obtiene el valor de la propiedad qty6060.
     * 
     * @return
     *     possible object is
     *     {@link QTY6060 }
     *     
     */
    public QTY6060 getQTY6060() {
        return qty6060;
    }

    /**
     * Define el valor de la propiedad qty6060.
     * 
     * @param value
     *     allowed object is
     *     {@link QTY6060 }
     *     
     */
    public void setQTY6060(QTY6060 value) {
        this.qty6060 = value;
    }

    /**
     * Obtiene el valor de la propiedad qty6411.
     * 
     * @return
     *     possible object is
     *     {@link QTY6411 }
     *     
     */
    public QTY6411 getQTY6411() {
        return qty6411;
    }

    /**
     * Define el valor de la propiedad qty6411.
     * 
     * @param value
     *     allowed object is
     *     {@link QTY6411 }
     *     
     */
    public void setQTY6411(QTY6411 value) {
        this.qty6411 = value;
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
