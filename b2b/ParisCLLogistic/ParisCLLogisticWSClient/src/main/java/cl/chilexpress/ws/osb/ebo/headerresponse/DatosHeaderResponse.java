
package cl.chilexpress.ws.osb.ebo.headerresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for datosHeaderResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datosHeaderResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transaccion" type="{http://ws.chilexpress.cl/OSB/EBO/HeaderResponse}datosTransaccion"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datosHeaderResponse", propOrder = {
    "transaccion"
})
public class DatosHeaderResponse {

    @XmlElement(required = true)
    protected DatosTransaccion transaccion;

    /**
     * Gets the value of the transaccion property.
     * 
     * @return
     *     possible object is
     *     {@link DatosTransaccion }
     *     
     */
    public DatosTransaccion getTransaccion() {
        return transaccion;
    }

    /**
     * Sets the value of the transaccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosTransaccion }
     *     
     */
    public void setTransaccion(DatosTransaccion value) {
        this.transaccion = value;
    }

}
