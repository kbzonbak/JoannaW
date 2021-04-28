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
 *         &lt;element ref="{}BONIF" minOccurs="0"/&gt;
 *         &lt;element ref="{}SURTPARC" minOccurs="0"/&gt;
 *         &lt;element ref="{}EANLOCAL" minOccurs="0"/&gt;
 *         &lt;element ref="{}NBRELOCAL" minOccurs="0"/&gt;
 *         &lt;element ref="{}DIRLOCAL" minOccurs="0"/&gt;
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
    "bonif",
    "surtparc",
    "eanlocal",
    "nbrelocal",
    "dirlocal"
})
@XmlRootElement(name = "Z1EDP09")
public class Z1EDP09 {

    @XmlElement(name = "BONIF")
    protected BONIF bonif;
    @XmlElement(name = "SURTPARC")
    protected SURTPARC surtparc;
    @XmlElement(name = "EANLOCAL")
    protected EANLOCAL eanlocal;
    @XmlElement(name = "NBRELOCAL")
    protected NBRELOCAL nbrelocal;
    @XmlElement(name = "DIRLOCAL")
    protected DIRLOCAL dirlocal;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad bonif.
     * 
     * @return
     *     possible object is
     *     {@link BONIF }
     *     
     */
    public BONIF getBONIF() {
        return bonif;
    }

    /**
     * Define el valor de la propiedad bonif.
     * 
     * @param value
     *     allowed object is
     *     {@link BONIF }
     *     
     */
    public void setBONIF(BONIF value) {
        this.bonif = value;
    }

    /**
     * Obtiene el valor de la propiedad surtparc.
     * 
     * @return
     *     possible object is
     *     {@link SURTPARC }
     *     
     */
    public SURTPARC getSURTPARC() {
        return surtparc;
    }

    /**
     * Define el valor de la propiedad surtparc.
     * 
     * @param value
     *     allowed object is
     *     {@link SURTPARC }
     *     
     */
    public void setSURTPARC(SURTPARC value) {
        this.surtparc = value;
    }

    /**
     * Obtiene el valor de la propiedad eanlocal.
     * 
     * @return
     *     possible object is
     *     {@link EANLOCAL }
     *     
     */
    public EANLOCAL getEANLOCAL() {
        return eanlocal;
    }

    /**
     * Define el valor de la propiedad eanlocal.
     * 
     * @param value
     *     allowed object is
     *     {@link EANLOCAL }
     *     
     */
    public void setEANLOCAL(EANLOCAL value) {
        this.eanlocal = value;
    }

    /**
     * Obtiene el valor de la propiedad nbrelocal.
     * 
     * @return
     *     possible object is
     *     {@link NBRELOCAL }
     *     
     */
    public NBRELOCAL getNBRELOCAL() {
        return nbrelocal;
    }

    /**
     * Define el valor de la propiedad nbrelocal.
     * 
     * @param value
     *     allowed object is
     *     {@link NBRELOCAL }
     *     
     */
    public void setNBRELOCAL(NBRELOCAL value) {
        this.nbrelocal = value;
    }

    /**
     * Obtiene el valor de la propiedad dirlocal.
     * 
     * @return
     *     possible object is
     *     {@link DIRLOCAL }
     *     
     */
    public DIRLOCAL getDIRLOCAL() {
        return dirlocal;
    }

    /**
     * Define el valor de la propiedad dirlocal.
     * 
     * @param value
     *     allowed object is
     *     {@link DIRLOCAL }
     *     
     */
    public void setDIRLOCAL(DIRLOCAL value) {
        this.dirlocal = value;
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
