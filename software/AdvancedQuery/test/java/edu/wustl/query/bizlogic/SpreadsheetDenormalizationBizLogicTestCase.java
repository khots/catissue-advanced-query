package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.BaseAbstractAttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.QueryHeaderData;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.query.beans.QueryResultObjectDataBean;
import edu.wustl.query.generator.GenericQueryGeneratorMock;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.querysuite.EntityCacheFactory;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.security.privilege.PrivilegeType;

import junit.framework.TestCase;

public class SpreadsheetDenormalizationBizLogicTestCase extends TestCase
{
	public void testScanIQuery() throws MultipleRootsException
	{
		HttpSession session = new HttpSession()
		{

			public void setMaxInactiveInterval(int arg0) {
				// TODO Auto-generated method stub

			}

			public void setAttribute(String arg0, Object arg1) {
				// TODO Auto-generated method stub

			}

			public void removeValue(String arg0) {
				// TODO Auto-generated method stub

			}

			public void removeAttribute(String arg0) {
				// TODO Auto-generated method stub

			}

			public void putValue(String arg0, Object arg1) {
				// TODO Auto-generated method stub

			}

			public boolean isNew() {
				// TODO Auto-generated method stub
				return false;
			}

			public void invalidate() {
				// TODO Auto-generated method stub

			}

			public String[] getValueNames() {
				// TODO Auto-generated method stub
				return null;
			}

			public Object getValue(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			public HttpSessionContext getSessionContext() {
				// TODO Auto-generated method stub
				return null;
			}

			public ServletContext getServletContext() {
				// TODO Auto-generated method stub
				return null;
			}

			public int getMaxInactiveInterval() {
				// TODO Auto-generated method stub
				return 0;
			}

			public long getLastAccessedTime() {
				// TODO Auto-generated method stub
				return 0;
			}

			public String getId() {
				// TODO Auto-generated method stub
				return null;
			}

			public long getCreationTime() {
				// TODO Auto-generated method stub
				return 0;
			}

			public Enumeration getAttributeNames() {
				// TODO Auto-generated method stub
				return null;
			}

			public Object getAttribute(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
		QueryDetails queryDetails = new QueryDetails(session);
		queryDetails.setQuery(query);

		List<List<String>> dataList = new ArrayList<List<String>>();

		List<String> list = populateList("zzzzzzzzzzzz","755358","mrn2","730138");
		dataList.add(list);
		list = populateList("abcdefg","755418","abc_mrn1","730158");
		dataList.add(list);

		list = populateList("abcdefg","755378","abc_mrn2","730158");
		dataList.add(list);

		QuerySessionData querySessionData = new QuerySessionData();

		SessionDataBean sessionData = new SessionDataBean();
		sessionData.setAdmin(true);
		sessionData.setCsmUserId("1");
		sessionData.setFirstName("ABC");
		sessionData.setLastName("PQR");
		sessionData.setSecurityRequired(false);
		sessionData.setUserName("abc@gmail.com");

		Map<AttributeInterface, String> attributeColumnNameMap = new HashMap<AttributeInterface, String>();
		List<QueryOutputTreeAttributeMetadata> selectedAttributeMetaDataList = new ArrayList<QueryOutputTreeAttributeMetadata>();

		EntityCache cache = EntityCacheFactory.getInstance();
        EntityInterface participantEntity = GenericQueryGeneratorMock.createEntity("Participant");
        participantEntity = GenericQueryGeneratorMock.getEntity(cache, participantEntity);
        IOutputEntity participantOutputEntity = QueryObjectFactory.createOutputEntity(participantEntity);
        participantOutputEntity.getSelectedAttributes().addAll(participantEntity.getEntityAttributesForQuery());
        OutputTreeDataNode outputTreeDataNode = new OutputTreeDataNode(participantOutputEntity, 1, 0);

        EntityInterface csrEntity = GenericQueryGeneratorMock.createEntity("CollectionProtocolRegistration");
        csrEntity = GenericQueryGeneratorMock.getEntity(cache, csrEntity);
        IOutputEntity csrOutputEntity = QueryObjectFactory.createOutputEntity(csrEntity);
        csrOutputEntity.getSelectedAttributes().addAll(csrEntity.getEntityAttributesForQuery());

        AttributeInterface lastName = GenericQueryGeneratorMock.findAttribute(participantEntity,"lastName");
        AttributeInterface csrId = GenericQueryGeneratorMock.findAttribute(csrEntity,"id");
        AttributeInterface regDate = GenericQueryGeneratorMock.findAttribute(csrEntity,"registrationDate");
        AttributeInterface participantId = GenericQueryGeneratorMock.findAttribute(participantEntity,"id");
        List<AttributeInterface> attributeList = new ArrayList<AttributeInterface>();
        attributeList.add(lastName);
        attributeList.add(csrId);
        attributeList.add(regDate);
        attributeList.add(participantId);

        int i=0;
        for(AttributeInterface attribute : attributeList)
        {
		    String className = edu.wustl.query.util.global.Utility.parseClassName(attribute.getEntity().getName());
		    String attributeLabel = edu.wustl.common.util.Utility.getDisplayLabel(attribute.getName());
		    String displayNmForCol = className+" : "+attributeLabel;
		    QueryOutputTreeAttributeMetadata opTreeMetadata = new QueryOutputTreeAttributeMetadata(attribute, "Column"+i, outputTreeDataNode,
		               displayNmForCol);
		    selectedAttributeMetaDataList.add(opTreeMetadata);
		    outputTreeDataNode.addAttribute(opTreeMetadata);
		    attributeColumnNameMap.put(attribute, "Column"+i);
		    i++;
        }
        SelectedColumnsMetadata selectedColumnsMetadata = new SelectedColumnsMetadata();
        selectedColumnsMetadata.setCurrentSelectedObject(outputTreeDataNode);
        selectedColumnsMetadata.setDefinedView(true);
        selectedColumnsMetadata.setSelColNVBeanList(null);
        selectedColumnsMetadata.setSelectedAttributeMetaDataList(selectedAttributeMetaDataList);
        selectedColumnsMetadata.setSelectedOutputAttributeList(null);

        QueryResultObjectDataBean queryResultObjectDataBean = new QueryResultObjectDataBean();
        queryResultObjectDataBean.setEntity(participantEntity);
        queryResultObjectDataBean.setCsmEntityName(participantEntity.getName());
        queryResultObjectDataBean.setEntityId(844);
        queryResultObjectDataBean.setFileTypeAtrributeIndexMetadataMap(new HashMap<Integer, Object>());
        queryResultObjectDataBean.setHasAssociatedIdentifiedData(true);
        queryResultObjectDataBean.setClobeType(false);
        queryResultObjectDataBean.setReadDeniedObject(true);
        queryResultObjectDataBean.setMainEntity(participantEntity);
        queryResultObjectDataBean.setMainEntityIdentifierColumnId(0);
        queryResultObjectDataBean.setPrivilegeType(PrivilegeType.ObjectLevel);
        queryResultObjectDataBean.setTqColumnMetadataList(new ArrayList());
        List<Integer> identifiedDataColumnIds = new ArrayList<Integer>();
        identifiedDataColumnIds.add(1);
        identifiedDataColumnIds.add(3);
        identifiedDataColumnIds.add(5);
        identifiedDataColumnIds.add(6);
        identifiedDataColumnIds.add(12);
        identifiedDataColumnIds.add(14);
        queryResultObjectDataBean.setIdentifiedDataColumnIds(identifiedDataColumnIds);

        List<Integer> objectColumnIds = new ArrayList<Integer>();
        objectColumnIds.add(0);
        objectColumnIds.add(1);
        objectColumnIds.add(2);
        objectColumnIds.add(3);
        objectColumnIds.add(4);
        objectColumnIds.add(5);
        objectColumnIds.add(6);
        objectColumnIds.add(7);
        objectColumnIds.add(8);
        objectColumnIds.add(9);
        objectColumnIds.add(10);
        objectColumnIds.add(11);
        objectColumnIds.add(12);
        objectColumnIds.add(13);
        objectColumnIds.add(14);
        queryResultObjectDataBean.setObjectColumnIds(objectColumnIds);
        Map<EntityInterface, Integer> entityIdIndexMap = new HashMap<EntityInterface, Integer>();
        entityIdIndexMap.put(participantEntity, 3);
        queryResultObjectDataBean.setEntityIdIndexMap(entityIdIndexMap);
        Map<Long, QueryResultObjectDataBean> queryResultObjectDataBeanMap = new HashMap<Long, QueryResultObjectDataBean>();
        queryResultObjectDataBeanMap.put(outputTreeDataNode.getId(), queryResultObjectDataBean);

        querySessionData.setQueryResultObjectDataMap(queryResultObjectDataBeanMap);
        querySessionData.setSql("select Column0,Column1,Column2,Column3 from TEMP_OUTPUTTREE1_84116 where Column3 is NOT NULL");
		SpreadsheetDenormalizationBizLogic bizLogic = new SpreadsheetDenormalizationBizLogic();
		bizLogic.scanIQuery(queryDetails, dataList, selectedColumnsMetadata, querySessionData);
		bizLogic.setMainIdColumnIndex(1);
		IExpression partExp = GenericQueryGeneratorMock.createExpression(participantEntity);
		Map<BaseAbstractAttributeInterface,Object> denormalizationMap =
			new HashMap<BaseAbstractAttributeInterface,Object>();
		Map<String,String> columnNameMap = getColumnNameMap(querySessionData.getSql(), dataList.get(0));
		bizLogic.setColumnNameMap(columnNameMap);
		//bizLogic.populateMap(denormalizationMap, partExp, selectedAttributeMetaDataList, query.getConstraints());

		QueryHeaderData headerData = new QueryHeaderData(participantEntity, "0",partExp);
		headerData.setEntity(participantEntity);
		headerData.getEntity();

		headerData.setRecordNo("1");
		headerData.getRecordNo();

		headerData.setExpression(partExp);
		headerData.getExpression();
	}

	private Map<String, String> getColumnNameMap(String selectSql,
			List<String> dataList)
	{
		String modifiedSql;
		if(selectSql.contains(AQConstants.DISTINCT))
		{
			modifiedSql = selectSql.substring(selectSql.indexOf(AQConstants.DISTINCT)+9, selectSql.indexOf(AQConstants.FROM_CLAUSE)-1);
		}
		else
		{
			modifiedSql = selectSql.substring(selectSql.indexOf(AQConstants.SELECT_CLAUSE)+7, selectSql.indexOf(AQConstants.FROM_CLAUSE)-1);
		}
		StringTokenizer tokenizer = new StringTokenizer(modifiedSql, ",");
		Map<String,String> columnNameMap = new HashMap<String, String>();
		int index = 0;
		while(tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken();
			String data = dataList.get(index);
			if(data.length() == 0)
			{
				data = " ";
			}
			columnNameMap.put(token.trim(), data);
			index++;
		}
		return columnNameMap;
	}

	private List<String> populateList(String string, String string2,
			String string3, String string4)
	{
		List<String> list = new ArrayList<String>();
		list.add(string);
		list.add(string2);
		list.add(string3);
		list.add(string4);
		return list;
	}
}
