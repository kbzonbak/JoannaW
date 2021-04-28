package bbr.b2b.logistic.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;

public class ClassUtilities {

	public static final long MILISPERDAY = 24 * 60 * 60 * 1000;

	private static final String LINE_SEPARATOR = "\r\n";

	public static Date addDaysToDate(Date source, int increment) {
		Calendar cal = null;
		Locale locale = null;
		locale = new Locale("es", "CL");
		cal = Calendar.getInstance(locale);
		cal.setTime(source);
		int newday = cal.get(Calendar.DAY_OF_MONTH) + increment;
		cal.set(Calendar.DAY_OF_MONTH, newday);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		source = cal.getTime();
		return source;
	}

	static public Date getTimeofDate(Date date) {
		Date Result = null;
		long ld = date.getTime();
		TimeZone tz = TimeZone.getDefault();
		ld = ld + tz.getRawOffset();
		long dmilis = ld % MILISPERDAY;
		dmilis = dmilis - tz.getRawOffset();
		Result = new Date(dmilis);
		return Result;
	}

	public static Object[] splitArray(Object[] source, Class<?> sourceclass, int chunksize) {
		int nchunks = (source.length / chunksize) + 1;
		Object[] result = new Object[nchunks];
		int chunk = 0;
		int i;
		for (i = 0; i < nchunks - 1; i++) {
			Object array = Array.newInstance(sourceclass, chunksize);
			System.arraycopy(source, chunk, array, 0, chunksize);
			result[i] = array;
			chunk += chunksize;
		}
		chunksize = source.length % chunksize;
		Object array2 = Array.newInstance(sourceclass, chunksize);
		System.arraycopy(source, chunk, array2, 0, chunksize);
		result[i] = array2;
		return result;
	}

	public static Object unsplitArrays(Object[] source, Class<?> sourceclass) {
		int size = 0;
		for (int i = 0; i < source.length; i++) {
			Object[] element = (Object[]) source[i];
			size += element.length;
		}
		int index = 0;
		Object result = Array.newInstance(sourceclass, size);
		for (int i = 0; i < source.length; i++) {
			Object[] element = (Object[]) source[i];
			System.arraycopy(element, 0, result, index, element.length);
			index += element.length;
		}
		return result;
	}

	/**
	 * Generador de query para añadir datos desde un CSV a una tabla temporal Para obtener linesDataArray (del CSV)
	 * llamar a {@link ClassUtilities.getLineDataArrayFromCSV(String path, Charset encoding)} antes
	 * 
	 * @param temporalTable
	 * @param columnsArray
	 * @param linesDataArray
	 * @return
	 */
	public static String getQueryElementsTempTable(String temporalTable, String[] columnsArray, String[] linesDataArray) {
		final String STATEMENT_TO_EXECUTE = "INSERT INTO "; // Con espacio al final
		final String VALUES = " VALUES ";
		String query = "";
		String columns = Arrays.stream(columnsArray).map(column -> column + ",").reduce("", String::concat);
		columns = addParentheses(columns.substring(0, columns.length() - 1));
		String data = Arrays.stream(linesDataArray).map(lineData -> addParentheses(lineData) + ",").reduce("", String::concat);
		data = data.substring(0, data.length() - 1);
		query = STATEMENT_TO_EXECUTE + temporalTable + columns + VALUES + data;
		System.out.println("QUERY: " + query);
		return query;
	}

	/**
	 * Lee el CSV y lo convierte a un arreglo de String
	 * 
	 * @param path
	 * @param encoding
	 * @return String[]
	 * @throws IOException
	 */
	public static String[] getLineDataArrayFromCSV(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		String fileContent = new String(encoded, encoding);
		String[] linesData = fileContent.split("\\r?\\n");
		return linesData;
	}

	private static String addParentheses(String word) {
		return "(" + word + ")";
	}

	public static boolean doExportCSVFromTempTable(String file, List<String> list) {
		boolean executed = true;
		String resultQuery = list.stream().map(line -> line + LINE_SEPARATOR).reduce("", String::concat);
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.print(resultQuery);
			writer.close();
		} catch (FileNotFoundException e) {
			executed = false;
			e.printStackTrace();
		}
		return executed;
	}

	
	public static String doCSVString(String[][] data, String separator) throws OperationFailedException {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				stringBuffer.append(data[i][j]);
				stringBuffer.append(separator);
			}
			// Eliminamos el ultimo separador y ponemos un salto de linea
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			stringBuffer.append("\n");
		}
		return stringBuffer.toString();
	}
}
