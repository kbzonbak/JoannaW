
package corp.cencosud.xmlns.core.ebm.common.ebm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the corp.cencosud.xmlns.core.ebm.common.ebm package. 
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

    private final static QName _SenderTypeLegalEntity_QNAME = new QName("http://xmlns.cencosud.corp/Core/EBM/Common/EBM", "LegalEntity");
    private final static QName _EBMAddressingTypeCorrelID_QNAME = new QName("http://xmlns.cencosud.corp/Core/EBM/Common/EBM", "CorrelID");
    private final static QName _EBMAddressingTypeReplyToAddress_QNAME = new QName("http://xmlns.cencosud.corp/Core/EBM/Common/EBM", "ReplyToAddress");
    private final static QName _EBMTrackingTypeReferenceID_QNAME = new QName("http://xmlns.cencosud.corp/Core/EBM/Common/EBM", "ReferenceID");
    private final static QName _EBMTrackingTypeParentEBMID_QNAME = new QName("http://xmlns.cencosud.corp/Core/EBM/Common/EBM", "ParentEBMID");
    private final static QName _EBMTrackingTypeIntegrationCode_QNAME = new QName("http://xmlns.cencosud.corp/Core/EBM/Common/EBM", "IntegrationCode");
    private final static QName _ResponseEBMTypeErrorCode_QNAME = new QName("http://xmlns.cencosud.corp/Core/EBM/Common/EBM", "ErrorCode");
    private final static QName _ErrorDetail_QNAME = new QName("http://xmlns.cencosud.corp/Core/EBM/Common/EBM", "ErrorDetail");
    private final static QName _EBMHeader_QNAME = new QName("http://xmlns.cencosud.corp/Core/EBM/Common/EBM", "EBMHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: corp.cencosud.xmlns.core.ebm.common.ebm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SenderType }
     * 
     */
    public SenderType createSenderType() {
        return new SenderType();
    }

    /**
     * Create an instance of {@link EBMAddressingType }
     * 
     */
    public EBMAddressingType createEBMAddressingType() {
        return new EBMAddressingType();
    }

    /**
     * Create an instance of {@link EBMHeaderType }
     * 
     */
    public EBMHeaderType createEBMHeaderType() {
        return new EBMHeaderType();
    }

    /**
     * Create an instance of {@link ErrorDetailType }
     * 
     */
    public ErrorDetailType createErrorDetailType() {
        return new ErrorDetailType();
    }

    /**
     * Create an instance of {@link EBMType }
     * 
     */
    public EBMType createEBMType() {
        return new EBMType();
    }

    /**
     * Create an instance of {@link EBMTrackingType }
     * 
     */
    public EBMTrackingType createEBMTrackingType() {
        return new EBMTrackingType();
    }

    /**
     * Create an instance of {@link ResponseEBMType }
     * 
     */
    public ResponseEBMType createResponseEBMType() {
        return new ResponseEBMType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", name = "LegalEntity", scope = SenderType.class)
    public JAXBElement<String> createSenderTypeLegalEntity(String value) {
        return new JAXBElement<String>(_SenderTypeLegalEntity_QNAME, String.class, SenderType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", name = "CorrelID", scope = EBMAddressingType.class)
    public JAXBElement<String> createEBMAddressingTypeCorrelID(String value) {
        return new JAXBElement<String>(_EBMAddressingTypeCorrelID_QNAME, String.class, EBMAddressingType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", name = "ReplyToAddress", scope = EBMAddressingType.class)
    public JAXBElement<String> createEBMAddressingTypeReplyToAddress(String value) {
        return new JAXBElement<String>(_EBMAddressingTypeReplyToAddress_QNAME, String.class, EBMAddressingType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", name = "ReferenceID", scope = EBMTrackingType.class)
    public JAXBElement<String> createEBMTrackingTypeReferenceID(String value) {
        return new JAXBElement<String>(_EBMTrackingTypeReferenceID_QNAME, String.class, EBMTrackingType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", name = "ParentEBMID", scope = EBMTrackingType.class)
    public JAXBElement<String> createEBMTrackingTypeParentEBMID(String value) {
        return new JAXBElement<String>(_EBMTrackingTypeParentEBMID_QNAME, String.class, EBMTrackingType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", name = "IntegrationCode", scope = EBMTrackingType.class)
    public JAXBElement<String> createEBMTrackingTypeIntegrationCode(String value) {
        return new JAXBElement<String>(_EBMTrackingTypeIntegrationCode_QNAME, String.class, EBMTrackingType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", name = "ErrorCode", scope = ResponseEBMType.class)
    public JAXBElement<String> createResponseEBMTypeErrorCode(String value) {
        return new JAXBElement<String>(_ResponseEBMTypeErrorCode_QNAME, String.class, ResponseEBMType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorDetailType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", name = "ErrorDetail")
    public JAXBElement<ErrorDetailType> createErrorDetail(ErrorDetailType value) {
        return new JAXBElement<ErrorDetailType>(_ErrorDetail_QNAME, ErrorDetailType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EBMHeaderType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", name = "EBMHeader")
    public JAXBElement<EBMHeaderType> createEBMHeader(EBMHeaderType value) {
        return new JAXBElement<EBMHeaderType>(_EBMHeader_QNAME, EBMHeaderType.class, null, value);
    }

}
