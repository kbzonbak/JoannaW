////////////////////////////////////////////////////////////////////////
//
// SplitAtDelimiterLineBased.java
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

package com.altova.text.flex;

public class SplitAtDelimiterLineBased extends SplitAtDelimiter {
	public SplitAtDelimiterLineBased(String delimiter, boolean reverse) {
		super(delimiter, reverse);
	}
	
	public Range split(Range range) {
		if (delimiter.length() == 0) {
			Range result = new Range(range);
			range.start = range.end;
			return result;
		}
		
		Range result = super.split(range);
		
		while (result.isValid() && result.charAt(result.end-1) != CR && result.charAt(result.end-1) != LF)
			--result.end;
		range.start = result.end;
		return result;
	}
	
	public void appendDelimiter(Appender output) {}
}
