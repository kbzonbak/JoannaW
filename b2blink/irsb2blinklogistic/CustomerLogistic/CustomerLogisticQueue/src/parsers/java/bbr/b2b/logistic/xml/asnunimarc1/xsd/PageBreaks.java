//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.08.28 a las 06:38:56 PM CLT 
//


package bbr.b2b.logistic.xml.asnunimarc1.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="RowBreaks"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="RowBreak"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Row" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *                           &lt;/sequence&gt;
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
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "rowBreaks"
})
@XmlRootElement(name = "PageBreaks")
public class PageBreaks {

    @XmlElement(name = "RowBreaks", required = true)
    protected PageBreaks.RowBreaks rowBreaks;

    /**
     * Obtiene el valor de la propiedad rowBreaks.
     * 
     * @return
     *     possible object is
     *     {@link PageBreaks.RowBreaks }
     *     
     */
    public PageBreaks.RowBreaks getRowBreaks() {
        return rowBreaks;
    }

    /**
     * Define el valor de la propiedad rowBreaks.
     * 
     * @param value
     *     allowed object is
     *     {@link PageBreaks.RowBreaks }
     *     
     */
    public void setRowBreaks(PageBreaks.RowBreaks value) {
        this.rowBreaks = value;
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
     *         &lt;element name="RowBreak"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Row" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
     *                 &lt;/sequence&gt;
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
        "rowBreak"
    })
    public static class RowBreaks {

        @XmlElement(name = "RowBreak", required = true)
        protected PageBreaks.RowBreaks.RowBreak rowBreak;

        /**
         * Obtiene el valor de la propiedad rowBreak.
         * 
         * @return
         *     possible object is
         *     {@link PageBreaks.RowBreaks.RowBreak }
         *     
         */
        public PageBreaks.RowBreaks.RowBreak getRowBreak() {
            return rowBreak;
        }

        /**
         * Define el valor de la propiedad rowBreak.
         * 
         * @param value
         *     allowed object is
         *     {@link PageBreaks.RowBreaks.RowBreak }
         *     
         */
        public void setRowBreak(PageBreaks.RowBreaks.RowBreak value) {
            this.rowBreak = value;
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
         *         &lt;element name="Row" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
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
            "row"
        })
        public static class RowBreak {

            @XmlElement(name = "Row")
            @XmlSchemaType(name = "unsignedByte")
            protected short row;

            /**
             * Obtiene el valor de la propiedad row.
             * 
             */
            public short getRow() {
                return row;
            }

            /**
             * Define el valor de la propiedad row.
             * 
             */
            public void setRow(short value) {
                this.row = value;
            }

        }

    }

}
