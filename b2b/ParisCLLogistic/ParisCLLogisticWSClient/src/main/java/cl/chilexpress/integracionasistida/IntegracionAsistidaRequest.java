
package cl.chilexpress.integracionasistida;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.GenerarIntegracionAsistidaRequestType;


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
 *         &lt;element ref="{http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaReq}reqGenerarIntegracionAsistida"/>
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
    "reqGenerarIntegracionAsistida"
})
@XmlRootElement(name = "IntegracionAsistidaRequest")
public class IntegracionAsistidaRequest {

    @XmlElement(namespace = "http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaReq", required = true)
    protected GenerarIntegracionAsistidaRequestType reqGenerarIntegracionAsistida;

    /**
     * Gets the value of the reqGenerarIntegracionAsistida property.
     * 
     * @return
     *     possible object is
     *     {@link GenerarIntegracionAsistidaRequestType }
     *     
     */
    public GenerarIntegracionAsistidaRequestType getReqGenerarIntegracionAsistida() {
        return reqGenerarIntegracionAsistida;
    }

    /**
     * Sets the value of the reqGenerarIntegracionAsistida property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenerarIntegracionAsistidaRequestType }
     *     
     */
    public void setReqGenerarIntegracionAsistida(GenerarIntegracionAsistidaRequestType value) {
        this.reqGenerarIntegracionAsistida = value;
    }

}
