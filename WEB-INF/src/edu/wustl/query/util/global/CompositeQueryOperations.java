package edu.wustl.query.util.global;

public enum CompositeQueryOperations {
	UNION("Union","+"),
	INTERSECTION("Intersection","*"),
	MINUS("Minus","-"),
	NONE("None","");

	String operation;
	String shortOperation;

	CompositeQueryOperations(String operation, String shortOperation)
	{
		this.operation=operation;
		this.shortOperation = shortOperation;
	}

	public String getOperation()
	{
		return this.operation;
	}

	public String getShortOperation()
	{
		return this.shortOperation;
	}

	public static CompositeQueryOperations get(final String operation)
	{
		CompositeQueryOperations opToReturn=null;
		for (CompositeQueryOperations operationEnum : CompositeQueryOperations.values()) {
			if (operationEnum.getOperation().equals(operation)
                    || operationEnum.getShortOperation().equals(operation))
			{
				opToReturn=operationEnum;
			}
		}
		return opToReturn;
	}
}
