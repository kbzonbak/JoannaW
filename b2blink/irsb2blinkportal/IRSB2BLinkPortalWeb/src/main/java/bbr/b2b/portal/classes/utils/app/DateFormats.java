package bbr.b2b.portal.classes.utils.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormats
{
	public static DateTimeFormatter getDTFGrid()
	{
		return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	}

	public static DateTimeFormatter getDF()
	{
		return DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}

	public static DateTimeFormatter getDFWs()
	{
		return DateTimeFormatter.ofPattern("yyyy-MM-dd");
	}

	public static String formatDate(LocalDateTime date)
	{
		return date != null ? getDF().format(date) : "";
	}

	public static String formatDateWs(LocalDateTime date)
	{
		return date != null ? getDFWs().format(date) : "";
	}

}
