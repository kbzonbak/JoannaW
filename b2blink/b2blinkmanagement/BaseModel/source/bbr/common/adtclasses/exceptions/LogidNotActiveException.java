package bbr.common.adtclasses.exceptions;

public class LogidNotActiveException extends BBRException {

	/**
	 * Creates a BBRException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param friendlymessage
	 *            the friendly detail message.
	 */
	public LogidNotActiveException(String friendlymessage, String message) {
		super(friendlymessage, message);
	}

	/**
	 * Creates a BBRException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param friendlymessage
	 *            the friendly detail message.
	 */
	public LogidNotActiveException(String friendlymessage, Exception e) {
		super(friendlymessage, e);
	}

	/**
	 * Creates a BBRException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public LogidNotActiveException(String message) {
		super(message);
	}

}
