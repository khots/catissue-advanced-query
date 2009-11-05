
package edu.wustl.query.util.global;

import java.awt.Color;

/**
 *
 * @author baljeet_dhindhwal
 * @version 1.0
 *
 */
public class Constants
{

	//Shopping cart related
	/**  Constant for QUERY_SHOPPING_CART.  */
	public static final String QUERY_SHOPPING_CART = "queryShoppingCart";
	/**  Constant for  CHECK_ALL_ACROSS_ALL_PAGES.*/
	public static final String CHECK_ALL_ACROSS_ALL_PAGES = "isCheckAllAcrossAllChecked";
	/**  Constant for  CHECK_ALL_PAGES.*/
	public static final String CHECK_ALL_PAGES = "checkAllPages";
	/**  Constant for  SELECTED_COLUMN_META_DATA.*/
	public static final String SELECTED_COLUMN_META_DATA = "selectedColumnMetaData";
	/**  Constant for  DELETE.*/
	public static final String DELETE = "delete";
	/**  Constant for MESSAGE_FOR_ORDERING.*/
	public static final String MESSAGE_FOR_ORDERING = "validationMessageForOrdering";
	/**  Constant for PAGEOF_QUERY_MODULE.*/
	public static final String PAGEOF_QUERY_MODULE = "pageOfQueryModule";
	/**  Constant for IS_LIST_EMPTY.*/
	public static final String IS_LIST_EMPTY = "isListEmpty";
	/**  Constant for SHOPPING_CART_DELETE.*/
	public static final String SHOPPING_CART_DELETE = "shoppingCartDelete";
	/**  Constant for SHOPPING_CART_ADD.*/
	public static final String SHOPPING_CART_ADD = "shoppingCartAdd";
	/**  Constant for PAGINATION_DATA_LIST.*/
	public static final String PAGINATION_DATA_LIST = "paginationDataList";
	/**  Constant for LABEL_TREE_NODE.*/
	public static final String LABEL_TREE_NODE = "Label";
	/**  Constant for DATA_TREE_NODE.*/
	public static final String DATA_TREE_NODE = "Data";
	/**  Constant for TREE_NODE_TYPE.*/
	public static final String TREE_NODE_TYPE = "nodeType";
	/**  Constant for HAS_IDENTIFIED_CONDITION.*/
	//public static final String HAS_IDENTIFIED_CONDITION = "hasConditionOnIdentifiedField";
	/**  Constant for Response "wait".*/
	public static final String RESPONSE_WAIT = "wait";
	/**  Constant for NODE_SEPARATOR.*/
	public static final String NODE_SEPARATOR = "::";
	/**  Constant for NODE_DATA_SEPARATOR.*/
	public static final String NODE_DATA_SEPARATOR = "!_!";
	/**  Constant for UNDERSCORE.*/
	public static final String UNDERSCORE = "_";
	/**  Constant for ADD_TO_ORDER_LIST.*/
	public static final String ADD_TO_ORDER_LIST = "addToOrderList";
	/**  Constant for REQUEST_TO_ORDER.*/
	public static final String REQUEST_TO_ORDER = "requestToOrder";
	/**  Constant for EDIT_QUERY.*/
	public static final String EDIT_QUERY = "editQuery";
	/**  Constant for BULK_TRANSFERS.*/
	public static final String BULK_TRANSFERS = "bulkTransfers";
	/**  Constant for BULK_DISPOSALS.*/
	public static final String BULK_DISPOSALS = "bulkDisposals";
	/**  Constant for OUTPUT_TERMS_COLUMNS.*/
	public static final String OUTPUT_TERMS_COLUMNS = "outputTermsColumns";
	/**  Constant for SUCCESS.*/
	public static final String SUCCESS = "success";
	/**  Constant for ATTRIBUTE_SEPARATOR.*/
	public static final String ATTRIBUTE_SEPARATOR = "|";
	/**  Constant for KEY_SEPARATOR.*/
	public static final String KEY_SEPARATOR = "*&*";
	/**  Constant for KEY_CODE.*/
	public static final String KEY_CODE = "key";
	/**  Constant for EXPORT_DATA_LIST.*/
	public static final String EXPORT_DATA_LIST = "exportDataList";
	/**  Constant for ENTITY_IDS_MAP.*/
	public static final String ENTITY_IDS_MAP = "entityIdsMap";
	/**  Constant for QUERY_STRING.*/
	public static final String QUERY_STRING = "query_String";
	/**  Constant for FINISH.*/
	public static final String FINISH = "finish";
	/**  Constant for QUERY_REASUL_OBJECT_DATA_MAP.*/
	public static final String QUERY_REASUL_OBJECT_DATA_MAP = "queryReasultObjectDataMap";
	/**  Constant for CONTAINTMENT_ASSOCIATION.*/
	public static final String CONTAINTMENT_ASSOCIATION = "CONTAINTMENT";
	/**  Constant for MAIN_ENTITY_MAP.*/
	public static final String MAIN_ENTITY_MAP = "mainEntityMap";
	/**  Constant for SPECIMENT_VIEW_ATTRIBUTE.*/
	public static final String SPECIMENT_VIEW_ATTRIBUTE = "defaultViewAttribute";
	/**  Constant for NO_MAIN_OBJECT_IN_QUERY.*/
	public static final String NO_MAIN_OBJECT_IN_QUERY = "noMainObjectInQuery";
	/**  Constant for QUERY_ALREADY_DELETED.*/
	public static final String QUERY_ALREADY_DELETED = "queryAlreadyDeleted";
	/**  Constant for BACK.*/
	public static final String BACK = "back";
	/**  Constant for RESTORE.*/
	public static final String RESTORE = "restore";
	/**  Constant for SELECTED_NAME_VALUE_LIST.*/
	public static final String SELECTED_NAME_VALUE_LIST = "selectedColumnNameValueBeanList";
	/**  Constant for TREE_DATA.*/
	public static final String TREE_DATA = "treeData";
	/**  Constant for ID_NODES_MAP.*/
	public static final String ID_NODES_MAP = "idNodesMap";
	/**  Constant for DEFINE_RESULTS_VIEW.*/
	public static final String DEFINE_RESULTS_VIEW = "DefineResultsView";
	/**  Constant for CURRENT_PAGE.*/
	public static final String CURRENT_PAGE = "currentPage";
	/**  Constant for ADD_LIMITS.*/
	public static final String ADD_LIMITS = "AddLimits";
	/**  Constant for QUERY_INTERFACE_BIZLOGIC_ID.*/
	public static final int QUERY_INTERFACE_BIZLOGIC_ID = 67;
	/**  Constant for SQL.*/
	public static final String SQL = "SQL";
	/**  Constant for ID_COLUMN_ID.*/
	public static final String ID_COLUMN_ID = "ID_COLUMN_ID";
	/**  Constant for ID.*/
	public static final String ID = "id";
	/**  Constant for SAVE_TREE_NODE_LIST.*/
	public static final String SAVE_TREE_NODE_LIST = "rootOutputTreeNodeList";
	/**  Constant for ATTRIBUTE_COLUMN_NAME_MAP.*/
	public static final String ATTRIBUTE_COLUMN_NAME_MAP = "attributeColumnNameMap";
	/**  Constant for IS_SAVED_QUERY.*/
	public static final String IS_SAVED_QUERY = "isSavedQuery";
	/**  Constant for TREE_ROOTS.*/
	public static final String TREE_ROOTS = "treeRoots";
	/**  Constant for NO_OF_TREES.*/
	public static final String NO_OF_TREES = "noOfTrees";
	/**  Constant for TREE_EXPANSION_LIMIT.*/
	public static final String TREE_EXPANSION_LIMIT = "treeExpansionLimit";
	/**  Constant for TREE_NODE_LIMIT_EXCEEDED_RECORDS.*/
	//public static final String TREE_NODE_LIMIT_EXCEEDED_RECORDS = "treeNodeLimitExceededRecords";
	/**  Constant for VIEW_LIMITED_RECORDS.*/
	public static final String VIEW_LIMITED_RECORDS = "viewLimitedRecords";
	/**  Constant for SAVE_GENERATED_SQL.*/
	public static final String SAVE_GENERATED_SQL = "sql";
	/**  Constant for STATIC_ENTITY_ID.*/
	public static final String STATIC_ENTITY_ID = "staticEntityId";
	/**  Constant for TREENO_ZERO.*/
	public static final String TREENO_ZERO = "zero";
	/**  Constant for COLUMN_NAMES.*/
	public static final String COLUMN_NAMES = "columnNames";
	/**  Constant for INDEX.*/
	public static final String INDEX = "index";
	/**  Constant for ATTRIBUTE_NAMES_FOR_TREENODE_LABEL.*/
	//public static final String[] ATTRIBUTE_NAMES_FOR_TREENODE_LABEL = {"firstName", "lastName",
	//"title", "name", "label", "shorttitle"};
	/**  Constant for  COLUMN_NAME.*/
	public static final String COLUMN_NAME = "Column";
	/**  Constant for ON.*/
	public static final String ON = " ON ";
	/**  Constant for DB_OPERATION_ERROR.*/
	public static final String DB_OPERATION_ERROR = "db.operation.error";
	/**  Constant for ABSTRACT_QUERY_EXECUTOR.*/
	public static final String ABSTRACT_QUERY_EXECUTOR = "AbstractQueryExecutor";
	/**  Constant for OFF.*/
	public static final String OFF = "off";
	/**  Constant for PAGE_OF_QUERY_MODULE.*/
	public static final String PAGE_OF_QUERY_MODULE = "pageOfQueryModule";
	/**  Constant for PAGE_OF_QUERY_RESULTS.*/
	public static final String PAGE_OF_QUERY_RESULTS = "pageOfQueryResults";
	/**  Constant for PAGEOF_QUERY_RESULTS.*/
	public static final String PAGEOF_QUERY_RESULTS = "pageOfQueryResults";
	/**  Constant for RANDOM_NUMBER.*/
	public static final String RANDOM_NUMBER = "randomNumber";
	/**  Constant for IS_NOT_NULL.*/
	public static final String IS_NOT_NULL = "is not null";
	/**  Constant for IS_NULL.*/
	public static final String IS_NULL = "is null";
	/**  Constant for IN.*/
	public static final String IN = "in";
	/**  Constant for Join 'ON' clause.*/
	public static final String JOIN_ON_CLAUSE = " ON ";
	/**  Constant for SYNONYM_LIKE.*/
	public static final String SYNONYM_LIKE = "synonym like '";
	/**  Constant for Static_Entity_Id.*/
	public static final String Static_Entity_Id = "staticEntityId";
	/**  Constant for Closing_Item.*/
	public static final String Closing_Item = "</item>";
	/**  Constant for Not_In.*/
	public static final String Not_In = "Not In";
	/**  Constant for Equals.*/
	public static final String Equals = "Equals";
	/**  Constant for Not_Equals.*/
	public static final String Not_Equals = "Not Equals";
	/**  Constant for Between.*/
	public static final String Between = "Between";
	/**  Constant for Less_Than.*/
	public static final String Less_Than = "Less than";
	/**  Constant for Less_Than_Or_Equals.*/
	public static final String Less_Than_Or_Equals = "Less than or Equal to";
	/**  Constant for Greater_Than.*/
	public static final String Greater_Than = "Greater than";
	/**  Constant for Greater_Than_Or_Equals.*/
	public static final String Greater_Than_Or_Equals = "Greater than or Equal to";
	/**  Constant for Contains.*/
	public static final String Contains = "Contains";
	/**  Constant for STRATS_WITH.*/
	public static final String STRATS_WITH = "Starts With";
	/**  Constant for ENDS_WITH.*/
	public static final String ENDS_WITH = "Ends With";
	/**  Constant for NOT_BETWEEN.*/
	public static final String NOT_BETWEEN = "Not Between";
	/**  Constant for INVALID_CONDITION_VALUES.*/
	public static final String INVALID_CONDITION_VALUES = "InvalidValues";
	/**  Constant for SAVE_QUERY_PAGE.*/
	public static final String SAVE_QUERY_PAGE = "Save Query Page";
	/**  Constant for EXECUTE_QUERY_PAGE.*/
	public static final String EXECUTE_QUERY_PAGE = "Execute Query Page";
	/**  Constant for FETCH_QUERY_ACTION.*/
	public static final String FETCH_QUERY_ACTION = "FetchQuery.do";
	/**  Constant for EXECUTE_QUERY_ACTION.*/
	public static final String EXECUTE_QUERY_ACTION = "ExecuteQueryAction.do";
	/**  Constant for EXECUTE_QUERY.*/
	public static final String EXECUTE_QUERY = "executeQuery";
	/**  Constant for SHOPPING_CART_FILE_NAME.*/
	public static final String SHOPPING_CART_FILE_NAME = "MyList.csv";
	/**  Constant for APPLICATION_DOWNLOAD.*/
	public static final String APPLICATION_DOWNLOAD = "application/download";
	/**  Constant for DOT_CSV.*/
	public static final String DOT_CSV = ".csv";
	/**  Constant for HTML_CONTENTS.*/
	public static final String HTML_CONTENTS = "HTMLContents";
	/**  Constant for INPUT_APPLET_DATA.*/
	public static final String INPUT_APPLET_DATA = "inputAppletData";
	/**  Constant for SHOW_ALL.*/
	public static final String SHOW_ALL = "showall";
	/**  Constant for SHOW_ALL_ATTRIBUTE.*/
	public static final String SHOW_ALL_ATTRIBUTE = "Show all attributes";
	/**  Constant for SHOW_SELECTED_ATTRIBUTE.*/
	public static final String SHOW_SELECTED_ATTRIBUTE = "Show selected attributes";
	/**  Constant for ADD_EDIT_PAGE.*/
	public static final String ADD_EDIT_PAGE = "Add Edit Page";
	/**  Constant for IS_QUERY_SAVED.*/
	public static final String IS_QUERY_SAVED = "isQuerySaved";
	/**  Constant for CONDITIONLIST.*/
	public static final String CONDITIONLIST = "conditionList";
	/**  Constant for CHILDRENLISTMAP.*/
	public static final String CHILDRENLISTMAP = "childrenList";
	/**  Constant for QUERY_SAVED.*/
	public static final String QUERY_SAVED = "querySaved";
	/**  Constant for Query_Type.*/
	public static final String Query_Type = "queryType";
	/**  Constant for DISPLAY_NAME_FOR_CONDITION.*/
	public static final String DISPLAY_NAME_FOR_CONDITION = "_displayName";
	/**  Constant for SHOW_ALL_LINK.*/
	public static final String SHOW_ALL_LINK = "showAllLink";
	/**  Constant for VIEW_ALL_RECORDS.*/
	public static final String VIEW_ALL_RECORDS = "viewAllRecords";
	/**  Constant for POPUP_MESSAGE.*/
	public static final String POPUP_MESSAGE = "popupMessage";
	/**  Constant for ID_COLUMNS_MAP.*/
	public static final String ID_COLUMNS_MAP = "idColumnsMap";
	/**  Constant for TREE_NODE_ID.*/
	public static final String TREE_NODE_ID = "nodeId";
	/**  Constant for HASHED_NODE_ID.*/
	public static final String HASHED_NODE_ID = "-1";
	/**  Constant for BUTTON_CLICKED.*/
	public static final String BUTTON_CLICKED = "buttonClicked";
	/**  Constant for UPDATE_SESSION_DATA.*/
	public static final String UPDATE_SESSION_DATA = "updateSessionData";
	/**  Constant for EVENT_PARAMETERS_LIST.*/
	public static final String EVENT_PARAMETERS_LIST = "eventParametersList";
	/**  Constant for VIEW.*/
	public static final String VIEW = "view";
	/**  Constant for APPLET_SERVER_URL_PARAM_NAME.*/
	public static final String APPLET_SERVER_URL_PARAM_NAME = "serverURL";
	/**  Constant for TEMP_OUPUT_TREE_TABLE_NAME.*/
	public static final String TEMP_OUPUT_TREE_TABLE_NAME = "TEMP_OUTPUTTREE";
	/**  Constant for CREATE_TABLE.*/
	public static final String CREATE_TABLE = "Create table ";
	/**  Constant for AS.*/
	public static final String AS = " as ";
	/**  Constant for TREE_NODE_FONT.*/
	public static final String TREE_NODE_FONT = "<font color='#FF9BFF' face='Verdana'><i>";
	/**  Constant for TREE_NODE_FONT_CLOSE.*/
	public static final String TREE_NODE_FONT_CLOSE = "</i></font>";
	/**  Constant for ZERO_ID.*/
	public static final String ZERO_ID = "0";
	/**  Constant for NULL_ID.*/
	public static final String NULL_ID = "NULL";
	/**  Constant for UNIQUE_ID_SEPARATOR.*/
	public static final String UNIQUE_ID_SEPARATOR = "UQ";
	/**  Constant for SELECT_DISTINCT.*/
	public static final String SELECT_DISTINCT = "select distinct ";
	/**  Constant for SELECT.*/
	public static final String SELECT = "SELECT ";
	/**  Constant for FILE_TYPE.*/
	public static final String FILE_TYPE = "file";
	/**  Constant for FROM.*/
	public static final String FROM = " from ";
	/**  Constant for WHERE.*/
	public static final String WHERE = " where ";
	/**  Constant for LIKE.*/
	public static final String LIKE = " LIKE ";
	/**  Constant for LEFT_JOIN.*/
	public static final String LEFT_JOIN = " LEFT JOIN ";
	/**  Constant for INNER_JOIN.*/
	public static final String INNER_JOIN = " INNER JOIN ";
	/**  Constant for GROUP_BY Clause.*/
	public static final String GROUP_BY_CLAUSE = " GROUP BY ";
	/**  Constant for HASHED_OUT.*/
	public static final String HASHED_OUT = "####";
	/**  Constant for RESPONSE_SEPRATOR.*/
	public static final String RESPONSE_SEPRATOR = "!####!";
	/**  Constant for DYNAMIC_UI_XML.*/
	public static final String DYNAMIC_UI_XML = "dynamicUI.xml";
	/**  Constant for DATE.*/
	public static final String DATE = "date";
	/**  Constant for Calendar.*/
	public static final String Calendar = "Calendar";
	/**  Constant for DATE_FORMAT.*/
	public static final String DATE_FORMAT = "MM-dd-yyyy";
	/**  Constant for DEFINE_SEARCH_RULES.*/
	public static final String DEFINE_SEARCH_RULES = "Define Limits For";
	/**  Constant for CLASSES_PRESENT_IN_QUERY.*/
	public static final String CLASSES_PRESENT_IN_QUERY = "Classes Present In Query";
	/**  Constant for CLASS.*/
	public static final String CLASS = "class";
	/**  Constant for ATTRIBUTE.*/
	public static final String ATTRIBUTE = "attribute";
	/**  Constant for FILE.*/
	public static final String FILE = "file";
	/**  Constant for MISSING_TWO_VALUES.*/
	public static final String MISSING_TWO_VALUES = "missingTwoValues";
	/**  Constant for METHOD_NAME.*/
	public static final String METHOD_NAME = "method";
	/**  Constant for categorySearchForm.*/
	public static final String categorySearchForm = "categorySearchForm";
	/**  Constant for ADVANCE_QUERY_TABLES.*/
	public static final int ADVANCE_QUERY_TABLES = 2;
	/**  Constant for DATE_TYPE.*/
	public static final String DATE_TYPE = "Date";
	/**  Constant for INTEGER_TYPE.*/
	public static final String INTEGER_TYPE = "Integer";
	/**  Constant for FLOAT_TYPE.*/
	public static final String FLOAT_TYPE = "Float";
	/**  Constant for DOUBLE_TYPE.*/
	public static final String DOUBLE_TYPE = "Double";
	/**  Constant for LONG_TYPE.*/
	public static final String LONG_TYPE = "Long";
	/**  Constant for SHORT_TYPE.*/
	public static final String SHORT_TYPE = "Short";
	/**  Constant for FIRST_NODE_ATTRIBUTES.*/
	public static final String FIRST_NODE_ATTRIBUTES = "firstDropDown";
	/**  Constant for ARITHMETIC_OPERATORS.*/
	public static final String ARITHMETIC_OPERATORS = "secondDropDown";
	/**  Constant for SECOND_NODE_ATTRIBUTES.*/
	public static final String SECOND_NODE_ATTRIBUTES = "thirdDropDown";
	/**  Constant for RELATIONAL_OPERATORS.*/
	public static final String RELATIONAL_OPERATORS = "fourthDropDown";
	/**  Constant for TIME_INTERVALS_LIST.*/
	public static final String TIME_INTERVALS_LIST = "timeIntervals";
	/**  Constant for ENTITY_LABEL_LIST.*/
	public static final String ENTITY_LABEL_LIST = "entityList";

	/**  Constant for DefineSearchResultsViewAction.*/
	public static final String DefineSearchResultsViewAction = "/DefineSearchResultsView.do";

	/**  Constant for BG_COLOR.*/
	public static final Color BG_COLOR = new Color(0xf4f4f5);

	// Dagviewapplet constants
	/**  Constant for QUERY_OBJECT.*/
	public static final String QUERY_OBJECT = "queryObject";
	/**  Constant for COUNT_QUERY.*/
	public static final String COUNT_QUERY = "CountQuery";
	/**  Constant for DATA_QUERY.*/
	public static final String DATA_QUERY = "DataQuery";

	/**  Constant for SESSION_ID.*/
	public static final String SESSION_ID = "session_id";
	/**  Constant for STR_TO_CREATE_QUERY_OBJ.*/
	public static final String STR_TO_CREATE_QUERY_OBJ = "strToCreateQueryObject";
	/**  Constant for ENTITY_NAME.*/
	public static final String ENTITY_NAME = "entityName";
	/**  Constant for INIT_DATA.*/
	public static final String INIT_DATA = "initData";
	/**  Constant for ATTRIBUTES.*/
	public static final String ATTRIBUTES = "Attributes";
	/**  Constant for ATTRIBUTE_OPERATORS.*/
	public static final String ATTRIBUTE_OPERATORS = "AttributeOperators";
	/**  Constant for FIRST_ATTR_VALUES.*/
	public static final String FIRST_ATTR_VALUES = "FirstAttributeValues";
	/**  Constant for SECOND_ATTR_VALUES.*/
	public static final String SECOND_ATTR_VALUES = "SecondAttributeValues";
	/**  Constant for SHOW_ENTITY_INFO.*/
	public static final String SHOW_ENTITY_INFO = "showEntityInformation";
	/**  Constant for SRC_ENTITY.*/
	public static final String SRC_ENTITY = "srcEntity";
	/**  Constant for PATHS.*/
	public static final String PATHS = "paths";
	/**  Constant for DEST_ENTITY.*/
	public static final String DEST_ENTITY = "destEntity";
	/**  Constant for ERROR_MESSAGE.*/
	public static final String ERROR_MESSAGE = "errorMessage";
	/**  Constant for SHOW_VALIDATION_MESSAGES.*/
	public static final String SHOW_VALIDATION_MESSAGES = "showValidationMessages";
	/**  Constant for SHOW_RESULTS_PAGE.*/
	public static final String SHOW_RESULTS_PAGE = "showViewSearchResultsJsp";
	/**  Constant for ATTR_VALUES.*/
	public static final String ATTR_VALUES = "AttributeValues";
	/**  Constant for SHOW_ERROR_PAGE.*/
	public static final String SHOW_ERROR_PAGE = "showErrorPage";
	/**  Constant for GET_DATA.*/
	public static final String GET_DATA = "getData";
	/**  Constant for SET_DATA.*/
	public static final String SET_DATA = "setData";
	/**  Constant for EMPTY_LIMIT_ERROR_MESSAGE.*/
	public static final String EMPTY_LIMIT_ERROR_MESSAGE = "<font color='red'>Please enter at least one condition to add a limit to limit set.</font>";
	/**  Constant for CANNOT_DELETE_NODE.*/
	public static final String CANNOT_DELETE_NODE = "Cannot delete an object in the edit mode. However, "
			+ "you can edit the conditions or attributes for the selected object.";
	/**  Constant for REMOVE_SELECTED_ATTRIBUTES.*/
	public static final String REMOVE_SELECTED_ATTRIBUTES = "Clear the attributes on the Define Result "
			+ "View page for the selected object, and then try deleting the object.";
	/**  Constant for EMPTY_DAG_ERROR_MESSAGE.*/
	public static final String EMPTY_DAG_ERROR_MESSAGE = "<font color='red'>Limit set should contain at least one limit.</font>";
	/**  Constant for MULTIPLE_ROOTS_EXCEPTION.*/
	public static final String MULTIPLE_ROOTS_EXCEPTION = "<font color='red'>Expression graph should be a connected graph.</font>";
	/**  Constant for EDIT_LIMITS.*/
	public static final String EDIT_LIMITS = "<font color='blue'>Limit succesfully edited.</font>";
	/**  Constant for EDIT_MODE.*/
	public static final String EDIT_MODE = "Edit";
	/**  Constant for DELETE_LIMITS.*/
	public static final String DELETE_LIMITS = "<font color='blue'>Limit succesfully deleted.</font>";

	/**  Constant for MAXIMUM_TREE_NODE_LIMIT.*/
	public static final String MAXIMUM_TREE_NODE_LIMIT = "resultView.maximumTreeNodeLimit";
	//public static final String ATTRIBUTES = "Attributes";
	/**  Constant for SESSION_EXPIRY_WARNING.*/
	public static final String SESSION_EXPIRY_WARNING = "session.expiry.warning.advanceTime";

	/**  Constant for SearchCategory.*/
	public static final String SearchCategory = "SearchCategory.do";
	/**  Constant for ResultsViewJSPAction.*/
	public static final String ResultsViewJSPAction = "ViewSearchResultsJSPAction.do";

	/**  Constant for NAME.*/
	public static final String NAME = "name";
	/**  Constant for TREE_VIEW_FRAME.*/
	public static final String TREE_VIEW_FRAME = "treeViewFrame";
	/**  Constant for QUERY_TREE_VIEW_ACTION.*/
	public static final String QUERY_TREE_VIEW_ACTION = "QueryTreeView.do";
	/**  Constant for QUERY_GRID_VIEW_ACTION.*/
	public static final String QUERY_GRID_VIEW_ACTION = "QueryGridView.do";
	/**  Constant for GRID_DATA_VIEW_FRAME.*/
	public static final String GRID_DATA_VIEW_FRAME = "gridFrame";
	/**  Constant for PAGE_NUMBER.*/
	public static final String PAGE_NUMBER = "pageNum";
	/**  Constant for TOTAL_RESULTS.*/
	public static final String TOTAL_RESULTS = "totalResults";
	/**  Constant for RESULTS_PER_PAGE.*/
	public static final String RESULTS_PER_PAGE = "numResultsPerPage";
	/**  Constant for SPREADSHEET_COLUMN_LIST.*/
	public static final String SPREADSHEET_COLUMN_LIST = "spreadsheetColumnList";
	/**  Constant for PAGE_OF.*/
	public static final String PAGE_OF = "pageOf";
	/**  Constant for PAGE_OF_GET_DATA.*/
	public static final String PAGE_OF_GET_DATA = "pageOfGetData";
	/**  Constant for PAGE_OF_GET_COUNT.*/
	public static final String PAGE_OF_GET_COUNT = "pageOfGetCount";
	/**  Constant for SPREADSHEET_EXPORT_ACTION.*/
	public static final String SPREADSHEET_EXPORT_ACTION = "SpreadsheetExport.do";
	/**  Constant for RESULT_PERPAGE_OPTIONS.*/
	public static final int[] RESULT_PERPAGE_OPTIONS = {10, 50, 100, 500, 1000};
	/**  Constant for PAGE_OF_PARTICIPANT_CP_QUERY.*/
	public static final String PAGE_OF_PARTICIPANT_CP_QUERY = "pageOfParticipantCPQuery";
	/**  Constant for CONFIGURE_GRID_VIEW_ACTION.*/
	public static final String CONFIGURE_GRID_VIEW_ACTION = "ConfigureGridView.do";
	/**  Constant for SAVE_QUERY_ACTION.*/
	public static final String SAVE_QUERY_ACTION = "SaveQueryAction.do";
	/**  Constant for CHARACTERS_IN_ONE_LINE.*/
	public static final int CHARACTERS_IN_ONE_LINE = 110;
	/**  Constant for SINGLE_QUOTE_ESCAPE_SEQUENCE.*/
	public static final String SINGLE_QUOTE_ESCAPE_SEQUENCE = "&#096;";
	/**  Constant for ViewSearchResultsAction.*/
	public static final String ViewSearchResultsAction = "ViewSearchResultsAction.do";

	/**  Constant for QUERY_IDENTIFIER_NOT_VALID.*/
	public static final String QUERY_IDENTIFIER_NOT_VALID = "Query identifier is not valid.";
	/**  Constant for NO_RESULT_FOUND.*/
	public static final String NO_RESULT_FOUND = "No result found.";
	/**  Constant for REMAINING_RECORDS.*/
	public static final String REMAINING_RECORDS = "RemainingRecordList";
	/**  Constant for GET_RECORDS_FROM.*/
	public static final String GET_RECORDS_FROM = "getRecordsFrom";
	/**  Constant for NOT_ALL.*/
	public static final String NOT_ALL = "NOT_ALL";
	/**  Constant for ALL.*/
	public static final String ALL = "ALL";
	/**  Constant for ACTION.*/
	public static final String ACTION = "action";
	/**  Constant for QUERY_ID.*/
	public static final String QUERY_ID = "queryId";
	/**  Constant for QUERY_COLUMN_NAME.*/
	public static final String QUERY_COLUMN_NAME = "Column";
	/**  Constant for SEMICOLON.*/
	public static final String SEMICOLON = ";";
	/**  Constant for QUERY_OPENING_PARENTHESIS.*/
	public static final String QUERY_OPENING_PARENTHESIS = "(";
	/**  Constant for QUERY_CLOSING_PARENTHESIS.*/
	public static final String QUERY_CLOSING_PARENTHESIS = ")";
	/**  Constant for QUERY_DOT.*/
	public static final String QUERY_DOT = ".";
	/**  Constant for QUERY_UNDERSCORE.*/
	public static final String QUERY_UNDERSCORE = "_";
	/**  Constant for QUERY_COMMA.*/
	public static final String QUERY_COMMA = " ,";
	/**  Constant for QUERY_EQUALS.*/
	public static final String QUERY_EQUALS = " = ";
	/**  Constant for QUERY_FILE.*/
	public static final String QUERY_FILE = "file";
	/**  Constant for STR_TO_DATE.*/
	public static final String STR_TO_DATE = "STR_TO_DATE";

	/**  Constant for QUERY_FROM.*/
	public static final String QUERY_FROM = " from ";

	/**  Constant for QUERY_XMLTABLE.*/
	public static final String QUERY_XMLTABLE = " xmltable";
	/**  Constant for QUERY_FOR.*/
	public static final String QUERY_FOR = " for ";
	/**  Constant for QUERY_LET.*/
	public static final String QUERY_LET = " let ";
	/**  Constant for QUERY_ORDER_BY.*/
	public static final String QUERY_ORDER_BY = " order by ";
	/**  Constant for QUERY_RETURN.*/
	public static final String QUERY_RETURN = " return ";
	/**  Constant for XQUERY_PARENTING.*/
	public static final String XQUERY_PARENTING = "../";

	/**  Constant for QUERY_DOLLAR.*/
	public static final char QUERY_DOLLAR = '$';
	/**  Constant for QUERY_XMLCOLUMN.*/
	public static final String QUERY_XMLCOLUMN = "db2-fn:xmlcolumn";
	/**  Constant for QUERY_XMLDATA.*/
	public static final String QUERY_XMLDATA = "XMLDATA";
	/**  Constant for QUERY_AND.*/
	public static final String QUERY_AND = " and ";
	/**  Constant for OR Clause.*/
	public static final String QUERY_OR = " OR ";
	/**  Constant for WHERE Clause.*/
	public static final String QUERY_WHERE = " where ";
	/**  Constant for Relational Table or Column Clause.*/
	public static final String QUERY_RELATIONAL = "r";
	/**  Constant for xml Table or Column Clause.*/
	public static final String QUERY_XML = "x";
	/**  Constant for Path Clause.*/
	public static final String QUERY_PATH = " path ";
	/**  Constant for Passing Clause.*/
	public static final String QUERY_PASSING = " passing ";
	/**  Constant for Column Clause.*/
	public static final String QUERY_COLUMN_CLAUSE = " columns ";
	/**  Constant for Error message.*/
	public static final String QUERY_ERRORMSG = "Problem while trying to build xquery:";

	/**  Constant for QUERY_TEMPORAL_CONDITION.*/
	public static final String QUERY_TEMPORAL_CONDITION = "TEMPORAL_CONDITION";

	/**  Constant for TRUE.*/
	public static final String TRUE = "true";
	/**  Constant for FALSE.*/
	public static final String FALSE = "false";
	/**  Constant for Is_PAGING.*/
	public static final String Is_PAGING = "isPaging";
	/**  Constant for CONTENT_TYPE_TEXT.*/
	public static final String CONTENT_TYPE_TEXT = "text/html";

	/**
	 * to getOracleTermString.
	 * @param timeStr String
	 * @return String
	 */
	public static final String getOracleTermString(String timeStr)
	{
		return "day-from-dateTime(" + timeStr + ") * 86400" + "hours-from-dateTime(" + timeStr
				+ ") * 3600" + "minutes-from-dateTime(" + timeStr + ") * 60"
				+ "seconds-from-dateTime(" + timeStr + ")";
	}

	/**
	 * to getDB2TermString.
	 * @param timeStr String
	 * @return the actual time in seconds
	 */
	public static final String getDB2TermString(String timeStr)
	{
		return "extract(day from " + timeStr + ")*86400 + extract(hour from " + timeStr
				+ ")*3600 + extract(minute from " + timeStr + ")*60 + extract(second from "
				+ timeStr + ")";
	}

	/**  Constant for VERSION.*/
	public static final String VERSION = "VERSION";

	//Constants related to Export functionality
	/**  Constant for SEARCH_RESULT.*/
	public static final String SEARCH_RESULT = "SearchResult.csv";
	/**  Constant for ZIP_FILE_EXTENTION.*/
	public static final String ZIP_FILE_EXTENTION = ".zip";
	/**  Constant for CSV_FILE_EXTENTION.*/
	public static final String CSV_FILE_EXTENTION = ".csv";
	/**  Constant for EXPORT_ZIP_NAME.*/
	public static final String EXPORT_ZIP_NAME = "SearchResult.zip";
	/**  Constant for PRIMARY_KEY_TAG_NAME.*/
	public static final String PRIMARY_KEY_TAG_NAME = "PRIMARY_KEY";
	/**  Constant for ID_COLUMN_NAME.*/
	public static final String ID_COLUMN_NAME = "ID_COLUMN_NAME";
	/**  Constant for PRIMARY_KEY_SEPARATOR.*/
	public static final String PRIMARY_KEY_SEPARATOR = "!~!~!";

	//Taraben Khoiwala
	/**  Constant for CON_CODE.*/
	public static final String PERMISSIBLEVALUEFILTER = "PV_FILTER";
	/**  Constant for SHOW_DEFAULT_PV. */
	public static final String SHOW_DEFAULT_PV = "SHOW_DEFAULT_PV";
	/**  Constant for DE_PV_LOOKUP_QUERY. */
	public static final String PV_LOOKUP_QUERY = "PV_LOOKUP_QUERY";
	/**  Constant for PRINT_NAME. */
	public static final String PRINT_NAME = "PRINT_NAME";
	/**  Constant for CONCEPT_ID. */
	public static final String CONCEPT_ID = "concept_id";
	/**  Constant for CODE_VS_TOOLTIP. */
	public static final String CODE_VS_TOOLTIP = "CODE_VS_TOOLTIP";
	/**  Constant for CON_CODE. */
	public static final String CON_CODE = "ConceptCode";

	/**  Constant for ADVANCE_QUERY_INTERFACE_ID.*/
	public static final int ADVANCE_QUERY_INTERFACE_ID = 24;
	/**  Constant for PUBLIC_QUERY_PROTECTION_GROUP.*/
	public static final String PUBLIC_QUERY_PROTECTION_GROUP = "Public_Query_Protection_Group";
	/**  Constant for MY_QUERIES.*/
	public static final String MY_QUERIES = "MyQueries";
	/**  Constant for SAHRED_QUERIES.*/
	public static final String SAHRED_QUERIES = "sharedQueries";

	/**  Constant for NUMBER.*/
	public static final String[] NUMBER = {"long", "double", "short", "integer", "float"};
	/**  Constant for NEWLINE.*/
	public static final String NEWLINE = "\n";
	/**  Constant for DATATYPE_BOOLEAN.*/
	public static final String DATATYPE_BOOLEAN = "boolean";
	/**  Constant for DATATYPE_NUMBER.*/
	public static final String DATATYPE_NUMBER = "number";
	/**  Constant for  data type range for Quantitative Attributes.*/
	public static final String DATATYPE_RANGE = "range";
	/**  Constant for MAX_PV_SIZE.*/
	public static final int MAX_PV_SIZE = 500;
	/**  Constant for MAX_SIZE.*/
	public static final int MAX_SIZE = 500;

	/**  Constant for WORKFLOW_BIZLOGIC_ID.*/
	public static final int WORKFLOW_BIZLOGIC_ID = 101;
	/**  Constant for MY_QUERIES_FOR_WORKFLOW.*/
	public static final String MY_QUERIES_FOR_WORKFLOW = "myQueriesForWorkFlow";
	/**  Constant for SAHRED_QUERIES_FOR_WORKFLOW.*/
	public static final String SAHRED_QUERIES_FOR_WORKFLOW = "sharedQueriesForWorkFlow";
	/**  Constant for MY_QUERIES_FOR_MAIN_MENU.*/
	public static final String MY_QUERIES_FOR_MAIN_MENU = "myQueriesForMainMenu";
	/**  Constant for SHARED_QUERIES_FOR_MAIN_MENU.*/
	public static final String SHARED_QUERIES_FOR_MAIN_MENU = "sharedQueriesForMainMenu";
	/**  Constant for PUBLIC_QUERIES_FOR_WORKFLOW.*/
	public static final String PUBLIC_QUERIES_FOR_WORKFLOW = "publicQueryForWorkFlow";
	/**  Constant for DISPLAY_QUERIES_IN_POPUP.*/
	public static final String DISPLAY_QUERIES_IN_POPUP = "displayQueriesInPopup";
	/**  Constant for PAGE_OF_MY_QUERIES.*/
	public static final String PAGE_OF_MY_QUERIES = "MyQueries";

	/**  Constant for WORKFLOW_FORM_ID.*/
	public static final int WORKFLOW_FORM_ID = 101;

	/**  Constant for ELEMENT_ENTITY_GROUP.*/
	public static final String ELEMENT_ENTITY_GROUP = "entity-group";
	/**  Constant for ELEMENT_ENTITY.*/
	public static final String ELEMENT_ENTITY = "entity";
	/**  Constant for ELEMENT_NAME.*/
	public static final String ELEMENT_NAME = "name";
	/**  Constant for ELEMENT_ATTRIBUTE.*/
	public static final String ELEMENT_ATTRIBUTE = "attribute";
	/**  Constant for ELEMENT_TAG.*/
	public static final String ELEMENT_TAG = "tag";
	/**  Constant for ELEMENT_TAG_NAME.*/
	public static final String ELEMENT_TAG_NAME = "tag-name";
	/**  Constant for ELEMENT_TAG_VALUE.*/
	public static final String ELEMENT_TAG_VALUE = "tag-value";
	/**  Constant for  tag for not searchable. */
	public static final String TAGGED_VALUE_NOT_SEARCHABLE = "NOT_SEARCHABLE";
	/**  Constant for  tag for not viewable. */
	public static final String TAGGED_VALUE_NOT_VIEWABLE = "NOT_VIEWABLE";
	/**  Constant for  PHI.*/
	public static final String TAGGED_VALUE_PHI = "PHI";
	/** constant for  tagged value for showing age.*/
	public static final String TAGGEED_VALUE_SHOW_AGE = "SHOW_AGE";
	/**  Constant for ignoring predicate in xquery. */
	public static final String VI_IGNORE_PREDICATE = "VI_IGNORE_PREDICATE";
	/** Constant for default condition tagged value. **/
	public static final String TAGGED_VALUE_DEFAULT_CONDITION = "DEFAULT_CONDITION";
	/** Constant for default condition tagged value. **/
	public static final String DEFAULT_CONDITION_DATA = "DEFAULT_CONDITION_DATA";
	/**  Constant for tagged value for primary key. */
	public static final String TAGGED_VALUE_PRIMARY_KEY = "PRIMARY_KEY";
	/**  Constant for  tagged value for pv filter. */
	public static final String TAGGED_VALUE_PV_FILTER = "PV_FILTER";
	/**  Constant for  tagged value for VI hidden attributes. */
	public static final String TAGGED_VALUE_VI_HIDDEN = "VI_HIDDEN";
	/**  Constant for tag for quantitative attribute. */
	public static final String TAG_QUANTITATIVE_ATTRIBUTE = "QUANTITATIVE_ATTRIBUTE";
	/**  Constant for  quantitative attribute.*/
	public static final String QUANTITATIVE_ATTRIBUTE_VIEW = "QUANTITATIVE_ATTRIBUTE_VIEW";
	/**  Constant for  low attribute for quantitative attribute.*/
	public static final String TAG_MIN_RANGE = "MIN_RANGE";
	/**  Constant for  high attribute for quantitative attribute.*/
	public static final String TAG_MAX_RANGE = "MAX_RANGE";
	/**  Constant for CONTAINMENT_OBJECTS_MAP.*/
	public static final String CONTAINMENT_OBJECTS_MAP = "containmentObjectMap";
	/**  Constant for ENTITY_EXPRESSION_ID_MAP.*/
	public static final String ENTITY_EXPRESSION_ID_MAP = "entityExpressionIdMap";

	/** Constant for default condition tagged value.*/
	public static final String TAGGED_VALUE_PRIMARY_KEY_VALUE = "PRIMARY_KEY_VALUE";
	/** Constant for result order tag.*/
	public static final String TAGGED_VALUE_UPPERCASE = "UPPER_CASE";
	//added by amit_doshi
	/**  Constant for PV_TREE_VECTOR.*/
	public static final String PV_TREE_VECTOR = "PermissibleValueTreeVector";

	/**  Constant for PROPERTIESFILENAME.*/
	public static final String PROPERTIESFILENAME = "vocab.properties";
	/**  Constant for PV_VOCAB_BILOGIC_ID.*/
	public static final int PV_VOCAB_BILOGIC_ID = 12;
	/**  Constant for ATTRIBUTE_INTERFACE.*/
	public static final String ATTRIBUTE_INTERFACE = "AttributeInterface";
	/**  Constant for ENTITY_INTERFACE.*/
	public static final String ENTITY_INTERFACE = "EntityInterface";
	/**  Constant for VOCABULIRES.*/
	public static final String VOCABULIRES = "Vocabulries";
	/**  Constant for ENUMRATED_ATTRIBUTE.*/
	public static final String ENUMRATED_ATTRIBUTE = "EnumratedAttribute";
	/**  Constant for COMPONENT_ID.*/
	public static final String COMPONENT_ID = "componentId";
	/**  Constant for NO_RESULT.*/
	public static final String NO_RESULT = "No results found";
	/**  Constant for PV_HTML.*/
	public static final String PV_HTML = "PVHTML";

	/**  Constant for DEFINE_VIEW_MSG.*/
	public static final String DEFINE_VIEW_MSG = "DefineView";
	/**  Constant for ENTITY_NOT_PRESENT.*/
	public static final String ENTITY_NOT_PRESENT = "not present";
	/**  Constant for ENTITY_PRESENT.*/
	public static final String ENTITY_PRESENT = "present";
	/**  Constant for MAIN_ENTITY_MSG.*/
	public static final String MAIN_ENTITY_MSG = "Main Entity";
	/**  Constant for NOT_PRESENT_MSG.*/
	public static final String NOT_PRESENT_MSG = " Is Not Present In DAG";
	/**  Constant for MAIN_ENTITY_LIST.*/
	public static final String MAIN_ENTITY_LIST = "mainEntityList";
	/**  Constant for SELECTED_CONCEPT_LIST.*/
	public static final String SELECTED_CONCEPT_LIST = "SELECTED_CONCEPT_LIST";
	/**  Constant for TAGGED_VALUE_MAIN_ENTIY.*/
	public static final String TAGGED_VALUE_MAIN_ENTIY = "MAIN_ENTITY";
	/**  Constant for BASE_MAIN_ENTITY.*/
	public static final String BASE_MAIN_ENTITY = "BASE_MAIN_ENTITY";
	/**  Constant for  QUERY_NO_ROOTEXPRESSION.*/
	public static final String QUERY_NO_ROOTEXPRESSION = "query.noRootExpression.message";
	/**  Constant for  QUERY_NO_ROOT_CAT_EXPRESSION.*/
	public static final String QUERY_NO_ROOT_CAT_EXPRESSION = "query.category.noRootExpression.message";
	/**  Constant for  QUERY_NO_MAINEXPRESSION.*/
	public static final String QUERY_NO_MAINEXPRESSION = "query.noMainExpression.message";
	/**  Constant for  QUERY_NO_MAIN_CAT_EXPRESSION.*/
	public static final String QUERY_NO_MAIN_CAT_EXPRESSION = "query.category.noMainExpression.message";
	/**  Constant for  QUERY_MULTIPLE_ROOTS.*/
	public static final String QUERY_MULTIPLE_ROOTS = "errors.executeQuery.multipleRoots";
	/**  Constant for  ENTITY_LIST.*/
	public static final String ENTITY_LIST = "entityList";
	/**  Constant for MAIN_ENTITY_EXPRESSIONS_MAP.*/
	public static final String MAIN_ENTITY_EXPRESSIONS_MAP = "mainEntityExpressionsMap";
	/**  Constant for MAIN_EXPR_TO_ADD_CONTAINMENTS.*/
	public static final String MAIN_EXPR_TO_ADD_CONTAINMENTS = "expressionsToAddContainments";
	/**  Constant for ALL_ADD_LIMIT_EXPRESSIONS.*/
	public static final String ALL_ADD_LIMIT_EXPRESSIONS = "allLimitExpressionIds";
	/**  Constant for MAIN_EXP_ENTITY_EXP_ID_MAP.*/
	public static final String MAIN_EXP_ENTITY_EXP_ID_MAP = "mainExpEntityExpressionIdMap";

	/**  Constant for MAIN_ENTITY_ID.*/
	public static final String MAIN_ENTITY_ID = "entityId";
	/**  Constant for XML_FILE_NAME.*/
	public static final String XML_FILE_NAME = "fileName";
	/**  Constant for PERMISSIBLEVALUEVIEW.*/
	public static final String PERMISSIBLEVALUEVIEW = "PV_VIEW";
	/**  Constant for VI_INFO_MESSAGE1.*/
	public static final String VI_INFO_MESSAGE1 = "This entity contains more than ";
	/**  Constant for VI_INFO_MESSAGE2.*/
	public static final String VI_INFO_MESSAGE2 = " Permissible values.Please search for the specific term ";

	//Start : Added for changes in Query Design for CIDER Query
	public static final String PROJECT_ID = "projectId";
	//End : Added for changes in Query Design for CIDER Query

	/** Constant for Advanced Query 'JNDI' name.*/
	public static final String JNDI_NAME_QUERY = "java:/query";
	/** Constant for CIDER 'JNDI' name.*/
	public static final String JNDI_NAME_CIDER = "java:/cider";
	// CONSTANTS for columns in table 'QUERY_EXECUTION_LOG'
	/**  Constant for CREATIONTIME.*/
	public static final String CREATIONTIME = "CREATION_TIME";
	/**  Constant for USER_ID.*/
	public static final String USER_ID = "USER_ID";
	/**  Constant for STATUS.*/
	public static final String QUERY_STATUS = "QUERY_STATUS";
	/**  Constant for QUERY_NAME.*/
	public static final String QUERY_NAME = "queryName";
	/**  Constant for ENTITY_ID.*/
	public static final String ENTITY_ID = "entityId";
	/**  Constant for PROJECT_ID.*/
	public static final String PRJCT_ID = "PROJECT_ID";

	/**  Constant for WORKFLOW_ID.*/
	public static final String COL_WORKFLOW_ID = "WORKFLOW_ID";

	/**  Constant for QUERY_EXECUTION_ID.*/
	public static final String QUERY_EXECUTION_ID = "QUERY_EXECUTION_ID";
	/**  Constant for QUERY_EXECUTION_ID.*/
	public static final String COUNT_QUERY_EXECUTION_ID = "COUNT_QUERY_EXECUTION_ID";
	/**  Constant for COUNT_QUERY_UPI.*/
	public static final String COUNT_QUERY_UPI = "UPI";
	/**  Constant for COUNT_QUERY_DOB.*/
	public static final String COUNT_QUERY_DOB = "DATE_OF_BIRTH";
	/**  Constant for QUERY_EXECUTION_ID.*/
	public static final String DATA_QUERY_EXECUTION_ID = "DATA_QUERY_EXECUTION_ID";
	/**  Constant for QUERY_ID.*/
	public static final String QRY_ID = "QUERY_ID";
	/**  Constant for GENERATING_QUERY.*/
	public static final String GENERATING_QUERY = "Generating Query";
	/**  Constant for QUERY_IN_PROGRESS.*/
	public static final String QUERY_IN_PROGRESS = "In Progress";
	/**  Constant for QUERY_COMPLETED.*/
	public static final String QUERY_COMPLETED = "Completed";
	/**  Constant for QUERY_CANCELLED.*/
	public static final String QUERY_CANCELLED = "Cancelled";
	/**  Constant for XQUERY_FAILED.*/
	public static final String QUERY_FAILED = "Query Failed";

	/**
	 *Status for the limited count/data.
	 */
	public static final String limitedCountStatus = "COMPLETED_WITH_WARNING";

	/** Constant for separator used in tag values for default conditions.*/
	public static final String DEFAULT_CONDITIONS_SEPARATOR = "!=!";
	/** Constant for space SPACE.*/
	public static final String SPACE = " ";
	/**  Constant for EMPTY_STRING.*/
	public static final String EMPTY_STRING = "";
	/**  Constant for IS_CHECKED.*/
	public static final String IS_CHECKED = "isChecked";
	/**  Constant for SHOW_LAST.*/
	public static final String SHOW_LAST = "showLast";
	/**  Constant for EXECUTION_LOG_ID.*/
	public static final String EXECUTION_LOG_ID = "executionLogId";
	/**  Constant for SHOW_LAST_COUNT.*/
	public static final int SHOW_LAST_COUNT = 25;
	/**  Constant for TOTAL_PAGES.*/
	public static final String TOTAL_PAGES = "totalPages";
	/**  Constant for RESULTS_PER_PAGE_OPTIONS.*/
	public static final String RESULTS_PER_PAGE_OPTIONS = "resultsPerPageOptions";
	/**  Constant for RECENT_QUERIES_BEAN_LIST.*/
	public static final String RECENT_QUERIES_BEAN_LIST = "recentQueriesBeanList";
	/**  Constant for PER_PAGE_RESULTS.*/
	public static final int PER_PAGE_RESULTS = 10;
	/**  Constant for RESULT_OBJECT.*/
	public static final String RESULT_OBJECT = "resultObject";
	/**  Constant for QUERY_COUNT.*/
	public static final String QUERY_COUNT = "queryCount";
	/**  Constant for GET_COUNT_STATUS.*/
	public static final String GET_COUNT_STATUS = "status";
	/**  Constant for EXECUTION_ID.*/
	public static final String EXECUTION_ID = "executionId";
	/**  Constant for QUERY_TYPE_GET_COUNT.*/
	public static final String QUERY_TYPE_GET_COUNT = "Count";
	/**  Constant for QUERY_TYPE_GET_DATA.*/
	public static final String QUERY_TYPE_GET_DATA = "Data";

	public static final int[] SHOW_LAST_OPTION = {25, 50, 100, 200};
	//Constants for Get Count
	/**  Constant for  abortExecution.*/
	public static final String ABORT_EXECUTION = "abortExecution";
	/**  Constant for  query_exec_id.*/
	public static final String QUERY_EXEC_ID = "query_exec_id";
	/**  Constant for  isNewQuery.*/
	public static final String IS_NEW_QUERY = "isNewQuery";
	/**  Constant for selectedProject.*/
	public static final String SELECTED_PROJECT = "selectedProject";
	/**  Constant for Query Exception.*/
	public static final String QUERY_EXCEPTION = "queryException";
	/**  Constant for Wait.*/
	public static final String WAIT = "wait";
	/**  Constant for Query Title.*/
	public static final String QUERY_TITLE = "queryTitle";
	/**  Constant for MY_QUERIESFOR_DASHBOARD.*/
	public static final String MY_QUERIESFOR_DASHBOARD = "myQueriesforDashboard";
	/**  Constant for showdashboard.*/
	public static final String SHOW_DASHBOARD = "showdashboard";
	/**  Constant for selectedColumnNamesList.*/
	public static final String SELECTED_COLUMN_NAMES_LIST = "selectedColumnNamesList";
	/**  Constant for WORKFLOW_NAME.*/
	public static final String WORKFLOW_NAME = "worflowName";
	/**  Constant for WORKFLOW_ID.*/
	public static final String WORKFLOW_ID = "workflowId";
	/**  Constant for IS_WORKFLOW.*/
	public static final String IS_WORKFLOW = "isWorkflow";
	/**  Constant for PAGE_OF_WORKFLOW.*/
	public static final String PAGE_OF_WORKFLOW = "workflow";
	/**  Constant for WORKFLOW.*/
	public static final String WORKFLOW = "Workflow";
	/**  Constant for FORWARD_TO_HASHMAP.*/
	public static final String FORWARD_TO_HASHMAP = "forwardToHashMap";
	/**  Constant for NEXT_PAGE_OF.*/
	public static final String NEXT_PAGE_OF = "nextPageOf";
	/**  Constant for QUERYWIZARD.*/
	public static final String QUERYWIZARD = "queryWizard";
	/**  Constant for DATA_QUERY_ID.*/
	public static final String DATA_QUERY_ID = "dataQueryId ";
	/**  Constant for generated query.*/
	public static final String GENERATED_QUERY = "generatedQuery";
	/**  Constant for COUNT_QUERY_ID.*/
	public static final String COUNT_QUERY_ID = "countQueryId ";
	/**  Constant for PROJECT_NAME_VALUE_BEAN.*/
	public static final String PROJECT_NAME_VALUE_BEAN = "projectsNameValueBeanList";
	/**  Constant for WORFLOW_ID.*/
	//public static final String WORFLOW_ID = "workflowId";
	/**  Constant for SPREADSHEET_DATA_QUERYEXECUTION_ID.*/
	public static final String SPREADSHEET_DQ_EXECUTION_ID = "dataQueryExecId";
	/**  Constant for ROOT_ENTITY.*/
	public static final String ROOT_ENTITY = "rootEntity";
	/**  Constant for TREEVIEW_DATA_QUERYEXECUTION_ID.*/
	public static final String TREEVIEW_DQ_EXECUTION_ID = "dataQueryExec_Id";
	/**  Constant for WORFLOW_NAME.*/
	//public static final String WORFLOW_NAME = "worflowName";
	/** constants for VI.*/
	public static final String SRC_VOCAB_MESSAGE = "SourceVocabMessage";
	/**  Constant for ABORT.*/
	public static final Object ABORT = "abort";
	/**  Constant for SELECTED_BOX.*/
	public static final String SELECTED_BOX = "selectedCheckBox";
	/**  Constant for OPERATION.*/
	public static final String OPERATION = "operation";
	/**  Constant for SEARCH_TERM.*/
	public static final String SEARCH_TERM = "searchTerm";
	/**  Constant for MED_MAPPED_N_VALID_PVCONCEPT.*/
	public static final String MED_MAPPED_N_VALID_PVCONCEPT = "Normal_Bold_Enabled";
	/**  Constant for MED_MAPPED_NOT_VALID_PVCONCEPT.*/
	public static final String MED_MAPPED_NOT_VALID_PVCONCEPT = "Bold_Italic_Disabled";
	/**  Constant for NOT_MED_MAPPED_PVCONCEPT.*/
	public static final String NOT_MED_MAPPED_PVCONCEPT = "Normal_Italic_Disabled";
	/**  Constant for NOT_MED_VALED_PVCONCEPT.*/
	public static final String NOT_MED_VALED_PVCONCEPT = "Normal_Disabled";
	/**  Constant for ID_DEL.*/
	public static final String ID_DEL = "ID_DEL";
	/**  Constant for MSG_DEL.*/
	// if you change its value,kindly change in queryModule.js its hard coded there
	public static final String MSG_DEL = "@MSG@";
	/**  Constant for NOT_AVAILABLE.*/
	public static final String NOT_AVAILABLE = "Not Available";
	/**  Constant for SEARCH_CRITERIA.*/
	public static final String SEARCH_CRITERIA = "searchCriteria";
	/**  Constant for IS_CODE_BASED_SEARCH.*/
	public static final String IS_CODE_BASED_SEARCH = "isCodeBased";
	/**  Constant for ANY_WORD.*/
	public static final String ANY_WORD = "ANY_WORD";
	/**  Constant for TARGET_VOCABS.*/
	public static final String TARGET_VOCABS = "targetVocabsForSearchTerm";
	/**  Constant for VI concept code open bracket.*/
	public static final String OPEN_CODE_BARCKET = "[";
	/**  Constant for VI concept code close bracket.*/
	public static final String CLOSE_CODE_BARCKET = "]";
	/**  Constant for VI concept code value DELIMITER.*/
	public static final String VALUE_DEL = ": ";
	/**  Constant for  String buffer size.*/
	public static final int STRING_BUF_SIZE = 255;
	/**  Constant for separator between Print name and Volume Count.*/
	public static final String VC_SEP = "~";
	/**  Constant for Concept Code.*/
	public static final String CONCEPT_CODE = "Concept Code: ";
	/**  Constant for Concept Description.*/
	public static final String CONCEPT_DES = "Concept Description: ";
	/**  Constant for Concept Description.*/
	public static final String CONCEPT_VOLUME = "Approx. Number of Occurrences: ";
	/**  Constant for CONCEPT Definition.*/
	public static final String CONCEPT_DEFI = "Definition: ";
	/**  Constant for MED CONCEPT NAME.*/
	public static final String MED_CONCEPT_NAME = "MED Concept Name: ";
	/**  Constant for TABLE HTML.*/
	public static final String TABLE = "<table id='tooltipTAB' class='black_ar_tt' width='100%'cellpadding ='2' cellspacing ='0' border='0' >";
	/**  Constant for TR_STYLE HTML.*/
	public static final String TR_STYLE = "<tr height='20'>";
	/**  Constant for TD_CLOSE HTML.*/
	public static final String TD_CLOSE = "</td>";

	/**
	 * Query ITABLE.
	 */
	public static final String ITABLE = " QUERY_ITABLE ";

	/**
	 * COUNT QUERY EXECUTION LOG TABLE.
	 */
	public static final String COUNT_QUERY_EXECUTION_LOG = " COUNT_QUERY_EXECUTION_LOG ";

	/**
	 * DATA QUERY EXECUTION LOG TABLE.
	 */
	public static final String DATA_QUERY_EXECUTION_LOG = " DATA_QUERY_EXECUTION_LOG ";

	/**
	 * QUERY EXECUTION LOG TABLE.
	 */
	public static final String QUERY_EXECUTION_LOG = " QUERY_EXECUTION_LOG ";

	/**
	 * VIEWED DATA LOG TABLE.
	 */
	public static final String VIEWED_DATA_LOG = " VIEWED_DATA_LOG ";

	/**  Constant for ITABLE_PK_ID.*/
	public static final String ITABLE_PK_ID = " ITABLE_PK_ID ";
	/**  Constant for DOWNLOAD_FLAG.*/
	public static final String DOWNLOAD_FLAG = " DOWNLOAD_FLAG ";

	/**
	 * QUERY SECURITY LOG TABLE.
	 */
	public static final String QUERY_SECURITY_LOG = "QUERY_SECURITY_LOG";
	/**  Constant for DATE_OF_BIRTH.*/
	public static final String DATE_OF_BIRTH = "DATE_OF_BIRTH";
	/**  Constant for VIEW_FLAG.*/
	public static final String VIEW_FLAG = "VIEW_FLAG";
	/**  Constant for XQUERY_STRING.*/
	public static final String XQUERY_STRING = "XQUERY_STRING";
	/**  Constant for QUERY_TYPE.*/
	public static final String QUERY_TYPE = "QUERY_TYPE";
	/**  Constant for IP_ADDRESS.*/
	public static final String IP_ADDRESS = "IP_ADDRESS";
	/**  Constant for QUERY_COUNT.*/
	public static final String QRY_COUNT = "QUERY_COUNT";
	/**  Constant for DEID_SEQUENCE.*/
	public static final String DEID_SEQUENCE = "DEID_SEQUENCE";
	/**  Constant for PARAMETRIZED_ATTRIBUTE.*/
	public static final String PARAMETERIZED = "PARAMETERIZED";

	/**  Constant for  ITABLE_ATTRIBUTES.*/
	public static final String TAGGED_VALUE_ITABLE_ATTRIBUTES = "ITABLE_ATTRIBUTES";
	/**  Constant for SECURE CONDITION.*/
	public static final String TAGGED_VALUE_SECURE_CONDITION = "SECURE_CONDITION";

	/**  Constant for tag on Parent entity.*/
	public static final String TAGGED_PARENT_ENTITY = "PARENT_ENTITY";
	/**  Constant for SECURE CONDITION.*/
	public static final int AGE = 89;
	/**  Constant for Minor Age.*/
	public static final int MINOR = 18;

	/**
	 * constant for  PATIENT_DATA_QUERY.
	 */
	public static final String PATIENT_DATA_QUERY = "patientDataQuery";

	public static final String DATA_ROOT_OUTPUT_LIST = "patientDataRootOutPutList";

	/**  Constant for Equal to operator.*/
	public static final String EQUALS = " = ";

	/** Constant for...*/
	public static final String EXECUTION_ID_OF_QUERY = "queryExecutionId";
	/** Constant for AbstractQuery.*/
	public static final String ABSTRACT_QUERY = "abstractQuery";

	/**
	 * Constant for request attribute for 'execution type'.
	 */
	public static final String REQ_ATTRIB_EXECUTION_TYPE = "executeType";

	/**
	 * Constant to denoted execution type as 'Workflow'.
	 */
	public static final String EXECUTION_TYPE_WORKFLOW = "executeWorkFlow";

	/** Constant for presenatation property name for med concept name.*/
	public static final String MED_CONECPT_NAME = "med_concept_name";
	/** Constant for MED_ENTITY_NAME.*/
	public static final String MED_ENTITY_NAME = "MedicalEntityDictionary";

	/**
	 * for shared queries Count.
	 */
	public static final String SHARED_QUERIES_COUNT = "sharedQueriesCount";
	/**
	 * for my queries Count.
	 */
	public static final String MY_QUERIES_COUNT = "myQueriesCount";
	/** Constant for PATIENT_DEID.*/
	public static final String PATIENT_DEID = "PATIENT_DEID";
	/** Constant for result view tag.*/
	public static final String TAGGED_VALUE_RESULTVIEW = "resultview";
	/** Constant for result order tag.*/
	public static final String TAGGED_VALUE_RESULTORDER = "resultorder";

	/**
	 * StrutsConfigReader related constants.
	 */
	/** Constant for WEB_INF_FOLDER_NAME.*/
	public static final String WEB_INF_FOLDER_NAME = "WEB-INF";
	/** Constant for AQ_STRUTS_CONFIG_FILE_NAME.*/
	public static final String AQ_STRUTS_CONFIG_FILE_NAME = "advancequery-struts-config.xml";
	/** Constant for AQ_STRUTS_CONFIG_DTD_FILE_NAME.*/
	public static final String AQ_STRUTS_CONFIG_DTD_FILE_NAME = "struts-config_1_1.dtd";
	/** Constant for STRUTS_NODE_ACTION.*/
	public static final String STRUTS_NODE_ACTION = "action";
	/** Constant for NODE_ACTION_ATTRIBUTE_PATH.*/
	public static final String NODE_ACTION_ATTRIBUTE_PATH = "path";
	/**
	 * for setting  project of last executed query on work flow page.
	 */
	public static final String EXECUTED_FOR_PROJECT = "executedForProject";
	/** Constant for QUERY_PRIVILEGE.*/
	public static final String QUERY_PRIVILEGE = "queryPrivilege";
	/** Constant for RECENT_QUERY_BIZLOGIC_ID.*/
	public static final int RECENT_QUERY_BIZLOGIC_ID = 102;
	/** Constant for PERSON_UPI_COUNT.*/
	public static final String PERSON_UPI_COUNT = "PersonUpiCount";
	/** Constant for TOO_FEW_RECORDS.*/
	public static final String TOO_FEW_RECORDS = "TOO_FEW_RECORDS";
	/** Constant for HAS_SECURE_PRIVILEGE.*/
	public static final String HAS_SECURE_PRIVILEGE = "hasSecurePrivilege";

	/**
	 * for parameterized condition list.
	 */
	public static final String PARAM_CONDITION_LIST = "parameterizedConditionList";
	/** Constant for IDENTIFIER_FIELD_INDEX.*/
	public static final String IDENTIFIER_FIELD_INDEX = "identifierFieldIndex";
	/**
	 * for temporal conditions.
	 */
	public static final String TEMPORAL_PARAM_CONDITON_LIST = "parameterizedConditionForTQ";

	/**
	 * for parameterized query page.
	 */
	public static final String SHOW_PQC_PAGE = "showParameterizedPage";
	/**
	 * for recent queries parameters in popup.
	 */
	public static final String SHOW_PQ_POPUP = "showParameterizedPopUp";
	/**
	 * for recent queries parameters in popup.
	 */
	public static final String NOT_PQ_POPUP = "notParameterized";
	/** Constant for SPREADSHEET_DATA_LIST.*/
	public static final String SPREADSHEET_DATA_LIST = "spreadsheetDataList";
	/**
	 * Added for target related to edit count query.
	 */
	public static final String EDIT_COUNT_QUERY_TARGET = "editCountQuery";
	/** Constant for null value.*/
	public static final String NULL = "NULL";

	/** Constant for Query results view temporary table name.*/
	public static final String QUERY_RESULTS_TABLE = "CATISSUE_QUERY_RESULTS";
	/** Constant for HASH_PRIME.*/
	public static final int HASH_PRIME = 7;
	/** Constant for EXPRESSION_ID_SEPARATOR.*/
	public static final String EXPRESSION_ID_SEPARATOR = ":";
	/** Constant for IS_SIMPLE_SEARCH.*/
	public static final String IS_SIMPLE_SEARCH = "isSimpleSearch";
	/** Constant for SIMPLE_QUERY_INTERFACE_ID.*/
	public static final int SIMPLE_QUERY_INTERFACE_ID = 40;
	/** Constant for QUERY_INTERFACE_ID.*/
	public static final int QUERY_INTERFACE_ID = 43;

	/*
	 * Patch ID: SimpleSearchEdit_3
	 * Description: Constants required for this feature plus Delimeter used in the DHTML grid.
	 */
	/** Constant for HYPERLINK_COLUMN_MAP.*/
	public static final String HYPERLINK_COLUMN_MAP = "hyperlinkColumnMap";
	/** Constant for FAILURE.*/
	public static final String FAILURE = "failure";
	/** Constant for TIME_PATTERN_HH_MM_SS.*/
	public static final String TIME_PATTERN_HH_MM_SS = "HH:mm:ss";
	/** Constant for ORACLE_DATABASE.*/
	public static final String ORACLE_DATABASE = "ORACLE";
	/** Constant for MYSQL_DATABASE.*/
	public static final String MYSQL_DATABASE = "MYSQL";
	/** Constant for POSTGRESQL_DATABASE.*/
	public static final String POSTGRESQL_DATABASE = "POSTGRESQL";
	/** Constant for DB2_DATABASE.*/
	public static final String DB2_DATABASE = "DB2";

	/** Metadata search configuration parameter : Used when a permissible value is to be included in search.*/
	public static final int PV = 3;

	// Constants for type of query results view.
	/** Constant for SPREADSHEET_VIEW.*/
	public static final String SPREADSHEET_VIEW = "Spreadsheet View";
	/** Constant for VIEW_TYPE.*/
	public static final String VIEW_TYPE = "viewType";
	/** Constant for DHTMLXGRID_DELIMETER.*/
	public static final String DHTMLXGRID_DELIMETER = "|@|";
	/** Constant for DATE_PATTERN_MM_DD_YYYY.*/
	public static final String DATE_PATTERN_MM_DD_YYYY = "MM-dd-yyyy";
	/** added constant for date format for timestamp attributes.**/
	public static final String DATE_TIME_FORMAT = "MM-dd-yyyy HH:mm";

	/**
	 * Added for target related to edit Data query.
	 */
	public static final String EDIT_DATA_QUERY_TARGET = "editDataQuery";
	/**
	 * Problem in executing query.
	 */
	public static final String EXECUTE_QUERY_ERROR = " Error while executing SQL query ";
	/** Constant for APPLICATION_NAME.*/
	public static final String APPLICATION_NAME = "Query";
	/** Constant for NOT_EQUAL.*/
	public static final String NOT_EQUAL = "!=";
	/** Constant for PQ_ID_SET.*/
	public static final String PQ_ID_SET = "pQueryIdList";
	/** Constant for SHOW_POP_UP.*/
	public static final String SHOW_POP_UP = "showPopup";
	/** Constant for NOT_SHOW_POP_UP.*/
	public static final String NOT_SHOW_POP_UP = "notshowPopup";
	/** Constant for BIZ_SQL_EXCEPTION_ERROR.*/
	public static final String BIZ_SQL_EXCEPTION_ERROR = "biz.sqlException.error";
	/** Constant for BIZ_INVALID_OBJECT.*/
	public static final String BIZ_INVALID_OBJECT = "biz.invalid.object";
	/** Constant for ERROR_GET_COUNT_STATUS_OBJECT.*/
	public static final String ERROR_GET_COUNT_STATUS_OBJECT = "Error while getting count status object - ";
	/** Constant for QUERY_ID_STRING.*/
	public static final String QUERY_ID_STRING = "queriesIdString";
	/** Constant for QUERY_CUSTOM_FORMULA_MAP.*/
	public static final String QUERY_CUSTOM_FORMULA_MAP = "eachQueryCFMap";
	/** Constant for QUERY_CONDITION_STRING.*/
	public static final String QUERY_CONDITION_STRING = "allQueriesCondStr";
	/** Constant for CANCEL_EXECUTION.*/
	public static final String CANCEL_EXECUTION = "cancelExecution";
	/** Constant for BREAK HTML tag.*/
	public static final String HTML_BREAK = "<br/>";
	/**
	 * for showing get count count button.
	 */
	public static final String SHOW_GET_COUNT = "showGetCount";
	/** Constant for TD_TR HTML tags.*/
	public static final String TD_TR = "</td></tr>";
	/** Constant for EXPORT URL.*/
	public static final String EXPORT_URL = "exportURL";
	/** Constant for PAGE_OF_RECENT_QUERIES.*/
	public static final String PAGE_OF_RECENT_QUERIES = "pageOfRecentQuery";
	/**
	 * CONSTANT FOR SEARCH ON MYQUERIES AND SHARED QUERIES.
	 */
	public static final String QUERY_NAME_LIKE = "queryNameLike";
	/** Constant for SHARING_STATUS.*/
	public static final String SHARING_STATUS = "sharingStatus";
	/** Constant for IS_SAVE_AS.*/
	public static final String IS_SAVE_AS = "isSaveAs";
	/**
	 * For created by constant used in retrieving work-flows.
	 */
	public static final String WORKFLOW_WHERE_CREATED_BY = "  Workflow  where  createdBy= ";
	/**
	 * Latest Executed for  project .
	 */
	public static final String LATEST_PROJECT = "latestProject";
	/**
	 * For query used in retrieving query by title.
	 */
	public static final String QUERY_WHERE_QUERY_ID_IN = " query.id in  ";

	/**
	 * For order desc by id.
	 */
	public static final String ORDER_BY_QUERY_ID_DESC = " order by query.id desc";

	/**
	 * Constants To specify the Query types.
	 */
	public static final String IS_QUERY_UPDATED = "isQueryUpdated";
	/** Constant for IS_EDITED_QUERY.*/
	public static final String IS_EDITED_QUERY = "isEditedQuery";
	/** Constant for category.*/
	public static final String IS_Category = "isCategory";
	/** Constant for TAG_PARTITIONED_ATTRIBUTE.*/
	public static final String TAG_PARTITIONED_ATTRIBUTE = "PARTITIONED_ATTRIBUTE";
	/** Constant for ACCESS_DENIED_URL.*/
	public static final String ACCESS_DENIED_URL = "AccessDenied.do";
	/** Constant for DATE_PATTERN_MM_DD_YYYY_HH_MM.*/
	public static final String DATE_PATTERN_MM_DD_YYYY_HH_MM = "MM/dd/yyyy hh:mm:ss";
	/** Constant for EXECUTE_MANAGED_QUERY.*/
	public static final String NO_DESCRIPTION = "Description is not available";
	/**  Constant for EXECUTED_FOR_WFID.*/
	public static final String EXECUTED_FOR_WFID = "wfId";
	/** Constant for EXECUTE_MANAGED_QUERY.*/
	public static final String EXECUTE_MANAGED_QUERY = "EXECUTE_MANAGED_QUERY";
	/** Constant for QUERY_ID_MAP.*/
	public static final String QUERY_ID_MAP = "queryIdMap";
	/**
	 * For execution id map of the not parametrized queries.
	 */
	public static final String EXECID_MAPFORNOTPQ = "execIdMapForNotPQ";
	/**  Constant for TAG_HIDE_ATTRIBUTES.*/
	public static final String TAG_HIDE_ATTRIBUTES = "HIDE_ATTRIBUTES";
	/**  Constant for INTEGER MINUS_ONE.*/
	public static final int MINUS_ONE = -1;
	/**  Constant for INTEGER ZERO.*/
	public static final int ZERO = 0;
	/**  Constant for INTEGER ONE.*/
	public static final int ONE = 1;
	/** Constant for INTEGER 2.*/
	public static final int TWO = 2;
	/** Constant for INTEGER 3.*/
	public static final int THREE = 3;
	/** Constant for INTEGER 4.*/
	public static final int FOUR = 4;
	/** Constant for INTEGER 5.*/
	public static final int FIVE = 5;
	/** Constant for INTEGER 10.*/
	public static final int TEN = 10;
	/** Constant for INTEGER 100.*/
	public static final int HUNDRED = 100;
	/** Constant for ORDERED_CONCEPTS.*/
	public static final String ORDERED_CONCEPTS = "orderedConcepts";
	/** Constant for INTEGER.*/
	public static final String VOCAB_URN = "vocabURN";;
	/** Constant for HTML.*/
	public static final String HTML = "html";
	/** Constant for STATUS.*/
	public static final String STATUS = "status";
	/** Constant for SOURCE_CONCEPT_MAP.*/
	public static final String SOURCE_CONCEPT_MAP = "sourceConceptMap";
	/** Constant for ROW_CREATED.*/
	public static final String ROW_CREATED = "rowsCreated";
	/** Constant for CON_CODE_VS_VOL_INDB.*/
	public static final String CON_CODE_VS_VOL_INDB = "conceptCodeVsVolumeInDb";
	/**  Constant for FINISH TIME of execution.*/
	public static final String FINISHTIME = "FINISH_TIME";
	/** Constant for magic number -1.*/
	public static final int MINU_ONE = -1;
	/** Constant for checking is query shared.*/
	public static final String IS_SHARED = "isShared";
	/** tag to hide children nodes in tree view.*/
	public static final String TAG_HIDE_CHILDREN_NODES = "HIDE_CHILDREN_NODES";
	/** constant for response completed.*/
	public static final String RESPONSE_COMPLETED = "completed";
	/** Constant for column 'VIEW_TYPE' in table 'DATA_QUERY_EXECUTION_LOG'.*/
	public static final String VIEW_TYPE_COLUMN = "VIEW_TYPE";

}
