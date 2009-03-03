/**
 * 
 */

package edu.wustl.query.annotations.xmi;

import java.io.Serializable;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;

/**
 * @author ashish_gupta
 *
 */
public class PathObject implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	EntityInterface sourceEntity;
	EntityInterface targetEntity;
	AssociationInterface association;

	/**
	 * This method overrides the equals method of the Object Class.
	 * This method checks whether path has been added between the source entity and target entity.
	 * @return boolean true if path has been added else false. 
	 */
	@Override
	public boolean equals(Object obj)
	{

		boolean equals = false;
		if (obj instanceof PathObject)
		{

			PathObject pathObject = (PathObject) obj;
			if (pathObject.getSourceEntity() != null && pathObject.getTargetEntity() != null
					&& pathObject.getSourceEntity().getId() != null
					&& pathObject.getTargetEntity().getId() != null && sourceEntity != null
					&& targetEntity != null && sourceEntity.getId() != null
					&& targetEntity.getId() != null && association!=null & pathObject.getAssociation()!=null
					&& association.getId() !=null & pathObject.getAssociation().getId() !=null)
			{

				if ((sourceEntity.getId().compareTo(pathObject.getSourceEntity().getId()) == 0)
						&& (targetEntity.getId().compareTo(pathObject.getTargetEntity().getId()) == 0)
						&& association.getId().compareTo(pathObject.getAssociation().getId())==0)
				{

					equals = true;
				}
			}
		}
		return equals;
	}
	
	/**
	 * @return the association used in path
	 */
	public AssociationInterface getAssociation()
	{
		return association;
	}
	
	
	/**
	 * @param association association to set 
	 */
	public void setAssociation(AssociationInterface association)
	{
		this.association = association;
	}

	/**
	 * @return the sourceEntity
	 */
	public EntityInterface getSourceEntity()
	{
		return sourceEntity;
	}

	/**
	 * @param sourceEntity the sourceEntity to set
	 */
	public void setSourceEntity(EntityInterface sourceEntity)
	{
		this.sourceEntity = sourceEntity;
	}

	/**
	 * @return the targetEntity
	 */
	public EntityInterface getTargetEntity()
	{
		return targetEntity;
	}

	/**
	 * @param targetEntity the targetEntity to set
	 */
	public void setTargetEntity(EntityInterface targetEntity)
	{
		this.targetEntity = targetEntity;
	}

	@Override
	public int hashCode()
	{

		return 1;
	}
}
