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
 *         &lt;element ref="{}TAGE" minOccurs="0"/&gt;
 *         &lt;element ref="{}PRZNT" minOccurs="0"/&gt;
 *         &lt;element ref="{}ZTERM_TXT" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDK07" minOccurs="0"/&gt;
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
    "tage",
    "prznt",
    "ztermtxt",
    "z1EDK07"
})
@XmlRootElement(name = "E1EDK18")
public class E1EDK18 {

    @XmlElement(name = "QUALF")
    protected QUALF qualf;
    @XmlElement(name = "TAGE")
    protected TAGE tage;
    @XmlElement(name = "PRZNT")
    protected PRZNT prznt;
    @XmlElement(name = "ZTERM_TXT")
    protected ZTERMTXT ztermtxt;
    @XmlElement(name = "Z1EDK07")
    protected Z1EDK07 z1EDK07;
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
     * Obtiene el valor de la propiedad tage.
     * 
     * @return
     *     possible object is
     *     {@link TAGE }
     *     
     */
    public TAGE getTAGE() {
        return tage;
    }

    /**
     * Define el valor de la propiedad tage.
     * 
     * @param value
     *     allowed object is
     *     {@link TAGE }
     *     
     */
    public void setTAGE(TAGE value) {
        this.tage = value;
    }

    /**
     * Obtiene el valor de la propiedad prznt.
     * 
     * @return
     *     possible object is
     *     {@link PRZNT }
     *     
     */
    public PRZNT getPRZNT() {
        return prznt;
    }

    /**
     * Define el valor de la propiedad prznt.
     * 
     * @param value
     *     allowed object is
     *     {@link PRZNT }
     *     
     */
    public void setPRZNT(PRZNT value) {
        this.prznt = value;
    }

    /**
     * Obtiene el valor de la propiedad ztermtxt.
     * 
     * @return
     *     possible object is
     *     {@link ZTERMTXT }
     *     
     */
    public ZTERMTXT getZTERMTXT() {
        return ztermtxt;
    }

    /**
     * Define el valor de la propiedad ztermtxt.
     * 
     * @param value
     *     allowed object is
     *     {@link ZTERMTXT }
     *     
     */
    public void setZTERMTXT(ZTERMTXT value) {
        this.ztermtxt = value;
    }

    /**
     * Obtiene el valor de la propiedad z1EDK07.
     * 
     * @return
     *     possible object is
     *     {@link Z1EDK07 }
     *     
     */
    public Z1EDK07 getZ1EDK07() {
        return z1EDK07;
    }

    /**
     * Define el valor de la propiedad z1EDK07.
     * 
     * @param value
     *     allowed object is
     *     {@link Z1EDK07 }
     *     
     */
    public void setZ1EDK07(Z1EDK07 value) {
        this.z1EDK07 = value;
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
