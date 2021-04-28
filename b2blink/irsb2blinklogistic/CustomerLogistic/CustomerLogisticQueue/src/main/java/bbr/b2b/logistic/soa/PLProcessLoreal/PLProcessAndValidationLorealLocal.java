package bbr.b2b.logistic.soa.PLProcessLoreal;

import java.util.List;

import javax.ejb.Local;

import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.logistic.customer.data.dto.PLFileDTO;

@Local
public interface PLProcessAndValidationLorealLocal {
	
	public List<BaseResultDTO> doValidateUnimarc(PLProveedor message);
	public PLFileDTO doCreateFileUnimarc(PLProveedor message, String folderpath, Long ticketNumber);
	
	public List<BaseResultDTO> doValidateFalabella(PLProveedor message);
	public PLFileDTO doCreateFileFalabella(PLProveedor message, String folderpath);
	
	public List<BaseResultDTO> doValidateTottus(PLProveedor message);
	public PLFileDTO doCreateFileTottus(PLProveedor message, String folderpath);
	
	public PLFileDTO doCreateFileRipley(PLProveedor message, String folderpath);
	
	public PLFileDTO doCreateFileWalmart(PLProveedor message, String folderpath);
}

