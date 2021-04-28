package bbr.b2b.portal.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.common.data.classes.AttributeTypeDTO;
import bbr.b2b.fep.common.data.classes.ClientDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoDTO;
import bbr.b2b.fep.common.data.classes.ModuleTypeDTO;
import bbr.b2b.fep.common.data.classes.UserDataTypeDTO;
import bbr.b2b.portal.constants.FEPConstants;

public class FEPUtils
{

	private static FEPUtils _instance;

	public static synchronized FEPUtils getInstance()
	{
		if (_instance == null)
		{
			_instance = new FEPUtils();
		}

		return _instance;
	}

	private String				FILE_TRANSFER_PATH					= new String(B2BSystemPropertiesUtil.getProperty("download_file_path"));
	private String				FILE_UPLOAD_PATH					= new String(B2BSystemPropertiesUtil.getProperty("upload_file_path"));

	private String				FEP_ATTRIBUTES_FILES_PATH			= new String(B2BSystemPropertiesUtil.getProperty("fep_attributes_files_path"));

	private Boolean				fileTransferPathChecked				= false;
	private Boolean				fileUploadPathChecked				= false;

	private Boolean				fep_attributes_files_path_Checked	= false;

	private SimpleDateFormat	DOWNLOAD_DATE_FORMAT				= new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss");
	private DateTimeFormatter	DOWNLOAD_LOCALDATE_FORMAT			= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	private static final String	OS									= System.getProperty("os.name");

	public static String getOS()
	{
		return OS;
	}

	public SimpleDateFormat getDownloadDateFormat()
	{
		return this.DOWNLOAD_DATE_FORMAT;
	}

	public DateTimeFormatter getDownloadLocalDateFormat()
	{
		return this.DOWNLOAD_LOCALDATE_FORMAT;
	}

	public String getFileTransferPath()
	{
		if ((this.FILE_TRANSFER_PATH != null) && (this.FILE_TRANSFER_PATH.length() > 0) && !this.fileTransferPathChecked)
		{
			this.fileTransferPathChecked = true;

			File dir = new File(this.FILE_TRANSFER_PATH);
			dir.mkdirs();
		}

		return this.FILE_TRANSFER_PATH;
	}

	public String getFileUploadPath()
	{
		if ((this.FILE_UPLOAD_PATH != null) && (this.FILE_UPLOAD_PATH.length() > 0) && !this.fileUploadPathChecked)
		{
			this.fileUploadPathChecked = true;

			File dir = new File(this.FILE_UPLOAD_PATH);
			dir.mkdirs();
		}

		return this.FILE_UPLOAD_PATH;
	}

	public void setFileTransferPath(String s)
	{
		this.FILE_TRANSFER_PATH = (s != null) ? s.trim() : "";

		if ((this.FILE_TRANSFER_PATH != null) && (this.FILE_TRANSFER_PATH.length() > 0) && !this.fileTransferPathChecked)
		{
			this.fileTransferPathChecked = true;

			File dir = new File(this.FILE_TRANSFER_PATH);
			dir.mkdirs();
		}
	}

	// Path donde se alojan los ficheros de los items de modificación de productos MDM
	public String getAttributesFilesPath()
	{
		if ((this.FEP_ATTRIBUTES_FILES_PATH != null) && (this.FEP_ATTRIBUTES_FILES_PATH.length() > 0) && !this.fep_attributes_files_path_Checked)
		{
			this.fep_attributes_files_path_Checked = true;

			File dir = new File(this.FEP_ATTRIBUTES_FILES_PATH);
			dir.mkdirs();
		}

		return this.FEP_ATTRIBUTES_FILES_PATH;
	}

	public CellStyle getHeaderCellStyle(XSSFWorkbook workbook)
	{
		CellStyle headercs = workbook.createCellStyle();

		Font headertextfont = workbook.createFont();
		headertextfont.setFontHeightInPoints((short) 10);
		headertextfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headercs.setFont(headertextfont);
		headercs.setAlignment(CellStyle.ALIGN_CENTER);
		headercs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headercs.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		headercs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headercs.setWrapText(true);
		headercs.setBorderRight(CellStyle.BORDER_THIN);
		headercs.setBorderTop(CellStyle.BORDER_THIN);
		headercs.setBorderLeft(CellStyle.BORDER_THIN);
		headercs.setBorderBottom(CellStyle.BORDER_THIN);
		//headercs.setShrinkToFit(true);

		return headercs;
	}

	public CellStyle getValueCellStyle(XSSFWorkbook workbook)
	{
		Font stringtextfont = workbook.createFont();
		stringtextfont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		stringtextfont.setFontHeightInPoints((short) 10);

		CellStyle stringscs = workbook.createCellStyle();
		stringscs.setFont(stringtextfont);
		stringscs.setAlignment(CellStyle.ALIGN_LEFT);
		stringscs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// stringscs.setFillForegroundColor(IndexedColors.INDIGO.getIndex());
		// stringscs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// stringscs.setWrapText(true);
		stringscs.setBorderRight(CellStyle.BORDER_THIN);
		stringscs.setBorderTop(CellStyle.BORDER_THIN);
		stringscs.setBorderLeft(CellStyle.BORDER_THIN);
		stringscs.setBorderBottom(CellStyle.BORDER_THIN);
		//stringscs.setShrinkToFit(true);

		return stringscs;
	}
	
	public CellStyle getValueCellStyleGrey(XSSFWorkbook workbook)
	{
		Font stringtextfont = workbook.createFont();
		stringtextfont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		stringtextfont.setFontHeightInPoints((short) 10);

		CellStyle stringscsgrey = workbook.createCellStyle();
		stringscsgrey.setFont(stringtextfont);
		stringscsgrey.setAlignment(CellStyle.ALIGN_LEFT);
		stringscsgrey.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		stringscsgrey.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		stringscsgrey.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// stringscs.setWrapText(true);
		stringscsgrey.setBorderRight(CellStyle.BORDER_THIN);
		stringscsgrey.setBorderTop(CellStyle.BORDER_THIN);
		stringscsgrey.setBorderLeft(CellStyle.BORDER_THIN);
		stringscsgrey.setBorderBottom(CellStyle.BORDER_THIN);

		return stringscsgrey;
	}
	
	public CellStyle getNumericValueCellStyle(XSSFWorkbook workbook)
	{
		Font stringtextfont = workbook.createFont();
		stringtextfont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		stringtextfont.setFontHeightInPoints((short) 10);

		CellStyle numericcs = workbook.createCellStyle();
		DataFormat numericformat = workbook.createDataFormat();
		numericcs.setFont(stringtextfont);
		numericcs.setDataFormat(numericformat.getFormat("#,##0")); 
		numericcs.setAlignment(CellStyle.ALIGN_LEFT);
		numericcs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// stringscs.setFillForegroundColor(IndexedColors.INDIGO.getIndex());
		// stringscs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// stringscs.setWrapText(true);
		numericcs.setBorderRight(CellStyle.BORDER_THIN);
		numericcs.setBorderTop(CellStyle.BORDER_THIN);
		numericcs.setBorderLeft(CellStyle.BORDER_THIN);
		numericcs.setBorderBottom(CellStyle.BORDER_THIN);

		return numericcs;
	}
	
	public CellStyle getNumericValueCellStyleGrey(XSSFWorkbook workbook)
	{
		Font stringtextfont = workbook.createFont();
		stringtextfont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		stringtextfont.setFontHeightInPoints((short) 10);

		CellStyle numericcsgrey = workbook.createCellStyle();
		DataFormat numericformat = workbook.createDataFormat();
		numericcsgrey.setFont(stringtextfont);
		numericcsgrey.setDataFormat(numericformat.getFormat("#,##0")); 
		numericcsgrey.setAlignment(CellStyle.ALIGN_LEFT);
		numericcsgrey.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		numericcsgrey.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		numericcsgrey.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// stringscs.setWrapText(true);
		numericcsgrey.setBorderRight(CellStyle.BORDER_THIN);
		numericcsgrey.setBorderTop(CellStyle.BORDER_THIN);
		numericcsgrey.setBorderLeft(CellStyle.BORDER_THIN);
		numericcsgrey.setBorderBottom(CellStyle.BORDER_THIN);

		return numericcsgrey;
	}

	public CellStyle getHelpColumnCellStyle(XSSFWorkbook workbook)
	{
		Font helptextfont = workbook.createFont();
		helptextfont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		helptextfont.setFontHeightInPoints((short) 8);

		CellStyle helpcs = workbook.createCellStyle();
		helpcs.setFont(helptextfont);
		helpcs.setAlignment(CellStyle.ALIGN_CENTER);
		helpcs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		helpcs.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		helpcs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		helpcs.setWrapText(true);
		helpcs.setBorderRight(CellStyle.BORDER_THIN);
		helpcs.setBorderTop(CellStyle.BORDER_THIN);
		helpcs.setBorderLeft(CellStyle.BORDER_THIN);
		helpcs.setBorderBottom(CellStyle.BORDER_THIN);

		return helpcs;
	}

	public static CellStyle getLinkCellStyle(XSSFWorkbook workbook)
	{
		CellStyle hlink_style = workbook.createCellStyle();
		Font hlink_font = workbook.createFont();
		hlink_font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		hlink_font.setUnderline(Font.U_SINGLE);
		hlink_font.setColor(IndexedColors.BLUE.getIndex());
		hlink_style.setFont(hlink_font);
		hlink_style.setAlignment(CellStyle.ALIGN_LEFT);
		
		hlink_style.setBorderRight(CellStyle.BORDER_THIN);
		hlink_style.setBorderTop(CellStyle.BORDER_THIN);
		hlink_style.setBorderLeft(CellStyle.BORDER_THIN);
		hlink_style.setBorderBottom(CellStyle.BORDER_THIN);
		
		return hlink_style;
	}

	/**
	 * Guarda los valores de las plantillas en una hoja de valores y crea un nombre para poder crear dropdowns en el excel
	 * 
	 * @param sheet
	 *            Hola de Datos principal
	 * @param valuesheet
	 *            Hoja de datos donde se colocarán los valores
	 * @param v_index
	 *            indice que se usará para dos cosas, es la columna donde se crean los selectores y a la vez es el numero de la fila donde se guardaran en la hoja de valores
	 * @param validationHelper
	 *            Helper para crear validaciones en la hoja
	 * @return la hoja principal
	 */
	public static XSSFSheet saveArticlesToSheet(XSSFSheet sheet, XSSFSheet valuesheet, int v_index, XSSFDataValidationHelper validationHelper, ArticleTypeDataDTO[] articles)
	{
		// Crear los valores para los Selectores de Cadena en la hoja de valores
		try
		{
			if (articles != null && articles.length > 0)
			{
				Row valuerow = valuesheet.createRow(v_index);
				for (int i = 0; i < articles.length; i++)
				{
					String name = articles[i].getName();
					Cell cell = valuerow.createCell(i);
					cell.setCellValue(name);
				}

				String lascolumnname = CellReference.convertNumToColString(articles.length - 1);
				// Crear un nombre en el libro asociado a la columna de datos previamente creada
				Name namedCell = valuesheet.getWorkbook().createName();
				namedCell.setNameName("Fila_" + v_index); // nombre
				String formulaStr = valuesheet.getSheetName() + "!$A$" + (v_index + 1) + ":$" + lascolumnname + "$" + (v_index + 1);
				namedCell.setRefersToFormula(formulaStr);
				DataValidationConstraint constraint = validationHelper.createFormulaListConstraint("Fila_" + v_index);

				CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, v_index, v_index);// v_index es el numero de la columna ,, 1000 filas
				DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
				sheet.addValidationData(dataValidation);
			}

		}
		catch (Exception e)
		{
			// logger.error("No se pudo obtener tipos de plantillas para utilizarlos en la descarga de Plantillas");
		}

		return sheet;

	}

	/**
	 * Define si un tipo de atributo es uan lista de las que requiere valores
	 */
	public static boolean attributeIsValueList(String att_type)
	{

		boolean isaList = FEPConstants.ATTRIBUTE_NAME_TYPE_CODEDLIST.equals(att_type) || FEPConstants.ATTRIBUTE_NAME_TYPE_LIST.equals(att_type) ||
				FEPConstants.ATTRIBUTE_NAME_TYPE_MU_LIST.equals(att_type) || FEPConstants.ATTRIBUTE_NAME_TYPE_MUCODEDLIST.equals(att_type) || 
				FEPConstants.ATTRIBUTE_NAME_TYPE_HIERARCHI_RETAIL.equals(att_type);

		return isaList;

	}

	/**
	 * Define si un tipo de atributo es una imagen o fichero lista de las que requiere valores
	 */
	public static boolean attributeIsaFile(String att_type)
	{

		boolean isaFile = FEPConstants.ATTRIBUTE_NAME_TYPE_IMAGE.equals(att_type) || FEPConstants.ATTRIBUTE_NAME_TYPE_FILE.equals(att_type);

		return isaFile;

	}

	/**
	 * Define si un tipo de atributo es numérico (entero o decimal)
	 */
	public static boolean attributeIsaNumber(String att_type)
	{

		boolean isaFile = FEPConstants.ATTRIBUTE_NAME_TYPE_INTEGER.equals(att_type) || FEPConstants.ATTRIBUTE_NAME_TYPE_FLOAT.equals(att_type);

		return isaFile;

	}

	public static String getCodedValue(String description, String code)
	{
		String result = description + " [" + code + "]";
		return result;
	}

	/*
	 * Obtiene el código de un valor codificado
	 * De la forma: Kilogramo [kg]
	 * EL código se encuentra entre las cadenas separadores que se definan
	 * Por ejemplo Kilogramo [kg] comienza con [ y termina con ]
	 * Si no ahy codigo se devuelve el mismo valor de entrada porque se asume que es el código solo
	 */
	public static String getCodeFromCodedValue(String value, String separator)
	{
		String result = "";
		if (value != null && value.length() > 2)
		{
			// Separar cadenas por separador de lista multiple
			String[] values = value.split(FEPConstants.LIST_SEPARATOR);
			for (String valstr : values)
			{
				// Buscar código
				int firstindex = valstr.lastIndexOf(FEPConstants.PROD_ATTRIB_CODED_VALUE_BEGIN) + 1;
				int endindex = valstr.lastIndexOf(FEPConstants.PROD_ATTRIB_CODED_VALUE_END);
				if (endindex > firstindex)
				{
					String tmpcoded_val = valstr.substring(firstindex, endindex).trim();
					if (result.isEmpty())
					{
						result = tmpcoded_val;
					}
					else
					{
						result += separator + tmpcoded_val;
					}
				}
			}
		}
		return (result != null && !result.isEmpty()) ? result.trim() : value;
	}

	/*
	 * Obtiene la descripción de de un valor codificado
	 * De la forma: Kilogramo [kg]
	 * EL nombre se encuentra antes de las cadenas separadoras
	 * Por ejemplo Kilogramo [kg] comienza con [ y termina con ] Retorna Kilogramo
	 */
	public static String getDescriptionFromCodedValue(String value, String separator)
	{
		String result = "";
		if (value != null && value.length() > 0)
		{
			// Separar cadenas por separador de lista multiple
			String[] values = value.split(FEPConstants.LIST_SEPARATOR);
			for (String valstr : values)
			{
				// Buscar primera separador
				int firstindex = valstr.lastIndexOf(FEPConstants.PROD_ATTRIB_CODED_VALUE_BEGIN);
				String tmpcoded_val = valstr.substring(0, firstindex).trim();
				if (result.isEmpty())
				{
					result = tmpcoded_val;
				}
				else
				{
					result += separator + tmpcoded_val;
				}
			}
		}
		return (result != null && !result.isEmpty()) ? result.trim() : value;
	}

	public static void addErrorResult(List<ErrorInfoDTO> list, int row, int column, String message)
	{
		ErrorInfoDTO aresult = new ErrorInfoDTO();
		aresult.setRownumber(row);
		aresult.setColnumber(column);
		aresult.setStatusmessage(message);
		list.add(aresult);
	}

	public static void addErrorResult(List<ErrorInfoDTO> list, int row, int column, String rowname, String columnname, String message)
	{
		ErrorInfoDTO aresult = new ErrorInfoDTO();
		aresult.setRownumber(row);
		aresult.setColnumber(column);
		aresult.setColumnname(columnname);
		aresult.setRowname(rowname);
		aresult.setStatusmessage(message);
		list.add(aresult);
	}

	public static String getValueData(String valuetype, Cell cel, int cellType) throws AccessDeniedException
	{

		String result = null;

		if (cel == null || (cel.toString() != null && cel.toString().isEmpty()) || cellType == HSSFCell.CELL_TYPE_BLANK || cellType == HSSFCell.CELL_TYPE_ERROR)
		{
			result = "";
			return result;
		}

		if (HSSFCell.CELL_TYPE_FORMULA == cellType)
		{
			result = getValueData(valuetype, cel, cel.getCachedFormulaResultType());
			return result;
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");

		NumberFormat dnf = new DecimalFormat();
		dnf.setMaximumFractionDigits(0);
		dnf.setGroupingUsed(false);
		NumberFormat df2 = new DecimalFormat();
		df2.setMaximumFractionDigits(2);
		df2.setGroupingUsed(false);

		// int cellType = cel.getCellType();

		if (attributeIsValueList(valuetype) ||
				valuetype.equals("String") || valuetype.equals("Image") ||
				valuetype.equals("File") || valuetype.equals("Memo"))
		{
			valuetype = "String";
		}

		try
		{

			if (valuetype.equals("Integer"))
			{
				if (HSSFCell.CELL_TYPE_STRING == cellType)
				{
					result = cel.getStringCellValue();
				}
				else if (HSSFCell.CELL_TYPE_NUMERIC == cellType)
				{
					Double d = cel.getNumericCellValue();
					if ((d % 1) == 0)
					{
						result = dnf.format(cel.getNumericCellValue());
					}
					else
					{
						throw new AccessDeniedException("Este valor no es válido (" + valuetype + ") : " + cel.toString().trim());
					}
				}

			}
			else if (valuetype.equals("String"))
			{
				if (HSSFCell.CELL_TYPE_STRING == cellType)
				{
					result = cel.getStringCellValue();
				}
				else if (HSSFCell.CELL_TYPE_NUMERIC == cellType)
				{
					if (HSSFDateUtil.isCellDateFormatted(cel))
					{
						result = dtf.format(cel.getDateCellValue());
					}
					else
					{
						Double d = cel.getNumericCellValue();
						if ((d % 1) == 0)
						{
							result = dnf.format(cel.getNumericCellValue());
						}
						else
						{
							result = df2.format(cel.getNumericCellValue());
						}
					}
				}
				else if (HSSFCell.CELL_TYPE_BOOLEAN == cellType)
				{
					boolean bv = cel.getBooleanCellValue();
					result = bv ? "SI" : "NO";
				}
				else
				{
					throw new AccessDeniedException("Este valor no es válido (" + valuetype + ") : " + cel.toString().trim());
					// result = cel.toString(); // Esto hay que verificarlo
				}

			}
			else if (valuetype.equals("Float"))
			{
				if (HSSFCell.CELL_TYPE_STRING == cellType)
				{
					result = cel.getStringCellValue();
				}
				else if (HSSFCell.CELL_TYPE_NUMERIC == cellType)
				{
					result = df2.format(cel.getNumericCellValue());
				}

				// Float tmpvalue = Float.valueOf(cel.toString().trim());

			}
			else if (valuetype.equals("Date"))
			{
				if (HSSFCell.CELL_TYPE_STRING == cellType)
				{
					df.parse(cel.getStringCellValue());
					result = cel.getStringCellValue();
				}
				else if (HSSFCell.CELL_TYPE_NUMERIC == cellType)
				{
					result = df.format(cel.getDateCellValue());
				}

			}
			else if (valuetype.equals("DateTime"))
			{
				if (HSSFCell.CELL_TYPE_STRING == cellType)
				{
					dtf.parse(cel.getStringCellValue());
					result = cel.getStringCellValue();
				}
				else if (HSSFCell.CELL_TYPE_NUMERIC == cellType)
				{
					result = dtf.format(cel.getDateCellValue());
				}

			}
			else if (valuetype.equals("Time"))
			{
				if (HSSFCell.CELL_TYPE_STRING == cellType)
				{
					tf.parse(cel.getStringCellValue());
					result = cel.getStringCellValue();
				}
				else if (HSSFCell.CELL_TYPE_NUMERIC == cellType)
				{
					result = tf.format(cel.getDateCellValue());
				}

			}
			else if (valuetype.equals("Boolean"))
			{

				if (HSSFCell.CELL_TYPE_BOOLEAN == cellType)
				{
					boolean bv = cel.getBooleanCellValue();
					result = bv ? "SI" : "NO";
				}
				else
				{
					boolean bvaltrue = cel.toString().trim().equalsIgnoreCase("true") || cel.toString().trim().equalsIgnoreCase("si") || cel.getStringCellValue().trim().equalsIgnoreCase("sí");
					boolean bvalfalse = cel.toString().trim().equalsIgnoreCase("false") || cel.toString().trim().equalsIgnoreCase("no");
					if (bvaltrue || bvalfalse)
					{
						result = bvaltrue ? "SI" : "NO"; // cel.getStringCellValue().trim();
					}
					else
						//throw new AccessDeniedException("Este valor no es válido (" + valuetype + ") : " + cel.toString().trim());
						result = cel.toString().trim();
				}

			}
			else
				throw new AccessDeniedException("Tipo básico no habilitado");
		}
		catch (Exception e)
		{
			throw new AccessDeniedException("Este valor no es válido (" + valuetype + ") : " + cel.toString().trim());
		}
		return result;
	}

	public static String getBooleanValueByLanguage(String value, Locale locale)
	{
		String result = "NO";

		if (value != null && !value.isEmpty())
		{
			String bvalue = "No";
			if (locale.getLanguage().contains("es"))
			{
				bvalue = (value.equalsIgnoreCase("si") || value.equalsIgnoreCase("true")) ? "Si" : "No";
			}
			else
			{
				bvalue = (value.equalsIgnoreCase("si") || value.equalsIgnoreCase("true")) ? "Yes" : "No";
			}
			result = bvalue;

		}

		return result;
	}

	public static String getFilenameByUri(String uri)
	{
		String result = "";
		try
		{
			result = new File(new URL(uri).getPath().toString()).getName();
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Guarda los valores de los tipos de datos del usuario en una hoja de valores y crea un nombre para poder crear dropdowns en el excel
	 * 
	 * @param sheet
	 *            Hola de Datos principal
	 * @param valuesheet
	 *            Hoja de datos donde se colocarán los valores
	 * @param v_index
	 *            indice que se usará para dos cosas, es la columna donde se crean los selectores y a la vez es el numero de la fila donde se guardaran en la hoja de valores
	 * @param validationHelper
	 *            Helper para crear validaciones en la hoja
	 * @return la hoja principal
	 */
	public static XSSFSheet saveUserDataTypesToSheet(XSSFSheet sheet, XSSFSheet valuesheet, int v_index, XSSFDataValidationHelper validationHelper, UserDataTypeDTO[] userdatatypes)
	{
		// Crear los valores para los Selectores de Cadena en la hoja de valores
		try
		{
			if (userdatatypes != null && userdatatypes.length > 0)
			{
				Row valuerow = valuesheet.createRow(v_index);
				for (int i = 0; i < userdatatypes.length; i++)
				{
					String name = userdatatypes[i].getName();
					Cell cell = valuerow.createCell(i);
					cell.setCellValue(name);
				}

				String lascolumnname = CellReference.convertNumToColString(userdatatypes.length - 1);
				// Crear un nombre en el libro asociado a la columna de datos previamente creada
				Name namedCell = valuesheet.getWorkbook().createName();
				namedCell.setNameName("Fila_" + v_index); // nombre
				String formulaStr = valuesheet.getSheetName() + "!$A$" + (v_index + 1) + ":$" + lascolumnname + "$" + (v_index + 1);
				namedCell.setRefersToFormula(formulaStr);
				DataValidationConstraint constraint = validationHelper.createFormulaListConstraint("Fila_" + v_index);

				CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, v_index, v_index);// v_index es el numero de la columna ,, 1000 filas
				DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
				sheet.addValidationData(dataValidation);
			}

		}
		catch (Exception e)
		{
			// logger.error("No se pudo obtener tipos de plantillas para utilizarlos en la descarga de Plantillas");
		}

		return sheet;
	}

	/**
	 * Guarda los valores de los tipos de atributos en una hoja de valores y crea un nombre para poder crear dropdowns en el excel
	 * 
	 * @param sheet
	 *            Hola de Datos principal
	 * @param valuesheet
	 *            Hoja de datos donde se colocarán los valores
	 * @param v_index
	 *            indice que se usará para dos cosas, es la columna donde se crean los selectores y a la vez es el numero de la fila donde se guardaran en la hoja de valores
	 * @param validationHelper
	 *            Helper para crear validaciones en la hoja
	 * @return la hoja principal
	 */
	public static XSSFSheet saveAttributeTypeToSheet(XSSFSheet sheet, XSSFSheet valuesheet, int v_index, XSSFDataValidationHelper validationHelper, AttributeTypeDTO[] attributetypes)
	{
		// Crear los valores para los Selectores de Cadena en la hoja de valores
		try
		{
			if (attributetypes != null && attributetypes.length > 0)
			{
				Row valuerow = valuesheet.createRow(v_index);
				for (int i = 0; i < attributetypes.length; i++)
				{
					String name = attributetypes[i].getName();
					Cell cell = valuerow.createCell(i);
					cell.setCellValue(name);
				}

				String lascolumnname = CellReference.convertNumToColString(attributetypes.length - 1);
				// Crear un nombre en el libro asociado a la columna de datos previamente creada
				Name namedCell = valuesheet.getWorkbook().createName();
				namedCell.setNameName("Fila_" + v_index); // nombre
				String formulaStr = valuesheet.getSheetName() + "!$A$" + (v_index + 1) + ":$" + lascolumnname + "$" + (v_index + 1);
				namedCell.setRefersToFormula(formulaStr);
				DataValidationConstraint constraint = validationHelper.createFormulaListConstraint("Fila_" + v_index);

				CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, v_index, v_index);// v_index es el numero de la columna ,, 1000 filas
				DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
				sheet.addValidationData(dataValidation);
			}

		}
		catch (Exception e)
		{
			// logger.error("No se pudo obtener tipos de plantillas para utilizarlos en la descarga de Plantillas");
		}

		return sheet;
	}

	/**
	 * Guarda los valores de los tipos de módulos en una hoja de valores y crea un nombre para poder crear dropdowns en el excel
	 * 
	 * @param sheet
	 *            Hola de Datos principal
	 * @param valuesheet
	 *            Hoja de datos donde se colocarán los valores
	 * @param v_index
	 *            indice que se usará para dos cosas, es la columna donde se crean los selectores y a la vez es el numero de la fila donde se guardaran en la hoja de valores
	 * @param validationHelper
	 *            Helper para crear validaciones en la hoja
	 * @return la hoja principal
	 */
	public static XSSFSheet saveModuleTypeToSheet(XSSFSheet sheet, XSSFSheet valuesheet, int v_index, XSSFDataValidationHelper validationHelper, ModuleTypeDTO[] moduletypes)
	{
		// Crear los valores para los Selectores de Cadena en la hoja de valores
		try
		{
			if (moduletypes != null && moduletypes.length > 0)
			{
				Row valuerow = valuesheet.createRow(v_index);
				for (int i = 0; i < moduletypes.length; i++)
				{
					String name = moduletypes[i].getName();
					Cell cell = valuerow.createCell(i);
					cell.setCellValue(name);
				}

				String lascolumnname = CellReference.convertNumToColString(moduletypes.length - 1);
				// Crear un nombre en el libro asociado a la columna de datos previamente creada
				Name namedCell = valuesheet.getWorkbook().createName();
				namedCell.setNameName("Fila_" + v_index); // nombre
				String formulaStr = valuesheet.getSheetName() + "!$A$" + (v_index + 1) + ":$" + lascolumnname + "$" + (v_index + 1);
				namedCell.setRefersToFormula(formulaStr);
				DataValidationConstraint constraint = validationHelper.createFormulaListConstraint("Fila_" + v_index);

				CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, v_index, v_index);// v_index es el numero de la columna ,, 1000 filas
				DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
				sheet.addValidationData(dataValidation);
			}

		}
		catch (Exception e)
		{
			// logger.error("No se pudo obtener tipos de plantillas para utilizarlos en la descarga de Plantillas");
		}

		return sheet;
	}
	
	/**
	 * Guarda los valores booleanos en una hoja de valores y crea un nombre para poder crear dropdowns en el excel
	 * 
	 * @param sheet
	 *            Hola de Datos principal
	 * @param valuesheet
	 *            Hoja de datos donde se colocarán los valores
	 * @param v_index
	 *            indice que se usará para dos cosas, es la columna donde se crean los selectores y a la vez es el numero de la fila donde se guardaran en la hoja de valores
	 * @param validationHelper
	 *            Helper para crear validaciones en la hoja
	 * @return la hoja principal
	 */
	public static XSSFSheet saveSimpleBooleanTypeToSheet(XSSFSheet sheet, XSSFSheet valuesheet, int v_index, XSSFDataValidationHelper validationHelper)
	{
		// Crear los valores para los Selectores de Cadena en la hoja de valores
		try
		{
			String[] arrayvalues = {"SI", "NO"};
			
			Row valuerow = valuesheet.createRow(v_index);
			for (int i = 0; i < arrayvalues.length; i++)
			{
				String name = arrayvalues[i];
				Cell cell = valuerow.createCell(i);
				cell.setCellValue(name);
			}

			String lascolumnname = CellReference.convertNumToColString(arrayvalues.length - 1);
			// Crear un nombre en el libro asociado a la columna de datos previamente creada
			Name namedCell = valuesheet.getWorkbook().createName();
			namedCell.setNameName("Fila_" + v_index); // nombre
			String formulaStr = valuesheet.getSheetName() + "!$A$" + (v_index + 1) + ":$" + lascolumnname + "$" + (v_index + 1);
			namedCell.setRefersToFormula(formulaStr);
			DataValidationConstraint constraint = validationHelper.createFormulaListConstraint("Fila_" + v_index);

			CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, v_index, v_index);// v_index es el numero de la columna ,, 1000 filas
			DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
			sheet.addValidationData(dataValidation);

		}
		catch (Exception e)
		{
			// logger.error("No se pudo obtener tipos de plantillas para utilizarlos en la descarga de Plantillas");
		}

		return sheet;
	}
	
	/**
	 * Guarda los valores booleanos en una hoja de valores y crea un nombre para poder crear dropdowns en el excel
	 * 
	 * @param sheet
	 *            Hola de Datos principal
	 * @param valuesheet
	 *            Hoja de datos donde se colocarán los valores
	 * @param v_index
	 *            indice que se usará para dos cosas, es la columna donde se crean los selectores y a la vez es el numero de la fila donde se guardaran en la hoja de valores
	 * @param validationHelper
	 *            Helper para crear validaciones en la hoja
	 * @return la hoja principal
	 */
	public static XSSFSheet saveBooleanTypeToSheet(XSSFSheet sheet, XSSFSheet valuesheet, int first_data_row, int v_index, int valuerowindex, int rowcount, XSSFDataValidationHelper validationHelper)
	{
		// Crear los valores para los Selectores de Cadena en la hoja de valores
		try
		{
			String[] arrayvalues = {"SI", "NO"};
			
			Row valuerow = valuesheet.createRow(valuerowindex);
			for (int i = 0; i < arrayvalues.length; i++)
			{
				String name = arrayvalues[i];
				Cell cell = valuerow.createCell(i);
				cell.setCellValue(name);
			}

			String lascolumnname = CellReference.convertNumToColString(arrayvalues.length - 1);
			// Crear un nombre en el libro asociado a la columna de datos previamente creada
			Name namedCell = valuesheet.getWorkbook().createName();
			namedCell.setNameName("Fila_" + valuerowindex); // nombre
			String formulaStr = valuesheet.getSheetName() + "!$A$" + (valuerowindex + 1) + ":$" + lascolumnname + "$" + (valuerowindex + 1);
			namedCell.setRefersToFormula(formulaStr);
			DataValidationConstraint constraint = validationHelper.createFormulaListConstraint("Fila_" + valuerowindex);

			CellRangeAddressList addressList = new CellRangeAddressList(first_data_row, first_data_row + rowcount, v_index, v_index);// v_index es el numero de la columna ,, 1000 filas
			DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
			sheet.addValidationData(dataValidation);

		}
		catch (Exception e)
		{
			// logger.error("No se pudo obtener tipos de plantillas para utilizarlos en la descarga de Plantillas");
		}

		return sheet;
	}
	
	/**
	 * Guarda los valores de los tipos de clientes en una hoja de valores y crea un nombre para poder crear dropdowns en el excel
	 * 
	 * @param sheet
	 *            Hola de Datos principal
	 * @param valuesheet
	 *            Hoja de datos donde se colocarán los valores
	 * @param v_index
	 *            indice que se usará para dos cosas, es la columna donde se crean los selectores y a la vez es el numero de la fila donde se guardaran en la hoja de valores
	 * @param validationHelper
	 *            Helper para crear validaciones en la hoja
	 * @return la hoja principal
	 */
	public static XSSFSheet saveClientsToSheet(XSSFSheet sheet, XSSFSheet valuesheet, int v_index, XSSFDataValidationHelper validationHelper, ClientDTO[] clients)
	{
		// Crear los valores para los Selectores de Cadena en la hoja de valores
		try
		{
			if (clients != null && clients.length > 0)
			{
				Row valuerow = valuesheet.createRow(v_index);
				for (int i = 0; i < clients.length; i++)
				{
					String name = clients[i].getInternalname();
					Cell cell = valuerow.createCell(i);
					cell.setCellValue(name);
				}

				String lascolumnname = CellReference.convertNumToColString(clients.length - 1);
				// Crear un nombre en el libro asociado a la columna de datos previamente creada
				Name namedCell = valuesheet.getWorkbook().createName();
				namedCell.setNameName("Fila_" + v_index); // nombre
				String formulaStr = valuesheet.getSheetName() + "!$A$" + (v_index + 1) + ":$" + lascolumnname + "$" + (v_index + 1);
				namedCell.setRefersToFormula(formulaStr);
				DataValidationConstraint constraint = validationHelper.createFormulaListConstraint("Fila_" + v_index);

				CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, v_index, v_index);// v_index es el numero de la columna ,, 1000 filas
				DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
				sheet.addValidationData(dataValidation);
			}

		}
		catch (Exception e)
		{
			// logger.error("No se pudo obtener tipos de plantillas para utilizarlos en la descarga de Plantillas");
		}

		return sheet;
	}
	
	public static Double getDecimalFormat (String value) throws ParseException{
		
		Double result = 0D;

		DecimalFormat df = new DecimalFormat();
		DecimalFormatSymbols newSymbols =  new DecimalFormatSymbols();
		newSymbols.setDecimalSeparator(FEPConstants.DECIMAL_SEPARATOR);
		df.setDecimalFormatSymbols(newSymbols);
		result = (df.parse(value)).doubleValue();
		
		return result;
	}


}
