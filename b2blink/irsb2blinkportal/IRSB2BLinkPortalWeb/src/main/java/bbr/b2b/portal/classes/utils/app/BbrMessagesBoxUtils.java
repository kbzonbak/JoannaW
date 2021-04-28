package bbr.b2b.portal.classes.utils.app;

import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrUI;

public abstract class BbrMessagesBoxUtils
{

	/**
	 * Muestra un mensaje de confirmación.
	 *
	 * @param caption
	 *            el titulo del mensaje
	 * @param message
	 *            el mensaje
	 * @param okButtonAction
	 *            accion del botón "Aceptar" antes de cerrarse
	 */
	public static void showConfirmationMessage(BbrUI bbrUI, String caption, String message, Runnable okButtonAction)
	{
		BbrMessagesBoxUtils.showConfirmationMessage(bbrUI, caption, message, null, okButtonAction, null);
	}

	/**
	 * Muestra un mensaje de confirmación.
	 *
	 * @param caption
	 *            el titulo del mensaje
	 * @param message
	 *            el mensaje
	 * @param okButtonAction
	 *            accion del botón "Aceptar" antes de cerrarse
	 * @param cancelButtonAction
	 *            accion del botón "Cancelar" antes de cerrarse
	 */
	public static void showConfirmationMessage(BbrUI bbrUI, String caption, String message, Runnable okButtonAction, Runnable cancelButtonAction)
	{
		BbrMessagesBoxUtils.showConfirmationMessage(bbrUI, caption, message, null, okButtonAction, cancelButtonAction);
	}

	/**
	 * Muestra un mensaje de confirmación.
	 *
	 * @param caption
	 *            el titulo del mensaje
	 * @param message
	 *            el mensaje
	 * @param width
	 *            tamaño de ancho de botones
	 * @param okButtonAction
	 *            accion del botón "Aceptar" antes de cerrarse
	 * @param cancelButtonAction
	 *            accion del botón "Cancelar" antes de cerrarse
	 */
	public static void showConfirmationMessage(BbrUI bbrUI, String caption, String message, String width, Runnable okButtonAction, Runnable cancelButtonAction)
	{
		BbrMessageBox.createQuestion(bbrUI)
				.withCaption(caption)
				.withHtmlMessage(message)
				.withWidthForAllButtons(width)
				.withOkButton(okButtonAction)
				.withCancelButton(cancelButtonAction)
				.open();
	}

	/**
	 * Muestra un mensaje de confirmación.
	 *
	 * @param caption
	 *            el titulo del mensaje
	 * @param message
	 *            el mensaje
	 * @param okButtonAction
	 *            accion del botón "Aceptar" antes de cerrarse
	 */
	public static void showConfirmationWarningMessage(BbrUI bbrUI, String caption, String message, Runnable okButtonAction)
	{
		BbrMessagesBoxUtils.showConfirmationWarningMessage(bbrUI, caption, message, null, okButtonAction, null);
	}

	/**
	 * Muestra un mensaje de confirmación.
	 *
	 * @param caption
	 *            el titulo del mensaje
	 * @param message
	 *            el mensaje
	 * @param okButtonAction
	 *            accion del botón "Aceptar" antes de cerrarse
	 * @param cancelButtonAction
	 *            accion del botón "Cancelar" antes de cerrarse
	 */
	public static void showConfirmationWarningMessage(BbrUI bbrUI, String caption, String message, Runnable okButtonAction, Runnable cancelButtonAction)
	{
		BbrMessagesBoxUtils.showConfirmationWarningMessage(bbrUI, caption, message, null, okButtonAction, cancelButtonAction);
	}

	/**
	 * Muestra un mensaje de confirmación.
	 *
	 * @param caption
	 *            el titulo del mensaje
	 * @param message
	 *            el mensaje
	 * @param width
	 *            tamaño de ancho de botones
	 * @param okButtonAction
	 *            accion del botón "Aceptar" antes de cerrarse
	 * @param cancelButtonAction
	 *            accion del botón "Cancelar" antes de cerrarse
	 */
	public static void showConfirmationWarningMessage(BbrUI bbrUI, String caption, String message, String width, Runnable okButtonAction, Runnable cancelButtonAction)
	{
		BbrMessageBox.createWarning(bbrUI)
				.withCaption(caption)
				.withHtmlMessage(message)
				.withWidthForAllButtons(width)
				.withOkButton(okButtonAction)
				.withCancelButton(cancelButtonAction)
				.open();
	}

	/**
	 * Muestra un mensaje de confirmación.
	 *
	 * @param caption
	 *            el titulo del mensaje
	 * @param message
	 *            el mensaje
	 * @param width
	 *            ancho de la ventana
	 * @param yesButtonAction
	 *            accion del botón "Si" antes de cerrarse
	 * @param noButtonAction
	 *            accion del botón "No" antes de cerrarse
	 */
	public static void showYesNoQuestionMessage(BbrUI bbrUI, String caption, String message, Runnable yesButtonAction)
	{
		BbrMessagesBoxUtils.showYesNoQuestionMessage(bbrUI, caption, message, null, yesButtonAction, null);
	}

	public static void showYesNoQuestionMessage(BbrUI bbrUI, String caption, String message, Runnable yesButtonAction, Runnable noButtonAction)
	{
		BbrMessagesBoxUtils.showYesNoQuestionMessage(bbrUI, caption, message, null, yesButtonAction, noButtonAction);
	}

	public static void showYesNoQuestionMessage(BbrUI bbrUI, String caption, String message, String width, Runnable yesButtonAction, Runnable noButtonAction)
	{
		BbrMessageBox.createQuestion(bbrUI)
				.withCaption(caption)
				.withHtmlMessage(message)
				.withWidthForAllButtons(width)
				.withYesButton(yesButtonAction)
				.withNoButton(noButtonAction)
				.withWidth("400px")
				.open();
	}

}
