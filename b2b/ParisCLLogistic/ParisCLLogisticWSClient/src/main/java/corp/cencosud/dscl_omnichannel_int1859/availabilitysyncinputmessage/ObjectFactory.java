
package corp.cencosud.dscl_omnichannel_int1859.availabilitysyncinputmessage;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the corp.cencosud.dscl_omnichannel_int1859.availabilitysyncinputmessage package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: corp.cencosud.dscl_omnichannel_int1859.availabilitysyncinputmessage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AvailabilityRequest.AvailabilityCriteria }
     * 
     */
    public AvailabilityRequest.AvailabilityCriteria createAvailabilityRequestAvailabilityCriteria() {
        return new AvailabilityRequest.AvailabilityCriteria();
    }

    /**
     * Create an instance of {@link AvailabilityRequest.AvailabilityCriteria.FacilityNames }
     * 
     */
    public AvailabilityRequest.AvailabilityCriteria.FacilityNames createAvailabilityRequestAvailabilityCriteriaFacilityNames() {
        return new AvailabilityRequest.AvailabilityCriteria.FacilityNames();
    }

    /**
     * Create an instance of {@link AvailabilityRequest.AvailabilityCriteria.ItemNames }
     * 
     */
    public AvailabilityRequest.AvailabilityCriteria.ItemNames createAvailabilityRequestAvailabilityCriteriaItemNames() {
        return new AvailabilityRequest.AvailabilityCriteria.ItemNames();
    }

    /**
     * Create an instance of {@link AvailabilityRequest }
     * 
     */
    public AvailabilityRequest createAvailabilityRequest() {
        return new AvailabilityRequest();
    }

}
