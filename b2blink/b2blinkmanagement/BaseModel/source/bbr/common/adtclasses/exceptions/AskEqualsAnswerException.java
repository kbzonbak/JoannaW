package bbr.common.adtclasses.exceptions;

// Para verificar que el usuario no exista
public class AskEqualsAnswerException extends ServiceException {

	/**
	 * Creates a BBRException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param friendlymessage
	 *            the friendly detail message.
	 */
	public AskEqualsAnswerException(String friendlymessage, String message) {
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
	public AskEqualsAnswerException(String friendlymessage, Exception e) {
		super(friendlymessage, e);
	}

	/**
	 * Creates a BBRException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public AskEqualsAnswerException(String message) {
		super(message);
	}

}
