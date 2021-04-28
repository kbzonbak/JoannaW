//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:48:44 PM CLT 
//


package bbr.b2b.logistic.xml.qorden_pred;

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
 *         &lt;element ref="{}FUNCTION" minOccurs="0"/&gt;
 *         &lt;element ref="{}TDOBJECT" minOccurs="0"/&gt;
 *         &lt;element ref="{}TDOBNAME" minOccurs="0"/&gt;
 *         &lt;element ref="{}TDID" minOccurs="0"/&gt;
 *         &lt;element ref="{}TDSPRAS" minOccurs="0"/&gt;
 *         &lt;element ref="{}TDTEXTTYPE" minOccurs="0"/&gt;
 *         &lt;element ref="{}LANGUA_ISO" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1TXTP1" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "function",
    "tdobject",
    "tdobname",
    "tdid",
    "tdspras",
    "tdtexttype",
    "languaiso",
    "e1TXTP1"
})
@XmlRootElement(name = "E1TXTH1")
public class E1TXTH1 {

    @XmlElement(name = "FUNCTION")
    protected FUNCTION function;
    @XmlElement(name = "TDOBJECT")
    protected TDOBJECT tdobject;
    @XmlElement(name = "TDOBNAME")
    protected TDOBNAME tdobname;
    @XmlElement(name = "TDID")
    protected TDID tdid;
    @XmlElement(name = "TDSPRAS")
    protected TDSPRAS tdspras;
    @XmlElement(name = "TDTEXTTYPE")
    protected TDTEXTTYPE tdtexttype;
    @XmlElement(name = "LANGUA_ISO")
    protected LANGUAISO languaiso;
    @XmlElement(name = "E1TXTP1")
    protected List<E1TXTP1> e1TXTP1;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad function.
     * 
     * @return
     *     possible object is
     *     {@link FUNCTION }
     *     
     */
    public FUNCTION getFUNCTION() {
        return function;
    }

    /**
     * Define el valor de la propiedad function.
     * 
     * @param value
     *     allowed object is
     *     {@link FUNCTION }
     *     
     */
    public void setFUNCTION(FUNCTION value) {
        this.function = value;
    }

    /**
     * Obtiene el valor de la propiedad tdobject.
     * 
     * @return
     *     possible object is
     *     {@link TDOBJECT }
     *     
     */
    public TDOBJECT getTDOBJECT() {
        return tdobject;
    }

    /**
     * Define el valor de la propiedad tdobject.
     * 
     * @param value
     *     allowed object is
     *     {@link TDOBJECT }
     *     
     */
    public void setTDOBJECT(TDOBJECT value) {
        this.tdobject = value;
    }

    /**
     * Obtiene el valor de la propiedad tdobname.
     * 
     * @return
     *     possible object is
     *     {@link TDOBNAME }
     *     
     */
    public TDOBNAME getTDOBNAME() {
        return tdobname;
    }

    /**
     * Define el valor de la propiedad tdobname.
     * 
     * @param value
     *     allowed object is
     *     {@link TDOBNAME }
     *     
     */
    public void setTDOBNAME(TDOBNAME value) {
        this.tdobname = value;
    }

    /**
     * Obtiene el valor de la propiedad tdid.
     * 
     * @return
     *     possible object is
     *     {@link TDID }
     *     
     */
    public TDID getTDID() {
        return tdid;
    }

    /**
     * Define el valor de la propiedad tdid.
     * 
     * @param value
     *     allowed object is
     *     {@link TDID }
     *     
     */
    public void setTDID(TDID value) {
        this.tdid = value;
    }

    /**
     * Obtiene el valor de la propiedad tdspras.
     * 
     * @return
     *     possible object is
     *     {@link TDSPRAS }
     *     
     */
    public TDSPRAS getTDSPRAS() {
        return tdspras;
    }

    /**
     * Define el valor de la propiedad tdspras.
     * 
     * @param value
     *     allowed object is
     *     {@link TDSPRAS }
     *     
     */
    public void setTDSPRAS(TDSPRAS value) {
        this.tdspras = value;
    }

    /**
     * Obtiene el valor de la propiedad tdtexttype.
     * 
     * @return
     *     possible object is
     *     {@link TDTEXTTYPE }
     *     
     */
    public TDTEXTTYPE getTDTEXTTYPE() {
        return tdtexttype;
    }

    /**
     * Define el valor de la propiedad tdtexttype.
     * 
     * @param value
     *     allowed object is
     *     {@link TDTEXTTYPE }
     *     
     */
    public void setTDTEXTTYPE(TDTEXTTYPE value) {
        this.tdtexttype = value;
    }

    /**
     * Obtiene el valor de la propiedad languaiso.
     * 
     * @return
     *     possible object is
     *     {@link LANGUAISO }
     *     
     */
    public LANGUAISO getLANGUAISO() {
        return languaiso;
    }

    /**
     * Define el valor de la propiedad languaiso.
     * 
     * @param value
     *     allowed object is
     *     {@link LANGUAISO }
     *     
     */
    public void setLANGUAISO(LANGUAISO value) {
        this.languaiso = value;
    }

    /**
     * Gets the value of the e1TXTP1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1TXTP1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1TXTP1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1TXTP1 }
     * 
     * 
     */
    public List<E1TXTP1> getE1TXTP1() {
        if (e1TXTP1 == null) {
            e1TXTP1 = new ArrayList<E1TXTP1>();
        }
        return this.e1TXTP1;
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
