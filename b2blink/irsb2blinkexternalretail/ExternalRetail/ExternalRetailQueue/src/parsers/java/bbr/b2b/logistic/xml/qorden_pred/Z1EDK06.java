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
 *         &lt;element ref="{}ALC5463" minOccurs="0"/&gt;
 *         &lt;element ref="{}ALC7161" minOccurs="0"/&gt;
 *         &lt;element ref="{}PCD5245" minOccurs="0"/&gt;
 *         &lt;element ref="{}PCD5482" minOccurs="0"/&gt;
 *         &lt;element ref="{}MOA5025" minOccurs="0"/&gt;
 *         &lt;element ref="{}MOA5004" minOccurs="0"/&gt;
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
    "alc5463",
    "alc7161",
    "pcd5245",
    "pcd5482",
    "moa5025",
    "moa5004"
})
@XmlRootElement(name = "Z1EDK06")
public class Z1EDK06 {

    @XmlElement(name = "ALC5463")
    protected ALC5463 alc5463;
    @XmlElement(name = "ALC7161")
    protected ALC7161 alc7161;
    @XmlElement(name = "PCD5245")
    protected PCD5245 pcd5245;
    @XmlElement(name = "PCD5482")
    protected PCD5482 pcd5482;
    @XmlElement(name = "MOA5025")
    protected MOA5025 moa5025;
    @XmlElement(name = "MOA5004")
    protected MOA5004 moa5004;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad alc5463.
     * 
     * @return
     *     possible object is
     *     {@link ALC5463 }
     *     
     */
    public ALC5463 getALC5463() {
        return alc5463;
    }

    /**
     * Define el valor de la propiedad alc5463.
     * 
     * @param value
     *     allowed object is
     *     {@link ALC5463 }
     *     
     */
    public void setALC5463(ALC5463 value) {
        this.alc5463 = value;
    }

    /**
     * Obtiene el valor de la propiedad alc7161.
     * 
     * @return
     *     possible object is
     *     {@link ALC7161 }
     *     
     */
    public ALC7161 getALC7161() {
        return alc7161;
    }

    /**
     * Define el valor de la propiedad alc7161.
     * 
     * @param value
     *     allowed object is
     *     {@link ALC7161 }
     *     
     */
    public void setALC7161(ALC7161 value) {
        this.alc7161 = value;
    }

    /**
     * Obtiene el valor de la propiedad pcd5245.
     * 
     * @return
     *     possible object is
     *     {@link PCD5245 }
     *     
     */
    public PCD5245 getPCD5245() {
        return pcd5245;
    }

    /**
     * Define el valor de la propiedad pcd5245.
     * 
     * @param value
     *     allowed object is
     *     {@link PCD5245 }
     *     
     */
    public void setPCD5245(PCD5245 value) {
        this.pcd5245 = value;
    }

    /**
     * Obtiene el valor de la propiedad pcd5482.
     * 
     * @return
     *     possible object is
     *     {@link PCD5482 }
     *     
     */
    public PCD5482 getPCD5482() {
        return pcd5482;
    }

    /**
     * Define el valor de la propiedad pcd5482.
     * 
     * @param value
     *     allowed object is
     *     {@link PCD5482 }
     *     
     */
    public void setPCD5482(PCD5482 value) {
        this.pcd5482 = value;
    }

    /**
     * Obtiene el valor de la propiedad moa5025.
     * 
     * @return
     *     possible object is
     *     {@link MOA5025 }
     *     
     */
    public MOA5025 getMOA5025() {
        return moa5025;
    }

    /**
     * Define el valor de la propiedad moa5025.
     * 
     * @param value
     *     allowed object is
     *     {@link MOA5025 }
     *     
     */
    public void setMOA5025(MOA5025 value) {
        this.moa5025 = value;
    }

    /**
     * Obtiene el valor de la propiedad moa5004.
     * 
     * @return
     *     possible object is
     *     {@link MOA5004 }
     *     
     */
    public MOA5004 getMOA5004() {
        return moa5004;
    }

    /**
     * Define el valor de la propiedad moa5004.
     * 
     * @param value
     *     allowed object is
     *     {@link MOA5004 }
     *     
     */
    public void setMOA5004(MOA5004 value) {
        this.moa5004 = value;
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
