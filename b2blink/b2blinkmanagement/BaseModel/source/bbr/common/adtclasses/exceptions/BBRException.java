package bbr.common.adtclasses.exceptions;

public class BBRException extends Exception {
	/**
	 * Creates a BBRException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param friendlymessage
	 *            the friendly detail message.
	 */
	public BBRException(String friendlymessage, String message) {
		super(message);
		this.friendlymessage = friendlymessage;
	}

	/**
	 * Creates a BBRException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param friendlymessage
	 *            the friendly detail message.
	 */
	public BBRException(String friendlymessage, Throwable e) {
		super(e.getMessage());
		this.friendlymessage = friendlymessage;
	}

	/**
	 * Creates a BBRException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public BBRException(String message) {
		super(message);
		this.friendlymessage = message;
	}

	private String friendlymessage = null;

	/**
	 * @return
	 */
	public String getFriendlymessage() {
		return friendlymessage;
	}

}
