package bbr.b2b.portal.utils;

import bbr.b2b.common.util.StatusCodeUtils;


public class PortalStatusCodeUtils extends StatusCodeUtils {

	private static PortalStatusCodeUtils instance;

	public static synchronized StatusCodeUtils getInstance() {
		if (instance == null) {
			instance = new PortalStatusCodeUtils();
		}
		return instance;
	}

	private PortalStatusCodeUtils() {
		super("bbr.b2b.portal.utils.PortalStatusCodeUtils");
	}

}
