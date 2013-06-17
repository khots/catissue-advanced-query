package edu.wustl.query.bizlogic;

import java.io.InputStream;
import java.io.OutputStream;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.beans.SharedQueryBean;


public class QueryExportImport {
	public void importQuery(SessionDataBean sdb, String queryName, InputStream in) throws BizLogicException {
		ParameterizedQuerySerializer serializer = new ParameterizedQueryXmlSerializer();
		IParameterizedQuery query = serializer.deserialize(in);
		query.setName(queryName);
		SaveQueryBizLogic saveQueryBizLogic = new SaveQueryBizLogic();
		saveQueryBizLogic.saveQuery(query, sdb, new SharedQueryBean());
	}

	public void exportQuery(SessionDataBean sdb, Long queryId, OutputStream out) throws DAOException {
		DashboardBizLogic bizLogic = new DashboardBizLogic();
		IParameterizedQuery query = bizLogic.getQueryById(queryId);

		ParameterizedQuerySerializer serializer = new ParameterizedQueryXmlSerializer();
		serializer.serialize(query, out);
	}
}
