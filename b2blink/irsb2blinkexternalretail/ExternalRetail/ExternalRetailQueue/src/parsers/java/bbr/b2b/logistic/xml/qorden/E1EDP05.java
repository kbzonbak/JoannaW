//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:48:19 PM CLT 
//


package bbr.b2b.logistic.xml.qorden;

import java.util.ArrayList;
import java.util.List;

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
 *         &lt;element ref="{}KOEIN" minOccurs="0"/&gt;
 *         &lt;element ref="{}CURTP" minOccurs="0"/&gt;
 *         &lt;element ref="{}KOBAS" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDP04" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDPS5" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "msatz",
    "koein",
    "curtp",
    "kobas",
    "z1EDP04",
    "e1EDPS5"
})
@XmlRootElement(name = "E1EDP05")
public class E1EDP05 {

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
    @XmlElement(name = "KOEIN")
    protected KOEIN koein;
    @XmlElement(name = "CURTP")
    protected CURTP curtp;
    @XmlElement(name = "KOBAS")
    protected KOBAS kobas;
    @XmlElement(name = "Z1EDP04")
    protected Z1EDP04 z1EDP04;
    @XmlElement(name = "E1EDPS5")
    protected List<E1EDPS5> e1EDPS5;
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
     * Obtiene el valor de la propiedad koein.
     * 
     * @return
     *     possible object is
     *     {@link KOEIN }
     *     
     */
    public KOEIN getKOEIN() {
        return koein;
    }

    /**
     * Define el valor de la propiedad koein.
     * 
     * @param value
     *     allowed object is
     *     {@link KOEIN }
     *     
     */
    public void setKOEIN(KOEIN value) {
        this.koein = value;
    }

    /**
     * Obtiene el valor de la propiedad curtp.
     * 
     * @return
     *     possible object is
     *     {@link CURTP }
     *     
     */
    public CURTP getCURTP() {
        return curtp;
    }

    /**
     * Define el valor de la propiedad curtp.
     * 
     * @param value
     *     allowed object is
     *     {@link CURTP }
     *     
     */
    public void setCURTP(CURTP value) {
        this.curtp = value;
    }

    /**
     * Obtiene el valor de la propiedad kobas.
     * 
     * @return
     *     possible object is
     *     {@link KOBAS }
     *     
     */
    public KOBAS getKOBAS() {
        return kobas;
    }

    /**
     * Define el valor de la propiedad kobas.
     * 
     * @param value
     *     allowed object is
     *     {@link KOBAS }
     *     
     */
    public void setKOBAS(KOBAS value) {
        this.kobas = value;
    }

    /**
     * Obtiene el valor de la propiedad z1EDP04.
     * 
     * @return
     *     possible object is
     *     {@link Z1EDP04 }
     *     
     */
    public Z1EDP04 getZ1EDP04() {
        return z1EDP04;
    }

    /**
     * Define el valor de la propiedad z1EDP04.
     * 
     * @param value
     *     allowed object is
     *     {@link Z1EDP04 }
     *     
     */
    public void setZ1EDP04(Z1EDP04 value) {
        this.z1EDP04 = value;
    }

    /**
     * Gets the value of the e1EDPS5 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDPS5 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDPS5().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDPS5 }
     * 
     * 
     */
    public List<E1EDPS5> getE1EDPS5() {
        if (e1EDPS5 == null) {
            e1EDPS5 = new ArrayList<E1EDPS5>();
        }
        return this.e1EDPS5;
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
