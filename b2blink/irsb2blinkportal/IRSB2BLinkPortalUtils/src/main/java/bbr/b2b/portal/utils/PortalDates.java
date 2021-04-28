package bbr.b2b.portal.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PortalDates
{

	public static final String TIME_DEFAULT = " 00:00:00 00:00";
	public static final String HOURS_MINUTES_DEFAULT = " 00:00";

	public PortalDates()
	{

	}

	public static String getDateWithoutTime(String date, boolean isOrganized)
	{
		String result = null;
		

		if (date != null && date.length() > 10)
		{
			date = date.contains("T") ? date.replace("T", " ") : date;
			result = isOrganized ? date.substring(0, 10) : getDateOrganized(date.substring(0, 10));
		}

		return result;
	}

	public static String getDateWithDefaultTime(String date, boolean isOrganized)
	{
		String result = null;

		if (date != null && date.length() > 10)
		{
			result = isOrganized ? date.substring(0, 10) + TIME_DEFAULT : getDateOrganized(date.substring(0, 10)) + TIME_DEFAULT;
		}

		return result;
	}

	public static Long getTimeStamp(LocalDateTime date)
	{
		Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
		long timeInMillis = instant.toEpochMilli();
		return timeInMillis;
	}

	public static String getDateOrganized(String date)
	{
		String result = null;

		String[] organized = date.split("-");

		result = organized[2] + "-" + organized[1] + "-" + organized[0];

		return result;
	}

	// Este metodo retorna la fecha en formato dd-mm-yyyy hh:mm
	// El booleando isOrganized determina si la fecha se ordenara dd-mm-yyyy si
	// es false o si es true la dejara en el orden que venga.
	// Se le debe pasar la fecha en formato largo ej: yyyy-mm-dd hh:mm:ss ms:ms

	public static String getDateWithHoursAndMinutes(String date, boolean isOrganized)
	{
		String result = "";
		if (date != null)
		{
			String shortTime = date.length() > 10 ? date.substring(10, 16) : HOURS_MINUTES_DEFAULT;
			result = isOrganized ? date.substring(0, 10) + shortTime : getDateOrganized(date.substring(0, 10)) + shortTime;
		}
		return result;
	}

	public static LocalDateTime getLocalDateTimeformat(String date)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		date = date.substring(0, 16);
		return LocalDateTime.parse(date, formatter);
	}
	
	public static LocalDateTime getLocalDateTimeformatT(String date)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		date = date.substring(0, 19);
		if(date.indexOf("T")>0)
		{
			date = date.contains("T") ? date.replace("T", " ") : date;
		}
		
		return LocalDateTime.parse(date, formatter);
	}
	
	public static LocalDateTime getLocalDateTimeformatYYYYMMDD(String date)
	{
		String fullDate = date+" 00:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		date = fullDate.substring(0, 16);
		return LocalDateTime.parse(date, formatter);
	}

	public String MonthOfYear(long num)
	{

		String month = "";

		if (num >= 1)
		{

			if (num == 1)
			{
				month = "Enero";

			}
			else if (num == 2)
			{
				month = "Febrero";

			}
			else if (num == 3)
			{
				month = "Marzo";

			}
			else if (num == 4)
			{
				month = "Abril";

			}
			else if (num == 5)
			{
				month = "Mayo";

			}
			else if (num == 6)
			{
				month = "Junio";

			}
			else if (num == 7)
			{
				month = "Julio";

			}
			else if (num == 8)
			{
				month = "Agosto";

			}
			else if (num == 9)
			{
				month = "Septiembre";

			}
			else if (num == 10)
			{
				month = "Octubre";

			}
			else if (num == 11)
			{
				month = "Noviembre";

			}
			else if (num == 12)
			{
				month = "Diciembre";

			}

		}
		return month;
	}

	// formato de fecha dd/mm/yyyy mas hora hh:mm
	// se pasa parametro strinf y se agrega false para que salga con formato indicado
	public static String getDateWithTime(String date, boolean isOrganized)
	{
		String result = null;
		String time = date;
		String aux = time.substring(11, 16);

		if (date != null && date.length() > 10)
		{
			result = isOrganized ? date.substring(0, 10) : getDateOrganized(date.substring(0, 10)) + " " + aux;
		}

		return result;
	}
	
	// formato de fecha ordenado dd/mm/yyyy 
	// se pasa parametro strinf y se agrega false para que salga con formato indicado
	public static String getOnlyDate(String date)
	{
		String result = null;
		String time = date;
		String year =time.substring(0, 4);
		String month = time.substring(5, 7);
		String days = time.substring(8, 10);

		 result = days+"/"+month+"/"+year;
		 
		return result;
	}

	// hora formato oracle mas hora y zona horaria yyyy-mm-dd 00:00:00 00:00
	public static String getFormatDateTime(String Date)
	{
		String result = null;
		String time = "00:00:00 00:00";

		String aux = Date.substring(0, 10);

		result = aux + " " + time;
		return result;
	}
	
	// hora formato yyyy-mm-dd
	public static String getFormatDateDDMMYY2YYMMDD(String date)
	{
		String result = null;

		String aux = date.substring(0, 10);
		String year =aux.substring(6, 10);
		String month = aux.substring(3, 5);
		String days = aux.substring(0, 2);

		result = year+"-"+month+"-"+days;
		return result;
	}

}
