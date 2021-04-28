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
 *         &lt;element ref="{}HERKL" minOccurs="0"/&gt;
 *         &lt;element ref="{}VERLD" minOccurs="0"/&gt;
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
    "herkl",
    "verld"
})
@XmlRootElement(name = "Z1EDK12")
public class Z1EDK12 {

    @XmlElement(name = "HERKL")
    protected HERKL herkl;
    @XmlElement(name = "VERLD")
    protected VERLD verld;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad herkl.
     * 
     * @return
     *     possible object is
     *     {@link HERKL }
     *     
     */
    public HERKL getHERKL() {
        return herkl;
    }

    /**
     * Define el valor de la propiedad herkl.
     * 
     * @param value
     *     allowed object is
     *     {@link HERKL }
     *     
     */
    public void setHERKL(HERKL value) {
        this.herkl = value;
    }

    /**
     * Obtiene el valor de la propiedad verld.
     * 
     * @return
     *     possible object is
     *     {@link VERLD }
     *     
     */
    public VERLD getVERLD() {
        return verld;
    }

    /**
     * Define el valor de la propiedad verld.
     * 
     * @param value
     *     allowed object is
     *     {@link VERLD }
     *     
     */
    public void setVERLD(VERLD value) {
        this.verld = value;
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
