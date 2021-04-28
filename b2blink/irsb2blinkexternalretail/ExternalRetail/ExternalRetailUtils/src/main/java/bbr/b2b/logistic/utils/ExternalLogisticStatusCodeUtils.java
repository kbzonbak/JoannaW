package bbr.b2b.logistic.utils;

import bbr.b2b.common.util.StatusCodeUtils;


public class ExternalLogisticStatusCodeUtils extends StatusCodeUtils {

	private static ExternalLogisticStatusCodeUtils instance;

	public static synchronized StatusCodeUtils getInstance() {
		if (instance == null) {
			instance = new ExternalLogisticStatusCodeUtils();
		}
		return instance;
	}

	private ExternalLogisticStatusCodeUtils() {
		super("bbr.b2b.logistic.utils.ExternalLogisticStatusCodeUtils");
	}

}
