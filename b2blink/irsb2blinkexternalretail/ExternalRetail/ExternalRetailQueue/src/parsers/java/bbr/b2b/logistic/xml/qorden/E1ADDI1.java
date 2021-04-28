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
 *         &lt;element ref="{}ADDIMATNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}ADDINUMBER" minOccurs="0"/&gt;
 *         &lt;element ref="{}ADDIVKME" minOccurs="0"/&gt;
 *         &lt;element ref="{}ADDIFM" minOccurs="0"/&gt;
 *         &lt;element ref="{}ADDIFM_TXT" minOccurs="0"/&gt;
 *         &lt;element ref="{}ADDIKLART" minOccurs="0"/&gt;
 *         &lt;element ref="{}ADDIKLART_TXT" minOccurs="0"/&gt;
 *         &lt;element ref="{}ADDICLASS" minOccurs="0"/&gt;
 *         &lt;element ref="{}ADDICLASS_TXT" minOccurs="0"/&gt;
 *         &lt;element ref="{}ADDIIDOC" minOccurs="0"/&gt;
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
    "addimatnr",
    "addinumber",
    "addivkme",
    "addifm",
    "addifmtxt",
    "addiklart",
    "addiklarttxt",
    "addiclass",
    "addiclasstxt",
    "addiidoc"
})
@XmlRootElement(name = "E1ADDI1")
public class E1ADDI1 {

    @XmlElement(name = "ADDIMATNR")
    protected ADDIMATNR addimatnr;
    @XmlElement(name = "ADDINUMBER")
    protected ADDINUMBER addinumber;
    @XmlElement(name = "ADDIVKME")
    protected ADDIVKME addivkme;
    @XmlElement(name = "ADDIFM")
    protected ADDIFM addifm;
    @XmlElement(name = "ADDIFM_TXT")
    protected ADDIFMTXT addifmtxt;
    @XmlElement(name = "ADDIKLART")
    protected ADDIKLART addiklart;
    @XmlElement(name = "ADDIKLART_TXT")
    protected ADDIKLARTTXT addiklarttxt;
    @XmlElement(name = "ADDICLASS")
    protected ADDICLASS addiclass;
    @XmlElement(name = "ADDICLASS_TXT")
    protected ADDICLASSTXT addiclasstxt;
    @XmlElement(name = "ADDIIDOC")
    protected ADDIIDOC addiidoc;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad addimatnr.
     * 
     * @return
     *     possible object is
     *     {@link ADDIMATNR }
     *     
     */
    public ADDIMATNR getADDIMATNR() {
        return addimatnr;
    }

    /**
     * Define el valor de la propiedad addimatnr.
     * 
     * @param value
     *     allowed object is
     *     {@link ADDIMATNR }
     *     
     */
    public void setADDIMATNR(ADDIMATNR value) {
        this.addimatnr = value;
    }

    /**
     * Obtiene el valor de la propiedad addinumber.
     * 
     * @return
     *     possible object is
     *     {@link ADDINUMBER }
     *     
     */
    public ADDINUMBER getADDINUMBER() {
        return addinumber;
    }

    /**
     * Define el valor de la propiedad addinumber.
     * 
     * @param value
     *     allowed object is
     *     {@link ADDINUMBER }
     *     
     */
    public void setADDINUMBER(ADDINUMBER value) {
        this.addinumber = value;
    }

    /**
     * Obtiene el valor de la propiedad addivkme.
     * 
     * @return
     *     possible object is
     *     {@link ADDIVKME }
     *     
     */
    public ADDIVKME getADDIVKME() {
        return addivkme;
    }

    /**
     * Define el valor de la propiedad addivkme.
     * 
     * @param value
     *     allowed object is
     *     {@link ADDIVKME }
     *     
     */
    public void setADDIVKME(ADDIVKME value) {
        this.addivkme = value;
    }

    /**
     * Obtiene el valor de la propiedad addifm.
     * 
     * @return
     *     possible object is
     *     {@link ADDIFM }
     *     
     */
    public ADDIFM getADDIFM() {
        return addifm;
    }

    /**
     * Define el valor de la propiedad addifm.
     * 
     * @param value
     *     allowed object is
     *     {@link ADDIFM }
     *     
     */
    public void setADDIFM(ADDIFM value) {
        this.addifm = value;
    }

    /**
     * Obtiene el valor de la propiedad addifmtxt.
     * 
     * @return
     *     possible object is
     *     {@link ADDIFMTXT }
     *     
     */
    public ADDIFMTXT getADDIFMTXT() {
        return addifmtxt;
    }

    /**
     * Define el valor de la propiedad addifmtxt.
     * 
     * @param value
     *     allowed object is
     *     {@link ADDIFMTXT }
     *     
     */
    public void setADDIFMTXT(ADDIFMTXT value) {
        this.addifmtxt = value;
    }

    /**
     * Obtiene el valor de la propiedad addiklart.
     * 
     * @return
     *     possible object is
     *     {@link ADDIKLART }
     *     
     */
    public ADDIKLART getADDIKLART() {
        return addiklart;
    }

    /**
     * Define el valor de la propiedad addiklart.
     * 
     * @param value
     *     allowed object is
     *     {@link ADDIKLART }
     *     
     */
    public void setADDIKLART(ADDIKLART value) {
        this.addiklart = value;
    }

    /**
     * Obtiene el valor de la propiedad addiklarttxt.
     * 
     * @return
     *     possible object is
     *     {@link ADDIKLARTTXT }
     *     
     */
    public ADDIKLARTTXT getADDIKLARTTXT() {
        return addiklarttxt;
    }

    /**
     * Define el valor de la propiedad addiklarttxt.
     * 
     * @param value
     *     allowed object is
     *     {@link ADDIKLARTTXT }
     *     
     */
    public void setADDIKLARTTXT(ADDIKLARTTXT value) {
        this.addiklarttxt = value;
    }

    /**
     * Obtiene el valor de la propiedad addiclass.
     * 
     * @return
     *     possible object is
     *     {@link ADDICLASS }
     *     
     */
    public ADDICLASS getADDICLASS() {
        return addiclass;
    }

    /**
     * Define el valor de la propiedad addiclass.
     * 
     * @param value
     *     allowed object is
     *     {@link ADDICLASS }
     *     
     */
    public void setADDICLASS(ADDICLASS value) {
        this.addiclass = value;
    }

    /**
     * Obtiene el valor de la propiedad addiclasstxt.
     * 
     * @return
     *     possible object is
     *     {@link ADDICLASSTXT }
     *     
     */
    public ADDICLASSTXT getADDICLASSTXT() {
        return addiclasstxt;
    }

    /**
     * Define el valor de la propiedad addiclasstxt.
     * 
     * @param value
     *     allowed object is
     *     {@link ADDICLASSTXT }
     *     
     */
    public void setADDICLASSTXT(ADDICLASSTXT value) {
        this.addiclasstxt = value;
    }

    /**
     * Obtiene el valor de la propiedad addiidoc.
     * 
     * @return
     *     possible object is
     *     {@link ADDIIDOC }
     *     
     */
    public ADDIIDOC getADDIIDOC() {
        return addiidoc;
    }

    /**
     * Define el valor de la propiedad addiidoc.
     * 
     * @param value
     *     allowed object is
     *     {@link ADDIIDOC }
     *     
     */
    public void setADDIIDOC(ADDIIDOC value) {
        this.addiidoc = value;
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
