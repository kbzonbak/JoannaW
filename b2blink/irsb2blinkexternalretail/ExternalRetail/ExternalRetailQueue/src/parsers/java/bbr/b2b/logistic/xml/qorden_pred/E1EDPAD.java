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
 *         &lt;element ref="{}QUALF" minOccurs="0"/&gt;
 *         &lt;element ref="{}ICC" minOccurs="0"/&gt;
 *         &lt;element ref="{}MOI" minOccurs="0"/&gt;
 *         &lt;element ref="{}PRI" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1TXTH1" minOccurs="0"/&gt;
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
    "qualf",
    "icc",
    "moi",
    "pri",
    "e1TXTH1"
})
@XmlRootElement(name = "E1EDPAD")
public class E1EDPAD {

    @XmlElement(name = "QUALF")
    protected QUALF qualf;
    @XmlElement(name = "ICC")
    protected ICC icc;
    @XmlElement(name = "MOI")
    protected MOI moi;
    @XmlElement(name = "PRI")
    protected PRI pri;
    @XmlElement(name = "E1TXTH1")
    protected E1TXTH1 e1TXTH1;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad qualf.
     * 
     * @return
     *     possible object is
     *     {@link QUALF }
     *     
     */
    public QUALF getQUALF() {
        return qualf;
    }

    /**
     * Define el valor de la propiedad qualf.
     * 
     * @param value
     *     allowed object is
     *     {@link QUALF }
     *     
     */
    public void setQUALF(QUALF value) {
        this.qualf = value;
    }

    /**
     * Obtiene el valor de la propiedad icc.
     * 
     * @return
     *     possible object is
     *     {@link ICC }
     *     
     */
    public ICC getICC() {
        return icc;
    }

    /**
     * Define el valor de la propiedad icc.
     * 
     * @param value
     *     allowed object is
     *     {@link ICC }
     *     
     */
    public void setICC(ICC value) {
        this.icc = value;
    }

    /**
     * Obtiene el valor de la propiedad moi.
     * 
     * @return
     *     possible object is
     *     {@link MOI }
     *     
     */
    public MOI getMOI() {
        return moi;
    }

    /**
     * Define el valor de la propiedad moi.
     * 
     * @param value
     *     allowed object is
     *     {@link MOI }
     *     
     */
    public void setMOI(MOI value) {
        this.moi = value;
    }

    /**
     * Obtiene el valor de la propiedad pri.
     * 
     * @return
     *     possible object is
     *     {@link PRI }
     *     
     */
    public PRI getPRI() {
        return pri;
    }

    /**
     * Define el valor de la propiedad pri.
     * 
     * @param value
     *     allowed object is
     *     {@link PRI }
     *     
     */
    public void setPRI(PRI value) {
        this.pri = value;
    }

    /**
     * Obtiene el valor de la propiedad e1TXTH1.
     * 
     * @return
     *     possible object is
     *     {@link E1TXTH1 }
     *     
     */
    public E1TXTH1 getE1TXTH1() {
        return e1TXTH1;
    }

    /**
     * Define el valor de la propiedad e1TXTH1.
     * 
     * @param value
     *     allowed object is
     *     {@link E1TXTH1 }
     *     
     */
    public void setE1TXTH1(E1TXTH1 value) {
        this.e1TXTH1 = value;
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
