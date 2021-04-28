package bbr.b2b.extractors.soa.queuehandler;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import bbr.b2b.extractors.manager.interfaces.WalmartEdiManagerLocal;

@Stateless(name = "handlers/SOAHandler")
@TransactionManagement(TransactionManagementType.BEAN)
public class SoaHandler implements SoaHandlerLocal {

	// private static JAXBContext jcSales = null;

	@EJB
	WalmartEdiManagerLocal walmartEdiManagerLocal;

	private static Logger logger = Logger.getLogger("SOALog");

	/*
	 * private static JAXBContext getJCSales() throws JAXBException { if
	 * (jcSales == null) jcSales =
	 * JAXBContext.newInstance("bbr.b2b.b2blink.commercial.xml.SolicitudVentas")
	 * ; return jcSales; }
	 */

	public void handleMessage(String from, String content, javax.ejb.MessageDrivenContext ctx) {
		UserTransaction usertransaction = null;
		ArrayList<String> errors = new ArrayList<String>();
		boolean commitAction = false;
		try {
			if (from.equals("CL0701")) {
				try {
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();
					try {
						walmartEdiManagerLocal.EdiToOc(content);
						commitAction = true;
					} catch (Exception e) {
						errors.add("Error Procesando reporte ventas Via SOA: " + e.getMessage());
					}

					if (commitAction) {
						usertransaction.commit();
					} else {
						for (String errmsg : errors) {
							logger.error(errmsg);
						}
						rollback(usertransaction);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("Error : " + ex.getMessage());
					logger.error("Procediendo a rollback en SOAHandler...");
					rollback(usertransaction);
					logger.error("Rollback en SOAHandler finalizado");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			rollback(usertransaction);
		}
	}

	private void rollback(UserTransaction usertransaction) {
		try {
			if (usertransaction != null && usertransaction.getStatus() == Status.STATUS_ACTIVE)
				usertransaction.rollback();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
	}
	/*
	private boolean doValidateSchema(JAXBContext jc, String content) {
		try {
			InputSource source = new InputSource(new StringReader(content));
			Unmarshaller u = jc.createUnmarshaller();
			u.unmarshal(source);
			return true;
		} catch (java.lang.ClassCastException e) {
			return false;
		} catch (JAXBException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private Object getUnmarshalObject(JAXBContext jc, String content) {
		try {
			Unmarshaller u = jc.createUnmarshaller();
			InputSource source = new InputSource(new StringReader(content));
			return u.unmarshal(source);
		} catch (java.lang.ClassCastException e) {
			return null;
		} catch (JAXBException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	*/
}
