
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="admitirEnvioResult" type="{http://tempuri.org/}RespuestaAdmisionTO" minOccurs="0"/>
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
    "admitirEnvioResult"
})
@XmlRootElement(name = "admitirEnvioResponse")
public class AdmitirEnvioResponse {

    protected RespuestaAdmisionTO admitirEnvioResult;

    /**
     * Gets the value of the admitirEnvioResult property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaAdmisionTO }
     *     
     */
    public RespuestaAdmisionTO getAdmitirEnvioResult() {
        return admitirEnvioResult;
    }

    /**
     * Sets the value of the admitirEnvioResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaAdmisionTO }
     *     
     */
    public void setAdmitirEnvioResult(RespuestaAdmisionTO value) {
        this.admitirEnvioResult = value;
    }

}
