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
 * 					&lt;ccts:DictionaryEntryName&gt;Goods Item. Details&#13;
 * 					&lt;/ccts:DictionaryEntryName&gt;&#13;
 * 					&lt;ccts:Definition&gt;A separately identifiable quantity of products of&#13;
 * 						a single product type.&lt;/ccts:Definition&gt;&#13;
 * 					&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
 * 				&lt;/ccts:Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Clase Java para GoodsItemType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="GoodsItemType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SequenceNumberID" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HazardousRiskIndicator" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DeclaredCustomsValueAmount" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DeclaredForCarriageValueAmount" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DeclaredStatisticsValueAmount" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FreeOnBoardValueAmount" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}InsuranceValueAmount" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValueAmount" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GrossWeightMeasure" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NetWeightMeasure" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NetNetWeightMeasure" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ChargeableWeightMeasure" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GrossVolumeMeasure" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NetVolumeMeasure" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Quantity" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PreferenceCriterionCode" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RequiredCustomsID" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CustomsStatusCode" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CustomsTariffQuantity" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CustomsImportClassifiedIndicator" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Item" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}GoodsItemContainer" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FreightAllowanceCharge" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}InvoiceLine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Temperature" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ContainedGoodsItem" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OriginAddress" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GoodsItemType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", propOrder = {
    "id",
    "sequenceNumberID",
    "description",
    "hazardousRiskIndicator",
    "declaredCustomsValueAmount",
    "declaredForCarriageValueAmount",
    "declaredStatisticsValueAmount",
    "freeOnBoardValueAmount",
    "insuranceValueAmount",
    "valueAmount",
    "grossWeightMeasure",
    "netWeightMeasure",
    "netNetWeightMeasure",
    "chargeableWeightMeasure",
    "grossVolumeMeasure",
    "netVolumeMeasure",
    "quantity",
    "preferenceCriterionCode",
    "requiredCustomsID",
    "customsStatusCode",
    "customsTariffQuantity",
    "customsImportClassifiedIndicator",
    "item",
    "goodsItemContainer",
    "freightAllowanceCharge",
    "invoiceLine",
    "temperature",
    "containedGoodsItem",
    "originAddress"
})
public class GoodsItemType {

    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "SequenceNumberID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SequenceNumberIDType sequenceNumberID;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "HazardousRiskIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HazardousRiskIndicatorType hazardousRiskIndicator;
    @XmlElement(name = "DeclaredCustomsValueAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DeclaredCustomsValueAmountType declaredCustomsValueAmount;
    @XmlElement(name = "DeclaredForCarriageValueAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DeclaredForCarriageValueAmountType declaredForCarriageValueAmount;
    @XmlElement(name = "DeclaredStatisticsValueAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DeclaredStatisticsValueAmountType declaredStatisticsValueAmount;
    @XmlElement(name = "FreeOnBoardValueAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FreeOnBoardValueAmountType freeOnBoardValueAmount;
    @XmlElement(name = "InsuranceValueAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected InsuranceValueAmountType insuranceValueAmount;
    @XmlElement(name = "ValueAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValueAmountType valueAmount;
    @XmlElement(name = "GrossWeightMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected GrossWeightMeasureType grossWeightMeasure;
    @XmlElement(name = "NetWeightMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NetWeightMeasureType netWeightMeasure;
    @XmlElement(name = "NetNetWeightMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NetNetWeightMeasureType netNetWeightMeasure;
    @XmlElement(name = "ChargeableWeightMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ChargeableWeightMeasureType chargeableWeightMeasure;
    @XmlElement(name = "GrossVolumeMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected GrossVolumeMeasureType grossVolumeMeasure;
    @XmlElement(name = "NetVolumeMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NetVolumeMeasureType netVolumeMeasure;
    @XmlElement(name = "Quantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected QuantityType2 quantity;
    @XmlElement(name = "PreferenceCriterionCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PreferenceCriterionCodeType preferenceCriterionCode;
    @XmlElement(name = "RequiredCustomsID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RequiredCustomsIDType requiredCustomsID;
    @XmlElement(name = "CustomsStatusCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CustomsStatusCodeType customsStatusCode;
    @XmlElement(name = "CustomsTariffQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CustomsTariffQuantityType customsTariffQuantity;
    @XmlElement(name = "CustomsImportClassifiedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CustomsImportClassifiedIndicatorType customsImportClassifiedIndicator;
    @XmlElement(name = "Item")
    protected List<ItemType> item;
    @XmlElement(name = "GoodsItemContainer")
    protected List<GoodsItemContainerType> goodsItemContainer;
    @XmlElement(name = "FreightAllowanceCharge")
    protected List<AllowanceChargeType> freightAllowanceCharge;
    @XmlElement(name = "InvoiceLine")
    protected List<InvoiceLineType> invoiceLine;
    @XmlElement(name = "Temperature")
    protected List<TemperatureType> temperature;
    @XmlElement(name = "ContainedGoodsItem")
    protected List<GoodsItemType> containedGoodsItem;
    @XmlElement(name = "OriginAddress")
    protected AddressType originAddress;

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Identifier&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;An identifier for the goods item.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
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
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Sequence Number. Identifier&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Sequence number differentiating a specific goods&#13;
     * 								item within a consignment.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Sequence Number&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Identifier&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Identifier. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Customs item number (WCO ID 021),&#13;
     * 								Sequence Position&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link SequenceNumberIDType }
     *     
     */
    public SequenceNumberIDType getSequenceNumberID() {
        return sequenceNumberID;
    }

    /**
     * Define el valor de la propiedad sequenceNumberID.
     * 
     * @param value
     *     allowed object is
     *     {@link SequenceNumberIDType }
     *     
     */
    public void setSequenceNumberID(SequenceNumberIDType value) {
        this.sequenceNumberID = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Description. Text&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Plain language description of a goods item&#13;
     * 								sufficient to identify it for customs, statistical, or transport&#13;
     * 								purposes.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Description&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Text&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Text. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Description of goods (WCO ID 137)&#13;
     * 							&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptionType }
     * 
     * 
     */
    public List<DescriptionType> getDescription() {
        if (description == null) {
            description = new ArrayList<DescriptionType>();
        }
        return this.description;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Hazardous Risk_ Indicator.&#13;
     * 								Indicator&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Indicates whether the goods item includes&#13;
     * 								hazardous items (dangerous goods).&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Hazardous Risk&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Indicator&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Indicator&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Indicator. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;default is negative&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link HazardousRiskIndicatorType }
     *     
     */
    public HazardousRiskIndicatorType getHazardousRiskIndicator() {
        return hazardousRiskIndicator;
    }

    /**
     * Define el valor de la propiedad hazardousRiskIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link HazardousRiskIndicatorType }
     *     
     */
    public void setHazardousRiskIndicator(HazardousRiskIndicatorType value) {
        this.hazardousRiskIndicator = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Declared Customs_ Value.&#13;
     * 								Amount&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Amount declared for Customs purposes of those&#13;
     * 								goods in a consignment which are subject to the same Customs&#13;
     * 								procedure and have the same tariff/statistical heading, country&#13;
     * 								information, and duty regime.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Declared Customs&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Value&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Amount&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Amount. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;For Customs Value (WCO ID 108)&#13;
     * 							&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link DeclaredCustomsValueAmountType }
     *     
     */
    public DeclaredCustomsValueAmountType getDeclaredCustomsValueAmount() {
        return declaredCustomsValueAmount;
    }

    /**
     * Define el valor de la propiedad declaredCustomsValueAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link DeclaredCustomsValueAmountType }
     *     
     */
    public void setDeclaredCustomsValueAmount(DeclaredCustomsValueAmountType value) {
        this.declaredCustomsValueAmount = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Declared For Carriage_&#13;
     * 								Value. Amount&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Value declared by the shipper or his agent&#13;
     * 								solely for the purpose of varying the carrier's level of&#13;
     * 								liability from that provided in the contract of carriage in case&#13;
     * 								of loss or damage to goods or delayed delivery.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Declared For Carriage&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Value&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Amount&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Amount. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Interest in delivery, declared&#13;
     * 								value for carriage&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link DeclaredForCarriageValueAmountType }
     *     
     */
    public DeclaredForCarriageValueAmountType getDeclaredForCarriageValueAmount() {
        return declaredForCarriageValueAmount;
    }

    /**
     * Define el valor de la propiedad declaredForCarriageValueAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link DeclaredForCarriageValueAmountType }
     *     
     */
    public void setDeclaredForCarriageValueAmount(DeclaredForCarriageValueAmountType value) {
        this.declaredForCarriageValueAmount = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Declared Statistics_ Value.&#13;
     * 								Amount&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Value declared for statistical purposes of those&#13;
     * 								goods in a consignment which have the same statistical heading.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Declared Statistics&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Value&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Amount&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Amount. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Statistical Value (WCO ID 114)&#13;
     * 							&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link DeclaredStatisticsValueAmountType }
     *     
     */
    public DeclaredStatisticsValueAmountType getDeclaredStatisticsValueAmount() {
        return declaredStatisticsValueAmount;
    }

    /**
     * Define el valor de la propiedad declaredStatisticsValueAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link DeclaredStatisticsValueAmountType }
     *     
     */
    public void setDeclaredStatisticsValueAmount(DeclaredStatisticsValueAmountType value) {
        this.declaredStatisticsValueAmount = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Free On Board_ Value.&#13;
     * 								Amount&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Monetary amount that has to be or has been paid&#13;
     * 								as calculated under the applicable trade delivery.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Free On Board&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Value&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Amount&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Amount. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;FOB Value&#13;
     * 							&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link FreeOnBoardValueAmountType }
     *     
     */
    public FreeOnBoardValueAmountType getFreeOnBoardValueAmount() {
        return freeOnBoardValueAmount;
    }

    /**
     * Define el valor de la propiedad freeOnBoardValueAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link FreeOnBoardValueAmountType }
     *     
     */
    public void setFreeOnBoardValueAmount(FreeOnBoardValueAmountType value) {
        this.freeOnBoardValueAmount = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Insurance_ Value. Amount&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The amount covered by an insurance for a&#13;
     * 								particular goods item.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Insurance&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Value&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Amount&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Amount. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Value Insured&#13;
     * 							&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link InsuranceValueAmountType }
     *     
     */
    public InsuranceValueAmountType getInsuranceValueAmount() {
        return insuranceValueAmount;
    }

    /**
     * Define el valor de la propiedad insuranceValueAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link InsuranceValueAmountType }
     *     
     */
    public void setInsuranceValueAmount(InsuranceValueAmountType value) {
        this.insuranceValueAmount = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Value. Amount&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Specifies the amount on which a duty, tax, or&#13;
     * 								fee will be assessed.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Value&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Amount&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Amount. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Duty/tax/fee assessment basis in&#13;
     * 								value (WCO ID 116)&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link ValueAmountType }
     *     
     */
    public ValueAmountType getValueAmount() {
        return valueAmount;
    }

    /**
     * Define el valor de la propiedad valueAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueAmountType }
     *     
     */
    public void setValueAmount(ValueAmountType value) {
        this.valueAmount = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Gross_ Weight. Measure&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Weight (mass) of goods, including packaging but&#13;
     * 								excluding the carrier's equipment.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Gross&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Weight&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Measure&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Measure. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Actual Gross Weight&#13;
     * 							&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link GrossWeightMeasureType }
     *     
     */
    public GrossWeightMeasureType getGrossWeightMeasure() {
        return grossWeightMeasure;
    }

    /**
     * Define el valor de la propiedad grossWeightMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link GrossWeightMeasureType }
     *     
     */
    public void setGrossWeightMeasure(GrossWeightMeasureType value) {
        this.grossWeightMeasure = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Net_ Weight. Measure&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Weight (mass) of goods item, excluding all&#13;
     * 								packing but including any packaging that normally goes with the&#13;
     * 								goods.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Net&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Weight&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Measure&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Measure. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link NetWeightMeasureType }
     *     
     */
    public NetWeightMeasureType getNetWeightMeasure() {
        return netWeightMeasure;
    }

    /**
     * Define el valor de la propiedad netWeightMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link NetWeightMeasureType }
     *     
     */
    public void setNetWeightMeasure(NetWeightMeasureType value) {
        this.netWeightMeasure = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Net Net_ Weight. Measure&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Weight (mass) of goods without any packaging.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Net Net&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Weight&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Measure&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Measure. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Customs Weight (WCO ID 128)&#13;
     * 							&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link NetNetWeightMeasureType }
     *     
     */
    public NetNetWeightMeasureType getNetNetWeightMeasure() {
        return netNetWeightMeasure;
    }

    /**
     * Define el valor de la propiedad netNetWeightMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link NetNetWeightMeasureType }
     *     
     */
    public void setNetNetWeightMeasure(NetNetWeightMeasureType value) {
        this.netNetWeightMeasure = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Chargeable_ Weight. Measure&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Gross weight (mass) on which a charge is to be&#13;
     * 								based.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Chargeable&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Weight&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Measure&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Measure. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link ChargeableWeightMeasureType }
     *     
     */
    public ChargeableWeightMeasureType getChargeableWeightMeasure() {
        return chargeableWeightMeasure;
    }

    /**
     * Define el valor de la propiedad chargeableWeightMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link ChargeableWeightMeasureType }
     *     
     */
    public void setChargeableWeightMeasure(ChargeableWeightMeasureType value) {
        this.chargeableWeightMeasure = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Gross_ Volume. Measure&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Measurement normally arrived at by multiplying&#13;
     * 								the maximum length, width, and height of the goods item.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Gross&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Volume&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Measure&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Measure. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Volume, Gross Measurement Cube&#13;
     * 								(GMC), Cube (WCO ID 134)&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link GrossVolumeMeasureType }
     *     
     */
    public GrossVolumeMeasureType getGrossVolumeMeasure() {
        return grossVolumeMeasure;
    }

    /**
     * Define el valor de la propiedad grossVolumeMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link GrossVolumeMeasureType }
     *     
     */
    public void setGrossVolumeMeasure(GrossVolumeMeasureType value) {
        this.grossVolumeMeasure = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Net_ Volume. Measure&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;The volume contained by a goods item, excluding&#13;
     * 								the volume of any packaging material.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Net&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Volume&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Measure&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Measure. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link NetVolumeMeasureType }
     *     
     */
    public NetVolumeMeasureType getNetVolumeMeasure() {
        return netVolumeMeasure;
    }

    /**
     * Define el valor de la propiedad netVolumeMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link NetVolumeMeasureType }
     *     
     */
    public void setNetVolumeMeasure(NetVolumeMeasureType value) {
        this.netVolumeMeasure = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Quantity&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Number of goods items.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Quantity&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Quantity&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Quantity. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link QuantityType2 }
     *     
     */
    public QuantityType2 getQuantity() {
        return quantity;
    }

    /**
     * Define el valor de la propiedad quantity.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType2 }
     *     
     */
    public void setQuantity(QuantityType2 value) {
        this.quantity = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Preference Criterion. Code&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Specifies the treatment preference for this good&#13;
     * 								according to international trading agreements.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Preference Criterion&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Code&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Code. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:Examples&gt;"Preference Criterion" is used in the following&#13;
     * 								manner in the paper CO of another country (e.g.):&#13;
     * 								"A" - The good&#13;
     * 								is "wholly obtained or produced entirely" in the&#13;
     * 								territory of one&#13;
     * 								or more of the NAFTA countries as reference in&#13;
     * 								Article 415. Note:&#13;
     * 								The purchase of a good in the territory does&#13;
     * 								not necessarily&#13;
     * 								render it "wholly obtained or produced". If the&#13;
     * 								good is an&#13;
     * 								agricultural good, see also criterion F and Annex&#13;
     * 								703.2.&#13;
     * 								(Reference: Article 401(a), 415).&#13;
     * 								"B" - ..&#13;
     * 								"C" - ..&#13;
     * 								"D" - ..&#13;
     * 								"E"&#13;
     * 								- ..&#13;
     * 								"F" - The good is an originating agricultural good under&#13;
     * 								preference&#13;
     * 								criterion A,B, or C above and is not subjected to&#13;
     * 								quantitative&#13;
     * 								restriction in the importing NAFTA country&#13;
     * 								because..&#13;
     * 								Thus, the column "Preference Criterion" will indicate&#13;
     * 								either A, B,&#13;
     * 								C,..&lt;/ccts:Examples&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link PreferenceCriterionCodeType }
     *     
     */
    public PreferenceCriterionCodeType getPreferenceCriterionCode() {
        return preferenceCriterionCode;
    }

    /**
     * Define el valor de la propiedad preferenceCriterionCode.
     * 
     * @param value
     *     allowed object is
     *     {@link PreferenceCriterionCodeType }
     *     
     */
    public void setPreferenceCriterionCode(PreferenceCriterionCodeType value) {
        this.preferenceCriterionCode = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Required_ Customs&#13;
     * 								Identifier. Identifier&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Additional tariff codes required to specify a&#13;
     * 								type of goods for Customs, transport, statistical, or other&#13;
     * 								regulatory purposes.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Required&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Customs Identifier&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Identifier&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Identifier. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Tariff code extensions (WCO ID&#13;
     * 								255)&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link RequiredCustomsIDType }
     *     
     */
    public RequiredCustomsIDType getRequiredCustomsID() {
        return requiredCustomsID;
    }

    /**
     * Define el valor de la propiedad requiredCustomsID.
     * 
     * @param value
     *     allowed object is
     *     {@link RequiredCustomsIDType }
     *     
     */
    public void setRequiredCustomsID(RequiredCustomsIDType value) {
        this.requiredCustomsID = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Customs Status Code. Code&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Status of goods as identified by customs for&#13;
     * 								regulation purposes.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Customs Status Code&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Code&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Code. Type&lt;/ccts:DataType&gt;&#13;
     * 							&lt;ccts:AlternativeBusinessTerms&gt;Customs status of goods (WCO ID&#13;
     * 								094)&lt;/ccts:AlternativeBusinessTerms&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link CustomsStatusCodeType }
     *     
     */
    public CustomsStatusCodeType getCustomsStatusCode() {
        return customsStatusCode;
    }

    /**
     * Define el valor de la propiedad customsStatusCode.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomsStatusCodeType }
     *     
     */
    public void setCustomsStatusCode(CustomsStatusCodeType value) {
        this.customsStatusCode = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Customs Tariff Quantity.&#13;
     * 								Quantity&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Quantity of the goods in the unit as required by&#13;
     * 								Customs for tariff, statistical, or fiscal purposes.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Customs Tariff Quantity&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Quantity&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Quantity. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link CustomsTariffQuantityType }
     *     
     */
    public CustomsTariffQuantityType getCustomsTariffQuantity() {
        return customsTariffQuantity;
    }

    /**
     * Define el valor de la propiedad customsTariffQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomsTariffQuantityType }
     *     
     */
    public void setCustomsTariffQuantity(CustomsTariffQuantityType value) {
        this.customsTariffQuantity = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;BBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Customs Import_ Classified&#13;
     * 								Indicator. Indicator&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Indicates whether the goods have been customs&#13;
     * 								classified for import.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Customs Import&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Classified Indicator&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:RepresentationTerm&gt;Indicator&lt;/ccts:RepresentationTerm&gt;&#13;
     * 							&lt;ccts:DataType&gt;Indicator. Type&lt;/ccts:DataType&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link CustomsImportClassifiedIndicatorType }
     *     
     */
    public CustomsImportClassifiedIndicatorType getCustomsImportClassifiedIndicator() {
        return customsImportClassifiedIndicator;
    }

    /**
     * Define el valor de la propiedad customsImportClassifiedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomsImportClassifiedIndicatorType }
     *     
     */
    public void setCustomsImportClassifiedIndicator(CustomsImportClassifiedIndicatorType value) {
        this.customsImportClassifiedIndicator = value;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Item&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Association to a description of the good or&#13;
     * 								service.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Item&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Item&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemType }
     * 
     * 
     */
    public List<ItemType> getItem() {
        if (item == null) {
            item = new ArrayList<ItemType>();
        }
        return this.item;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Goods Item Container&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Association to describe the transporting of a&#13;
     * 								goods item in a unit of transport equipment (e.g., container).&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Goods Item Container&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Goods Item Container&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the goodsItemContainer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the goodsItemContainer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGoodsItemContainer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GoodsItemContainerType }
     * 
     * 
     */
    public List<GoodsItemContainerType> getGoodsItemContainer() {
        if (goodsItemContainer == null) {
            goodsItemContainer = new ArrayList<GoodsItemContainerType>();
        }
        return this.goodsItemContainer;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Freight_ Allowance Charge.&#13;
     * 								Allowance Charge&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Costs incurred by the shipper in moving goods,&#13;
     * 								by whatever means, from one place to another under the terms of&#13;
     * 								the contract of carriage. In addition to transport costs, this&#13;
     * 								may include such elements as packing, documentation, loading,&#13;
     * 								unloading, and insurance (to the extent that they relate to the&#13;
     * 								freight costs).&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Freight&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Allowance Charge&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Allowance Charge&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the freightAllowanceCharge property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freightAllowanceCharge property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreightAllowanceCharge().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AllowanceChargeType }
     * 
     * 
     */
    public List<AllowanceChargeType> getFreightAllowanceCharge() {
        if (freightAllowanceCharge == null) {
            freightAllowanceCharge = new ArrayList<AllowanceChargeType>();
        }
        return this.freightAllowanceCharge;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Invoice Line&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Association to information directly relating to&#13;
     * 								a line item of an invoice.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Invoice Line&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Invoice Line&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the invoiceLine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoiceLine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoiceLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvoiceLineType }
     * 
     * 
     */
    public List<InvoiceLineType> getInvoiceLine() {
        if (invoiceLine == null) {
            invoiceLine = new ArrayList<InvoiceLineType>();
        }
        return this.invoiceLine;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Temperature&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Any temperatures associated with the goods.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Temperature&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Temperature&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the temperature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the temperature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTemperature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TemperatureType }
     * 
     * 
     */
    public List<TemperatureType> getTemperature() {
        if (temperature == null) {
            temperature = new ArrayList<TemperatureType>();
        }
        return this.temperature;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Contained_ Goods Item.&#13;
     * 								Goods Item&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Associates with any other goods items contained&#13;
     * 								in this goods item.&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.n&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Contained&#13;
     * 							&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Goods Item&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Goods Item&#13;
     * 							&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					Gets the value of the containedGoodsItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the containedGoodsItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContainedGoodsItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GoodsItemType }
     * 
     * 
     */
    public List<GoodsItemType> getContainedGoodsItem() {
        if (containedGoodsItem == null) {
            containedGoodsItem = new ArrayList<GoodsItemType>();
        }
        return this.containedGoodsItem;
    }

    /**
     * 
     * 						
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:custom="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:customcac="http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0" xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
     * 							&lt;ccts:ComponentType&gt;ASBIE&lt;/ccts:ComponentType&gt;&#13;
     * 							&lt;ccts:DictionaryEntryName&gt;Goods Item. Origin_ Address. Address&#13;
     * 							&lt;/ccts:DictionaryEntryName&gt;&#13;
     * 							&lt;ccts:Definition&gt;Region in which the goods have been produced or&#13;
     * 								manufactured, according to criteria laid down for the purposes&#13;
     * 								of application of the Customs tariff, or quantitative&#13;
     * 								restrictions, or any other measure related to trade.&#13;
     * 							&lt;/ccts:Definition&gt;&#13;
     * 							&lt;ccts:Cardinality&gt;0.1&lt;/ccts:Cardinality&gt;&#13;
     * 							&lt;ccts:ObjectClass&gt;Goods Item&lt;/ccts:ObjectClass&gt;&#13;
     * 							&lt;ccts:PropertyTermQualifier&gt;Origin&lt;/ccts:PropertyTermQualifier&gt;&#13;
     * 							&lt;ccts:PropertyTerm&gt;Address&lt;/ccts:PropertyTerm&gt;&#13;
     * 							&lt;ccts:AssociatedObjectClass&gt;Address&lt;/ccts:AssociatedObjectClass&gt;&#13;
     * 						&lt;/ccts:Component&gt;
     * </pre>
     * 
     * 					
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getOriginAddress() {
        return originAddress;
    }

    /**
     * Define el valor de la propiedad originAddress.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setOriginAddress(AddressType value) {
        this.originAddress = value;
    }

}
