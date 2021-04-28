package bbr.b2b.regional.logistic.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.util.Mailer;
import bbr.b2b.common.util.PrefixAndSuffixFilter;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;

public class MsgRecoveryServices {

	private static Logger logger = Logger.getLogger(MsgRecoveryServices.class);

	// This comparator will compare two File objects and
	// return -1, 0, or 1 depending on their modification date
	class fileComparator implements Comparator {

		public fileComparator() {
		}

		// Comparator interface requires defining compare method.
		public int compare(Object obj1, Object obj2) {

			File file1 = (File) obj1;
			File file2 = (File) obj2;
			long comp = file1.getName().compareToIgnoreCase(file2.getName());

			// ... If equal lengths, sort per item_id.
			comp = file1.lastModified() - file2.lastModified();
			if (comp > 0)
				return 1;
			else if (comp < 0)
				return -1;
			else
				return 0;
		}
	}

	private static MsgRecoveryServices _instance;

	// private static final Logger log = Logger.getLogger(MsgRecoveryServices.class);

	// Constructor
	public static synchronized MsgRecoveryServices getInstance() {
		if (_instance == null) {
			_instance = new MsgRecoveryServices();
		}
		return _instance;
	}

	private ResourceBundle config = null;

	private String dir_err = null;

	private String dir_gen = null;

	// MENSAJES PERDIDOS
	private String dir_lostmsges = null;

	private String dir_out = null;

	private String file_type = null;

	private String filecsv_type = null;

	private Locale loc = new Locale("es", "CL");

	private File ruta_err = null;

	private File ruta_gen = null;

	private File ruta_lostmsges = null;

	private File ruta_out = null;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", loc);

	public MsgRecoveryServices() {

		try {
			getParams("msgrec");

			if (ruta_out.exists()) {
				if (ruta_out.isFile()) {
					logger.error("Directorio de Out no es de tipo directorio");
				}
			} else {
				logger.debug("Construyendo ruta para directorio OUT");
				ruta_out.mkdirs();
			}

			if (ruta_gen.exists()) {
				if (ruta_gen.isFile()) {
					logger.debug("Directorio general no es de tipo directorio");
				}
			} else {
				logger.debug("Construyendo ruta para directorio GEN");
				ruta_gen.mkdirs();
			}

			if (ruta_err.exists()) {
				if (ruta_err.isFile()) {
					logger.debug("Directorio de Errores no es de tipo directorio");
				}
			} else {
				logger.debug("Construyendo ruta para directorio ERR");
				ruta_err.mkdirs();
			}

			if (ruta_lostmsges.exists()) {
				if (ruta_lostmsges.isFile()) {
					logger.debug("Directorio de MENSAJES PERDIDOS no es de tipo directorio");
				}
			} else {
				logger.debug("Construyendo ruta para directorio MENSAJES PERDIDOS");
				ruta_lostmsges.mkdirs();
			}
		} catch (OperationFailedException e) {
			e.printStackTrace();
		}
	}

	private String getDate(Date datenow) {
		String date;
		date = sdf.format(datenow) + "_" + System.nanoTime();
		return (date);
	}

	private File getInputFolder() throws OperationFailedException {
		return ruta_lostmsges;
	}

	private String getInputFolderName() {
		return dir_lostmsges;
	}

	private void getParams(String ruta) throws OperationFailedException {
		try {
			config = ResourceBundle.getBundle("bbr.b2b.regional.logistic.utils.msgrec");
			// Tipo de archivo
			file_type = config.getString("bbr.msgrec.file.type");
			filecsv_type = config.getString("bbr.msgrec.filecsv.type");

			if ("windows".equalsIgnoreCase(RegionalLogisticConstants.getInstance().getOS())) {
				// Carpetas donde se graba los mensajes
				dir_out = config.getString("bbr.msgrec.dir.outWIN");
				dir_err = config.getString("bbr.msgrec.dir.errWIN");
				dir_gen = config.getString("bbr.msgrec.dir.genWIN");
				dir_lostmsges = config.getString("bbr.msgrec.dir.lostmsgesWIN");
			} else if ("linux".equalsIgnoreCase(RegionalLogisticConstants.getInstance().getOS())) {
				// TESTING
				if (!RegionalLogisticConstants.getInstance().getProduccion()) {
					// Carpetas donde se graba los mensajes
					dir_out = config.getString("bbr.msgrec.dir.outTEST");
					dir_err = config.getString("bbr.msgrec.dir.errTEST");
					dir_gen = config.getString("bbr.msgrec.dir.genTEST");
					dir_lostmsges = config.getString("bbr.msgrec.dir.lostmsgesTEST");
				}
				// PRODUCCION
				else {
					// Carpetas donde se graba los mensajes
					dir_out = config.getString("bbr.msgrec.dir.outPROD");
					dir_err = config.getString("bbr.msgrec.dir.errPROD");
					dir_gen = config.getString("bbr.msgrec.dir.genPROD");
					dir_lostmsges = config.getString("bbr.msgrec.dir.lostmsgesPROD");
				}
			} else
				logger.error("No hay Sistema Operativo asignado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		ruta_out = new File(dir_out);
		ruta_err = new File(dir_err);
		ruta_gen = new File(dir_gen);
		ruta_lostmsges = new File(dir_lostmsges);
	}

	private void mailMe(String msg, String msgetype, String path, Exception exception) {

		String subject = "";
		try {
			subject = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "Logistica: Mensaje no se quiere procesar. MsgeType :" + msgetype;
			String[] mailreciever = RegionalLogisticConstants.getInstance().getDEVELOPER_MAIL_ERROR();
			// String mailsender = Constants.getPropertyValue("mailsender");
			String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
			String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
			String text = path + "\n\n" + exception.getMessage() + "\n\n\n" + msg;
			Mailer.getInstance().sendMailBySession(mailreciever, null, null, mailsender, subject, text, false, null, mailSession);
		} catch (Exception e) {
			logger.error("No se envio el mail del fracaso de envio de mensaje");
		}
	}

	/*
	 * Lee la carpeta que contiene los archivos perdidos, buscando los archivos del tipo indicado por messagetype
	 * @author Administrator @return HashMap , key = nombre del archivo, value = contenido
	 */
	public HashMap readMsgsFromFiles(String messagetype) throws Exception {

		HashMap contentMap = new HashMap();
		String[] listaArchivos = null;
		int lenLista = 0;
		// Busca carpeta que corresponde de entrada
		File file = getInputFolder();
		if (file.exists()) {
			if (file.isFile()) {
				logger.debug("Directorio de Entrada no es de tipo directorio");
			} else if (file.isDirectory()) {
				File[] archTmp1Lst = file.listFiles(new PrefixAndSuffixFilter(messagetype, file_type)); // xml
				File[] archTmp2Lst = file.listFiles(new PrefixAndSuffixFilter(messagetype, filecsv_type)); // csv
				File[] archTmpLst = null;

				archTmpLst = (File[]) ArrayUtils.addAll(archTmp1Lst, archTmp2Lst);

				lenLista = archTmpLst.length;
				// Ordena los archivos por fecha last modified.
				// Crea un comparator
				fileComparator comp = new fileComparator();
				Arrays.sort(archTmpLst, comp);
				// Stream to read file
				// FileInputStream fin;
				listaArchivos = new String[lenLista];
				File fileToMove = null;
				String destFile = "";
				String finalStr = "";
				for (int i = 0; i < lenLista; i++) {
					listaArchivos[i] = new String(archTmpLst[i].getAbsolutePath());
					logger.debug("ARCHIVO POR PROCESAR --> " + listaArchivos[i]);
					// Open an input stream
					FileInputStream fin = new FileInputStream(listaArchivos[i]);
					BufferedReader dis = new BufferedReader(new InputStreamReader(fin));
					try {
						StringBuffer buffer = new StringBuffer();
						while (true) {
							// Read a line of text
							String line = dis.readLine();
							if (line == null)
								break;
							buffer.append(line);
						}
						String content = buffer.toString();
						logger.debug(content);
						contentMap.put(listaArchivos[i], content);
					}
					// Catches any error conditions
					catch (IOException e) {
						logger.debug("Unable to read from file");
					} finally {
						fin.close();
					}
					// Move the file
					fileToMove = new File(listaArchivos[i]);
					finalStr = listaArchivos[i].substring(listaArchivos[i].lastIndexOf(File.separator), listaArchivos[i].length());
					destFile = dir_out + finalStr;
					logger.debug("ARCHIVO DESTINO --> " + destFile);
					fileToMove.renameTo(new File(destFile));
				}
			}
		} else {
			logger.debug("Construyendo ruta para directorio IN");
			file.mkdirs();
		}
		return contentMap;
	}

	/*
	 * Graba los mensajes que no se pueden procesar a un archivo. Este archivo se deja en una carpeta �nica. El nombre
	 * del archivo depende del tipo de mensaje y la hora de creación.
	 */
	public void saveMsgToFile(String messagetype, String msg, Exception exception) throws Exception {
		// Stream to write file
		FileOutputStream fout = null;
		// Busca carpeta de destino
		String folderName = getInputFolderName();
		try {
			// Open an output stream
			if (msg != null && msg.length() > 0) {
				String path = "";
				Date now = new Date();
				if (messagetype.equals("")) {
					path = dir_gen + File.separator + messagetype + "_" + getDate(now) + "." + file_type;
				} else {
					path = folderName + File.separator + messagetype + "_" + getDate(now) + "." + file_type;
				}
				fout = new FileOutputStream(path);
				// Print a line of text
				new PrintStream(fout).println(msg);
				logger.info("Mensaje guardado :" + path + " Causa:" + exception.getMessage());
				mailMe(msg, messagetype, path, exception);
			}
		} catch (IOException e) {
			// TODO DVI enviar mail
			logger.debug("Unable to write to file " + e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			if (fout != null)
				try {
					fout.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
	}

	public void saveMsgToFile(String messagetype, String msg, Exception exception, String filetype) throws Exception {
		this.file_type = filetype;
		saveMsgToFile(messagetype, msg, exception);
	}

}
