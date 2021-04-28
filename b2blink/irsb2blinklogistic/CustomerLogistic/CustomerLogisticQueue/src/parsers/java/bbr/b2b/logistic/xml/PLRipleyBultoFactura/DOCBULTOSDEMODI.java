//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.08.24 a las 05:07:25 PM CLT 
//


package bbr.b2b.logistic.xml.PLRipleyBultoFactura;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="RUT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FACTURA_GUIA" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="NUM_FAC_GUI" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="FACTURA_O_GUIA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="NUM_ODI" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="BULTO_DEM_ODI" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="NUM_BULTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="FECHA_BULTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="DETALLE_BULTO_DEM_ODI" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="NUM_PROD_RIPLEY" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="PROV_CASEPACK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="CANTIDAD" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
 *                                     &lt;/sequence&gt;
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
    "rut",
    "facturaguia"
})
@XmlRootElement(name = "DOC_BULTOS_DEM_ODI")
public class DOCBULTOSDEMODI {

    @XmlElement(name = "RUT", required = true)
    protected String rut;
    @XmlElement(name = "FACTURA_GUIA", required = true)
    protected List<DOCBULTOSDEMODI.FACTURAGUIA> facturaguia;

    /**
     * Obtiene el valor de la propiedad rut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRUT() {
        return rut;
    }

    /**
     * Define el valor de la propiedad rut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRUT(String value) {
        this.rut = value;
    }

    /**
     * Gets the value of the facturaguia property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the facturaguia property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFACTURAGUIA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DOCBULTOSDEMODI.FACTURAGUIA }
     * 
     * 
     */
    public List<DOCBULTOSDEMODI.FACTURAGUIA> getFACTURAGUIA() {
        if (facturaguia == null) {
            facturaguia = new ArrayList<DOCBULTOSDEMODI.FACTURAGUIA>();
        }
        return this.facturaguia;
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
     *         &lt;element name="NUM_FAC_GUI" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="FACTURA_O_GUIA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="NUM_ODI" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="BULTO_DEM_ODI" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="NUM_BULTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="FECHA_BULTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="DETALLE_BULTO_DEM_ODI" maxOccurs="unbounded"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="NUM_PROD_RIPLEY" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="PROV_CASEPACK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="CANTIDAD" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
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
        "numfacgui",
        "facturaoguia",
        "numodi",
        "bultodemodi"
    })
    public static class FACTURAGUIA {

        @XmlElement(name = "NUM_FAC_GUI", required = true)
        protected String numfacgui;
        @XmlElement(name = "FACTURA_O_GUIA", required = true)
        protected String facturaoguia;
        @XmlElement(name = "NUM_ODI", required = true)
        protected String numodi;
        @XmlElement(name = "BULTO_DEM_ODI", required = true)
        protected List<DOCBULTOSDEMODI.FACTURAGUIA.BULTODEMODI> bultodemodi;

        /**
         * Obtiene el valor de la propiedad numfacgui.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNUMFACGUI() {
            return numfacgui;
        }

        /**
         * Define el valor de la propiedad numfacgui.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNUMFACGUI(String value) {
            this.numfacgui = value;
        }

        /**
         * Obtiene el valor de la propiedad facturaoguia.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFACTURAOGUIA() {
            return facturaoguia;
        }

        /**
         * Define el valor de la propiedad facturaoguia.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFACTURAOGUIA(String value) {
            this.facturaoguia = value;
        }

        /**
         * Obtiene el valor de la propiedad numodi.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNUMODI() {
            return numodi;
        }

        /**
         * Define el valor de la propiedad numodi.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNUMODI(String value) {
            this.numodi = value;
        }

        /**
         * Gets the value of the bultodemodi property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the bultodemodi property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBULTODEMODI().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DOCBULTOSDEMODI.FACTURAGUIA.BULTODEMODI }
         * 
         * 
         */
        public List<DOCBULTOSDEMODI.FACTURAGUIA.BULTODEMODI> getBULTODEMODI() {
            if (bultodemodi == null) {
                bultodemodi = new ArrayList<DOCBULTOSDEMODI.FACTURAGUIA.BULTODEMODI>();
            }
            return this.bultodemodi;
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
         *         &lt;element name="NUM_BULTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="FECHA_BULTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="DETALLE_BULTO_DEM_ODI" maxOccurs="unbounded"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="NUM_PROD_RIPLEY" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="PROV_CASEPACK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="CANTIDAD" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
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
            "numbulto",
            "fechabulto",
            "detallebultodemodi"
        })
        public static class BULTODEMODI {

            @XmlElement(name = "NUM_BULTO", required = true)
            protected String numbulto;
            @XmlElement(name = "FECHA_BULTO", required = true)
            protected String fechabulto;
            @XmlElement(name = "DETALLE_BULTO_DEM_ODI", required = true)
            protected List<DOCBULTOSDEMODI.FACTURAGUIA.BULTODEMODI.DETALLEBULTODEMODI> detallebultodemodi;

            /**
             * Obtiene el valor de la propiedad numbulto.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNUMBULTO() {
                return numbulto;
            }

            /**
             * Define el valor de la propiedad numbulto.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNUMBULTO(String value) {
                this.numbulto = value;
            }

            /**
             * Obtiene el valor de la propiedad fechabulto.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFECHABULTO() {
                return fechabulto;
            }

            /**
             * Define el valor de la propiedad fechabulto.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFECHABULTO(String value) {
                this.fechabulto = value;
            }

            /**
             * Gets the value of the detallebultodemodi property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the detallebultodemodi property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDETALLEBULTODEMODI().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link DOCBULTOSDEMODI.FACTURAGUIA.BULTODEMODI.DETALLEBULTODEMODI }
             * 
             * 
             */
            public List<DOCBULTOSDEMODI.FACTURAGUIA.BULTODEMODI.DETALLEBULTODEMODI> getDETALLEBULTODEMODI() {
                if (detallebultodemodi == null) {
                    detallebultodemodi = new ArrayList<DOCBULTOSDEMODI.FACTURAGUIA.BULTODEMODI.DETALLEBULTODEMODI>();
                }
                return this.detallebultodemodi;
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
             *         &lt;element name="NUM_PROD_RIPLEY" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="PROV_CASEPACK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="CANTIDAD" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
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
                "numprodripley",
                "provcasepack",
                "cantidad"
            })
            public static class DETALLEBULTODEMODI {

                @XmlElement(name = "NUM_PROD_RIPLEY", required = true)
                protected String numprodripley;
                @XmlElement(name = "PROV_CASEPACK", required = true)
                protected String provcasepack;
                @XmlElement(name = "CANTIDAD")
                @XmlSchemaType(name = "unsignedInt")
                protected long cantidad;

                /**
                 * Obtiene el valor de la propiedad numprodripley.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getNUMPRODRIPLEY() {
                    return numprodripley;
                }

                /**
                 * Define el valor de la propiedad numprodripley.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setNUMPRODRIPLEY(String value) {
                    this.numprodripley = value;
                }

                /**
                 * Obtiene el valor de la propiedad provcasepack.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPROVCASEPACK() {
                    return provcasepack;
                }

                /**
                 * Define el valor de la propiedad provcasepack.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPROVCASEPACK(String value) {
                    this.provcasepack = value;
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

            }

        }

    }

}
