//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.02.19 a las 12:10:58 PM CLST 
//


package bbr.b2b.logistic.xml.lib_shipment;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{}ib_shipment_hdr"/&gt;
 *         &lt;element ref="{}ib_shipment_dtl" maxOccurs="unbounded"/&gt;
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
    "ibShipmentHdr",
    "ibShipmentDtl"
})
@XmlRootElement(name = "ib_shipment")
public class IbShipment {

    @XmlElement(name = "ib_shipment_hdr", required = true)
    protected IbShipmentHdr ibShipmentHdr;
    @XmlElement(name = "ib_shipment_dtl", required = true)
    protected List<IbShipmentDtl> ibShipmentDtl;

    /**
     * Obtiene el valor de la propiedad ibShipmentHdr.
     * 
     * @return
     *     possible object is
     *     {@link IbShipmentHdr }
     *     
     */
    public IbShipmentHdr getIbShipmentHdr() {
        return ibShipmentHdr;
    }

    /**
     * Define el valor de la propiedad ibShipmentHdr.
     * 
     * @param value
     *     allowed object is
     *     {@link IbShipmentHdr }
     *     
     */
    public void setIbShipmentHdr(IbShipmentHdr value) {
        this.ibShipmentHdr = value;
    }

    /**
     * Gets the value of the ibShipmentDtl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ibShipmentDtl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIbShipmentDtl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IbShipmentDtl }
     * 
     * 
     */
    public List<IbShipmentDtl> getIbShipmentDtl() {
        if (ibShipmentDtl == null) {
            ibShipmentDtl = new ArrayList<IbShipmentDtl>();
        }
        return this.ibShipmentDtl;
    }

}
