//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.02.19 a las 12:10:58 PM CLST 
//


package bbr.b2b.logistic.xml.lib_shipment;

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
 *         &lt;element ref="{}Header"/&gt;
 *         &lt;element ref="{}ListOfIbShipments"/&gt;
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
    "header",
    "listOfIbShipments"
})
@XmlRootElement(name = "LgfData")
public class LgfData {

    @XmlElement(name = "Header", required = true)
    protected Header header;
    @XmlElement(name = "ListOfIbShipments", required = true)
    protected ListOfIbShipments listOfIbShipments;

    /**
     * Obtiene el valor de la propiedad header.
     * 
     * @return
     *     possible object is
     *     {@link Header }
     *     
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Define el valor de la propiedad header.
     * 
     * @param value
     *     allowed object is
     *     {@link Header }
     *     
     */
    public void setHeader(Header value) {
        this.header = value;
    }

    /**
     * Obtiene el valor de la propiedad listOfIbShipments.
     * 
     * @return
     *     possible object is
     *     {@link ListOfIbShipments }
     *     
     */
    public ListOfIbShipments getListOfIbShipments() {
        return listOfIbShipments;
    }

    /**
     * Define el valor de la propiedad listOfIbShipments.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfIbShipments }
     *     
     */
    public void setListOfIbShipments(ListOfIbShipments value) {
        this.listOfIbShipments = value;
    }

}
