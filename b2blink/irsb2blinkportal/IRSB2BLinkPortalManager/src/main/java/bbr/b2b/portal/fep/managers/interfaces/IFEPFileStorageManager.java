package bbr.b2b.portal.fep.managers.interfaces;

import java.util.List;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemAttributeValueDTO;
import bbr.b2b.fep.mp.data.classes.MPItemAttributeValueDTO;
import bbr.b2b.fep.storage.classes.FileObjectArrayResultDTO;
import bbr.b2b.fep.storage.classes.FileObjectInitParamDTO;
import bbr.b2b.fep.storage.classes.FileObjectResultDTO;
import bbr.b2b.fep.storage.classes.FileObjectsInitParamDTO;
import bbr.b2b.fep.storage.classes.FileStorageInitParamDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;

public interface IFEPFileStorageManager extends IGenericEJBInterface {
	
	public FileObjectResultDTO addOrUpdateFile(FileObjectInitParamDTO initparam, Long uskey);
	
	public FileObjectArrayResultDTO addOrUpdateFiles(FileObjectsInitParamDTO initparam, Long uskey);
	
	public FileObjectArrayResultDTO getFileObjects(FileStorageInitParamDTO initparam);

	public BaseResultDTO removeFileObjects(FileObjectsInitParamDTO initparam);
	
	public BaseResultDTO downloadMPFileImagesObjects (List<MPItemAttributeValueDTO> attributesImages, String tempdir);
	
	public BaseResultDTO downloadCPFileImagesObjects (List<CPItemAttributeValueDTO> attributesImages, String tempdir);

}
