package bbr.b2b.regional.logistic.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

	private static StringUtils _instance;

	// For lazy initialization
	public static synchronized StringUtils getInstance() {
		if (_instance == null) {
			_instance = new StringUtils();
		}
		return _instance;
	}

	// Constructor
	private StringUtils() {
	}

	public int lastIndexOf(String str, String searchStr) {
		int index = -1;
		index = org.apache.commons.lang.StringUtils.lastIndexOf(str, searchStr);
		return index;
	}
	
	public String insertInto(String str, String insertStr, int index) throws IndexOutOfBoundsException {
		String newStr = str.substring(0, index) + insertStr + str.substring(index);
				
		return newStr;
	}
	
	public Date StrToDateIgnoreTime(String datestr) throws ParseException {
		// DVI Ignorar la zona horaria
		 String DATETIME_FORMAT_NOTIME = "yyyy-MM-dd";
		 SimpleDateFormat sdfNOT = new SimpleDateFormat(DATETIME_FORMAT_NOTIME);
		
		try {
			datestr = datestr.substring(0, DATETIME_FORMAT_NOTIME.length());			
		} catch (StringIndexOutOfBoundsException e) {
			throw new ParseException("El largo del string no es correcto", datestr.length());
		}
		Date value = sdfNOT.parse(datestr);
		return value;
	}
	
	
}
