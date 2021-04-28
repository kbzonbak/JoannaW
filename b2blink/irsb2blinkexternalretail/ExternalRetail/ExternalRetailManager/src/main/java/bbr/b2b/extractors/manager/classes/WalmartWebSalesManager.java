package bbr.b2b.extractors.manager.classes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
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
import org.openqa.selenium.remote.RemoteWebDriver;

import com.sun.xml.bind.marshaller.DataWriter;

import bbr.b2b.b2blink.commercial.xml.ReporteInventario.Inventario;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.ObjectFactory;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.Ventas;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.Ventas.Comprador;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.Ventas.Vendedor;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.keycloak.classes.JsonUtils;
import bbr.b2b.extractors.manager.interfaces.WalmartWebSalesManagerLocal;
import bbr.b2b.extractors.timers.manager.interfaces.SchedulerManagerLocal;
import bbr.b2b.extractors.utils.MyCharacterEscapeHandler;
import bbr.b2b.extractors.utils.WsSoaService;
import bbr.b2b.extractors.walmart.dto.CredentialsDataDTO;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.esb.services.webservices.facade.InitParamCSDTO;
import io.github.bonigarcia.wdm.WebDriverManager;

@Stateless(name = "managers/WalmartWebSalesManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class WalmartWebSalesManager implements WalmartWebSalesManagerLocal {

	private static Logger logger = Logger.getLogger(WalmartWebSalesManager.class);

	private static Logger soaMessage = Logger.getLogger("SOAMessage");

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	private static final String SITECODE = "CL0702";

	private static final String SITENAME = "WALMART";

	private static final String PORTALNAME = "WALMART COMERCIAL";

	private WebDriver driver = null;

	@EJB
	SchedulerManagerLocal schmanager;

	@Resource
	private SessionContext ctx;

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	public void doProcess() throws Exception {
		try {
			logger.info("Empresa: " + SITENAME + " DESCARGANDO VENTAS");
			boolean isHideMode = B2BSystemPropertiesUtil.getProperty("HIDEMODEFIREFOX").equals("true");
			String geckoDriverPath = B2BSystemPropertiesUtil.getProperty("CHROMEDRIVER");
			String finalPath = B2BSystemPropertiesUtil.getProperty("FINALPATH") + "sales" + File.separator;
			String tempPath = finalPath + "temp" + File.separator;
			Files.createDirectories(Paths.get(tempPath));

			String service = "RVT";
			logger.info("Uso de proxy " + (B2BSystemPropertiesUtil.getProperty("PROXY").equals("true") ? "HABILITADO" : "DESHABILITADO"));
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
						CredentialsDataDTO credentialsdata = JsonUtils.getObjectFromJson(credential.getCredenciales(),
								CredentialsDataDTO.class);
						String prefix = "VTA_" + SITECODE + "_" + credential.getAccesscode() + "_";

						List<String> files = doDownload("empresa", "proveedor", credentialsdata.getUserId(), credentialsdata.getUser(),
								credentialsdata.getPassword(), tempPath, credentialsdata.getUrlLogin(), geckoDriverPath,
								prefix, finalPath, isHideMode,
								Integer.valueOf(credentialsdata.getMaxTimeWaitDownload()),
								Integer.valueOf(credentialsdata.getMaxNumberOfAttempts()),
								credentialsdata.getStatusToDownload().replace(" ", "").split(","),
								credentialsdata.getFileNameToDownload(), credentialsdata.getExtension());
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
				logger.info("Empresa: " + SITENAME + " DESCARGANDO INVENTARIO");
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
						CredentialsDataDTO credentialsdata = JsonUtils.getObjectFromJson(credential.getCredenciales(),
								CredentialsDataDTO.class);
						String prefix = "INV_" + SITECODE + "_" + credential.getAccesscode() + "_";

						List<String> files = doDownload("empresa", "proveedor", credentialsdata.getUserId(), credentialsdata.getUser(),
								credentialsdata.getPassword(), tempPath, credentialsdata.getUrlLogin(), geckoDriverPath,
								prefix, finalPath, isHideMode,
								Integer.valueOf(credentialsdata.getMaxTimeWaitDownload()),
								Integer.valueOf(credentialsdata.getMaxNumberOfAttempts()),
								credentialsdata.getStatusToDownload().replace(" ", "").split(","),
								credentialsdata.getFileNameToDownload(), credentialsdata.getExtension());
						for (String file : files) {
							sendFileInventory(credential, file);
						}

					} catch (Exception ex) {
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

	public void doProcessInventario() throws Exception {
		try {
			logger.info("Empresa: " + SITENAME + " DESCARGANDO VENTAS E INVENTARIO");
			boolean isHideMode = B2BSystemPropertiesUtil.getProperty("HIDEMODEFIREFOX").equals("true");
			String geckoDriverPath = B2BSystemPropertiesUtil.getProperty("CHROMEDRIVER");
			String finalPath = B2BSystemPropertiesUtil.getProperty("FINALPATH") + "inventory" + File.separator;
			String tempPath = finalPath + "temp" + File.separator;
			Files.createDirectories(Paths.get(tempPath));

			String service = "RVT";
			logger.info("Uso de proxy " + (B2BSystemPropertiesUtil.getProperty("PROXY").equals("true") ? "HABILITADO" : "DESHABILITADO"));
			WsSoaService wsSoaService;
			List<InitParamCSDTO> credentials;
			wsSoaService = new WsSoaService();
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

						CredentialsDataDTO credentialsdata = JsonUtils.getObjectFromJson(credential.getCredenciales(),
								CredentialsDataDTO.class);
						String prefix = "INV_" + SITECODE + "_" + credential.getAccesscode() + "_";

						List<String> files = doDownload("empresa", "proveedor", credentialsdata.getUserId(), credentialsdata.getUser(),
								credentialsdata.getPassword(), tempPath, credentialsdata.getUrlLogin(), geckoDriverPath,
								prefix, finalPath, isHideMode,
								Integer.valueOf(credentialsdata.getMaxTimeWaitDownload()),
								Integer.valueOf(credentialsdata.getMaxNumberOfAttempts()),
								credentialsdata.getStatusToDownload().replace(" ", "").split(","),
								credentialsdata.getFileNameToDownload(), credentialsdata.getExtension());
						for (String file : files) {
							sendFileInventory(credential, file);
						}

					} catch (Exception ex) {
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
			logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Error: no se encontró el archivo");
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
			soaMessage.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Ventas: " + file.getName());
			soaMessage.info(msg);
			schmanager.doSendMessageQueue(SITECODE, credential.getAccesscode(), file.getName(), msg, null);
			logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Ventas: " + file.getName() + " enviada a MQ");

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
			logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Error: no se encontró el archivo");
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
			// System.setProperty("webdriver.chrome.driver", geckoDriverpath);

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
			chromePrefs.put("download.prompt_for_download", false);
			chromePrefs.put("download.directory_upgrade", true);
			chromePrefs.put("behavior", "allow");
			options.addArguments("--test-type");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-gpu");
			options.addArguments("window-size=1600x900");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-popup-blocking");

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

	private List<String> doDownload(String empresa, String rutProveedor, String userId, String user, String pass, String downloadPath,
			String urlLogin, String geckoDriverpath, String prefix, String finalPath, boolean hideMode,
			int maxTimeWaitDownload, int maxNumberOfAttempts, String[] statusToDownload, String fileNameToDownload, String extension)
			throws Exception {
		List<String> files = new ArrayList<String>();
				
		for (int i = 0; i < maxNumberOfAttempts; i++) {
			try {
				
				boolean isLogged = false;
				
				driver = init(geckoDriverpath, downloadPath, hideMode);
				
				if (!isLogged) {
					logger.info("INTENTO -> " + (i + 1) + " <- INTENTO");
					logger.info("CARGANDO -> " + urlLogin);
					driver.get(urlLogin);
					logger.info("PÁGINA CARGADA");
					Thread.sleep(5000);
					// busncado textbox usuario
					logger.info("CARGANDO USUARIO");
					WebElement userFirefox = driver.findElement(By.xpath("//input[@data-automation-id='uname']"));
					userFirefox.sendKeys(user);

					logger.info("CARGANDO PASSWORD");
					WebElement passFirefox = driver.findElement(By.xpath("//input[@data-automation-id='pwd']"));
					passFirefox.sendKeys(pass);
					logger.info("LOGIN");
					WebElement button = driver.findElement(By.xpath("//button[@data-automation-id='loginBtn']"));
					button.click();
					logger.info("ESPERANDO");
					Thread.sleep(10000);

					try {
						logger.info("VALIDANDO LOGIN");
						WebElement errorWebElement = driver.findElement(By.xpath("//div[@data-automation-id='alert-element']"));
						String error = errorWebElement.getText();
						logger.info(error);
						if (error.equals("")) {
							continue;
						} else {
							error = error.substring(0, error.indexOf("\n"));
							logger.info(error);
							continue;
						}
					} catch (NoSuchElementException e) {
						isLogged = true;
						logger.info("USUARIO LOGEADO");
					}
				}

				logger.info("Accediento a Ventas/Inventario (Request-Page)");
				driver.get("https://retaillink.wal-mart.com/rl_portal/#/request-page");
				Thread.sleep(15000);
				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				Date today = formatter.parse(formatter.format(new Date()));

				Boolean download = false;

				List<WebElement> groups = new ArrayList<WebElement>();
				List<WebElement> contents = new ArrayList<WebElement>();
				Thread.sleep(15000);

				boolean filesExists = true;
				try {
					((ChromeDriver) driver).findElementByClassName("rt-tbody");
				} catch (Exception e) {
					filesExists = false;
					logger.error("NO EXISTEN REGISTROS EN LA TABLA");
					logger.trace("INICIANDO CIERRE DE VENTANAS");
					String homeWindow = driver.getWindowHandle();
					Set<String> allWindows = driver.getWindowHandles();
					for (String childWindow : allWindows) {						
						logger.trace("CERRANDO VENTANA -> " + childWindow);
						if (!homeWindow.equals(childWindow)) {
							driver.switchTo().window(childWindow);
							driver.close();
						}
					}
					logger.trace("FIN CIERRE DE VENTANAS");
					driver.switchTo().window(homeWindow);
					logger.info("NAVEGADOR CERRADO");
					driver.close();
					continue;		
				}

				if (filesExists) {
					WebElement tablebody = ((ChromeDriver) driver).findElementByClassName("rt-tbody");

					groups = tablebody.findElements(By.className("rt-tr-group"));// filas
					for (WebElement group : groups) {
						WebElement row = group.findElement(By.className("rt-tr"));// fila
						if (row.getAttribute("class").toString().contains("-padRow")) {// excluye filas vacias
							continue;
						}
						if (row != null) {
							contents = row.findElements(By.className("rt-td"));// contenidos
							String name = "";
							String date = "";
							String status = "";
							String fileId = "";
							Date datef = formatter.parse(formatter.format(new Date()));
							for (int j = 0; j < contents.size(); j++) {
								switch (j) {
								case 0: // nombre y fecha
									List<WebElement> test = contents.get(j).findElement(By.tagName("div")).findElements(By.tagName("p"));
									name = test.get(0).getAttribute("data-tip");
									System.out.println(name);

									fileId = name.substring(name.indexOf(":") + 1).trim();
									System.out.println(fileId);

									date = test.get(1).getAttribute("data-tip");
									date = date.replace("Date: ", "");
									datef = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
									System.out.println(date);
									break;
								case 1:
									status = contents.get(j).findElement(By.tagName("p")).getText();
									System.out.println(status);
									break;
								case 2:
									if (name.contains(fileNameToDownload) && datef.after(today) && Arrays.asList(statusToDownload).contains(status)) {
										download = true;
										Paths.get(downloadPath);
										logger.info("DESCARGANDO ARCHIVO -> " + fileId);
										contents.get(j).findElement(By.tagName("div")).findElement(By.tagName("p")).findElement(By.className("icon-download")).click();
										// se espera maximo un minuto que se cree el archivo
										logger.info("DESCARGANDO....");
										String downloadedfilename = "";

										try {
											// Path filePath = Paths.get(downloadPath, fileId + "." + extension);
											logger.info("ESPERANDO ARCHIVO... ID " + fileId);
											LocalDateTime dtl = LocalDateTime.now().plusMinutes(5);
											Thread.sleep(20000);
											boolean downloadedfile = false;
											while (!downloadedfile && dtl.isAfter(LocalDateTime.now())) { // buscar si
																											// contiene
																											// el nombre
																											// user+iddocumento

												String file = fileId;
												File f = new File(downloadPath);

												File[] matchingFiles = f.listFiles(new FilenameFilter() {
													public boolean accept(File dir, String name) {
														return name.startsWith(userId + "_" + file) && name.endsWith(extension);
													}
												});
												if (matchingFiles.length > 0) {
													downloadedfilename = matchingFiles[0].getName();
													logger.info("Archivo encontrado " + downloadedfilename);
													downloadedfile = true;
												}
												Thread.sleep(1000);
											}
										} catch (ConditionTimeoutException ctoe) {
											logger.error("Archivo no se pudo descargar(documento no existe) ");
											continue;
										}
										logger.info("FIN DESCARGA ARCHIVO..");

										// nombre final de archivo
										String newFileName = finalPath + prefix + sdf.format(new Date()) + "." + extension;
										Thread.sleep(5000);
										// se mueve archivo de ruta temporal a final
										File file = new File(downloadPath); // Change this to the directory you want to
																			// search in.
										String[] filesarr = file.list(); // get the files in String format.
										for (String fileName : filesarr) {
											if (fileName.equals(downloadedfilename)) {
												logger.info("MOVIENDO ARCHIVO -> " + downloadedfilename + " a " + newFileName);
												Files.move(Paths.get(downloadPath, fileName), Paths.get(newFileName),
														StandardCopyOption.ATOMIC_MOVE);
												files.add(newFileName);
											}
										}
									}
									break;
								default:
									break;
								}
							}
						}

					}

					if (!download) {
						logger.info("NO HAY ARCHIVOS QUE CUMPLAN LOS REQUISITOS PARA DESCARGAR");
					} else {
						logger.info("FIN DESCARGA");
					}
				}

				logger.trace("INICIANDO CIERRE DE VENTANAS");
				String homeWindow = driver.getWindowHandle();
				Set<String> allWindows = driver.getWindowHandles();
				for (String childWindow : allWindows) {
					// Store the Recruiter window id
					logger.trace("CERRANDO VENTANA -> " + childWindow);
					if (!homeWindow.equals(childWindow)) {
						driver.switchTo().window(childWindow);
						driver.close();
					}
				}
				logger.trace("FIN CIERRE DE VENTANAS");
				driver.switchTo().window(homeWindow);

				driver.close();
				driver = null;
				logger.info("NAVEGADOR CERRADO");
				break;
			} catch (ConditionTimeoutException ctoe) {
				driver.close();			
				continue;
			} catch (Exception e) {
				// CERRAMOS EL NAVEGADOR PARA COMENZAR OTRO INTENTO
				driver.close();	
				e.printStackTrace();

			}
		}
		return files;
	}
}
