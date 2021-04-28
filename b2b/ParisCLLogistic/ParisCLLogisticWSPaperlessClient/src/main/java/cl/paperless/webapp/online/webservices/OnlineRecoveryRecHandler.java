package cl.paperless.webapp.online.webservices;

import java.io.ByteArrayOutputStream;

import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.handler.AbstractHandler;
import org.codehaus.xfire.util.DOMUtils;
import org.codehaus.xfire.util.dom.DOMInHandler;
import org.w3c.dom.Document;

import bbr.b2b.common.util.LoggingUtil;

/**
 * Logs a message to a commons logging Log at the INFO level. This requires DOM
 * to be activated for the particular flow. This can be done with the DOMInHandler
 * or DOMOutHandler.
 * 
 * @see org.codehaus.xfire.util.dom.DOMOutHandler
 * @see org.codehaus.xfire.util.dom.DOMInHandler
 * @author Dan Diephouse
 */
public class OnlineRecoveryRecHandler extends AbstractHandler
{
	//custom logger
	
	private static LoggingUtil log = new LoggingUtil("Paperlesslog");
    
    public void invoke(MessageContext context) throws Exception
    {
        Document doc = (Document) context.getCurrentMessage().getProperty(DOMInHandler.DOM_MESSAGE);
        
        if (doc.getDocumentElement() == null)
        {
            log.error("DOM Document was not found so the message could not be logged.");
            return;
        }
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DOMUtils.writeXml(doc.getDocumentElement(), bos);
        log.info(bos.toString());
    }
    
}