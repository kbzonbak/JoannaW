//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.03.09 a las 09:01:39 AM CLT 
//


package bbr.b2b.b2blink.logistic.xml.SolicitudOrdenes;

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
 *         &lt;element name="codproveedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codcomprador" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nombreportal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fecha_activacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="estadoSoa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="parametro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="documentos" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element name="documento"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "codproveedor",
    "codcomprador",
    "nombreportal",
    "fechaActivacion",
    "estadoSoa",
    "parametro",
    "documentos"
})
@XmlRootElement(name = "SolicitudOrdenes")
public class SolicitudOrdenes {

    @XmlElement(required = true)
    protected String codproveedor;
    @XmlElement(required = true)
    protected String codcomprador;
    protected String nombreportal;
    @XmlElement(name = "fecha_activacion")
    protected String fechaActivacion;
    protected String estadoSoa;
    protected String parametro;
    protected SolicitudOrdenes.Documentos documentos;

    /**
     * Obtiene el valor de la propiedad codproveedor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodproveedor() {
        return codproveedor;
    }

    /**
     * Define el valor de la propiedad codproveedor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodproveedor(String value) {
        this.codproveedor = value;
    }

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
     * Obtiene el valor de la propiedad estadoSoa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstadoSoa() {
        return estadoSoa;
    }

    /**
     * Define el valor de la propiedad estadoSoa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstadoSoa(String value) {
        this.estadoSoa = value;
    }

    /**
     * Obtiene el valor de la propiedad parametro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParametro() {
        return parametro;
    }

    /**
     * Define el valor de la propiedad parametro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParametro(String value) {
        this.parametro = value;
    }

    /**
     * Obtiene el valor de la propiedad documentos.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudOrdenes.Documentos }
     *     
     */
    public SolicitudOrdenes.Documentos getDocumentos() {
        return documentos;
    }

    /**
     * Define el valor de la propiedad documentos.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudOrdenes.Documentos }
     *     
     */
    public void setDocumentos(SolicitudOrdenes.Documentos value) {
        this.documentos = value;
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
     *         &lt;element name="documento"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "documento"
    })
    public static class Documentos {

        @XmlElement(required = true)
        protected List<SolicitudOrdenes.Documentos.Documento> documento;

        /**
         * Gets the value of the documento property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the documento property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDocumento().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SolicitudOrdenes.Documentos.Documento }
         * 
         * 
         */
        public List<SolicitudOrdenes.Documentos.Documento> getDocumento() {
            if (documento == null) {
                documento = new ArrayList<SolicitudOrdenes.Documentos.Documento>();
            }
            return this.documento;
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
         *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "id"
        })
        public static class Documento {

            @XmlElement(required = true)
            protected String id;

            /**
             * Obtiene el valor de la propiedad id.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getId() {
                return id;
            }

            /**
             * Define el valor de la propiedad id.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setId(String value) {
                this.id = value;
            }

        }

    }

}
