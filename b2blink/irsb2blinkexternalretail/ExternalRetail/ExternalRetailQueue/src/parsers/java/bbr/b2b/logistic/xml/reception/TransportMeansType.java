//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:47:48 PM CLT 
//


package bbr.b2b.logistic.xml.reception;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
 * 					&lt;ccts:ComponentType&gt;ABIE&lt;/ccts:ComponentType&gt;&#13;
 * 					&lt;ccts:DictionaryEntryName&gt;Transport Means. Details&#13;
 * 					&lt;/ccts:DictionaryEntryName&gt;&#13;
 * 					&lt;ccts:Definition&gt;The particular vehicle used for the transport of&#13;
 * 						goods or persons.&lt;/ccts:Definition&gt;&#13;
 * 					&lt;ccts:ObjectClass&gt;Transport Means&lt;/ccts:ObjectClass&gt;&#13;
 * 					&lt;ccts:AlternativeBusinessTerms&gt;Conveyance&#13;
 * 					&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
 * 				&lt;/ccts:Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Clase Java para TransportMeansType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TransportMeansType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}JourneyID" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RegistrationNationalityID" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RegistrationNationality" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DirectionCode" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Stowage" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AirTransport" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RoadTransport" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RailTransport" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MaritimeTransport" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OwnerParty" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransportMeansType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", propOrder = {
    "journeyID",
    "registrationNationalityID",
    "registrationNationality",
    "directionCode",
    "stowage",
    "airTransport",
    "roadTransport",
    "railTransport",
    "maritimeTransport",
    "ownerParty"
})
public class TransportMeansType {

    @XmlElement(name = "JourneyID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected JourneyIDType journeyID;
    @XmlElement(name = "RegistrationNationalityID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RegistrationNationalityIDType registrationNationalityID;
    @XmlElement(name = "RegistrationNationality", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<RegistrationNationalityType> registrationNationality;
    @XmlElement(name = "DirectionCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DirectionCodeType directionCode;
    @XmlElement(name = "Stowage")
    protected StowageType stowage;
    @XmlElement(name = "AirTransport")
    protected AirTransportType airTransport;
    @XmlElement(name = "RoadTransport")
    protected RoadTransportType roadTransport;
    @XmlElement(name = "RailTransport")
    protected RailTransportType railTransport;
    @XmlElement(name = "MaritimeTransport")
    protected MaritimeTransportType maritimeTransport;
    @XmlElement(name = "OwnerParty")
    protected PartyType ownerParty;

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Transport Means. Journey Identifier.&#13;
     * 								Identifier&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An identifier assigned to a regularly scheduled&#13;
     * 								service of a means of transport.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Transport Means&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Journey Identifier&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Identifier&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Identifier. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Voyage Number, Scheduled&#13;
     * 								Conveyance Identifier (WCO ID 205), Flight Number&#13;
     * 							&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link JourneyIDType }
     *     
     */
    public JourneyIDType getJourneyID() {
        return journeyID;
    }

    /**
     * Define el valor de la propiedad journeyID.
     * 
     * @param value
     *     allowed object is
     *     {@link JourneyIDType }
     *     
     */
    public void setJourneyID(JourneyIDType value) {
        this.journeyID = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Transport Means. Registration_&#13;
     * 								Nationality Identifier. Identifier&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Formal identification of the country in which a&#13;
     * 								means of transport is registered.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Transport Means&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Registration&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Nationality Identifier&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Identifier&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Identifier. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Nationality of Means of Transport&#13;
     * 								(WCO 175, 178 and 179)&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 							&lt;ccts:Examples&gt;"LIB"&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link RegistrationNationalityIDType }
     *     
     */
    public RegistrationNationalityIDType getRegistrationNationalityID() {
        return registrationNationalityID;
    }

    /**
     * Define el valor de la propiedad registrationNationalityID.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistrationNationalityIDType }
     *     
     */
    public void setRegistrationNationalityID(RegistrationNationalityIDType value) {
        this.registrationNationalityID = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Transport Means. Registration_&#13;
     * 								Nationality. Text&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Name of the country in which a means of&#13;
     * 								transport is registered.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Transport Means&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Registration&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Nationality&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Text&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Text. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Flag of Vessel, Nationality of&#13;
     * 								Ship&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 							&lt;ccts:Examples&gt;Liberia&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the registrationNationality property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the registrationNationality property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegistrationNationality().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RegistrationNationalityType }
     * 
     * 
     */
    public List<RegistrationNationalityType> getRegistrationNationality() {
        if (registrationNationality == null) {
            registrationNationality = new ArrayList<RegistrationNationalityType>();
        }
        return this.registrationNationality;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Transport Means. Direction Code. Code&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The direction of the transport means.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Transport Means&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Direction Code&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Code&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Code. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Transit Direction&#13;
     * 							&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 							&lt;ccts:Examples&gt;"North","East"&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link DirectionCodeType }
     *     
     */
    public DirectionCodeType getDirectionCode() {
        return directionCode;
    }

    /**
     * Define el valor de la propiedad directionCode.
     * 
     * @param value
     *     allowed object is
     *     {@link DirectionCodeType }
     *     
     */
    public void setDirectionCode(DirectionCodeType value) {
        this.directionCode = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Transport Means. Stowage&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Association to a location on board a means of&#13;
     * 								transport where specified goods or transport equipment have been&#13;
     * 								or are to be stowed.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Transport Means&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Stowage&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Stowage&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link StowageType }
     *     
     */
    public StowageType getStowage() {
        return stowage;
    }

    /**
     * Define el valor de la propiedad stowage.
     * 
     * @param value
     *     allowed object is
     *     {@link StowageType }
     *     
     */
    public void setStowage(StowageType value) {
        this.stowage = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Transport Means. Air Transport&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Association to identify an aircraft.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Transport Means&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Air Transport&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Air Transport&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link AirTransportType }
     *     
     */
    public AirTransportType getAirTransport() {
        return airTransport;
    }

    /**
     * Define el valor de la propiedad airTransport.
     * 
     * @param value
     *     allowed object is
     *     {@link AirTransportType }
     *     
     */
    public void setAirTransport(AirTransportType value) {
        this.airTransport = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Transport Means. Road Transport&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Association to identify a road vehicle.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Transport Means&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Road Transport&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Road Transport&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link RoadTransportType }
     *     
     */
    public RoadTransportType getRoadTransport() {
        return roadTransport;
    }

    /**
     * Define el valor de la propiedad roadTransport.
     * 
     * @param value
     *     allowed object is
     *     {@link RoadTransportType }
     *     
     */
    public void setRoadTransport(RoadTransportType value) {
        this.roadTransport = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Transport Means. Rail Transport&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Association to identify a train.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Transport Means&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Rail Transport&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Rail Transport&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link RailTransportType }
     *     
     */
    public RailTransportType getRailTransport() {
        return railTransport;
    }

    /**
     * Define el valor de la propiedad railTransport.
     * 
     * @param value
     *     allowed object is
     *     {@link RailTransportType }
     *     
     */
    public void setRailTransport(RailTransportType value) {
        this.railTransport = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Transport Means. Maritime Transport&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Association to identify a ship.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Transport Means&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Maritime Transport&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Maritime Transport&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link MaritimeTransportType }
     *     
     */
    public MaritimeTransportType getMaritimeTransport() {
        return maritimeTransport;
    }

    /**
     * Define el valor de la propiedad maritimeTransport.
     * 
     * @param value
     *     allowed object is
     *     {@link MaritimeTransportType }
     *     
     */
    public void setMaritimeTransport(MaritimeTransportType value) {
        this.maritimeTransport = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Transport Means. Owner_ Party. Party&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Association to the party owning the means of&#13;
     * 								transport.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Transport Means&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Owner&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Party&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Party&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getOwnerParty() {
        return ownerParty;
    }

    /**
     * Define el valor de la propiedad ownerParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setOwnerParty(PartyType value) {
        this.ownerParty = value;
    }

}
