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
 *         &lt;element ref="{}QUALF" minOccurs="0"/&gt;
 *         &lt;element ref="{}BELNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}ZEILE" minOccurs="0"/&gt;
 *         &lt;element ref="{}DATUM" minOccurs="0"/&gt;
 *         &lt;element ref="{}UZEIT" minOccurs="0"/&gt;
 *         &lt;element ref="{}BSARK" minOccurs="0"/&gt;
 *         &lt;element ref="{}IHREZ" minOccurs="0"/&gt;
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
    "belnr",
    "zeile",
    "datum",
    "uzeit",
    "bsark",
    "ihrez"
})
@XmlRootElement(name = "E1EDP02")
public class E1EDP02 {

    @XmlElement(name = "QUALF")
    protected QUALF qualf;
    @XmlElement(name = "BELNR")
    protected BELNR belnr;
    @XmlElement(name = "ZEILE")
    protected ZEILE zeile;
    @XmlElement(name = "DATUM")
    protected DATUM datum;
    @XmlElement(name = "UZEIT")
    protected UZEIT uzeit;
    @XmlElement(name = "BSARK")
    protected BSARK bsark;
    @XmlElement(name = "IHREZ")
    protected IHREZ ihrez;
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
     * Obtiene el valor de la propiedad belnr.
     * 
     * @return
     *     possible object is
     *     {@link BELNR }
     *     
     */
    public BELNR getBELNR() {
        return belnr;
    }

    /**
     * Define el valor de la propiedad belnr.
     * 
     * @param value
     *     allowed object is
     *     {@link BELNR }
     *     
     */
    public void setBELNR(BELNR value) {
        this.belnr = value;
    }

    /**
     * Obtiene el valor de la propiedad zeile.
     * 
     * @return
     *     possible object is
     *     {@link ZEILE }
     *     
     */
    public ZEILE getZEILE() {
        return zeile;
    }

    /**
     * Define el valor de la propiedad zeile.
     * 
     * @param value
     *     allowed object is
     *     {@link ZEILE }
     *     
     */
    public void setZEILE(ZEILE value) {
        this.zeile = value;
    }

    /**
     * Obtiene el valor de la propiedad datum.
     * 
     * @return
     *     possible object is
     *     {@link DATUM }
     *     
     */
    public DATUM getDATUM() {
        return datum;
    }

    /**
     * Define el valor de la propiedad datum.
     * 
     * @param value
     *     allowed object is
     *     {@link DATUM }
     *     
     */
    public void setDATUM(DATUM value) {
        this.datum = value;
    }

    /**
     * Obtiene el valor de la propiedad uzeit.
     * 
     * @return
     *     possible object is
     *     {@link UZEIT }
     *     
     */
    public UZEIT getUZEIT() {
        return uzeit;
    }

    /**
     * Define el valor de la propiedad uzeit.
     * 
     * @param value
     *     allowed object is
     *     {@link UZEIT }
     *     
     */
    public void setUZEIT(UZEIT value) {
        this.uzeit = value;
    }

    /**
     * Obtiene el valor de la propiedad bsark.
     * 
     * @return
     *     possible object is
     *     {@link BSARK }
     *     
     */
    public BSARK getBSARK() {
        return bsark;
    }

    /**
     * Define el valor de la propiedad bsark.
     * 
     * @param value
     *     allowed object is
     *     {@link BSARK }
     *     
     */
    public void setBSARK(BSARK value) {
        this.bsark = value;
    }

    /**
     * Obtiene el valor de la propiedad ihrez.
     * 
     * @return
     *     possible object is
     *     {@link IHREZ }
     *     
     */
    public IHREZ getIHREZ() {
        return ihrez;
    }

    /**
     * Define el valor de la propiedad ihrez.
     * 
     * @param value
     *     allowed object is
     *     {@link IHREZ }
     *     
     */
    public void setIHREZ(IHREZ value) {
        this.ihrez = value;
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
