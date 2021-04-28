package bbr.common.adtclasses.exceptions;

public class MismatchPasswordException extends BBRException {

	/**
	 * Creates a BBRException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param friendlymessage
	 *            the friendly detail message.
	 */
	public MismatchPasswordException(String friendlymessage, String message) {
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
	public MismatchPasswordException(String friendlymessage, Exception e) {
		super(friendlymessage, e);
	}

	/**
	 * Creates a BBRException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public MismatchPasswordException(String message) {
		super(message);
	}

}
