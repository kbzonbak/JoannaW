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
 *         &lt;element ref="{}POSEX" minOccurs="0"/&gt;
 *         &lt;element ref="{}UEPOS" minOccurs="0"/&gt;
 *         &lt;element ref="{}MENGE" minOccurs="0"/&gt;
 *         &lt;element ref="{}MENEE" minOccurs="0"/&gt;
 *         &lt;element ref="{}ZE1EDP05" minOccurs="0"/&gt;
 *         &lt;element ref="{}ZE1EDP03" minOccurs="0"/&gt;
 *         &lt;element ref="{}ZE1EDP04" minOccurs="0"/&gt;
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
    "posex",
    "uepos",
    "menge",
    "menee",
    "ze1EDP05",
    "ze1EDP03",
    "ze1EDP04"
})
@XmlRootElement(name = "ZE1EDP01")
public class ZE1EDP01 {

    @XmlElement(name = "POSEX")
    protected POSEX posex;
    @XmlElement(name = "UEPOS")
    protected UEPOS uepos;
    @XmlElement(name = "MENGE")
    protected MENGE menge;
    @XmlElement(name = "MENEE")
    protected MENEE menee;
    @XmlElement(name = "ZE1EDP05")
    protected ZE1EDP05 ze1EDP05;
    @XmlElement(name = "ZE1EDP03")
    protected ZE1EDP03 ze1EDP03;
    @XmlElement(name = "ZE1EDP04")
    protected ZE1EDP04 ze1EDP04;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad posex.
     * 
     * @return
     *     possible object is
     *     {@link POSEX }
     *     
     */
    public POSEX getPOSEX() {
        return posex;
    }

    /**
     * Define el valor de la propiedad posex.
     * 
     * @param value
     *     allowed object is
     *     {@link POSEX }
     *     
     */
    public void setPOSEX(POSEX value) {
        this.posex = value;
    }

    /**
     * Obtiene el valor de la propiedad uepos.
     * 
     * @return
     *     possible object is
     *     {@link UEPOS }
     *     
     */
    public UEPOS getUEPOS() {
        return uepos;
    }

    /**
     * Define el valor de la propiedad uepos.
     * 
     * @param value
     *     allowed object is
     *     {@link UEPOS }
     *     
     */
    public void setUEPOS(UEPOS value) {
        this.uepos = value;
    }

    /**
     * Obtiene el valor de la propiedad menge.
     * 
     * @return
     *     possible object is
     *     {@link MENGE }
     *     
     */
    public MENGE getMENGE() {
        return menge;
    }

    /**
     * Define el valor de la propiedad menge.
     * 
     * @param value
     *     allowed object is
     *     {@link MENGE }
     *     
     */
    public void setMENGE(MENGE value) {
        this.menge = value;
    }

    /**
     * Obtiene el valor de la propiedad menee.
     * 
     * @return
     *     possible object is
     *     {@link MENEE }
     *     
     */
    public MENEE getMENEE() {
        return menee;
    }

    /**
     * Define el valor de la propiedad menee.
     * 
     * @param value
     *     allowed object is
     *     {@link MENEE }
     *     
     */
    public void setMENEE(MENEE value) {
        this.menee = value;
    }

    /**
     * Obtiene el valor de la propiedad ze1EDP05.
     * 
     * @return
     *     possible object is
     *     {@link ZE1EDP05 }
     *     
     */
    public ZE1EDP05 getZE1EDP05() {
        return ze1EDP05;
    }

    /**
     * Define el valor de la propiedad ze1EDP05.
     * 
     * @param value
     *     allowed object is
     *     {@link ZE1EDP05 }
     *     
     */
    public void setZE1EDP05(ZE1EDP05 value) {
        this.ze1EDP05 = value;
    }

    /**
     * Obtiene el valor de la propiedad ze1EDP03.
     * 
     * @return
     *     possible object is
     *     {@link ZE1EDP03 }
     *     
     */
    public ZE1EDP03 getZE1EDP03() {
        return ze1EDP03;
    }

    /**
     * Define el valor de la propiedad ze1EDP03.
     * 
     * @param value
     *     allowed object is
     *     {@link ZE1EDP03 }
     *     
     */
    public void setZE1EDP03(ZE1EDP03 value) {
        this.ze1EDP03 = value;
    }

    /**
     * Obtiene el valor de la propiedad ze1EDP04.
     * 
     * @return
     *     possible object is
     *     {@link ZE1EDP04 }
     *     
     */
    public ZE1EDP04 getZE1EDP04() {
        return ze1EDP04;
    }

    /**
     * Define el valor de la propiedad ze1EDP04.
     * 
     * @param value
     *     allowed object is
     *     {@link ZE1EDP04 }
     *     
     */
    public void setZE1EDP04(ZE1EDP04 value) {
        this.ze1EDP04 = value;
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
