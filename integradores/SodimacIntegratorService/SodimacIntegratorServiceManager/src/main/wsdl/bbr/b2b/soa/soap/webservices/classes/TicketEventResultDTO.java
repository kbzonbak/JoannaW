//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.08 a las 02:24:51 PM CLT 
//


package bbr.b2b.soa.soap.webservices.classes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ticketEventResultDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ticketEventResultDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}baseResultDTO"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ticketresults" type="{http://facade.webservices.services.esb.bbr/}ticketEventDataDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ticketEventResultDTO", propOrder = {
    "ticketresults"
})
public class TicketEventResultDTO
    extends BaseResultDTO
{

    @XmlElement(nillable = true)
    protected List<TicketEventDataDTO> ticketresults;

    /**
     * Gets the value of the ticketresults property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ticketresults property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTicketresults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TicketEventDataDTO }
     * 
     * 
     */
    public List<TicketEventDataDTO> getTicketresults() {
        if (ticketresults == null) {
            ticketresults = new ArrayList<TicketEventDataDTO>();
        }
        return this.ticketresults;
    }

}
