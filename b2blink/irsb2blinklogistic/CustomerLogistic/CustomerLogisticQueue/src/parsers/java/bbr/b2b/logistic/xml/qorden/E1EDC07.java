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
 *         &lt;element ref="{}USERF1_NUM" minOccurs="0"/&gt;
 *         &lt;element ref="{}USERF2_NUM" minOccurs="0"/&gt;
 *         &lt;element ref="{}USERF1_TXT" minOccurs="0"/&gt;
 *         &lt;element ref="{}USERF2_TXT" minOccurs="0"/&gt;
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
    "userf1NUM",
    "userf2NUM",
    "userf1TXT",
    "userf2TXT"
})
@XmlRootElement(name = "E1EDC07")
public class E1EDC07 {

    @XmlElement(name = "USERF1_NUM")
    protected USERF1NUM userf1NUM;
    @XmlElement(name = "USERF2_NUM")
    protected USERF2NUM userf2NUM;
    @XmlElement(name = "USERF1_TXT")
    protected USERF1TXT userf1TXT;
    @XmlElement(name = "USERF2_TXT")
    protected USERF2TXT userf2TXT;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad userf1NUM.
     * 
     * @return
     *     possible object is
     *     {@link USERF1NUM }
     *     
     */
    public USERF1NUM getUSERF1NUM() {
        return userf1NUM;
    }

    /**
     * Define el valor de la propiedad userf1NUM.
     * 
     * @param value
     *     allowed object is
     *     {@link USERF1NUM }
     *     
     */
    public void setUSERF1NUM(USERF1NUM value) {
        this.userf1NUM = value;
    }

    /**
     * Obtiene el valor de la propiedad userf2NUM.
     * 
     * @return
     *     possible object is
     *     {@link USERF2NUM }
     *     
     */
    public USERF2NUM getUSERF2NUM() {
        return userf2NUM;
    }

    /**
     * Define el valor de la propiedad userf2NUM.
     * 
     * @param value
     *     allowed object is
     *     {@link USERF2NUM }
     *     
     */
    public void setUSERF2NUM(USERF2NUM value) {
        this.userf2NUM = value;
    }

    /**
     * Obtiene el valor de la propiedad userf1TXT.
     * 
     * @return
     *     possible object is
     *     {@link USERF1TXT }
     *     
     */
    public USERF1TXT getUSERF1TXT() {
        return userf1TXT;
    }

    /**
     * Define el valor de la propiedad userf1TXT.
     * 
     * @param value
     *     allowed object is
     *     {@link USERF1TXT }
     *     
     */
    public void setUSERF1TXT(USERF1TXT value) {
        this.userf1TXT = value;
    }

    /**
     * Obtiene el valor de la propiedad userf2TXT.
     * 
     * @return
     *     possible object is
     *     {@link USERF2TXT }
     *     
     */
    public USERF2TXT getUSERF2TXT() {
        return userf2TXT;
    }

    /**
     * Define el valor de la propiedad userf2TXT.
     * 
     * @param value
     *     allowed object is
     *     {@link USERF2TXT }
     *     
     */
    public void setUSERF2TXT(USERF2TXT value) {
        this.userf2TXT = value;
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
