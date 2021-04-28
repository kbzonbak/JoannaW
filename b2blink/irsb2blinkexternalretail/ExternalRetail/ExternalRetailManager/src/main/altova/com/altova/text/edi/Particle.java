////////////////////////////////////////////////////////////////////////
//
// Particle.java
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


package com.altova.text.edi;

public class Particle
{
	int mMinOccurs = 0;
	int mMaxOccurs = Integer.MAX_VALUE;
	int mMergedEntries = 1;
	boolean mRespectMaxOccurs = true;
	StructureItem mNode = null;
	String mNameOverride = null;
	String[] mCodeValues = null;

	public int getMinOccurs() { 
		return mMinOccurs; 
	}

	public int getMaxOccurs() {
		return mMaxOccurs;
	}

	public int getMergedEntries() { 
		return mMergedEntries; 
	}

	public StructureItem getNode() {
		return mNode; 
	}

	public String getNameOverride() {
		return mNameOverride;
	}

	public String getName() {
		if (mNameOverride == null)
			return mNode.getName();

		return mNameOverride; 
	}

	public String[] getCodeValues() {
		return mCodeValues;
	}

	public Particle (String nameOverride, StructureItem node, int minOccurs, int maxOccurs, int mergedEntries, boolean respectMaxOccurs, String[] codeValues)	{
		this.mNameOverride = nameOverride;
		this.mNode = node;
		this.mMinOccurs = minOccurs;
		this.mMaxOccurs = maxOccurs;
		this.mMergedEntries = mergedEntries;
		this.mRespectMaxOccurs = respectMaxOccurs;
		this.mCodeValues = codeValues;
	}
}
	
