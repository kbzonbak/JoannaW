package bbr.b2b.logistic.utils;

import bbr.b2b.common.util.StatusCodeUtils;


public class LogisticStatusCodeUtils extends StatusCodeUtils {

	private static LogisticStatusCodeUtils instance;

	public static synchronized StatusCodeUtils getInstance() {
		if (instance == null) {
			instance = new LogisticStatusCodeUtils();
		}
		return instance;
	}

	private LogisticStatusCodeUtils() {
		super("bbr.b2b.logistic.utils.LogisticStatusCodeUtils");
	}

}
