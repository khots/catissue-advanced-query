
package edu.wustl.query.util.global;

import java.awt.Color;

public class Constants extends edu.wustl.common.util.global.Constants
{

	//Shopping cart related
	public static final String QUERY_SHOPPING_CART = "queryShoppingCart";
	public static final String CHECK_ALL_ACROSS_ALL_PAGES = "isCheckAllAcrossAllChecked";
	public static final String SELECTED_COLUMN_META_DATA = "selectedColumnMetaData";
	public static final String DELETE = "delete";

	public static final String VALIDATION_MESSAGE_FOR_ORDERING = "validationMessageForOrdering";
	public static final String PAGEOF_QUERY_MODULE = "pageOfQueryModule";
	public static final String IS_LIST_EMPTY = "isListEmpty";
	public static final String SHOPPING_CART_DELETE = "shoppingCartDelete";
	public static final String SHOPPING_CART_ADD = "shoppingCartAdd";
	public static final String PAGINATION_DATA_LIST = "paginationDataList";
	public static final String LABEL_TREE_NODE = "Label";
	public static final String HAS_CONDITION_ON_IDENTIFIED_FIELD = "hasConditionOnIdentifiedField";
	public static final String NODE_SEPARATOR = "::";
	public static final String UNDERSCORE = "_";
	public static final String ADD_TO_ORDER_LIST = "addToOrderList";
	public static final String REQUEST_TO_ORDER = "requestToOrder";
	public static final String BULK_TRANSFERS = "bulkTransfers";
	public static final String BULK_DISPOSALS = "bulkDisposals";
	public static final String OUTPUT_TERMS_COLUMNS = "outputTermsColumns";
	public static final String SUCCESS = "success";
	public static final String ENTITY_SEPARATOR = ";";
	public static final String ATTRIBUTE_SEPARATOR = "|";
	public static final String KEY_SEPARATOR = "*&*";
	public static final String KEY_CODE = "key";
	public static final String EXPORT_DATA_LIST = "exportDataList";
	public static final String ENTITY_IDS_MAP = "entityIdsMap";
	public static final String FINISH = "finish";
	public static final String QUERY_REASUL_OBJECT_DATA_MAP = "queryReasultObjectDataMap";
	public static final String DEFINE_VIEW_QUERY_REASULT_OBJECT_DATA_MAP = "defineViewQueryReasultObjectDataMap";
	public static final String CONTAINTMENT_ASSOCIATION = "CONTAINTMENT";
	public static final String MAIN_ENTITY_MAP = "mainEntityMap";

	public static final String PAGEOF_SPECIMEN_COLLECTION_REQUIREMENT_GROUP = "pageOfSpecimenCollectionRequirementGroup";
	public static final String NO_MAIN_OBJECT_IN_QUERY = "noMainObjectInQuery";
	public static final String QUERY_ALREADY_DELETED = "queryAlreadyDeleted";
	public static final String BACK = "back";
	public static final String RESTORE = "restore";
	public static final String SELECTED_COLUMN_NAME_VALUE_BEAN_LIST = "selectedColumnNameValueBeanList";
	public static final String TREE_DATA = "treeData";
	public static final String ID_NODES_MAP = "idNodesMap";
	public static final String DEFINE_RESULTS_VIEW = "DefineResultsView";
	public static final String CURRENT_PAGE = "currentPage";
	public static final String ADD_LIMITS = "AddLimits";
	public static final int QUERY_INTERFACE_BIZLOGIC_ID = 67;
	public static final String SQL = "SQL";
	public static final String ID_COLUMN_ID = "ID_COLUMN_ID";
	public static final String ID = "id";
	public static final String SAVE_TREE_NODE_LIST = "rootOutputTreeNodeList";
	public static final String ATTRIBUTE_COLUMN_NAME_MAP = "attributeColumnNameMap";
	public static final String IS_SAVED_QUERY = "isSavedQuery";
	public static final String TREE_ROOTS = "treeRoots";
	public static final String NO_OF_TREES = "noOfTrees";
	public static final String TREE_NODE_LIMIT_EXCEEDED_RECORDS = "treeNodeLimitExceededRecords";
	public static final String VIEW_LIMITED_RECORDS = "viewLimitedRecords";
	public static final String SAVE_GENERATED_SQL = "sql";
	public static final String TREENO_ZERO = "zero";
	public static final String COLUMN_NAMES = "columnNames";
	public static final String INDEX = "index";
	public static final String[] ATTRIBUTE_NAMES_FOR_TREENODE_LABEL = {"firstName", "lastName",
			"title", "name", "label", "shorttitle"};
	public static final String COLUMN_NAME = "Column";
	public static final String ON = "on";
	public static final String OFF = "off";
	public static final String PAGE_OF_QUERY_MODULE = "pageOfQueryModule";
	public static final String RANDOM_NUMBER = "randomNumber";
	public static final String IS_NOT_NULL = "is not null";
	public static final String IS_NULL = "is null";
	public static final String In = "In";
	public static final String Not_In = "Not In";
	public static final String Equals = "Equals";
	public static final String Not_Equals = "Not Equals";
	public static final String Between = "Between";
	public static final String Less_Than = "Less than";
	public static final String Less_Than_Or_Equals = "Less than or Equal to";
	public static final String Greater_Than = "Greater than";
	public static final String Greater_Than_Or_Equals = "Greater than or Equal to";
	public static final String Contains = "Contains";
	public static final String STRATS_WITH = "Starts With";
	public static final String ENDS_WITH = "Ends With";
	public static final String NOT_BETWEEN = "Not Between";
	public static final String INVALID_CONDITION_VALUES = "InvalidValues";
	public static final String SAVE_QUERY_PAGE = "Save Query Page";
	public static final String EXECUTE_QUERY_PAGE = "Execute Query Page";
	public static final String FETCH_QUERY_ACTION = "FetchQuery.do";
	public static final String EXECUTE_QUERY_ACTION = "ExecuteQueryAction.do";
	public static final String EXECUTE_QUERY = "executeQuery";
	public static final String SHOPPING_CART_FILE_NAME = "MyList.csv";
	public static final String APPLICATION_DOWNLOAD = "application/download";
	public static final String DOT_CSV = ".csv";
	public static final String HTML_CONTENTS = "HTMLContents";
	public static final String INPUT_APPLET_DATA = "inputAppletData";
	public static final String SHOW_ALL = "showall";
	public static final String SHOW_ALL_ATTRIBUTE = "Show all attributes";
	public static final String SHOW_SELECTED_ATTRIBUTE = "Show selected attributes";
	public static final String ADD_EDIT_PAGE = "Add Edit Page";
	public static final String IS_QUERY_SAVED = "isQuerySaved";
	public static final String CONDITIONLIST = "conditionList";
	public static final String QUERY_SAVED = "querySaved";
	public static final String DISPLAY_NAME_FOR_CONDITION = "_displayName";
	public static final String SHOW_ALL_LINK = "showAllLink";
	public static final String VIEW_ALL_RECORDS = "viewAllRecords";
	public static final String POPUP_MESSAGE = "popupMessage";
	public static final String ID_COLUMNS_MAP = "idColumnsMap";
	public static final String TREE_NODE_ID = "nodeId";
	public static final String HASHED_NODE_ID = "-1";
	public static final String BUTTON_CLICKED = "buttonClicked";
	public static final String UPDATE_SESSION_DATA = "updateSessionData";
	public static final String EVENT_PARAMETERS_LIST = "eventParametersList";
	public static final String VIEW = "view";
	public static final String APPLET_SERVER_URL_PARAM_NAME = "serverURL";
	public static final String TEMP_OUPUT_TREE_TABLE_NAME = "TEMP_OUTPUTTREE";
	public static final String CREATE_TABLE = "Create table ";
	public static final String AS = "as";
	public static final String TREE_NODE_FONT = "<font color='#FF9BFF' face='Verdana'><i>";
	public static final String TREE_NODE_FONT_CLOSE = "</i></font>";
	public static final String ZERO_ID = "0";
	public static final String NULL_ID = "NULL";
	public static final String UNIQUE_ID_SEPARATOR = "UQ";
	public static final String SELECT_DISTINCT = "select distinct ";
	public static final String FILE_TYPE = "file";
	public static final String FROM = " from ";
	public static final String WHERE = " where ";
	public static final String HASHED_OUT = "####";
	public static final String DYNAMIC_UI_XML = "dynamicUI.xml";
	public static final String DATE = "date";
	public static final String DATE_FORMAT = "MM-dd-yyyy";
	public static final String DEFINE_SEARCH_RULES = "Define Limits For";
	public static final String CLASSES_PRESENT_IN_QUERY = "Objects Present In Query";
	public static final String CLASS = "class";
	public static final String ATTRIBUTE = "attribute";
	public static final String FILE = "file";
	public static final String MISSING_TWO_VALUES = "missingTwoValues";
	public static final String METHOD_NAME = "method";
	public static final String categorySearchForm = "categorySearchForm";
	public static final int ADVANCE_QUERY_TABLES = 2;
	public static final String DATE_TYPE = "Date";
	public static final String INTEGER_TYPE = "Integer";
	public static final String FLOAT_TYPE = "Float";
	public static final String DOUBLE_TYPE = "Double";
	public static final String LONG_TYPE = "Long";
	public static final String SHORT_TYPE = "Short";
	public static final String FIRST_NODE_ATTRIBUTES = "firstDropDown";
	public static final String ARITHMETIC_OPERATORS = "secondDropDown";
	public static final String SECOND_NODE_ATTRIBUTES = "thirdDropDown";
	public static final String RELATIONAL_OPERATORS = "fourthDropDown";
	public static final String TIME_INTERVALS_LIST = "timeIntervals";
	public static final String ENTITY_LABEL_LIST = "entityList";

	/**
	 * Array grid component key used in map.
	 */
	String ARRAY_GRID_COMPONENT_KEY = "arrayGridComponentKey";

	/**
	 * selected cell color 
	 */
	Color CELL_SELECTION_COLOR = Color.blue;

	/**
	 * delimiter 
	 */
	String delimiter = "_";

	/**
	 * key prefix 
	 */
	String ARRAY_CONTENT_KEY_PREFIX = "SpecimenArrayContent:";
	/**
	 * Arrau specimen prefix
	 */
	String SPECIMEN_PREFIX = "Specimen:";

	/**
	 * Array specimen prefix
	 */
	String ARRAY_CONTENT_SPECIMEN_PREFIX = "Specimen_";

	/**
	 * Array specimen prefix
	 */
	String ARRAY_CONTENT_QUANTITY_PREFIX = "initialQuantity";

	String VIRTUALLY_LOCATED_CHECKBOX = "virtuallyLocatedCheckBox";

	/**
	 * array attributes name
	 */
	String[] ARRAY_CONTENT_ATTRIBUTE_NAMES = {ARRAY_CONTENT_SPECIMEN_PREFIX + "label",
			ARRAY_CONTENT_SPECIMEN_PREFIX + "barcode", ARRAY_CONTENT_QUANTITY_PREFIX,
			"concentrationInMicrogramPerMicroliter", "positionDimensionOne",
			"positionDimensionTwo", "id"};
	// ,ARRAY_CONTENT_SPECIMEN_PREFIX + "id"

	/**
	 * Specify the ARRAY_CONTENT_ATTR_LABEL_INDEX field 
	 */
	int ARRAY_CONTENT_ATTR_LABEL_INDEX = 0;

	/**
	 * Specify the ARRAY_CONTENT_ATTR_BARCODE_INDEX field 
	 */
	int ARRAY_CONTENT_ATTR_BARCODE_INDEX = 1;

	/**
	 * Specify the ARRAY_CONTENT_ATTR_QUANTITY_INDEX field 
	 */
	int ARRAY_CONTENT_ATTR_QUANTITY_INDEX = 2;

	/**
	 * Specify the ARRAY_CONTENT_ATTR_CONC_INDEX field 
	 */
	int ARRAY_CONTENT_ATTR_CONC_INDEX = 3;

	/**
	 * Specify the ARRAY_CONTENT_ATTR_POS_DIM_ONE_INDEX field 
	 */
	int ARRAY_CONTENT_ATTR_POS_DIM_ONE_INDEX = 4;

	/**
	 * Specify the ARRAY_CONTENT_ATTR_POS_DIM_TWO_INDEX field 
	 */
	int ARRAY_CONTENT_ATTR_POS_DIM_TWO_INDEX = 5;

	/**
	 * Specify the ARRAY_CONTENT_ATTR_ID_INDEX field 
	 */
	int ARRAY_CONTENT_ATTR_ID_INDEX = 6;

	/**
	 * Specify the ARRAY_CONTENT_ATTR_QUANTITY_ID_INDEX field 
	 */
	int ARRAY_CONTENT_ATTR_QUANTITY_ID_INDEX = 7;

	/**
	 * Specify the SPECIMEN_ARRAY_APPLET_ACTION field 
	 */
	String SPECIMEN_ARRAY_APPLET_ACTION = "/SpecimenArrayAppletAction.do";

	/**
	 * Specify the ADD_TO_LIMIT_ACTION field 
	 */
	String ADD_TO_LIMIT_ACTION = "/addToLimitSet.do";
	/**
	 * Specify the GET_SEARCH_RESULTS field 
	 */
	String GET_SEARCH_RESULTS = "/ViewSearchResultsAction.do";
	/**
	 * Specify the PATH_FINDER field 
	 */
	String PATH_FINDER = "/PathFinderAction.do";
	/**
	 * Specify the ADD_TO_LIMIT_ACTION field 
	 */
	String GET_DAG_VIEW_DATA = "/getDagViewDataAction.do";
	/**
	 * 
	 */
	public static final String RESOURCE_BUNDLE_PATH = "dagViewApplet.jar/ApplicationResources.properties";
	public static final String DefineSearchResultsViewAction = "/DefineSearchResultsView.do";
	/**
	 * Specify the ADD_TO_LIMIT_ACTION field 
	 */
	String DAG_VIEW_DATA = "dagViewData";
	/**
	 * Specify the ADD_TO_LIMIT_ACTION field 
	 */
	String ENTITY_MAP = "entity_map";
	/**
	 * Specify the ADD_TO_LIMIT_ACTION field 
	 */
	public static final String ENTITY_STR = "entity_str";

	/**
	 * Specimen Attributes Row Nos
	 * */
	short SPECIMEN_CHECKBOX_ROW_NO = 0; //	FOR CHECKBOXES
	short SPECIMEN_COLLECTION_GROUP_ROW_NO = 1;
	short SPECIMEN_PARENT_ROW_NO = 2;
	short SPECIMEN_LABEL_ROW_NO = 3;
	short SPECIMEN_BARCODE_ROW_NO = 4;
	short SPECIMEN_CLASS_ROW_NO = 5;
	short SPECIMEN_TYPE_ROW_NO = 6;
	short SPECIMEN_TISSUE_SITE_ROW_NO = 7;
	short SPECIMEN_TISSUE_SIDE_ROW_NO = 8;

	/**
	 * Patch ID: 3835_1_14
	 * See also: 1_1 to 1_5
	 * Description : Added created date row and changed below row no accordingly. 
	 */

	short SPECIMEN_PATHOLOGICAL_STATUS_ROW_NO = 9;
	short SPECIMEN_CREATED_DATE_ROW_NO = 10;
	short SPECIMEN_QUANTITY_ROW_NO = 11;
	short SPECIMEN_CONCENTRATION_ROW_NO = 12;
	short SPECIMEN_COMMENTS_ROW_NO = 13;
	short SPECIMEN_EVENTS_ROW_NO = 14;
	short SPECIMEN_EXTERNAL_IDENTIFIERS_ROW_NO = 15;
	short SPECIMEN_BIOHAZARDS_ROW_NO = 16;
	short SPECIMEN_DERIVE_ROW_NO = 17;

	//	Mandar: 06Nov06: location removed since auto allocation will take place.
	//	short SPECIMEN_STORAGE_LOCATION_ROW_NO = 11;

	String NO_OF_SPECIMENS = "NO_OF_SPECIMENS";
	// this is key to put specimen map in session.

	String APPLET_ACTION_PARAM_NAME = "method";

	//Constants for buttons
	public static final String MULTIPLE_SPECIMEN_EXTERNAL_IDENTIFIERS = "Add";
	public static final String MULTIPLE_SPECIMEN_BIOHAZARDS = "Add";
	public static final String MULTIPLE_SPECIMEN_EVENTS = "Add";
	public static final String MULTIPLE_SPECIMEN_DERIVE = "Add";
	public static final String MULTIPLE_SPECIMEN_MAP = "Map";
	public static final String MULTIPLE_SPECIMEN_COMMENTS = "Add";
	public static final String ADD = "Add";
	public static final String EDIT = "Edit";

	public static final String MULTIPLE_SPECIMEN_EXTERNAL_IDENTIFIERS_STRING = "external";
	public static final String MULTIPLE_SPECIMEN_BIOHAZARDS_STRING = "biohazard";
	public static final String MULTIPLE_SPECIMEN_EVENTS_STRING = "event";
	public static final String MULTIPLE_SPECIMEN_DERIVE_STRING = "derive";
	public static final String MULTIPLE_SPECIMEN_COMMENTS_STRING = "comment";

	public static final String MULTIPLE_SPECIMEN_ADD_SPECIMEN = "More";
	public static final String MULTIPLE_SPECIMEN_COPY = "Copy";
	public static final String MULTIPLE_SPECIMEN_PASTE = "Paste";
	public static final String MULTIPLE_SPECIMEN_MANDATORY = "*";
	public static final String MULTIPLE_SPECIMEN_SUBMIT = "Submit";
	public static final String MULTIPLE_SPECIMEN_DELETE_LAST = "Delete Last";

	public static final String MULTIPLE_SPECIMEN_LOCATION_LABEL = "Containerlabel_temp";
	public static final String MULTIPLE_SPECIMEN_ROW_COLUMN_SEPARATOR = "@";
	public static final String MULTIPLE_SPECIMEN_BUTTON_MAP_KEY_SEPARATOR = "@";
	//for parent specimen enable
	public static final String MULTIPLE_SPECIMEN_COLLECTION_GROUP_RADIOMAP = "collectionGroupRadioMap";

	public static final String COPY_OPERATION = "copy";
	public static final String PASTE_OPERATION = "paste";
	public static final String VALIDATOR_MODEL = "validatorModel";

	public static final String ARRAY_COPY_OPTION_LABELBAR = "Label/Barcode";
	public static final String ARRAY_COPY_OPTION_QUANTITY = "Quantity";
	public static final String ARRAY_COPY_OPTION_CONCENTRATION = "Concentration";
	public static final String ARRAY_COPY_OPTION_ALL = "All";
	public static final int LINK_BUTTON_WIDTH = 70;

	public static final Color BG_COLOR = new Color(0xf4f4f5);

	public static final char MULTIPLE_SPECIMEN_COPY_ACCESSKEY = 'C';
	public static final char MULTIPLE_SPECIMEN_PASTE_ACCESSKEY = 'P';
	public static final char MULTIPLE_SPECIMEN_DELETE_LAST_ACCESSKEY = 'D';

	public static final String MULTIPLE_SPECIMEN_CHECKBOX_LABEL = "Specimen ";

	// Dagviewapplet constants
	public static final String QUERY_OBJECT = "queryObject";
	public static final String SESSION_ID = "session_id";
	public static final String STR_TO_CREATE_QUERY_OBJ = "strToCreateQueryObject";
	public static final String ENTITY_NAME = "entityName";
	public static final String INIT_DATA = "initData";
	public static final String ATTRIBUTES = "Attributes";
	public static final String ATTRIBUTE_OPERATORS = "AttributeOperators";
	public static final String FIRST_ATTR_VALUES = "FirstAttributeValues";
	public static final String SECOND_ATTR_VALUES = "SecondAttributeValues";
	public static final String SHOW_ENTITY_INFO = "showEntityInformation";
	public static final String SRC_ENTITY = "srcEntity";
	public static final String PATHS = "paths";
	public static final String DEST_ENTITY = "destEntity";
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String SHOW_VALIDATION_MESSAGES = "showValidationMessages";
	public static final String SHOW_RESULTS_PAGE = "showViewSearchResultsJsp";
	public static final String ATTR_VALUES = "AttributeValues";
	public static final String SHOW_ERROR_PAGE = "showErrorPage";
	public static final String GET_DATA = "getData";
	public static final String SET_DATA = "setData";
	public static final String EMPTY_LIMIT_ERROR_MESSAGE = "<li><font color='red'>Please enter at least one condition to add a limit to limit set.</font></li>";
	public static final String EMPTY_DAG_ERROR_MESSAGE = "<li><font color='red'>Limit set should contain at least one limit.</font></li>";
	public static final String MULTIPLE_ROOTS_EXCEPTION = "<li><font color='red'>Expression graph should be a connected graph.</font></li>";
	public static final String EDIT_LIMITS = "<li><font color='blue'>Limit succesfully edited.</font></li>";
	public static final String DELETE_LIMITS = "<li><font color='blue'>Limit succesfully deleted.</font></li>";

	public static final String MAXIMUM_TREE_NODE_LIMIT = "resultView.maximumTreeNodeLimit";
	//public static final String ATTRIBUTES = "Attributes";
	public static final String SESSION_EXPIRY_WARNING_ADVANCE_TIME = "session.expiry.warning.advanceTime";

	public static final String SearchCategory = "SearchCategory.do";
	public static final String DefineSearchResultsViewJSPAction = "ViewSearchResultsJSPAction.do";
	public static final String NAME = "name";
	public static final String TREE_VIEW_FRAME = "treeViewFrame";
	public static final String QUERY_TREE_VIEW_ACTION = "QueryTreeView.do";
	public static final String QUERY_GRID_VIEW_ACTION = "QueryGridView.do";
	public static final String GRID_DATA_VIEW_FRAME = "gridFrame";
	public static final String PAGE_NUMBER = "pageNum";
	public static final String TOTAL_RESULTS = "totalResults";
	public static final String RESULTS_PER_PAGE = "numResultsPerPage";
	public static final String SPREADSHEET_COLUMN_LIST = "spreadsheetColumnList";
	public static final String PAGE_OF = "pageOf";

	public static final String SPREADSHEET_EXPORT_ACTION = "SpreadsheetExport.do";
	public static final int[] RESULT_PERPAGE_OPTIONS = {10, 50, 100, 500, 1000};
	public static final String PAGE_OF_PARTICIPANT_CP_QUERY = "pageOfParticipantCPQuery";
	public static final String CONFIGURE_GRID_VIEW_ACTION = "ConfigureGridView.do";
	public static final String SAVE_QUERY_ACTION = "SaveQueryAction.do";

	public static final int CHARACTERS_IN_ONE_LINE = 110;
	public static final String SINGLE_QUOTE_ESCAPE_SEQUENCE = "&#096;";
	public static final String ViewSearchResultsAction = "ViewSearchResultsAction.do";
}
