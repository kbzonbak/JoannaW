
package cl.paperless.webapp.online.webservices;

import java.util.List;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import cl.paperless.reception.xsd.RespuestaBOMasivo;
import cl.paperless.reception.xsd.RespuestaCCMasivo;
import cl.paperless.webapp.online.webservices.xsd.OnlineResponse;

@WebService(name = "OnlinePortType", targetNamespace = "http://webservices.online.webapp.paperless.cl")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface OnlinePortType {

	@WebMethod(operationName = "OnlineRecoveryRecCT", action = "urn:OnlineRecoveryRecCT")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public OnlineResponse onlineRecoveryRecCT(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Long args5, @WebParam(name = "args6", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args6);

	@WebMethod(operationName = "OnlineGeneration", action = "urn:OnlineGeneration")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String onlineGeneration(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args5);

	@WebMethod(operationName = "OnlineRecovery", action = "urn:OnlineRecovery")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String onlineRecovery(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Long args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args5);

	@WebMethod(operationName = "aprobacionRechazoMasivo", action = "urn:aprobacionRechazoMasivo")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public RespuestaCCMasivo aprobacionRechazoMasivo(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3);

	@WebMethod(operationName = "aprobRechLeyMasivo", action = "urn:aprobRechLeyMasivo")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String aprobRechLeyMasivo(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3);

	@WebMethod(operationName = "OnlineGenerationRaw2", action = "urn:OnlineGenerationRaw2")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String onlineGenerationRaw2(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args5, @WebParam(name = "args6", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args6, @WebParam(name = "args7", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args7, @WebParam(name = "args8", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args8);

	@WebMethod(operationName = "OnlineGenerationBolCT", action = "urn:OnlineGenerationBolCT")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public OnlineResponse onlineGenerationBolCT(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args5);

	@WebMethod(operationName = "OnlineGenerationDte2", action = "urn:OnlineGenerationDte2")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String onlineGenerationDte2(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args5, @WebParam(name = "args6", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args6, @WebParam(name = "args7", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args7);

	@WebMethod(operationName = "Consult", action = "urn:Consult")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String consult(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3);

	@WebMethod(operationName = "GestionRec", action = "urn:GestionRec")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String gestionRec(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Long args5, @WebParam(name = "args6", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args6, @WebParam(name = "args7", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args7);

	@WebMethod(operationName = "BusinessReply", action = "urn:BusinessReply")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String businessReply(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Long args5, @WebParam(name = "args6", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args6, @WebParam(name = "args7", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args7);

	@WebMethod(operationName = "main", action = "urn:main")
	@Oneway
	public void main(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	List<String> args0);

	@WebMethod(operationName = "OnlineGeneration2", action = "urn:OnlineGeneration2")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String onlineGeneration2(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args5, @WebParam(name = "args6", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args6, @WebParam(name = "args7", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args7);

	@WebMethod(operationName = "OnlineRecoveryCT", action = "urn:OnlineRecoveryCT")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public OnlineResponse onlineRecoveryCT(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Long args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args5);

	@WebMethod(operationName = "listaDocRec", action = "urn:listaDocRec")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String listaDocRec(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args5, @WebParam(name = "args6", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args6, @WebParam(name = "args7", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args7);

	@WebMethod(operationName = "reinyeccionBOMasivo", action = "urn:reinyeccionBOMasivo")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public RespuestaBOMasivo reinyeccionBOMasivo(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3);

	@WebMethod(operationName = "OnlineRecoveryRec", action = "urn:OnlineRecoveryRec")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String onlineRecoveryRec(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Long args5, @WebParam(name = "args6", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args6);

	@WebMethod(operationName = "GestionEmitido", action = "urn:GestionEmitido")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String gestionEmitido(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Long args5, @WebParam(name = "args6", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args6);

	@WebMethod(operationName = "ConsultCT", action = "urn:ConsultCT")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public OnlineResponse consultCT(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3);

	@WebMethod(operationName = "OnlineGenerationBol", action = "urn:OnlineGenerationBol")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String onlineGenerationBol(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args5);

	@WebMethod(operationName = "OnlineRecoveryFolio", action = "urn:OnlineRecoveryFolio")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String onlineRecoveryFolio(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4);

	@WebMethod(operationName = "AnulaGuia", action = "urn:AnulaGuia")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String anulaGuia(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4);

	@WebMethod(operationName = "OnlineGenerationRaw", action = "urn:OnlineGenerationRaw")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String onlineGenerationRaw(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args5, @WebParam(name = "args6", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args6);

	@WebMethod(operationName = "OnlineGenerationBol2", action = "urn:OnlineGenerationBol2")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String onlineGenerationBol2(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args5, @WebParam(name = "args6", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args6, @WebParam(name = "args7", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args7);

	@WebMethod(operationName = "OnlineGenerationDte", action = "urn:OnlineGenerationDte")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String onlineGenerationDte(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args5);

	@WebMethod(operationName = "OnlineGenerationCT", action = "urn:OnlineGenerationCT")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public OnlineResponse onlineGenerationCT(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args5);

	@WebMethod(operationName = "OnlineRecovery2", action = "urn:OnlineRecovery2")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String onlineRecovery2(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Long args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Long args5, @WebParam(name = "args6", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args6);

	@WebMethod(operationName = "isNumeric", action = "urn:isNumeric")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public Boolean isNumeric(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args0);

	@WebMethod(operationName = "OnlineRecoveryRecList", action = "urn:OnlineRecoveryRecList")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String onlineRecoveryRecList(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3);

	@WebMethod(operationName = "anulaFolioBoleta", action = "urn:anulaFolioBoleta")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public String anulaFolioBoleta(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Long args4);

	@WebMethod(operationName = "OnlineGenerationDteCT", action = "urn:OnlineGenerationDteCT")
	@WebResult(name = "return", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	public OnlineResponse onlineGenerationDteCT(@WebParam(name = "args0", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args0, @WebParam(name = "args1", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args1, @WebParam(name = "args2", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args2, @WebParam(name = "args3", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	String args3, @WebParam(name = "args4", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args4, @WebParam(name = "args5", targetNamespace = "http://webservices.online.webapp.paperless.cl")
	Integer args5);

}
