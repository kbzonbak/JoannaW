//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2019.06.24 a las 09:55:43 AM CLT 
//


package bbr.b2b.b2blink.logistic.xml.Rec_Interno;

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
 *         &lt;element name="nombreportal" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="norecepcion" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="norden" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="tipo_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fecha_recepcion" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="cod_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="forma_pago" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="comentarios" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="responsable" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="edi_data"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="senderIdentification" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="recipientIdentification" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="correlativeVendor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="messageReferenceNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="countrycode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="buyerCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="buyerLocationCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="comprador"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="unidad_negocio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="vendedor"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="contacto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="locales"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element name="local"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="cod" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ean" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="detalles"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element name="detalle"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="cod_prod_comp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cod_prod_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="descripcion_prod" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cod_empaque" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="desc_empaque" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="desc_um_unidad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="prod_empaque" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *                             &lt;element name="cantidad_recepcionada" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *                             &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *                             &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
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
    "norecepcion",
    "norden",
    "tipoOc",
    "fechaRecepcion",
    "total",
    "codLocalEntrega",
    "formaPago",
    "comentarios",
    "responsable",
    "ediData",
    "comprador",
    "vendedor",
    "locales",
    "detalles"
})
@XmlRootElement(name = "recepcion")
public class Recepcion {

    @XmlElement(required = true)
    protected String nombreportal;
    protected long norecepcion;
    protected long norden;
    @XmlElement(name = "tipo_oc", required = true)
    protected String tipoOc;
    @XmlElement(name = "fecha_recepcion", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaRecepcion;
    protected double total;
    @XmlElement(name = "cod_local_entrega", required = true)
    protected String codLocalEntrega;
    @XmlElement(name = "forma_pago", required = true)
    protected String formaPago;
    @XmlElement(required = true)
    protected String comentarios;
    @XmlElement(required = true)
    protected String responsable;
    @XmlElement(name = "edi_data", required = true)
    protected Recepcion.EdiData ediData;
    @XmlElement(required = true)
    protected Recepcion.Comprador comprador;
    @XmlElement(required = true)
    protected Recepcion.Vendedor vendedor;
    @XmlElement(required = true)
    protected Recepcion.Locales locales;
    @XmlElement(required = true)
    protected Recepcion.Detalles detalles;

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
     * Obtiene el valor de la propiedad norecepcion.
     * 
     */
    public long getNorecepcion() {
        return norecepcion;
    }

    /**
     * Define el valor de la propiedad norecepcion.
     * 
     */
    public void setNorecepcion(long value) {
        this.norecepcion = value;
    }

    /**
     * Obtiene el valor de la propiedad norden.
     * 
     */
    public long getNorden() {
        return norden;
    }

    /**
     * Define el valor de la propiedad norden.
     * 
     */
    public void setNorden(long value) {
        this.norden = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoOc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoOc() {
        return tipoOc;
    }

    /**
     * Define el valor de la propiedad tipoOc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoOc(String value) {
        this.tipoOc = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaRecepcion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaRecepcion() {
        return fechaRecepcion;
    }

    /**
     * Define el valor de la propiedad fechaRecepcion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaRecepcion(XMLGregorianCalendar value) {
        this.fechaRecepcion = value;
    }

    /**
     * Obtiene el valor de la propiedad total.
     * 
     */
    public double getTotal() {
        return total;
    }

    /**
     * Define el valor de la propiedad total.
     * 
     */
    public void setTotal(double value) {
        this.total = value;
    }

    /**
     * Obtiene el valor de la propiedad codLocalEntrega.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodLocalEntrega() {
        return codLocalEntrega;
    }

    /**
     * Define el valor de la propiedad codLocalEntrega.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodLocalEntrega(String value) {
        this.codLocalEntrega = value;
    }

    /**
     * Obtiene el valor de la propiedad formaPago.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormaPago() {
        return formaPago;
    }

    /**
     * Define el valor de la propiedad formaPago.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormaPago(String value) {
        this.formaPago = value;
    }

    /**
     * Obtiene el valor de la propiedad comentarios.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * Define el valor de la propiedad comentarios.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComentarios(String value) {
        this.comentarios = value;
    }

    /**
     * Obtiene el valor de la propiedad responsable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsable() {
        return responsable;
    }

    /**
     * Define el valor de la propiedad responsable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsable(String value) {
        this.responsable = value;
    }

    /**
     * Obtiene el valor de la propiedad ediData.
     * 
     * @return
     *     possible object is
     *     {@link Recepcion.EdiData }
     *     
     */
    public Recepcion.EdiData getEdiData() {
        return ediData;
    }

    /**
     * Define el valor de la propiedad ediData.
     * 
     * @param value
     *     allowed object is
     *     {@link Recepcion.EdiData }
     *     
     */
    public void setEdiData(Recepcion.EdiData value) {
        this.ediData = value;
    }

    /**
     * Obtiene el valor de la propiedad comprador.
     * 
     * @return
     *     possible object is
     *     {@link Recepcion.Comprador }
     *     
     */
    public Recepcion.Comprador getComprador() {
        return comprador;
    }

    /**
     * Define el valor de la propiedad comprador.
     * 
     * @param value
     *     allowed object is
     *     {@link Recepcion.Comprador }
     *     
     */
    public void setComprador(Recepcion.Comprador value) {
        this.comprador = value;
    }

    /**
     * Obtiene el valor de la propiedad vendedor.
     * 
     * @return
     *     possible object is
     *     {@link Recepcion.Vendedor }
     *     
     */
    public Recepcion.Vendedor getVendedor() {
        return vendedor;
    }

    /**
     * Define el valor de la propiedad vendedor.
     * 
     * @param value
     *     allowed object is
     *     {@link Recepcion.Vendedor }
     *     
     */
    public void setVendedor(Recepcion.Vendedor value) {
        this.vendedor = value;
    }

    /**
     * Obtiene el valor de la propiedad locales.
     * 
     * @return
     *     possible object is
     *     {@link Recepcion.Locales }
     *     
     */
    public Recepcion.Locales getLocales() {
        return locales;
    }

    /**
     * Define el valor de la propiedad locales.
     * 
     * @param value
     *     allowed object is
     *     {@link Recepcion.Locales }
     *     
     */
    public void setLocales(Recepcion.Locales value) {
        this.locales = value;
    }

    /**
     * Obtiene el valor de la propiedad detalles.
     * 
     * @return
     *     possible object is
     *     {@link Recepcion.Detalles }
     *     
     */
    public Recepcion.Detalles getDetalles() {
        return detalles;
    }

    /**
     * Define el valor de la propiedad detalles.
     * 
     * @param value
     *     allowed object is
     *     {@link Recepcion.Detalles }
     *     
     */
    public void setDetalles(Recepcion.Detalles value) {
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
     *         &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="unidad_negocio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "razonSocial",
        "unidadNegocio"
    })
    public static class Comprador {

        @XmlElement(required = true)
        protected String rut;
        @XmlElement(name = "razon_social", required = true)
        protected String razonSocial;
        @XmlElement(name = "unidad_negocio", required = true)
        protected String unidadNegocio;

        /**
         * Obtiene el valor de la propiedad rut.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRut() {
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
        public void setRut(String value) {
            this.rut = value;
        }

        /**
         * Obtiene el valor de la propiedad razonSocial.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRazonSocial() {
            return razonSocial;
        }

        /**
         * Define el valor de la propiedad razonSocial.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRazonSocial(String value) {
            this.razonSocial = value;
        }

        /**
         * Obtiene el valor de la propiedad unidadNegocio.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUnidadNegocio() {
            return unidadNegocio;
        }

        /**
         * Define el valor de la propiedad unidadNegocio.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUnidadNegocio(String value) {
            this.unidadNegocio = value;
        }

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
     *         &lt;element name="detalle"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="cod_prod_comp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cod_prod_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="descripcion_prod" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cod_empaque" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="desc_empaque" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="desc_um_unidad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="prod_empaque" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
     *                   &lt;element name="cantidad_recepcionada" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
     *                   &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
     *                   &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
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

        @XmlElement(required = true)
        protected List<Recepcion.Detalles.Detalle> detalle;

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
         * {@link Recepcion.Detalles.Detalle }
         * 
         * 
         */
        public List<Recepcion.Detalles.Detalle> getDetalle() {
            if (detalle == null) {
                detalle = new ArrayList<Recepcion.Detalles.Detalle>();
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
         *         &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="cod_prod_comp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cod_prod_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="descripcion_prod" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cod_empaque" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="desc_empaque" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="desc_um_unidad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="prod_empaque" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
         *         &lt;element name="cantidad_recepcionada" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
         *         &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
         *         &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
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
            "item",
            "codProdComp",
            "codProdVendedor",
            "ean13",
            "descripcionProd",
            "codEmpaque",
            "descEmpaque",
            "descUmUnidad",
            "prodEmpaque",
            "cantidadRecepcionada",
            "costoLista",
            "costoFinal"
        })
        public static class Detalle {

            protected int item;
            @XmlElement(name = "cod_prod_comp", required = true)
            protected String codProdComp;
            @XmlElement(name = "cod_prod_vendedor", required = true)
            protected String codProdVendedor;
            @XmlElement(required = true)
            protected String ean13;
            @XmlElement(name = "descripcion_prod", required = true)
            protected String descripcionProd;
            @XmlElement(name = "cod_empaque", required = true)
            protected String codEmpaque;
            @XmlElement(name = "desc_empaque", required = true)
            protected String descEmpaque;
            @XmlElement(name = "desc_um_unidad", required = true)
            protected String descUmUnidad;
            @XmlElement(name = "prod_empaque")
            protected float prodEmpaque;
            @XmlElement(name = "cantidad_recepcionada")
            protected float cantidadRecepcionada;
            @XmlElement(name = "costo_lista")
            protected double costoLista;
            @XmlElement(name = "costo_final")
            protected double costoFinal;

            /**
             * Obtiene el valor de la propiedad item.
             * 
             */
            public int getItem() {
                return item;
            }

            /**
             * Define el valor de la propiedad item.
             * 
             */
            public void setItem(int value) {
                this.item = value;
            }

            /**
             * Obtiene el valor de la propiedad codProdComp.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodProdComp() {
                return codProdComp;
            }

            /**
             * Define el valor de la propiedad codProdComp.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodProdComp(String value) {
                this.codProdComp = value;
            }

            /**
             * Obtiene el valor de la propiedad codProdVendedor.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodProdVendedor() {
                return codProdVendedor;
            }

            /**
             * Define el valor de la propiedad codProdVendedor.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodProdVendedor(String value) {
                this.codProdVendedor = value;
            }

            /**
             * Obtiene el valor de la propiedad ean13.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEan13() {
                return ean13;
            }

            /**
             * Define el valor de la propiedad ean13.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEan13(String value) {
                this.ean13 = value;
            }

            /**
             * Obtiene el valor de la propiedad descripcionProd.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescripcionProd() {
                return descripcionProd;
            }

            /**
             * Define el valor de la propiedad descripcionProd.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescripcionProd(String value) {
                this.descripcionProd = value;
            }

            /**
             * Obtiene el valor de la propiedad codEmpaque.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodEmpaque() {
                return codEmpaque;
            }

            /**
             * Define el valor de la propiedad codEmpaque.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodEmpaque(String value) {
                this.codEmpaque = value;
            }

            /**
             * Obtiene el valor de la propiedad descEmpaque.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescEmpaque() {
                return descEmpaque;
            }

            /**
             * Define el valor de la propiedad descEmpaque.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescEmpaque(String value) {
                this.descEmpaque = value;
            }

            /**
             * Obtiene el valor de la propiedad descUmUnidad.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescUmUnidad() {
                return descUmUnidad;
            }

            /**
             * Define el valor de la propiedad descUmUnidad.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescUmUnidad(String value) {
                this.descUmUnidad = value;
            }

            /**
             * Obtiene el valor de la propiedad prodEmpaque.
             * 
             */
            public float getProdEmpaque() {
                return prodEmpaque;
            }

            /**
             * Define el valor de la propiedad prodEmpaque.
             * 
             */
            public void setProdEmpaque(float value) {
                this.prodEmpaque = value;
            }

            /**
             * Obtiene el valor de la propiedad cantidadRecepcionada.
             * 
             */
            public float getCantidadRecepcionada() {
                return cantidadRecepcionada;
            }

            /**
             * Define el valor de la propiedad cantidadRecepcionada.
             * 
             */
            public void setCantidadRecepcionada(float value) {
                this.cantidadRecepcionada = value;
            }

            /**
             * Obtiene el valor de la propiedad costoLista.
             * 
             */
            public double getCostoLista() {
                return costoLista;
            }

            /**
             * Define el valor de la propiedad costoLista.
             * 
             */
            public void setCostoLista(double value) {
                this.costoLista = value;
            }

            /**
             * Obtiene el valor de la propiedad costoFinal.
             * 
             */
            public double getCostoFinal() {
                return costoFinal;
            }

            /**
             * Define el valor de la propiedad costoFinal.
             * 
             */
            public void setCostoFinal(double value) {
                this.costoFinal = value;
            }

        }

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
     *         &lt;element name="senderIdentification" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="recipientIdentification" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="correlativeVendor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="messageReferenceNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="countrycode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="buyerCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="buyerLocationCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "senderIdentification",
        "recipientIdentification",
        "correlativeVendor",
        "messageReferenceNumber",
        "countrycode",
        "buyerCode",
        "buyerLocationCode"
    })
    public static class EdiData {

        @XmlElement(required = true)
        protected String senderIdentification;
        @XmlElement(required = true)
        protected String recipientIdentification;
        @XmlElement(required = true)
        protected String correlativeVendor;
        @XmlElement(required = true)
        protected String messageReferenceNumber;
        @XmlElement(required = true)
        protected String countrycode;
        @XmlElement(required = true)
        protected String buyerCode;
        @XmlElement(required = true)
        protected String buyerLocationCode;

        /**
         * Obtiene el valor de la propiedad senderIdentification.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSenderIdentification() {
            return senderIdentification;
        }

        /**
         * Define el valor de la propiedad senderIdentification.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSenderIdentification(String value) {
            this.senderIdentification = value;
        }

        /**
         * Obtiene el valor de la propiedad recipientIdentification.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecipientIdentification() {
            return recipientIdentification;
        }

        /**
         * Define el valor de la propiedad recipientIdentification.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecipientIdentification(String value) {
            this.recipientIdentification = value;
        }

        /**
         * Obtiene el valor de la propiedad correlativeVendor.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCorrelativeVendor() {
            return correlativeVendor;
        }

        /**
         * Define el valor de la propiedad correlativeVendor.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCorrelativeVendor(String value) {
            this.correlativeVendor = value;
        }

        /**
         * Obtiene el valor de la propiedad messageReferenceNumber.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMessageReferenceNumber() {
            return messageReferenceNumber;
        }

        /**
         * Define el valor de la propiedad messageReferenceNumber.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMessageReferenceNumber(String value) {
            this.messageReferenceNumber = value;
        }

        /**
         * Obtiene el valor de la propiedad countrycode.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCountrycode() {
            return countrycode;
        }

        /**
         * Define el valor de la propiedad countrycode.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCountrycode(String value) {
            this.countrycode = value;
        }

        /**
         * Obtiene el valor de la propiedad buyerCode.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBuyerCode() {
            return buyerCode;
        }

        /**
         * Define el valor de la propiedad buyerCode.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBuyerCode(String value) {
            this.buyerCode = value;
        }

        /**
         * Obtiene el valor de la propiedad buyerLocationCode.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBuyerLocationCode() {
            return buyerLocationCode;
        }

        /**
         * Define el valor de la propiedad buyerLocationCode.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBuyerLocationCode(String value) {
            this.buyerLocationCode = value;
        }

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
     *         &lt;element name="local"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="cod" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ean" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "local"
    })
    public static class Locales {

        @XmlElement(required = true)
        protected List<Recepcion.Locales.Local> local;

        /**
         * Gets the value of the local property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the local property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLocal().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Recepcion.Locales.Local }
         * 
         * 
         */
        public List<Recepcion.Locales.Local> getLocal() {
            if (local == null) {
                local = new ArrayList<Recepcion.Locales.Local>();
            }
            return this.local;
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
         *         &lt;element name="cod" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ean" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "cod",
            "nombre",
            "direccion",
            "ean"
        })
        public static class Local {

            @XmlElement(required = true)
            protected String cod;
            @XmlElement(required = true)
            protected String nombre;
            @XmlElement(required = true)
            protected String direccion;
            @XmlElement(required = true)
            protected String ean;

            /**
             * Obtiene el valor de la propiedad cod.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCod() {
                return cod;
            }

            /**
             * Define el valor de la propiedad cod.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCod(String value) {
                this.cod = value;
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
             * Obtiene el valor de la propiedad direccion.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDireccion() {
                return direccion;
            }

            /**
             * Define el valor de la propiedad direccion.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDireccion(String value) {
                this.direccion = value;
            }

            /**
             * Obtiene el valor de la propiedad ean.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEan() {
                return ean;
            }

            /**
             * Define el valor de la propiedad ean.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEan(String value) {
                this.ean = value;
            }

        }

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
     *         &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="contacto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "razonSocial",
        "contacto",
        "direccion",
        "telefono"
    })
    public static class Vendedor {

        @XmlElement(required = true)
        protected String rut;
        @XmlElement(name = "razon_social", required = true)
        protected String razonSocial;
        @XmlElement(required = true)
        protected String contacto;
        @XmlElement(required = true)
        protected String direccion;
        @XmlElement(required = true)
        protected String telefono;

        /**
         * Obtiene el valor de la propiedad rut.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRut() {
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
        public void setRut(String value) {
            this.rut = value;
        }

        /**
         * Obtiene el valor de la propiedad razonSocial.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRazonSocial() {
            return razonSocial;
        }

        /**
         * Define el valor de la propiedad razonSocial.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRazonSocial(String value) {
            this.razonSocial = value;
        }

        /**
         * Obtiene el valor de la propiedad contacto.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContacto() {
            return contacto;
        }

        /**
         * Define el valor de la propiedad contacto.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContacto(String value) {
            this.contacto = value;
        }

        /**
         * Obtiene el valor de la propiedad direccion.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDireccion() {
            return direccion;
        }

        /**
         * Define el valor de la propiedad direccion.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDireccion(String value) {
            this.direccion = value;
        }

        /**
         * Obtiene el valor de la propiedad telefono.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTelefono() {
            return telefono;
        }

        /**
         * Define el valor de la propiedad telefono.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTelefono(String value) {
            this.telefono = value;
        }

    }

}
