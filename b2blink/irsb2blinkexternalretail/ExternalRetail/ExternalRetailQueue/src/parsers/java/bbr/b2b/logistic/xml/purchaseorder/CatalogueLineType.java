//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:39:41 PM CLT 
//


package bbr.b2b.logistic.xml.purchaseorder;

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
 * 					&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Details&#13;
 * 					&lt;/ccts:DictionaryEntryName&gt;&#13;
 * 					&lt;ccts:Definition&gt;The basic element of Catalogue; something that can&#13;
 * 						be bought.&lt;/ccts:Definition&gt;&#13;
 * 					&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
 * 				&lt;/ccts:Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Clase Java para CatalogueLineType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CatalogueLineType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ActionCode" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LifeCycleStatusCode" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ContractSubdivision" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Note" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}OrderableIndicator" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}OrderableUnit" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ContentUnitQuantity" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}OrderQuantityIncrementNumeric" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MinimumOrderQuantity" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumOrderQuantity" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}WarrantyInformation" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PackLevelCode" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ContractorCustomerParty" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SellerSupplierParty" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}WarrantyParty" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}WarrantyValidityPeriod" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LineValidityPeriod" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ItemComparison" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ComponentRelatedItem" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AccessoryRelatedItem" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequiredRelatedItem" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ReplacementRelatedItem" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ComplementaryRelatedItem" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequiredItemLocationQuantity" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DocumentReference" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Item"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CatalogueLineType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", propOrder = {
    "id",
    "actionCode",
    "lifeCycleStatusCode",
    "contractSubdivision",
    "note",
    "orderableIndicator",
    "orderableUnit",
    "contentUnitQuantity",
    "orderQuantityIncrementNumeric",
    "minimumOrderQuantity",
    "maximumOrderQuantity",
    "warrantyInformation",
    "packLevelCode",
    "contractorCustomerParty",
    "sellerSupplierParty",
    "warrantyParty",
    "warrantyValidityPeriod",
    "lineValidityPeriod",
    "itemComparison",
    "componentRelatedItem",
    "accessoryRelatedItem",
    "requiredRelatedItem",
    "replacementRelatedItem",
    "complementaryRelatedItem",
    "requiredItemLocationQuantity",
    "documentReference",
    "item"
})
public class CatalogueLineType {

    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "ActionCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ActionCodeType actionCode;
    @XmlElement(name = "LifeCycleStatusCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LifeCycleStatusCodeType lifeCycleStatusCode;
    @XmlElement(name = "ContractSubdivision", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ContractSubdivisionType contractSubdivision;
    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<NoteType> note;
    @XmlElement(name = "OrderableIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected OrderableIndicatorType orderableIndicator;
    @XmlElement(name = "OrderableUnit", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected OrderableUnitType orderableUnit;
    @XmlElement(name = "ContentUnitQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ContentUnitQuantityType contentUnitQuantity;
    @XmlElement(name = "OrderQuantityIncrementNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected OrderQuantityIncrementNumericType orderQuantityIncrementNumeric;
    @XmlElement(name = "MinimumOrderQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MinimumOrderQuantityType minimumOrderQuantity;
    @XmlElement(name = "MaximumOrderQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumOrderQuantityType maximumOrderQuantity;
    @XmlElement(name = "WarrantyInformation", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<WarrantyInformationType> warrantyInformation;
    @XmlElement(name = "PackLevelCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PackLevelCodeType packLevelCode;
    @XmlElement(name = "ContractorCustomerParty")
    protected CustomerPartyType contractorCustomerParty;
    @XmlElement(name = "SellerSupplierParty")
    protected SupplierPartyType sellerSupplierParty;
    @XmlElement(name = "WarrantyParty")
    protected PartyType warrantyParty;
    @XmlElement(name = "WarrantyValidityPeriod")
    protected PeriodType warrantyValidityPeriod;
    @XmlElement(name = "LineValidityPeriod")
    protected PeriodType lineValidityPeriod;
    @XmlElement(name = "ItemComparison")
    protected List<ItemComparisonType> itemComparison;
    @XmlElement(name = "ComponentRelatedItem")
    protected List<RelatedItemType> componentRelatedItem;
    @XmlElement(name = "AccessoryRelatedItem")
    protected List<RelatedItemType> accessoryRelatedItem;
    @XmlElement(name = "RequiredRelatedItem")
    protected List<RelatedItemType> requiredRelatedItem;
    @XmlElement(name = "ReplacementRelatedItem")
    protected List<RelatedItemType> replacementRelatedItem;
    @XmlElement(name = "ComplementaryRelatedItem")
    protected List<RelatedItemType> complementaryRelatedItem;
    @XmlElement(name = "RequiredItemLocationQuantity")
    protected List<ItemLocationQuantityType> requiredItemLocationQuantity;
    @XmlElement(name = "DocumentReference")
    protected List<DocumentReferenceType> documentReference;
    @XmlElement(name = "Item", required = true)
    protected ItemType item;

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Identifier&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;A unique instance identifier for the line in&#13;
     * 								this Catalogue document.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Identifier&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Identifier&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Identifier. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;"1"&lt;/ccts:Examples&gt;&#13;
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
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Action Code. Code&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Code indicating the action required for this&#13;
     * 								item to synchronize with external repositories.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Action Code&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Code&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Code. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;"Replace", "Update", "Delete","Add"&#13;
     * 							&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link ActionCodeType }
     *     
     */
    public ActionCodeType getActionCode() {
        return actionCode;
    }

    /**
     * Define el valor de la propiedad actionCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionCodeType }
     *     
     */
    public void setActionCode(ActionCodeType value) {
        this.actionCode = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Life Cycle Status Code.&#13;
     * 								Code&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Code indicating availability of this line.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Life Cycle Status Code&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Code&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Code. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;"new - announcement only", "new and available",&#13;
     * 								"deleted - announcement only"&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link LifeCycleStatusCodeType }
     *     
     */
    public LifeCycleStatusCodeType getLifeCycleStatusCode() {
        return lifeCycleStatusCode;
    }

    /**
     * Define el valor de la propiedad lifeCycleStatusCode.
     * 
     * @param value
     *     allowed object is
     *     {@link LifeCycleStatusCodeType }
     *     
     */
    public void setLifeCycleStatusCode(LifeCycleStatusCodeType value) {
        this.lifeCycleStatusCode = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Contract Subdivision.&#13;
     * 								Text&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Identifies a subdivision of a contract or&#13;
     * 								tender.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Contract Subdivision&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Text&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Text. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;"Installation", "Phase One", Support and&#13;
     * 								Maintenance"&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link ContractSubdivisionType }
     *     
     */
    public ContractSubdivisionType getContractSubdivision() {
        return contractSubdivision;
    }

    /**
     * Define el valor de la propiedad contractSubdivision.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractSubdivisionType }
     *     
     */
    public void setContractSubdivision(ContractSubdivisionType value) {
        this.contractSubdivision = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Note. Text&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Free-text note used for non-structured&#13;
     * 								information about the line in the specific Catalogue document&#13;
     * 								(intended to be human readable).&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
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
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Orderable_ Indicator.&#13;
     * 								Indicator&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Indicates whether the line is orderable (that&#13;
     * 								is, not just for information only).&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Orderable&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Indicator&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Indicator&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Indicator. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;TRUE means orderable, FALSE means not orderable&#13;
     * 							&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link OrderableIndicatorType }
     *     
     */
    public OrderableIndicatorType getOrderableIndicator() {
        return orderableIndicator;
    }

    /**
     * Define el valor de la propiedad orderableIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderableIndicatorType }
     *     
     */
    public void setOrderableIndicator(OrderableIndicatorType value) {
        this.orderableIndicator = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Orderable_ Unit. Text&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The unit that can be ordered.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Orderable&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Unit&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Text&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Text. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link OrderableUnitType }
     *     
     */
    public OrderableUnitType getOrderableUnit() {
        return orderableUnit;
    }

    /**
     * Define el valor de la propiedad orderableUnit.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderableUnitType }
     *     
     */
    public void setOrderableUnit(OrderableUnitType value) {
        this.orderableUnit = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Content Unit. Quantity&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The quantity of the order unit of measure of the&#13;
     * 								line.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Content Unit&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Quantity&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Quantity. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;If order unit measure identifier is "each", then&#13;
     * 								content unit quantity is "1".&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link ContentUnitQuantityType }
     *     
     */
    public ContentUnitQuantityType getContentUnitQuantity() {
        return contentUnitQuantity;
    }

    /**
     * Define el valor de la propiedad contentUnitQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentUnitQuantityType }
     *     
     */
    public void setContentUnitQuantity(ContentUnitQuantityType value) {
        this.contentUnitQuantity = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Order Quantity&#13;
     * 								Increment. Numeric&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The number of items that can set the order&#13;
     * 								quantity increments.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Order Quantity Increment&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Numeric&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Numeric. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link OrderQuantityIncrementNumericType }
     *     
     */
    public OrderQuantityIncrementNumericType getOrderQuantityIncrementNumeric() {
        return orderQuantityIncrementNumeric;
    }

    /**
     * Define el valor de la propiedad orderQuantityIncrementNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderQuantityIncrementNumericType }
     *     
     */
    public void setOrderQuantityIncrementNumeric(OrderQuantityIncrementNumericType value) {
        this.orderQuantityIncrementNumeric = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Minimum_ Order&#13;
     * 								Quantity. Quantity&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The minimum amount of items that can be ordered.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Minimum&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Order Quantity&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Quantity&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Quantity. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;"10 boxes"&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link MinimumOrderQuantityType }
     *     
     */
    public MinimumOrderQuantityType getMinimumOrderQuantity() {
        return minimumOrderQuantity;
    }

    /**
     * Define el valor de la propiedad minimumOrderQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link MinimumOrderQuantityType }
     *     
     */
    public void setMinimumOrderQuantity(MinimumOrderQuantityType value) {
        this.minimumOrderQuantity = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Maximum_ Order&#13;
     * 								Quantity. Quantity&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The maximum amount of items that can be ordered.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Maximum&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Order Quantity&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Quantity&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Quantity. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;"1 tonne"&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link MaximumOrderQuantityType }
     *     
     */
    public MaximumOrderQuantityType getMaximumOrderQuantity() {
        return maximumOrderQuantity;
    }

    /**
     * Define el valor de la propiedad maximumOrderQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumOrderQuantityType }
     *     
     */
    public void setMaximumOrderQuantity(MaximumOrderQuantityType value) {
        this.maximumOrderQuantity = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Warranty_ Information.&#13;
     * 								Text&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Information regarding the warranty for the good&#13;
     * 								or service. Warranty may be provided by any Party (can be&#13;
     * 								described in the assiciation to Warranty Party).&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Warranty&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Information&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Text&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Text. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;"Unless specified otherwise and in addition to any&#13;
     * 								rights the Customer may have under statute, Dell warrants to the&#13;
     * 								Customer that Dell branded Products (excluding third party&#13;
     * 								products and software), will be free from defects in materials&#13;
     * 								and workmanship affecting normal use for a period of one year&#13;
     * 								from invoice date ('Standard Warranty')."&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the warrantyInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the warrantyInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWarrantyInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WarrantyInformationType }
     * 
     * 
     */
    public List<WarrantyInformationType> getWarrantyInformation() {
        if (warrantyInformation == null) {
            warrantyInformation = new ArrayList<WarrantyInformationType>();
        }
        return this.warrantyInformation;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Pack Level Code. Code&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The level of packaging involved.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Pack Level Code&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Code&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Code. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Consumer Unit, Trading Unit&#13;
     * 							&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 							&lt;ccts:Examples&gt;"level 2", "Group 4"&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link PackLevelCodeType }
     *     
     */
    public PackLevelCodeType getPackLevelCode() {
        return packLevelCode;
    }

    /**
     * Define el valor de la propiedad packLevelCode.
     * 
     * @param value
     *     allowed object is
     *     {@link PackLevelCodeType }
     *     
     */
    public void setPackLevelCode(PackLevelCodeType value) {
        this.packLevelCode = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Contractor_ Customer&#13;
     * 								Party. Customer Party&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The Customer Party responsible for the contract&#13;
     * 								to which the Catalogue relates.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Contractor&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Customer Party&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Customer Party&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
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
    public CustomerPartyType getContractorCustomerParty() {
        return contractorCustomerParty;
    }

    /**
     * Define el valor de la propiedad contractorCustomerParty.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerPartyType }
     *     
     */
    public void setContractorCustomerParty(CustomerPartyType value) {
        this.contractorCustomerParty = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Seller_ Supplier Party.&#13;
     * 								Supplier Party&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association to Seller of the item.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Seller&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Supplier Party&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Supplier Party&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
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
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Warranty_ Party. Party&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The party responsible for the Warranty.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Warranty&lt;/ccts:PropertyTermQualifier&gt;&#13;
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
    public PartyType getWarrantyParty() {
        return warrantyParty;
    }

    /**
     * Define el valor de la propiedad warrantyParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setWarrantyParty(PartyType value) {
        this.warrantyParty = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Warranty Validity_&#13;
     * 								Period. Period&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The period for which the Warranty is valid.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Warranty Validity&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Period&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Period&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getWarrantyValidityPeriod() {
        return warrantyValidityPeriod;
    }

    /**
     * Define el valor de la propiedad warrantyValidityPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setWarrantyValidityPeriod(PeriodType value) {
        this.warrantyValidityPeriod = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Line Validity_ Period.&#13;
     * 								Period&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The period for which the Catalogue Line is&#13;
     * 								valid.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Line Validity&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Period&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Period&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getLineValidityPeriod() {
        return lineValidityPeriod;
    }

    /**
     * Define el valor de la propiedad lineValidityPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setLineValidityPeriod(PeriodType value) {
        this.lineValidityPeriod = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Item Comparison&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association to comparative details for this&#13;
     * 								Item.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Item Comparison&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Item Comparison&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the itemComparison property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemComparison property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemComparison().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemComparisonType }
     * 
     * 
     */
    public List<ItemComparisonType> getItemComparison() {
        if (itemComparison == null) {
            itemComparison = new ArrayList<ItemComparisonType>();
        }
        return this.itemComparison;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Component_ Related&#13;
     * 								Item. Related Item&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association that describes any catalogue&#13;
     * 								items that may be components of this Item.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Component&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Related Item&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Related Item&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the componentRelatedItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the componentRelatedItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComponentRelatedItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedItemType }
     * 
     * 
     */
    public List<RelatedItemType> getComponentRelatedItem() {
        if (componentRelatedItem == null) {
            componentRelatedItem = new ArrayList<RelatedItemType>();
        }
        return this.componentRelatedItem;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Accessory_ Related&#13;
     * 								Item. Related Item&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association that describes any catalogue&#13;
     * 								items that may be optional accessories to this Item.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Accessory&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Related Item&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Related Item&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the accessoryRelatedItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accessoryRelatedItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccessoryRelatedItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedItemType }
     * 
     * 
     */
    public List<RelatedItemType> getAccessoryRelatedItem() {
        if (accessoryRelatedItem == null) {
            accessoryRelatedItem = new ArrayList<RelatedItemType>();
        }
        return this.accessoryRelatedItem;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Required_ Related Item.&#13;
     * 								Related Item&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association that describes any catalogue&#13;
     * 								items that may be required for this Item.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Required&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Related Item&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Related Item&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the requiredRelatedItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requiredRelatedItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequiredRelatedItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedItemType }
     * 
     * 
     */
    public List<RelatedItemType> getRequiredRelatedItem() {
        if (requiredRelatedItem == null) {
            requiredRelatedItem = new ArrayList<RelatedItemType>();
        }
        return this.requiredRelatedItem;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Replacement_ Related&#13;
     * 								Item. Related Item&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association that describes any catalogue&#13;
     * 								items that may be replacements for this Item.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Replacement&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Related Item&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Related Item&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the replacementRelatedItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the replacementRelatedItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReplacementRelatedItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedItemType }
     * 
     * 
     */
    public List<RelatedItemType> getReplacementRelatedItem() {
        if (replacementRelatedItem == null) {
            replacementRelatedItem = new ArrayList<RelatedItemType>();
        }
        return this.replacementRelatedItem;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Complementary_ Related&#13;
     * 								Item. Related Item&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association that describes any catalogue&#13;
     * 								items that may complement this Item.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Complementary&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Related Item&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Related Item&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the complementaryRelatedItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the complementaryRelatedItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComplementaryRelatedItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedItemType }
     * 
     * 
     */
    public List<RelatedItemType> getComplementaryRelatedItem() {
        if (complementaryRelatedItem == null) {
            complementaryRelatedItem = new ArrayList<RelatedItemType>();
        }
        return this.complementaryRelatedItem;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Required_ Item Location&#13;
     * 								Quantity. Item Location Quantity&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association to the description of properties&#13;
     * 								related to locations and quantities of the Item.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Required&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Item Location Quantity&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Item Location Quantity&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the requiredItemLocationQuantity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requiredItemLocationQuantity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequiredItemLocationQuantity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemLocationQuantityType }
     * 
     * 
     */
    public List<ItemLocationQuantityType> getRequiredItemLocationQuantity() {
        if (requiredItemLocationQuantity == null) {
            requiredItemLocationQuantity = new ArrayList<ItemLocationQuantityType>();
        }
        return this.requiredItemLocationQuantity;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Document Reference&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association to Document Reference.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Document Reference&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Document Reference&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the documentReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getDocumentReference() {
        if (documentReference == null) {
            documentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.documentReference;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Catalogue Line. Item&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An association to the Item itself.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Catalogue Line&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Item&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Item&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link ItemType }
     *     
     */
    public ItemType getItem() {
        return item;
    }

    /**
     * Define el valor de la propiedad item.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemType }
     *     
     */
    public void setItem(ItemType value) {
        this.item = value;
    }

}
