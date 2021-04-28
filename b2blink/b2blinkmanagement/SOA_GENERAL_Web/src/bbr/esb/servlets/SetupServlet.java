package bbr.esb.servlets;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bbr.esb.constants.Constants;

/**
 * @version 1.0
 * @author
 */
public class SetupServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6260465131970777650L;

	private static Logger logger = LoggerFactory.getLogger(SetupServlet.class);

	private void doInitConstantsPropertiesFile(ServletConfig config) throws ServletException {
		// Extrae el path donde se encuentra el contexto
		// Asume que el archivo de configuración se encuentra en este directorio
		String realpath = getServletContext().getRealPath("/");
		String downloadpath = realpath + "download" + File.separator;
		String file = "constants.properties";
		String absolutefile = realpath + File.separator + "WEB-INF" + File.separator + file;
		logger.info("Ruta a descarga archivos: " + downloadpath);
		logger.info("Ruta a Constants.properties: " + absolutefile);
		if (file == null || file.length() == 0 || !(new File(absolutefile)).isFile()) {
			System.err.println("ERROR: No se puede leer el archivo de configuración de Constants. ");
			throw new ServletException();
		}
		Constants.setPropertyfilepath(absolutefile);
		Constants.setDownloadpath(downloadpath);

		// A continuación se obtiene el nombre del EAR para setearlo al sistema. Para esto se divide la ruta (realpath)
		// en dos partes, la segunda parte contiene el nombre del EAR.
		// EJEMPLO DE REALPATH =
		// "C:\Archivos_de_programa\jboss-4.2.2.GA\server\abcdin\.\deploy\ABCDinLogisticaEAR_JMA_20090123.ear\ABCDinLogisticaWS.war"
		String fileseparator = new Character(File.separatorChar).toString();
		String deploy = "deploy";
		// windows
		if (fileseparator.equalsIgnoreCase("\\"))
			deploy = deploy + "\\\\";
		// linux
		else
			deploy = deploy + "/";
		String[] realpathSplitted = realpath.split(deploy);
		// pathsplit[0] = "C:\Archivos de programa\jboss-4.2.2.GA\server\abcdin\.\"
		// pathsplit[1] = "ABCDinLogisticaEAR_JMA_20090123.ear\ABCDinLogisticaWS.war"
		String secondPart = realpathSplitted[1];
		String earname = secondPart.split(".ear")[0];
		Constants.setPropertyEarName(earname);
		System.out.println("NOMBRE DEL EAR CARGADO: " + earname);

	}

	/**
	 * @see javax.servlet.GenericServlet#void ()
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		doInitConstantsPropertiesFile(config);
	}
}
