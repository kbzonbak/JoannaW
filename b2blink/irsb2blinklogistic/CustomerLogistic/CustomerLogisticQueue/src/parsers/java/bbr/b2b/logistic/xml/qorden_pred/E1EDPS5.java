//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
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
 *         &lt;element ref="{}KSTBM" minOccurs="0"/&gt;
 *         &lt;element ref="{}KBETR" minOccurs="0"/&gt;
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
    "kstbm",
    "kbetr"
})
@XmlRootElement(name = "E1EDPS5")
public class E1EDPS5 {

    @XmlElement(name = "KSTBM")
    protected KSTBM kstbm;
    @XmlElement(name = "KBETR")
    protected KBETR kbetr;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad kstbm.
     * 
     * @return
     *     possible object is
     *     {@link KSTBM }
     *     
     */
    public KSTBM getKSTBM() {
        return kstbm;
    }

    /**
     * Define el valor de la propiedad kstbm.
     * 
     * @param value
     *     allowed object is
     *     {@link KSTBM }
     *     
     */
    public void setKSTBM(KSTBM value) {
        this.kstbm = value;
    }

    /**
     * Obtiene el valor de la propiedad kbetr.
     * 
     * @return
     *     possible object is
     *     {@link KBETR }
     *     
     */
    public KBETR getKBETR() {
        return kbetr;
    }

    /**
     * Define el valor de la propiedad kbetr.
     * 
     * @param value
     *     allowed object is
     *     {@link KBETR }
     *     
     */
    public void setKBETR(KBETR value) {
        this.kbetr = value;
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
