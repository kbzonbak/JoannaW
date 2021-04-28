package bbr.b2b.portal.constants.stockpool;

import java.io.File;

import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;

public class StockPoolConstants
{

	private static StockPoolConstants _instance;

	public static synchronized StockPoolConstants getInstance()
	{
		if (_instance == null)
		{
			_instance = new StockPoolConstants();
		}

		return _instance;
	}

	private String				STOCKPOOL_WEBSERVICE_ENDPOINT_PATH		= new String(B2BSystemPropertiesUtil.getProperty("stockpool_webservice_endpoint_path"));
	
	private Boolean				stockPoolWebServiceEndpointPathChecked	= false;

	

	public String getStockPoolWebServiceEndpointPath()
	{
		if ((this.STOCKPOOL_WEBSERVICE_ENDPOINT_PATH != null) && (this.STOCKPOOL_WEBSERVICE_ENDPOINT_PATH.length() > 0) && !this.stockPoolWebServiceEndpointPathChecked)
		{
			this.stockPoolWebServiceEndpointPathChecked = true;

			File dir = new File(this.STOCKPOOL_WEBSERVICE_ENDPOINT_PATH);
			dir.mkdirs();
		}

		return this.STOCKPOOL_WEBSERVICE_ENDPOINT_PATH;
	}

}
