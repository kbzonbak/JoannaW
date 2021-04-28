
package cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PiezaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PiezaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="peso" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="alto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ancho" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="largo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PiezaType", propOrder = {
    "peso",
    "alto",
    "ancho",
    "largo"
})
public class PiezaType {

    @XmlElement(required = true)
    protected BigDecimal peso;
    protected int alto;
    protected int ancho;
    protected int largo;

    /**
     * Gets the value of the peso property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPeso() {
        return peso;
    }

    /**
     * Sets the value of the peso property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPeso(BigDecimal value) {
        this.peso = value;
    }

    /**
     * Gets the value of the alto property.
     * 
     */
    public int getAlto() {
        return alto;
    }

    /**
     * Sets the value of the alto property.
     * 
     */
    public void setAlto(int value) {
        this.alto = value;
    }

    /**
     * Gets the value of the ancho property.
     * 
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * Sets the value of the ancho property.
     * 
     */
    public void setAncho(int value) {
        this.ancho = value;
    }

    /**
     * Gets the value of the largo property.
     * 
     */
    public int getLargo() {
        return largo;
    }

    /**
     * Sets the value of the largo property.
     * 
     */
    public void setLargo(int value) {
        this.largo = value;
    }

}
