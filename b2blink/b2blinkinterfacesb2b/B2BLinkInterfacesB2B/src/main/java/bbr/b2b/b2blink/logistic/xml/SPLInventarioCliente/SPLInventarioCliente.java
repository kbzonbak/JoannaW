//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.09.22 a las 04:33:12 PM CLST 
//


package bbr.b2b.b2blink.logistic.xml.SPLInventarioCliente;

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
 *         &lt;element name="vendedor" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="fecha_solicitud" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
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
 *                             &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="stock" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "vendedor",
    "fechaSolicitud",
    "productos"
})
@XmlRootElement(name = "SPLInventarioCliente")
public class SPLInventarioCliente {

    @XmlElement(required = true)
    protected Object vendedor;
    @XmlElement(name = "fecha_solicitud", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaSolicitud;
    @XmlElement(required = true)
    protected SPLInventarioCliente.Productos productos;

    /**
     * Obtiene el valor de la propiedad vendedor.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getVendedor() {
        return vendedor;
    }

    /**
     * Define el valor de la propiedad vendedor.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setVendedor(Object value) {
        this.vendedor = value;
    }

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
     * Obtiene el valor de la propiedad productos.
     * 
     * @return
     *     possible object is
     *     {@link SPLInventarioCliente.Productos }
     *     
     */
    public SPLInventarioCliente.Productos getProductos() {
        return productos;
    }

    /**
     * Define el valor de la propiedad productos.
     * 
     * @param value
     *     allowed object is
     *     {@link SPLInventarioCliente.Productos }
     *     
     */
    public void setProductos(SPLInventarioCliente.Productos value) {
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
     *                   &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="stock" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
        protected List<SPLInventarioCliente.Productos.Producto> producto;

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
         * {@link SPLInventarioCliente.Productos.Producto }
         * 
         * 
         */
        public List<SPLInventarioCliente.Productos.Producto> getProducto() {
            if (producto == null) {
                producto = new ArrayList<SPLInventarioCliente.Productos.Producto>();
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
         *         &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="stock" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
            "sku",
            "stock",
            "accion"
        })
        public static class Producto {

            @XmlElement(required = true)
            protected String sku;
            protected int stock;
            @XmlElement(required = true)
            protected String accion;

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
             * Obtiene el valor de la propiedad stock.
             * 
             */
            public int getStock() {
                return stock;
            }

            /**
             * Define el valor de la propiedad stock.
             * 
             */
            public void setStock(int value) {
                this.stock = value;
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
