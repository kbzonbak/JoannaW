/**
 * DOMDocumentAsMFNodeAdapter.java
 *
 * This file was generated by MapForce 2011r3sp1.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the MapForce Documentation for further details.
 * http://www.altova.com/mapforce
 */
 
package com.altova.xml;

import com.altova.mapforce.IMFDocumentNode;

public class DOMDocumentAsMFNodeAdapter extends DOMNodeAsMFNodeAdapter implements IMFDocumentNode 
{
	private String filename;
	public DOMDocumentAsMFNodeAdapter(org.w3c.dom.Node document, String filename)
	{
		super(document);
		this.filename = filename;
	}
	public String getDocumentUri() 
	{
		return filename;
	}
}