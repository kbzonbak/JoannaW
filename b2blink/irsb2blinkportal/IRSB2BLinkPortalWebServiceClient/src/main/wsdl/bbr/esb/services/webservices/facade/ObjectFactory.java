
package bbr.esb.services.webservices.facade;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bbr.esb.services.webservices.facade package. 
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

    private final static QName _GetTickesStatesResponse_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getTickesStatesResponse");
    private final static QName _GetServicesResponse_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getServicesResponse");
    private final static QName _GetScoreCardTableOfCompany_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getScoreCardTableOfCompany");
    private final static QName _DoAddServiceEventByContracted_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "doAddServiceEventByContracted");
    private final static QName _GetRequestFilter_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getRequestFilter");
    private final static QName _NotFoundException_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "NotFoundException");
    private final static QName _GetScoreCardTableOfCompanyResponse_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getScoreCardTableOfCompanyResponse");
    private final static QName _GetRequestFilterResponse_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getRequestFilterResponse");
    private final static QName _GetCompanies_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getCompanies");
    private final static QName _GetScoreCardOfCompany_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getScoreCardOfCompany");
    private final static QName _GetServices_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getServices");
    private final static QName _OperationFailedException_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "OperationFailedException");
    private final static QName _AccessDeniedException_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "AccessDeniedException");
    private final static QName _GetAvailableSite_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getAvailableSite");
    private final static QName _GetSites_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getSites");
    private final static QName _GetTicketReportResponse_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getTicketReportResponse");
    private final static QName _DoAddServiceEventByContractedResponse_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "doAddServiceEventByContractedResponse");
    private final static QName _GetAvailableSiteResponse_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getAvailableSiteResponse");
    private final static QName _GetCompaniesResponse_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getCompaniesResponse");
    private final static QName _GetScoreCardOfCompanyResponse_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getScoreCardOfCompanyResponse");
    private final static QName _GetTicketReport_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getTicketReport");
    private final static QName _GetSitesResponse_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getSitesResponse");
    private final static QName _GetTickesStates_QNAME = new QName("http://facade.webservices.services.esb.bbr/", "getTickesStates");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bbr.esb.services.webservices.facade
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TicketStateTypeDTO }
     * 
     */
    public TicketStateTypeDTO createTicketStateTypeDTO() {
        return new TicketStateTypeDTO();
    }

    /**
     * Create an instance of {@link NotFoundException }
     * 
     */
    public NotFoundException createNotFoundException() {
        return new NotFoundException();
    }

    /**
     * Create an instance of {@link ServiceDTO }
     * 
     */
    public ServiceDTO createServiceDTO() {
        return new ServiceDTO();
    }

    /**
     * Create an instance of {@link PageInfoDTO }
     * 
     */
    public PageInfoDTO createPageInfoDTO() {
        return new PageInfoDTO();
    }

    /**
     * Create an instance of {@link OrderCriteriaDTO }
     * 
     */
    public OrderCriteriaDTO createOrderCriteriaDTO() {
        return new OrderCriteriaDTO();
    }

    /**
     * Create an instance of {@link AccessDeniedException }
     * 
     */
    public AccessDeniedException createAccessDeniedException() {
        return new AccessDeniedException();
    }

    /**
     * Create an instance of {@link SiteDTO }
     * 
     */
    public SiteDTO createSiteDTO() {
        return new SiteDTO();
    }

    /**
     * Create an instance of {@link OperationFailedException }
     * 
     */
    public OperationFailedException createOperationFailedException() {
        return new OperationFailedException();
    }

    /**
     * Create an instance of {@link TicketStateTypeFilterDTO }
     * 
     */
    public TicketStateTypeFilterDTO createTicketStateTypeFilterDTO() {
        return new TicketStateTypeFilterDTO();
    }

    /**
     * Create an instance of {@link GetScoreCardOfCompanyResponse }
     * 
     */
    public GetScoreCardOfCompanyResponse createGetScoreCardOfCompanyResponse() {
        return new GetScoreCardOfCompanyResponse();
    }

    /**
     * Create an instance of {@link GetRequestFilterResponse }
     * 
     */
    public GetRequestFilterResponse createGetRequestFilterResponse() {
        return new GetRequestFilterResponse();
    }

    /**
     * Create an instance of {@link TicketReportResultDTO }
     * 
     */
    public TicketReportResultDTO createTicketReportResultDTO() {
        return new TicketReportResultDTO();
    }

    /**
     * Create an instance of {@link SitesFilterDTO }
     * 
     */
    public SitesFilterDTO createSitesFilterDTO() {
        return new SitesFilterDTO();
    }

    /**
     * Create an instance of {@link GetScoreCardTableOfCompany }
     * 
     */
    public GetScoreCardTableOfCompany createGetScoreCardTableOfCompany() {
        return new GetScoreCardTableOfCompany();
    }

    /**
     * Create an instance of {@link GetScoreCardOfCompany }
     * 
     */
    public GetScoreCardOfCompany createGetScoreCardOfCompany() {
        return new GetScoreCardOfCompany();
    }

    /**
     * Create an instance of {@link GetCompanies }
     * 
     */
    public GetCompanies createGetCompanies() {
        return new GetCompanies();
    }

    /**
     * Create an instance of {@link ScoreCardSiteFilterDTO }
     * 
     */
    public ScoreCardSiteFilterDTO createScoreCardSiteFilterDTO() {
        return new ScoreCardSiteFilterDTO();
    }

    /**
     * Create an instance of {@link ScoreCardTableDTO }
     * 
     */
    public ScoreCardTableDTO createScoreCardTableDTO() {
        return new ScoreCardTableDTO();
    }

    /**
     * Create an instance of {@link GetSites }
     * 
     */
    public GetSites createGetSites() {
        return new GetSites();
    }

    /**
     * Create an instance of {@link DoAddServiceEventByContracted }
     * 
     */
    public DoAddServiceEventByContracted createDoAddServiceEventByContracted() {
        return new DoAddServiceEventByContracted();
    }

    /**
     * Create an instance of {@link DocumentsToResendInitParamDTO }
     * 
     */
    public DocumentsToResendInitParamDTO createDocumentsToResendInitParamDTO() {
        return new DocumentsToResendInitParamDTO();
    }

    /**
     * Create an instance of {@link GetServicesResponse }
     * 
     */
    public GetServicesResponse createGetServicesResponse() {
        return new GetServicesResponse();
    }

    /**
     * Create an instance of {@link GetTickesStates }
     * 
     */
    public GetTickesStates createGetTickesStates() {
        return new GetTickesStates();
    }

    /**
     * Create an instance of {@link SiteProgress }
     * 
     */
    public SiteProgress createSiteProgress() {
        return new SiteProgress();
    }

    /**
     * Create an instance of {@link CompanyDTO }
     * 
     */
    public CompanyDTO createCompanyDTO() {
        return new CompanyDTO();
    }

    /**
     * Create an instance of {@link ElementDTO }
     * 
     */
    public ElementDTO createElementDTO() {
        return new ElementDTO();
    }

    /**
     * Create an instance of {@link ServiceFilterDTO }
     * 
     */
    public ServiceFilterDTO createServiceFilterDTO() {
        return new ServiceFilterDTO();
    }

    /**
     * Create an instance of {@link GetAvailableSite }
     * 
     */
    public GetAvailableSite createGetAvailableSite() {
        return new GetAvailableSite();
    }

    /**
     * Create an instance of {@link GetCompaniesResponse }
     * 
     */
    public GetCompaniesResponse createGetCompaniesResponse() {
        return new GetCompaniesResponse();
    }

    /**
     * Create an instance of {@link GetAvailableSiteResponse }
     * 
     */
    public GetAvailableSiteResponse createGetAvailableSiteResponse() {
        return new GetAvailableSiteResponse();
    }

    /**
     * Create an instance of {@link DocumentToResendInitParamDTO }
     * 
     */
    public DocumentToResendInitParamDTO createDocumentToResendInitParamDTO() {
        return new DocumentToResendInitParamDTO();
    }

    /**
     * Create an instance of {@link GetSitesResponse }
     * 
     */
    public GetSitesResponse createGetSitesResponse() {
        return new GetSitesResponse();
    }

    /**
     * Create an instance of {@link BaseResultDTO }
     * 
     */
    public BaseResultDTO createBaseResultDTO() {
        return new BaseResultDTO();
    }

    /**
     * Create an instance of {@link GetRequestFilter }
     * 
     */
    public GetRequestFilter createGetRequestFilter() {
        return new GetRequestFilter();
    }

    /**
     * Create an instance of {@link GetTickesStatesResponse }
     * 
     */
    public GetTickesStatesResponse createGetTickesStatesResponse() {
        return new GetTickesStatesResponse();
    }

    /**
     * Create an instance of {@link GetScoreCardTableOfCompanyResponse }
     * 
     */
    public GetScoreCardTableOfCompanyResponse createGetScoreCardTableOfCompanyResponse() {
        return new GetScoreCardTableOfCompanyResponse();
    }

    /**
     * Create an instance of {@link ScoreCardTableOfCompanyInitParamDTO }
     * 
     */
    public ScoreCardTableOfCompanyInitParamDTO createScoreCardTableOfCompanyInitParamDTO() {
        return new ScoreCardTableOfCompanyInitParamDTO();
    }

    /**
     * Create an instance of {@link GetTicketReport }
     * 
     */
    public GetTicketReport createGetTicketReport() {
        return new GetTicketReport();
    }

    /**
     * Create an instance of {@link RequestFilterForTicketDTO }
     * 
     */
    public RequestFilterForTicketDTO createRequestFilterForTicketDTO() {
        return new RequestFilterForTicketDTO();
    }

    /**
     * Create an instance of {@link ScoreCardTableOfCompanyReturnDTO }
     * 
     */
    public ScoreCardTableOfCompanyReturnDTO createScoreCardTableOfCompanyReturnDTO() {
        return new ScoreCardTableOfCompanyReturnDTO();
    }

    /**
     * Create an instance of {@link PageInitParamDTO }
     * 
     */
    public PageInitParamDTO createPageInitParamDTO() {
        return new PageInitParamDTO();
    }

    /**
     * Create an instance of {@link TicketReportInitParamDTO }
     * 
     */
    public TicketReportInitParamDTO createTicketReportInitParamDTO() {
        return new TicketReportInitParamDTO();
    }

    /**
     * Create an instance of {@link ScoreCardDTO }
     * 
     */
    public ScoreCardDTO createScoreCardDTO() {
        return new ScoreCardDTO();
    }

    /**
     * Create an instance of {@link TicketReportDataResultDTO }
     * 
     */
    public TicketReportDataResultDTO createTicketReportDataResultDTO() {
        return new TicketReportDataResultDTO();
    }

    /**
     * Create an instance of {@link GetServices }
     * 
     */
    public GetServices createGetServices() {
        return new GetServices();
    }

    /**
     * Create an instance of {@link DoAddServiceEventByContractedResponse }
     * 
     */
    public DoAddServiceEventByContractedResponse createDoAddServiceEventByContractedResponse() {
        return new DoAddServiceEventByContractedResponse();
    }

    /**
     * Create an instance of {@link GetTicketReportResponse }
     * 
     */
    public GetTicketReportResponse createGetTicketReportResponse() {
        return new GetTicketReportResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTickesStatesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getTickesStatesResponse")
    public JAXBElement<GetTickesStatesResponse> createGetTickesStatesResponse(GetTickesStatesResponse value) {
        return new JAXBElement<GetTickesStatesResponse>(_GetTickesStatesResponse_QNAME, GetTickesStatesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServicesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getServicesResponse")
    public JAXBElement<GetServicesResponse> createGetServicesResponse(GetServicesResponse value) {
        return new JAXBElement<GetServicesResponse>(_GetServicesResponse_QNAME, GetServicesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetScoreCardTableOfCompany }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getScoreCardTableOfCompany")
    public JAXBElement<GetScoreCardTableOfCompany> createGetScoreCardTableOfCompany(GetScoreCardTableOfCompany value) {
        return new JAXBElement<GetScoreCardTableOfCompany>(_GetScoreCardTableOfCompany_QNAME, GetScoreCardTableOfCompany.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoAddServiceEventByContracted }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "doAddServiceEventByContracted")
    public JAXBElement<DoAddServiceEventByContracted> createDoAddServiceEventByContracted(DoAddServiceEventByContracted value) {
        return new JAXBElement<DoAddServiceEventByContracted>(_DoAddServiceEventByContracted_QNAME, DoAddServiceEventByContracted.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRequestFilter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getRequestFilter")
    public JAXBElement<GetRequestFilter> createGetRequestFilter(GetRequestFilter value) {
        return new JAXBElement<GetRequestFilter>(_GetRequestFilter_QNAME, GetRequestFilter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "NotFoundException")
    public JAXBElement<NotFoundException> createNotFoundException(NotFoundException value) {
        return new JAXBElement<NotFoundException>(_NotFoundException_QNAME, NotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetScoreCardTableOfCompanyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getScoreCardTableOfCompanyResponse")
    public JAXBElement<GetScoreCardTableOfCompanyResponse> createGetScoreCardTableOfCompanyResponse(GetScoreCardTableOfCompanyResponse value) {
        return new JAXBElement<GetScoreCardTableOfCompanyResponse>(_GetScoreCardTableOfCompanyResponse_QNAME, GetScoreCardTableOfCompanyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRequestFilterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getRequestFilterResponse")
    public JAXBElement<GetRequestFilterResponse> createGetRequestFilterResponse(GetRequestFilterResponse value) {
        return new JAXBElement<GetRequestFilterResponse>(_GetRequestFilterResponse_QNAME, GetRequestFilterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCompanies }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getCompanies")
    public JAXBElement<GetCompanies> createGetCompanies(GetCompanies value) {
        return new JAXBElement<GetCompanies>(_GetCompanies_QNAME, GetCompanies.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetScoreCardOfCompany }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getScoreCardOfCompany")
    public JAXBElement<GetScoreCardOfCompany> createGetScoreCardOfCompany(GetScoreCardOfCompany value) {
        return new JAXBElement<GetScoreCardOfCompany>(_GetScoreCardOfCompany_QNAME, GetScoreCardOfCompany.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServices }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getServices")
    public JAXBElement<GetServices> createGetServices(GetServices value) {
        return new JAXBElement<GetServices>(_GetServices_QNAME, GetServices.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "OperationFailedException")
    public JAXBElement<OperationFailedException> createOperationFailedException(OperationFailedException value) {
        return new JAXBElement<OperationFailedException>(_OperationFailedException_QNAME, OperationFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccessDeniedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "AccessDeniedException")
    public JAXBElement<AccessDeniedException> createAccessDeniedException(AccessDeniedException value) {
        return new JAXBElement<AccessDeniedException>(_AccessDeniedException_QNAME, AccessDeniedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailableSite }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getAvailableSite")
    public JAXBElement<GetAvailableSite> createGetAvailableSite(GetAvailableSite value) {
        return new JAXBElement<GetAvailableSite>(_GetAvailableSite_QNAME, GetAvailableSite.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSites }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getSites")
    public JAXBElement<GetSites> createGetSites(GetSites value) {
        return new JAXBElement<GetSites>(_GetSites_QNAME, GetSites.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTicketReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getTicketReportResponse")
    public JAXBElement<GetTicketReportResponse> createGetTicketReportResponse(GetTicketReportResponse value) {
        return new JAXBElement<GetTicketReportResponse>(_GetTicketReportResponse_QNAME, GetTicketReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoAddServiceEventByContractedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "doAddServiceEventByContractedResponse")
    public JAXBElement<DoAddServiceEventByContractedResponse> createDoAddServiceEventByContractedResponse(DoAddServiceEventByContractedResponse value) {
        return new JAXBElement<DoAddServiceEventByContractedResponse>(_DoAddServiceEventByContractedResponse_QNAME, DoAddServiceEventByContractedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailableSiteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getAvailableSiteResponse")
    public JAXBElement<GetAvailableSiteResponse> createGetAvailableSiteResponse(GetAvailableSiteResponse value) {
        return new JAXBElement<GetAvailableSiteResponse>(_GetAvailableSiteResponse_QNAME, GetAvailableSiteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCompaniesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getCompaniesResponse")
    public JAXBElement<GetCompaniesResponse> createGetCompaniesResponse(GetCompaniesResponse value) {
        return new JAXBElement<GetCompaniesResponse>(_GetCompaniesResponse_QNAME, GetCompaniesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetScoreCardOfCompanyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getScoreCardOfCompanyResponse")
    public JAXBElement<GetScoreCardOfCompanyResponse> createGetScoreCardOfCompanyResponse(GetScoreCardOfCompanyResponse value) {
        return new JAXBElement<GetScoreCardOfCompanyResponse>(_GetScoreCardOfCompanyResponse_QNAME, GetScoreCardOfCompanyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTicketReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getTicketReport")
    public JAXBElement<GetTicketReport> createGetTicketReport(GetTicketReport value) {
        return new JAXBElement<GetTicketReport>(_GetTicketReport_QNAME, GetTicketReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSitesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getSitesResponse")
    public JAXBElement<GetSitesResponse> createGetSitesResponse(GetSitesResponse value) {
        return new JAXBElement<GetSitesResponse>(_GetSitesResponse_QNAME, GetSitesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTickesStates }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facade.webservices.services.esb.bbr/", name = "getTickesStates")
    public JAXBElement<GetTickesStates> createGetTickesStates(GetTickesStates value) {
        return new JAXBElement<GetTickesStates>(_GetTickesStates_QNAME, GetTickesStates.class, null, value);
    }

}
