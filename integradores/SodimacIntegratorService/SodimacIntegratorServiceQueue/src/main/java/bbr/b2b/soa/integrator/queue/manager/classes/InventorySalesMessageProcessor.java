package bbr.b2b.soa.integrator.queue.manager.classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.soa.integrator.config.classes.ServiceConfiguration;
import bbr.b2b.soa.integrator.facade.entities.FileData;
import bbr.b2b.soa.integrator.facade.entities.SellOut;
import bbr.b2b.soa.integrator.facade.entities.SellOutType;
import bbr.b2b.soa.integrator.facade.entities.SoaState;
import bbr.b2b.soa.integrator.facade.entities.SoaStateType;
import bbr.b2b.soa.integrator.facade.entities.Vendor;
import bbr.b2b.soa.integrator.facade.repository.classes.FileDataRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SellOutRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SellOutTypeRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateTypeRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.VendorRepository;

@Component
@Transactional
public class InventorySalesMessageProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InventorySalesMessageProcessor.class);	
	
	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Autowired
	private VendorRepository companyRepository;	
	
	@Autowired
	private SoaStateTypeRepository soaStateTypeRepository;
	
	@Autowired
	private SellOutTypeRepository sellOutTypeRepository;
	
	@Autowired
	private SellOutRepository sellOutRepository;
	
	@Autowired
	private SoaStateRepository soaStateRepository;
	
	@Autowired
	private FileDataRepository fileDataRepository;
	
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	
	public void processVtaMessage(String message, String vendorCode) {
				
		try {
			
			Optional<SoaStateType> toNotifyOp = soaStateTypeRepository.findByCode("POR_NOTIFICAR");
			SoaStateType toNotify = toNotifyOp.isPresent() ? toNotifyOp.get() : null; 
						
			Optional<Vendor> vendorOp = companyRepository.findByCode(vendorCode);					
			Vendor vendor = vendorOp.isPresent() ? vendorOp.get() : null;
			
			Optional<SellOutType> salesTypeOp = sellOutTypeRepository.findByCode("VTA");
			SellOutType salesType = salesTypeOp.isPresent() ? salesTypeOp.get() : null; 
			
			// ALMACENA EL MENSAJE EN FILEDATA
			FileData fileData = new FileData();
			fileData.setLoadDate(LocalDateTime.now());
			fileData.setType("eVTA");
			fileData.setReference("");
			fileData.setVendorCode(vendorCode);
			fileData.setData(""); // EBO:  NO SE ALMACENA POR SU TAMAÑO
			
			fileDataRepository.save(fileData);	
			
			if (toNotify != null && vendor != null && salesType != null) {
				
				// ALMACENA EL SELLOUT EN EL SISTEMA DE ARCHIVOS
				String fileNameVta = "VTA_" + serviceConfiguration.getBuyerCode() + "_" + vendorCode + "_" + dtf.format(LocalDateTime.now()) + ".csv";
				String filePath = serviceConfiguration.getFilePath() + fileNameVta;
				
				Path path = null;				
				try {
					path = Paths.get(filePath);
				    byte[] strToBytes = message.getBytes();

				    Files.write(path, strToBytes);
				}catch (IOException e) {
					e.printStackTrace();					
				}
				
				if (path != null && Files.exists(path)) {
					
					SellOut sellOut = new SellOut();
					sellOut.setWhen(LocalDateTime.now());
					sellOut.setPath(filePath);
					sellOut.setSellOutType(salesType);
					sellOut.setSoaStateType(toNotify);
					sellOut.setVendor(vendor);
					
					sellOutRepository.save(sellOut);
					
					// SE DEJA EN ESTADO POR NOTIFICAR
					SoaState state = new SoaState();
					state.setSellOut(sellOut);
					state.setSoaStateType(toNotify);
					state.setWhen(LocalDateTime.now());
					state.setComment("Por notificar");
					
					soaStateRepository.save(state);			
					
					LOGGER.info("Venta " + filePath + " cargada exitosamente");
					
				}					
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
			
	}	
	
	public void processInvMessage(String message, String vendorCode) {
		
		try {
			
			Optional<SoaStateType> toNotifyOp = soaStateTypeRepository.findByCode("POR_NOTIFICAR");
			SoaStateType toNotify = toNotifyOp.isPresent() ? toNotifyOp.get() : null; 
						
			Optional<Vendor> vendorOp = companyRepository.findByCode(vendorCode);					
			Vendor vendor = vendorOp.isPresent() ? vendorOp.get() : null;
			
			Optional<SellOutType> invTypeOp = sellOutTypeRepository.findByCode("INV");
			SellOutType invType = invTypeOp.isPresent() ? invTypeOp.get() : null; 
			
			// ALMACENA EL MENSAJE EN FILEDATA
			FileData fileData = new FileData();
			fileData.setLoadDate(LocalDateTime.now());
			fileData.setType("eVTA");
			fileData.setReference("");
			fileData.setVendorCode(vendorCode);
			fileData.setData(""); // EBO:  NO SE ALMACENA POR SU TAMAÑO
			
			fileDataRepository.save(fileData);	
			
			if (toNotify != null && vendor != null && invType != null) {
				
				// ALMACENA EL SELLOUT EN EL SISTEMA DE ARCHIVOS
				String fileNameVta = "INV_" + serviceConfiguration.getBuyerCode() + "_" + vendorCode + "_" + dtf.format(LocalDateTime.now()) + ".csv";
				String filePath = serviceConfiguration.getFilePath() + fileNameVta;
				
				Path path = null;				
				try {
					path = Paths.get(filePath);
				    byte[] strToBytes = message.getBytes();

				    Files.write(path, strToBytes);
				}catch (IOException e) {
					e.printStackTrace();					
				}
				
				if (path != null && Files.exists(path)) {
					
					SellOut sellOut = new SellOut();
					sellOut.setWhen(LocalDateTime.now());
					sellOut.setPath(filePath);
					sellOut.setSellOutType(invType);
					sellOut.setSoaStateType(toNotify);
					sellOut.setVendor(vendor);
					
					sellOutRepository.save(sellOut);
					
					// SE DEJA EN ESTADO POR NOTIFICAR
					SoaState state = new SoaState();
					state.setSellOut(sellOut);
					state.setSoaStateType(toNotify);
					state.setWhen(LocalDateTime.now());
					state.setComment("Por notificar");
					
					soaStateRepository.save(state);		
					
					LOGGER.info("Inventario " + filePath + " cargado exitosamente");
				}
									
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
