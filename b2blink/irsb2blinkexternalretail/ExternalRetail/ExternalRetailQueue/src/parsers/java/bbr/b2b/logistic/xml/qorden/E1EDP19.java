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
 *         &lt;element ref="{}QUALF" minOccurs="0"/&gt;
 *         &lt;element ref="{}IDTNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}KTEXT" minOccurs="0"/&gt;
 *         &lt;element ref="{}MFRPN" minOccurs="0"/&gt;
 *         &lt;element ref="{}MFRNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDP05" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDP08" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "idtnr",
    "ktext",
    "mfrpn",
    "mfrnr",
    "z1EDP05",
    "z1EDP08"
})
@XmlRootElement(name = "E1EDP19")
public class E1EDP19 {

    @XmlElement(name = "QUALF")
    protected QUALF qualf;
    @XmlElement(name = "IDTNR")
    protected IDTNR idtnr;
    @XmlElement(name = "KTEXT")
    protected KTEXT ktext;
    @XmlElement(name = "MFRPN")
    protected MFRPN mfrpn;
    @XmlElement(name = "MFRNR")
    protected MFRNR mfrnr;
    @XmlElement(name = "Z1EDP05")
    protected Z1EDP05 z1EDP05;
    @XmlElement(name = "Z1EDP08")
    protected List<Z1EDP08> z1EDP08;
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
     * Obtiene el valor de la propiedad idtnr.
     * 
     * @return
     *     possible object is
     *     {@link IDTNR }
     *     
     */
    public IDTNR getIDTNR() {
        return idtnr;
    }

    /**
     * Define el valor de la propiedad idtnr.
     * 
     * @param value
     *     allowed object is
     *     {@link IDTNR }
     *     
     */
    public void setIDTNR(IDTNR value) {
        this.idtnr = value;
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
     * Obtiene el valor de la propiedad mfrpn.
     * 
     * @return
     *     possible object is
     *     {@link MFRPN }
     *     
     */
    public MFRPN getMFRPN() {
        return mfrpn;
    }

    /**
     * Define el valor de la propiedad mfrpn.
     * 
     * @param value
     *     allowed object is
     *     {@link MFRPN }
     *     
     */
    public void setMFRPN(MFRPN value) {
        this.mfrpn = value;
    }

    /**
     * Obtiene el valor de la propiedad mfrnr.
     * 
     * @return
     *     possible object is
     *     {@link MFRNR }
     *     
     */
    public MFRNR getMFRNR() {
        return mfrnr;
    }

    /**
     * Define el valor de la propiedad mfrnr.
     * 
     * @param value
     *     allowed object is
     *     {@link MFRNR }
     *     
     */
    public void setMFRNR(MFRNR value) {
        this.mfrnr = value;
    }

    /**
     * Obtiene el valor de la propiedad z1EDP05.
     * 
     * @return
     *     possible object is
     *     {@link Z1EDP05 }
     *     
     */
    public Z1EDP05 getZ1EDP05() {
        return z1EDP05;
    }

    /**
     * Define el valor de la propiedad z1EDP05.
     * 
     * @param value
     *     allowed object is
     *     {@link Z1EDP05 }
     *     
     */
    public void setZ1EDP05(Z1EDP05 value) {
        this.z1EDP05 = value;
    }

    /**
     * Gets the value of the z1EDP08 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the z1EDP08 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZ1EDP08().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Z1EDP08 }
     * 
     * 
     */
    public List<Z1EDP08> getZ1EDP08() {
        if (z1EDP08 == null) {
            z1EDP08 = new ArrayList<Z1EDP08>();
        }
        return this.z1EDP08;
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
