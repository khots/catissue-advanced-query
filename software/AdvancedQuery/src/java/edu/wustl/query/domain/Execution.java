
package edu.wustl.query.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.actionForm.IValueObject;
import edu.wustl.common.domain.AbstractDomainObject;
import edu.wustl.common.exception.AssignDataException;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.impl.Constraints;

/**
 * This object set the execution related parameters
 * @author chitra_garg
 *
 */
public class Execution extends AbstractDomainObject implements Serializable
{

	/**
	 * Serial Version Unique Identifier.
	 */
	private static final long serialVersionUID = 1234567890L;
	private IConstraints constraints;

	private List<IOutputTerm> outputTerms;
	private Long id;
	private List<IParameter<?>> parameters;

	/**
	 * Default Constructor
	 */
	public Execution()
	{

	}

	/**
	 * Parameterized Constructor
	 *
	 */
	public Execution(Execution execution)
	{

		this.setOutputTerms(execution.getOutputTerms());
	}

	/**
	 * @returns the identifier assigned to BaseQueryObject.
	 */
	public Long getId()
	{
		return this.id;
	}

	/**
	 * @return the reference to constraints.
	 * @see edu.wustl.common.querysuite.queryobject.IQuery#getConstraints()
	    */
	public IConstraints getConstraints()
	{
		if (constraints == null)
		{
			constraints = new Constraints();
		}
		return constraints;
	}

	/**
	 * @param constraints the constraints to set.
	 * @see edu.wustl.common.querysuite.queryobject.IQuery#setConstraints
	 * (edu.wustl.common.querysuite.queryobject.IConstraints)
	 */
	public void setConstraints(IConstraints constraints)
	{
		this.constraints = constraints;
	}

	public List<IOutputTerm> getOutputTerms()
	{
		if (outputTerms == null)
		{
			outputTerms = new ArrayList<IOutputTerm>();
		}
		return outputTerms;
	}

	// for hibernate

	/**
	 * @param outputTerms IOutputTerm
	 */
	public void setOutputTerms(List<IOutputTerm> outputTerms)
	{
		this.outputTerms = outputTerms;
	}

	@Override
	public void setAllValues(IValueObject valueObject) throws AssignDataException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setId(Long id)
	{
		this.id = id;

	}

	//private List<IOutputAttribute> outputAttributeList = new ArrayList<IOutputAttribute>();

	/**
	 * @return the outputAttributeList
	     */
	//	    public List<IOutputAttribute> getOutputAttributeList() {
	//	        return outputAttributeList;
	//	    }
	//
	//	    /**
	//	     * @param outputAttributeList the outputAttributeList to set
	//	     */
	//	    public void setOutputAttributeList(List<IOutputAttribute> outputAttributeList) {
	//	        if (outputAttributeList == null) {
	//	            outputAttributeList = new ArrayList<IOutputAttribute>();
	//	        }
	//	        this.outputAttributeList = outputAttributeList;
	//	    }
	public List<IParameter<?>> getParameters()
	{
		if (parameters == null)
		{
			parameters = new ArrayList<IParameter<?>>();
		}
		return parameters;
	}

	// for hibernate
	/**
	 * @param parameters IParameter
	 */
	public void setParameters(List<IParameter<?>> parameters)
	{
		this.parameters = parameters;
	}
}
