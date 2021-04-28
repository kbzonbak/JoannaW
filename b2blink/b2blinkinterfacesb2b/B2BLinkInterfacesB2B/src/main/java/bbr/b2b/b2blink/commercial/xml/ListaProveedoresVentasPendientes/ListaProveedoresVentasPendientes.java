//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2019.06.24 a las 09:55:49 AM CLT 
//


package bbr.b2b.b2blink.commercial.xml.ListaProveedoresVentasPendientes;

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
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Detalle"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="motivo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="estadoSoaVenta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlRootElement(name = "ListaProveedoresVentasPendientes")
public class ListaProveedoresVentasPendientes {

    @XmlElement(required = true)
    protected String nombreportal;
    @XmlElement(required = true)
    protected String codcomprador;
    @XmlElement(name = "Proveedores", required = true)
    protected ListaProveedoresVentasPendientes.Proveedores proveedores;

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
     *     {@link ListaProveedoresVentasPendientes.Proveedores }
     *     
     */
    public ListaProveedoresVentasPendientes.Proveedores getProveedores() {
        return proveedores;
    }

    /**
     * Define el valor de la propiedad proveedores.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaProveedoresVentasPendientes.Proveedores }
     *     
     */
    public void setProveedores(ListaProveedoresVentasPendientes.Proveedores value) {
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
     *                           &lt;sequence&gt;
     *                             &lt;element name="Detalle"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="motivo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="estadoSoaVenta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<ListaProveedoresVentasPendientes.Proveedores.Proveedor> proveedor;

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
         * {@link ListaProveedoresVentasPendientes.Proveedores.Proveedor }
         * 
         * 
         */
        public List<ListaProveedoresVentasPendientes.Proveedores.Proveedor> getProveedor() {
            if (proveedor == null) {
                proveedor = new ArrayList<ListaProveedoresVentasPendientes.Proveedores.Proveedor>();
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
         *                 &lt;sequence&gt;
         *                   &lt;element name="Detalle"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="motivo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="estadoSoaVenta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            protected ListaProveedoresVentasPendientes.Proveedores.Proveedor.Detalles detalles;

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
             *     {@link ListaProveedoresVentasPendientes.Proveedores.Proveedor.Detalles }
             *     
             */
            public ListaProveedoresVentasPendientes.Proveedores.Proveedor.Detalles getDetalles() {
                return detalles;
            }

            /**
             * Define el valor de la propiedad detalles.
             * 
             * @param value
             *     allowed object is
             *     {@link ListaProveedoresVentasPendientes.Proveedores.Proveedor.Detalles }
             *     
             */
            public void setDetalles(ListaProveedoresVentasPendientes.Proveedores.Proveedor.Detalles value) {
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
             *       &lt;sequence&gt;
             *         &lt;element name="Detalle"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="motivo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="estadoSoaVenta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                protected ListaProveedoresVentasPendientes.Proveedores.Proveedor.Detalles.Detalle detalle;

                /**
                 * Obtiene el valor de la propiedad detalle.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ListaProveedoresVentasPendientes.Proveedores.Proveedor.Detalles.Detalle }
                 *     
                 */
                public ListaProveedoresVentasPendientes.Proveedores.Proveedor.Detalles.Detalle getDetalle() {
                    return detalle;
                }

                /**
                 * Define el valor de la propiedad detalle.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ListaProveedoresVentasPendientes.Proveedores.Proveedor.Detalles.Detalle }
                 *     
                 */
                public void setDetalle(ListaProveedoresVentasPendientes.Proveedores.Proveedor.Detalles.Detalle value) {
                    this.detalle = value;
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
                 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="motivo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="estadoSoaVenta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                    "fecha",
                    "motivo",
                    "estadoSoaVenta",
                    "ultimoCambioEstadoSOA"
                })
                public static class Detalle {

                    @XmlElement(required = true)
                    protected String fecha;
                    @XmlElement(required = true)
                    protected String motivo;
                    @XmlElement(required = true)
                    protected String estadoSoaVenta;
                    @XmlElement(required = true)
                    protected String ultimoCambioEstadoSOA;

                    /**
                     * Obtiene el valor de la propiedad fecha.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getFecha() {
                        return fecha;
                    }

                    /**
                     * Define el valor de la propiedad fecha.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setFecha(String value) {
                        this.fecha = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad motivo.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMotivo() {
                        return motivo;
                    }

                    /**
                     * Define el valor de la propiedad motivo.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMotivo(String value) {
                        this.motivo = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad estadoSoaVenta.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getEstadoSoaVenta() {
                        return estadoSoaVenta;
                    }

                    /**
                     * Define el valor de la propiedad estadoSoaVenta.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setEstadoSoaVenta(String value) {
                        this.estadoSoaVenta = value;
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
