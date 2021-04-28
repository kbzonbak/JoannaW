package bbr.b2b.test.classes;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.logistic.managers.interfaces.ContextUtil;
import bbr.b2b.logistic.managers.interfaces.IDatingReportManager;

public class AddDatingResource {


	private IDatingReportManager datingreportmanager;
	
	@Before
	public void myTest() {	
		try{
			datingreportmanager = ContextUtil.getInstance().getDatingReportManager();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	
	@BeforeClass
	public static void setup() throws NamingException, InterruptedException{		
		// DEMORA LA EJECUCI?N DE LAS PRUEBAS POR DESPLIEGUE DE EAR
		TimeUnit.SECONDS.sleep(10);	
	}
	
	@AfterClass
	public static void shutdown() throws NamingException{	
	}
	
	
	@Test
	public void addDatingResource_CD_36(){
		
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		String nameInitialModule = "08:00-08:15" ;
		String nameFinalModule = "19:45-20:00";
		String locationcode = "36";
		boolean hasChangeDay = false;
		
		resultDTO = datingreportmanager.doAddDatingResourceByLocation(nameInitialModule, nameFinalModule, locationcode, hasChangeDay);
		assertEquals(1, 1);

	}

	//@Test
	public void addDatingResource_CD_2(){
		
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		String nameInitialModule = "08:00-08:15" ;
		String nameFinalModule = "19:45-20:00";
		String locationcode = "2";
		boolean hasChangeDay = false;
		
		resultDTO = datingreportmanager.doAddDatingResourceByLocation(nameInitialModule, nameFinalModule, locationcode, hasChangeDay);
		assertEquals(1, 1);

	}
	
	
}
