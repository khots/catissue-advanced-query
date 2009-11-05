
package edu.wustl.query.flex.dag;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class CustomFormulaNode implements Externalizable
{

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	private String name = "";
	private String firstNodeName = "";
	private String secondNodeName = "";

	private int firstNodeExpId = 0;
	private int secondNodeExpId = 0;

	private String firstSelectedAttrName = "";
	private String secondSelectedAttrName = "";

	private String firstSelectedAttrId = "";
	private String secondSelectedAttrId = "";

	private String firstSelectedAttrType = "";
	private String secondSelectedAttrType = "";

	private String selectedArithmeticOp = "";
	private String selectedLogicalOp = "";

	private String timeValue1 = "";
	private String timeInterval1 = "";
	
	private String timeValue2 = "";
	private String timeInterval2 = "";
	

	/**
	 * 
	 * @return
	 */
	public String getTimeInterval2() 
	{
		return timeInterval2;
	}

	/**
	 * 
	 * @param timeInterval2
	 */
	public void setTimeInterval2(String timeInterval2) 
	{
		this.timeInterval2 = timeInterval2;
	}

	/**
	 * 
	 * @return
	 */
	public String getTimeValue2() 
	{
		return timeValue2;
	}

     /**
      * 
      * @param timeValue2
      */
	public void setTimeValue2(String timeValue2) 
	{
		this.timeValue2 = timeValue2;
	}

	private String operation = "";

	private String customColumnName = "";
	private int xLocation;
	private int yLocation;
	private String nodeView = "";
	private String qAttrInterval1 = "";
	private String qAttrInterval2 = "";
	private String ccInterval = "";

	/**
	 * @return Returns the ccInterval.
	 */
	public String getCcInterval()
	{
		return ccInterval;
	}

	/**
	 * @param ccInterval The ccInterval to set.
	 */
	public void setCcInterval(String ccInterval)
	{
		this.ccInterval = ccInterval;
	}

	/**
	 * @return Returns the nodeView.
	 */
	public String getNodeView()
	{
		return nodeView;
	}

	public String getQAttrInterval1()
	{
		return qAttrInterval1;
	}

	public void setQAttrInterval1(String attrInterval)
	{
		qAttrInterval1 = attrInterval;
	}

	public String getQAttrInterval2()
	{
		return qAttrInterval2;
	}

	public void setQAttrInterval2(String attrInterval2)
	{
		qAttrInterval2 = attrInterval2;
	}

	/**
	 * @param nodeView The nodeView to set.
	 */
	public void setNodeView(String nodeView)
	{
		this.nodeView = nodeView;
	}

	/**
	 * @return Returns the customColumnName.
	 */
	public String getCustomColumnName()
	{
		return customColumnName;
	}

	/**
	 * @param customColumnName The customColumnName to set.
	 */
	public void setCustomColumnName(String customColumnName)
	{
		this.customColumnName = customColumnName;
	}

	/**
	 * @return Returns the xLocation.
	 */
	public int getXLocation()
	{
		return xLocation;
	}

	/**
	 * @param xLocation The xLocation to set.
	 */
	public void setXLocation(int xLocation)
	{
		this.xLocation = xLocation;
	}

	/**
	 * @return Returns the yLocation.
	 */
	public int getYLocation()
	{
		return yLocation;
	}

	/**
	 * @param yLocation The yLocation to set.
	 */
	public void setYLocation(int yLocation)
	{
		this.yLocation = yLocation;
	}

	/**
	 * @return Returns the firstNodeExpId.
	 */
	public int getFirstNodeExpId()
	{
		return firstNodeExpId;
	}

	/**
	 * @param firstNodeExpId The firstNodeExpId to set.
	 */
	public void setFirstNodeExpId(int firstNodeExpId)
	{
		this.firstNodeExpId = firstNodeExpId;
	}

	/**
	 * @return Returns the firstNodeName.
	 */
	public String getFirstNodeName()
	{
		return firstNodeName;
	}

	/**
	 * @param firstNodeName The firstNodeName to set.
	 */
	public void setFirstNodeName(String firstNodeName)
	{
		this.firstNodeName = firstNodeName;
	}

	/**
	 * @return Returns the firstSelectedAttrId.
	 */
	public String getFirstSelectedAttrId()
	{
		return firstSelectedAttrId;
	}

	/**
	 * @param firstSelectedAttrId The firstSelectedAttrId to set.
	 */
	public void setFirstSelectedAttrId(String firstSelectedAttrId)
	{
		this.firstSelectedAttrId = firstSelectedAttrId;
	}

	/**
	 * @return Returns the firstSelectedAttrName.
	 */
	public String getFirstSelectedAttrName()
	{
		return firstSelectedAttrName;
	}

	/**
	 * @param firstSelectedAttrName The firstSelectedAttrName to set.
	 */
	public void setFirstSelectedAttrName(String firstSelectedAttrName)
	{
		this.firstSelectedAttrName = firstSelectedAttrName;
	}

	/**
	 * @return Returns the firstSelectedAttrType.
	 */
	public String getFirstSelectedAttrType()
	{
		return firstSelectedAttrType;
	}

	/**
	 * @param firstSelectedAttrType The firstSelectedAttrType to set.
	 */
	public void setFirstSelectedAttrType(String firstSelectedAttrType)
	{
		this.firstSelectedAttrType = firstSelectedAttrType;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return Returns the secondNodeExpId.
	 */
	public int getSecondNodeExpId()
	{
		return secondNodeExpId;
	}

	/**
	 * @param secondNodeExpId The secondNodeExpId to set.
	 */
	public void setSecondNodeExpId(int secondNodeExpId)
	{
		this.secondNodeExpId = secondNodeExpId;
	}

	/**
	 * @return Returns the secondNodeName.
	 */
	public String getSecondNodeName()
	{
		return secondNodeName;
	}

	/**
	 * @param secondNodeName The secondNodeName to set.
	 */
	public void setSecondNodeName(String secondNodeName)
	{
		this.secondNodeName = secondNodeName;
	}

	/**
	 * @return Returns the secondSelectedAttrId.
	 */
	public String getSecondSelectedAttrId()
	{
		return secondSelectedAttrId;
	}

	/**
	 * @param secondSelectedAttrId The secondSelectedAttrId to set.
	 */
	public void setSecondSelectedAttrId(String secondSelectedAttrId)
	{
		this.secondSelectedAttrId = secondSelectedAttrId;
	}

	/**
	 * @return Returns the secondSelectedAttrName.
	 */
	public String getSecondSelectedAttrName()
	{
		return secondSelectedAttrName;
	}

	/**
	 * @param secondSelectedAttrName The secondSelectedAttrName to set.
	 */
	public void setSecondSelectedAttrName(String secondSelectedAttrName)
	{
		this.secondSelectedAttrName = secondSelectedAttrName;
	}

	/**
	 * @return Returns the secondSelectedAttrType.
	 */
	public String getSecondSelectedAttrType()
	{
		return secondSelectedAttrType;
	}

	/**
	 * @param secondSelectedAttrType The secondSelectedAttrType to set.
	 */
	public void setSecondSelectedAttrType(String secondSelectedAttrType)
	{
		this.secondSelectedAttrType = secondSelectedAttrType;
	}

	/**
	 * @return Returns the selectedArithmeticOp.
	 */
	public String getSelectedArithmeticOp()
	{
		return selectedArithmeticOp;
	}

	/**
	 * @param selectedArithmeticOp The selectedArithmeticOp to set.
	 */
	public void setSelectedArithmeticOp(String selectedArithmeticOp)
	{
		this.selectedArithmeticOp = selectedArithmeticOp;
	}

	/**
	 * @return Returns the selectedLogicalOp.
	 */
	public String getSelectedLogicalOp()
	{
		return selectedLogicalOp;
	}

	/**
	 * @param selectedLogicalOp The selectedLogicalOp to set.
	 */
	public void setSelectedLogicalOp(String selectedLogicalOp)
	{
		this.selectedLogicalOp = selectedLogicalOp;
	}

	/**
	 * @return Returns the timeInterval.
	 */
	public String getTimeInterval1()
	{
		return timeInterval1;
	}

	/**
	 * @param timeInterval The timeInterval to set.
	 */
	public void setTimeInterval1(String timeInterval1)
	{
		this.timeInterval1 = timeInterval1;
	}

	/**
	 * @return Returns the timeValue.
	 */
	public String getTimeValue1()
	{
		return timeValue1;
	}

	/**
	 * @param timeValue The timeValue to set.
	 */
	public void setTimeValue1(String timeValue1)
	{
		this.timeValue1 = timeValue1;
	}

	/**
	 * Reading searilized data
	 */
	public void readExternal(ObjectInput inputObject) throws IOException, ClassNotFoundException
	{
		name = inputObject.readUTF();
		firstNodeName = inputObject.readUTF();
		secondNodeName = inputObject.readUTF();
		firstNodeExpId = inputObject.readInt();
		secondNodeExpId = inputObject.readInt();
		firstSelectedAttrName = inputObject.readUTF();
		secondSelectedAttrName = inputObject.readUTF();
		firstSelectedAttrId = inputObject.readUTF();
		secondSelectedAttrId = inputObject.readUTF();
		firstSelectedAttrType = inputObject.readUTF();
		secondSelectedAttrType = inputObject.readUTF();
		selectedArithmeticOp = inputObject.readUTF();
		selectedLogicalOp = inputObject.readUTF();
		timeValue1 = inputObject.readUTF();
		timeInterval1 = inputObject.readUTF();
		timeValue2 = inputObject.readUTF();
		timeInterval2 = inputObject.readUTF();
		
		operation = inputObject.readUTF();
		customColumnName = inputObject.readUTF();
		xLocation = inputObject.readInt();
		yLocation = inputObject.readInt();
		nodeView = inputObject.readUTF();
		qAttrInterval1 = inputObject.readUTF();
		qAttrInterval2 = inputObject.readUTF();
		ccInterval = inputObject.readUTF();
	}

	/**
	 * Writing seraialized Id
	 */
	public void writeExternal(ObjectOutput out) throws IOException
	{
		out.writeUTF(name);
		out.writeUTF(firstNodeName);
		out.writeUTF(secondNodeName);
		out.writeInt(firstNodeExpId);
		out.writeInt(secondNodeExpId);
		out.writeUTF(firstSelectedAttrName);
		out.writeUTF(secondSelectedAttrName);
		out.writeUTF(firstSelectedAttrId);
		out.writeUTF(secondSelectedAttrId);
		out.writeUTF(firstSelectedAttrType);
		out.writeUTF(secondSelectedAttrType);
		out.writeUTF(selectedArithmeticOp);
		out.writeUTF(selectedLogicalOp);
		out.writeUTF(timeValue1);
		out.writeUTF(timeInterval1);
		out.writeUTF(timeValue2);
		out.writeUTF(timeInterval2);
		out.writeUTF(operation);
		out.writeUTF(customColumnName);
		out.writeInt(xLocation);
		out.writeInt(yLocation);
		out.writeUTF(nodeView);
		out.writeUTF(qAttrInterval1);
		out.writeUTF(qAttrInterval2);
		out.writeUTF(ccInterval);
	}

	/**
	 * @return Returns the operation.
	 */
	public String getOperation()
	{
		return operation;
	}

	/**
	 * @param operation The operation to set.
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

}
