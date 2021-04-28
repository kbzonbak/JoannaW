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
 *         &lt;element ref="{}ALCKZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}KSCHL" minOccurs="0"/&gt;
 *         &lt;element ref="{}KOTXT" minOccurs="0"/&gt;
 *         &lt;element ref="{}BETRG" minOccurs="0"/&gt;
 *         &lt;element ref="{}KPERC" minOccurs="0"/&gt;
 *         &lt;element ref="{}KRATE" minOccurs="0"/&gt;
 *         &lt;element ref="{}UPRBS" minOccurs="0"/&gt;
 *         &lt;element ref="{}MEAUN" minOccurs="0"/&gt;
 *         &lt;element ref="{}KOBTR" minOccurs="0"/&gt;
 *         &lt;element ref="{}MENGE" minOccurs="0"/&gt;
 *         &lt;element ref="{}PREIS" minOccurs="0"/&gt;
 *         &lt;element ref="{}MWSKZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}MSATZ" minOccurs="0"/&gt;
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
    "alckz",
    "kschl",
    "kotxt",
    "betrg",
    "kperc",
    "krate",
    "uprbs",
    "meaun",
    "kobtr",
    "menge",
    "preis",
    "mwskz",
    "msatz"
})
@XmlRootElement(name = "E1EDC05")
public class E1EDC05 {

    @XmlElement(name = "ALCKZ")
    protected ALCKZ alckz;
    @XmlElement(name = "KSCHL")
    protected KSCHL kschl;
    @XmlElement(name = "KOTXT")
    protected KOTXT kotxt;
    @XmlElement(name = "BETRG")
    protected BETRG betrg;
    @XmlElement(name = "KPERC")
    protected KPERC kperc;
    @XmlElement(name = "KRATE")
    protected KRATE krate;
    @XmlElement(name = "UPRBS")
    protected UPRBS uprbs;
    @XmlElement(name = "MEAUN")
    protected MEAUN meaun;
    @XmlElement(name = "KOBTR")
    protected KOBTR kobtr;
    @XmlElement(name = "MENGE")
    protected MENGE menge;
    @XmlElement(name = "PREIS")
    protected PREIS preis;
    @XmlElement(name = "MWSKZ")
    protected MWSKZ mwskz;
    @XmlElement(name = "MSATZ")
    protected MSATZ msatz;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad alckz.
     * 
     * @return
     *     possible object is
     *     {@link ALCKZ }
     *     
     */
    public ALCKZ getALCKZ() {
        return alckz;
    }

    /**
     * Define el valor de la propiedad alckz.
     * 
     * @param value
     *     allowed object is
     *     {@link ALCKZ }
     *     
     */
    public void setALCKZ(ALCKZ value) {
        this.alckz = value;
    }

    /**
     * Obtiene el valor de la propiedad kschl.
     * 
     * @return
     *     possible object is
     *     {@link KSCHL }
     *     
     */
    public KSCHL getKSCHL() {
        return kschl;
    }

    /**
     * Define el valor de la propiedad kschl.
     * 
     * @param value
     *     allowed object is
     *     {@link KSCHL }
     *     
     */
    public void setKSCHL(KSCHL value) {
        this.kschl = value;
    }

    /**
     * Obtiene el valor de la propiedad kotxt.
     * 
     * @return
     *     possible object is
     *     {@link KOTXT }
     *     
     */
    public KOTXT getKOTXT() {
        return kotxt;
    }

    /**
     * Define el valor de la propiedad kotxt.
     * 
     * @param value
     *     allowed object is
     *     {@link KOTXT }
     *     
     */
    public void setKOTXT(KOTXT value) {
        this.kotxt = value;
    }

    /**
     * Obtiene el valor de la propiedad betrg.
     * 
     * @return
     *     possible object is
     *     {@link BETRG }
     *     
     */
    public BETRG getBETRG() {
        return betrg;
    }

    /**
     * Define el valor de la propiedad betrg.
     * 
     * @param value
     *     allowed object is
     *     {@link BETRG }
     *     
     */
    public void setBETRG(BETRG value) {
        this.betrg = value;
    }

    /**
     * Obtiene el valor de la propiedad kperc.
     * 
     * @return
     *     possible object is
     *     {@link KPERC }
     *     
     */
    public KPERC getKPERC() {
        return kperc;
    }

    /**
     * Define el valor de la propiedad kperc.
     * 
     * @param value
     *     allowed object is
     *     {@link KPERC }
     *     
     */
    public void setKPERC(KPERC value) {
        this.kperc = value;
    }

    /**
     * Obtiene el valor de la propiedad krate.
     * 
     * @return
     *     possible object is
     *     {@link KRATE }
     *     
     */
    public KRATE getKRATE() {
        return krate;
    }

    /**
     * Define el valor de la propiedad krate.
     * 
     * @param value
     *     allowed object is
     *     {@link KRATE }
     *     
     */
    public void setKRATE(KRATE value) {
        this.krate = value;
    }

    /**
     * Obtiene el valor de la propiedad uprbs.
     * 
     * @return
     *     possible object is
     *     {@link UPRBS }
     *     
     */
    public UPRBS getUPRBS() {
        return uprbs;
    }

    /**
     * Define el valor de la propiedad uprbs.
     * 
     * @param value
     *     allowed object is
     *     {@link UPRBS }
     *     
     */
    public void setUPRBS(UPRBS value) {
        this.uprbs = value;
    }

    /**
     * Obtiene el valor de la propiedad meaun.
     * 
     * @return
     *     possible object is
     *     {@link MEAUN }
     *     
     */
    public MEAUN getMEAUN() {
        return meaun;
    }

    /**
     * Define el valor de la propiedad meaun.
     * 
     * @param value
     *     allowed object is
     *     {@link MEAUN }
     *     
     */
    public void setMEAUN(MEAUN value) {
        this.meaun = value;
    }

    /**
     * Obtiene el valor de la propiedad kobtr.
     * 
     * @return
     *     possible object is
     *     {@link KOBTR }
     *     
     */
    public KOBTR getKOBTR() {
        return kobtr;
    }

    /**
     * Define el valor de la propiedad kobtr.
     * 
     * @param value
     *     allowed object is
     *     {@link KOBTR }
     *     
     */
    public void setKOBTR(KOBTR value) {
        this.kobtr = value;
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
     * Obtiene el valor de la propiedad preis.
     * 
     * @return
     *     possible object is
     *     {@link PREIS }
     *     
     */
    public PREIS getPREIS() {
        return preis;
    }

    /**
     * Define el valor de la propiedad preis.
     * 
     * @param value
     *     allowed object is
     *     {@link PREIS }
     *     
     */
    public void setPREIS(PREIS value) {
        this.preis = value;
    }

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
