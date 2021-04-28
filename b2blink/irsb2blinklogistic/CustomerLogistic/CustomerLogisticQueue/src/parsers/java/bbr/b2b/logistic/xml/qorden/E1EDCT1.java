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
 *         &lt;element ref="{}TDID" minOccurs="0"/&gt;
 *         &lt;element ref="{}TSSPRAS" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDCT2" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "tdid",
    "tsspras",
    "e1EDCT2"
})
@XmlRootElement(name = "E1EDCT1")
public class E1EDCT1 {

    @XmlElement(name = "TDID")
    protected TDID tdid;
    @XmlElement(name = "TSSPRAS")
    protected TSSPRAS tsspras;
    @XmlElement(name = "E1EDCT2")
    protected List<E1EDCT2> e1EDCT2;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

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
     * Obtiene el valor de la propiedad tsspras.
     * 
     * @return
     *     possible object is
     *     {@link TSSPRAS }
     *     
     */
    public TSSPRAS getTSSPRAS() {
        return tsspras;
    }

    /**
     * Define el valor de la propiedad tsspras.
     * 
     * @param value
     *     allowed object is
     *     {@link TSSPRAS }
     *     
     */
    public void setTSSPRAS(TSSPRAS value) {
        this.tsspras = value;
    }

    /**
     * Gets the value of the e1EDCT2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDCT2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDCT2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDCT2 }
     * 
     * 
     */
    public List<E1EDCT2> getE1EDCT2() {
        if (e1EDCT2 == null) {
            e1EDCT2 = new ArrayList<E1EDCT2>();
        }
        return this.e1EDCT2;
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
