//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.09.30 a las 10:22:55 AM CLST 
//


package bbr.b2b.b2blink.logistic.xml.SPLMaestroProductos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="vendor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="buyer" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="productos"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element name="producto"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="fecha_solicitud" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                             &lt;element name="id_requerimiento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="tipo_solicitud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="marca" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="stock_critico" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="variacion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "vendor",
    "buyer",
    "productos"
})
@XmlRootElement(name = "SPLMaestroProductos")
public class SPLMaestroProductos {

    @XmlElement(required = true)
    protected String vendor;
    @XmlElement(required = true)
    protected String buyer;
    @XmlElement(required = true)
    protected SPLMaestroProductos.Productos productos;

    /**
     * Obtiene el valor de la propiedad vendor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Define el valor de la propiedad vendor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendor(String value) {
        this.vendor = value;
    }

    /**
     * Obtiene el valor de la propiedad buyer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyer() {
        return buyer;
    }

    /**
     * Define el valor de la propiedad buyer.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyer(String value) {
        this.buyer = value;
    }

    /**
     * Obtiene el valor de la propiedad productos.
     * 
     * @return
     *     possible object is
     *     {@link SPLMaestroProductos.Productos }
     *     
     */
    public SPLMaestroProductos.Productos getProductos() {
        return productos;
    }

    /**
     * Define el valor de la propiedad productos.
     * 
     * @param value
     *     allowed object is
     *     {@link SPLMaestroProductos.Productos }
     *     
     */
    public void setProductos(SPLMaestroProductos.Productos value) {
        this.productos = value;
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
     *         &lt;element name="producto"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="fecha_solicitud" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                   &lt;element name="id_requerimiento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="tipo_solicitud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="marca" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="stock_critico" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="variacion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
        "producto"
    })
    public static class Productos {

        @XmlElement(required = true)
        protected List<SPLMaestroProductos.Productos.Producto> producto;

        /**
         * Gets the value of the producto property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the producto property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProducto().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SPLMaestroProductos.Productos.Producto }
         * 
         * 
         */
        public List<SPLMaestroProductos.Productos.Producto> getProducto() {
            if (producto == null) {
                producto = new ArrayList<SPLMaestroProductos.Productos.Producto>();
            }
            return this.producto;
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
         *         &lt;element name="fecha_solicitud" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *         &lt;element name="id_requerimiento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="tipo_solicitud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="marca" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="stock_critico" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="variacion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
            "fechaSolicitud",
            "idRequerimiento",
            "tipoSolicitud",
            "vendedor",
            "sku",
            "descripcion",
            "marca",
            "stockCritico",
            "variacion"
        })
        public static class Producto {

            @XmlElement(name = "fecha_solicitud", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar fechaSolicitud;
            @XmlElement(name = "id_requerimiento", required = true)
            protected String idRequerimiento;
            @XmlElement(name = "tipo_solicitud", required = true)
            protected String tipoSolicitud;
            @XmlElement(required = true)
            protected String vendedor;
            @XmlElement(required = true)
            protected String sku;
            @XmlElement(required = true)
            protected String descripcion;
            @XmlElement(required = true)
            protected String marca;
            @XmlElement(name = "stock_critico")
            protected int stockCritico;
            protected int variacion;

            /**
             * Obtiene el valor de la propiedad fechaSolicitud.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getFechaSolicitud() {
                return fechaSolicitud;
            }

            /**
             * Define el valor de la propiedad fechaSolicitud.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setFechaSolicitud(XMLGregorianCalendar value) {
                this.fechaSolicitud = value;
            }

            /**
             * Obtiene el valor de la propiedad idRequerimiento.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIdRequerimiento() {
                return idRequerimiento;
            }

            /**
             * Define el valor de la propiedad idRequerimiento.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIdRequerimiento(String value) {
                this.idRequerimiento = value;
            }

            /**
             * Obtiene el valor de la propiedad tipoSolicitud.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipoSolicitud() {
                return tipoSolicitud;
            }

            /**
             * Define el valor de la propiedad tipoSolicitud.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipoSolicitud(String value) {
                this.tipoSolicitud = value;
            }

            /**
             * Obtiene el valor de la propiedad vendedor.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVendedor() {
                return vendedor;
            }

            /**
             * Define el valor de la propiedad vendedor.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVendedor(String value) {
                this.vendedor = value;
            }

            /**
             * Obtiene el valor de la propiedad sku.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSku() {
                return sku;
            }

            /**
             * Define el valor de la propiedad sku.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSku(String value) {
                this.sku = value;
            }

            /**
             * Obtiene el valor de la propiedad descripcion.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescripcion() {
                return descripcion;
            }

            /**
             * Define el valor de la propiedad descripcion.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescripcion(String value) {
                this.descripcion = value;
            }

            /**
             * Obtiene el valor de la propiedad marca.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMarca() {
                return marca;
            }

            /**
             * Define el valor de la propiedad marca.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMarca(String value) {
                this.marca = value;
            }

            /**
             * Obtiene el valor de la propiedad stockCritico.
             * 
             */
            public int getStockCritico() {
                return stockCritico;
            }

            /**
             * Define el valor de la propiedad stockCritico.
             * 
             */
            public void setStockCritico(int value) {
                this.stockCritico = value;
            }

            /**
             * Obtiene el valor de la propiedad variacion.
             * 
             */
            public int getVariacion() {
                return variacion;
            }

            /**
             * Define el valor de la propiedad variacion.
             * 
             */
            public void setVariacion(int value) {
                this.variacion = value;
            }

        }

    }

}
