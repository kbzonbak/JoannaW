package bbr.b2b.portal.classes.utils.fep;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.vaadin.shared.ui.datefield.DateTimeResolution;

import bbr.b2b.fep.common.data.classes.AttributeValueDTO;
import bbr.b2b.portal.constants.FEPConstants;
import cl.bbr.core.classes.arrays.BbrArraysUtils;
import cl.bbr.core.classes.sets.BbrSetsUtils;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.components.basics.BbrDateTimeField;
import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;

public class FEPAttributesComponentsUtils
{
	public static boolean isBbrTextFieldAttributeType(String attributeType)
	{
		boolean result = false;

		result = (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_STRING)
				|| attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_INTEGER)
				|| attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_FLOAT));

		return result;
	}

	public static String getTextRestrictByAttributeType(String attributeType)
	{
		String result = "";

		if (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_STRING))
		{
			result = RestrictRange.ALL_LETTERS_SPACE + "," +RestrictRange.NUMBERS;
		}
		else if (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_INTEGER))
		{
			result = RestrictRange.NUMBERS;
		}
		else if (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_FLOAT))
		{
			result = RestrictRange.DOUBLE;
		}

		return result;
	}

	public static boolean isBbrComboBoxAttributeType(String attributeType)
	{
		boolean result = false;

		result = (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_LIST)
				|| attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_CODEDLIST) || attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_HIERARCHI_RETAIL) 
				|| attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_MUHIERARCHI_RETAIL) ||  attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_TRADELIST)
				) ;

		return result;
	}

	public static boolean isComboBoxMultiselectAttributeType(String attributeType)
	{
		boolean result = false;

		result = (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_MU_LIST)
				|| attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_MUCODEDLIST));

		return result;
	}

	public static boolean isCheckBoxAttributeType(String attributeType)
	{
		boolean result = false;

		result = (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_BOOLEAN));

		return result;
	}

	public static boolean isBbrItemSelectFieldAttributeType(String attributeType)
	{
		boolean result = false;
		
		result = (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_IMAGE) || attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_FILE));
		
		return result;
	}
	
	public static boolean isBbrItemSelectFieldAttributeTypeRelatedProduct(String attributeType)
	{
		boolean result = false;
		
		result = attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_RELATEDPRODUCTLIST);
		
		return result;
	}

	public static boolean isBbrDateTimeFieldAttributeType(String attributeType)
	{

		boolean result = false;

		result = (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_DATE)
				|| attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_DATE_TIME)
				|| attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_TIME));

		return result;
	}

	public static BbrDateTimeField updateBbrDateTimeFieldByAttributeType(String attributeType, BbrDateTimeField component)
	{
		BbrDateTimeField result = component;

		if (result != null)
		{
			if (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_DATE))
			{
				result.setResolution(DateTimeResolution.DAY);
				result.setDateFormat(BbrDateUtils.SHORT_DATE_FORMAT);
			}
			else if (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_DATE_TIME))
			{
				result.setResolution(DateTimeResolution.SECOND);
				result.setDateFormat(BbrDateUtils.SHORT_DATE_TIME_FORMAT);
			}
			else if (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_TIME))
			{
				result.setResolution(DateTimeResolution.SECOND);
				result.setDateFormat(BbrDateUtils.SHORT_TIME_FORMAT);
				result.addStyleName("time-only");
			}
		}

		return result;
	}

	public static String getFormattedDateByAttributeType(String attributeType, LocalDateTime value)
	{
		String result = null;

		if (attributeType != null)
		{
			if (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_DATE))
			{
				result = BbrDateUtils.getInstance().toShortDate(value);
			}
			else if (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_DATE_TIME))
			{
				result = BbrDateUtils.getInstance().toShortDateTime(value);
			}
			else if (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_TIME))
			{
				result = BbrDateUtils.getInstance().toShortTime(value);
			}
		}

		return result;
	}

	public static LocalDateTime getDateByFormattedDateAttributeType(String attributeType, String value)
	{
		LocalDateTime result = null;

		if ((attributeType != null) && (value != null) && (value.length() > 0))
		{
			if (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_DATE))
			{
				result = LocalDateTime.of(LocalDate.parse(value, BbrDateUtils.getInstance().getFormatterShortDate()), LocalTime.now());
			}
			else if (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_DATE_TIME))
			{
				result = LocalDateTime.parse(value, BbrDateUtils.getInstance().getFormatterShortDateTime());
			}
			else if (attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_TIME))
			{
				result = LocalDateTime.of(LocalDate.now(), LocalTime.parse(value, BbrDateUtils.getInstance().getFormatterShortTime()));

				result = LocalDateTime.parse(value, BbrDateUtils.getInstance().getFormatterShortTime());
			}
		}

		return result;
	}

	public static String getMultiAttributeStringValue(Set<AttributeValueDTO> values)
	{
		String result = null;

		if (values != null && !values.isEmpty())
		{
			AttributeValueDTO[] attributes = BbrSetsUtils.getInstance().toArray(values, AttributeValueDTO.class); 
			
			result = attributes[0].getValue();

			if (attributes.length > 1)
			{
				for (int i = 1; i < attributes.length; i++)
				{
					result += FEPConstants.LIST_SEPARATOR + attributes[i].getValue();
				}
			}
		}
		return result;
	}

	public static AttributeValueDTO[] getAttributeFromStringValue(String value, AttributeValueDTO[] allAttributes)
	{
		AttributeValueDTO[] result = null;

		if (value != null && value.length() > 0 && allAttributes != null && allAttributes.length > 0)
		{
			String[] values = value.split(FEPConstants.LIST_SEPARATOR);

			if (values != null && values.length > 0)
			{
				List<AttributeValueDTO> lstAllAttributes = BbrArraysUtils.getInstance().ArrayToList(allAttributes, AttributeValueDTO.class);

				List<AttributeValueDTO> lstResult = new ArrayList<>();

				for (String strValue : values)
				{
					Optional<AttributeValueDTO> matchOptional = lstAllAttributes.stream().filter((attribute) -> attribute.getValue().equals(strValue)).findAny();
					if(matchOptional.isPresent())
					{
						lstResult.add(matchOptional.get());
					}
				}
				
				if(!lstResult.isEmpty())
				{
					result = BbrArraysUtils.getInstance().ListToArray(lstResult, AttributeValueDTO.class);
				}
			}
		}
		return result;
	}
}
