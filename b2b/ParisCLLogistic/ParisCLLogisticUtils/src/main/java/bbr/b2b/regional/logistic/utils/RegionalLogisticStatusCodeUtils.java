package bbr.b2b.regional.logistic.utils;

import bbr.b2b.common.util.StatusCodeUtils;


public class RegionalLogisticStatusCodeUtils extends StatusCodeUtils {

	private static RegionalLogisticStatusCodeUtils instance;

	public static synchronized StatusCodeUtils getInstance() {
		if (instance == null) {
			instance = new RegionalLogisticStatusCodeUtils();
		}
		return instance;
	}

	private RegionalLogisticStatusCodeUtils() {
		super("bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils");
	}

}
