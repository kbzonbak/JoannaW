package bbr.b2b.portal.classes.functions;

import java.util.function.Function;

import bbr.b2b.fep.common.data.classes.ErrorInfoDTO;
import cl.bbr.core.classes.errors.BbrError;

public class ConverterFunctionsUtils
{
	public static Function<ErrorInfoDTO, BbrError> errorConverterFunction = new Function<ErrorInfoDTO, BbrError>()
	{
		@Override
		public BbrError apply(ErrorInfoDTO error)
		{
			BbrError result = new BbrError(error.getStatuscode(), error.getStatusmessage());
			return result;
		}
	};
}
