
package bbr.esb.services.webservices.facade;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ticketReportResultDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ticketReportResultDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}baseResultDTO">
 *       &lt;sequence>
 *         &lt;element name="pageInfoDTO" type="{http://facade.webservices.services.esb.bbr/}pageInfoDTO" minOccurs="0"/>
 *         &lt;element name="tickets" type="{http://facade.webservices.services.esb.bbr/}ticketReportDataResultDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ticketReportResultDTO", propOrder = {
    "pageInfoDTO",
    "tickets"
})
public class TicketReportResultDTO
    extends BaseResultDTO
{

    protected PageInfoDTO pageInfoDTO;
    @XmlElement(nillable = true)
    protected List<TicketReportDataResultDTO> tickets;

    /**
     * Gets the value of the pageInfoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link PageInfoDTO }
     *     
     */
    public PageInfoDTO getPageInfoDTO() {
        return pageInfoDTO;
    }

    /**
     * Sets the value of the pageInfoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageInfoDTO }
     *     
     */
    public void setPageInfoDTO(PageInfoDTO value) {
        this.pageInfoDTO = value;
    }

    /**
     * Gets the value of the tickets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tickets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTickets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TicketReportDataResultDTO }
     * 
     * 
     */
    public List<TicketReportDataResultDTO> getTickets() {
        if (tickets == null) {
            tickets = new ArrayList<TicketReportDataResultDTO>();
        }
        return this.tickets;
    }

}
