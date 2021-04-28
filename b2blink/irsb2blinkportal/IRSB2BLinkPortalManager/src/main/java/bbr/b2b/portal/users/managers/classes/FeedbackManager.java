package bbr.b2b.portal.users.managers.classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.util.LoggingUtil;
import bbr.b2b.common.util.Mailer;
import bbr.b2b.portal.constants.PortalResources;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;
import bbr.b2b.portal.wrapper.FeedbackItemDTO;
import bbr.b2b.users.report.classes.UserDTO;

@Stateless(name = "managers/FeedbackManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FeedbackManager implements FeedbackManagerLocal
{

	private static LoggingUtil logger = new LoggingUtil(FeedbackManager.class);

	public BaseResultDTO sendFeedback(UserDTO user, FeedbackItemDTO feedbackItem)
	{
		BaseResultDTO result = new BaseResultDTO();
		String mailstring = PortalResources.getInstance().getMailforB2BOpinion();
		String[] mail_to = mailstring.split(",");

		String[] mail_to2 = new String[2];

		mail_to2[0] = mail_to[0];
		mail_to2[1] = user.getEmail();

		String mailcostring = PortalResources.getInstance().getMailCOforB2BOpinion();
		String[] mail_to_co = mailcostring.split(",");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar now = Calendar.getInstance();

		String subject = "Portal B2B Link : Su Opinión";

		String from = PortalResources.getInstance().getMailFrom();

		String body = "Estimado usuario,<br/><br/>";
		body += "Hemos recibido su comentario y pronto nos contactaremos con Ud.<br/><br/>";
		body += "<table>";
		body += "<tr align='char'><td>Fecha/Hora</td><td>: " + format.format(now.getTime()) + "</td></tr>";
		body += "<tr align='char'><td>Funcionalidad</td><td>: " + this.checkEmptyField(feedbackItem.getFuncionality()) + "</td></tr>";
		body += "<tr align='char'><td>Usuario</td><td>: " + this.checkEmptyField(user.getEmail()) + "</td></tr>";
		body += "<tr align='char'><td>Nombre</td><td>: " + this.checkEmptyField(user.getName()) + " " + this.checkEmptyField(user.getLastname()) + "</td></tr>";
		body += "<tr align='char'><td>Cargo</td><td>: " + this.checkEmptyField(user.getPosition()) + "</td></tr>";
		body += "<tr align='char'><td>Teléfono</td><td>: " + this.checkEmptyField(user.getPhone()) + "</td></tr>";
		body += "<tr align='char'><td>Empresa</td><td>: " + this.checkEmptyField(feedbackItem.getEnterprise()) + " [" + this.checkEmptyField(feedbackItem.getEnterpriseCode()) + "]</td></tr>";
		body += "<tr align='left'><td valign='top'>Comentario</td><td>: " + this.checkEmptyField(feedbackItem.getComment()) + "</td></tr>";
		body += "</table>";
		body += "<br/>Saludos,<br/>Soporte B2B<br/>";
		body += "<br/>";

		String mailsession = "java:/Mail";

		try
		{
			Mailer.getInstance().sendMailBySession(mail_to2, null, mail_to_co, from, subject, body, true, null, mailsession);
		}
		catch (OperationFailedException e)
		{
			logger.info("Error de envio de mail");
			BaseResultDTO resultW = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P2000");// Error//en//envio//de//correo.
		}
		catch (Exception e)
		{
			e.printStackTrace();
			BaseResultDTO resultW = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P2000");// Error//en//envio//de//correo.
		}

		return result;
	}

	private String checkEmptyField(String field)
	{
		return (field != null) ? field : "";
	}

}
