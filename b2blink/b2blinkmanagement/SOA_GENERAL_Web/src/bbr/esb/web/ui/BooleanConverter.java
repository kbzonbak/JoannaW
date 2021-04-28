package bbr.esb.web.ui;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class BooleanConverter implements Converter<String, Boolean> {
	@Override
	public Boolean convertToModel(String value, Class<? extends Boolean> targetType, Locale locale) throws ConversionException {
		return null;
	}

	@Override
	public String convertToPresentation(Boolean value, Class<? extends String> targetType, Locale locale) throws ConversionException {
		if (value != null)
			return "<input type='checkbox' disabled='disabled'" + (value.booleanValue() ? " checked" : "") + " />";
		else
			return null;
	}

	@Override
	public Class<Boolean> getModelType() {
		return Boolean.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}
}
