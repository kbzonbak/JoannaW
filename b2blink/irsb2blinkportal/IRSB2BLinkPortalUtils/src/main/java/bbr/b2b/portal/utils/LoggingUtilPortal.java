package bbr.b2b.portal.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author MyEclipse Persistence Tools
 */
public class LoggingUtilPortal {

	private Logger logger = null;
	
	private  Logger loggerUserAccess = null;

	private  Logger loggerUserLog = null;
	
	private Logger loggerUserEvent		= null;
	
	public Logger getLogger() {
		logger = Logger.getLogger("FILE");
		logger.setLevel(Level.ALL);
		return logger;
	}

	public Logger getLoggerUserAccess() {
		loggerUserAccess = Logger.getLogger("USER_ACCESS");
		loggerUserAccess.setLevel(Level.ALL);
		return loggerUserAccess;
	}
	public Logger getLoggerUserLog() {
		loggerUserLog = Logger.getLogger("USER_LOG");
		loggerUserLog.setLevel(Level.ALL);
		return loggerUserLog;
	}
	
	public Logger getLoggerUserEvent() {
		loggerUserEvent = Logger.getLogger("USER_EVENT");
		loggerUserEvent.setLevel(Level.ALL);
		return loggerUserEvent;
	}

}
