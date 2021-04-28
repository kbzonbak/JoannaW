package bbr.b2b.extractors.soa.msgb2btosoa;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;

@Local
public interface LocationsToXmlLocal {

	public String processMessage(String nombrePortal, String codProveedor, String formato, String codComprador) throws OperationFailedException, LoadDataException;

}