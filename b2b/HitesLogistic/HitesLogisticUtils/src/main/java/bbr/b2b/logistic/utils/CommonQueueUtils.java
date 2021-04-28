package bbr.b2b.logistic.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;

public class CommonQueueUtils {

	// LoggingUtil loggingUtil = LoggingUtil.getInstance();

	private static CommonQueueUtils _instance;

	// For lazy initialization
	public static synchronized CommonQueueUtils getInstance() {
		if (_instance == null) {
			_instance = new CommonQueueUtils();
		}
		return _instance;
	}

	// Constructor
	private CommonQueueUtils() {
		// loggingUtil.getLogger().debug("Constructor CommonQueueUtils");
	}

	/*
	 * Se quitan tildes
	 */

	// Set Hour =23 Minutes =59 and seconds=59 of inputDate
	public Date addHoursSeconds(Date inputDate) throws LoadDataException {
		Calendar cal = null;
		Locale locale = null;
		locale = new Locale("es", "CL");
		cal = Calendar.getInstance(locale);
		cal.setTime(inputDate);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		return cal.getTime();
	}

	// Agrega 0 al inicio del number
	public String addZeros(String number, int finallength) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < finallength - number.length(); i++)
			sb.append("0");
		return sb.toString().concat(number);
	}

	// El metodo elimina todos los 0 antes del sku
	public String cutZeros(String number) {
		int i = 0;
		boolean isZero = true;
		number = number.trim();
		int len = number.length();
		// Elimina los zeros hasta llega un numero diferente de zero
		while ((i < len) && isZero) {
			if (number.startsWith("0"))
				number = number.substring(1, number.length());
			else
				isZero = false;
			i++;
		}
		return number;
	}

	public void datoObligatorio(Double var, String msg) throws LoadDataException {
		if (var == null || "".equals(var)) {
			throw new LoadDataException(msg);
		}
	}
	
	public void datoObligatorio(BigDecimal var, String msg) throws LoadDataException {
		if (var == null || "".equals(var)) {
			throw new LoadDataException(msg);
		}
	}

	public void datoObligatorio(Integer var, String msg) throws LoadDataException {
		if (var == null || var.intValue() <= 0) {
			throw new LoadDataException(msg);
		}
	}
	
	public void datoObligatorio(Long var, String msg) throws LoadDataException {
		if (var == null || var.longValue() <= 0) {
			throw new LoadDataException(msg);
		}
	}

	public void datoObligatorio(String var, String msg) throws LoadDataException {
		if (var == null || "".equals(var.trim())) {
			throw new LoadDataException(msg);
		}
	}

	public void error(String msg) throws LoadDataException {
		if (msg != null && !"".equals(msg))
			throw new LoadDataException(msg);
	}

	public double floatTodouble(float floatValue) {
		return Double.parseDouble(Float.toString(floatValue));
	}

	public Date getDate(String date) throws ParseException {

		try {
			if (date.length() != 8) {
				throw new ParseException("", 0);
			}

			Locale loc = new Locale("es", "CL");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", loc);
			Date fecha = null;

			fecha = sdf.parse(date);
			return (fecha);
		} catch (ParseException e) {
			// loggingUtil.getLogger().error("Error en formato de fecha :" + date);
			throw e;
		}
	}
	
	public LocalDate getLocalDate(String date, String datePattern) throws DateTimeParseException {
		LocalDate fecha;

		try {
			if (date.length() != datePattern.length()) {
				throw new DateTimeParseException("Tamaño incorrecto", date, 0);
			}

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);	
			fecha = LocalDate.parse(date, dateFormatter);
					
		} catch (DateTimeParseException e) {
			throw e;
		}
		
		return (fecha);
	}

	public Date getDateHour(String date) throws ParseException {

		try {
			if (date.length() != 12)
				throw new ParseException("", 0);

			Locale loc = new Locale("es", "CL");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss", loc);
			Date fecha = null;

			fecha = sdf.parse(date);
			return (fecha);
		} catch (ParseException e) {
			// loggingUtil.getLogger().error("Error en formato de fecha :" + date);
			throw e;
		}
	}	
	
	public Date getDate(String date, String format) throws ParseException {

		try {
			if (date.length() != format.length()) {
				throw new ParseException("", 0);
			}
			
			Locale loc = new Locale("es", "CL");
			SimpleDateFormat sdf = new SimpleDateFormat(format, loc);
			Date fecha = null;

			fecha = sdf.parse(date);
			return (fecha);
		} catch (ParseException e) {
			// loggingUtil.getLogger().error("Error en formato de fecha :" + date);
			throw e;
		}
	}

	public Date getHour(String date) {

		try {
			if (date.length() != 5)
				throw new ParseException("", 0);

			Locale loc = new Locale("es", "CL");
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", loc);
			Date fecha = null;

			fecha = sdf.parse(date);
			return (fecha);
		} catch (ParseException e) {
			// loggingUtil.getLogger().error("Error en formato de hora :" + date);
			return null;
		}
	}

	public String getShortMessage(String astring) {
		if (astring == null || "".equals(astring))
			return "";
		else if (astring.length() > 70)
			return astring.substring(0, 70);
		else
			return new String(astring);
	}

	public String jdaTransformRut(String jdaRut) {
		int index = jdaRut.indexOf("-");
		if (index > -1)
			jdaRut = jdaRut.substring(0, index);
		return jdaRut;
	}

	public String stringToNumber(String number) {
		number = number.trim();
		if (number.startsWith("."))
			number = "0" + number;
		return number;
	}

	// El metodo elimina todos los 0 antes del sku
	public String transformSKU(String orgSku) {
		int i = 0;
		boolean isZero = true;
		int len = orgSku.length();
		// Elimina los zeros hasta llega un numero diferente de zero
		while ((i < len) && isZero) {
			if (orgSku.startsWith("0"))
				orgSku = orgSku.substring(1, orgSku.length());
			else
				isZero = false;
			i++;
		}
		return orgSku;
	}

	public String transformString(String checkDesc) {
		if (checkDesc == null || checkDesc == "" || checkDesc == " ")
			checkDesc = "0";
		else {
			int ind = checkDesc.indexOf(",");
			if (ind > 0)
				checkDesc = checkDesc.substring(0, ind) + "." + checkDesc.substring(ind + 1, checkDesc.length());
		}

		return checkDesc;
	}

	public String trimZeros(String inputString) throws OperationFailedException {
		int i = 0;
		boolean check = false;
		int leng = inputString.length();
		while (i < leng && (!check)) {
			if (inputString.startsWith("0"))
				inputString = inputString.substring(1, inputString.length());
			else
				check = true;
			i++;
		}
		if (inputString.compareToIgnoreCase("") == 0)
			throw new OperationFailedException("inputString no v�lido");
		return inputString;
	}

	public void validaLargo(int var, int valMax, String msg) throws LoadDataException {
		int digits = 0;
		while (var > 0) {
			var /= 10;
			digits++;
			if (digits > valMax)
				throw new LoadDataException(msg);
		}
	}

	public void validaLargo(String var, int valMax, String msg) throws LoadDataException {
		if (var.length() > valMax)
			throw new LoadDataException(msg);
	}

	public String validateSpecialCharacter(String message) {
		message = message.toLowerCase();
		StringBuffer finalMessage = new StringBuffer();
		HashMap<String, String> specialCharset = new HashMap<String, String>();
		specialCharset.put("�", "a");
		specialCharset.put("�", "e");
		specialCharset.put("�", "i");
		specialCharset.put("�", "o");
		specialCharset.put("�", "u");
		for (int i = 0; i < message.length(); i++) {
			if (!specialCharset.containsKey(String.valueOf(message.charAt(i))))
				finalMessage.append(message.charAt(i));
			else
				finalMessage.append(specialCharset.get(String.valueOf(message.charAt(i))));
		}
		return finalMessage.toString();
	}
}
