//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.03.09 a las 09:01:39 AM CLT 
//


package bbr.b2b.b2blink.logistic.xml.OC_Interno;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para tipo_cargo_descuento.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="tipo_cargo_descuento"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="monto"/&gt;
 *     &lt;enumeration value="porcentaje"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "tipo_cargo_descuento")
@XmlEnum
public enum TipoCargoDescuento {

    @XmlEnumValue("monto")
    MONTO("monto"),
    @XmlEnumValue("porcentaje")
    PORCENTAJE("porcentaje");
    private final String value;

    TipoCargoDescuento(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoCargoDescuento fromValue(String v) {
        for (TipoCargoDescuento c: TipoCargoDescuento.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
