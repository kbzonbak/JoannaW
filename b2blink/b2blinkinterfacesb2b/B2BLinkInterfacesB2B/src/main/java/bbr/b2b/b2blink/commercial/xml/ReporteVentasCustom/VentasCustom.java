//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.03.09 a las 09:01:41 AM CLT 
//


package bbr.b2b.b2blink.commercial.xml.ReporteVentasCustom;

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
 *         &lt;element name="flowid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nombreportal" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="formato" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "flowid",
    "nombreportal",
    "comprador",
    "vendedor",
    "formato",
    "data"
})
@XmlRootElement(name = "VentasCustom")
public class VentasCustom {

    protected String flowid;
    @XmlElement(required = true)
    protected String nombreportal;
    @XmlElement(required = true)
    protected VentasCustom.Comprador comprador;
    @XmlElement(required = true)
    protected VentasCustom.Vendedor vendedor;
    @XmlElement(required = true)
    protected String formato;
    @XmlElement(required = true)
    protected String data;

    /**
     * Obtiene el valor de la propiedad flowid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlowid() {
        return flowid;
    }

    /**
     * Define el valor de la propiedad flowid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlowid(String value) {
        this.flowid = value;
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
     * Obtiene el valor de la propiedad comprador.
     * 
     * @return
     *     possible object is
     *     {@link VentasCustom.Comprador }
     *     
     */
    public VentasCustom.Comprador getComprador() {
        return comprador;
    }

    /**
     * Define el valor de la propiedad comprador.
     * 
     * @param value
     *     allowed object is
     *     {@link VentasCustom.Comprador }
     *     
     */
    public void setComprador(VentasCustom.Comprador value) {
        this.comprador = value;
    }

    /**
     * Obtiene el valor de la propiedad vendedor.
     * 
     * @return
     *     possible object is
     *     {@link VentasCustom.Vendedor }
     *     
     */
    public VentasCustom.Vendedor getVendedor() {
        return vendedor;
    }

    /**
     * Define el valor de la propiedad vendedor.
     * 
     * @param value
     *     allowed object is
     *     {@link VentasCustom.Vendedor }
     *     
     */
    public void setVendedor(VentasCustom.Vendedor value) {
        this.vendedor = value;
    }

    /**
     * Obtiene el valor de la propiedad formato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormato() {
        return formato;
    }

    /**
     * Define el valor de la propiedad formato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormato(String value) {
        this.formato = value;
    }

    /**
     * Obtiene el valor de la propiedad data.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getData() {
        return data;
    }

    /**
     * Define el valor de la propiedad data.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setData(String value) {
        this.data = value;
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
     *       &lt;sequence&gt;
     *         &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "razonSocial"
    })
    public static class Vendedor {

        @XmlElement(required = true)
        protected String rut;
        @XmlElement(name = "razon_social", required = true)
        protected String razonSocial;

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

    }

}
