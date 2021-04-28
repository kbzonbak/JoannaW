//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.02.26 a las 02:30:47 PM CLST 
//


package bbr.b2b.logistic.xml.reception;

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
 *         &lt;element name="num_cita" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="num_asn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="num_recepcion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="fecha_recepcion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="comentario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="detalles"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="detalle" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="cod_producto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cod_local_destino" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cantidades_umc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
    "numCita",
    "numAsn",
    "numRecepcion",
    "fechaRecepcion",
    "estado",
    "comentario",
    "detalles"
})
@XmlRootElement(name = "recepcion")
public class Recepcion {

    @XmlElement(name = "num_cita")
    protected Integer numCita;
    @XmlElement(name = "num_asn", required = true)
    protected String numAsn;
    @XmlElement(name = "num_recepcion")
    protected Integer numRecepcion;
    @XmlElement(name = "fecha_recepcion", required = true)
    protected String fechaRecepcion;
    protected int estado;
    protected String comentario;
    @XmlElement(required = true)
    protected Recepcion.Detalles detalles;

    /**
     * Obtiene el valor de la propiedad numCita.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumCita() {
        return numCita;
    }

    /**
     * Define el valor de la propiedad numCita.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumCita(Integer value) {
        this.numCita = value;
    }

    /**
     * Obtiene el valor de la propiedad numAsn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumAsn() {
        return numAsn;
    }

    /**
     * Define el valor de la propiedad numAsn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumAsn(String value) {
        this.numAsn = value;
    }

    /**
     * Obtiene el valor de la propiedad numRecepcion.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumRecepcion() {
        return numRecepcion;
    }

    /**
     * Define el valor de la propiedad numRecepcion.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumRecepcion(Integer value) {
        this.numRecepcion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaRecepcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    /**
     * Define el valor de la propiedad fechaRecepcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaRecepcion(String value) {
        this.fechaRecepcion = value;
    }

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     */
    public int getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     */
    public void setEstado(int value) {
        this.estado = value;
    }

    /**
     * Obtiene el valor de la propiedad comentario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Define el valor de la propiedad comentario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComentario(String value) {
        this.comentario = value;
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
     *         &lt;element name="detalle" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="cod_producto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cod_local_destino" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cantidades_umc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
         *         &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="cod_producto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cod_local_destino" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cantidades_umc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
            "cantidadesUmc"
        })
        public static class Detalle {

            @XmlElement(name = "num_oc")
            protected int numOc;
            protected int posicion;
            @XmlElement(name = "cod_producto", required = true)
            protected String codProducto;
            @XmlElement(name = "cod_local_destino", required = true)
            protected String codLocalDestino;
            @XmlElement(name = "cantidades_umc", required = true)
            protected BigDecimal cantidadesUmc;

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
             * Obtiene el valor de la propiedad cantidadesUmc.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getCantidadesUmc() {
                return cantidadesUmc;
            }

            /**
             * Define el valor de la propiedad cantidadesUmc.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setCantidadesUmc(BigDecimal value) {
                this.cantidadesUmc = value;
            }

        }

    }

}
