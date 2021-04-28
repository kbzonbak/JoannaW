
package bbr.b2b.portal.classes.utils.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.vaadin.ui.renderers.TextRenderer;

import elemental.json.JsonValue;

public class TimeRenderer extends TextRenderer
{
	private static final long serialVersionUID = 1731758854672889315L;

	@Override
	public JsonValue encode(Object value)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		String result = (value != null) ? dtf.format((LocalDateTime) value) : "";
		return super.encode(result);
	}

	@Override
	public String getNullRepresentation()
	{
		return "";
	}
}
