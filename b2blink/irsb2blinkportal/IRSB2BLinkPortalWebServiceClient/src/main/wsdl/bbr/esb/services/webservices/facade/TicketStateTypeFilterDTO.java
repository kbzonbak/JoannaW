
package bbr.esb.services.webservices.facade;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ticketStateTypeFilterDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ticketStateTypeFilterDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}baseResultDTO">
 *       &lt;sequence>
 *         &lt;element name="ticketstatetype" type="{http://facade.webservices.services.esb.bbr/}ticketStateTypeDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ticketStateTypeFilterDTO", propOrder = {
    "ticketstatetype"
})
public class TicketStateTypeFilterDTO
    extends BaseResultDTO
{

    @XmlElement(nillable = true)
    protected List<TicketStateTypeDTO> ticketstatetype;

    /**
     * Gets the value of the ticketstatetype property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ticketstatetype property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTicketstatetype().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TicketStateTypeDTO }
     * 
     * 
     */
    public List<TicketStateTypeDTO> getTicketstatetype() {
        if (ticketstatetype == null) {
            ticketstatetype = new ArrayList<TicketStateTypeDTO>();
        }
        return this.ticketstatetype;
    }

}
