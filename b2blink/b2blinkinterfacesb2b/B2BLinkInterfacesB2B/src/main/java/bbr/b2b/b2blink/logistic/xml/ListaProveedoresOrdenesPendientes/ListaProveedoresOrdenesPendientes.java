//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2019.06.24 a las 09:55:48 AM CLT 
//


package bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *         &lt;element name="nombreportal" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codcomprador" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Proveedores"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element name="Proveedor"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="numpendientes" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="Detalles" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence maxOccurs="unbounded"&gt;
 *                                       &lt;element name="Detalle"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="numeroOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="tipoOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="estadoOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="estadoSoaOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="ultimoCambioEstadoSOA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                               &lt;/sequence&gt;
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
    "nombreportal",
    "codcomprador",
    "proveedores"
})
@XmlRootElement(name = "ListaProveedoresOrdenesPendientes")
public class ListaProveedoresOrdenesPendientes {

    @XmlElement(required = true)
    protected String nombreportal;
    @XmlElement(required = true)
    protected String codcomprador;
    @XmlElement(name = "Proveedores", required = true)
    protected ListaProveedoresOrdenesPendientes.Proveedores proveedores;

    /**
     * Obtiene el valor de la propiedad nombreportal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreportal() {
        return nombreportal;
    }

    /**
     * Define el valor de la propiedad nombreportal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreportal(String value) {
        this.nombreportal = value;
    }

    /**
     * Obtiene el valor de la propiedad codcomprador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodcomprador() {
        return codcomprador;
    }

    /**
     * Define el valor de la propiedad codcomprador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodcomprador(String value) {
        this.codcomprador = value;
    }

    /**
     * Obtiene el valor de la propiedad proveedores.
     * 
     * @return
     *     possible object is
     *     {@link ListaProveedoresOrdenesPendientes.Proveedores }
     *     
     */
    public ListaProveedoresOrdenesPendientes.Proveedores getProveedores() {
        return proveedores;
    }

    /**
     * Define el valor de la propiedad proveedores.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaProveedoresOrdenesPendientes.Proveedores }
     *     
     */
    public void setProveedores(ListaProveedoresOrdenesPendientes.Proveedores value) {
        this.proveedores = value;
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
     *       &lt;sequence maxOccurs="unbounded"&gt;
     *         &lt;element name="Proveedor"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="numpendientes" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="Detalles" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence maxOccurs="unbounded"&gt;
     *                             &lt;element name="Detalle"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="numeroOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="tipoOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="estadoOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="estadoSoaOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="ultimoCambioEstadoSOA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "proveedor"
    })
    public static class Proveedores {

        @XmlElement(name = "Proveedor", required = true)
        protected List<ListaProveedoresOrdenesPendientes.Proveedores.Proveedor> proveedor;

        /**
         * Gets the value of the proveedor property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the proveedor property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProveedor().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ListaProveedoresOrdenesPendientes.Proveedores.Proveedor }
         * 
         * 
         */
        public List<ListaProveedoresOrdenesPendientes.Proveedores.Proveedor> getProveedor() {
            if (proveedor == null) {
                proveedor = new ArrayList<ListaProveedoresOrdenesPendientes.Proveedores.Proveedor>();
            }
            return this.proveedor;
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
         *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="numpendientes" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="Detalles" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence maxOccurs="unbounded"&gt;
         *                   &lt;element name="Detalle"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="numeroOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="tipoOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="estadoOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="estadoSoaOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="ultimoCambioEstadoSOA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "codigo",
            "nombre",
            "numpendientes",
            "detalles"
        })
        public static class Proveedor {

            @XmlElement(required = true)
            protected String codigo;
            @XmlElement(required = true)
            protected String nombre;
            protected int numpendientes;
            @XmlElement(name = "Detalles")
            protected ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles detalles;

            /**
             * Obtiene el valor de la propiedad codigo.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodigo() {
                return codigo;
            }

            /**
             * Define el valor de la propiedad codigo.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodigo(String value) {
                this.codigo = value;
            }

            /**
             * Obtiene el valor de la propiedad nombre.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNombre() {
                return nombre;
            }

            /**
             * Define el valor de la propiedad nombre.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNombre(String value) {
                this.nombre = value;
            }

            /**
             * Obtiene el valor de la propiedad numpendientes.
             * 
             */
            public int getNumpendientes() {
                return numpendientes;
            }

            /**
             * Define el valor de la propiedad numpendientes.
             * 
             */
            public void setNumpendientes(int value) {
                this.numpendientes = value;
            }

            /**
             * Obtiene el valor de la propiedad detalles.
             * 
             * @return
             *     possible object is
             *     {@link ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles }
             *     
             */
            public ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles getDetalles() {
                return detalles;
            }

            /**
             * Define el valor de la propiedad detalles.
             * 
             * @param value
             *     allowed object is
             *     {@link ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles }
             *     
             */
            public void setDetalles(ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles value) {
                this.detalles = value;
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
             *       &lt;sequence maxOccurs="unbounded"&gt;
             *         &lt;element name="Detalle"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="numeroOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="tipoOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="estadoOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="estadoSoaOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="ultimoCambioEstadoSOA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "detalle"
            })
            public static class Detalles {

                @XmlElement(name = "Detalle", required = true)
                protected List<ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles.Detalle> detalle;

                /**
                 * Gets the value of the detalle property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the detalle property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getDetalle().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles.Detalle }
                 * 
                 * 
                 */
                public List<ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles.Detalle> getDetalle() {
                    if (detalle == null) {
                        detalle = new ArrayList<ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles.Detalle>();
                    }
                    return this.detalle;
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
                 *         &lt;element name="numeroOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="tipoOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="estadoOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="estadoSoaOC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="ultimoCambioEstadoSOA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                    "numeroOC",
                    "tipoOC",
                    "estadoOC",
                    "estadoSoaOC",
                    "ultimoCambioEstadoSOA"
                })
                public static class Detalle {

                    @XmlElement(required = true)
                    protected String numeroOC;
                    @XmlElement(required = true)
                    protected String tipoOC;
                    @XmlElement(required = true)
                    protected String estadoOC;
                    @XmlElement(required = true)
                    protected String estadoSoaOC;
                    @XmlElement(required = true)
                    protected String ultimoCambioEstadoSOA;

                    /**
                     * Obtiene el valor de la propiedad numeroOC.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNumeroOC() {
                        return numeroOC;
                    }

                    /**
                     * Define el valor de la propiedad numeroOC.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNumeroOC(String value) {
                        this.numeroOC = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad tipoOC.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTipoOC() {
                        return tipoOC;
                    }

                    /**
                     * Define el valor de la propiedad tipoOC.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTipoOC(String value) {
                        this.tipoOC = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad estadoOC.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getEstadoOC() {
                        return estadoOC;
                    }

                    /**
                     * Define el valor de la propiedad estadoOC.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setEstadoOC(String value) {
                        this.estadoOC = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad estadoSoaOC.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getEstadoSoaOC() {
                        return estadoSoaOC;
                    }

                    /**
                     * Define el valor de la propiedad estadoSoaOC.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setEstadoSoaOC(String value) {
                        this.estadoSoaOC = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad ultimoCambioEstadoSOA.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getUltimoCambioEstadoSOA() {
                        return ultimoCambioEstadoSOA;
                    }

                    /**
                     * Define el valor de la propiedad ultimoCambioEstadoSOA.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setUltimoCambioEstadoSOA(String value) {
                        this.ultimoCambioEstadoSOA = value;
                    }

                }

            }

        }

    }

}
