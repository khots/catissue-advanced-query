
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
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.TimeInterval;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.util.global.Constants;

public class SingalNodeTemporalQuery
{

	private IDateOffsetAttribute dateOffsetAttr = null;
	private IExpressionAttribute attributeIExpression = null;
	private QueryableAttributeInterface attributeById = null;
	private IDateOffsetLiteral lhsDateOffSetLiteral = null;
	private IDateOffsetLiteral rhsDateOffSetLiteral1 = null;
	private IDateOffsetLiteral rhsDateOffSetLiteral2 = null;
	
	private ILiteral lhsDateLiteral = null;
	private ILiteral rhsDateLiteral1 = null;
	private ILiteral rhsDateLiteral2 = null;

	private ITerm lhsTerm = null;
	//private ITerm rhsTerm = null;
	private ArrayList <ITerm> rhsTermsList = null;

	private IConnector iCon = null;
	private ICustomFormula customFormula = null;
	private IExpression entityIExpression = null;
	private int entityExpressionId = 0;

	private ArithmeticOperator arithOp = null;
	private RelationalOperator relOp = null;

	private String attributeType = null;
	private TimeInterval rhsTimeInterval1 = null;
	private TimeInterval lhsTimeInterval = null;


	private TimeInterval rhsTimeInterval2 = null;
	
	private TimeInterval qAttrInterval = null;
	private SimpleDateFormat formatter;
	
	private static org.apache.log4j.Logger logger = LoggerConfig.getConfiguredLogger(SingalNodeTemporalQuery.class);

	/**
	 * 
	 * @return
	 */
	public ArrayList<ITerm> getRhsTermsList() 
	{
		return rhsTermsList;
	}

	/**
	 * 
	 * @param rhsTermsList
	 */
	public void setRhsTermsList(ArrayList<ITerm> rhsTermsList) 
	{
		this.rhsTermsList = rhsTermsList;
	}
	
	/**
	 * 
	 * @return
	 */
	public TimeInterval getQAttrInterval()
	{
		return qAttrInterval;
	}

	/**
	 * 
	 * @param attrInterval
	 */
	public void setQAttrInterval(TimeInterval attrInterval)
	{
		qAttrInterval = attrInterval;
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
	 * @return Returns the attributeIExpression.
	 */
	public IExpressionAttribute getAttributeIExpression()
	{
		return attributeIExpression;
	}

	/**
	 * @param attributeIExpression The attributeIExpression to set.
	 */
	public void setAttributeIExpression(IExpressionAttribute attributeIExpression)
	{
		this.attributeIExpression = attributeIExpression;
	}

	/**
	 * @return Returns the attributeType.
	 */
	public String getAttributeType()
	{
		return attributeType;
	}

	/**
	 * @param attributeType The attributeType to set.
	 */
	public void setAttributeType(String attributeType)
	{
		this.attributeType = attributeType;
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
	 * @return Returns the lhsDateLiteral.
	 */
	public ILiteral getLhsDateLiteral()
	{
		return lhsDateLiteral;
	}

	/**
	 * @param lhsDateLiteral The lhsDateLiteral to set.
	 */
	public void setLhsDateLiteral(ILiteral lhsDateLiteral)
	{
		this.lhsDateLiteral = lhsDateLiteral;
	}

	/**
	 * @return Returns the rhsDateLiteral.
	 */
	public ILiteral getRhsDateLiteral1()
	{
		return rhsDateLiteral1;
	}

	/**
	 * @param rhsDateLiteral The rhsDateLiteral to set.
	 */
	public void setRhsDateLiteral1(ILiteral rhsDateLiteral1)
	{
		this.rhsDateLiteral1 = rhsDateLiteral1;
	}

	/**
	 * 
	 * @return
	 */
	public ILiteral getRhsDateLiteral2() 
	{
		return rhsDateLiteral2;
	}

	/**
	 * 
	 * @param rhsDateLiteral2
	 */
	public void setRhsDateLiteral2(ILiteral rhsDateLiteral2) 
	{
		this.rhsDateLiteral2 = rhsDateLiteral2;
	}

	/**
	 * @return Returns the dateOffsetAttr.
	 */
	public IDateOffsetAttribute getDateOffsetAttr()
	{
		return dateOffsetAttr;
	}

	/**
	 * @param dateOffsetAttr The dateOffsetAttr to set.
	 */
	public void setDateOffsetAttr(IDateOffsetAttribute dateOffsetAttr)
	{
		this.dateOffsetAttr = dateOffsetAttr;
	}

	/**
	 * @return Returns the lhsDateOffSetLiteral.
	 */
	public IDateOffsetLiteral getLhsDateOffSetLiteral()
	{
		return lhsDateOffSetLiteral;
	}

	/**
	 * @param lhsDateOffSetLiteral The lhsDateOffSetLiteral to set.
	 */
	public void setLhsDateOffSetLiteral(IDateOffsetLiteral lhsDateOffSetLiteral)
	{
		this.lhsDateOffSetLiteral = lhsDateOffSetLiteral;
	}

	/**
	 * @return Returns the rhsDateOffSetLiteral.
	 */
	public IDateOffsetLiteral getRhsDateOffSetLiteral1()
	{
		return rhsDateOffSetLiteral1;
	}

	/**
	 * @param rhsDateOffSetLiteral The rhsDateOffSetLiteral to set.
	 */
	public void setRhsDateOffSetLiteral1(IDateOffsetLiteral rhsDateOffSetLiteral1)
	{
		this.rhsDateOffSetLiteral1 = rhsDateOffSetLiteral1;
	}
	
	/**
	 * @return Returns the rhsDateOffSetLiteral.
	 */
	public IDateOffsetLiteral getRhsDateOffSetLiteral2()
	{
		return rhsDateOffSetLiteral2;
	}

	/**
	 * @param rhsDateOffSetLiteral The rhsDateOffSetLiteral to set.
	 */
	public void setRhsDateOffSetLiteral2(IDateOffsetLiteral rhsDateOffSetLiteral2)
	{
		this.rhsDateOffSetLiteral2 = rhsDateOffSetLiteral2;
	}

	/**
	 * @return Returns the entityIExpression.
	 */
	public IExpression getEntityIExpression()
	{
		return entityIExpression;
	}

	/**
	 * @param entityIExpression The entityIExpression to set.
	 */
	public void setEntityIExpression(IExpression entityIExpression)
	{
		this.entityIExpression = entityIExpression;
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
	 * @return Returns the lhsTimeInterval.
	 */
	public TimeInterval getLhsTimeInterval()
	{
		return lhsTimeInterval;
	}

	/**
	 * @param lhsTimeInterval The lhsTimeInterval to set.
	 */
	public void setLhsTimeInterval(TimeInterval lhsTimeInterval)
	{
		this.lhsTimeInterval = lhsTimeInterval;
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
	}
*/
	/**
	 * @param rhsTerm The rhsTerm to set.
	 */
/*	public void setRhsTerm(ITerm rhsTerm)
	{
		this.rhsTerm = rhsTerm;
	}
*/
	/**
	 * @return Returns the rhsTimeInterval.
	 */
	public TimeInterval getRhsTimeInterval1()
	{
		return rhsTimeInterval1;
	}

	/**
	 * @param rhsTimeInterval The rhsTimeInterval to set.
	 */
	public void setRhsTimeInterval1(TimeInterval rhsTimeInterval1)
	{
		this.rhsTimeInterval1 = rhsTimeInterval1;
	}
	
	
	//here
	/**
	 * @return Returns the rhsTimeInterval.
	 */
	public TimeInterval getRhsTimeInterval2()
	{
		return rhsTimeInterval2;
	}

	/**
	 * @param rhsTimeInterval The rhsTimeInterval to set.
	 */
	public void setRhsTimeInterval2(TimeInterval rhsTimeInterval2)
	{
		this.rhsTimeInterval2 = rhsTimeInterval2;
	}

	
	
	
	//here

	/**
	 * @return Returns the entityExpressionId.
	 */
	public int getEntityExpressionId()
	{
		return entityExpressionId;
	}

	/**
	 * @param entityExpressionId The entityExpressionId to set.
	 */
	public void setEntityExpressionId(int entityExpressionId)
	{
		this.entityExpressionId = entityExpressionId;
	}

	/**
	 * @return Returns the attributeById.
	 */
	public QueryableAttributeInterface getAttributeById()
	{
		return attributeById;
	}

	/**
	 * @param attributeById The attributeById to set.
	 */
	public void setAttributeById(QueryableAttributeInterface attributeById)
	{
		this.attributeById = attributeById;
	}

	public void createOnlyLHS()
	{
		lhsTerm = QueryObjectFactory.createTerm();
		//Updating lhsTerm
		if (lhsDateLiteral != null)
		{
			//if DatePicker exists on LHS
			lhsTerm.addOperand(lhsDateLiteral);
			addSecondLhsOperand();
		}
		else
		{
			//If DatePicker doesn't exists on LHS
			lhsTerm.addOperand(lhsDateOffSetLiteral);
			addSecondLhsOperand();
		}

	}

	public void createLHSAndRHS()
	{
		lhsTerm = QueryObjectFactory.createTerm();
		ITerm rhsTerm1 = QueryObjectFactory.createTerm();

		//Updating lhsTerm
		if (lhsDateLiteral != null)
		{
			//if DatePicker exists on LHS
			lhsTerm.addOperand(lhsDateLiteral);
			addSecondLhsOperand();
		}
		else
		{
			//If DatePicker doesn't exists on LHS
			lhsTerm.addOperand(lhsDateOffSetLiteral);
			addSecondLhsOperand();
		}

		addRhsTerms(rhsTerm1);
	}

	private void addRhsTerms(ITerm rhsTerm1) 
	{
		//Updating rhsTerm
		rhsTermsList = new ArrayList<ITerm>();
		if (rhsDateLiteral1 != null)
		{
			//IF DatePicker on RHS
			rhsTerm1.addOperand(rhsDateLiteral1);
			rhsTermsList.add(rhsTerm1);
			
			//this is the case of "Between" operator, where we have two Date Literals
			if(rhsDateLiteral2 != null)
			{
				ITerm rhsTerm2 = QueryObjectFactory.createTerm();
				rhsTerm2.addOperand(rhsDateLiteral2);
				rhsTermsList.add(rhsTerm2);
			}
		}
		else
		{
			//If No datePciker
			rhsTerm1.addOperand(rhsDateOffSetLiteral1);
			rhsTermsList.add(rhsTerm1);
			
			//this is the case of "Between" operator, where we have two dateoffset Literals 
			if(rhsDateOffSetLiteral2 != null)
			{
				ITerm rhsTerm2 = QueryObjectFactory.createTerm();
				rhsTerm2.addOperand(rhsDateOffSetLiteral2);
				rhsTermsList.add(rhsTerm2);
			}
		}
	}

	private void addSecondLhsOperand()
	{
		if (attributeIExpression != null)
		{
			//If attribute selected is of type Date
			lhsTerm.addOperand(iCon, attributeIExpression);
		}
		else
		{
			//If attribute selected is Int type
			lhsTerm.addOperand(iCon, dateOffsetAttr);
		}

	}

	/**
	 * 
	 *
	 */
	public void createExpressions()
	{
		if (attributeType.equals(Constants.DATE_TYPE))
		{
			//Means Attribute is of Date type , then it is Expression attribute
			attributeIExpression = QueryObjectFactory.createExpressionAttribute(entityIExpression,
					attributeById);
		}
		else
		{
			//It will be DateOffSetLiteral
			//			dateOffsetAttr = QueryObjectFactory.createDateOffsetAttribute(entityIExpression,attributeById,TimeInterval.Day);
			if (qAttrInterval != null)
			{
				dateOffsetAttr = QueryObjectFactory.createDateOffsetAttribute(entityIExpression,
						attributeById, qAttrInterval);
			}
			else
			{
				dateOffsetAttr = QueryObjectFactory.createDateOffsetAttribute(entityIExpression,
						attributeById, TimeInterval.Day);
			}
		}
	}

	/*public void createRhsDateOffSetLiteral(String rhsTimeInterval)
	{
		if ((!rhsTimeInterval.equals(DAGConstant.NULL_STRING)))
		{
			this.rhsTimeInterval = getTimeInterval(rhsTimeInterval);
			rhsDateOffSetLiteral = QueryObjectFactory.createDateOffsetLiteral(this.rhsTimeInterval);
		}
	}*/

	/**
	 * This method creates both dateOffSetLiterals for "Between" operator   
	 * @param timeIntervalValue1
	 * @param timeValue1
	 * @param timeIntervalValue2
	 * @param timeValue2
	 */

	public void createBothLiterals(String timeIntervalValue1, String timeValue1,String timeIntervalValue2, String timeValue2)
	{
		if(attributeType.equals("Date"))
		{
			//In this case , we will have Both Date offset Literals
			this.rhsTimeInterval1 = getTimeInterval(timeIntervalValue1);
			rhsDateOffSetLiteral1 = QueryObjectFactory.createDateOffsetLiteral(timeValue1,
					this.rhsTimeInterval1);
			
			this.rhsTimeInterval2 = getTimeInterval(timeIntervalValue2);
			rhsDateOffSetLiteral2 = QueryObjectFactory.createDateOffsetLiteral(timeValue2,
					this.rhsTimeInterval2);
			
			
		}
		else
		{
			//this is the case when attribute type is Integer, so we have Both date Pickers on RHS
			//So we need to create both Date Literals
			try
			{
				String datePattern = getDatePattern();
				Date date = null;
				formatter = new SimpleDateFormat(datePattern);
				date = formatter.parse(timeValue1);
				rhsDateLiteral1 = QueryObjectFactory.createDateLiteral(new java.sql.Date(date
						.getTime()));
			
				 date = formatter.parse(timeValue2);
				 rhsDateLiteral2 =  QueryObjectFactory.createDateLiteral(new java.sql.Date(date
							.getTime()));
			
			
			}
			catch(ParseException  e)
			{
				logger.debug(e.getMessage(), e);
			}
		}
		
	}
	
	
	private String getDatePattern()
	{
		   String pattern = "";
		
			//Date date = Utility.parseDate(rhsTimeValue, "MM/dd/yyyy HH:MM:SS");
			if (attributeType.equals("DateTime"))
			{
				pattern = "MM/dd/yyyy HH:mm:ss";
			}
			else
			{
				pattern = "MM/dd/yyyy";
			}
           return  pattern;
	}
	
	/**
	 * 
	 * @param rhsTimeValue
	 * @param rhsTimeInterval
	 */
	public void createRightLiterals(String rhsTimeValue, String rhsTimeInterval)
	{
		if ((!rhsTimeValue.equals(DAGConstant.NULL_STRING))
				&& (!rhsTimeInterval.equals(DAGConstant.NULL_STRING)))
		{
			//It  means there exists TextInput and Time Intervals on LHS, so create dateOffSetLiteral
			this.rhsTimeInterval1 = getTimeInterval(rhsTimeInterval);
			rhsDateOffSetLiteral1 = QueryObjectFactory.createDateOffsetLiteral(rhsTimeValue,
					this.rhsTimeInterval1);
		}
		else
		{
			generateRhsTimeValue(rhsTimeValue, rhsTimeInterval);
		}
	}

	private void generateRhsTimeValue(String rhsTimeValue, String rhsTimeInterval)
	{
		if ((!rhsTimeValue.equals(DAGConstant.NULL_STRING))
				&& (rhsTimeInterval.equals(DAGConstant.NULL_STRING)))
		{
			//It  means there exists TextInput and Time Intervals on LHS, so create dateOffSetLiteral
			Date date = null;
			String pattern = "";
			try
			{
				//Date date = Utility.parseDate(rhsTimeValue, "MM/dd/yyyy HH:MM:SS");
				if (attributeType.equals("DateTime"))
				{
					pattern = "MM/dd/yyyy HH:mm:ss";
				}
				else
				{
					pattern = "MM/dd/yyyy";
				}

				formatter = new SimpleDateFormat(pattern);
				date = formatter.parse(rhsTimeValue);
				rhsDateLiteral1 = QueryObjectFactory.createDateLiteral(new java.sql.Date(date
						.getTime()));
			}
			catch (ParseException e)
			{
				Logger.out.debug(e.getMessage(),e);
			}
		}
	}

	/**
	 * 
	 * @param lhsTimeValue
	 * @param lhsTimeInterval
	 */
	public void createLeftLiterals(String lhsTimeValue, String lhsTimeInterval)
	{
		if ((!lhsTimeValue.equals(DAGConstant.NULL_STRING))
				&& (!lhsTimeInterval.equals(DAGConstant.NULL_STRING)))
		{
			//It  means there exists TextInput and Time Intervals on LHS, so create dateOffSetLiteral
			this.lhsTimeInterval = getTimeInterval(lhsTimeInterval);
			lhsDateOffSetLiteral = QueryObjectFactory.createDateOffsetLiteral(lhsTimeValue,
					this.lhsTimeInterval);
		}
		else
		{
			dateFormatter(lhsTimeValue, lhsTimeInterval);
		}
	}

	private void dateFormatter(String lhsTimeValue, String lhsTimeInterval)
	{
		if ((!lhsTimeValue.equals(DAGConstant.NULL_STRING))
				&& (lhsTimeInterval.equals(DAGConstant.NULL_STRING)))
		{
			//This is the case when there exists DataPicker on LHS, so create only Date Literal
			try
			{
				Date date = Utility.parseDate(lhsTimeValue, "MM/dd/yyyy");
				lhsDateLiteral = QueryObjectFactory.createDateLiteral(new java.sql.Date(date
						.getTime()));
			}
			catch (ParseException e)
			{
				Logger.out.debug(e.getMessage(),e);
			}
		}
	}

	/**
	 * 
	 * @param timeIntervalValue
	 * @return
	 */
	public TimeInterval getTimeInterval(String timeIntervalValue)
	{
		TimeInterval timeInterval = null;
		for (TimeInterval time : TimeInterval.values())
		{
			if (timeIntervalValue.equals(time.name() + "s"))
			{
				timeInterval = time;
				break;
			}
		}
		return timeInterval;
	}

}
