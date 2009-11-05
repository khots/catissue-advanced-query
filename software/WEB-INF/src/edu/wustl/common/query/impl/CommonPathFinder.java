
package edu.wustl.common.query.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.cab2b.server.path.PathFinder;
import edu.wustl.common.querysuite.metadata.associations.IInterModelAssociation;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.metadata.path.ICuratedPath;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Constants;

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
			try
			{
				InitialContext ctx = new InitialContext();
			    DataSource ds = (DataSource)ctx.lookup(Constants.JNDI_NAME_CIDER);
			    connection = ds.getConnection();
				
				Logger.out.info("Connection established: " + connection);
				
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
			finally
			{
				try
				{
					if (connection != null)
					{
						connection.close();
					}
				}
				catch (SQLException e)
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

	public List<IPath> getAllPathsForQuery(EntityInterface srcEntity, EntityInterface destEntity)
	{
		//PathFinder pathFinder= getPathFinderInstance();
		return pathFinder.getAllPathsForQuery(srcEntity, destEntity);
	}

	/**
	 * To set Path Finder
	 * @param pathFinder
	 */
	public static void setPathFinder(PathFinder pathFinder)
	{
		CommonPathFinder.pathFinder = pathFinder;
	}
}

