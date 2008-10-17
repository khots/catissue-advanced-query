package edu.wustl.common.query.impl;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.AttributeTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.DateTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.IntegerTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.LongTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.StringTypeInformationInterface;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SQLXMLException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.JoinGraph;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.queryobject.util.QueryObjectProcessor;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.queryengine.impl.IQueryGenerator;


public class XQueryGenerator implements IQueryGenerator
{     
    private IConstraints constraint;
    private JoinGraph joinGraph;
  	
	// Connection Parameters
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet result =null;

	/*
	 * The following function takes IQuery object as input for further processing
	 * @parameters : IQuery query= The query object
	 * @parameters : char QueryType = representing the kind of query whether aggregate or normal
	 */
	public String generateQuery(IQuery query) throws MultipleRootsException
	{
		String sqlxml = "";
		try
		{ 
			char QueryType = 'N';
			sqlxml = (String) buildQuery(query, QueryType);
			System.out.println("sqlxml = " + sqlxml);

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (SQLXMLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				result.close();
				stmt.close();
				conn.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return sqlxml;
	}
	
	public String buildQuery(IQuery query,char QueryType) throws MultipleRootsException, SQLException, RuntimeException, SQLXMLException
	{
		
		String FormedQuery = "";
		IQuery queryClone = (IQuery) getObjectCopy(query);
		constraint = queryClone.getConstraints();
		this.joinGraph = (JoinGraph) constraint.getJoinGraph();
		IExpression rootExpression = constraint.getRootExpression();		
		QueryObjectProcessor.replaceMultipleParents(constraint);
        String select = getSelectPart(rootExpression,QueryType);
        String column = getColumn() + ")"; 
        if(QueryType == 'N')
        {
        	FormedQuery = select + column;
        }
        else if(QueryType == 'C')
        {
        	FormedQuery = select + column + ')';
        }
		return FormedQuery; 
		
	}
	


	private String getColumn() {
		StringBuffer buffer = new StringBuffer(20);
		buffer.append(" columns ");
		for (IExpression expression : constraint)
		{
			if(expression.isInView())
			{
				EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
				List<AttributeInterface> attributes = (List<AttributeInterface>) entity.getAllAttributes();
				for(AttributeInterface attribute : attributes)
				{
					String attrName = attribute.getName();
					String dataType = getDataTypeInformation(attribute);
					buffer.append(attrName + " " +  dataType + " path '" + attrName + "'" + ",");
				}
				
			}
		}
		return removeLastComma(buffer.toString()); 
	}
	
	
	private String getDataTypeInformation(AttributeInterface attribute) {
		AttributeTypeInformationInterface dataType = attribute.getAttributeTypeInformation();
		if(dataType instanceof StringTypeInformationInterface)
		{
			return "varchar(50)";
		}
		else if(dataType instanceof DateTypeInformationInterface)
		{
			return "timestamp";
		}
		else if(dataType instanceof LongTypeInformationInterface)
		{
			return "double";
		}
		else if (dataType instanceof IntegerTypeInformationInterface)
		{
			return "integer";
		}
		return null;
	}
	/**
	 * Method to create deep copy of the object.
	 * @param obj The object to be copied.
	 * @return The Object reference representing deep copy of the given object.
	 */
	public static Object getObjectCopy(Object obj)
	{
		Object copy = null;
		try
		{
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(obj);
			out.flush();
			out.close();
			// Make an input stream from the byte array and read
			// a copy of the object back in.
			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(bos.toByteArray()));
			copy = in.readObject();
		}
		catch (IOException e)
		{
			Logger.out.error(e.getMessage(), e);
			copy = null;
		}
		catch (ClassNotFoundException cnfe)
		{
			Logger.out.error(cnfe.getMessage(), cnfe);
			copy = null;
		}
		return copy;
	}
	
    
    public String getSelectPart(IExpression expression,char QueryType) throws SQLException, RuntimeException, MultipleRootsException, SQLXMLException
    {
    	String xmlForQueryPart = "";
    	String xmlLetQueryPart = "";
    	String xmlGetLetQueryPart = "";
    	String xmlGetForQueryPart = "";
    	String selectAttribute = "";
    	List<IExpression> expressionList = new ArrayList<IExpression>();
//    	if(QueryType == 'N')
//    	{
    	selectAttribute = "Select distinct " + getAttributes() + " from xmltable('";
 //   	}
//    	else if(QueryType == 'C')
//    	{
//    	selectAttribute = "Select count(*) from (Select distinct upi," + getAttributes() +  " from xmltable('";	
//    	}
    	
    	List<Long> TableId = getTableIdList();
    	for(Long tableListId : TableId)
    	{
    		expressionList = getExpressionList(tableListId);
    		xmlGetForQueryPart += getForPart(tableListId);
    	}
    	for(Long tableListId : TableId)
    	{
    		expressionList = getExpressionList(tableListId);
    		xmlGetLetQueryPart += getLetQuery(expressionList);
    	}
    	xmlForQueryPart = "for " + removeLastComma(xmlGetForQueryPart);
    	xmlLetQueryPart += " let " + removeLastComma(xmlGetLetQueryPart);
    	String whereClause = getWhereClause(TableId);
    	String returnClause = getReturnClause();

    	selectAttribute += xmlForQueryPart + "\n" +xmlLetQueryPart + "\n" + whereClause + "\n" + returnClause;
    	 return selectAttribute;
    }


	private List<IExpression> getExpressionList(Long tableId) throws SQLException {
		List<IExpression> expressionList = new ArrayList<IExpression>();
		int i = 0;
		for(IExpression expression : constraint)
		{
			i++;
			EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
			Long classId = entity.getId();
			Long newtableId = getTableId(classId);
			if(newtableId == tableId)
			{
				expressionList.add(expression);
			}		
		}
		return expressionList;
	}
	private String getReturnClause() {
		StringBuffer buffer =  new StringBuffer();
		buffer.append(" return <return>") ;
		for (IExpression expression : constraint)
		{
			if(expression.isInView())
			{
				EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
				List<AttributeInterface> attributes = (List<AttributeInterface>) entity.getAllAttributes();
				for(AttributeInterface attribute : attributes)
				{
					String attrName = attribute.getName();
					buffer.append("<" + attrName + ">{$" + attrName +  "}</" + attrName + ">");
				}
				
			}
		}
		buffer.append("</return>'");
		return buffer.toString();
	}
	
	
	private String getWhereClause(List<Long> tableId) throws SQLException, RuntimeException, SQLXMLException {
		String startTimeStamp = "";
		String endTimeStamp = "";
		String activeClause = "";
		String researchOptOut = "";
		String whereClause = "where ";
		String getCondition = "";
		String additionalClausei = "";
		String additionalClausej = "";
		String additionalClause = "";
		List<IExpression> expression = new ArrayList<IExpression>();
			for(Long table : tableId)
			{
				String tableName = getTableName(table);
				if(tableName.equalsIgnoreCase("DEMOGRAPHICS"))
				{
				activeClause += " and $" + tableName + table + "/../../../activeUpiFlag = \"A\"";
				researchOptOut += " and $" +  tableName + table + "/../../../researchOptOut = \"N\"" ;	
				}
				startTimeStamp += " and $" + tableName + table + "/../startTimeStamp <= xs:dateTime(\"2008-09-14T22:00:00\")";
				endTimeStamp += " and $" + tableName + table + "/../endTimeStamp >= xs:dateTime(\"2008-09-14T22:00:00\")";
				expression = getExpressionList(table);
				getCondition  += getConditions(expression);
			}
			if(tableId.size() > 1)
			{
				for(int i = 0;i < tableId.size() - 1; i++)
				{
					Long tableIdi = tableId.get(i);
					String tableNamei = getTableName(tableIdi);
					for(int j = i+1;j < tableId.size();j++)
					{					
						Long tableIdj = tableId.get(j);				
						String tableNamej = getTableName(tableIdj);
						additionalClausei = " and $" + tableNamei + tableIdi + "/../../../personUpi = ";
						additionalClausej = "$" + tableNamej + tableIdj + "/../../../personUpi";
						additionalClause += additionalClausei + additionalClausej;
					}
				}
			}
			whereClause += 	removeLastAnd(getCondition) + activeClause + researchOptOut + startTimeStamp + endTimeStamp + additionalClause;
		return whereClause;
	}
	
	
	
	private String getLetQuery(List<IExpression> expressionList) throws SQLException {
		StringBuffer buffer = new StringBuffer();
		for (IExpression allExpression : expressionList)
		{
			if(allExpression.isInView())
			{
				EntityInterface entity = allExpression.getQueryEntity().getDynamicExtensionsEntity();
				Long classId = entity.getId();
				Long tableId = getTableId(classId);
				String tableName = getTableName(tableId);
				String xPath = getXPath(classId);
				List<AttributeInterface> attributes = (List<AttributeInterface>) entity.getAllAttributes();
				for(AttributeInterface attribute : attributes)
				{
					String actualAttributeName = attribute.getName();
					buffer.append( "$" + actualAttributeName + " := $" + tableName + tableId + "/");
					if(!xPath.equals(""))
					{
						buffer.append( xPath + "/");
					} 
					buffer.append(actualAttributeName + ",");
				}
			}
		}
		return buffer.toString();
	}
	
	
	public ResultSet getMysqlConnectivity(String q) throws SQLException
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://ps5286:3306/CIDER","root", "root");
			stmt = conn.createStatement();
			result = stmt.executeQuery(q);
			return result;		
		}
		catch(Exception e)
		{
			System.out.println("In Exception while connecting to MYSQL");
			return null;
		}

		
	}
	
    
    public String getXPath(Long classId) throws SQLException
    {
    	String q2 = "Select XPATH from CIDER_CLASS_XPATH where CLASS_ID = " + classId;
    	ResultSet rs = getMysqlConnectivity(q2);
    	String xpath = "";
    	
		while(rs.next())		
		{
			xpath = rs.getString("XPATH");
		}	
		return xpath;
    }
    
    
    
    private String removeLastComma(String select) {
      String selectString = "";
        if (select.endsWith(",")) {
        	selectString = select.substring(0, select.length() - 1);
        }
        return selectString;
    }
    
    private Long getTableId(Long classId) throws SQLException
    {
    	String q3 = "Select TABLE_ID from CIDER_TABLE_CLASS where CLASS_ID = " + classId;
    	ResultSet rs = getMysqlConnectivity(q3);
    	Long tableId = null; 	
		while(rs.next())		
		{
			tableId = rs.getLong("TABLE_ID");
		}
		return tableId;
    	
    }
    
    private String getTableName(Long tableId) throws SQLException {
    	String tableName = "";
		String q4 = "Select TABLE_NAME from CIDER_TABLE where TABLE_ID = " + tableId;
		ResultSet rs = getMysqlConnectivity(q4);
		while(rs.next())		
		{
			tableName = rs.getString("TABLE_NAME");
		}
		return tableName;

    }
    
    public String getColumnName(Long tableId) throws SQLException {
    	String tableName = "";
		String q5 = "Select TABLE_COLUMN from CIDER_TABLE where TABLE_ID = " + tableId;
		ResultSet rs = getMysqlConnectivity(q5);
		while(rs.next())		
		{
			tableName = rs.getString("TABLE_COLUMN");
		}
		return tableName;

    }
    
    private String getRootPath(Long tableId) throws SQLException {
    	String rootPath = "";
		String q6 = "Select ROOT_ELEMENT from CIDER_TABLE where TABLE_ID = " + tableId;
		ResultSet rs = getMysqlConnectivity(q6);
		while(rs.next())		
		{
			rootPath = rs.getString("ROOT_ELEMENT");
		}
		return rootPath;
	}
    
    /**
     * To process all child operands of the expression.
     * 
     * @param expression the reference to Expression.
     * @return the SQL representation for the child operands.
     * @throws SqlException When there is error in the passed IQuery object.
     * @throws SQLXMLException 
     * @throws SQLException 
     */
    private String processOperands(List<IExpression> expression) throws RuntimeException, SQLXMLException, SQLException {
    	
    	 StringBuffer buffer = new StringBuffer();
         String operandQuery = "";
         
         for(IExpression expressions : expression)
         {
        	 
        	 int noOfRules = expressions.numberOfOperands();
        	 for (int i = 0; i < noOfRules; i++) 
        	 {
        		 IExpressionOperand operand = expressions.getOperand(i);         
        		 if (operand instanceof IRule) 
        		 {
        			 operandQuery += getQueryCondition((IRule) operand) + " and "; // Processing Rule.

        		 }
        		 else if(operand instanceof IExpression)
        		 {
        			 operandQuery += "";
        		 }
            }
        	 
            
        }
         
        buffer.append(operandQuery);
        return buffer.toString();
    }
    
    private String removeLastAnd(String select) {
    	String selectString = select;
        if (select.endsWith("and ")) 
        {
        	selectString = selectString.substring(0, selectString.length() - 5);
        }
        return selectString;
    }
     
    /**
     * To get the SQL representation of the Rule.
     * 
     * @param rule The reference to Rule.
     * @return The SQL representation of the Rule.
     * @throws SqlException When there is error in the passed IQuery object.
     * @throws SQLException 
     * @throws SQLXMLException 
     */
    String getQueryCondition(IRule rule) throws SQLException, SQLXMLException {
        StringBuffer buffer = new StringBuffer("");
        
        
        int noOfConditions = rule.size();
        if (noOfConditions == 0) {
            throw new SQLXMLException("No conditions defined in the Rule!!!");
        }
        for (int i = 0; i < noOfConditions; i++)
        {
            String condition = getQueryCondition(rule.getCondition(i), rule.getContainingExpression());
            if (i != noOfConditions - 1)
            {
                buffer.append(condition + " " + LogicalOperator.And + " ");
            } 
            else 
            {
                buffer.append(condition);
            }
        }
        return buffer.toString();
    }
    
    String getQueryCondition(ICondition condition, IExpression expression) throws SQLException {
        String query = null;
        
        EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
    	Long classId  =  entity.getId();
    	String xPath = getXPath(classId);
    	Long tableId = getTableId(classId);
    	String tableName = getTableName(tableId);
    	
      
        AttributeInterface attribute = condition.getAttribute();
        String attributeName = attribute.getName();
        
        String actualAtrributeName = "$" + tableName + tableId + "/" + xPath + "/" + attributeName;
        query = processComparisionOperator(condition, actualAtrributeName);       
        return query;
    }
    

    
    private String processComparisionOperator(ICondition condition, String attributeName) {
    	
    	AttributeTypeInformationInterface dataType = condition.getAttribute().getAttributeTypeInformation();
        RelationalOperator operator = condition.getRelationalOperator();
        List<String> values = condition.getValues();
        String value = "";
        for(int i = 0;i < values.size();++i)
        {
        	value += values.get(i) + ",";
        }
        
        value = removeLastComma(value);
        
        if(dataType instanceof StringTypeInformationInterface)
        {
        	value = "\"" + value + "\"";
        }
        else if(dataType instanceof DateTypeInformationInterface)
        {
        	value = "xs:dateTime(\"" + value + "\")";
        }

        value = "(" + value + ")";
        String sql = attributeName + " " + RelationalOperator.getSQL(operator) + " " + value ;
        return sql;
    }
    
    
    
    private List<Long> getTableIdList() throws SQLException
    {
    	List<Long> existTable =  new ArrayList<Long>();
    	for(IExpression expression : constraint)
    	{
    		EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
    		Long classId = entity.getId();
    		Long tableId = getTableId(classId);
    		if((!existTable.contains(tableId)))
    		{
    			existTable.add(tableId); 			
    		}
    	}
    	return existTable;
    }
    
    
    
    private String getForPart(Long tableId) throws SQLException, RuntimeException, SQLXMLException
    {
    	
    	String tablename = getTableName(tableId);
    	String columnName = getColumnName(tableId);
    	String rootPath = getRootPath(tableId); 	
    	String basePath = "(\"" + tablename + "." + columnName + "\")" + "/" + rootPath;		
    	String forClause = "$" + tablename + tableId.toString() + " in db2-fn:xmlcolumn" + basePath  + ",";
    	return forClause;
    }
    
    
    private String getConditions(List<IExpression> expression) throws RuntimeException, SQLXMLException, SQLException
    {
    	String operandRules = processOperands(expression);
    	return operandRules;
    }
    

	
	private String getAttributes() throws MultipleRootsException, SQLException
	{	
		StringBuffer attributeName = new StringBuffer();
		for(IExpression allExpression : constraint)
		{
			if(allExpression.isInView())
			{
				EntityInterface entity = allExpression.getQueryEntity().getDynamicExtensionsEntity();
				List<AttributeInterface> attributes = (List<AttributeInterface>) entity.getAllAttributes();
				for(AttributeInterface attribute : attributes)
				{
					attributeName.append(attribute.getName() + ",");
				}
			}				
		}
		return removeLastComma(attributeName.toString());
	}

   public Map<String, IOutputTerm> getOutputTermsColumns()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List<OutputTreeDataNode> getRootOutputTreeNodeList()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Map<AttributeInterface, String> getAttributeColumnNameMap()
	{
		// TODO Auto-generated method stub
		return null;
	}
	

	

		

}