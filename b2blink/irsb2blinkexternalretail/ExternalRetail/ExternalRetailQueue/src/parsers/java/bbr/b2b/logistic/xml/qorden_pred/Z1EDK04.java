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
 *         &lt;element ref="{}FTX4451" minOccurs="0"/&gt;
 *         &lt;element ref="{}FTX4440" minOccurs="0"/&gt;
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
    "ftx4451",
    "ftx4440"
})
@XmlRootElement(name = "Z1EDK04")
public class Z1EDK04 {

    @XmlElement(name = "FTX4451")
    protected FTX4451 ftx4451;
    @XmlElement(name = "FTX4440")
    protected FTX4440 ftx4440;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad ftx4451.
     * 
     * @return
     *     possible object is
     *     {@link FTX4451 }
     *     
     */
    public FTX4451 getFTX4451() {
        return ftx4451;
    }

    /**
     * Define el valor de la propiedad ftx4451.
     * 
     * @param value
     *     allowed object is
     *     {@link FTX4451 }
     *     
     */
    public void setFTX4451(FTX4451 value) {
        this.ftx4451 = value;
    }

    /**
     * Obtiene el valor de la propiedad ftx4440.
     * 
     * @return
     *     possible object is
     *     {@link FTX4440 }
     *     
     */
    public FTX4440 getFTX4440() {
        return ftx4440;
    }

    /**
     * Define el valor de la propiedad ftx4440.
     * 
     * @param value
     *     allowed object is
     *     {@link FTX4440 }
     *     
     */
    public void setFTX4440(FTX4440 value) {
        this.ftx4440 = value;
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
