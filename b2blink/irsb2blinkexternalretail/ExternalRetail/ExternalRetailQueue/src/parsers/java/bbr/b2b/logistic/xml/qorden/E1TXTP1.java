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
 *         &lt;element ref="{}TDFORMAT" minOccurs="0"/&gt;
 *         &lt;element ref="{}TDLINE" minOccurs="0"/&gt;
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
    "tdformat",
    "tdline"
})
@XmlRootElement(name = "E1TXTP1")
public class E1TXTP1 {

    @XmlElement(name = "TDFORMAT")
    protected TDFORMAT tdformat;
    @XmlElement(name = "TDLINE")
    protected TDLINE tdline;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad tdformat.
     * 
     * @return
     *     possible object is
     *     {@link TDFORMAT }
     *     
     */
    public TDFORMAT getTDFORMAT() {
        return tdformat;
    }

    /**
     * Define el valor de la propiedad tdformat.
     * 
     * @param value
     *     allowed object is
     *     {@link TDFORMAT }
     *     
     */
    public void setTDFORMAT(TDFORMAT value) {
        this.tdformat = value;
    }

    /**
     * Obtiene el valor de la propiedad tdline.
     * 
     * @return
     *     possible object is
     *     {@link TDLINE }
     *     
     */
    public TDLINE getTDLINE() {
        return tdline;
    }

    /**
     * Define el valor de la propiedad tdline.
     * 
     * @param value
     *     allowed object is
     *     {@link TDLINE }
     *     
     */
    public void setTDLINE(TDLINE value) {
        this.tdline = value;
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
