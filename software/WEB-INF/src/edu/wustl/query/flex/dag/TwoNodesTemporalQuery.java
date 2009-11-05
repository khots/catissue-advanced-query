
package edu.wustl.query.flex.dag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.queryobject.ArithmeticOperator;
import edu.wustl.common.querysuite.queryobject.IConnector;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IDateOffsetAttribute;
import edu.wustl.common.querysuite.queryobject.IDateOffsetLiteral;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.ILiteral;
import edu.wustl.common.querysuite.queryobject.INumericLiteral;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.TimeInterval;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Constants;

/**
 * 
 * @author baljeet_dhindhwal
 *
 */
public class TwoNodesTemporalQuery
{

	private IDateOffsetAttribute dateOffsetAttr1 = null;
	private IDateOffsetAttribute dateOffsetAttr2 = null;
	private IExpressionAttribute IExpression1 = null;
	private IExpressionAttribute IExpression2 = null;
	private IDateOffsetLiteral dateOffSetLiteral1 = null;
	private IDateOffsetLiteral dateOffSetLiteral2 = null;
	private ILiteral dateLiteral1 = null;
	private ILiteral dateLiteral2 = null;
	private ITerm lhsTerm = null;
	//private ITerm rhsTerm = null;
	
	private ArrayList <ITerm> rhsTermsList = null;
	
	/**
	 * This method returns the  rhsTermsList
	 * @return
	 */
	public ArrayList<ITerm> getRhsTermsList() 
	{
		return rhsTermsList;
	}

	/**
	 * This method sets the rhsTermsList
	 * @param rhsTermsList
	 */
	public void setRhsTermsList(ArrayList<ITerm> rhsTermsList) 
	{
		this.rhsTermsList = rhsTermsList;
	}

	private IConnector iCon = null;
	private ICustomFormula customFormula = null;
	private IExpression srcIExpression = null;
	private IExpression destIExpression = null;

	private ArithmeticOperator arithOp = null;
	private RelationalOperator relOp = null;
	private int srcExpressionId = 0;
	private int destExpressionId = 0;

	private QueryableAttributeInterface srcAttributeById = null;
	private QueryableAttributeInterface destAttributeById = null;

	private String firstAttributeType = null;
	private String secondAttributeType = null;

	private TimeInterval timeInterval1 = null;
	
	private TimeInterval timeInterval2 = null;

	private TimeInterval qAttrInterval1 = null;

	private TimeInterval qAttrInterval2 = null;

	private INumericLiteral intLiteral1 = null;
	private INumericLiteral intLiteral2 = null;

	private SimpleDateFormat formatter;

	//private String timeIntervalValue = null;
	//private String timeValue = null; 

	public TimeInterval getQAttrInterval2()
	{
		return qAttrInterval2;
	}

	public void setQAttrInterval2(TimeInterval attrInterval2)
	{
		qAttrInterval2 = attrInterval2;
	}

	public TimeInterval getQAttrInterval1()
	{
		return qAttrInterval1;
	}

	public void setQAttrInterval1(TimeInterval attrInterval)
	{
		qAttrInterval1 = attrInterval;
	}

	/**
	 * @return Returns the firstAttributeType.
	 */
	public String getFirstAttributeType()
	{
		return firstAttributeType;
	}

	/**
	 * @param firstAttributeType The firstAttributeType to set.
	 */
	public void setFirstAttributeType(String firstAttributeType)
	{
		this.firstAttributeType = firstAttributeType;
	}

	/**
	 * @return Returns the secondAttributeType.
	 */
	public String getSecondAttributeType()
	{
		return secondAttributeType;
	}

	/**
	 * @param secondAttributeType The secondAttributeType to set.
	 */
	public void setSecondAttributeType(String secondAttributeType)
	{
		this.secondAttributeType = secondAttributeType;
	}

	/**
	 * @return Returns the destAttributeById.
	 */
	public QueryableAttributeInterface getDestAttributeById()
	{
		return destAttributeById;
	}

	/**
	 * @param destAttributeById The destAttributeById to set.
	 */
	public void setDestAttributeById(QueryableAttributeInterface destAttributeById)
	{
		this.destAttributeById = destAttributeById;
	}

	/**
	 * @return Returns the srcAttributeById.
	 */
	public QueryableAttributeInterface getSrcAttributeById()
	{
		return srcAttributeById;
	}

	/**
	 * @param srcAttributeById The srcAttributeById to set.
	 */
	public void setSrcAttributeById(QueryableAttributeInterface srcAttributeById)
	{
		this.srcAttributeById = srcAttributeById;
	}

	/**
	 * @return Returns the arithOp.
	 */
	public ArithmeticOperator getArithOp()
	{
		return arithOp;
	}

	/**
	 * @param arithOp The arithOp to set.
	 */
	public void setArithOp(ArithmeticOperator arithOp)
	{
		this.arithOp = arithOp;
	}

	/**
	 * @return Returns the customFormula.
	 */
	public ICustomFormula getCustomFormula()
	{
		return customFormula;
	}

	/**
	 * @param customFormula The customFormula to set.
	 */
	public void setCustomFormula(ICustomFormula customFormula)
	{
		this.customFormula = customFormula;
	}

	/**
	 * @return Returns the dateLiteral.
	 */
	public ILiteral getDateLiteral1()
	{
		return dateLiteral1;
	}

	/**
	 * @param dateLiteral The dateLiteral to set.
	 */
	public void setDateLiteral1(ILiteral dateLiteral1)
	{
		this.dateLiteral1 = dateLiteral1;
	}

	/**
	 * @return Returns the dateOffsetAttr1.
	 */
	public IDateOffsetAttribute getDateOffsetAttr1()
	{
		return dateOffsetAttr1;
	}

	/**
	 * @param dateOffsetAttr1 The dateOffsetAttr1 to set.
	 */
	public void setDateOffsetAttr1(IDateOffsetAttribute dateOffsetAttr1)
	{
		this.dateOffsetAttr1 = dateOffsetAttr1;
	}

	/**
	 * @return Returns the dateOffsetAttr2.
	 */
	public IDateOffsetAttribute getDateOffsetAttr2()
	{
		return dateOffsetAttr2;
	}

	/**
	 * @param dateOffsetAttr2 The dateOffsetAttr2 to set.
	 */
	public void setDateOffsetAttr2(IDateOffsetAttribute dateOffsetAttr2)
	{
		this.dateOffsetAttr2 = dateOffsetAttr2;
	}

	/**
	 * @return Returns the dateOffSetLiteral.
	 */
	public IDateOffsetLiteral getDateOffSetLiteral1()
	{
		return dateOffSetLiteral1;
	}

	/**
	 * @param dateOffSetLiteral The dateOffSetLiteral to set.
	 */
	public void setDateOffSetLiteral1(IDateOffsetLiteral dateOffSetLiteral1)
	{
		this.dateOffSetLiteral1 = dateOffSetLiteral1;
	}
	
//
	/**
	 * @return Returns the dateOffSetLiteral.
	 */
	public IDateOffsetLiteral getDateOffSetLiteral2()
	{
		return dateOffSetLiteral2;
	}

	/**
	 * @param dateOffSetLiteral The dateOffSetLiteral to set.
	 */
	public void setDateOffSetLiteral2(IDateOffsetLiteral dateOffSetLiteral2)
	{
		this.dateOffSetLiteral2 = dateOffSetLiteral2;
	}
	
	
	
	
//	

	/**
	 * @return Returns the destExpressionId.
	 */
	public int getDestExpressionId()
	{
		return destExpressionId;
	}

	/**
	 * @param destExpressionId The destExpressionId to set.
	 */
	public void setDestExpressionId(int destExpressionId)
	{
		this.destExpressionId = destExpressionId;
	}

	/**
	 * @return Returns the iCon.
	 */
	public IConnector getICon()
	{
		return iCon;
	}

	/**
	 * @param con The iCon to set.
	 */
	public void setICon(IConnector con)
	{
		iCon = con;
	}

	/**
	 * @return Returns the iExpression1.
	 */
	public IExpressionAttribute getIExpression1()
	{
		return IExpression1;
	}

	/**
	 * @param expression1 The iExpression1 to set.
	 */
	public void setIExpression1(IExpressionAttribute expression1)
	{
		IExpression1 = expression1;
	}

	/**
	 * @return Returns the iExpression2.
	 */
	public IExpressionAttribute getIExpression2()
	{
		return IExpression2;
	}

	/**
	 * @param expression2 The iExpression2 to set.
	 */
	public void setIExpression2(IExpressionAttribute expression2)
	{
		IExpression2 = expression2;
	}

	/**
	 * @return Returns the lhsTerm.
	 */
	public ITerm getLhsTerm()
	{
		return lhsTerm;
	}

	/**
	 * @param lhsTerm The lhsTerm to set.
	 */
	public void setLhsTerm(ITerm lhsTerm)
	{
		this.lhsTerm = lhsTerm;
	}

	/**
	 * @return Returns the relOp.
	 */
	public RelationalOperator getRelOp()
	{
		return relOp;
	}

	/**
	 * @param relOp The relOp to set.
	 */
	public void setRelOp(RelationalOperator relOp)
	{
		this.relOp = relOp;
	}

	/**
	 * @return Returns the rhsTerm.
	 */
/*	public ITerm getRhsTerm()
	{
		return rhsTerm;
	}*/

	/**
	 * @param rhsTerm The rhsTerm to set.
	 */
	/*public void setRhsTerm(ITerm rhsTerm)
	{
		this.rhsTerm = rhsTerm;
	}*/

	/**
	 * @return Returns the srcExpressionId.
	 */
	public int getSrcExpressionId()
	{
		return srcExpressionId;
	}

	/**
	 * @param srcExpressionId The srcExpressionId to set.
	 */
	public void setSrcExpressionId(int srcExpressionId)
	{
		this.srcExpressionId = srcExpressionId;
	}

	/**
	 * @return Returns the srcIExpression.
	 */
	public IExpression getSrcIExpression()
	{
		return srcIExpression;
	}

	/**
	 * @param srcIExpression The srcIExpression to set.
	 */
	public void setSrcIExpression(IExpression srcIExpression)
	{
		this.srcIExpression = srcIExpression;
	}

	public void createExpressions()
	{
		//If Both attributes have type 
		if (firstAttributeType.equals(secondAttributeType))
		{
			IExpression1 = QueryObjectFactory.createExpressionAttribute(srcIExpression,
					srcAttributeById);
			IExpression2 = QueryObjectFactory.createExpressionAttribute(destIExpression,
					destAttributeById);
		}
		else
		{
			//If The attribute type is of type Date
			if (firstAttributeType.equals(Constants.DATE_TYPE))
			{
				IExpression1 = QueryObjectFactory.createExpressionAttribute(srcIExpression,
						srcAttributeById);
				if (qAttrInterval2 != null)
				{
					dateOffsetAttr2 = QueryObjectFactory.createDateOffsetAttribute(destIExpression,
							destAttributeById, qAttrInterval2);
				}
				else
				{
					dateOffsetAttr2 = QueryObjectFactory.createDateOffsetAttribute(destIExpression,
							destAttributeById, TimeInterval.Day);
				}
			}
			else
			{
				IExpression2 = QueryObjectFactory.createExpressionAttribute(destIExpression,
						destAttributeById);
				if (qAttrInterval1 != null)
				{
					dateOffsetAttr1 = QueryObjectFactory.createDateOffsetAttribute(srcIExpression,
							srcAttributeById, qAttrInterval1);
				}
				else
				{
					dateOffsetAttr1 = QueryObjectFactory.createDateOffsetAttribute(srcIExpression,
							srcAttributeById, TimeInterval.Day);
				}

			}
		}
	}

	/**
	 * This method will create only lhsTERM
	 * @param iCon
	 */
	public void createOnlyLHS()
	{
		lhsTerm = QueryObjectFactory.createTerm();
		if (IExpression1 != null && IExpression2 != null)
		{
			lhsTerm.addOperand(IExpression1);
			lhsTerm.addOperand(iCon, IExpression2);
		}
		else
		{
			if (IExpression1 != null && dateOffsetAttr2 != null)
			{
				lhsTerm.addOperand(IExpression1);
				lhsTerm.addOperand(iCon, dateOffsetAttr2);
			}
			else
			{
				lhsTerm.addOperand(IExpression2);
				lhsTerm.addOperand(iCon, dateOffsetAttr1);
			}
		}
	}

	/**
	 * This method creates either date Literal or dateOffset Literal depending on the time Interval values 
	 * @param timeIntervalValue
	 * @param timeValue
	 */
	public void createLiterals(String timeIntervalValue, String timeValue)
	{
		if ((!timeIntervalValue.equals("null")) && (!timeValue.equals("null")))
		{
			//Creating the dateOffSet Literal 
			timeInterval1 = setTimeInterval(timeIntervalValue);
			dateOffSetLiteral1 = QueryObjectFactory.createDateOffsetLiteral(timeValue, timeInterval1);
		}
		else
		{
			checkAttributeType(timeValue);
		}
	}
	
/**
 * This method creates both dateOffSetLiterals for "Between" operator   
 * @param timeIntervalValue1
 * @param timeValue1
 * @param timeIntervalValue2
 * @param timeValue2
 */
	public void createBothLiterals(String timeIntervalValue1, String timeValue1,String timeIntervalValue2, String timeValue2)
	{
		if((firstAttributeType.equals(DAGConstant.DATE_ATTRIBUTE)) && (secondAttributeType.equals(DAGConstant.DATE_ATTRIBUTE)))
		{
			//Then in that case create both date Literals
			timeInterval1 = setTimeInterval(timeIntervalValue1);
			dateOffSetLiteral1 = QueryObjectFactory.createDateOffsetLiteral(timeValue1, timeInterval1);

			timeInterval2 = setTimeInterval(timeIntervalValue2);
			dateOffSetLiteral2 = QueryObjectFactory.createDateOffsetLiteral(timeValue2, timeInterval2);
			
		}
		else if((firstAttributeType.equals(DAGConstant.INTEGER_ATTRIBUTE)) && (secondAttributeType.equals(DAGConstant.INTEGER_ATTRIBUTE)))
		{
			//In this case , create both integer Literals
			intLiteral1 = QueryObjectFactory.createNumericLiteral(timeValue1);
			intLiteral2 = QueryObjectFactory.createNumericLiteral(timeValue2);
		}
		else 
		{
				//This is the case when one attribute type is Date and other attribute type is Integer 
				//Both will be Date  Literals 
				Date date = dateFormater(timeValue1);
				dateLiteral1 = QueryObjectFactory.createDateLiteral(new java.sql.Date(date.getTime()));
				date = dateFormater(timeValue2);
				dateLiteral2 = QueryObjectFactory.createDateLiteral(new java.sql.Date(date.getTime()));
		}
	}
    	

	private void checkAttributeType(String timeValue)
	{
		if ((firstAttributeType.equals(DAGConstant.INTEGER_ATTRIBUTE)) && (secondAttributeType.equals(DAGConstant.INTEGER_ATTRIBUTE)))
		{
			intLiteral1 = QueryObjectFactory.createNumericLiteral(timeValue);
		}
		else if (!timeValue.equals("null"))
		{
			Date date = dateFormater(timeValue);

			dateLiteral1 = QueryObjectFactory.createDateLiteral(new java.sql.Date(date.getTime()));
		}
	}

	private Date dateFormater(String timeValue)
	{
		//Date date = Utility.parseDate(timeValue, "MM/dd/yyyy HH:MM:SS");					
		Date date = null;
		String pattern = "";
		try
		{
			if ((firstAttributeType.equals("DateTime")) && (secondAttributeType.equals("DateTime")))
			{
				pattern = "MM/dd/yyyy HH:mm:ss";
			}
			else
			{
				pattern = "MM/dd/yyyy";
			}
			formatter = new SimpleDateFormat(pattern);
			date = formatter.parse(timeValue);
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			Logger.out.error(e.getMessage(),e);
		}
		return date;
	}

	public TimeInterval setTimeInterval(String timeIntervalValue)
	{
		TimeInterval timeInterval = null;
		for (TimeInterval time : TimeInterval.values())
		{
			if (timeIntervalValue.equals(time.name() + "s"))
			{
				timeInterval  = time;
				break;
			}
		}
		return timeInterval;
	}

	public void setQAttrTInterval1(String timeIntervalValue)
	{
		qAttrInterval1 = getTInterval(timeIntervalValue);
	}

	public void setQAttrTInterval2(String timeIntervalValue)
	{
		qAttrInterval2 = getTInterval(timeIntervalValue);
	}

	//This method is marked public as it is accessed from DAGPanel class also  
	/**
	 * 
	 * @param timeIntervalValue
	 * @return
	 */
	public TimeInterval getTInterval(String timeIntervalValue)
	{
		TimeInterval t = null;
		for (TimeInterval time : TimeInterval.values())
		{
			if (timeIntervalValue.equals(time.name() + "s"))
			{
				t = time;
				break;
			}
		}
		return t;
	}

	public ILiteral getDateLiteral2() {
		return dateLiteral2;
	}

	public void setDateLiteral2(ILiteral dateLiteral2) {
		this.dateLiteral2 = dateLiteral2;
	}

	public INumericLiteral getIntLiteral1() {
		return intLiteral1;
	}

	public void setIntLiteral1(INumericLiteral intLiteral1) {
		this.intLiteral1 = intLiteral1;
	}

	public INumericLiteral getIntLiteral2() {
		return intLiteral2;
	}

	public void setIntLiteral2(INumericLiteral intLiteral2) {
		this.intLiteral2 = intLiteral2;
	}

	public void createDateOffsetLiteral(String timeIntervalValue)
	{
		timeInterval1 = setTimeInterval(timeIntervalValue);
		dateOffSetLiteral1 = QueryObjectFactory.createDateOffsetLiteral(timeInterval1);
	}
	//This method was required only in case of "IS NULL" and "IS NOT NULL"
	/*public void createOnlyRHS()
	{
		rhsTerm = QueryObjectFactory.createTerm();
		if (dateOffSetLiteral1 != null)
		{
			rhsTerm.addOperand(dateOffSetLiteral1);
		}
		else if (intLiteral != null)
		{
			rhsTerm.addOperand(intLiteral);
		}
	}*/

	/**
	 * 
	 */
	public void createLHSAndRHS()
	{
		rhsTermsList = new ArrayList<ITerm>();
		lhsTerm = QueryObjectFactory.createTerm();
		ITerm rhsTerm1 = QueryObjectFactory.createTerm();
		if (IExpression1 != null && IExpression2 != null)
		{
			lhsTerm.addOperand(IExpression1);
			lhsTerm.addOperand(iCon, IExpression2);
			if(dateOffSetLiteral1 != null && dateOffSetLiteral2 != null)
			{
				rhsTerm1.addOperand(dateOffSetLiteral1);
				rhsTermsList.add(rhsTerm1);
				
				//This second rhsTerm will be required in case of "Between" operator
				ITerm rhsTerm2 = QueryObjectFactory.createTerm();
				rhsTerm2.addOperand(dateOffSetLiteral2);
				rhsTermsList.add(rhsTerm2);
			}
			else if(intLiteral1 != null && intLiteral2 != null)
			{
				rhsTerm1.addOperand(intLiteral1);
				rhsTermsList.add(rhsTerm1);
				
				//Create second rhsTerm 
				ITerm rhsTerm2 = QueryObjectFactory.createTerm();
				rhsTerm2.addOperand(intLiteral2);
				rhsTermsList.add(rhsTerm2);
			}
			else if (dateOffSetLiteral1 != null)
			{
				rhsTerm1.addOperand(dateOffSetLiteral1);
				rhsTermsList.add(rhsTerm1);
			}
			else if(intLiteral1 != null)
			{
				rhsTerm1.addOperand(intLiteral1);
				rhsTermsList.add(rhsTerm1);
			}
		}
		else
		{
			addDifferentLHSOperand(rhsTerm1);
		}
	}

	private void addDifferentLHSOperand(ITerm rhsTerm)
	{
		if (IExpression1 != null && dateOffsetAttr2 != null)
		{
			lhsTerm.addOperand(IExpression1);
			lhsTerm.addOperand(iCon, dateOffsetAttr2);
			rhsTerm.addOperand(dateLiteral1);
			
			rhsTermsList.add(rhsTerm);
			if(dateLiteral2 != null)
			{
				//Then this is the case of Between operartor
				ITerm rhsTerm2 = QueryObjectFactory.createTerm();
				rhsTerm2.addOperand(dateLiteral2);
				rhsTermsList.add(rhsTerm2);
			}

		}
		else
		{
			lhsTerm.addOperand(dateOffsetAttr1);
			lhsTerm.addOperand(iCon, IExpression2);
			rhsTerm.addOperand(dateLiteral1);
			rhsTermsList.add(rhsTerm);
			if(dateLiteral2 != null)
			{
				//Then this is the case of Between operartor
				ITerm rhsTerm2 = QueryObjectFactory.createTerm();
				rhsTerm2.addOperand(dateLiteral2);
				rhsTermsList.add(rhsTerm2);
			}
		}
	}

	/**
	 * @return Returns the timeInterval.
	 */
	public TimeInterval getTimeInterval1()
	{
		return timeInterval1;
	}

	/**
	 * @param timeInterval The timeInterval to set.
	 */
	public void setTimeInterval1(TimeInterval timeInterval1)
	{
		this.timeInterval1 = timeInterval1;
	}
	
	/**
	 * @return Returns the timeInterval.
	 */
	public TimeInterval getTimeInterval2()
	{
		return timeInterval2;
	}

	/**
	 * @param timeInterval The timeInterval to set.
	 */
	public void setTimeInterval2(TimeInterval timeInterval2)
	{
		this.timeInterval2 = timeInterval2;
	}
	

	/**
	 * @return Returns the destIExpression.
	 */
	public IExpression getDestIExpression()
	{
		return destIExpression;
	}

	/**
	 * @param destIExpression The destIExpression to set.
	 */
	public void setDestIExpression(IExpression destIExpression)
	{
		this.destIExpression = destIExpression;
	}

}
