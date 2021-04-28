package webservice;
//package webservice;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.List;
//
//import bbr.esb.services.webservices.facade.ScoreCardDTO;
//import bbr.esb.services.webservices.facade.ScoreCardManagerServer;
//import bbr.esb.services.webservices.facade.ScoreCardManagerServerService;
//import bbr.esb.services.webservices.facade.ScoreCardTableDTO;
//
//public class ScoreCardWebServiceTest
//{
//	public static void main(String[] args)
//	{
//		try
//		{
//			String endpoint1 = "https://b2blink.bbr.cl/doLogonUser?wsdl";
//			String endpoint2 = "https://b2blink.bbr.cl/FileService?wsdl";
//			String endpoint3 = "http://10.200.3.12:8180/SOA_GENERAL_Web/ScoreCardManagerServer?wsdl";
//			ScoreCardManagerServer loginClient = new ScoreCardManagerServerService(new URL(endpoint3)).getScoreCardManagerServerPort();
//			//pvkey rut
//			//"Listado Orden de Compra"
//			ScoreCardDTO item = loginClient.getScoreCardOfCompany("90703000", "Listado Orden de Compra");
//			System.out.println(item.getSiteprogressarray().get(0).getSitename());
//			
////			List<ScoreCardTableDTO> item0 = loginClient.getScoreCardTableOfCompany("5465465465", "Listado Orden de Compra");
//		}
//		catch (MalformedURLException e)
//		{
//			e.printStackTrace();
//		}
//		catch (RuntimeException e)
//		{
//			e.printStackTrace();
//		}
//	}
//}
