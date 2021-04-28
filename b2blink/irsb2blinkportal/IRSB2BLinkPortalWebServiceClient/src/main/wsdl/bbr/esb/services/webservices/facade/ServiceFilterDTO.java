
package bbr.esb.services.webservices.facade;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for serviceFilterDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serviceFilterDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}baseResultDTO">
 *       &lt;sequence>
 *         &lt;element name="serviceforfilterArray" type="{http://facade.webservices.services.esb.bbr/}serviceDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceFilterDTO", propOrder = {
    "serviceforfilterArray"
})
public class ServiceFilterDTO
    extends BaseResultDTO
{

    @XmlElement(nillable = true)
    protected List<ServiceDTO> serviceforfilterArray;

    /**
     * Gets the value of the serviceforfilterArray property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceforfilterArray property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceforfilterArray().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceDTO }
     * 
     * 
     */
    public List<ServiceDTO> getServiceforfilterArray() {
        if (serviceforfilterArray == null) {
            serviceforfilterArray = new ArrayList<ServiceDTO>();
        }
        return this.serviceforfilterArray;
    }

}
