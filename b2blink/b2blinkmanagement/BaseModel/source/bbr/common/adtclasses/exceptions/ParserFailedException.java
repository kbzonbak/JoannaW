package bbr.common.adtclasses.exceptions;

public class ParserFailedException extends ServiceException {

	/**
	 * Creates a OperationFailedException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public ParserFailedException(String message) {
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
	public ParserFailedException(String friendlymessage, String message) {
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
	public ParserFailedException(String friendlymessage, Exception e) {
		super(friendlymessage, e);
	}

}
