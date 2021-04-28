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
 *         &lt;element ref="{}CTA3139" minOccurs="0"/&gt;
 *         &lt;element ref="{}CTA3412" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDK03" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "cta3139",
    "cta3412",
    "z1EDK03"
})
@XmlRootElement(name = "Z1EDK02")
public class Z1EDK02 {

    @XmlElement(name = "CTA3139")
    protected CTA3139 cta3139;
    @XmlElement(name = "CTA3412")
    protected CTA3412 cta3412;
    @XmlElement(name = "Z1EDK03")
    protected List<Z1EDK03> z1EDK03;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad cta3139.
     * 
     * @return
     *     possible object is
     *     {@link CTA3139 }
     *     
     */
    public CTA3139 getCTA3139() {
        return cta3139;
    }

    /**
     * Define el valor de la propiedad cta3139.
     * 
     * @param value
     *     allowed object is
     *     {@link CTA3139 }
     *     
     */
    public void setCTA3139(CTA3139 value) {
        this.cta3139 = value;
    }

    /**
     * Obtiene el valor de la propiedad cta3412.
     * 
     * @return
     *     possible object is
     *     {@link CTA3412 }
     *     
     */
    public CTA3412 getCTA3412() {
        return cta3412;
    }

    /**
     * Define el valor de la propiedad cta3412.
     * 
     * @param value
     *     allowed object is
     *     {@link CTA3412 }
     *     
     */
    public void setCTA3412(CTA3412 value) {
        this.cta3412 = value;
    }

    /**
     * Gets the value of the z1EDK03 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the z1EDK03 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZ1EDK03().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Z1EDK03 }
     * 
     * 
     */
    public List<Z1EDK03> getZ1EDK03() {
        if (z1EDK03 == null) {
            z1EDK03 = new ArrayList<Z1EDK03>();
        }
        return this.z1EDK03;
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
