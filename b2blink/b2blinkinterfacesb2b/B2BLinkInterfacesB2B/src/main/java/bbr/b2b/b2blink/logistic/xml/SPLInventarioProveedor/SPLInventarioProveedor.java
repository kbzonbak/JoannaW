//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.09.28 a las 11:57:42 AM CLST 
//


package bbr.b2b.b2blink.logistic.xml.SPLInventarioProveedor;

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
 *                             &lt;element name="vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="tipo_trasaccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                             &lt;element name="inventario" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="accion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlRootElement(name = "SPLInventarioProveedor")
public class SPLInventarioProveedor {

    @XmlElement(required = true)
    protected String vendor;
    @XmlElement(required = true)
    protected String buyer;
    @XmlElement(required = true)
    protected SPLInventarioProveedor.Productos productos;

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
     *     {@link SPLInventarioProveedor.Productos }
     *     
     */
    public SPLInventarioProveedor.Productos getProductos() {
        return productos;
    }

    /**
     * Define el valor de la propiedad productos.
     * 
     * @param value
     *     allowed object is
     *     {@link SPLInventarioProveedor.Productos }
     *     
     */
    public void setProductos(SPLInventarioProveedor.Productos value) {
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
     *                   &lt;element name="vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="tipo_trasaccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                   &lt;element name="inventario" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="accion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<SPLInventarioProveedor.Productos.Producto> producto;

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
         * {@link SPLInventarioProveedor.Productos.Producto }
         * 
         * 
         */
        public List<SPLInventarioProveedor.Productos.Producto> getProducto() {
            if (producto == null) {
                producto = new ArrayList<SPLInventarioProveedor.Productos.Producto>();
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
         *         &lt;element name="vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="tipo_trasaccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *         &lt;element name="inventario" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="accion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "vendedor",
            "tipoTrasaccion",
            "sku",
            "fecha",
            "inventario",
            "accion"
        })
        public static class Producto {

            @XmlElement(required = true)
            protected String vendedor;
            @XmlElement(name = "tipo_trasaccion", required = true)
            protected String tipoTrasaccion;
            @XmlElement(required = true)
            protected String sku;
            @XmlElement(required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar fecha;
            protected int inventario;
            @XmlElement(required = true)
            protected String accion;

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
             * Obtiene el valor de la propiedad tipoTrasaccion.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipoTrasaccion() {
                return tipoTrasaccion;
            }

            /**
             * Define el valor de la propiedad tipoTrasaccion.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipoTrasaccion(String value) {
                this.tipoTrasaccion = value;
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
             * Obtiene el valor de la propiedad fecha.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getFecha() {
                return fecha;
            }

            /**
             * Define el valor de la propiedad fecha.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setFecha(XMLGregorianCalendar value) {
                this.fecha = value;
            }

            /**
             * Obtiene el valor de la propiedad inventario.
             * 
             */
            public int getInventario() {
                return inventario;
            }

            /**
             * Define el valor de la propiedad inventario.
             * 
             */
            public void setInventario(int value) {
                this.inventario = value;
            }

            /**
             * Obtiene el valor de la propiedad accion.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAccion() {
                return accion;
            }

            /**
             * Define el valor de la propiedad accion.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAccion(String value) {
                this.accion = value;
            }

        }

    }

}
