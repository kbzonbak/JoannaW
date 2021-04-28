//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:39:41 PM CLT 
//


package bbr.b2b.logistic.xml.purchaseorder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *         
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Component xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2" xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&#13;
 *           &lt;ccts:DictionaryEntryName&gt;Payment Means_ Code. Type&lt;/ccts:DictionaryEntryName&gt;&#13;
 *           &lt;ccts:Version&gt;2.0&lt;/ccts:Version&gt;&#13;
 *           &lt;ccts:Definition&gt;The set of valid means of paying the debt incurred.&lt;/ccts:Definition&gt;&#13;
 *           &lt;ccts:RepresentationTerm&gt;Code&lt;/ccts:RepresentationTerm&gt;&#13;
 *           &lt;ccts:QualifierTerm&gt;Payment Means&lt;/ccts:QualifierTerm&gt;&#13;
 *           &lt;ccts:UniqueID/&gt;&#13;
 *           &lt;ccts:UsageRule&gt;Used under the terms of the UNECE  policy stated at http://www.unece.org/ece_legal.htm.&lt;/ccts:UsageRule&gt;&#13;
 *         &lt;/ccts:Component&gt;
 * </pre>
 * 
 *       
 * 
 * <p>Clase Java para PaymentMeansCodeType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="PaymentMeansCodeType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;restriction base="&lt;urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2&gt;CodeType"&gt;
 *       &lt;attribute name="listID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" default="UN/ECE 4461" /&gt;
 *       &lt;attribute name="listAgencyID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" default="6" /&gt;
 *       &lt;attribute name="listAgencyName" type="{http://www.w3.org/2001/XMLSchema}string" default="United Nations Economic Commission for Europe" /&gt;
 *       &lt;attribute name="listName" type="{http://www.w3.org/2001/XMLSchema}string" default="Payment Means" /&gt;
 *       &lt;attribute name="listVersionID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" default="D03A" /&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="languageID" type="{http://www.w3.org/2001/XMLSchema}language" default="en" /&gt;
 *       &lt;attribute name="listURI" type="{http://www.w3.org/2001/XMLSchema}anyURI" default="http://docs.oasis-open.org/ubl/os-ubl-2.0/cl/gc/default/PaymentMeansCode-2.0.gc" /&gt;
 *       &lt;attribute name="listSchemeURI" type="{http://www.w3.org/2001/XMLSchema}anyURI" default="urn:oasis:names:specification:ubl:codelist:gc:PaymentMeansCode-2.0" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentMeansCodeType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2")
@XmlSeeAlso({
    PaymentMeansCodeType2 .class
})
public class PaymentMeansCodeType
    extends CodeType
{


}
