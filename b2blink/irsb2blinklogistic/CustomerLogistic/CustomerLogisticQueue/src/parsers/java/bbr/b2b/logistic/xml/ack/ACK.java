//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 06:18:07 PM CLT 
//


package bbr.b2b.logistic.xml.ack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{}A0"/&gt;
 *         &lt;element ref="{}A1"/&gt;
 *         &lt;element ref="{}A2"/&gt;
 *         &lt;element ref="{}A3"/&gt;
 *         &lt;element ref="{}A4"/&gt;
 *         &lt;element ref="{}A5"/&gt;
 *         &lt;element ref="{}A6"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "a0",
    "a1",
    "a2",
    "a3",
    "a4",
    "a5",
    "a6"
})
@XmlRootElement(name = "ACK")
public class ACK {

    @XmlElement(name = "A0", required = true)
    protected String a0;
    @XmlElement(name = "A1", required = true)
    protected String a1;
    @XmlElement(name = "A2", required = true)
    protected String a2;
    @XmlElement(name = "A3", required = true)
    protected String a3;
    @XmlElement(name = "A4", required = true)
    protected String a4;
    @XmlElement(name = "A5", required = true)
    protected String a5;
    @XmlElement(name = "A6", required = true)
    protected String a6;

    /**
     * Obtiene el valor de la propiedad a0.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getA0() {
        return a0;
    }

    /**
     * Define el valor de la propiedad a0.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setA0(String value) {
        this.a0 = value;
    }

    /**
     * Obtiene el valor de la propiedad a1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getA1() {
        return a1;
    }

    /**
     * Define el valor de la propiedad a1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setA1(String value) {
        this.a1 = value;
    }

    /**
     * Obtiene el valor de la propiedad a2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getA2() {
        return a2;
    }

    /**
     * Define el valor de la propiedad a2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setA2(String value) {
        this.a2 = value;
    }

    /**
     * Obtiene el valor de la propiedad a3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getA3() {
        return a3;
    }

    /**
     * Define el valor de la propiedad a3.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setA3(String value) {
        this.a3 = value;
    }

    /**
     * Obtiene el valor de la propiedad a4.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getA4() {
        return a4;
    }

    /**
     * Define el valor de la propiedad a4.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setA4(String value) {
        this.a4 = value;
    }

    /**
     * Obtiene el valor de la propiedad a5.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getA5() {
        return a5;
    }

    /**
     * Define el valor de la propiedad a5.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setA5(String value) {
        this.a5 = value;
    }

    /**
     * Obtiene el valor de la propiedad a6.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getA6() {
        return a6;
    }

    /**
     * Define el valor de la propiedad a6.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setA6(String value) {
        this.a6 = value;
    }

}
