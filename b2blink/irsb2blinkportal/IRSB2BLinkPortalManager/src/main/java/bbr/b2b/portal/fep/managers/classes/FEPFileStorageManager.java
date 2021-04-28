package bbr.b2b.portal.fep.managers.classes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.NamingException;

import org.apache.commons.io.FileUtils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.util.LoggingUtil;
import bbr.b2b.fep.cp.data.classes.CPItemAttributeValueDTO;
import bbr.b2b.fep.managers.interfaces.ContextUtil;
import bbr.b2b.fep.managers.interfaces.IFileStorageManager;
import bbr.b2b.fep.mp.data.classes.MPItemAttributeValueDTO;
import bbr.b2b.fep.storage.classes.FileObjectArrayResultDTO;
import bbr.b2b.fep.storage.classes.FileObjectDataDTO;
import bbr.b2b.fep.storage.classes.FileObjectInitParamDTO;
import bbr.b2b.fep.storage.classes.FileObjectResultDTO;
import bbr.b2b.fep.storage.classes.FileObjectsInitParamDTO;
import bbr.b2b.fep.storage.classes.FileStorageInitParamDTO;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;
import bbr.b2b.portal.utils.FEPUtils;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;

@Stateless(name = "managers/FEPFileStorageManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FEPFileStorageManager implements FEPFileStorageManagerLocal {
	private IFileStorageManager storageManager = null;

	private static LoggingUtil logger = new LoggingUtil(FEPFileStorageManager.class);

	AWSCredentials credentials = null;
	AmazonS3 S3Connector = null;

	Calendar calendar = null;
	Date date = null;

	@PostConstruct
	public void getRemote() {

		try {
			storageManager = ContextUtil.getInstance().getFileStorageManager();
		} catch (NamingException e) {
			e.printStackTrace();

		}
	}

	private void initS3Configuration(){

		String	S3_ACCESS_KEY	= new String(B2BSystemPropertiesUtil.getProperty("S3_ACCESS_KEY"));
		String	S3_SECRET_KEY	= new String(B2BSystemPropertiesUtil.getProperty("S3_SECRET_KEY"));
		String	S3_ENDPOINT	= new String(B2BSystemPropertiesUtil.getProperty("S3_ENDPOINT"));

		AWSCredentials credentials = new BasicAWSCredentials(S3_ACCESS_KEY, S3_SECRET_KEY);

		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);

		S3Connector = new AmazonS3Client (credentials,clientConfig);
		S3Connector.setEndpoint(S3_ENDPOINT);

	}

	private boolean deleteS3Object(String bucketName,String keyName ){
		boolean result = true;
		try {
			initS3Configuration();

			//initCEPHConfiguration();

			S3Connector.deleteObject(new DeleteObjectRequest(bucketName, keyName));
		}	       
		catch( Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public FileObjectArrayResultDTO addOrUpdateFiles(FileObjectsInitParamDTO initparam, Long uskey)
	{
		FileObjectArrayResultDTO resultDTO = new FileObjectArrayResultDTO();
		ArrayList<FileObjectDataDTO> filesdataOK = new ArrayList<>();
		try {
			//Adicionar o editar	
			FileObjectInitParamDTO initparamOne = new FileObjectInitParamDTO();
			for(FileObjectDataDTO fileObject : initparam.getFileObjects()){
				initparamOne.setFileObject(fileObject);
				FileObjectResultDTO tmpres = this.addOrUpdateFile(initparamOne, uskey);
				if(tmpres != null && tmpres.getStatuscode().equals("0")){
					fileObject.setUri(tmpres.getValue().getUri());
					filesdataOK.add(fileObject);
				}				
			}

			if(filesdataOK.size() >0 ){
				FileObjectDataDTO[] archivesDataOK = new FileObjectDataDTO[filesdataOK.size()];
				archivesDataOK = filesdataOK.toArray(archivesDataOK);
				resultDTO.setValues(archivesDataOK);
			}
			//Todos OK
			if( !(filesdataOK.size() == initparam.getFileObjects().length) ){
				PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P11001");// algunos ficheros no se pudieron subir	
			}
			else if (filesdataOK.size() == 0){
				PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp
				logger.error("No se pudo realizar la subida de ningún fichero");
			}


		} catch (Exception e) {
			e.printStackTrace();
			PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}

		return resultDTO;

	}


	@SuppressWarnings("deprecation")
	@Override
	public FileObjectResultDTO addOrUpdateFile(FileObjectInitParamDTO initparam, Long uskey)
	{
		FileObjectResultDTO resultDTO = new FileObjectResultDTO();
		try {
			
			//Bucketname raiz
			String rootBucketName = "bbrmdm";
			try {
				rootBucketName = new String(B2BSystemPropertiesUtil.getProperty("bbrmdm_bucket"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//Adicionar o editar			
			String bucketName = this.getBucketName(initparam.getFileObject().getOwnercode());

			initparam.getFileObject().setOwnercode(bucketName);

			//Buscar el fichero temporal que se subió
			String attachDir = PortalConstants.getInstance().getFileUploadPath() + uskey;
			File file = new File(attachDir, initparam.getFileObject().getName());
			if(file != null && file.exists()){

				initparam.getFileObject().setCheckfileerror(false);
				FileObjectResultDTO restmp = storageManager.addOrUpdateFileObject(initparam);
				if(restmp == null || !restmp.getStatuscode().equals("0")){
					if(restmp != null){
						return restmp;	
					}
					else{
						PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
						return resultDTO;
					}

				}
				FileObjectDataDTO fileobject = restmp.getValue();


				try
				{
					initS3Configuration();

					if(S3Connector != null){
						java.net.URL uri = null;
						if (!S3Connector.doesBucketExist(rootBucketName)){
							S3Connector.createBucket(rootBucketName);
						}
						
						String keyName = bucketName + "/" + fileobject.getName();

						S3Connector.putObject(rootBucketName, keyName, file);

						S3Connector.setObjectAcl(rootBucketName, keyName, CannedAccessControlList.PublicRead);

						uri = S3Connector.getUrl(rootBucketName, keyName);
						fileobject.setUri(uri.toString());
					}
				}

				catch (Exception e)
				{
					e.printStackTrace();
					return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P11000");// No se pudo subir el fichero a la nube				
				}
				//Volver a editarlo para colocar la uri
				initparam.setFileObject(fileobject);
				resultDTO = storageManager.addOrUpdateFileObject(initparam);
			}
			else{
				if (initparam.getFileObject().getCheckfileerror()!= null && initparam.getFileObject().getCheckfileerror()){
					return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P11000");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}

		return resultDTO;

	}

	@Override
	public FileObjectArrayResultDTO getFileObjects(FileStorageInitParamDTO initparam)
	{
		FileObjectArrayResultDTO resultDTO = new FileObjectArrayResultDTO();
		try {
			String bucketName = this.getBucketName(initparam.getOwnercode());

			initparam.setOwnercode(bucketName);

			return storageManager.getFileObjects(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}

	@Override
	public BaseResultDTO removeFileObjects(FileObjectsInitParamDTO initparam)
	{
		FileObjectArrayResultDTO resultDTO = new FileObjectArrayResultDTO();
		ArrayList<FileObjectDataDTO> removedOKList = new ArrayList<>();
		try {
			for(FileObjectDataDTO archive : initparam.getFileObjects()){
				try
				{
					boolean deleted = this.deleteS3Object(archive.getOwnercode(), archive.getName());
					if(deleted){
						removedOKList.add(archive);
					}
				}
				catch (Exception e)
				{
					;
				}				
			}

			FileObjectDataDTO[] archivesToDelete = new FileObjectDataDTO[removedOKList.size()];
			archivesToDelete = removedOKList.toArray(archivesToDelete);
			initparam.setFileObjects(archivesToDelete);
			BaseResultDTO remRes = storageManager.removeFileObjects(initparam);

			if(remRes != null && remRes.getStatuscode().equals("0")){
				if(removedOKList.size() == initparam.getFileObjects().length){
					return remRes;
				}
				else{
					return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P11000");// No se pudo subir el fichero a la nube
				}

			}
			else{
				return remRes;
			}
		} catch (Exception e) {
			e.printStackTrace();
			PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}

		return resultDTO;
	}

	public BaseResultDTO downloadMPFileImagesObjects (List<MPItemAttributeValueDTO> attributesImages, String tempdir)
	{
		FileObjectArrayResultDTO resultDTO = new FileObjectArrayResultDTO();

		try {
			if (attributesImages != null && attributesImages.size() >0)
			{
				for (MPItemAttributeValueDTO dto: attributesImages){

					String file = tempdir + "/" + FEPUtils.getFilenameByUri(dto.getMetadata());

					FileUtils.copyURLToFile(new URL(dto.getMetadata()), new File(file));
				}
			} else if (attributesImages == null){

				String imageuri = new String(B2BSystemPropertiesUtil.getProperty("DEFAULT_IMAGE"));

				String file = tempdir + "/" + FEPUtils.getFilenameByUri(imageuri);

				FileUtils.copyURLToFile(new URL(imageuri), new File(file));
			}

		} catch (IOException e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
		return resultDTO;

	}

	private String getBucketName(String ownerCode)
	{
		String	OWNERCODE	= ownerCode.toLowerCase();
		String  PROJECTNAME = "";
		String	ENVIRONMENT = "";
		
		String result = "";
		boolean byproject = false;
		
		if (OWNERCODE.equalsIgnoreCase(PortalConstants.INTERNAL_FILES)){
			PROJECTNAME = new String(B2BSystemPropertiesUtil.getProperty("project_name")).toLowerCase();
			byproject = true;
		}

		ENVIRONMENT	= new String(B2BSystemPropertiesUtil.getProperty("environment")).toLowerCase();

		result = byproject ?  PROJECTNAME + "-" + ENVIRONMENT + "-" + OWNERCODE : ENVIRONMENT + "-" + OWNERCODE;

		return result;
	}

	@Override
	public BaseResultDTO downloadCPFileImagesObjects(List<CPItemAttributeValueDTO> attributesImages, String tempdir) {
		FileObjectArrayResultDTO resultDTO = new FileObjectArrayResultDTO();
		
		String imageuri = "";
		String file = "";

		try {
			if (attributesImages != null && attributesImages.size() >0)
			{
				for (CPItemAttributeValueDTO dto: attributesImages){

					if (dto.getMetadata() != null && !dto.getMetadata().isEmpty()){
						
						file = tempdir + "/" + FEPUtils.getFilenameByUri(dto.getMetadata());
						
						FileUtils.copyURLToFile(new URL(dto.getMetadata()), new File(file));
						
					}else{
						imageuri = new String(B2BSystemPropertiesUtil.getProperty("DEFAULT_IMAGE"));
						
						file = tempdir + "/" + FEPUtils.getFilenameByUri(imageuri);
						
						FileUtils.copyURLToFile(new URL(imageuri), new File(file));
						
						dto.setMetadata(imageuri);
						
					}
				}
				
			} else if (attributesImages == null){

				imageuri = new String(B2BSystemPropertiesUtil.getProperty("DEFAULT_IMAGE"));

				file = tempdir + "/" + FEPUtils.getFilenameByUri(imageuri);

				FileUtils.copyURLToFile(new URL(imageuri), new File(file));
			}

		} catch (IOException e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
		return resultDTO;
	}

}
