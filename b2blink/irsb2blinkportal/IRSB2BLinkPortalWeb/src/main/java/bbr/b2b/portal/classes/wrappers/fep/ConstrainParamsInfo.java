package bbr.b2b.portal.classes.wrappers.fep;

import bbr.b2b.fep.common.data.classes.ConstraintTypeDTO;

public class ConstrainParamsInfo
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ConstrainParamsInfo(ConstraintTypeDTO constrain, String FirstValue, String SecondValue)
	{
		super();
		this.setConstrain(constrain);
		this.setFirstValue(FirstValue);
		this.setSecondValue(SecondValue);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private ConstraintTypeDTO	constrain	= null;
	private String				firstValue	= null;
	private String				secondValue	= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	public String getFirstValue()
	{
		return firstValue;
	}

	public void setFirstValue(String value)
	{
		this.firstValue = value;
	}

	public ConstraintTypeDTO getConstrain()
	{
		return constrain;
	}

	public void setConstrain(ConstraintTypeDTO constrain)
	{
		this.constrain = constrain;
	}

	public String getSecondValue()
	{
		return secondValue;
	}

	public void setSecondValue(String secondValue)
	{
		this.secondValue = secondValue;
	}
}
