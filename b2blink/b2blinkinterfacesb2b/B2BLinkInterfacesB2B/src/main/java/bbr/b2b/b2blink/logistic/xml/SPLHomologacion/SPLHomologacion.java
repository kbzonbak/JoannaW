//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.10.07 a las 05:13:28 PM CLST 
//


package bbr.b2b.b2blink.logistic.xml.SPLHomologacion;

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
 *                             &lt;element name="comprador" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="sku_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="sku_comprador" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="spl_habilitado" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
@XmlRootElement(name = "SPLHomologacion")
public class SPLHomologacion {

    @XmlElement(required = true)
    protected String vendor;
    @XmlElement(required = true)
    protected String buyer;
    @XmlElement(required = true)
    protected SPLHomologacion.Productos productos;

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
     *     {@link SPLHomologacion.Productos }
     *     
     */
    public SPLHomologacion.Productos getProductos() {
        return productos;
    }

    /**
     * Define el valor de la propiedad productos.
     * 
     * @param value
     *     allowed object is
     *     {@link SPLHomologacion.Productos }
     *     
     */
    public void setProductos(SPLHomologacion.Productos value) {
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
     *                   &lt;element name="comprador" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="sku_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="sku_comprador" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="spl_habilitado" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
        protected List<SPLHomologacion.Productos.Producto> producto;

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
         * {@link SPLHomologacion.Productos.Producto }
         * 
         * 
         */
        public List<SPLHomologacion.Productos.Producto> getProducto() {
            if (producto == null) {
                producto = new ArrayList<SPLHomologacion.Productos.Producto>();
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
         *         &lt;element name="comprador" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="sku_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="sku_comprador" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="spl_habilitado" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
            "comprador",
            "skuVendedor",
            "skuComprador",
            "splHabilitado"
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
            protected String comprador;
            @XmlElement(name = "sku_vendedor", required = true)
            protected String skuVendedor;
            @XmlElement(name = "sku_comprador", required = true)
            protected String skuComprador;
            @XmlElement(name = "spl_habilitado")
            protected boolean splHabilitado;

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
             * Obtiene el valor de la propiedad comprador.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getComprador() {
                return comprador;
            }

            /**
             * Define el valor de la propiedad comprador.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setComprador(String value) {
                this.comprador = value;
            }

            /**
             * Obtiene el valor de la propiedad skuVendedor.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSkuVendedor() {
                return skuVendedor;
            }

            /**
             * Define el valor de la propiedad skuVendedor.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSkuVendedor(String value) {
                this.skuVendedor = value;
            }

            /**
             * Obtiene el valor de la propiedad skuComprador.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSkuComprador() {
                return skuComprador;
            }

            /**
             * Define el valor de la propiedad skuComprador.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSkuComprador(String value) {
                this.skuComprador = value;
            }

            /**
             * Obtiene el valor de la propiedad splHabilitado.
             * 
             */
            public boolean isSplHabilitado() {
                return splHabilitado;
            }

            /**
             * Define el valor de la propiedad splHabilitado.
             * 
             */
            public void setSplHabilitado(boolean value) {
                this.splHabilitado = value;
            }

        }

    }

}
