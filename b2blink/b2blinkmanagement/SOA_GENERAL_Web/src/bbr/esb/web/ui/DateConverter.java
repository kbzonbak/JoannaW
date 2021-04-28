package bbr.esb.web.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class DateConverter implements Converter<String, Date> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1577597475286111086L;

	private static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY HH:mm");

	@Override
	public Date convertToModel(String value, Class<? extends Date> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
		try {
			return format.parse(value);
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
	}

	@Override
	public String convertToPresentation(Date value, Class<? extends String> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
		return value != null ? format.format(value) : null;
	}

	@Override
	public Class<Date> getModelType() {
		return Date.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}
}
