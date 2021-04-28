//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2019.06.24 a las 09:55:48 AM CLT 
//


package bbr.b2b.b2blink.commercial.xml.SolicitudInvenarioRecPendientes;

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
 *         &lt;element name="codcomprador" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nombreportal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *                             &lt;element name="fecha_activacion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="tiempoMaximoFinFlujo" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "codcomprador",
    "nombreportal",
    "proveedores"
})
@XmlRootElement(name = "SolicitudProveedoresInventarioPendientes")
public class SolicitudProveedoresInventarioPendientes {

    @XmlElement(required = true)
    protected String codcomprador;
    protected String nombreportal;
    @XmlElement(name = "Proveedores", required = true)
    protected SolicitudProveedoresInventarioPendientes.Proveedores proveedores;

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
     * Obtiene el valor de la propiedad proveedores.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudProveedoresInventarioPendientes.Proveedores }
     *     
     */
    public SolicitudProveedoresInventarioPendientes.Proveedores getProveedores() {
        return proveedores;
    }

    /**
     * Define el valor de la propiedad proveedores.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudProveedoresInventarioPendientes.Proveedores }
     *     
     */
    public void setProveedores(SolicitudProveedoresInventarioPendientes.Proveedores value) {
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
     *                   &lt;element name="fecha_activacion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="tiempoMaximoFinFlujo" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
        protected List<SolicitudProveedoresInventarioPendientes.Proveedores.Proveedor> proveedor;

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
         * {@link SolicitudProveedoresInventarioPendientes.Proveedores.Proveedor }
         * 
         * 
         */
        public List<SolicitudProveedoresInventarioPendientes.Proveedores.Proveedor> getProveedor() {
            if (proveedor == null) {
                proveedor = new ArrayList<SolicitudProveedoresInventarioPendientes.Proveedores.Proveedor>();
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
         *         &lt;element name="fecha_activacion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="tiempoMaximoFinFlujo" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
            "fechaActivacion",
            "tiempoMaximoFinFlujo"
        })
        public static class Proveedor {

            @XmlElement(required = true)
            protected String codigo;
            @XmlElement(name = "fecha_activacion", required = true)
            protected String fechaActivacion;
            protected int tiempoMaximoFinFlujo;

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
             * Obtiene el valor de la propiedad fechaActivacion.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFechaActivacion() {
                return fechaActivacion;
            }

            /**
             * Define el valor de la propiedad fechaActivacion.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFechaActivacion(String value) {
                this.fechaActivacion = value;
            }

            /**
             * Obtiene el valor de la propiedad tiempoMaximoFinFlujo.
             * 
             */
            public int getTiempoMaximoFinFlujo() {
                return tiempoMaximoFinFlujo;
            }

            /**
             * Define el valor de la propiedad tiempoMaximoFinFlujo.
             * 
             */
            public void setTiempoMaximoFinFlujo(int value) {
                this.tiempoMaximoFinFlujo = value;
            }

        }

    }

}
