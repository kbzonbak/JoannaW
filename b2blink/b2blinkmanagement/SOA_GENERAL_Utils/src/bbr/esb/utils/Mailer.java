/*
 * Created on Feb 16, 2004
 *
 */
package bbr.esb.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bbr.common.adtclasses.exceptions.OperationFailedException;

/**
 * Clase Utilizada para el envío de e_mail, tanto de mensajes, como de archivos atachados.
 */
public class Mailer {

	private static Logger logger = LoggerFactory.getLogger(Mailer.class);

	/**
	 * Método que realiza el envío de mensajes.
	 * 
	 * @param smtp_host
	 *            host de salida.
	 * @param from
	 *            Direccion de envío.
	 * @param to
	 *            Dirección de destino.
	 * @param subject
	 *            Asunto.
	 * @exception Exception
	 *                Excepción generalizada.
	 * @return Arreglo de Artículos.
	 */

	private static Mailer _instance;

	// Constructor
	private Mailer() {
		logger.info("Iniciando Mailer");
	}

	// For lazy initialization
	public static synchronized Mailer getInstance() {
		if (_instance == null) {
			_instance = new Mailer();
		}

		return _instance;
	}

	public void sendMailBySession(String[] to, String[] bcc, String from, String subject, String text, String mailSession) throws OperationFailedException {
		Context ctx = null;

		try {
			ctx = new InitialContext();
			javax.mail.Session mail_session = null;

			try {
				mail_session = (javax.mail.Session) ctx.lookup(mailSession);

			} finally {
				ctx.close();
			}

			MimeMessage msg = new MimeMessage(mail_session);

			msg.setFrom(new InternetAddress(from));

			msg.setRecipients(Message.RecipientType.TO, getRecipient(to));
			msg.setRecipients(Message.RecipientType.BCC, getRecipient(bcc));

			msg.setSubject(subject, "utf-8");
			msg.setHeader("Content-Type", "text/plain; charset=\"utf-8\"");

			msg.setText(text, "utf-8");

			Transport.send(msg);

		} catch (AddressException e) {
			logger.warn(e.getMessage());
			throw new OperationFailedException("Error al parsear las direcciones de correo", e);

		} catch (NamingException e) {
			logger.warn(e.getMessage());
			throw new OperationFailedException("Error al obtener mailsession por JNDI " + mailSession, e);

		} catch (MessagingException e) {
			logger.warn(e.getMessage());
			throw new OperationFailedException("Error al construir o enviar el correo", e);

		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			throw new OperationFailedException("Error desconocido al enviar el correo", e);
		}

	}

	public void sendMailBySession(String[] to, String[] cc, String[] bcc, String from, String subject, String message, boolean isHtml, List<File> attach, String mailSession) throws OperationFailedException {

		Context ctx = null;

		List<String> imageList = new ArrayList<String>();
		int countimages = 0;

		while (message.indexOf("<imagen>") != -1) {
			String before = message.substring(0, message.indexOf("<imagen>"));
			String image = message.substring(message.indexOf("<imagen>") + 8, message.indexOf("</imagen>"));
			imageList.add(image);
			String after = message.substring(message.indexOf("</imagen>") + 9, message.length());
			countimages++;
			message = before + "<br><img src=\"cid:image" + countimages + "\"><br>" + after;
		}

		try {
			ctx = new InitialContext();
			javax.mail.Session mail_session = null;

			try {
				mail_session = (javax.mail.Session) ctx.lookup(mailSession);

			} finally {
				ctx.close();
			}

			MimeMessage msg = new MimeMessage(mail_session);
			MimeMultipart mp = new MimeMultipart();

			MimeBodyPart textmsg = new MimeBodyPart();
			textmsg.setDisposition(Part.INLINE);

			if (isHtml)
				textmsg.setContent(message, "text/html;charset=iso-8859-1");
			else
				textmsg.setContent(message, "text/plain");

			mp.addBodyPart(textmsg);

			for (int i = 1; i <= imageList.size(); i++) {

				MimeBodyPart file_part = new MimeBodyPart();

				URL url = new URL(imageList.get(i - 1));
				DataHandler dh = new DataHandler(url);
				file_part.setDataHandler(dh);
				file_part.setHeader("Content-ID", "<image" + i + ">");
				mp.addBodyPart(file_part);
			}

			if (attach != null) {
				for (int i = 0; i < attach.size(); i++) {
					MimeBodyPart file_part = new MimeBodyPart();
					File file = attach.get(i);
					FileDataSource fds = new FileDataSource(file);
					DataHandler dh = new DataHandler(fds);
					file_part.setFileName(file.getName());
					file_part.setDisposition(Part.ATTACHMENT);
					file_part.setDescription("Archivo anexado: " + file.getName());
					file_part.setDataHandler(dh);
					mp.addBodyPart(file_part);
				}
			}

			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, getRecipient(to));
			if (cc != null)
				msg.setRecipients(Message.RecipientType.CC, getRecipient(cc));
			if (bcc != null)
				msg.setRecipients(Message.RecipientType.BCC, getRecipient(bcc));
			msg.setSubject(subject);
			msg.setContent(mp);

			Transport.send(msg);

		} catch (AddressException e) {
			logger.warn(e.getMessage());
			throw new OperationFailedException("Error al parsear las direcciones de correo", e);

		} catch (NamingException e) {
			logger.warn(e.getMessage());
			throw new OperationFailedException("Error al obtener mailsession por JNDI " + mailSession, e);

		} catch (MessagingException e) {
			logger.warn(e.getMessage());
			throw new OperationFailedException("Error al construir o enviar el correo", e);

		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			throw new OperationFailedException("Error desconocido al enviar el correo", e);
		}

	}

	public void sendMailBySessionWithImages(String[] to, String[] cc, String[] bcc, String from, String subject, String htmlText, String mailSession) throws OperationFailedException {

		Context ctx = null;
		List<String> imageList = new ArrayList<String>();
		int countimages = 0;

		while (htmlText.indexOf("<imagen>") != -1) {
			String before = htmlText.substring(0, htmlText.indexOf("<imagen>"));
			String image = htmlText.substring(htmlText.indexOf("<imagen>") + 8, htmlText.indexOf("</imagen>"));
			imageList.add(image);
			String after = htmlText.substring(htmlText.indexOf("</imagen>") + 9, htmlText.length());
			countimages++;
			htmlText = before + "<br><img src=\"cid:image" + countimages + "\"><br>" + after;
		}

		try {
			ctx = new InitialContext();
			javax.mail.Session mail_session = null;

			try {
				mail_session = (javax.mail.Session) ctx.lookup(mailSession);
			} finally {
				ctx.close();
			}

			MimeMessage msg = new MimeMessage(mail_session);
			MimeMultipart mp = new MimeMultipart("related");

			MimeBodyPart htmlmsg = new MimeBodyPart();
			htmlmsg.setContent(htmlText, "text/html;charset=iso-8859-1");
			mp.addBodyPart(htmlmsg);

			for (int i = 1; i <= imageList.size(); i++) {

				MimeBodyPart file_part = new MimeBodyPart();
				// File file = new File(Constants.getInstance().getImagePath() + imageList.get(i-1));
				URL url = new URL(imageList.get(i - 1));
				/*
				 * File file = new File (new URL(imageList.get(i-1))); FileDataSource fds = new FileDataSource(file);
				 */
				/* DataHandler dh = new DataHandler(fds); */
				DataHandler dh = new DataHandler(url);
				file_part.setDataHandler(dh);
				file_part.setHeader("Content-ID", "<image" + i + ">");
				mp.addBodyPart(file_part);

			}

			msg.setRecipients(Message.RecipientType.TO, getRecipient(to));
			if (cc != null) {
				msg.setRecipients(Message.RecipientType.CC, getRecipient(cc));
			}
			if (bcc != null) {
				msg.setRecipients(Message.RecipientType.BCC, getRecipient(bcc));
			}
			msg.setFrom(new InternetAddress(from));
			msg.setSubject(subject);
			msg.setContent(mp);
			Transport.send(msg);

		} catch (AddressException e) {
			logger.warn(e.getMessage());
			throw new OperationFailedException("Error al parsear las direcciones de correo", e);
		} catch (NamingException e) {
			logger.warn(e.getMessage());
			throw new OperationFailedException("Error al obtener mailsession por JNDI " + mailSession, e);
		} catch (MessagingException e) {
			logger.warn(e.getMessage());
			throw new OperationFailedException("Error al construir o enviar el correo", e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			throw new OperationFailedException("Error desconocido al enviar el correo", e);
		}
	}

	/**
	 * Convierte un arreglo de strings en un arreglo de direcciones de mails
	 */
	private Address[] getRecipient(String[] strings) throws AddressException {
		if (strings == null) {
			return null;
		}

		String address = "";

		for (int i = 0; i < strings.length; i++) {
			if (i == 0) {
				address = strings[i];

			} else {
				address = address + "," + strings[i];
			}
		}

		InternetAddress[] Result = InternetAddress.parse(address);

		return Result;
	}

}
