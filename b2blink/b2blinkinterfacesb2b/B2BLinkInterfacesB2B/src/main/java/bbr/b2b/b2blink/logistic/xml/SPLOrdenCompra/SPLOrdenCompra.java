//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.10.26 a las 02:09:49 PM CLST 
//


package bbr.b2b.b2blink.logistic.xml.SPLOrdenCompra;

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
 *         &lt;element name="fecha_solicitud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipo_solicitud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="comprador" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="accion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipo_oc_bbr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fecha_oc" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
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
 *                             &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "fechaSolicitud",
    "tipoSolicitud",
    "vendedor",
    "comprador",
    "numOc",
    "accion",
    "tipoOcBbr",
    "fechaOc",
    "productos"
})
@XmlRootElement(name = "SPLOrdenCompra")
public class SPLOrdenCompra {

    @XmlElement(name = "fecha_solicitud", required = true)
    protected String fechaSolicitud;
    @XmlElement(name = "tipo_solicitud", required = true)
    protected String tipoSolicitud;
    @XmlElement(required = true)
    protected String vendedor;
    @XmlElement(required = true)
    protected String comprador;
    @XmlElement(name = "num_oc", required = true)
    protected String numOc;
    @XmlElement(required = true)
    protected String accion;
    @XmlElement(name = "tipo_oc_bbr", required = true)
    protected String tipoOcBbr;
    @XmlElement(name = "fecha_oc", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaOc;
    @XmlElement(required = true)
    protected SPLOrdenCompra.Productos productos;

    /**
     * Obtiene el valor de la propiedad fechaSolicitud.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * Define el valor de la propiedad fechaSolicitud.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaSolicitud(String value) {
        this.fechaSolicitud = value;
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
     * Obtiene el valor de la propiedad numOc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumOc() {
        return numOc;
    }

    /**
     * Define el valor de la propiedad numOc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumOc(String value) {
        this.numOc = value;
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

    /**
     * Obtiene el valor de la propiedad tipoOcBbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoOcBbr() {
        return tipoOcBbr;
    }

    /**
     * Define el valor de la propiedad tipoOcBbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoOcBbr(String value) {
        this.tipoOcBbr = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaOc.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaOc() {
        return fechaOc;
    }

    /**
     * Define el valor de la propiedad fechaOc.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaOc(XMLGregorianCalendar value) {
        this.fechaOc = value;
    }

    /**
     * Obtiene el valor de la propiedad productos.
     * 
     * @return
     *     possible object is
     *     {@link SPLOrdenCompra.Productos }
     *     
     */
    public SPLOrdenCompra.Productos getProductos() {
        return productos;
    }

    /**
     * Define el valor de la propiedad productos.
     * 
     * @param value
     *     allowed object is
     *     {@link SPLOrdenCompra.Productos }
     *     
     */
    public void setProductos(SPLOrdenCompra.Productos value) {
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
     *                   &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
        protected List<SPLOrdenCompra.Productos.Producto> producto;

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
         * {@link SPLOrdenCompra.Productos.Producto }
         * 
         * 
         */
        public List<SPLOrdenCompra.Productos.Producto> getProducto() {
            if (producto == null) {
                producto = new ArrayList<SPLOrdenCompra.Productos.Producto>();
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
         *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
            "descripcion",
            "cantidad"
        })
        public static class Producto {

            @XmlElement(required = true)
            protected String sku;
            @XmlElement(required = true)
            protected String descripcion;
            protected int cantidad;

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
             * Obtiene el valor de la propiedad cantidad.
             * 
             */
            public int getCantidad() {
                return cantidad;
            }

            /**
             * Define el valor de la propiedad cantidad.
             * 
             */
            public void setCantidad(int value) {
                this.cantidad = value;
            }

        }

    }

}
