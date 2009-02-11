
package edu.wustl.common.query.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.cab2b.server.path.PathFinder;
import edu.wustl.common.dao.DatabaseConnectionParams;
import edu.wustl.common.querysuite.metadata.associations.IInterModelAssociation;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.metadata.path.ICuratedPath;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;

/**
 * This class is used to find all the possible paths between two entities.
 * @author deepti_shelar
 * @author lalit_chand
 *
 */
public class CommonPathFinder implements IPathFinder
{

	private static PathFinder pathFinder = null;

	public CommonPathFinder()
	{
		if (pathFinder == null)
		{
			Connection connection = null;
			DatabaseConnectionParams DB_CONNECTION_PARAMS = new DatabaseConnectionParams();
			try
			{
				if(Variables.isExecutingTestCase)
				{
					DB_CONNECTION_PARAMS.openSession(Constants.JNDI_NAME_CIDER);
					connection = DB_CONNECTION_PARAMS.getConnection();
				}
				else
				{
					InitialContext initialContext = new InitialContext();
					Context env = (Context) initialContext.lookup("java:comp/env");
	                String dsName = (String) env.lookup("DataSource");
	                Logger.out.info("Data source name found: " + dsName);
	                
	                DataSource dataSource = (DataSource)initialContext.lookup(dsName);
	                Logger.out.info("Data source found: " + dataSource);
	                
					connection = dataSource.getConnection();
					Logger.out.info("Connection established: " + connection);
				}
				
				pathFinder = PathFinder.getInstance(connection);
			}
			catch (NamingException e)
			{
				Logger.out.error("CommonPathFinder:", e);
				//TODO need to see how to handle exception
			}
			catch (SQLException e)
			{
				Logger.out.error("CommonPathFinder:", e);
				//TODO need to see how to handle exception
			}
			catch (DAOException e)
			{
				// TODO Auto-generated catch block
				Logger.out.error("CommonPathFinder:", e);
			}
			finally
			{
				try
				{
					if(Variables.isExecutingTestCase)
					{
						DB_CONNECTION_PARAMS.commit();
						DB_CONNECTION_PARAMS.closeSession();
					}
					
					if (connection != null)
					{
						connection.close();
					}
				}
				catch (SQLException e)
				{
					Logger.out.error("CommonPathFinder:", e);
				}
				catch (DAOException e)
				{
					Logger.out.error("CommonPathFinder:", e);
				}
			}
		}
	}
	
	/**
	 * This method gets all the possible paths between two entities.
	 */
	public List<IPath> getAllPossiblePaths(EntityInterface srcEntity, EntityInterface destEntity)
	{
		//PathFinder pathFinder= getPathFinderInstance();
		return pathFinder.getAllPossiblePaths(srcEntity, destEntity);
	}

	public IPath getPathForAssociations(List<IIntraModelAssociation> intraModelAssociationList)
	{
		//PathFinder pathFinder = getPathFinderInstance();
		return pathFinder.getPathForAssociations(intraModelAssociationList);
	}

	public Set<ICuratedPath> autoConnect(Set<EntityInterface> entitySet)
	{
		return pathFinder.autoConnect(entitySet);
	}

	public Set<ICuratedPath> getCuratedPaths(EntityInterface srcEntity, EntityInterface destEntity)
	{
		return pathFinder.getCuratedPaths(srcEntity, destEntity);
	}

	//
	//	public Collection<AssociationInterface> getIncomingIntramodelAssociations(Long arg0)
	//	{
	//		return  pathFinder.getIncomingIntramodelAssociations(arg0);
	//	}

	public List<IInterModelAssociation> getInterModelAssociations(Long arg0)
	{
		return pathFinder.getInterModelAssociations(arg0);
	}
}
