
package edu.wustl.query.flex.dag;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import edu.wustl.cab2b.client.ui.util.ClientConstants;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.global.VIProperties;

public class DAGNode implements Externalizable, Comparable<DAGNode>
{

	private String nodeName = "";
	private int expressionId = 0;
	private String toolTip = "";
	/** operator between attribute and association*/
	private String operator = "";
	private String nodeType = DAGConstant.CONSTRAINT_VIEW_NODE;
	public List<DAGNode> associationList = new ArrayList<DAGNode>();
	public List<String> operatorList = new ArrayList<String>();
	//private List<String> pathList  = new ArrayList<String>();
	public List<DAGPath> dagpathList = new ArrayList<DAGPath>();
	private String errorMsg = "";

	private int x;
	private int y;

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public DAGNode()
	{
		setOperator(ClientConstants.OPERATOR_AND);
	}

	public String getNodeName()
	{
		return nodeName;
	}

	public void setNodeName(String nodeName)
	{
		this.nodeName = nodeName;
	}

	/**
	 * @param expression
	 *            Expression to set
	 */
	public void setExpressionId(int expressionId)
	{
		this.expressionId = expressionId;

	}

	public int getExpressionId()
	{
		return expressionId;
	}

	public void setToolTip(IExpression expression)
	{
		StringBuffer sb = new StringBuffer(128);
		IRule rule = null;
		if (!(expression).containsRule())
		{
			return;
		}
		else
		{
			rule = (IRule) expression.getOperand(0);
		}
		int totalConditions = rule.size();

		if (totalConditions > 0)
		{
			sb.append("Condition(s) on  \n");
			generateFormattedString(sb, rule, totalConditions);
		}
		else
		{
			sb.append("No Condition Added \n");
		}

		this.toolTip = sb.toString();
	}

	private void generateFormattedString(StringBuffer sb, IRule rule, int totalConditions)
	{
		int condnctr = 0;
		for (int i = 0; i < totalConditions; i++)
		{
			ICondition condition = rule.getCondition(i);
			if (!condition.getAttribute().isTagPresent(Constants.TAGGED_VALUE_VI_HIDDEN))
			{
				sb.append((condnctr + 1) + ") ");
//				String formattedAttributeName = CommonUtils.getFormattedString(condition
//						.getAttribute().getName());
				String formattedAttributeName = Utility.getFormattedString(condition
						.getAttribute().getDisplayName());

				sb.append(formattedAttributeName).append(' ');
				
				List<String> values = new ArrayList<String>();
				values.addAll(condition.getValues());
				QueryableObjectInterface parentEntity = condition.getAttribute().getActualEntity();
				if (parentEntity != null
						&& parentEntity.getName().equals(VIProperties.medClassName)
						&& condition.getAttribute().getName().equals("name"))
				{
					List<String> newValues = new ArrayList<String>();
					for (String conValue : values)
					{
						String[] nameValue = conValue.split(Constants.ID_DEL);
						if(nameValue.length>2)
						{
							newValues.add(nameValue[2]);
						}
					}
					values.clear();
					values.addAll(newValues);
				}
				RelationalOperator operator = condition.getRelationalOperator();
				sb.append(
						Utility.displayStringForRelationalOperator(operator)).append(' ');
				checkEquality(sb, values);
				sb.append('\n');
				condnctr++;
			}
		}
	}

	private void checkEquality(StringBuffer sb, List<String> values)
	{
		int size = values.size();
		if (size > 0)// Special case for 'Not Equals and Equals
		{
			if (size == 1)
			{
				sb.append(values.get(0));
			}
			else
			{
				sb.append('(');
				generateFomulaString(sb, values, size);
				sb.append(')');
			}
		}
	}

	private void generateFomulaString(StringBuffer sb, List<String> values, int size)
	{
		if (values.get(0).indexOf(",") != -1)
		{
			sb.append('\'');
			sb.append(values.get(0));
			sb.append('\'');
		}
		else
		{
			sb.append(values.get(0));
		}
		for (int j = 1; j < size; j++)
		{
			sb.append(", ");
			if (values.get(j).indexOf(",") != -1)
			{
				sb.append('\'');
				sb.append(values.get(j));
				sb.append('\'');
			}
			else
			{
				sb.append(values.get(j));
			}
		}
	}

	public String getToolTip()
	{
		return toolTip;
	}

	public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
		// TODO Auto-generated method stub
		nodeName = in.readUTF();
		toolTip = in.readUTF();
		expressionId = in.readInt();
		operator = in.readUTF();
		nodeType = in.readUTF();
		associationList = (List<DAGNode>) in.readObject();
		operatorList = (List<String>) in.readObject();
		//	pathList = (List<String>) in.readObject();
		dagpathList = (List<DAGPath>) in.readObject();
		errorMsg = in.readUTF();
		x = in.readInt();
		y = in.readInt();
	}

	public void writeExternal(ObjectOutput out) throws IOException
	{
		// TODO Auto-generated method stub
		out.writeUTF(nodeName);
		out.writeUTF(toolTip);
		out.writeInt(expressionId);
		out.writeUTF(operator);
		out.writeUTF(nodeType);
		out.writeObject(associationList);
		out.writeObject(operatorList);
		//out.writeObject(pathList);
		out.writeObject(dagpathList);
		out.writeUTF(errorMsg);
		out.writeInt(x);
		out.writeInt(y);
	}

	@Override
	public String toString()
	{
		StringBuffer buff = new StringBuffer(128);
		buff.append("\n nodeName: ").append(nodeName).append("\n toolTip: ").append(toolTip)
				.append("\n expressionId: ").append(expressionId).append(
						"\n operator:").append(
								operator);
		return buff.toString();
	}

	public int compareTo(DAGNode node)
	{
		// TODO Auto-generated method stub

		return 0;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;
		if (obj!=null && obj instanceof DAGNode) {
			DAGNode node = (DAGNode) obj;
			if (this.expressionId == node.expressionId) {
				equal = true;
			}
		}
		return equal;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	public List<DAGNode> getAssociationList()
	{
		return associationList;
	}

	public void setAssociationList(DAGNode node)
	{
		this.associationList.add(node);
	}

	public List<String> getOperatorList()
	{
		return operatorList;
	}

	public void setOperatorList(String operator)
	{
		this.operatorList.add(operator);
	}

	/*	public List<String> getPathList() {
			return pathList;
		}


		public void setPathList(String path) {
			this.pathList.add(path);
		}*/

	public String getNodeType()
	{
		return nodeType;
	}

	public void setNodeType(String nodeType)
	{
		this.nodeType = nodeType;
	}

	public List<DAGPath> getDagpathList()
	{
		return dagpathList;
	}

	public void setDagpathList(DAGPath dagpath)
	{
		this.dagpathList.add(dagpath);
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}
}
