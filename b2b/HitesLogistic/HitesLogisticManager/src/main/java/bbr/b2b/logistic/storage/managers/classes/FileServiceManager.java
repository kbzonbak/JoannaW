package bbr.b2b.logistic.storage.managers.classes;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import bbr.b2b.logistic.storage.data.classes.S3FileDataDTO;
import bbr.b2b.logistic.storage.managers.interfaces.FileServiceManagerLocal;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;


@Stateless(name = "managers/FileServiceManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FileServiceManager implements FileServiceManagerLocal{
	
	@Resource
	private javax.ejb.SessionContext mySessionCtx;
	
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}

	//private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceManager.class);
	private static Logger LOGGER= Logger.getLogger("SOALog");
	
	String endpoint = B2BSystemPropertiesUtil.getProperty("S3_ENDPOINT");
	String accessKey = B2BSystemPropertiesUtil.getProperty("S3_ACCESS_KEY");
	String secretKey = B2BSystemPropertiesUtil.getProperty("S3_SECRET_KEY");
	String regionName = B2BSystemPropertiesUtil.getProperty("S3_REGION");

	private AmazonS3 s3client;

	private AmazonS3 getS3Client() {
		if (s3client == null) {
			s3client = initS3Client();
		}
		return s3client;
	}

	private AmazonS3 initS3Client() {
		
		LOGGER.info("Inicializando cliente S3 con región "+this.regionName+" y endpoint "+ this.endpoint);

		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);

		BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
		EndpointConfiguration endpointConfig = new EndpointConfiguration(this.endpoint, this.regionName);

		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(creds))
				.withEndpointConfiguration(endpointConfig).withClientConfiguration(clientConfig)
				.withPathStyleAccessEnabled(true).build();

		LOGGER.info("Inicialización finalizada");
		return s3client;
	}

	public void createBucket(String bucketName) {
		try {
			AmazonS3 s3client = getS3Client();
			// Consulta por el bucket, ya que si no existe lo crea
			if (!s3client.doesBucketExistV2(bucketName)) {
				s3client.createBucket(bucketName);
				LOGGER.info("bucket "+bucketName+" creado");
			}
		} catch (AmazonServiceException e) {
			LOGGER.error("Error", e);
			// The call was transmitted successfully, but S3 couldn't process it, so it
			// returned an error response.
		} catch (SdkClientException e) {
			LOGGER.error("Error", e);
			// Amazon S3 couldn't be contacted for a response, or the client couldn't parse
			// the response from Amazon S3.
		}
	}

	public void deleteBucket(String bucketName) {
		try {
			AmazonS3 s3client = getS3Client();
			// Consulta por el bucket, ya que si no existe lo crea
			if (s3client.doesBucketExistV2(bucketName)) {
				s3client.deleteBucket(bucketName);
				LOGGER.info("bucket "+bucketName+" borrado");
			}
		} catch (AmazonServiceException e) {
			LOGGER.error("Error", e);
			// The call was transmitted successfully, but S3 couldn't process it, so it
			// returned an error response.
		} catch (SdkClientException e) {
			LOGGER.error("Error", e);
			// Amazon S3 couldn't be contacted for a response, or the client couldn't parse
			// the response from Amazon S3.
		}
	}

	public boolean checkObjectInBucket(String bucketName, String key) {
		AmazonS3 s3client = getS3Client();
		return s3client.doesObjectExist(bucketName, key);
	}

	public void printObjectMetadata(String bucketName, String key) {
		AmazonS3 s3client = getS3Client();
		S3Object object = s3client.getObject(bucketName, key);
		URL url = s3client.getUrl(bucketName, key);
		ObjectMetadata metadata = object.getObjectMetadata();
		LOGGER.info("Datos del objeto Key          : "+ key);
		LOGGER.info("URL      : "+ url);
		LOGGER.info("Metadata : ContentDisposition : "+ metadata.getContentDisposition());
		LOGGER.info("Metadata : ExpirationTime     : "+ metadata.getExpirationTime());
		LOGGER.info("Metadata : HttpExpiresDate    : "+ metadata.getHttpExpiresDate());
		Owner owner = s3client.getS3AccountOwner();
		LOGGER.info("owner: "+ owner);

	}

	public URL getUrl(String bucketName, String key) {
		AmazonS3 s3client = getS3Client();
		URL url = s3client.getUrl(bucketName, key);
		return url;
	}

	public URL generateSecureUrlRequest(String bucketName, String key, int days) {
		AmazonS3 s3client = getS3Client();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		Date expiration = cal.getTime();
		GeneratePresignedUrlRequest requestpurl = new GeneratePresignedUrlRequest(bucketName, key);
		requestpurl.setExpiration(expiration);
		URL url = s3client.generatePresignedUrl(requestpurl);
		return url;
	}

	public URL generateSecureUrlRequest(String bucketName, String key) {
		return generateSecureUrlRequest(bucketName, key, 7);
	}

	public S3FileDataDTO createObject(String bucketName, String key, File file, String filename) throws Exception {
		return createObject(bucketName, key, file, filename, 7);
	}
	
	public S3FileDataDTO createObject(String bucketName, String key, File file, String filename, int pubUrlValidity) throws Exception {
		S3FileDataDTO result = new S3FileDataDTO();
		AmazonS3 s3client = getS3Client();
		// Consulta por el bucket, ya que si no existe lo crea
		if (!s3client.doesBucketExistV2(bucketName)) {
			s3client.createBucket(bucketName);
			LOGGER.info("bucket "+bucketName+" creado");
		}
		// Comprobar si existe el Objeto con la key dada
		boolean exists = s3client.doesObjectExist(bucketName, key);
		if (exists)
			throw new Exception("Objeto con key '{" + key + "}' ya existe");
		
		// Sube el archivo
		ObjectMetadata metadata = new ObjectMetadata();
		FileInputStream stream = new FileInputStream(file);
		metadata.setContentDisposition("attachment;filename=" + filename);
		metadata.setContentLength(file.length());
		PutObjectResult newobject = s3client.putObject(bucketName, key, stream, metadata);
		LOGGER.info("Objeto creado : "+ newobject);

		printObjectMetadata(bucketName, key);
		// s3client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
		URL url = s3client.getUrl(bucketName, key);
		LOGGER.info("URL interna creada : "+ url);
		result.setUrl(url);

		// Generar Link público para descarga por tiempo limitado
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, pubUrlValidity);
		Date expiration = cal.getTime();
		GeneratePresignedUrlRequest requestpurl = new GeneratePresignedUrlRequest(bucketName, key);
		requestpurl.setExpiration(expiration);
		URL link = s3client.generatePresignedUrl(requestpurl);

		result.setSignedUrl(link);
		LOGGER.info("URL pública creada : "+ link);

		return result;
	}

	public void deleteObject(String bucketName, String key) throws Exception {
		AmazonS3 s3client = getS3Client();
		// Consulta por el bucket, ya que si no existe lo crea
		if (!s3client.doesBucketExistV2(bucketName))
			throw new Exception("No existe el bucket '{" + bucketName + "}'");
		// Comprobar si existe el archivo con la key dada
		boolean exists = s3client.doesObjectExist(bucketName, key);
		if (!exists)
			throw new Exception("Objeto con key '{" + key + "}' no existe");
		s3client.deleteObject(bucketName, key);
		LOGGER.info("Objeto borrado exitosamente, bucket: "+bucketName+", key : "+ key);
	}

	public void listBuckets() {
		try {
			AmazonS3 s3client = getS3Client();
			List<Bucket> buckets = s3client.listBuckets();
			if (buckets == null || buckets.isEmpty()) {
				LOGGER.info("No hay buckets creados");
				return;
			}
			LOGGER.info("Los buckets son:");
			for (Bucket b : buckets) {
				String bucketName = b.getName();
				LOGGER.info(bucketName);
			}

		} catch (AmazonServiceException e) {
			LOGGER.error("Error", e);
		} catch (SdkClientException e) {
			LOGGER.error("Error", e);
		}
	}

	public List<String> getBucketNames() {
		List<String> result = null;
		try {
			AmazonS3 s3client = getS3Client();
			List<Bucket> buckets = s3client.listBuckets();
			result = buckets.stream().map(Bucket::getName).collect(Collectors.toList());
		} catch (AmazonServiceException e) {
			LOGGER.error("Error", e);
		} catch (SdkClientException e) {
			LOGGER.error("Error", e);
		}
		return result;
	}

	public void listObjectsInBucket(String bucketName) {
		try {
			AmazonS3 s3client = getS3Client();

			ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName);
			ListObjectsV2Result result;

			LOGGER.info("Lista de objetos en el Bucket :"+ bucketName );
			do {
				result = s3client.listObjectsV2(req);

				for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
					LOGGER.info("key: "+objectSummary.getKey()+", size: "+objectSummary.getSize());
				}
				// If there are more than maxKeys keys in the bucket, get a continuation token
				// and list the next objects.
				String token = result.getNextContinuationToken();
				// System.out.println("Next Continuation Token: " + token);
				req.setContinuationToken(token);
			} while (result.isTruncated());
		} catch (AmazonServiceException e) {
			LOGGER.error("Error", e);
		} catch (SdkClientException e) {
			LOGGER.error("Error", e);
		}
	}
	
	
	

}
