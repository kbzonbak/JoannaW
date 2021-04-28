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
 *         &lt;element ref="{}QUALP" minOccurs="0"/&gt;
 *         &lt;element ref="{}STDPN" minOccurs="0"/&gt;
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
    "qualp",
    "stdpn"
})
@XmlRootElement(name = "E1EDKA3")
public class E1EDKA3 {

    @XmlElement(name = "QUALP")
    protected QUALP qualp;
    @XmlElement(name = "STDPN")
    protected STDPN stdpn;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad qualp.
     * 
     * @return
     *     possible object is
     *     {@link QUALP }
     *     
     */
    public QUALP getQUALP() {
        return qualp;
    }

    /**
     * Define el valor de la propiedad qualp.
     * 
     * @param value
     *     allowed object is
     *     {@link QUALP }
     *     
     */
    public void setQUALP(QUALP value) {
        this.qualp = value;
    }

    /**
     * Obtiene el valor de la propiedad stdpn.
     * 
     * @return
     *     possible object is
     *     {@link STDPN }
     *     
     */
    public STDPN getSTDPN() {
        return stdpn;
    }

    /**
     * Define el valor de la propiedad stdpn.
     * 
     * @param value
     *     allowed object is
     *     {@link STDPN }
     *     
     */
    public void setSTDPN(STDPN value) {
        this.stdpn = value;
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
