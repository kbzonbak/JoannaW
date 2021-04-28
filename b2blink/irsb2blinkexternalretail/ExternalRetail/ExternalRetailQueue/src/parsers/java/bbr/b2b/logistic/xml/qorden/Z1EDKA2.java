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
 *         &lt;element ref="{}COM3155" minOccurs="0"/&gt;
 *         &lt;element ref="{}COM3148" minOccurs="0"/&gt;
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
    "com3155",
    "com3148"
})
@XmlRootElement(name = "Z1EDKA2")
public class Z1EDKA2 {

    @XmlElement(name = "COM3155")
    protected COM3155 com3155;
    @XmlElement(name = "COM3148")
    protected COM3148 com3148;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad com3155.
     * 
     * @return
     *     possible object is
     *     {@link COM3155 }
     *     
     */
    public COM3155 getCOM3155() {
        return com3155;
    }

    /**
     * Define el valor de la propiedad com3155.
     * 
     * @param value
     *     allowed object is
     *     {@link COM3155 }
     *     
     */
    public void setCOM3155(COM3155 value) {
        this.com3155 = value;
    }

    /**
     * Obtiene el valor de la propiedad com3148.
     * 
     * @return
     *     possible object is
     *     {@link COM3148 }
     *     
     */
    public COM3148 getCOM3148() {
        return com3148;
    }

    /**
     * Define el valor de la propiedad com3148.
     * 
     * @param value
     *     allowed object is
     *     {@link COM3148 }
     *     
     */
    public void setCOM3148(COM3148 value) {
        this.com3148 = value;
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
