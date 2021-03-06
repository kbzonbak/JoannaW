package bbr.esb.services.webservices.facade;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.1.3
 * Wed Jul 01 17:24:33 CLT 2020
 * Generated source version: 2.1.3
 * 
 */
 
@WebService(targetNamespace = "http://facade.webservices.services.esb.bbr/", name = "ServiceManagerServer")
@XmlSeeAlso({ObjectFactory.class})
public interface ServiceManagerServer {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMessageFolderofUser", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetMessageFolderofUser")
    @ResponseWrapper(localName = "getMessageFolderofUserResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetMessageFolderofUserResponse")
    @WebMethod
    public bbr.esb.services.webservices.facade.MessageFolderDataDTO getMessageFolderofUser(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "doAddTicketEventStatus", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoAddTicketEventStatus")
    @ResponseWrapper(localName = "doAddTicketEventStatusResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoAddTicketEventStatusResponse")
    @WebMethod
    public bbr.esb.services.webservices.facade.TicketEventDTO doAddTicketEventStatus(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Long arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        java.lang.String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        java.lang.String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        java.lang.String arg6,
        @WebParam(name = "arg7", targetNamespace = "")
        java.util.List<bbr.esb.services.webservices.facade.TicketEventStatusDataDTO> arg7
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "checkUploadStatus", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.CheckUploadStatus")
    @ResponseWrapper(localName = "checkUploadStatusResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.CheckUploadStatusResponse")
    @WebMethod
    public bbr.esb.services.webservices.facade.TicketEventResultDTO checkUploadStatus(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        java.lang.String arg4
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @RequestWrapper(localName = "addlogByCompanyAccess", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.AddlogByCompanyAccess")
    @ResponseWrapper(localName = "addlogByCompanyAccessResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.AddlogByCompanyAccessResponse")
    @WebMethod
    public void addlogByCompanyAccess(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        java.lang.String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        java.lang.String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        java.lang.Boolean arg6
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "doAddTicketRefNumber", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoAddTicketRefNumber")
    @ResponseWrapper(localName = "doAddTicketRefNumberResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoAddTicketRefNumberResponse")
    @WebMethod
    public bbr.esb.services.webservices.facade.BaseResultDTO doAddTicketRefNumber(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Long arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @RequestWrapper(localName = "monitor", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.Monitor")
    @ResponseWrapper(localName = "monitorResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.MonitorResponse")
    @WebMethod
    public void monitor();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUninformedFileEvents", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetUninformedFileEvents")
    @ResponseWrapper(localName = "getUninformedFileEventsResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetUninformedFileEventsResponse")
    @WebMethod
    public java.util.List<bbr.esb.services.webservices.facade.FileEventDataDTO> getUninformedFileEvents() throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getCredentialsBySiteService", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetCredentialsBySiteService")
    @ResponseWrapper(localName = "getCredentialsBySiteServiceResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetCredentialsBySiteServiceResponse")
    @WebMethod
    public java.util.List<bbr.esb.services.webservices.facade.InitParamCSDTO> getCredentialsBySiteService(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMessageFolder", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetMessageFolder")
    @ResponseWrapper(localName = "getMessageFolderResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetMessageFolderResponse")
    @WebMethod
    public bbr.esb.services.webservices.facade.MessageFolderDataDTO getMessageFolder(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "doUpdateFileEventStatusofPathandUser", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoUpdateFileEventStatusofPathandUser")
    @ResponseWrapper(localName = "doUpdateFileEventStatusofPathandUserResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoUpdateFileEventStatusofPathandUserResponse")
    @WebMethod
    public bbr.esb.services.webservices.facade.FileEventDTO doUpdateFileEventStatusofPathandUser(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @RequestWrapper(localName = "addlogById", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.AddlogById")
    @ResponseWrapper(localName = "addlogByIdResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.AddlogByIdResponse")
    @WebMethod
    public void addlogById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.Integer arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.Integer arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        java.lang.String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        java.lang.String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        java.lang.Boolean arg6
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "doLogonUser", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoLogonUser")
    @ResponseWrapper(localName = "doLogonUserResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoLogonUserResponse")
    @WebMethod
    public bbr.esb.services.webservices.facade.LogonResultDTO doLogonUser(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMessageData", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetMessageData")
    @ResponseWrapper(localName = "getMessageDataResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetMessageDataResponse")
    @WebMethod
    public bbr.esb.services.webservices.facade.MessageDataDTO getMessageData(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "doAddFileEvent", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoAddFileEvent")
    @ResponseWrapper(localName = "doAddFileEventResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoAddFileEventResponse")
    @WebMethod
    public bbr.esb.services.webservices.facade.FileEventDTO doAddFileEvent(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        java.lang.String arg4
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "doUpdateFileEventStatus", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoUpdateFileEventStatus")
    @ResponseWrapper(localName = "doUpdateFileEventStatusResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoUpdateFileEventStatusResponse")
    @WebMethod
    public bbr.esb.services.webservices.facade.FileEventDTO doUpdateFileEventStatus(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "doAddServiceEvent", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoAddServiceEvent")
    @ResponseWrapper(localName = "doAddServiceEventResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoAddServiceEventResponse")
    @WebMethod
    public bbr.esb.services.webservices.facade.ServiceEventDTO doAddServiceEvent(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMessageDataofUser", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetMessageDataofUser")
    @ResponseWrapper(localName = "getMessageDataofUserResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetMessageDataofUserResponse")
    @WebMethod
    public bbr.esb.services.webservices.facade.MessageDataDTO getMessageDataofUser(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @RequestWrapper(localName = "updateContractedServiceMonitorData", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.UpdateContractedServiceMonitorData")
    @ResponseWrapper(localName = "updateContractedServiceMonitorDataResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.UpdateContractedServiceMonitorDataResponse")
    @WebMethod
    public void updateContractedServiceMonitorData(
        @WebParam(name = "arg0", targetNamespace = "")
        java.util.List<bbr.esb.services.webservices.facade.ContractedServiceMonitorDataDTO> arg0
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @RequestWrapper(localName = "addlog", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.Addlog")
    @ResponseWrapper(localName = "addlogResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.AddlogResponse")
    @WebMethod
    public void addlog(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        java.lang.String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        java.lang.String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        java.lang.Boolean arg6
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getCompaniesServiceStatusReport", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetCompaniesServiceStatusReport")
    @ResponseWrapper(localName = "getCompaniesServiceStatusReportResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetCompaniesServiceStatusReportResponse")
    @WebMethod
    public java.util.List<bbr.esb.services.webservices.facade.CompaniesServiceStatusReportDTO> getCompaniesServiceStatusReport() throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "doAddGenericServiceEvent", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoAddGenericServiceEvent")
    @ResponseWrapper(localName = "doAddGenericServiceEventResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.DoAddGenericServiceEventResponse")
    @WebMethod
    public java.util.List<bbr.esb.services.webservices.facade.ServiceEventDTO> doAddGenericServiceEvent(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @RequestWrapper(localName = "addlogByCompanyRut", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.AddlogByCompanyRut")
    @ResponseWrapper(localName = "addlogByCompanyRutResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.AddlogByCompanyRutResponse")
    @WebMethod
    public void addlogByCompanyRut(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        java.lang.String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        java.lang.String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        java.lang.Boolean arg6
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getCredentialsBySiteServiceAndCompany", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetCredentialsBySiteServiceAndCompany")
    @ResponseWrapper(localName = "getCredentialsBySiteServiceAndCompanyResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetCredentialsBySiteServiceAndCompanyResponse")
    @WebMethod
    public bbr.esb.services.webservices.facade.InitParamCSDTO getCredentialsBySiteServiceAndCompany(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2
    ) throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUnprocessedServiceEvents", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetUnprocessedServiceEvents")
    @ResponseWrapper(localName = "getUnprocessedServiceEventsResponse", targetNamespace = "http://facade.webservices.services.esb.bbr/", className = "bbr.esb.services.webservices.facade.GetUnprocessedServiceEventsResponse")
    @WebMethod
    public java.util.List<bbr.esb.services.webservices.facade.ServiceEventDataDTO> getUnprocessedServiceEvents() throws AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception;
}
