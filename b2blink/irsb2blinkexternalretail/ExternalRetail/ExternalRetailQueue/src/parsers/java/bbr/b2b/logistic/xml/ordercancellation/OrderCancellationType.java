//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:46:49 PM CLT 
//


package bbr.b2b.logistic.xml.ordercancellation;

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
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
 * 					&lt;ccts:ComponentType&gt;ABIE&lt;/ccts:ComponentType&gt;&#13;
 * 					&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Details&lt;/ccts:DictionaryEntryName&gt;&#13;
 * 					&lt;ccts:Definition&gt;The document used to cancel an entire Order.&lt;/ccts:Definition&gt;&#13;
 * 					&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
 * 				&lt;/ccts:Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Clase Java para OrderCancellationType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="OrderCancellationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}UBLVersionID" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CustomizationID" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ProfileID" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CopyIndicator" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}UUID" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}IssueDate"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}IssueTime" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Note" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CancellationNote"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OrderReference" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OriginatorDocumentReference" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalDocumentReference" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Contract" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Signature" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}BuyerCustomerParty"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SellerSupplierParty"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OriginatorCustomerParty" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderCancellationType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2", propOrder = {
    "ublExtensions",
    "ublVersionID",
    "customizationID",
    "profileID",
    "id",
    "copyIndicator",
    "uuid",
    "issueDate",
    "issueTime",
    "note",
    "cancellationNote",
    "orderReference",
    "originatorDocumentReference",
    "additionalDocumentReference",
    "contract",
    "signature",
    "buyerCustomerParty",
    "sellerSupplierParty",
    "originatorCustomerParty"
})
public class OrderCancellationType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "UBLVersionID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected UBLVersionIDType ublVersionID;
    @XmlElement(name = "CustomizationID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CustomizationIDType customizationID;
    @XmlElement(name = "ProfileID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ProfileIDType profileID;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "CopyIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CopyIndicatorType copyIndicator;
    @XmlElement(name = "UUID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected UUIDType uuid;
    @XmlElement(name = "IssueDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IssueDateType issueDate;
    @XmlElement(name = "IssueTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IssueTimeType issueTime;
    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<NoteType> note;
    @XmlElement(name = "CancellationNote", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected CancellationNoteType cancellationNote;
    @XmlElement(name = "OrderReference", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected List<OrderReferenceType> orderReference;
    @XmlElement(name = "OriginatorDocumentReference", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected DocumentReferenceType originatorDocumentReference;
    @XmlElement(name = "AdditionalDocumentReference", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected List<DocumentReferenceType> additionalDocumentReference;
    @XmlElement(name = "Contract", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected List<ContractType> contract;
    @XmlElement(name = "Signature", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected List<SignatureType> signature;
    @XmlElement(name = "BuyerCustomerParty", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected CustomerPartyType buyerCustomerParty;
    @XmlElement(name = "SellerSupplierParty", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected SupplierPartyType sellerSupplierParty;
    @XmlElement(name = "OriginatorCustomerParty", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected CustomerPartyType originatorCustomerParty;

    /**
     * A container for all extensions present in the document.
     * 
     * @return
     *     possible object is
     *     {@link UBLExtensionsType }
     *     
     */
    public UBLExtensionsType getUBLExtensions() {
        return ublExtensions;
    }

    /**
     * Define el valor de la propiedad ublExtensions.
     * 
     * @param value
     *     allowed object is
     *     {@link UBLExtensionsType }
     *     
     */
    public void setUBLExtensions(UBLExtensionsType value) {
        this.ublExtensions = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. UBL Version Identifier. Identifier&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The earliest version of the UBL 2 schema for this document type that defines all of the elements that might be encountered in the current instance.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;UBL Version Identifier&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Identifier&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Identifier. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;2.0.5&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link UBLVersionIDType }
     *     
     */
    public UBLVersionIDType getUBLVersionID() {
        return ublVersionID;
    }

    /**
     * Define el valor de la propiedad ublVersionID.
     * 
     * @param value
     *     allowed object is
     *     {@link UBLVersionIDType }
     *     
     */
    public void setUBLVersionID(UBLVersionIDType value) {
        this.ublVersionID = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Customization Identifier. Identifier&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Identifies a user-defined customization of UBL for a specific use.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Customization Identifier&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Identifier&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Identifier. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;NES&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link CustomizationIDType }
     *     
     */
    public CustomizationIDType getCustomizationID() {
        return customizationID;
    }

    /**
     * Define el valor de la propiedad customizationID.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomizationIDType }
     *     
     */
    public void setCustomizationID(CustomizationIDType value) {
        this.customizationID = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Profile Identifier. Identifier&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Identifies a user-defined profile of the customization of UBL being used.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Profile Identifier&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Identifier&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Identifier. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;BasicProcurementProcess&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link ProfileIDType }
     *     
     */
    public ProfileIDType getProfileID() {
        return profileID;
    }

    /**
     * Define el valor de la propiedad profileID.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileIDType }
     *     
     */
    public void setProfileID(ProfileIDType value) {
        this.profileID = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Identifier&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An identifier for the Order Cancellation assigned by the Buyer.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Identifier&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Identifier&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Identifier. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getID() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setID(IDType value) {
        this.id = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Copy_ Indicator. Indicator&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Indicates whether the Order Cancellation is a copy (true) or not (false).&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Copy&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Indicator&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Indicator&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Indicator. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link CopyIndicatorType }
     *     
     */
    public CopyIndicatorType getCopyIndicator() {
        return copyIndicator;
    }

    /**
     * Define el valor de la propiedad copyIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link CopyIndicatorType }
     *     
     */
    public void setCopyIndicator(CopyIndicatorType value) {
        this.copyIndicator = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. UUID. Identifier&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;A universally unique identifier for an instance of this ABIE.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;UUID&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Identifier&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Identifier. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link UUIDType }
     *     
     */
    public UUIDType getUUID() {
        return uuid;
    }

    /**
     * Define el valor de la propiedad uuid.
     * 
     * @param value
     *     allowed object is
     *     {@link UUIDType }
     *     
     */
    public void setUUID(UUIDType value) {
        this.uuid = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Issue Date. Date&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The date assigned by the Buyer on which the Order was cancelled.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Issue Date&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Date&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Date. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link IssueDateType }
     *     
     */
    public IssueDateType getIssueDate() {
        return issueDate;
    }

    /**
     * Define el valor de la propiedad issueDate.
     * 
     * @param value
     *     allowed object is
     *     {@link IssueDateType }
     *     
     */
    public void setIssueDate(IssueDateType value) {
        this.issueDate = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Issue Time. Time&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The time assigned by the Buyer at which the Order was cancelled.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Issue Time&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Time&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Time. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link IssueTimeType }
     *     
     */
    public IssueTimeType getIssueTime() {
        return issueTime;
    }

    /**
     * Define el valor de la propiedad issueTime.
     * 
     * @param value
     *     allowed object is
     *     {@link IssueTimeType }
     *     
     */
    public void setIssueTime(IssueTimeType value) {
        this.issueTime = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Note. Text&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Free-form text applying to the Order Cancellation. This element may contain notes or any other similar information that is not contained explicitly in another structure.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Note&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Text&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Text. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the note property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the note property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNote().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NoteType }
     * 
     * 
     */
    public List<NoteType> getNote() {
        if (note == null) {
            note = new ArrayList<NoteType>();
        }
        return this.note;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Cancellation_ Note. Text&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The general reason for cancellation of the referenced order.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Cancellation&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Note&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Text&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Text. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link CancellationNoteType }
     *     
     */
    public CancellationNoteType getCancellationNote() {
        return cancellationNote;
    }

    /**
     * Define el valor de la propiedad cancellationNote.
     * 
     * @param value
     *     allowed object is
     *     {@link CancellationNoteType }
     *     
     */
    public void setCancellationNote(CancellationNoteType value) {
        this.cancellationNote = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Order Reference&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An associative reference to the Order(s) being cancelled. It is considered better practice to cancel only one Order in each Order Cancellation document.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;1.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Order Reference&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Order Reference&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the orderReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderReferenceType }
     * 
     * 
     */
    public List<OrderReferenceType> getOrderReference() {
        if (orderReference == null) {
            orderReference = new ArrayList<OrderReferenceType>();
        }
        return this.orderReference;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Originator_ Document Reference. Document Reference&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An associative reference to Originator Document.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Originator&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Document Reference&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Document Reference&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link DocumentReferenceType }
     *     
     */
    public DocumentReferenceType getOriginatorDocumentReference() {
        return originatorDocumentReference;
    }

    /**
     * Define el valor de la propiedad originatorDocumentReference.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentReferenceType }
     *     
     */
    public void setOriginatorDocumentReference(DocumentReferenceType value) {
        this.originatorDocumentReference = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Additional_ Document Reference. Document Reference&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An associative reference to Additional Document.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Additional&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Document Reference&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Document Reference&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the additionalDocumentReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalDocumentReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalDocumentReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getAdditionalDocumentReference() {
        if (additionalDocumentReference == null) {
            additionalDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.additionalDocumentReference;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Contract&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association to Contract.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Contract&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Contract&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the contract property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contract property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContract().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContractType }
     * 
     * 
     */
    public List<ContractType> getContract() {
        if (contract == null) {
            contract = new ArrayList<ContractType>();
        }
        return this.contract;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Signature&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association to Signature.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Signature&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Signature&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the signature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignatureType }
     * 
     * 
     */
    public List<SignatureType> getSignature() {
        if (signature == null) {
            signature = new ArrayList<SignatureType>();
        }
        return this.signature;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Buyer_ Customer Party. Customer Party&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association to the Buyer.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Buyer&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Customer Party&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Customer Party&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link CustomerPartyType }
     *     
     */
    public CustomerPartyType getBuyerCustomerParty() {
        return buyerCustomerParty;
    }

    /**
     * Define el valor de la propiedad buyerCustomerParty.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerPartyType }
     *     
     */
    public void setBuyerCustomerParty(CustomerPartyType value) {
        this.buyerCustomerParty = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Seller_ Supplier Party. Supplier Party&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association to the Seller.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Seller&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Supplier Party&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Supplier Party&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link SupplierPartyType }
     *     
     */
    public SupplierPartyType getSellerSupplierParty() {
        return sellerSupplierParty;
    }

    /**
     * Define el valor de la propiedad sellerSupplierParty.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierPartyType }
     *     
     */
    public void setSellerSupplierParty(SupplierPartyType value) {
        this.sellerSupplierParty = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Order Cancellation. Originator_ Customer Party. Customer Party&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association to the Originator.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Order Cancellation&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Originator&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Customer Party&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Customer Party&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link CustomerPartyType }
     *     
     */
    public CustomerPartyType getOriginatorCustomerParty() {
        return originatorCustomerParty;
    }

    /**
     * Define el valor de la propiedad originatorCustomerParty.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerPartyType }
     *     
     */
    public void setOriginatorCustomerParty(CustomerPartyType value) {
        this.originatorCustomerParty = value;
    }

}
