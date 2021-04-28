package bbr.b2b.logistic.storage.data.interfaces;

import java.io.File;
import java.net.URL;
import java.util.List;

import bbr.b2b.logistic.storage.data.classes.S3FileDataDTO;

public interface IFileServiceManager {

	public S3FileDataDTO createObject(String bucketName, String key, File file, String filename) throws Exception;
	public void createBucket(String bucketName);
	public void deleteBucket(String bucketName);
	public boolean checkObjectInBucket(String bucketName, String key);
	public void printObjectMetadata(String bucketName, String key);
	public URL getUrl(String bucketName, String key);
	public URL generateSecureUrlRequest(String bucketName, String key);
	public void deleteObject(String bucketName, String key) throws Exception;
	public void listBuckets();
	public List<String> getBucketNames();
	public void listObjectsInBucket(String bucketName);

}