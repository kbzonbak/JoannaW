//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.08.07 a las 03:31:01 PM CLT 
//


package bbr.b2b.logistic.xml.PLFalabella;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="PIR"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="NRO_OC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="FECHA_DESPACHO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="HORA_DESPACHO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="TOTAL_BULTOS" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
 *                   &lt;element name="TOTAL_TOTES" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
 *                   &lt;element name="TOTAL_COLGADOS" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
 *                   &lt;element name="NRO_SERIE_FACT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="NRO_FACTURA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ALMACEN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="GUIAS_DESPACHO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="PRODUCTO"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="PRODUCTO_ROW" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="UPC" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
 *                                       &lt;element name="DESCRIPCION_LARGA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="NRO_LOCAL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="LOCAL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="CANTIDAD" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
 *                                       &lt;element name="TIPO_EMBALAJE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="NRO_BULTO" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
 *                                     &lt;/sequence&gt;
 *                                     &lt;attribute name="num" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
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
 *                 &lt;attribute name="num" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
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
    "pir"
})
@XmlRootElement(name = "ePIR")
public class EPIR {

    @XmlElement(name = "PIR", required = true)
    protected EPIR.PIR pir;

    /**
     * Obtiene el valor de la propiedad pir.
     * 
     * @return
     *     possible object is
     *     {@link EPIR.PIR }
     *     
     */
    public EPIR.PIR getPIR() {
        return pir;
    }

    /**
     * Define el valor de la propiedad pir.
     * 
     * @param value
     *     allowed object is
     *     {@link EPIR.PIR }
     *     
     */
    public void setPIR(EPIR.PIR value) {
        this.pir = value;
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
     *         &lt;element name="NRO_OC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="FECHA_DESPACHO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="HORA_DESPACHO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="TOTAL_BULTOS" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
     *         &lt;element name="TOTAL_TOTES" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
     *         &lt;element name="TOTAL_COLGADOS" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
     *         &lt;element name="NRO_SERIE_FACT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="NRO_FACTURA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ALMACEN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="GUIAS_DESPACHO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="PRODUCTO"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="PRODUCTO_ROW" maxOccurs="unbounded"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="UPC" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
     *                             &lt;element name="DESCRIPCION_LARGA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="NRO_LOCAL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="LOCAL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="CANTIDAD" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
     *                             &lt;element name="TIPO_EMBALAJE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="NRO_BULTO" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
     *                           &lt;/sequence&gt;
     *                           &lt;attribute name="num" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
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
     *       &lt;attribute name="num" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "nrooc",
        "fechadespacho",
        "horadespacho",
        "totalbultos",
        "totaltotes",
        "totalcolgados",
        "nroseriefact",
        "nrofactura",
        "almacen",
        "guiasdespacho",
        "producto"
    })
    public static class PIR {

        @XmlElement(name = "NRO_OC", required = true)
        protected String nrooc;
        @XmlElement(name = "FECHA_DESPACHO", required = true)
        protected String fechadespacho;
        @XmlElement(name = "HORA_DESPACHO", required = true)
        protected String horadespacho;
        @XmlElement(name = "TOTAL_BULTOS")
        @XmlSchemaType(name = "unsignedInt")
        protected long totalbultos;
        @XmlElement(name = "TOTAL_TOTES")
        @XmlSchemaType(name = "unsignedInt")
        protected long totaltotes;
        @XmlElement(name = "TOTAL_COLGADOS")
        @XmlSchemaType(name = "unsignedInt")
        protected long totalcolgados;
        @XmlElement(name = "NRO_SERIE_FACT", required = true)
        protected String nroseriefact;
        @XmlElement(name = "NRO_FACTURA", required = true)
        protected String nrofactura;
        @XmlElement(name = "ALMACEN", required = true)
        protected String almacen;
        @XmlElement(name = "GUIAS_DESPACHO", required = true)
        protected String guiasdespacho;
        @XmlElement(name = "PRODUCTO", required = true)
        protected EPIR.PIR.PRODUCTO producto;
        @XmlAttribute(name = "num", required = true)
        @XmlSchemaType(name = "unsignedInt")
        protected long num;

        /**
         * Obtiene el valor de la propiedad nrooc.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNROOC() {
            return nrooc;
        }

        /**
         * Define el valor de la propiedad nrooc.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNROOC(String value) {
            this.nrooc = value;
        }

        /**
         * Obtiene el valor de la propiedad fechadespacho.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFECHADESPACHO() {
            return fechadespacho;
        }

        /**
         * Define el valor de la propiedad fechadespacho.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFECHADESPACHO(String value) {
            this.fechadespacho = value;
        }

        /**
         * Obtiene el valor de la propiedad horadespacho.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHORADESPACHO() {
            return horadespacho;
        }

        /**
         * Define el valor de la propiedad horadespacho.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHORADESPACHO(String value) {
            this.horadespacho = value;
        }

        /**
         * Obtiene el valor de la propiedad totalbultos.
         * 
         */
        public long getTOTALBULTOS() {
            return totalbultos;
        }

        /**
         * Define el valor de la propiedad totalbultos.
         * 
         */
        public void setTOTALBULTOS(long value) {
            this.totalbultos = value;
        }

        /**
         * Obtiene el valor de la propiedad totaltotes.
         * 
         */
        public long getTOTALTOTES() {
            return totaltotes;
        }

        /**
         * Define el valor de la propiedad totaltotes.
         * 
         */
        public void setTOTALTOTES(long value) {
            this.totaltotes = value;
        }

        /**
         * Obtiene el valor de la propiedad totalcolgados.
         * 
         */
        public long getTOTALCOLGADOS() {
            return totalcolgados;
        }

        /**
         * Define el valor de la propiedad totalcolgados.
         * 
         */
        public void setTOTALCOLGADOS(long value) {
            this.totalcolgados = value;
        }

        /**
         * Obtiene el valor de la propiedad nroseriefact.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNROSERIEFACT() {
            return nroseriefact;
        }

        /**
         * Define el valor de la propiedad nroseriefact.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNROSERIEFACT(String value) {
            this.nroseriefact = value;
        }

        /**
         * Obtiene el valor de la propiedad nrofactura.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNROFACTURA() {
            return nrofactura;
        }

        /**
         * Define el valor de la propiedad nrofactura.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNROFACTURA(String value) {
            this.nrofactura = value;
        }

        /**
         * Obtiene el valor de la propiedad almacen.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getALMACEN() {
            return almacen;
        }

        /**
         * Define el valor de la propiedad almacen.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setALMACEN(String value) {
            this.almacen = value;
        }

        /**
         * Obtiene el valor de la propiedad guiasdespacho.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGUIASDESPACHO() {
            return guiasdespacho;
        }

        /**
         * Define el valor de la propiedad guiasdespacho.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGUIASDESPACHO(String value) {
            this.guiasdespacho = value;
        }

        /**
         * Obtiene el valor de la propiedad producto.
         * 
         * @return
         *     possible object is
         *     {@link EPIR.PIR.PRODUCTO }
         *     
         */
        public EPIR.PIR.PRODUCTO getPRODUCTO() {
            return producto;
        }

        /**
         * Define el valor de la propiedad producto.
         * 
         * @param value
         *     allowed object is
         *     {@link EPIR.PIR.PRODUCTO }
         *     
         */
        public void setPRODUCTO(EPIR.PIR.PRODUCTO value) {
            this.producto = value;
        }

        /**
         * Obtiene el valor de la propiedad num.
         * 
         */
        public long getNum() {
            return num;
        }

        /**
         * Define el valor de la propiedad num.
         * 
         */
        public void setNum(long value) {
            this.num = value;
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
         *         &lt;element name="PRODUCTO_ROW" maxOccurs="unbounded"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="UPC" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
         *                   &lt;element name="DESCRIPCION_LARGA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="NRO_LOCAL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="LOCAL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="CANTIDAD" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
         *                   &lt;element name="TIPO_EMBALAJE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="NRO_BULTO" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
         *                 &lt;/sequence&gt;
         *                 &lt;attribute name="num" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
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
            "productorow"
        })
        public static class PRODUCTO {

            @XmlElement(name = "PRODUCTO_ROW", required = true)
            protected List<EPIR.PIR.PRODUCTO.PRODUCTOROW> productorow;

            /**
             * Gets the value of the productorow property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the productorow property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPRODUCTOROW().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link EPIR.PIR.PRODUCTO.PRODUCTOROW }
             * 
             * 
             */
            public List<EPIR.PIR.PRODUCTO.PRODUCTOROW> getPRODUCTOROW() {
                if (productorow == null) {
                    productorow = new ArrayList<EPIR.PIR.PRODUCTO.PRODUCTOROW>();
                }
                return this.productorow;
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
             *         &lt;element name="UPC" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
             *         &lt;element name="DESCRIPCION_LARGA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="NRO_LOCAL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="LOCAL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="CANTIDAD" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
             *         &lt;element name="TIPO_EMBALAJE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="NRO_BULTO" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
             *       &lt;/sequence&gt;
             *       &lt;attribute name="num" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "upc",
                "descripcionlarga",
                "nrolocal",
                "local",
                "cantidad",
                "tipoembalaje",
                "nrobulto"
            })
            public static class PRODUCTOROW {

                @XmlElement(name = "UPC")
                @XmlSchemaType(name = "unsignedInt")
                protected long upc;
                @XmlElement(name = "DESCRIPCION_LARGA", required = true)
                protected String descripcionlarga;
                @XmlElement(name = "NRO_LOCAL", required = true)
                protected String nrolocal;
                @XmlElement(name = "LOCAL", required = true)
                protected String local;
                @XmlElement(name = "CANTIDAD")
                @XmlSchemaType(name = "unsignedInt")
                protected long cantidad;
                @XmlElement(name = "TIPO_EMBALAJE", required = true)
                protected String tipoembalaje;
                @XmlElement(name = "NRO_BULTO")
                @XmlSchemaType(name = "unsignedInt")
                protected long nrobulto;
                @XmlAttribute(name = "num", required = true)
                @XmlSchemaType(name = "unsignedInt")
                protected long num;

                /**
                 * Obtiene el valor de la propiedad upc.
                 * 
                 */
                public long getUPC() {
                    return upc;
                }

                /**
                 * Define el valor de la propiedad upc.
                 * 
                 */
                public void setUPC(long value) {
                    this.upc = value;
                }

                /**
                 * Obtiene el valor de la propiedad descripcionlarga.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDESCRIPCIONLARGA() {
                    return descripcionlarga;
                }

                /**
                 * Define el valor de la propiedad descripcionlarga.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDESCRIPCIONLARGA(String value) {
                    this.descripcionlarga = value;
                }

                /**
                 * Obtiene el valor de la propiedad nrolocal.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getNROLOCAL() {
                    return nrolocal;
                }

                /**
                 * Define el valor de la propiedad nrolocal.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setNROLOCAL(String value) {
                    this.nrolocal = value;
                }

                /**
                 * Obtiene el valor de la propiedad local.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLOCAL() {
                    return local;
                }

                /**
                 * Define el valor de la propiedad local.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLOCAL(String value) {
                    this.local = value;
                }

                /**
                 * Obtiene el valor de la propiedad cantidad.
                 * 
                 */
                public long getCANTIDAD() {
                    return cantidad;
                }

                /**
                 * Define el valor de la propiedad cantidad.
                 * 
                 */
                public void setCANTIDAD(long value) {
                    this.cantidad = value;
                }

                /**
                 * Obtiene el valor de la propiedad tipoembalaje.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTIPOEMBALAJE() {
                    return tipoembalaje;
                }

                /**
                 * Define el valor de la propiedad tipoembalaje.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTIPOEMBALAJE(String value) {
                    this.tipoembalaje = value;
                }

                /**
                 * Obtiene el valor de la propiedad nrobulto.
                 * 
                 */
                public long getNROBULTO() {
                    return nrobulto;
                }

                /**
                 * Define el valor de la propiedad nrobulto.
                 * 
                 */
                public void setNROBULTO(long value) {
                    this.nrobulto = value;
                }

                /**
                 * Obtiene el valor de la propiedad num.
                 * 
                 */
                public long getNum() {
                    return num;
                }

                /**
                 * Define el valor de la propiedad num.
                 * 
                 */
                public void setNum(long value) {
                    this.num = value;
                }

            }

        }

    }

}
