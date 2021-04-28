//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.08.28 a las 06:38:56 PM CLT 
//


package bbr.b2b.logistic.xml.asnunimarc1.xsd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:schemas-microsoft-com:office:office}DocumentProperties"/&gt;
 *         &lt;element ref="{urn:schemas-microsoft-com:office:excel}ExcelWorkbook"/&gt;
 *         &lt;element name="Styles"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Style" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Alignment" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Vertical use="required""/&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Borders" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence minOccurs="0"&gt;
 *                                       &lt;element name="Border" maxOccurs="unbounded"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Position use="required""/&gt;
 *                                               &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}LineStyle use="required""/&gt;
 *                                               &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Weight use="required""/&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Font"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}FontName"/&gt;
 *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Family"/&gt;
 *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Size"/&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Interior" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Color"/&gt;
 *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Pattern"/&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="NumberFormat" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Format"/&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Protection" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}ID use="required""/&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Name"/&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Worksheet" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Table"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence minOccurs="0"&gt;
 *                             &lt;element name="Column" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}StyleID use="required""/&gt;
 *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Width use="required""/&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Row" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence minOccurs="0"&gt;
 *                                       &lt;element name="Cell" maxOccurs="unbounded"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence minOccurs="0"&gt;
 *                                                 &lt;element name="Data"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;simpleContent&gt;
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                                                         &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Type use="required""/&gt;
 *                                                       &lt;/extension&gt;
 *                                                     &lt;/simpleContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                               &lt;/sequence&gt;
 *                                               &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}StyleID use="required""/&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}FullColumns use="required""/&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}FullRows use="required""/&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}StyleID"/&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}DefaultColumnWidth use="required""/&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}DefaultRowHeight"/&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element ref="{urn:schemas-microsoft-com:office:excel}WorksheetOptions"/&gt;
 *                   &lt;element ref="{urn:schemas-microsoft-com:office:excel}PageBreaks" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Name use="required""/&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "documentProperties",
    "excelWorkbook",
    "styles",
    "worksheet"
})
@XmlRootElement(name = "Workbook", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
public class Workbook {

    @XmlElement(name = "DocumentProperties", namespace = "urn:schemas-microsoft-com:office:office", required = true)
    protected DocumentProperties documentProperties;
    @XmlElement(name = "ExcelWorkbook", required = true)
    protected ExcelWorkbook excelWorkbook;
    @XmlElement(name = "Styles", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
    protected Workbook.Styles styles;
    @XmlElement(name = "Worksheet", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
    protected List<Workbook.Worksheet> worksheet;

    /**
     * Obtiene el valor de la propiedad documentProperties.
     * 
     * @return
     *     possible object is
     *     {@link DocumentProperties }
     *     
     */
    public DocumentProperties getDocumentProperties() {
        return documentProperties;
    }

    /**
     * Define el valor de la propiedad documentProperties.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentProperties }
     *     
     */
    public void setDocumentProperties(DocumentProperties value) {
        this.documentProperties = value;
    }

    /**
     * Obtiene el valor de la propiedad excelWorkbook.
     * 
     * @return
     *     possible object is
     *     {@link ExcelWorkbook }
     *     
     */
    public ExcelWorkbook getExcelWorkbook() {
        return excelWorkbook;
    }

    /**
     * Define el valor de la propiedad excelWorkbook.
     * 
     * @param value
     *     allowed object is
     *     {@link ExcelWorkbook }
     *     
     */
    public void setExcelWorkbook(ExcelWorkbook value) {
        this.excelWorkbook = value;
    }

    /**
     * Obtiene el valor de la propiedad styles.
     * 
     * @return
     *     possible object is
     *     {@link Workbook.Styles }
     *     
     */
    public Workbook.Styles getStyles() {
        return styles;
    }

    /**
     * Define el valor de la propiedad styles.
     * 
     * @param value
     *     allowed object is
     *     {@link Workbook.Styles }
     *     
     */
    public void setStyles(Workbook.Styles value) {
        this.styles = value;
    }

    /**
     * Gets the value of the worksheet property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the worksheet property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWorksheet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Workbook.Worksheet }
     * 
     * 
     */
    public List<Workbook.Worksheet> getWorksheet() {
        if (worksheet == null) {
            worksheet = new ArrayList<Workbook.Worksheet>();
        }
        return this.worksheet;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Style" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Alignment" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Vertical use="required""/&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Borders" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence minOccurs="0"&gt;
     *                             &lt;element name="Border" maxOccurs="unbounded"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Position use="required""/&gt;
     *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}LineStyle use="required""/&gt;
     *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Weight use="required""/&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Font"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}FontName"/&gt;
     *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Family"/&gt;
     *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Size"/&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Interior" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Color"/&gt;
     *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Pattern"/&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="NumberFormat" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Format"/&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Protection" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}ID use="required""/&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Name"/&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "style"
    })
    public static class Styles {

        @XmlElement(name = "Style", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
        protected List<Workbook.Styles.Style> style;

        /**
         * Gets the value of the style property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the style property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getStyle().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Workbook.Styles.Style }
         * 
         * 
         */
        public List<Workbook.Styles.Style> getStyle() {
            if (style == null) {
                style = new ArrayList<Workbook.Styles.Style>();
            }
            return this.style;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="Alignment" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Vertical use="required""/&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Borders" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence minOccurs="0"&gt;
         *                   &lt;element name="Border" maxOccurs="unbounded"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Position use="required""/&gt;
         *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}LineStyle use="required""/&gt;
         *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Weight use="required""/&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Font"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}FontName"/&gt;
         *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Family"/&gt;
         *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Size"/&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Interior" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Color"/&gt;
         *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Pattern"/&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="NumberFormat" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Format"/&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Protection" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}ID use="required""/&gt;
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Name"/&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "alignment",
            "borders",
            "font",
            "interior",
            "numberFormat",
            "protection"
        })
        public static class Style {

            @XmlElement(name = "Alignment", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
            protected Workbook.Styles.Style.Alignment alignment;
            @XmlElement(name = "Borders", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
            protected Workbook.Styles.Style.Borders borders;
            @XmlElement(name = "Font", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
            protected Workbook.Styles.Style.Font font;
            @XmlElement(name = "Interior", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
            protected Workbook.Styles.Style.Interior interior;
            @XmlElement(name = "NumberFormat", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
            protected Workbook.Styles.Style.NumberFormat numberFormat;
            @XmlElement(name = "Protection", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
            protected Object protection;
            @XmlAttribute(name = "ID", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
            protected String id;
            @XmlAttribute(name = "Name", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
            protected String name;

            /**
             * Obtiene el valor de la propiedad alignment.
             * 
             * @return
             *     possible object is
             *     {@link Workbook.Styles.Style.Alignment }
             *     
             */
            public Workbook.Styles.Style.Alignment getAlignment() {
                return alignment;
            }

            /**
             * Define el valor de la propiedad alignment.
             * 
             * @param value
             *     allowed object is
             *     {@link Workbook.Styles.Style.Alignment }
             *     
             */
            public void setAlignment(Workbook.Styles.Style.Alignment value) {
                this.alignment = value;
            }

            /**
             * Obtiene el valor de la propiedad borders.
             * 
             * @return
             *     possible object is
             *     {@link Workbook.Styles.Style.Borders }
             *     
             */
            public Workbook.Styles.Style.Borders getBorders() {
                return borders;
            }

            /**
             * Define el valor de la propiedad borders.
             * 
             * @param value
             *     allowed object is
             *     {@link Workbook.Styles.Style.Borders }
             *     
             */
            public void setBorders(Workbook.Styles.Style.Borders value) {
                this.borders = value;
            }

            /**
             * Obtiene el valor de la propiedad font.
             * 
             * @return
             *     possible object is
             *     {@link Workbook.Styles.Style.Font }
             *     
             */
            public Workbook.Styles.Style.Font getFont() {
                return font;
            }

            /**
             * Define el valor de la propiedad font.
             * 
             * @param value
             *     allowed object is
             *     {@link Workbook.Styles.Style.Font }
             *     
             */
            public void setFont(Workbook.Styles.Style.Font value) {
                this.font = value;
            }

            /**
             * Obtiene el valor de la propiedad interior.
             * 
             * @return
             *     possible object is
             *     {@link Workbook.Styles.Style.Interior }
             *     
             */
            public Workbook.Styles.Style.Interior getInterior() {
                return interior;
            }

            /**
             * Define el valor de la propiedad interior.
             * 
             * @param value
             *     allowed object is
             *     {@link Workbook.Styles.Style.Interior }
             *     
             */
            public void setInterior(Workbook.Styles.Style.Interior value) {
                this.interior = value;
            }

            /**
             * Obtiene el valor de la propiedad numberFormat.
             * 
             * @return
             *     possible object is
             *     {@link Workbook.Styles.Style.NumberFormat }
             *     
             */
            public Workbook.Styles.Style.NumberFormat getNumberFormat() {
                return numberFormat;
            }

            /**
             * Define el valor de la propiedad numberFormat.
             * 
             * @param value
             *     allowed object is
             *     {@link Workbook.Styles.Style.NumberFormat }
             *     
             */
            public void setNumberFormat(Workbook.Styles.Style.NumberFormat value) {
                this.numberFormat = value;
            }

            /**
             * Obtiene el valor de la propiedad protection.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getProtection() {
                return protection;
            }

            /**
             * Define el valor de la propiedad protection.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setProtection(Object value) {
                this.protection = value;
            }

            /**
             * Obtiene el valor de la propiedad id.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getID() {
                return id;
            }

            /**
             * Define el valor de la propiedad id.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setID(String value) {
                this.id = value;
            }

            /**
             * Obtiene el valor de la propiedad name.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Define el valor de la propiedad name.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Vertical use="required""/&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Alignment {

                @XmlAttribute(name = "Vertical", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
                protected String vertical;

                /**
                 * Obtiene el valor de la propiedad vertical.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVertical() {
                    return vertical;
                }

                /**
                 * Define el valor de la propiedad vertical.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVertical(String value) {
                    this.vertical = value;
                }

            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence minOccurs="0"&gt;
             *         &lt;element name="Border" maxOccurs="unbounded"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Position use="required""/&gt;
             *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}LineStyle use="required""/&gt;
             *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Weight use="required""/&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "border"
            })
            public static class Borders {

                @XmlElement(name = "Border", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
                protected List<Workbook.Styles.Style.Borders.Border> border;

                /**
                 * Gets the value of the border property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the border property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getBorder().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Workbook.Styles.Style.Borders.Border }
                 * 
                 * 
                 */
                public List<Workbook.Styles.Style.Borders.Border> getBorder() {
                    if (border == null) {
                        border = new ArrayList<Workbook.Styles.Style.Borders.Border>();
                    }
                    return this.border;
                }


                /**
                 * <p>Clase Java para anonymous complex type.
                 * 
                 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                 * 
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Position use="required""/&gt;
                 *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}LineStyle use="required""/&gt;
                 *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Weight use="required""/&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class Border {

                    @XmlAttribute(name = "Position", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
                    protected String position;
                    @XmlAttribute(name = "LineStyle", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
                    protected String lineStyle;
                    @XmlAttribute(name = "Weight", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
                    @XmlSchemaType(name = "unsignedByte")
                    protected short weight;

                    /**
                     * Obtiene el valor de la propiedad position.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPosition() {
                        return position;
                    }

                    /**
                     * Define el valor de la propiedad position.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPosition(String value) {
                        this.position = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad lineStyle.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getLineStyle() {
                        return lineStyle;
                    }

                    /**
                     * Define el valor de la propiedad lineStyle.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setLineStyle(String value) {
                        this.lineStyle = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad weight.
                     * 
                     */
                    public short getWeight() {
                        return weight;
                    }

                    /**
                     * Define el valor de la propiedad weight.
                     * 
                     */
                    public void setWeight(short value) {
                        this.weight = value;
                    }

                }

            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}FontName"/&gt;
             *       &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Family"/&gt;
             *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Size"/&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Font {

                @XmlAttribute(name = "FontName", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
                protected String fontName;
                @XmlAttribute(name = "Family", namespace = "urn:schemas-microsoft-com:office:excel")
                protected String family;
                @XmlAttribute(name = "Size", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
                protected BigDecimal size;

                /**
                 * Obtiene el valor de la propiedad fontName.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getFontName() {
                    return fontName;
                }

                /**
                 * Define el valor de la propiedad fontName.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setFontName(String value) {
                    this.fontName = value;
                }

                /**
                 * Obtiene el valor de la propiedad family.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getFamily() {
                    return family;
                }

                /**
                 * Define el valor de la propiedad family.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setFamily(String value) {
                    this.family = value;
                }

                /**
                 * Obtiene el valor de la propiedad size.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getSize() {
                    return size;
                }

                /**
                 * Define el valor de la propiedad size.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setSize(BigDecimal value) {
                    this.size = value;
                }

            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Color"/&gt;
             *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Pattern"/&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Interior {

                @XmlAttribute(name = "Color", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
                protected String color;
                @XmlAttribute(name = "Pattern", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
                protected String pattern;

                /**
                 * Obtiene el valor de la propiedad color.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getColor() {
                    return color;
                }

                /**
                 * Define el valor de la propiedad color.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setColor(String value) {
                    this.color = value;
                }

                /**
                 * Obtiene el valor de la propiedad pattern.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPattern() {
                    return pattern;
                }

                /**
                 * Define el valor de la propiedad pattern.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPattern(String value) {
                    this.pattern = value;
                }

            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Format"/&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class NumberFormat {

                @XmlAttribute(name = "Format", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
                @XmlSchemaType(name = "unsignedByte")
                protected Short format;

                /**
                 * Obtiene el valor de la propiedad format.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Short }
                 *     
                 */
                public Short getFormat() {
                    return format;
                }

                /**
                 * Define el valor de la propiedad format.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Short }
                 *     
                 */
                public void setFormat(Short value) {
                    this.format = value;
                }

            }

        }

    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Table"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence minOccurs="0"&gt;
     *                   &lt;element name="Column" maxOccurs="unbounded"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}StyleID use="required""/&gt;
     *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Width use="required""/&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Row" maxOccurs="unbounded"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence minOccurs="0"&gt;
     *                             &lt;element name="Cell" maxOccurs="unbounded"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence minOccurs="0"&gt;
     *                                       &lt;element name="Data"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;simpleContent&gt;
     *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *                                               &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Type use="required""/&gt;
     *                                             &lt;/extension&gt;
     *                                           &lt;/simpleContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                     &lt;/sequence&gt;
     *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}StyleID use="required""/&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}FullColumns use="required""/&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}FullRows use="required""/&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}StyleID"/&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}DefaultColumnWidth use="required""/&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}DefaultRowHeight"/&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element ref="{urn:schemas-microsoft-com:office:excel}WorksheetOptions"/&gt;
     *         &lt;element ref="{urn:schemas-microsoft-com:office:excel}PageBreaks" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Name use="required""/&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "table",
        "worksheetOptions",
        "pageBreaks"
    })
    public static class Worksheet {

        @XmlElement(name = "Table", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
        protected Workbook.Worksheet.Table table;
        @XmlElement(name = "WorksheetOptions", required = true)
        protected WorksheetOptions worksheetOptions;
        @XmlElement(name = "PageBreaks")
        protected PageBreaks pageBreaks;
        @XmlAttribute(name = "Name", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
        protected String name;

        /**
         * Obtiene el valor de la propiedad table.
         * 
         * @return
         *     possible object is
         *     {@link Workbook.Worksheet.Table }
         *     
         */
        public Workbook.Worksheet.Table getTable() {
            return table;
        }

        /**
         * Define el valor de la propiedad table.
         * 
         * @param value
         *     allowed object is
         *     {@link Workbook.Worksheet.Table }
         *     
         */
        public void setTable(Workbook.Worksheet.Table value) {
            this.table = value;
        }

        /**
         * Obtiene el valor de la propiedad worksheetOptions.
         * 
         * @return
         *     possible object is
         *     {@link WorksheetOptions }
         *     
         */
        public WorksheetOptions getWorksheetOptions() {
            return worksheetOptions;
        }

        /**
         * Define el valor de la propiedad worksheetOptions.
         * 
         * @param value
         *     allowed object is
         *     {@link WorksheetOptions }
         *     
         */
        public void setWorksheetOptions(WorksheetOptions value) {
            this.worksheetOptions = value;
        }

        /**
         * Obtiene el valor de la propiedad pageBreaks.
         * 
         * @return
         *     possible object is
         *     {@link PageBreaks }
         *     
         */
        public PageBreaks getPageBreaks() {
            return pageBreaks;
        }

        /**
         * Define el valor de la propiedad pageBreaks.
         * 
         * @param value
         *     allowed object is
         *     {@link PageBreaks }
         *     
         */
        public void setPageBreaks(PageBreaks value) {
            this.pageBreaks = value;
        }

        /**
         * Obtiene el valor de la propiedad name.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Define el valor de la propiedad name.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence minOccurs="0"&gt;
         *         &lt;element name="Column" maxOccurs="unbounded"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}StyleID use="required""/&gt;
         *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Width use="required""/&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Row" maxOccurs="unbounded"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence minOccurs="0"&gt;
         *                   &lt;element name="Cell" maxOccurs="unbounded"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence minOccurs="0"&gt;
         *                             &lt;element name="Data"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;simpleContent&gt;
         *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
         *                                     &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Type use="required""/&gt;
         *                                   &lt;/extension&gt;
         *                                 &lt;/simpleContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                           &lt;/sequence&gt;
         *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}StyleID use="required""/&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}FullColumns use="required""/&gt;
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}FullRows use="required""/&gt;
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}StyleID"/&gt;
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}DefaultColumnWidth use="required""/&gt;
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}DefaultRowHeight"/&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "column",
            "row"
        })
        public static class Table {

            @XmlElement(name = "Column", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
            protected List<Workbook.Worksheet.Table.Column> column;
            @XmlElement(name = "Row", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
            protected List<Workbook.Worksheet.Table.Row> row;
            @XmlAttribute(name = "FullColumns", namespace = "urn:schemas-microsoft-com:office:excel", required = true)
            @XmlSchemaType(name = "unsignedByte")
            protected short fullColumns;
            @XmlAttribute(name = "FullRows", namespace = "urn:schemas-microsoft-com:office:excel", required = true)
            @XmlSchemaType(name = "unsignedByte")
            protected short fullRows;
            @XmlAttribute(name = "StyleID", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
            protected String styleID;
            @XmlAttribute(name = "DefaultColumnWidth", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
            @XmlSchemaType(name = "unsignedByte")
            protected short defaultColumnWidth;
            @XmlAttribute(name = "DefaultRowHeight", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
            protected BigDecimal defaultRowHeight;

            /**
             * Gets the value of the column property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the column property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getColumn().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Workbook.Worksheet.Table.Column }
             * 
             * 
             */
            public List<Workbook.Worksheet.Table.Column> getColumn() {
                if (column == null) {
                    column = new ArrayList<Workbook.Worksheet.Table.Column>();
                }
                return this.column;
            }

            /**
             * Gets the value of the row property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the row property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getRow().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Workbook.Worksheet.Table.Row }
             * 
             * 
             */
            public List<Workbook.Worksheet.Table.Row> getRow() {
                if (row == null) {
                    row = new ArrayList<Workbook.Worksheet.Table.Row>();
                }
                return this.row;
            }

            /**
             * Obtiene el valor de la propiedad fullColumns.
             * 
             */
            public short getFullColumns() {
                return fullColumns;
            }

            /**
             * Define el valor de la propiedad fullColumns.
             * 
             */
            public void setFullColumns(short value) {
                this.fullColumns = value;
            }

            /**
             * Obtiene el valor de la propiedad fullRows.
             * 
             */
            public short getFullRows() {
                return fullRows;
            }

            /**
             * Define el valor de la propiedad fullRows.
             * 
             */
            public void setFullRows(short value) {
                this.fullRows = value;
            }

            /**
             * Obtiene el valor de la propiedad styleID.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStyleID() {
                return styleID;
            }

            /**
             * Define el valor de la propiedad styleID.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStyleID(String value) {
                this.styleID = value;
            }

            /**
             * Obtiene el valor de la propiedad defaultColumnWidth.
             * 
             */
            public short getDefaultColumnWidth() {
                return defaultColumnWidth;
            }

            /**
             * Define el valor de la propiedad defaultColumnWidth.
             * 
             */
            public void setDefaultColumnWidth(short value) {
                this.defaultColumnWidth = value;
            }

            /**
             * Obtiene el valor de la propiedad defaultRowHeight.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getDefaultRowHeight() {
                return defaultRowHeight;
            }

            /**
             * Define el valor de la propiedad defaultRowHeight.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setDefaultRowHeight(BigDecimal value) {
                this.defaultRowHeight = value;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}StyleID use="required""/&gt;
             *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Width use="required""/&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Column {

                @XmlAttribute(name = "StyleID", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
                protected String styleID;
                @XmlAttribute(name = "Width", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
                protected BigDecimal width;

                /**
                 * Obtiene el valor de la propiedad styleID.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStyleID() {
                    return styleID;
                }

                /**
                 * Define el valor de la propiedad styleID.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStyleID(String value) {
                    this.styleID = value;
                }

                /**
                 * Obtiene el valor de la propiedad width.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getWidth() {
                    return width;
                }

                /**
                 * Define el valor de la propiedad width.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setWidth(BigDecimal value) {
                    this.width = value;
                }

            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence minOccurs="0"&gt;
             *         &lt;element name="Cell" maxOccurs="unbounded"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence minOccurs="0"&gt;
             *                   &lt;element name="Data"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;simpleContent&gt;
             *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
             *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Type use="required""/&gt;
             *                         &lt;/extension&gt;
             *                       &lt;/simpleContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                 &lt;/sequence&gt;
             *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}StyleID use="required""/&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "cell"
            })
            public static class Row {

                @XmlElement(name = "Cell", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
                protected List<Workbook.Worksheet.Table.Row.Cell> cell;

                /**
                 * Gets the value of the cell property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the cell property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getCell().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Workbook.Worksheet.Table.Row.Cell }
                 * 
                 * 
                 */
                public List<Workbook.Worksheet.Table.Row.Cell> getCell() {
                    if (cell == null) {
                        cell = new ArrayList<Workbook.Worksheet.Table.Row.Cell>();
                    }
                    return this.cell;
                }


                /**
                 * <p>Clase Java para anonymous complex type.
                 * 
                 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                 * 
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;sequence minOccurs="0"&gt;
                 *         &lt;element name="Data"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;simpleContent&gt;
                 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
                 *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Type use="required""/&gt;
                 *               &lt;/extension&gt;
                 *             &lt;/simpleContent&gt;
                 *           &lt;/complexType&gt;
                 *         &lt;/element&gt;
                 *       &lt;/sequence&gt;
                 *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}StyleID use="required""/&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "data"
                })
                public static class Cell {

                    @XmlElement(name = "Data", namespace = "urn:schemas-microsoft-com:office:spreadsheet")
                    protected Workbook.Worksheet.Table.Row.Cell.Data data;
                    @XmlAttribute(name = "StyleID", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
                    protected String styleID;

                    /**
                     * Obtiene el valor de la propiedad data.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Workbook.Worksheet.Table.Row.Cell.Data }
                     *     
                     */
                    public Workbook.Worksheet.Table.Row.Cell.Data getData() {
                        return data;
                    }

                    /**
                     * Define el valor de la propiedad data.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Workbook.Worksheet.Table.Row.Cell.Data }
                     *     
                     */
                    public void setData(Workbook.Worksheet.Table.Row.Cell.Data value) {
                        this.data = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad styleID.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getStyleID() {
                        return styleID;
                    }

                    /**
                     * Define el valor de la propiedad styleID.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setStyleID(String value) {
                        this.styleID = value;
                    }


                    /**
                     * <p>Clase Java para anonymous complex type.
                     * 
                     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                     * 
                     * <pre>
                     * &lt;complexType&gt;
                     *   &lt;simpleContent&gt;
                     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
                     *       &lt;attribute ref="{urn:schemas-microsoft-com:office:spreadsheet}Type use="required""/&gt;
                     *     &lt;/extension&gt;
                     *   &lt;/simpleContent&gt;
                     * &lt;/complexType&gt;
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "value"
                    })
                    public static class Data {

                        @XmlValue
                        protected String value;
                        @XmlAttribute(name = "Type", namespace = "urn:schemas-microsoft-com:office:spreadsheet", required = true)
                        protected String type;

                        /**
                         * Obtiene el valor de la propiedad value.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getValue() {
                            return value;
                        }

                        /**
                         * Define el valor de la propiedad value.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setValue(String value) {
                            this.value = value;
                        }

                        /**
                         * Obtiene el valor de la propiedad type.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getType() {
                            return type;
                        }

                        /**
                         * Define el valor de la propiedad type.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setType(String value) {
                            this.type = value;
                        }

                    }

                }

            }

        }

    }

}
