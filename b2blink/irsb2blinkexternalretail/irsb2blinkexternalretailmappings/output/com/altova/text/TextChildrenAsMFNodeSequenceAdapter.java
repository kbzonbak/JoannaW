////////////////////////////////////////////////////////////////////////
//
// TextChildrenAsMFNodeSequenceAdapter.java
//
// This file was generated by MapForce 2011r3sp1.
//
// YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
// OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
//
// Refer to the MapForce Documentation for further details.
// http://www.altova.com/mapforce
//
////////////////////////////////////////////////////////////////////////

package com.altova.text;

import com.altova.mapforce.IEnumerable;
import com.altova.mapforce.IEnumerator;

public class TextChildrenAsMFNodeSequenceAdapter implements IEnumerable 
{
	public static class Enumerator implements IEnumerator
	{
		int i = -1;
		ITextNodeList children;
		int pos = 0;
		
		Enumerator(ITextNode from)
		{
			this.children = from.getChildren();
		}
		
		public Object current()
		{
			if (i == -1) throw new UnsupportedOperationException("No current.");
				return new TextNodeAsMFNodeAdapter(children.getAt(i));
		}
		
		public int position() {return pos;}
		
		public boolean moveNext()
		{
			++i;
			if (i < children.size())
			{
				pos++;
				return true;
			}
			return false;
		}
		
		public void close() {}
	}
	
	ITextNode node;
	
	public TextChildrenAsMFNodeSequenceAdapter(ITextNode node)
	{
		this.node = node;
	}
	
	public IEnumerator enumerator() throws Exception {return new Enumerator(node);} 
}

