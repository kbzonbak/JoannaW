
package cl.chilexpress.ws.osb.interno.com.generarintegracionasistidaresp;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatosOTType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatosOTType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroOt" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="numeroOtPadre" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatosOTType", propOrder = {
    "numeroOt",
    "numeroOtPadre"
})
public class DatosOTType {

    @XmlElement(required = true)
    protected BigDecimal numeroOt;
    @XmlElement(required = true)
    protected BigDecimal numeroOtPadre;

    /**
     * Gets the value of the numeroOt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNumeroOt() {
        return numeroOt;
    }

    /**
     * Sets the value of the numeroOt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNumeroOt(BigDecimal value) {
        this.numeroOt = value;
    }

    /**
     * Gets the value of the numeroOtPadre property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNumeroOtPadre() {
        return numeroOtPadre;
    }

    /**
     * Sets the value of the numeroOtPadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNumeroOtPadre(BigDecimal value) {
        this.numeroOtPadre = value;
    }

}
