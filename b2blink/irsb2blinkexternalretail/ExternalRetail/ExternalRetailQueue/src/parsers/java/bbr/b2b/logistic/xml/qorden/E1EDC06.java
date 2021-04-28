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
 *         &lt;element ref="{}FORMELNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}FRMVAL1" minOccurs="0"/&gt;
 *         &lt;element ref="{}FRMVAL2" minOccurs="0"/&gt;
 *         &lt;element ref="{}FRMVAL3" minOccurs="0"/&gt;
 *         &lt;element ref="{}FRMVAL4" minOccurs="0"/&gt;
 *         &lt;element ref="{}FRMVAL5" minOccurs="0"/&gt;
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
    "formelnr",
    "frmval1",
    "frmval2",
    "frmval3",
    "frmval4",
    "frmval5"
})
@XmlRootElement(name = "E1EDC06")
public class E1EDC06 {

    @XmlElement(name = "FORMELNR")
    protected FORMELNR formelnr;
    @XmlElement(name = "FRMVAL1")
    protected FRMVAL1 frmval1;
    @XmlElement(name = "FRMVAL2")
    protected FRMVAL2 frmval2;
    @XmlElement(name = "FRMVAL3")
    protected FRMVAL3 frmval3;
    @XmlElement(name = "FRMVAL4")
    protected FRMVAL4 frmval4;
    @XmlElement(name = "FRMVAL5")
    protected FRMVAL5 frmval5;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad formelnr.
     * 
     * @return
     *     possible object is
     *     {@link FORMELNR }
     *     
     */
    public FORMELNR getFORMELNR() {
        return formelnr;
    }

    /**
     * Define el valor de la propiedad formelnr.
     * 
     * @param value
     *     allowed object is
     *     {@link FORMELNR }
     *     
     */
    public void setFORMELNR(FORMELNR value) {
        this.formelnr = value;
    }

    /**
     * Obtiene el valor de la propiedad frmval1.
     * 
     * @return
     *     possible object is
     *     {@link FRMVAL1 }
     *     
     */
    public FRMVAL1 getFRMVAL1() {
        return frmval1;
    }

    /**
     * Define el valor de la propiedad frmval1.
     * 
     * @param value
     *     allowed object is
     *     {@link FRMVAL1 }
     *     
     */
    public void setFRMVAL1(FRMVAL1 value) {
        this.frmval1 = value;
    }

    /**
     * Obtiene el valor de la propiedad frmval2.
     * 
     * @return
     *     possible object is
     *     {@link FRMVAL2 }
     *     
     */
    public FRMVAL2 getFRMVAL2() {
        return frmval2;
    }

    /**
     * Define el valor de la propiedad frmval2.
     * 
     * @param value
     *     allowed object is
     *     {@link FRMVAL2 }
     *     
     */
    public void setFRMVAL2(FRMVAL2 value) {
        this.frmval2 = value;
    }

    /**
     * Obtiene el valor de la propiedad frmval3.
     * 
     * @return
     *     possible object is
     *     {@link FRMVAL3 }
     *     
     */
    public FRMVAL3 getFRMVAL3() {
        return frmval3;
    }

    /**
     * Define el valor de la propiedad frmval3.
     * 
     * @param value
     *     allowed object is
     *     {@link FRMVAL3 }
     *     
     */
    public void setFRMVAL3(FRMVAL3 value) {
        this.frmval3 = value;
    }

    /**
     * Obtiene el valor de la propiedad frmval4.
     * 
     * @return
     *     possible object is
     *     {@link FRMVAL4 }
     *     
     */
    public FRMVAL4 getFRMVAL4() {
        return frmval4;
    }

    /**
     * Define el valor de la propiedad frmval4.
     * 
     * @param value
     *     allowed object is
     *     {@link FRMVAL4 }
     *     
     */
    public void setFRMVAL4(FRMVAL4 value) {
        this.frmval4 = value;
    }

    /**
     * Obtiene el valor de la propiedad frmval5.
     * 
     * @return
     *     possible object is
     *     {@link FRMVAL5 }
     *     
     */
    public FRMVAL5 getFRMVAL5() {
        return frmval5;
    }

    /**
     * Define el valor de la propiedad frmval5.
     * 
     * @param value
     *     allowed object is
     *     {@link FRMVAL5 }
     *     
     */
    public void setFRMVAL5(FRMVAL5 value) {
        this.frmval5 = value;
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
