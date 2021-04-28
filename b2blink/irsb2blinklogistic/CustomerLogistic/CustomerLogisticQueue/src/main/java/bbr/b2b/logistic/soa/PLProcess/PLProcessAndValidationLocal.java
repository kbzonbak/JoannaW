package bbr.b2b.logistic.soa.PLProcess;

import java.util.List;

import javax.ejb.Local;

import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.customer.data.dto.PLFileDTO;

@Local
public interface PLProcessAndValidationLocal {
	
	public List<BaseResultDTO> doValidateHites(PLProveedor message);
	public PLFileDTO doCreateFileLaPolar(PLProveedor message, String folderpath, Long ticketNumber) throws LoadDataException, OperationFailedException;
}

