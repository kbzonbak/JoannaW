//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.08 a las 02:24:51 PM CLT 
//


package bbr.b2b.soa.soap.webservices.classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para initParamCSDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="initParamCSDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="accesscode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="companyname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="credenciales" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "initParamCSDTO", propOrder = {
    "accesscode",
    "companyname",
    "credenciales"
})
public class InitParamCSDTO {

    protected String accesscode;
    protected String companyname;
    protected String credenciales;

    /**
     * Obtiene el valor de la propiedad accesscode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccesscode() {
        return accesscode;
    }

    /**
     * Define el valor de la propiedad accesscode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccesscode(String value) {
        this.accesscode = value;
    }

    /**
     * Obtiene el valor de la propiedad companyname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyname() {
        return companyname;
    }

    /**
     * Define el valor de la propiedad companyname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyname(String value) {
        this.companyname = value;
    }

    /**
     * Obtiene el valor de la propiedad credenciales.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCredenciales() {
        return credenciales;
    }

    /**
     * Define el valor de la propiedad credenciales.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCredenciales(String value) {
        this.credenciales = value;
    }

}
