//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
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
 *         &lt;element ref="{}BELNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}XLINE" minOccurs="0"/&gt;
 *         &lt;element ref="{}DATUM" minOccurs="0"/&gt;
 *         &lt;element ref="{}UZEIT" minOccurs="0"/&gt;
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
    "xline",
    "datum",
    "uzeit"
})
@XmlRootElement(name = "E1EDC02")
public class E1EDC02 {

    @XmlElement(name = "QUALF")
    protected QUALF qualf;
    @XmlElement(name = "BELNR")
    protected BELNR belnr;
    @XmlElement(name = "XLINE")
    protected XLINE xline;
    @XmlElement(name = "DATUM")
    protected DATUM datum;
    @XmlElement(name = "UZEIT")
    protected UZEIT uzeit;
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
     * Obtiene el valor de la propiedad xline.
     * 
     * @return
     *     possible object is
     *     {@link XLINE }
     *     
     */
    public XLINE getXLINE() {
        return xline;
    }

    /**
     * Define el valor de la propiedad xline.
     * 
     * @param value
     *     allowed object is
     *     {@link XLINE }
     *     
     */
    public void setXLINE(XLINE value) {
        this.xline = value;
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