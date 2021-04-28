package bbr.b2b.soa.integrator.test.classes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import bbr.b2b.common.rest.utils.IAPIRESTClientConnection;
import bbr.b2b.soa.integrator.queue.config.classes.JMSMessageService;
import bbr.b2b.soa.integrator.queue.utils.JMSPropertyManager;
import bbr.b2b.soa.integrator.queue.utils.QueueDefinitions;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJms
@ActiveProfiles(profiles = "dev")
public class TestManager {

	@Rule
    public EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();
	
	@Autowired
    private JMSMessageService messageService;
	
	@Autowired
	private IAPIRESTClientConnection apiConnection;
	
	@Ignore
	@Test	
	public void testeOC_76163495() {
		
		String msg = "1|11368096||Publicada|SE|72346     |76163495|K|ELECTROLUX DE CHILE SA|ALBERTO LLONA N.777 MAIPU|8376421   |Comprador PAP|25-03-2021|100CH030FEF |CLP|0|0|22641|0|0|22641|4301.79|31-03-2021|10-04-2021|7807330500544|5754178        |PE LICUADORA ULTRABLEND BL1500|130020072|SOMELA              |0316030602     |TER|C/U   |22641|1|1|0|0|0|0";
		
		Map<String, Object> jmsProperties = Map.of("vendorCode", "76163495");
		
		JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
		
		messageService.send(QueueDefinitions.Q_EOC, msg, jmsPropManager);		
		
	}
	
	@Ignore
	@Test
	public void testeOC_99503870() {
		
		String msg = "4|11368037||Publicada|CD|74623     |99503870|6|CERAMICAS CORDILLERA SPA|LO BOZA N.120 A PUDAHUEL|23874200  |Central de Compras|25-03-2021|100CH030FEF |CLP|455220|0|7503670|455220|0|7958890|1512189.1|01-04-2021|08-04-2021|840692066850|2184699        |CER 45X45 LUCAR OCRE 2,08M2|ILUOCCO14KE|CORDILLERA          |0314010105     |TER|CAJA  |8951|88|44|63015|0|0|0\r\n" + 
					"7|11368037||Publicada|CD|74623     |99503870|6|CERAMICAS CORDILLERA SPA|LO BOZA N.120 A PUDAHUEL|23874200  |Central de Compras|25-03-2021|100CH030FEF |CLP|455220|0|7503670|455220|0|7958890|1512189.1|01-04-2021|08-04-2021|2000003099313|3099318        |CER 60X60 LEGNO MIEL 1,44M2|ILEMICO16JE|CORDILLERA          |0314010101     |TER|CAJA  |7085|72|36|40810|0|0|0\r\n" + 
					"1|11368037||Publicada|CD|74623     |99503870|6|CERAMICAS CORDILLERA SPA|LO BOZA N.120 A PUDAHUEL|23874200  |Central de Compras|25-03-2021|100CH030FEF |CLP|455220|0|7503670|455220|0|7958890|1512189.1|01-04-2021|08-04-2021|7807991304062|1304062        |CER 20X30 CARIBE GRIS 1,52M2|LCARH00130W|CORDILLERA          |0314010202     |TER|CAJA  |4850|112|56|20642|0|0|0\r\n" + 
					"9|11368037||Publicada|CD|74623     |99503870|6|CERAMICAS CORDILLERA SPA|LO BOZA N.120 A PUDAHUEL|23874200  |Central de Compras|25-03-2021|100CH030FEF |CLP|455220|0|7503670|455220|0|7958890|1512189.1|01-04-2021|08-04-2021|7898583706913|409137X        |CER 61X61 SWING GRIS 2,6M2|6280204269|CORDILLERA          |0314010102     |TER|CAJA  |12224.75|48|24|46943|0|0|0\r\n" + 
					"11|11368037||Publicada|CD|74623     |99503870|6|CERAMICAS CORDILLERA SPA|LO BOZA N.120 A PUDAHUEL|23874200  |Central de Compras|25-03-2021|100CH030FEF |CLP|455220|0|7503670|455220|0|7958890|1512189.1|01-04-2021|08-04-2021|7791020053345|4213483        |CER 33X33 CANELO 1,96M2|MCANES113CJ|SCOP                |0314010101     |TER|CAJA  |6686|120|60|30488|0|0|0\r\n" + 
					"10|11368037||Publicada|CD|74623     |99503870|6|CERAMICAS CORDILLERA SPA|LO BOZA N.120 A PUDAHUEL|23874200  |Central de Compras|25-03-2021|100CH030FEF |CLP|455220|0|7503670|455220|0|7958890|1512189.1|01-04-2021|08-04-2021|7791300082362|4170458        |CER 33X33 HUILAN VERDE 1,96M2|MHUILC113CS|CORDILLERA          |0314010205     |TER|CAJA  |6827|120|60|65539|0|0|0\r\n" + 
					"3|11368037||Publicada|CD|74623     |99503870|6|CERAMICAS CORDILLERA SPA|LO BOZA N.120 A PUDAHUEL|23874200  |Central de Compras|25-03-2021|100CH030FEF |CLP|455220|0|7503670|455220|0|7958890|1512189.1|01-04-2021|08-04-2021|840692065259|2184680        |CER 45X45 LATO CARAMELO 2,08M2|ILACACO14KM|CORDILLERA          |0314010101     |TER|CAJA  |7761|88|44|54637|0|0|0\r\n" + 
					"2|11368037||Publicada|CD|74623     |99503870|6|CERAMICAS CORDILLERA SPA|LO BOZA N.120 A PUDAHUEL|23874200  |Central de Compras|25-03-2021|100CH030FEF |CLP|455220|0|7503670|455220|0|7958890|1512189.1|01-04-2021|08-04-2021|7807991304321|1304321        |CER 33X33 CIMA BEIGE 1,53M2|LCIMH001N07|CORDILLERA          |0314010105     |TER|CAJA  |5219|138|69|27368|0|0|0\r\n" + 
					"5|11368037||Publicada|CD|74623     |99503870|6|CERAMICAS CORDILLERA SPA|LO BOZA N.120 A PUDAHUEL|23874200  |Central de Compras|25-03-2021|100CH030FEF |CLP|455220|0|7503670|455220|0|7958890|1512189.1|01-04-2021|08-04-2021|2000002843139|2843137        |CER 33X33 MARRAKESH 1,53M2|LMARH001N07|CORDILLERA          |0314010107     |TER|CAJA  |5078|138|69|26629|0|0|0\r\n" + 
					"6|11368037||Publicada|CD|74623     |99503870|6|CERAMICAS CORDILLERA SPA|LO BOZA N.120 A PUDAHUEL|23874200  |Central de Compras|25-03-2021|100CH030FEF |CLP|455220|0|7503670|455220|0|7958890|1512189.1|01-04-2021|08-04-2021|2000003099306|309930X        |CER 45X45 MAD MURCIA 2,08M2|IMAMUCO14KX|CORDILLERA          |0314010101     |TER|CAJA  |7531|88|44|53018|0|0|0\r\n" + 
					"8|11368037||Publicada|CD|74623     |99503870|6|CERAMICAS CORDILLERA SPA|LO BOZA N.120 A PUDAHUEL|23874200  |Central de Compras|25-03-2021|100CH030FEF |CLP|455220|0|7503670|455220|0|7958890|1512189.1|01-04-2021|08-04-2021|190014133280|3184889        |CER 45X45 MAD ADRIANA 2,08M2|IMAADCO14KE|CORDILLERA          |0314010101     |TER|CAJA  |7814|88|44|26130|0|0|0";
		
		Map<String, Object> jmsProperties = Map.of("vendorCode", "99503870");
		
		JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
		
		messageService.send(QueueDefinitions.Q_EOC, msg, jmsPropManager);		
		
	}
	
	@Ignore
	@Test	
	public void testeOD_76163495() {
		
		String msg = "1|11368096||11368096|Publicada|SE|72346     |76163495|K|214|Bod La Farfana|CAlle La Farfana N.400                  |25-03-2021|31-03-2021|10-04-2021|7807330500544|5754178        |PE LICUADORA ULTRABLEND BL1500|130020072|C/U   |1|1|214|Bod La Farfana|1|1|||";
		
		Map<String, Object> jmsProperties = Map.of("vendorCode", "76163495");
		
		JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
		
		messageService.send(QueueDefinitions.Q_EOD, msg, jmsPropManager);		
		
	}
	
	@Ignore
	@Test	
	public void testeOD_99503870() {
		
		String msg = "1|11368037||11368037|Publicada|CD|74623     |99503870|6|58|HC 58 Angol|Avda. Dilman Bullock # 1744             |25-03-2021|01-04-2021|08-04-2021|7807991304062|1304062        |CER 20X30 CARIBE GRIS 1,52M2|LCARH00130W|CAJA  |1|1|58|HC 58 Angol|112|2|||\r\n" + 
					"2|11368037||11368037|Publicada|CD|74623     |99503870|6|58|HC 58 Angol|Avda. Dilman Bullock # 1744             |25-03-2021|01-04-2021|08-04-2021|7807991304321|1304321        |CER 33X33 CIMA BEIGE 1,53M2|LCIMH001N07|CAJA  |1|1|58|HC 58 Angol|138|2|||\r\n" + 
					"3|11368037||11368037|Publicada|CD|74623     |99503870|6|58|HC 58 Angol|Avda. Dilman Bullock # 1744             |25-03-2021|01-04-2021|08-04-2021|840692065259|2184680        |CER 45X45 LATO CARAMELO 2,08M2|ILACACO14KM|CAJA  |1|1|58|HC 58 Angol|88|2|||\r\n" + 
					"4|11368037||11368037|Publicada|CD|74623     |99503870|6|58|HC 58 Angol|Avda. Dilman Bullock # 1744             |25-03-2021|01-04-2021|08-04-2021|840692066850|2184699        |CER 45X45 LUCAR OCRE 2,08M2|ILUOCCO14KE|CAJA  |1|1|58|HC 58 Angol|88|2|||\r\n" + 
					"5|11368037||11368037|Publicada|CD|74623     |99503870|6|58|HC 58 Angol|Avda. Dilman Bullock # 1744             |25-03-2021|01-04-2021|08-04-2021|2000002843139|2843137        |CER 33X33 MARRAKESH 1,53M2|LMARH001N07|CAJA  |1|1|58|HC 58 Angol|138|2|||\r\n" + 
					"6|11368037||11368037|Publicada|CD|74623     |99503870|6|58|HC 58 Angol|Avda. Dilman Bullock # 1744             |25-03-2021|01-04-2021|08-04-2021|2000003099306|309930X        |CER 45X45 MAD MURCIA 2,08M2|IMAMUCO14KX|CAJA  |1|1|58|HC 58 Angol|88|2|||\r\n" + 
					"7|11368037||11368037|Publicada|CD|74623     |99503870|6|58|HC 58 Angol|Avda. Dilman Bullock # 1744             |25-03-2021|01-04-2021|08-04-2021|2000003099313|3099318        |CER 60X60 LEGNO MIEL 1,44M2|ILEMICO16JE|CAJA  |1|1|58|HC 58 Angol|72|2|||\r\n" + 
					"8|11368037||11368037|Publicada|CD|74623     |99503870|6|58|HC 58 Angol|Avda. Dilman Bullock # 1744             |25-03-2021|01-04-2021|08-04-2021|190014133280|3184889        |CER 45X45 MAD ADRIANA 2,08M2|IMAADCO14KE|CAJA  |1|1|58|HC 58 Angol|88|2|||\r\n" + 
					"9|11368037||11368037|Publicada|CD|74623     |99503870|6|58|HC 58 Angol|Avda. Dilman Bullock # 1744             |25-03-2021|01-04-2021|08-04-2021|7898583706913|409137X        |CER 61X61 SWING GRIS 2,6M2|6280204269|CAJA  |1|1|58|HC 58 Angol|48|2|||\r\n" + 
					"10|11368037||11368037|Publicada|CD|74623     |99503870|6|58|HC 58 Angol|Avda. Dilman Bullock # 1744             |25-03-2021|01-04-2021|08-04-2021|7791300082362|4170458        |CER 33X33 HUILAN VERDE 1,96M2|MHUILC113CS|CAJA  |1|1|58|HC 58 Angol|120|2|||\r\n" + 
					"11|11368037||11368037|Publicada|CD|74623     |99503870|6|58|HC 58 Angol|Avda. Dilman Bullock # 1744             |25-03-2021|01-04-2021|08-04-2021|7791020053345|4213483        |CER 33X33 CANELO 1,96M2|MCANES113CJ|CAJA  |1|1|58|HC 58 Angol|120|2|||";
		
		Map<String, Object> jmsProperties = Map.of("vendorCode", "99503870");
		
		JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
		
		messageService.send(QueueDefinitions.Q_EOD, msg, jmsPropManager);		
		
	}
	
	@Ignore
	@Test
	public void testeOE_76163495() {
		
		String msg = "23228727551002|11368096|25/03/2021|31/03/2021||||NO APLICA|SAN JAVIER DE LONCOMILLA|LINARES                       |||POR DEFINIR|||Sodimac||7807330500544|5754178        |PE LICUADORA ULTRABLEND BL1500||1|22641|0|||||||31-mar-2021|VII REGION DEL MAULE||\r\n" + 
					 "23228727678001|11368111|25/03/2021|01/04/2021||||NO APLICA|CORONEL|CONCEPCION                    |||POR DEFINIR|||Sodimac||7807311747432|318000X        |FREEZ H M100 99L MADEMSA||1|104615|0|||||||01-apr-2021|VIII REGION DEL BIOBIO||\r\n" + 
					 "27228720131001|11368149|25/03/2021|30/03/2021||||NO APLICA|LINARES|LINARES                       |||POR DEFINIR|||Sodimac||7807311816220|5610184        |PE TWISTER 5300-BLUE-M||1|104615|0|||||||30-mar-2021|VII REGION DEL MAULE||\r\n" + 
					 "27228728257001|11368156|25/03/2021|30/03/2021||||NO APLICA|TEMUCO|TEMUCO                        |||POR DEFINIR|||Sodimac||7807311768321|3281043        |ESTUFA PARAF F 1120+ FENSA||1|73910|0|||||||30-mar-2021|IX REGION DE LA ARAUCANIA||";
		
		Map<String, Object> jmsProperties = Map.of("vendorCode", "76163495");
		
		JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
		
		messageService.send(QueueDefinitions.Q_EOE, msg, jmsPropManager);		
		
	}
	
	@Ignore
	@Test
	public void testeOC_96763560() {
		
		String msg = "1|11419157||Publicada|SE|9126 |96763560|K|IMPORTADORA MIDEA CARRIER CHILE LIMITA|VICUNA MACKENNA N.3318 HS|3778100 |Comprador PAP|04-04-2021|100CH030FEF |CLP|0|0|31506|0|0|31506|5986.14|08-04-2021|18-04-2021|6944271652992|6250068 |PE MICROONDAS ANALO MMP-20NCJ9|MMP-20NCJ9|MIDEA |0316030501 |TER|C/U |31506|1|1|0|0|0|0";
		
		Map<String, Object> jmsProperties = Map.of("vendorCode", "96763560");
		
		JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
		
		messageService.send(QueueDefinitions.Q_EOC, msg, jmsPropManager);		
		
	}
	
	@Ignore
	@Test
	public void testeOE_96763560() {
		
		String msg = "RESERVA|NRO_OC|FECHA_EMISION_OC|FECHA_DESPACHO_PACTADA|NOM_COMPRADOR|TELEFONO_COMPRADOR|NOM_RECEPTOR|DIRECCION_RECEPTOR|COMUNA_RECEPTOR|CIUDAD_RECEPTOR|COD_POSTAL_RECEPTOR|TELEFONO_RECEPTOR|ATENCION|NRO_LOCAL| LOCAL| DESPACHAR_A| OBSERVACION|UPC|SKU|DESCRIPCION|DESCRIPCION_EXTENDIDA|UNIDADES|PRECIO_COSTO|DESCUENTO|PAPEL_REGALO|SALUDO|HORARIO_PROGRAMADO|DOMICILIO/OFICINA|EMAIL|ENTRE_CALLE|FECHA_REPARTO_CLIENTE|REGION|IDENTIFICACION_CLIENTE|DNI_COMPRADOR\r\n" + 
				"23229044301001|11419157|04/04/2021|08/04/2021||||NO APLICA|LO ESPEJO|SANTIAGO                      |||POR DEFINIR|||Sodimac||6944271652992|6250068        |PE MICROONDAS ANALO MMP-20NCJ9||1|31506|0|||||||08-apr-2021|REGION METROPOLITANA DE SANTIA||";
		
		Map<String, Object> jmsProperties = Map.of("vendorCode", "96763560");
		
		JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
		
		messageService.send(QueueDefinitions.Q_EOE, msg, jmsPropManager);		
		
	}
	
	@Ignore
	@Test
	public void testeOC_76163495_2() {
		
		String msg = "N_ITEM|NRO_OC|FECHA_DESCARGA_OC|ESTADO_OC|TIPO_OC|COD_PROVEEDOR|RUT|DV_RUT|RAZON_SOCIAL|DIRECCION|FONO|COMPRADOR|FECHA_EMISION|FORMA_PAGO|MONEDA|CARGO_OC|DESCUENTO_OC|TOTAL_OC_BRUTO|TOTAL_CARGOS|TOTAL_DESCUENTOS|TOTAL_OC_NETO|TOTAL_IVA|FECHA_ESPER|FECHA_VENC|UPC|SKU|DESCRIPCION_LARGA|COD_EMPAQUE|MARCA|FAMILIA|DEPARTAMENTO|UM_EMPAQUE|PRECIO_UNI|CANTIDAD_PROD|CANTIDAD_EMPAQ|CARGO_ADIC|DESCUENTO_ADIC|TOT_CARGOS|TOT_DESCUEN\r\n" + 
				"1|11426119||Publicada|SE|72346 |76163495|K|ELECTROLUX DE CHILE SA|ALBERTO LLONA N.777 MAIPU|8376421 |Comprador PAP|06-04-2021|100CH030FEF |CLP|0|0|153438|0|0|153438|29153.22|16-04-2021|26-04-2021|7807311733282|3516628 |COCINA 4P 66L 775X MADEMSA|240073328|MADEMSA |0316010101 |TER|C/U |153438|1|1|0|0|0|0";
		
		Map<String, Object> jmsProperties = Map.of("vendorCode", "76163495");
		
		JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
		
		messageService.send(QueueDefinitions.Q_EOC, msg, jmsPropManager);		
		
	}	
	
	@Ignore
	@Test
	public void testeOD_76163495_2() {
		
		String msg = "N_ITEM |NRO_OD|FECHA_DESCARGA_OD|NRO_OC|ESTADO_OC|TIPO_OC|COD_PROVEEDOR|RUT|DV_RUT|COD_L_ENTREGA|DESC_L_ENTREGA|DIREC_L_ENTREGA|FECHA_EMISION|FECHA_ESPER|FECHA_VENC|UPC|SKU|DESCRIPCION_LARGA|COD_EMPAQUE|UM_EMPAQUE|NRO_CAPAS_PALET|EMPAQ_CAPA_PALET|NRO_TIENDA|TIENDA|CANTIDAD_PROD|CANTIDAD_EMPAQ|PASILLO|LINEAL|METRO\r\n" + 
				"1|11426119||11426119|Publicada|SE|72346 |76163495|K|214|Bod La Farfana|CAlle La Farfana N.400 |06-04-2021|16-04-2021|26-04-2021|7807311733282|3516628 |COCINA 4P 66L 775X MADEMSA|240073328|C/U |1|1|214|Bod La Farfana|1|1|||";
		
		Map<String, Object> jmsProperties = Map.of("vendorCode", "76163495");
		
		JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
		
		messageService.send(QueueDefinitions.Q_EOD, msg, jmsPropManager);		
		
	}	
	
	@Test
	public void testeOE_76163495_2() {
		
		String msg = "RESERVA|NRO_OC|FECHA_EMISION_OC|FECHA_DESPACHO_PACTADA|NOM_COMPRADOR|TELEFONO_COMPRADOR|NOM_RECEPTOR|DIRECCION_RECEPTOR|COMUNA_RECEPTOR|CIUDAD_RECEPTOR|COD_POSTAL_RECEPTOR|TELEFONO_RECEPTOR|ATENCION|NRO_LOCAL| LOCAL| DESPACHAR_A| OBSERVACION|UPC|SKU|DESCRIPCION|DESCRIPCION_EXTENDIDA|UNIDADES|PRECIO_COSTO|DESCUENTO|PAPEL_REGALO|SALUDO|HORARIO_PROGRAMADO|DOMICILIO/OFICINA|EMAIL|ENTRE_CALLE|FECHA_REPARTO_CLIENTE|REGION|IDENTIFICACION_CLIENTE|DNI_COMPRADOR\r\n" + 
				"23229075958001|11426119|06/04/2021|16/04/2021||||NO APLICA|COPIAPO|COPIAPO |||POR DEFINIR|||Sodimac||7807311733282|3516628 |COCINA 4P 66L 775X MADEMSA||1|153438|0|||||||16-apr-2021|III REGION DE ATACAMA||";
		
		Map<String, Object> jmsProperties = Map.of("vendorCode", "76163495");
		
		JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
		
		messageService.send(QueueDefinitions.Q_EOE, msg, jmsPropManager);		
		
	}	
	
	
	@Ignore
	@Test
	public void testeVTA_76163495() throws IOException {
		
		Path path = Paths.get("src/test/resources/vtas/vta_1_76163495.csv");
		String msg = Files.readString(path);		
		
		Map<String, Object> jmsProperties = Map.of("vendorCode", "76163495");
		
		JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
		
		messageService.send(QueueDefinitions.Q_VTA, msg, jmsPropManager);		
		
	}
	
	@Ignore
	@Test
	public void testeINV_76163495() throws IOException {
		
		Path path = Paths.get("src/test/resources/invs/inv_1_76163495.csv");
		String msg = Files.readString(path);		
		
		Map<String, Object> jmsProperties = Map.of("vendorCode", "76163495");
		
		JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
		
		messageService.send(QueueDefinitions.Q_INV, msg, jmsPropManager);		
		
	}
	
	@Ignore
	@Test
	public void testeREST() throws IOException {
		
		ResponseEntity<String> response = apiConnection.executeRequest("http://localhost:8780/ExternalRetailScheduleApi/schedule/sodimac/eoc/downloadorders/execute", HttpMethod.GET);

		if (HttpStatus.OK.equals(response.getStatusCode())) {
			// MOSTRAR EL RESULTADO
			if (response != null && response.getBody() != null) {
				
			}			
		}
	}
	
	@Ignore
	@Test
	public void testeRESTDownload() throws IOException {
		
		ResponseEntity<File> response = apiConnection.downloadRequest("http://localhost:8780/ExternalRetailScheduleApi/schedule/download");

		if (HttpStatus.OK.equals(response.getStatusCode())) {
			// MOSTRAR EL RESULTADO
			if (response != null && response.getBody() != null) {
				
				File file = response.getBody();
				
				Path path = Paths.get(file.toURI());
				String message = Files.readString(path);
				
				System.out.println(message);
			}			
		}		
	}	
}
