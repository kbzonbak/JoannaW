////////////////////////////////////////////////////////////////////////
//
// SplitAtDelimiterLineBasedMultiple.java
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

public class SplitAtDelimiterLineStartsWith extends SplitAtDelimiter {
	public SplitAtDelimiterLineStartsWith(String delimiter) {
		super(delimiter, false);
	}
	
	public Range split(Range range) {
		SplitLines splitAtFirstLine = new SplitLines(1);
		boolean firstLine = true;
		Range result = new Range(range);
		while (true)
		{
			Range line = splitAtFirstLine.split(range);
			if (!line.isValid())
			{
				result.end = line.start;
				break;
			}
			if (line.toString().indexOf(delimiter) == 0)
			{
				if (!firstLine)
				{
					result.end = line.start;
					break;
				}
			}
			firstLine = false;
		}
		range.start = result.end;
		return result;
	}
	
	public void appendDelimiter(Appender output) {}
}
