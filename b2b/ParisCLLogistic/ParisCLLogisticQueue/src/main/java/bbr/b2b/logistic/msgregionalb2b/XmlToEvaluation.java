package bbr.b2b.logistic.msgregionalb2b;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.regional.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.ReceptionErrorServerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.ReceptionEvaluationServerLocal;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.EvaluationDetailW;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ReceptionErrorW;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ReceptionEvaluationW;
import bbr.b2b.regional.logistic.evaluations.managers.interfaces.EvaluationReportManagerLocal;
import bbr.b2b.regional.logistic.utils.CommonQueueUtils;
import bbr.b2b.regional.logistic.xml.qevaluation.QEVALUACION;

@Stateless(name = "handlers/XmlToEvaluation")
public class XmlToEvaluation implements XmlToEvaluationLocal{
	
	@EJB
	DatingServerLocal datingserver;
	
	@EJB
	DeliveryServerLocal deliveryserver;
	
	@EJB
	OrderServerLocal orderserver;
	
	@EJB
	ReceptionErrorServerLocal receptionerrorserver;
	
	@EJB
	ReceptionEvaluationServerLocal receptionevaluationserver;
	
	@EJB
	EvaluationReportManagerLocal evaluationmanager;
	
	@EJB
	BuyOrderReportManagerLocal ordermanager;
	
	CommonQueueUtils qutils = CommonQueueUtils.getInstance();	
	private static Logger logger = Logger.getLogger("CargaMsgesLog");
	
	private void doValidateEvaluationMessage(QEVALUACION qevaluacion) throws LoadDataException {
		
		// SEQUENCENUMBER: N??MERO DE SECUENCIA
		qutils.datoObligatorio(qevaluacion.getSequenceNumber(), "No se especifica N??mero de secuencia");
		qutils.validaLargo(qevaluacion.getSequenceNumber(), 12, "El N??mero de secuencia debe tener un largo m???ximo de 12 d??gitos");	
		
		// TRANSNBR: N??MERO DE TRANSACci??n
		qutils.datoObligatorio(qevaluacion.getTransNbr(), "No se especifica N??mero de transacci??n");
		qutils.validaLargo(qevaluacion.getTransNbr(), 9, "El N??mero de transacci??n debe tener un largo m???ximo de 9 d??gitos");
		
		// NASN: N??MERO DE ASN
		qutils.datoObligatorio(qevaluacion.getNasn(), "No se especifica N??mero de ASN");
		qutils.validaLargo(qevaluacion.getNasn(), 20, "El N??mero de ASN debe tener un largo m???ximo de 20 caracteres");
		
		// DATECREATED: FECHA EVALUAci??n
		qutils.datoObligatorio(qevaluacion.getDateCreated(), "No se especifica fecha de evaluaci??n");
		qutils.validaLargo(qevaluacion.getDateCreated(), 6, "La fecha de evaluaci??n debe tener un largo m???ximo de 6 caracteres");
		
		try{
			qutils.getDate(qevaluacion.getDateCreated(), "yyMMdd");
		}catch (ParseException e) {
			throw new LoadDataException("La fecha de evaluaci??n debe tener formato 'YYMMDD'");
		}		
		
		// PONBR: N??MERO DE OC
		qutils.datoObligatorio(qevaluacion.getPonbr(), "No se especifica N??mero de orden");
		qutils.validaLargo(qevaluacion.getPonbr(), 20, "El N??mero de orden debe tener un largo m???ximo de 20 caracteres");
		
		// AS400USERID: NOMBRE USUARIO QUE APRUEBA EVALUAci??n
		qutils.datoObligatorio(qevaluacion.getAs400UserID(), "No se especifica nombre de usuario que aprueba la evaluaci??n");
		qutils.validaLargo(qevaluacion.getAs400UserID(), 15, "El nombre de usuario que aprueba la evaluaci??n debe tener un largo m???ximo de 15 caracteres");
		
		/* JPE: Los siguientes campos est???n definidos de esta forma por error en la interfaz original,
				se dej??? tal como est??? en el c??digo originario porque PARIS lo tiene implementado as???,
				considerar posible modificaci??n futura */
		
		// REFERENCE3: MOTIVO DE LA TRANSACci??n (c??digo DE EVALUAci??n)	// debe ser ???ste REFERENCECODE3: VENDOR PERFORMANCE ERROR
		qutils.datoObligatorio(qevaluacion.getReference3(), "No se especifica c??digo de evaluaci??n");
		qutils.validaLargo(qevaluacion.getReference3(), 20, "El c??digo de evaluaci??n debe tener un largo m???ximo de 20 caracteres"); // deben ser 3 caracteres
		
		// REFERENCECODE3: VENDOR PERFORMANCE ERROR (COMENTARIO DE EVALUAci??n)	// debe ser ???ste REFERENCE3: MOTIVO DE LA TRANSACci??n
		qutils.validaLargo(qevaluacion.getReferenceCode3(), 3, "El comentario de evaluaci??n debe tener un largo m???ximo de 3 caracteres");	// deben ser 20 caracteres

	}
	
	public void error(String msge) throws LoadDataException {
		qutils.error(msge);
	}

	public Long processMessage(QEVALUACION qevaluacion) throws LoadDataException, ServiceException{
		
		DatingW dating = null;
		OrderW order = null;
		ReceptionErrorW receptionError = null;
		ReceptionEvaluationW receptionEvaluation = null;
		EvaluationDetailW evaluationDetail = null;
		
		//////////// Validaci??n del mensaje //////////////
		doValidateEvaluationMessage(qevaluacion);
		
		// SEQUENCENUMBER: N??MERO DE SECUENCIA
		Long sequenceNumber = null;
		try {
			sequenceNumber = qevaluacion.getSequenceNumber();		
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el N??mero de secuencia");			
		}	
		
		// TRANSNBR: N??MERO DE TRANSACci??n
		Long transactionNumber = null;
		try {
			transactionNumber = qevaluacion.getTransNbr();	
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el N??mero de transacci??n");			
		}	
		
		//////////// Validaci??n de la Cita asociada /////////////
		
		// NASN: N??MERO DE ASN
		Long asnNumber = null;
		try {
			asnNumber = Long.parseLong(qevaluacion.getNasn().trim());
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el N??mero de ASN");			
		}	

		// Obtener la cita asociada al N??mero de ASN
		List<DatingW> datings =  datingserver.getByProperty("number", asnNumber);
				
		if (datings == null || datings.size() <= 0){
			throw new LoadDataException("La cita asociada al No. ASN " + asnNumber + " no existe");		
		}	
		
		dating = datings.get(0);
		
		// DATECREATED: FECHA EVALUAci??n
		Date createdDate = null;
		try{
			createdDate = qutils.getDate(qevaluacion.getDateCreated(), "yyMMdd");	// Ya fue validado	
		}catch (ParseException e) {
			throw new LoadDataException("La fecha de evaluaci??n debe tener formato 'YYMMDD'");
		}		
			 
		// PONBR: N??MERO DE OC
		String orderNumber = "";
		try {
			orderNumber = qevaluacion.getPonbr().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el N??mero de orden");			
		}	
		
		// Obtener la Orden
		List<OrderW> orders =  orderserver.getByProperty("number", new Long(orderNumber));
		
		if (orders == null || orders.size() <= 0){
			throw new LoadDataException("La O/C con N??mero " + orderNumber + " no existe");		
		}	
		
		order = orders.get(0);
		
		// AS400USERID: NOMBRE USUARIO QUE APRUEBA EVALUAci??n
		String userName = "";
		try {
			userName = qevaluacion.getAs400UserID().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el nombre de usuario que aprueba la evaluaci??n");			
		}	
		
		//////////// Validaci??n del c??digo de error asociado a la recepci??n /////////////
		
		// REFERENCE3: c??digo DE EVALUAci??n
		String evaluationCode = "";
		try {
			evaluationCode = qevaluacion.getReference3().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el c??digo de evaluaci??n");			
		}	
		
		// Obtener el c??digo de error asociado a la recepci??n
		List<ReceptionErrorW> receptionErrors =  receptionerrorserver.getByProperty("code", evaluationCode);
		
		if (receptionErrors == null || receptionErrors.size() <= 0){
			throw new LoadDataException("El c??digo de error de recepci??n no existe: " + evaluationCode);		
		}	
		
		receptionError = receptionErrors.get(0);
	
		// REFERENCECODE3: COMENTARIO DE EVALUAci??n
		String evaluationComment = "";
		try {
			evaluationComment = qevaluacion.getReferenceCode3().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el comentario de evaluaci??n");			
		}	
		
		//////////// Validaci??n de evaluaci??n previa de recepci??n para la cita /////////////

		// Obtener la evaluaci??n previa de recepci??n si existe, de lo contrario instanciarla
		PropertyInfoDTO property = new PropertyInfoDTO("dating.id", "dating", dating.getId());
		List<ReceptionEvaluationW> receptionEvaluations =  receptionevaluationserver.getByProperties(property);
		
		if (receptionEvaluations != null && receptionEvaluations.size() > 0){
			receptionEvaluation = receptionEvaluations.get(0);	
		}	
		else{
			receptionEvaluation = new ReceptionEvaluationW();
			receptionEvaluation.setDatingid(dating.getId());
		}

		// Actualizar la evaluaci??n con los datos del mensaje
		receptionEvaluation.setAutogenerated(false);
		receptionEvaluation.setComments(evaluationComment);
		receptionEvaluation.setWhen(createdDate);
		receptionEvaluation.setWho(userName);
		
		//////////// Creaci??n del detalle de evaluaci??n /////////////
		evaluationDetail = new EvaluationDetailW();
		
		evaluationDetail.setReceptionerrorid(receptionError.getId());
		
		//////////// Actualizar/Crear la evaluaci??n y el detalle /////////////
		receptionEvaluation = evaluationmanager.doAddOrUpdateReceptionEvaluationAndDetails(receptionEvaluation, evaluationDetail);
		
		// Recalcular los totales de la Orden
		ordermanager.doCalculateTotalOfOrder(order.getId());
		
		logger.info("Recalculadas las unidades de la Orden No. " + order.getNumber());
		
		return transactionNumber;
			
	}
}
