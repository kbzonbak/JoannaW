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
 *         &lt;element ref="{}INST_ID" minOccurs="0"/&gt;
 *         &lt;element ref="{}CHARC" minOccurs="0"/&gt;
 *         &lt;element ref="{}CHARC_TXT" minOccurs="0"/&gt;
 *         &lt;element ref="{}VALUE" minOccurs="0"/&gt;
 *         &lt;element ref="{}VALUE_TXT" minOccurs="0"/&gt;
 *         &lt;element ref="{}AUTHOR" minOccurs="0"/&gt;
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
    "instid",
    "charc",
    "charctxt",
    "value",
    "valuetxt",
    "author"
})
@XmlRootElement(name = "E1CUVAL")
public class E1CUVAL {

    @XmlElement(name = "INST_ID")
    protected INSTID instid;
    @XmlElement(name = "CHARC")
    protected CHARC charc;
    @XmlElement(name = "CHARC_TXT")
    protected CHARCTXT charctxt;
    @XmlElement(name = "VALUE")
    protected VALUE value;
    @XmlElement(name = "VALUE_TXT")
    protected VALUETXT valuetxt;
    @XmlElement(name = "AUTHOR")
    protected AUTHOR author;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad instid.
     * 
     * @return
     *     possible object is
     *     {@link INSTID }
     *     
     */
    public INSTID getINSTID() {
        return instid;
    }

    /**
     * Define el valor de la propiedad instid.
     * 
     * @param value
     *     allowed object is
     *     {@link INSTID }
     *     
     */
    public void setINSTID(INSTID value) {
        this.instid = value;
    }

    /**
     * Obtiene el valor de la propiedad charc.
     * 
     * @return
     *     possible object is
     *     {@link CHARC }
     *     
     */
    public CHARC getCHARC() {
        return charc;
    }

    /**
     * Define el valor de la propiedad charc.
     * 
     * @param value
     *     allowed object is
     *     {@link CHARC }
     *     
     */
    public void setCHARC(CHARC value) {
        this.charc = value;
    }

    /**
     * Obtiene el valor de la propiedad charctxt.
     * 
     * @return
     *     possible object is
     *     {@link CHARCTXT }
     *     
     */
    public CHARCTXT getCHARCTXT() {
        return charctxt;
    }

    /**
     * Define el valor de la propiedad charctxt.
     * 
     * @param value
     *     allowed object is
     *     {@link CHARCTXT }
     *     
     */
    public void setCHARCTXT(CHARCTXT value) {
        this.charctxt = value;
    }

    /**
     * Obtiene el valor de la propiedad value.
     * 
     * @return
     *     possible object is
     *     {@link VALUE }
     *     
     */
    public VALUE getVALUE() {
        return value;
    }

    /**
     * Define el valor de la propiedad value.
     * 
     * @param value
     *     allowed object is
     *     {@link VALUE }
     *     
     */
    public void setVALUE(VALUE value) {
        this.value = value;
    }

    /**
     * Obtiene el valor de la propiedad valuetxt.
     * 
     * @return
     *     possible object is
     *     {@link VALUETXT }
     *     
     */
    public VALUETXT getVALUETXT() {
        return valuetxt;
    }

    /**
     * Define el valor de la propiedad valuetxt.
     * 
     * @param value
     *     allowed object is
     *     {@link VALUETXT }
     *     
     */
    public void setVALUETXT(VALUETXT value) {
        this.valuetxt = value;
    }

    /**
     * Obtiene el valor de la propiedad author.
     * 
     * @return
     *     possible object is
     *     {@link AUTHOR }
     *     
     */
    public AUTHOR getAUTHOR() {
        return author;
    }

    /**
     * Define el valor de la propiedad author.
     * 
     * @param value
     *     allowed object is
     *     {@link AUTHOR }
     *     
     */
    public void setAUTHOR(AUTHOR value) {
        this.author = value;
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
