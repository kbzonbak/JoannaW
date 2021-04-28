
package cl.chilexpress.ws.osb.ebo.estadooperacion;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EstadoOperacionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EstadoOperacionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoEstado" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="descripcionEstado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstadoOperacionType", propOrder = {
    "codigoEstado",
    "descripcionEstado"
})
public class EstadoOperacionType {

    protected BigInteger codigoEstado;
    protected String descripcionEstado;

    /**
     * Gets the value of the codigoEstado property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodigoEstado() {
        return codigoEstado;
    }

    /**
     * Sets the value of the codigoEstado property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodigoEstado(BigInteger value) {
        this.codigoEstado = value;
    }

    /**
     * Gets the value of the descripcionEstado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    /**
     * Sets the value of the descripcionEstado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionEstado(String value) {
        this.descripcionEstado = value;
    }

}
