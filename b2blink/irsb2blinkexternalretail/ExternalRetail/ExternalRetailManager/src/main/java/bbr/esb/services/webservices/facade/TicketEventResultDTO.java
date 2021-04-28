
package bbr.esb.services.webservices.facade;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ticketEventResultDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ticketEventResultDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}baseResultDTO">
 *       &lt;sequence>
 *         &lt;element name="ticketresults" type="{http://facade.webservices.services.esb.bbr/}ticketEventDataDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
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
