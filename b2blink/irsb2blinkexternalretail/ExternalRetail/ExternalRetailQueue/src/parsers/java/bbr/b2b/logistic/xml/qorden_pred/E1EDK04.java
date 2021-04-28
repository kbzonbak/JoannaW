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
 *         &lt;element ref="{}MWSKZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}MSATZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}MWSBT" minOccurs="0"/&gt;
 *         &lt;element ref="{}TXJCD" minOccurs="0"/&gt;
 *         &lt;element ref="{}KTEXT" minOccurs="0"/&gt;
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
    "mwskz",
    "msatz",
    "mwsbt",
    "txjcd",
    "ktext"
})
@XmlRootElement(name = "E1EDK04")
public class E1EDK04 {

    @XmlElement(name = "MWSKZ")
    protected MWSKZ mwskz;
    @XmlElement(name = "MSATZ")
    protected MSATZ msatz;
    @XmlElement(name = "MWSBT")
    protected MWSBT mwsbt;
    @XmlElement(name = "TXJCD")
    protected TXJCD txjcd;
    @XmlElement(name = "KTEXT")
    protected KTEXT ktext;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad mwskz.
     * 
     * @return
     *     possible object is
     *     {@link MWSKZ }
     *     
     */
    public MWSKZ getMWSKZ() {
        return mwskz;
    }

    /**
     * Define el valor de la propiedad mwskz.
     * 
     * @param value
     *     allowed object is
     *     {@link MWSKZ }
     *     
     */
    public void setMWSKZ(MWSKZ value) {
        this.mwskz = value;
    }

    /**
     * Obtiene el valor de la propiedad msatz.
     * 
     * @return
     *     possible object is
     *     {@link MSATZ }
     *     
     */
    public MSATZ getMSATZ() {
        return msatz;
    }

    /**
     * Define el valor de la propiedad msatz.
     * 
     * @param value
     *     allowed object is
     *     {@link MSATZ }
     *     
     */
    public void setMSATZ(MSATZ value) {
        this.msatz = value;
    }

    /**
     * Obtiene el valor de la propiedad mwsbt.
     * 
     * @return
     *     possible object is
     *     {@link MWSBT }
     *     
     */
    public MWSBT getMWSBT() {
        return mwsbt;
    }

    /**
     * Define el valor de la propiedad mwsbt.
     * 
     * @param value
     *     allowed object is
     *     {@link MWSBT }
     *     
     */
    public void setMWSBT(MWSBT value) {
        this.mwsbt = value;
    }

    /**
     * Obtiene el valor de la propiedad txjcd.
     * 
     * @return
     *     possible object is
     *     {@link TXJCD }
     *     
     */
    public TXJCD getTXJCD() {
        return txjcd;
    }

    /**
     * Define el valor de la propiedad txjcd.
     * 
     * @param value
     *     allowed object is
     *     {@link TXJCD }
     *     
     */
    public void setTXJCD(TXJCD value) {
        this.txjcd = value;
    }

    /**
     * Obtiene el valor de la propiedad ktext.
     * 
     * @return
     *     possible object is
     *     {@link KTEXT }
     *     
     */
    public KTEXT getKTEXT() {
        return ktext;
    }

    /**
     * Define el valor de la propiedad ktext.
     * 
     * @param value
     *     allowed object is
     *     {@link KTEXT }
     *     
     */
    public void setKTEXT(KTEXT value) {
        this.ktext = value;
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
