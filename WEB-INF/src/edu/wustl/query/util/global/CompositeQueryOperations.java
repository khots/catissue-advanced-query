package edu.wustl.query.util.global;

public enum CompositeQueryOperations {
	UNION("Union"),
	INTERSECTION("Intersection"),
	MINUS("Minus"),
	NONE("None");
	
	String operation;
	
	CompositeQueryOperations(String operation)
	{
		this.operation=operation;
	}

	public String getOperation()
	{
		return this.operation;
	}
	
	public static CompositeQueryOperations get(final String operation)
	{
		CompositeQueryOperations opToReturn=null;
		for (CompositeQueryOperations operationEnum : CompositeQueryOperations.values()) {
			if(operationEnum.getOperation().equals(operation))
			{
				opToReturn=operationEnum;
			}
		}
		return opToReturn;
	}
}
