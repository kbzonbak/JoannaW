//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.10.07 a las 04:17:20 PM CLST 
//


package bbr.b2b.b2blink.logistic.xml.OC_Fullkom;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bbr.b2b.b2blink.logistic.xml.OC_Fullkom package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bbr.b2b.b2blink.logistic.xml.OC_Fullkom
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Order }
     * 
     */
    public Order createOrder() {
        return new Order();
    }

    /**
     * Create an instance of {@link Order.Details }
     * 
     */
    public Order.Details createOrderDetails() {
        return new Order.Details();
    }

    /**
     * Create an instance of {@link Order.Details.Detail }
     * 
     */
    public Order.Details.Detail createOrderDetailsDetail() {
        return new Order.Details.Detail();
    }

    /**
     * Create an instance of {@link Order.Details.Detail.Predistributions }
     * 
     */
    public Order.Details.Detail.Predistributions createOrderDetailsDetailPredistributions() {
        return new Order.Details.Detail.Predistributions();
    }

    /**
     * Create an instance of {@link Local }
     * 
     */
    public Local createLocal() {
        return new Local();
    }

    /**
     * Create an instance of {@link Order.Section }
     * 
     */
    public Order.Section createOrderSection() {
        return new Order.Section();
    }

    /**
     * Create an instance of {@link Order.Action }
     * 
     */
    public Order.Action createOrderAction() {
        return new Order.Action();
    }

    /**
     * Create an instance of {@link Order.Client }
     * 
     */
    public Order.Client createOrderClient() {
        return new Order.Client();
    }

    /**
     * Create an instance of {@link Order.Discounts }
     * 
     */
    public Order.Discounts createOrderDiscounts() {
        return new Order.Discounts();
    }

    /**
     * Create an instance of {@link Order.Charges }
     * 
     */
    public Order.Charges createOrderCharges() {
        return new Order.Charges();
    }

    /**
     * Create an instance of {@link DiscountCharge }
     * 
     */
    public DiscountCharge createDiscountCharge() {
        return new DiscountCharge();
    }

    /**
     * Create an instance of {@link Order.Details.Detail.Discounts }
     * 
     */
    public Order.Details.Detail.Discounts createOrderDetailsDetailDiscounts() {
        return new Order.Details.Detail.Discounts();
    }

    /**
     * Create an instance of {@link Order.Details.Detail.Charges }
     * 
     */
    public Order.Details.Detail.Charges createOrderDetailsDetailCharges() {
        return new Order.Details.Detail.Charges();
    }

    /**
     * Create an instance of {@link Order.Details.Detail.Predistributions.Predistribution }
     * 
     */
    public Order.Details.Detail.Predistributions.Predistribution createOrderDetailsDetailPredistributionsPredistribution() {
        return new Order.Details.Detail.Predistributions.Predistribution();
    }

}
