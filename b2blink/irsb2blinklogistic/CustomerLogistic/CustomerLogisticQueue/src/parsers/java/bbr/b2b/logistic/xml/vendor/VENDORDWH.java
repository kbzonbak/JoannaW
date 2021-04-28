//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:49:33 PM CLT 
//


package bbr.b2b.logistic.xml.vendor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{}VS"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "vs"
})
@XmlRootElement(name = "VENDOR_DWH")
public class VENDORDWH {

    @XmlElement(name = "VS", required = true)
    protected VS vs;

    /**
     * Obtiene el valor de la propiedad vs.
     * 
     * @return
     *     possible object is
     *     {@link VS }
     *     
     */
    public VS getVS() {
        return vs;
    }

    /**
     * Define el valor de la propiedad vs.
     * 
     * @param value
     *     allowed object is
     *     {@link VS }
     *     
     */
    public void setVS(VS value) {
        this.vs = value;
    }

}
