
package bbr.common.adtclasses.exceptions;


public class LoadProcessException extends BBRException{

	/**
	 * Creates a BBRException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param friendlymessage
	 *            the friendly detail message.
	 */
	public LoadProcessException(String friendlymessage, String message) {
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
	public LoadProcessException(String friendlymessage, Exception e) {
		super(friendlymessage, e);
	}

	/**
	 * Creates a BBRException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public LoadProcessException(String message) {
		super(message);
	}


}
