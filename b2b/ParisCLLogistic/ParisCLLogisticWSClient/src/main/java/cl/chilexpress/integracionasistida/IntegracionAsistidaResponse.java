
package cl.chilexpress.integracionasistida;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidaresp.GenerarIntegracionAsistidaResponseType;


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
 *         &lt;element ref="{http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaResp}respGenerarIntegracionAsistida"/>
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
    "respGenerarIntegracionAsistida"
})
@XmlRootElement(name = "IntegracionAsistidaResponse")
public class IntegracionAsistidaResponse {

    @XmlElement(namespace = "http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaResp", required = true)
    protected GenerarIntegracionAsistidaResponseType respGenerarIntegracionAsistida;

    /**
     * Gets the value of the respGenerarIntegracionAsistida property.
     * 
     * @return
     *     possible object is
     *     {@link GenerarIntegracionAsistidaResponseType }
     *     
     */
    public GenerarIntegracionAsistidaResponseType getRespGenerarIntegracionAsistida() {
        return respGenerarIntegracionAsistida;
    }

    /**
     * Sets the value of the respGenerarIntegracionAsistida property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenerarIntegracionAsistidaResponseType }
     *     
     */
    public void setRespGenerarIntegracionAsistida(GenerarIntegracionAsistidaResponseType value) {
        this.respGenerarIntegracionAsistida = value;
    }

}
