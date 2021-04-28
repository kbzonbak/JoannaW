
package corp.cencosud.dscl_omnichannel_int1859.availabilitysyncoutputmessage;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the corp.cencosud.dscl_omnichannel_int1859.availabilitysyncoutputmessage package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: corp.cencosud.dscl_omnichannel_int1859.availabilitysyncoutputmessage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Availability.CustomFields }
     * 
     */
    public Availability.CustomFields createAvailabilityCustomFields() {
        return new Availability.CustomFields();
    }

    /**
     * Create an instance of {@link Availability.Messages }
     * 
     */
    public Availability.Messages createAvailabilityMessages() {
        return new Availability.Messages();
    }

    /**
     * Create an instance of {@link Availability.Messages.Message }
     * 
     */
    public Availability.Messages.Message createAvailabilityMessagesMessage() {
        return new Availability.Messages.Message();
    }

    /**
     * Create an instance of {@link Availability.AvailabilityDetails.AvailabilityDetail }
     * 
     */
    public Availability.AvailabilityDetails.AvailabilityDetail createAvailabilityAvailabilityDetailsAvailabilityDetail() {
        return new Availability.AvailabilityDetails.AvailabilityDetail();
    }

    /**
     * Create an instance of {@link Availability }
     * 
     */
    public Availability createAvailability() {
        return new Availability();
    }

    /**
     * Create an instance of {@link Availability.AvailabilityDetails }
     * 
     */
    public Availability.AvailabilityDetails createAvailabilityAvailabilityDetails() {
        return new Availability.AvailabilityDetails();
    }

}
