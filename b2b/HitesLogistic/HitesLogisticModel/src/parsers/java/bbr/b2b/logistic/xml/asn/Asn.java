//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.02.03 a las 03:15:00 PM CLST 
//


package bbr.b2b.logistic.xml.asn;

import java.math.BigDecimal;
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
 *         &lt;element name="accion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="num_cita_b2b" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="cod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cod_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fecha_cita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="num_asn" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="bultos"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="detalle_bulto" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="cod_producto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cod_local_destino" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cod_lpn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="num_documento" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="tipo_documento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="unidades_comprometidas" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
    "accion",
    "numCitaB2B",
    "codProveedor",
    "codLocalEntrega",
    "fechaCita",
    "numAsn",
    "bultos"
})
@XmlRootElement(name = "asn")
public class Asn {

    @XmlElement(required = true)
    protected String accion;
    @XmlElement(name = "num_cita_b2b")
    protected int numCitaB2B;
    @XmlElement(name = "cod_proveedor", required = true)
    protected String codProveedor;
    @XmlElement(name = "cod_local_entrega", required = true)
    protected String codLocalEntrega;
    @XmlElement(name = "fecha_cita", required = true)
    protected String fechaCita;
    @XmlElement(name = "num_asn")
    protected int numAsn;
    @XmlElement(required = true)
    protected Asn.Bultos bultos;

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
     * Obtiene el valor de la propiedad numCitaB2B.
     * 
     */
    public int getNumCitaB2B() {
        return numCitaB2B;
    }

    /**
     * Define el valor de la propiedad numCitaB2B.
     * 
     */
    public void setNumCitaB2B(int value) {
        this.numCitaB2B = value;
    }

    /**
     * Obtiene el valor de la propiedad codProveedor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodProveedor() {
        return codProveedor;
    }

    /**
     * Define el valor de la propiedad codProveedor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodProveedor(String value) {
        this.codProveedor = value;
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
     * Obtiene el valor de la propiedad fechaCita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaCita() {
        return fechaCita;
    }

    /**
     * Define el valor de la propiedad fechaCita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaCita(String value) {
        this.fechaCita = value;
    }

    /**
     * Obtiene el valor de la propiedad numAsn.
     * 
     */
    public int getNumAsn() {
        return numAsn;
    }

    /**
     * Define el valor de la propiedad numAsn.
     * 
     */
    public void setNumAsn(int value) {
        this.numAsn = value;
    }

    /**
     * Obtiene el valor de la propiedad bultos.
     * 
     * @return
     *     possible object is
     *     {@link Asn.Bultos }
     *     
     */
    public Asn.Bultos getBultos() {
        return bultos;
    }

    /**
     * Define el valor de la propiedad bultos.
     * 
     * @param value
     *     allowed object is
     *     {@link Asn.Bultos }
     *     
     */
    public void setBultos(Asn.Bultos value) {
        this.bultos = value;
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
     *         &lt;element name="detalle_bulto" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="cod_producto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cod_local_destino" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cod_lpn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="num_documento" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="tipo_documento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="unidades_comprometidas" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
        "detalleBulto"
    })
    public static class Bultos {

        @XmlElement(name = "detalle_bulto", required = true)
        protected List<Asn.Bultos.DetalleBulto> detalleBulto;

        /**
         * Gets the value of the detalleBulto property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the detalleBulto property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDetalleBulto().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Asn.Bultos.DetalleBulto }
         * 
         * 
         */
        public List<Asn.Bultos.DetalleBulto> getDetalleBulto() {
            if (detalleBulto == null) {
                detalleBulto = new ArrayList<Asn.Bultos.DetalleBulto>();
            }
            return this.detalleBulto;
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
         *         &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="cod_producto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cod_local_destino" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cod_lpn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="num_documento" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="tipo_documento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="unidades_comprometidas" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
            "numOc",
            "posicion",
            "codProducto",
            "codLocalDestino",
            "codLpn",
            "numDocumento",
            "tipoDocumento",
            "unidadesComprometidas"
        })
        public static class DetalleBulto {

            @XmlElement(name = "num_oc")
            protected int numOc;
            protected int posicion;
            @XmlElement(name = "cod_producto", required = true)
            protected String codProducto;
            @XmlElement(name = "cod_local_destino", required = true)
            protected String codLocalDestino;
            @XmlElement(name = "cod_lpn", required = true)
            protected String codLpn;
            @XmlElement(name = "num_documento")
            protected int numDocumento;
            @XmlElement(name = "tipo_documento", required = true)
            protected String tipoDocumento;
            @XmlElement(name = "unidades_comprometidas", required = true)
            protected BigDecimal unidadesComprometidas;

            /**
             * Obtiene el valor de la propiedad numOc.
             * 
             */
            public int getNumOc() {
                return numOc;
            }

            /**
             * Define el valor de la propiedad numOc.
             * 
             */
            public void setNumOc(int value) {
                this.numOc = value;
            }

            /**
             * Obtiene el valor de la propiedad posicion.
             * 
             */
            public int getPosicion() {
                return posicion;
            }

            /**
             * Define el valor de la propiedad posicion.
             * 
             */
            public void setPosicion(int value) {
                this.posicion = value;
            }

            /**
             * Obtiene el valor de la propiedad codProducto.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodProducto() {
                return codProducto;
            }

            /**
             * Define el valor de la propiedad codProducto.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodProducto(String value) {
                this.codProducto = value;
            }

            /**
             * Obtiene el valor de la propiedad codLocalDestino.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodLocalDestino() {
                return codLocalDestino;
            }

            /**
             * Define el valor de la propiedad codLocalDestino.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodLocalDestino(String value) {
                this.codLocalDestino = value;
            }

            /**
             * Obtiene el valor de la propiedad codLpn.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodLpn() {
                return codLpn;
            }

            /**
             * Define el valor de la propiedad codLpn.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodLpn(String value) {
                this.codLpn = value;
            }

            /**
             * Obtiene el valor de la propiedad numDocumento.
             * 
             */
            public int getNumDocumento() {
                return numDocumento;
            }

            /**
             * Define el valor de la propiedad numDocumento.
             * 
             */
            public void setNumDocumento(int value) {
                this.numDocumento = value;
            }

            /**
             * Obtiene el valor de la propiedad tipoDocumento.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipoDocumento() {
                return tipoDocumento;
            }

            /**
             * Define el valor de la propiedad tipoDocumento.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipoDocumento(String value) {
                this.tipoDocumento = value;
            }

            /**
             * Obtiene el valor de la propiedad unidadesComprometidas.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getUnidadesComprometidas() {
                return unidadesComprometidas;
            }

            /**
             * Define el valor de la propiedad unidadesComprometidas.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setUnidadesComprometidas(BigDecimal value) {
                this.unidadesComprometidas = value;
            }

        }

    }

}
