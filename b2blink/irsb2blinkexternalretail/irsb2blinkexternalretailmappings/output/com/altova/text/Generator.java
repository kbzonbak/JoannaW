////////////////////////////////////////////////////////////////////////
//
// Generator.java
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

import java.io.FileWriter;
import java.io.Writer;

import com.altova.AltovaException;

public class Generator implements ITextNode
{
	private ITextNode		m_Current = null;
	private ITextNodeList	m_Children;

	public Generator() {
		m_Children = new com.altova.text.TextNodeList(this);
	}
	
	public ITextNode getRoot() { return this; }
	
	public ITextNode getParent() { return null; }

	public void setParent(ITextNode rhs) {  }
	
	public ITextNodeList getChildren() { return m_Children; }

	public String getName() { return null; }

	public void setName(String name) {  }

	public String getValue() { return null; }

	public void setValue(String value) {  }

	public boolean isNull() { return false; }

	public byte getNodeClass() { return 0; }

	public void setNodeClass(byte rhs) {  }

	public void init() {
		m_Current = null;
	}

	public void resetToRoot() {
		m_Current = m_Current.getRoot();
	}

	public void enterElement(String name, byte nodeClass) {
		TextNode node = new TextNode(m_Current, name, nodeClass);
		if (m_Current == null) 
			m_Children.add(node);
			
		m_Current = node;
	}

	public void leaveElement(String name) 
	{
		if (name == null || 0 == name.length() || !m_Current.getName().equals(name))
			throw new Error("this is useless behavior. do therefore not use.");
		
		m_Current = m_Current.getParent();
	}

	public void insertElement(String name, String value, byte nodeClass) {
		TextNode node = new TextNode(m_Current, name, nodeClass);
		node.setValue(value);
	}

	public boolean doesNameEqual(String name) {
		return m_Current.getName().equals(name);
	}

	public boolean doesNamedChildExist(String name) {
		return (0 < m_Current.getChildren().filterByName(name).size());
	}

	public ITextNode getRootNode() {
		return m_Children.getAt(0);
	}

	public void setRootNode(ITextNode rhs) {
		m_Current = rhs;
		m_Children.add(m_Current);
	}

	public ITextNode getCurrentNode() {
		return m_Current;
	}

	public String getNodeValueByPath( final String sPath) {
		ITextNode node = m_Current;
		
		if( node.getName() != null && node.getName().equals(sPath) )
			return node.getValue();
		
		for (String sPart : sPath.split("/")) {
			node = node.getChildren().getFirstNodeByName(sPart);
			if( node == null )
				return "";
		}
		
		return node.getValue();
	}

	public void saveAsSimpleXML(String filename) {
		try {
			Writer writer = new FileWriter(filename);
			TextNodeXMLSerializer xmlSerializer = new TextNodeXMLSerializer(
					writer);
			xmlSerializer.serialize(m_Current.getRoot());
			writer.close();
		} catch (Exception e) {
			throw new AltovaException(e.getMessage());
		}
	}
}