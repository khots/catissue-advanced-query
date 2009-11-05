
package edu.wustl.query.flex.dag;

/**
 *
 * @author baljeet_dhindhwal
 * @version 1.0
 *
 */
public class DAGConstant
{
	/**  Constant for  */
	public static final String QUERY_OBJECT = "queryObject";
	/**  Constant for  */
	public static final String CONSTRAINT_VIEW_NODE = "ConstraintViewNode";
	/**  Constant for  */
	public static final String VIEW_ONLY_NODE = "ViewOnlyNode";
	/**  Constant for  */
	public static final String CONSTRAINT_ONLY_NODE = "ConstraintOnlyNode";
	/**  Constant for  */
	public static final String HTML_STR = "HTMLSTR";
	/**  Constant for  */
	public static final String EXPRESSION = "EXPRESSION";
	/**  Constant for  */
	public static final String ADD_LIMIT = "Add";
	/**  Constant for  */
	public static final String EDIT_LIMIT = "Edit";
	/**  Constant for  */
	public static final String DAG_NODE_LIST = "dagNodeList";
	/**  Constant for  */
	public static final String CUSTOM_FORMULA_NODE_LIST = "customFormulaNodeList";
	/**  Constant for  */
	public static final String SINGLE_NODE_CF_NODE_LIST = "singleNodeCFList";
	/**  Constant for  */
	public static final String REPAINT_OPERATION = "rePaint";
	/**  Constant for  */
	public static final String ISREPAINT = "isRepaint";
	/**  Constant for  */
	public static final String ISREPAINT_TRUE = "true";
	/**  Constant for  */
	public static final String ISREPAINT_FALSE = "false";
	/**  Constant for  */
	public static final String EDIT_OPERATION = "edit";
	/**  Constant for  */
	public static final String REPAINT_EDIT = "rePaintEdit";
	/**  Constant for  */
	public static final String REPAINT_CREATE = "rePaintCreate";
	/**  Constant for  */
	public static final String TQUIMap = "TQUIMap";
	/**  Constant for  */
	public static final String ADD_LIMIT_VIEW = "AddLimit";
	/**  Constant for  */
	public static final String RESULT_VIEW = "Result";
	/**  Constant for  */
	public static final String NULL_STRING = "null";

	//Error Codes
	/**  Constant for  success.*/
	public static final int SUCCESS = 0;
	/**  Constant for  empty dag.*/
	public static final int EMPTY_DAG = 1;
	/**  Constant for multiple roots. */
	public static final int MULTIPLE_ROOT = 2;
	/**  Constant for  no results.*/
	public static final int NO_RESULT_PRESENT = 3;
	/**  Constant for  sql exception.*/
	public static final int SQL_EXCEPTION = 4;
	/**  Constant for  dao exception.*/
	public static final int DAO_EXCEPTION = 5;
	/**  Constant for  class not found error.*/
	public static final int CLASS_NOT_FOUND = 6;
	/**  Constant for  no paths present error.*/
	public static final int NO_PATHS_PRESENT = 7;
	/**  Constant for  DE exception.*/
	public static final int DYNAMIC_EXTENSION_EXCEPTION = 8;
	/**  Constant for  cyclic graph.*/
	public static final int CYCLIC_GRAPH = 9;

	/**
	 *
	 */
	public static final String DATE_ATTRIBUTE = "Date";

	/**
	 *
	 */
    public static final String INTEGER_ATTRIBUTE  =  "Integer";
    /**
     * constant for invalid nodes in DAG.
     */
    public static final String ERROR_NODES_INVALID  =  "NodesInvalid";
    /**
     * constant for invalid entity names for quantitative attributes.
     */
    public static final String ENTITY_NAME_INVALID  =  "EntityNameInvalid";
    /**
     * constant for invalid parent for quantitative attributes.
     */
    public static final String PARENT_INVALID  =  "ParentInvalid";
}
