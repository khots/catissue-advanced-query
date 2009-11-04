
package edu.wustl.query.querysuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;
import edu.wustl.common.query.util.Constants;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.utils.ConstraintsObjectBuilder;
import edu.wustl.common.querysuite.utils.IConstraintsObjectBuilderInterface;
import edu.wustl.query.util.querysuite.QueryAddContainmentsUtil;
import edu.wustl.query.util.querysuite.ResultsViewTreeUtil;

/**
* Test case to test QueryAddContainmentsUtil
*
* @author vijay_pande
*
*/
public class QueryAddContainmentsUtilTestCases extends TestCase
{

	
	/**
	* Test the Method: getMainEntityContainments(Map,List,QueryableObjectInterface)
	* 
	*/
	public void testGetMainEntityContainments() throws Exception
	{
		IQuery query = QueryBuilder.skeletalRaceGenderAddressQuery();
		IExpression expressionPerson = query.getConstraints().getRootExpression();
		QueryableObjectInterface mainEntity = expressionPerson.getQueryEntity()
				.getDynamicExtensionsEntity();

		HashMap<QueryableObjectInterface, List<QueryableObjectInterface>> partentChildEntityMap = new HashMap<QueryableObjectInterface, List<QueryableObjectInterface>>();
		ArrayList<QueryableObjectInterface> mainEntityContainmentList = new ArrayList<QueryableObjectInterface>();
		QueryAddContainmentsUtil.getMainEntityContainments(partentChildEntityMap,
				mainEntityContainmentList, mainEntity);
		if (partentChildEntityMap.isEmpty())
		{
			fail();
		}
	}

	/**
	* Test the Method: linkTwoNodes(int,int,IPath,IConstraintsObjectBuilderInterface)
	* 
	*/
	public void testLinkTwoNodes() throws Exception
	{

		IParameterizedQuery query = null;
		query = QueryObjectFactory.createParameterizedQuery();
		IConstraints constraints = QueryObjectFactory.createConstraints();
		query.setConstraints(constraints);

		IExpression parentExpression = QueryBuilder.createExpression(constraints, null,
				Constants.PERSON, false);
		IExpression childExpresion = QueryBuilder.createExpression(constraints, null,
				Constants.LABORATORY_PROCEDURE, false);

		IConstraintsObjectBuilderInterface iconstraintsobjectbuilderinterface = new ConstraintsObjectBuilder(
				query);

		IPath ipath = ResultsViewTreeUtil.getIPath(parentExpression.getQueryEntity()
				.getDynamicExtensionsEntity(), childExpresion.getQueryEntity()
				.getDynamicExtensionsEntity());

		//Call to the Utility Method
		QueryAddContainmentsUtil.linkTwoNodes(parentExpression.getExpressionId(), childExpresion
				.getExpressionId(), ipath, iconstraintsobjectbuilderinterface);

	}
}
