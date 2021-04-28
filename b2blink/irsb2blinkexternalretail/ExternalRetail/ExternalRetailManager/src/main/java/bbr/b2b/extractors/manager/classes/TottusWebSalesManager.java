package bbr.b2b.extractors.manager.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.security.auth.login.LoginException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.awaitility.core.ConditionTimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.sun.xml.bind.marshaller.DataWriter;

import bbr.b2b.b2blink.commercial.xml.ReporteInventario.Inventario;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.ObjectFactory;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.Ventas;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.Ventas.Comprador;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.Ventas.Vendedor;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.extractors.manager.interfaces.TottusWebSalesManagerLocal;
import bbr.b2b.extractors.timers.manager.interfaces.SchedulerManagerLocal;
import bbr.b2b.extractors.utils.MyCharacterEscapeHandler;
import bbr.b2b.extractors.utils.WsSoaService;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.logistic.utils.JsonParser;
import bbr.esb.services.webservices.facade.InitParamCSDTO;
import io.github.bonigarcia.wdm.WebDriverManager;

@Stateless(name = "managers/TottusWebSalesManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TottusWebSalesManager implements TottusWebSalesManagerLocal {

	private static Logger logger = Logger.getLogger(TottusWebSalesManager.class);
	private static Logger soaMessage = Logger.getLogger("SOAMessage");
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final String SITECODE = "CL1502";
	private static final String SITENAME = "TOTTUS";
	private static final String PORTALNAME = "TOTTUS COMERCIAL";
	
	private WebDriver driver = null;

	private SimpleDateFormat pageFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final int NAMEINDEX = 0;
	private static final int IDINDEX = 1;
	private static final int DATEINDEX = 2;
	private static final int STATUSINDEX = 3;
	private static final int ACTIONSINDEX = 4;

	@EJB
	SchedulerManagerLocal schmanager;

	@Resource
	private SessionContext ctx;

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	public void doProcess() throws Exception {
		try {
			logger.info("Empresa: " + SITENAME + " DESCARGANDO VENTAS E INVENTARIO");
			boolean isHideMode = B2BSystemPropertiesUtil.getProperty("HIDEMODEFIREFOX").equals("true");
			String geckoDriverPath = B2BSystemPropertiesUtil.getProperty("CHROMEDRIVER");
			String finalPath = B2BSystemPropertiesUtil.getProperty("FINALPATH") + "sales" + File.separator;
			String tempPath = finalPath + "temp" + File.separator;
			Files.createDirectories(Paths.get(tempPath));

			String service = "RVT";
			logger.info("Uso de proxy "	+ (B2BSystemPropertiesUtil.getProperty("PROXY").equals("true") ? "HABILITADO" : "DESHABILITADO"));
			WsSoaService wsSoaService;
			List<InitParamCSDTO> credentials;
			wsSoaService = new WsSoaService();
			try {
				credentials = wsSoaService.getCredentials(SITECODE, service);
				if (credentials.size() == 0) {
					logger.info("Empresa: " + SITENAME + " SERVICIO DE DESCARGA DE VENTAS NO CONTRATADO");
				} else {
					logger.info("Empresa: " + SITENAME + " DESCARGANDO VENTAS");
				}
				for (InitParamCSDTO credential : credentials) {
					try {
						JsonParser jsonParser = new JsonParser();
						HashMap<String, String> keys = jsonParser.JsonToHashMap(credential.getCredenciales(), "rut", "user",
								"password", "urlLogin", "maxTimeWaitDownload", "maxNumberOfAttempts", "extension");
						String prefix = "VTA_" + SITECODE + "_" + credential.getAccesscode() + "_";
						List<String> files = doDownload("empresa", "proveedor", keys.get("rut"), keys.get("user"), keys.get("password"),
								tempPath, keys.get("urlLogin"), geckoDriverPath, prefix, finalPath, isHideMode,
								Integer.valueOf(keys.get("maxTimeWaitDownload")),
								Integer.valueOf(keys.get("maxNumberOfAttempts")),
								keys.get("extension")
								);
						for (String file : files) {
							sendFileSales(credential, file);
						}

					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getAccesscode()
								+ " Error (Ventas): " + ex.getMessage());
						throw ex;
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Empresa: " + SITENAME + " Error al descarga ventas");
				throw e;
			}
			try {
				service = "RIV";
				finalPath = B2BSystemPropertiesUtil.getProperty("FINALPATH") + "inventory" + File.separator;
				tempPath = finalPath + "temp" + File.separator;
				Files.createDirectories(Paths.get(tempPath));
				
				credentials = new ArrayList<>();
				credentials = wsSoaService.getCredentials(SITECODE, service);
				if (credentials.size() == 0) {
					logger.info("Empresa: " + SITENAME + " SERVICIO DE DESCARGA DE INVENTARIO NO CONTRATADO");
				} else {
					logger.info("Empresa: " + SITENAME + " DESCARGANDO INVENTARIO");
				}
				for (InitParamCSDTO credential : credentials) {
					try {
						JsonParser jsonParser = new JsonParser();
						HashMap<String, String> keys = jsonParser.JsonToHashMap(credential.getCredenciales(), "rut", "user",
								"password", "urlLogin", "maxTimeWaitDownload", "maxNumberOfAttempts", "extension");
						String prefix = "INV_" + SITECODE + "_" + credential.getAccesscode() + "_";
						List<String> files = doDownload("empresa", "proveedor", keys.get("rut"), keys.get("user"), keys.get("password"),
								tempPath, keys.get("urlLogin"), geckoDriverPath, prefix, finalPath, isHideMode,
								Integer.valueOf(keys.get("maxTimeWaitDownload")),
								Integer.valueOf(keys.get("maxNumberOfAttempts")),
								keys.get("extension"));
						for (String file : files) {
							sendFileInventory(credential, file);
						}

					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getAccesscode()
								+ " Error (Inventario): " + ex.getMessage());
						throw ex;
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Empresa: " + SITENAME + " Error al descarga inventario");
				throw e;
			}

		} catch (Exception e) {
			logger.error("Empresa: " + SITENAME + " Proceso terminado con error");
			throw e;
		}

	}

	private void sendFileSales(InitParamCSDTO credential, String filePath) throws OperationFailedException {
		File file = new File(filePath);
		byte[] encodedContent = null;
		try {
			encodedContent = Files.readAllBytes(file.toPath());
		} catch (IOException e1) {
			logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname()	+ " Error: no se encontró el archivo");
		}

		// ------- Creacion de XML -------
		try {
			JAXBContext jc = JAXBContext.newInstance("bbr.b2b.b2blink.commercial.xml.ReporteVentas");
			ObjectFactory objFactory = new ObjectFactory();
			Ventas salesrequest = objFactory.createVentas();

			salesrequest.setNombreportal(PORTALNAME);

			Comprador comprador = objFactory.createVentasComprador();
			comprador.setUnidadNegocio("");
			comprador.setRut(SITECODE);
			comprador.setRazonSocial("");
			salesrequest.setComprador(comprador);

			Vendedor vendedor = objFactory.createVentasVendedor();
			vendedor.setRut(credential.getAccesscode());
			vendedor.setRazonSocial("");
			salesrequest.setVendedor(vendedor);

			// salesrequest.setData(csvResult);

			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			GZIPOutputStream gzipstream = new GZIPOutputStream(outstream);
			byte[] databytes = encodedContent;
			gzipstream.write(databytes);
			gzipstream.close();
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			String data_compressed = encoder.encode(outstream.toByteArray());
			salesrequest.setData(data_compressed);

			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			// The below code will take care of avoiding the conversion of < to
			// &lt; and > to &gt; etc
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", new MyCharacterEscapeHandler());

			// Perform Marshalling operation
			m.marshal(salesrequest, dataWriter);

			String msg = stringWriter.toString();
			soaMessage.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Ventas: "	+ file.getName());
			soaMessage.info(msg);
			schmanager.doSendMessageQueue(SITECODE, credential.getAccesscode(), file.getName(), msg, null);
			logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Ventas: "	+ file.getName() + " enviada a MQ");

		} catch (JAXBException e2) {
			e2.printStackTrace();
			throw new OperationFailedException(e2.getMessage());
		} catch (Exception e) {
			throw new OperationFailedException(e.getMessage());
		}
	}

	private void sendFileInventory(InitParamCSDTO credential, String filePath) throws OperationFailedException {
		File file = new File(filePath);
		byte[] encodedContent = null;
		try {
			encodedContent = Files.readAllBytes(file.toPath());
		} catch (IOException e1) {
			logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname()	+ " Error: no se encontró el archivo");
		}

		// ------- Creacion de XML -------

		try {
			JAXBContext jc = JAXBContext.newInstance("bbr.b2b.b2blink.commercial.xml.ReporteInventario");
			bbr.b2b.b2blink.commercial.xml.ReporteInventario.ObjectFactory objFactory = new bbr.b2b.b2blink.commercial.xml.ReporteInventario.ObjectFactory();
			Inventario request = objFactory.createInventario();

			request.setNombreportal(PORTALNAME);

			bbr.b2b.b2blink.commercial.xml.ReporteInventario.Inventario.Comprador comprador = objFactory.createInventarioComprador();
			comprador.setUnidadNegocio("");
			comprador.setRut(SITECODE);
			comprador.setRazonSocial("");
			request.setComprador(comprador);

			bbr.b2b.b2blink.commercial.xml.ReporteInventario.Inventario.Vendedor vendedor = objFactory.createInventarioVendedor();
			vendedor.setRut(credential.getAccesscode());
			vendedor.setRazonSocial("");
			request.setVendedor(vendedor);

			// salesrequest.setData(csvResult);

			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			GZIPOutputStream gzipstream = new GZIPOutputStream(outstream);
			byte[] databytes = encodedContent;
			gzipstream.write(databytes);
			gzipstream.close();
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			String data_compressed = encoder.encode(outstream.toByteArray());
			request.setData(data_compressed);

			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			// The below code will take care of avoiding the conversion of < to
			// &lt; and > to &gt; etc
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", new MyCharacterEscapeHandler());

			// Perform Marshalling operation
			m.marshal(request, dataWriter);

			String msg = stringWriter.toString();
			System.out.print(msg);
			schmanager.doSendMessageQueue(SITECODE, credential.getAccesscode(), file.getName(), msg, null);
			logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Ventas: " + file.getName() + " enviada a MQ");

		} catch (JAXBException e2) {
			e2.printStackTrace();
			throw new OperationFailedException(e2.getMessage());
		} catch (Exception e) {
			throw new OperationFailedException(e.getMessage());
		}
	}

	private WebDriver init(String geckoDriverpath, String downloadPath, boolean hideMode) {
		driver = null;
		if (driver == null) {
			logger.info("CARGANDO \"geckoDriver\" " + geckoDriverpath);
			// se carga ruta de geckodriver
			//System.setProperty("webdriver.chrome.driver", geckoDriverpath);			
			java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
			System.setProperty("webdriver.chrome.silentOutput", "true");
			// Add the WebDriver proxy capability.
			logger.info("CARGANDO PROFILE");
			
			WebDriverManager.chromedriver().config().setCachePath(B2BSystemPropertiesUtil.getProperty("DRIVERPATH"));
			WebDriverManager.chromedriver().browserVersion(B2BSystemPropertiesUtil.getProperty("BROWSERVERSION")).setup();
			
			ChromeOptions options = new ChromeOptions();
			// set directorio de descarga
			logger.info("browser.download.dir -> " + downloadPath);
			
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadPath);
			
			options.setExperimentalOption("prefs", chromePrefs);
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);

			logger.info("CARGANDO OPTIONS");
			// config uso de proxy
			if (B2BSystemPropertiesUtil.getProperty("PROXY").equals("true")) {
				String proxyAdress = B2BSystemPropertiesUtil.getProperty("PROXY_HOST");
				int proxyPort = Integer.valueOf(B2BSystemPropertiesUtil.getProperty("PROXY_PORT"));
				logger.info("CARGANDO PROXY " + proxyAdress + ":" + proxyPort);
				Proxy proxy = new Proxy();
				proxy.setHttpProxy(proxyAdress + ":" + proxyPort);
				proxy.setSslProxy(proxyAdress + ":" + proxyPort);
				proxy.setAutodetect(false);
				options.setCapability("proxy", proxy);
				options.addArguments("--proxy-server=http://" + proxyAdress + ":" + proxyPort);
				
				WebDriverManager.chromedriver().config().setProxy(proxyAdress + ":" + proxyPort);
				
				System.setProperty("http.proxyHost", proxyAdress);
				System.setProperty("http.proxyPort", String.valueOf(proxyPort));
				System.setProperty("https.proxyHost", proxyAdress);
				System.setProperty("https.proxyPort", String.valueOf(proxyPort)); 
				
			}
			
			// set tipo de despligue, modo no display = true
			logger.info("HIDE MODE: " + (hideMode ? "true" : "false"));
			options.setHeadless(hideMode);
			logger.info("SETTING PROFILE");
			driver = new ChromeDriver(options);
			if (driver instanceof RemoteWebDriver) {
		          ((RemoteWebDriver) driver).setLogLevel(Level.OFF);
		    } 
		}

		return driver;
	}
	


	private List<String> doDownload(String empresa, String rutProveedor, String rut, String user, String pass, String downloadPath,
			String urlLogin, String geckoDriverpath, String prefix, String finalPath, boolean hideMode,
			int maxTimeWaitDownload, int maxNumberOfAttempts, String extension)
			throws Exception {
		List<String> files = new ArrayList<String>();
		boolean isLogged = false;
		
		driver = init(geckoDriverpath, downloadPath, hideMode);
		int i = 0;
		while (i < maxNumberOfAttempts) {
			try {
				if (!isLogged) {
					logger.info("INTENTO -> " + (i + 1) + " <- INTENTO");
					logger.info("CARGANDO -> " + urlLogin);
					driver.get(urlLogin);
					logger.info("PÁGINA CARGADA");
					Thread.sleep(5000);
					System.out.println(driver.getPageSource());
					driver.switchTo().frame(driver.findElement(By.name("mainFrame")));
					
					Select select = new Select(driver.findElement(By.id("CADENA")));
					select.selectByValue("8");
					
					//busncado textbox empresa
					logger.info("CARGANDO EMPRESA");
					WebElement empresaFirefox = driver.findElement(By.id("empresa"));
					empresaFirefox.sendKeys(rut);
					
					// busncado textbox usuario
					logger.info("CARGANDO USUARIO");
					WebElement userFirefox = driver.findElement(By.id("usuario"));
					userFirefox.sendKeys(user);
					
					logger.info("CARGANDO PASSWORD");
					WebElement passFirefox = driver.findElement(By.id("clave"));
					passFirefox.sendKeys(pass);
					logger.info("LOGIN");
					WebElement button = driver.findElement(By.id("entrar2"));
					button.click();
					logger.info("ESPERANDO");
					Thread.sleep(10000);
					
					try {
						logger.info("VALIDANDO LOGIN");
						WebElement errorWebElement = driver.findElement(By.xpath("//td[@class='titPagina']"));
						String error = errorWebElement.getText();
						logger.info(error);
						if (error.equals("ERROR")) {
							throw new LoginException("ERROR: Usuario y/o contraseña incorrectos");
						} else {
							throw new NoSuchElementException("");
						}
					} catch (NoSuchElementException e) {
						isLogged = true;
						logger.info("USUARIO LOGEADO");
					}
				}
				
				logger.info("BUSCANDO VENTAS ");
				Thread.sleep(10000);
				Actions action = new Actions(driver);
				WebElement topMenu = driver.findElement(By.id("Bar2"));
				WebElement button = driver.findElement(By.xpath("//div[@title='Informe de Ventas e Inventario']"));
				action.moveToElement(topMenu).moveToElement(button).click().build().perform();
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY"); 
				LocalDate yesterday = LocalDate.now().minusDays(1);
				
				WebElement desde = driver.findElement(By.id("desde"));
				desde.sendKeys(formatter.format(yesterday));
				
				WebElement hasta = driver.findElement(By.id("hasta"));
				hasta.sendKeys(formatter.format(yesterday));
				
				WebElement check = driver.findElement(By.id("chkstock"));
				check.click();
				
				WebElement downloadButton = driver.findElement(By.name("botonBuscar"));
				String id = "datos";
				Files.deleteIfExists(Paths.get(downloadPath, id + "." + extension));
				logger.info("DESCARGANDO ARCHIVO -> " + id + "." + extension);
				Path filePath = Paths.get(downloadPath, id + "." + extension);
				logger.trace("ESPERANDO CREACION DE ARCHIVO TEMPORAL -> " + filePath.getFileName());
				
				// se espera maximo un minuto que se cree el archivo
				// temporal
				logger.trace("DESCARGANDO....");
				downloadButton.click();
				Thread.sleep(3000);
				logger.info("ESPERANDO ARCHIVO -> " + filePath.toFile().toString());
				LocalDateTime dtl = LocalDateTime.now().plusMinutes(5);
				while(!filePath.toFile().exists()){
					if(dtl.isBefore(LocalDateTime.now())){
						Thread.sleep(1000);
					} else {
						throw new Exception("Archivo no se pudo descargar(documento no existe)");
					}
				}

				// se espera mAximo X minutos que se elimine el archivo
				// temporal (property)
				logger.info("FIN DESCARGA ARCHIVO -> " + id);

				// nombre final de archivo
				String datestr = sdf.format(new Date());
				String newFileName = finalPath + prefix + datestr + "." + extension;
				String finalName = prefix + datestr + "." + extension;
				
				addColumn(downloadPath, id + "." + extension, yesterday, finalPath, finalName);
				
				files.add(newFileName);
				
				Path fileToDeletePath = Paths.get(downloadPath+id + "." + extension);
			    Files.delete(fileToDeletePath); 

				
				logger.info("FIN DESCARGA");
				logger.trace("INICIANDO CIERRE DE VENTANAS");
				driver.close();
				driver = null;
				logger.info("NAVEGADOR CERRADO");
				break;
			} catch (ConditionTimeoutException ctoe) {
				i++;
				if (i == maxNumberOfAttempts) {
					driver.close();
					throw new TimeoutException("Archivo no se pudo descargar en el tiempo maximo establecido("
							+ maxTimeWaitDownload + " min)");
					// throw ctoe;
				} else {
					logger.warn(ctoe);
					driver.close();
					driver = init(geckoDriverpath, downloadPath, hideMode);
				}
			} catch (Exception e) {
				i++;
				driver.close();
				if (i == maxNumberOfAttempts) {
					throw e;
				} else {
					logger.warn(e);
				}

			}
		}
		return files;
	}
	
	public void addColumn(String path,String fileName, LocalDate fecha, String newPath, String finalName) throws Exception{
	    BufferedReader br=null;
	    BufferedWriter bw=null;
	    final String lineSep=",";

	    try {
	        File file = new File(path, fileName);
	        File file2 = new File(newPath, finalName);

	        br = new BufferedReader(new InputStreamReader(new FileInputStream(file))) ;
	        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2)));
	        String line = null;
	        int i=0;
	        for ( line = br.readLine(); line != null; line = br.readLine(),i++)
	        {   
	        	 String addedColumn = i==0 ? "Fecha" : String.valueOf(fecha);
	           	 bw.write(addedColumn+lineSep+line);
	           	 bw.newLine();
	        }

	    }catch(Exception e){
	    	e.printStackTrace();
	    	throw new Exception("Error al agregar columna fecha al archivo");
	    }finally  {
	        if(br!=null)
	            br.close();
	        if(bw!=null)
	            bw.close();
	        logger.info("COLUMNA FECHA AGREGADA");
	    }

	}
}
