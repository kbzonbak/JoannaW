package bbr.b2b.logistic.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.Vector;

import org.apache.log4j.Logger;

public class SFTPClient {
	private Session session = null;
	private static Logger logger = Logger.getLogger(SFTPClient.class);

	public boolean connect(String user, String password, String server, int port) throws JSchException, IOException {
		JSch jsch = new JSch();
		Session session = null;
		try {
			//cuando se realice una implementación, solicitar puerto para llamar a servidor sftp(no usa proxy
			//se llama directamente a el, el maneja la url de destino
			session = jsch.getSession(user, server, port);
			logger.info("Configurando USER ->" + user + " PASSWORD -> " + password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			/*if(B2BSystemPropertiesUtil.getProperty("PROXY").equals("true")){
				String proxy = B2BSystemPropertiesUtil.getProperty("PROXY_HOST");
				int proxyPort = 2222;//Integer.valueOf(B2BSystemPropertiesUtil.getProperty("PROXY_PORT"));
				session.setProxy(new ProxySOCKS5(proxy, proxyPort));
				logger.info("Configurando proxy -> " + proxy + ":" + proxyPort);
			}*/
			session.connect();
			
 		} catch (JSchException e) {
 			e.printStackTrace();
			logger.error("Error a intentar conectar al Ftp " + e.getMessage());
			return false;
		} finally {
			session.disconnect();
		}
		return true;
	}
	
	public boolean getFiles(String originFolder, String destinationFolder) throws IOException{
		try {
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			Vector<ChannelSftp.LsEntry> list = sftpChannel.ls(originFolder + "*");
			for (ChannelSftp.LsEntry entry : list) {
				System.out.println(entry.getLongname());
				try (InputStream inputStream = sftpChannel.get(originFolder + entry.getFilename())){
					InputStreamToFile(inputStream, destinationFolder + entry.getFilename());
					sftpChannel.rm(originFolder + entry.getFilename());
				}
			}
			sftpChannel.exit();
		} catch (JSchException e) {
				e.printStackTrace();
			logger.error("Error a intentar conectar al Ftp " + e.getMessage());
			return false;
		} catch (SftpException e) {
			e.printStackTrace();
			logger.error("Error a intentar conectar al Ftp " + e.getMessage());
			return false;
		} finally {
			session.disconnect();
		}
		return true;
	}

	public void InputStreamToFile(InputStream initialStream, String fileDest) throws IOException {
		File targetFile = new File(fileDest);
		java.nio.file.Files.copy(initialStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	public void filePath(String source, String destination) throws JSchException, SftpException {
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftpChannel = (ChannelSftp) channel;
		sftpChannel.put(source, destination);
		sftpChannel.exit();
	}
	
	public boolean upload(String user, String password, String server, int port, String source, String destination) throws JSchException, SftpException {
		
		JSch jsch = new JSch();
		Session session = null;
		try {
			//cuando se realice una implementación, solicitar puerto para llamar a servidor sftp(no usa proxy
			//se llama directamente a el, el maneja la url de destino
			session = jsch.getSession(user, server, port);
			logger.info("Configurando USER ->" + user + " PASSWORD -> " + password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			/*if(B2BSystemPropertiesUtil.getProperty("PROXY").equals("true")){
				String proxy = B2BSystemPropertiesUtil.getProperty("PROXY_HOST");
				int proxyPort = 2222;//Integer.valueOf(B2BSystemPropertiesUtil.getProperty("PROXY_PORT"));
				session.setProxy(new ProxySOCKS5(proxy, proxyPort));
				logger.info("Configurando proxy -> " + proxy + ":" + proxyPort);
			}*/
			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.put(source, destination);
			sftpChannel.exit();
			
 		} catch (JSchException e) {
 			e.printStackTrace();
			logger.error("Error a intentar conectar al Ftp " + e.getMessage());
			return false;
		} finally {
			session.disconnect();
		}
		return true;
		
		
	}

	public void download(String source, String destination) throws JSchException, SftpException {
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftpChannel = (ChannelSftp) channel;
		sftpChannel.get(source, destination);
		sftpChannel.exit();
	}

	public void disconnect() {
		if (session != null) {
			session.disconnect();
		}
	}
}
