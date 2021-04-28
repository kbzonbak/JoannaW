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
 *         &lt;element ref="{}QUALZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}CUSADD" minOccurs="0"/&gt;
 *         &lt;element ref="{}CUSADD_BEZ" minOccurs="0"/&gt;
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
    "qualz",
    "cusadd",
    "cusaddbez"
})
@XmlRootElement(name = "E1EDK35")
public class E1EDK35 {

    @XmlElement(name = "QUALZ")
    protected QUALZ qualz;
    @XmlElement(name = "CUSADD")
    protected CUSADD cusadd;
    @XmlElement(name = "CUSADD_BEZ")
    protected CUSADDBEZ cusaddbez;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad qualz.
     * 
     * @return
     *     possible object is
     *     {@link QUALZ }
     *     
     */
    public QUALZ getQUALZ() {
        return qualz;
    }

    /**
     * Define el valor de la propiedad qualz.
     * 
     * @param value
     *     allowed object is
     *     {@link QUALZ }
     *     
     */
    public void setQUALZ(QUALZ value) {
        this.qualz = value;
    }

    /**
     * Obtiene el valor de la propiedad cusadd.
     * 
     * @return
     *     possible object is
     *     {@link CUSADD }
     *     
     */
    public CUSADD getCUSADD() {
        return cusadd;
    }

    /**
     * Define el valor de la propiedad cusadd.
     * 
     * @param value
     *     allowed object is
     *     {@link CUSADD }
     *     
     */
    public void setCUSADD(CUSADD value) {
        this.cusadd = value;
    }

    /**
     * Obtiene el valor de la propiedad cusaddbez.
     * 
     * @return
     *     possible object is
     *     {@link CUSADDBEZ }
     *     
     */
    public CUSADDBEZ getCUSADDBEZ() {
        return cusaddbez;
    }

    /**
     * Define el valor de la propiedad cusaddbez.
     * 
     * @param value
     *     allowed object is
     *     {@link CUSADDBEZ }
     *     
     */
    public void setCUSADDBEZ(CUSADDBEZ value) {
        this.cusaddbez = value;
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
