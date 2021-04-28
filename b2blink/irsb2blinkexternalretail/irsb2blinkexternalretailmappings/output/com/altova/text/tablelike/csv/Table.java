////////////////////////////////////////////////////////////////////////
//
// Table.java
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

package com.altova.text.tablelike.csv;

import com.altova.text.tablelike.ISerializer;

public class Table extends com.altova.text.tablelike.Table
{

	public Table(com.altova.typeinfo.TypeInfo tableType) 
	{
		super(false);
		this.tableType = tableType;
		init();
	}

	public Format getFormat() { return ((Serializer) m_Serializer).getFormat(); }

	protected ISerializer createSerializer()
	{
		return new Serializer(this);
	}

	protected void initHeader(com.altova.text.tablelike.Header header)
	{
		for( int iMember = 0 ; iMember < tableType.getMembers().length ; ++iMember )
		{
			com.altova.typeinfo.MemberInfo member = tableType.getMembers()[iMember];
			header.add(
				new com.altova.text.tablelike.ColumnSpecification(member.getLocalName()));
		}
	}
}