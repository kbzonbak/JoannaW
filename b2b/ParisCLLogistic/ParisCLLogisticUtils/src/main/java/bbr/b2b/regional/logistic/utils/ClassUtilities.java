package bbr.b2b.regional.logistic.utils;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ClassUtilities {

	public static final long MILISPERDAY = 24 * 60 * 60 * 1000;

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

	public static Object[] SplitArray(Object[] source, Class sourceclass, int chunksize) {
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

	public static Object UnsplitArrays(Object[] source, Class sourceclass) {
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

	public static Date setFinalDay(Date inputDate) {
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

	public static String getDigitoVerificador(int rutAux) {
		int m = 0, s = 1;
		for (; rutAux != 0; rutAux /= 10) {
			s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
		}
		return String.valueOf((char) (s != 0 ? s + 47 : 75));
	}

	public static String trimZerosLeft(String input) {
		// Se eliminan ceros iniciales
		String result = "";
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) != '0') {
				result = input.substring(i);
				break;
			}
		}

		return result;
	}
		
	public static String theMonth(int month) {
	    String[] monthNames = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	    return monthNames[month];
	}
}
