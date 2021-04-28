package bbr.b2b.portal.classes.utils.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;
import cl.bbr.captcha.utils.BbrUtils;
import cl.bbr.core.components.basics.BbrUI;

public class ConvertExcelFile
{
	private static XSSFWorkbook	workBook;
	private static final String	IGNORE_CELL	= "#IGNORE_-CELL";

	/**
	 * 
	 * @param realfilename
	 * @param withHeader
	 *        Booleano para pintar la cabecera
	 * @return
	 */
	public static boolean csvToXLSX(String realfilename, boolean withHeader)
	{
		try
		{
			String filePath = PortalConstants.getInstance().getFileUploadPath();
			String csvFileAddress = filePath + realfilename;
			String xlsxFileAddress = filePath + realfilename.replace("csv", "xlsx");
			workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet("sheet1");
			String currentLine = null;
			int RowNum = 0;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFileAddress), "UTF-8"));
			int lengthHeader = 0;
			while ((currentLine = br.readLine()) != null)
			{
				String str[] = currentLine.split(",");
				XSSFRow currentRow = sheet.createRow(RowNum);
				for (int i = 0; i < str.length; i++)
				{
					Cell cell = currentRow.createCell(i);
					if (currentRow.getRowNum() == 0 && withHeader)
					{
						XSSFCellStyle style = sheet.getWorkbook().createCellStyle();
						style.setFillBackgroundColor(HSSFColor.YELLOW.index);
						style.setFillForegroundColor(HSSFColor.YELLOW.index);
						style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
						Font font = sheet.getWorkbook().createFont();
						font.setBold(true);
						style.setFont(font);
						style.setRightBorderColor(HSSFColor.BLACK.index);
						style.setLeftBorderColor(HSSFColor.BLACK.index);
						style.setTopBorderColor(HSSFColor.BLACK.index);
						style.setBottomBorderColor(HSSFColor.BLACK.index);
						style.setBorderBottom(BorderStyle.THIN);
						style.setBorderTop(BorderStyle.THIN);
						style.setBorderLeft(BorderStyle.THIN);
						style.setBorderRight(BorderStyle.THIN);
						cell.setCellStyle(style);
						lengthHeader = str.length;
					}
					cell.setCellValue(str[i]);
					cell.setCellType(CellType.STRING);
				}
				RowNum++;
			}

			for (int i = 0; i <= lengthHeader; i++)
			{
				sheet.autoSizeColumn(i);
			}

			FileOutputStream fileOutputStream = new FileOutputStream(xlsxFileAddress);
			workBook.write(fileOutputStream);
			fileOutputStream.close();
			br.close();
			System.out.println("Done");
			return true;
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + "Exception in try");
			return false;
		}
	}

	public static BaseResultDTO processFileAndConvertToCSV(BbrUI bbrUI, String realfilename, List<String> colsToAccept)
	{
		try
		{
			System.out.println("Inicio del procesado del Excel y conversión a CSV");
			// Inicializamos
			List<Integer> colsToIgnore = new ArrayList<>();
			List<String> colsInFile = new ArrayList<>();
			int ValidCols = 0;

			// Ruta de los archivos compartidos
			String extFile = realfilename.substring(realfilename.length() - 4, realfilename.length());

			if (extFile.indexOf("xls") == -1 && extFile.indexOf("xlsx") == -1)
			{
				// Mostramos el mensaje de error y retornamos el error
				if (!bbrUI.hasAlertWindowOpen())
				{
					bbrUI.showErrorMessage(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "format_file_excel"));
				}
				return PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501");
			}

			// Ruta exacta donde se guardará posteriormente el CSV y cambiamos
			// su extensión de XLS ó XLSX a CSV
			String shareFolder = PortalConstants.getInstance().getFileUploadPath();
			String dirToSave = shareFolder + (extFile.indexOf("xlsx") == -1 ? realfilename.replaceAll("xls", "csv") : realfilename.replaceAll("xlsx", "csv"));

			System.out.println("Ruta donde se guardará el CSV:" + dirToSave);

			// Leemos el archivo excel
			String file = PortalConstants.getInstance().getFileUploadPath() + bbrUI.getUser().getId() + "/" + realfilename;
			// String file = "C:/Users/BBR/Desktop/" + realfilename;
			System.out.println("Ruta del archivo excel a leer:" + file);
			FileInputStream input_document = new FileInputStream(file);
			Workbook my_xlsx_workbook = WorkbookFactory.create(input_document);

			// Obtenemos la hoja que vamos a modificar (0)
			Sheet my_worksheet = my_xlsx_workbook.getSheetAt(0);

			if (my_worksheet.getRow(0) != null && my_worksheet.getRow(1) != null)
			{
				// Recorremos todas las celdas de la hoja
				for (int i = 0; i <= my_worksheet.getRow(0).getLastCellNum(); i++)
				{
					// La fila (0) por que queremos obtener cada titulo
					Cell cell = my_worksheet.getRow(0).getCell(i);
					if (cell != null)
					{
						// Chequeamos si es nula o si no está en nuestra lista de
						// aceptados
						// Si no está en nuestra lista lo añadimos a la lista de las
						// columnas que queremos ignorar
						if (cell.getStringCellValue() == null || !colsToAccept.contains(cell.getStringCellValue().trim()))
						{
							colsToIgnore.add(i);
						}
						else
						{
							// Conteo de las columnas que son validas
							if (colsToAccept.get(ValidCols).equals(cell.getStringCellValue()))
							{
								colsInFile.add(cell.getStringCellValue());
								ValidCols++;
							}
							else
							{
								return PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3502");
							}
						}
					}
				}

			}
			else
			{
				if (!bbrUI.hasAlertWindowOpen())
				{
					bbrUI.showErrorMessage(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_COMMERCIAL, "empty_excel"));
				}
				return PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3503");
			}
			// Chequeamos que las columnas validas sean las indicadas
			if (colsToAccept.size() != ValidCols)
			{
				ArrayList<String> colsNeeded = new ArrayList<>();

				// Extraemos las columnas que no tenemos y son necesarias
				for (String col : colsToAccept)
				{
					if (!colsInFile.contains(col))
					{
						colsNeeded.add(col + "<br>");
					}
				}

				// Varags del multilenguaje {1..n} Como es variable se hace de
				// esta forma y no se define en el archivo de propiedades
				StringBuilder varagsI18N = new StringBuilder();
				for (int i = 0; i < colsNeeded.size(); i++)
				{
					varagsI18N.append("<li>" + (i + 1) + ". {" + i + "}" + "</li>");
				}

				// Cerramos todo
				my_xlsx_workbook.close();
				input_document.close();

				// Mostramos el mensaje de error y retornamos el error
				String txtError = I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "columns_needed_in_file_excel") + "<br>"
				+ BbrUtils.getInstance().substitute(varagsI18N.toString(), (Object[]) colsNeeded.toArray(new String[colsNeeded.size()]));
				if (!bbrUI.hasAlertWindowOpen())
				{
					bbrUI.showErrorMessage(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_GENERIC, "windows_title_error"), txtError, "20%", () -> {
					});
				}
				return PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501");
			}

			// Obtenemos todas las filas para recorrerlas
			for (int i = 0; i <= my_worksheet.getPhysicalNumberOfRows(); i++)
			{
				// Obtenemos cada fila por individual
				if (my_worksheet.getRow(i) != null)
				{
					// Recorremos cada celda de esa fila
					for (int j = 0; j < my_worksheet.getRow(i).getLastCellNum(); j++)
					{
						Cell cell = my_worksheet.getRow(i).getCell(j);
						// Verificamos si está en la lista para ser IGNORADA
						// osea que no sea una columna valida
						if (colsToIgnore.contains(j))
						{
							// Si no es una columna valida la marcamos como
							// celda a ignorar
							if (cell != null)
							{
								// IGNORE_CELL es un valor que se le da para
								// distinguir las celdas validas de las que no
								cell.setCellValue(IGNORE_CELL);
							}
						}
						else
						{
							if (cell != null)
							{
								// Si es una celda valida, decimos en todos los
								// casos que es de tipo STRING
								cell.setCellType(CellType.STRING);
							}
							else{
								cell = my_worksheet.getRow(i).createCell(j);
								cell.setCellType(CellType.STRING);
								cell.setCellValue(" ");
							}
						}
					}
				}

			}

			// IMPORTANTE: cerramos todo
			input_document.close();

			// Abrimos el archivo para posteriormente escribir los cambios
			FileOutputStream output_file = new FileOutputStream(file);

			// Escribimos los cambios
			my_xlsx_workbook.write(output_file);

			// Llamamos a un metodo de esta misma clase, para guardar como CSV
			ConvertExcelFile.saveAsCSV(my_worksheet, dirToSave);

			// IMPORTANTE: cerramos todo
			output_file.close();
			my_xlsx_workbook.close();

			return PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "0");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			if (!bbrUI.hasAlertWindowOpen())
			{
				bbrUI.showErrorMessage(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_GENERIC, "windows_title_error"),
				I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "error_processing_file"));
			}
			return PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P4000");
		}
	}

	@SuppressWarnings("deprecation")
	private static void saveAsCSV(Sheet my_worksheet, String dirToSave)
	{
		try
		{
			// Obtenemos el archivo
			new File(dirToSave);
			// Abrimos el PrintWriter para escribir en el archivo
			PrintWriter writer = new PrintWriter(dirToSave);
			Row row = null;

			// Recorremos todas las filas
			for (int i = 1; i <= my_worksheet.getLastRowNum(); i++)
			{
				int counterAdded = 0;
				// Obtenemos la fila
				row = my_worksheet.getRow(i);

				// Inicializamos
				String lineRow = i + ",";

				// Recorremos cada celda de la fila seleccionada
				for (int j = 0; j < row.getLastCellNum(); j++)
				{
					// Inicializamos
					String value = "";

					// Obtenemos la celda
					Cell cell = row.getCell(j);

					// Si la celda es de tipo numerico
					if (cell.getCellTypeEnum() == CellType.NUMERIC)
					{
						// Obtenemos el valor de la celda en String
						value = String.valueOf(cell.getNumericCellValue());
					}
					else
					{
						// Obtenemos el valor de la celda
						value = cell.getStringCellValue();
					}

					// Chequeamos que sea una celda valida
					if (!value.equalsIgnoreCase("#IGNORE_-CELL"))
					{
						// contamos +1
						counterAdded++;

						// Añadimos el valor a la linea tipo CSV separado por
						// comas
						lineRow += (counterAdded > 1 ? "," : "") + value.trim();
					}
				}

				// Escribimos en el archivos
				writer.println(lineRow);
				System.out.println(lineRow);
			}

			// Liberamos y cerramos
			writer.flush();
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
