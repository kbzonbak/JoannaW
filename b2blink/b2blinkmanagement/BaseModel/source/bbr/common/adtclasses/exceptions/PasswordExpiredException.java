
package bbr.common.adtclasses.exceptions;


public class PasswordExpiredException extends BBRException {

	/**
	 * Creates a OperationFailedException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public PasswordExpiredException(String message) {
		super(message);
	}

	/**
	 * Creates a BBRException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param friendlymessage
	 *            the friendly detail message.
	 */
	public PasswordExpiredException(String friendlymessage, String message) {
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
	public PasswordExpiredException(String friendlymessage, Exception e) {
		super(friendlymessage, e);
	}
}