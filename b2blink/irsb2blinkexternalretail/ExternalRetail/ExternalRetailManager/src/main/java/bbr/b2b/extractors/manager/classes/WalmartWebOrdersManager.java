package bbr.b2b.extractors.manager.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

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
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.extractor.OldExcelExtractor;
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

import bbr.b2b.b2blink.logistic.xml.RecCustomer.Local;
import bbr.b2b.b2blink.logistic.xml.RecCustomer.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.RecCustomer.Recepcion;
import bbr.b2b.b2blink.logistic.xml.RecCustomer.Recepcion.Details;
import bbr.b2b.b2blink.logistic.xml.RecCustomer.Recepcion.Locals;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.keycloak.classes.JsonUtils;
import bbr.b2b.extractors.manager.interfaces.WalmartWebOrdersManagerLocal;
import bbr.b2b.extractors.timers.manager.interfaces.SchedulerManagerLocal;
import bbr.b2b.extractors.utils.WsSoaService;
import bbr.b2b.extractors.walmart.dto.CredentialsDataDTO;
import bbr.b2b.extractors.walmart.dto.OrderReceptionDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckReceptionInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckReceptionResultDTO;
import bbr.b2b.logistic.rest.client.CustomerLogisticRestFulWSClient;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.esb.services.webservices.facade.InitParamCSDTO;
import io.github.bonigarcia.wdm.WebDriverManager;

@Stateless(name = "managers/WalmartWebOrdersManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class WalmartWebOrdersManager implements WalmartWebOrdersManagerLocal {

	private static Logger logger = Logger.getLogger(WalmartWebOrdersManager.class);

	private static JAXBContext jc = null;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	private static final String SITECODE = "CL0701";

	private static final String SITENAME = "WALMART";

	private static final String SITERUT = "76042014K";

	private WebDriver driver = null;

	CustomerLogisticRestFulWSClient client = null;

	@EJB
	SchedulerManagerLocal schmanager;

	@Resource
	private SessionContext ctx;

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.RecCustomer");
		return jc;
	}

	public void doProcess() throws Exception {
		try {
			logger.info("Empresa: " + SITENAME + " DESCARGANDO RECEPCIONES DE OC");
			boolean isHideMode = B2BSystemPropertiesUtil.getProperty("HIDEMODEFIREFOX").equals("true");
			String geckoDriverPath = B2BSystemPropertiesUtil.getProperty("CHROMEDRIVER");
			String finalPath = B2BSystemPropertiesUtil.getProperty("WALMART_OCRECEPTIONS_PATH");
			String tempPath = finalPath + "temp" + File.separator;

			Files.createDirectories(Paths.get(tempPath));

			String service = "LRM";
			logger.info("Uso de proxy " + (B2BSystemPropertiesUtil.getProperty("PROXY").equals("true") ? "HABILITADO" : "DESHABILITADO"));
			WsSoaService wsSoaService;
			List<InitParamCSDTO> credentials;
			wsSoaService = new WsSoaService();
			client = CustomerLogisticRestFulWSClient.getInstance(B2BSystemPropertiesUtil.getProperty("urlWSWalmart"));

			try {
				credentials = wsSoaService.getCredentials(SITECODE, service);
				if (credentials.size() == 0) {
					logger.info("Empresa: " + SITENAME + " SERVICIO DE DESCARGA DE ORDENES NO CONTRATADO");
				}

				for (InitParamCSDTO credential : credentials) {
					try {
						CredentialsDataDTO credentialsdata = JsonUtils.getObjectFromJson(credential.getCredenciales(), CredentialsDataDTO.class);

						String prefix = credential.getAccesscode() + "_" + SITERUT + "_";

						List<String> files = doDownload("empresa", "proveedor", credentialsdata.getUserId(), credentialsdata.getUser(),
								credentialsdata.getPassword(), tempPath, credentialsdata.getUrlLogin(), geckoDriverPath,
								prefix, finalPath, isHideMode,
								Integer.valueOf(credentialsdata.getMaxTimeWaitDownload()),
								Integer.valueOf(credentialsdata.getMaxNumberOfAttempts()),
								credentialsdata.getStatusToDownload().replace(" ", "").split(","),
								credentialsdata.getFileNameToDownload(), credentialsdata.getExtension());
						for (String file : files) {

							// COMENTADO YA QUE EL FLUJO LLEGA SOLO HASTA LA DESCARGA DEL ARCHIVO,
							// EL QUE SE DEJA EN UNA CARPETA MONTADA EN walmart23
							// QUEDA PENDIENTE INCLUIR ENVIO DE ARCHIVO A SOA
							// doProccessExcel(credential, file);
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

		} catch (Exception e) {
			logger.error("Empresa: " + SITENAME + " Proceso terminado con error");
			throw e;
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
			// setea directorio de descarga
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
			int maxTimeWaitDownload, int maxNumberOfAttempts, String[] statusToDownload, String fileNameToDownload,
			String extension) throws Exception {
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

				logger.info("Accediento a ordenes (Request-Page)");
				driver.get("https://retaillink.wal-mart.com/rl_portal/#/request-page");
				logger.info("BUSCANDO ORDENES..");
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
									// BUSCA COINCIDENCIAS DEL DÍA ACTUAL
									if (name.contains(fileNameToDownload) && datef.after(today) && Arrays.asList(statusToDownload).contains(status)) {
										download = true;
										Paths.get(downloadPath);
										logger.info("DESCARGANDO ARCHIVO -> " + fileId);

										contents.get(j).findElement(By.tagName("div")).findElement(By.tagName("p")).findElement(By.xpath("//*[contains(@class,'icon-download')]")).click();

										// se espera maximo un minuto que se cree el archivo
										logger.info("DESCARGANDO....");
										String downloadedfilename = "";
										try {
											// Path filePath = Paths.get(downloadPath, fileId + "." + extension);
											logger.info("ESPERANDO ARCHIVO... ID " + fileId);
											LocalDateTime dtl = LocalDateTime.now().plusMinutes(5);

											boolean downloadedfile = false;
											while (!downloadedfile && dtl.isAfter(LocalDateTime.now())) { // buscar si
																											// contiene
																											// el nombre
																											// user+iddocumento

												String file = fileId;
												File f = new File(downloadPath);

												File[] matchingFiles = f.listFiles(new FilenameFilter() {
													public boolean accept(File dir, String name) {
														return name.startsWith(userId + "_" + file) && name.endsWith("xls");
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
												Files.move(Paths.get(downloadPath, fileName), Paths.get(newFileName), StandardCopyOption.ATOMIC_MOVE);
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

	public void doProccessExcel(InitParamCSDTO credential, String path) throws Exception {
		HashMap<String, List<OrderReceptionDTO>> numOrders = new HashMap<>();
		OldExcelExtractor wb = new OldExcelExtractor(new FileInputStream(path));
		logger.info("Procesando archivo Excel versión BIFF: " + wb.getBiffVersion());
		String excel = wb.getText().intern(); // Traigo los datos del excel
		String data = excel.substring(848); // Selecciono solo lo que quiero
		String[] dataArray = data.split("\n"); // Los transformo en arreglo

		String[] linea = new String[15];
		List<String[]> listaLineas = new ArrayList<String[]>();
		int position = 0;
		int sumador = 14;

		// Cortar el arreglo gigante
		for (int i = 0; i < dataArray.length; i++) {
			linea[position] = dataArray[i];
			// System.out.println("Position:" +position + " Valor: " +linea[position] +" - "+ "i: "+ i +" Valor: " +
			// dataArray[i]);
			position++;
			if ((i != 0) && (i == sumador)) {
				listaLineas.add(linea);
				position = 0;
				sumador = sumador + 15;
				linea = new String[15];
			}
		}

		for (int i = 0; i < listaLineas.size(); i++) {
			OrderReceptionDTO orderReceptionDTO = new OrderReceptionDTO();
			orderReceptionDTO.setNumeroOc(listaLineas.get(i)[0]);

			if (!listaLineas.get(i)[1].equals("")) {
				Date fechaCancelacion = new SimpleDateFormat("MM/dd/yyyy").parse(listaLineas.get(i)[1]);
				orderReceptionDTO.setFechaCancelacionOc(fechaCancelacion);
			}

			orderReceptionDTO.setNumeroLineaOC(Double.parseDouble(listaLineas.get(i)[2]));
			orderReceptionDTO.setNumeroTienda(Double.parseDouble(listaLineas.get(i)[3]));
			orderReceptionDTO.setNombreTienda(listaLineas.get(i)[4]);
			orderReceptionDTO.setIdConsumidor(Double.parseDouble(listaLineas.get(i)[5]));
			orderReceptionDTO.setNumeroArticulo(Double.parseDouble(listaLineas.get(i)[6]));
			orderReceptionDTO.setDescripcion(listaLineas.get(i)[7]);
			orderReceptionDTO.setUpc_emcd(listaLineas.get(i)[8]);
			orderReceptionDTO.setUpc_empro(listaLineas.get(i)[9]);
			orderReceptionDTO.setUpc(listaLineas.get(i)[10]);
			orderReceptionDTO.setDescripcionUpc(listaLineas.get(i)[11]);
			orderReceptionDTO.setTotal(Double.parseDouble(listaLineas.get(i)[12]));
			orderReceptionDTO.setEmcd(Double.parseDouble(listaLineas.get(i)[13]));
			orderReceptionDTO.setTotal_emcd(Double.parseDouble(listaLineas.get(i)[14]));

			if (numOrders.containsKey(orderReceptionDTO.getNumeroOc())) {
				List<OrderReceptionDTO> temLinea = numOrders.get(orderReceptionDTO.getNumeroOc());
				temLinea.add(orderReceptionDTO);
				numOrders.put(orderReceptionDTO.getNumeroOc(), temLinea);
			} else {
				List<OrderReceptionDTO> lineaList = new ArrayList<>();
				lineaList.add(orderReceptionDTO);
				numOrders.put(orderReceptionDTO.getNumeroOc(), lineaList);
			}
		}

		for (HashMap.Entry<String, List<OrderReceptionDTO>> entry : numOrders.entrySet()) {
			CheckReceptionInitParamDTO initParamDTO = new CheckReceptionInitParamDTO();
			initParamDTO.setRecnumber(entry.getKey());
			initParamDTO.setSitecode(SITECODE);
			CheckReceptionResultDTO checkOrderResultDTO = client.doCheckReceptionByRetail(initParamDTO);
			if (!checkOrderResultDTO.isFound()) {
				doTransform(credential, entry.getKey(), entry.getValue());
			} else {
				logger.warn("Orden ya existe en el Customer Service");
			}
		}
	}

	public void doTransform(InitParamCSDTO credential, String numeroOrden, List<OrderReceptionDTO> order)
			throws DatatypeConfigurationException, JAXBException, OperationFailedException {

		bbr.b2b.b2blink.logistic.xml.RecCustomer.ObjectFactory objFactory = new bbr.b2b.b2blink.logistic.xml.RecCustomer.ObjectFactory();
		Recepcion orderXML = objFactory.createRecepcion();

		orderXML.setBuyer(SITECODE);
		orderXML.setVendor(credential.getAccesscode());
		orderXML.setReceptionnumber(Long.parseLong(numeroOrden));
		orderXML.setOrdernumber(Long.parseLong(numeroOrden));
		orderXML.setOrdertype("");
		orderXML.setOrdertypename("");
		orderXML.setComplete(true);

		// TODO:Si no viene la fecha ver que hacer.
		if (order.get(0).getFechaCancelacionOc() != null) {
			Locale locale = new Locale("es", "CL");
			GregorianCalendar gcal = new GregorianCalendar(locale);
			gcal.setTime(order.get(0).getFechaCancelacionOc());
			XMLGregorianCalendar xmlgcal_valid = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			orderXML.setReceptiondate(xmlgcal_valid);
		} else {
			Locale locale = new Locale("es", "CL");
			GregorianCalendar gcal = new GregorianCalendar(locale);
			Date dateSystem = new Date();
			gcal.setTime(dateSystem);
			XMLGregorianCalendar xmlgcal_valid = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			orderXML.setReceptiondate(xmlgcal_valid);
		}

		// TODO: Buscar expresión LAMBDA QUE SUME
		Double total = order.stream().mapToDouble(o -> o.getTotal()).sum();
		orderXML.setTotal(total);

		Local localDelivery = new Local();
		if (order.get(0).getNumeroTienda() != 0 && order.get(0).getNumeroTienda() != 0.0) {
			localDelivery.setCode(String.valueOf(order.get(0).getNumeroTienda()));
			localDelivery.setName(order.get(0).getNombreTienda());
		} else {
			localDelivery.setCode("");
			localDelivery.setName("");
		}
		orderXML.setDeliveryplace(localDelivery);

		orderXML.setPaymentcondition("");
		orderXML.setObservation("");
		orderXML.setResponsible("");

		orderXML.setLocals(setLocal(objFactory, numeroOrden, order));
		orderXML.setDetails(setDetail(objFactory, numeroOrden, order));

		JAXBContext jc = getJC();
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		m.marshal(orderXML, sw);
		String msg = sw.toString();

		logger.info(msg);
		schmanager.doSendMessageQueue(SITENAME, credential.getAccesscode(), String.valueOf(orderXML.getOrdernumber()),
				msg, null);

	}

	public Locals setLocal(ObjectFactory objFactory, String numeroOrden, List<OrderReceptionDTO> receptionLocals) {
		Locals listaLocales = objFactory.createRecepcionLocals();
		List<Local> listaDetalles = listaLocales.getLocal();

		for (OrderReceptionDTO receptionLocal : receptionLocals) {
			if (receptionLocal.getNumeroOc().equals(numeroOrden)) {
				Local local = new Local();
				local.setCode(String.valueOf(receptionLocal.getNumeroTienda()));
				local.setName(String.valueOf(receptionLocal.getNombreTienda()));
				listaDetalles.add(local);
			}
		}
		return listaLocales;
	}

	public Details setDetail(ObjectFactory objFactory, String numeroOrden, List<OrderReceptionDTO> receptions) {
		Details detalles = objFactory.createRecepcionDetails();
		List<bbr.b2b.b2blink.logistic.xml.RecCustomer.Recepcion.Details.Detail> listaDetalles = detalles.getDetail();
		int position = 1;
		for (OrderReceptionDTO reception : receptions) {
			if (reception.getNumeroOc().equals(numeroOrden)) {
				bbr.b2b.b2blink.logistic.xml.RecCustomer.Recepcion.Details.Detail detalle = objFactory
						.createRecepcionDetailsDetail();
				detalle.setPosition(position);
				detalle.setSkubuyer(String.valueOf(reception.getNumeroArticulo()));
				detalle.setSkuvendor(String.valueOf(reception.getNumeroArticulo()));
				detalle.setEan13("");
				detalle.setProductdescription(reception.getDescripcion());
				detalle.setCodeumc(reception.getUpc_emcd());
				detalle.setDescriptionumc(reception.getDescripcionUpc());
				detalle.setDescriptionumb("");
				detalle.setQuantityumc(1); // Al parecer es 1 siempre
				detalle.setReceivedquantity((float) reception.getTotal_emcd());
				detalle.setListcost(0.0);
				detalle.setFinalcost(0.0);
				position++;
				listaDetalles.add(detalle);
			}
		}
		return detalles;
	}
}
