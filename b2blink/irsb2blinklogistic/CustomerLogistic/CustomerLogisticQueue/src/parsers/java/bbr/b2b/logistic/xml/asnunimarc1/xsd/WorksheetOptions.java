//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.08.28 a las 06:38:56 PM CLT 
//


package bbr.b2b.logistic.xml.asnunimarc1.xsd;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="PageSetup"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Layout" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}CenterHorizontal use="required""/&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Header"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Margin use="required""/&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Footer"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Margin use="required""/&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="PageMargins"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Bottom use="required""/&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Left use="required""/&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Right use="required""/&gt;
 *                           &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Top use="required""/&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Print" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ValidPrinterInfo" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *                   &lt;element name="HorizontalResolution" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/&gt;
 *                   &lt;element name="VerticalResolution" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Selected" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *         &lt;element name="DoNotDisplayGridlines" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *         &lt;element name="ProtectObjects" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProtectScenarios" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "pageSetup",
    "print",
    "selected",
    "doNotDisplayGridlines",
    "protectObjects",
    "protectScenarios"
})
@XmlRootElement(name = "WorksheetOptions")
public class WorksheetOptions {

    @XmlElement(name = "PageSetup", required = true)
    protected WorksheetOptions.PageSetup pageSetup;
    @XmlElement(name = "Print")
    protected WorksheetOptions.Print print;
    @XmlElement(name = "Selected")
    protected Object selected;
    @XmlElement(name = "DoNotDisplayGridlines")
    protected Object doNotDisplayGridlines;
    @XmlElement(name = "ProtectObjects", required = true)
    protected String protectObjects;
    @XmlElement(name = "ProtectScenarios", required = true)
    protected String protectScenarios;

    /**
     * Obtiene el valor de la propiedad pageSetup.
     * 
     * @return
     *     possible object is
     *     {@link WorksheetOptions.PageSetup }
     *     
     */
    public WorksheetOptions.PageSetup getPageSetup() {
        return pageSetup;
    }

    /**
     * Define el valor de la propiedad pageSetup.
     * 
     * @param value
     *     allowed object is
     *     {@link WorksheetOptions.PageSetup }
     *     
     */
    public void setPageSetup(WorksheetOptions.PageSetup value) {
        this.pageSetup = value;
    }

    /**
     * Obtiene el valor de la propiedad print.
     * 
     * @return
     *     possible object is
     *     {@link WorksheetOptions.Print }
     *     
     */
    public WorksheetOptions.Print getPrint() {
        return print;
    }

    /**
     * Define el valor de la propiedad print.
     * 
     * @param value
     *     allowed object is
     *     {@link WorksheetOptions.Print }
     *     
     */
    public void setPrint(WorksheetOptions.Print value) {
        this.print = value;
    }

    /**
     * Obtiene el valor de la propiedad selected.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getSelected() {
        return selected;
    }

    /**
     * Define el valor de la propiedad selected.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setSelected(Object value) {
        this.selected = value;
    }

    /**
     * Obtiene el valor de la propiedad doNotDisplayGridlines.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDoNotDisplayGridlines() {
        return doNotDisplayGridlines;
    }

    /**
     * Define el valor de la propiedad doNotDisplayGridlines.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDoNotDisplayGridlines(Object value) {
        this.doNotDisplayGridlines = value;
    }

    /**
     * Obtiene el valor de la propiedad protectObjects.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtectObjects() {
        return protectObjects;
    }

    /**
     * Define el valor de la propiedad protectObjects.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtectObjects(String value) {
        this.protectObjects = value;
    }

    /**
     * Obtiene el valor de la propiedad protectScenarios.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtectScenarios() {
        return protectScenarios;
    }

    /**
     * Define el valor de la propiedad protectScenarios.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtectScenarios(String value) {
        this.protectScenarios = value;
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
     *         &lt;element name="Layout" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}CenterHorizontal use="required""/&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Header"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Margin use="required""/&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Footer"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Margin use="required""/&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="PageMargins"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Bottom use="required""/&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Left use="required""/&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Right use="required""/&gt;
     *                 &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Top use="required""/&gt;
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
        "layout",
        "header",
        "footer",
        "pageMargins"
    })
    public static class PageSetup {

        @XmlElement(name = "Layout")
        protected WorksheetOptions.PageSetup.Layout layout;
        @XmlElement(name = "Header", required = true)
        protected WorksheetOptions.PageSetup.Header header;
        @XmlElement(name = "Footer", required = true)
        protected WorksheetOptions.PageSetup.Footer footer;
        @XmlElement(name = "PageMargins", required = true)
        protected WorksheetOptions.PageSetup.PageMargins pageMargins;

        /**
         * Obtiene el valor de la propiedad layout.
         * 
         * @return
         *     possible object is
         *     {@link WorksheetOptions.PageSetup.Layout }
         *     
         */
        public WorksheetOptions.PageSetup.Layout getLayout() {
            return layout;
        }

        /**
         * Define el valor de la propiedad layout.
         * 
         * @param value
         *     allowed object is
         *     {@link WorksheetOptions.PageSetup.Layout }
         *     
         */
        public void setLayout(WorksheetOptions.PageSetup.Layout value) {
            this.layout = value;
        }

        /**
         * Obtiene el valor de la propiedad header.
         * 
         * @return
         *     possible object is
         *     {@link WorksheetOptions.PageSetup.Header }
         *     
         */
        public WorksheetOptions.PageSetup.Header getHeader() {
            return header;
        }

        /**
         * Define el valor de la propiedad header.
         * 
         * @param value
         *     allowed object is
         *     {@link WorksheetOptions.PageSetup.Header }
         *     
         */
        public void setHeader(WorksheetOptions.PageSetup.Header value) {
            this.header = value;
        }

        /**
         * Obtiene el valor de la propiedad footer.
         * 
         * @return
         *     possible object is
         *     {@link WorksheetOptions.PageSetup.Footer }
         *     
         */
        public WorksheetOptions.PageSetup.Footer getFooter() {
            return footer;
        }

        /**
         * Define el valor de la propiedad footer.
         * 
         * @param value
         *     allowed object is
         *     {@link WorksheetOptions.PageSetup.Footer }
         *     
         */
        public void setFooter(WorksheetOptions.PageSetup.Footer value) {
            this.footer = value;
        }

        /**
         * Obtiene el valor de la propiedad pageMargins.
         * 
         * @return
         *     possible object is
         *     {@link WorksheetOptions.PageSetup.PageMargins }
         *     
         */
        public WorksheetOptions.PageSetup.PageMargins getPageMargins() {
            return pageMargins;
        }

        /**
         * Define el valor de la propiedad pageMargins.
         * 
         * @param value
         *     allowed object is
         *     {@link WorksheetOptions.PageSetup.PageMargins }
         *     
         */
        public void setPageMargins(WorksheetOptions.PageSetup.PageMargins value) {
            this.pageMargins = value;
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
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Margin use="required""/&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Footer {

            @XmlAttribute(name = "Margin", namespace = "urn:schemas-microsoft-com:office:excel", required = true)
            @XmlSchemaType(name = "unsignedByte")
            protected short margin;

            /**
             * Obtiene el valor de la propiedad margin.
             * 
             */
            public short getMargin() {
                return margin;
            }

            /**
             * Define el valor de la propiedad margin.
             * 
             */
            public void setMargin(short value) {
                this.margin = value;
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
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Margin use="required""/&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Header {

            @XmlAttribute(name = "Margin", namespace = "urn:schemas-microsoft-com:office:excel", required = true)
            @XmlSchemaType(name = "unsignedByte")
            protected short margin;

            /**
             * Obtiene el valor de la propiedad margin.
             * 
             */
            public short getMargin() {
                return margin;
            }

            /**
             * Define el valor de la propiedad margin.
             * 
             */
            public void setMargin(short value) {
                this.margin = value;
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
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}CenterHorizontal use="required""/&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Layout {

            @XmlAttribute(name = "CenterHorizontal", namespace = "urn:schemas-microsoft-com:office:excel", required = true)
            @XmlSchemaType(name = "unsignedByte")
            protected short centerHorizontal;

            /**
             * Obtiene el valor de la propiedad centerHorizontal.
             * 
             */
            public short getCenterHorizontal() {
                return centerHorizontal;
            }

            /**
             * Define el valor de la propiedad centerHorizontal.
             * 
             */
            public void setCenterHorizontal(short value) {
                this.centerHorizontal = value;
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
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Bottom use="required""/&gt;
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Left use="required""/&gt;
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Right use="required""/&gt;
         *       &lt;attribute ref="{urn:schemas-microsoft-com:office:excel}Top use="required""/&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class PageMargins {

            @XmlAttribute(name = "Bottom", namespace = "urn:schemas-microsoft-com:office:excel", required = true)
            protected BigDecimal bottom;
            @XmlAttribute(name = "Left", namespace = "urn:schemas-microsoft-com:office:excel", required = true)
            protected BigDecimal left;
            @XmlAttribute(name = "Right", namespace = "urn:schemas-microsoft-com:office:excel", required = true)
            protected BigDecimal right;
            @XmlAttribute(name = "Top", namespace = "urn:schemas-microsoft-com:office:excel", required = true)
            protected BigDecimal top;

            /**
             * Obtiene el valor de la propiedad bottom.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getBottom() {
                return bottom;
            }

            /**
             * Define el valor de la propiedad bottom.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setBottom(BigDecimal value) {
                this.bottom = value;
            }

            /**
             * Obtiene el valor de la propiedad left.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getLeft() {
                return left;
            }

            /**
             * Define el valor de la propiedad left.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setLeft(BigDecimal value) {
                this.left = value;
            }

            /**
             * Obtiene el valor de la propiedad right.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getRight() {
                return right;
            }

            /**
             * Define el valor de la propiedad right.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setRight(BigDecimal value) {
                this.right = value;
            }

            /**
             * Obtiene el valor de la propiedad top.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getTop() {
                return top;
            }

            /**
             * Define el valor de la propiedad top.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setTop(BigDecimal value) {
                this.top = value;
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
     *         &lt;element name="ValidPrinterInfo" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *         &lt;element name="HorizontalResolution" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/&gt;
     *         &lt;element name="VerticalResolution" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/&gt;
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
        "validPrinterInfo",
        "horizontalResolution",
        "verticalResolution"
    })
    public static class Print {

        @XmlElement(name = "ValidPrinterInfo", required = true)
        protected Object validPrinterInfo;
        @XmlElement(name = "HorizontalResolution")
        @XmlSchemaType(name = "unsignedShort")
        protected int horizontalResolution;
        @XmlElement(name = "VerticalResolution")
        @XmlSchemaType(name = "unsignedShort")
        protected int verticalResolution;

        /**
         * Obtiene el valor de la propiedad validPrinterInfo.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getValidPrinterInfo() {
            return validPrinterInfo;
        }

        /**
         * Define el valor de la propiedad validPrinterInfo.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setValidPrinterInfo(Object value) {
            this.validPrinterInfo = value;
        }

        /**
         * Obtiene el valor de la propiedad horizontalResolution.
         * 
         */
        public int getHorizontalResolution() {
            return horizontalResolution;
        }

        /**
         * Define el valor de la propiedad horizontalResolution.
         * 
         */
        public void setHorizontalResolution(int value) {
            this.horizontalResolution = value;
        }

        /**
         * Obtiene el valor de la propiedad verticalResolution.
         * 
         */
        public int getVerticalResolution() {
            return verticalResolution;
        }

        /**
         * Define el valor de la propiedad verticalResolution.
         * 
         */
        public void setVerticalResolution(int value) {
            this.verticalResolution = value;
        }

    }

}
