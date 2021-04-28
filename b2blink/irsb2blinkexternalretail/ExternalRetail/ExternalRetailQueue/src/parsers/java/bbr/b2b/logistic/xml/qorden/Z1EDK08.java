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
 *         &lt;element ref="{}TOD4215" minOccurs="0"/&gt;
 *         &lt;element ref="{}LOC3225" minOccurs="0"/&gt;
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
    "tod4215",
    "loc3225"
})
@XmlRootElement(name = "Z1EDK08")
public class Z1EDK08 {

    @XmlElement(name = "TOD4215")
    protected TOD4215 tod4215;
    @XmlElement(name = "LOC3225")
    protected LOC3225 loc3225;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad tod4215.
     * 
     * @return
     *     possible object is
     *     {@link TOD4215 }
     *     
     */
    public TOD4215 getTOD4215() {
        return tod4215;
    }

    /**
     * Define el valor de la propiedad tod4215.
     * 
     * @param value
     *     allowed object is
     *     {@link TOD4215 }
     *     
     */
    public void setTOD4215(TOD4215 value) {
        this.tod4215 = value;
    }

    /**
     * Obtiene el valor de la propiedad loc3225.
     * 
     * @return
     *     possible object is
     *     {@link LOC3225 }
     *     
     */
    public LOC3225 getLOC3225() {
        return loc3225;
    }

    /**
     * Define el valor de la propiedad loc3225.
     * 
     * @param value
     *     allowed object is
     *     {@link LOC3225 }
     *     
     */
    public void setLOC3225(LOC3225 value) {
        this.loc3225 = value;
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
