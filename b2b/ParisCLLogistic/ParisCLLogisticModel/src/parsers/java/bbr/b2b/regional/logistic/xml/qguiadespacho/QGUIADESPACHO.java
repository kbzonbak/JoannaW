//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.11 at 11:27:36 AM CLT 
//


package bbr.b2b.regional.logistic.xml.qguiadespacho;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}secuencia"/>
 *         &lt;element ref="{}nrecepcion"/>
 *         &lt;element ref="{}nasn"/>
 *         &lt;element ref="{}detalles"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "secuencia",
    "nrecepcion",
    "nasn",
    "detalles"
})
@XmlRootElement(name = "QGUIADESPACHO")
public class QGUIADESPACHO {

    protected long secuencia;
    protected long nrecepcion;
    protected long nasn;
    @XmlElement(required = true)
    protected Detalles detalles;

    /**
     * Gets the value of the secuencia property.
     * 
     */
    public long getSecuencia() {
        return secuencia;
    }

    /**
     * Sets the value of the secuencia property.
     * 
     */
    public void setSecuencia(long value) {
        this.secuencia = value;
    }

    /**
     * Gets the value of the nrecepcion property.
     * 
     */
    public long getNrecepcion() {
        return nrecepcion;
    }

    /**
     * Sets the value of the nrecepcion property.
     * 
     */
    public void setNrecepcion(long value) {
        this.nrecepcion = value;
    }

    /**
     * Gets the value of the nasn property.
     * 
     */
    public long getNasn() {
        return nasn;
    }

    /**
     * Sets the value of the nasn property.
     * 
     */
    public void setNasn(long value) {
        this.nasn = value;
    }

    /**
     * Gets the value of the detalles property.
     * 
     * @return
     *     possible object is
     *     {@link Detalles }
     *     
     */
    public Detalles getDetalles() {
        return detalles;
    }

    /**
     * Sets the value of the detalles property.
     * 
     * @param value
     *     allowed object is
     *     {@link Detalles }
     *     
     */
    public void setDetalles(Detalles value) {
        this.detalles = value;
    }

}
