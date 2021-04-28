package bbr.b2b.test.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.common.dataset.util.CSVConverter;
import bbr.common.dataset.util.DataColumnStyleInfo;
import bbr.common.dataset.util.DataTable;
import bbr.common.dataset.util.XLSConverterPOI;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class FileHandlerUtils
{

	public enum FilesFormats
	{
		XLS("xls", ".xls"), XLSX("xlsx", ".xlsx"), ZIP("zip", ".zip"), PDF("pdf", ".pdf"), XML("xml", ".xml"), TXT("txt", ".txt"), CSV("csv", ".csv");

		private String	value;
		private String	extension;

		private FilesFormats(String value, String extension)
		{
			this.value = value;
			this.extension = extension;
		}

		public String getValue()
		{
			return value;
		}

		public String getExtension()
		{
			return extension;
		}
	}
	
	private static FileHandlerUtils _instance;

	// Constructor
	public static synchronized FileHandlerUtils getInstance()
	{
		if (_instance == null)
		{
			_instance = new FileHandlerUtils();
		}
		return _instance;
	}

	protected String filePath = null;

	private FileHandlerUtils()
	{
		super();
		filePath = "D:\\Projects\\ParisCL\\";
	}

	public OutputStream getFileWriter(String filename) throws IOException
	{
		File iFile = new File(filePath, filename);

		if (iFile.exists())
		{
			deleteFile(filename);
		}

		OutputStream outputStream = new FileOutputStream(iFile);
		return outputStream;
	}

	public BufferedReader getFileReader(String filename) throws IOException
	{
		File iFile = new File(filePath, filename);

		if (!iFile.exists())
		{
			throw new IOException("El archivo no existe");
		}

		BufferedReader sourceDataReader = new BufferedReader(new InputStreamReader(new FileInputStream(iFile)));
		return sourceDataReader;
	}

	public void deleteFile(String filename)
	{
		File iFile = new File(filePath, filename);

		if (!iFile.isFile())
		{
			return;
		}
		iFile.delete();
	}

	public void renameFile(String path, String surceFileName, String destFileName) throws FileNotFoundException, IOException
	{
		FileInputStream in = null;
		File inFile = new File(path, surceFileName);
		FileOutputStream out = null;

		byte[] buf = new byte[2048];
		int i = 0;

		out = new FileOutputStream(new File(path, destFileName));
		in = new FileInputStream(inFile);

		while ((i = in.read(buf)) != -1)
		{
			out.write(buf, 0, i);
		}

		out.close();
		in.close();

		inFile.delete();
	}

	public boolean deleteFiles(List<String> filesToDelete, String path) throws SecurityException
	{
		boolean allDelete = true;

		Iterator<String> auxIt = filesToDelete.iterator();

		while (auxIt.hasNext())
		{
			File fileToDelete = new File(path + File.separator + (String) auxIt.next());

			boolean fileDelete = fileToDelete.delete();

			allDelete = allDelete ? fileDelete : allDelete;

			fileToDelete = null;
		}

		return allDelete;
	}

	private String compressFilesZIP(List<String> filesToCompress, String sourcePath, String destFileName) throws IOException
	{
		// Create a buffer for reading the files
		byte[] buf = new byte[1024];

		// String newDestFileName= destFileName.concat(".zip");
		String newDestFileName = destFileName;
		try(ZipOutputStream out = new ZipOutputStream(new FileOutputStream(new File(sourcePath, newDestFileName)))) {
			Iterator<String> filesToCompressIt = filesToCompress.iterator();

			// Compress the files
			while (filesToCompressIt.hasNext())
			{
				String filesToCompressName = (String) filesToCompressIt.next();

				try(FileInputStream in = new FileInputStream(new File(sourcePath, filesToCompressName))) {
					// Add ZIP entry to output stream.
					out.putNextEntry(new ZipEntry(filesToCompressName));

					// Transfer bytes from the file to the ZIP file
					int len = 0;

					while ((len = in.read(buf)) > 0)
					{
						out.write(buf, 0, len);
					}

					// Complete the entry
					out.closeEntry();
				}
			}
		}


		return newDestFileName;
	}

	private String compressFilesZIPLOG(List<String> filesToCompress, String sourcePath, String destPath, String destFileName) throws IOException
	{
		// Create a buffer for reading the files
		byte[] buf = new byte[1024];

		// String newDestFileName= destFileName.concat(".zip");
		String newDestFileName = destFileName;
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(new File(destPath, newDestFileName)));

		Iterator<String> filesToCompressIt = filesToCompress.iterator();

		// Compress the files
		while (filesToCompressIt.hasNext())
		{
			String filesToCompressName = (String) filesToCompressIt.next();

			FileInputStream in = new FileInputStream(new File(sourcePath, filesToCompressName));

			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(filesToCompressName));

			// Transfer bytes from the file to the ZIP file
			int len = 0;

			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}

			// Complete the entry
			out.closeEntry();

			in.close();
		}

		// Complete the ZIP file
		out.close();

		return newDestFileName;
	}

	public FileDownloadInfoResultDTO downlaodExcelFileWithSheets(List<DataTable> dataTableList, String downloadname, String realname, Long uskey)
	{

		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy-HHmmss");
		LocalDateTime date = LocalDateTime.now();
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		// 1 - Obtener el nombre del archivo
		String downloadfilename = downloadname + "_" + dateFormat.format(date) + ".xls";
		String realfilename = realname + "_" + dateFormat.format(date) + "_" + uskey + ".xls";

		XLSConverterPOI converter = new XLSConverterPOI();
		converter.setExcelheaderbold(true);
		converter.setShowexceltableborder(true);

		resultDTO.setDownloadfilename(downloadfilename);
		resultDTO.setRealfilename(realfilename);

		try
		{
			converter.ExportToXLS(dataTableList, "D:\\Projects\\ParisCL\\" + realfilename, Charset.forName("UTF-16"));
			// comprimiendo el archivo a transferir...
			/** ************************************* */
			CompressToZipFileInitParamDTO compressToZipFileInitParamDTO = new CompressToZipFileInitParamDTO();
			compressToZipFileInitParamDTO.setSourceFileName(resultDTO.getRealfilename());
			compressToZipFileInitParamDTO.setDownloadFileName(resultDTO.getDownloadfilename());

			CompressToZipFileResultDTO compressToZipFileResultDTO = this.compressToZipFile(compressToZipFileInitParamDTO);

			resultDTO.setRealfilename("D:\\Projects\\ParisCL\\" + compressToZipFileResultDTO.getRealFileName());
			resultDTO.setDownloadfilename(compressToZipFileResultDTO.getDownloadFileName());
			/** ************************************* */
		}
		catch (IOException e)
		{
			e.printStackTrace();			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return resultDTO;
	}

	public FileDownloadInfoResultDTO downloadPredeliveryFile(DataTable dt0, String downloadname, String realname, Long uskey, String... headers)
	{
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH.mm.ss");
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		LocalDateTime date = LocalDateTime.now();
		Writer buffwriter = null;
		String realfilename = realname + dateFormat.format(date) + "_" + uskey + ".csv";
		String savePathFileName = "D:\\Projects\\ParisCL\\" + realfilename;
		String downloadfilename = downloadname + "_" + dateFormat.format(date) + ".csv";
		FileOutputStream stream = null;
		try
		{
			File file = new File(savePathFileName);
			stream = new FileOutputStream(file);
			buffwriter = new BufferedWriter(new OutputStreamWriter(stream));
			buffwriter.append(headers[0] != null ? headers[0] : "");
			buffwriter.append(headers[1] != null ? headers[1] : "");
			buffwriter.append(headers[2] != null ? headers[2] : "");
			buffwriter.append("\n");
			buffwriter.flush();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		CSVConverter converter = new CSVConverter();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		NumberFormat decimalformatter = new DecimalFormat("#0", symbols);
		converter.setDecimalFormatter(decimalformatter);

		try
		{
			converter.ExportToCSV(dt0, stream, Charset.forName("UTF-16LE"));
			stream.close();
			buffwriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		resultDTO.setDownloadfilename(downloadfilename);
		resultDTO.setRealfilename(realfilename);
		return resultDTO;
	}

	public FileDownloadInfoResultDTO downloadFile(DataTable dt0, String fileformat, String downloadname, String realname, Long uskey, DataColumnStyleInfo styleinfo){
		
		//DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH.mm.ss");
		
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		LocalDateTime date = LocalDateTime.now();
		String downloadfilename = null;
		String realfilename = null;
		
		
		if (fileformat.equalsIgnoreCase(FilesFormats.XLS.getValue()))
		{
			 downloadfilename = downloadname + "_" + dateFormat.format(date) + FilesFormats.XLS.getExtension();
			 realfilename = realname + "_" + dateFormat.format(date) + "_" + uskey +  FilesFormats.XLS.getExtension();

			XLSConverterPOI converter = new XLSConverterPOI();
			System.out.println("Converter XLS-> "+converter);
			converter.setExcelheaderbold(true);
			converter.setShowexceltableborder(true);

			try
			{
				if(styleinfo != null){
					converter.ExportToXLS(dt0, styleinfo, "D:\\Projects\\ParisCL\\" + realfilename, Charset.forName("UTF-16"));
				}else{
					converter.ExportToXLS(dt0, "D:\\Projects\\ParisCL\\" + realfilename, Charset.forName("UTF-16"));	
				}
				
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

		}
		
				
		if (fileformat.equalsIgnoreCase(FilesFormats.XLSX.getValue()))
		{
			 downloadfilename = downloadname + "_" + dateFormat.format(date) + FilesFormats.XLSX.getExtension();
			 realfilename = realname + "_" + dateFormat.format(date) + "_" + uskey +  FilesFormats.XLSX.getExtension();

			XLSConverterPOI converter = new XLSConverterPOI();
			System.out.println("Converter XLSX-> "+converter);
			converter.setExcelheaderbold(true);
			converter.setShowexceltableborder(true);

			try
			{

				if(styleinfo != null){
					System.out.println("styleinfo TRUE");
					System.out.println("Converter XLSX dt0-> "+dt0);
					System.out.println("Converter XLSX PortalConstants.getInstance().getFileTransferPath() + realfilename-> "+ "D:\\Projects\\ParisCL\\" + realfilename);
					System.out.println("Converter XLSX Charset.forName(UTF-16)-> "+Charset.forName("UTF-16"));
					converter.ExportToXLSX(dt0, styleinfo, "D:\\Projects\\ParisCL\\" + realfilename, Charset.forName("UTF-16"));
					System.out.println("Before converter.ExportToXLSX");
				}else{

					System.out.println("Converter XLSX dt0-> "+dt0);
					System.out.println("Converter XLSX PortalConstants.getInstance().getFileTransferPath() + realfilename-> "+ "D:\\Projects\\ParisCL\\" + realfilename);
					System.out.println("Converter XLSX Charset.forName(UTF-16)-> "+Charset.forName("UTF-16"));
					converter.ExportToXLSX(dt0, "D:\\Projects\\ParisCL\\" + realfilename, Charset.forName("UTF-16"));
					System.out.println("Before converter.ExportToXLSX");
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}

		if (fileformat.equalsIgnoreCase(FilesFormats.CSV.getValue()))
		{
			 downloadfilename = downloadname + "_" + dateFormat.format(date) + FilesFormats.CSV.getExtension();
			 realfilename = realname + dateFormat.format(date) + "_" + uskey + FilesFormats.CSV.getExtension();

			CSVConverter converter = new CSVConverter();

			try
			{
				if(styleinfo != null){
					converter.ExportToCSV(dt0,styleinfo, "D:\\Projects\\ParisCL\\" + realfilename, Charset.forName("UTF-16LE"));
				}else{
					System.out.println("Converter CSV dt0-> "+dt0);
					System.out.println("Converter CSV PortalConstants.getInstance().getFileTransferPath() + realfilename-> " + "D:\\Projects\\ParisCL\\" + realfilename);
					System.out.println("Converter CSV CHARSET_CSV-> "+Charset.forName("UTF-16LE"));
					converter.ExportToCSV(dt0, "D:\\Projects\\ParisCL\\" + realfilename, Charset.forName("UTF-16LE"));	
				}
				
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

		}
		resultDTO.setRealfilename(realfilename);
		resultDTO.setDownloadfilename(downloadfilename);
		System.out.println("resultDTO realfilename"+realfilename);
		System.out.println("resultDTO downloadfilename"+downloadfilename);
		return resultDTO;
		
	}

	public FileDownloadInfoResultDTO downloadFile(DataTable dt0, String fileformat, String downloadname, String realname, Long uskey)
	{
			return downloadFile(dt0, fileformat, downloadname, realname, uskey, null);
		
	}

	public FileDownloadInfoResultDTO downloadPdfFile(HashMap<String, Object> parameters, List<?> list, String jrxml, String downloadName, String realName, Long userKey) throws JRException, IOException {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm.ss");
		LocalDateTime date = LocalDateTime.now();

		String pathToPdfReportFile = jrxml;	
		//	En este path deben existir los jrxml pathToPdfReportFile
		try (InputStream is = new FileInputStream(pathToPdfReportFile)) {
			// Se obtiene el diseño del reporte a partir del .jrxml
			JasperDesign design = JRXmlLoader.load(is);

			// Se compila el diseño, generando un .jasper
			JasperReport report = JasperCompileManager.compileReport(design);

			// Se pasa el reporte (.jasper), parámetros y campos para generar el reporte
			JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(list));

			// Se exporta a PDF en una ruta determinada
			String downloadFilename = downloadName + " " + dateFormat.format(date) + " hrs.pdf";
			String realFilename = realName + " " + dateFormat.format(date) + " hrs_" + userKey + ".pdf";
			String pathToSaveFile = "D:\\Projects\\ParisCL\\" + realFilename;
			JasperExportManager.exportReportToPdfFile(print, pathToSaveFile);
			
			resultDTO.setDownloadfilename(downloadFilename);
			resultDTO.setRealfilename(realFilename);
		}
		
		return resultDTO;
	}

	public synchronized CompressToZipFileResultDTO compressToZipFile(CompressToZipFileInitParamDTO initPamamDTO) throws Exception
	{
		String sourceExtension = initPamamDTO.getDownloadFileName().substring(initPamamDTO.getDownloadFileName().lastIndexOf("."),
		initPamamDTO.getDownloadFileName().length());
		String destinyExtension = ".zip";

		String newSourceFileName = initPamamDTO.getDownloadFileName();
		String newDownloadFileName = initPamamDTO.getDownloadFileName().replaceAll(sourceExtension, destinyExtension);

		this.renameFile("D:\\Projects\\ParisCL\\", initPamamDTO.getSourceFileName(), newSourceFileName);

		List<String> lista = new ArrayList<String>();
		lista.add(new String(newSourceFileName));

		this.compressFilesZIP(lista, "D:\\Projects\\ParisCL\\",
		initPamamDTO.getSourceFileName().replaceAll(sourceExtension, ".zip"));

		// Borramos archivos intermedios
		this.deleteFiles(lista, "D:\\Projects\\ParisCL\\");

		CompressToZipFileResultDTO resultDTO = new CompressToZipFileResultDTO();

		String realname = "";
		realname = initPamamDTO.getSourceFileName().replaceAll("xls", "zip");
		realname = realname.replaceAll("csv", "zip");

		resultDTO.setRealFileName(realname);
		resultDTO.setDownloadFileName(newDownloadFileName);

		return resultDTO;
	}

	public synchronized CompressToZipFileResultDTO compressFilesToZipFile(List<CompressToZipFileInitParamDTO> initPamamDTO, String name , Long userKey) throws Exception
	{
		// EBO: Este método permite comprimir varios archivos en un solo
		// zip
		List<String> lista = new ArrayList<String>();
		String sourceExtension = initPamamDTO.get(0).getDownloadFileName().substring(initPamamDTO.get(0).getDownloadFileName().lastIndexOf("."),
		initPamamDTO.get(0).getDownloadFileName().length());
		String destinyExtension = ".zip";

		for (int i = 0; i < initPamamDTO.size(); i++)
		{
			String newSourceFileName = initPamamDTO.get(i).getSourceFileName().replaceAll("D:\\Projects\\ParisCL\\", "");
			lista.add(new String(newSourceFileName));
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_hh.mm.ss");
		String newDownloadFileName = name + dateFormat.format(new Date()) + " hrs"+ destinyExtension;
		String realfilename = "OC_" + dateFormat.format(new Date()) + " hrs_" + userKey + destinyExtension;
		this.compressFilesZIPLOG(lista, "D:\\Projects\\ParisCL\\","D:\\Projects\\ParisCL\\",
		realfilename.replaceAll(sourceExtension, ""));

		// Borramos archivos intermedios
		this.deleteFiles(lista, "D:\\Projects\\ParisCL\\");

		CompressToZipFileResultDTO resultDTO = new CompressToZipFileResultDTO();
		String realname = "";
		realname = realfilename.replaceAll("pdf", destinyExtension);
		realname = realname.replaceAll("pdf", destinyExtension);

		resultDTO.setRealFileName(realname);
		resultDTO.setDownloadFileName(newDownloadFileName);

		return resultDTO;
	}

	public synchronized CompressToZipFileResultDTO compressFilesToZipFile(List<CompressToZipFileInitParamDTO> initPamamDTO, String realfilename,
	String downloadfilename, Long uskey) throws Exception
	{
		// EBO: Este método permite comprimir varios archivos en un solo
		// zip
		
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH.mm.ss");
		
		downloadfilename = downloadfilename + " " + dateFormat.format(date) + " hrs.zip";
		realfilename = realfilename + "_" + dateFormat.format(date) + "_" + uskey + ".zip";
		List<String> lista = new ArrayList<String>();

		for (CompressToZipFileInitParamDTO compressToZipFileInitParamDTO : initPamamDTO) {
			lista.add(compressToZipFileInitParamDTO.getSourceFileName());
		}

		this.compressFilesZIP(lista, "D:\\Projects\\ParisCL\\",
		realfilename);

		// Borramos archivos intermedios
		this.deleteFiles(lista, "D:\\Projects\\ParisCL\\");

		CompressToZipFileResultDTO resultDTO = new CompressToZipFileResultDTO();

		resultDTO.setRealFileName(realfilename);
		resultDTO.setDownloadFileName(downloadfilename);

		return resultDTO;
	}

	public synchronized CompressToZipFileResultDTO compressTxtToZipFile(CompressToZipFileInitParamDTO initPamamDTO) throws Exception
	{
		String sourceExtension = initPamamDTO.getDownloadFileName().substring(initPamamDTO.getDownloadFileName().lastIndexOf("."),
		initPamamDTO.getDownloadFileName().length());
		String destinyExtension = ".zip";

		String newSourceFileName = initPamamDTO.getDownloadFileName();
		String newDownloadFileName = initPamamDTO.getDownloadFileName().replaceAll(sourceExtension, destinyExtension);

		List<String> lista = new ArrayList<String>();
		lista.add(new String(newSourceFileName));

		this.compressFilesZIP(lista, "D:\\Projects\\ParisCL\\",
		initPamamDTO.getSourceFileName().replaceAll(sourceExtension, ".zip"));

		// Borramos archivos intermedios
		this.deleteFiles(lista, "D:\\Projects\\ParisCL\\");

		CompressToZipFileResultDTO resultDTO = new CompressToZipFileResultDTO();

		String realname = "";
		realname = initPamamDTO.getSourceFileName().replaceAll("xls", "zip");
		realname = realname.replaceAll("csv", "zip");
		realname = realname.replaceAll("txt", "zip");

		resultDTO.setRealFileName(realname);
		resultDTO.setDownloadFileName(newDownloadFileName);

		return resultDTO;
	}

	public void deleteOldFiles(int minutes)
	{
		File iFile = new File(filePath);

		if (!iFile.isDirectory())
		{
			return;
		}

		deleteDirectory(iFile, minutes);

	}

	public static void deleteDirectory(File iFile, int minutes)
	{
		// Borrar archivos en la carpeta con tiempo de creacion anterior a
		// los
		// minutos especificados
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, (-1 * minutes));
		Date threshold = cal.getTime();

		LastModifiedDateFilter filter = new LastModifiedDateFilter(threshold);

		File[] files = iFile.listFiles(filter);

		if (files == null || files.length == 0)
		{
			return;
		}

		for (int i = 0; i < files.length; i++)
		{
			if (files[i].isDirectory())
			{
				deleteDirectory(files[i], minutes);
			}

			files[i].delete();
		}

	}

	// DSU - 2016
	public static void createPathIfNotExists(String downloadFilePath)
	{
		if (Files.notExists(Paths.get(downloadFilePath)))
		{
			(new File(downloadFilePath)).mkdirs();
		}
	}

	public static String getFileExtension(String filename)
	{
		String result = null;
		if (filename != null)
		{
			int index = filename.lastIndexOf(".");
			if (index != -1)
			{
				result = filename.substring(index + 1);
			}
		}

		return result;
	}

}
